"""Build tooling plus the authorized vendor bundle and pinned runtime packages."""
import argparse,hashlib,json,shutil,subprocess
from pathlib import Path
from build_deb import build as build_payload_deb,ROOT
from build_meta_deb import build as build_meta_deb
from build_vendor_deb import build as build_vendor_deb
from build_tomcat_deb import build as build_tomcat_deb
from vendor_bundle import verify as verify_vendor_bundle,NAME as VENDOR_BUNDLE_NAME
RELEASE_PATHS=['src','scripts','deploy','config','packaging','tests','docs','.github','VERSION','pyproject.toml','README.md','LICENSE','CHANGELOG.md','AGENTS.md','.gitattributes','.gitignore']
def validate_sources():
    subprocess.run(['git','diff','--exit-code','HEAD','--',*RELEASE_PATHS],cwd=ROOT,check=True)
    untracked=subprocess.check_output(['git','ls-files','--others','--',*RELEASE_PATHS],cwd=ROOT,text=True).splitlines(); untracked=[n for n in untracked if '__pycache__' not in Path(n).parts and Path(n).suffix!='.pyc']
    if untracked:raise SystemExit('Uncommitted release inputs detected; commit or remove them before building')
    tracked=subprocess.check_output(['git','ls-files'],cwd=ROOT,text=True).splitlines(); forbidden={'.war','.jar','.exe','.zip','.sql','.key','.crt','.pem','.p12','.pfx','.pcap','.pcapng','.log'}
    if any(Path(n).suffix.lower() in forbidden or n.startswith(('.lab/','.private-staging/')) for n in tracked):raise SystemExit('Private/vendor input detected in tracked release sources')
def digest(path):
    h=hashlib.sha256()
    with path.open('rb') as s:
        for chunk in iter(lambda:s.read(1024*1024),b''):h.update(chunk)
    return h.hexdigest()
def main():
    p=argparse.ArgumentParser(description=__doc__); p.add_argument('--vendor-bundle',type=Path,required=True); p.add_argument('--tomcat-archive',type=Path,required=True); a=p.parse_args(); vendor_meta=verify_vendor_bundle(a.vendor_bundle); validate_sources()
    payload=build_payload_deb(); vendor=build_vendor_deb(a.vendor_bundle); tomcat=build_tomcat_deb(a.tomcat_archive); meta=build_meta_deb(); version=(ROOT/'VERSION').read_text().strip(); dest=ROOT/'dist'
    installer=dest/'install.sh'; installer.write_bytes((ROOT/'scripts/install-native.sh').read_bytes().replace(b'\r\n',b'\n'))
    manifest=dest/'linux-cmnd-update.json'; manifest.write_text(json.dumps({'schema':1,'version':version,'minimum_version':'0.4.0','scope':'tooling-only','vendor':'7.5.9','database_migration':False,'vendor_payload_changed':False},indent=2)+'\n')
    source=dest/f'linux-cmnd-{version}-source.tar.gz'; subprocess.run(['git','archive','--format=tar.gz',f'--prefix=linux-cmnd-{version}/','-o',str(source),'HEAD','--',*RELEASE_PATHS],cwd=ROOT,check=True)
    guide=dest/'EVALUATION-GUIDE.md'; shutil.copyfile(ROOT/'docs/EVALUATION-GUIDE.md',guide); cfg=dest/'cmnd.example.toml'; shutil.copyfile(ROOT/'config/cmnd.install.toml',cfg); bootstrap=dest/'bootstrap.py'; bootstrap.write_bytes((ROOT/'scripts/bootstrap.py').read_bytes().replace(b'\r\n',b'\n'))
    artifacts=[payload,meta,vendor,tomcat,installer,manifest,source,guide,cfg,bootstrap]; bundled=dest/VENDOR_BUNDLE_NAME
    if bundled.exists():
        if verify_vendor_bundle(bundled)!=vendor_meta:raise SystemExit('Existing release vendor bundle differs; refusing overwrite')
    else:shutil.copyfile(a.vendor_bundle,bundled)
    if verify_vendor_bundle(bundled)!=vendor_meta:raise SystemExit('Vendor bundle changed while copying')
    vm=dest/'vendor-bundle.json'; vm.write_text(json.dumps(vendor_meta,indent=2)+'\n'); artifacts += [bundled,vm]; checks=dest/'SHA256SUMS'; checks.write_text(''.join(f'{digest(x)}  {x.name}\n' for x in artifacts),encoding='ascii')
    print(json.dumps({'version':version,'assets':[str(x) for x in artifacts+[checks]],'apt_packages':[payload.name,meta.name,vendor.name,tomcat.name],'vendor_payload_included':True,'vendor_bundle':vendor_meta},indent=2))
if __name__=='__main__':main()
