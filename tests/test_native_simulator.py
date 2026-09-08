import hashlib
from http.server import BaseHTTPRequestHandler, ThreadingHTTPServer
from io import BytesIO
import json
import threading
import time
import unittest
from urllib.error import HTTPError
from urllib.request import Request, urlopen
import zipfile

from cmnd_linux.protocol import WIXPClient, clone_request, discovery_request
from cmnd_linux.simulator import TVState, handler_factory


def room_zip(serial: str, room_id: str) -> bytes:
    stream = BytesIO()
    xml = (f'<RoomSpecificSettings><TV><SerialNumber>{serial}</SerialNumber>'
           '<Item><Name>Professional Settings.Advanced.Identification Settings.RoomID</Name>'
           f'<Value>{room_id}</Value></Item></TV></RoomSpecificSettings>')
    with zipfile.ZipFile(stream, "w", zipfile.ZIP_DEFLATED) as archive:
        archive.writestr("RoomSpecificSettings.xml", xml)
        archive.writestr("TVSettings.xml", "<TVSettings/>")
    return stream.getvalue()


class OriginHandler(BaseHTTPRequestHandler):
    payload = b""
    callback = None
    callback_event = threading.Event()
    fetch_count = 0

    def log_message(self, _format, *args):
        return

    def do_GET(self):
        if self.path.split("?", 1)[0] == "/SmartInstall/Profile/Clone/device/RoomSpecificSettings.zip":
            type(self).fetch_count += 1
            self.send_response(200)
            self.send_header("Content-Type", "application/zip")
            self.send_header("Content-Length", str(len(type(self).payload)))
            self.end_headers()
            self.wfile.write(type(self).payload)
        else:
            self.send_error(404)

    def do_POST(self):
        if self.path != "/SmartInstall/webservices.jsp":
            self.send_error(404)
            return
        type(self).callback = json.loads(self.rfile.read(int(self.headers["Content-Length"])))
        type(self).callback_event.set()
        self.send_response(200)
        self.send_header("Content-Length", "2")
        self.end_headers()
        self.wfile.write(b"{}")


class NativeSimulatorTests(unittest.TestCase):
    def setUp(self):
        OriginHandler.payload = room_zip("SIMULATOR00000001", "00704")
        OriginHandler.callback = None
        OriginHandler.callback_event = threading.Event()
        OriginHandler.fetch_count = 0
        self.origin = ThreadingHTTPServer(("127.0.0.1", 0), OriginHandler)
        self.origin_thread = threading.Thread(target=self.origin.serve_forever, daemon=True)
        self.origin_thread.start()
        self.base_url = f"http://127.0.0.1:{self.origin.server_port}"
        self.state = TVState("SIMULATOR00000001", "43HFL6114U/27", callback_base_url=self.base_url)
        self.tv = ThreadingHTTPServer(("127.0.0.1", 0), handler_factory(self.state, apply_delay=0))
        self.tv_thread = threading.Thread(target=self.tv.serve_forever, daemon=True)
        self.tv_thread.start()

    def tearDown(self):
        for server, thread in ((self.tv, self.tv_thread), (self.origin, self.origin_thread)):
            server.shutdown(); server.server_close(); thread.join(timeout=2)

    def send(self, message):
        return WIXPClient(2).send("127.0.0.1", message, self.tv.server_port)

    def test_power_request_change_and_reboot(self):
        request = lambda cookie, kind, details=None: {
            "Svc": "WebListeningServices", "SvcVer": "1.0", "Cookie": cookie,
            "CmdType": kind, "Fun": "PowerService", **({"CommandDetails": details} if details else {})}
        self.assertEqual(self.send(request(1, "Request"))["CommandDetails"]
                         ["PowerServiceParameters"]["CurrentPowerState"], "On")
        changed = self.send(request(2, "Change", {"ToPowerState": "Standby"}))
        self.assertEqual(changed["CommandDetails"]["PowerServiceParameters"]["CurrentPowerState"], "Standby")
        rebooted = self.send(request(3, "Change", {"PowerAction": "Reboot"}))
        self.assertEqual(rebooted["CommandDetails"]["PowerServiceParameters"]["CurrentPowerState"], "On")
        self.assertEqual(self.state.reboot_count, 1)

    def test_native_remote_control_prerequisites_use_exact_schemas(self):
        services = {name: "On" for name in self.state.enabled_services}
        enabler = self.send({
            "Svc": "WebListeningServices", "SvcVer": "4.0", "Cookie": 10,
            "CmdType": "Change", "Fun": "EnablerService",
            "CommandDetails": {"WebListeningServicesEnablerParameters": services}})
        self.assertEqual(enabler["CommandDetails"]["WebListeningServicesEnablerParameters"], services)
        applications = self.send({
            "Svc": "WebListeningServices", "SvcVer": "4.0", "Cookie": 11,
            "CmdType": "Request", "Fun": "ApplicationControl",
            "CommandDetails": {"RequestListOfAvailableApplications": {"Filter": ["NonNative"]}}})
        names = [item["ApplicationName"] for item in
                 applications["CommandDetails"]["CurrentAvailableApplicationList"]]
        self.assertEqual(names, list(self.state.available_applications))
        active = self.send({
            "Svc": "WebListeningServices", "SvcVer": "4.0", "Cookie": 12,
            "CmdType": "Request", "Fun": "ApplicationControl"})
        self.assertEqual(active["CommandDetails"]["ActiveApplications"][0]["ApplicationName"], "TVChannels")

    def test_real_room_zip_updates_readback_records_evidence_and_callbacks(self):
        version = "07/08/2026:15:08"
        path = "/SmartInstall/Profile/Clone/device/RoomSpecificSettings.zip"
        response = self.send(clone_request(self.state.identity, "RoomSpecificSettings", version,
                                           self.base_url + path, correlation=4))
        params = response["CommandDetails"]["IPCloneParameters"]
        self.assertEqual(params["CurrentUpgradeStatus"], "UpgradeInProgress")
        self.assertEqual(params["CloneSessionStatus"]["CloneItemStatus"][0]
                         ["CloneItemDetails"]["CloneItemVersionNo"], version)
        self.assertTrue(OriginHandler.callback_event.wait(2))
        callback = OriginHandler.callback["CommandDetails"]["IPCloneParameters"]
        self.assertEqual(OriginHandler.callback["CmdType"], "Change")
        self.assertEqual(OriginHandler.callback["Cookie"], 293)
        self.assertEqual(callback["CurrentUpgradeStatus"], "NotInUpgradeMode")
        self.assertEqual(callback["CloneSessionStatus"]["SessionStatus"], "Successful")
        discovery = self.send(discovery_request(correlation=5))
        self.assertEqual(discovery["CommandDetails"]["TVDiscoveryParameters"]["TVRoomID"], "00704")
        evidence = self.state.clone_details["RoomSpecificSettings"]
        self.assertEqual(evidence["bytes"], len(OriginHandler.payload))
        self.assertEqual(evidence["sha256"], hashlib.sha256(OriginHandler.payload).hexdigest())
        self.assertEqual(evidence["path"], path)
        self.assertGreater(evidence["uncompressed_bytes"], 0)

    def test_pk_prefix_alone_is_not_accepted_as_a_zip(self):
        OriginHandler.payload = b"PKthis is not a real ZIP"
        url = self.base_url + "/SmartInstall/Profile/Clone/device/RoomSpecificSettings.zip"
        self.send(clone_request(self.state.identity, "RoomSpecificSettings", "v1", url, correlation=6))
        self.assertTrue(OriginHandler.callback_event.wait(2))
        self.assertEqual(self.state.clone_status["RoomSpecificSettings"], "Failed")
        self.assertEqual(self.state.room_id, "0001")

    def test_mismatched_origin_is_rejected_before_fetch(self):
        message = clone_request(self.state.identity, "RoomSpecificSettings", "v1",
                                "http://127.0.0.1:9/SmartInstall/Profile/Clone/x.zip", correlation=7)
        body = json.dumps(message).encode()
        request = Request(f"http://127.0.0.1:{self.tv.server_port}/WIXP", data=body,
                          headers={"Content-Type": "application/json"}, method="POST")
        with self.assertRaises(HTTPError) as raised:
            urlopen(request, timeout=2)
        self.assertEqual(raised.exception.code, 400)
        time.sleep(0.05)
        self.assertEqual(OriginHandler.fetch_count, 0)
        self.assertNotIn("RoomSpecificSettings", self.state.clone_status)

    def test_encoded_traversal_and_query_are_rejected(self):
        for suffix in ("../outside.zip", "%2e%2e/outside.zip", "file.zip?redirect=1"):
            with self.subTest(suffix=suffix):
                message = clone_request(self.state.identity, "RoomSpecificSettings", "v1",
                                        self.base_url + "/SmartInstall/Profile/Clone/" + suffix,
                                        correlation=9)
                body = json.dumps(message).encode()
                request = Request(f"http://127.0.0.1:{self.tv.server_port}/WIXP", data=body,
                                  headers={"Content-Type": "application/json"}, method="POST")
                with self.assertRaises(HTTPError) as raised:
                    urlopen(request, timeout=2)
                self.assertEqual(raised.exception.code, 400)

    def test_serial_mismatch_fails_without_changing_room(self):
        OriginHandler.payload = room_zip("ANOTHER-SERIAL", "9999")
        url = self.base_url + "/SmartInstall/Profile/Clone/device/RoomSpecificSettings.zip"
        self.send(clone_request(self.state.identity, "RoomSpecificSettings", "v2", url, correlation=8))
        self.assertTrue(OriginHandler.callback_event.wait(2))
        self.assertEqual(self.state.clone_status["RoomSpecificSettings"], "Failed")
        self.assertEqual(self.state.room_id, "0001")


if __name__ == "__main__":
    unittest.main()
