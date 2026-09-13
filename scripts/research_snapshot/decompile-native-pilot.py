"""Bounded Ghidra static pilot for native CMND helpers, not executable testing."""
from pathlib import Path
import json, subprocess, time
lab=Path(__file__).resolve().parent
root=lab/'complete-vendor-1789227709466446600'
ghidra=lab/'decompilation-tools/ghidra/ghidra_12.1.3_PUBLIC'
java=lab/'decompilation-tools/jdk21/jdk-21.0.12.1+1/bin/java.exe'
base=root/('native-recovery-'+str(time.time_ns()));base.mkdir()
prefs=base/'preferences';prefs.mkdir()
# Ghidra rejects project paths containing any dot-prefixed directory. Its private
# project database stays in an ignored sibling; source/reports stay in this worktree.
project=lab.parents[2]/'native-research-private'/base.name
project.mkdir(parents=True)
options=[]
for line in (ghidra/'support/launch.properties').read_text().splitlines():
    if line.startswith(('VMARGS=','VMARGS_WINDOWS=')):
        options.append(line.split('=',1)[1])
rows=json.loads((root/'native-inventory.json').read_text())
selected=[r for r in rows if r.get('preserved_file') and r['path'].endswith(('UPG_Create_2K14/HTV_DWPack_1401.exe','PSG.zip!TsGenUtil.exe','PSG.zip!TSGen.dll'))]
results=[]
for row in selected:
    dest=base/row['sha256'];dest.mkdir()
    cmd=[str(java),'-Xmx1500m','-XX:ActiveProcessorCount=2',*options,'-Duser.home='+str(prefs),
         '-Dapplication.settingsdir='+str(prefs),'-Djava.awt.headless=true','-cp',str(ghidra/'Ghidra/Framework/Utility/lib/Utility.jar'),
         'ghidra.Ghidra','ghidra.app.util.headless.AnalyzeHeadless',str(project),row['sha256'],
         '-import',str(root/'native-artifacts'/row['preserved_file']),'-max-cpu','2','-analysisTimeoutPerFile','180',
         '-scriptPath',str(lab/'ghidra-scripts'),'-postScript','ExportNativeRecovery.java',str(dest),
         '-log',str(dest/'analysis.log'),'-scriptlog',str(dest/'script.log')]
    with (dest/'launcher.log').open('wb') as log:
        try:code=subprocess.run(cmd,stdout=log,stderr=subprocess.STDOUT,timeout=480).returncode
        except subprocess.TimeoutExpired:code='timeout'
    record={'path':row['path'],'sha256':row['sha256'],'exit':code,'executed':False}
    summary=dest/'summary.json'
    if summary.exists():record.update(json.loads(summary.read_text()))
    results.append(record);(base/'report.json').write_text(json.dumps(results,indent=2));print(json.dumps(record),flush=True)
