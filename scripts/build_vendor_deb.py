#!/usr/bin/env python3
"""Build a Debian package from the exact verified Philips CMND 7.5.9 bundle.

Vendor binaries remain outside Git. The operator supplies the already-approved
`cmnd-vendor-7.5.9.zip` release input; every member is hash-verified before it is
placed under /usr/lib/cmnd/vendor/7.5.9 in the generated package.
"""
from __future__ import annotations

from hashlib import file_digest
from io import BytesIO
from pathlib import Path
import gzip
import os
import shutil
import sys
import tarfile
import tempfile

ROOT = Path(__file__).resolve().parents[1]
sys.path.insert(0, str(ROOT / 'src'))
sys.path.insert(0, str(ROOT / 'scripts'))

from cmnd_linux.application_stage import VENDOR_INPUT_HASHES
from cmnd_linux.artifacts import file_hash, safe_extract_zip
from vendor_bundle import verify as verify_vendor_bundle

PACKAGE = 'cmnd-vendor-759'
PACKAGE_VERSION = '7.5.9-1'
INSTALL_ROOT = 'usr/lib/cmnd/vendor/7.5.9'
EPOCH = int(os.environ.get('SOURCE_DATE_EPOCH', '1788835200'))


def _tar_header(archive: tarfile.TarFile, name: str, *, mode: int, size: int = 0,
                directory: bool = False, data=None) -> None:
    info = tarfile.TarInfo(name)
    info.type = tarfile.DIRTYPE if directory else tarfile.REGTYPE
    info.mode = mode
    info.size = 0 if directory else size
    info.mtime = EPOCH
    info.uid = info.gid = 0
    info.uname = info.gname = 'root'
    archive.addfile(info, None if directory else data)


def _write_control(path: Path) -> None:
    control = f'''Package: {PACKAGE}\nVersion: {PACKAGE_VERSION}\nSection: admin\nPriority: optional\nArchitecture: all\nMaintainer: Music City Telecom <tommy@tomcom.us>\nDescription: Verified Philips CMND 7.5.9 application payload for Linux CMND\n Original Philips application inputs used by CMND for Linux. Philips/TP Vision\n components retain their original ownership, attribution and applicable terms.\n'''.encode()
    with path.open('wb') as raw:
        with gzip.GzipFile(filename='', mode='wb', fileobj=raw, mtime=EPOCH) as zipped:
            with tarfile.open(fileobj=zipped, mode='w') as archive:
                _tar_header(archive, 'control', mode=0o644, size=len(control), data=BytesIO(control))


def _write_data(path: Path, extracted: Path) -> None:
    files = [extracted / name for name in sorted(VENDOR_INPUT_HASHES)]
    directories = {'usr', 'usr/lib', 'usr/lib/cmnd', 'usr/lib/cmnd/vendor', INSTALL_ROOT,
                   'usr/share', 'usr/share/doc', f'usr/share/doc/{PACKAGE}'}
    for source in files:
        relative = source.relative_to(extracted)
        current = Path(INSTALL_ROOT)
        for parent in relative.parents:
            if str(parent) == '.':
                continue
            directories.add((current / parent).as_posix())
    attribution = (
        'Philips CMND vendor application payload version 7.5.9.\n'
        'Packaged for Linux deployment by Music City Telecom without relicensing vendor components.\n'
        'This package contains only the hash-pinned original installer inputs approved by the project.\n'
    ).encode()
    with path.open('wb') as raw:
        with gzip.GzipFile(filename='', mode='wb', fileobj=raw, mtime=EPOCH) as zipped:
            with tarfile.open(fileobj=zipped, mode='w') as archive:
                for directory in sorted(directories, key=lambda value: (value.count('/'), value)):
                    _tar_header(archive, directory, mode=0o755, directory=True)
                for source in files:
                    destination = f'{INSTALL_ROOT}/{source.relative_to(extracted).as_posix()}'
                    with source.open('rb') as stream:
                        _tar_header(archive, destination, mode=0o644, size=source.stat().st_size, data=stream)
                _tar_header(archive, f'usr/share/doc/{PACKAGE}/ATTRIBUTION', mode=0o644,
                            size=len(attribution), data=BytesIO(attribution))


def _write_ar_member(output, name: str, source: Path) -> None:
    size = source.stat().st_size
    header = ((name + '/').encode().ljust(16, b' ') + str(EPOCH).encode().ljust(12, b' ') +
              b'0     0     100644  ' + str(size).encode().ljust(10, b' ') + b'`\n')
    output.write(header)
    with source.open('rb') as stream:
        shutil.copyfileobj(stream, output, 1024 * 1024)
    if size % 2:
        output.write(b'\n')


def build(bundle: Path) -> Path:
    report = verify_vendor_bundle(bundle)
    if report['vendor_version'] != '7.5.9' or report['verified_original_inputs'] != len(VENDOR_INPUT_HASHES):
        raise ValueError('unexpected verified vendor bundle metadata')
    output = ROOT / 'dist' / f'{PACKAGE}_{PACKAGE_VERSION}_all.deb'
    output.parent.mkdir(exist_ok=True)
    with tempfile.TemporaryDirectory(prefix='cmnd-vendor-deb-') as temp:
        temp_root = Path(temp)
        extracted = temp_root / 'vendor'
        names = safe_extract_zip(bundle, extracted, max_files=100, max_bytes=1024**3)
        if set(names) != set(VENDOR_INPUT_HASHES):
            raise ValueError('vendor archive member set changed during extraction')
        for name, expected in VENDOR_INPUT_HASHES.items():
            path = extracted / name
            if not path.is_file() or path.is_symlink() or file_hash(path) != expected:
                raise ValueError('vendor input hash mismatch after extraction: ' + name)
        control = temp_root / 'control.tar.gz'
        data = temp_root / 'data.tar.gz'
        _write_control(control)
        _write_data(data, extracted)
        with output.open('wb') as deb:
            deb.write(b'!<arch>\n')
            binary = temp_root / 'debian-binary'
            binary.write_bytes(b'2.0\n')
            _write_ar_member(deb, 'debian-binary', binary)
            _write_ar_member(deb, 'control.tar.gz', control)
            _write_ar_member(deb, 'data.tar.gz', data)
    with output.open('rb') as stream:
        digest = file_digest(stream, 'sha256').hexdigest()
    Path(str(output) + '.sha256').write_text(f'{digest}  {output.name}\n')
    return output


def main(argv=None) -> int:
    import argparse
    parser = argparse.ArgumentParser()
    parser.add_argument('bundle', type=Path)
    args = parser.parse_args(argv)
    try:
        print(build(args.bundle))
    except (OSError, ValueError) as exc:
        print(f'build-vendor-deb: {exc}', file=sys.stderr)
        return 2
    return 0


if __name__ == '__main__':
    raise SystemExit(main())
