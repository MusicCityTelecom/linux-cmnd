import importlib.util
from pathlib import Path
import sys
import unittest
from unittest.mock import patch


SCRIPTS = Path(__file__).resolve().parents[1] / 'scripts'
sys.path.insert(0, str(SCRIPTS))
try:
    spec = importlib.util.spec_from_file_location('cmnd_release_builder', SCRIPTS / 'build_release.py')
    builder = importlib.util.module_from_spec(spec)
    spec.loader.exec_module(builder)
finally:
    sys.path.remove(str(SCRIPTS))


class ReleaseBuildTests(unittest.TestCase):
    def test_release_archive_excludes_private_working_notes(self):
        self.assertNotIn('HANDOFF.md', builder.RELEASE_PATHS)
        self.assertNotIn('.lab', builder.RELEASE_PATHS)
        self.assertNotIn('.private-staging', builder.RELEASE_PATHS)

    def test_untracked_and_ignored_inputs_block_release(self):
        for name in ('src/forgotten.py', 'deploy/private.jar', 'config/local.toml'):
            with patch.object(builder.subprocess, 'run'), \
                    patch.object(builder.subprocess, 'check_output', return_value=name + '\n'):
                with self.assertRaisesRegex(SystemExit, 'Uncommitted'):
                    builder.validate_sources()

    def test_tracked_private_inputs_block_release(self):
        for name in ('resources/vendor.war', '.lab/reference.txt', 'docs/customer.sql'):
            with patch.object(builder.subprocess, 'run'), \
                    patch.object(builder.subprocess, 'check_output', side_effect=['', name + '\n']):
                with self.assertRaisesRegex(SystemExit, 'Private/vendor'):
                    builder.validate_sources()

    def test_bytecode_excluded_from_package_does_not_block_release(self):
        with patch.object(builder.subprocess, 'run') as diff, \
                patch.object(builder.subprocess, 'check_output',
                             side_effect=['src/cmnd_linux/__pycache__/cli.cpython-312.pyc\n', 'src/cmnd_linux/cli.py\n']) as listing:
            builder.validate_sources()
        self.assertIn('README.md', diff.call_args.args[0])
        self.assertNotIn('--exclude-standard', listing.call_args_list[0].args[0])


if __name__ == '__main__':
    unittest.main()
