from pathlib import Path
import os
import tempfile
import unittest
from unittest.mock import patch

from cmnd_linux.config import ConfigError
from cmnd_linux.native_deploy import os_support, service_units, validate_image, wait_database, wait_php, wait_ready, write_new, https_origin, validate_baseline


class NativeDeploymentTests(unittest.TestCase):
    def test_management_origin_uses_browser_default_port_serialization(self):
        self.assertEqual(https_origin('192.0.2.10', 443), 'https://192.0.2.10')
        self.assertEqual(https_origin('192.0.2.10', 8444), 'https://192.0.2.10:8444')

    def test_matching_rollback_installer_is_mandatory(self):
        import subprocess
        from cmnd_linux import __version__
        with tempfile.TemporaryDirectory() as root:
            package = Path(root) / 'baseline.deb'
            with self.assertRaises(ConfigError):
                validate_baseline(package)
            package.write_bytes(b'synthetic package')
            values = {'Package': 'linux-cmnd', 'Architecture': 'amd64', 'Version': __version__}
            def run(args, **kwargs):
                return subprocess.CompletedProcess(args, 0, values[args[-1]].encode(), b'')
            with patch('cmnd_linux.native_deploy.subprocess.run', side_effect=run):
                validate_baseline(package)
                values['Version'] = '0.0.1'
                with self.assertRaises(ConfigError):
                    validate_baseline(package)

    @unittest.skipUnless(os.name == 'posix', 'POSIX file permission semantics')
    def test_explicit_permissions_survive_private_umask(self):
        with tempfile.TemporaryDirectory() as root:
            previous = os.umask(0o077)
            try:
                for mode in (0o600, 0o640, 0o644):
                    path = Path(root) / str(mode)
                    write_new(path, 'synthetic configuration', mode)
                    self.assertEqual(path.stat().st_mode & 0o777, mode)
            finally:
                os.umask(previous)

    def test_supported_os_and_architecture_are_explicit(self):
        for distro, version in [('ubuntu', '24.04'), ('debian', '12'), ('debian', '13')]:
            self.assertEqual(os_support(f'ID={distro}\nVERSION_ID="{version}"', 'x86_64'), (distro, version))
        for data, arch in [('ID=ubuntu\nVERSION_ID=22.04', 'amd64'), ('ID=debian\nVERSION_ID=12', 'arm64')]:
            with self.assertRaises(ConfigError):
                os_support(data, arch)

    def test_only_immutable_images(self):
        validate_image('sha256:' + 'a' * 64)
        validate_image('php@sha256:' + 'a' * 64)
        for value in ('php:latest', 'sha256:' + 'a' * 63, 'php@sha256:' + 'a' * 64 + ';id'):
            with self.assertRaises(ConfigError):
                validate_image(value)

    def test_files_are_never_overwritten(self):
        with tempfile.TemporaryDirectory() as root:
            path = Path(root) / 'private/config'
            write_new(path, 'original')
            with self.assertRaises(FileExistsError):
                write_new(path, 'replacement')
            self.assertEqual(path.read_text(), 'original')

    def test_startup_requires_guard_and_database_readiness(self):
        units = service_units(Path('/usr/lib/jvm/java-17-openjdk-amd64'))
        self.assertEqual(len(units), 8)
        for name in ('cmnd-php.service', 'cmnd-tomcat.service'):
            self.assertIn('native-wait-database', units[name])
            self.assertIn('cmnd-egress.service', units[name])
            self.assertIn('/etc/cmnd/egress.json --execute', units[name])
        self.assertIn('User=cmnd', units['cmnd-tomcat.service'])
        self.assertNotIn('apache2.service', ''.join(units.values()))

    def test_database_gate_checks_query_result_and_retries(self):
        with patch('cmnd_linux.native_deploy._mysql', side_effect=[RuntimeError('not ready'), b'1\n']) as query, \
                patch('cmnd_linux.native_deploy.time.sleep'):
            wait_database(10)
        self.assertEqual(query.call_count, 2)
        self.assertEqual(query.call_args.kwargs['timeout'], 5)

    def test_invalid_timeouts_fail_before_network(self):
        for seconds in (0, -1, True, 1801):
            with self.assertRaises(ConfigError):
                wait_ready(None, seconds)
            with self.assertRaises(ConfigError):
                wait_php(seconds)
        with self.assertRaises(ConfigError):
            wait_database(0)


if __name__ == '__main__':
    unittest.main()
