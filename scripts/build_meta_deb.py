#!/usr/bin/env python3
"""Build the user-facing cmnd-linux APT meta package.

The payload remains in linux-cmnd during 0.8 development so the known 0.7.1 file
layout and package internals do not need a risky rename.  Repository users will
install `cmnd-linux`; APT then resolves the matching payload package.
"""
from __future__ import annotations

from hashlib import sha256
from pathlib import Path

from build_deb import ROOT, ar_member, tar_blob


def build() -> Path:
    version = (ROOT / 'VERSION').read_text().strip()
    control = f'''Package: cmnd-linux
Version: {version}
Section: admin
Priority: optional
Architecture: all
Depends: linux-cmnd (= {version})
Maintainer: Music City Telecom <tommy@tomcom.us>
Description: Philips CMND for Linux installation entry point
 User-facing APT package for CMND for Linux. The version-matched linux-cmnd
 payload package contains the deployment tooling and runtime integration.
'''.encode()
    notice = (
        'cmnd-linux is the user-facing APT installation package.\n'
        f'It requires the exact linux-cmnd {version} payload package.\n'
        'Philips vendor applications retain their original ownership and attribution.\n'
    ).encode()
    deb = b'!<arch>\n' + ar_member('debian-binary', b'2.0\n')
    deb += ar_member('control.tar.gz', tar_blob([(None, 'control', 0o644, control)]))
    deb += ar_member('data.tar.gz', tar_blob([
        (None, 'usr/share/doc/cmnd-linux/README', 0o644, notice),
    ]))
    output = ROOT / 'dist' / f'cmnd-linux_{version}_all.deb'
    output.parent.mkdir(exist_ok=True)
    output.write_bytes(deb)
    Path(str(output) + '.sha256').write_text(f'{sha256(deb).hexdigest()}  {output.name}\n')
    return output


if __name__ == '__main__':
    print(build())
