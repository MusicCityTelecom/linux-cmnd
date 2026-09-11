import hashlib
import importlib.util
from pathlib import Path
import tempfile
import unittest
from unittest.mock import patch
import zipfile

spec = importlib.util.spec_from_file_location('vendor_bundle_test', Path(__file__).resolve().parents[1] / 'scripts/vendor_bundle.py')
bundle = importlib.util.module_from_spec(spec)
spec.loader.exec_module(bundle)


class VendorBundleTests(unittest.TestCase):
    def test_exact_original_only_and_no_source_mutation(self):
        with tempfile.TemporaryDirectory() as temp:
            path = Path(temp) / 'vendor.zip'
            with zipfile.ZipFile(path, 'w') as archive:
                archive.writestr('app.war', b'original')
            before = path.read_bytes()
            with patch.object(bundle, 'VENDOR_INPUT_HASHES', {'app.war': hashlib.sha256(b'original').hexdigest()}):
                report = bundle.verify(path)
            self.assertEqual(report['verified_original_inputs'], 1)
            self.assertEqual(report['sha256'], hashlib.sha256(before).hexdigest())
            self.assertEqual(path.read_bytes(), before)

    def test_extra_customer_files_or_changed_apps_rejected(self):
        for mode in ('extra', 'changed', 'missing'):
            with tempfile.TemporaryDirectory() as temp:
                path = Path(temp) / 'vendor.zip'
                with zipfile.ZipFile(path, 'w') as archive:
                    if mode != 'missing':
                        archive.writestr('app.war', b'changed' if mode == 'changed' else b'original')
                    if mode == 'extra':
                        archive.writestr('customer.sql', b'private')
                with patch.object(bundle, 'VENDOR_INPUT_HASHES', {'app.war': hashlib.sha256(b'original').hexdigest()}):
                    with self.assertRaises(ValueError, msg=mode):
                        bundle.verify(path)
