"""Independent static decompilation/recompilation; no vendor code execution."""
from pathlib import Path
from hashlib import sha256
from zipfile import ZipFile, ZIP_DEFLATED
from collections import Counter
import json,re,subprocess,time
lab=Path(__file__).resolve().parent
root=lab/'decompile-assessment-1789226770468985600'
out=lab/('vineflower-comparison-'+str(time.time_ns()));out.mkdir()
tool=lab/'decompilation-tools/vineflower-1.12.0.jar'
assert sha256(tool.read_bytes()).hexdigest()=='1dfcfe974395734fa467ce620661c7623d05ba83670de0529b1fbd63ff548b9d'
jdk=Path('C:/Program Files/TPV/jre-17.0.16/bin')
tomcat=lab/'tomcat-installer-static'
targets={
 'reload':['com/tpv/smartinstall/util/ReloadProtocol'],
 'smartcontrol':['be/tpvision/smartcontrol/service/ContentManagementServiceImpl'],
 'SmartInstall':['com/tpvision/smartinstall/core/SettingCreator','com/tpvision/smartinstall/util/ZipCommonUtils',
                 'com/tpvision/smartinstall/servlet/SettingServlet','com/tpvision/smartinstall/dao/mgr/DevicesManager',
                 'com/tpvision/smartinstall/dao/mgr/JpaManager']}
results=[]
for component,names in targets.items():
    folder=out/component;folder.mkdir();original=root/component/'input.jar';selected=[]
    with ZipFile(original) as archive,ZipFile(folder/'selected.jar','x',ZIP_DEFLATED) as dest:
        for name in archive.namelist():
            if any(name==n+'.class' or name.startswith(n+'$') for n in names):
                dest.writestr(name,archive.read(name));selected.append(name)
    libs=[original]+list((root/component/'lib').glob('*.jar'))+list((tomcat/'lib').glob('*.jar'))+list((tomcat/'bin').glob('*.jar'))
    args=['-XX:ActiveProcessorCount=2','-Xmx768m','-jar',str(tool),'--folder','--thread-count=2','--log-level=WARN',
          '--bytecode-source-mapping=true']+['-e='+str(p) for p in libs]+[str(folder/'selected.jar'),str(folder/'source')]
    argfile=folder/'decompiler-args.txt';argfile.write_text('\n'.join(json.dumps(a) for a in args))
    with (folder/'decompiler.log').open('wb') as log:
        try:decompile=subprocess.run([str(jdk/'java.exe'),'@'+str(argfile)],stdout=log,stderr=subprocess.STDOUT,timeout=240).returncode
        except subprocess.TimeoutExpired:decompile='timeout'
    sources=list((folder/'source').rglob('*.java'));compiled=folder/'classes';compiled.mkdir()
    compilecode='not-run';errors=Counter()
    if sources:
        args=['-proc:none','-implicit:none','--release','8','-encoding','UTF-8','-Xmaxerrs','100','-d',str(compiled),
              '-classpath',';'.join(str(p) for p in libs)]+[str(p) for p in sources]
        argfile=folder/'compiler-args.txt';argfile.write_text('\n'.join(json.dumps(a) for a in args))
        with (folder/'compiler.log').open('wb') as log:
            try:compilecode=subprocess.run([str(jdk/'javac.exe'),'-J-XX:ActiveProcessorCount=2','-J-Xmx512m','@'+str(argfile)],stdout=log,stderr=subprocess.STDOUT,timeout=90).returncode
            except subprocess.TimeoutExpired:compilecode='timeout'
        errors.update(re.findall(r'\.java:\d+: error: ([^\r\n]+)',(folder/'compiler.log').read_text(errors='replace')))
    warnings=[p.relative_to(folder/'source').as_posix() for p in sources if re.search(r'(?i)could not be decompiled|couldn.t be decompiled|\$VF:|decompilation failed',p.read_text(errors='replace'))]
    result={'component':component,'selected_classes':len(selected),'source_files':len(sources),'decompiler_exit':decompile,
            'compiler_exit':compilecode,'class_files_emitted':len(list(compiled.rglob('*.class'))),'compiler_errors':dict(errors),
            'warning_files':warnings,'vendor_executed':False,'equivalence_verified':False}
    results.append(result);(out/'report.json').write_text(json.dumps(results,indent=2));print(json.dumps(result),flush=True)
