from dataclasses import replace
import json
from pathlib import Path
import tempfile
import unittest
import xml.etree.ElementTree as ET

from cmnd_linux.config import Config, ConfigError
from cmnd_linux.native_config import NativeLayout, render_native_files, stage_native_config
from cmnd_linux.vendor_config import REQUIRED_SECRETS


class NativeConfigTests(unittest.TestCase):
    def setUp(self):
        self.config = Config('isolated', '127.0.0.1', 'http://127.0.0.1:8080', ('127.0.0.0/8',), ())
        self.secrets = {name: 'test-only<&"secret' for name in REQUIRED_SECRETS}

    def test_original_ports_and_vendor_reload_and_callback(self):
        files = render_native_files(self.config, self.secrets)
        root = ET.fromstring(files['server.xml'])
        connectors = root.findall('./Service/Connector')
        self.assertEqual([node.attrib['port'] for node in connectors], ['8080', '8443'])
        self.assertTrue(all(node.attrib['address'] == '127.0.0.1' for node in connectors))
        self.assertEqual(connectors[1].attrib['protocol'], 'com.tpv.smartinstall.util.ReloadProtocol')
        cert = root.find('.//Certificate')
        self.assertEqual(cert.attrib['certificateKeystorePassword'], self.secrets['cert_ca_password'])
        self.assertEqual(cert.attrib['certificateKeyAlias'], 'tomcat')
        self.assertIn('/SmartInstall/webservices.jsp', files['ROOT/WEB-INF/rewrite.config'])
        self.assertIn('Listen 127.0.0.1:8082', files['apache.conf'])
        self.assertIn('Listen 127.0.0.1:8444', files['apache.conf'])
        self.assertNotIn('sites-enabled', files['apache.conf'])
        self.assertNotIn('ports.conf', files['apache.conf'])

    def test_changed_ports_propagate_to_all_consumers(self):
        cfg = replace(self.config, callback_base_url='https://cmnd.example.test:19443',
                      tomcat_http=19080, tomcat_https=19443, apache_http=19082,
                      apache_https=19444, database_port=13306)
        files = render_native_files(cfg, self.secrets, replace(NativeLayout(), fpm_port=19000))
        for name, values in {'server.xml': ('19080', '19443'), 'apache.conf': ('19082', '19444', '19000'),
                             'php-fpm.conf': ('19000',), 'configure-cms.php': ('19444',)}.items():
            for value in values:
                self.assertIn(value, files[name])
        self.assertIn('cmnd.example.test', files['apache.conf'])

    def test_rejects_inconsistent_or_unsafe_endpoints_before_output(self):
        invalid = [replace(self.config, callback_base_url=value) for value in (
            'http://127.0.0.1:8081', 'http://user:secret@127.0.0.1:8080',
            'http://127.0.0.1:8080/SmartInstall', 'http://127.0.0.1:8080?x=y',
            'http://[::1]:8080')]
        invalid += [replace(self.config, bind='0.0.0.0'), replace(self.config, tomcat_http=True)]
        with tempfile.TemporaryDirectory() as temp:
            output = Path(temp) / 'stage'
            for config in invalid:
                with self.subTest(config=config), self.assertRaises(ConfigError):
                    stage_native_config(config, self.secrets, output, execute=True)
                self.assertFalse(output.exists())

    def test_paths_cannot_inject_configuration(self):
        for path in ('/', '/etc/cmnd/../other', '/etc/cmnd//other', '/etc/cmnd\nInjected value', '/etc/cmnd;bad'):
            with self.subTest(path=path), self.assertRaises(ConfigError):
                render_native_files(self.config, self.secrets, replace(NativeLayout(), cms=path))
        with self.assertRaises(ConfigError):
            render_native_files(self.config, self.secrets, replace(NativeLayout(), fpm_port=8080))

    def test_new_private_stage_and_no_secrets_in_manifest(self):
        with tempfile.TemporaryDirectory() as temp:
            output = Path(temp) / 'stage'
            result = stage_native_config(self.config, self.secrets, output)
            self.assertFalse(result['executed'])
            self.assertFalse(output.exists())
            result = stage_native_config(self.config, self.secrets, output, execute=True)
            self.assertFalse(result['services_started'])
            self.assertEqual(json.loads((output / 'manifest.json').read_text()), result)
            self.assertNotIn(self.secrets['cert_ca_password'], (output / 'manifest.json').read_text())
            with self.assertRaises(FileExistsError):
                stage_native_config(self.config, self.secrets, output, execute=True)

    def test_cms_public_uploads_cannot_execute_php_and_secret_files_denied(self):
        files = render_native_files(self.config, self.secrets)
        self.assertIn('AllowOverride None\n    Options -ExecCGI -Indexes', files['apache.conf'])
        self.assertIn('phtml|phar', files['apache.conf'])
        self.assertIn('p12|pfx|pem|key|sql|bak', files['apache.conf'])
        self.assertIn('clear_env = yes', files['php-fpm.conf'])
        self.assertIn('listen.allowed_clients = 127.0.0.1', files['php-fpm.conf'])
        self.assertIn("variable_set('cas_cert', $cmnd_ca)", files['configure-cms.php'])


if __name__ == '__main__':
    unittest.main()
