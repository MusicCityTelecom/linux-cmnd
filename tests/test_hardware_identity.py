import unittest
from unittest.mock import patch
from cmnd_linux.hardware_identity import grant_access, usable_serial


class HardwareIdentityTests(unittest.TestCase):
    def test_actual_values_accepted_without_making_new_identifiers(self):
        self.assertTrue(usable_serial(b'CMND-SYNTHETIC-VM-0001\n'))
        self.assertTrue(usable_serial(b'12345'))

    def test_absent_placeholder_oversize_or_control_data_rejected(self):
        for value in (b'', b'unknown', b'None', b'Not Specified', b'Default string',
                      b'To be filled by O.E.M.', b'a' * 257, b'x\x00y', b'\xff'):
            self.assertFalse(usable_serial(value), value)

    def test_preview_does_not_read_or_change_kernel_files(self):
        with patch('cmnd_linux.hardware_identity.Path.resolve', side_effect=AssertionError('Unexpected kernel read')):
            report = grant_access()
        self.assertFalse(report['executed'])
        self.assertFalse(report['serial_generated_or_copied'])
