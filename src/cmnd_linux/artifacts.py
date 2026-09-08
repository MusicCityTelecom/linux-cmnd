from __future__ import annotations

from dataclasses import asdict, dataclass
from hashlib import sha256
from pathlib import Path, PurePosixPath
import json
import os
import stat
import zipfile


class UnsafeArchive(ValueError):
    pass


@dataclass
class Artifact:
    path: str
    bytes: int
    sha256: str
    category: str
    confidentiality: str


PRIVATE_SUFFIXES = {".exe", ".war", ".jar", ".zip", ".sql", ".pcap", ".pcapng", ".p12", ".pem", ".key"}


def file_hash(path: Path, chunk_size: int = 1024 * 1024) -> str:
    digest = sha256()
    with path.open("rb") as handle:
        for chunk in iter(lambda: handle.read(chunk_size), b""):
            digest.update(chunk)
    return digest.hexdigest()


def categorize(path: Path) -> str:
    suffix = path.suffix.lower()
    return {
        ".exe": "vendor-binary", ".war": "java-webapp", ".jar": "java-library",
        ".zip": "archive", ".sql": "database", ".pcap": "capture",
        ".pcapng": "capture", ".p12": "certificate", ".properties": "configuration",
    }.get(suffix, "other")


def inventory(root: str | Path) -> list[Artifact]:
    base = Path(root).resolve()
    records: list[Artifact] = []
    for path in sorted(p for p in base.rglob("*") if p.is_file() and ".git" not in p.parts):
        records.append(Artifact(
            path=path.relative_to(base).as_posix(), bytes=path.stat().st_size,
            sha256=file_hash(path), category=categorize(path),
            confidentiality="private-input" if path.suffix.lower() in PRIVATE_SUFFIXES else "review",
        ))
    return records


def write_inventory(records: list[Artifact], destination: str | Path) -> None:
    output = Path(destination)
    output.parent.mkdir(parents=True, exist_ok=True)
    output.write_text(json.dumps([asdict(r) for r in records], indent=2) + "\n", encoding="utf-8")


def _safe_member(name: str) -> PurePosixPath:
    normalized = name.replace("\\", "/")
    if normalized.startswith(("/", "//")) or (len(normalized) >= 2 and normalized[1] == ":"):
        raise UnsafeArchive(f"absolute/drive archive path rejected: {name!r}")
    path = PurePosixPath(normalized)
    if not normalized or any(part in {"", ".", ".."} for part in path.parts):
        raise UnsafeArchive(f"unsafe archive path rejected: {name!r}")
    return path


def safe_extract_zip(source: str | Path, destination: str | Path, *, max_files: int = 20_000,
                     max_bytes: int = 4 * 1024**3, max_ratio: int = 200) -> list[str]:
    archive, target = Path(source), Path(destination).resolve()
    extracted: list[str] = []
    seen: set[str] = set()
    created_files: list[Path] = []
    with zipfile.ZipFile(archive) as zf:
        members = zf.infolist()
        if len(members) > max_files:
            raise UnsafeArchive("archive file-count limit exceeded")
        total = sum(m.file_size for m in members)
        if total > max_bytes:
            raise UnsafeArchive("archive expansion-size limit exceeded")
        validated = []
        for member in members:
            path = _safe_member(member.filename)
            folded = path.as_posix().casefold()
            if folded in seen:
                raise UnsafeArchive(f"normalized-name collision: {member.filename!r}")
            seen.add(folded)
            mode = member.external_attr >> 16
            if stat.S_ISLNK(mode):
                raise UnsafeArchive(f"symbolic link rejected: {member.filename!r}")
            if member.compress_size and member.file_size / member.compress_size > max_ratio:
                raise UnsafeArchive(f"compression-ratio limit exceeded: {member.filename!r}")
            output = target.joinpath(*path.parts).resolve()
            try:
                output.relative_to(target)
            except ValueError as exc:
                raise UnsafeArchive(f"path traversal rejected: {member.filename!r}") from exc
            validated.append((member, path, output))
        try:
            for member, path, output in validated:
                if member.is_dir() or member.filename.endswith(("/", "\\")):
                    output.mkdir(parents=True, exist_ok=True)
                    continue
                output.parent.mkdir(parents=True, exist_ok=True)
                flags = os.O_WRONLY | os.O_CREAT | os.O_EXCL
                fd = os.open(output, flags, 0o600)
                created_files.append(output)
                try:
                    with os.fdopen(fd, "wb") as dst, zf.open(member) as src:
                        while chunk := src.read(1024 * 1024):
                            dst.write(chunk)
                except Exception:
                    output.unlink(missing_ok=True)
                    raise
                extracted.append(path.as_posix())
        except Exception:
            for output in reversed(created_files):
                output.unlink(missing_ok=True)
            raise
    return extracted
