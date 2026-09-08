from pathlib import Path
import re
import unittest

import cmnd_linux


class VersionTests(unittest.TestCase):
    def test_tooling_version_is_consistent(self):
        root = Path(__file__).resolve().parents[1]
        version = (root / 'VERSION').read_text().strip()
        self.assertRegex(version, r'^\d+\.\d+\.\d+$')
        self.assertEqual(cmnd_linux.__version__, version)
        for name, pattern in (('pyproject.toml', r'(?m)^version = "([^"]+)"$'),
                              ('scripts/install.sh', r'(?m)^TOOL_VERSION="([^"]+)"$')):
            self.assertEqual(re.search(pattern, (root / name).read_text()).group(1), version)


if __name__ == '__main__':
    unittest.main()
