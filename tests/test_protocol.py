import unittest

from cmnd_linux.protocol import clone_request, discovery_request, power_request


class ProtocolFixtureTests(unittest.TestCase):
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
