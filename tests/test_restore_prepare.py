import json
from pathlib import Path
import tempfile
import unittest
import zipfile

from cmnd_linux.restore_prepare import (
    RestorePreparationError,
    WINDOWS_748_DATABASES,
    prepare_windows_748_sql,
)


class RestorePreparationTests(unittest.TestCase):
    def create_backup(self, path, *, mutate=None, definers=11, version="7.4.8"):
        with zipfile.ZipFile(path, "w") as archive:
            archive.writestr("backupinfo.txt", f"CMND\\ version={version}\n")
            archive.writestr("customer/private-image.jpg", b"not extracted")
            for member, (windows_database, database, history) in WINDOWS_748_DATABASES.items():
                sql = f"-- Host: localhost    Database: {windows_database}\nUSE `{windows_database}`;\n"
                if history:
                    sql += (
                        f"DROP TABLE IF EXISTS `{history}`;\n"
                        f"CREATE TABLE `{history}` (`installed_rank` int);\n"
                        f"INSERT INTO `{history}` VALUES (1);\n"
                    )
                else:
                    sql += "DROP TABLE IF EXISTS `node`; CREATE TABLE `node` (`id` int);\n"
                if database == "smartinstall":
                    sql += "\n".join(
                        "/*!50017 DEFINER=`siuser`@`localhost`*/ SELECT 1;"
                        for _ in range(definers)
                    )
                    sql += (
                        "\nINSERT INTO `logs` VALUES "
                        "('DEFINER=`siuser`@`localhost`; USE CustomerSecret');\n"
                    )
                if mutate:
                    sql = mutate(database, sql)
                archive.writestr(member, sql)

    def test_stages_only_normalized_sql_and_content_free_manifest(self):
        with tempfile.TemporaryDirectory() as temporary:
            base = Path(temporary)
            backup, stage = base / "backup.zip", base / "stage"
            self.create_backup(backup)
            manifest = prepare_windows_748_sql(backup, stage)
            self.assertFalse(manifest["database_execution_performed"])
            self.assertFalse(manifest["customer_content_extracted"])
            self.assertEqual(len(manifest["databases"]), 5)
            self.assertEqual(
                {path.name for path in stage.iterdir()},
                {f"{name}.sql" for name in manifest["databases"]} | {"manifest.json"},
            )
            smartinstall = (stage / "smartinstall.sql").read_text(encoding="utf-8")
            self.assertIn("USE `smartinstall`", smartinstall)
            self.assertEqual(smartinstall.count("DEFINER=CURRENT_USER"), 11)
            self.assertIn("'DEFINER=`siuser`@`localhost`; USE CustomerSecret'", smartinstall)
            self.assertEqual(
                manifest["databases"]["smartinstall"]["definers_normalized"], 11
            )
            serialized = json.dumps(manifest)
            self.assertNotIn("CustomerSecret", serialized)
            self.assertFalse((stage / "customer").exists())

    def test_existing_destination_is_rejected(self):
        with tempfile.TemporaryDirectory() as temporary:
            base = Path(temporary)
            backup, stage = base / "backup.zip", base / "stage"
            self.create_backup(backup)
            stage.mkdir()
            with self.assertRaises(RestorePreparationError):
                prepare_windows_748_sql(backup, stage)

    def test_cross_schema_reference_fails_before_staging(self):
        with tempfile.TemporaryDirectory() as temporary:
            base = Path(temporary)
            backup, stage = base / "backup.zip", base / "stage"

            def mutate(database, sql):
                return sql + "\nSELECT * FROM `other_customer`.`users`;" if database == "cas" else sql

            self.create_backup(backup, mutate=mutate)
            with self.assertRaises(RestorePreparationError):
                prepare_windows_748_sql(backup, stage)
            self.assertFalse(stage.exists())

    def test_grant_fails_before_staging(self):
        with tempfile.TemporaryDirectory() as temporary:
            base = Path(temporary)
            backup, stage = base / "backup.zip", base / "stage"

            def mutate(database, sql):
                return sql + "\nGRANT ALL ON cas.* TO 'x'@'localhost';" if database == "cas" else sql

            self.create_backup(backup, mutate=mutate)
            with self.assertRaises(RestorePreparationError):
                prepare_windows_748_sql(backup, stage)
            self.assertFalse(stage.exists())

    def test_unproven_definer_count_fails_closed(self):
        with tempfile.TemporaryDirectory() as temporary:
            base = Path(temporary)
            backup, stage = base / "backup.zip", base / "stage"
            self.create_backup(backup, definers=10)
            with self.assertRaises(RestorePreparationError):
                prepare_windows_748_sql(backup, stage)
            self.assertFalse(stage.exists())

    def test_missing_history_fails_closed(self):
        with tempfile.TemporaryDirectory() as temporary:
            base = Path(temporary)
            backup, stage = base / "backup.zip", base / "stage"

            def mutate(database, sql):
                return sql.replace("schema_version", "ordinary_table") if database == "cas" else sql

            self.create_backup(backup, mutate=mutate)
            with self.assertRaises(RestorePreparationError):
                prepare_windows_748_sql(backup, stage)
            self.assertFalse(stage.exists())

    def test_wrong_backup_version_is_rejected(self):
        with tempfile.TemporaryDirectory() as temporary:
            base = Path(temporary)
            backup, stage = base / "backup.zip", base / "stage"
            self.create_backup(backup, version="7.5.1")
            with self.assertRaises(RestorePreparationError):
                prepare_windows_748_sql(backup, stage)
            self.assertFalse(stage.exists())


if __name__ == "__main__":
    unittest.main()
