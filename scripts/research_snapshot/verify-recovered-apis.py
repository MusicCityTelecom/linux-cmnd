"""Compare javap public/protected surface without loading application classes."""
from pathlib import Path
import json,subprocess
lab=Path(__file__).resolve().parent
root=lab/'decompile-assessment-1789226770468985600'
comparison=sorted(lab.glob('vineflower-comparison-*'))[-1]
javap='C:/Program Files/TPV/jre-17.0.16/bin/javap.exe'
report=json.loads((comparison/'report.json').read_text());results=[]
for row in report:
    name=row['component'];folder=comparison/name
    sources=list((folder/'source').rglob('*.java'))
    names=[p.relative_to(folder/'source').as_posix()[:-5].replace('/','.') for p in sources]
    surfaces=[]
    for kind,classpath in [('original',root/name/'input.jar'),('rebuilt',folder/'classes')]:
        process=subprocess.run([javap,'-protected','-s','-classpath',str(classpath),*names],capture_output=True,text=True,timeout=60)
        (folder/(kind+'-api.txt')).write_text(process.stdout)
        if process.returncode:raise SystemExit('javap failed; no successful API comparison asserted')
        surfaces.append(sorted(line.strip() for line in process.stdout.splitlines() if line.strip() and not line.startswith('Compiled from')))
    result={'component':name,'outer_classes':len(names),'public_protected_surface_equal':surfaces[0]==surfaces[1],
            'removed_surface_lines':sorted(set(surfaces[0])-set(surfaces[1])),
            'added_surface_lines':sorted(set(surfaces[1])-set(surfaces[0])),
            'private_and_package_members_compared':False,'vendor_executed':False,'behavior_verified':False}
    results.append(result);print(json.dumps({k:v for k,v in result.items() if k not in ('removed_surface_lines','added_surface_lines')}),flush=True)
(comparison/'api-comparison.json').write_text(json.dumps(results,indent=2))
