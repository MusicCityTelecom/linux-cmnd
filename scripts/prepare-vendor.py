#!/usr/bin/env python3
"""Prepare a private, minimal payload from original licensed 7.5.9 inputs.

Run from a source checkout on Windows or Linux. Never includes a live database,
certificates, credentials, customer content, firmware, or unrelated files.
"""
from __future__ import annotations

import argparse
import hashlib
import os
from pathlib import Path
import sys
import zipfile

sys.path.insert(0, str(Path(__file__).resolve().parents[1] / 'src'))
from cmnd_linux.application_stage import VENDOR_INPUT_HASHES
from cmnd_linux.artifacts import file_hash


def prepare(source, output, execute=False):
    source, output = Path(source), Path(output)
    for name, expected in VENDOR_INPUT_HASHES.items():
        path = source / name
        if not path.is_file() or any(p.is_symlink() for p in (path, *path.parents)) or file_hash(path) != expected:
            raise ValueError('Missing or changed original input: ' + name + '; use the extracted original installer, not modified runtime files')
    if output.exists() or any(p.is_symlink() for p in (output, *output.parents)):
        raise ValueError('Refusing existing output or symlink path')
    if not execute:
        return {'verified_inputs': len(VENDOR_INPUT_HASHES), 'written': False}
    # Exclusive output; restrictive mode on Linux. On Windows, use a private ACL-protected directory.
    with os.fdopen(os.open(output, os.O_WRONLY | os.O_CREAT | os.O_EXCL, 0o600), 'wb') as stream:
        with zipfile.ZipFile(stream, 'w', compression=zipfile.ZIP_STORED) as archive:
            for name in sorted(VENDOR_INPUT_HASHES):
                archive.write(source / name, name)
    # Verify the bytes copied as well as the inputs (detect concurrent modification).
    with zipfile.ZipFile(output) as archive:
        for name, expected in VENDOR_INPUT_HASHES.items():
            with archive.open(name) as stream:
                digest = hashlib.file_digest(stream, 'sha256').hexdigest()
            if digest != expected:
                raise ValueError('Source changed while packaging; do not use the incomplete private output')
    return {'verified_inputs': len(VENDOR_INPUT_HASHES), 'written': True, 'sha256': file_hash(output)}


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument('--source', type=Path, required=True)
    parser.add_argument('--output', type=Path, required=True)
    parser.add_argument('--execute', action='store_true')
    args = parser.parse_args()
    result = prepare(args.source, args.output, args.execute)
    print(result)
    print('Private licensed payload only. Transfer directly to the evaluator; never commit or upload to public GitHub.')


if __name__ == '__main__':
    try:
        main()
    except (ValueError, OSError, zipfile.BadZipFile) as error:
        print(str(error), file=sys.stderr)
        sys.exit(1)
