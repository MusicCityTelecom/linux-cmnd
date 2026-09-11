"""Opt-in repair for the pinned MySQL 5.7 runtime with already-lowercase tables.

Never rename tables or change text collations. Mixed-case stored identifiers need
a separately reviewed dump/reload migration and are deliberately rejected here.
"""
import json
import os
from pathlib import Path
import re
import shutil
import subprocess
import time

from .config import ConfigError, load_config
from .native_deploy import MYSQL_IMAGE, STATE, _mysql, run, wait_database, write_new

CONTAINER = 'cmnd-native-mysql'
CONFIG = '/etc/mysql/conf.d/linux-cmnd-case.cnf'
SERVICES = ('cmnd-update.path', 'cmnd-admin', 'cmnd-apache', 'cmnd-tomcat', 'cmnd-php')


def validate_identifiers(schemas, tables):
    """Do not silently merge names that differ only by case."""
    identifiers = list(schemas) + [name for pair in tables for name in pair]
    if not identifiers or any(not re.fullmatch(r'[a-z0-9_$]+', name) for name in identifiers):
        raise ConfigError('Case repair requires all stored schema/table names already lowercase; use a reviewed dump/reload migration')
    if len(set(schemas)) != len(schemas) or len(set(map(tuple, tables))) != len(tables):
        raise ConfigError('Duplicate database identifiers')


def package_counts():
    return {name: int(_mysql(f'SELECT COUNT(*) FROM smartinstall.`{name}`;'.encode()))
            for name in ('settingpackage', 'channelpackage', 'apppackage', 'upg_setting')}


def _repair(config_path=Path('/etc/cmnd/cmnd.toml'), *, execute=False):
    load_config(config_path)  # Safety validation precedes every database access.
    if os.name != 'posix' or os.geteuid() != 0:
        raise ConfigError('Database case audit/repair requires Linux root')
    info = json.loads(run('docker', 'inspect', CONTAINER))[0]
    if info['Config']['Image'] != MYSQL_IMAGE or not info['State']['Running']:
        raise ConfigError('Requires the running, pinned CMND MySQL container')
    mounts = [m for m in info['Mounts'] if m['Destination'] == '/var/lib/mysql']
    if len(mounts) != 1 or mounts[0]['Source'] != str(STATE / 'mysql'):
        raise ConfigError('Unexpected database mount; refusing repair')
    version, mode = _mysql(b'SELECT VERSION(), @@lower_case_table_names;').decode().strip().split('\t')
    if not version.startswith('5.7.'):
        raise ConfigError('This repair is only reviewed for MySQL 5.7')
    schemas = _mysql(b'SELECT SCHEMA_NAME FROM information_schema.SCHEMATA;').decode().splitlines()
    tables = [line.split('\t') for line in _mysql(b"SELECT TABLE_SCHEMA,TABLE_NAME FROM information_schema.TABLES WHERE TABLE_SCHEMA <> 'information_schema';").decode().splitlines()]
    validate_identifiers(schemas, tables)
    report = {'executed': False, 'lower_case_table_names': int(mode), 'schemas_checked': len(schemas),
              'tables_checked': len(tables), 'package_counts': package_counts()}
    if mode == '1':
        return report | {'already_compatible': True}
    if mode != '0' or any('lower-case-table' in arg or 'lower_case_table' in arg for arg in info['Config']['Cmd']):
        raise ConfigError('Conflicting MySQL case option; refusing repair')
    # Existing configuration must never be overwritten, even on a repeated failed attempt.
    if subprocess.run(['docker', 'exec', CONTAINER, 'test', '-e', CONFIG]).returncode != 1:
        raise ConfigError('Case configuration already exists; inspect the previous repair')
    if not execute:
        return report | {'plan': 'Stop CMND writers; cold-backup MySQL; enable Windows-compatible table lookup; verify counts; restart previously active services'}
    for path in (STATE, STATE / 'mysql', STATE / 'case-backups'):
        if any(p.is_symlink() for p in (path, *path.parents)):
            raise ConfigError('Database/backup path must not traverse symlinks')
    size = sum(p.stat().st_size for p in (STATE / 'mysql').rglob('*') if p.is_file())
    if shutil.disk_usage(STATE).free < size * 2 + 512 * 1024**2:
        raise ConfigError('Insufficient free disk for a verified cold backup')
    backup = STATE / 'case-backups' / str(time.time_ns())
    backup.mkdir(parents=True, mode=0o700)
    active = [name for name in SERVICES if subprocess.run(['systemctl', 'is-active', '--quiet', name]).returncode == 0]
    changed = False
    try:
        if active:
            run('systemctl', 'stop', *active)
        before = package_counts()
        run('systemctl', 'stop', 'cmnd-mysql')
        if json.loads(run('docker', 'inspect', CONTAINER))[0]['State']['Running']:
            raise ConfigError('Database did not stop; backup aborted')
        run('tar', '--numeric-owner', '-cpf', backup / 'mysql.tar', '-C', STATE, 'mysql', timeout=900)
        run('tar', '-tf', backup / 'mysql.tar', timeout=900)
        write_new(backup / 'container.json', json.dumps(info))
        write_new(backup / 'before.json', json.dumps(report | {'package_counts': before, 'active_services': active}))
        write_new(backup / 'case.cnf', '[mysqld]\nlower_case_table_names=1\n', 0o644)
        write_new(backup / 'disabled.cnf', '# Case repair rolled back.\n', 0o644)
        changed = True
        run('docker', 'cp', backup / 'case.cnf', CONTAINER + ':' + CONFIG)
        run('systemctl', 'start', 'cmnd-mysql')
        wait_database(90)
        if _mysql(b'SELECT @@lower_case_table_names;').strip() != b'1':
            raise ConfigError('MySQL did not enable case-insensitive table lookup')
        if package_counts() != before:
            raise ConfigError('Package row counts changed; application remains stopped pending review')
        # Execute the same mixed-case identifiers that previously failed.
        _mysql(b'SELECT COUNT(*) FROM smartinstall.settingPackage; SELECT COUNT(*) FROM smartinstall.channelPackage;')
        write_new(backup / 'SUCCESS.json', json.dumps({'counts_preserved': True, 'mode': 1}))
    except Exception:
        if changed:
            run('systemctl', 'stop', 'cmnd-mysql')
            run('docker', 'cp', backup / 'disabled.cnf', CONTAINER + ':' + CONFIG)
        run('systemctl', 'start', 'cmnd-mysql')
        wait_database(90)
        # Preserve backup and leave application writers stopped for investigation.
        raise
    if active:
        run('systemctl', 'start', *reversed(active), timeout=300)
    return report | {'executed': True, 'lower_case_table_names': 1, 'backup': str(backup), 'counts_preserved': True}


def repair(config_path=Path('/etc/cmnd/cmnd.toml'), *, execute=False):
    if not execute:
        return _repair(config_path, execute=False)
    load_config(config_path)
    if os.name != 'posix' or os.geteuid() != 0:
        raise ConfigError('Database repair requires Linux root')
    import fcntl
    if any(p.is_symlink() for p in (STATE, *STATE.parents)):
        raise ConfigError('Deployment state must not traverse symlinks')
    fd = os.open(STATE / 'database-case.lock', os.O_CREAT | os.O_RDWR | os.O_NOFOLLOW, 0o600)
    try:
        fcntl.flock(fd, fcntl.LOCK_EX | fcntl.LOCK_NB)
        return _repair(config_path, execute=True)
    finally:
        os.close(fd)
