"""Small private stage log; expected probes are logged only at their deadline."""
from datetime import datetime, timezone
import os
from pathlib import Path
import stat


def append_private(path: Path, message: str):
    # Deployment creates this root-only directory before any log is written.
    for parent in path.parents:
        info = parent.lstat()
        if not stat.S_ISDIR(info.st_mode) or info.st_uid != 0 or info.st_mode & 0o022:
            raise ValueError('Untrusted diagnostic directory')
    fd = os.open(path, os.O_WRONLY | os.O_APPEND | os.O_CREAT | os.O_NOFOLLOW | os.O_NONBLOCK, 0o600)
    try:
        info = os.fstat(fd)
        if not stat.S_ISREG(info.st_mode) or info.st_uid != 0 or info.st_mode & 0o077 or info.st_nlink != 1:
            raise ValueError('Untrusted diagnostic file')
        with os.fdopen(fd, 'a', encoding='utf-8', closefd=False) as output:
            output.write(message + '\n')
    finally:
        os.close(fd)


def event(message: str, *, level='INFO', state=Path('/var/lib/cmnd-deployment'), private_detail=None):
    line = f'{datetime.now(timezone.utc).isoformat(timespec="seconds")} {level} {message}'
    print(line, flush=True)
    if state.is_dir():
        append_private(state / 'deployment.log', line)
        if private_detail:
            append_private(state / 'deployment.log', str(private_detail)[-8192:])
