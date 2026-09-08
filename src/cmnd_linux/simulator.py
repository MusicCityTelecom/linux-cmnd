from __future__ import annotations

from http.server import BaseHTTPRequestHandler, ThreadingHTTPServer
import json
import threading
import time
from urllib.request import urlopen


class TVState:
    def __init__(self, identity: str = "SIMULATOR00000001"):
        self.identity = identity
        self.power = "On"
        self.clone_status: dict[str, str] = {}
        self.lock = threading.Lock()


def handler_factory(state: TVState, apply_delay: float = 0.05):
    class Handler(BaseHTTPRequestHandler):
        server_version = "CMND-TV-Simulator/0.1"

        def log_message(self, _format, *args):
            return

        def _json(self, code: int, payload: dict):
            body = json.dumps(payload, separators=(",", ":")).encode()
            self.send_response(code)
            self.send_header("Content-Type", "application/json; charset=UTF-8")
            self.send_header("Content-Length", str(len(body)))
            self.end_headers()
            self.wfile.write(body)

        def do_GET(self):
            if self.path == "/state":
                with state.lock:
                    self._json(200, {"identity": state.identity, "power": state.power, "clones": state.clone_status})
            else:
                self._json(404, {"error": "not found"})

        def do_POST(self):
            if self.path != "/WIXP":
                self._json(404, {"error": "not found"})
                return
            try:
                length = int(self.headers.get("Content-Length", "0"))
                if length <= 0 or length > 1024 * 1024:
                    raise ValueError("invalid content length")
                request = json.loads(self.rfile.read(length))
                fun = request["Fun"]
                details = request.get("CommandDetails", {})
                response_details = {"WebListeningServiceParameters": {"TVUniqueID": state.identity}}
                if fun == "TVDiscoveryService":
                    if request.get("CmdType") != "Request":
                        raise ValueError("discovery requires CmdType Request")
                    response_details["TVDiscoveryParameters"] = {
                        "PowerStatus": state.power, "TVIPAddress": self.client_address[0],
                        "TVMACAddress": "02:00:00:00:00:01", "TVModelNumber": "SIMULATOR",
                        "TVRoomID": "0001", "TVSerialNumber": state.identity, "VSecureTVID": ""}
                elif fun == "PowerService":
                    power = details["ToPowerState"]
                    if power not in {"On", "Standby"}:
                        raise ValueError("unsupported power state")
                    with state.lock:
                        state.power = power
                    response_details["PowerServiceParameters"] = {
                        "CurrentPowerState": power, "Error": "No", "Transition": "No"}
                elif fun == "IPCloneService":
                    item = details["IPCloneParameters"]["CloneItemDownloadDetails"][0]
                    name, url = item["CloneItemDetails"]["CloneItemName"], item["URL"]
                    with state.lock:
                        state.clone_status[name] = "Accepted"
                    threading.Thread(target=self._fetch, args=(name, url), daemon=True).start()
                    response_details["IPCloneParameters"] = {"CloneSessionStatus": {
                        "SessionStatus": "InProgress", "CloneItemStatus": [{
                            "CloneItemDetails": {"CloneItemName": name}, "CloneStatus": "Accepted"}]}}
                else:
                    raise ValueError("unsupported function")
                self._json(200, {
                    "Svc": "WebListeningServices", "SvcVer": "4.20",
                    "Cookie": request["Cookie"], "CmdType": "Response", "Fun": fun,
                    "CommandDetails": response_details,
                })
            except (KeyError, ValueError, json.JSONDecodeError) as exc:
                self._json(400, {"error": str(exc)})

        def _fetch(self, name: str, url: str):
            try:
                time.sleep(apply_delay)
                with urlopen(url, timeout=2) as response:
                    payload = response.read(16 * 1024 * 1024 + 1)
                    if len(payload) > 16 * 1024 * 1024 or not payload.startswith(b"PK"):
                        raise ValueError("malformed or oversized clone package")
                result = "Successful"
            except Exception:
                result = "Failed"
            with state.lock:
                state.clone_status[name] = result

    return Handler


def serve(bind: str, port: int, identity: str) -> None:
    server = ThreadingHTTPServer((bind, port), handler_factory(TVState(identity)))
    server.serve_forever()
