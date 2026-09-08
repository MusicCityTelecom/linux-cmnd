import tempfile
import unittest
from pathlib import Path

from cmnd_linux.config import Config
from cmnd_linux.runtime import REQUIRED_WARS, RuntimeErrorCMND, install_release, render_runtime_config, rollback, status


class RuntimeLifecycleTests(unittest.TestCase):
    def test_invalid_release_rejected_before_source_or_destination_access(self):
        with tempfile.TemporaryDirectory() as temp:
            root = Path(temp) / 'runtime'
            for release in ('../outside', '/absolute', 'C:\\outside', '.', '..', 'a/b'):
                with self.subTest(release=release), self.assertRaises(RuntimeErrorCMND):
                    install_release(Path(temp) / 'missing', root, release, execute=True)
                self.assertFalse(root.exists())

    def test_dry_run_and_idempotent_install(self):
        with tempfile.TemporaryDirectory() as temp:
            base = Path(temp); source = base / "input"; root = base / "runtime"; source.mkdir()
            for name in REQUIRED_WARS:
                (source / name).write_bytes(b"synthetic")
            plan = install_release(source, root, "7.5.9-test", execute=False)
            self.assertFalse(root.exists())
            self.assertFalse(plan["executed"])
            first = install_release(source, root, "7.5.9-test", execute=True)
            second = install_release(source, root, "7.5.9-test", execute=True)
            self.assertEqual(first["release"], second["release"])
            self.assertEqual(status(root)["current_release"], "7.5.9-test")

    def test_rollback_requires_installed_release(self):
        with tempfile.TemporaryDirectory() as temp:
            with self.assertRaises(Exception):
                rollback(Path(temp), "missing", execute=True)

    def test_runtime_ports_are_rendered_from_one_config(self):
        with tempfile.TemporaryDirectory() as temp:
            config = Config("isolated", "127.0.0.1", "http://127.0.0.1:8080", ("127.0.0.0/8",), (),
                            tomcat_http=8080, tomcat_https=8443, apache_http=8082,
                            apache_https=8444, database_port=3307)
            output = Path(temp) / "rendered"
            render_runtime_config(config, output, True)
            self.assertIn("CMND_TOMCAT_HTTP_PORT=8080", (output / "tomcat.env").read_text())
            self.assertEqual((output / "compose.env").read_text(), "CMND_DATABASE_PORT=3307\n")


if __name__ == "__main__":
    unittest.main()
