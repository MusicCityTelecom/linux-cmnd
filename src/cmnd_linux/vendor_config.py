"""Render host-specific configuration into operator-supplied CMND WARs.

The vendor archives are private inputs.  This module only writes derived archives
to an explicitly supplied staging directory; it never stores vendor payloads in
the source tree by itself.
"""

from __future__ import annotations

from dataclasses import dataclass
import ipaddress
import json
from pathlib import Path
import re
import shutil
import tempfile
from typing import Mapping
from zipfile import ZipFile


WAR_NAMES = ("cas.war", "smartcms.war", "smartcontrol.war", "SmartInstall.war", "usermanagement.war")

REQUIRED_PORTS = ("tomcat_http", "tomcat_https", "apache_http", "apache_https", "database")
REQUIRED_SECRETS = (
    "cas_db_password",
    "smartcms_db_password",
    "smartcontrol_db_password",
    "smartinstall_db_password",
    "application_security_password",
    "cert_ca_password",
)


@dataclass(frozen=True)
class VendorRenderConfig:
    public_host: str
    ports: Mapping[str, int]
    secrets: Mapping[str, str]
    database_host: str = "127.0.0.1"

    def validate(self) -> None:
        if not _valid_host(self.public_host):
            raise ValueError("public_host must be a hostname or IP address")
        if not _valid_host(self.database_host):
            raise ValueError("database_host must be a hostname or IP address")
        missing_ports = sorted(set(REQUIRED_PORTS) - self.ports.keys())
        if missing_ports:
            raise ValueError(f"missing ports: {', '.join(missing_ports)}")
        for name in REQUIRED_PORTS:
            value = self.ports[name]
            if isinstance(value, bool) or not isinstance(value, int) or not 1 <= value <= 65535:
                raise ValueError(f"invalid port: {name}")
        missing_secrets = sorted(name for name in REQUIRED_SECRETS if not self.secrets.get(name))
        if missing_secrets:
            raise ValueError(f"missing secrets: {', '.join(missing_secrets)}")
        if any("\n" in value or "\r" in value for value in self.secrets.values()):
            raise ValueError("secrets must not contain line breaks")


def render_vendor_wars(source_dir: Path, stage_dir: Path, config: VendorRenderConfig, *, linux_helpers: bool = False) -> list[Path]:
    """Copy and configure the five CMND WARs into a new, empty stage directory."""
    config.validate()
    source_dir = Path(source_dir)
    stage_dir = Path(stage_dir)
    if stage_dir.exists() and any(stage_dir.iterdir()):
        raise FileExistsError(f"staging directory is not empty: {stage_dir}")
    stage_dir.mkdir(parents=True, exist_ok=True)
    missing = [name for name in WAR_NAMES if not (source_dir / name).is_file()]
    if missing:
        raise FileNotFoundError(f"missing vendor WARs: {', '.join(missing)}")

    rendered: list[Path] = []
    for name in WAR_NAMES:
        source = source_dir / name
        destination = stage_dir / name
        replacements = _replacements(name, source, config)
        if linux_helpers and name == 'SmartInstall.war':
            from .java_portability import linux_class_replacements
            replacements.update(linux_class_replacements(source))
        _rewrite_zip(source, destination, replacements)
        rendered.append(destination)
    return rendered


def _valid_host(value: str) -> bool:
    if not value or len(value) > 253 or "/" in value or "://" in value:
        return False
    candidate = value[1:-1] if value.startswith("[") and value.endswith("]") else value
    try:
        ipaddress.ip_address(candidate)
        return True
    except ValueError:
        return all(re.fullmatch(r"[A-Za-z0-9](?:[A-Za-z0-9-]{0,61}[A-Za-z0-9])?", part) for part in candidate.split("."))


def _read_entries(path: Path, names: tuple[str, ...]) -> dict[str, bytes]:
    with ZipFile(path) as archive:
        available = set(archive.namelist())
        missing = sorted(set(names) - available)
        if missing:
            raise ValueError(f"{path.name} lacks expected configuration entries: {', '.join(missing)}")
        return {name: archive.read(name) for name in names}


def _props(data: bytes, updates: Mapping[str, str], *, additions: Mapping[str, str] | None = None) -> bytes:
    text = data.decode("utf-8")
    newline = "\r\n" if "\r\n" in text else "\n"
    lines = text.splitlines()
    found: set[str] = set()
    existing: set[str] = set()
    for index, line in enumerate(lines):
        if not line or line.lstrip().startswith(("#", "!")) or "=" not in line:
            continue
        key = line.split("=", 1)[0].strip()
        existing.add(key)
        if key in updates:
            lines[index] = f"{key}={_java_property_value(updates[key])}"
            found.add(key)
    missing = sorted(set(updates) - found)
    if missing:
        raise ValueError(f"properties resource lacks expected keys: {', '.join(missing)}")
    for key, value in (additions or {}).items():
        if key in existing:
            raise ValueError(f"property expected to be absent before addition: {key}")
        lines.append(f"{key}={_java_property_value(value)}")
    return (newline.join(lines) + (newline if text.endswith(("\n", "\r")) else "")).encode("utf-8")


def _yaml_scalar(data: bytes, key: str, value: str, *, occurrence: int = 1) -> bytes:
    text = data.decode("utf-8")
    pattern = re.compile(rf"(?m)^(\s*){re.escape(key)}\s*:\s*.*$")
    matches = list(pattern.finditer(text))
    if len(matches) != occurrence:
        raise ValueError(f"YAML resource expected {occurrence} occurrence(s) of {key}, found {len(matches)}")
    # JSON string syntax is a safe subset of YAML string syntax.
    encoded = json.dumps(value, ensure_ascii=True)
    return pattern.sub(lambda match: f"{match.group(1)}{key}: {encoded}", text).encode("utf-8")


def _java_property_value(value: str) -> str:
    """Escape a value for java.util.Properties without changing its meaning."""
    escaped: list[str] = []
    for index, char in enumerate(value):
        if char == "\\":
            escaped.append("\\\\")
        elif char == "\t":
            escaped.append("\\t")
        elif char == "\f":
            escaped.append("\\f")
        elif index == 0 and char == " ":
            escaped.append("\\ ")
        elif char in "=:# !":
            escaped.append("\\" + char)
        elif ord(char) > 0x7E:
            encoded = char.encode("utf-16-be")
            escaped.extend(f"\\u{int.from_bytes(encoded[offset:offset + 2], 'big'):04x}" for offset in range(0, len(encoded), 2))
        else:
            escaped.append(char)
    return "".join(escaped)


def _smartinstall_xml(data: bytes, password: str) -> bytes:
    text = data.decode("utf-8")
    pattern = re.compile(r'(<property\s+name="password"\s+value=")[^"]*("\s*/>)')
    text, count = pattern.subn(lambda match: match.group(1) + _xml_attr(password) + match.group(2), text)
    if count != 1:
        raise ValueError(f"SmartInstall datasource expected one password property, found {count}")
    return text.encode("utf-8")


def _disable_config_logging(data: bytes) -> bytes:
    text = data.decode("utf-8")
    logger = '        <Logger name="com.tpvision.smartinstall.util.Configs" level="off" additivity="false"/>\n'
    if "com.tpvision.smartinstall.util.Configs" in text:
        return data
    marker = "    </Loggers>"
    if text.count(marker) != 1:
        raise ValueError("SmartInstall log4j2.xml lacks a unique Loggers element")
    return text.replace(marker, logger + marker).encode("utf-8")


def _xml_attr(value: str) -> str:
    return value.replace("&", "&amp;").replace('"', "&quot;").replace("<", "&lt;").replace(">", "&gt;")


def _replacements(war_name: str, source: Path, cfg: VendorRenderConfig) -> dict[str, bytes]:
    host = cfg.public_host
    db_host = cfg.database_host
    p = cfg.ports
    s = cfg.secrets
    if war_name == "cas.war":
        names = ("WEB-INF/classes/application.yml", "WEB-INF/classes/services/localhost-100.json")
        entries = _read_entries(source, names)
        app = _yaml_scalar(entries[names[0]], "password", s["cas_db_password"])
        app = _yaml_scalar(app, "url", f"jdbc:mysql://{db_host}:{p['database']}/cas?serverTimezone=UTC&allowMultiQueries=true")
        service = json.loads(entries[names[1]].decode("utf-8"))
        host_pattern = re.escape(host)
        service_pattern = rf"^https?://{host_pattern}(?::\d+)?/.*"
        service["serviceId"] = service_pattern
        service["name"] = f"CMND on {host}"
        service["proxyPolicy"]["pattern"] = service_pattern
        return {names[0]: app, names[1]: (json.dumps(service, indent=4) + "\n").encode("utf-8")}

    if war_name == "smartcms.war":
        names = ("WEB-INF/classes/application.properties", "WEB-INF/classes/config.properties")
        entries = _read_entries(source, names)
        return {
            names[0]: _props(entries[names[0]], {
                "db.url": f"jdbc:mysql://{db_host}:{p['database']}/smartcms",
                "db.password": s["smartcms_db_password"],
            }),
            names[1]: _props(entries[names[1]], _endpoint_properties(host, p)),
        }

    if war_name == "smartcontrol.war":
        names = ("WEB-INF/classes/application.properties", "WEB-INF/classes/application-production.properties")
        entries = _read_entries(source, names)
        return {
            names[0]: _props(entries[names[0]], {
                "smartcms.content.url": f"http://{host}:{p['tomcat_http']}/smartcms/website/download/",
                "smartcms.content.list.url": f"http://{host}:{p['tomcat_http']}/smartcms/website/list",
                "smartcontrol.system.path": "/var/lib/cmnd/smartcontrol",
                "security.user.password": s["application_security_password"],
                "server.port": str(p["tomcat_http"]),
            }),
            names[1]: _props(entries[names[1]], {
                "cas.url": f"http://{host}:{p['tomcat_http']}/cas",
                "cas.service-url": f"http://{host}:${{server.port}}${{server.context-path}}/login/cas",
                "cas.proxy.callback-url": f"http://{host}:${{server.port}}${{server.context-path}}${{cas.proxy.receptor-url}}",
                "spring.datasource.url": f"jdbc:mysql://{db_host}:{p['database']}/smartcontroldb",
                "spring.datasource.password": s["smartcontrol_db_password"],
            }),
        }

    if war_name == "SmartInstall.war":
        names = ("WEB-INF/config.properties", "WEB-INF/applicationContext.xml", "WEB-INF/log4j2.xml")
        entries = _read_entries(source, names)
        updates = _endpoint_properties(host, p) | {
            "server.https.port": str(p["tomcat_https"]),
            "cms.https.port": str(p["apache_https"]),
            "cas.https.port": str(p["tomcat_https"]),
            "published.https.port": str(p["tomcat_https"]),
            "cas.url": f"http://{host}:${{server.port}}/cas",
            "cms.url": f"http://{host}:${{cms.port}}/SmartCMS",
            "cas.service-url": f"http://{host}:${{server.port}}/SmartInstall",
            "listener.load": "off",
            "cert.ca.password": s["cert_ca_password"],
            "security.pwd": s["application_security_password"],
            "mysql.ip": db_host,
            "mysql.port": str(p["database"]),
            # Shipped third-party credentials must not survive into a rendered runtime.
            "survey.email.smtp.account": s.get("survey_email_account", ""),
            "survey.email.smtp.pwd": s.get("survey_email_password", ""),
            "cloud.server.auth.clientid": s.get("cloud_client_id", ""),
            "cloud.server.auth.clientsecret": s.get("cloud_client_secret", ""),
        }
        return {
            names[0]: _props(entries[names[0]], updates, additions={
                # These are the actual keys consumed by menuBar/CommonConstants;
                # the shipped file contains differently punctuated aliases.
                "server.httpsport": str(p["tomcat_https"]),
                "tomcat.http.port": str(p["tomcat_http"]),
                "tomcat.https.port": str(p["tomcat_https"]),
                "cms.port.https": str(p["apache_https"]),
                "philips.path": "/opt/Philips/",
            }),
            names[1]: _smartinstall_xml(entries[names[1]], s["smartinstall_db_password"]),
            names[2]: _disable_config_logging(entries[names[2]]),
        }

    if war_name == "usermanagement.war":
        names = ("WEB-INF/classes/application.properties", "WEB-INF/classes/application-production.properties")
        entries = _read_entries(source, names)
        return {
            names[0]: _props(entries[names[0]], {
                "security.user.password": s["application_security_password"],
                "server.port": str(p["tomcat_http"]),
            }),
            names[1]: _props(entries[names[1]], {
                "cas.url": f"http://{host}:{p['tomcat_http']}/cas",
                "cas.service-url": f"http://{host}:${{server.port}}${{server.context-path}}/login/cas",
                "spring.datasource.url": f"jdbc:mysql://{db_host}:{p['database']}/cas",
                "spring.datasource.password": s["cas_db_password"],
            }),
        }
    raise AssertionError(f"unhandled WAR: {war_name}")


def _endpoint_properties(host: str, ports: Mapping[str, int]) -> dict[str, str]:
    return {
        "server.port": str(ports["tomcat_http"]),
        "server.name": host,
        "cms.port": str(ports["apache_http"]),
        "cms.name": host,
        "cas.port": str(ports["tomcat_http"]),
        "cas.name": host,
        "published.port": str(ports["tomcat_http"]),
        "published.name": host,
    }


def _rewrite_zip(source: Path, destination: Path, replacements: Mapping[str, bytes]) -> None:
    with tempfile.NamedTemporaryFile(prefix=f".{destination.name}.", suffix=".tmp", dir=destination.parent, delete=False) as handle:
        temporary = Path(handle.name)
    try:
        with ZipFile(source, "r") as original, ZipFile(temporary, "w") as rendered:
            available = set(original.namelist())
            missing = sorted(set(replacements) - available)
            if missing:
                raise ValueError(f"{source.name} lacks expected entries: {', '.join(missing)}")
            rendered.comment = original.comment
            for info in original.infolist():
                data = replacements.get(info.filename, original.read(info.filename))
                rendered.writestr(info, data)
        shutil.move(temporary, destination)
    finally:
        temporary.unlink(missing_ok=True)
