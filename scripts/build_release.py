"""Build only original public-safe release assets from a clean committed tree."""
from hashlib import sha256
import json
from pathlib import Path
import shutil
import subprocess

from build_deb import build, ROOT

RELEASE_PATHS = ['src', 'scripts', 'deploy', 'config', 'packaging', 'tests', 'docs',
                 'VERSION', 'pyproject.toml', 'README.md', 'LICENSE', 'CHANGELOG.md',
                 'AGENTS.md', '.gitattributes', '.gitignore']


def validate_sources():
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
    shutil.copyfile(ROOT / 'config/cmnd.example.toml', configuration)
    artifacts = [package, installer, manifest, source, guide, configuration]
    checksums = destination / 'SHA256SUMS'
    checksums.write_text(''.join(f'{sha256(path.read_bytes()).hexdigest()}  {path.name}\n' for path in artifacts), encoding='ascii')
    print(json.dumps({'version': version, 'assets': [str(path) for path in artifacts + [checksums]],
                      'vendor_payload_included': False}, indent=2))


if __name__ == '__main__':
    main()
