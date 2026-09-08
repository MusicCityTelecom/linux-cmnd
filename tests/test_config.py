import tempfile
import unittest
from pathlib import Path
from dataclasses import replace

from cmnd_linux.config import ConfigError, load_config


CONFIG = '''
[safety]
mode = "isolated"
permitted_ranges = ["127.0.0.0/8"]
[network]
bind = "127.0.0.1"
callback_base_url = "http://127.0.0.1:8080"
timeout_seconds = 2
[[tv.allowlist]]
ip = "127.0.0.1"
identity = "SIMULATOR00000001"
operations = ["power", "clone"]
'''


class ConfigSafetyTests(unittest.TestCase):
    def load(self):
        temp = tempfile.TemporaryDirectory(); path = Path(temp.name) / "config.toml"; path.write_text(CONFIG)
        return temp, load_config(path)

    def test_write_requires_execute(self):
        temp, config = self.load()
        try:
            with self.assertRaises(ConfigError):
                config.authorize("127.0.0.1", "SIMULATOR00000001", "power", False)
        finally: temp.cleanup()

    def test_identity_and_operation_are_jointly_checked(self):
        temp, config = self.load()
        try:
            with self.assertRaises(ConfigError):
                config.authorize("127.0.0.1", "WRONG000000", "power", True)
            with self.assertRaises(ConfigError):
                config.authorize("127.0.0.1", "SIMULATOR00000001", "room-id", True)
        finally: temp.cleanup()

    def test_clone_url_is_restricted(self):
        temp, config = self.load()
        try:
            config.validate_package_url("http://127.0.0.1:8080/SmartInstall/Profile/Clone/id/test.zip")
            with self.assertRaises(ConfigError):
                config.validate_package_url("http://example.com/SmartInstall/Profile/Clone/id/test.zip")
        finally: temp.cleanup()

    def test_clone_url_rejects_ambiguous_paths_and_credentials(self):
        temp, config = self.load()
        try:
            base = 'http://127.0.0.1:8080/SmartInstall/Profile/Clone/'
            for suffix in ('../secret', '%2e%2e/secret', '%252e%252e/secret',
                           'a%5cb.zip', 'a%00.zip', 'a.zip?token=x', 'a.zip#x'):
                with self.subTest(suffix=suffix), self.assertRaises(ConfigError):
                    config.validate_package_url(base + suffix)
            with self.assertRaises(ConfigError):
                config.validate_package_url(base.replace('http://', 'http://user:pass@') + 'a.zip')
        finally: temp.cleanup()

    def test_unknown_operation_and_isolation_fail_closed(self):
        temp, config = self.load()
        try:
            with self.assertRaises(ConfigError):
                config.authorize('127.0.0.1', 'SIMULATOR00000001', 'typo', True)
            expanded = replace(config, permitted_ranges=('0.0.0.0/0',))
            with self.assertRaises(ConfigError):
                expanded.authorize('192.0.2.1', 'SIMULATOR00000001', 'power', True)
        finally: temp.cleanup()


if __name__ == "__main__": unittest.main()
