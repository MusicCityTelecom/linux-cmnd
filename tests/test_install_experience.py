from contextlib import ExitStack
import io
import json
import os
from pathlib import Path
from types import SimpleNamespace
import tempfile
import unittest
from unittest.mock import patch

from cmnd_linux import install_summary as receipt, install_storage as storage, native_deploy
from cmnd_linux.cli import main
from cmnd_linux.config import ConfigError

ROOT = Path(__file__).resolve().parents[1]


class InstallSummaryTests(unittest.TestCase):
    def setup_receipt(self, stack, root, *, credential=True, **qualification):
        root = Path(root)
        (root / 'cmnd.toml').write_text((ROOT / 'config/cmnd.install.toml').read_text())
        stack.enter_context(patch.object(receipt, 'STATE', root))
        stack.enter_context(patch.object(receipt, 'ETC', root))
        stack.enter_context(patch.object(receipt, 'require_root'))
        stack.enter_context(patch.object(receipt, 'trusted_read', return_value=''))
        objects = {'deployment.json': {'managed_by': 'linux-cmnd-native', 'state': 'active'},
            'qualification.json': dict(services_started=True, readiness_verified=True, services_enabled=True, **qualification),
            'initial-admin.json': {'username': 'admin', 'password': 'synthetic-initial-password'}}
        def read(path, **kwargs):
            if path.name == 'initial-admin.json' and not credential:
                raise FileNotFoundError(path)
            return objects[path.name]
        stack.enter_context(patch.object(receipt, 'read_object', side_effect=read))
        return objects

    def test_success_actual_credential_and_all_configured_urls(self):
        with tempfile.TemporaryDirectory() as root, ExitStack() as stack:
            self.setup_receipt(stack, root)
            path = Path(root) / 'cmnd.toml'
            path.write_text(path.read_text().replace('8443', '9443').replace('8444', '9444'))
            text, complete = receipt.summary()
            self.assertTrue(complete)
            self.assertIn('synthetic-initial-password', text)
            for suffix in ('9443/SmartInstall/', '9443/smartcontrol/', '9443/usermanagement/', '9444/SmartCMS/', '9444/linux-cmnd/'):
                self.assertIn('https://127.0.0.1:' + suffix, text)
            self.assertIn('FINAL STATUS: PASS', text)

    def test_missing_credential_is_not_invented(self):
        with tempfile.TemporaryDirectory() as root, ExitStack() as stack:
            self.setup_receipt(stack, root, credential=False)
            text, complete = receipt.summary()
            self.assertFalse(complete)
            self.assertIn('UNAVAILABLE', text)
            self.assertNotIn('FINAL STATUS: PASS', text)

    def test_incomplete_or_failed_never_displays_credentials_or_pass(self):
        for field in ('services_started', 'readiness_verified', 'services_enabled', 'FAILED'):
            with tempfile.TemporaryDirectory() as root, ExitStack() as stack:
                objects = self.setup_receipt(stack, root)
                if field == 'FAILED':
                    (Path(root) / field).write_text('earlier failure')
                else:
                    objects['qualification.json'][field] = False
                text, complete = receipt.summary()
                self.assertFalse(complete)
                self.assertNotIn('synthetic-initial-password', text)
                self.assertNotIn('STATUS: PASS', text)

    def test_malformed_credentials_are_refused(self):
        for value in ({}, {'username': 'admin', 'password': '\x1b[2J'}, {'username': 'other', 'password': 'test'}):
            with tempfile.TemporaryDirectory() as root, ExitStack() as stack:
                objects = self.setup_receipt(stack, root)
                objects['initial-admin.json'] = value
                with self.assertRaises(ConfigError):
                    receipt.summary()

    def test_non_root_denied_and_cli_aliases_share_implementation(self):
        with patch.object(receipt.os, 'geteuid', return_value=1000, create=True):
            with self.assertRaises(ConfigError):
                receipt.require_root()
        for command in ('install-summary', 'show-login'):
            with patch.object(receipt, 'show_summary', return_value=True) as show:
                self.assertEqual(main([command]), 0)
                show.assert_called_once_with()

    @unittest.skipUnless(os.name == 'posix' and getattr(os, 'geteuid', lambda: 1)() == 0, 'root POSIX trust boundary')
    def test_untrusted_permissions_symlinks_hardlinks_and_types(self):
        # /tmp is deliberately untrusted; use root's private directory for valid control.
        with tempfile.TemporaryDirectory(dir='/root') as temp:
            path = Path(temp) / 'credential'
            path.write_text('{"password":"synthetic"}')
            path.chmod(0o600)
            self.assertIn('synthetic', receipt.trusted_read(path, private=True))
            path.chmod(0o640)
            with self.assertRaises(ConfigError):
                receipt.trusted_read(path, private=True)
            path.chmod(0o600)
            link = Path(temp) / 'link'
            link.symlink_to(path)
            with self.assertRaises(OSError):
                receipt.trusted_read(link, private=True)
            os.link(path, Path(temp) / 'hardlink')
            with self.assertRaises(ConfigError):
                receipt.trusted_read(path, private=True)
            with self.assertRaises(ConfigError):
                receipt.trusted_read(Path(temp), private=True)


class StorageTests(unittest.TestCase):
    def test_bootstrap_policy_is_identical_to_package_policy(self):
        def block(path):
            return path.read_text().split('# BEGIN STANDALONE STORAGE POLICY\n')[1].split('# END STANDALONE STORAGE POLICY')[0]
        self.assertEqual(block(ROOT / 'scripts/bootstrap.py'), block(Path(storage.__file__)))

    def probe(self, free, **kwargs):
        stack = ExitStack()
        stack.enter_context(patch.object(storage.Path, 'exists', return_value=True))
        stack.enter_context(patch.object(storage.Path, 'stat', return_value=SimpleNamespace(st_dev=1)))
        stack.enter_context(patch.object(storage.shutil, 'disk_usage', return_value=SimpleNamespace(total=40*1024**3, free=free)))
        with stack:
            return storage.storage_preflight(**kwargs)

    def test_peak_budget_small_disk_and_pending_reboot_warning(self):
        with patch('sys.stdout', new_callable=io.StringIO) as output:
            with self.assertRaisesRegex(ValueError, 'Insufficient free storage'):
                self.probe(9*1024**3)
            self.assertIn('27.81 GiB', output.getvalue())
            result = self.probe(30*1024**3)
            self.assertEqual(result[0]['required'], 12*1024**3 + 2*8096*1024**2)
            self.assertIn('will NOT reboot', output.getvalue())
            result = self.probe(30*1024**3, phase='prepared')
            self.assertEqual(result[0]['required'], 10*1024**3 + 2*8096*1024**2)

    def test_invalid_limits_fail_before_filesystem_probe(self):
        with patch.object(storage.shutil, 'disk_usage') as disk:
            for value in (0, True, 8097, '8096'):
                with self.assertRaises(ValueError):
                    storage.storage_preflight(value)
            disk.assert_not_called()


class ProbeLoggingTests(unittest.TestCase):
    def test_retry_failure_only_logged_at_deadline_with_last_reason(self):
        clock = [0]
        def sleep(seconds):
            clock[0] += seconds
        error = RuntimeError('expected startup')
        error.private_detail = 'synthetic final connection failure'
        with patch.object(native_deploy.time, 'monotonic', side_effect=lambda: clock[0]), \
             patch.object(native_deploy.time, 'sleep', side_effect=sleep), \
             patch.object(native_deploy, '_mysql', side_effect=error) as mysql, \
             patch.object(native_deploy, 'event') as event:
            with self.assertRaisesRegex(RuntimeError, 'did not become ready'):
                native_deploy.wait_database(3)
            self.assertEqual(mysql.call_count, 3)
            self.assertTrue(all(call.kwargs['probe'] for call in mysql.call_args_list))
            errors = [call for call in event.call_args_list if call.kwargs.get('level') == 'ERROR']
            self.assertEqual(len(errors), 1)
            self.assertEqual(errors[0].kwargs['private_detail'], error.private_detail)

    def test_probe_command_does_not_append_expected_errors(self):
        result = SimpleNamespace(returncode=1, stdout=b'', stderr=b'expected missing table')
        with patch.object(native_deploy.subprocess, 'run', return_value=result), patch.object(native_deploy, 'event') as event:
            with self.assertRaises(RuntimeError) as error:
                native_deploy._mysql(b'SELECT 1', probe=True)
            self.assertIn('expected missing table', error.exception.private_detail)
            event.assert_not_called()

    def test_package_home_precedes_account_and_entrypoints_end_in_receipt(self):
        postinst = (ROOT / 'packaging/debian/postinst').read_text()
        self.assertLess(postinst.index('install -d -o root -g cmnd'), postinst.index('adduser --system'))
        shell = (ROOT / 'scripts/install-native.sh').read_text()
        self.assertLess(shell.index('install_storage.py'), shell.index('dpkg --force-confold'))
        self.assertLess(shell.index('install_storage.py'), shell.index('apt-get update'))
        self.assertNotIn("echo 'Native CMND installed.", shell)
        deploy = Path(native_deploy.__file__).read_text()
        self.assertLess(deploy.index("'enable', '--now', 'cmnd-update.path'"), deploy.index('receipt, complete = summary()'))
        self.assertIn("indent=2) + '\\n'", deploy)


if __name__ == '__main__':
    unittest.main()
