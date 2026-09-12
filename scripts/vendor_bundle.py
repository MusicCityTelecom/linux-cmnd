"""Verify a release bundle contains exactly the approved original installer inputs."""
import hashlib
from pathlib import Path
import sys
import zipfile

sys.path.insert(0, str(Path(__file__).resolve().parents[1] / 'src'))
from cmnd_linux.application_stage import VENDOR_INPUT_HASHES

NAME = 'cmnd-vendor-7.5.9.zip'


def stream_digest(stream, algorithm='sha256'):
    digest = hashlib.new(algorithm)
    for chunk in iter(lambda: stream.read(1024 * 1024), b''):
        digest.update(chunk)
    return digest.hexdigest()


def verify(path):
    path = Path(path)
    if not path.is_file() or any(p.is_symlink() for p in (path, *path.parents)):
        raise ValueError('Vendor bundle must be a regular, non-symlink file')
    if not 0 < path.stat().st_size <= 1024**3:
        raise ValueError('Vendor bundle exceeds the release bound')
    with zipfile.ZipFile(path) as archive:
        entries = archive.infolist()
        names = [entry.filename for entry in entries]
        if len(names) != len(set(names)) or set(names) != set(VENDOR_INPUT_HASHES):
            raise ValueError('Bundle must contain exactly the approved original installer inputs')
        if sum(entry.file_size for entry in entries) > 1024**3:
            raise ValueError('Expanded bundle exceeds the release bound')
        for entry in entries:
            if entry.flag_bits & 1 or entry.is_dir() or (entry.external_attr >> 16) & 0o170000 == 0o120000:
                raise ValueError('Encrypted/nonregular vendor bundle member')
            with archive.open(entry) as stream:
                digest = stream_digest(stream)
            if digest != VENDOR_INPUT_HASHES[entry.filename]:
                raise ValueError('Changed original vendor input: ' + entry.filename)
    with path.open('rb') as stream:
        digest = stream_digest(stream)
    return {'name': NAME, 'sha256': digest, 'size': path.stat().st_size,
            'vendor_version': '7.5.9', 'verified_original_inputs': len(entries),
            'customer_data_included': False, 'original_vendor_defaults_retained': True}
