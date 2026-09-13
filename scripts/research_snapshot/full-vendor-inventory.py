"""Preserve every supplied installer-tree file and inventory ALL ZIP/JAR classes.

No namespace filters, no RF exclusions. Native/opaque inputs are retained and
reported, not pretended to be Java. No application code is run.
"""
from collections import Counter
from hashlib import sha256
from io import BytesIO
import json,shutil,sys,time
from pathlib import Path,PurePosixPath
from zipfile import ZipFile,ZIP_STORED,is_zipfile

lab=Path(__file__).resolve().parent
project=lab.parent
original=project.parents[1]/'.private-staging/installer-7.5.9'
assert original.is_dir()
sys.path.insert(0,str(project/'src'))
from cmnd_linux.application_stage import VENDOR_INPUT_HASHES
for name,digest in VENDOR_INPUT_HASHES.items():
    with (original/'{app}'/name).open('rb') as stream:
        import hashlib
        assert hashlib.file_digest(stream,'sha256').hexdigest()==digest
files=sorted(p for p in original.rglob('*') if p.is_file())
assert not any(p.is_symlink() for p in original.rglob('*'))
size=sum(p.stat().st_size for p in files)
if shutil.disk_usage(lab).free < size*2+5*1024**3:
    raise SystemExit('Insufficient space for a complete retained collection; nothing omitted')
out=lab/('complete-vendor-'+str(time.time_ns()));out.mkdir()
(out/'java-artifacts').mkdir()
manifest=[]
with ZipFile(out/'original-installer-tree.zip','x',ZIP_STORED,allowZip64=True) as snapshot:
    for p in files:
        name=p.relative_to(original).as_posix()
        h=sha256()
        with p.open('rb') as source,snapshot.open(name,'w',force_zip64=True) as target:
            while block:=source.read(1024**2):h.update(block);target.write(block)
        manifest.append({'path':name,'size':p.stat().st_size,'sha256':h.hexdigest()})
(out/'files.json').write_text(json.dumps(manifest,indent=2))
print(json.dumps({'snapshot_files':len(files),'bytes':size,'all_original_tree_files_retained':True}),flush=True)

seen={};artifacts=[];occurrences=[];opaque=[];stats=Counter();errors=[]
def inspect(raw,label,depth=0):
    if depth>16:errors.append({'path':label,'reason':'archive depth bound; raw parent retained'});return
    digest=sha256(raw).hexdigest()
    if digest in seen:
        occurrences.append({'path':label,'same_content_as':seen[digest],'sha256':digest});return
    seen[digest]=label
    try:
        with ZipFile(BytesIO(raw)) as z:
            entries=z.infolist();stats['unique_archives']+=1;stats['archive_members']+=len(entries)
            classes=[e for e in entries if not e.is_dir() and e.filename.endswith('.class')]
            if classes:
                # Preserve complete dependency context, resource files and signatures.
                artifact=out/'java-artifacts'/(digest+'.zip');artifact.write_bytes(raw)
                artifacts.append({'path':label,'sha256':digest,'file':str(artifact.relative_to(out)),
                                  'classes':len(classes),'class_members':[e.filename for e in classes],
                                  'status':'pending-decompilation'})
                stats['class_occurrences_unique_archives']+=len(classes)
            for e in entries:
                if e.is_dir():continue
                suffix=PurePosixPath(e.filename).suffix.lower();stats['member_extension_'+(suffix or '(none)')]+=1
                if suffix in ('.jar','.war','.zip','.ear','.jmod','.apk'):
                    if e.file_size>1024**3:
                        errors.append({'path':label+'!'+e.filename,'reason':'large nested archive; original retained'});continue
                    inspect(z.read(e),label+'!'+e.filename,depth+1)
                elif suffix in ('.exe','.dll','.msi','.sys','.dex','.so','.dylib') or PurePosixPath(e.filename).name=='modules':
                    opaque.append({'path':label+'!'+e.filename,'size':e.file_size,'kind':suffix or 'JDK-modules','status':'retained-non-Java-or-opaque'})
    except Exception as exc:
        errors.append({'path':label,'reason':type(exc).__name__,'original_bytes_retained':True})
for record in manifest:
    p=original/record['path'];suffix=p.suffix.lower()
    if suffix in ('.jar','.war','.zip','.ear','.jmod','.apk'):
        inspect(p.read_bytes(),record['path'])
    elif suffix=='.class':
        raw=p.read_bytes();digest=sha256(raw).hexdigest();target=out/'java-artifacts'/(digest+'.class');target.write_bytes(raw)
        artifacts.append({'path':record['path'],'sha256':digest,'file':str(target.relative_to(out)), 'classes':1,'class_members':[p.name],'status':'pending-decompilation'})
    elif suffix in ('.exe','.dll','.msi','.sys','.dex','.so','.dylib'):
        opaque.append({'path':record['path'],'kind':suffix,'size':record['size'],'status':'retained-non-Java-or-opaque'})
report={'snapshot':'original-installer-tree.zip','files':len(manifest),'bytes':size,
        'stats':dict(stats),'java_artifacts':artifacts,'duplicate_archive_occurrences':occurrences,
        'opaque_components':opaque,'scan_issues':errors,'omitted_original_files':0,
        'rf_components_excluded':False,'vendor_namespace_filter':False,
        'decompilation_complete':False,'lossless_source_recovery_claimed':False}
(out/'inventory.json').write_text(json.dumps(report,indent=2))
print(json.dumps({'private_inventory':str(out/'inventory.json'),'source_files_preserved':len(manifest),
                  'java_artifacts_pending':len(artifacts),'stats':dict(stats),
                  'opaque_inputs_retained':len(opaque),'scan_issues':len(errors)}),flush=True)
