import tarfile
import unittest
from io import BytesIO

from scripts.build_deb import build


def ar_members(blob):
    assert blob.startswith(b"!<arch>\n")
    pos, result = 8, {}
    while pos < len(blob):
        header = blob[pos:pos + 60]; pos += 60
        name = header[:16].decode().strip().rstrip("/")
        size = int(header[48:58].decode().strip())
        result[name] = blob[pos:pos + size]; pos += size + size % 2
    return result


class DebianPackageTests(unittest.TestCase):
    def test_package_contains_cli_config_and_source_without_vendor_payload(self):
        package = build(); members = ar_members(package.read_bytes())
        self.assertEqual(set(members), {"debian-binary", "control.tar.gz", "data.tar.gz"})
        with tarfile.open(fileobj=BytesIO(members["control.tar.gz"]), mode="r:gz") as archive:
            control_names = set(archive.getnames())
            for name in ('preinst', 'postinst', 'prerm', 'postrm'):
                self.assertNotIn(b'\r', archive.extractfile(name).read())
        self.assertTrue({"control", "preinst", "postinst", "prerm", "postrm", "conffiles"} <= control_names)
        with tarfile.open(fileobj=BytesIO(members["data.tar.gz"]), mode="r:gz") as archive:
            names = set(archive.getnames())
            self.assertTrue(archive.getmember('etc/cmnd').isdir())
            self.assertTrue(archive.getmember('usr/bin').isdir())
        self.assertIn("usr/bin/cmndctl", names)
        self.assertIn("usr/bin/cmnd-install", names)
        self.assertIn("usr/share/doc/linux-cmnd/docs/EVALUATION-GUIDE.md", names)
        self.assertIn("etc/cmnd/cmnd.toml", names)
        self.assertFalse(any(name.lower().endswith((".war", ".exe", ".p12", ".pcapng")) for name in names))

    def test_build_is_reproducible(self):
        first = build().read_bytes()
        self.assertEqual(first, build().read_bytes())


if __name__ == "__main__": unittest.main()
