"""Scoped integration with an already-running Debian/Ubuntu Apache service.

CMND owns exactly one conf-available fragment and never edits ports.conf,
apache2.conf, existing sites, or unrelated module configuration. The caller
must choose free CMND ports before applying this fragment.
"""
from __future__ import annotations

import hashlib
import os
from pathlib import Path
import shutil
import subprocess

from .config import Config, ConfigError
from .native_config import NativeLayout, native_endpoint

CONF_AVAILABLE = Path('/etc/apache2/conf-available/cmnd-linux.conf')
CONF_ENABLED = Path('/etc/apache2/conf-enabled/cmnd-linux.conf')
MODULES = ('alias', 'dir', 'proxy', 'proxy_fcgi', 'proxy_http', 'ssl', 'rewrite',
           'headers', 'expires', 'filter', 'deflate')


def supported() -> bool:
    return (Path('/etc/apache2/apache2.conf').is_file()
            and all(shutil.which(name) for name in ('apache2ctl', 'a2enmod', 'a2enconf', 'a2disconf', 'systemctl')))


def render(config: Config, *, layout: NativeLayout = NativeLayout(), management_port: int = 9078) -> str:
    host, _ = native_endpoint(config)
    if type(management_port) is not int or not 1024 <= management_port <= 65535:
        raise ConfigError('invalid management port for host Apache integration')
    if layout.fpm_port in {config.apache_http, config.apache_https, management_port}:
        raise ConfigError('host Apache integration port collision')
    return f'''# Managed by CMND Linux 0.8. Do not merge this file into unrelated sites.
Listen {config.bind}:{config.apache_http}
Listen {config.bind}:{config.apache_https}

<VirtualHost {config.bind}:{config.apache_http}>
    ServerName {host}
    Redirect / https://{host}:{config.apache_https}/
</VirtualHost>

<VirtualHost {config.bind}:{config.apache_https}>
    ServerName {host}
    DocumentRoot {layout.cms}
    SSLEngine on
    SSLProtocol -all +TLSv1.2 +TLSv1.3
    SSLCertificateFile {layout.certificate}
    SSLCertificateKeyFile {layout.key}
    ProxyTimeout 900
    ProxyRequests Off

    Alias /SmartCMS {layout.cms}
    <Directory {layout.cms}>
        LimitRequestBody 0
        Require all granted
        AllowOverride All
        Options FollowSymLinks
        DirectoryIndex index.php
        <FilesMatch "(?i)(^\\.|^settings\\.php$|\\.(properties|p12|pfx|pem|key|sql|bak|inc|module|install|profile|test|sh)$)">
            Require all denied
        </FilesMatch>
        <FilesMatch "\\.php$">
            SetHandler "proxy:fcgi://127.0.0.1:{layout.fpm_port}"
        </FilesMatch>
    </Directory>

    <Directory {layout.cms}/sites/default/files>
        AllowOverride None
        Options -ExecCGI -Indexes
        Require all granted
        <FilesMatch "(?i)\\.(php[0-9]?|phtml|phar)$">
            Require all denied
        </FilesMatch>
    </Directory>

    ProxyPass /linux-cmnd/ http://127.0.0.1:{management_port}/ connectiontimeout=5 timeout=30
    ProxyPassReverse /linux-cmnd/ http://127.0.0.1:{management_port}/
    <Location /linux-cmnd/>
        Require all granted
    </Location>
</VirtualHost>
'''


def _run(args: list[str], *, timeout: int = 60) -> subprocess.CompletedProcess[str]:
    result = subprocess.run(args, capture_output=True, text=True, timeout=timeout, check=False)
    if result.returncode:
        raise RuntimeError('host Apache command failed: ' + Path(args[0]).name + '; inspect journal/configtest output locally')
    return result


def apply(config: Config, *, execute: bool = False) -> dict:
    content = render(config)
    if not supported():
        raise ConfigError('existing Apache is not a supported Debian/Ubuntu apache2 layout')
    if CONF_AVAILABLE.exists() or CONF_AVAILABLE.is_symlink() or CONF_ENABLED.exists() or CONF_ENABLED.is_symlink():
        raise ConfigError('CMND host Apache fragment already exists; refusing overwrite')
    enabled_before = {module: (Path('/etc/apache2/mods-enabled') / f'{module}.load').exists() for module in MODULES}
    report = {'executed': execute, 'configuration': str(CONF_AVAILABLE),
              'sha256': hashlib.sha256(content.encode()).hexdigest(),
              'modules_already_enabled': sorted(name for name, enabled in enabled_before.items() if enabled),
              'host_apache_reloaded': False}
    if not execute:
        return report

    fd = os.open(CONF_AVAILABLE, os.O_CREAT | os.O_EXCL | os.O_WRONLY, 0o644)
    enabled_by_us: list[str] = []
    try:
        with os.fdopen(fd, 'w', encoding='utf-8', newline='\n') as handle:
            handle.write(content)
        for module, already in enabled_before.items():
            if not already:
                _run([shutil.which('a2enmod') or 'a2enmod', module])
                enabled_by_us.append(module)
        _run([shutil.which('a2enconf') or 'a2enconf', 'cmnd-linux'])
        _run([shutil.which('apache2ctl') or 'apache2ctl', 'configtest'])
        _run([shutil.which('systemctl') or 'systemctl', 'reload', 'apache2.service'])
        report['host_apache_reloaded'] = True
        report['modules_enabled_by_cmnd'] = enabled_by_us
        return report
    except Exception:
        subprocess.run([shutil.which('a2disconf') or 'a2disconf', 'cmnd-linux'], capture_output=True, timeout=20)
        if CONF_AVAILABLE.is_file() and not CONF_AVAILABLE.is_symlink():
            try:
                if CONF_AVAILABLE.read_text(encoding='utf-8', errors='replace').startswith('# Managed by CMND Linux 0.8.'):
                    CONF_AVAILABLE.unlink()
            except OSError:
                pass
        subprocess.run([shutil.which('apache2ctl') or 'apache2ctl', 'configtest'], capture_output=True, timeout=20)
        subprocess.run([shutil.which('systemctl') or 'systemctl', 'reload', 'apache2.service'], capture_output=True, timeout=30)
        raise


def detach(*, execute: bool = False) -> dict:
    if not CONF_AVAILABLE.exists() and not CONF_ENABLED.exists():
        return {'executed': execute, 'present': False}
    if CONF_AVAILABLE.is_symlink():
        raise ConfigError('unexpected symlink at CMND Apache source fragment')
    if CONF_AVAILABLE.exists():
        text = CONF_AVAILABLE.read_text(encoding='utf-8', errors='replace')
        if not text.startswith('# Managed by CMND Linux 0.8.'):
            raise ConfigError('existing Apache fragment is not recognized as CMND-owned')
    report = {'executed': execute, 'present': True, 'configuration': str(CONF_AVAILABLE)}
    if not execute:
        return report
    subprocess.run([shutil.which('a2disconf') or 'a2disconf', 'cmnd-linux'], capture_output=True, timeout=20)
    _run([shutil.which('apache2ctl') or 'apache2ctl', 'configtest'])
    _run([shutil.which('systemctl') or 'systemctl', 'reload', 'apache2.service'])
    if CONF_AVAILABLE.exists():
        CONF_AVAILABLE.unlink()
    return report | {'detached': True}
