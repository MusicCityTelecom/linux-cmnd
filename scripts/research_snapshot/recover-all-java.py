"""Resumable full class-set recovery, no vendor namespace or RF exclusions."""
from pathlib import Path, PurePosixPath
from hashlib import sha256
from zipfile import ZipFile, ZIP_DEFLATED
from collections import defaultdict, Counter
from concurrent.futures import ThreadPoolExecutor
import json, re, subprocess, sys, time

lab=Path(__file__).resolve().parent
sys.path.insert(0,str(lab.parent/'src'))
from cmnd_linux.recovery_inventory import class_identity
root=lab/'complete-vendor-1789227709466446600'
out=root/'java-recovery-v1';out.mkdir(exist_ok=True)
queuefile=out/'queue.json'
java=Path('C:/Program Files/TPV/jre-17.0.16/bin/java.exe')
cfr=lab/'decompilation-tools/cfr-0.152.jar'
assert sha256(cfr.read_bytes()).hexdigest()=='f686e8f3ded377d7bc87d216a90e9e9512df4156e75b06c655a16648ae8765b2'

if not queuefile.exists():
    inventory=json.loads((root/'inventory.json').read_text())
    jobs={};issues=[];stats=Counter();unique_classes=set()
    def register(entries,origin,context):
        classes={};members=[]
        for member,raw in entries:
            stats['class_occurrences']+=1
            digest=sha256(raw).hexdigest();unique_classes.add(digest)
            try:identity=class_identity(raw)
            except ValueError as error:
                issues.append({'origin':origin,'member':member,'reason':str(error),'original_retained':True});continue
            name=identity.name+'.class'
            if name in classes and classes[name]!=raw:
                # Do not overwrite alternate definitions; each gets its own job.
                issues.append({'origin':origin,'member':member,'reason':'conflicting internal name; isolated job retained'})
                register([(member,raw)],origin,context+'-conflict-'+digest);continue
            classes[name]=raw;members.append({'member':member,'class':identity.name,'sha256':digest,'major':identity.major})
        if not classes:return
        identity=sha256()
        for name,raw in sorted(classes.items()):identity.update(name.encode()+b'\0'+sha256(raw).digest())
        key=identity.hexdigest()
        occurrence={'origin':origin,'context':context,'members':members}
        if key in jobs:jobs[key]['occurrences'].append(occurrence);return
        folder=out/key;folder.mkdir(exist_ok=True)
        jar=folder/'input.jar'
        if not jar.exists():
            with ZipFile(jar,'x',ZIP_DEFLATED) as archive:
                for name,raw in sorted(classes.items()):archive.writestr(name,raw)
        jobs[key]={'key':key,'classes':len(classes),'expected_outer_sources':sorted(n[:-6]+'.java' for n in classes if '$' not in PurePosixPath(n).name),
                   'occurrences':[occurrence],'input_sha256':sha256(jar.read_bytes()).hexdigest()}
    for artifact in inventory['java_artifacts']:
        path=root/artifact['file']
        groups=defaultdict(list)
        if path.suffix=='.class':groups['base'].append((path.name,path.read_bytes()))
        else:
            with ZipFile(path) as z:
                for name in artifact['class_members']:
                    match=re.search(r'(?:^|/)META-INF/versions/(\d+)/',name)
                    context='java-'+match.group(1) if match else 'base'
                    groups[context].append((name,z.read(name)))
        for context,entries in groups.items():register(entries,artifact['path'],context)
    variants=json.loads((root/'original-source-members.json').read_text())
    groups=defaultdict(list)
    for item in variants['records']:
        if item['java_class_magic']:
            origin,member=item['path'].rsplit('!',1)
            groups[origin].append((member,(root/'original-source-and-variants'/item['file']).read_bytes()))
    for origin,entries in groups.items():register(entries,origin,'nonstandard-bytecode')
    queue={'jobs':list(jobs.values()),'issues':issues,'unique_class_bytes':len(unique_classes),'stats':dict(stats),
           'namespace_filter':False,'rf_excluded':False,'vendor_executed':False}
    queuefile.write_text(json.dumps(queue,indent=2))
else:queue=json.loads(queuefile.read_text())
# Recursive conflict-isolation visits are not additional original class entries.
if 'input_class_occurrences' not in queue:
    original_inventory=json.loads((root/'inventory.json').read_text())
    variant_inventory=json.loads((root/'original-source-members.json').read_text())
    queue['input_class_occurrences']=sum(a['classes'] for a in original_inventory['java_artifacts'])+sum(r['java_class_magic'] for r in variant_inventory['records'])
    queuefile.write_text(json.dumps(queue,indent=2))
print(json.dumps({'prepared_jobs':len(queue['jobs']),'unique_class_bytes':queue['unique_class_bytes'],
                  'class_occurrences':queue['input_class_occurrences'],'inventory_issues':len(queue['issues'])}),flush=True)

def recover(job):
    folder=out/job['key'];resultfile=folder/'result.json'
    if resultfile.exists():return json.loads(resultfile.read_text())
    jar=folder/'input.jar'
    assert sha256(jar.read_bytes()).hexdigest()==job['input_sha256']
    # New per-attempt directory keeps partial/timed-out output distinct on retries.
    attempt=folder/('attempt-'+str(time.time_ns()));attempt.mkdir()
    command=[str(java),'-XX:ActiveProcessorCount=2','-Xmx768m','-jar',str(cfr),str(jar),
             '--outputdir',str(attempt/'source'),'--silent','true','--caseinsensitivefs','true']
    start=time.monotonic()
    with (attempt/'decompiler.log').open('wb') as log:
        try:code=subprocess.run(command,stdout=log,stderr=subprocess.STDOUT,timeout=240).returncode
        except subprocess.TimeoutExpired:code='timeout'
    files=list((attempt/'source').rglob('*.java'));warnings=[]
    for path in files:
        if re.search(r'(?i)could not decompile|decompilation failed|illegal stack|unable to fully structure|decompiler exception',path.read_text(errors='replace')):
            warnings.append(path.relative_to(attempt/'source').as_posix())
    actual={p.relative_to(attempt/'source').as_posix() for p in files}
    result={'key':job['key'],'classes':job['classes'],'exit':code,'java_files':len(files),
            'missing_expected_outer_sources':sorted(set(job['expected_outer_sources'])-actual),'warning_files':warnings,
            'seconds':round(time.monotonic()-start,2),'attempt':attempt.name,'vendor_executed':False,
            'build_verified':False,'equivalence_verified':False}
    resultfile.write_text(json.dumps(result,indent=2));return result

results=[]
with ThreadPoolExecutor(max_workers=2) as pool:
    # Larger archives start early; every job is included, regardless of namespace.
    for result in pool.map(recover,sorted(queue['jobs'],key=lambda j:-j['classes'])):
        results.append(result)
        print(json.dumps({'finished':len(results),'total':len(queue['jobs']),'classes':result['classes'],
                          'exit':result['exit'],'java_files':result['java_files'],'warning_files':len(result['warning_files'])}),flush=True)
summary={'jobs':len(results),'exit_counts':dict(Counter(str(r['exit']) for r in results)),
         'java_files':sum(r['java_files'] for r in results),'classes_in_jobs':sum(r['classes'] for r in results),
         'warning_files':sum(len(r['warning_files']) for r in results),
         'missing_expected_outer_sources':sum(len(r['missing_expected_outer_sources']) for r in results),
         'inventory_issues':len(queue['issues']),'all_jobs_attempted':len(results)==len(queue['jobs']),
         'complete_lossless_source_recovery':False,'vendor_executed':False}
(out/'summary.json').write_text(json.dumps(summary,indent=2));print(json.dumps(summary),flush=True)
