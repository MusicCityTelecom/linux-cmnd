"""Fixed-repository GitHub updates. No arbitrary URL, shell or vendor migration API."""
from __future__ import annotations

import hashlib
import json
import os
from pathlib import Path
import re
import ssl
import subprocess
import time
from urllib.error import HTTPError
from urllib.parse import urlsplit
from urllib.request import Request, HTTPSHandler, HTTPRedirectHandler, ProxyHandler, build_opener

REPOSITORY = 'MusicCityTelecom/linux-cmnd'
API = 'https://api.github.com/repos/' + REPOSITORY
MAX_PACKAGE = 64 * 1024**2
SETTINGS = Path('/etc/linux-cmnd-management/updates.json')
STATE = Path('/var/lib/cmnd-updates')
QUEUE = Path('/var/lib/cmnd-update-requests/request.json')
CONFIGURATION = Path('/etc/cmnd')
SERVICES = ('cmnd-tomcat', 'cmnd-apache', 'cmnd-php')


class UpdateError(ValueError):
    pass


def version_tuple(value: str) -> tuple[int, int, int]:
    if not isinstance(value, str) or not re.fullmatch(r'(0|[1-9][0-9]*)\.(0|[1-9][0-9]*)\.(0|[1-9][0-9]*)', value):
        raise UpdateError('unsupported release version')
    return tuple(map(int, value.split('.')))


def settings(path: Path = SETTINGS) -> dict:
    value = json.loads(path.read_text())
    if value.get('channel', 'stable') not in ('stable', 'preview') or type(value.get('enabled', True)) is not bool:
        raise UpdateError('invalid updater settings')
    return value


class NoRedirect(HTTPRedirectHandler):
    def redirect_request(self, *args):
        return None


def credential(config: dict) -> str | None:
    path = config.get('token_file')
    if not path:
        return None
    path = Path(path)
    if path.is_symlink() or not path.is_file() or (os.name == 'posix' and path.stat().st_mode & 0o007):
        raise UpdateError('GitHub token must be a private regular server-side file')
    token = path.read_text().strip()
    if not re.fullmatch(r'[A-Za-z0-9_]{20,255}', token):
        raise UpdateError('invalid GitHub token file')
    return token


def fetch(path: str, config: dict, *, binary: bool = False, destination: Path | None = None, limit: int = 2 * 1024**2):
    if not re.fullmatch(r'/releases(?:\?per_page=100|/assets/[1-9][0-9]*)', path):
        raise UpdateError('updater accepts only fixed GitHub release endpoints')
    headers = {'Accept': 'application/octet-stream' if binary else 'application/vnd.github+json',
               'User-Agent': 'MusicCityTelecom-linux-cmnd-updater', 'X-GitHub-Api-Version': '2022-11-28'}
    token = credential(config)
    if token:
        headers['Authorization'] = 'Bearer ' + token
    opener = build_opener(ProxyHandler({}), HTTPSHandler(context=ssl.create_default_context()), NoRedirect())
    url, deadline = API + path, time.monotonic() + 300
    for _ in range(5):
        try:
            response = opener.open(Request(url, headers=headers), timeout=20)
            break
        except HTTPError as error:
            if not binary or error.code not in (301, 302, 303, 307, 308):
                raise UpdateError(f'GitHub update request failed (HTTP {error.code}); check repository access or rate limits') from None
            location = error.headers.get('Location', '')
            parsed = urlsplit(location)
            if (parsed.scheme != 'https' or parsed.hostname not in
                    {'release-assets.githubusercontent.com', 'objects.githubusercontent.com'}
                    or parsed.username is not None or parsed.password is not None or parsed.port not in (None, 443)):
                raise UpdateError('untrusted release asset redirect')
            url = location
            headers = {'Accept': 'application/octet-stream', 'User-Agent': 'MusicCityTelecom-linux-cmnd-updater'}
    else:
        raise UpdateError('too many release asset redirects')
    chunks, size = [], 0
    stream = None
    try:
        if destination:
            stream = os.fdopen(os.open(destination, os.O_CREAT | os.O_EXCL | os.O_WRONLY, 0o600), 'wb')
        with response:
            while True:
                chunk = response.read(65536)
                if not chunk:
                    break
                size += len(chunk)
                if size > limit or time.monotonic() > deadline:
                    raise UpdateError('release response exceeded bounds')
                if stream:
                    stream.write(chunk)
                else:
                    chunks.append(chunk)
    finally:
        if stream:
            stream.close()
    return size if destination else b''.join(chunks)


def select_release(releases: list, current: str, channel: str) -> dict | None:
    current_version = version_tuple(current)
    candidates = []
    if channel not in ('stable', 'preview') or not isinstance(releases, list):
        raise UpdateError('invalid release channel/response')
    for release in releases:
        if not isinstance(release, dict) or release.get('draft') is not False:
            continue
        if release.get('prerelease') and channel != 'preview':
            continue
        tag = release.get('tag_name', '')
        if not isinstance(tag, str) or not tag.startswith('v'):
            continue
        try:
            version = version_tuple(tag[1:])
        except UpdateError:
            continue
        if version > current_version:
            candidates.append((version, release))
    if not candidates:
        return None
    release = max(candidates, key=lambda item: item[0])[1]
    version = release['tag_name'][1:]
    if type(release.get('id')) is not int or release['id'] < 1:
        raise UpdateError('release lacks a valid immutable ID')
    assets = {}
    for name in (f'linux-cmnd_{version}_amd64.deb', 'linux-cmnd-update.json'):
        matches = [asset for asset in release.get('assets', []) if isinstance(asset, dict) and asset.get('name') == name]
        if len(matches) != 1:
            raise UpdateError('new release lacks an unambiguous installable package/manifest')
        asset = matches[0]
        if (type(asset.get('id')) is not int or asset['id'] < 1
                or type(asset.get('size')) is not int or not 1 <= asset['size'] <= MAX_PACKAGE
                or not re.fullmatch(r'sha256:[a-f0-9]{64}', asset.get('digest') or '')):
            raise UpdateError('release asset lacks a valid size, ID or GitHub SHA-256 digest')
        assets[name] = {key: asset[key] for key in ('id', 'size', 'digest', 'name')}
    return {'version': version, 'release_id': release['id'], 'assets': assets,
            'url': f'https://github.com/{REPOSITORY}/releases/tag/v{version}',
            'prerelease': bool(release.get('prerelease'))}


def check_release(config: dict, current: str) -> dict:
    if not config.get('enabled', True):
        return {'enabled': False, 'current_version': current, 'available': False}
    releases = json.loads(fetch('/releases?per_page=100', config))
    candidate = select_release(releases, current, config.get('channel', 'stable'))
    return {'enabled': True, 'current_version': current, 'available': candidate is not None, 'release': candidate}


def download_asset(asset: dict, config: dict, destination: Path, *, limit: int = MAX_PACKAGE):
    fetch('/releases/assets/' + str(asset['id']), config, binary=True, destination=destination, limit=limit)
    digest = hashlib.sha256(destination.read_bytes()).hexdigest()
    if destination.stat().st_size != asset['size'] or 'sha256:' + digest != asset['digest']:
        raise UpdateError('release asset size/SHA-256 verification failed')


def validate_manifest(manifest: dict, current: str, selected: str, vendor: str):
    if (manifest.get('schema') != 1 or manifest.get('version') != selected
            or manifest.get('scope') != 'tooling-only' or manifest.get('vendor') != vendor
            or manifest.get('database_migration') is not False
            or manifest.get('vendor_payload_changed') is not False
            or version_tuple(current) < version_tuple(manifest.get('minimum_version', ''))
            or version_tuple(current)[0] != version_tuple(selected)[0]):
        raise UpdateError('update requires a manual vendor/database/major-version migration')


def validate_job(job: dict) -> None:
    if (not isinstance(job, dict) or set(job) != {'version', 'release_id', 'execute'}
            or job.get('execute') is not True or type(job.get('release_id')) is not int or job['release_id'] < 1):
        raise UpdateError('installation requires an explicit, version-scoped confirmation')
    version_tuple(job['version'])


def write_status(value: dict):
    # Root owns this directory; GUI has read-only group access to status.
    path = STATE / 'status.json'
    temporary = STATE / ('status-' + str(time.time_ns()) + '.next')
    try:
        with os.fdopen(os.open(temporary, os.O_CREAT | os.O_EXCL | os.O_WRONLY, 0o640), 'w') as stream:
            if os.name == 'posix':
                # Also support an explicitly invoked root CLI outside systemd's
                # cmnd-admin group and umask, without hiding status from the GUI.
                os.fchown(stream.fileno(), -1, STATE.stat().st_gid)
                os.fchmod(stream.fileno(), 0o640)
            json.dump(value, stream)
            stream.flush()
            os.fsync(stream.fileno())
        os.replace(temporary, path)
    finally:
        temporary.unlink(missing_ok=True)


def install_pending(*, execute: bool = False, confirmed_job: dict | None = None):
    """Root-only fixed operation, using a queued or directly confirmed CLI job."""
    if not execute or os.name != 'posix' or os.geteuid() != 0:
        raise UpdateError('update worker requires Linux root and --execute')
    if confirmed_job is not None:
        validate_job(confirmed_job)
    import fcntl
    import gzip
    import tarfile
    from . import __version__
    from .config import load_config
    from .native_deploy import wait_ready
    lock = (STATE / 'worker.lock').open('a')
    try:
        fcntl.flock(lock, fcntl.LOCK_EX | fcntl.LOCK_NB)
    except BlockingIOError:
        lock.close()
        raise UpdateError('another update worker is running; inspect its status before retrying') from None
    except Exception:
        lock.close()
        raise
    try:
        if confirmed_job is not None:
            if QUEUE.exists() or QUEUE.is_symlink():
                raise UpdateError('another update is queued; inspect its status before retrying')
            job = dict(confirmed_job)
        else:
            with os.fdopen(os.open(QUEUE, os.O_RDONLY | os.O_NOFOLLOW | os.O_NONBLOCK), 'r') as handle:
                import stat
                if not stat.S_ISREG(os.fstat(handle.fileno()).st_mode):
                    raise UpdateError('queued job must be a regular file')
                job = json.loads(handle.read(4097))
            validate_job(job)
            QUEUE.unlink()
    except Exception:
        if confirmed_job is not None:
            lock.close()
            raise
        # Reject malformed/truncated legacy jobs without wedging PathExists or
        # retaining an actionable confirmation. Never follow a queue symlink.
        try:
            QUEUE.unlink(missing_ok=True)
            write_status({'state': 'failed', 'error': 'InvalidQueuedJob',
                          'previous_runtime_recovered': False, 'backup_retained': False,
                          'runtime_unchanged': True, 'operator_review_required': True})
        finally:
            lock.close()
        raise UpdateError('invalid queued job rejected; runtime unchanged') from None
    attempt = STATE / ('attempt-' + str(time.time_ns()))
    try:
        attempt.mkdir(mode=0o700)
    except Exception:
        lock.close()
        raise
    stopped, upgraded, database_backup_complete = False, False, False

    def command(*args, timeout=300):
        result = subprocess.run(args, capture_output=True, timeout=timeout)
        if result.returncode:
            (attempt / 'command-error.log').write_bytes(result.stdout + result.stderr)
            raise UpdateError('update command failed; inspect private attempt log')
        return result.stdout

    try:
        write_status({'state': 'verifying', 'version': job['version']})
        config = settings()
        candidate = check_release(config, __version__).get('release')
        if not candidate or any(candidate[key] != job[key] for key in ('version', 'release_id')):
            raise UpdateError('selected release changed or is no longer a newer permitted release')
        manifest_path = attempt / 'manifest.json'
        download_asset(candidate['assets']['linux-cmnd-update.json'], config, manifest_path, limit=65536)
        vendor = json.loads((CONFIGURATION / 'deployment.json').read_text())['vendor']
        validate_manifest(json.loads(manifest_path.read_text()), __version__, candidate['version'], vendor)
        package = attempt / 'candidate.deb'
        download_asset(candidate['assets'][f"linux-cmnd_{candidate['version']}_amd64.deb"], config, package)
        expected = f"linux-cmnd\namd64\n{candidate['version']}\n".encode()
        actual = b''.join(command('dpkg-deb', '-f', str(package), field) for field in ('Package', 'Architecture', 'Version'))
        if actual != expected:
            raise UpdateError('downloaded package identity differs from selected release')
        baseline = STATE / 'current.deb'
        if not baseline.is_file() or baseline.is_symlink():
            raise UpdateError('retained current installer .deb required before updates can be installed')
        if command('dpkg-deb', '-f', str(baseline), 'Version').decode().strip() != __version__:
            raise UpdateError('rollback package does not match the installed version')
        command('cp', '--preserve=mode', str(baseline), str(attempt / 'previous.deb'))
        with tarfile.open(attempt / 'configuration.tar.gz', 'w:gz') as archive:
            archive.add(CONFIGURATION, arcname='etc/cmnd', recursive=True)
        write_status({'state': 'backing-up', 'version': job['version']})
        # A failed stop can still have stopped a subset of the services.
        stopped = True
        command('systemctl', 'stop', *SERVICES, timeout=180)
        import shutil
        if shutil.disk_usage(attempt).free < 4 * 1024**3:
            raise UpdateError('at least 4GiB free space required for the private update backup')
        raw_backup = attempt / 'databases.sql'
        with raw_backup.open('xb') as output:
            process = subprocess.Popen(['docker', 'exec', 'cmnd-native-mysql', 'mysqldump',
                '--defaults-extra-file=/run/secrets/client.cnf', '--single-transaction', '--routines', '--events',
                '--databases', 'smartinstall', 'cas', 'tpvision', 'smartcontroldb', 'smartcms'],
                stdout=output, stderr=subprocess.DEVNULL)
            try:
                if process.wait(timeout=300):
                    raise UpdateError('pre-update database backup failed')
            except subprocess.TimeoutExpired:
                process.kill()
                process.wait(timeout=10)
                raise UpdateError('pre-update database backup timed out') from None
        with raw_backup.open('rb') as source, gzip.open(attempt / 'databases.sql.gz', 'wb') as archive:
            shutil.copyfileobj(source, archive)
        database_backup_complete = True
        # Both private files are retained; never erase backup evidence on failure.
        write_status({'state': 'installing', 'version': job['version']})
        upgraded = True  # Even a failed dpkg may have unpacked part of the new version.
        command('dpkg', '--force-confold', '--install', str(package))
        command('systemctl', 'daemon-reload')
        command('systemctl', 'start', *reversed(SERVICES), timeout=180)
        if not wait_ready(load_config(CONFIGURATION / 'cmnd.toml'), 900)['readiness_verified']:
            raise UpdateError('post-update application readiness failed')
        command('systemctl', 'try-restart', 'cmnd-admin')
        command('cp', '--preserve=mode', str(package), str(STATE / 'current.next'))
        os.replace(STATE / 'current.next', baseline)
        write_status({'state': 'complete', 'version': job['version'], 'configuration_preserved': True, 'backup_retained': True})
    except Exception as error:
        recovered = False
        if stopped:
            try:
                if upgraded:
                    command('dpkg', '--force-confold', '--install', str(attempt / 'previous.deb'))
                    command('systemctl', 'daemon-reload')
                    command('cp', '--preserve=mode', str(attempt / 'previous.deb'), str(STATE / 'current.next'))
                    os.replace(STATE / 'current.next', baseline)
                    command('systemctl', 'try-restart', 'cmnd-admin')
                command('systemctl', 'start', *reversed(SERVICES), timeout=180)
                recovered = wait_ready(load_config(CONFIGURATION / 'cmnd.toml'), 900)['readiness_verified']
            except Exception:
                pass
        write_status({'state': 'failed', 'error': type(error).__name__, 'previous_runtime_recovered': recovered,
                      'backup_retained': database_backup_complete, 'operator_review_required': True})
        raise
    finally:
        lock.close()
