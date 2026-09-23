#!/usr/bin/env python3
"""Build the user-facing cmnd-linux APT package.

The known tooling package remains `linux-cmnd`; this package adds the complete
APT dependency graph, debconf activation scripts and a retained exact rollback
copy of the matching tooling .deb.
"""
from __future__ import annotations
from hashlib import sha256
from pathlib import Path
try:
    from build_deb import ROOT, ar_member, tar_blob, build as build_payload_deb
except ModuleNotFoundError:
    from scripts.build_deb import ROOT, ar_member, tar_blob, build as build_payload_deb

VENDOR_PACKAGE_VERSION='7.5.9-1'
TOMCAT_PACKAGE_VERSION='9.0.121-1'

def build()->Path:
    version=(ROOT/'VERSION').read_text().strip()
    payload=ROOT/'dist'/f'linux-cmnd_{version}_amd64.deb'
    if not payload.exists():payload=build_payload_deb()
    if not payload.is_file() or payload.is_symlink():raise ValueError('matching linux-cmnd payload package is missing or unsafe')
    control=f'''Package: cmnd-linux
Version: {version}
Section: admin
Priority: optional
Architecture: all
Pre-Depends: debconf (>= 1.5.0) | debconf-2.0
Depends: linux-cmnd (= {version}), cmnd-vendor-759 (= {VENDOR_PACKAGE_VERSION}), cmnd-tomcat9 (= {TOMCAT_PACKAGE_VERSION}), python3 (>= 3.11), adduser, ca-certificates, openssl, iproute2, iptables, 7zip, openjdk-17-jre-headless, apache2-bin, media-types, docker.io | docker-ce
Maintainer: Music City Telecom <tommy@tomcom.us>
Description: Philips CMND for Linux installation entry point
 Environment-aware APT installer for CMND for Linux. Reuses compatible existing
 services where qualified, preserves unrelated applications and falls back to
 isolated compatibility runtimes rather than changing unsafe host configuration.
'''.encode()
    ctl=[
      (None,'control',0o644,control),
      (ROOT/'packaging/cmnd-linux/templates','templates',0o644,None),
      (ROOT/'packaging/cmnd-linux/config','config',0o755,None),
      (ROOT/'packaging/cmnd-linux/postinst','postinst',0o755,None),
      (ROOT/'packaging/cmnd-linux/prerm','prerm',0o755,None),
      (ROOT/'packaging/cmnd-linux/postrm','postrm',0o755,None),
    ]
    notice=(f'cmnd-linux {version} is the APT entry package.\n'
            f'Requires linux-cmnd {version}, cmnd-vendor-759 {VENDOR_PACKAGE_VERSION}, and cmnd-tomcat9 {TOMCAT_PACKAGE_VERSION}.\n'
            'Philips/TP Vision components retain their original ownership and attribution.\n').encode()
    data=[(None,'usr/share/doc/cmnd-linux/README',0o644,notice),
          (payload,f'usr/lib/cmnd/packages/{payload.name}',0o600,None)]
    deb=b'!<arch>\n'+ar_member('debian-binary',b'2.0\n')+ar_member('control.tar.gz',tar_blob(ctl))+ar_member('data.tar.gz',tar_blob(data))
    out=ROOT/'dist'/f'cmnd-linux_{version}_all.deb'; out.parent.mkdir(exist_ok=True); out.write_bytes(deb)
    Path(str(out)+'.sha256').write_text(f'{sha256(deb).hexdigest()}  {out.name}\n'); return out

if __name__=='__main__':print(build())
