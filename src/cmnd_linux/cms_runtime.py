from __future__ import annotations

from dataclasses import dataclass
import os
from pathlib import Path, PurePosixPath
import re


class CmsRuntimeError(ValueError):
    pass


@dataclass(frozen=True)
class CmsRuntimeConfig:
    cms_root: str | Path
    ca_bundle: str | Path
    pgt_storage: str | Path
    public_host: str
    public_port: int = 8444
    https: bool = True


def _linux_path(value: str | Path, field: str) -> str:
    text = str(value)
    path = PurePosixPath(text)
    if not text.startswith("/") or any(part in {"", ".", ".."} for part in path.parts[1:]):
        raise CmsRuntimeError(f"{field} must be a normalized absolute Linux path")
    if any(ord(character) < 0x20 for character in text):
        raise CmsRuntimeError(f"{field} contains a control character")
    return str(path)


def _php_string(value: str) -> str:
    """Encode a literal without PHP interpolation or executable expressions."""
    return "'" + value.replace("\\", "\\\\").replace("'", "\\'") + "'"


def _validate(config: CmsRuntimeConfig) -> tuple[str, str, str]:
    root = _linux_path(config.cms_root, "SmartCMS root")
    ca_bundle = _linux_path(config.ca_bundle, "CAS CA bundle")
    pgt_storage = _linux_path(config.pgt_storage, "CAS PGT storage")
    root_path, pgt_path = PurePosixPath(root), PurePosixPath(pgt_storage)
    if pgt_path == root_path or root_path in pgt_path.parents:
        raise CmsRuntimeError("CAS PGT storage must be outside the SmartCMS document root")
    if isinstance(config.public_port, bool) or not isinstance(config.public_port, int):
        raise CmsRuntimeError("public port must be an integer")
    if not 1 <= config.public_port <= 65535:
        raise CmsRuntimeError("public port must be from 1 through 65535")
    if not isinstance(config.public_host, str) or not re.fullmatch(r"[A-Za-z0-9.:[\]-]+", config.public_host):
        raise CmsRuntimeError("public host must be a hostname or IP literal")
    return root, ca_bundle, pgt_storage


def generate_cms_runtime_script(config: CmsRuntimeConfig) -> str:
    """Generate a CLI-only Drupal bootstrap that applies proven CAS path fixes."""
    root, ca_bundle, pgt_storage = _validate(config)
    scheme = "https" if config.https else "http"
    host_header = config.public_host
    if ":" in host_header and not host_header.startswith("["):
        host_header = f"[{host_header}]"
    host_header = f"{host_header}:{config.public_port}"

    values = {
        "root": _php_string(root),
        "ca": _php_string(ca_bundle),
        "pgt": _php_string(pgt_storage),
        "host": _php_string(config.public_host),
        "host_header": _php_string(host_header),
        "port": _php_string(str(config.public_port)),
        "https": _php_string("on" if config.https else "off"),
        "scheme": _php_string(scheme),
    }
    return f"""<?php
// Generated CMND SmartCMS runtime configuration. Execute as the same OS user
// as PHP-FPM so the PGT writability check represents the web runtime.
if (PHP_SAPI !== 'cli') {{
  fwrite(STDERR, "This bootstrap is CLI-only.\\n");
  exit(2);
}}
if (getenv('CMND_CMS_EXECUTE') !== '1') {{
  fwrite(STDERR, "Set CMND_CMS_EXECUTE=1 to apply reviewed CMS variables.\\n");
  exit(2);
}}
$cmnd_root = {values['root']};
$cmnd_ca = {values['ca']};
$cmnd_pgt = {values['pgt']};

// Complete every filesystem and identity-neutral safety check before Drupal
// is bootstrapped or any database value can be changed.
if ($cmnd_root === '' || $cmnd_root[0] !== '/' || realpath($cmnd_root) === false) {{
  fwrite(STDERR, "Unknown SmartCMS root.\\n");
  exit(3);
}}
$cmnd_root = realpath($cmnd_root);
$cmnd_required = array(
  $cmnd_root . '/includes/bootstrap.inc',
  $cmnd_root . '/sites/default/settings.php',
  $cmnd_root . '/sites/all/libraries/CAS/CAS.php',
);
foreach ($cmnd_required as $cmnd_file) {{
  if (!is_file($cmnd_file) || !is_readable($cmnd_file)) {{
    fwrite(STDERR, "Unknown or incomplete SmartCMS root.\\n");
    exit(3);
  }}
}}
if ($cmnd_ca === '' || $cmnd_ca[0] !== '/' || realpath($cmnd_ca) === false || !is_file($cmnd_ca) || !is_readable($cmnd_ca)) {{
  fwrite(STDERR, "CAS CA bundle is missing or unreadable.\\n");
  exit(3);
}}
if ($cmnd_pgt === '' || $cmnd_pgt[0] !== '/' || realpath($cmnd_pgt) === false || !is_dir($cmnd_pgt) || !is_writable($cmnd_pgt)) {{
  fwrite(STDERR, "CAS PGT storage is missing or not writable by the PHP runtime user.\\n");
  exit(3);
}}
$cmnd_ca = realpath($cmnd_ca);
$cmnd_pgt = realpath($cmnd_pgt);
if ($cmnd_pgt === $cmnd_root || strpos($cmnd_pgt, $cmnd_root . '/') === 0) {{
  fwrite(STDERR, "CAS PGT storage must be outside the SmartCMS document root.\\n");
  exit(3);
}}
$cmnd_pgt_permissions = fileperms($cmnd_pgt);
if ($cmnd_pgt_permissions === false || ($cmnd_pgt_permissions & 0077) !== 0) {{
  fwrite(STDERR, "CAS PGT storage must deny all group and other permissions.\\n");
  exit(3);
}}

chdir($cmnd_root);
define('DRUPAL_ROOT', $cmnd_root);
$_SERVER['HTTP_HOST'] = {values['host_header']};
$_SERVER['SERVER_NAME'] = {values['host']};
$_SERVER['SERVER_PORT'] = {values['port']};
$_SERVER['REMOTE_ADDR'] = '127.0.0.1';
$_SERVER['HTTPS'] = {values['https']};
$_SERVER['REQUEST_SCHEME'] = {values['scheme']};
$_SERVER['REQUEST_METHOD'] = 'GET';
$_SERVER['REQUEST_URI'] = '/SmartCMS/';
$_SERVER['SCRIPT_NAME'] = '/SmartCMS/index.php';
require_once DRUPAL_ROOT . '/includes/bootstrap.inc';
drupal_bootstrap(DRUPAL_BOOTSTRAP_VARIABLES);

// variable_set performs Drupal-native PHP serialization and cache invalidation.
// A non-empty readable CA bundle keeps CAS peer validation enabled.
variable_set('cas_cert', $cmnd_ca);
variable_set('cas_debugfile', '');
variable_set('cas_pgtpath', $cmnd_pgt);
fwrite(STDOUT, "SmartCMS CAS runtime paths configured with TLS validation enabled.\\n");
"""


def write_cms_runtime_script(destination: str | Path, config: CmsRuntimeConfig) -> Path:
    destination = Path(destination)
    script = generate_cms_runtime_script(config)
    destination.parent.mkdir(parents=True, exist_ok=True)
    flags = os.O_WRONLY | os.O_CREAT | os.O_EXCL
    try:
        descriptor = os.open(destination, flags, 0o700)
    except FileExistsError as exc:
        raise CmsRuntimeError("CMS runtime script destination already exists") from exc
    try:
        with os.fdopen(descriptor, "w", encoding="utf-8", newline="\n") as output:
            output.write(script)
    except Exception:
        destination.unlink(missing_ok=True)
        raise
    return destination
