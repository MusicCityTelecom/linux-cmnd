import tempfile
import unittest
from pathlib import Path

from cmnd_linux.cms_runtime import (
    CmsRuntimeConfig,
    CmsRuntimeError,
    generate_cms_runtime_script,
    write_cms_runtime_script,
)


class CmsRuntimeTests(unittest.TestCase):
    def config(self, **changes):
        values = dict(
            cms_root="/opt/cmnd/SmartCMS",
            ca_bundle="/etc/ssl/certs/ca-certificates.crt",
            pgt_storage="/run/cmnd/pgt",
            public_host="cmnd.example.test",
            public_port=8444,
            https=True,
        )
        values.update(changes)
        return CmsRuntimeConfig(**values)

    def test_script_is_cli_and_execute_gated(self):
        script = generate_cms_runtime_script(self.config())
        self.assertIn("PHP_SAPI !== 'cli'", script)
        self.assertIn("getenv('CMND_CMS_EXECUTE') !== '1'", script)
        self.assertLess(script.index("realpath($cmnd_root)"), script.index("drupal_bootstrap("))
        self.assertLess(script.index("is_file($cmnd_ca)"), script.index("drupal_bootstrap("))
        self.assertLess(script.index("is_writable($cmnd_pgt)"), script.index("drupal_bootstrap("))

    def test_native_variable_updates_preserve_tls_validation(self):
        script = generate_cms_runtime_script(self.config())
        self.assertIn("variable_set('cas_cert', $cmnd_ca);", script)
        self.assertIn("variable_set('cas_debugfile', '');", script)
        self.assertIn("variable_set('cas_pgtpath', $cmnd_pgt);", script)
        self.assertNotIn("setNoCasServerValidation", script)
        self.assertNotIn("CURLOPT_SSL_VERIFYPEER", script)
        self.assertNotIn("UPDATE variable", script)
        self.assertNotIn("user_role", script)

    def test_php_literal_encoding_does_not_interpolate(self):
        script = generate_cms_runtime_script(
            self.config(
                cms_root="/opt/cmnd/$release/Smart'CMS",
                ca_bundle="/etc/cmnd/ca\\bundle.pem",
            )
        )
        self.assertIn("'/opt/cmnd/$release/Smart\\'CMS'", script)
        self.assertIn("'/etc/cmnd/ca\\\\bundle.pem'", script)

    def test_private_pgt_storage_checks_are_emitted(self):
        script = generate_cms_runtime_script(self.config())
        self.assertIn("($cmnd_pgt_permissions & 0077) !== 0", script)
        self.assertIn("strpos($cmnd_pgt, $cmnd_root . '/') === 0", script)
        self.assertIn("not writable by the PHP runtime user", script)
        self.assertIn("same OS user", script)

    def test_invalid_paths_and_host_are_rejected(self):
        for change in (
            {"cms_root": "relative/SmartCMS"},
            {"ca_bundle": "/etc/../private/ca.pem"},
            {"pgt_storage": "/opt/cmnd/SmartCMS/pgt"},
            {"public_host": "https://cmnd.example.test"},
            {"public_port": 0},
        ):
            with self.subTest(change=change), self.assertRaises(CmsRuntimeError):
                generate_cms_runtime_script(self.config(**change))

    def test_ipv6_host_header_is_bracketed(self):
        script = generate_cms_runtime_script(self.config(public_host="2001:db8::10"))
        self.assertIn("$_SERVER['HTTP_HOST'] = '[2001:db8::10]:8444';", script)

    def test_writer_is_exclusive(self):
        with tempfile.TemporaryDirectory() as temp:
            destination = Path(temp) / "configure.php"
            write_cms_runtime_script(destination, self.config())
            original = destination.read_text(encoding="utf-8")
            with self.assertRaises(CmsRuntimeError):
                write_cms_runtime_script(destination, self.config(public_port=9444))
            self.assertEqual(destination.read_text(encoding="utf-8"), original)


if __name__ == "__main__":
    unittest.main()
