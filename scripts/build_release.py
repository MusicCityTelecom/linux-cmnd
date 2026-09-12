"""Build tooling plus the explicitly authorized, pinned original vendor bundle."""
import argparse
import hashlib
import json
from pathlib import Path
import shutil
import subprocess

from build_deb import build, ROOT
from vendor_bundle import verify as verify_vendor_bundle, NAME as VENDOR_BUNDLE_NAME

RELEASE_PATHS = ['src', 'scripts', 'deploy', 'config', 'packaging', 'tests', 'docs',
                 'VERSION', 'pyproject.toml', 'README.md', 'LICENSE', 'CHANGELOG.md',
                 'AGENTS.md', '.gitattributes', '.gitignore']


def validate_sources():
    if (ROOT / 'config/java-native-track.json').exists():
        raise SystemExit('Java-native research track cannot publish compatibility releases')
    # Build inputs must match the exact commit exported as the release source.
    # Include ignored files: a local ignored file must not slip into the package.
    inputs = RELEASE_PATHS
    subprocess.run(['git', 'diff', '--exit-code', 'HEAD', '--', *inputs], cwd=ROOT, check=True)
    untracked = subprocess.check_output(
        ['git', 'ls-files', '--others', '--', *inputs], cwd=ROOT, text=True).splitlines()
    untracked = [name for name in untracked
                 if '__pycache__' not in Path(name).parts and Path(name).suffix != '.pyc']
    if untracked:
        raise SystemExit('Uncommitted release inputs detected; commit or remove them before building')
    tracked = subprocess.check_output(['git', 'ls-files'], cwd=ROOT, text=True).splitlines()
    forbidden = {'.war', '.jar', '.exe', '.zip', '.sql', '.key', '.crt', '.pem', '.p12', '.pfx', '.pcap', '.pcapng', '.log'}
    if any(Path(name).suffix.lower() in forbidden or name.startswith(('.lab/', '.private-staging/')) for name in tracked):
        raise SystemExit('Private/vendor input detected in tracked release sources')


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument('--vendor-bundle', type=Path, required=True,
                        help='authorized, exact original-input ZIP; never a live installation backup')
    args = parser.parse_args()
    vendor_metadata = verify_vendor_bundle(args.vendor_bundle)
    validate_sources()
    package = build()
    version = (ROOT / 'VERSION').read_text().strip()
    destination = ROOT / 'dist'
    installer = destination / 'install.sh'
    installer.write_bytes((ROOT / 'scripts/install-native.sh').read_bytes().replace(b'\r\n', b'\n'))
    manifest = destination / 'linux-cmnd-update.json'
    manifest.write_text(json.dumps({'schema': 1, 'version': version, 'minimum_version': '0.4.0',
        'scope': 'tooling-only', 'vendor': '7.5.9', 'database_migration': False,
        'vendor_payload_changed': False}, indent=2) + '\n', encoding='utf-8')
    source = destination / f'linux-cmnd-{version}-source.tar.gz'
    subprocess.run(['git', 'archive', '--format=tar.gz', f'--prefix=linux-cmnd-{version}/',
                    '-o', str(source), 'HEAD', '--', *RELEASE_PATHS], cwd=ROOT, check=True)
    guide = destination / 'EVALUATION-GUIDE.md'
    shutil.copyfile(ROOT / 'docs/EVALUATION-GUIDE.md', guide)
    configuration = destination / 'cmnd.example.toml'
    shutil.copyfile(ROOT / 'config/cmnd.install.toml', configuration)
    bootstrap = destination / 'bootstrap.py'
    bootstrap.write_bytes((ROOT / 'scripts/bootstrap.py').read_bytes().replace(b'\r\n', b'\n'))
    artifacts = [package, installer, manifest, source, guide, configuration, bootstrap]
    bundled = destination / VENDOR_BUNDLE_NAME
    if bundled.exists():
        if verify_vendor_bundle(bundled) != vendor_metadata:
            raise SystemExit('Existing release vendor bundle differs; refusing overwrite')
    else:
        shutil.copyfile(args.vendor_bundle, bundled)
    if verify_vendor_bundle(bundled) != vendor_metadata:
        raise SystemExit('Vendor bundle changed while copying')
    vendor_manifest = destination / 'vendor-bundle.json'
    vendor_manifest.write_text(json.dumps(vendor_metadata, indent=2) + '\n', encoding='utf-8')
    artifacts += [bundled, vendor_manifest]
    checksums = destination / 'SHA256SUMS'
    lines = []
    for path in artifacts:
        with path.open('rb') as stream:
            lines.append(f'{hashlib.file_digest(stream, "sha256").hexdigest()}  {path.name}\n')
    checksums.write_text(''.join(lines), encoding='ascii')
    print(json.dumps({'version': version, 'assets': [str(path) for path in artifacts + [checksums]],
                      'vendor_payload_included': True, 'vendor_bundle': vendor_metadata}, indent=2))


if __name__ == '__main__':
    main()
