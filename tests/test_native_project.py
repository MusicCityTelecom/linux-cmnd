import importlib.util
import json
from pathlib import Path
import tempfile
import unittest
import zipfile

spec = importlib.util.spec_from_file_location('native_project', Path(__file__).resolve().parents[1]/'scripts/native_project.py')
native = importlib.util.module_from_spec(spec)
spec.loader.exec_module(native)


class NativeProjectTests(unittest.TestCase):
    def test_rejects_unsafe_paths(self):
        for name in ('../escape', '/absolute', 'C:/drive', 'x\\y', 'x\nfile'):
            with self.assertRaises(ValueError):
                native.safe_relative(name)

    def test_preserve_write_refuses_to_destroy_edit(self):
        with tempfile.TemporaryDirectory() as tmp:
            path = Path(tmp)/'source.java'
            native.preserve_write(path, b'original')
            native.preserve_write(path, b'original')
            with self.assertRaises(ValueError):
                native.preserve_write(path, b'changed')
            self.assertEqual(path.read_bytes(), b'original')

    def test_duplicate_zip_rejected(self):
        with tempfile.TemporaryDirectory() as tmp:
            path = Path(tmp)/'a.zip'
            with zipfile.ZipFile(path, 'w') as z:
                z.writestr('same', 'one')
                z.writestr('same', 'two')
            with zipfile.ZipFile(path) as z, self.assertRaises(ValueError):
                list(native.checked_members(z))

    def test_symlink_zip_rejected(self):
        with tempfile.TemporaryDirectory() as tmp:
            path = Path(tmp)/'a.zip'
            with zipfile.ZipFile(path, 'w') as z:
                i = zipfile.ZipInfo('link')
                i.external_attr = 0o120777 << 16
                z.writestr(i, 'target')
            with zipfile.ZipFile(path) as z, self.assertRaises(ValueError):
                list(native.checked_members(z))

    def test_init_preserves_edits_and_catalogs_alternates(self):
        with tempfile.TemporaryDirectory() as tmp:
            root = Path(tmp)
            project, ref = root/'project', root/'reference'
            project.mkdir()
            (project/'project.json').write_text(json.dumps({'modules':{'test':{}}}))
            source=ref/'java/vineflower/test/Test.java'
            source.parent.mkdir(parents=True)
            source.write_bytes(b'class Test {}')
            alternate=ref/'native/helper.c'
            alternate.parent.mkdir()
            alternate.write_bytes(b'/* pseudocode */')
            (ref/'provenance.json').write_text(json.dumps([
                {'path':p.relative_to(ref).as_posix(),'sha256':native.digest(p)} for p in (source,alternate)]))
            native.materialize(project,ref)
            editable=project/'modules/test/src/main/java/Test.java'
            editable.write_bytes(b'class Test { int edited; }')
            native.materialize(project,ref)
            self.assertIn(b'edited',editable.read_bytes())
            self.assertEqual(len(json.loads((project/'catalog/sources.json').read_text())),2)
            native.validate_project(project, ref)
            native.checkout_tree('native', project, ref)
            self.assertEqual((project/'working/native/helper.c').read_bytes(), b'/* pseudocode */')

    def test_omitted_catalog_entry_rejected(self):
        with tempfile.TemporaryDirectory() as tmp:
            root = Path(tmp); project=root/'project'; ref=root/'ref'
            (project/'catalog').mkdir(parents=True); ref.mkdir()
            (ref/'provenance.json').write_text('[{"path":"keep.java","sha256":"abc"}]')
            (project/'catalog/sources.json').write_text('[]')
            with self.assertRaises(ValueError): native.validate_project(project,ref)

    def test_changed_reference_rejected(self):
        with tempfile.TemporaryDirectory() as tmp:
            root=Path(tmp); project=root/'project'; ref=root/'ref'
            project.mkdir(); (project/'project.json').write_text('{"modules":{"test":{}}}')
            source=ref/'java/vineflower/test/Test.java'; source.parent.mkdir(parents=True)
            source.write_bytes(b'class Test {}')
            (ref/'provenance.json').write_text(json.dumps([{'path':'java/vineflower/test/Test.java','sha256':'bad'}]))
            with self.assertRaises(ValueError): native.materialize(project,ref)


if __name__ == '__main__': unittest.main()
