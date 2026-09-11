import unittest
import json
from unittest.mock import MagicMock, patch

from cmnd_linux.protocol import WIXPClient, ProtocolError, cookie, clone_request, discovery_request, power_request


class ProtocolFixtureTests(unittest.TestCase):
    def test_generated_cookie_uses_vendor_half_open_range(self):
        for boundary in (0, 99_998):
            with self.subTest(boundary=boundary):
                source = MagicMock(spec=['randrange'])
                source.randrange.return_value = boundary
                with patch('cmnd_linux.protocol.random.SystemRandom', return_value=source):
                    self.assertEqual(cookie(), boundary)
                    self.assertEqual(discovery_request()['Cookie'], boundary)
                self.assertEqual(source.randrange.call_count, 2)
                source.randrange.assert_called_with(0, 99_999)

    def test_real_response_cookie_mismatch_remains_rejected(self):
        message = discovery_request(correlation=45123)
        response = dict(message, Cookie=45124, CmdType='Response')
        stream = MagicMock()
        stream.headers.get_content_type.return_value = 'application/json'
        stream.read.return_value = json.dumps(response).encode()
        opener = MagicMock()
        opener.open.return_value.__enter__.return_value = stream
        with patch('cmnd_linux.protocol.build_opener', return_value=opener):
            with self.assertRaisesRegex(ProtocolError, 'correlation cookie'):
                WIXPClient().send('127.0.0.1', message)

    def test_discovery_matches_capture_shape(self):
        self.assertEqual(discovery_request(correlation=96526), {
            "Svc": "WebListeningServices", "SvcVer": "1.0", "Cookie": 96526,
            "CmdType": "Request", "Fun": "TVDiscoveryService"})

    def test_power_matches_capture_shape(self):
        msg = power_request("Standby", correlation=34813)
        self.assertEqual(msg["CommandDetails"], {"ToPowerState": "Standby"})
        self.assertEqual(msg["CmdType"], "Change")

    def test_clone_preserves_identity_and_url(self):
        msg = clone_request("SERIAL0001", "RoomSpecificSettings", "07/08/2026:15:08",
                            "http://127.0.0.1:8080/SmartInstall/Profile/Clone/device/RoomSpecificSettings.zip",
                            correlation=30114)
        self.assertEqual(msg["Cookie"], 30114)
        details = msg["CommandDetails"]["IPCloneParameters"]["CloneItemDownloadDetails"][0]
        self.assertEqual(details["CloneItemDetails"]["CloneItemName"], "RoomSpecificSettings")

    def test_unsupported_power_is_rejected(self):
        with self.assertRaises(ValueError):
            power_request("Off")


if __name__ == "__main__":
    unittest.main()
