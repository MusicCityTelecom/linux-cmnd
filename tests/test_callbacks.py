import json
import threading
import unittest
from http.server import ThreadingHTTPServer
from urllib.request import Request, urlopen

from cmnd_linux.callbacks import CallbackState, handler_factory


class CallbackTests(unittest.TestCase):
    def setUp(self):
        self.state = CallbackState()
        self.server = ThreadingHTTPServer(("127.0.0.1", 0), handler_factory(self.state))
        self.thread = threading.Thread(target=self.server.serve_forever, daemon=True); self.thread.start()

    def tearDown(self):
        self.server.shutdown(); self.server.server_close(); self.thread.join(timeout=2)

    def test_clone_progress_is_recorded_and_poll_command_returned(self):
        identity = "SIMULATOR00000001"
        callback = {"Svc": "WebServices", "SvcVer": "4.20", "Cookie": -1,
                    "CmdType": "Response", "Fun": "IPCloneService",
                    "CommandDetails": {"WebServiceParameters": {"TVUniqueID": identity},
                                       "IPCloneParameters": {"CloneSessionStatus": {"SessionStatus": "Successful"},
                                                              "CurrentUpgradeStatus": "NotinUpgradeMode"}}}
        body = json.dumps(callback).encode()
        request = Request(f"http://127.0.0.1:{self.server.server_port}/SmartInstall/webservices.jsp",
                          data=body, headers={"Content-Type": "application/json"}, method="POST")
        with urlopen(request, timeout=2) as response:
            command = json.loads(response.read())
        self.assertEqual(command["CmdType"], "Change")
        self.assertEqual(command["Fun"], "IPCloneService")
        self.assertEqual(self.state.latest(identity)["CommandDetails"]["IPCloneParameters"]["CloneSessionStatus"]["SessionStatus"], "Successful")


if __name__ == "__main__": unittest.main()
