from __future__ import annotations

from dataclasses import dataclass
from pathlib import Path
import os
import re
import shutil

from .artifacts import safe_extract_zip


class VendorCmsError(ValueError):
    pass


@dataclass(frozen=True)
class VendorCmsStage:
    root: Path
    settings: Path
    writable_files: Path


def configure_cms_rewrite_base(root: Path) -> bool:
    """Keep Drupal's public subdirectory stable when DocumentRoot is CMS itself."""
    path = Path(root) / '.htaccess'
    if path.is_symlink() or not path.is_file() or any(p.is_symlink() for p in path.parents):
        raise VendorCmsError('SmartCMS rewrite configuration must be a regular file')
    content = path.read_text(encoding='utf-8')
    active = re.findall(r'^\s*RewriteBase\s+([^\s#]+)\s*$', content, re.MULTILINE)
    if active == ['/SmartCMS']:
        return False
    if active:
        raise VendorCmsError('Unexpected active SmartCMS RewriteBase; preserve for review')
    engine = re.compile(r'^(\s*RewriteEngine\s+on)\s*$', re.MULTILINE | re.IGNORECASE)
    if len(engine.findall(content)) != 1:
        raise VendorCmsError('Expected exactly one SmartCMS RewriteEngine')
    rendered = engine.sub(lambda m: m.group(1) + '\n  RewriteBase /SmartCMS\n', content)
    path.write_text(rendered, encoding='utf-8', newline='')
    return True


def prepare_cms_writable_directories(root: Path) -> tuple[Path, Path]:
    """Create only the vendor export/content directories, never writable code."""
    root = Path(root)
    files = root / 'sites/default/files'
    if not root.is_dir() or not files.is_dir():
        raise VendorCmsError('SmartCMS writable files root is missing')
    for parent in (files, files.parent, files.parent.parent, root):
        if parent.is_symlink():
            raise VendorCmsError('SmartCMS writable path must not traverse symbolic links')
    directories = tuple(files / name for name in ('export', 'tpvision'))
    # Validate the entire set before creating anything.
    for directory in directories:
        if directory.is_symlink() or (directory.exists() and not directory.is_dir()):
            raise VendorCmsError('SmartCMS export/content destination is not a regular directory')
    for directory in directories:
        directory.mkdir(mode=0o750, exist_ok=True)
    return directories


_DATABASE_BLOCK = re.compile(
    r"^\$databases\['default'\]\['default'\]\s*=\s*array\(\s*$"
    r".*?"
    r"^\);\s*$",
    re.MULTILINE | re.DOTALL,
)


def _php_single_quoted(value: str, field: str) -> str:
    if not isinstance(value, str) or not value:
        raise VendorCmsError(f"database {field} must be a non-empty string")
    if any(ord(character) < 0x20 for character in value):
        raise VendorCmsError(f"database {field} contains a control character")
    return "'" + value.replace("\\", "\\\\").replace("'", "\\'") + "'"


def _replace_array_value(block: str, key: str, value: str) -> str:
    pattern = re.compile(
        rf"^(?P<indent>\s*)'{re.escape(key)}'\s*=>\s*[^,\r\n]+,\s*$",
        re.MULTILINE,
    )
    matches = list(pattern.finditer(block))
    if len(matches) != 1:
        raise VendorCmsError(
            f"expected exactly one {key!r} entry in the default database block; found {len(matches)}"
        )
    match = matches[0]
    replacement = f"{match.group('indent')}'{key}' => {value},"
    return block[: match.start()] + replacement + block[match.end() :]


def render_settings(
    settings: Path,
    *,
    db_host: str,
    db_port: int,
    db_user: str,
    db_password: str,
) -> None:
    if isinstance(db_port, bool) or not isinstance(db_port, int) or not 1 <= db_port <= 65535:
        raise VendorCmsError("database port must be an integer from 1 through 65535")

    try:
        source = settings.read_text(encoding="utf-8")
    except (OSError, UnicodeError) as exc:
        raise VendorCmsError(f"cannot read SmartCMS settings: {settings}") from exc

    matches = list(_DATABASE_BLOCK.finditer(source))
    if len(matches) != 1:
        raise VendorCmsError(
            "expected exactly one default database block in SmartCMS settings; "
            f"found {len(matches)}"
        )

    match = matches[0]
    block = match.group(0)
    block = _replace_array_value(block, "driver", "'mysql'")
    block = _replace_array_value(block, "database", "'tpvision'")
    block = _replace_array_value(block, "username", _php_single_quoted(db_user, "user"))
    block = _replace_array_value(block, "password", _php_single_quoted(db_password, "password"))
    block = _replace_array_value(block, "host", _php_single_quoted(db_host, "host"))

    port_pattern = re.compile(r"^\s*'port'\s*=>", re.MULTILINE)
    port_matches = list(port_pattern.finditer(block))
    if len(port_matches) > 1:
        raise VendorCmsError("multiple port entries found in the default database block")
    if port_matches:
        block = _replace_array_value(block, "port", str(db_port))
    else:
        prefix = re.search(r"^(?P<indent>\s*)'prefix'\s*=>", block, re.MULTILINE)
        if prefix is None:
            raise VendorCmsError("default database block has no prefix entry before which to add port")
        block = block[: prefix.start()] + f"{prefix.group('indent')}'port' => {db_port},\n" + block[prefix.start() :]

    rendered = source[: match.start()] + block + source[match.end() :]
    try:
        settings.write_text(rendered, encoding="utf-8", newline="")
        if os.name == "posix":
            settings.chmod(0o600)
    except OSError as exc:
        raise VendorCmsError(f"cannot write SmartCMS settings: {settings}") from exc


def stage_smartcms(
    archive: Path,
    staging: Path,
    *,
    db_host: str,
    db_port: int,
    db_user: str,
    db_password: str,
) -> VendorCmsStage:
    archive = Path(archive)
    staging = Path(staging)
    if staging.exists():
        raise VendorCmsError("SmartCMS staging destination already exists")

    staging.mkdir(parents=True, mode=0o700)
    try:
        safe_extract_zip(archive, staging)
        children = list(staging.iterdir())
        if len(children) != 1 or children[0].name != "SmartCMS" or not children[0].is_dir():
            raise VendorCmsError("SmartCMS archive must contain exactly one top-level SmartCMS directory")

        root = children[0]
        settings = root / "sites" / "default" / "settings.php"
        files = root / "sites" / "default" / "files"
        source_htaccess = root / "htbakcess"
        destination_htaccess = root / ".htaccess"
        for required in (settings, files):
            if not required.exists():
                raise VendorCmsError(f"required SmartCMS archive member missing: {required.relative_to(root)}")
        if not files.is_dir():
            raise VendorCmsError("SmartCMS sites/default/files member is not a directory")
        if source_htaccess.exists() and destination_htaccess.exists():
            raise VendorCmsError("SmartCMS archive unexpectedly contains both htbakcess and .htaccess")
        if not source_htaccess.is_file() and not destination_htaccess.is_file():
            raise VendorCmsError("required SmartCMS archive member missing: htbakcess or .htaccess")

        render_settings(
            settings,
            db_host=db_host,
            db_port=db_port,
            db_user=db_user,
            db_password=db_password,
        )
        if source_htaccess.exists():
            source_htaccess.rename(destination_htaccess)
        configure_cms_rewrite_base(root)
        prepare_cms_writable_directories(root)
        return VendorCmsStage(root=root, settings=settings, writable_files=files)
    except Exception:
        shutil.rmtree(staging, ignore_errors=True)
        raise
