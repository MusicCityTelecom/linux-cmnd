from contextlib import ExitStack
import json
import os
from pathlib import Path
import shutil
import subprocess
import tempfile
import unittest
from unittest.mock import Mock, patch

from cmnd_linux import updates


@unittest.skipUnless(os.name == 'posix', 'Linux worker locking and permissions')
class UpdateWorkerRecoveryTests(unittest.TestCase):
    def test_busy_worker_rejects_direct_job_without_touching_queue(self):
        import fcntl
        with tempfile.TemporaryDirectory() as root:
            state = Path(root)
            queue = state / 'request.json'
            with (state / 'worker.lock').open('a') as held:
                fcntl.flock(held, fcntl.LOCK_EX | fcntl.LOCK_NB)
                with patch.object(updates, 'STATE', state), patch.object(updates, 'QUEUE', queue), \
                        patch.object(updates.os, 'geteuid', return_value=0), \
                        patch.object(updates, 'check_release') as check:
                    with self.assertRaisesRegex(updates.UpdateError, 'another update worker is running'):
                        updates.install_pending(execute=True, confirmed_job={
                            'version': '0.7.0', 'release_id': 321, 'execute': True})
                    check.assert_not_called()
            self.assertFalse(queue.exists())
            self.assertEqual([p.name for p in state.iterdir()], ['worker.lock'])

    def test_cli_confirmation_does_not_consume_existing_queue(self):
        with tempfile.TemporaryDirectory() as root:
            state = Path(root)
            queue = state / 'request.json'
            original = '{"preserve":"existing GUI request"}'
            queue.write_text(original)
            job = {'version': '0.7.0', 'release_id': 321, 'execute': True}
            with patch.object(updates, 'STATE', state), patch.object(updates, 'QUEUE', queue), \
                    patch.object(updates.os, 'geteuid', return_value=0), \
                    patch.object(updates, 'check_release') as check:
                with self.assertRaisesRegex(updates.UpdateError, 'another update is queued'):
                    updates.install_pending(execute=True, confirmed_job=job)
                check.assert_not_called()
            self.assertEqual(queue.read_text(), original)
            self.assertFalse((state / 'status.json').exists())

    def test_invalid_direct_confirmation_fails_before_files_or_network(self):
        with patch.object(updates.os, 'geteuid', return_value=0), \
                patch.object(updates, 'check_release') as check:
            with self.assertRaisesRegex(updates.UpdateError, 'explicit'):
                updates.install_pending(execute=True, confirmed_job={'version': '0.7.0'})
            check.assert_not_called()

    def test_status_preserves_gui_read_access_under_private_umask(self):
        with tempfile.TemporaryDirectory() as root:
            state = Path(root)
            previous = os.umask(0o077)
            try:
                with patch.object(updates, 'STATE', state):
                    updates.write_status({'state': 'idle'})
                status = state / 'status.json'
                self.assertEqual(status.stat().st_mode & 0o777, 0o640)
                self.assertEqual(status.stat().st_gid, state.stat().st_gid)
                self.assertEqual(list(state.iterdir()), [status])
            finally:
                os.umask(previous)

    def test_malformed_queue_is_rejected_without_wedging_worker(self):
        with tempfile.TemporaryDirectory() as root:
            state = Path(root)
            queue = state / 'request.json'
            queue.write_text('{"version":')
            with patch.object(updates, 'STATE', state), patch.object(updates, 'QUEUE', queue), \
                    patch.object(updates.os, 'geteuid', return_value=0), \
                    patch.object(updates, 'check_release') as fetch:
                with self.assertRaises(updates.UpdateError):
                    updates.install_pending(execute=True)
            fetch.assert_not_called()
            self.assertFalse(queue.exists())
            status = json.loads((state / 'status.json').read_text())
            self.assertEqual(status['error'], 'InvalidQueuedJob')
            self.assertTrue(status['runtime_unchanged'])

    def exercise_failure(self, fail_stop=False):
        with tempfile.TemporaryDirectory() as root, ExitStack() as stack:
            root = Path(root)
            state, configuration = root / 'state', root / 'configuration'
            state.mkdir(); configuration.mkdir()
            queue = root / 'request.json'
            queue.write_text(json.dumps({'version': '0.5.0', 'release_id': 123, 'execute': True}))
            (configuration / 'deployment.json').write_text(json.dumps({'vendor': '7.5.9'}))
            (state / 'current.deb').write_bytes(b'previous-package')
            candidate = {'version': '0.5.0', 'release_id': 123,
                         'assets': {'linux-cmnd-update.json': {}, 'linux-cmnd_0.5.0_amd64.deb': {}}}
            manifest = {'schema': 1, 'version': '0.5.0', 'minimum_version': '0.4.0',
                        'scope': 'tooling-only', 'vendor': '7.5.9',
                        'database_migration': False, 'vendor_payload_changed': False}
            commands, statuses = [], []

            def download(_asset, _settings, destination, **_kwargs):
                destination.write_bytes(json.dumps(manifest).encode() if destination.suffix == '.json' else b'candidate-package')

            def command(args, **_kwargs):
                commands.append(tuple(map(str, args)))
                if fail_stop and args[:2] == ('systemctl', 'stop'):
                    return subprocess.CompletedProcess(args, 1, b'', b'synthetic partial-stop failure')
                output = b''
                if args[0] == 'dpkg-deb':
                    version = '0.5.0' if Path(args[2]).name == 'candidate.deb' else '0.4.0'
                    output = {'Package': b'linux-cmnd\n', 'Architecture': b'amd64\n',
                              'Version': (version + '\n').encode()}[args[3]]
                elif args[0] == 'cp':
                    shutil.copyfile(args[-2], args[-1])
                return subprocess.CompletedProcess(args, 0, output, b'')

            def dump(_args, **kwargs):
                kwargs['stdout'].write(b'-- synthetic database backup\n')
                return Mock(wait=Mock(return_value=0))

            for name, value in [('STATE', state), ('QUEUE', queue), ('CONFIGURATION', configuration)]:
                stack.enter_context(patch.object(updates, name, value))
            stack.enter_context(patch('cmnd_linux.__version__', '0.4.0'))
            stack.enter_context(patch.object(updates.os, 'geteuid', return_value=0))
            stack.enter_context(patch.object(updates, 'settings', return_value={}))
            stack.enter_context(patch.object(updates, 'check_release', return_value={'release': candidate}))
            stack.enter_context(patch.object(updates, 'download_asset', side_effect=download))
            stack.enter_context(patch.object(updates, 'write_status', side_effect=statuses.append))
            stack.enter_context(patch.object(updates.subprocess, 'run', side_effect=command))
            popen = stack.enter_context(patch.object(updates.subprocess, 'Popen', side_effect=dump))
            stack.enter_context(patch('shutil.disk_usage', return_value=Mock(free=20 * 1024**3)))
            stack.enter_context(patch('cmnd_linux.config.load_config', return_value=object()))
            health = [{'readiness_verified': True}] if fail_stop else [
                {'readiness_verified': False}, {'readiness_verified': True}]
            stack.enter_context(patch('cmnd_linux.native_deploy.wait_ready', side_effect=health))
            with self.assertRaises(updates.UpdateError):
                updates.install_pending(execute=True)
            self.assertEqual((state / 'current.deb').read_bytes(), b'previous-package')
            self.assertTrue(statuses[-1]['previous_runtime_recovered'])
            self.assertEqual(statuses[-1]['backup_retained'], not fail_stop)
            installs = [args for args in commands if args[0] == 'dpkg']
            self.assertEqual(len(installs), 0 if fail_stop else 2)
            if fail_stop:
                popen.assert_not_called()
            else:
                self.assertEqual(Path(installs[-1][-1]).name, 'previous.deb')
            self.assertTrue(any(args[:2] == ('systemctl', 'start') for args in commands))

    def test_partial_stop_failure_restarts_previously_running_services(self):
        self.exercise_failure(fail_stop=True)

    def test_failed_upgrade_restores_matching_retained_package(self):
        self.exercise_failure()
