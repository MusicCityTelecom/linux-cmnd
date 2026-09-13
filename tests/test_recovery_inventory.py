import struct
import unittest

from cmnd_linux.recovery_inventory import class_identity


def identity_fixture(name: bytes, extra: bytes = b'', slots: int = 0) -> bytes:
    # Minimal header through this_class; not executable bytecode.
    return (b'\xca\xfe\xba\xbe' + struct.pack('>HHH', 0, 61, 3 + slots)
            + b'\x01' + struct.pack('>H', len(name)) + name
            + b'\x07\x00\x01' + extra + b'\x00\x21\x00\x02')


class RecoveryInventoryTests(unittest.TestCase):
    def test_identity_uses_bytecode_not_filename(self):
        identity = class_identity(identity_fixture(b'com/vendor/Example$Inner'))
        self.assertEqual(identity.name, 'com/vendor/Example$Inner')
        self.assertEqual(identity.major, 61)

    def test_double_slot_and_dynamic_constants(self):
        extra = b'\x05' + bytes(8) + b'\x12' + bytes(4)
        self.assertEqual(class_identity(identity_fixture(b'Example', extra, 3)).name, 'Example')

    def test_truncation_rejected(self):
        fixture = identity_fixture(b'Example')
        for size in range(len(fixture)):
            with self.subTest(size=size), self.assertRaises(ValueError):
                class_identity(fixture[:size])

    def test_unsafe_paths_rejected(self):
        for name in (b'../X', b'/X', b'X//Y', b'C:/X', b'X\\Y', b'X\x00Y', b'[X;', b'X/..'):
            with self.subTest(name=name), self.assertRaises(ValueError):
                class_identity(identity_fixture(name))

    def test_non_class_and_bad_reference_rejected(self):
        with self.assertRaises(ValueError):
            class_identity(b'not a class')
        fixture = identity_fixture(b'Example')
        with self.assertRaises(ValueError):
            class_identity(fixture[:-2] + b'\x00\x01')

    def test_unicode_class_identity(self):
        self.assertEqual(class_identity(identity_fixture('pkg/\u00c5'.encode())).name, 'pkg/\u00c5')
