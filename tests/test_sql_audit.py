import unittest

from cmnd_linux.sql_audit import CMND_DATABASES, audit_database_bundle, audit_sql


class SqlAuditTests(unittest.TestCase):
    def test_detects_scope_and_privileged_constructs_without_returning_sql(self):
        sql = """
        -- GRANT in an ordinary comment is inert
        /*!40101 SET NAMES utf8 */;
        USE `smartinstall`;
        CREATE USER 'service'@'localhost';
        GRANT SELECT ON smartinstall.* TO 'service'@'localhost';
        CREATE DEFINER=`root`@`localhost` PROCEDURE p() SELECT 1;
        SET GLOBAL event_scheduler = ON;
        LOAD DATA LOCAL INFILE '/private/input' INTO TABLE t;
        """
        report = audit_sql(sql, "smartinstall")
        self.assertTrue(report["accepted_scope"])
        self.assertTrue(report["has_executable_comments"])
        self.assertEqual(report["database_names"], ["smartinstall"])
        self.assertEqual(
            set(report["privileged_constructs"]),
            {"create_user", "definer", "grant", "load_data_local", "set_global"},
        )
        self.assertNotIn("sql", report)
        self.assertNotIn("service", repr(report))
        self.assertNotIn("/private/input", repr(report))

    def test_detects_mysql_client_and_file_escape_constructs(self):
        report = audit_sql(
            "USE cas;\nSYSTEM id\n\\! whoami\nSOURCE other.sql\n"
            "SELECT LOAD_FILE('/x') INTO DUMPFILE '/y';"
        )
        self.assertEqual(report["statement_counts"]["mysql_system"], 3)
        self.assertEqual(report["statement_counts"]["file_read"], 1)
        self.assertEqual(report["statement_counts"]["file_write"], 1)

    def test_unknown_or_mismatched_database_is_rejected(self):
        unknown = audit_sql("USE customer_data", "cas")
        self.assertFalse(unknown["accepted_scope"])
        self.assertEqual(unknown["unknown_database_names"], ["customer_data"])
        self.assertFalse(unknown["expected_database_seen"])

    def test_unqualified_mysqldump_uses_external_target_mapping(self):
        report = audit_sql("DROP TABLE IF EXISTS `users`;", "cas")
        self.assertTrue(report["expected_database_seen"])
        self.assertTrue(report["accepted_scope"])

    def test_quoted_qualified_database_is_checked(self):
        report = audit_sql("SELECT * FROM `foreign_db`.`users`;", "cas")
        self.assertFalse(report["accepted_scope"])
        self.assertEqual(report["unknown_database_names"], ["foreign_db"])

    def test_qualified_column_alias_is_not_a_database(self):
        report = audit_sql(
            "SELECT `ap`.`x` FROM `accounts` AS `ap` "
            "JOIN smartinstall.devices d ON `ap`.`id` = d.account_id;",
            "smartinstall",
        )
        self.assertEqual(report["database_names"], ["smartinstall"])
        self.assertTrue(report["accepted_scope"])

    def test_sql_looking_literals_are_ignored(self):
        sql = (
            "INSERT INTO logs(message) VALUES "
            "('please USE admin; GRANT ALL ON evil.*; FROM `other`.`x`'),"
            "('escaped \\' quote DROP DATABASE customer'),"
            "('doubled '' quote SET GLOBAL event_scheduler=ON'),"
            '("double quoted USE analytics and CREATE USER x");'
        )
        report = audit_sql(sql, "smartinstall")
        self.assertEqual(report["database_names"], [])
        self.assertEqual(report["privileged_constructs"], [])
        self.assertFalse(report["has_executable_comments"])

    def test_executable_comment_body_is_scanned_but_its_literals_are_not(self):
        report = audit_sql(
            "/*!50000 CREATE DEFINER='root'@'localhost' PROCEDURE p() "
            "SELECT 'DROP DATABASE fake' */;"
        )
        self.assertTrue(report["has_executable_comments"])
        self.assertEqual(report["statement_counts"]["definer"], 1)
        self.assertEqual(report["statement_counts"]["drop_database"], 0)

    def test_bundle_requires_exact_five_database_names(self):
        documents = {name: f"USE `{name}`; SELECT 1;" for name in CMND_DATABASES}
        report = audit_database_bundle(documents)
        self.assertTrue(report["complete"])
        self.assertTrue(report["accepted_scope"])
        del documents["cas"]
        documents["other"] = "USE other;"
        report = audit_database_bundle(documents)
        self.assertFalse(report["complete"])
        self.assertEqual(report["missing_databases"], ["cas"])
        self.assertEqual(report["unexpected_databases"], ["other"])

    def test_drop_detection_and_comments(self):
        report = audit_sql(
            "# DROP DATABASE ignored\n"
            "/* DROP USER ignored */\n"
            "USE smartcms; DROP TABLE IF EXISTS website; DROP DATABASE smartcms;"
        )
        self.assertEqual(report["statement_counts"]["drop_table"], 1)
        self.assertEqual(report["statement_counts"]["drop_database"], 1)
        self.assertIn("drop_database", report["privileged_constructs"])


if __name__ == "__main__":
    unittest.main()
