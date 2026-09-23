from pathlib import Path
import unittest

from cmnd_linux.apt_installer import _planned_ports, render_config
from cmnd_linux.apache_integration import render as render_apache
from cmnd_linux.config import Config
from cmnd_linux.install_plan import build_plan
from cmnd_linux.shared_database import _sql_string


def inventory():
    return {
        'os': {'id': 'ubuntu', 'version_id': '24.04', 'qualification': 'qualified-baseline'},
        'packages': {'linux-cmnd': '0.8.0'},
        'listeners': {'available': True, 'entries': []},
        'web': {
            'apache': {'present': False}, 'apache_ctl': {'present': False},
            'apache_service': {'active': False}, 'nginx': {'present': False},
        },
        'database': {
            'mariadb_client': {'present': False}, 'mysql_client': {'present': False},
            'packages': {}, 'root_socket_auth': 'not-tested',
        },
        'docker': {'docker': {'present': True}, 'daemon_access': True, 'running_containers': []},
        'runtime': {'java': {'present': True, 'version': 'openjdk version "17.0.12"', 'path': '/usr/bin/java'}},
        'cmnd': {
            'package_version': '0.8.0',
            'paths': {'/opt/linux-cmnd': True, '/opt/cmnd': True, '/etc/cmnd': True},
            'runtime_paths': {
                '/var/lib/cmnd-deployment': False,
                '/opt/cmnd/tomcat': False,
                '/opt/cmnd/SmartCMS': False,
                '/opt/Philips': False,
                '/etc/cmnd/deployment.json': False,
            },
            'active_runtime_detected': False,
        },
    }


class AptCoexistenceTests(unittest.TestCase):
    def test_tooling_only_host_can_be_planned_for_first_activation(self):
        inv = inventory()
        plan = build_plan(inv)
        self.assertTrue(plan['fresh_install_allowed'])
        ports = _planned_ports(inv, plan, 'isolated')
        self.assertEqual(ports['tomcat_http'], 8080)
        self.assertEqual(ports['tomcat_https'], 8443)
        self.assertEqual(ports['apache_http'], 8082)
        self.assertEqual(ports['apache_https'], 8444)
        self.assertEqual(ports['database'], 3306)

    def test_main_cmnd_ports_move_without_taking_existing_listener(self):
        inv = inventory()
        inv['listeners']['entries'] = [
            {'port': 8080, 'process': 'other-java'},
            {'port': 8082, 'process': 'other-web'},
            {'port': 3306, 'process': 'other-db'},
        ]
        plan = build_plan(inv)
        ports = _planned_ports(inv, plan, 'isolated')
        self.assertNotIn(8080, ports.values())
        self.assertNotIn(8082, ports.values())
        self.assertNotIn(3306, ports.values())

    def test_generated_config_has_empty_tv_allowlist(self):
        text = render_config('10.1.10.14', {
            'tomcat_http': 8080, 'tomcat_https': 8443,
            'apache_http': 8082, 'apache_https': 8444, 'database': 3306,
        })
        self.assertIn('bind = "10.1.10.14"', text)
        self.assertIn('allowlist = []', text)
        self.assertIn('permitted_ranges = ["127.0.0.0/8"]', text)

    def test_host_apache_fragment_is_scoped_and_does_not_include_host_sites(self):
        config = Config(
            mode='lab', bind='10.1.10.14',
            callback_base_url='http://10.1.10.14:8080',
            permitted_ranges=('127.0.0.0/8',), allowed_tvs=(),
        )
        text = render_apache(config)
        self.assertIn('# Managed by CMND Linux 0.8.', text)
        self.assertIn('Listen 10.1.10.14:8082', text)
        self.assertNotIn('IncludeOptional sites-enabled', text)
        self.assertNotIn('ports.conf', text)

    def test_generated_database_credentials_are_restricted_literals(self):
        self.assertEqual(_sql_string('abc123'), "'abc123'")
        with self.assertRaises(Exception):
            _sql_string("bad'quote")

    def test_package_upgrade_preserves_runtime_and_password_is_transient(self):
        root = Path(__file__).resolve().parents[1]
        postinst = (root / 'packaging/cmnd-linux/postinst').read_text()
        prerm = (root / 'packaging/cmnd-linux/prerm').read_text()
        self.assertIn('existing active CMND deployment preserved', postinst)
        self.assertIn("db_set cmnd-linux/database-admin-password ''", postinst)
        self.assertIn('upgrade|deconfigure', prerm)
        upgrade_block = prerm.split('upgrade|deconfigure', 1)[1]
        self.assertNotIn('disable --now', upgrade_block)

    def test_coexistence_source_sets_runtime_permissions(self):
        root = Path(__file__).resolve().parents[1]
        source = (root / 'src/cmnd_linux/coexist_deploy.py').read_text()
        self.assertIn("os.chown(ETC/'java-cacerts',0,java.pw_gid)", source)
        self.assertIn("MANAGEMENT.chmod(0o750)", source)
        self.assertIn("management_file.chmod(0o640)", source)


if __name__ == '__main__':
    unittest.main()
