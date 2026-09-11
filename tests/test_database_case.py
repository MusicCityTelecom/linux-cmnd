import unittest
import json
import os
import subprocess
import time
import uuid
from unittest.mock import patch
from cmnd_linux.config import ConfigError
from cmnd_linux.database_case import validate_identifiers, _repair
from cmnd_linux.native_deploy import MYSQL_CASE_OPTION, MYSQL_IMAGE, STATE, mysql_server_options


class DatabaseCaseTests(unittest.TestCase):
    def test_fresh_install_uses_windows_table_semantics(self):
        self.assertEqual(MYSQL_CASE_OPTION, '--lower-case-table-names=1')
        self.assertIn(MYSQL_CASE_OPTION, mysql_server_options(3306))
        self.assertIn('--bind-address=127.0.0.1', mysql_server_options(3306))
        self.assertIn('--port=13306', mysql_server_options(13306))

    def test_existing_lowercase_names_are_safe(self):
        validate_identifiers(['smartinstall', 'sys'], [('smartinstall', 'settingpackage'), ('sys', 'x$statements')])

    def test_mixed_case_collision_and_unsafe_identifiers_fail_closed(self):
        for schemas, tables in [(['smartinstall'], [('smartinstall', 'settingPackage')]),
                                (['SmartInstall'], []), ([], []),
                                (['smartinstall'], [('smartinstall', 'a'), ('smartinstall', 'a')]),
                                (['smartinstall'], [('smartinstall', 'a;DROP')])]:
            with self.assertRaises(ConfigError):
                validate_identifiers(schemas, tables)

    def test_dry_run_validates_before_database_and_never_restarts(self):
        info = {'Config': {'Image': MYSQL_IMAGE, 'Cmd': []}, 'State': {'Running': True},
                'Mounts': [{'Destination': '/var/lib/mysql', 'Source': str(STATE / 'mysql')}]}
        responses = [b'5.7.44\t0\n', b'smartinstall\n', b'smartinstall\tsettingpackage\n']
        with patch('cmnd_linux.database_case.load_config') as config, \
             patch('cmnd_linux.database_case.os.name', 'posix'), \
             patch('cmnd_linux.database_case.os.geteuid', return_value=0, create=True), \
             patch('cmnd_linux.database_case.run', return_value=json.dumps([info]).encode()) as run, \
             patch('cmnd_linux.database_case._mysql', side_effect=responses), \
             patch('cmnd_linux.database_case.package_counts', return_value={}), \
             patch('cmnd_linux.database_case.subprocess.run', return_value=subprocess.CompletedProcess([],1)):
            result = _repair(execute=False)
            self.assertFalse(result['executed'])
            config.assert_called_once()
            run.assert_called_once_with('docker','inspect','cmnd-native-mysql')

    def test_invalid_configuration_fails_before_container_or_database_access(self):
        with patch('cmnd_linux.database_case.load_config', side_effect=ConfigError('invalid')), \
             patch('cmnd_linux.database_case.run') as run, \
             patch('cmnd_linux.database_case._mysql') as mysql:
            with self.assertRaises(ConfigError): _repair(execute=True)
            run.assert_not_called()
            mysql.assert_not_called()


@unittest.skipUnless(os.environ.get('CMND_TEST_MYSQL_CASE') == '1', 'Opt-in isolated Docker MySQL runtime test')
class DatabaseCaseRuntimeTests(unittest.TestCase):
    def test_fresh_initialization_mixed_case_tables_views_and_data(self):
        name = 'cmnd-case-test-' + uuid.uuid4().hex[:12]
        def docker(*args):
            return subprocess.run(['docker', *args], capture_output=True, check=True, timeout=120).stdout
        try:
            docker('run','-d','--pull=never','--name',name,'--network','none',
                   '--memory','1g','--tmpfs','/var/lib/mysql:rw,size=1g',
                   '-e','MYSQL_ALLOW_EMPTY_PASSWORD=yes',MYSQL_IMAGE,*mysql_server_options(3306))
            for attempt in range(60):
                try:
                    docker('exec',name,'mysql','-uroot','-N','-B','-e','SELECT 1;')
                    break
                except subprocess.CalledProcessError:
                    if attempt == 59: raise
                    time.sleep(1)
            sql = ('CREATE DATABASE CaseTest; CREATE TABLE CaseTest.ChannelPackage (id INT PRIMARY KEY, label VARCHAR(20));'
                   "INSERT INTO casetest.channelpackage VALUES (1,'KeepMyCase');"
                   'CREATE VIEW CaseTest.PackageView AS SELECT * FROM casetest.CHANNELPACKAGE;'
                   'SELECT @@lower_case_table_names; SELECT label FROM CASETEST.PackageView;'
                   'SELECT COUNT(*) FROM CaseTest.channelPackage;')
            result=docker('exec',name,'mysql','-uroot','-N','-B','-e',sql).decode().splitlines()
            self.assertEqual(result,['1','KeepMyCase','1'])
        finally:
            subprocess.run(['docker','rm','-f',name],capture_output=True,timeout=60)
