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
    return random.SystemRandom().randint(1, 2_147_483_647)


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
