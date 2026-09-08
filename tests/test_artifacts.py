import tempfile
import unittest
from pathlib import Path
import zipfile

from cmnd_linux.artifacts import UnsafeArchive, safe_extract_zip


class ArchiveSafetyTests(unittest.TestCase):
    def make_zip(self, root, entries):
        archive = Path(root) / "input.zip"
        with zipfile.ZipFile(archive, "w") as zf:
            for name, body in entries:
                zf.writestr(name, body)
        return archive

    def test_windows_separators_are_normalized(self):
        with tempfile.TemporaryDirectory() as temp:
            archive = self.make_zip(temp, [(r"one\two.txt", b"ok")])
            out = Path(temp) / "out"
            self.assertEqual(safe_extract_zip(archive, out), ["one/two.txt"])
            self.assertEqual((out / "one" / "two.txt").read_bytes(), b"ok")

    def test_traversal_rejected_before_any_write(self):
        with tempfile.TemporaryDirectory() as temp:
            archive = self.make_zip(temp, [("good.txt", b"good"), ("../escape", b"bad")])
            out = Path(temp) / "out"
            with self.assertRaises(UnsafeArchive):
                safe_extract_zip(archive, out)
            self.assertFalse((out / "good.txt").exists())

    def test_casefold_collision_rejected(self):
        with tempfile.TemporaryDirectory() as temp:
            archive = self.make_zip(temp, [("A.txt", b"one"), ("a.txt", b"two")])
            with self.assertRaises(UnsafeArchive):
                safe_extract_zip(archive, Path(temp) / "out")


if __name__ == "__main__":
    unittest.main()
