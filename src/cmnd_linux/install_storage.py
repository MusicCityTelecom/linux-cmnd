"""Read-only capacity policy, also embedded verbatim in standalone bootstrap."""
from pathlib import Path
import os
import shutil
import tomllib

# BEGIN STANDALONE STORAGE POLICY
def storage_preflight(upload_mb=8096, *, phase='download'):
    if type(upload_mb) is not int or not 1 <= upload_mb <= 8096:
        raise ValueError('CMS upload_limit_mb must be an integer from 1 to 8096')
    if phase not in ('download', 'prepared'):
        raise ValueError('Invalid storage preflight phase')
    gib = 1024 ** 3
    # Peak budget, not a promise about final disk use. Two simultaneous copies
    # of the largest configured upload are retained for staging + CMS storage.
    allocations = [
        ('/var/tmp', 2 if phase == 'download' else 0, 'vendor ZIP, extracted inputs and tooling download'),
        ('/var/cache', .5, 'Tomcat archive and retained installer'),
        ('/var/lib/docker', 4, 'MySQL/PHP images and transient build layers'),
        ('/opt', 2, 'installed application runtime'),
        ('/var/lib/cmnd-deployment', 1.5, 'database initialization and candidate staging'),
        ('/usr', 1.5, 'Java, Apache and OS prerequisites'),
        ('/tmp', .5, 'temporary working space'),
        ('/var/lib/cmnd/php-uploads', upload_mb / 1024, 'upload staging reserve'),
        ('/opt/cmnd/SmartCMS/sites/default/files', upload_mb / 1024, 'CMS upload copy reserve')]
    filesystems = {}
    for destination, size, reason in allocations:
        path = Path(destination)
        while not path.exists():
            path = path.parent
        disk = shutil.disk_usage(path)
        entry = filesystems.setdefault(path.stat().st_dev, {'path': str(path), 'total': disk.total,
            'free': disk.free, 'required': 0, 'costs': []})
        entry['required'] += int(size * gib)
        entry['costs'].append(f'{size:.2f} GiB {reason}')
    failed = False
    for entry in filesystems.values():
        print(f'Storage preflight ({phase}): filesystem containing {entry["path"]}; '
              f'total {entry["total"]/gib:.2f} GiB; free {entry["free"]/gib:.2f} GiB; '
              f'required free {entry["required"]/gib:.2f} GiB', flush=True)
        print('  Budget: ' + '; '.join(entry['costs']), flush=True)
        failed |= entry['free'] < entry['required']
    if failed:
        raise ValueError('Insufficient free storage. Recommended target disk: at least 40 GiB; '
                         'expand the filesystem, not just the virtual disk, or free space before retrying. '
                         'No automatic repartitioning or database initialization was performed.')
    if Path('/var/run/reboot-required').exists():
        print('WARNING: This host has a pending reboot. Reboot at an operator-approved time before '
              'installation if possible; this installer will NOT reboot or power off the host.', flush=True)
    return list(filesystems.values())
# END STANDALONE STORAGE POLICY


def main():
    import argparse
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument('--config', type=Path, required=True)
    parser.add_argument('--phase', choices=('download', 'prepared'), default='download')
    args = parser.parse_args()
    content = tomllib.loads(args.config.read_text(encoding='utf-8'))
    storage_preflight(content.get('cms', {}).get('upload_limit_mb', 8096), phase=args.phase)


if __name__ == '__main__':
    try:
        main()
    except (ValueError, OSError) as error:
        raise SystemExit('FINAL STATUS: FAIL - storage preflight: ' + str(error))
