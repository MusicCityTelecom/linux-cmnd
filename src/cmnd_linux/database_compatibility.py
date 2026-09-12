"""Read-only compatibility assessment for an existing MySQL/MariaDB service.

An existing server is never reconfigured by this module.  In particular,
`lower_case_table_names` is a server-wide data-layout/lookup behavior and must
not be changed on an unrelated populated server just to satisfy CMND.
"""
from __future__ import annotations

from dataclasses import dataclass
import re
import subprocess
from typing import Mapping


REQUIRED_CASE_MODE = '1'
REQUIRED_VARIABLES = (
    'lower_case_table_names', 'event_scheduler', 'sql_mode', 'character_set_server',
    'collation_server', 'max_allowed_packet', 'read_only',
)


@dataclass(frozen=True)
class DatabaseCompatibility:
    compatible: bool
    qualification: str
    server_family: str
    version: str
    reasons: tuple[str, ...]
    variables: Mapping[str, str]

    def as_dict(self) -> dict:
        return {
            'compatible': self.compatible,
            'qualification': self.qualification,
            'server_family': self.server_family,
            'version': self.version,
            'reasons': list(self.reasons),
            'variables': dict(self.variables),
            'changes_performed': False,
        }


def parse_variable_rows(text: str) -> dict[str, str]:
    values: dict[str, str] = {}
    for line in text.splitlines():
        if not line.strip():
            continue
        fields = line.rstrip('\r\n').split('\t', 1)
        if len(fields) != 2:
            continue
        values[fields[0].strip().lower()] = fields[1].strip()
    return values


def server_family(version: str) -> str:
    lower = version.lower()
    if 'mariadb' in lower:
        return 'mariadb'
    if re.match(r'^\d+\.\d+(?:\.\d+)?', version):
        return 'mysql'
    return 'unknown'


def assess(version: str, variables: Mapping[str, str]) -> DatabaseCompatibility:
    family = server_family(version)
    normalized = {str(key).lower(): str(value) for key, value in variables.items()}
    reasons: list[str] = []

    case_mode = normalized.get('lower_case_table_names')
    if case_mode != REQUIRED_CASE_MODE:
        reasons.append(
            'lower_case_table_names must already be 1; changing it on an existing populated server is unsafe'
        )

    read_only = normalized.get('read_only', '').upper()
    if read_only in {'1', 'ON', 'TRUE', 'YES'}:
        reasons.append('server is read-only')

    if family == 'mysql' and version.startswith('5.7.'):
        qualification = 'matches-current-qualified-family'
    elif family in {'mysql', 'mariadb'}:
        qualification = 'candidate-requires-0.8-qualification'
        reasons.append(f'{family} {version} has not yet been qualified as a shared 0.8.0 CMND database')
    else:
        qualification = 'unsupported-or-unknown'
        reasons.append('database family/version could not be identified')

    missing = sorted(name for name in REQUIRED_VARIABLES if name not in normalized)
    if missing:
        reasons.append('compatibility probe did not return required variables: ' + ', '.join(missing))

    compatible = not reasons and qualification == 'matches-current-qualified-family'
    return DatabaseCompatibility(compatible, qualification, family, version, tuple(reasons), normalized)


def probe(client: str, *, socket_path: str | None = None, user: str = 'root', timeout: int = 5) -> dict:
    """Probe with an already-authorized client session; never prompts or mutates."""
    command = [client, '--batch', '--skip-column-names', '--connect-timeout=3']
    if socket_path:
        command += ['--protocol=socket', f'--socket={socket_path}']
    command += [f'-u{user}', '-e', 'SELECT VERSION(); SHOW VARIABLES WHERE Variable_name IN (' +
                ','.join("'" + name + "'" for name in REQUIRED_VARIABLES) + ');']
    run = subprocess.run(command, capture_output=True, text=True, timeout=timeout, check=False)
    if run.returncode:
        return {
            'connected': False,
            'returncode': run.returncode,
            'compatible': False,
            'qualification': 'authentication-or-connection-failed',
            'changes_performed': False,
        }
    lines = run.stdout.splitlines()
    if not lines:
        return {
            'connected': True,
            'compatible': False,
            'qualification': 'invalid-probe-output',
            'changes_performed': False,
        }
    version = lines[0].strip()
    result = assess(version, parse_variable_rows('\n'.join(lines[1:]))).as_dict()
    result['connected'] = True
    return result
