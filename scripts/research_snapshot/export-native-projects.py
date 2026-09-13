from pathlib import Path
import json,subprocess,time
lab=Path(__file__).resolve().parent
ghidra=lab/'decompilation-tools/ghidra/ghidra_12.1.3_PUBLIC'
jdk=lab/'decompilation-tools/jdk21/jdk-21.0.12.1+1/bin'
root=lab/'complete-vendor-1789227709466446600'
base=root/'native-recovery-1789228881729394700'
projects=lab.parents[2]/'native-research-private'/base.name
private=lab.parents[2]/'native-research-private'
classes=private/'exporter-classes';classes.mkdir(exist_ok=True)
args=['-proc:none','-cp',';'.join(str(p) for p in ghidra.rglob('*.jar')),'-d',str(classes),str(lab/'ghidra-scripts/NativeExportLauncher.java')]
argfile=classes/'compile-args.txt';argfile.write_text('\n'.join(json.dumps(a) for a in args))
subprocess.run([str(jdk/'javac.exe'),'@'+str(argfile)],check=True)
rows=json.loads((root/'native-inventory.json').read_text())
results=[]
for row in rows:
    if not row.get('preserved_file'):continue
    if not (projects/(row['sha256']+'.gpr')).is_file():continue
    if (projects/(row['sha256']+'.lock')).exists():continue
    dest=base/row['sha256']/('export-'+str(time.time_ns()));dest.mkdir(parents=True)
    command=[str(jdk/'java.exe'),'-Xmx1024m','-XX:ActiveProcessorCount=2','-Djava.system.class.loader=ghidra.GhidraClassLoader',
             '-Djava.awt.headless=true','-Duser.home='+str(private/'exporter-prefs'),'-Dapplication.settingsdir='+str(private/'exporter-prefs'),
             '-cp',';'.join([str(classes)]+[str(p) for p in ghidra.rglob('*.jar')]),
             'ghidra.Ghidra','NativeExportLauncher',str(projects),row['sha256'],row['preserved_file'],str(dest)]
    runtime_args=dest/'runtime-args.txt';runtime_args.write_text('\n'.join(json.dumps(a) for a in command[1:]))
    with (dest/'export.log').open('wb') as log:
        try:code=subprocess.run([command[0],'@'+str(runtime_args)],stdout=log,stderr=subprocess.STDOUT,timeout=240).returncode
        except subprocess.TimeoutExpired:code='timeout'
    result={'path':row['path'],'exit':code,'executed':False}
    if (dest/'summary.json').exists():result.update(json.loads((dest/'summary.json').read_text()))
    results.append(result);(base/'export-report.json').write_text(json.dumps(results,indent=2));print(json.dumps(result),flush=True)
