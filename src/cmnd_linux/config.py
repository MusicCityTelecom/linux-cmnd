from __future__ import annotations

from dataclasses import dataclass
from ipaddress import ip_address, ip_network
from pathlib import Path
from urllib.parse import urlparse, unquote
import math
import re
try:
    import tomllib
except ModuleNotFoundError:  # Python 3.9/3.10 development hosts
    import tomli as tomllib


class ConfigError(ValueError):
    pass


WRITE_OPERATIONS = {"power", "room-id", "clone"}


@dataclass(frozen=True)
class AllowedTV:
    ip: str
    identity: str
    operations: frozenset[str]


@dataclass(frozen=True)
class Config:
    mode: str
    bind: str
    callback_base_url: str
    permitted_ranges: tuple[str, ...]
    allowed_tvs: tuple[AllowedTV, ...]
    timeout_seconds: float = 5.0
    tomcat_http: int = 8080
    tomcat_https: int = 8443
    apache_http: int = 8082
    apache_https: int = 8444
    database_port: int = 3306

    def authorize(self, target: str, identity: str, operation: str, execute: bool) -> None:
        if operation not in WRITE_OPERATIONS:
            raise ConfigError('unsupported control operation')
        if not execute:
            raise ConfigError("write blocked: pass --execute after reviewing the exact target")
        try:
            addr = ip_address(target)
        except ValueError as exc:
            raise ConfigError("write target must be a literal IP address") from exc
        if self.mode == 'isolated' and not addr.is_loopback:
            raise ConfigError('isolated mode permits only loopback TV targets')
        if not any(addr in ip_network(net, strict=False) for net in self.permitted_ranges):
            raise ConfigError(f"write target {target} is outside permitted_ranges")
        canonical = str(addr)
        match = next((tv for tv in self.allowed_tvs if tv.ip == canonical and tv.identity == identity), None)
        if not match:
            raise ConfigError("write blocked: IP and stable MAC/serial identity are not jointly allowlisted")
        if operation not in match.operations:
            raise ConfigError(f"write blocked: {operation!r} is not approved for this device")

    def validate_package_url(self, url: str) -> None:
        expected, candidate = urlparse(self.callback_base_url), urlparse(url)
        if candidate.scheme != expected.scheme or candidate.hostname != expected.hostname or candidate.port != expected.port:
            raise ConfigError("clone URL must use the configured callback host and port")
        path = unquote(candidate.path)
        if candidate.username or candidate.password or candidate.fragment or candidate.query:
            raise ConfigError('clone URLs must not contain credentials, query, or fragment')
        if '\\' in path or '%' in path or any(ord(c) < 32 for c in path) or any(part in {'.', '..'} for part in path.split('/')):
            raise ConfigError('clone URL contains ambiguous path encoding or traversal')
        if not path.startswith("/SmartInstall/Profile/Clone/"):
            raise ConfigError("clone URL is outside the authorized SmartInstall clone prefix")


def load_config(path: str | Path) -> Config:
    source = Path(path)
    with source.open("rb") as handle:
        raw = tomllib.load(handle)
    safety = raw.get("safety", {})
    network = raw.get("network", {})
    ports = raw.get("ports", {})
    mode = safety.get("mode", "")
    if mode not in {"isolated", "lab", "production-candidate"}:
        raise ConfigError("safety.mode must be isolated, lab, or production-candidate")
    bind = str(network.get("bind", "127.0.0.1"))
    try:
        ip_address(bind)
    except ValueError as exc:
        raise ConfigError("network.bind must be a literal IP address") from exc
    callback = str(network.get("callback_base_url", ""))
    parsed = urlparse(callback)
    if parsed.scheme not in {"http", "https"} or not parsed.hostname:
        raise ConfigError("network.callback_base_url must be an absolute HTTP(S) URL")
    ranges = tuple(str(item) for item in safety.get("permitted_ranges", []))
    for net in ranges:
        ip_network(net, strict=False)
    allowed = []
    for item in raw.get("tv", {}).get("allowlist", []):
        target = str(ip_address(str(item.get("ip", ""))))
        identity = str(item.get("identity", "")).strip()
        if not re.fullmatch(r"[A-Za-z0-9:._-]{8,128}", identity):
            raise ConfigError("each allowlisted TV requires a stable MAC or serial identity")
        operations = frozenset(str(op) for op in item.get("operations", []))
        unknown = operations - WRITE_OPERATIONS
        if unknown:
            raise ConfigError(f"unknown allowlisted operations: {sorted(unknown)}")
        allowed.append(AllowedTV(target, identity, operations))
    if mode == "production-candidate" and parsed.hostname in {"localhost", "127.0.0.1", "0.0.0.0"}:
        raise ConfigError("production-candidate callback URL must be reachable by TVs")
    timeout = float(network.get("timeout_seconds", 5.0))
    if not math.isfinite(timeout) or not 0 < timeout <= 120:
        raise ConfigError("network.timeout_seconds must be greater than zero and at most 120")
    port_values = {"tomcat_http": int(ports.get("tomcat_http", 8080)),
                   "tomcat_https": int(ports.get("tomcat_https", 8443)),
                   "apache_http": int(ports.get("apache_http", 8082)),
                   "apache_https": int(ports.get("apache_https", 8444)),
                   "database": int(ports.get("database", 3306))}
    if any(not 1 <= value <= 65535 for value in port_values.values()):
        raise ConfigError("all configured ports must be in 1..65535")
    if len(set(port_values.values())) != len(port_values):
        raise ConfigError("CMND listener ports must be distinct")
    return Config(
        mode=mode,
        bind=bind,
        callback_base_url=callback.rstrip("/"),
        permitted_ranges=ranges,
        allowed_tvs=tuple(allowed),
        timeout_seconds=timeout,
        tomcat_http=port_values["tomcat_http"], tomcat_https=port_values["tomcat_https"],
        apache_http=port_values["apache_http"], apache_https=port_values["apache_https"],
        database_port=port_values["database"],
    )
