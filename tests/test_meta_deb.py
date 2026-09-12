import tarfile
import unittest
from io import BytesIO

from scripts.build_meta_deb import build


def ar_members(blob):
    assert blob.startswith(b'!<arch>\n')
    pos, result = 8, {}
    while pos < len(blob):
        header = blob[pos:pos + 60]
        pos += 60
        name = header[:16].decode().strip().rstrip('/')
        size = int(header[48:58].decode().strip())
        result[name] = blob[pos:pos + size]
        pos += size + size % 2
    return result


class MetaDebianPackageTests(unittest.TestCase):
    def test_user_facing_package_depends_on_exact_payload_version(self):
        package = build()
        self.assertTrue(package.name.startswith('cmnd-linux_'))
        members = ar_members(package.read_bytes())
        with tarfile.open(fileobj=BytesIO(members['control.tar.gz']), mode='r:gz') as archive:
            control = archive.extractfile('control').read().decode()
        self.assertIn('Package: cmnd-linux\n', control)
        self.assertIn('Architecture: all\n', control)
        self.assertIn('Depends: linux-cmnd (= 0.8.0)\n', control)

    def test_meta_build_is_reproducible(self):
        first = build().read_bytes()
        self.assertEqual(first, build().read_bytes())


if __name__ == '__main__':
    unittest.main()
