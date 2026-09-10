"""Execute the package configuration guard in an isolated temporary directory."""
import json
import os
from pathlib import Path
import subprocess
import tempfile
import unittest


@unittest.skipUnless(os.name == 'posix' and getattr(os, 'geteuid', lambda: -1)() == 0,
                     'POSIX root-owned package configuration semantics')
class PackageConfigPreservationTests(unittest.TestCase):
    def guard(self, root):
        source = (Path(__file__).resolve().parents[1] / 'packaging/debian/postinst').read_text()
        fragment = source[source.index('if [ -e /etc/cmnd/deployment.json ]'):source.index('for rendered in')]
        return subprocess.run(['sh', '-eu', '-c', fragment.replace('/etc/cmnd', str(root))],
                              capture_output=True, timeout=20)

    def test_native_upgrade_preserves_operator_environments(self):
        with tempfile.TemporaryDirectory(prefix='cmnd-package-') as directory:
            root = Path(directory)
            (root / 'deployment.json').write_text(json.dumps({'managed_by': 'linux-cmnd-native'}))
            for name in ('tomcat.env', 'apache.env', 'compose.env'):
                (root / name).write_text('operator supplied value\n')
            before = {p.name: p.read_bytes() for p in root.iterdir()}
            result = self.guard(root)
            self.assertEqual(result.returncode, 0, result.stderr)
            self.assertEqual(before, {p.name: p.read_bytes() for p in root.iterdir()})

    def test_unrecognized_or_symlink_marker_is_rejected(self):
        with tempfile.TemporaryDirectory(prefix='cmnd-package-') as directory:
            root = Path(directory)
            marker = root / 'deployment.json'
            marker.write_text(json.dumps({'managed_by': 'unrecognized'}))
            self.assertNotEqual(self.guard(root).returncode, 0)
            marker.rename(root / 'other.json')
            marker.symlink_to(root / 'other.json')
            self.assertNotEqual(self.guard(root).returncode, 0)

    def test_group_writable_marker_is_rejected(self):
        with tempfile.TemporaryDirectory(prefix='cmnd-package-') as directory:
            root = Path(directory)
            marker = root / 'deployment.json'
            marker.write_text(json.dumps({'managed_by': 'linux-cmnd-native'}))
            marker.chmod(0o660)
            self.assertNotEqual(self.guard(root).returncode, 0)
