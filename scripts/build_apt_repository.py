#!/usr/bin/env python3
"""Build a bounded signed Debian/Ubuntu repository from reviewed CMND .deb files."""
from __future__ import annotations

import argparse
from datetime import datetime, timezone
import gzip
import json
import os
from pathlib import Path
import re
import shutil
import subprocess


ALLOWED_PACKAGES = {'linux-cmnd', 'cmnd-linux'}
ALLOWED_ARCHITECTURES = {'amd64', 'all'}
SUITES = {'development', 'testing', 'stable'}


def run(args, *, cwd: Path | None = None, input_text: str | None = None) -> str:
    result = subprocess.run(list(map(str, args)), cwd=cwd, input=input_text, text=True,
                            capture_output=True, check=False)
    if result.returncode:
        raise RuntimeError(f'command failed: {args[0]}: {result.stderr.strip()[:1000]}')
    return result.stdout


def require_command(name: str) -> str:
    path = shutil.which(name)
    if not path:
        raise RuntimeError(f'required repository build command is missing: {name}')
    return path


def deb_field(path: Path, field: str) -> str:
    return run((require_command('dpkg-deb'), '-f', path, field)).strip()


def validate_deb(path: Path) -> dict[str, str | int]:
    if not path.is_file() or path.is_symlink():
        raise ValueError(f'Debian package must be a regular non-symlink file: {path}')
    if path.stat().st_size <= 0 or path.stat().st_size > 4 * 1024**3:
        raise ValueError(f'Debian package size outside repository bound: {path.name}')
    package = deb_field(path, 'Package')
    version = deb_field(path, 'Version')
    architecture = deb_field(path, 'Architecture')
    if package not in ALLOWED_PACKAGES:
        raise ValueError(f'unexpected package for CMND repository: {package}')
    if architecture not in ALLOWED_ARCHITECTURES:
        raise ValueError(f'unexpected architecture for {package}: {architecture}')
    if not re.fullmatch(r'[0-9][A-Za-z0-9.+:~_-]*', version):
        raise ValueError(f'invalid Debian version: {version}')
    return {'package': package, 'version': version, 'architecture': architecture, 'size': path.stat().st_size}


def release_date() -> str:
    epoch = int(os.environ.get('SOURCE_DATE_EPOCH', '0'))
    when = datetime.fromtimestamp(epoch, timezone.utc) if epoch else datetime.now(timezone.utc)
    return when.strftime('%a, %d %b %Y %H:%M:%S +0000')


def gzip_file(source: Path, destination: Path) -> None:
    epoch = int(os.environ.get('SOURCE_DATE_EPOCH', '0'))
    with source.open('rb') as inp, destination.open('wb') as raw:
        with gzip.GzipFile(filename='', mode='wb', fileobj=raw, mtime=epoch or 0) as zipped:
            shutil.copyfileobj(inp, zipped)


def build_repository(output: Path, debs: list[Path], *, codename: str, suite: str,
                     signing_key: str | None, allow_unsigned_development: bool,
                     execute: bool = False) -> dict:
    if not re.fullmatch(r'[a-z0-9][a-z0-9.-]{1,31}', codename):
        raise ValueError('invalid repository codename')
    if suite not in SUITES:
        raise ValueError('invalid repository suite')
    if not debs:
        raise ValueError('at least one .deb is required')
    metadata = [validate_deb(path) for path in debs]
    versions = {str(item['version']) for item in metadata}
    if len(versions) != 1:
        raise ValueError('cmnd-linux and linux-cmnd repository packages must use the same version')
    names = {str(item['package']) for item in metadata}
    if names != ALLOWED_PACKAGES:
        raise ValueError('repository build requires both linux-cmnd and cmnd-linux packages')
    if not signing_key and not (suite == 'development' and allow_unsigned_development):
        raise ValueError('signed repository metadata is required; unsigned output is allowed only for explicit development builds')
    if output.exists() and any(output.iterdir()):
        raise ValueError('repository output directory must be absent or empty')

    plan = {
        'executed': execute,
        'output': str(output),
        'codename': codename,
        'suite': suite,
        'version': next(iter(versions)),
        'packages': metadata,
        'signed': bool(signing_key),
    }
    if not execute:
        return plan

    apt_ftparchive = require_command('apt-ftparchive')
    if signing_key:
        require_command('gpg')
    output.mkdir(parents=True, exist_ok=True)
    for source, info in zip(debs, metadata):
        package = str(info['package'])
        initial = package[0]
        target = output / 'pool' / 'main' / initial / package / source.name
        target.parent.mkdir(parents=True, exist_ok=True)
        shutil.copyfile(source, target)
        target.chmod(0o644)

    binary = output / 'dists' / codename / 'main' / 'binary-amd64'
    binary.mkdir(parents=True)
    packages = binary / 'Packages'
    packages.write_text(run((apt_ftparchive, 'packages', 'pool/main'), cwd=output), encoding='utf-8')
    gzip_file(packages, binary / 'Packages.gz')

    dist = output / 'dists' / codename
    body = run((apt_ftparchive, 'release', f'dists/{codename}'), cwd=output)
    header = (
        'Origin: Music City Telecom\n'
        'Label: CMND Linux\n'
        f'Suite: {suite}\n'
        f'Codename: {codename}\n'
        'Architectures: amd64\n'
        'Components: main\n'
        'Description: CMND for Linux packages\n'
        f'Date: {release_date()}\n'
    )
    release = dist / 'Release'
    release.write_text(header + body, encoding='utf-8')

    if signing_key:
        run(('gpg', '--batch', '--yes', '--local-user', signing_key, '--digest-algo', 'SHA256',
             '--clearsign', '--output', dist / 'InRelease', release))
        run(('gpg', '--batch', '--yes', '--local-user', signing_key, '--digest-algo', 'SHA256',
             '--armor', '--detach-sign', '--output', dist / 'Release.gpg', release))

    plan['files'] = sorted(str(path.relative_to(output)) for path in output.rglob('*') if path.is_file())
    return plan


def parser() -> argparse.ArgumentParser:
    p = argparse.ArgumentParser()
    p.add_argument('--output', required=True, type=Path)
    p.add_argument('--deb', action='append', required=True, type=Path)
    p.add_argument('--codename', default='noble')
    p.add_argument('--suite', choices=sorted(SUITES), default='development')
    p.add_argument('--signing-key', help='GPG key fingerprint/key ID; private key material is never read from the repository')
    p.add_argument('--allow-unsigned-development', action='store_true')
    p.add_argument('--execute', action='store_true')
    return p


def main(argv=None) -> int:
    args = parser().parse_args(argv)
    try:
        result = build_repository(args.output, args.deb, codename=args.codename, suite=args.suite,
                                  signing_key=args.signing_key,
                                  allow_unsigned_development=args.allow_unsigned_development,
                                  execute=args.execute)
    except (OSError, RuntimeError, ValueError) as exc:
        print(f'build-apt-repository: {exc}', file=os.sys.stderr)
        return 2
    print(json.dumps(result, indent=2, sort_keys=True))
    return 0


if __name__ == '__main__':
    raise SystemExit(main())
