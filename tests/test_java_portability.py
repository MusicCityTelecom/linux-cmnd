from hashlib import sha256
from pathlib import Path
import struct
import tempfile
import unittest
from unittest.mock import patch
from zipfile import ZipFile

from cmnd_linux.java_portability import adapt_literals, linux_class_replacements


def fixture(*values, other=()):
    constants = [b'\x01' + struct.pack('>H', len(value)) + value for value in values]
    constants.extend(other)
    slots = len(values) + sum(2 if entry[0] in (5, 6) else 1 for entry in other)
    # Synthetic parser fixture, not copied from any vendor class.
    return b'\xca\xfe\xba\xbe\x00\x00\x00\x34' + struct.pack('>H', slots + 1) + b''.join(constants) + b'\x00' * 14


class JavaPortabilityTests(unittest.TestCase):
    def test_only_reviewed_utf8_constants_change(self):
        source = fixture(b'bin\\keytool.exe', b'unrelated\\path', b'\xc0\x80')
        expected = fixture(b'bin/keytool', b'unrelated\\path', b'\xc0\x80')
        self.assertEqual(adapt_literals(source, {b'bin\\keytool.exe': b'bin/keytool'}), expected)

    def test_command_replacement_preserves_argument_separator(self):
        self.assertEqual(adapt_literals(fixture(b'cmd /c 7z.exe a '),
            {b'cmd /c 7z.exe a ': b'/usr/bin/cmnd-7zip a '}), fixture(b'/usr/bin/cmnd-7zip a '))

    def test_non_utf8_constants_and_long_slots_remain_unchanged(self):
        other = tuple(bytes([tag]) + b'\x00' * size for tag, size in
                      ((3,4),(4,4),(5,8),(6,8),(7,2),(8,2),(9,4),(10,4),(11,4),(12,4),
                       (15,3),(16,2),(17,4),(18,4),(19,2),(20,2)))
        self.assertEqual(adapt_literals(fixture(b'old', other=other), {b'old': b'new-longer'}),
                         fixture(b'new-longer', other=other))

    def test_missing_or_duplicate_literal_refused(self):
        for data in (fixture(b'other'), fixture(b'old', b'old')):
            with self.assertRaisesRegex(ValueError, 'missing/duplicate'):
                adapt_literals(data, {b'old': b'new'})

    def test_invalid_or_truncated_pool_refused(self):
        source = fixture(b'old')
        for data in (b'not a class', source[:10], source[:11], source[:13], source[:-1],
                     source[:10] + b'\xff' + source[11:]):
            with self.assertRaises(ValueError):
                adapt_literals(data, {b'old': b'new'})

    def test_invalid_replacement_contract_refused(self):
        for replacements in ({}, {b'': b'x'}, {b'old': b'\xff'}, {b'old': b'x' * 65536}):
            with self.assertRaises(ValueError):
                adapt_literals(fixture(b'old'), replacements)

    def test_war_hash_gate_and_non_mutating_result(self):
        original = fixture(b'old')
        name = 'WEB-INF/classes/synthetic/Helper.class'
        with tempfile.TemporaryDirectory() as root:
            source = Path(root) / 'synthetic.war'
            with ZipFile(source, 'w') as archive:
                archive.writestr(name, original)
            before = source.read_bytes()
            spec = {name: (sha256(original).hexdigest(), {b'old': b'new'})}
            with patch('cmnd_linux.java_portability.PATCHES', spec):
                self.assertEqual(linux_class_replacements(source), {name: fixture(b'new')})
            self.assertEqual(source.read_bytes(), before)
            with patch('cmnd_linux.java_portability.PATCHES', {name: ('0' * 64, {b'old': b'new'})}):
                with self.assertRaisesRegex(ValueError, 'unrecognized vendor class'):
                    linux_class_replacements(source)

    def test_missing_vendor_class_refused(self):
        with tempfile.TemporaryDirectory() as root:
            source = Path(root) / 'synthetic.war'
            with ZipFile(source, 'w') as archive:
                archive.writestr('unrelated', b'test')
            with self.assertRaisesRegex(ValueError, 'missing/duplicate'):
                linux_class_replacements(source)


if __name__ == '__main__':
    unittest.main()
