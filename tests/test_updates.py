import hashlib
from http.client import HTTPConnection
from http.server import ThreadingHTTPServer
import json
from pathlib import Path
import tempfile
import threading
import unittest
from unittest.mock import patch

from cmnd_linux.updates import (UpdateError, select_release, validate_job, validate_manifest,
                               version_tuple, download_asset, install_pending)
from cmnd_linux.update_gui import GuiState, ManagementServer, handler, password_record, verify_password, publish_job


def release(version='0.5.0', **overrides):
    result = {'id': 123, 'tag_name': 'v' + version, 'draft': False, 'prerelease': False,
              'assets': [{'id': index, 'name': name, 'size': 123, 'digest': 'sha256:' + 'a'*64}
                         for index, name in enumerate((f'linux-cmnd_{version}_amd64.deb', 'linux-cmnd-update.json'), 1)]}
    result.update(overrides)
    return result


class UpdateTests(unittest.TestCase):
    def test_queue_is_complete_at_publication_and_never_overwritten(self):
        import os
        with tempfile.TemporaryDirectory() as root:
            queue = Path(root) / 'request.json'
            job = {'version': '0.5.0', 'release_id': 123, 'execute': True}
            original_link = os.link
            def observe(source, destination):
                self.assertFalse(queue.exists())
                self.assertEqual(json.loads(Path(source).read_text()), job)
                original_link(source, destination)
            with patch('cmnd_linux.update_gui.os.link', side_effect=observe):
                publish_job(queue, job)
            with self.assertRaises(FileExistsError):
                publish_job(queue, dict(job, release_id=124))
            self.assertEqual(json.loads(queue.read_text()), job)
            self.assertEqual(list(Path(root).iterdir()), [queue])

    def test_management_server_bounds_workers_and_releases_slots(self):
        server = ManagementServer(('127.0.0.1', 0), lambda *_: None, max_clients=1)
        try:
            self.assertTrue(server.slots.acquire(blocking=False))
            with patch.object(server, 'shutdown_request') as closed, \
                    patch.object(ThreadingHTTPServer, 'process_request') as dispatched:
                server.process_request('synthetic-socket', ('127.0.0.1', 1234))
            closed.assert_called_once()
            dispatched.assert_not_called()
            with patch.object(ThreadingHTTPServer, 'process_request_thread'):
                server.process_request_thread('synthetic-socket', ('127.0.0.1', 1234))
            self.assertTrue(server.slots.acquire(blocking=False))
        finally:
            server.server_close()

    def test_numeric_version_order_and_no_downgrade(self):
        self.assertGreater(version_tuple('0.10.0'), version_tuple('0.9.99'))
        self.assertIsNone(select_release([release('0.4.0')], '0.5.0', 'stable'))
        self.assertEqual(select_release([release('0.9.0'), release('0.10.0')], '0.5.0', 'stable')['version'], '0.10.0')
        for value in ('1.2', '01.2.3', 'v1.2.3', '1.2.3;id'):
            with self.assertRaises(UpdateError):
                version_tuple(value)

    def test_drafts_and_preview_channel(self):
        self.assertIsNone(select_release([release(draft=True)], '0.4.0', 'preview'))
        self.assertIsNone(select_release([release(prerelease=True)], '0.4.0', 'stable'))
        self.assertTrue(select_release([release(prerelease=True)], '0.4.0', 'preview')['prerelease'])

    def test_asset_digest_and_unique_names_required(self):
        value = release()
        value['assets'][0]['digest'] = None
        with self.assertRaises(UpdateError):
            select_release([value], '0.4.0', 'preview')
        value = release()
        value['assets'].append(value['assets'][0])
        with self.assertRaises(UpdateError):
            select_release([value], '0.4.0', 'preview')

    def test_manifest_blocks_vendor_database_major_changes(self):
        value = {'schema': 1, 'version': '0.5.0', 'minimum_version': '0.4.0', 'scope': 'tooling-only',
                 'vendor': '7.5.9', 'database_migration': False, 'vendor_payload_changed': False}
        validate_manifest(value, '0.4.0', '0.5.0', '7.5.9')
        for key, replacement in [('vendor', '8.0'), ('database_migration', True), ('scope', 'full'),
                                 ('vendor_payload_changed', True), ('minimum_version', '0.6.0')]:
            with self.assertRaises(UpdateError):
                validate_manifest(dict(value, **{key: replacement}), '0.4.0', '0.5.0', '7.5.9')
        with self.assertRaises(UpdateError):
            validate_manifest(dict(value, version='1.0.0'), '0.4.0', '1.0.0', '7.5.9')

    def test_no_arbitrary_install_job_or_implicit_execution(self):
        value = {'version': '0.5.0', 'release_id': 123, 'execute': True}
        validate_job(value)
        for job in (dict(value, execute=False), dict(value, url='https://evil.test/x.deb'), dict(value, release_id=True)):
            with self.assertRaises(UpdateError):
                validate_job(job)
        with self.assertRaises(UpdateError):
            install_pending()

    def test_download_hash_mismatch_rejected(self):
        with tempfile.TemporaryDirectory() as root:
            output = Path(root) / 'candidate'
            def fake_fetch(*args, **kwargs):
                kwargs['destination'].write_bytes(b'candidate')
            with patch('cmnd_linux.updates.fetch', side_effect=fake_fetch):
                with self.assertRaises(UpdateError):
                    download_asset({'id':1, 'size':9, 'digest':'sha256:'+'0'*64}, {}, output)

    def test_password_not_stored_as_plaintext(self):
        value = password_record('synthetic-only-password')
        self.assertTrue(verify_password('synthetic-only-password', value))
        self.assertFalse(verify_password('wrong', value))
        self.assertNotIn('synthetic-only-password', json.dumps(value))

    def test_gui_login_csrf_and_exact_confirmation_queue(self):
        with tempfile.TemporaryDirectory() as root:
            queue, status = Path(root)/'request.json', Path(root)/'status.json'
            state = GuiState({'origin':'https://localhost:8444', 'password':password_record('synthetic-password'),
                              'applications':{}, 'check_on_startup':False}, queue=queue, status=status)
            state.result = {'release':select_release([release()], '0.4.0', 'preview')}
            server = ThreadingHTTPServer(('127.0.0.1', 0), handler(state))
            thread = threading.Thread(target=server.serve_forever, daemon=True)
            thread.start()
            def request(path, body=None, cookie=None, csrf='', origin='https://localhost:8444'):
                client = HTTPConnection('127.0.0.1', server.server_port, timeout=5)
                headers = {'Origin':origin, 'Content-Type':'application/json', 'X-CSRF-Token':csrf}
                if cookie:
                    headers['Cookie'] = cookie
                client.request('POST' if body is not None else 'GET', path, None if body is None else json.dumps(body), headers)
                response = client.getresponse()
                result = response.status, dict(response.getheaders()), json.loads(response.read())
                client.close()
                return result
            try:
                self.assertEqual(request('/api/status')[0], 401)
                self.assertEqual(request('/api/login', {'username':'admin','password':'synthetic-password'}, origin='https://evil.test')[0], 403)
                code, headers, _ = request('/api/login', {'username':'admin','password':'synthetic-password'})
                self.assertEqual(code, 200)
                self.assertIn('Secure; HttpOnly; SameSite=Strict', headers['Set-Cookie'])
                cookie = headers['Set-Cookie'].split(';')[0]
                data = request('/api/status', cookie=cookie)[2]
                job = {'version':'0.5.0', 'release_id':123, 'execute':True}
                self.assertEqual(request('/api/install', job, cookie)[0], 403)
                self.assertEqual(request('/api/install', dict(job, execute=False), cookie, data['csrf'])[0], 409)
                self.assertFalse(queue.exists())
                self.assertEqual(request('/api/install', job, cookie, data['csrf'])[0], 202)
                self.assertEqual(json.loads(queue.read_text()), job)
                self.assertEqual(request('/api/install', job, cookie, data['csrf'])[0], 409)
            finally:
                server.shutdown(); server.server_close(); thread.join()


if __name__ == '__main__':
    unittest.main()
