"""Compile recovered text as an offline experiment; never load/run the output."""
from collections import Counter
from pathlib import Path
import json,re,subprocess,time

lab=Path(__file__).resolve().parent
root=lab/'decompile-assessment-1789226770468985600'
report=json.loads((root/'report.json').read_text())
javac=Path('C:/Program Files/TPV/jre-17.0.16/bin/javac.exe')
tomcat=lab/'tomcat-installer-static/lib'
assert javac.is_file() and (tomcat/'servlet-api.jar').is_file()
results=[]
for component in sorted(report['components'],key=lambda c:c['classes']):
    work=root/component['name'];target=work/('compile-'+str(time.time_ns()));target.mkdir()
    sources=list((work/'recovered').rglob('*.java'))
    release=max(int(k.removeprefix('class_major_'))-44 for k in component['metrics'] if k.startswith('class_major_'))
    args=['-proc:none','-implicit:none','--release',str(release),'-encoding','UTF-8','-Xmaxerrs','200',
          '-d',target.as_posix(),'-classpath',';'.join(p.as_posix() for p in list((work/'lib').glob('*.jar'))+list(tomcat.glob('*.jar'))+list((tomcat.parent/'bin').glob('*.jar')))]
    args += [p.as_posix() for p in sources]
    argfile=target/'arguments.txt';argfile.write_text('\n'.join(json.dumps(a) for a in args))
    with (target/'compiler.log').open('wb') as log:
        try:code=subprocess.run([str(javac),'-J-Xmx1024m','@'+str(argfile)],stdout=log,stderr=subprocess.STDOUT,timeout=120).returncode
        except subprocess.TimeoutExpired:code='timeout'
    text=(target/'compiler.log').read_text(errors='replace')
    errors=Counter(re.findall(r'\.java:\d+: error: ([^\r\n]+)',text))
    result={'name':component['name'],'source_files':len(sources),'exit':code,
            'class_files_emitted':len(list(target.rglob('*.class'))),'reported_error_count':sum(errors.values()),
            'error_categories':dict(errors),'annotation_processors_disabled':True,'application_executed':False}
    results.append(result);print(json.dumps(result),flush=True)
(root/'compilation-report.json').write_text(json.dumps(results,indent=2))
metrics=Counter()
for c in report['components']:metrics.update(c['metrics'])
print(json.dumps({'aggregate_metadata':dict(metrics),'source_files':sum(r['java_files'] for r in report['decompilation']),
                  'warning_files':sum(len(r['flagged_sources']) for r in report['decompilation']),
                  'windows_indicator_classes':len(report['windows_indicators'])}))
