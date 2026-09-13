"""Private offline recovery assessment. Never execute application classes."""
from collections import Counter
from concurrent.futures import ThreadPoolExecutor
from hashlib import sha256
from io import BytesIO
import json
from pathlib import Path, PurePosixPath
import re, struct, subprocess, sys, time
from zipfile import ZipFile, ZIP_DEFLATED

lab=Path(__file__).resolve().parent
project=lab.parent
sys.path.insert(0,str(project/'src'))
from cmnd_linux.application_stage import VENDOR_INPUT_HASHES
from cmnd_linux.java_portability import linux_class_replacements
if len(sys.argv)!=2:
    raise SystemExit('Supply the absolute path to the verified original vendor input directory')
source=Path(sys.argv[1]).resolve(strict=True)
cfr=lab/'decompilation-tools/cfr-0.152.jar'
java=Path('C:/Program Files/TPV/jre-17.0.16/bin/java.exe')
assert sha256(cfr.read_bytes()).hexdigest()=='f686e8f3ded377d7bc87d216a90e9e9512df4156e75b06c655a16648ae8765b2'
out=lab/('decompile-assessment-'+str(time.time_ns()))
out.mkdir(mode=0o700)

def parse(data):
    assert data[:4]==b'\xca\xfe\xba\xbe'
    count=struct.unpack_from('>H',data,8)[0]; pos=10;i=1;strings={}
    while i<count:
        tag=data[pos];pos+=1
        if tag==1:
            size=struct.unpack_from('>H',data,pos)[0];pos+=2
            strings[i]=data[pos:pos+size].decode('utf8',errors='replace');pos+=size
        else:
            pos+={3:4,4:4,5:8,6:8,7:2,8:2,9:4,10:4,11:4,12:4,15:3,16:2,17:4,18:4,19:2,20:2}[tag]
            if tag in (5,6):i+=1
        i+=1
    def u2():
        nonlocal pos
        value=struct.unpack_from('>H',data,pos)[0];pos+=2;return value
    def attributes():
        nonlocal pos
        result={}
        for _ in range(u2()):
            name=strings[u2()];size=struct.unpack_from('>I',data,pos)[0];pos+=4
            result[name]=data[pos:pos+size];pos+=size
        return result
    pos+=6;interfaces=u2();pos+=interfaces*2
    for _ in range(u2()):pos+=6;attributes()
    metrics=Counter(classes=1,methods=0,bodies=0,native_methods=0,bodies_with_local_names=0,bodies_with_lines=0)
    for _ in range(u2()):
        access=u2();name=u2();desc=u2();attrs=attributes();metrics['methods']+=1
        if access&0x100:metrics['native_methods']+=1
        if 'Code' in attrs:
            metrics['bodies']+=1;code=attrs['Code'];off=8+struct.unpack_from('>I',code,4)[0]
            off+=2+8*struct.unpack_from('>H',code,off)[0]
            subcount=struct.unpack_from('>H',code,off)[0];off+=2
            for _ in range(subcount):
                index,size=struct.unpack_from('>HI',code,off);off+=6
                if strings[index]=='LocalVariableTable':metrics['bodies_with_local_names']+=1
                if strings[index]=='LineNumberTable':metrics['bodies_with_lines']+=1
                off+=size
    attrs=attributes();metrics['with_source_filename']=int('SourceFile' in attrs)
    metrics['class_major_'+str(struct.unpack_from('>H',data,6)[0])]=1
    return metrics,list(strings.values())

patterns={
 'windows_process':r'(?i)cmd(?:\.exe)?\s+/c|cmd\.exe|powershell|taskkill|wmic|sc\.exe|net stop|net start',
 'windows_executable':r'(?i)\.(?:exe|bat|cmd)(?:$|[\s"\x27])',
 'windows_drive':r'(?i)[c-z]:[\\/]',
 'windows_registry':r'(?i)HKEY_|SOFTWARE\\Microsoft|winreg',
 'rf_deprioritized':r'(?i)dektec|mgate|dtapi|dtu-|dta-',
}
components=[];windows=[];thirdparty=Counter();archives=[]
for archive_name,expected in VENDOR_INPUT_HASHES.items():
    if not archive_name.endswith(('.war','.jar')):continue
    path=source/archive_name
    assert sha256(path.read_bytes()).hexdigest()==expected
    key=Path(archive_name).stem
    root=out/key;root.mkdir();(root/'lib').mkdir()
    metrics=Counter();top=[];private_classes={}
    with ZipFile(path) as z:
        for entry in z.infolist():
            name=entry.filename
            if name.endswith('.class'):
                relative=name.removeprefix('WEB-INF/classes/')
                assert not PurePosixPath(relative).is_absolute() and '..' not in PurePosixPath(relative).parts
                data=z.read(entry);private_classes[relative]=data
            elif name.startswith('WEB-INF/lib/') and name.endswith('.jar'):
                dest=root/'lib'/PurePosixPath(name).name
                assert not dest.exists();raw=z.read(entry);dest.write_bytes(raw)
                with ZipFile(BytesIO(raw)) as nested:
                    names=[n for n in nested.namelist() if n.endswith('.class')]
                    thirdparty[key]+=len(names)
                    own=[n for n in names if n.startswith(('com/tpvision/','com/tpv/','com/philips/'))]
                    for n in own:
                        data=nested.read(n)
                        if n in private_classes and private_classes[n]!=data:
                            raise ValueError('Conflicting vendor classes: '+n)
                        private_classes[n]=data
    for name,data in private_classes.items():
        m,values=parse(data);metrics.update(m)
        if '$' not in PurePosixPath(name).name:top.append(name[:-6]+'.java')
        flags={p:sum(bool(re.search(expr,s)) for s in values) for p,expr in patterns.items()}
        if any(flags.values()):windows.append({'component':key,'class':name,'flags':{k:v for k,v in flags.items() if v}})
    jar=root/'input.jar'
    with ZipFile(jar,'w',ZIP_DEFLATED) as z:
        for name,data in private_classes.items():z.writestr(name,data)
    components.append({'name':key,'metrics':dict(metrics),'top_sources':top,'classes':len(private_classes)})
    print(json.dumps({'prepared':key,'metrics':dict(metrics)}),flush=True)

def decompile(component):
    root=out/component['name'];dest=root/'recovered'
    libs=';'.join(str(p.relative_to(root)) for p in (root/'lib').glob('*.jar'))
    cmd=[str(java),'-Xmx1500m','-jar',str(cfr),'input.jar','--outputdir',str(dest),
         '--silent','true','--caseinsensitivefs','true']
    if libs:cmd+=['--extraclasspath',libs]
    with (root/'decompiler.log').open('wb') as log:
        try:code=subprocess.run(cmd,cwd=root,stdout=log,stderr=subprocess.STDOUT,timeout=300).returncode
        except subprocess.TimeoutExpired:code='timeout'
    files=list(dest.rglob('*.java')) if dest.exists() else []
    flagged=[]
    for p in files:
        text=p.read_text(errors='replace')
        if re.search(r'(?i)could not decompile|decompilation failed|illegal stack|unable to fully structure|decompiler exception',text):
            flagged.append(p.relative_to(dest).as_posix())
    found={p.relative_to(dest).as_posix() for p in files}
    result={'name':component['name'],'exit':code,'java_files':len(files),'top_sources_expected':len(component['top_sources']),
            'top_sources_missing':sorted(set(component['top_sources'])-found),'flagged_sources':flagged}
    print(json.dumps({'decompilation':result}),flush=True)
    return result
with ThreadPoolExecutor(max_workers=2) as pool:results=list(pool.map(decompile,components))
report={'source':'hash-pinned original 7.5.9 inputs','components':components,'decompilation':results,
        'windows_indicators':windows,'nested_dependency_class_occurrences':dict(thirdparty),
        'scope':'All direct application classes in five WARs plus reload.jar; vendor namespaces in nested dependencies. Not every third-party dependency or native binary.',
        'lossless_source_recovery':False,'recompilation_tested':False,'application_execution':False}
(out/'report.json').write_text(json.dumps(report,indent=2))
print('PRIVATE_REPORT',out/'report.json',flush=True)
