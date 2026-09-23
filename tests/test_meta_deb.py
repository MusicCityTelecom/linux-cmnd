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
    def test_user_facing_package_contains_complete_apt_transaction(self):
        package = build()
        self.assertTrue(package.name.startswith('cmnd-linux_'))
        members = ar_members(package.read_bytes())
        with tarfile.open(fileobj=BytesIO(members['control.tar.gz']), mode='r:gz') as archive:
            names = set(archive.getnames())
            control = archive.extractfile('control').read().decode()
        self.assertIn('Package: cmnd-linux\n', control)
        self.assertIn('Architecture: all\n', control)
        self.assertIn('linux-cmnd (= 0.8.0)', control)
        self.assertIn('cmnd-vendor-759 (= 7.5.9-1)', control)
        self.assertIn('cmnd-tomcat9 (= 9.0.121-1)', control)
        self.assertTrue({'templates', 'config', 'postinst', 'prerm', 'postrm'} <= names)
        with tarfile.open(fileobj=BytesIO(members['data.tar.gz']), mode='r:gz') as archive:
            data_names = set(archive.getnames())
        self.assertIn('usr/lib/cmnd/packages/linux-cmnd_0.8.0_amd64.deb', data_names)

    def test_meta_build_is_reproducible(self):
        first = build().read_bytes()
        self.assertEqual(first, build().read_bytes())


if __name__ == '__main__':
    unittest.main()
