"""Coexistence-safe use of an already-installed MySQL service for CMND 0.8.

This module never changes global MySQL options. It only accepts a server that
already matches the reviewed Windows-compatible lookup semantics, refuses any
pre-existing CMND schema/user namespace, and creates/drops only the CMND objects
that this invocation created. Administrator credentials are supplied through a
root-only temporary defaults file or local root socket authentication and are
never written to CMND persistent state.
"""
from __future__ import annotations

from dataclasses import dataclass
import json
import os
from pathlib import Path
import re
import shutil
import subprocess
import time
from typing import Mapping

from .application_stage import VENDOR_INPUT_HASHES
from .artifacts import file_hash
from .config import ConfigError
from .database_compatibility import REQUIRED_VARIABLES, assess, parse_variable_rows

SCHEMA_SPECS = (
    ('smartinstall', 'siuser', 'smartinstall', (2, 3)),
    ('cas', 'cas', 'cas', (2,)),
    ('tpvision', 'tpvision', 'tpvision', tuple(range(2, 14))),
    ('smartcontroldb', 'smartcontrol', 'smartcontrol', (2,)),
    ('smartcms', 'smartcms', 'smartcms', (2, 3)),
)
APP_HOSTS = ('127.0.0.1', 'localhost')
HEALTH_DIR = Path('/etc/cmnd/shared-database')


def _valid_client(path: str) -> str:
    candidate = Path(path)
    if not candidate.is_absolute() or not candidate.is_file() or not os.access(candidate, os.X_OK):
        raise ConfigError('shared database client must be an executable absolute path')
    return str(candidate)


def _validate_defaults_file(path: Path) -> Path:
    path = Path(path)
    if not path.is_file() or path.is_symlink():
        raise ConfigError('database administrator defaults file must be a regular file')
    if os.name == 'posix' and path.stat().st_mode & 0o077:
        raise ConfigError('database administrator defaults file must be mode 0600 or stricter')
    return path


def _sql_string(value: str) -> str:
    if not re.fullmatch(r'[A-Za-z0-9._@%+:/=-]{1,255}', value):
        raise ConfigError('unsafe SQL credential/identifier literal')
    return "'" + value.replace("'", "''") + "'"


@dataclass
class SharedDatabaseSession:
    client: str
    port: int
    socket_path: str | None = None
    admin_defaults_file: Path | None = None
    admin_user: str = 'root'

    def __post_init__(self) -> None:
        self.client = _valid_client(self.client)
        if type(self.port) is not int or not 1 <= self.port <= 65535:
            raise ConfigError('invalid shared database port')
        if self.socket_path is not None:
            socket = Path(self.socket_path)
            if not socket.exists() or socket.is_symlink():
                raise ConfigError('shared database socket does not exist or is unsafe')
            self.socket_path = str(socket)
        if self.admin_defaults_file is not None:
            self.admin_defaults_file = _validate_defaults_file(self.admin_defaults_file)
        if not re.fullmatch(r'[A-Za-z0-9_@.-]{1,64}', self.admin_user):
            raise ConfigError('invalid database administrator user')

    def _command(self, database: str | None = None) -> list[str]:
        command = [self.client]
        if self.admin_defaults_file is not None:
            command.append(f'--defaults-extra-file={self.admin_defaults_file}')
        command += ['--batch', '--skip-column-names', '--binary-mode=1', '--connect-timeout=5']
        if self.socket_path:
            command += ['--protocol=socket', f'--socket={self.socket_path}']
        else:
            command += ['--protocol=tcp', '-h127.0.0.1', f'-P{self.port}']
        if self.admin_defaults_file is None:
            command.append(f'-u{self.admin_user}')
        if database:
            if not re.fullmatch(r'[A-Za-z0-9_]{1,64}', database):
                raise ConfigError('unsafe database name')
            command.append(database)
        return command

    def execute(self, sql: bytes | str, database: str | None = None, *, timeout: int = 900) -> bytes:
        data = sql.encode() if isinstance(sql, str) else sql
        result = subprocess.run(self._command(database), input=data, capture_output=True,
                                timeout=timeout, check=False)
        if result.returncode:
            raise RuntimeError('shared database command failed; administrator output intentionally withheld')
        return result.stdout

    def compatibility(self) -> dict:
        names = ','.join("'" + name + "'" for name in REQUIRED_VARIABLES)
        output = self.execute('SELECT VERSION(); SHOW VARIABLES WHERE Variable_name IN (' + names + ');', timeout=20)
        lines = output.decode(errors='replace').splitlines()
        if not lines:
            raise ConfigError('shared database compatibility probe returned no output')
        result = assess(lines[0].strip(), parse_variable_rows('\n'.join(lines[1:]))).as_dict()
        result['connected'] = True
        return result

    def namespace_conflicts(self) -> dict[str, list[str]]:
        schemas = ','.join(_sql_string(spec[0]) for spec in SCHEMA_SPECS)
        users = ','.join(_sql_string(spec[1]) for spec in SCHEMA_SPECS)
        schema_rows = self.execute(
            f'SELECT SCHEMA_NAME FROM information_schema.SCHEMATA WHERE SCHEMA_NAME IN ({schemas});', timeout=20
        ).decode(errors='replace').splitlines()
        user_rows = self.execute(
            f'SELECT CONCAT(User,"@",Host) FROM mysql.user WHERE User IN ({users});', timeout=20
        ).decode(errors='replace').splitlines()
        return {'schemas': sorted(filter(None, map(str.strip, schema_rows))),
                'users': sorted(filter(None, map(str.strip, user_rows)))}

    def validate_for_fresh_cmnd(self) -> dict:
        compatibility = self.compatibility()
        if not compatibility.get('compatible'):
            reasons = compatibility.get('reasons') or [compatibility.get('qualification', 'incompatible')]
            raise ConfigError('existing database is not qualified for shared CMND use: ' + '; '.join(map(str, reasons)))
        conflicts = self.namespace_conflicts()
        if conflicts['schemas'] or conflicts['users']:
            raise ConfigError('existing CMND database/user namespace detected; fresh shared-database install refused')
        probe = subprocess.run(
            [self.client, '--batch', '--skip-column-names', '--connect-timeout=3',
             '--protocol=tcp', '-h127.0.0.1', f'-P{self.port}', '-u__cmnd_preflight_nonexistent__', '-e', 'SELECT 1'],
            capture_output=True, timeout=8, check=False,
        )
        combined = (probe.stdout + probe.stderr).decode(errors='replace')
        if probe.returncode and ('2002' in combined or '2003' in combined or "Can't connect" in combined):
            raise ConfigError('existing database is not reachable on TCP loopback at the planned CMND port')
        return {'compatible': True, 'namespace_clear': True, 'tcp_loopback_reachable': True,
                'changes_performed': False, 'compatibility': compatibility}

    def rollback_fresh(self) -> dict:
        """Remove only the fixed CMND namespaces from a failed fresh install.

        Callers may use this only after this session successfully passed the
        namespace-clear gate and created the fresh CMND objects.
        """
        removed_users: list[str] = []
        removed_schemas: list[str] = []
        for _schema, user, _folder, _numbers in reversed(SCHEMA_SPECS):
            for host in reversed(APP_HOSTS):
                self.execute(f'DROP USER IF EXISTS {_sql_string(user)}@{_sql_string(host)};', timeout=20)
                removed_users.append(f'{user}@{host}')
        for schema, _user, _folder, _numbers in reversed(SCHEMA_SPECS):
            self.execute(f'DROP DATABASE IF EXISTS `{schema}`;', timeout=60)
            removed_schemas.append(schema)
        return {'users_removed': removed_users, 'schemas_removed': removed_schemas}

    def initialize(self, vendor: Path, values: Mapping[str, str]) -> dict:
        self.validate_for_fresh_cmnd()
        vendor = Path(vendor)
        for name, expected in VENDOR_INPUT_HASHES.items():
            path = vendor / name
            if not path.is_file() or path.is_symlink() or file_hash(path) != expected:
                raise ConfigError('unrecognized or missing pinned vendor input: ' + name)
        required_keys = {folder + '_db_password' for _, _, folder, _ in SCHEMA_SPECS}
        required_keys.add('tpvision_db_password')
        missing = sorted(key for key in required_keys if not values.get(key))
        if missing:
            raise ConfigError('missing generated CMND database credentials: ' + ', '.join(missing))

        created_schemas: list[str] = []
        created_users: list[tuple[str, str]] = []
        try:
            for schema, user, folder, numbers in SCHEMA_SPECS:
                key = folder + '_db_password'
                password = values[key]
                self.execute(f'CREATE DATABASE `{schema}` CHARACTER SET utf8 COLLATE utf8_general_ci;')
                created_schemas.append(schema)
                for host in APP_HOSTS:
                    self.execute(f'CREATE USER {_sql_string(user)}@{_sql_string(host)} IDENTIFIED BY {_sql_string(password)};')
                    created_users.append((user, host))
                    self.execute(f'GRANT ALL ON `{schema}`.* TO {_sql_string(user)}@{_sql_string(host)};')
                for number in numbers:
                    self.execute((vendor / 'SQLScripts' / folder / f'sql_{number}.sql').read_bytes(), schema)
            return {'schemas_created': created_schemas,
                    'users_created': [f'{user}@{host}' for user, host in created_users],
                    'shared_database': True}
        except Exception:
            for user, host in reversed(created_users):
                subprocess.run(self._command(), input=f'DROP USER IF EXISTS {_sql_string(user)}@{_sql_string(host)};'.encode(),
                               capture_output=True, timeout=20, check=False)
            for schema in reversed(created_schemas):
                subprocess.run(self._command(), input=f'DROP DATABASE IF EXISTS `{schema}`;'.encode(),
                               capture_output=True, timeout=60, check=False)
            raise


def write_health_files(values: Mapping[str, str], port: int, *, root: Path = HEALTH_DIR) -> dict[str, str]:
    if root.exists() or root.is_symlink():
        raise ConfigError('shared database health configuration already exists')
    root.mkdir(parents=True, mode=0o700)
    result: dict[str, str] = {}
    for schema, user, folder in (('smartinstall', 'siuser', 'smartinstall'),
                                 ('smartcontroldb', 'smartcontrol', 'smartcontrol')):
        password = values[folder + '_db_password']
        content = (f'[client]\nuser={user}\npassword={password}\nhost=127.0.0.1\n'
                   f'port={port}\nprotocol=tcp\n')
        path = root / f'{schema}.cnf'
        fd = os.open(path, os.O_CREAT | os.O_EXCL | os.O_WRONLY, 0o600)
        with os.fdopen(fd, 'w', encoding='utf-8', newline='\n') as handle:
            handle.write(content)
        result[schema] = str(path)
    marker = root / 'state.json'
    marker.write_text(json.dumps({'port': port, 'files': result}, indent=2) + '\n', encoding='utf-8')
    marker.chmod(0o600)
    return result


def _health_query(schema: str, sql: str, *, root: Path = HEALTH_DIR, timeout: int = 10) -> bytes:
    state_path = root / 'state.json'
    if not state_path.is_file() or state_path.is_symlink():
        raise ConfigError('shared database health state is missing')
    state = json.loads(state_path.read_text(encoding='utf-8'))
    path = Path(state['files'][schema])
    _validate_defaults_file(path)
    client = shutil.which('mariadb') or shutil.which('mysql')
    if not client:
        raise ConfigError('mysql/mariadb client unavailable for shared database health check')
    result = subprocess.run([client, f'--defaults-extra-file={path}', '--batch', '--skip-column-names', schema, '-e', sql],
                            capture_output=True, timeout=timeout, check=False)
    if result.returncode:
        raise RuntimeError('shared database health query failed')
    return result.stdout


def wait_shared_database(seconds: int = 120) -> None:
    if type(seconds) is not int or not 1 <= seconds <= 600:
        raise ConfigError('shared database readiness timeout must be 1..600 seconds')
    deadline = time.monotonic() + seconds
    while time.monotonic() < deadline:
        try:
            if _health_query('smartinstall', 'SELECT 1;').strip() == b'1':
                return
        except Exception:
            pass
        time.sleep(1)
    raise RuntimeError('shared database did not become ready')


def migration_histories() -> dict[str, bool]:
    checks = (
        ('smartinstall', 'flyway_schema_history', 122, '9.7'),
        ('smartcontroldb', 'schema_version', 27, '1.5.1'),
    )
    result: dict[str, bool] = {}
    for schema, table, count, version in checks:
        try:
            lines = _health_query(schema, f'SELECT version,success FROM {schema}.{table} ORDER BY installed_rank;').decode().splitlines()
            result[schema] = bool(lines and len(lines) == count and lines[-1].split('\t')[0] == version
                                  and all(line.endswith('\t1') for line in lines))
        except Exception:
            result[schema] = False
    return result
