from __future__ import annotations

from http.server import BaseHTTPRequestHandler, ThreadingHTTPServer
import json
import threading


class CallbackState:
    def __init__(self):
        self._lock = threading.Lock()
        self._latest: dict[str, dict] = {}

    def record(self, identity: str, message: dict) -> None:
        with self._lock:
            self._latest[identity] = message

    def latest(self, identity: str) -> dict | None:
        with self._lock:
            value = self._latest.get(identity)
            return json.loads(json.dumps(value)) if value else None


def polling_command(identity: str, fun: str, correlation: int = 293) -> dict:
    details = {"WebServiceParameters": {
        "PollingFrequency": 10, "PollingFrequencyGreen": 60, "TVUniqueID": identity}}
    if fun == "IPCloneService":
        details.update({"IPCloneParameters": {}, "CloneToServerParameters": {}})
    return {"Svc": "WebServices", "SvcVer": "1.0", "Cookie": correlation,
            "CmdType": "Change", "Fun": fun, "CommandDetails": details}


def handler_factory(state: CallbackState):
    class CallbackHandler(BaseHTTPRequestHandler):
        server_version = "CMND-Callback-Qualification/0.1"

        def log_message(self, _format, *args):
            return

        def do_POST(self):
            if self.path != "/SmartInstall/webservices.jsp":
                self.send_error(404)
                return
            try:
                length = int(self.headers.get("Content-Length", "0"))
                if length <= 0 or length > 1024 * 1024:
                    raise ValueError("callback body must be 1..1048576 bytes")
                message = json.loads(self.rfile.read(length))
                if message.get("Svc") != "WebServices" or message.get("CmdType") != "Response":
                    raise ValueError("unexpected callback service/type")
                details = message.get("CommandDetails", {})
                web = details.get("WebServiceParameters", {})
                identity = str(web.get("TVUniqueID", ""))
                if not identity:
                    raise ValueError("callback is missing TVUniqueID")
                fun = str(message.get("Fun", ""))
                if not fun:
                    raise ValueError("callback is missing Fun")
                state.record(identity, message)
                body = json.dumps(polling_command(identity, fun), separators=(",", ":")).encode()
                self.send_response(200)
                # Capture frame 3162 uses text/html;charset=UTF-8 while carrying JSON.
                self.send_header("Content-Type", "text/html;charset=UTF-8")
                self.send_header("Cache-Control", "no-cache, no-store, max-age=0")
                self.send_header("Content-Length", str(len(body)))
                self.end_headers(); self.wfile.write(body)
            except (ValueError, json.JSONDecodeError) as exc:
                body = json.dumps({"error": str(exc)}).encode()
                self.send_response(400); self.send_header("Content-Type", "application/json")
                self.send_header("Content-Length", str(len(body))); self.end_headers(); self.wfile.write(body)

    return CallbackHandler


def serve_callbacks(bind: str, port: int) -> None:
    ThreadingHTTPServer((bind, port), handler_factory(CallbackState())).serve_forever()
