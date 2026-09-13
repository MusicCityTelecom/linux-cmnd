"""Recover managed source and IL with ILSpy; never execute the target assemblies."""
from pathlib import Path
import json,subprocess,time
lab=Path(__file__).resolve().parent
root=lab/'complete-vendor-1789227709466446600'
rows=json.loads((root/'native-inventory.json').read_text())
tool=lab/'decompilation-tools/ilspy/tools/net8.0/any/ilspycmd.dll'
dotnet='C:/Program Files (x86)/dotnet/dotnet.exe'
base=root/('managed-recovery-'+str(time.time_ns()));base.mkdir()
# Preserve original names for reference resolution, with a digest-indexed manifest.
refs=base/'references';refs.mkdir()
for row in rows:
    if row.get('managed_dotnet'):
        name=Path(row['path'].split('!')[-1]).name
        (refs/name).write_bytes((root/'native-artifacts'/row['preserved_file']).read_bytes())
results=[]
for row in rows:
    if not row.get('managed_dotnet'):continue
    dest=base/row['sha256'];dest.mkdir()
    target=refs/Path(row['path'].split('!')[-1]).name
    states={}
    for mode,args in [('source',['-p']),('il',['--ilcode'])]:
        with (dest/(mode+'.log')).open('wb') as log:
            try:
                states[mode]=subprocess.run([dotnet,str(tool),'--disable-updatecheck',*args,'-r',str(refs),'-o',str(dest/mode),str(target)],
                    stdout=log,stderr=subprocess.STDOUT,timeout=240).returncode
            except subprocess.TimeoutExpired:states[mode]='timeout'
    record={'path':row['path'],'sha256':row['sha256'],'exit':states,'csharp_files':len(list(dest.rglob('*.cs'))),
            'il_files':len(list(dest.rglob('*.il'))),'executed':False,'equivalence_verified':False}
    results.append(record);(base/'report.json').write_text(json.dumps(results,indent=2));print(json.dumps(record),flush=True)
