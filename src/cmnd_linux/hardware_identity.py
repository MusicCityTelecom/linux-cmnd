"""Give only the CMND group read access to the real kernel motherboard serial."""
import os
from pathlib import Path
import stat


def usable_serial(data):
    try:
        value = data.decode('utf-8').strip()
    except UnicodeDecodeError:
        return False
    return (0 < len(value) <= 256 and not any(ord(char) < 32 for char in value)
            and value.lower() not in {'unknown', 'none', 'not specified', 'default string',
                                     'to be filled by o.e.m.', 'to be filled by oem'})


def grant_access(*, execute=False):
    report = {'executed': execute, 'hardware_serial_available': False,
              'serial_generated_or_copied': False, 'license_requested': False}
    if not execute:
        return report | {'note': 'Permit CMND-group read of the real DMI serial; no synthetic identity'}
    if os.name != 'posix' or os.geteuid() != 0:
        raise PermissionError('Hardware identity access requires Linux root')
    import pwd
    group = pwd.getpwnam('cmnd').pw_gid
    try:
        path = Path('/sys/class/dmi/id/board_serial').resolve(strict=True)
    except FileNotFoundError:
        return report | {'warning': 'No motherboard serial exposed; review VM SMBIOS identity before licensing'}
    if path != Path('/sys/devices/virtual/dmi/id/board_serial'):
        raise ValueError('Unexpected kernel DMI serial path')
    before = path.stat()
    if not stat.S_ISREG(before.st_mode) or before.st_uid != 0:
        raise ValueError('Unexpected kernel DMI serial ownership/type')
    with path.open('rb') as source:
        if not usable_serial(source.read(1024)):
            return report | {'warning': 'Missing/placeholder motherboard serial; no identity synthesized'}
    if not before.st_mode & stat.S_IROTH:
        try:
            os.chown(path, 0, group)
            os.chmod(path, 0o440)
        except OSError:
            os.chown(path, before.st_uid, before.st_gid)
            os.chmod(path, stat.S_IMODE(before.st_mode))
            raise
    return report | {'hardware_serial_available': True, 'note': 'Original vendor OSHI reads actual hardware; no serial value logged'}
