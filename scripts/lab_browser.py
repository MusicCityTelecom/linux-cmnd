"""Observe the real, TLS-verified CMND login page in the disposable Linux VM."""
import json
import secrets
import sys
import socket
import time
from pathlib import Path
from playwright.sync_api import sync_playwright

if socket.gethostname() != 'cmnd-qualification':
    raise SystemExit('This browser runner is restricted to the disposable lab VM')
output = Path('/home/cmndlab/browser-results')
if '--cms-templates' in sys.argv and '--execute' not in sys.argv:
    raise SystemExit('CMS template selection creates synthetic content and requires --execute')
control_test = any(flag in sys.argv for flag in ('--scan-simulator', '--room-simulator', '--remote-dialog'))
if control_test and '--execute' not in sys.argv:
    raise SystemExit('Synthetic scan/import requires --execute')
if control_test:
    sys.path.insert(0, str(Path(__file__).resolve().parents[1] / 'src'))
    from cmnd_linux.config import load_config
    from cmnd_linux.discovery import verify_identity
    cfg = load_config(Path(__file__).resolve().parents[1] / 'config/cmnd.native-simulator.toml')
    if len(cfg.allowed_tvs) != 1:
        raise SystemExit('Exactly one synthetic TV must be allowlisted')
    selected_tv = cfg.allowed_tvs[0]
    if selected_tv.identity != 'SIMULATOR00000001020000000001':
        raise SystemExit('This runner is restricted to the synthetic identity')
    cfg.authorize(selected_tv.ip, selected_tv.identity, 'clone', True)
    verify_identity(cfg, selected_tv.ip, selected_tv.identity)


def await_readback(field, expected):
    deadline = time.monotonic() + 10
    while time.monotonic() < deadline:
        observed = verify_identity(cfg, selected_tv.ip, selected_tv.identity)
        if observed[field] == expected:
            return True
        time.sleep(0.1)
    return False


output.mkdir(mode=0o700, exist_ok=True)
with sync_playwright() as runtime:
    browser = runtime.chromium.launch()
    context = browser.new_context(ignore_https_errors=False, viewport={'width': 1920, 'height': 1080})
    page = context.new_page()
    errors = []
    page.on('pageerror', lambda error: errors.append(str(error.stack)[:1000]))
    response = page.goto('https://127.0.0.1:8443/SmartInstall/', wait_until='domcontentloaded', timeout=60000)
    page.screenshot(path=str(output / 'login.png'), full_page=True)
    report = {'status': response.status if response else None, 'url_without_query': page.url.split('?')[0],
        'title': page.title(), 'body': page.locator('body').inner_text()[:1500],
        'inputs': page.locator('input').evaluate_all('(nodes) => nodes.map(n => ({name:n.name,type:n.type}))')}
    credentials = Path('/home/cmndlab/browser-credentials.json')
    if credentials.exists():
        account = json.loads(credentials.read_text())
        if '--negative-login' in sys.argv:
            page.locator('input[name="username"]').fill(account['username'])
            page.locator('input[name="password"]').fill(secrets.token_hex(24))
            page.locator('input[name="submit"]').click()
            page.wait_for_load_state('domcontentloaded')
            report['failed_login'] = {'url': page.url.split('?')[0], 'body': page.locator('body').inner_text()[:1500]}
        page.locator('input[name="username"]').fill(account['username'])
        page.locator('input[name="password"]').fill(account['password'])
        page.locator('input[name="submit"]').click()
        page.wait_for_load_state('domcontentloaded')
        report['correct_login'] = {'url': page.url.split('?')[0], 'title': page.title(), 'body': page.locator('body').inner_text()[:3000]}
        page.screenshot(path=str(output / 'after-login.png'), full_page=True)
        if '--room-simulator' in sys.argv:
            cfg.authorize(selected_tv.ip, selected_tv.identity, 'room-id', True)
            verify_identity(cfg, selected_tv.ip, selected_tv.identity)
            room = page.locator('#tv_RID_' + selected_tv.identity)
            report['room_before'] = room.input_value()
            expected_room = '0043' if report['room_before'] == '0042' else '0042'
            room.fill(expected_room)
            room.press('Tab')
            report['room_after_local'] = room.input_value()
            report['room_readback_verified'] = await_readback('room_id', expected_room)
        if '--remote-dialog' in sys.argv:
            cfg.authorize(selected_tv.ip, selected_tv.identity, 'power', True)
            verify_identity(cfg, selected_tv.ip, selected_tv.identity)
            page.locator('a[name="remote_control"]').click()
            report['remote_dialog'] = page.locator('body').inner_text()[-2500:]
            report['remote_controls'] = page.locator('button:visible,a:visible,input:visible').evaluate_all(
                '(nodes)=>nodes.map(n=>({tag:n.tagName,id:n.id,name:n.name,text:n.innerText,onclick:n.getAttribute("onclick")}))')
            report['remote_selects'] = page.locator('select:visible').evaluate_all(
                '(nodes)=>nodes.map(n=>({id:n.id,name:n.name,options:Array.from(n.options).slice(0,8).map(o=>({label:o.label,value:o.value})),parent:n.parentElement.outerHTML.slice(0,1600)}))')
            if '--power-simulator' in sys.argv:
                page.locator('#power').select_option('Standby')
                report['power_send_controls'] = page.locator('button:visible').evaluate_all(
                    '(nodes)=>nodes.filter(n=>n.innerText.trim()==="Send").map(n=>n.outerHTML)')
                page.locator('button:visible').filter(has_text='Send').first.click()
                report['power_command_submitted'] = 'Standby'
                report['power_readback_verified'] = await_readback('power', 'Standby')
        if '--inspect-controls' in sys.argv:
            page.locator('body').filter(has_text='172.30.44.4').wait_for(timeout=15000)
            report['controls'] = page.locator('input:visible,button:visible,a:visible').evaluate_all(
                '(nodes) => nodes.map(n=>({tag:n.tagName,name:n.name,id:n.id,type:n.type,text:n.innerText,href:n.getAttribute("href"),onclick:n.getAttribute("onclick")}))')
        if '--cms' in sys.argv:
            response = page.goto('https://127.0.0.1:8444/SmartCMS/', wait_until='domcontentloaded', timeout=60000)
            body = page.locator('body').inner_text()
            # Legacy PHP errors can expose configuration values. Do not print body.
            report['cms'] = {'status': response.status if response else None,
                'url': page.url.split('?')[0], 'title': page.title(), 'body_length': len(body),
                'php_error': any(word in body for word in ('PDOException', 'Fatal error', 'Warning:', 'Internal script failure')),
                'links': page.locator('a:visible').evaluate_all('(nodes)=>nodes.map(n=>({text:n.innerText,href:n.getAttribute("href")}))')}
            if '--cms-templates' in sys.argv:
                page.get_by_role('link', name='Create new content', exact=True).click()
                try:
                    page.wait_for_url('**/website/edit/*', timeout=15000)
                except Exception:
                    report['cms_editor_navigation_failed'] = True
                report['cms_templates'] = {'url': page.url.split('?')[0], 'title': page.title(), 'body': page.locator('body').inner_text()[:2500],
                    'links': page.locator('a:visible').evaluate_all('(nodes)=>nodes.map(n=>({text:n.innerText,href:n.getAttribute("href")}))')}
        if '--add-dialog' in sys.argv:
            page.locator('#addDetectDevicesBtn').click()
            try:
                page.locator('input[name="search_start_ip"]').wait_for(state='visible', timeout=15000)
            except Exception:
                report['add_dialog_wait_failed'] = True
            report['add_dialog'] = {'body': page.locator('body').inner_text()[-4500:],
                'inputs': page.locator('input:visible,select:visible,button:visible').evaluate_all('(nodes) => nodes.map(n => ({name:n.name,id:n.id,type:n.type,text:n.innerText,options:n.options ? Array.from(n.options).map(o=>({label:o.label,value:o.value})):undefined}))')}
            page.screenshot(path=str(output / 'add-tv-dialog.png'), full_page=True)
            if '--scan-simulator' in sys.argv:
                page.locator('#add_ip').select_option('Manual')
                page.locator('input[name="search_start_ip"]').fill(selected_tv.ip)
                page.locator('input[name="search_end_ip"]').fill(selected_tv.ip)
                page.locator('#detect_target').select_option('0')
                page.locator('#detectDevicesSelectAll_').check()
                page.locator('#searchDevice').click()
                try:
                    page.wait_for_function("document.body.innerText.includes('43HFL6114U/27')", timeout=30000)
                except Exception:
                    report['simulator_detection_wait_failed'] = True
                report['after_scan'] = page.locator('body').inner_text()[-5000:]
                page.screenshot(path=str(output / 'after-scan.png'), full_page=True)
    if '--room-simulator' in sys.argv:
        page.goto('https://127.0.0.1:8443/SmartInstall/dev?type=index', wait_until='domcontentloaded')
        try:
            report['vendor_room_after_reload'] = page.locator('#tv_RID_' + selected_tv.identity).input_value(timeout=10000)
            report['vendor_room_persisted'] = report['vendor_room_after_reload'] == expected_room
        except Exception:
            report['vendor_room_persisted'] = False
            report['reload_diagnostic'] = {'url': page.url.split('?')[0], 'title': page.title(),
                'body': page.locator('body').inner_text()[:1800]}
    report['javascript_errors'] = errors
    (output / 'latest-report.json').write_text(json.dumps(report, indent=2))
    if '--summary' in sys.argv:
        report = {key: value for key, value in report.items() if key not in
                  {'controls', 'remote_controls', 'remote_selects', 'remote_dialog', 'add_dialog', 'inputs', 'body'}}
        for key in ('correct_login', 'cms', 'cms_templates'):
            if key in report:
                report[key] = {field: value for field, value in report[key].items() if field not in {'body', 'links'}}
    print(json.dumps(report, indent=2))
    browser.close()
    if any(report.get(key) is False for key in ('room_readback_verified', 'vendor_room_persisted', 'power_readback_verified')):
        raise SystemExit(1)
