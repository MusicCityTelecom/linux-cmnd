"""Read-only host inventory for coexistence-aware CMND installation planning.

This module intentionally does not install packages, edit configuration, start or
stop services, bind listeners, initialize databases, or contact TVs.  It reports
what is already present so a later planner can decide whether to reuse, isolate,
or install each dependency.
"""
from __future__ import annotations

import json
import os
from pathlib import Path
import platform
import re
import shutil
import socket
import subprocess
from typing import Iterable


DEFAULT_PORTS = {
    'tomcat_http': 8080,
    'tomcat_https': 8443,
    'cms_http': 8082,
    'cms_https': 8444,
    'database': 3306,
    'php_fpm': 9000,
    'management': 9078,
}

PACKAGE_NAMES = (
    'apache2', 'nginx', 'mariadb-server', 'mysql-server', 'docker.io', 'docker-ce',
    'openjdk-17-jre-headless', 'php', 'php-fpm', 'tomcat9', 'linux-cmnd',
)


def _run(args: Iterable[str], *, timeout: int = 5) -> subprocess.CompletedProcess[str] | None:
    command = list(args)
    if not command:
        return None
    executable = command[0]
    if '/' not in executable:
        resolved = shutil.which(executable)
        if not resolved:
            return None
        command[0] = resolved
    elif not os.access(executable, os.X_OK):
        return None
    try:
        return subprocess.run(command, capture_output=True, text=True, timeout=timeout, check=False)
    except (OSError, subprocess.SubprocessError):
        return None


def _first_line(value: str) -> str | None:
    for line in value.splitlines():
        line = line.strip()
        if line:
            return line[:500]
    return None


def read_os_release(path: Path = Path('/etc/os-release')) -> dict[str, str]:
    values: dict[str, str] = {}
    try:
        text = path.read_text(encoding='utf-8')
    except OSError:
        return values
    for line in text.splitlines():
        line = line.strip()
        if not line or line.startswith('#') or '=' not in line:
            continue
        key, value = line.split('=', 1)
        value = value.strip()
        if len(value) >= 2 and value[0] == value[-1] and value[0] in {'"', "'"}:
            value = value[1:-1]
        values[key] = value
    return values


def qualification_for(os_release: dict[str, str]) -> str:
    target = (os_release.get('ID', ''), os_release.get('VERSION_ID', ''))
    if target == ('ubuntu', '24.04'):
        return 'qualified-baseline'
    if target in {('ubuntu', '22.04'), ('ubuntu', '26.04')}:
        return 'target-unqualified'
    if target in {('debian', '12'), ('debian', '13')}:
        return 'existing-detected-target'
    return 'unsupported-or-unknown'


def package_version(name: str) -> str | None:
    result = _run(('dpkg-query', '-W', '-f=${Status}\t${Version}', name))
    if not result or result.returncode:
        return None
    status, _, version = result.stdout.partition('\t')
    if status.strip() != 'install ok installed' or not version.strip():
        return None
    return version.strip()


def service_state(unit: str) -> dict[str, str | bool | None]:
    active = _run(('systemctl', 'is-active', unit))
    enabled = _run(('systemctl', 'is-enabled', unit))
    return {
        'unit': unit,
        'active': bool(active and active.returncode == 0),
        'active_state': _first_line(active.stdout) if active else None,
        'enabled': bool(enabled and enabled.returncode == 0),
        'enabled_state': _first_line(enabled.stdout) if enabled else None,
    }


def command_version(command: str, *args: str) -> dict[str, str | bool | int | None]:
    path = shutil.which(command)
    if not path:
        return {'present': False, 'path': None, 'version': None, 'returncode': None}
    result = _run((path, *args))
    combined = '' if result is None else (result.stdout + '\n' + result.stderr)
    return {
        'present': True,
        'path': str(Path(path).resolve()),
        'version': _first_line(combined),
        'returncode': None if result is None else result.returncode,
    }


def parse_ss_listeners(text: str) -> list[dict[str, object]]:
    listeners: list[dict[str, object]] = []
    for raw in text.splitlines():
        line = raw.strip()
        if not line:
            continue
        fields = line.split()
        if len(fields) < 5:
            continue
        protocol = fields[0].lower()
        local = fields[4]
        match = re.search(r':(\d+)$', local)
        if not match:
            continue
        port = int(match.group(1))
        address = local[:match.start()]
        if address.startswith('[') and address.endswith(']'):
            address = address[1:-1]
        process_text = ' '.join(fields[6:]) if len(fields) > 6 else ''
        process = None
        pid = None
        name_match = re.search(r'\(\("([^"\\]+)"', process_text)
        pid_match = re.search(r'pid=(\d+)', process_text)
        if name_match:
            process = name_match.group(1)
        if pid_match:
            pid = int(pid_match.group(1))
        listeners.append({
            'protocol': protocol,
            'address': address,
            'port': port,
            'process': process,
            'pid': pid,
        })
    return listeners


def listeners() -> dict[str, object]:
    result = _run(('ss', '-H', '-lntup'))
    if result is None:
        return {'available': False, 'entries': []}
    return {'available': result.returncode == 0, 'entries': parse_ss_listeners(result.stdout)}


def default_port_conflicts(entries: list[dict[str, object]]) -> dict[str, list[dict[str, object]]]:
    result: dict[str, list[dict[str, object]]] = {}
    for name, port in DEFAULT_PORTS.items():
        result[name] = [entry for entry in entries if entry.get('port') == port]
    return result


def _socket_candidates() -> list[str]:
    candidates = ('/run/mysqld/mysqld.sock', '/var/run/mysqld/mysqld.sock', '/var/lib/mysql/mysql.sock')
    return [path for path in candidates if Path(path).exists()]


def database_inventory() -> dict[str, object]:
    mariadb = command_version('mariadb', '--version')
    mysql = command_version('mysql', '--version')
    sockets = _socket_candidates()
    socket_auth = 'not-tested'
    socket_auth_client = None
    if os.name == 'posix' and hasattr(os, 'geteuid') and os.geteuid() == 0 and sockets:
        client = mariadb if mariadb['present'] else mysql
        if client['present'] and client['path']:
            socket_auth_client = str(client['path'])
            probe = _run((socket_auth_client, '--protocol=socket', f'--socket={sockets[0]}',
                          '-uroot', '--batch', '--skip-column-names', '-e', 'SELECT 1'), timeout=4)
            if probe is None:
                socket_auth = 'unknown'
            elif probe.returncode == 0 and probe.stdout.strip() == '1':
                socket_auth = 'available'
            else:
                socket_auth = 'denied-or-unavailable'
    return {
        'mariadb_client': mariadb,
        'mysql_client': mysql,
        'mariadb_service': service_state('mariadb.service'),
        'mysql_service': service_state('mysql.service'),
        'socket_candidates': sockets,
        'root_socket_auth': socket_auth,
        'root_socket_auth_client': socket_auth_client,
        'packages': {
            'mariadb-server': package_version('mariadb-server'),
            'mysql-server': package_version('mysql-server'),
        },
    }


def web_inventory() -> dict[str, object]:
    apache = command_version('apache2', '-v')
    apachectl = command_version('apache2ctl', '-v')
    nginx = command_version('nginx', '-v')
    return {
        'apache': apache,
        'apache_ctl': apachectl,
        'apache_service': service_state('apache2.service'),
        'nginx': nginx,
        'nginx_service': service_state('nginx.service'),
        'known_config_paths': {
            'apache': [path for path in ('/etc/apache2/apache2.conf', '/etc/apache2/ports.conf') if Path(path).exists()],
            'nginx': [path for path in ('/etc/nginx/nginx.conf',) if Path(path).exists()],
        },
    }


def docker_inventory() -> dict[str, object]:
    docker = command_version('docker', '--version')
    compose = {'present': False, 'version': None, 'returncode': None}
    containers: list[str] = []
    daemon_access = False
    if docker['present'] and docker['path']:
        result = _run((str(docker['path']), 'compose', 'version'))
        if result is not None:
            compose = {
                'present': result.returncode == 0,
                'version': _first_line(result.stdout + '\n' + result.stderr),
                'returncode': result.returncode,
            }
        result = _run((str(docker['path']), 'ps', '--format', '{{.Names}}'))
        if result is not None and result.returncode == 0:
            daemon_access = True
            containers = [line.strip() for line in result.stdout.splitlines() if line.strip()]
    return {
        'docker': docker,
        'compose': compose,
        'daemon_access': daemon_access,
        'running_containers': containers,
    }


def runtime_inventory() -> dict[str, object]:
    java = command_version('java', '-version')
    keytool = command_version('keytool', '-help')
    php = command_version('php', '-v')
    tomcat_paths = [
        path for path in ('/opt/cmnd/tomcat/bin/catalina.sh', '/usr/share/tomcat9/bin/catalina.sh')
        if Path(path).exists()
    ]
    return {'java': java, 'keytool': keytool, 'php': php, 'tomcat_paths': tomcat_paths}


def cmnd_inventory() -> dict[str, object]:
    paths = ('/opt/linux-cmnd', '/opt/cmnd', '/etc/cmnd', '/var/lib/cmnd-deployment')
    return {
        'package_version': package_version('linux-cmnd'),
        'paths': {path: Path(path).exists() for path in paths},
        'services': {name: service_state(name) for name in (
            'cmnd-egress.service', 'cmnd-mysql.service', 'cmnd-php.service',
            'cmnd-apache.service', 'cmnd-tomcat.service',
        )},
    }


def resource_inventory() -> dict[str, object]:
    memory_kib = None
    try:
        match = re.search(r'^MemTotal:\s+(\d+)\s+kB', Path('/proc/meminfo').read_text(), re.M)
        if match:
            memory_kib = int(match.group(1))
    except OSError:
        pass
    disk: dict[str, dict[str, int]] = {}
    for path in ('/', '/var/lib'):
        try:
            usage = shutil.disk_usage(path)
        except OSError:
            continue
        disk[path] = {'total': usage.total, 'used': usage.used, 'free': usage.free}
    return {'memory_kib': memory_kib, 'disk': disk}


def host_inventory() -> dict[str, object]:
    os_release = read_os_release()
    listener_report = listeners()
    entries = listener_report['entries'] if isinstance(listener_report.get('entries'), list) else []
    dpkg_arch = _run(('dpkg', '--print-architecture'))
    architecture = _first_line(dpkg_arch.stdout) if dpkg_arch and dpkg_arch.returncode == 0 else platform.machine()
    return {
        'schema': 1,
        'read_only': True,
        'hostname': socket.gethostname(),
        'os': {
            'id': os_release.get('ID'),
            'version_id': os_release.get('VERSION_ID'),
            'pretty_name': os_release.get('PRETTY_NAME'),
            'qualification': qualification_for(os_release),
            'architecture': architecture,
            'kernel': platform.release(),
        },
        'packages': {name: package_version(name) for name in PACKAGE_NAMES},
        'web': web_inventory(),
        'database': database_inventory(),
        'docker': docker_inventory(),
        'runtime': runtime_inventory(),
        'listeners': listener_report,
        'default_port_conflicts': default_port_conflicts(entries),
        'cmnd': cmnd_inventory(),
        'resources': resource_inventory(),
    }


def main() -> int:
    print(json.dumps(host_inventory(), indent=2, sort_keys=True))
    return 0


if __name__ == '__main__':
    raise SystemExit(main())
