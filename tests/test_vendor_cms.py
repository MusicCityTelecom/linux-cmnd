import tempfile
import unittest
from pathlib import Path
import zipfile

from cmnd_linux.vendor_cms import VendorCmsError, configure_cms_rewrite_base, prepare_cms_writable_directories, render_settings, stage_smartcms


SETTINGS = """<?php
$custom_setting = 'preserve me';
$databases['default']['default'] = array(
  'driver' => 'old-driver',
  'database' => 'old-database',
  'username' => 'old-user',
  'password' => 'old-password',
  'host' => 'old-host',
  'prefix' => '',
);
$another_setting = TRUE;
"""


class VendorCmsTests(unittest.TestCase):
    def make_archive(
        self, base: Path, settings: str = SETTINGS, *, htaccess: bool = True, canonical_htaccess: bool = False
    ) -> Path:
        archive = base / "SmartCMS.zip"
        with zipfile.ZipFile(archive, "w") as output:
            output.writestr("SmartCMS/sites/default/settings.php", settings)
            output.writestr("SmartCMS/sites/default/files/.keep", "")
            output.writestr("SmartCMS/index.php", "<?php")
            if htaccess:
                name = "SmartCMS/.htaccess" if canonical_htaccess else "SmartCMS/htbakcess"
                output.writestr(name, "RewriteEngine on\n")
        return archive

    def test_stage_renders_database_and_installs_htaccess(self):
        with tempfile.TemporaryDirectory() as temp:
            base = Path(temp)
            result = stage_smartcms(
                self.make_archive(base),
                base / "stage",
                db_host="db.internal",
                db_port=3307,
                db_user="cmnd-user",
                db_password="pa'ss\\word",
            )
            rendered = result.settings.read_text(encoding="utf-8")
            self.assertIn("'driver' => 'mysql',", rendered)
            self.assertIn("'database' => 'tpvision',", rendered)
            self.assertIn("'host' => 'db.internal',", rendered)
            self.assertIn("'port' => 3307,", rendered)
            self.assertIn("'username' => 'cmnd-user',", rendered)
            self.assertIn("'password' => 'pa\\'ss\\\\word',", rendered)
            self.assertIn("$custom_setting = 'preserve me';", rendered)
            self.assertIn("$another_setting = TRUE;", rendered)
            self.assertEqual(result.writable_files, result.root / "sites" / "default" / "files")
            self.assertTrue((result.root / ".htaccess").is_file())
            self.assertIn('RewriteBase /SmartCMS', (result.root / '.htaccess').read_text())
            self.assertFalse((result.root / "htbakcess").exists())
            self.assertTrue((result.writable_files / 'export').is_dir())
            self.assertTrue((result.writable_files / 'tpvision').is_dir())

    def test_rewrite_base_is_idempotent_and_preserves_rules(self):
        with tempfile.TemporaryDirectory() as temp:
            root = Path(temp)
            path = root / '.htaccess'
            path.write_text('# vendor attribution\n<IfModule mod_rewrite.c>\n  RewriteEngine on\n  RewriteRule ^ index.php [L]\n</IfModule>\n')
            self.assertTrue(configure_cms_rewrite_base(root))
            first = path.read_bytes()
            self.assertFalse(configure_cms_rewrite_base(root))
            self.assertEqual(first, path.read_bytes())
            self.assertIn(b'# vendor attribution', first)
            self.assertIn(b'RewriteRule ^ index.php [L]', first)

    def test_unknown_rewrite_base_is_preserved(self):
        with tempfile.TemporaryDirectory() as temp:
            root = Path(temp)
            path = root / '.htaccess'
            original = 'RewriteEngine on\nRewriteBase /custom\n'
            path.write_text(original)
            with self.assertRaises(VendorCmsError):
                configure_cms_rewrite_base(root)
            self.assertEqual(original, path.read_text())

    def test_export_directory_setup_is_rerunnable_and_does_not_touch_code(self):
        with tempfile.TemporaryDirectory() as temp:
            root = Path(temp) / 'SmartCMS'
            files = root / 'sites/default/files'
            files.mkdir(parents=True)
            code = root / 'index.php'
            code.write_text('unchanged')
            before = code.stat().st_mode
            first = prepare_cms_writable_directories(root)
            self.assertEqual(first, prepare_cms_writable_directories(root))
            self.assertEqual(code.stat().st_mode, before)
            self.assertEqual(code.read_text(), 'unchanged')

    def test_bad_export_destination_fails_before_other_directory_creation(self):
        with tempfile.TemporaryDirectory() as temp:
            root = Path(temp) / 'SmartCMS'
            files = root / 'sites/default/files'
            files.mkdir(parents=True)
            (files / 'tpvision').write_text('preserve')
            with self.assertRaises(VendorCmsError):
                prepare_cms_writable_directories(root)
            self.assertFalse((files / 'export').exists())
            self.assertEqual((files / 'tpvision').read_text(), 'preserve')

    def test_existing_stage_is_refused_without_changes(self):
        with tempfile.TemporaryDirectory() as temp:
            base = Path(temp)
            stage = base / "stage"
            stage.mkdir()
            marker = stage / "keep"
            marker.write_text("unchanged", encoding="utf-8")
            with self.assertRaises(VendorCmsError):
                stage_smartcms(
                    self.make_archive(base), stage,
                    db_host="localhost", db_port=3306, db_user="user", db_password="pass",
                )
            self.assertEqual(marker.read_text(encoding="utf-8"), "unchanged")

    def test_archive_with_canonical_htaccess_is_accepted(self):
        with tempfile.TemporaryDirectory() as temp:
            base = Path(temp)
            result = stage_smartcms(
                self.make_archive(base, canonical_htaccess=True), base / "stage",
                db_host="localhost", db_port=3306, db_user="user", db_password="pass",
            )
            self.assertTrue((result.root / ".htaccess").is_file())

    def test_ambiguous_database_key_fails_closed_and_cleans_stage(self):
        with tempfile.TemporaryDirectory() as temp:
            base = Path(temp)
            duplicate = SETTINGS.replace("  'host' => 'old-host',", "  'host' => 'one',\n  'host' => 'two',")
            stage = base / "stage"
            with self.assertRaisesRegex(VendorCmsError, "exactly one 'host'"):
                stage_smartcms(
                    self.make_archive(base, duplicate), stage,
                    db_host="localhost", db_port=3306, db_user="user", db_password="pass",
                )
            self.assertFalse(stage.exists())

    def test_missing_htbakcess_fails_and_cleans_stage(self):
        with tempfile.TemporaryDirectory() as temp:
            base = Path(temp)
            stage = base / "stage"
            with self.assertRaisesRegex(VendorCmsError, "htbakcess"):
                stage_smartcms(
                    self.make_archive(base, htaccess=False), stage,
                    db_host="localhost", db_port=3306, db_user="user", db_password="pass",
                )
            self.assertFalse(stage.exists())

    def test_invalid_port_is_rejected(self):
        with tempfile.TemporaryDirectory() as temp:
            settings = Path(temp) / "settings.php"
            settings.write_text(SETTINGS, encoding="utf-8")
            with self.assertRaisesRegex(VendorCmsError, "port"):
                render_settings(
                    settings, db_host="localhost", db_port=0, db_user="user", db_password="pass"
                )
            self.assertEqual(settings.read_text(encoding="utf-8"), SETTINGS)


if __name__ == "__main__":
    unittest.main()
