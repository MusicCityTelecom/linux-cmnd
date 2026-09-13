"""Preserve supplied source/debug files and nonstandard Java bytecode variants."""
from pathlib import Path, PurePosixPath
from hashlib import sha256, file_digest
from io import BytesIO
from zipfile import ZipFile
from collections import Counter
import json, shutil
lab=Path(__file__).resolve().parent
root=lab/'complete-vendor-1789227709466446600'
dest=root/'original-source-and-variants';dest.mkdir(exist_ok=True)
records=[];seen=set();issues=[]
source_suffixes={'.java','.c','.cc','.cpp','.cxx','.h','.hpp','.cs','.vb','.pdb','.map','.idl','.class_terracotta'}
archive_suffixes={'.zip','.jar','.war','.ear','.jmod','.apk'}
def retain(raw,path):
    digest=sha256(raw).hexdigest();suffix=PurePosixPath(path.split('!')[-1]).suffix.lower()
    target=dest/(digest+suffix)
    if not target.exists():target.write_bytes(raw)
    records.append({'path':path,'sha256':digest,'bytes':len(raw),'file':target.name,
                    'java_class_magic':raw[:4]==bytes.fromhex('cafebabe'),'status':'original-not-decompiled'})
def scan(raw,label,depth=0):
    if depth>16:issues.append({'path':label,'reason':'depth-bound; parent retained'});return
    digest=sha256(raw).hexdigest()
    if digest in seen:return
    seen.add(digest)
    try:
        with ZipFile(BytesIO(raw)) as z:
            for entry in z.infolist():
                if entry.is_dir():continue
                suffix=PurePosixPath(entry.filename).suffix.lower();path=label+'!'+entry.filename
                if suffix in source_suffixes:retain(z.read(entry),path)
                elif suffix in archive_suffixes:scan(z.read(entry),path,depth+1)
    except Exception as exc:issues.append({'path':label,'reason':type(exc).__name__,'original_retained':True})
with ZipFile(root/'original-installer-tree.zip') as snapshot:
    for entry in snapshot.infolist():
        suffix=PurePosixPath(entry.filename).suffix.lower()
        if suffix in source_suffixes:retain(snapshot.read(entry),entry.filename)
        elif suffix in archive_suffixes:scan(snapshot.read(entry),entry.filename)
# Preserve the installer container itself, not only its extracted tree.
installer=lab.parents[2]/'cmnd_release_master-7.5.9.exe'
with installer.open('rb') as stream:digest=file_digest(stream,'sha256').hexdigest()
assert digest=='4cc2d3bf79a8d6d8445c308ba5b4d2b6d551492b9f8ffddf3071c53b51de2348'
target=root/'original-master-installer.exe'
if not target.exists():shutil.copyfile(installer,target)
with target.open('rb') as stream:assert file_digest(stream,'sha256').hexdigest()==digest
report={'records':records,'issues':issues,'installer_sha256':digest,
        'counts':dict(Counter(PurePosixPath(r['file']).suffix for r in records)),
        'unusual_class_bytecode':sum(r['java_class_magic'] for r in records),
        'decompilation_complete':False}
(root/'original-source-members.json').write_text(json.dumps(report,indent=2))
print(json.dumps({k:v for k,v in report.items() if k!='records'}),flush=True)
