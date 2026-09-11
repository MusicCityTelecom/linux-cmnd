from dataclasses import replace
from http.client import HTTPConnection
from http.server import HTTPServer
from io import BytesIO
from pathlib import Path
import tempfile
import threading
import unittest
import json
import socket
from urllib.parse import urlsplit
from unittest.mock import patch
from zipfile import BadZipFile, ZipFile

from cmnd_linux.clone_export import (ReceiveState, export_capabilities, export_clone,
    extract_multipart_files, inspect_received_zip, receive_handler)
from cmnd_linux.config import AllowedTV, Config, ConfigError
from cmnd_linux.protocol import clone_export_request, clone_info_request


def archive_bytes(name='TVSettings/TVSettings.xml'):
    result = BytesIO()
    with ZipFile(result, 'w') as archive:
        archive.writestr(name, '<Settings>synthetic</Settings>')
    return result.getvalue()


def multipart(content, filename='TVSettings.zip', boundary='cmnd-boundary'):
    return (f'--{boundary}\r\nContent-Disposition: form-data; name="file"; filename="{filename}"\r\n'
            'Content-Type: application/zip\r\n\r\n').encode() + content + f'\r\n--{boundary}--\r\n'.encode()


class CloneExportTests(unittest.TestCase):
    def test_physical_channel_list_alias_is_recognized_in_multipart_upload(self):
        raw = BytesIO()
        with ZipFile(raw, 'w') as archive:
            archive.writestr('ChannelList/htvchlist.db', b'synthetic channel database')
            archive.writestr('ChannelList/ChannelList_Identifier.txt', b'synthetic identifier')
        body = self.root / 'channels.http-body'
        body.write_bytes(multipart(raw.getvalue(), filename='ChannelList.zip'))
        results = extract_multipart_files(body, 'multipart/form-data; boundary=cmnd-boundary', self.root)
        self.assertEqual(len(results), 1)
        self.assertEqual(results[0]['identified_items'], ['TVChannelList'])
        self.assertTrue(results[0]['zip_crc_valid'])

    def test_channel_alias_requires_both_nonempty_root_markers(self):
        cases = [
            {'ChannelList/htvchlist.db': b'database'},
            {'ChannelList/htvchlist.db': b'', 'ChannelList/ChannelList_Identifier.txt': b'id'},
            {'nested/ChannelList/htvchlist.db': b'database',
             'nested/ChannelList/ChannelList_Identifier.txt': b'id'},
        ]
        for entries in cases:
            with self.subTest(entries=list(entries)):
                path = self.root / 'incomplete-channel-markers.zip'
                with ZipFile(path, 'w') as archive:
                    for name, data in entries.items():
                        archive.writestr(name, data)
                self.assertNotIn('TVChannelList', inspect_received_zip(path)['identified_items'])

    def setUp(self):
        self.temp = tempfile.TemporaryDirectory()
        self.addCleanup(self.temp.cleanup)
        self.root = Path(self.temp.name)
        self.url = 'http://127.0.0.1:18080/SmartInstall/CloneToServer/' + 'a' * 48
        self.cfg = Config('isolated', '127.0.0.1', 'http://127.0.0.1:18080', ('127.0.0.0/8',),
            (AllowedTV('127.0.0.1', 'SERIAL0001', frozenset({'clone-export'})),))

    def test_export_has_no_clone_to_tv_or_power_fields(self):
        message = clone_export_request('SERIAL0001', ['TVSettings'], self.url, correlation=12)
        self.assertEqual(message['Cookie'], 12)
        details = message['CommandDetails']
        self.assertEqual(set(details), {'WebListeningServiceParameters', 'CloneToServerParameters'})
        self.assertNotIn('IPCloneParameters', details)
        self.assertEqual(details['CloneToServerParameters']['CloneToServerDetails'][0]['CloneItemName'], 'TVSettings')
        self.assertEqual(clone_info_request('5.0')['CmdType'], 'Request')

    def test_invalid_export_targets_and_items(self):
        for url in (self.url.replace('http:', 'file:'), self.url.replace('127.0.0.1', '0.0.0.0'),
                    self.url.replace(':18080', ':0'), self.url.replace('http://', 'http://@'), self.url + '?x=1'):
            with self.assertRaises(ValueError):
                clone_export_request('SERIAL0001', ['TVSettings'], url)
        for items in ([], ['Firmware'], ['TVSettings', 'TVSettings']):
            with self.assertRaises(ValueError):
                clone_export_request('SERIAL0001', items, self.url)

    def test_export_permission_does_not_grant_push(self):
        self.cfg.authorize('127.0.0.1', 'SERIAL0001', 'clone-export', True)
        for operation in ('clone', 'power', 'room-id'):
            with self.assertRaises(ConfigError):
                self.cfg.authorize('127.0.0.1', 'SERIAL0001', operation, True)

    def test_capabilities_require_vendor_readiness_fields(self):
        for response in ({}, {'CommandDetails': None}, {'CommandDetails': {'CloneToServerParameters': []}}):
            with self.assertRaises(ConfigError):
                export_capabilities(response)
        response = {'CommandDetails': {'CloneToServerParameters': {
            'CloneToServerStatus': 'Ready', 'CloneToServerSessionStatus': {'SessionStartTime': '0'},
            'CloneItemsAvailableToServer': [{'CloneItemName': 'TVSettings'}, {'CloneItemName': 'Firmware'}]}}}
        self.assertEqual(export_capabilities(response)['available_items'], ['TVSettings'])
        response['CommandDetails']['CloneToServerParameters']['CloneToServerStatus'] = 'Busy'
        self.assertFalse(export_capabilities(response)['ready'])

    def test_preview_and_missing_permission_never_contact_tv(self):
        with patch('cmnd_linux.clone_export.verify_identity', side_effect=AssertionError('network')):
            self.assertFalse(export_clone(self.cfg, '127.0.0.1', 'SERIAL0001', self.root / 'out', items=['TVSettings'])['executed'])
            with self.assertRaises(ConfigError):
                export_clone(replace(self.cfg, allowed_tvs=()), '127.0.0.1', 'SERIAL0001', self.root / 'out', items=['TVSettings'], execute=True)
        self.assertFalse((self.root / 'out').exists())

    def test_multipart_preserves_zip_and_ignores_path_in_filename(self):
        body = self.root / 'body'
        content = archive_bytes()
        body.write_bytes(multipart(content, '../../TVSettings.zip'))
        result = extract_multipart_files(body, 'multipart/form-data; boundary=cmnd-boundary', self.root)
        self.assertEqual((self.root / result[0]['path']).read_bytes(), content)
        self.assertEqual(result[0]['identified_items'], ['TVSettings'])
        self.assertTrue(result[0]['zip_crc_valid'])
        self.assertFalse(result[0]['content_imported'])

    def test_traversal_zip_is_not_extracted_or_accepted(self):
        path = self.root / 'unsafe.zip'
        path.write_bytes(archive_bytes('../../escape'))
        with self.assertRaises(ValueError):
            inspect_received_zip(path)
        self.assertFalse((self.root / 'escape').exists())

    def test_empty_zip_cannot_count_as_a_clone(self):
        path = self.root / 'empty.zip'
        with ZipFile(path, 'w'):
            pass
        with self.assertRaises(ValueError):
            inspect_received_zip(path)

    def test_corrupt_zip_crc_is_rejected(self):
        path = self.root / 'corrupt.zip'
        path.write_bytes(archive_bytes().replace(b'synthetic', b'CORRUPTED', 1))
        with self.assertRaises((ValueError, BadZipFile)):
            inspect_received_zip(path)

    def export_fixture(self):
        with socket.socket() as sock:
            sock.bind(('127.0.0.1', 0))
            port = sock.getsockname()[1]
        cfg = replace(self.cfg, callback_base_url=f'http://127.0.0.1:{port}')
        capability = {'CommandDetails': {'CloneToServerParameters': {
            'CloneToServerStatus': 'Ready', 'CloneToServerSessionStatus': {'SessionStartTime': '0'},
            'CloneItemsAvailableToServer': [{'CloneItemName': 'TVSettings'}]}}}
        return cfg, capability

    def test_complete_export_exchange_is_receive_only(self):
        cfg, capability = self.export_fixture()
        sent = []
        content = archive_bytes()

        def send(target, message):
            self.assertEqual(target, '127.0.0.1')
            sent.append(message)
            if message['CmdType'] == 'Request':
                return capability
            details = message['CommandDetails']
            self.assertEqual(details['WebListeningServiceParameters']['TVUniqueID'], 'UNIQUEID0001')
            self.assertEqual(set(details), {'WebListeningServiceParameters', 'CloneToServerParameters'})
            url = urlsplit(details['CloneToServerParameters']['CloneToServerDetails'][0]['URL'])
            client = HTTPConnection(url.hostname, url.port, timeout=5)
            try:
                client.request('POST', url.path, multipart(content),
                    {'Content-Type': 'multipart/form-data; boundary=cmnd-boundary'})
                response = client.getresponse()
                self.assertEqual(response.status, 200)
                response.read()
            finally:
                client.close()
            return {'CmdType': 'Response'}

        with patch('cmnd_linux.clone_export.verify_identity', return_value={'identity': 'UNIQUEID0001'}) as verify, \
                patch('cmnd_linux.clone_export.WIXPClient.send', side_effect=send):
            report = export_clone(cfg, '127.0.0.1', 'SERIAL0001', self.root / 'export',
                                  items=['TVSettings'], execute=True, wait_seconds=10)
        self.assertEqual(verify.call_count, 2)
        self.assertEqual([m['CmdType'] for m in sent], ['Request', 'Change'])
        self.assertTrue(report['completed'])
        self.assertFalse(report['tv_settings_pushed'])
        self.assertFalse(report['content_imported'])
        self.assertEqual((self.root / 'export' / report['files'][0]['path']).read_bytes(), content)
        self.assertTrue(json.loads((self.root / 'export/export-report.json').read_text())['completed'])

    def test_identity_changed_before_export_sends_no_change(self):
        cfg, capability = self.export_fixture()
        with patch('cmnd_linux.clone_export.verify_identity', side_effect=[{'identity': 'UNIQUEID0001'}, {'identity': 'UNIQUEID0002'}]), \
                patch('cmnd_linux.clone_export.WIXPClient.send', return_value=capability) as send:
            with self.assertRaises(ConfigError):
                export_clone(cfg, '127.0.0.1', 'SERIAL0001', self.root / 'changed',
                             items=['TVSettings'], execute=True, wait_seconds=10)
        self.assertEqual(send.call_count, 1)
        self.assertEqual(send.call_args.args[1]['CmdType'], 'Request')
        self.assertFalse(json.loads((self.root / 'changed/export-report.json').read_text())['completed'])

    def test_partial_export_times_out_without_import_or_success(self):
        cfg, capability = self.export_fixture()

        def send(target, message):
            if message['CmdType'] == 'Request':
                return capability
            url = urlsplit(message['CommandDetails']['CloneToServerParameters']['CloneToServerDetails'][0]['URL'])
            client = HTTPConnection(url.hostname, url.port, timeout=5)
            try:
                # A valid, different item is not proof that the requested item arrived.
                client.request('POST', url.path,
                    multipart(archive_bytes('TVChannelList/channels.xml'), 'TVChannelList.zip'),
                    {'Content-Type': 'multipart/form-data; boundary=cmnd-boundary'})
                response = client.getresponse()
                self.assertEqual(response.status, 200)
                response.read()
            finally:
                client.close()
            return {'CmdType': 'Response'}

        with patch('cmnd_linux.clone_export.verify_identity', return_value={'identity': 'UNIQUEID0001'}), \
                patch('cmnd_linux.clone_export.WIXPClient.send', side_effect=send):
            report = export_clone(cfg, '127.0.0.1', 'SERIAL0001', self.root / 'partial',
                                  items=['TVSettings'], execute=True, wait_seconds=10)
        self.assertFalse(report['completed'])
        self.assertFalse(report['content_imported'])
        self.assertEqual(len(report['files']), 1)
        self.assertTrue((self.root / 'partial' / report['files'][0]['path']).exists())

    def test_busy_tv_sends_no_change_and_creates_no_receiver(self):
        cfg, capability = self.export_fixture()
        capability['CommandDetails']['CloneToServerParameters']['CloneToServerStatus'] = 'Busy'
        with patch('cmnd_linux.clone_export.verify_identity', return_value={'identity': 'UNIQUEID0001'}), \
                patch('cmnd_linux.clone_export.WIXPClient.send', return_value=capability) as send:
            with self.assertRaises(ConfigError):
                export_clone(cfg, '127.0.0.1', 'SERIAL0001', self.root / 'busy',
                             items=['TVSettings'], execute=True, wait_seconds=10)
        self.assertEqual(send.call_count, 1)
        self.assertFalse((self.root / 'busy').exists())

    def test_truncated_multipart_is_rejected(self):
        path = self.root / 'body'
        path.write_bytes(multipart(archive_bytes())[:-10])
        with self.assertRaises(ValueError):
            extract_multipart_files(path, 'multipart/form-data; boundary=cmnd-boundary', self.root)

    def test_receiver_requires_peer_and_token_then_accepts_valid_zip(self):
        state = ReceiveState('127.0.0.1', '/private-token', self.root, 1000000)
        server = HTTPServer(('127.0.0.1', 0), receive_handler(state))
        thread = threading.Thread(target=server.serve_forever, daemon=True)
        thread.start()
        try:
            for peer, path, expected in [('127.0.0.1', '/wrong', 403), ('127.0.0.2', '/private-token', 403), ('127.0.0.1', '/private-token', 200)]:
                state.target = peer
                client = HTTPConnection('127.0.0.1', server.server_port, timeout=5)
                body = multipart(archive_bytes())
                if expected == 403:
                    # A rejected upload must not send a body after the server's
                    # close. On Windows unread request bytes can reset the socket
                    # before the client receives 403. Exercise the real pre-body
                    # authorization gate instead of racing TCP teardown.
                    client.putrequest('POST', path)
                    client.putheader('Content-Type', 'multipart/form-data; boundary=cmnd-boundary')
                    client.putheader('Content-Length', str(len(body)))
                    client.putheader('Expect', '100-continue')
                    client.endheaders()
                else:
                    client.request('POST', path, body, {'Content-Type': 'multipart/form-data; boundary=cmnd-boundary'})
                response = client.getresponse()
                self.assertEqual(response.status, expected)
                response.read()
                client.close()
            self.assertEqual(len(state.files), 1)
        finally:
            server.shutdown()
            server.server_close()
            thread.join()


if __name__ == '__main__':
    unittest.main()
