"""Bounded, source-only preparation of CMND 7.4.8 Windows SQL backups.

The preparer never connects to a database and deliberately ignores non-SQL
customer content in the backup.  Its lexical checks reduce accidental restore
risk, but they are not an SQL execution sandbox or a substitute for restoring
into an isolated disposable database first.
"""

from __future__ import annotations

from hashlib import sha256
import json
import os
from pathlib import Path, PurePosixPath
import re
import stat
import zipfile

from .sql_audit import (
    _DATABASE_REFERENCE,
    _OBJECT_REFERENCE,
    _lexically_mask,
    audit_sql,
)


WINDOWS_748_DATABASES = {
    "cas/cas.sql": ("cas", "cas", "schema_version"),
    "CMS/tpvision.sql": ("tpvision", "tpvision", None),
    "smartcms/smartcms.sql": ("smartcms", "smartcms", "schema_version"),
    "smartcontrol/smartcontroldb.sql": (
        "smartcontroldb",
        "smartcontroldb",
        "schema_version",
    ),
    # Windows names this database and directory with capitals.  The Linux WAR
    # connects to the lowercase schema on a case-sensitive MySQL filesystem.
    "SmartInstall/SmartInstall.sql": (
        "SmartInstall",
        "smartinstall",
        "flyway_schema_history",
    ),
}

_EXPECTED_DEFINERS = {database: 0 for _, database, _ in WINDOWS_748_DATABASES.values()}
_EXPECTED_DEFINERS["smartinstall"] = 11
_FORBIDDEN_CONSTRUCTS = frozenset(
    {
        "drop_database",
        "drop_user",
        "grant",
        "create_user",
        "set_global",
        "load_data_local",
        "file_write",
        "file_read",
        "mysql_system",
    }
)
_PROVEN_DEFINER = re.compile(
    r"\bDEFINER\s*=\s*`siuser`\s*@\s*`localhost`", re.IGNORECASE
)


class RestorePreparationError(ValueError):
    pass


def _archive_member(name: str) -> PurePosixPath:
    normalized = name.replace("\\", "/")
    path = PurePosixPath(normalized)
    if (
        not normalized
        or normalized.startswith(("/", "//"))
        or (len(normalized) > 1 and normalized[1] == ":")
        or any(part in {"", ".", ".."} for part in path.parts)
    ):
        raise RestorePreparationError("unsafe archive member path")
    return path


def _sha256_bytes(value: bytes) -> str:
    return sha256(value).hexdigest()


def _file_sha256(path: Path) -> str:
    digest = sha256()
    with path.open("rb") as handle:
        for block in iter(lambda: handle.read(1024 * 1024), b""):
            digest.update(block)
    return digest.hexdigest()


def _literal_digest(sql: str) -> str:
    """Hash every string literal verbatim, without returning literal content."""

    digest = sha256()
    index = 0
    while index < len(sql):
        if sql[index] not in {"'", '"'}:
            index += 1
            continue
        quote = sql[index]
        end = index + 1
        while end < len(sql):
            if sql[end] == "\\":
                end = min(len(sql), end + 2)
                continue
            if sql[end] == quote:
                if end + 1 < len(sql) and sql[end + 1] == quote:
                    end += 2
                    continue
                end += 1
                break
            end += 1
        encoded = sql[index:end].encode("utf-8")
        digest.update(len(encoded).to_bytes(8, "big"))
        digest.update(encoded)
        index = end
    return digest.hexdigest()


def _history_digest(sql: str, marker: str | None) -> tuple[str | None, int]:
    if marker is None:
        return None, 0
    masked, _ = _lexically_mask(sql)
    source_lines = sql.splitlines(keepends=True)
    masked_lines = masked.splitlines(keepends=True)
    selected = [
        source
        for source, lexical in zip(source_lines, masked_lines)
        if re.search(rf"\b{re.escape(marker)}\b", lexical, re.IGNORECASE)
    ]
    if not selected:
        raise RestorePreparationError("required migration history is missing")
    encoded = "".join(selected).encode("utf-8")
    return _sha256_bytes(encoded), len(selected)


def _replacement_spans(sql: str, database: str) -> tuple[list[tuple[int, int, str]], int]:
    """Locate only lexically proven schema and definer syntax spans."""

    masked, _ = _lexically_mask(sql)
    replacements: list[tuple[int, int, str]] = []
    schema_changes = 0
    for pattern in (_DATABASE_REFERENCE, _OBJECT_REFERENCE):
        for match in pattern.finditer(masked):
            if pattern is _DATABASE_REFERENCE:
                group = 1 if match.group(1) is not None else 2
                start, end = match.span(group)
            else:
                group = "quoted" if match.group("quoted") is not None else "bare"
                start, end = match.span(group)
            value = sql[start:end]
            if value.casefold() == database.casefold() and value != database:
                replacements.append((start, end, database))
                schema_changes += 1

    definer_matches = list(_PROVEN_DEFINER.finditer(masked))
    replacements.extend(
        (match.start(), match.end(), "DEFINER=CURRENT_USER")
        for match in definer_matches
    )
    replacements.sort(key=lambda item: item[0])
    for previous, current in zip(replacements, replacements[1:]):
        if previous[1] > current[0]:
            raise RestorePreparationError("overlapping SQL normalization spans")
    return replacements, schema_changes


def _apply_replacements(sql: str, replacements: list[tuple[int, int, str]]) -> str:
    output: list[str] = []
    position = 0
    for start, end, value in replacements:
        output.extend((sql[position:start], value))
        position = end
    output.append(sql[position:])
    return "".join(output)


def _validate_audit(report: dict, database: str, allowed_definers: int) -> None:
    references = set(report["database_names"])
    if not report["accepted_scope"] or references - {database}:
        raise RestorePreparationError("SQL references an unsafe target schema")
    counts = report["statement_counts"]
    forbidden = sorted(name for name in _FORBIDDEN_CONSTRUCTS if counts[name])
    if forbidden:
        raise RestorePreparationError(
            "SQL contains forbidden privileged constructs: " + ", ".join(forbidden)
        )
    if counts["definer"] != allowed_definers:
        raise RestorePreparationError("SQL contains an unproven DEFINER count")


def prepare_windows_748_sql(
    archive_path: str | Path,
    destination: str | Path,
    *,
    password: bytes | None = None,
    max_archive_files: int = 20_000,
    max_archive_bytes: int = 4 * 1024**3,
    max_sql_bytes: int = 256 * 1024**2,
    max_compression_ratio: int = 200,
) -> dict:
    """Audit and stage only five SQL dumps from a CMND 7.4.8 Windows backup.

    ``destination`` must not already exist.  The returned manifest contains
    hashes, sizes, counts, and mappings only; it never contains SQL or literals.
    """

    source = Path(archive_path)
    target = Path(destination)
    if target.exists():
        raise RestorePreparationError("restore destination must not already exist")
    if not zipfile.is_zipfile(source):
        raise RestorePreparationError("CMND backup must be a ZIP archive")

    prepared: dict[str, tuple[bytes, dict]] = {}
    with zipfile.ZipFile(source) as archive:
        members = archive.infolist()
        if len(members) > max_archive_files:
            raise RestorePreparationError("archive file-count limit exceeded")
        if sum(member.file_size for member in members) > max_archive_bytes:
            raise RestorePreparationError("archive expansion-size limit exceeded")

        indexed: dict[str, zipfile.ZipInfo] = {}
        folded: set[str] = set()
        for member in members:
            normalized = _archive_member(member.filename).as_posix()
            collision_key = normalized.casefold()
            if collision_key in folded:
                raise RestorePreparationError("archive member-name collision")
            folded.add(collision_key)
            mode = member.external_attr >> 16
            if stat.S_ISLNK(mode):
                raise RestorePreparationError("archive symbolic link rejected")
            if (
                member.compress_size
                and member.file_size / member.compress_size > max_compression_ratio
            ):
                raise RestorePreparationError("archive compression-ratio limit exceeded")
            indexed[normalized] = member

        metadata = indexed.get("backupinfo.txt")
        if metadata is None or metadata.file_size > 128 * 1024:
            raise RestorePreparationError("bounded backup metadata is missing")
        try:
            metadata_text = archive.read(metadata, pwd=password).decode("utf-8", "strict")
        except (RuntimeError, UnicodeError) as exc:
            raise RestorePreparationError("backup metadata cannot be decoded") from exc
        if not re.search(r"(?m)^CMND\\ version=7\.4\.8\r?$", metadata_text):
            raise RestorePreparationError("backup is not CMND version 7.4.8")

        for member_name, (windows_database, database, history) in WINDOWS_748_DATABASES.items():
            member = indexed.get(member_name)
            if member is None:
                raise RestorePreparationError("required database dump is missing")
            if member.file_size > max_sql_bytes:
                raise RestorePreparationError("database dump size limit exceeded")
            try:
                source_bytes = archive.read(member, pwd=password)
                sql = source_bytes.decode("utf-8", "strict")
            except (RuntimeError, UnicodeError) as exc:
                raise RestorePreparationError("database dump cannot be decoded") from exc
            if "\x00" in sql:
                raise RestorePreparationError("database dump contains NUL bytes")
            header = "\n".join(sql.splitlines()[:80])
            if not re.search(
                rf"(?m)^-- Host:.*\sDatabase:\s*{re.escape(windows_database)}\s*$",
                header,
            ):
                raise RestorePreparationError("database dump header does not match its member")

            source_report = audit_sql(sql, expected_database=database)
            expected_definers = _EXPECTED_DEFINERS[database]
            _validate_audit(source_report, database, expected_definers)
            replacements, schema_changes = _replacement_spans(sql, database)
            actual_definers = sum(
                1 for _, _, value in replacements if value == "DEFINER=CURRENT_USER"
            )
            if actual_definers != expected_definers:
                raise RestorePreparationError("DEFINER syntax differs from the proven backup")

            literal_hash = _literal_digest(sql)
            history_hash, history_lines = _history_digest(sql, history)
            normalized = _apply_replacements(sql, replacements)
            if _literal_digest(normalized) != literal_hash:
                raise RestorePreparationError("SQL literal preservation check failed")
            normalized_history_hash, _ = _history_digest(normalized, history)
            if normalized_history_hash != history_hash:
                raise RestorePreparationError("migration history preservation check failed")
            staged_report = audit_sql(normalized, expected_database=database)
            _validate_audit(staged_report, database, expected_definers)
            staged_bytes = normalized.encode("utf-8")
            prepared[database] = (
                staged_bytes,
                {
                    "source_member": member_name,
                    "target_database": database,
                    "staged_file": f"{database}.sql",
                    "source_bytes": len(source_bytes),
                    "staged_bytes": len(staged_bytes),
                    "source_sha256": _sha256_bytes(source_bytes),
                    "staged_sha256": _sha256_bytes(staged_bytes),
                    "schema_names_normalized": schema_changes,
                    "definers_normalized": actual_definers,
                    "string_literals_sha256": literal_hash,
                    "migration_history_sha256": history_hash,
                    "migration_history_lines": history_lines,
                    "audit_statement_counts": staged_report["statement_counts"],
                },
            )

    manifest = {
        "format": "cmnd-windows-7.4.8-sql-restore-preparation-v1",
        "vendor_version": "7.4.8",
        "archive_bytes": source.stat().st_size,
        "archive_sha256": _file_sha256(source),
        "database_execution_performed": False,
        "customer_content_extracted": False,
        "databases": {name: details for name, (_, details) in sorted(prepared.items())},
    }

    created: list[Path] = []
    try:
        target.mkdir(parents=True, mode=0o700)
        for database, (content, _) in sorted(prepared.items()):
            output = target / f"{database}.sql"
            descriptor = os.open(output, os.O_WRONLY | os.O_CREAT | os.O_EXCL, 0o600)
            created.append(output)
            with os.fdopen(descriptor, "wb") as handle:
                handle.write(content)
        manifest_path = target / "manifest.json"
        descriptor = os.open(
            manifest_path, os.O_WRONLY | os.O_CREAT | os.O_EXCL, 0o600
        )
        created.append(manifest_path)
        with os.fdopen(descriptor, "w", encoding="utf-8") as handle:
            json.dump(manifest, handle, indent=2, sort_keys=True)
            handle.write("\n")
    except Exception:
        for output in reversed(created):
            output.unlink(missing_ok=True)
        try:
            target.rmdir()
        except OSError:
            pass
        raise
    return manifest
