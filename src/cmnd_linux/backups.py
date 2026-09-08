from __future__ import annotations

from dataclasses import dataclass
from pathlib import Path
import getpass
import os
import re
import zipfile

from .artifacts import safe_extract_zip


REQUIRED_DATABASES = {
    "cas/cas.sql": "cas",
    "CMS/tpvision.sql": "tpvision",
    "smartcms/smartcms.sql": "smartcms",
    "smartcontrol/smartcontroldb.sql": "smartcontroldb",
    "SmartInstall/SmartInstall.sql": "SmartInstall",
}


class BackupError(ValueError):
    pass


def read_password(path: Path | None, prompt: bool) -> bytes | None:
    if path:
        if os.name == "posix" and path.stat().st_mode & 0o077:
            raise BackupError("password file must not be accessible by group/other")
        value = path.read_bytes().rstrip(b"\r\n")
        if not value:
            raise BackupError("password file is empty")
        return value
    if prompt:
        value = getpass.getpass("CMND backup password: ").encode()
        return value or None
    return None


def inspect_windows_backup(path: Path, password: bytes | None = None) -> dict:
    if not zipfile.is_zipfile(path):
        raise BackupError("Windows CMND backup must be a ZIP archive")
    found: dict[str, dict] = {}
    with zipfile.ZipFile(path) as archive:
        normalized = {info.filename.replace("\\", "/"): info for info in archive.infolist()}
        for member, database in REQUIRED_DATABASES.items():
            info = normalized.get(member)
            if info is None:
                raise BackupError(f"required database dump missing: {member}")
            try:
                with archive.open(info, pwd=password) as handle:
                    header = handle.read(128 * 1024).decode("utf-8", "replace")
            except RuntimeError as exc:
                raise BackupError("backup password is missing or incorrect") from exc
            patterns = (rf"Current Database:\s*`{re.escape(database)}`", rf"Database:\s*{re.escape(database)}(?:\s|$)")
            if not any(re.search(pattern, header, re.IGNORECASE) for pattern in patterns):
                raise BackupError(f"dump header does not identify expected database {database}: {member}")
            found[member] = {"database": database, "bytes": info.file_size,
                             "encrypted": bool(info.flag_bits & 1)}
    return {"backup": str(path.resolve()), "databases": found, "complete": True}


def prepare_windows_restore(path: Path, staging: Path, password: bytes | None, execute: bool) -> dict:
    result = inspect_windows_backup(path, password)
    result.update({"staging": str(staging.resolve()), "executed": execute,
                   "database_import_executed": False})
    if not execute:
        return result
    if staging.exists():
        raise BackupError("restore staging destination already exists")
    staging.mkdir(parents=True, mode=0o700)
    try:
        safe_extract_zip(path, staging, password=password)
    except Exception:
        try: staging.rmdir()
        except OSError: pass
        raise
    return result
