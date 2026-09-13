#!/usr/bin/env python3
"""Read-only verification of a research source snapshot; never executes vendor code."""
import argparse
import hashlib
import json
from pathlib import Path, PurePosixPath

SOURCE_EXTENSIONS = {'.java', '.cs', '.csproj', '.resx', '.config', '.xml',
                     '.json', '.sln', '.props', '.targets', '.il', '.c', '.h',
                     '.map', '.txt', '.tsv'}
PROJECT_METADATA = {'README.md', 'provenance.json', 'build-status.json', 'recovery-status.json'}


def verify(root):
    root = Path(root).resolve()
    records = json.loads((root / 'provenance.json').read_text(encoding='utf-8'))
    expected = {}
    for row in records:
        relative = row['path']
        path = PurePosixPath(relative)
        if path.is_absolute() or '..' in path.parts or '\\' in relative or ':' in relative:
            raise ValueError('Unsafe manifest path: ' + relative)
        if relative in expected and expected[relative] != row['sha256']:
            raise ValueError('Conflicting provenance hashes: ' + relative)
        expected[relative] = row['sha256']
    total_bytes = 0
    for relative, digest in expected.items():
        path = root / relative
        if path.is_symlink() or not path.resolve().is_relative_to(root):
            raise ValueError('Source escapes snapshot: ' + relative)
        raw = path.read_bytes()
        if hashlib.sha256(raw).hexdigest() != digest:
            raise ValueError('Source hash mismatch: ' + relative)
        if not relative.startswith('notices/') and path.suffix.lower() not in SOURCE_EXTENSIONS:
            raise ValueError('Non-source artifact belongs in release assets: ' + relative)
        if raw[:4] in (b'\xca\xfe\xba\xbe', b'\x7fELF') or raw[:2] == b'MZ':
            raise ValueError('Executable content belongs in release assets: ' + relative)
        total_bytes += len(raw)
    extras = [p.relative_to(root).as_posix() for p in root.rglob('*')
              if p.is_file() and p.relative_to(root).as_posix() not in expected
              and p.relative_to(root).as_posix() not in PROJECT_METADATA]
    if extras:
        raise ValueError('Files missing provenance: ' + ', '.join(extras[:10]))
    return {'verified_source_files': len(expected), 'source_bytes': total_bytes,
            'vendor_executed': False, 'runtime_equivalence_verified': False}


if __name__ == '__main__':
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument('root', nargs='?', default=str(Path(__file__).resolve().parents[1] / 'recovered'))
    args = parser.parse_args()
    print(json.dumps(verify(args.root), indent=2))
