#!/usr/bin/env python3
"""Public GitHub bootstrap; Python 3.11+ stdlib, no checkout or pip required.

Downloads tooling and the bundled Philips applications, or uses --payload for
an explicitly supplied local bundle. All release
assets are checked against the size and SHA-256 returned by GitHub over TLS.
"""
from __future__ import annotations

import sys

# Keep this ahead of tomllib and every installer/dependency import. In
# particular, Ubuntu 21.04's Python 3.9 must fail clearly before initialization.
if sys.version_info < (3, 11):
    print('Installation stopped: Python 3.11 or newer is required; detected Python '
          + '.'.join(map(str, sys.version_info[:3])) + '. '
          'Use Ubuntu 24.04 or Debian 12/13 amd64 with Python 3.11+. '
          'No installation changes were made.', file=sys.stderr)
    raise SystemExit(2)

import argparse
import hashlib
import ipaddress
import json
import os
from pathlib import Path
import re
import shutil
import socket
import subprocess
import tempfile
import time
import tomllib
from urllib.parse import urlsplit
from urllib.request import HTTPRedirectHandler, Request, build_opener

REPO = 'MusicCityTelecom/linux-cmnd'
API = 'https://api.github.com/repos/' + REPO
MAX_ASSET = 32 * 1024**2
VENDOR_ASSET = 'cmnd-vendor-7.5.9.zip'
MAX_VENDOR_ASSET = 1024**3
STATE_PATHS = ('/var/lib/cmnd-deployment', '/opt/cmnd/tomcat', '/opt/cmnd/SmartCMS',
               '/opt/Philips', '/etc/linux-cmnd-management', '/var/lib/cmnd-updates',
               '/var/lib/cmnd-update-requests', '/etc/cmnd/deployment.json',
               '/etc/cmnd/tls', '/etc/cmnd/apache.conf', '/etc/cmnd/php-fpm.conf',
               '/etc/cmnd/native-tomcat.env', '/etc/cmnd/java-cacerts',
               '/etc/cmnd/configure-cms.php', '/etc/cmnd/egress.json', '/etc/cmnd/egress.py',
               '/usr/local/share/ca-certificates/linux-cmnd.crt')


def version_number(value):
    if not isinstance(value, str) or not re.fullmatch(r'v?(0|[1-9][0-9]*)\.(0|[1-9][0-9]*)\.(0|[1-9][0-9]*)', value):
        raise ValueError('Expected a numeric SemVer release such as v0.6.0')
    return tuple(map(int, value.removeprefix('v').split('.')))


def trusted_url(url):
    parsed = urlsplit(url)
    if (parsed.scheme != 'https' or parsed.username or parsed.password or parsed.port not in (None, 443)
            or parsed.hostname not in {'api.github.com', 'github.com',
                                       'release-assets.githubusercontent.com', 'objects.githubusercontent.com'}):
        raise ValueError('Download must stay on approved GitHub HTTPS hosts')
    return url


class GitHubRedirect(HTTPRedirectHandler):
    def redirect_request(self, request, fp, code, msg, headers, newurl):
        trusted_url(newurl)
        return super().redirect_request(request, fp, code, msg, headers, newurl)


def fetch(url, limit, destination=None):
    """Bounded unauthenticated HTTPS transfer; no development credentials used."""
    request = Request(trusted_url(url), headers={'User-Agent': 'linux-cmnd-bootstrap',
                                               'Accept': 'application/vnd.github+json'})
    chunks, count, started = [], 0, time.monotonic()
    with build_opener(GitHubRedirect()).open(request, timeout=30) as response:
        trusted_url(response.geturl())
        while chunk := response.read(1024 * 1024):
            count += len(chunk)
            if count > limit or time.monotonic() - started > 300:
                raise ValueError('GitHub download exceeded its size/time limit')
            if destination is None:
                chunks.append(chunk)
            else:
                destination.write(chunk)
    return b''.join(chunks)


def choose_release(releases, requested=None, channel='preview'):
    if not isinstance(releases, list):
        raise ValueError('Unexpected GitHub releases response')
    candidates = []
    for release in releases:
        if not isinstance(release, dict) or release.get('draft') or (channel == 'stable' and release.get('prerelease')):
            continue
        try:
            version = version_number(release.get('tag_name'))
        except ValueError:
            continue
        if version < (0, 5, 0) or version[0] != 0:
            continue
        if requested and version != version_number(requested):
            continue
        candidates.append((version, release))
    if not candidates:
        raise ValueError('No compatible published release found (evaluation releases use --channel preview)')
    return max(candidates, key=lambda item: item[0])[1]


def download_assets(release, destination, *, include_vendor=False):
    version = release['tag_name'].removeprefix('v')
    names = (f'linux-cmnd_{version}_amd64.deb', 'install.sh')
    if include_vendor:
        names += (VENDOR_ASSET,)
    result = {}
    for name in names:
        matches = [a for a in release.get('assets', []) if a.get('name') == name]
        if len(matches) != 1:
            raise ValueError('Missing or duplicate release asset: ' + name)
        asset = matches[0]
        size, digest = asset.get('size'), asset.get('digest', '')
        limit = MAX_VENDOR_ASSET if name == VENDOR_ASSET else MAX_ASSET
        if type(size) is not int or not 0 < size <= limit or not re.fullmatch(r'sha256:[0-9a-f]{64}', digest or ''):
            raise ValueError('Release asset lacks a valid size/SHA-256: ' + name)
        url = f'https://github.com/{REPO}/releases/download/{release["tag_name"]}/{name}'
        # Never execute a URL supplied by release notes or arbitrary asset metadata.
        path = destination / name
        with path.open('xb') as stream:
            fetch(url, size, stream)
        with path.open('rb') as stream:
            actual_digest = hashlib.file_digest(stream, 'sha256').hexdigest()
        if path.stat().st_size != size or actual_digest != digest[7:]:
            raise ValueError('GitHub asset integrity mismatch: ' + name)
        result[name] = path
    return result


def server_address(value):
    address = ipaddress.IPv4Address(value)
    if address.is_unspecified or address.is_multicast or address.is_link_local or str(address) == '255.255.255.255':
        raise ValueError('Choose a stable IPv4 address assigned to this server (not 0.0.0.0)')
    return str(address)


def configuration(address):
    address = server_address(address)
    mode = 'isolated' if ipaddress.ip_address(address).is_loopback else 'lab'
    return f'''# Generated by the Linux CMND bootstrap. No TVs are authorized.
[safety]
mode = "{mode}"
permitted_ranges = ["127.0.0.0/8"]
[network]
bind = "{address}"
callback_base_url = "http://{address}:8080"
timeout_seconds = 5
[ports]
tomcat_http = 8080
tomcat_https = 8443
apache_http = 8082
apache_https = 8444
database = 3306
[tv]
allowlist = []
'''


def check_configuration(content):
    """Minimal standalone safety check before the first GitHub request."""
    raw = tomllib.loads(content)
    address = server_address(raw['network']['bind'])
    if raw['safety']['mode'] not in {'isolated', 'lab'} or raw.get('tv', {}).get('allowlist') != []:
        raise ValueError('Fresh quick install requires isolated/lab mode and an empty TV allowlist')
    if raw['safety']['mode'] == 'isolated' and not ipaddress.ip_address(address).is_loopback:
        raise ValueError('Isolated mode requires a loopback bind address')
    for network in raw['safety']['permitted_ranges']:
        ipaddress.ip_network(network, strict=False)
    ports = raw['ports']
    listeners = [ports[key] for key in ('tomcat_http', 'tomcat_https', 'apache_http', 'apache_https', 'database')]
    if any(type(p) is not int or not 1 <= p <= 65535 for p in listeners) or len(set(listeners + [9000, 9078])) != 7:
        raise ValueError('Invalid or conflicting listener ports')
    if raw['network']['callback_base_url'] not in (f'http://{address}:{ports["tomcat_http"]}',
                                                    f'https://{address}:{ports["tomcat_https"]}'):
        raise ValueError('Callback must use the selected server IP and configured Tomcat port')
    with socket.socket() as probe:
        probe.bind((address, 0))
    for port in listeners + [9000, 9078]:
        with socket.socket() as probe:
            probe.bind(('0.0.0.0', port))
    return address, ports['apache_https']


def check_host(java_home):
    if sys.platform != 'linux' or sys.version_info < (3, 11):
        raise ValueError('Run on Ubuntu 24.04 or Debian 12/13 amd64 with Python 3.11+')
    values = {}
    for line in Path('/etc/os-release').read_text().splitlines():
        if '=' in line:
            key, value = line.split('=', 1)
            values[key] = value.strip('"\'')
    distro = values.get('ID'), values.get('VERSION_ID')
    if distro not in {('ubuntu', '24.04'), ('debian', '12'), ('debian', '13')}:
        raise ValueError('Unsupported operating system: ' + ' '.join(value or 'unknown' for value in distro)
                         + '. Supported systems: Ubuntu 24.04 or Debian 12/13 amd64')
    if subprocess.check_output(['dpkg', '--print-architecture'], text=True).strip() != 'amd64':
        raise ValueError('amd64 architecture required')
    if distro == ('debian', '13') and not (Path(java_home) / 'bin/java').is_file():
        raise ValueError('Debian 13 needs separately supplied Java 17 via --java-home')
    for value in STATE_PATHS:
        path = Path(value)
        if path.exists() or any(p.is_symlink() for p in (path, *path.parents)):
            raise ValueError('Fresh install refuses existing/symlink state: ' + value + '; use GUI updates for an installed system')
    if list(Path('/etc/systemd/system').glob('cmnd-*')):
        raise ValueError('Existing CMND service definitions must be preserved')
    memory = re.search(r'^MemTotal:\s+(\d+)', Path('/proc/meminfo').read_text(), re.M)
    if not memory or int(memory[1]) < 5_500_000:
        raise ValueError('At least 6 GiB installed RAM required')
    if shutil.disk_usage('/var/lib').free < 10 * 1024**3:
        raise ValueError('At least 10 GiB free deployment space required (40 GiB disk recommended)')


def main(argv=None):
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument('--payload', type=Path, help='optional local vendor directory/ZIP; otherwise download the release application bundle')
    group = parser.add_mutually_exclusive_group()
    group.add_argument('--server-ip', help='stable IPv4 address on this server')
    group.add_argument('--config', type=Path, help='reviewed TOML; kept unchanged')
    parser.add_argument('--release', help='pin a published version; default newest compatible release')
    parser.add_argument('--channel', choices=('preview', 'stable'), default='preview')
    parser.add_argument('--java-home', default='/usr/lib/jvm/java-17-openjdk-amd64')
    parser.add_argument('--accept-legacy-runtime', action='store_true')
    parser.add_argument('--prepare-only', action='store_true', help='prepare dependencies but do not initialize CMND')
    parser.add_argument('--execute', action='store_true')
    parser.add_argument('--dry-run', action='store_true', help='no downloads or changes; overrides --execute')
    args = parser.parse_args(argv)
    if args.release:
        version_number(args.release)
    if not args.execute or args.dry_run:
        print('PLAN: validate a fresh Linux host and empty TV allowlist; download and verify GitHub tooling;')
        print('download and verify bundled Philips applications (or use --payload); install dependencies; initialize and start CMND.')
        print('No downloads or changes made. Run with --execute for guided installation.')
        return
    if not hasattr(os, 'geteuid') or os.geteuid() != 0:
        raise ValueError('Run using sudo python3 bootstrap.py --execute')
    check_host(args.java_home)
    interactive = sys.stdin.isatty()
    payload = None
    if args.payload is not None:
        if not args.payload.exists() or any(p.is_symlink() for p in (args.payload, *args.payload.parents)):
            raise ValueError('Explicit --payload must exist and must not traverse symlinks')
        payload = args.payload.resolve()
        if not payload.is_dir() and not (payload.is_file() and payload.suffix.lower() == '.zip'):
            raise ValueError('Payload must be an extracted directory or prepared vendor ZIP')
    if args.config:
        content = args.config.read_text(encoding='utf-8')
    else:
        if args.server_ip is None and interactive:
            args.server_ip = input('Stable server IPv4 address (127.0.0.1 for local-only access): ').strip()
        if not args.server_ip:
            raise ValueError('Supply --server-ip or --config')
        content = configuration(args.server_ip)
    address, https_port = check_configuration(content)
    if not args.accept_legacy_runtime and interactive:
        print('Evaluation only: includes legacy PHP 5.6 / MySQL 5.7. Do not expose to the Internet.')
        args.accept_legacy_runtime = input('Type INSTALL to accept and start installation: ').strip() == 'INSTALL'
    if not args.accept_legacy_runtime:
        raise ValueError('Legacy runtime acceptance required: --accept-legacy-runtime')
    os.umask(0o077)
    # Private, unpredictable workspace; no shared-cache overwrite or shell interpolation.
    with tempfile.TemporaryDirectory(prefix='linux-cmnd-', dir='/var/tmp') as temp:
        work = Path(temp)
        endpoint = (API + '/releases/tags/v' + args.release.removeprefix('v')) if args.release else API + '/releases?per_page=100'
        metadata = json.loads(fetch(endpoint, 8 * 1024**2))
        selected = choose_release([metadata] if args.release else metadata, args.release, args.channel)
        version = selected['tag_name'].removeprefix('v')
        print('Downloading verified Linux CMND ' + version + ' from public GitHub...', flush=True)
        assets = download_assets(selected, work, include_vendor=payload is None)
        if payload is None:
            payload = assets[VENDOR_ASSET]
        package = assets[f'linux-cmnd_{version}_amd64.deb']
        for field, expected in (('Package', 'linux-cmnd'), ('Architecture', 'amd64'), ('Version', version)):
            if subprocess.check_output(['dpkg-deb', '-f', str(package), field], text=True).strip() != expected:
                raise ValueError('Unexpected Debian package identity')
        staging = work / 'tooling'
        subprocess.run(['dpkg-deb', '-x', str(package), str(staging)], check=True)
        source = staging / 'opt/linux-cmnd/releases' / version / 'src'
        # Import only the hash-verified release, without installing its maintainer scripts yet.
        sys.path.insert(0, str(source))
        from cmnd_linux.config import load_config
        from cmnd_linux.native_config import native_endpoint
        from cmnd_linux.application_stage import VENDOR_INPUT_HASHES
        from cmnd_linux.artifacts import safe_extract_zip, file_hash
        config = work / 'cmnd.toml'
        config.write_text(content, encoding='utf-8')
        native_endpoint(load_config(config))
        if payload.is_file():
            target = work / 'vendor'
            names = safe_extract_zip(payload, target, max_files=100, max_bytes=1024**3)
            if set(names) != set(VENDOR_INPUT_HASHES):
                raise ValueError('Prepared vendor ZIP must contain only the required original inputs')
            payload = target
        for name, expected in VENDOR_INPUT_HASHES.items():
            path = payload / name
            if not path.is_file() or any(p.is_symlink() for p in (path, *path.parents)) or file_hash(path) != expected:
                raise ValueError('Unrecognized or missing Philips input: ' + name)
        print('Configuration and all licensed inputs verified. Installing prerequisites...', flush=True)
        # Package configuration needs adduser; install it only after input validation.
        subprocess.run(['apt-get', 'update'], check=True)
        subprocess.run(['apt-get', 'install', '-y', '--no-install-recommends', 'python3', 'adduser', 'ca-certificates'],
                       env=dict(os.environ, DEBIAN_FRONTEND='noninteractive'), check=True)
        command = ['sh', str(assets['install.sh']), '--package', str(package), '--payload', str(payload),
                   '--config', str(config), '--java-home', args.java_home,
                   '--install-dependencies', '--accept-legacy-runtime', '--execute']
        if args.prepare_only:
            command.append('--prepare-only')
        subprocess.run(command, check=True)
        if not args.prepare_only:
            print(f'CMND ready: https://{address}:{https_port}/linux-cmnd/')
            print('Configuration: /etc/cmnd/cmnd.toml. No physical TVs authorized.')
        else:
            print('Dependencies prepared; rerun without --prepare-only to initialize CMND.')


if __name__ == '__main__':
    try:
        main()
    except (ValueError, KeyError, OSError, subprocess.CalledProcessError) as error:
        print('Installation stopped: ' + str(error), file=sys.stderr)
        sys.exit(1)
    except (KeyboardInterrupt, EOFError):
        print('Installation cancelled.', file=sys.stderr)
        sys.exit(130)
