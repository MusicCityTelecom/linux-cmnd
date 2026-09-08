from __future__ import annotations

from datetime import datetime, timezone
from pathlib import Path
import json
import os
import shutil
import socket
import tarfile


REQUIRED_WARS = ("cas.war", "usermanagement.war", "smartcontrol.war", "SmartInstall.war", "smartcms.war")


class RuntimeErrorCMND(RuntimeError):
    pass


def check_port(host: str, port: int) -> bool:
    with socket.socket() as sock:
        sock.settimeout(0.2)
        return sock.connect_ex((host, port)) == 0


def preflight(source: Path | None = None) -> dict:
    result = {
        "platform": os.name,
        "ports": {str(port): ("in-use" if check_port("127.0.0.1", port) else "available")
                  for port in (3306, 3307, 8080, 8082, 8443, 8444)},
        "tools": {name: shutil.which(name) for name in ("java", "systemctl", "docker", "podman", "innoextract", "innounp")},
    }
    if source:
        result["wars"] = {name: (source / name).is_file() for name in REQUIRED_WARS}
    return result


def install_release(source: Path, root: Path, release: str, *, execute: bool) -> dict:
    missing = [name for name in REQUIRED_WARS if not (source / name).is_file()]
    if missing:
        raise RuntimeErrorCMND(f"required application payload missing: {', '.join(missing)}")
    destination = root / "releases" / release
    if destination.exists():
        marker = destination / "release.json"
        if marker.is_file():
            return json.loads(marker.read_text(encoding="utf-8"))
        raise RuntimeErrorCMND(f"refusing unknown existing release directory: {destination}")
    plan = {"release": release, "source": str(source.resolve()), "destination": str(destination.resolve()),
            "wars": list(REQUIRED_WARS), "executed": execute}
    if not execute:
        return plan
    lock = root / ".cmndctl.lock"
    root.mkdir(parents=True, exist_ok=True)
    try:
        fd = os.open(lock, os.O_CREAT | os.O_EXCL | os.O_WRONLY, 0o600)
    except FileExistsError as exc:
        raise RuntimeErrorCMND("another cmndctl lifecycle operation is active") from exc
    os.close(fd)
    try:
        webapps = destination / "tomcat" / "webapps"
        webapps.mkdir(parents=True)
        for name in REQUIRED_WARS:
            shutil.copy2(source / name, webapps / name)
        (destination / "state").mkdir()
        plan["installed_at"] = datetime.now(timezone.utc).isoformat()
        (destination / "release.json").write_text(json.dumps(plan, indent=2) + "\n", encoding="utf-8")
        current = root / "current.txt"
        current.write_text(release + "\n", encoding="utf-8")
        if os.name == "posix":
            link, pending = root / "current", root / ".current.next"
            pending.unlink(missing_ok=True)
            pending.symlink_to(destination.relative_to(root), target_is_directory=True)
            pending.replace(link)
        return plan
    except Exception:
        shutil.rmtree(destination, ignore_errors=True)
        raise
    finally:
        lock.unlink(missing_ok=True)


def backup_runtime(root: Path, output: Path) -> dict:
    if not root.is_dir() or not (root / "current.txt").is_file():
        raise RuntimeErrorCMND("runtime root is not an initialized cmndctl installation")
    output.parent.mkdir(parents=True, exist_ok=True)
    if output.exists():
        raise RuntimeErrorCMND("backup output already exists")
    with tarfile.open(output, "w:gz") as archive:
        for name in ("current.txt", "releases"):
            path = root / name
            if path.exists():
                archive.add(path, arcname=name, recursive=True)
    return {"backup": str(output.resolve()), "bytes": output.stat().st_size}


def restore_runtime(backup: Path, root: Path, *, execute: bool) -> dict:
    if not backup.is_file():
        raise RuntimeErrorCMND("backup does not exist")
    with tarfile.open(backup, "r:gz") as archive:
        members = archive.getmembers()
        for member in members:
            path = Path(member.name)
            if path.is_absolute() or ".." in path.parts or member.issym() or member.islnk():
                raise RuntimeErrorCMND(f"unsafe backup member: {member.name}")
        plan = {"backup": str(backup.resolve()), "root": str(root.resolve()), "members": len(members), "executed": execute}
        if not execute:
            return plan
        if (root / "current.txt").exists():
            raise RuntimeErrorCMND("restore destination is populated; use a clean root")
        root.mkdir(parents=True, exist_ok=True)
        archive.extractall(root, filter="data")
        return plan


def status(root: Path) -> dict:
    current_file = root / "current.txt"
    current = current_file.read_text(encoding="utf-8").strip() if current_file.is_file() else None
    marker = root / "releases" / current / "release.json" if current else None
    return {"root": str(root.resolve()), "current_release": current,
            "release_metadata": json.loads(marker.read_text(encoding="utf-8")) if marker and marker.is_file() else None}


def rollback(root: Path, release: str, *, execute: bool) -> dict:
    marker = root / "releases" / release / "release.json"
    if not marker.is_file():
        raise RuntimeErrorCMND("requested rollback release is not installed")
    result = {"root": str(root.resolve()), "release": release, "executed": execute}
    if execute:
        (root / "current.txt").write_text(release + "\n", encoding="utf-8")
        if os.name == "posix":
            link, pending = root / "current", root / ".current.next"
            pending.unlink(missing_ok=True)
            pending.symlink_to((root / "releases" / release).relative_to(root), target_is_directory=True)
            pending.replace(link)
    return result


def uninstall(root: Path, *, keep_data: bool, execute: bool) -> dict:
    if not keep_data:
        raise RuntimeErrorCMND("data-destructive uninstall is intentionally unsupported; use --keep-data")
    result = {"root": str(root.resolve()), "keep_data": True, "executed": execute,
              "note": "service removal is host-specific; releases and state are preserved"}
    if execute:
        (root / "disabled-by-cmndctl").write_text(datetime.now(timezone.utc).isoformat() + "\n", encoding="utf-8")
    return result
