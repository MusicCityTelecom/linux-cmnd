"""Static PE/CLR inventory; preserve every original, execute no vendor binary."""
from collections import Counter
from functools import lru_cache
from hashlib import sha256
from io import BytesIO
from pathlib import Path
import json,pefile
from zipfile import ZipFile

root=Path(__file__).resolve().parent/'complete-vendor-1789227709466446600'
inventory=json.loads((root/'inventory.json').read_text())
out=root/'native-artifacts';out.mkdir(exist_ok=True)
snapshot=ZipFile(root/'original-installer-tree.zip')
@lru_cache(maxsize=3)
def get_data(path):
    parts=path.split('!');data=snapshot.read(parts[0])
    for part in parts[1:]:
        with ZipFile(BytesIO(data)) as z:data=z.read(part)
    return data
records=[];seen={}
for entry in inventory['opaque_components']:
    raw=get_data(entry['path']);digest=sha256(raw).hexdigest()
    record=dict(entry,sha256=digest)
    if digest in seen:
        records.append(record|{'duplicate_of':seen[digest]});continue
    seen[digest]=entry['path']
    record['bytes']=len(raw)
    suffix=Path(entry['path'].split('!')[-1]).suffix.lower() or '.bin'
    target=out/(digest+suffix);target.write_bytes(raw)
    record['preserved_file']=target.name
    if raw[:2]==b'MZ':
        try:
            pe=pefile.PE(data=raw,fast_load=True)
            pe.parse_data_directories(directories=[1,0,14])
            managed=bool(pe.OPTIONAL_HEADER.DATA_DIRECTORY[14].VirtualAddress)
            record.update({'format':'PE','architecture':hex(pe.FILE_HEADER.Machine),'managed_dotnet':managed,
                'entry_point_rva':pe.OPTIONAL_HEADER.AddressOfEntryPoint,
                'imports':{i.dll.decode(errors='replace'):[f.name.decode(errors='replace') if f.name else '#'+str(f.ordinal) for f in i.imports]
                           for i in getattr(pe,'DIRECTORY_ENTRY_IMPORT',[])},
                'exports':[e.name.decode(errors='replace') if e.name else '#'+str(e.ordinal) for e in getattr(getattr(pe,'DIRECTORY_ENTRY_EXPORT',None),'symbols',[])],
                'decompilation_status':'pending-managed-decompiler' if managed else 'pending-native-decompiler'})
            pe.close()
        except Exception as exc:record['parse_error']=type(exc).__name__
    elif raw.startswith(b'\x7fELF'):record['format']='ELF';record['decompilation_status']='pending-native-decompiler'
    elif raw.startswith(bytes.fromhex('d0cf11e0a1b11ae1')):record['format']='OLE/MSI';record['decompilation_status']='pending-container-extraction'
    else:record['format']='other';record['decompilation_status']='retained-pending-format-analysis'
    records.append(record)
snapshot.close()
(root/'native-inventory.json').write_text(json.dumps(records,indent=2))
unique=[r for r in records if 'duplicate_of' not in r]
print(json.dumps({'occurrences':len(records),'unique_binaries':len(unique),'formats':dict(Counter(r.get('format','parse-error') for r in unique)),
                  'managed_pe':sum(r.get('managed_dotnet',False) for r in unique),
                  'native_pe':sum(r.get('format')=='PE' and not r.get('managed_dotnet',False) for r in unique),
                  'decompiled':0,'executed':0,'private_report':str(root/'native-inventory.json')}))
