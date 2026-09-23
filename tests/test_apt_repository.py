from pathlib import Path
import tempfile
import unittest
from unittest.mock import patch

from scripts.build_apt_repository import build_repository


def debs():
    return [
        Path('linux-cmnd_0.8.0_amd64.deb'),
        Path('cmnd-linux_0.8.0_all.deb'),
        Path('cmnd-vendor-759_7.5.9-1_all.deb'),
        Path('cmnd-tomcat9_9.0.121-1_all.deb'),
    ]


class AptRepositoryTests(unittest.TestCase):
    def package_metadata(self, path):
        name = path.name
        if name.startswith('cmnd-linux_'):
            package, version, architecture = 'cmnd-linux', '0.8.0', 'all'
        elif name.startswith('cmnd-vendor-759_'):
            package, version, architecture = 'cmnd-vendor-759', '7.5.9-1', 'all'
        elif name.startswith('cmnd-tomcat9_'):
            package, version, architecture = 'cmnd-tomcat9', '9.0.121-1', 'all'
        else:
            package, version, architecture = 'linux-cmnd', '0.8.0', 'amd64'
        return {'package': package, 'version': version, 'architecture': architecture, 'size': 1234}

    def test_plan_requires_complete_four_package_set(self):
        with tempfile.TemporaryDirectory() as temp, patch('scripts.build_apt_repository.validate', side_effect=self.package_metadata):
            with self.assertRaisesRegex(ValueError, 'requires exactly'):
                build_repository(Path(temp) / 'repo', debs()[:3], codename='noble',
                                 suite='development', signing_key=None,
                                 allow_unsigned_development=True, execute=False)

    def test_unsigned_stable_repository_is_refused(self):
        with tempfile.TemporaryDirectory() as temp, patch('scripts.build_apt_repository.validate', side_effect=self.package_metadata):
            with self.assertRaisesRegex(ValueError, 'signed metadata required'):
                build_repository(Path(temp) / 'repo', debs(), codename='noble', suite='stable',
                                 signing_key=None, allow_unsigned_development=False, execute=False)

    def test_development_plan_is_non_mutating(self):
        with tempfile.TemporaryDirectory() as temp, patch('scripts.build_apt_repository.validate', side_effect=self.package_metadata):
            output = Path(temp) / 'repo'
            plan = build_repository(output, debs(), codename='noble', suite='development',
                                    signing_key=None, allow_unsigned_development=True, execute=False)
            self.assertFalse(plan['executed'])
            self.assertFalse(plan['signed'])
            self.assertEqual(plan['tooling_version'], '0.8.0')
            self.assertEqual(plan['vendor_version'], '7.5.9-1')
            self.assertEqual(plan['tomcat_version'], '9.0.121-1')
            self.assertFalse(output.exists())

    def test_mismatched_tooling_package_versions_are_refused(self):
        def metadata(path):
            result = self.package_metadata(path)
            if path.name.startswith('cmnd-linux_'):
                result = dict(result, version='0.8.1')
            return result
        with tempfile.TemporaryDirectory() as temp, patch('scripts.build_apt_repository.validate', side_effect=metadata):
            with self.assertRaisesRegex(ValueError, 'versions must match'):
                build_repository(Path(temp) / 'repo', debs(), codename='noble',
                                 suite='development', signing_key='TESTKEY',
                                 allow_unsigned_development=False, execute=False)

    def test_unreviewed_vendor_and_tomcat_versions_are_refused(self):
        for prefix, version, expected in (
            ('cmnd-vendor-759_', '7.5.9-2', 'unexpected vendor package version'),
            ('cmnd-tomcat9_', '9.0.122-1', 'unexpected Tomcat package version'),
        ):
            def metadata(path, prefix=prefix, version=version):
                result = self.package_metadata(path)
                if path.name.startswith(prefix):
                    result = dict(result, version=version)
                return result
            with tempfile.TemporaryDirectory() as temp, patch('scripts.build_apt_repository.validate', side_effect=metadata):
                with self.assertRaisesRegex(ValueError, expected):
                    build_repository(Path(temp) / 'repo', debs(), codename='noble',
                                     suite='development', signing_key='TESTKEY',
                                     allow_unsigned_development=False, execute=False)


if __name__ == '__main__':
    unittest.main()
