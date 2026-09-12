from pathlib import Path
import subprocess
import tempfile
import unittest
from unittest.mock import patch

from cmnd_linux import environment


class EnvironmentInventoryTests(unittest.TestCase):
    def test_os_release_and_qualification(self):
        with tempfile.TemporaryDirectory() as temp:
            path = Path(temp) / 'os-release'
            path.write_text('ID=ubuntu\nVERSION_ID="24.04"\nPRETTY_NAME="Ubuntu 24.04.4 LTS"\n')
            values = environment.read_os_release(path)
        self.assertEqual(values['ID'], 'ubuntu')
        self.assertEqual(values['VERSION_ID'], '24.04')
        self.assertEqual(environment.qualification_for(values), 'qualified-baseline')
        self.assertEqual(environment.qualification_for({'ID': 'ubuntu', 'VERSION_ID': '22.04'}),
                         'target-unqualified')
        self.assertEqual(environment.qualification_for({'ID': 'ubuntu', 'VERSION_ID': '26.04'}),
                         'target-unqualified')

    def test_parse_ss_and_default_port_conflicts(self):
        text = (
            'tcp LISTEN 0 4096 127.0.0.1:3306 0.0.0.0:* users:(("mysqld",pid=123,fd=20))\n'
            'tcp LISTEN 0 128 0.0.0.0:8080 0.0.0.0:* users:(("java",pid=456,fd=30))\n'
            'tcp LISTEN 0 128 [::]:22 [::]:* users:(("sshd",pid=10,fd=3))\n'
        )
        entries = environment.parse_ss_listeners(text)
        self.assertEqual([entry['port'] for entry in entries], [3306, 8080, 22])
        self.assertEqual(entries[0]['process'], 'mysqld')
        self.assertEqual(entries[0]['pid'], 123)
        conflicts = environment.default_port_conflicts(entries)
        self.assertEqual(conflicts['database'][0]['process'], 'mysqld')
        self.assertEqual(conflicts['tomcat_http'][0]['process'], 'java')
        self.assertEqual(conflicts['cms_http'], [])

    def test_database_socket_probe_never_requests_password(self):
        commands = []

        def fake_run(args, timeout=5):
            commands.append(tuple(args))
            return subprocess.CompletedProcess(list(args), 0, '1\n', '')

        client = {'present': True, 'path': '/usr/bin/mariadb', 'version': 'mariadb test', 'returncode': 0}
        with patch.object(environment, 'command_version', return_value=client), \
             patch.object(environment, '_socket_candidates', return_value=['/run/mysqld/mysqld.sock']), \
             patch.object(environment, 'service_state', return_value={'active': True}), \
             patch.object(environment, 'package_version', return_value='test'), \
             patch.object(environment.os, 'geteuid', return_value=0, create=True), \
             patch.object(environment, '_run', side_effect=fake_run):
            result = environment.database_inventory()
        self.assertEqual(result['root_socket_auth'], 'available')
        probe = next(command for command in commands if '-uroot' in command)
        self.assertIn('--protocol=socket', probe)
        self.assertIn('SELECT 1', probe)
        self.assertFalse(any(arg == '-p' or arg.startswith('--password') for arg in probe))

    def test_inventory_source_contains_no_mutating_service_or_package_actions(self):
        source = Path(environment.__file__).read_text(encoding='utf-8')
        forbidden = (
            'apt-get install', 'apt install', 'systemctl start', 'systemctl stop',
            'systemctl restart', 'systemctl enable', 'CREATE DATABASE', 'CREATE USER',
            'ALTER USER', 'GRANT ALL', 'docker run', 'docker create',
        )
        for token in forbidden:
            self.assertNotIn(token, source)

    def test_port_defaults_remain_vendor_compatible(self):
        self.assertEqual(environment.DEFAULT_PORTS['tomcat_http'], 8080)
        self.assertEqual(environment.DEFAULT_PORTS['tomcat_https'], 8443)
        self.assertEqual(environment.DEFAULT_PORTS['cms_http'], 8082)
        self.assertEqual(environment.DEFAULT_PORTS['cms_https'], 8444)
        self.assertEqual(environment.DEFAULT_PORTS['database'], 3306)


if __name__ == '__main__':
    unittest.main()
