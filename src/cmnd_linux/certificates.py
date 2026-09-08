"""Pre-provision CMND TLS material without invoking vendor Windows helpers."""

from __future__ import annotations

from dataclasses import dataclass
from ipaddress import IPv4Address, ip_address
import os
from pathlib import Path
import re
import shutil
import stat
import subprocess
import tempfile
from typing import Iterable


CA_ALIAS = "tpvision"
SERVER_ALIAS = "tomcat"
CA_SUBJECT = "/C=CN/ST=XM/L=XM/O=tpv/OU=tpv/CN=CMND"
_HOST_LABEL = re.compile(r"^(?!-)[A-Za-z0-9-]{1,63}(?<!-)$")


@dataclass(frozen=True)
class CertificateConfig:
    output_dir: Path
    public_host: str
    active_ipv4: Iterable[str]
    computer_name: str
    ca_password_file: Path
    server_password_file: Path
    existing_ca_p12: Path | None = None
    openssl_binary: str = "openssl"
    validity_days: int = 3650


@dataclass(frozen=True)
class CertificateArtifacts:
    stage: Path
    ca_p12: Path
    ca_pem: Path
    server_p12: Path
    apache_certificate: Path
    apache_private_key: Path
    apache_chain: Path
    computername_environment: Path
    ca_alias: str = CA_ALIAS
    server_alias: str = SERVER_ALIAS


def _hostname(value: str, field: str) -> str:
    value = value.rstrip(".")
    if not value or len(value) > 253 or any(not _HOST_LABEL.fullmatch(label) for label in value.split(".")):
        raise ValueError(f"{field} is not a valid hostname")
    return value


def _read_secret(path: Path) -> None:
    path = Path(path)
    if not path.is_file() or path.is_symlink():
        raise ValueError("password source must be a regular, non-symlink file")
    if os.name == "posix" and stat.S_IMODE(path.stat().st_mode) & 0o077:
        raise PermissionError("password file must have mode 0600 or stricter")
    value = path.read_bytes()
    if not value or len(value) > 4096 or b"\x00" in value or b"\n" in value or b"\r" in value:
        raise ValueError("password file must contain one nonempty line-free value")


def _reject_output_symlinks(output: Path) -> None:
    current = Path(os.path.abspath(output))
    while True:
        if current.is_symlink():
            raise ValueError("certificate output path and its ancestors cannot be symlinks")
        parent = current.parent
        if parent == current:
            return
        current = parent


def _validate(config: CertificateConfig) -> tuple[str, tuple[str, ...], str]:
    output = Path(config.output_dir)
    _reject_output_symlinks(output)
    if output.exists():
        raise FileExistsError(f"certificate stage already exists: {output}")
    if not 1 <= config.validity_days <= 3650:
        raise ValueError("validity_days must be between 1 and 3650")
    try:
        public_ip = ip_address(config.public_host)
    except ValueError:
        public_ip = None
        public_host = _hostname(config.public_host, "public_host")
    else:
        if not isinstance(public_ip, IPv4Address):
            raise ValueError("public_host IP must be IPv4")
        if public_ip.is_unspecified or public_ip.is_multicast:
            raise ValueError("public_host IP must be a usable IPv4 address")
        public_host = str(public_ip)
    computer_name = _hostname(config.computer_name, "computer_name")
    ips: set[IPv4Address] = {IPv4Address("127.0.0.1")}
    if isinstance(public_ip, IPv4Address):
        ips.add(public_ip)
    for value in config.active_ipv4:
        address = ip_address(value)
        if not isinstance(address, IPv4Address) or address.is_unspecified or address.is_multicast:
            raise ValueError("active_ipv4 must contain usable IPv4 addresses")
        ips.add(address)
    _read_secret(Path(config.ca_password_file))
    _read_secret(Path(config.server_password_file))
    if config.existing_ca_p12 is not None:
        source = Path(config.existing_ca_p12)
        if not source.is_file() or source.is_symlink():
            raise ValueError("existing CA must be a regular, non-symlink PKCS12 file")
    if shutil.which(config.openssl_binary) is None and not Path(config.openssl_binary).is_file():
        raise FileNotFoundError("OpenSSL executable was not found")
    return public_host, tuple(str(value) for value in sorted(ips)), computer_name


def _run(binary: str, args: list[str], *, action: str) -> None:
    result = subprocess.run([binary, *args], stdin=subprocess.DEVNULL, stdout=subprocess.PIPE,
                            stderr=subprocess.PIPE, check=False)
    if result.returncode:
        raise RuntimeError(f"OpenSSL failed while {action}")


def _capture(binary: str, args: list[str], *, action: str) -> bytes:
    result = subprocess.run([binary, *args], stdin=subprocess.DEVNULL, stdout=subprocess.PIPE,
                            stderr=subprocess.PIPE, check=False)
    if result.returncode:
        raise RuntimeError(f"OpenSSL failed while {action}")
    return result.stdout


def _run_pkcs12(binary: str, args: list[str], *, action: str) -> None:
    """Prefer the vendor's legacy PKCS12 encoding, with a provider-safe fallback."""
    for legacy in (["-legacy"], []):
        result = subprocess.run([binary, "pkcs12", *legacy, *args], stdin=subprocess.DEVNULL,
                                stdout=subprocess.PIPE, stderr=subprocess.PIPE, check=False)
        if result.returncode == 0:
            return
    raise RuntimeError(f"OpenSSL failed while {action}")


def _capture_pkcs12(binary: str, args: list[str], *, action: str) -> bytes:
    for legacy in (["-legacy"], []):
        result = subprocess.run([binary, "pkcs12", *legacy, *args], stdin=subprocess.DEVNULL,
                                stdout=subprocess.PIPE, stderr=subprocess.PIPE, check=False)
        if result.returncode == 0:
            return result.stdout + result.stderr
    raise RuntimeError(f"OpenSSL failed while {action}")


def _validate_retained_ca(binary: str, ca_p12: Path, ca_pem: Path, ca_key: Path,
                          ca_password: str) -> None:
    package_info = _capture_pkcs12(
        binary, ["-in", str(ca_p12), "-nokeys", "-passin", f"file:{ca_password}"],
        action="checking the retained CA alias")
    if not re.search(rb"(?mi)^\s*friendlyName:\s*" + re.escape(CA_ALIAS.encode()) + rb"\s*$",
                     package_info):
        raise ValueError(f"retained CA PKCS12 must use alias {CA_ALIAS!r}")
    if ca_pem.read_bytes().count(b"-----BEGIN CERTIFICATE-----") != 1:
        raise ValueError("retained CA PKCS12 must contain exactly one certificate")
    _run(binary, ["x509", "-in", str(ca_pem), "-noout", "-checkend", "0"],
         action="checking retained CA validity")
    basic_constraints = _capture(
        binary, ["x509", "-in", str(ca_pem), "-noout", "-ext", "basicConstraints"],
        action="checking retained CA constraints")
    key_usage = _capture(binary, ["x509", "-in", str(ca_pem), "-noout", "-ext", "keyUsage"],
                         action="checking retained CA key usage")
    if b"CA:TRUE" not in basic_constraints.replace(b" ", b""):
        raise ValueError("retained certificate is not a CA")
    if b"Certificate Sign" not in key_usage:
        raise ValueError("retained CA cannot sign certificates")
    cert_public_key = _capture(binary, ["x509", "-in", str(ca_pem), "-pubkey", "-noout"],
                               action="reading the retained CA public key")
    private_public_key = _capture(
        binary, ["pkey", "-in", str(ca_key), "-passin", f"file:{ca_password}", "-pubout"],
        action="reading the retained CA private key")
    if cert_public_key.strip() != private_public_key.strip():
        raise ValueError("retained CA certificate and private key do not match")
    _run(binary, ["verify", "-CAfile", str(ca_pem), str(ca_pem)],
         action="checking the retained CA self-signature")


def _write(path: Path, value: str, mode: int = 0o600) -> None:
    path.write_text(value, encoding="utf-8", newline="\n")
    os.chmod(path, mode)


def provision_certificates(config: CertificateConfig) -> CertificateArtifacts:
    """Create a fresh certificate stage; optionally retain an existing CA identity."""
    public_host, ips, computer_name = _validate(config)
    output = Path(config.output_dir)
    output.parent.mkdir(parents=True, exist_ok=True)
    _reject_output_symlinks(output)
    work = Path(tempfile.mkdtemp(prefix=f".{output.name}.", dir=output.parent))
    os.chmod(work, 0o700)
    binary = config.openssl_binary
    ca_password = str(Path(config.ca_password_file).resolve())
    server_password = str(Path(config.server_password_file).resolve())
    try:
        cert_dir = work / "Cert"
        tomcat_dir = work / "tomcat"
        apache_dir = work / "apache"
        for directory in (cert_dir, tomcat_dir, apache_dir):
            directory.mkdir(mode=0o700)
        # Some OpenSSL builds consume a shared file source when it is used for both
        # passin and passout in one process. Keep a second 0600 file, never plaintext argv.
        ca_password_out_file = work / ".ca-password-out"
        shutil.copyfile(config.ca_password_file, ca_password_out_file)
        os.chmod(ca_password_out_file, 0o600)
        ca_password_out = str(ca_password_out_file.resolve())
        ca_key = cert_dir / "ca.key"
        ca_pem = cert_dir / "ca.pem"
        ca_p12 = cert_dir / "ca.p12"
        if config.existing_ca_p12 is None:
            _run(binary, ["req", "-x509", "-newkey", "rsa:3072", "-sha256", "-days",
                  str(config.validity_days), "-keyout", str(ca_key), "-out", str(ca_pem),
                  "-subj", CA_SUBJECT, "-passout", f"file:{ca_password}",
                  "-addext", "basicConstraints=critical,CA:TRUE,pathlen:0",
                  "-addext", "keyUsage=critical,keyCertSign,cRLSign"], action="creating the CA")
            _run_pkcs12(binary, ["-export", "-name", CA_ALIAS,
                  "-inkey", str(ca_key), "-in", str(ca_pem), "-out", str(ca_p12),
                  "-passin", f"file:{ca_password}", "-passout", f"file:{ca_password_out}"],
                 action="packaging the CA")
        else:
            shutil.copyfile(config.existing_ca_p12, ca_p12)
            _run_pkcs12(binary, ["-in", str(ca_p12), "-nokeys",
                  "-out", str(ca_pem), "-passin", f"file:{ca_password}"], action="reading the retained CA")
            _run_pkcs12(binary, ["-in", str(ca_p12), "-nocerts",
                  "-out", str(ca_key), "-passin", f"file:{ca_password}",
                  "-passout", f"file:{ca_password_out}"], action="reading the retained CA key")
            _validate_retained_ca(binary, ca_p12, ca_pem, ca_key, ca_password)
        for private_path in (ca_key, ca_p12):
            os.chmod(private_path, 0o600)
        os.chmod(ca_pem, 0o644)

        server_key = apache_dir / "serverkey.pem"
        server_csr = work / "server.csr"
        server_pem = apache_dir / "servercert.pem"
        san_config = work / "server-san.cnf"
        dns_names: list[str] = []
        try:
            ip_address(public_host)
        except ValueError:
            dns_names.append(public_host)
        for name in (computer_name, "localhost"):
            if name.casefold() not in {entry.casefold() for entry in dns_names}:
                dns_names.append(name)
        san = ",".join([*(f"DNS:{name}" for name in dns_names), *(f"IP:{value}" for value in ips)])
        _write(san_config, "[server_cert]\n"
               "basicConstraints=critical,CA:FALSE\n"
               "keyUsage=critical,digitalSignature,keyEncipherment\n"
               "extendedKeyUsage=serverAuth\n"
               f"subjectAltName={san}\n", 0o600)
        _run(binary, ["genpkey", "-algorithm", "RSA", "-pkeyopt", "rsa_keygen_bits:2048",
              "-out", str(server_key)], action="creating the server key")
        _run(binary, ["req", "-new", "-sha256", "-key", str(server_key), "-out", str(server_csr),
              "-subj", f"/C=CN/ST=XM/L=XM/O=tpv/OU=tpv/CN={computer_name}"],
             action="creating the server request")
        _run(binary, ["x509", "-req", "-sha256", "-days", str(config.validity_days),
              "-in", str(server_csr), "-CA", str(ca_pem), "-CAkey", str(ca_key),
              "-passin", f"file:{ca_password}", "-CAcreateserial", "-out", str(server_pem),
              "-extfile", str(san_config), "-extensions", "server_cert"], action="signing the server certificate")
        server_p12 = tomcat_dir / "server.p12"
        _run_pkcs12(binary, ["-export", "-name", SERVER_ALIAS,
              "-inkey", str(server_key), "-in", str(server_pem), "-certfile", str(ca_pem),
              "-out", str(server_p12), "-passout", f"file:{server_password}"],
             action="packaging the server certificate")
        chain = apache_dir / "server-chain.pem"
        chain.write_bytes(server_pem.read_bytes() + ca_pem.read_bytes())
        environment = work / "computername.env"
        _write(environment, f"COMPUTERNAME={computer_name}\n", 0o644)
        for path in (server_key, server_p12):
            os.chmod(path, 0o600)
        for path in (server_pem, chain):
            os.chmod(path, 0o644)
        server_csr.unlink()
        san_config.unlink()
        serial_file = cert_dir / "ca.srl"
        if serial_file.exists():
            serial_file.unlink()
        ca_password_out_file.unlink()
        os.replace(work, output)
    except Exception:
        shutil.rmtree(work, ignore_errors=True)
        raise
    return CertificateArtifacts(
        output, output / "Cert/ca.p12", output / "Cert/ca.pem", output / "tomcat/server.p12",
        output / "apache/servercert.pem", output / "apache/serverkey.pem",
        output / "apache/server-chain.pem", output / "computername.env")
