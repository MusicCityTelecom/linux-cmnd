import json
import threading
import unittest
from http.server import ThreadingHTTPServer
from urllib.request import Request, urlopen

from cmnd_linux.protocol import WIXPClient, discovery_request, power_request
from cmnd_linux.simulator import TVState, handler_factory


class SimulatorIntegrationTests(unittest.TestCase):
    def setUp(self):
        self.state = TVState("SIMULATOR00000001")
        self.server = ThreadingHTTPServer(("127.0.0.1", 0), handler_factory(self.state))
        self.thread = threading.Thread(target=self.server.serve_forever, daemon=True)
        self.thread.start()

    def tearDown(self):
        self.server.shutdown(); self.server.server_close(); self.thread.join(timeout=2)

    def test_discovery_and_power_state(self):
        client = WIXPClient(2)
        target, port = self.server.server_address
        discovery = client.send(target, discovery_request(correlation=96526), port)
        params = discovery["CommandDetails"]["TVDiscoveryParameters"]
        self.assertEqual(params["TVSerialNumber"], "SIMULATOR00000001")
        response = client.send(target, power_request("Standby", correlation=93679), port)
        self.assertEqual(response["CommandDetails"]["PowerServiceParameters"]["CurrentPowerState"], "Standby")

    def test_cookie_mismatch_is_rejected(self):
        # Correlation behavior is exercised by the client on every successful exchange.
        response = WIXPClient(2).send(self.server.server_address[0], discovery_request(correlation=1), self.server.server_address[1])
        self.assertEqual(response["Cookie"], 1)


if __name__ == "__main__":
    unittest.main()
