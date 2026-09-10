from contextlib import ExitStack
import io
import unittest
from unittest.mock import patch

from cmnd_linux import update_cli
from cmnd_linux.cli import main
from cmnd_linux.updates import UpdateError


class UpdateCliTests(unittest.TestCase):
    def invoke(self, *, available=True, enabled=True, root=True, terminal=True,
               answer='INSTALL 0.7.0', worker_error=None):
        report = {'enabled': enabled, 'available': available,
                  'release': {'version': '0.7.0', 'release_id': 321, 'prerelease': True,
                              'url': 'https://github.com/MusicCityTelecom/linux-cmnd/releases/tag/v0.7.0'}}
        lines = []
        with ExitStack() as stack:
            stack.enter_context(patch.object(update_cli, 'settings', return_value={'channel': 'preview'}))
            stack.enter_context(patch.object(update_cli, 'check_release', return_value=report))
            stack.enter_context(patch.object(update_cli.os, 'name', 'posix'))
            stack.enter_context(patch.object(update_cli.os, 'geteuid', return_value=0 if root else 1000, create=True))
            stack.enter_context(patch.object(update_cli.sys.stdin, 'isatty', return_value=terminal))
            worker = stack.enter_context(patch.object(update_cli, 'install_pending', side_effect=worker_error))
            reader = stack.enter_context(patch('builtins.input', return_value=answer))
            if worker_error:
                with self.assertRaisesRegex(UpdateError, 'do not assume rollback succeeded'):
                    update_cli.interactive_update(write=lines.append)
            else:
                self.assertEqual(update_cli.interactive_update(write=lines.append), 0)
            return lines, worker, reader

    def test_switch_reaches_interactive_updates_without_loading_tv_config(self):
        with patch.object(update_cli, 'interactive_update', return_value=0) as update:
            self.assertEqual(main(['--updates']), 0)
            update.assert_called_once_with()

    def test_switch_rejects_combination_with_other_action(self):
        with patch('sys.stderr', new_callable=io.StringIO):
            with self.assertRaises(SystemExit):
                main(['--updates', 'update-apply', '--execute'])

    def test_no_update_and_disabled_never_prompt_or_install(self):
        for options in ({'available': False}, {'enabled': False}):
            lines, worker, reader = self.invoke(**options)
            worker.assert_not_called()
            reader.assert_not_called()

    def test_non_root_and_non_interactive_only_report(self):
        for options in ({'root': False}, {'terminal': False}):
            lines, worker, reader = self.invoke(**options)
            worker.assert_not_called()
            reader.assert_not_called()

    def test_only_exact_version_confirmation_installs(self):
        for answer in ('', 'yes', 'INSTALL', 'INSTALL 0.8.0'):
            lines, worker, reader = self.invoke(answer=answer)
            worker.assert_not_called()
            self.assertIn('Cancelled; nothing installed.', lines)

    def test_exact_confirmation_hands_off_fixed_release(self):
        lines, worker, reader = self.invoke()
        worker.assert_called_once_with(execute=True, confirmed_job={'version': '0.7.0', 'release_id': 321, 'execute': True})
        self.assertTrue(any('queued TV operations' in line for line in lines))
        self.assertTrue(any('readiness verified' in line for line in lines))

    def test_failure_does_not_claim_success_or_recovery(self):
        lines, worker, reader = self.invoke(worker_error=UpdateError('synthetic failure'))
        self.assertFalse(any('readiness verified' in line for line in lines))

    def test_network_failure_is_not_up_to_date(self):
        with patch.object(update_cli, 'settings', return_value={}), \
                patch.object(update_cli, 'check_release', side_effect=UpdateError('offline')), \
                patch.object(update_cli, 'install_pending') as worker:
            with self.assertRaisesRegex(UpdateError, 'offline'):
                update_cli.interactive_update()
            worker.assert_not_called()


if __name__ == '__main__':
    unittest.main()
