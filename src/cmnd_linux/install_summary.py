"""Local, root-only installation receipt. Never resets credentials or services."""
from __future__ import annotations

from datetime import datetime, timezone
import json
import os
from pathlib import Path
import stat

from .config import ConfigError, load_config
from .native_config import native_endpoint
from . import __version__

STATE = Path('/var/lib/cmnd-deployment')
ETC = Path('/etc/cmnd')


def require_root():
    if os.name != 'posix' or os.geteuid() != 0:
        raise ConfigError('Installation credentials are available locally to root only; use sudo cmndctl install-summary')


def trusted_read(path: Path, *, private=False) -> str:
    """Check every ancestor and open without following a final-component symlink."""
    for parent in path.parents:
        info = parent.lstat()
        if not stat.S_ISDIR(info.st_mode) or info.st_uid != 0 or info.st_mode & 0o022:
            raise ConfigError('Untrusted installation state directory: ' + str(parent))
    fd = os.open(path, os.O_RDONLY | os.O_NOFOLLOW | os.O_NONBLOCK)
    try:
        info = os.fstat(fd)
        if (not stat.S_ISREG(info.st_mode) or info.st_uid != 0 or info.st_nlink != 1
                or info.st_mode & (0o077 if private else 0o022) or info.st_size > 65536):
            raise ConfigError('Untrusted installation state file: ' + str(path))
        with os.fdopen(fd, 'r', encoding='utf-8', closefd=False) as stream:
            return stream.read(65537)
    finally:
        os.close(fd)


def read_object(path: Path, *, private=False) -> dict:
    value = json.loads(trusted_read(path, private=private))
    if not isinstance(value, dict):
        raise ConfigError('Installation state must be a JSON object: ' + str(path))
    return value


def summary() -> tuple[str, bool]:
    require_root()
    header = ['=' * 72, 'LINUX CMND INSTALLATION SUMMARY',
              'Time: ' + datetime.now(timezone.utc).isoformat(timespec='seconds'),
              'Installed tooling version: ' + __version__]
    try:
        deployment = read_object(ETC / 'deployment.json')
        qualification = read_object(STATE / 'qualification.json')
    except FileNotFoundError:
        return '\n'.join(header + ['FINAL STATUS: INCOMPLETE / NOT VERIFIED',
            'No completed managed deployment receipt. No passwords changed.',
            f'Inspect private diagnostics in {STATE}; do not delete data or force a reinstall.', '=' * 72]) + '\n', False
    complete = (deployment.get('managed_by') == 'linux-cmnd-native' and deployment.get('state') == 'active'
                and qualification.get('services_started') is True
                and qualification.get('readiness_verified') is True
                and qualification.get('services_enabled', True) is True
                and not (STATE / 'FAILED').exists() and not (STATE / 'FAILED').is_symlink())
    if not complete:
        return '\n'.join(header + ['FINAL STATUS: INCOMPLETE / NOT VERIFIED',
            'Managed deployment and readiness checks have not both passed.',
            f'Preserve {STATE} and inspect deployment.log. No passwords changed.', '=' * 72]) + '\n', False
    # Validate the on-disk configuration before load_config opens it as usual.
    trusted_read(ETC / 'cmnd.toml')
    config = load_config(ETC / 'cmnd.toml')
    host, _ = native_endpoint(config)
    def origin(port):
        return f'https://{host}' + ('' if port == 443 else f':{port}')
    apache, tomcat = origin(config.apache_https), origin(config.tomcat_https)
    try:
        credentials = read_object(STATE / 'initial-admin.json', private=True)
    except FileNotFoundError:
        credentials = None
    if credentials is not None and (credentials.get('username') != 'admin'
            or not isinstance(credentials.get('password'), str)
            or not 1 <= len(credentials['password']) <= 1024
            or not credentials['password'].isascii()
            or any(ord(c) < 33 or ord(c) > 126 for c in credentials['password'])):
        raise ConfigError('Malformed initial administrator credentials; refusing display')
    lines = header + [
        'Installation status: PASS', 'Readiness at installation: PASS',
        'This receipt records installation checks, not a new live health test.', '',
        'Linux CMND Management: ' + apache + '/linux-cmnd/',
        'Administrator username: admin',
        'Initial administrator password: ' + (credentials['password'] if credentials else 'UNAVAILABLE (credential file missing)'),
        'If you changed this password after installation, use your current password.', '',
        'TV Management / SmartInstall: ' + tomcat + '/SmartInstall/',
        'Smart Control: ' + tomcat + '/smartcontrol/',
        'User Management: ' + tomcat + '/usermanagement/',
        'SmartCMS: ' + apache + '/SmartCMS/', '',
        f'Ports: Tomcat HTTP {config.tomcat_http}, HTTPS {config.tomcat_https}; Apache HTTP {config.apache_http}, HTTPS {config.apache_https}; MySQL {config.database_port}',
        f'Configuration: {ETC}/cmnd.toml',
        f'Credential file (root only): {STATE}/initial-admin.json',
        f'Qualification report: {STATE}/qualification.json',
        f'Deployment state: {ETC}/deployment.json',
        f'Private deployment log: {STATE}/deployment.log',
        f'Private installation receipt: {STATE}/install-summary.txt',
        'Retrieve this summary: sudo cmndctl install-summary (or show-login)',
        'Check live readiness: sudo cmndctl native-health --seconds 120',
        'Check GitHub updates: sudo cmndctl --updates', '',
        f'Browser certificate warning is expected until you trust {ETC}/tls/ca.crt on your workstation.',
        'No physical TVs are authorized by a fresh installation. Legacy runtime: evaluation only.',
        'Full vendor feature parity and RF/MGate hardware are NOT verified.',
        'FINAL STATUS: PASS' if credentials else 'FINAL STATUS: INSTALLED; INITIAL CREDENTIAL UNAVAILABLE', '=' * 72]
    return '\n'.join(lines) + '\n', credentials is not None


def show_summary() -> bool:
    text, complete = summary()
    print(text, end='', flush=True)
    return complete


def save_receipt(text: str) -> None:
    """Fresh deployment only; never overwrites old evidence."""
    from .native_deploy import write_new
    write_new(STATE / 'install-summary.txt', text, 0o600)
