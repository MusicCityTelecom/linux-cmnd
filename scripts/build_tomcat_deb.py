#!/usr/bin/env python3
"""Build the pinned CMND Tomcat 9 archive package."""
from __future__ import annotations
from hashlib import sha256
from io import BytesIO
from pathlib import Path
import gzip, os, shutil, sys, tarfile, tempfile
ROOT=Path(__file__).resolve().parents[1]; sys.path.insert(0,str(ROOT/'src'))
from cmnd_linux.application_stage import TOMCAT_SHA512,TOMCAT_VERSION,_tomcat_members
PACKAGE='cmnd-tomcat9'; PACKAGE_VERSION=TOMCAT_VERSION+'-1'; INSTALL_DIR='usr/lib/cmnd/tomcat'; EPOCH=int(os.environ.get('SOURCE_DATE_EPOCH','1788835200'))
def _info(name,size,mode):
    x=tarfile.TarInfo(name); x.size=size; x.mode=mode; x.mtime=EPOCH; x.uid=x.gid=0; x.uname=x.gname='root'; return x
def _dir(a,name):x=_info(name,0,0o755); x.type=tarfile.DIRTYPE; a.addfile(x)
def _control(path):
    data=f'Package: {PACKAGE}\nVersion: {PACKAGE_VERSION}\nSection: admin\nPriority: optional\nArchitecture: all\nMaintainer: Music City Telecom <tommy@tomcom.us>\nDescription: Pinned Apache Tomcat runtime archive for CMND Linux\n Exact Apache Tomcat {TOMCAT_VERSION} archive used by CMND for Linux.\n'.encode()
    with path.open('wb') as raw,gzip.GzipFile(filename='',mode='wb',fileobj=raw,mtime=EPOCH) as gz,tarfile.open(fileobj=gz,mode='w') as a:a.addfile(_info('control',len(data),0o644),BytesIO(data))
def _data(path,source):
    note=f'Apache Tomcat {TOMCAT_VERSION}.\nExpected SHA-512: {TOMCAT_SHA512}\n'.encode()
    with path.open('wb') as raw,gzip.GzipFile(filename='',mode='wb',fileobj=raw,mtime=EPOCH) as gz,tarfile.open(fileobj=gz,mode='w') as a:
        for d in ('usr','usr/lib','usr/lib/cmnd',INSTALL_DIR,'usr/share','usr/share/doc',f'usr/share/doc/{PACKAGE}'):_dir(a,d)
        with source.open('rb') as s:a.addfile(_info(f'{INSTALL_DIR}/apache-tomcat-{TOMCAT_VERSION}.tar.gz',source.stat().st_size,0o644),s)
        a.addfile(_info(f'usr/share/doc/{PACKAGE}/ATTRIBUTION',len(note),0o644),BytesIO(note))
def _ar(out,name,source):
    size=source.stat().st_size; out.write((name+'/').encode().ljust(16,b' ')+str(EPOCH).encode().ljust(12,b' ')+b'0     0     100644  '+str(size).encode().ljust(10,b' ')+b'`\n')
    with source.open('rb') as s:shutil.copyfileobj(s,out,1024*1024)
    if size%2:out.write(b'\n')
def build(archive):
    archive=Path(archive)
    if not archive.is_file() or archive.is_symlink():raise ValueError('Tomcat input must be a regular non-symlink file')
    _tomcat_members(archive); output=ROOT/'dist'/f'{PACKAGE}_{PACKAGE_VERSION}_all.deb'; output.parent.mkdir(exist_ok=True)
    with tempfile.TemporaryDirectory(prefix='cmnd-tomcat-deb-') as t:
        r=Path(t); c=r/'control.tar.gz'; d=r/'data.tar.gz'; b=r/'debian-binary'; _control(c); _data(d,archive); b.write_bytes(b'2.0\n')
        with output.open('wb') as o:o.write(b'!<arch>\n'); _ar(o,'debian-binary',b); _ar(o,'control.tar.gz',c); _ar(o,'data.tar.gz',d)
    h=sha256();
    with output.open('rb') as s:
        for chunk in iter(lambda:s.read(1024*1024),b''):h.update(chunk)
    Path(str(output)+'.sha256').write_text(f'{h.hexdigest()}  {output.name}\n'); return output
def main(argv=None):
    import argparse; p=argparse.ArgumentParser(); p.add_argument('archive',type=Path); a=p.parse_args(argv)
    try:print(build(a.archive))
    except (OSError,ValueError) as e:print('build-tomcat-deb: '+str(e),file=sys.stderr); return 2
    return 0
if __name__=='__main__':raise SystemExit(main())
