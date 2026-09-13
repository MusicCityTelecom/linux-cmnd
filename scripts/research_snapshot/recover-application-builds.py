"""Full direct-application Vineflower pass plus offline compilation evidence."""
from pathlib import Path
from hashlib import sha256
from collections import Counter
import json,re,subprocess,time
lab=Path(__file__).resolve().parent
root=lab/'decompile-assessment-1789226770468985600'
out=lab/('application-build-recovery-'+str(time.time_ns()));out.mkdir()
tool=lab/'decompilation-tools/vineflower-1.12.0.jar'
assert sha256(tool.read_bytes()).hexdigest()=='1dfcfe974395734fa467ce620661c7623d05ba83670de0529b1fbd63ff548b9d'
jdk=Path('C:/Program Files/TPV/jre-17.0.16/bin');tomcat=lab/'tomcat-installer-static'
report=json.loads((root/'report.json').read_text());results=[]
for component in sorted(report['components'],key=lambda c:c['classes']):
    name=component['name'];folder=out/name;folder.mkdir();original=root/name/'input.jar'
    libs=list((root/name/'lib').glob('*.jar'))+list((tomcat/'lib').glob('*.jar'))+list((tomcat/'bin').glob('*.jar'))
    supplemental=[]
    if name=='smartcms':
        # Compile-only annotation API already supplied in the original CAS WAR.
        supplemental=list((root/'cas/lib').glob('validation-api-*.jar'))
    libs+=supplemental
    args=['-XX:ActiveProcessorCount=2','-Xmx768m','-jar',str(tool),'--folder','--thread-count=2','--log-level=WARN',
          '--bytecode-source-mapping=true']+['-e='+str(p) for p in libs]+[str(original),str(folder/'source')]
    argfile=folder/'decompiler-args.txt';argfile.write_text('\n'.join(json.dumps(a) for a in args))
    with (folder/'decompiler.log').open('wb') as log:
        try:decompile=subprocess.run([str(jdk/'java.exe'),'@'+str(argfile)],stdout=log,stderr=subprocess.STDOUT,timeout=300).returncode
        except subprocess.TimeoutExpired:decompile='timeout'
    sources=list((folder/'source').rglob('*.java'));compiled=folder/'classes';compiled.mkdir()
    compilecode='not-run';errors=Counter()
    if sources:
        release=max(int(k.removeprefix('class_major_'))-44 for k in component['metrics'] if k.startswith('class_major_'))
        args=['-proc:none','-implicit:none','--release',str(release),'-encoding','UTF-8','-Xmaxerrs','200',
              '-d',str(compiled),'-classpath',';'.join(str(p) for p in libs)]+[str(p) for p in sources]
        argfile=folder/'compiler-args.txt';argfile.write_text('\n'.join(json.dumps(a) for a in args))
        with (folder/'compiler.log').open('wb') as log:
            try:compilecode=subprocess.run([str(jdk/'javac.exe'),'-J-XX:ActiveProcessorCount=2','-J-Xmx512m','@'+str(argfile)],stdout=log,stderr=subprocess.STDOUT,timeout=120).returncode
            except subprocess.TimeoutExpired:compilecode='timeout'
        errors.update(re.findall(r'\.java:\d+: error: ([^\r\n]+)',(folder/'compiler.log').read_text(errors='replace')))
    warnings=[p.relative_to(folder/'source').as_posix() for p in sources if re.search(r'(?i)could not be decompiled|couldn.t be decompiled|\$VF:|decompilation failed',p.read_text(errors='replace'))]
    result={'component':name,'input_classes':component['classes'],'source_files':len(sources),'decompiler_exit':decompile,
            'compiler_exit':compilecode,'class_files_emitted':len(list(compiled.rglob('*.class'))),'compiler_errors':dict(errors),
            'compile_only_supplements':[p.name for p in supplemental],'warning_files':warnings,
            'vendor_executed':False,'equivalence_verified':False}
    results.append(result);(out/'report.json').write_text(json.dumps(results,indent=2));print(json.dumps(result),flush=True)
