"""Pure installation planning from the read-only 0.8.0 host inventory.

Planning never mutates the host.  It describes reuse/install/isolate decisions and
allocates only CMND-owned ports.  Existing unrelated listeners always win.
"""
from __future__ import annotations

import json
from typing import Iterable

from .environment import DEFAULT_PORTS, host_inventory


DATABASE_PROCESS_HINTS = ('mysql', 'mysqld', 'maria', 'mariadb')


def _present(record: object) -> bool:
    return isinstance(record, dict) and bool(record.get('present'))


def _listeners(inventory: dict) -> list[dict]:
    value = inventory.get('listeners', {}).get('entries', [])
    return value if isinstance(value, list) else []


def _occupied(inventory: dict) -> set[int]:
    return {int(item['port']) for item in _listeners(inventory) if isinstance(item.get('port'), int)}


def _listeners_on(inventory: dict, port: int) -> list[dict]:
    return [item for item in _listeners(inventory) if item.get('port') == port]


def _database_listener(items: Iterable[dict]) -> bool:
    for item in items:
        process = str(item.get('process') or '').lower()
        if any(token in process for token in DATABASE_PROCESS_HINTS):
            return True
    return False


def allocate_port(preferred: int, occupied: set[int], reserved: set[int], *, span: int = 1000) -> int:
    """Return the preferred port or the first higher free CMND-owned candidate."""
    if not 1 <= preferred <= 65535:
        raise ValueError('preferred port out of range')
    for port in range(preferred, min(65535, preferred + span) + 1):
        if port not in occupied and port not in reserved:
            return port
    raise ValueError(f'no free port available near {preferred}')


def _apache_plan(inventory: dict, occupied: set[int], reserved: set[int]) -> dict:
    web = inventory.get('web', {})
    packages = inventory.get('packages', {})
    apache_present = _present(web.get('apache')) or _present(web.get('apache_ctl')) or bool(packages.get('apache2'))
    nginx_present = _present(web.get('nginx')) or bool(packages.get('nginx'))
    http = allocate_port(DEFAULT_PORTS['cms_http'], occupied, reserved)
    reserved.add(http)
    https = allocate_port(DEFAULT_PORTS['cms_https'], occupied, reserved)
    reserved.add(https)
    if apache_present:
        action = 'reuse-host-apache'
        detail = 'Add CMND-owned site/port configuration only; preserve unrelated Apache sites and global settings.'
    elif nginx_present:
        action = 'install-isolated-apache'
        detail = 'Preserve nginx unchanged; install Apache only for the legacy CMND CMS on CMND-owned listeners.'
    else:
        action = 'install-apache2'
        detail = 'Install Apache and keep CMND configuration in CMND-owned fragments.'
    return {
        'action': action,
        'existing_apache': apache_present,
        'existing_nginx': nginx_present,
        'ports': {'http': http, 'https': https},
        'detail': detail,
    }


def _database_plan(inventory: dict, occupied: set[int], reserved: set[int]) -> dict:
    database = inventory.get('database', {})
    packages = inventory.get('packages', {})
    mariadb_present = (_present(database.get('mariadb_client')) or
                       bool(database.get('packages', {}).get('mariadb-server')) or
                       bool(packages.get('mariadb-server')))
    mysql_present = (_present(database.get('mysql_client')) or
                     bool(database.get('packages', {}).get('mysql-server')) or
                     bool(packages.get('mysql-server')))
    existing = mariadb_present or mysql_present
    preferred_listeners = _listeners_on(inventory, DEFAULT_PORTS['database'])
    database_on_default = _database_listener(preferred_listeners)

    if existing:
        action = 'probe-existing-database'
        port = DEFAULT_PORTS['database'] if database_on_default or not preferred_listeners else None
        auth = ('local-root-socket' if database.get('root_socket_auth') == 'available'
                else 'request-admin-credentials-at-execution')
        return {
            'action': action,
            'candidate': 'mariadb' if mariadb_present else 'mysql',
            'port': port,
            'admin_auth': auth,
            'compatibility_required': True,
            'compatibility_checks': [
                'server/version compatibility with Philips CMND 7.5.9',
                'case-insensitive Windows-compatible table lookup behavior',
                'event scheduler availability',
                'required SQL modes/charset/collation behavior',
                'ability to create isolated CMND schemas/users without changing unrelated schemas',
            ],
            'detail': ('Do not change the existing database until compatibility passes. '
                       'Administrator credentials are transient and must never be persisted.'),
        }

    # Until host MariaDB/MySQL reuse has its own qualification evidence, retain the
    # already-proven isolated database runtime as the fallback implementation.
    port = allocate_port(DEFAULT_PORTS['database'], occupied, reserved)
    reserved.add(port)
    return {
        'action': 'install-qualified-isolated-database',
        'candidate': 'pinned-mysql57-runtime',
        'port': port,
        'admin_auth': 'generated-cmnd-credentials',
        'compatibility_required': False,
        'detail': ('Use the currently qualified isolated MySQL 5.7 runtime until a host database '
                   'implementation is separately qualified for 0.8.0.'),
    }


def _java_plan(inventory: dict) -> dict:
    java = inventory.get('runtime', {}).get('java', {})
    version = str(java.get('version') or '') if isinstance(java, dict) else ''
    if _present(java) and ('17.' in version or ' 17 ' in version):
        return {'action': 'reuse-java17', 'path': java.get('path'), 'version': version}
    return {
        'action': 'install-java17',
        'package': 'openjdk-17-jre-headless',
        'existing_java': version or None,
        'detail': 'Do not replace another application\'s Java; CMND must explicitly use Java 17.',
    }


def _tomcat_plan(inventory: dict, occupied: set[int], reserved: set[int]) -> dict:
    http = allocate_port(DEFAULT_PORTS['tomcat_http'], occupied, reserved)
    reserved.add(http)
    https = allocate_port(DEFAULT_PORTS['tomcat_https'], occupied, reserved)
    reserved.add(https)
    return {
        'action': 'install-isolated-pinned-tomcat9',
        'version_line': '9.x',
        'ports': {'http': http, 'https': https},
        'detail': 'Never deploy CMND into an unrelated host Tomcat instance; use a CMND-owned CATALINA_BASE/runtime.',
    }


def _docker_plan(inventory: dict, database_plan: dict) -> dict:
    docker = inventory.get('docker', {})
    present = _present(docker.get('docker'))
    required_for_database = database_plan.get('action') == 'install-qualified-isolated-database'
    return {
        'present': present,
        'daemon_access': bool(docker.get('daemon_access')),
        'running_containers': list(docker.get('running_containers', [])),
        'native_install_requirement': ('required-for-current-legacy-runtime' if required_for_database
                                       else 'still-required-for-current-legacy-php-runtime'),
        'action': 'reuse-docker' if present else 'install-docker-runtime',
        'detail': ('0.7.1/initial 0.8 architecture still isolates legacy PHP 5.6 in a container. '
                   'The separate full Docker/Compose deployment remains a different target.'),
    }


def build_plan(inventory: dict) -> dict:
    occupied = _occupied(inventory)
    reserved: set[int] = set()
    cmnd = inventory.get('cmnd', {})
    runtime_paths = cmnd.get('runtime_paths', {}) if isinstance(cmnd, dict) else {}
    existing_cmnd = bool(cmnd.get('active_runtime_detected')) or any(
        bool(value) for value in runtime_paths.values()
    )

    database = _database_plan(inventory, occupied, reserved)
    tomcat = _tomcat_plan(inventory, occupied, reserved)
    apache = _apache_plan(inventory, occupied, reserved)
    java = _java_plan(inventory)
    docker = _docker_plan(inventory, database)

    warnings: list[str] = []
    if inventory.get('os', {}).get('qualification') != 'qualified-baseline':
        warnings.append('This OS is not the Ubuntu 24.04 qualified baseline; qualification is required before release support.')
    if existing_cmnd:
        warnings.append('Existing CMND state detected: fresh installation must stop and use an explicit upgrade/adoption workflow.')
    if apache.get('existing_nginx') and not apache.get('existing_apache'):
        warnings.append('nginx is present; it will be preserved and CMND Apache must use separate listeners.')
    if database.get('action') == 'probe-existing-database':
        warnings.append('Existing database reuse is only a candidate until the 0.8.0 compatibility probe passes.')

    return {
        'schema': 1,
        'read_only': True,
        'fresh_install_allowed': not existing_cmnd,
        'existing_cmnd_detected': existing_cmnd,
        'os': inventory.get('os', {}),
        'components': {
            'database': database,
            'java': java,
            'tomcat': tomcat,
            'apache': apache,
            'docker': docker,
        },
        'port_policy': {
            'rule': 'existing unrelated listeners win; move only CMND-owned listeners',
            'occupied_before_plan': sorted(occupied),
            'cmnd_reserved': sorted(reserved),
        },
        'warnings': warnings,
        'requires_operator_confirmation': True,
        'execution_performed': False,
    }


def main() -> int:
    print(json.dumps(build_plan(host_inventory()), indent=2, sort_keys=True))
    return 0


if __name__ == '__main__':
    raise SystemExit(main())
