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
    ]


class AptRepositoryTests(unittest.TestCase):
    def package_metadata(self, path):
        name = path.name
        if name.startswith('cmnd-linux_'):
            package, version, architecture = 'cmnd-linux', '0.8.0', 'all'
        elif name.startswith('cmnd-vendor-759_'):
            package, version, architecture = 'cmnd-vendor-759', '7.5.9-1', 'all'
        else:
            package, version, architecture = 'linux-cmnd', '0.8.0', 'amd64'
        return {'package': package, 'version': version, 'architecture': architecture, 'size': 1234}

    def test_plan_requires_tooling_meta_and_vendor_packages(self):
        with tempfile.TemporaryDirectory() as temp, patch('scripts.build_apt_repository.validate_deb', side_effect=self.package_metadata):
            output = Path(temp) / 'repo'
            with self.assertRaisesRegex(ValueError, 'requires exactly'):
                build_repository(output, debs()[:2], codename='noble', suite='development',
                                 signing_key=None, allow_unsigned_development=True, execute=False)

    def test_unsigned_stable_repository_is_refused(self):
        with tempfile.TemporaryDirectory() as temp, patch('scripts.build_apt_repository.validate_deb', side_effect=self.package_metadata):
            with self.assertRaisesRegex(ValueError, 'signed repository metadata is required'):
                build_repository(Path(temp) / 'repo', debs(), codename='noble', suite='stable',
                                 signing_key=None, allow_unsigned_development=False, execute=False)

    def test_development_plan_is_non_mutating_and_accepts_explicit_unsigned_mode(self):
        with tempfile.TemporaryDirectory() as temp, patch('scripts.build_apt_repository.validate_deb', side_effect=self.package_metadata):
            output = Path(temp) / 'repo'
            plan = build_repository(output, debs(), codename='noble', suite='development',
                                    signing_key=None, allow_unsigned_development=True, execute=False)
            self.assertFalse(plan['executed'])
            self.assertFalse(plan['signed'])
            self.assertEqual(plan['tooling_version'], '0.8.0')
            self.assertEqual(plan['vendor_version'], '7.5.9-1')
            self.assertFalse(output.exists())

    def test_mismatched_tooling_package_versions_are_refused(self):
        def metadata(path):
            result = self.package_metadata(path)
            if path.name.startswith('cmnd-linux_'):
                result = dict(result, version='0.8.1')
            return result

        with tempfile.TemporaryDirectory() as temp, patch('scripts.build_apt_repository.validate_deb', side_effect=metadata):
            with self.assertRaisesRegex(ValueError, 'same version'):
                build_repository(Path(temp) / 'repo', debs(), codename='noble', suite='development',
                                 signing_key='TESTKEY', allow_unsigned_development=False, execute=False)

    def test_unreviewed_vendor_package_version_is_refused(self):
        def metadata(path):
            result = self.package_metadata(path)
            if path.name.startswith('cmnd-vendor-759_'):
                result = dict(result, version='7.5.9-2')
            return result

        with tempfile.TemporaryDirectory() as temp, patch('scripts.build_apt_repository.validate_deb', side_effect=metadata):
            with self.assertRaisesRegex(ValueError, 'reviewed vendor package version'):
                build_repository(Path(temp) / 'repo', debs(), codename='noble', suite='development',
                                 signing_key='TESTKEY', allow_unsigned_development=False, execute=False)


if __name__ == '__main__':
    unittest.main()
