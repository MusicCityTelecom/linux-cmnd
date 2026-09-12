#!/usr/bin/env python3
"""Build a bounded signed Debian/Ubuntu repository from reviewed CMND .deb files."""
from __future__ import annotations
import argparse
from datetime import datetime,timezone
import gzip,json,os,re,shutil,subprocess
from pathlib import Path

REQUIRED_PACKAGES={'linux-cmnd','cmnd-linux','cmnd-vendor-759','cmnd-tomcat9'}
ALLOWED_ARCHITECTURES={'amd64','all'}; SUITES={'development','testing','stable'}
VENDOR_VERSION='7.5.9-1'; TOMCAT_VERSION='9.0.121-1'
def run(args,*,cwd=None,input_text=None):
    r=subprocess.run(list(map(str,args)),cwd=cwd,input=input_text,text=True,capture_output=True,check=False)
    if r.returncode:raise RuntimeError(f'command failed: {args[0]}: {r.stderr.strip()[:1000]}')
    return r.stdout
def require(name):
    p=shutil.which(name)
    if not p:raise RuntimeError('required repository build command is missing: '+name)
    return p
def field(path,name):return run((require('dpkg-deb'),'-f',path,name)).strip()
def validate(path):
    if not path.is_file() or path.is_symlink():raise ValueError('Debian package must be a regular non-symlink file: '+str(path))
    if not 0<path.stat().st_size<=4*1024**3:raise ValueError('Debian package size outside bound: '+path.name)
    p,v,a=field(path,'Package'),field(path,'Version'),field(path,'Architecture')
    if p not in REQUIRED_PACKAGES:raise ValueError('unexpected package for CMND repository: '+p)
    if a not in ALLOWED_ARCHITECTURES:raise ValueError(f'unexpected architecture for {p}: {a}')
    if not re.fullmatch(r'[0-9][A-Za-z0-9.+:~_-]*',v):raise ValueError('invalid Debian version: '+v)
    return {'package':p,'version':v,'architecture':a,'size':path.stat().st_size}
def release_date():
    e=int(os.environ.get('SOURCE_DATE_EPOCH','0')); d=datetime.fromtimestamp(e,timezone.utc) if e else datetime.now(timezone.utc); return d.strftime('%a, %d %b %Y %H:%M:%S +0000')
def gzip_file(src,dst):
    e=int(os.environ.get('SOURCE_DATE_EPOCH','0'))
    with src.open('rb') as i,dst.open('wb') as raw,gzip.GzipFile(filename='',mode='wb',fileobj=raw,mtime=e or 0) as z:shutil.copyfileobj(i,z)
def build_repository(output,debs,*,codename,suite,signing_key,allow_unsigned_development,execute=False):
    if not re.fullmatch(r'[a-z0-9][a-z0-9.-]{1,31}',codename):raise ValueError('invalid repository codename')
    if suite not in SUITES:raise ValueError('invalid repository suite')
    meta=[validate(Path(p)) for p in debs]; by={x['package']:x for x in meta}
    if set(by)!=REQUIRED_PACKAGES or len(meta)!=len(REQUIRED_PACKAGES):raise ValueError('repository build requires exactly linux-cmnd, cmnd-linux, cmnd-vendor-759 and cmnd-tomcat9')
    tooling=str(by['linux-cmnd']['version'])
    if str(by['cmnd-linux']['version'])!=tooling:raise ValueError('cmnd-linux and linux-cmnd versions must match')
    if str(by['cmnd-vendor-759']['version'])!=VENDOR_VERSION:raise ValueError('unexpected vendor package version')
    if str(by['cmnd-tomcat9']['version'])!=TOMCAT_VERSION:raise ValueError('unexpected Tomcat package version')
    if not signing_key and not(suite=='development' and allow_unsigned_development):raise ValueError('signed metadata required except explicit unsigned development builds')
    if output.exists() and any(output.iterdir()):raise ValueError('repository output directory must be absent or empty')
    plan={'executed':execute,'output':str(output),'codename':codename,'suite':suite,'tooling_version':tooling,'vendor_version':VENDOR_VERSION,'tomcat_version':TOMCAT_VERSION,'packages':meta,'signed':bool(signing_key)}
    if not execute:return plan
    apt=require('apt-ftparchive'); require('gpg') if signing_key else None; output.mkdir(parents=True,exist_ok=True)
    for source,info in zip(map(Path,debs),meta):
        target=output/'pool/main'/info['package'][0]/info['package']/source.name; target.parent.mkdir(parents=True,exist_ok=True); shutil.copyfile(source,target); target.chmod(0o644)
    binary=output/'dists'/codename/'main/binary-amd64'; binary.mkdir(parents=True); packages=binary/'Packages'; packages.write_text(run((apt,'packages','pool/main'),cwd=output),encoding='utf-8'); gzip_file(packages,binary/'Packages.gz')
    dist=output/'dists'/codename; body=run((apt,'release',f'dists/{codename}'),cwd=output); header=f'Origin: Music City Telecom\nLabel: CMND Linux\nSuite: {suite}\nCodename: {codename}\nArchitectures: amd64\nComponents: main\nDescription: CMND for Linux packages\nDate: {release_date()}\n'; rel=dist/'Release'; rel.write_text(header+body,encoding='utf-8')
    if signing_key:
        run(('gpg','--batch','--yes','--local-user',signing_key,'--digest-algo','SHA256','--clearsign','--output',dist/'InRelease',rel)); run(('gpg','--batch','--yes','--local-user',signing_key,'--digest-algo','SHA256','--armor','--detach-sign','--output',dist/'Release.gpg',rel))
    plan['files']=sorted(str(p.relative_to(output)) for p in output.rglob('*') if p.is_file()); return plan
def main(argv=None):
    p=argparse.ArgumentParser(); p.add_argument('--output',required=True,type=Path); p.add_argument('--deb',action='append',required=True,type=Path); p.add_argument('--codename',default='noble'); p.add_argument('--suite',choices=sorted(SUITES),default='development'); p.add_argument('--signing-key'); p.add_argument('--allow-unsigned-development',action='store_true'); p.add_argument('--execute',action='store_true'); a=p.parse_args(argv)
    try:r=build_repository(a.output,a.deb,codename=a.codename,suite=a.suite,signing_key=a.signing_key,allow_unsigned_development=a.allow_unsigned_development,execute=a.execute)
    except (OSError,RuntimeError,ValueError) as e:print('build-apt-repository: '+str(e),file=os.sys.stderr); return 2
    print(json.dumps(r,indent=2,sort_keys=True)); return 0
if __name__=='__main__':raise SystemExit(main())
