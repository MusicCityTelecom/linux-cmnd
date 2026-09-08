import tempfile
import unittest
from pathlib import Path

from cmnd_linux.runtime import REQUIRED_WARS, install_release, rollback, status


class RuntimeLifecycleTests(unittest.TestCase):
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


if __name__ == "__main__":
    unittest.main()
