from io import BytesIO
import hashlib
from pathlib import Path
import shutil
import tarfile
import tempfile
import unittest
from unittest.mock import patch
from zipfile import ZipFile

from cmnd_linux.application_stage import ApplicationInputs, SQL_INITIALIZATION, VENDOR_INPUT_HASHES, stage_application
from cmnd_linux.certificates import CertificateArtifacts
from cmnd_linux.config import Config, ConfigError
from cmnd_linux.vendor_cms import VendorCmsStage
from cmnd_linux.vendor_config import REQUIRED_SECRETS, WAR_NAMES


class ApplicationStageTests(unittest.TestCase):
    def setUp(self):
        self.temp = tempfile.TemporaryDirectory()
        self.addCleanup(self.temp.cleanup)
        self.root = Path(self.temp.name)
        self.vendor = self.root / 'vendor'
        self.vendor.mkdir()
        for name in WAR_NAMES:
            with ZipFile(self.vendor / name, 'w') as archive:
                archive.writestr('WEB-INF/classes/vendor.class', b'private-test-bytecode')
        with ZipFile(self.vendor / 'Philips.zip', 'w') as archive:
            archive.writestr('templates/template.txt', 'synthetic template')
        (self.vendor / 'SmartCMS.zip').write_bytes(b'mocked CMS input')
        reload = self.vendor / 'Tomcat 9.0/lib/reload.jar'
        reload.parent.mkdir(parents=True)
        reload.write_bytes(b'vendor reload')
        for schema, numbers in SQL_INITIALIZATION.items():
            for number in (*numbers, 1):
                sql = self.vendor / 'SQLScripts' / schema / f'sql_{number}.sql'
                sql.parent.mkdir(parents=True, exist_ok=True)
                sql.write_text('SELECT 1;')
        self.archive = self.root / 'tomcat.tar.gz'
        with tarfile.open(self.archive, 'w:gz') as archive:
            for name in ('bin/catalina.sh', 'lib/library.jar', 'conf/server.xml', 'conf/context.xml',
                         'webapps/manager/index.jsp'):
                entry = tarfile.TarInfo('apache-tomcat-9.0.121/' + name)
                entry.size = 4
                archive.addfile(entry, BytesIO(b'test'))
        cert_dir = self.root / 'certs'
        cert_dir.mkdir()
        paths = [cert_dir / name for name in ('ca.p12', 'ca.crt', 'server.p12', 'server.crt',
                                             'server.key', 'chain.crt', 'computername.env')]
        for path in paths:
            path.write_text('test certificate ' + path.name)
        self.inputs = ApplicationInputs(self.vendor, self.archive, CertificateArtifacts(cert_dir, *paths))
        self.config = Config('isolated', '127.0.0.1', 'http://127.0.0.1:8080', ('127.0.0.0/8',), ())
        self.secrets = {name: 'unit-test-secret' for name in (*REQUIRED_SECRETS, 'tpvision_db_password')}
        self.output = self.root / 'stage'
        self.pin = patch('cmnd_linux.application_stage.TOMCAT_SHA512', hashlib.sha512(self.archive.read_bytes()).hexdigest())
        self.pin.start()
        self.addCleanup(self.pin.stop)
        self.vendor_pin = patch('cmnd_linux.application_stage.VENDOR_INPUT_HASHES',
            {name: hashlib.sha256((self.vendor / name).read_bytes()).hexdigest() for name in VENDOR_INPUT_HASHES})
        self.vendor_pin.start()
        self.addCleanup(self.vendor_pin.stop)

    @staticmethod
    def render_wars(source, stage, config, *, linux_helpers=False):
        assert linux_helpers is True
        stage.mkdir()
        return [Path(shutil.copyfile(source / name, stage / name)) for name in WAR_NAMES]

    @staticmethod
    def cms_stage(archive, stage, **kwargs):
        root = stage / 'SmartCMS'
        settings = root / 'sites/default/settings.php'
        settings.parent.mkdir(parents=True)
        settings.write_text('<?php // mocked CMS settings')
        (settings.parent / 'config.properties').write_text('server.name=old\nserver.port=1\nmysql.port=1\n')
        files = settings.parent / 'files'
        files.mkdir()
        return VendorCmsStage(root, settings, files)

    def test_dry_run_validates_inputs_without_mutation(self):
        report = stage_application(self.inputs, self.config, self.secrets, self.output)
        self.assertFalse(report['executed'])
        self.assertFalse(self.output.exists())

    def test_unqualified_ipv6_database_host_is_rejected(self):
        with self.assertRaisesRegex(ConfigError, 'IPv6'):
            stage_application(self.inputs, self.config, self.secrets, self.output,
                              database_host='::1', execute=True)
        self.assertFalse(self.output.exists())

    def test_archive_pin_and_missing_input_fail_before_output(self):
        with patch('cmnd_linux.application_stage.TOMCAT_SHA512', '0' * 128), self.assertRaises(ConfigError):
            stage_application(self.inputs, self.config, self.secrets, self.output, execute=True)
        self.assertFalse(self.output.exists())

    def test_unrecognized_vendor_bytes_fail_before_output(self):
        (self.vendor / 'cas.war').write_bytes(b'changed vendor input')
        with self.assertRaisesRegex(ConfigError, 'cas.war'):
            stage_application(self.inputs, self.config, self.secrets, self.output, execute=True)
        self.assertFalse(self.output.exists())
        (self.vendor / 'cas.war').unlink()
        with self.assertRaises(ConfigError):
            stage_application(self.inputs, self.config, self.secrets, self.output, execute=True)
        self.assertFalse(self.output.exists())

    def test_assembly_contract_omits_stock_admin_and_customer_backups(self):
        with patch('cmnd_linux.application_stage.render_vendor_wars', self.render_wars), \
                patch('cmnd_linux.application_stage.stage_smartcms', self.cms_stage):
            report = stage_application(self.inputs, self.config, self.secrets, self.output, execute=True)
        self.assertEqual(report['state'], 'staged-not-activated')
        self.assertFalse(report['activation_qualified'])
        webapps = self.output / 'payload/tomcat/webapps'
        self.assertFalse((webapps / 'manager').exists())
        for name in WAR_NAMES:
            self.assertEqual((webapps / Path(name).stem / 'WEB-INF/classes/vendor.class').read_bytes(), b'private-test-bytecode')
        self.assertEqual((webapps / 'SmartInstall/Cert/ca.p12').read_bytes(), self.inputs.certificates.ca_p12.read_bytes())
        self.assertEqual((self.output / 'payload/Philips/Cert/ca.p12').read_bytes(), self.inputs.certificates.ca_p12.read_bytes())
        self.assertFalse(list((self.output / 'fresh-schema').rglob('sql_1.sql')))
        self.assertTrue((webapps / 'ROOT/WEB-INF/rewrite.config').is_file())
        self.assertNotIn('unit-test-secret', (self.output / 'manifest.json').read_text())
        with self.assertRaises(FileExistsError):
            stage_application(self.inputs, self.config, self.secrets, self.output, execute=True)

    def test_failed_candidate_is_retained_and_marked_never_activated(self):
        with patch('cmnd_linux.application_stage.render_vendor_wars', side_effect=ValueError('synthetic failure')):
            with self.assertRaises(ValueError):
                stage_application(self.inputs, self.config, self.secrets, self.output, execute=True)
        self.assertTrue((self.output / 'FAILED').is_file())
        self.assertFalse((self.output / 'manifest.json').exists())


if __name__ == '__main__':
    unittest.main()
