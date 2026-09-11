"""Identity-gated, receive-only clone export; never import or deploy its content."""
from __future__ import annotations

from email.parser import BytesHeaderParser
from email.policy import default
from http.server import BaseHTTPRequestHandler, HTTPServer
from ipaddress import ip_address
import json
import mmap
import os
from pathlib import Path
import re
import secrets
import shutil
import stat
import subprocess
import threading
import time
from urllib.parse import urlsplit
import zipfile

from .artifacts import _safe_member, file_hash
from .config import Config, ConfigError
from .discovery import verify_identity, scan_targets
from .protocol import EXPORT_ITEMS, WIXPClient, clone_export_request, clone_info_request

MAX_UPLOAD = 1_572_864_000  # Original vendor servlet limit; firmware is not requested.


def private_json(path: Path, value: dict) -> None:
    with os.fdopen(os.open(path, os.O_WRONLY | os.O_CREAT | os.O_EXCL, 0o600), 'w', encoding='utf-8') as stream:
        json.dump(value, stream, indent=2)


def private_directory(path: Path) -> None:
    if any(parent.is_symlink() for parent in (path, *path.parents)):
        raise ConfigError('private output must not traverse symlinks')
    path.mkdir(mode=0o700, parents=False, exist_ok=False)
    if os.name == 'nt':
        who = subprocess.run(['whoami', '/user', '/fo', 'csv', '/nh'], capture_output=True, text=True, check=True)
        sid = re.search(r'S-1-[0-9-]+', who.stdout)
        if not sid:
            raise ConfigError('cannot determine Windows owner SID')
        subprocess.run(['icacls', str(path), '/inheritance:r', '/grant:r', '*' + sid.group() + ':(OI)(CI)F'],
                       check=True, capture_output=True)


def export_capabilities(response: dict) -> dict:
    details = response.get('CommandDetails')
    params = details.get('CloneToServerParameters') if isinstance(details, dict) else None
    if not isinstance(params, dict):
        raise ConfigError('TV did not advertise clone-export parameters')
    session = params.get('CloneToServerSessionStatus')
    items = params.get('CloneItemsAvailableToServer')
    if not isinstance(session, dict) or not isinstance(items, list):
        raise ConfigError('TV did not advertise the vendor clone-export capability/session fields')
    names = sorted({item['CloneItemName'] for item in items if isinstance(item, dict)
                    and isinstance(item.get('CloneItemName'), str) and item['CloneItemName'] in EXPORT_ITEMS})
    return {'ready': str(params.get('CloneToServerStatus', '')).lower() == 'ready',
            'available_items': names, 'session_start': session.get('SessionStartTime')}


def inspect_received_zip(path: Path) -> dict:
    with zipfile.ZipFile(path) as archive:
        entries = archive.infolist()
        if not any(not e.is_dir() and e.file_size > 0 for e in entries):
            raise ValueError('received ZIP has no nonempty file content')
        if len(entries) > 20000 or sum(e.file_size for e in entries) > 4 * 1024**3:
            raise ValueError('received ZIP exceeds safe inspection limits')
        seen, item_names = set(), set()
        channel_markers = set()
        for entry in entries:
            member = _safe_member(entry.filename)
            folded = member.as_posix().casefold()
            if folded in seen or stat.S_ISLNK(entry.external_attr >> 16):
                raise ValueError('ambiguous or symlink ZIP member')
            seen.add(folded)
            if entry.compress_size and entry.file_size / entry.compress_size > 1000:
                raise ValueError('received ZIP expansion ratio exceeds limit')
            for part in member.parts:
                if part in EXPORT_ITEMS:
                    item_names.add(part)
            # Physical TVs label the request TVChannelList but export ChannelList/.
            # Require the observed database and identifier, not just a folder name.
            if entry.file_size > 0 and member.as_posix() in {
                    'ChannelList/htvchlist.db', 'ChannelList/ChannelList_Identifier.txt'}:
                channel_markers.add(member.as_posix())
        if len(channel_markers) == 2:
            item_names.add('TVChannelList')
        encrypted = any(e.flag_bits & 1 for e in entries)
        if not encrypted and archive.testzip() is not None:
            raise ValueError('received ZIP CRC validation failed')
        return {'sha256': file_hash(path), 'bytes': path.stat().st_size, 'members': len(entries),
                'identified_items': sorted(item_names), 'encrypted': encrypted,
                'zip_crc_valid': None if encrypted else True, 'content_imported': False}


def extract_multipart_files(body: Path, content_type: str, output: Path) -> list[dict]:
    """Parse a disk-spooled request without loading multi-GB files into RAM."""
    header = BytesHeaderParser(policy=default).parsebytes(('Content-Type: ' + content_type + '\r\n\r\n').encode('ascii'))
    boundary = header.get_boundary()
    if (header.get_content_type() != 'multipart/form-data' or not boundary
            or not re.fullmatch(r"[A-Za-z0-9'()+_,./:=?-]{1,70}", boundary)):
        raise ValueError('invalid multipart boundary')
    delimiter = b'\r\n--' + boundary.encode('ascii')
    results = []
    with body.open('rb') as handle, mmap.mmap(handle.fileno(), 0, access=mmap.ACCESS_READ) as data:
        prefix = b'--' + boundary.encode() + b'\r\n'
        if data[:len(prefix)] != prefix:
            raise ValueError('missing multipart opening delimiter')
        position = len(prefix)
        for _ in range(64):
            header_end = data.find(b'\r\n\r\n', position, position + 8192)
            if header_end < 0:
                raise ValueError('missing or oversized multipart part headers')
            part = BytesHeaderParser(policy=default).parsebytes(data[position:header_end] + b'\r\n\r\n')
            start = header_end + 4
            end = data.find(delimiter, start)
            while end >= 0 and data[end + len(delimiter):end + len(delimiter) + 2] not in (b'--', b'\r\n'):
                end = data.find(delimiter, end + 1)
            if end < 0:
                raise ValueError('missing multipart closing delimiter')
            filename = part.get_filename()
            if filename is not None:
                # Ignore the supplied filename for filesystem placement.
                destination = output / ('received-' + secrets.token_hex(12) + '.zip')
                with os.fdopen(os.open(destination, os.O_WRONLY | os.O_CREAT | os.O_EXCL, 0o600), 'wb') as stream:
                    for offset in range(start, end, 1024 * 1024):
                        stream.write(data[offset:min(offset + 1024 * 1024, end)])
                result = inspect_received_zip(destination)
                result.update(path=destination.name, original_filename=filename)
                basename = filename.replace('\\', '/').rsplit('/', 1)[-1]
                stem = basename[:-4] if basename.lower().endswith('.zip') else basename
                if stem in EXPORT_ITEMS:
                    result['identified_items'] = sorted(set(result['identified_items']) | {stem})
                results.append(result)
            elif end - start > 65536:
                raise ValueError('oversized multipart text field')
            position = end + len(delimiter)
            if data[position:position + 2] == b'--':
                if data[position + 2:] not in (b'', b'\r\n'):
                    raise ValueError('unexpected multipart trailing bytes')
                break
            position += 2
        else:
            raise ValueError('too many multipart parts')
    if not results:
        raise ValueError('clone upload contained no files')
    return results


class ReceiveState:
    def __init__(self, target: str, path: str, output: Path, max_bytes: int, wait_seconds: int = 600):
        self.target, self.path, self.output, self.max_bytes = target, path, output, max_bytes
        self.files, self.failures = [], []
        self.total_bytes = 0
        self.lock = threading.Lock()
        self.deadline = time.monotonic() + wait_seconds


class ReceiveServer(HTTPServer):
    def get_request(self):
        connection, address = super().get_request()
        # Bound even the header-reading phase before do_POST is reached.
        connection.settimeout(5)
        return connection, address


def receive_handler(state: ReceiveState):
    class Handler(BaseHTTPRequestHandler):
        protocol_version = 'HTTP/1.1'

        def log_message(self, *_args):
            pass  # Never log the receive token or uploaded data.

        def allowed(self):
            return self.path == state.path and ip_address(self.client_address[0]) == ip_address(state.target)

        def handle_expect_100(self):
            if not self.allowed():
                self.send_error(403)
                return False
            return super().handle_expect_100()

        def do_POST(self):
            if not self.allowed():
                self.send_error(403)
                return
            try:
                self.connection.settimeout(30)
                if self.headers.get('Transfer-Encoding') or len(self.headers.get_all('Content-Length', [])) != 1:
                    raise ValueError('upload requires a single Content-Length and no transfer coding')
                length = int(self.headers['Content-Length'])
                if not 1 <= length <= MAX_UPLOAD or state.total_bytes + length > state.max_bytes:
                    raise ValueError('upload byte limit exceeded')
                state.total_bytes += length
                body = state.output / ('request-' + secrets.token_hex(12) + '.http-body')
                with os.fdopen(os.open(body, os.O_WRONLY | os.O_CREAT | os.O_EXCL, 0o600), 'wb') as stream:
                    remaining = length
                    while remaining:
                        if time.monotonic() >= state.deadline:
                            raise ValueError('clone receive deadline exceeded')
                        chunk = self.rfile.read1(min(65536, remaining))
                        if not chunk:
                            raise ValueError('truncated clone upload')
                        stream.write(chunk)
                        remaining -= len(chunk)
                files = extract_multipart_files(body, self.headers.get('Content-Type', ''), state.output)
                with state.lock:
                    state.files.extend(files)
                payload = b'Received\r\n'
                self.send_response(200)
                self.send_header('Content-Type', 'text/plain')
                self.send_header('Content-Length', str(len(payload)))
                self.send_header('Connection', 'close')
                self.end_headers()
                self.wfile.write(payload)
            except Exception as error:
                with state.lock:
                    state.failures.append(type(error).__name__)
                try:
                    self.send_error(400, 'Clone receive rejected; private evidence retained')
                except (OSError, TimeoutError):
                    pass  # Disconnected sender; the failure is already retained.
            finally:
                self.close_connection = True
    return Handler


def export_clone(config: Config, target: str, identity: str, output: Path, *,
                 items: list[str], service_version: str = '3.0', execute: bool = False,
                 wait_seconds: int = 600, max_bytes: int = 3 * 1024**3) -> dict:
    scan_targets(config, [target], 1)
    origin = urlsplit(config.callback_base_url)
    if (origin.scheme != 'http' or origin.hostname != config.bind
            or ip_address(config.bind).version != 4 or ip_address(config.bind).is_unspecified
            or origin.path not in ('', '/') or origin.query or origin.fragment):
        raise ConfigError('receiver needs an explicit IPv4 bind matching its TV-reachable HTTP origin')
    if not 10 <= wait_seconds <= 3600 or not MAX_UPLOAD <= max_bytes <= 8 * 1024**3:
        raise ConfigError('invalid receive duration or byte cap')
    token_path = '/SmartInstall/CloneToServer/' + secrets.token_hex(24)
    message = clone_export_request(identity, items, config.callback_base_url.rstrip('/') + token_path, service_version)
    report = {'executed': execute, 'direction': 'TV-to-server', 'items': items,
              'tv_settings_pushed': False, 'content_imported': False, 'completed': False}
    if not execute:
        return report
    config.authorize(target, identity, 'clone-export', True)
    selected_tv = verify_identity(config, target, identity)
    capabilities = export_capabilities(WIXPClient(config.timeout_seconds).send(target, clone_info_request(service_version)))
    if not capabilities['ready'] or not set(items) <= set(capabilities['available_items']):
        raise ConfigError('TV export is not ready or requested items are not advertised')
    if shutil.disk_usage(output.parent).free < max_bytes * 2 + 100 * 1024**2:
        raise ConfigError('insufficient private receive disk space')
    private_directory(output)
    state = ReceiveState(target, token_path, output, max_bytes, wait_seconds)
    server = ReceiveServer((config.bind, origin.port or 80), receive_handler(state))
    server.timeout = 1
    thread = threading.Thread(target=server.serve_forever, daemon=True)
    thread.start()
    try:
        # Revalidate immediately before the only Change command. It contains
        # CloneToServerParameters only; no clone-to-TV or power command exists here.
        current_tv = verify_identity(config, target, identity)
        if current_tv['identity'] != selected_tv['identity']:
            raise ConfigError('TV unique identity changed before export; no Change command sent')
        # The allowlist may use serial/MAC, but Philips requires TVUniqueID on wire.
        message = clone_export_request(current_tv['identity'], items,
            config.callback_base_url.rstrip('/') + token_path, service_version)
        response = WIXPClient(config.timeout_seconds).send(target, message)
        private_json(output / 'command-response.json', response)
        while time.monotonic() < state.deadline:
            with state.lock:
                if state.failures:
                    break
                observed = set().union(*(set(f['identified_items']) for f in state.files)) if state.files else set()
                if set(items) <= observed and all(f['zip_crc_valid'] is True for f in state.files):
                    report['completed'] = True
                    break
            time.sleep(0.25)
    finally:
        server.shutdown()
        server.server_close()
        thread.join(timeout=35)
        if state.failures:
            report['completed'] = False
        report.update(files=state.files, receive_failures=state.failures, output=str(output))
        private_json(output / 'export-report.json', report)
    return report
