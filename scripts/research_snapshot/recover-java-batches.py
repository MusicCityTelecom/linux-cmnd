"""Bounded resumable recovery. Full originals and prior attempts stay intact."""
from pathlib import Path
from zipfile import ZipFile,ZIP_STORED
from collections import defaultdict,Counter
from concurrent.futures import ThreadPoolExecutor,wait,FIRST_COMPLETED
from hashlib import sha256
import argparse,json,re,subprocess,time
parser=argparse.ArgumentParser();parser.add_argument('--max-units',type=int,default=20)
args=parser.parse_args()
lab=Path(__file__).resolve().parent
root=lab/'complete-vendor-1789227709466446600'
old=root/'java-recovery-v1';out=root/'java-recovery-v2';out.mkdir(exist_ok=True)
queue=json.loads((old/'queue.json').read_text());planfile=out/'plan.json'
java=Path('C:/Program Files/TPV/jre-17.0.16/bin/java.exe');cfr=lab/'decompilation-tools/cfr-0.152.jar'
assert sha256(cfr.read_bytes()).hexdigest()=='f686e8f3ded377d7bc87d216a90e9e9512df4156e75b06c655a16648ae8765b2'
if not planfile.exists():
    units=[];reused=[]
    for job in queue['jobs']:
        parent=old/job['key'];prior=parent/'result.json'
        if prior.exists():
            result=json.loads(prior.read_text())
            if result['exit']==0 and not result['missing_expected_outer_sources']:
                reused.append({'parent':job['key'],'result':str(prior.relative_to(root))});continue
        with ZipFile(parent/'input.jar') as archive:
            groups=defaultdict(list)
            for member in archive.namelist():groups[member.split('$',1)[0].removesuffix('.class')].append(member)
            chunks=[];chunk=[]
            for members in groups.values():
                if chunk and len(chunk)+len(members)>500:chunks.append(chunk);chunk=[]
                chunk+=members
            if chunk:chunks.append(chunk)
            for index,members in enumerate(chunks):
                key=job['key']+'-'+str(index);folder=out/key;folder.mkdir(exist_ok=True)
                target=folder/'input.jar'
                if not target.exists():
                    with ZipFile(target,'x',ZIP_STORED) as staged:
                        for member in members:staged.writestr(member,archive.read(member))
                units.append({'key':key,'parent':job['key'],'classes':len(members),'input_sha256':sha256(target.read_bytes()).hexdigest(),
                              'expected_outer':[n[:-6]+'.java' for n in members if '$' not in Path(n).name]})
    plan={'units':units,'reused_successful_jobs':reused,'original_jobs':len(queue['jobs']),
          'namespace_filter':False,'rf_excluded':False,'prior_failed_output_retained':True}
    planfile.write_text(json.dumps(plan,indent=2))
else:plan=json.loads(planfile.read_text())
pending=[u for u in plan['units'] if not (out/u['key']/'result.json').exists()]
print(json.dumps({'total_units':len(plan['units']),'pending':len(pending),'this_pass_limit':args.max_units,
                  'reused_jobs':len(plan['reused_successful_jobs'])}),flush=True)
def recover(unit):
    folder=out/unit['key'];jar=folder/'input.jar';assert sha256(jar.read_bytes()).hexdigest()==unit['input_sha256']
    dest=folder/('attempt-'+str(time.time_ns()));dest.mkdir()
    command=[str(java),'-XX:ActiveProcessorCount=2','-Xmx640m','-jar',str(cfr),str(jar),'--outputdir',str(dest/'source'),
             '--extraclasspath',str(old/unit['parent']/'input.jar'),'--silent','true','--caseinsensitivefs','true']
    with (dest/'decompiler.log').open('wb') as log:
        try:code=subprocess.run(command,stdout=log,stderr=subprocess.STDOUT,timeout=150).returncode
        except subprocess.TimeoutExpired:code='timeout'
    files=list((dest/'source').rglob('*.java'));names={p.relative_to(dest/'source').as_posix() for p in files}
    warnings=[p.relative_to(dest/'source').as_posix() for p in files if re.search(r'(?i)could not decompile|decompilation failed|illegal stack|unable to fully structure|decompiler exception',p.read_text(errors='replace'))]
    result={'key':unit['key'],'classes':unit['classes'],'exit':code,'java_files':len(files),'warning_files':warnings,
            'missing_expected_outer':sorted(set(unit['expected_outer'])-names),'attempt':dest.name,'vendor_executed':False,'equivalence_verified':False}
    (folder/'result.json').write_text(json.dumps(result,indent=2));return result
selected=pending[:max(0,args.max_units)]
with ThreadPoolExecutor(max_workers=2) as pool:
    iterator=iter(selected);active=set()
    while True:
        while len(active)<2 and not (out/'PAUSE').exists():
            unit=next(iterator,None)
            if unit is None:break
            active.add(pool.submit(recover,unit))
        if not active:break
        done,active=wait(active,return_when=FIRST_COMPLETED)
        for future in done:
            result=future.result();print(json.dumps({k:v for k,v in result.items() if k not in ('warning_files','missing_expected_outer','attempt')}),flush=True)
results=[json.loads(p.read_text()) for p in out.glob('*/result.json')]
summary={'total_units':len(plan['units']),'attempted_units':len(results),'pending_units':len(plan['units'])-len(results),
         'exit_counts':dict(Counter(str(r['exit']) for r in results)),'java_files':sum(r['java_files'] for r in results),
         'reused_jobs':len(plan['reused_successful_jobs']),'vendor_executed':False,'complete_lossless_recovery':False}
(out/'summary.json').write_text(json.dumps(summary,indent=2));print(json.dumps(summary),flush=True)
