"""Stage the native web-service configuration; never start or alter services.

The installer and an operator can consume the same rendered files. This is not
a claim that database bootstrap, certificate installation, or service lifecycle
has completed. All paths are target-Linux paths, even when staged on Windows.
"""
from __future__ import annotations

from dataclasses import dataclass
import hashlib
import ipaddress
import json
import os
from pathlib import Path, PurePosixPath
import re
from urllib.parse import urlsplit
import xml.etree.ElementTree as ET

from .config import Config, ConfigError
from .cms_runtime import CmsRuntimeConfig, generate_cms_runtime_script
from .vendor_config import VendorRenderConfig


@dataclass(frozen=True)
class NativeLayout:
    tomcat: str = '/opt/cmnd/tomcat'
    cms: str = '/opt/cmnd/SmartCMS'
    configuration: str = '/etc/cmnd'
    log: str = '/var/log/cmnd'
    run: str = '/run/cmnd'
    certificate: str = '/etc/cmnd/tls/server.crt'
    key: str = '/etc/cmnd/tls/server.key'
    ca_bundle: str = '/etc/ssl/certs/ca-certificates.crt'
    pgt: str = '/var/lib/cmnd/cas-pgt'
    php_uploads: str = '/var/lib/cmnd/php-uploads'
    php_user: str = 'cmnd-cms'
    fpm_port: int = 9000

    def validate(self) -> None:
        for name in ('tomcat', 'cms', 'configuration', 'log', 'run', 'certificate', 'key', 'ca_bundle', 'pgt', 'php_uploads'):
            value = getattr(self, name)
            if (not re.fullmatch(r'/[A-Za-z0-9_./-]+', value)
                    or value != str(PurePosixPath(value))
                    or '..' in PurePosixPath(value).parts or value == '/'):
                raise ConfigError(f'{name} must be a normalized absolute Linux path without shell syntax')
        if PurePosixPath(self.php_uploads) == PurePosixPath(self.cms) or PurePosixPath(self.cms) in PurePosixPath(self.php_uploads).parents:
            raise ConfigError('PHP temporary uploads must be outside the public CMS root')
        if not re.fullmatch(r'[a-z_][a-z0-9_-]{0,31}', self.php_user):
            raise ConfigError('invalid PHP service account')
        if type(self.fpm_port) is not int or not 1024 <= self.fpm_port <= 65535:
            raise ConfigError('PHP-FPM port must be an unprivileged integer port')


def native_endpoint(config: Config) -> tuple[str, dict[str, int]]:
    """Enforce a single public authority and the port used in TV callbacks."""
    parsed = urlsplit(config.callback_base_url)
    host = parsed.hostname
    if (parsed.scheme not in {'http', 'https'} or not host or parsed.username is not None
            or parsed.password is not None or parsed.path not in ('', '/')
            or parsed.query or parsed.fragment or ':' in host):
        raise ConfigError('native runtime requires an HTTP(S) IPv4/DNS origin, without credentials or a path')
    if not re.fullmatch(r'[A-Za-z0-9](?:[A-Za-z0-9.-]*[A-Za-z0-9])?', host):
        raise ConfigError('invalid public hostname')
    if ipaddress.ip_address(config.bind).version != 4:
        raise ConfigError('native vendor IPv6 deployment is not yet qualified')
    if config.mode == 'isolated' and not ipaddress.ip_address(config.bind).is_loopback:
        raise ConfigError('isolated runtime must bind to loopback')
    ports = dict(tomcat_http=config.tomcat_http, tomcat_https=config.tomcat_https,
                 apache_http=config.apache_http, apache_https=config.apache_https,
                 database=config.database_port)
    if any(type(value) is not int or not 1 <= value <= 65535 for value in ports.values()):
        raise ConfigError('native listener ports must be integers in 1..65535')
    if len(set(ports.values())) != len(ports):
        raise ConfigError('native listener ports must be distinct')
    expected = config.tomcat_https if parsed.scheme == 'https' else config.tomcat_http
    actual = parsed.port or (443 if parsed.scheme == 'https' else 80)
    if actual != expected:
        raise ConfigError('callback_base_url port must match the configured Tomcat HTTP(S) port')
    return host, ports


def render_native_files(config: Config, secrets: dict[str, str], layout: NativeLayout = NativeLayout()) -> dict[str, str]:
    layout.validate()
    for value, lower, upper in ((config.cms_upload_limit_mb, 1, 8096), (config.cms_memory_limit_mb, 64, 256),
                                (config.cms_execution_timeout_seconds, 0, 86400)):
        if type(value) is not int or not lower <= value <= upper:
            raise ConfigError('invalid CMS upload/memory/timeout limit')
    host, ports = native_endpoint(config)
    VendorRenderConfig(host, ports, secrets).validate()
    if layout.fpm_port in ports.values():
        raise ConfigError('PHP-FPM port conflicts with a CMND listener')
    root = ET.Element('Server', port='-1')
    service = ET.SubElement(root, 'Service', name='Catalina')
    ET.SubElement(service, 'Connector', address=config.bind, port=str(config.tomcat_http),
                  protocol='HTTP/1.1', connectionTimeout='20000', redirectPort=str(config.tomcat_https), maxSwallowSize='-1')
    connector = ET.SubElement(service, 'Connector', address=config.bind, port=str(config.tomcat_https),
                              protocol='com.tpv.smartinstall.util.ReloadProtocol', SSLEnabled='true', maxThreads='150')
    ssl = ET.SubElement(connector, 'SSLHostConfig', protocols='TLSv1.2,TLSv1.3')
    ET.SubElement(ssl, 'Certificate', certificateKeystoreFile=f'{layout.tomcat}/server.p12',
                  certificateKeystorePassword=secrets['cert_ca_password'], certificateKeystoreType='PKCS12',
                  certificateKeyAlias='tomcat')
    engine = ET.SubElement(service, 'Engine', name='Catalina', defaultHost='localhost')
    ET.SubElement(engine, 'Host', name='localhost', appBase='webapps', unpackWARs='true', autoDeploy='false')
    ET.indent(root)
    server_xml = ET.tostring(root, encoding='unicode', xml_declaration=True) + '\n'
    context = '<Context swallowOutput="true"><Valve className="org.apache.catalina.valves.rewrite.RewriteValve"/></Context>\n'
    rewrite = (f'RewriteRule ^/$ https://{host}:{config.apache_https}/linux-cmnd/ [R=302,L]\n'
               'RewriteRule ^/webservices.jsp /SmartInstall/webservices.jsp [L]\n'
               'RewriteRule ^/(.*).jsp /SmartInstall/$1.jsp [L]\n')
    # Standalone Apache configuration: never include another application's
    # sites-enabled or change the distribution-wide ports.conf.
    modules = ('mpm_event', 'authz_core', 'authz_host', 'access_compat', 'dir', 'mime',
               'alias', 'rewrite', 'headers', 'expires', 'filter', 'deflate', 'proxy',
               'proxy_fcgi', 'proxy_http', 'ssl', 'socache_shmcb', 'unixd')
    apache = '\n'.join(f'LoadModule {name}_module /usr/lib/apache2/modules/mod_{name}.so'
                       for name in modules if name != 'unixd') + '\n'
    apache += f'''ServerRoot /etc/cmnd
ProxyTimeout 900
ServerName {host}
DefaultRuntimeDir {layout.run}
PidFile {layout.run}/apache.pid
User {layout.php_user}
Group {layout.php_user}
ErrorLog {layout.log}/apache-error.log
LogLevel warn
ServerTokens Prod
ServerSignature Off
TraceEnable Off
TypesConfig /etc/mime.types
Listen {config.bind}:{config.apache_http}
Listen {config.bind}:{config.apache_https}
SSLProtocol -all +TLSv1.2 +TLSv1.3
<Directory />
    AllowOverride None
    Require all denied
</Directory>
'''
    for port in (config.apache_http, config.apache_https):
        apache += f'''<VirtualHost {config.bind}:{port}>
ServerName {host}
DocumentRoot {layout.cms}
Alias /SmartCMS {layout.cms}
<Directory {layout.cms}>
    # PHP enforces the configured upload/post ceiling; avoid Apache's lower default.
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
    <FilesMatch "(?i)\\.(php[0-9]?|phtml|phar)$">
        Require all denied
    </FilesMatch>
</Directory>
'''
        if port == config.apache_https:
            apache += f'SSLEngine on\nSSLCertificateFile {layout.certificate}\nSSLCertificateKeyFile {layout.key}\n'
            apache += ('ProxyRequests Off\nProxyPass /linux-cmnd/ http://127.0.0.1:9078/ connectiontimeout=5 timeout=30\n'
                       'ProxyPassReverse /linux-cmnd/ http://127.0.0.1:9078/\n<Location /linux-cmnd/>\nRequire all granted\n</Location>\n')
        else:
            apache += f'Redirect /linux-cmnd/ https://{host}:{config.apache_https}/linux-cmnd/\n'
        apache += '</VirtualHost>\n'
    fpm = f'''[global]
daemonize = no
error_log = /proc/self/fd/2
[www]
listen = 127.0.0.1:{layout.fpm_port}
listen.allowed_clients = 127.0.0.1
user = {layout.php_user}
group = {layout.php_user}
pm = dynamic
pm.max_children = 2
pm.start_servers = 2
pm.min_spare_servers = 1
pm.max_spare_servers = 2
catch_workers_output = yes
clear_env = yes
security.limit_extensions = .php
php_admin_flag[display_errors] = off
php_admin_flag[log_errors] = on
php_admin_value[error_log] = /proc/self/fd/2
; Original Windows upload ceiling, with disk-backed temporary storage.
php_admin_value[upload_max_filesize] = {config.cms_upload_limit_mb}M
php_admin_value[post_max_size] = {config.cms_upload_limit_mb}M
php_admin_value[upload_tmp_dir] = {layout.php_uploads}
php_admin_value[sys_temp_dir] = {layout.php_uploads}
; Two bounded workers leave room for media helpers inside the 1 GiB container.
php_admin_value[memory_limit] = {config.cms_memory_limit_mb}M
php_admin_value[max_execution_time] = {config.cms_execution_timeout_seconds}
env[PATH] = /usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin
'''
    return {'server.xml': server_xml, 'context.xml': context, 'ROOT/WEB-INF/rewrite.config': rewrite,
            'apache.conf': apache, 'php-fpm.conf': fpm,
            'configure-cms.php': generate_cms_runtime_script(CmsRuntimeConfig(
                layout.cms, layout.ca_bundle, layout.pgt, host, config.apache_https, True))}


def stage_native_config(config: Config, secrets: dict[str, str], output: Path, *,
                        execute: bool = False, layout: NativeLayout = NativeLayout()) -> dict:
    files = render_native_files(config, secrets, layout)  # Validate before any write.
    report = {'files': sorted(files), 'executed': execute, 'services_started': False,
              'certificate_installation_performed': False, 'database_initialization_performed': False}
    if not execute:
        return report
    # Never overwrite an active configuration or follow a pre-existing stage.
    output = Path(output)
    if any(parent.is_symlink() for parent in (output, *output.parents)):
        raise ConfigError('configuration staging path must not traverse a symbolic link')
    output.mkdir(mode=0o700, parents=False, exist_ok=False)
    for name, content in files.items():
        path = output / name
        path.parent.mkdir(mode=0o700, parents=True, exist_ok=True)
        with os.fdopen(os.open(path, os.O_CREAT | os.O_EXCL | os.O_WRONLY, 0o600),
                       'w', encoding='utf-8', newline='\n') as handle:
            handle.write(content)
    report['sha256'] = {name: hashlib.sha256(content.encode()).hexdigest() for name, content in files.items()}
    with os.fdopen(os.open(output / 'manifest.json', os.O_CREAT | os.O_EXCL | os.O_WRONLY, 0o600),
                   'w', encoding='utf-8', newline='\n') as handle:
        json.dump(report, handle, indent=2)
        handle.write('\n')
    return report
