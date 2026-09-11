from __future__ import annotations

from dataclasses import dataclass
import json
import random
from ipaddress import ip_address
from urllib.request import Request, build_opener, ProxyHandler, HTTPRedirectHandler


class NoRedirects(HTTPRedirectHandler):
    def redirect_request(self, req, fp, code, msg, headers, newurl):
        raise ProtocolError("TV redirected the request; refusing a different target")


class ProtocolError(RuntimeError):
    pass


def cookie() -> int:
    # Match the vendor JAPITUtils generator: lower bound inclusive, upper exclusive.
    # A physical TV rejected our former 31-bit values with Cookie=-1/Fun=Error.
    return random.SystemRandom().randrange(0, 99_999)


def discovery_request(*, correlation: int | None = None) -> dict:
    return {"Svc": "WebListeningServices", "SvcVer": "1.0",
            "Cookie": correlation if correlation is not None else cookie(),
            "CmdType": "Request", "Fun": "TVDiscoveryService"}


def power_request(state: str, *, correlation: int | None = None) -> dict:
    if state not in {"On", "Standby"}:
        raise ValueError("observed power states are On and Standby")
    return {
        "Svc": "WebListeningServices", "SvcVer": "1.0",
        "Cookie": correlation if correlation is not None else cookie(),
        "CmdType": "Change", "Fun": "PowerService",
        "CommandDetails": {"ToPowerState": state},
    }


EXPORT_ITEMS = frozenset({
    'TVSettings', 'TVChannelList', 'WelcomeLogo', 'SmartInfoImages', 'SmartInfoPages',
    'AndroidApps', 'RoomSpecificSettings', 'DataDump', 'CustomDashboardFallback',
    'Script', 'MediaChannels', 'WeatherForecast', 'HTVCfg.xml', 'Banner', 'PMS',
    'AndroidAppsData', 'ProfessionalApps', 'ProfessionalAppsData', 'Schedules', 'MyChoice', 'Vsecure',
})


def clone_info_request(service_version: str = '3.0', *, correlation: int | None = None) -> dict:
    if service_version not in {'1.0', '3.0', '5.0'}:
        raise ValueError('clone service version must match the inspected vendor mapping: 1.0, 3.0, or 5.0')
    return {'Svc': 'WebListeningServices', 'SvcVer': service_version,
            'Cookie': correlation if correlation is not None else cookie(),
            'CmdType': 'Request', 'Fun': 'IPCloneService'}


def clone_export_request(identity: str, items: list[str], upload_url: str,
                         service_version: str = '3.0', *, correlation: int | None = None) -> dict:
    """Vendor TV-to-server operation, deliberately separate from clone-to-TV."""
    from urllib.parse import urlsplit
    import re
    parsed = urlsplit(upload_url)
    if (not re.fullmatch(r'[A-Za-z0-9:._-]{8,128}', identity)
            or not items or len(set(items)) != len(items) or not set(items) <= EXPORT_ITEMS
            or parsed.scheme != 'http' or not parsed.hostname
            or parsed.username is not None or parsed.password is not None or parsed.query or parsed.fragment
            or not re.fullmatch(r'/SmartInstall/CloneToServer/[a-f0-9]{48}', parsed.path)):
        raise ValueError('invalid identity, export items, or private receive URL')
    if parsed.port is not None and not 1 <= parsed.port <= 65535:
        raise ValueError('invalid receiver port')
    address = ip_address(parsed.hostname)
    if address.is_unspecified or address.is_multicast:
        raise ValueError('receiver must have an explicit unicast address')
    message = clone_info_request(service_version, correlation=correlation)
    message['CmdType'] = 'Change'  # Starts export, not a TV configuration change.
    message['CommandDetails'] = {
        'WebListeningServiceParameters': {'TVUniqueID': identity},
        'CloneToServerParameters': {'CloneToServerDetails': [
            {'CloneItemName': item, 'URL': upload_url} for item in items]},
    }
    return message


def clone_request(identity: str, item: str, version: str, url: str, *, correlation: int | None = None) -> dict:
    if item not in {"RoomSpecificSettings", "TVSettings", "TVChannelList", "AndroidApps", "MyChoice", "HTVCfg.xml", "ProfessionalAppsData"}:
        raise ValueError("clone item is not in the capture-qualified set")
    return {
        "Svc": "WebListeningServices", "SvcVer": "3.0",
        "Cookie": correlation if correlation is not None else cookie(),
        "CmdType": "Change", "Fun": "IPCloneService",
        "CommandDetails": {
            "WebListeningServiceParameters": {"TVUniqueID": identity},
            "IPCloneParameters": {"CloneItemDownloadDetails": [{
                "CloneItemDetails": {"CloneItemName": item, "CloneItemVersionNo": version},
                "URL": url,
            }]},
        },
    }


@dataclass
class WIXPClient:
    timeout: float = 5.0

    def send(self, target: str, message: dict, port: int = 9079) -> dict:
        body = json.dumps(message, separators=(",", ":")).encode("utf-8")
        addr = ip_address(target)
        host = f"[{addr}]" if addr.version == 6 else str(addr)
        request = Request(
            f"http://{host}:{port}/WIXP", data=body, method="POST",
            headers={"Content-Type": "application/x-www-form-urlencoded", "Cache-Control": "no-cache"},
        )
        try:
            with build_opener(ProxyHandler({}), NoRedirects()).open(request, timeout=self.timeout) as response:
                content_type = response.headers.get_content_type()
                if content_type != "application/json":
                    raise ProtocolError(f"unexpected response content type: {content_type}")
                body = response.read(1024 * 1024 + 1)
                if len(body) > 1024 * 1024:
                    raise ProtocolError("response exceeds 1 MiB limit")
                parsed = json.loads(body.decode("utf-8"))
        except Exception as exc:
            raise ProtocolError(f"WIXP request failed: {exc}") from exc
        if not isinstance(parsed, dict):
            raise ProtocolError("TV response must be a JSON object")
        if parsed.get("Cookie") != message.get("Cookie"):
            raise ProtocolError("response correlation cookie does not match request")
        if parsed.get("Fun") != message.get("Fun") or parsed.get("CmdType") != "Response":
            raise ProtocolError("response function/type does not match request")
        return parsed


def clone_session_state(response: dict, item: str) -> str:
    params = response.get("CommandDetails", {}).get("IPCloneParameters", {})
    session = params.get("CloneSessionStatus", {})
    for status in session.get("CloneItemStatus", []):
        if status.get("CloneItemDetails", {}).get("CloneItemName") == item:
            return str(status.get("CloneStatus", "Unknown"))
    return str(session.get("SessionStatus", "Unknown"))
