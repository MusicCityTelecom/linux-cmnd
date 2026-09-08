import tempfile
import threading
import unittest
from pathlib import Path
from http.server import ThreadingHTTPServer
from unittest.mock import patch

from cmnd_linux.config import Config, ConfigError
from cmnd_linux.discovery import scan, add_tv, verify_identity
from cmnd_linux.protocol import ProtocolError
from cmnd_linux.simulator import TVState, handler_factory


class DiscoveryTests(unittest.TestCase):
    def setUp(self):
        self.cfg = Config('isolated', '127.0.0.1', 'http://127.0.0.1:8080', ('127.0.0.0/8',), ())

    def test_bad_scan_rejected_before_transport(self):
        with patch('cmnd_linux.discovery.WIXPClient.send') as transport:
            with self.assertRaises(ConfigError):
                scan(self.cfg, ['192.168.254.0/23'])
            transport.assert_not_called()

    def test_oversized_scan_rejected_before_transport(self):
        with patch('cmnd_linux.discovery.WIXPClient.send') as transport:
            with self.assertRaises(ConfigError):
                scan(self.cfg, ['127.0.0.0/8'])
            transport.assert_not_called()

    def test_identity_probe_validates_scope_and_port_before_transport(self):
        with patch('cmnd_linux.discovery.WIXPClient.send') as transport:
            for target, port in [('192.0.2.1', 9079), ('127.0.0.1', 0)]:
                with self.subTest(target=target, port=port), self.assertRaises(ConfigError):
                    verify_identity(self.cfg, target, 'SIMULATOR00000001', port=port)
            transport.assert_not_called()

    def test_scan_and_revalidated_add_without_write_permissions(self):
        server = ThreadingHTTPServer(('127.0.0.1', 0), handler_factory(TVState('SIMULATOR00000001')))
        thread = threading.Thread(target=server.serve_forever, daemon=True)
        thread.start()
        try:
            result = scan(self.cfg, ['127.0.0.1'], port=server.server_port)
            self.assertEqual(result['devices'][0]['room_id'], '0001')
            with tempfile.TemporaryDirectory() as directory:
                inventory = Path(directory) / 'inventory.sqlite'
                with self.assertRaises(ProtocolError):
                    add_tv(self.cfg, '127.0.0.1', 'WRONG-IDENTITY', inventory, port=server.server_port)
                self.assertFalse(inventory.exists())
                added = add_tv(self.cfg, '127.0.0.1', 'SIMULATOR00000001', inventory, port=server.server_port)
                self.assertTrue(added['added'])
                self.assertFalse(added['write_permissions_added'])
                self.assertFalse(added['vendor_inventory_updated'])
        finally:
            server.shutdown()
            server.server_close()
            thread.join()


if __name__ == '__main__':
    unittest.main()
