import hashlib
import importlib.util
import json
from pathlib import Path
import tempfile
import unittest

spec = importlib.util.spec_from_file_location('verify_recovered_source', Path(__file__).resolve().parents[1] / 'scripts/verify_recovered_source.py')
module = importlib.util.module_from_spec(spec)
spec.loader.exec_module(module)


class RecoverySourceVerificationTests(unittest.TestCase):
    def fixture(self, root, name='Original.java', content=b'class Original {}\r\n'):
        path = root / name
        path.parent.mkdir(parents=True, exist_ok=True)
        path.write_bytes(content)
        records = [{'path': name, 'sha256': hashlib.sha256(content).hexdigest()}]
        (root / 'provenance.json').write_text(json.dumps(records))

    def test_exact_bytes_pass(self):
        with tempfile.TemporaryDirectory() as temporary:
            root = Path(temporary)
            self.fixture(root)
            self.assertEqual(module.verify(root)['verified_source_files'], 1)

    def test_changed_line_endings_fail(self):
        with tempfile.TemporaryDirectory() as temporary:
            root = Path(temporary)
            self.fixture(root)
            (root / 'Original.java').write_bytes(b'class Original {}\n')
            with self.assertRaisesRegex(ValueError, 'hash mismatch'):
                module.verify(root)

    def test_unmanifested_file_fails(self):
        with tempfile.TemporaryDirectory() as temporary:
            root = Path(temporary)
            self.fixture(root)
            (root / 'unexpected.txt').write_text('not reviewed')
            with self.assertRaisesRegex(ValueError, 'missing provenance'):
                module.verify(root)

    def test_executable_disguised_as_source_fails(self):
        with tempfile.TemporaryDirectory() as temporary:
            root = Path(temporary)
            self.fixture(root, content=b'MZbinary')
            with self.assertRaisesRegex(ValueError, 'Executable content'):
                module.verify(root)

    def test_traversal_fails(self):
        with tempfile.TemporaryDirectory() as temporary:
            root = Path(temporary)
            (root / 'provenance.json').write_text(json.dumps([{'path': '../outside.java', 'sha256': '0'*64}]))
            with self.assertRaisesRegex(ValueError, 'Unsafe manifest path'):
                module.verify(root)

    def test_class_named_notice_is_still_binary(self):
        with tempfile.TemporaryDirectory() as temporary:
            root = Path(temporary)
            self.fixture(root, name='notices/original-license', content=b'\xca\xfe\xba\xbebytecode')
            with self.assertRaisesRegex(ValueError, 'Executable content'):
                module.verify(root)
