import importlib.util
from pathlib import Path
import sys
import unittest

class NativeTrackSeparationTests(unittest.TestCase):
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
