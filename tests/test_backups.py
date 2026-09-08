import tempfile
import unittest
from pathlib import Path
import zipfile

from cmnd_linux.backups import BackupError, REQUIRED_DATABASES, inspect_windows_backup, prepare_windows_restore


class WindowsBackupTests(unittest.TestCase):
    def create_backup(self, path, omit=None):
        with zipfile.ZipFile(path, "w") as archive:
            for member, database in REQUIRED_DATABASES.items():
                if member != omit:
                    archive.writestr(member, f"-- MySQL dump\n-- Current Database: `{database}`\nSELECT 1;\n")
            archive.writestr("backupinfo.txt", "CMND 7.5.1")

    def test_five_database_backup_is_prepared_safely(self):
        with tempfile.TemporaryDirectory() as temp:
            base = Path(temp); backup = base / "backup.zip"; staging = base / "staging"
            self.create_backup(backup)
            report = inspect_windows_backup(backup)
            self.assertEqual(len(report["databases"]), 5)
            prepared = prepare_windows_restore(backup, staging, None, True)
            self.assertTrue((staging / "SmartInstall" / "SmartInstall.sql").is_file())
            self.assertFalse(prepared["database_import_executed"])

    def test_missing_database_is_rejected(self):
        with tempfile.TemporaryDirectory() as temp:
            backup = Path(temp) / "bad.zip"
            self.create_backup(backup, "SmartInstall/SmartInstall.sql")
            with self.assertRaises(BackupError): inspect_windows_backup(backup)


if __name__ == "__main__": unittest.main()
