"""Fresh native-service deployment of the locally supplied Philips application.

No existing CMND data is adopted or overwritten. Legacy PHP/MySQL fidelity is
explicitly acknowledged. Vendor files and secrets stay outside the source repo.
"""
from __future__ import annotations

from dataclasses import dataclass
import hashlib
import ipaddress
import json
import os
from pathlib import Path
import platform
import re
import secrets
import shutil
import socket
import ssl
import subprocess
import time
from urllib.error import HTTPError, URLError
from urllib.parse import urlsplit
from urllib.request import HTTPSHandler, HTTPRedirectHandler, ProxyHandler, build_opener

from .application_stage import ApplicationInputs, VENDOR_INPUT_HASHES, _tomcat_members, stage_application
from .artifacts import file_hash
from .certificates import CertificateConfig, provision_certificates
from .config import Config, ConfigError, load_config
from .egress import EgressPolicy, apply_policy
from .native_config import NativeLayout, native_endpoint, render_native_files
from .vendor_config import REQUIRED_SECRETS

MYSQL_IMAGE = 'mysql@sha256:4bc6bc963e6d8443453676cae56536f4b8156d78bae03c0145cbe47c2aad73bb'
STATE = Path('/var/lib/cmnd-deployment')
ETC = Path('/etc/cmnd')
LAYOUT = NativeLayout()
MANAGEMENT = Path('/etc/linux-cmnd-management')
BASELINE = Path('/var/cache/linux-cmnd/bootstrap/initial-installer.deb')
SCHEMAS = (('smartinstall', 'siuser', 'smartinstall', (2, 3)), ('cas', 'cas', 'cas', (2,)),
           ('tpvision', 'tpvision', 'tpvision', tuple(range(2, 14))),
           ('smartcontroldb', 'smartcontrol', 'smartcontrol', (2,)), ('smartcms', 'smartcms', 'smartcms', (2, 3)))


@dataclass(frozen=True)
class DeploymentInputs:
    config: Path
    vendor: Path
    tomcat_archive: Path
    php_image: str
    java_home: Path


def validate_image(value: str) -> None:
    if not re.fullmatch(r'(?:[a-z0-9./_-]+@)?sha256:[0-9a-f]{64}', value):
        raise ConfigError('PHP image must be an immutable image ID or repository digest, never a mutable tag')


def write_new(path: Path, content: str | bytes, mode: int = 0o600) -> None:
    if any(parent.is_symlink() for parent in (path, *path.parents)):
        raise ConfigError('deployment path must not traverse a symlink: ' + str(path))
    path.parent.mkdir(parents=True, exist_ok=True)
    data = content.encode() if isinstance(content, str) else content
    with os.fdopen(os.open(path, os.O_CREAT | os.O_EXCL | os.O_WRONLY, mode), 'wb') as handle:
        # The deployment's restrictive umask protects intermediate files, but
        # explicitly group-readable FPM configuration and public CA files must
        # retain their requested final permissions.
        if os.name == 'posix':
            os.fchmod(handle.fileno(), mode)
        handle.write(data)


def run(*args, data: bytes | None = None, timeout: int = 180) -> bytes:
    result = subprocess.run(list(map(str, args)), input=data, capture_output=True, timeout=timeout)
    if result.returncode:
        if STATE.is_dir():
            with (STATE / 'deployment.log').open('ab') as log:
                log.write(result.stdout + result.stderr)
        raise RuntimeError('deployment subprocess failed: ' + str(args[0]) + '; inspect private deployment.log')
    return result.stdout


def os_support(content: str, machine: str) -> tuple[str, str]:
    values = {}
    for line in content.splitlines():
        if '=' in line and not line.startswith('#'):
            name, value = line.split('=', 1)
            values[name] = value.strip('"\'')
    selected = values.get('ID', ''), values.get('VERSION_ID', '')
    if selected not in {('ubuntu', '24.04'), ('debian', '12'), ('debian', '13')}:
        raise ConfigError('native deployment supports Ubuntu24.04 or Debian12/13 only')
    if machine not in {'x86_64', 'amd64'}:
        raise ConfigError('native deployment requires amd64')
    return selected


def https_origin(host: str, port: int) -> str:
    return f'https://{host}' + (f':{port}' if port != 443 else '')


def validate_baseline(path: Path = BASELINE) -> None:
    from . import __version__
    if not path.is_file() or any(p.is_symlink() for p in (path, *path.parents)):
        raise ConfigError('retained installer required; run cmnd-install with --package DEB')
    for field, expected in (('Package', 'linux-cmnd'), ('Architecture', 'amd64'), ('Version', __version__)):
        result = subprocess.run(['dpkg-deb', '-f', str(path), field], capture_output=True)
        if result.returncode or result.stdout.decode().strip() != expected:
            raise ConfigError('retained installer does not match this tooling version')


def preflight(inputs: DeploymentInputs, config: Config) -> dict:
    native_endpoint(config)
    render_native_files(config, {key: 'preflight-placeholder' for key in REQUIRED_SECRETS})
    validate_image(inputs.php_image)
    if os.name != 'posix' or not Path('/etc/os-release').is_file():
        raise ConfigError('native deployment runs on the target Linux host')
    distro = os_support(Path('/etc/os-release').read_text(), platform.machine())
    validate_baseline()
    for name, expected in VENDOR_INPUT_HASHES.items():
        path = inputs.vendor / name
        if not path.is_file() or path.is_symlink() or file_hash(path) != expected:
            raise ConfigError('unrecognized or missing pinned vendor input: ' + name)
    _tomcat_members(inputs.tomcat_archive)
    if not re.fullmatch(r'/[A-Za-z0-9_./-]+', str(inputs.java_home)) or '..' in inputs.java_home.parts:
        raise ConfigError('JAVA_HOME must be a safe absolute Linux path')
    for command in ('docker', 'apache2', 'openssl', 'systemctl', 'systemd-analyze', 'iptables', 'ip6tables', 'ip', 'update-ca-certificates', 'useradd'):
        if not shutil.which(command):
            raise ConfigError('missing deployment dependency: ' + command)
    java = subprocess.run([str(inputs.java_home / 'bin/java'), '-version'], capture_output=True)
    if java.returncode or not re.search(rb'version "17\.', java.stdout + java.stderr):
        raise ConfigError('the supplied runtime must be Java17')
    for target in (STATE, MANAGEMENT, Path('/var/lib/cmnd-updates'), Path('/var/lib/cmnd-update-requests'),
                   Path(LAYOUT.tomcat), Path(LAYOUT.cms), Path(LAYOUT.php_uploads), Path('/opt/Philips'), ETC / 'deployment.json',
                   ETC / 'tls', ETC / 'apache.conf', ETC / 'php-fpm.conf', ETC / 'native-tomcat.env',
                   ETC / 'java-cacerts', ETC / 'configure-cms.php', ETC / 'egress.json', ETC / 'egress.py',
                   Path('/usr/local/share/ca-certificates/linux-cmnd.crt')):
        if target.exists() or any(p.is_symlink() for p in (target, *target.parents)):
            raise ConfigError('fresh deployment refuses existing/symlink state: ' + str(target))
    for name in ('cmnd-native-mysql', 'cmnd-native-php'):
        if subprocess.run(['docker', 'inspect', name], capture_output=True).returncode == 0:
            raise ConfigError('existing deployment container must be preserved: ' + name)
    for name in service_units(inputs.java_home):
        if (Path('/etc/systemd/system') / name).exists():
            raise ConfigError('existing service definition must be preserved: ' + name)
    ports = (config.tomcat_http, config.tomcat_https, config.apache_http, config.apache_https, config.database_port, LAYOUT.fpm_port, 9078)
    if len(set(ports)) != len(ports):
        raise ConfigError('CMND ports conflict with the private FPM/management listeners')
    for port in ports:
        with socket.socket() as probe:
            try:
                probe.bind(('0.0.0.0', port))
            except OSError as error:
                raise ConfigError('configured port is already occupied: ' + str(port)) from error
    if shutil.disk_usage('/var/lib').free < 10 * 1024**3:
        raise ConfigError('at least10GiB free deployment space is required')
    upload_reserve = 10 * 1024**3 + 2 * config.cms_upload_limit_mb * 1024**2
    if shutil.disk_usage('/var/lib').free < upload_reserve:
        raise ConfigError('insufficient disk for deployment plus two configured CMS uploads; reduce cms.upload_limit_mb or add disk')
    memory = re.search(r'^MemTotal:\s+(\d+)', Path('/proc/meminfo').read_text(), re.M)
    if not memory or int(memory.group(1)) < 5_500_000:
        raise ConfigError('at least 6GiB installed RAM is required for the evaluation runtime')
    for image in (MYSQL_IMAGE, inputs.php_image):
        run('docker', 'image', 'inspect', image)
    # The dependency probe has no application/data mounts or network access.
    probe = ('if(PHP_VERSION!=="5.6.40"){exit(2);} '
             'foreach(array("pdo_mysql","mysqli","gd","zip","xmlrpc","pcntl","curl") as $m)'
             '{if(!extension_loaded($m)){exit(3);}} '
             'foreach(array("/usr/local/bin/cutycapt.sh","/usr/bin/cutycapt","/usr/bin/xvfb-run","/usr/bin/ffmpeg","/usr/bin/ffprobe","/usr/bin/convert") as $p)'
             '{if(!is_executable($p)){exit(4);}}')
    run('docker', 'run', '--rm', '--network', 'none', '--read-only', '--cap-drop', 'ALL',
        '--security-opt', 'no-new-privileges', '--user', '65534:65534', '--memory', '256m',
        '--pids-limit', '32', inputs.php_image, 'php', '-r', probe)
    return {'os': list(distro), 'vendor_inputs_verified': len(VENDOR_INPUT_HASHES),
            'ports_available': True, 'existing_application_adopted': False}


def service_units(java_home: Path) -> dict[str, str]:
    """Standalone services do not change the host Apache sites or ports.conf."""
    common = 'Requires=cmnd-egress.service docker.service\nAfter=cmnd-egress.service docker.service\n'
    units = {'cmnd-egress.service': '''[Unit]
Description=CMND service-identity IPv4 and IPv6 isolation
After=local-fs.target
Before=cmnd-mysql.service cmnd-php.service cmnd-apache.service cmnd-tomcat.service
[Service]
Type=oneshot
RemainAfterExit=yes
Environment=PATH=/usr/sbin:/usr/bin:/sbin:/bin
ExecStart=/usr/bin/python3 /etc/cmnd/egress.py --policy /etc/cmnd/egress.json --execute
RuntimeDirectory=cmnd
RuntimeDirectoryMode=0755
NoNewPrivileges=true
ProtectHome=true
ProtectSystem=strict
ReadWritePaths=/run
CapabilityBoundingSet=CAP_NET_ADMIN CAP_NET_RAW
[Install]
WantedBy=multi-user.target
'''}
    for service, container in (('mysql', 'cmnd-native-mysql'), ('php', 'cmnd-native-php')):
        extra = 'Requires=cmnd-mysql.service\nAfter=cmnd-mysql.service\n' if service == 'php' else ''
        ready = 'ExecStartPre=/usr/bin/cmndctl native-wait-database\n' if service == 'php' else ''
        units['cmnd-' + service + '.service'] = f'''[Unit]
Description=CMND isolated legacy {service} runtime
{common}{extra}[Service]
Type=simple
{ready}ExecStartPre=/usr/bin/python3 /etc/cmnd/egress.py --policy /etc/cmnd/egress.json --execute
ExecStart=/usr/bin/docker start -a {container}
ExecStop=/usr/bin/docker stop -t 30 {container}
Restart=on-failure
RestartSec=5
TimeoutStartSec=180
TimeoutStopSec=45
[Install]
WantedBy=multi-user.target
'''
    units['cmnd-apache.service'] = '''[Unit]
Description=CMND standalone Apache frontend
Requires=cmnd-egress.service cmnd-php.service
After=cmnd-egress.service cmnd-php.service
[Service]
Type=simple
ExecStart=/usr/sbin/apache2 -f /etc/cmnd/apache.conf -DFOREGROUND
ExecReload=/usr/sbin/apache2 -f /etc/cmnd/apache.conf -k graceful
Restart=on-failure
RestartSec=5
PrivateTmp=true
NoNewPrivileges=true
ProtectSystem=strict
ProtectHome=true
ReadWritePaths=/run/cmnd /var/log/cmnd /opt/cmnd/SmartCMS/sites/default/files
[Install]
WantedBy=multi-user.target
'''
    units['cmnd-tomcat.service'] = f'''[Unit]
Description=Philips CMND native Java applications
Requires=cmnd-egress.service cmnd-mysql.service
After=cmnd-egress.service cmnd-mysql.service
[Service]
Type=simple
User=cmnd
Group=cmnd
Environment=JAVA_HOME={java_home}
EnvironmentFile=/etc/cmnd/native-tomcat.env
WorkingDirectory=/opt/cmnd/tomcat
ExecStartPre=+/usr/bin/cmndctl native-wait-database
ExecStartPre=+/usr/bin/python3 /etc/cmnd/egress.py --policy /etc/cmnd/egress.json --execute
ExecStart=/opt/cmnd/tomcat/bin/catalina.sh run
SuccessExitStatus=143
Restart=on-failure
RestartSec=10
TimeoutStopSec=60
UMask=0027
NoNewPrivileges=true
PrivateTmp=true
ProtectHome=true
ProtectSystem=strict
ReadWritePaths=/opt/Philips /opt/cmnd/tomcat/webapps /opt/cmnd/tomcat/logs /opt/cmnd/tomcat/temp /opt/cmnd/tomcat/work /var/lib/cmnd /var/log/cmnd
[Install]
WantedBy=multi-user.target
'''
    units['cmnd-admin.service'] = '''[Unit]
Description=Linux CMND unprivileged administration GUI
After=network-online.target
[Service]
Type=simple
User=cmnd-admin
Group=cmnd-admin
ExecStart=/usr/bin/cmndctl update-gui --settings /etc/linux-cmnd-management/admin.json
Restart=on-failure
RestartSec=5
UMask=0077
NoNewPrivileges=true
PrivateTmp=true
ProtectHome=true
ProtectSystem=strict
ReadWritePaths=/var/lib/cmnd-update-requests
[Install]
WantedBy=multi-user.target
'''
    units['cmnd-update.service'] = '''[Unit]
Description=Linux CMND confirmed release update worker
Requires=cmnd-mysql.service
After=cmnd-mysql.service
[Service]
Type=oneshot
User=root
Group=cmnd-admin
UMask=0027
ExecStart=/usr/bin/cmndctl update-apply --execute
TimeoutStartSec=5400
PrivateTmp=true
ProtectHome=true
'''
    units['cmnd-update.path'] = '''[Unit]
Description=Linux CMND administrator-confirmed update queue
[Path]
PathExists=/var/lib/cmnd-update-requests/request.json
Unit=cmnd-update.service
[Install]
WantedBy=multi-user.target
'''
    return units


def _owned_tree(root: Path, uid: int, gid: int, private_files: bool = False) -> None:
    for path in (root, *root.rglob('*')):
        if path.is_symlink():
            raise ConfigError('refusing symlink inside assembled runtime')
        os.chown(path, uid, gid)
        path.chmod(0o750 if path.is_dir() or path.suffix == '.sh' else (0o600 if private_files else 0o640))


def _mysql(query: bytes, database: str | None = None, *, timeout: int = 900) -> bytes:
    args = ['docker', 'exec', '-i', 'cmnd-native-mysql', 'mysql',
            '--defaults-extra-file=/run/secrets/client.cnf', '--binary-mode=1', '--batch', '--skip-column-names']
    if database:
        args.append(database)
    return run(*args, data=query, timeout=timeout)


def wait_database(seconds: int = 120) -> None:
    if type(seconds) is not int or not 1 <= seconds <= 600:
        raise ConfigError('database readiness timeout must be 1..600 seconds')
    deadline = time.monotonic() + seconds
    while time.monotonic() < deadline:
        try:
            if _mysql(b'SELECT 1;', timeout=5).strip() == b'1':
                return
        except (RuntimeError, subprocess.TimeoutExpired):
            pass
        time.sleep(1)
    raise RuntimeError('private MySQL did not become ready; dependent application remains stopped')


def wait_php(seconds: int = 60) -> None:
    if type(seconds) is not int or not 1 <= seconds <= 1800:
        raise ConfigError('PHP readiness timeout must be an integer from 1 to 1800 seconds')
    deadline = time.monotonic() + seconds
    while time.monotonic() < deadline:
        try:
            with socket.create_connection(('127.0.0.1', LAYOUT.fpm_port), timeout=1):
                return
        except OSError:
            time.sleep(0.5)
    raise RuntimeError('PHP-FPM did not become ready')


def deploy(inputs: DeploymentInputs, *, execute: bool = False, accept_legacy: bool = False) -> dict:
    config = load_config(inputs.config)
    report = preflight(inputs, config)  # Before all database/network initialization.
    report.update(executed=execute, legacy_profile=True, services_started=False, readiness_verified=False)
    report.update(full_vendor_feature_parity_verified=False, native_mgate_installed=False,
                  scope='native web applications and isolated IP-management evaluation')
    if not execute:
        return report
    if os.geteuid() != 0 or not accept_legacy:
        raise ConfigError('native deployment requires root, --execute, and --accept-legacy-runtime')
    import pwd
    os.umask(0o077)
    STATE.mkdir(mode=0o700)
    try:
        values = {key: secrets.token_hex(24) for key in (*REQUIRED_SECRETS, 'tpvision_db_password')}
        write_new(STATE / 'secrets.json', json.dumps(values))
        write_new(STATE / 'certificate-password', values['cert_ca_password'])
        root_password = secrets.token_hex(32)
        write_new(STATE / 'mysql-root-password', root_password)
        write_new(STATE / 'mysql-client.cnf', f'[client]\nuser=root\npassword={root_password}\nhost=127.0.0.1\nport={config.database_port}\nprotocol=tcp\n')
        addresses = json.loads(run('ip', '-j', '-4', 'address', 'show'))
        ips = sorted({entry['local'] for interface in addresses if 'UP' in interface.get('flags', [])
                      for entry in interface.get('addr_info', []) if entry.get('family') == 'inet'})
        public_host, _ = native_endpoint(config)
        for user, directory in (('cmnd', '/var/lib/cmnd'), ('cmnd-cms', '/var/lib/cmnd/cms'), ('cmnd-admin', '/var/lib/cmnd-update-requests')):
            try:
                account = pwd.getpwnam(user)
                if account.pw_uid == 0 or account.pw_shell not in ('/usr/sbin/nologin', '/bin/false'):
                    raise ConfigError('existing service account has unexpected privileges')
            except KeyError:
                run('useradd', '--system', '--user-group', '--home-dir', directory, '--shell', '/usr/sbin/nologin', user)
        java_user, cms_user = pwd.getpwnam('cmnd'), pwd.getpwnam('cmnd-cms')
        certs = provision_certificates(CertificateConfig(STATE / 'certificates', public_host, ips,
            socket.gethostname(), STATE / 'certificate-password', STATE / 'certificate-password'))
        stage_application(ApplicationInputs(inputs.vendor, inputs.tomcat_archive, certs), config, values,
                          STATE / 'candidate', execute=True, database_host='127.0.0.1')
        payload, native = STATE / 'candidate/payload', STATE / 'candidate/native-config'
        Path('/opt/cmnd').mkdir(mode=0o755, exist_ok=True)
        Path('/opt/cmnd').chmod(0o755)
        for name, target in (('tomcat', Path(LAYOUT.tomcat)), ('SmartCMS', Path(LAYOUT.cms)), ('Philips', Path('/opt/Philips'))):
            shutil.copytree(payload / name, target)
        # Tomcat expects this directory even with autoDeploy disabled. Create it
        # before the service's read-only configuration protection takes effect.
        (Path(LAYOUT.tomcat) / 'conf/Catalina/localhost').mkdir(parents=True, exist_ok=True)
        _owned_tree(Path(LAYOUT.tomcat), java_user.pw_uid, java_user.pw_gid)
        _owned_tree(Path('/opt/Philips'), java_user.pw_uid, java_user.pw_gid)
        _owned_tree(Path(LAYOUT.cms), 0, cms_user.pw_gid)
        _owned_tree(Path(LAYOUT.cms) / 'sites/default/files', cms_user.pw_uid, cms_user.pw_gid)
        for directory, uid, gid in ((Path('/var/lib/cmnd'), java_user.pw_uid, java_user.pw_gid),
                                     (Path('/var/lib/cmnd/smartcontrol'), java_user.pw_uid, java_user.pw_gid),
                                     (Path('/var/log/cmnd'), java_user.pw_uid, java_user.pw_gid),
                                     (Path(LAYOUT.php_uploads), cms_user.pw_uid, cms_user.pw_gid),
                                     (Path(LAYOUT.pgt), cms_user.pw_uid, cms_user.pw_gid)):
            directory.mkdir(parents=True, exist_ok=True)
            os.chown(directory, uid, gid)
            directory.chmod(0o700 if str(directory) in (LAYOUT.pgt, LAYOUT.php_uploads) else 0o750)
        ETC.mkdir(parents=True, exist_ok=True)
        shutil.copytree(payload / 'tls', ETC / 'tls')
        for name in ('apache.conf', 'php-fpm.conf', 'configure-cms.php'):
            write_new(ETC / name, (native / name).read_bytes(), 0o640)
            os.chown(ETC / name, 0, cms_user.pw_gid)
        # Apache root opens its key before dropping privileges; never expose it to PHP.
        for certificate in (ETC / 'tls').iterdir():
            certificate.chmod(0o600)
        write_new(ETC / 'native-tomcat.env', (native / 'computername.env').read_text() +
            '\nCATALINA_OPTS="-Xms512m -Xmx4096m -Djava.awt.headless=true -Djavax.net.ssl.trustStore=/etc/cmnd/java-cacerts"\n', 0o640)
        os.chown(ETC / 'native-tomcat.env', 0, java_user.pw_gid)
        shutil.copyfile(inputs.java_home / 'lib/security/cacerts', ETC / 'java-cacerts')
        write_new(STATE / 'truststore-password', 'changeit')  # Public default truststore password, not a private-key secret.
        run(inputs.java_home / 'bin/keytool', '-importcert', '-noprompt', '-alias', 'linux-cmnd',
            '-file', certs.ca_pem, '-keystore', ETC / 'java-cacerts', '-storepass:file', STATE / 'truststore-password')
        os.chown(ETC / 'java-cacerts', 0, java_user.pw_gid)
        (ETC / 'java-cacerts').chmod(0o640)
        write_new(Path('/usr/local/share/ca-certificates/linux-cmnd.crt'), certs.ca_pem.read_bytes(), 0o644)
        run('update-ca-certificates')
        endpoints = {('127.0.0.1', config.database_port)}
        endpoints.update((address, port) for address in ips for port in
                         (config.tomcat_http, config.tomcat_https, config.apache_http, config.apache_https))
        # Initial installation grants no physical-TV egress. A CLI allowlist
        # must never silently authorize autonomous commands from the vendor GUI.
        policy = EgressPolicy('CMND_NATIVE', (java_user.pw_uid, cms_user.pw_uid), tuple(sorted(endpoints)))
        policy.validate()
        write_new(ETC / 'egress.json', json.dumps({'chain': policy.chain, 'uids': policy.uids, 'tcp_endpoints': policy.tcp_endpoints}))
        write_new(ETC / 'egress.py', Path(__file__).with_name('egress.py').read_bytes(), 0o644)
        for name, content in service_units(inputs.java_home).items():
            write_new(Path('/etc/systemd/system') / name, content, 0o644)
        run('systemd-analyze', 'verify', *(str(Path('/etc/systemd/system') / name) for name in service_units(inputs.java_home)))
        run('systemctl', 'daemon-reload')
        run('systemctl', 'enable', '--now', 'cmnd-egress')
        apply_policy(policy, execute=True)
        (STATE / 'mysql').mkdir(mode=0o700)
        run('docker', 'create', '--name', 'cmnd-native-mysql', '--network', 'host',
            '--mount', f'type=bind,src={STATE}/mysql,dst=/var/lib/mysql',
            '--mount', f'type=bind,src={STATE}/mysql-root-password,dst=/run/secrets/root-password,readonly',
            '--mount', f'type=bind,src={STATE}/mysql-client.cnf,dst=/run/secrets/client.cnf,readonly',
            '-e', 'MYSQL_ROOT_PASSWORD_FILE=/run/secrets/root-password', MYSQL_IMAGE,
            '--bind-address=127.0.0.1', f'--port={config.database_port}', '--event-scheduler=ON',
            '--local-infile=0', '--sql-mode=NO_ENGINE_SUBSTITUTION')
        run('systemctl', 'start', 'cmnd-mysql')
        wait_database()
        for schema, user, folder, numbers in SCHEMAS:
            key = folder + '_db_password'
            _mysql((f'CREATE DATABASE `{schema}` CHARACTER SET utf8 COLLATE utf8_general_ci;'
                    f"CREATE USER '{user}'@'%' IDENTIFIED BY '{values[key]}';"
                    f"GRANT ALL ON `{schema}`.* TO '{user}'@'%';").encode())
            for number in numbers:
                _mysql((inputs.vendor / 'SQLScripts' / folder / f'sql_{number}.sql').read_bytes(), schema)
        password = secrets.token_hex(24)
        # This schema was created immediately above; there is no restored/user data.
        _mysql(("DELETE FROM cas.users; INSERT INTO cas.users(username,password,role) VALUES"
                f"('admin','{hashlib.md5(password.encode()).hexdigest()}','ADMIN');").encode())
        write_new(STATE / 'initial-admin.json', json.dumps({'username': 'admin', 'password': password}))
        from .update_gui import password_record
        admin = pwd.getpwnam('cmnd-admin')
        for directory in (MANAGEMENT, Path('/var/lib/cmnd-updates')):
            directory.mkdir(mode=0o750)
            os.chown(directory, 0, admin.pw_gid)
            directory.chmod(0o750)
        queue = Path('/var/lib/cmnd-update-requests')
        queue.mkdir(mode=0o700)
        os.chown(queue, admin.pw_uid, admin.pw_gid)
        write_new(MANAGEMENT / 'admin.json', json.dumps({'password': password_record(password),
            'origin': https_origin(public_host, config.apache_https), 'check_on_startup': True,
            'applications': {'TV management': f'https://{public_host}:{config.tomcat_https}/SmartInstall/',
                             'Site and content editor': f'https://{public_host}:{config.apache_https}/SmartCMS/'}}), 0o640)
        write_new(MANAGEMENT / 'updates.json', json.dumps({'enabled': True, 'channel': 'preview', 'token_file': None}), 0o640)
        for path in MANAGEMENT.iterdir():
            os.chown(path, 0, admin.pw_gid)
            path.chmod(0o640)
        validate_baseline()
        shutil.copyfile(BASELINE, '/var/lib/cmnd-updates/current.deb')
        Path('/var/lib/cmnd-updates/current.deb').chmod(0o600)
        run('docker', 'create', '--name', 'cmnd-native-php', '--network', 'host',
            '--user', f'{cms_user.pw_uid}:{cms_user.pw_gid}', '--read-only', '--cap-drop', 'ALL',
            '--pids-limit', '128', '--memory', '1g', '--security-opt', 'no-new-privileges',
            '--tmpfs', '/tmp:rw,nosuid,noexec,size=256m',
            '--mount', f'type=bind,src={LAYOUT.cms},dst={LAYOUT.cms},readonly',
            '--mount', f'type=bind,src={LAYOUT.cms}/sites/default/files,dst={LAYOUT.cms}/sites/default/files',
            '--mount', f'type=bind,src={LAYOUT.pgt},dst={LAYOUT.pgt}',
            '--mount', f'type=bind,src={LAYOUT.php_uploads},dst={LAYOUT.php_uploads}',
            '--mount', 'type=bind,src=/etc/cmnd/php-fpm.conf,dst=/usr/local/etc/php-fpm.conf,readonly',
            '--mount', 'type=bind,src=/etc/ssl/certs,dst=/etc/ssl/certs,readonly', inputs.php_image)
        run('systemctl', 'start', 'cmnd-php')
        wait_php()
        run('docker', 'exec', '-i', '-e', 'CMND_CMS_EXECUTE=1', 'cmnd-native-php', 'php',
            data=(ETC / 'configure-cms.php').read_bytes())
        run('apache2', '-t', '-f', ETC / 'apache.conf')
        run('systemctl', 'start', 'cmnd-admin', 'cmnd-apache', 'cmnd-tomcat')
        report['services_started'] = True
        report.update(wait_ready(config, seconds=900))
        if not report['readiness_verified']:
            raise RuntimeError('native readiness failed; preserve private deployment state')
        run('systemctl', 'enable', 'cmnd-mysql', 'cmnd-php', 'cmnd-apache', 'cmnd-tomcat', 'cmnd-admin')
        run('systemctl', 'enable', '--now', 'cmnd-update.path')
        write_new(ETC / 'deployment.json', json.dumps({'state': 'active', 'managed_by': 'linux-cmnd-native', 'php_image': inputs.php_image,
            'mysql_image': MYSQL_IMAGE, 'vendor': '7.5.9', 'legacy_profile': True, 'config_sha256': file_hash(inputs.config)}))
        write_new(STATE / 'qualification.json', json.dumps(report, indent=2))
        return report
    except Exception:
        # Preserve database and all evidence, but do not leave a failed partial
        # application accepting requests. Never touch unrelated host services.
        subprocess.run(['systemctl', 'stop', 'cmnd-tomcat', 'cmnd-apache', 'cmnd-php', 'cmnd-admin', 'cmnd-update.path'], capture_output=True, timeout=120)
        write_new(STATE / 'FAILED', 'Incomplete deployment; preserve all state and inspect private logs.\n')
        raise


class _NoRedirect(HTTPRedirectHandler):
    def redirect_request(self, *args):
        return None


def wait_ready(config: Config, seconds: int = 900) -> dict:
    if type(seconds) is not int or not 1 <= seconds <= 1800:
        raise ConfigError('readiness timeout must be 1..1800 seconds')
    context = ssl.create_default_context(cafile=str(ETC / 'tls/ca.crt'))
    opener = build_opener(ProxyHandler({}), HTTPSHandler(context=context), _NoRedirect())
    statuses, histories = {}, {}
    deadline = time.monotonic() + seconds
    host = urlsplit(config.callback_base_url).hostname
    while time.monotonic() < deadline:
        paths = [(config.tomcat_https, path) for path in
                 ('cas/login', 'SmartInstall/', 'smartcontrol/', 'usermanagement/', 'smartcms/')]
        paths.append((config.apache_https, 'SmartCMS/'))
        paths.append((config.apache_https, 'linux-cmnd/'))
        for port, path in paths:
            try:
                with opener.open(f'https://{host}:{port}/{path}', timeout=5) as response:
                    statuses[path] = response.status
            except HTTPError as error:
                statuses[path] = error.code
            except (URLError, OSError):
                statuses[path] = 0
        histories = {}
        for schema, table, count, version in (('smartinstall', 'flyway_schema_history', 122, '9.7'),
                                                ('smartcontroldb', 'schema_version', 27, '1.5.1')):
            try:
                lines = _mysql(f'SELECT version,success FROM {schema}.{table} ORDER BY installed_rank;'.encode()).decode().splitlines()
                histories[schema] = len(lines) == count and lines[-1].split('\t')[0] == version and all(line.endswith('\t1') for line in lines)
            except RuntimeError:
                histories[schema] = False
        if all(code in (200, 301, 302, 303) for code in statuses.values()) and all(histories.values()):
            return {'readiness_verified': True, 'https_contexts': statuses, 'migration_histories': histories}
        time.sleep(3)
    return {'readiness_verified': False, 'https_contexts': statuses, 'migration_histories': histories}
