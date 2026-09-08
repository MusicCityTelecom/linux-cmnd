"""Static safety checks for vendor and backup SQL.

This module deliberately does not execute SQL and does not include source SQL in
its reports.  It is a preflight aid, not a complete SQL parser.
"""

from __future__ import annotations

from collections import Counter
from collections.abc import Mapping
import re


CMND_DATABASES = frozenset(
    {"cas", "tpvision", "smartcms", "smartcontroldb", "smartinstall"}
)

_DB_IDENTIFIER = r"(?:`([^`]+)`|([A-Za-z0-9_$.-]+))"
_DATABASE_REFERENCE = re.compile(
    rf"\b(?:USE|CREATE\s+(?:DATABASE|SCHEMA)(?:\s+IF\s+NOT\s+EXISTS)?|"
    rf"DROP\s+(?:DATABASE|SCHEMA)(?:\s+IF\s+EXISTS)?)\s+{_DB_IDENTIFIER}",
    re.IGNORECASE,
)
_SCHEMA_TOKEN = r"(?:`(?P<quoted>[^`]+)`|(?P<bare>[A-Za-z_$][A-Za-z0-9_$]*))"
_OBJECT_REFERENCE = re.compile(
    rf"\b(?:FROM|JOIN|UPDATE|INTO|REFERENCES|CALL|"
    rf"(?:CREATE(?:\s+OR\s+REPLACE)?|ALTER|DROP|TRUNCATE|RENAME|ANALYZE)\s+"
    rf"(?:TEMPORARY\s+)?(?:TABLE|VIEW)(?:\s+IF\s+(?:NOT\s+)?EXISTS)?|"
    rf"LOCK\s+TABLES?)\s+{_SCHEMA_TOKEN}\s*\.\s*(?:`[^`]+`|[A-Za-z_$][A-Za-z0-9_$]*|\*)",
    re.IGNORECASE,
)
_GRANT_REFERENCE = re.compile(
    rf"\bGRANT\b[^;]{{0,1000}}?\bON\s+{_SCHEMA_TOKEN}\s*\.\s*(?:`[^`]+`|[A-Za-z_$][A-Za-z0-9_$]*|\*)",
    re.IGNORECASE,
)

_CHECKS = {
    "executable_comment": re.compile(r"/\*!", re.IGNORECASE),
    "drop_database": re.compile(r"\bDROP\s+(?:DATABASE|SCHEMA)\b", re.IGNORECASE),
    "drop_table": re.compile(r"\bDROP\s+(?:TEMPORARY\s+)?TABLE\b", re.IGNORECASE),
    "drop_user": re.compile(r"\bDROP\s+USER\b", re.IGNORECASE),
    "grant": re.compile(r"\bGRANT\b", re.IGNORECASE),
    "create_user": re.compile(r"\bCREATE\s+USER\b", re.IGNORECASE),
    "definer": re.compile(r"\bDEFINER\s*=", re.IGNORECASE),
    "set_global": re.compile(r"\bSET\s+(?:@@\s*)?GLOBAL\b", re.IGNORECASE),
    "load_data_local": re.compile(r"\bLOAD\s+DATA\s+LOCAL\s+INFILE\b", re.IGNORECASE),
    "file_write": re.compile(r"\bINTO\s+(?:OUTFILE|DUMPFILE)\b", re.IGNORECASE),
    "file_read": re.compile(r"\bLOAD_FILE\s*\(", re.IGNORECASE),
    "mysql_system": re.compile(r"(?im)^\s*(?:SYSTEM\b|\\!|SOURCE\b)"),
}

# These constructs either affect the server outside a single CMND schema or
# can access the host filesystem.  DROP TABLE is intentionally reported but is
# not in this set because ordinary mysqldump restore files contain it.
PRIVILEGED_CHECKS = frozenset(
    {
        "drop_database",
        "drop_user",
        "grant",
        "create_user",
        "definer",
        "set_global",
        "load_data_local",
        "file_write",
        "file_read",
        "mysql_system",
    }
)


def _blank(text: str) -> str:
    """Replace content with whitespace while retaining line boundaries."""

    return "".join("\n" if char == "\n" else "\r" if char == "\r" else " " for char in text)


def _lexically_mask(sql: str) -> tuple[str, int]:
    """Mask literals and inert comments, preserving executable comment bodies.

    This recognizes the quoting and comment forms needed by MySQL dumps.  It is
    intentionally a conservative lexical pass, not a validation or execution
    sandbox.
    """

    output: list[str] = []
    executable_comments = 0
    index = 0
    length = len(sql)
    while index < length:
        char = sql[index]

        if char in {"'", '"'}:
            quote = char
            end = index + 1
            while end < length:
                if sql[end] == "\\":
                    end = min(length, end + 2)
                    continue
                if sql[end] == quote:
                    if end + 1 < length and sql[end + 1] == quote:
                        end += 2
                        continue
                    end += 1
                    break
                end += 1
            output.append(_blank(sql[index:end]))
            index = end
            continue

        if char == "`":
            end = index + 1
            while end < length:
                if sql[end] == "`":
                    if end + 1 < length and sql[end + 1] == "`":
                        end += 2
                        continue
                    end += 1
                    break
                end += 1
            output.append(sql[index:end])
            index = end
            continue

        if sql.startswith("/*!", index):
            close = sql.find("*/", index + 3)
            close = length if close < 0 else close
            body_start = index + 3
            while body_start < close and sql[body_start].isdigit():
                body_start += 1
            masked_body, nested = _lexically_mask(sql[body_start:close])
            output.append(_blank(sql[index:body_start]))
            output.append(masked_body)
            if close < length:
                output.append(_blank(sql[close : close + 2]))
                index = close + 2
            else:
                index = close
            executable_comments += 1 + nested
            continue

        if sql.startswith("/*", index):
            close = sql.find("*/", index + 2)
            end = length if close < 0 else close + 2
            output.append(_blank(sql[index:end]))
            index = end
            continue

        if char == "#" or (
            sql.startswith("--", index)
            and (index + 2 == length or sql[index + 2].isspace())
        ):
            end = sql.find("\n", index)
            end = length if end < 0 else end
            output.append(_blank(sql[index:end]))
            index = end
            continue

        output.append(char)
        index += 1

    return "".join(output), executable_comments


def _without_backtick_identifiers(sql: str) -> str:
    """Mask quoted identifiers before keyword counting."""

    return re.sub(r"`(?:``|[^`])*`", lambda match: _blank(match.group(0)), sql)


def audit_sql(sql: str, expected_database: str | None = None) -> dict:
    """Return a content-free structural safety report for one SQL document."""

    if not isinstance(sql, str):
        raise TypeError("sql must be text")
    if expected_database is not None:
        expected_database = expected_database.lower()
        if expected_database not in CMND_DATABASES:
            raise ValueError("expected database is not a CMND database")

    scanned, executable_comments = _lexically_mask(sql)
    keyword_text = _without_backtick_identifiers(scanned)
    counts = Counter(
        {name: len(pattern.findall(keyword_text)) for name, pattern in _CHECKS.items()}
    )
    counts["executable_comment"] = executable_comments
    database_names = {
        (match.group(1) or match.group(2)).lower()
        for match in _DATABASE_REFERENCE.finditer(scanned)
    }
    for pattern in (_OBJECT_REFERENCE, _GRANT_REFERENCE):
        database_names.update(
            (match.group("quoted") or match.group("bare")).lower()
            for match in pattern.finditer(scanned)
        )
    unknown = database_names - CMND_DATABASES
    privileged = sorted(name for name in PRIVILEGED_CHECKS if counts[name])
    # A mysqldump made for one explicitly selected database normally has no USE
    # statement or qualified table names.  Its caller must validate the dump
    # header and supplies the target mapping; absence of an internal reference is
    # therefore acceptable, while any conflicting reference is not.
    expected_seen = (
        expected_database is None
        or not database_names
        or expected_database in database_names
    )

    return {
        "database_names": sorted(database_names),
        "unknown_database_names": sorted(unknown),
        "expected_database": expected_database,
        "expected_database_seen": expected_seen,
        "statement_counts": dict(sorted(counts.items())),
        "privileged_constructs": privileged,
        "requires_privileged_review": bool(privileged),
        "has_executable_comments": bool(counts["executable_comment"]),
        "accepted_scope": not unknown and expected_seen,
    }


def audit_database_bundle(documents: Mapping[str, str]) -> dict:
    """Audit a five-database mapping and require the exact CMND database set."""

    normalized = {name.lower(): text for name, text in documents.items()}
    supplied = set(normalized)
    missing = CMND_DATABASES - supplied
    unexpected = supplied - CMND_DATABASES
    reports = {
        name: audit_sql(text, expected_database=name)
        for name, text in normalized.items()
        if name in CMND_DATABASES
    }
    return {
        "database_names": sorted(supplied),
        "missing_databases": sorted(missing),
        "unexpected_databases": sorted(unexpected),
        "databases": reports,
        "complete": not missing and not unexpected,
        "accepted_scope": (
            not missing
            and not unexpected
            and all(report["accepted_scope"] for report in reports.values())
        ),
    }
