from __future__ import annotations

from dataclasses import dataclass
from hashlib import sha256
from http.server import BaseHTTPRequestHandler, ThreadingHTTPServer
from io import BytesIO
from ipaddress import ip_address
import json
from pathlib import PurePosixPath
import threading
import time
from urllib.parse import unquote, urlsplit, urlunsplit
from urllib.request import HTTPRedirectHandler, ProxyHandler, Request, build_opener
import xml.etree.ElementTree as ET
import zipfile


MAX_COMMAND_BYTES = 1024 * 1024
MAX_CLONE_BYTES = 16 * 1024 * 1024
MAX_UNCOMPRESSED_BYTES = 32 * 1024 * 1024
MAX_ZIP_ENTRIES = 256
CLONE_PATH_PREFIX = "/SmartInstall/Profile/Clone/"
CALLBACK_PATH = "/SmartInstall/webservices.jsp"
ROOM_ITEM_NAME = "Professional Settings.Advanced.Identification Settings.RoomID"


class _NoRedirects(HTTPRedirectHandler):
    def redirect_request(self, req, fp, code, msg, headers, newurl):
        raise ValueError("redirects are not allowed")


def _origin(parts) -> tuple[str, str, int]:
    if parts.scheme not in {"http", "https"} or not parts.hostname:
        raise ValueError("URL must use HTTP or HTTPS and include a host")
    if parts.username is not None or parts.password is not None:
        raise ValueError("URL credentials are not allowed")
    try:
        port = parts.port
    except ValueError as exc:
        raise ValueError("URL has an invalid port") from exc
    scheme = parts.scheme.lower()
    return scheme, parts.hostname.lower(), port or (443 if scheme == "https" else 80)


def _configured_origin(callback_base_url: str | None) -> tuple[str, str, int] | None:
    if callback_base_url is None:
        return None
    parts = urlsplit(callback_base_url)
    origin = _origin(parts)
    if parts.query or parts.fragment or parts.path not in {"", "/"}:
        raise ValueError("callback base URL must contain only an origin")
    return origin


def _allowed_clone_url(url: str, configured_origin: tuple[str, str, int] | None) -> tuple[str, str]:
    parts = urlsplit(url)
    origin = _origin(parts)
    if any(ord(character) < 32 for character in url) or "\\" in url:
        raise ValueError("clone URL contains unsafe characters")
    if parts.query or parts.fragment:
        raise ValueError("clone URL queries and fragments are not allowed")
    decoded_path = unquote(parts.path)
    path_parts = PurePosixPath(decoded_path).parts
    if decoded_path != parts.path or ".." in path_parts or "." in path_parts:
        raise ValueError("clone URL path must be canonical and unencoded")
    if not decoded_path.endswith(".zip"):
        raise ValueError("clone URL must identify a ZIP package")
    if configured_origin is not None:
        if origin != configured_origin or not decoded_path.startswith(CLONE_PATH_PREFIX):
            raise ValueError("clone URL is outside the configured CMND package origin/path")
    else:
        # Compatibility mode is deliberately limited to literal loopback addresses.
        try:
            if not ip_address(parts.hostname).is_loopback:
                raise ValueError("clone URL requires a configured CMND origin")
        except (TypeError, ValueError) as exc:
            raise ValueError("clone URL requires a configured CMND origin") from exc
    return urlunsplit(parts), decoded_path


def _local_name(tag: str) -> str:
    return tag.rsplit("}", 1)[-1]


@dataclass(frozen=True)
class _CloneItem:
    name: str
    version: str
    url: str
    path: str


class TVState:
    def __init__(
        self,
        identity: str = "SIMULATOR00000001",
        model: str = "SIMULATOR",
        serial: str | None = None,
        callback_base_url: str | None = None,
    ):
        self.identity = identity
        self.model = model
        self.serial = serial or identity
        self.callback_base_url = callback_base_url.rstrip("/") if callback_base_url else None
        self.callback_origin = _configured_origin(callback_base_url)
        self.requests: list[dict[str, object]] = []
        self.power = "On"
        self.room_id = "0001"
        self.reboot_count = 0
        self.enabled_services = {
            "ApplicationControlService": "Off", "AudioService": "Off",
            "ChannelSelectionService": "Off", "PowerService": "Off", "SourceService": "Off",
            "MyChoiceService": "Off", "ProfessionalSettingsService": "Off"}
        self.available_applications = ("DefaultDashboard", "Googlecast", "TVChannels", "Weather")
        self.active_application = "TVChannels"
        self.clone_status: dict[str, str] = {}
        self.clone_details: dict[str, dict[str, object]] = {}
        self.callbacks: list[dict[str, object]] = []
        self.lock = threading.Lock()


def _validate_zip(payload: bytes) -> tuple[zipfile.ZipFile, int]:
    if not payload or len(payload) > MAX_CLONE_BYTES:
        raise ValueError("clone package is empty or oversized")
    stream = BytesIO(payload)
    if not zipfile.is_zipfile(stream):
        raise ValueError("clone package is not a ZIP archive")
    stream.seek(0)
    archive = zipfile.ZipFile(stream)
    infos = archive.infolist()
    if not infos or len(infos) > MAX_ZIP_ENTRIES:
        archive.close()
        raise ValueError("clone ZIP has an invalid entry count")
    uncompressed = 0
    for info in infos:
        path = PurePosixPath(info.filename.replace("\\", "/"))
        if path.is_absolute() or ".." in path.parts or info.flag_bits & 1:
            archive.close()
            raise ValueError("clone ZIP contains an unsafe entry")
        uncompressed += info.file_size
        if uncompressed > MAX_UNCOMPRESSED_BYTES:
            archive.close()
            raise ValueError("clone ZIP expands beyond the safety limit")
    if archive.testzip() is not None:
        archive.close()
        raise ValueError("clone ZIP failed its CRC check")
    return archive, uncompressed


def _room_values(archive: zipfile.ZipFile) -> tuple[str, str]:
    allowed_paths = {"RoomSpecificSettings.xml", "RoomSpecificSettings/RoomSpecificSettings.xml"}
    candidates = [name for name in archive.namelist() if name in allowed_paths]
    if not candidates:
        raise ValueError("RoomSpecificSettings.xml is missing")
    if len(candidates) != 1:
        raise ValueError("clone ZIP has ambiguous RoomSpecificSettings.xml entries")
    try:
        xml = archive.read(candidates[0])
    except KeyError as exc:
        raise ValueError("RoomSpecificSettings.xml is missing") from exc
    if b"<!DOCTYPE" in xml.upper() or b"<!ENTITY" in xml.upper():
        raise ValueError("RoomSpecificSettings.xml declarations are not allowed")
    try:
        root = ET.fromstring(xml)
    except ET.ParseError as exc:
        raise ValueError("RoomSpecificSettings.xml is malformed") from exc
    serial = next((str(node.text or "").strip() for node in root.iter()
                   if _local_name(node.tag) == "SerialNumber"), "")
    room_id = ""
    for node in root.iter():
        if _local_name(node.tag) not in {"item", "Item"}:
            continue
        children = {_local_name(child.tag): str(child.text or "").strip() for child in node}
        name = node.attrib.get("Name", children.get("Name", ""))
        if name == ROOM_ITEM_NAME:
            room_id = str(node.attrib.get("Value", children.get("Value", "")))
            break
    if not serial or not room_id:
        raise ValueError("RoomSpecificSettings.xml lacks serial or RoomID")
    if not room_id.isascii() or not room_id.isdigit() or len(room_id) > 16:
        raise ValueError("RoomSpecificSettings.xml has an invalid RoomID")
    return serial, room_id


def handler_factory(state: TVState, apply_delay: float = 0.05):
    opener = build_opener(ProxyHandler({}), _NoRedirects())

    def status(item: _CloneItem, result: str) -> dict:
        return {"CloneStatus": result, "CloneItemDetails": {
            "CloneItemName": item.name, "CloneItemVersionNo": item.version}}

    def send_callback(statuses: list[dict], session_status: str) -> None:
        if state.callback_base_url is None:
            return
        message = {
            "Svc": "WebServices", "SvcVer": "1.0", "Cookie": 293,
            "CmdType": "Change", "Fun": "IPCloneService",
            "CommandDetails": {
                "WebServiceParameters": {"TVUniqueID": state.identity},
                "IPCloneParameters": {
                    "CurrentUpgradeStatus": "NotInUpgradeMode",
                    "CloneSessionStatus": {"SessionStatus": session_status, "CloneItemStatus": statuses},
                },
            },
        }
        body = json.dumps(message, separators=(",", ":")).encode()
        try:
            request = Request(state.callback_base_url + CALLBACK_PATH, data=body, method="POST",
                              headers={"Content-Type": "application/json"})
            with opener.open(request, timeout=5) as response:
                response.read(MAX_COMMAND_BYTES + 1)
            record: dict[str, object] = {"status": "sent", "path": CALLBACK_PATH}
        except Exception as exc:
            record = {"status": "failed", "path": CALLBACK_PATH, "error": str(exc)}
        with state.lock:
            state.callbacks.append(record)
            del state.callbacks[:-20]

    def fetch_all(items: list[_CloneItem]) -> None:
        time.sleep(apply_delay)
        statuses: list[dict] = []
        for item in items:
            result = "Successful"
            record: dict[str, object] = {"status": result, "version": item.version, "path": item.path}
            try:
                request = Request(item.url, method="GET", headers={"Accept": "application/zip"})
                with opener.open(request, timeout=5) as response:
                    payload = response.read(MAX_CLONE_BYTES + 1)
                record.update({"sha256": sha256(payload).hexdigest(), "bytes": len(payload)})
                archive, uncompressed = _validate_zip(payload)
                try:
                    if item.name == "RoomSpecificSettings":
                        serial, room_id = _room_values(archive)
                        if serial.casefold() != state.serial.casefold():
                            raise ValueError("RoomSpecificSettings serial does not match this TV")
                        with state.lock:
                            state.room_id = room_id
                        record["room_id"] = room_id
                finally:
                    archive.close()
                record["uncompressed_bytes"] = uncompressed
            except Exception as exc:
                result = "Failed"
                record.update({"status": result, "error": str(exc)})
            with state.lock:
                state.clone_status[item.name] = result
                state.clone_details[item.name] = record
            statuses.append(status(item, result))
        send_callback(statuses, "Successful" if all(
            item_status["CloneStatus"] == "Successful" for item_status in statuses) else "Failed")

    class Handler(BaseHTTPRequestHandler):
        server_version = "CMND-TV-Simulator/0.2"

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
            if urlsplit(self.path).path != "/state":
                self._json(404, {"error": "not found"})
                return
            with state.lock:
                self._json(200, {
                    "identity": state.identity, "model": state.model, "serial": state.serial,
                    "room_id": state.room_id, "power": state.power, "reboot_count": state.reboot_count,
                    "clones": state.clone_status, "clone_details": state.clone_details,
                    "callbacks": state.callbacks[-20:], "requests": state.requests[-50:],
                })

        def do_POST(self):
            if urlsplit(self.path).path != "/WIXP":
                self._json(404, {"error": "not found"})
                return
            try:
                length = int(self.headers.get("Content-Length", "0"))
                if length <= 0 or length > MAX_COMMAND_BYTES:
                    raise ValueError("invalid content length")
                request = json.loads(self.rfile.read(length))
                if not isinstance(request, dict):
                    raise ValueError("command must be a JSON object")
                fun = request["Fun"]
                command_type = request.get("CmdType")
                with state.lock:
                    state.requests.append({"function": fun, "type": command_type})
                    del state.requests[:-50]
                details = request.get("CommandDetails") or {}
                response_details = {"WebListeningServiceParameters": {"TVUniqueID": state.identity}}
                if fun == "TVDiscoveryService":
                    if command_type != "Request":
                        raise ValueError("discovery requires CmdType Request")
                    response_details["TVDiscoveryParameters"] = {
                        "PowerStatus": state.power, "TVIPAddress": self.connection.getsockname()[0],
                        "TVMACAddress": "02:00:00:00:00:01", "TVModelNumber": state.model,
                        "TVRoomID": state.room_id, "TVSerialNumber": state.serial, "VSecureTVID": "NO"}
                elif fun == "PowerService":
                    if command_type == "Request":
                        pass
                    elif command_type == "Change":
                        if details.get("PowerAction") == "Reboot":
                            with state.lock:
                                state.reboot_count += 1
                                state.power = "On"
                        else:
                            power = details.get("ToPowerState")
                            if power not in {"On", "Standby"}:
                                raise ValueError("unsupported power state")
                            with state.lock:
                                state.power = power
                    else:
                        raise ValueError("power requires CmdType Request or Change")
                    with state.lock:
                        current_power = state.power
                    response_details["PowerServiceParameters"] = {
                        "CurrentPowerState": current_power, "Error": "No", "Transition": "No"}
                elif fun == "EnablerService":
                    if command_type != "Change":
                        raise ValueError("enabler requires CmdType Change")
                    parameters = details.get("WebListeningServicesEnablerParameters")
                    if not isinstance(parameters, dict) or not parameters:
                        raise ValueError("enabler parameters are missing")
                    unknown = set(parameters) - set(state.enabled_services)
                    if unknown or any(value not in {"On", "Off"} for value in parameters.values()):
                        raise ValueError("enabler contains an unsupported service or state")
                    with state.lock:
                        state.enabled_services.update(parameters)
                        enabled = dict(state.enabled_services)
                    response_details["WebListeningServicesEnablerParameters"] = enabled
                elif fun == "ApplicationControl":
                    if command_type == "Request":
                        if "RequestListOfAvailableApplications" in details:
                            request_list = details["RequestListOfAvailableApplications"]
                            if not isinstance(request_list, dict) or not isinstance(request_list.get("Filter"), list):
                                raise ValueError("application list request has an invalid Filter")
                            response_details["CurrentAvailableApplicationList"] = [
                                {"ApplicationName": name} for name in state.available_applications]
                        elif details:
                            raise ValueError("unsupported application request details")
                        else:
                            response_details["ActiveApplications"] = [
                                {"ApplicationName": state.active_application}]
                    elif command_type == "Change":
                        app = details.get("ApplicationDetails")
                        name = app.get("ApplicationName") if isinstance(app, dict) else None
                        if details.get("ApplicationState") != "Activate" or name not in state.available_applications:
                            raise ValueError("unsupported application activation")
                        with state.lock:
                            state.active_application = name
                        response_details["ActiveApplications"] = [{"ApplicationName": name}]
                    else:
                        raise ValueError("application control requires CmdType Request or Change")
                elif fun == "IPCloneService":
                    if command_type != "Change":
                        raise ValueError("IP clone requires CmdType Change")
                    raw_items = details["IPCloneParameters"]["CloneItemDownloadDetails"]
                    if not isinstance(raw_items, list) or not raw_items:
                        raise ValueError("clone command has no download items")
                    items: list[_CloneItem] = []
                    for raw_item in raw_items:
                        item_details = raw_item["CloneItemDetails"]
                        fetch_url, path = _allowed_clone_url(str(raw_item["URL"]), state.callback_origin)
                        items.append(_CloneItem(str(item_details["CloneItemName"]),
                                                str(item_details.get("CloneItemVersionNo", "")),
                                                fetch_url, path))
                    with state.lock:
                        for item in items:
                            state.clone_status[item.name] = "InProgress"
                            state.clone_details[item.name] = {
                                "status": "InProgress", "version": item.version, "path": item.path}
                    threading.Thread(target=fetch_all, args=(items,), daemon=True).start()
                    response_details["IPCloneParameters"] = {
                        "CurrentUpgradeStatus": "UpgradeInProgress",
                        "CloneSessionStatus": {"SessionStatus": "InProgress",
                                               "CloneItemStatus": [status(item, "InProgress") for item in items]}}
                else:
                    raise ValueError("unsupported function")
                self._json(200, {
                    "Svc": "WebListeningServices", "SvcVer": request.get("SvcVer", "1.0"),
                    "Cookie": request["Cookie"], "CmdType": "Response", "Fun": fun,
                    "CommandDetails": response_details})
            except (KeyError, TypeError, ValueError, json.JSONDecodeError) as exc:
                self._json(400, {"error": str(exc)})

    return Handler


def serve(bind: str, port: int, identity: str, model: str = "SIMULATOR", serial: str | None = None,
          callback_base_url: str | None = None) -> None:
    server = ThreadingHTTPServer((bind, port), handler_factory(
        TVState(identity, model, serial, callback_base_url)))
    server.serve_forever()
