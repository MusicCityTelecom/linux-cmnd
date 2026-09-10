"""Unprivileged management GUI; privileged updates are fixed-schema queued jobs."""
from __future__ import annotations

import hashlib
import hmac
from http.cookies import CookieError, SimpleCookie
from http.server import BaseHTTPRequestHandler, ThreadingHTTPServer
import json
import os
from pathlib import Path
import secrets
import threading
import time
import tempfile

from . import __version__
from . import updates


def publish_job(queue: Path, job: dict) -> None:
    """Publish complete JSON without replacing an already queued confirmation."""
    updates.validate_job(job)
    fd, name = tempfile.mkstemp(prefix='.request-', dir=queue.parent)
    temporary = Path(name)
    try:
        with os.fdopen(fd, 'w') as stream:
            json.dump(job, stream)
            stream.flush()
            os.fsync(stream.fileno())
        # Atomic, exclusive publication; a rename would overwrite another job.
        os.link(temporary, queue)
    finally:
        temporary.unlink(missing_ok=True)


def password_record(password: str, salt: str | None = None) -> dict:
    salt = salt or secrets.token_hex(24)
    digest = hashlib.pbkdf2_hmac('sha256', password.encode(), bytes.fromhex(salt), 310000).hex()
    return {'salt': salt, 'hash': digest}


def verify_password(password: str, record: dict) -> bool:
    if not isinstance(password, str) or len(password) > 1024:
        return False
    return hmac.compare_digest(password_record(password, record['salt'])['hash'], record['hash'])


class ManagementServer(ThreadingHTTPServer):
    """Bound slow clients without creating an unbounded number of worker threads."""
    def __init__(self, *args, max_clients=32, **kwargs):
        self.slots = threading.BoundedSemaphore(max_clients)
        super().__init__(*args, **kwargs)

    def process_request(self, request, client_address):
        if not self.slots.acquire(blocking=False):
            self.shutdown_request(request)
            return
        try:
            super().process_request(request, client_address)
        except BaseException:
            self.slots.release()
            raise

    def process_request_thread(self, request, client_address):
        try:
            super().process_request_thread(request, client_address)
        finally:
            self.slots.release()


class GuiState:
    def __init__(self, config: dict, *, queue: Path = updates.QUEUE, status: Path = updates.STATE / 'status.json'):
        self.config, self.queue, self.status = config, queue, status
        self.sessions, self.attempts = {}, []
        self.lock = threading.Lock()
        self.checking = False
        self.checked = 0.0
        self.result = {'available': False, 'current_version': __version__, 'not_checked': True}

    def check(self):
        with self.lock:
            if self.checking or time.monotonic() - self.checked < 60:
                return
            self.checking = True
        def worker():
            try:
                result = updates.check_release(updates.settings(), __version__)
            except Exception:
                result = {'available': False, 'current_version': __version__,
                          'error': 'Update check unavailable. Check server connectivity, GitHub access, and update settings.'}
            with self.lock:
                self.result, self.checked, self.checking = result, time.monotonic(), False
        threading.Thread(target=worker, daemon=True).start()

    def session(self, cookie: str):
        parsed = SimpleCookie()
        try:
            parsed.load(cookie)
            token = parsed['cmnd_admin'].value
        except (KeyError, ValueError, CookieError):
            return None
        with self.lock:
            session = self.sessions.get(token)
            return session if session and session['expires'] > time.monotonic() else None


def handler(state: GuiState):
    class Handler(BaseHTTPRequestHandler):
        def setup(self):
            super().setup()
            self.connection.settimeout(5)

        def log_message(self, *_args):
            pass

        def reply(self, code: int, body, *, html=False, cookie=None):
            payload = body if html else json.dumps(body).encode()
            self.send_response(code)
            self.send_header('Content-Type', 'text/html; charset=utf-8' if html else 'application/json')
            self.send_header('Content-Length', str(len(payload)))
            self.send_header('Cache-Control', 'no-store')
            self.send_header('X-Content-Type-Options', 'nosniff')
            self.send_header('X-Frame-Options', 'DENY')
            self.send_header('Content-Security-Policy', "default-src 'self'; script-src 'self'; style-src 'self'; frame-ancestors 'none'; base-uri 'none'; form-action 'self'")
            if cookie:
                self.send_header('Set-Cookie', cookie)
            self.end_headers()
            self.wfile.write(payload)

        def do_GET(self):
            if self.path in ('/', '/app.js', '/app.css'):
                name = {'/': 'index.html', '/app.js': 'app.js', '/app.css': 'app.css'}[self.path]
                path = Path(__file__).with_name('management') / name
                if name == 'index.html':
                    return self.reply(200, path.read_bytes(), html=True)
                payload = path.read_bytes()
                self.send_response(200)
                self.send_header('Content-Type', 'text/javascript' if name.endswith('.js') else 'text/css')
                self.send_header('Content-Length', str(len(payload)))
                self.send_header('X-Content-Type-Options', 'nosniff')
                self.send_header('Cache-Control', 'no-store')
                self.end_headers()
                self.wfile.write(payload)
                return
            session = state.session(self.headers.get('Cookie', ''))
            if not session:
                return self.reply(401, {'error': 'Administrator login required'})
            if self.path != '/api/status':
                return self.reply(404, {'error': 'Not found'})
            if state.config.get('check_on_startup', True) and state.checked == 0:
                state.check()
            status = json.loads(state.status.read_text()) if state.status.is_file() else {'state': 'idle'}
            with state.lock:
                result = dict(state.result, checking=state.checking)
            self.reply(200, {'update': result, 'job': status, 'csrf': session['csrf'],
                             'applications': state.config['applications']})

        def do_POST(self):
            if self.headers.get('Origin') != state.config['origin']:
                return self.reply(403, {'error': 'Origin rejected'})
            try:
                if self.headers.get('Transfer-Encoding') or len(self.headers.get_all('Content-Length', [])) != 1:
                    raise ValueError('length')
                length = int(self.headers.get('Content-Length', '0'))
                if not 1 <= length <= 4096 or self.headers.get('Content-Type') != 'application/json':
                    raise ValueError('body')
                body = json.loads(self.rfile.read(length))
                if not isinstance(body, dict):
                    raise ValueError('object')
            except (ValueError, OSError):
                return self.reply(400, {'error': 'Invalid bounded JSON request'})
            if self.path == '/api/login':
                with state.lock:
                    state.attempts = [value for value in state.attempts if time.monotonic() - value < 60]
                    if len(state.attempts) >= 12:
                        return self.reply(429, {'error': 'Too many login attempts; wait one minute'})
                    state.attempts.append(time.monotonic())
                if body.get('username') != 'admin' or not verify_password(body.get('password'), state.config['password']):
                    return self.reply(401, {'error': 'Invalid administrator credentials'})
                token = secrets.token_hex(32)
                with state.lock:
                    state.sessions = {key: value for key, value in state.sessions.items() if value['expires'] > time.monotonic()}
                    if len(state.sessions) >= 64:
                        return self.reply(429, {'error': 'Session limit reached'})
                    state.sessions[token] = {'csrf': secrets.token_hex(24), 'expires': time.monotonic() + 1800}
                return self.reply(200, {'authenticated': True}, cookie=f'cmnd_admin={token}; Path=/linux-cmnd/; Secure; HttpOnly; SameSite=Strict; Max-Age=1800')
            session = state.session(self.headers.get('Cookie', ''))
            if not session or not hmac.compare_digest(self.headers.get('X-CSRF-Token', ''), session['csrf']):
                return self.reply(403, {'error': 'Authenticated confirmation required'})
            if self.path == '/api/check':
                state.check()
                return self.reply(202, {'checking': True})
            if self.path != '/api/install':
                return self.reply(404, {'error': 'Not found'})
            try:
                updates.validate_job(body)
                with state.lock:
                    candidate = state.result.get('release')
                    if not candidate or any(body[key] != candidate[key] for key in ('version', 'release_id')):
                        raise updates.UpdateError('Refresh and confirm the currently offered version')
                    job = json.loads(state.status.read_text()) if state.status.is_file() else {}
                    if job.get('state') in ('verifying', 'backing-up', 'installing'):
                        raise updates.UpdateError('An update is already running')
                    publish_job(state.queue, body)
            except (ValueError, FileExistsError):
                return self.reply(409, {'error': 'Update not queued: stale selection, missing confirmation, or an active job'})
            self.reply(202, {'queued': True, 'version': body['version']})
    return Handler


def serve(config_path: Path):
    config = json.loads(config_path.read_text())
    state = GuiState(config)
    server = ManagementServer(('127.0.0.1', 9078), handler(state))
    if config.get('check_on_startup', True):
        state.check()
    server.serve_forever()
