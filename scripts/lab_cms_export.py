"""Exercise native CMS local export in the synthetic qualification VM only.

This never clicks Publish or sends content to a TV. The downloaded vendor ZIP
and browser details remain private lab evidence, not repository artifacts.
"""
import argparse
import hashlib
import json
import os
from pathlib import Path, PurePosixPath
import re
import socket
import subprocess
import tempfile
from urllib.parse import urlsplit
import zipfile
import xml.etree.ElementTree as ET

from playwright.sync_api import sync_playwright


def main():
    parser = argparse.ArgumentParser()
    parser.add_argument('--node', type=int, required=True)
    parser.add_argument('--execute', action='store_true')
    parser.add_argument('--clone-first', action='store_true', help='Create a synthetic native copy so an old cached export is not reused')
    args = parser.parse_args()
    if not args.execute or args.node <= 0 or socket.gethostname() != 'cmnd-qualification':
        raise SystemExit('Requires dedicated synthetic lab, an existing content node, and --execute')
    subprocess.run(['systemctl', 'is-active', '--quiet', 'cmnd-lab-egress'], check=True)
    os.umask(0o077)
    output = Path('/home/cmndlab/browser-results')
    output.mkdir(mode=0o700, exist_ok=True)
    report = {'evidence': 'native-browser-local-export', 'node': args.node, 'tv_deployment': False}
    credentials = json.loads(Path('/home/cmndlab/browser-credentials.json').read_text())
    with sync_playwright() as runtime:
        browser = runtime.chromium.launch()
        context = browser.new_context(ignore_https_errors=False, accept_downloads=True)
        blocked = []

        def restrict(route):
            parsed = urlsplit(route.request.url)
            if (parsed.hostname not in ('127.0.0.1', 'localhost') or parsed.port not in (8080, 8082, 8443, 8444)
                    or 'publish-content-to-server' in parsed.path):
                blocked.append({'host': parsed.hostname, 'port': parsed.port, 'path': parsed.path})
                route.abort()
            else:
                route.continue_()

        context.route('**/*', restrict)
        page = context.new_page()
        try:
            report['phase'] = 'login'
            page.goto('https://127.0.0.1:8443/SmartInstall/', wait_until='domcontentloaded')
            page.locator('input[name="username"]').fill(credentials['username'])
            page.locator('input[name="password"]').fill(credentials['password'])
            page.locator('input[name="submit"]').click()
            page.wait_for_url(lambda url: urlsplit(url).path == '/SmartInstall/dev', wait_until='domcontentloaded', timeout=60000)
            report['phase'] = 'overview'
            response = page.goto('https://127.0.0.1:8444/SmartCMS/websites-overview', wait_until='domcontentloaded')
            report['overview_status'] = response.status
            if response.status != 200:
                raise RuntimeError('CMS overview did not return HTTP 200')
            if args.clone_first:
                report['phase'] = 'clone-existing-synthetic-content'
                clone = page.locator(f'.clone_website[nval="{args.node}"]')
                clone.wait_for(state='attached', timeout=15000)
                if clone.count() != 1:
                    raise RuntimeError('Expected exactly one native Clone action')
                clone.dispatch_event('click')
                page.wait_for_url(lambda url: urlsplit(url).path.startswith('/SmartCMS/website/edit/'),
                                  wait_until='domcontentloaded', timeout=60000)
                new_node = int(urlsplit(page.url).path.rsplit('/', 1)[-1])
                if new_node == args.node:
                    raise RuntimeError('Native clone did not create a separate node')
                report['source_node'] = args.node
                args.node = new_node
                report['node'] = new_node
                report['cloned_through_native_ui'] = True
                page.goto('https://127.0.0.1:8444/SmartCMS/websites-overview', wait_until='domcontentloaded')
            export = page.locator(f'.export_website[wnid="{args.node}"]')
            export.wait_for(state='attached', timeout=15000)
            if export.count() != 1:
                report['available_export_nodes'] = page.locator('.export_website').evaluate_all('(nodes) => nodes.map(n => n.getAttribute("wnid"))')
                report['overview_links'] = page.locator('a:visible').evaluate_all('(nodes) => nodes.map(n => ({text:n.innerText,href:n.getAttribute("href")}))')
                raise RuntimeError('Expected exactly one export action for the supplied synthetic node')
            report['phase'] = 'export'
            # The original UI hides actions until hover. Dispatch the native
            # Export element's event; do not forge the underlying export POST.
            report['export_action'] = 'native DOM click event'
            with page.expect_download(timeout=120000) as pending_download, page.expect_response(
                    lambda r: urlsplit(r.url).path == '/SmartCMS/publish/website', timeout=120000) as pending:
                export.dispatch_event('click')
            response = pending.value
            report['export_http_status'] = response.status
            try:
                data = response.json()
            except Exception:
                report['export_json_valid'] = False
                raise RuntimeError('Native export response was not JSON') from None
            report['export_json_valid'] = True
            report['export_status'] = data.get('status')
            if data.get('status') != 'success':
                raise RuntimeError('Native exporter reported failure')
            url = data.get('url', '')
            parsed = urlsplit(url)
            if (parsed.scheme != 'https' or parsed.hostname != '127.0.0.1' or parsed.port != 8444
                    or not parsed.path.startswith('/SmartCMS/sites/default/files/export/')
                    or not parsed.path.endswith('.zip') or parsed.query or parsed.fragment):
                raise RuntimeError('Unexpected download origin or path')
            report['phase'] = 'download-validation'
            # Chromium uses the verified guest NSS trust store. The separate
            # Playwright API client does not inherit that store; do not disable
            # TLS verification to work around the difference.
            download = pending_download.value
            if download.url != url or download.failure():
                raise RuntimeError('Native browser download failed or differed from the export URL')
            private_download = Path(tempfile.mkdtemp(prefix='cms-export-', dir=output))
            destination = private_download / 'content.zip'
            download.save_as(destination)
            if not 1024 <= destination.stat().st_size <= 128 * 1024**2:
                raise RuntimeError('Export ZIP outside qualification size limit')
            content = destination.read_bytes()
            if not 1024 <= len(content) <= 128 * 1024**2:
                raise RuntimeError('Export ZIP outside qualification size limit')
            with zipfile.ZipFile(destination) as archive:
                members = archive.infolist()
                if len(members) > 10000 or sum(m.file_size for m in members) > 1024**3:
                    raise RuntimeError('Export ZIP exceeds expansion limits')
                for member in members:
                    path = PurePosixPath(member.filename)
                    if path.is_absolute() or '..' in path.parts or '\\' in member.filename:
                        raise RuntimeError('Unsafe export ZIP member')
                if archive.testzip() is not None:
                    raise RuntimeError('Export ZIP CRC check failed')
                report['zip_members'] = len(members)
                report['html_members'] = sum(m.filename.lower().endswith('.html') for m in members)
                if not report['html_members']:
                    raise RuntimeError('Export ZIP contains no HTML')
                for required in ('index.html', 'config.txt', 'SmartInfoBrowserMetaData.xml'):
                    if required not in archive.namelist() or not archive.getinfo(required).file_size:
                        raise RuntimeError('Native export lacks required generated content')
                html = archive.read('index.html').lower()
                if b'<html' not in html or b'name="username"' in html or b'pdoexception' in html:
                    raise RuntimeError('Exported entry page is not valid content HTML')
                metadata = archive.read('SmartInfoBrowserMetaData.xml')
                report['metadata_bytes'] = len(metadata)
                report['metadata_xml_declaration'] = metadata.lstrip().startswith(b'<?xml')
                report['metadata_html_error'] = any(word in metadata.lower() for word in (b'<html', b'warning:', b'fatal error', b'pdoexception'))
                try:
                    # The inspected vendor generator intentionally emits two
                    # top-level elements. Validate that exact fragment inside a
                    # temporary parser root; never rewrite the exported bytes.
                    fragment = re.sub(rb'^\s*<\?xml[^?]*\?>', b'', metadata, count=1)
                    tree = ET.fromstring(b'<vendor-fragment>' + fragment + b'</vendor-fragment>')
                    if [child.tag for child in tree] != ['SchemaVersion', 'SmartInfoBrowser']:
                        raise RuntimeError('Unexpected native SmartInfo metadata fragment')
                    report['metadata_format'] = 'vendor SchemaVersion + SmartInfoBrowser fragment'
                except ET.ParseError as error:
                    report['metadata_parse_error_position'] = error.position
                    raise
                report['required_generated_files_valid'] = True
            report.update(zip_bytes=len(content), zip_sha256=hashlib.sha256(content).hexdigest(), zip_crc_valid=True)
            report['passed'] = True
        except Exception as error:
            # Do not print vendor response bodies, PHP warnings, or credential URLs.
            report.update(passed=False, failure_class=type(error).__name__)
        finally:
            report['blocked_browser_requests'] = len(blocked)
            report['blocked_targets'] = blocked
            report['final_path'] = urlsplit(page.url).path
            (output / 'cms-export-report.json').write_text(json.dumps(report, indent=2))
            browser.close()
    print(json.dumps(report, indent=2))
    if not report.get('passed'):
        raise SystemExit(1)


if __name__ == '__main__':
    main()
