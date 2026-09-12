import subprocess
import unittest
from unittest.mock import patch

from cmnd_linux.database_compatibility import REQUIRED_VARIABLES, assess, parse_variable_rows, probe


COMPATIBLE_VARIABLES = {
    'lower_case_table_names': '1',
    'event_scheduler': 'ON',
    'sql_mode': 'NO_ENGINE_SUBSTITUTION',
    'character_set_server': 'latin1',
    'collation_server': 'latin1_swedish_ci',
    'max_allowed_packet': '67108864',
    'read_only': 'OFF',
}


class DatabaseCompatibilityTests(unittest.TestCase):
    def test_parse_variable_rows(self):
        values = parse_variable_rows('lower_case_table_names\t1\nevent_scheduler\tON\n')
        self.assertEqual(values, {'lower_case_table_names': '1', 'event_scheduler': 'ON'})

    def test_current_qualified_mysql_family_can_be_reused_when_already_case_compatible(self):
        result = assess('5.7.44', COMPATIBLE_VARIABLES)
        self.assertTrue(result.compatible)
        self.assertEqual(result.server_family, 'mysql')
        self.assertEqual(result.qualification, 'matches-current-qualified-family')
        self.assertEqual(result.reasons, ())

    def test_case_mode_zero_is_never_silently_changed_on_existing_server(self):
        variables = dict(COMPATIBLE_VARIABLES, lower_case_table_names='0')
        result = assess('5.7.44', variables)
        self.assertFalse(result.compatible)
        self.assertTrue(any('changing it on an existing populated server is unsafe' in reason
                            for reason in result.reasons))

    def test_mysql8_requires_separate_shared_database_qualification(self):
        result = assess('8.0.43', COMPATIBLE_VARIABLES)
        self.assertFalse(result.compatible)
        self.assertEqual(result.qualification, 'candidate-requires-0.8-qualification')

    def test_mariadb_requires_separate_shared_database_qualification(self):
        result = assess('10.11.13-MariaDB-0ubuntu0.24.04.1', COMPATIBLE_VARIABLES)
        self.assertFalse(result.compatible)
        self.assertEqual(result.server_family, 'mariadb')

    def test_probe_is_read_only_and_never_places_password_on_command_line(self):
        rows = ['5.7.44'] + [f'{name}\t{COMPATIBLE_VARIABLES[name]}' for name in REQUIRED_VARIABLES]
        completed = subprocess.CompletedProcess([], 0, '\n'.join(rows) + '\n', '')
        with patch('cmnd_linux.database_compatibility.subprocess.run', return_value=completed) as run:
            result = probe('/usr/bin/mysql', socket_path='/run/mysqld/mysqld.sock')
        command = run.call_args.args[0]
        self.assertTrue(result['compatible'])
        self.assertIn('SELECT VERSION()', command[-1])
        self.assertIn('SHOW VARIABLES', command[-1])
        self.assertFalse(any(arg == '-p' or arg.startswith('--password') for arg in command))
        self.assertFalse(any(token in command[-1].upper() for token in ('CREATE ', 'ALTER ', 'GRANT ', 'SET GLOBAL', 'DROP ')))


if __name__ == '__main__':
    unittest.main()
