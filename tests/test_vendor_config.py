import json
from pathlib import Path
import tempfile
import unittest
from zipfile import ZIP_DEFLATED, ZipFile, ZipInfo

from cmnd_linux.vendor_config import VendorRenderConfig, render_vendor_wars


COMMON = "server.port=1\nserver.name=old\ncms.port=2\ncms.name=old\ncas.port=1\ncas.name=old\npublished.port=1\npublished.name=old\n"


class VendorConfigTests(unittest.TestCase):
    def setUp(self):
        self.temporary = tempfile.TemporaryDirectory()
        self.root = Path(self.temporary.name)
        self.source = self.root / "vendor"
        self.source.mkdir()
        entries = {
            "cas.war": {
                "WEB-INF/classes/application.yml": "cas:\n  authn:\n    jdbc:\n      query[0]:\n        password: old\n        url: jdbc:mysql://old/cas\n",
                "WEB-INF/classes/services/localhost-100.json": json.dumps({"serviceId": "old", "name": "old", "proxyPolicy": {"pattern": "old"}}),
            },
            "smartcms.war": {
                "WEB-INF/classes/application.properties": "db.url=old\ndb.password=old\n",
                "WEB-INF/classes/config.properties": COMMON,
            },
            "smartcontrol.war": {
                "WEB-INF/classes/application.properties": "smartcms.content.url=old\nsmartcms.content.list.url=old\nsmartcontrol.system.path=C:/smartcontrol\nsecurity.user.password=old\nserver.port=1\n",
                "WEB-INF/classes/application-production.properties": "cas.url=old\ncas.service-url=old\ncas.proxy.callback-url=old\nspring.datasource.url=old\nspring.datasource.password=old\n",
            },
            "SmartInstall.war": {
                "WEB-INF/config.properties": COMMON + "server.https.port=3\ncms.https.port=4\ncas.https.port=3\npublished.https.port=3\ncas.url=old\ncms.url=old\ncas.service-url=old\nlistener.load=on\ncert.ca.password=old\nsecurity.pwd=old\nmysql.ip=old\nmysql.port=9\nsurvey.email.smtp.account=old\nsurvey.email.smtp.pwd=old\ncloud.server.auth.clientid=old\ncloud.server.auth.clientsecret=old\n",
                "WEB-INF/applicationContext.xml": '<beans><property name="password" value="old"/></beans>',
                "WEB-INF/log4j2.xml": "<Configuration><Loggers>\n    </Loggers></Configuration>",
            },
            "usermanagement.war": {
                "WEB-INF/classes/application.properties": "security.user.password=old\nserver.port=1\n",
                "WEB-INF/classes/application-production.properties": "cas.url=old\ncas.service-url=old\nspring.datasource.url=old\nspring.datasource.password=old\n",
            },
        }
        for war, resources in entries.items():
            with ZipFile(self.source / war, "w") as archive:
                binary = ZipInfo("WEB-INF/classes/vendor.class")
                binary.external_attr = 0o100640 << 16
                binary.compress_type = ZIP_DEFLATED
                archive.writestr(binary, b"\xca\xfe\xba\xbeprivate-vendor-bytecode")
                for name, value in resources.items():
                    archive.writestr(name, value)

    def tearDown(self):
        self.temporary.cleanup()

    @staticmethod
    def config(**secret_changes):
        secrets = {
            "cas_db_password": "cafe0123",
            "smartcms_db_password": "beef4567",
            "smartcontrol_db_password": "fade89ab",
            "smartinstall_db_password": "facecafe",
            "application_security_password": "deadbeef",
            "cert_ca_password": "0123abcd",
        }
        secrets.update(secret_changes)
        return VendorRenderConfig("cmnd.example.test", {"tomcat_http": 8080, "tomcat_https": 8443, "apache_http": 8082, "apache_https": 8444, "database": 3307}, secrets)

    def test_renders_and_preserves_binary_metadata(self):
        stage = self.root / "stage"
        outputs = render_vendor_wars(self.source, stage, self.config())
        self.assertEqual(len(outputs), 5)
        for source in self.source.glob("*.war"):
            with ZipFile(source) as before, ZipFile(stage / source.name) as after:
                self.assertEqual(after.read("WEB-INF/classes/vendor.class"), before.read("WEB-INF/classes/vendor.class"))
                self.assertEqual(after.getinfo("WEB-INF/classes/vendor.class").external_attr, before.getinfo("WEB-INF/classes/vendor.class").external_attr)
        with ZipFile(stage / "smartcontrol.war") as archive:
            app = archive.read("WEB-INF/classes/application.properties").decode()
            production = archive.read("WEB-INF/classes/application-production.properties").decode()
            self.assertIn("smartcontrol.system.path=/var/lib/cmnd/smartcontrol", app)
            self.assertIn(r"jdbc\:mysql\://127.0.0.1\:3307/smartcontroldb", production)
        with ZipFile(stage / "SmartInstall.war") as archive:
            props = archive.read("WEB-INF/config.properties").decode()
            self.assertIn("listener.load=off", props)
            self.assertIn("server.httpsport=8443", props)
            self.assertIn("tomcat.http.port=8080", props)
            self.assertIn("cms.port.https=8444", props)
            self.assertIn("philips.path=/opt/Philips/", props)
            self.assertIn("survey.email.smtp.account=\n", props)
            self.assertIn("facecafe", archive.read("WEB-INF/applicationContext.xml").decode())
            self.assertIn('name="com.tpvision.smartinstall.util.Configs" level="off"', archive.read("WEB-INF/log4j2.xml").decode())
        with ZipFile(stage / "cas.war") as archive:
            service = json.loads(archive.read("WEB-INF/classes/services/localhost-100.json"))
            self.assertEqual(service["serviceId"], r"^https?://cmnd\.example\.test(?::\d+)?/.*")

    def test_refuses_nonempty_stage(self):
        stage = self.root / "stage"
        stage.mkdir()
        (stage / "owned.txt").write_text("do not overwrite")
        with self.assertRaises(FileExistsError):
            render_vendor_wars(self.source, stage, self.config())

    def test_requires_every_secret(self):
        with self.assertRaisesRegex(ValueError, "cas_db_password"):
            render_vendor_wars(self.source, self.root / "stage", self.config(cas_db_password=""))

    def test_escapes_property_and_yaml_secrets(self):
        tricky = " leading:=#!\\caf\N{LATIN SMALL LETTER E WITH ACUTE}"
        stage = self.root / "escaped"
        render_vendor_wars(self.source, stage, self.config(cas_db_password=tricky))
        with ZipFile(stage / "usermanagement.war") as archive:
            props = archive.read("WEB-INF/classes/application-production.properties").decode()
            self.assertIn(r"spring.datasource.password=\ leading\:\=\#\!\\caf\u00e9", props)
        with ZipFile(stage / "cas.war") as archive:
            yaml = archive.read("WEB-INF/classes/application.yml").decode()
            self.assertIn(r'password: " leading:=#!\\caf\u00e9"', yaml)


if __name__ == "__main__":
    unittest.main()
