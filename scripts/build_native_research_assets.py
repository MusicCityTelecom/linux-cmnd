#!/usr/bin/env python3
"""Archive explicitly selected compile outputs; never include logs/site resources."""
import argparse
import json
from pathlib import Path
import zipfile

from native_project import PROJECT, digest, write_json


def package(primary, others, output, commit):
    runs = [Path(p).resolve() for p in [primary, *others]]
    build_root = (PROJECT / 'build').resolve()
    for run in runs:
        if not run.is_relative_to(build_root) or not (run/'report.json').is_file():
            raise ValueError('Select explicit recorded research build runs only')
    if len(set(runs)) != len(runs):
        raise ValueError('Duplicate run')
    primary_report = json.loads((runs[0]/'report.json').read_text())
    if len(primary_report) != 6 or any(r['compiler_exit'] != 0 for r in primary_report):
        raise ValueError('Primary build must compile all six modules')
    if len(commit) != 40 or any(c not in '0123456789abcdef' for c in commit):
        raise ValueError('Exact source commit SHA required')
    output = Path(output).resolve()
    output.mkdir(parents=True, exist_ok=False)
    rows = []
    target = output/'cmnd-native-research-builds.zip'
    with zipfile.ZipFile(target, 'w', compression=zipfile.ZIP_DEFLATED) as archive:
        archive.writestr('README.txt', 'RESEARCH ONLY. Not a complete application or installer.\n'
                         'primary-linux contains the six successful Linux class JARs.\n'
                         'attempts preserves ALL class/JAR outputs from the explicitly selected runs,\n'
                         'including failed/partial attempts and offline contract fixtures.\n'
                         'No compiler logs, site data or generated application identities are included.\n'
                         'Original binaries remain in research-recovery-2026-09-12 release assets.\n'
                         'Vendor ownership/licenses remain applicable; not relicensed or endorsed.\n')
        for report in primary_report:
            path = runs[0]/report['module']/report['artifact']
            if digest(path) != report['artifact_sha256']:
                raise ValueError('Primary artifact hash mismatch')
            relative = 'primary-linux/'+path.name
            archive.write(path, relative)
            rows.append({'path':relative,'sha256':digest(path),'bytes':path.stat().st_size,'status':'compiled-not-deployable'})
        for run in runs:
            for path in sorted(run.rglob('*')):
                if not path.is_file() or path.suffix not in ('.class','.jar'):
                    continue
                if path.is_symlink() or not path.resolve().is_relative_to(run):
                    raise ValueError('Output escapes selected run')
                relative='attempts/'+run.name+'/'+path.relative_to(run).as_posix()
                archive.write(path,relative)
                rows.append({'path':relative,'sha256':digest(path),'bytes':path.stat().st_size,
                             'status':'research-attempt-not-deployable'})
        archive.writestr('MANIFEST.json',json.dumps({'source_commit':commit,'outputs':rows,
                         'full_application_runtime_tested':False,'deployable':False},indent=2))
        archive.write(PROJECT/'catalog/linux-build-evidence.json','linux-build-evidence.json')
    with zipfile.ZipFile(target) as archive:
        for row in rows:
            import hashlib
            data=archive.read(row['path'])
            if len(data)!=row['bytes'] or hashlib.sha256(data).hexdigest()!=row['sha256']:
                raise ValueError('Packaged output verification failed')
    (output/'SHA256SUMS').write_text(digest(target)+'  '+target.name+'\n',encoding='utf-8')
    write_json(output/'asset-verification.json',{'asset':target.name,'sha256':digest(target),
               'bytes':target.stat().st_size,'outputs_preserved':len(rows),'source_commit':commit})
    print((output/'asset-verification.json').read_text(),flush=True)


if __name__=='__main__':
    parser=argparse.ArgumentParser(description=__doc__)
    parser.add_argument('--primary-run',required=True)
    parser.add_argument('--include-run',action='append',default=[])
    parser.add_argument('--output',required=True)
    parser.add_argument('--source-commit',required=True)
    args=parser.parse_args()
    package(args.primary_run,args.include_run,args.output,args.source_commit)
