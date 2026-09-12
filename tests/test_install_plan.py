import unittest

from cmnd_linux.install_plan import allocate_port, build_plan


def base_inventory():
    return {
        'os': {'id': 'ubuntu', 'version_id': '24.04', 'qualification': 'qualified-baseline'},
        'packages': {},
        'listeners': {'available': True, 'entries': []},
        'web': {
            'apache': {'present': False}, 'apache_ctl': {'present': False},
            'nginx': {'present': False},
        },
        'database': {
            'mariadb_client': {'present': False}, 'mysql_client': {'present': False},
            'packages': {}, 'root_socket_auth': 'not-tested',
        },
        'docker': {'docker': {'present': False}, 'daemon_access': False, 'running_containers': []},
        'runtime': {'java': {'present': False, 'version': None, 'path': None}},
        'cmnd': {'package_version': None, 'paths': {}},
    }


class InstallPlanTests(unittest.TestCase):
    def test_clean_host_preserves_vendor_default_ports(self):
        plan = build_plan(base_inventory())
        self.assertTrue(plan['fresh_install_allowed'])
        self.assertEqual(plan['components']['tomcat']['ports'], {'http': 8080, 'https': 8443})
        self.assertEqual(plan['components']['apache']['ports'], {'http': 8082, 'https': 8444})
        self.assertEqual(plan['components']['database']['port'], 3306)
        self.assertFalse(plan['execution_performed'])

    def test_existing_apache_is_reused_but_its_listener_is_never_taken(self):
        inventory = base_inventory()
        inventory['web']['apache'] = {'present': True, 'path': '/usr/sbin/apache2'}
        inventory['listeners']['entries'] = [
            {'protocol': 'tcp', 'address': '0.0.0.0', 'port': 8082, 'process': 'apache2', 'pid': 100},
        ]
        plan = build_plan(inventory)
        apache = plan['components']['apache']
        self.assertEqual(apache['action'], 'reuse-host-apache')
        self.assertNotEqual(apache['ports']['http'], 8082)
        self.assertEqual(apache['ports']['http'], 8083)
        self.assertIn(8082, plan['port_policy']['occupied_before_plan'])

    def test_existing_database_on_3306_is_candidate_reuse_not_port_conflict(self):
        inventory = base_inventory()
        inventory['database']['mariadb_client'] = {'present': True, 'path': '/usr/bin/mariadb'}
        inventory['database']['root_socket_auth'] = 'available'
        inventory['database']['packages'] = {'mariadb-server': '10.11', 'mysql-server': None}
        inventory['listeners']['entries'] = [
            {'protocol': 'tcp', 'address': '127.0.0.1', 'port': 3306, 'process': 'mariadbd', 'pid': 200},
        ]
        plan = build_plan(inventory)
        database = plan['components']['database']
        self.assertEqual(database['action'], 'probe-existing-database')
        self.assertEqual(database['port'], 3306)
        self.assertEqual(database['admin_auth'], 'local-root-socket')
        self.assertTrue(database['compatibility_required'])

    def test_existing_database_without_socket_auth_requests_transient_admin_credentials(self):
        inventory = base_inventory()
        inventory['database']['mysql_client'] = {'present': True, 'path': '/usr/bin/mysql'}
        inventory['database']['packages'] = {'mariadb-server': None, 'mysql-server': '8.0'}
        plan = build_plan(inventory)
        self.assertEqual(plan['components']['database']['admin_auth'],
                         'request-admin-credentials-at-execution')

    def test_existing_cmnd_blocks_fresh_install(self):
        inventory = base_inventory()
        inventory['cmnd'] = {'package_version': '0.7.1', 'paths': {'/opt/cmnd': True}}
        plan = build_plan(inventory)
        self.assertFalse(plan['fresh_install_allowed'])
        self.assertTrue(plan['existing_cmnd_detected'])
        self.assertTrue(any('Existing CMND state' in warning for warning in plan['warnings']))

    def test_unrelated_nginx_is_preserved(self):
        inventory = base_inventory()
        inventory['web']['nginx'] = {'present': True, 'path': '/usr/sbin/nginx'}
        plan = build_plan(inventory)
        self.assertEqual(plan['components']['apache']['action'], 'install-isolated-apache')
        self.assertTrue(any('nginx is present' in warning for warning in plan['warnings']))

    def test_allocator_never_steals_occupied_or_reserved_port(self):
        self.assertEqual(allocate_port(8080, {8080, 8081}, {8082}), 8083)


if __name__ == '__main__':
    unittest.main()
