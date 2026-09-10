"""Interactive release updates through the existing verified Linux worker."""
from __future__ import annotations

import os
import sys

from . import __version__
from .updates import UpdateError, check_release, install_pending, settings


def interactive_update(*, read=None, write=None) -> int:
    read = input if read is None else read
    write = print if write is None else write
    config = settings()
    result = check_release(config, __version__)
    if not result['enabled']:
        write('Release checks are disabled in /etc/linux-cmnd-management/updates.json.')
        return 0
    if not result['available']:
        write(f'Linux CMND {__version__}: no newer release on the {config.get("channel", "stable")} channel.')
        return 0
    release = result['release']
    write(f'Installed: {__version__}; available: {release["version"]}' + (' (evaluation prerelease)' if release['prerelease'] else ''))
    write(release['url'])
    if os.name != 'posix' or os.geteuid() != 0:
        write('To review and install this update, rerun: sudo cmndctl --updates')
        return 0
    if not sys.stdin.isatty():
        write('No interactive terminal: nothing installed. Run sudo cmndctl --updates in a terminal.')
        return 0
    write('The worker will recheck the release, verify hashes/compatibility, and back up configuration and databases.')
    write('Services will restart. Review/pause vendor jobs first: restarting can resume queued TV operations.')
    write('Failed updates attempt rollback; inspect the reported recovery status before further operations.')
    expected = 'INSTALL ' + release['version']
    try:
        answer = read(f'Type {expected} to install, or press Enter to cancel: ').strip()
    except (EOFError, KeyboardInterrupt):
        write('Cancelled; nothing installed.')
        return 0
    if answer != expected:
        write('Cancelled; nothing installed.')
        return 0
    try:
        # Direct version-scoped handoff shares the worker lock but never races the GUI queue watcher.
        install_pending(execute=True, confirmed_job={'version': release['version'],
                        'release_id': release['release_id'], 'execute': True})
    except Exception as error:
        raise UpdateError('Update failed. Inspect /var/lib/cmnd-updates/status.json and the private attempt logs; '
                          'do not assume rollback succeeded. ' + str(error)) from error
    write(f'Updated to {release["version"]}; application readiness verified. Private backups retained in /var/lib/cmnd-updates.')
    return 0
