import importlib.util
from pathlib import Path
import sys
import unittest

class NativeTrackSeparationTests(unittest.TestCase):
    def test_research_snapshot_is_not_an_installer_update(self):
        from cmnd_linux.updates import select_release
        research = {'id': 900001, 'tag_name': 'research-recovery-2026-09-12',
                    'draft': False, 'prerelease': True, 'assets': []}
        script = Path(__file__).resolve().parents[1] / 'scripts/bootstrap.py'
        spec = importlib.util.spec_from_file_location('research_bootstrap_test', script)
        bootstrap = importlib.util.module_from_spec(spec)
        spec.loader.exec_module(bootstrap)
        installer = {'id': 900000, 'tag_name': 'v0.7.1', 'draft': False,
                     'prerelease': True, 'assets': []}
        for tag in ('research-recovery-2026-09-12', 'research-native-project-2026-09-13'):
            research['tag_name'] = tag
            for channel in ('stable', 'preview'):
                self.assertIsNone(select_release([research], '0.7.1', channel))
            self.assertEqual(bootstrap.choose_release([research, installer]), installer)

    def test_compatibility_builders_refuse_experimental_track(self):
        scripts=Path(__file__).resolve().parents[1]/'scripts'
        sys.path.insert(0,str(scripts))
        try:
            for name,function in [('build_deb','build'),('build_release','validate_sources')]:
                spec=importlib.util.spec_from_file_location('track_test_'+name,scripts/(name+'.py'))
                module=importlib.util.module_from_spec(spec)
                spec.loader.exec_module(module)
                with self.assertRaisesRegex(SystemExit,'Java-native research track'):
                    getattr(module,function)()
        finally:
            sys.path.remove(str(scripts))
