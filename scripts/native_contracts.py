#!/usr/bin/env python3
"""Offline original/rebuilt launcher contract + class-name inventory.

Executes ONLY MainMethodRunner against a dummy main, not an application startup.
No database, web server, protocol request, device scan or network operation.
"""
import argparse
import json
import os
from pathlib import Path
import subprocess
import sys
import time
import zipfile

from native_project import PROJECT, digest, verify_dependencies, write_json


def inspect_and_probe(run, java_home):
    run = Path(run).resolve()
    if not run.is_relative_to((PROJECT / 'build').resolve()):
        raise ValueError('Select a run inside native-project/build')
    verify_dependencies(PROJECT)
    reports = json.loads((run / 'report.json').read_text())
    probe = run / 'contracts' / ('run-' + str(time.time_ns()))
    probe.mkdir(parents=True, exist_ok=False)
    bin_dir = Path(java_home) / 'bin'
    suffix = '.exe' if os.name == 'nt' else ''
    subprocess.run([str(bin_dir / ('javac' + suffix)), '--release', '17', '-d', str(probe),
                    str(PROJECT / 'tests/LauncherProbe.java')], check=True, timeout=60)
    results = []
    for report in reports:
        name = report['module']
        if report['compiler_exit'] != 0:
            raise ValueError('Cannot qualify failed module: ' + name)
        rebuilt = run / name / report['artifact']
        if digest(rebuilt) != report['artifact_sha256']:
            raise ValueError('Build artifact has changed')
        original = next((PROJECT / '.vendor/originals').glob(name + '.*'))
        with zipfile.ZipFile(original) as z:
            classes = {i.filename.removeprefix('WEB-INF/classes/'): z.read(i)
                       for i in z.infolist() if i.filename.endswith('.class')
                       and not i.filename.startswith('META-INF/versions/')}
        reference = probe / (name + '-original-classes.jar')
        with zipfile.ZipFile(reference, 'w') as z:
            for path, content in classes.items(): z.writestr(path, content)
        with zipfile.ZipFile(rebuilt) as z:
            generated = {i.filename for i in z.infolist() if i.filename.endswith('.class')}
        row = {'module': name, 'original_class_count': len(classes), 'rebuilt_class_count': len(generated),
               'missing_original_class_names': sorted(set(classes) - generated),
               'additional_class_names': sorted(generated - set(classes)),
               'artifact_sha256': digest(rebuilt), 'full_application_runtime_tested': False,
               'tv_tested': False}
        row['missing_class_metadata'] = []
        for missing in row['missing_original_class_names']:
            result = subprocess.run([str(bin_dir / ('javap' + suffix)), '-v', '-p', '-classpath', str(reference),
                                     missing[:-6].replace('/', '.')], capture_output=True, text=True, check=True, timeout=30)
            flags = next(line.strip() for line in result.stdout.splitlines() if line.strip().startswith('flags:'))
            row['missing_class_metadata'].append({'class': missing, 'original_flags': flags,
                                                  'synthetic': 'ACC_SYNTHETIC' in flags})
        if name in ('cas', 'usermanagement', 'smartcontrol'):
            row['launcher_contracts'] = []
            for label, jar in (('original', reference), ('rebuilt', rebuilt)):
                result = subprocess.run([str(bin_dir / ('java' + suffix)), '-cp',
                                         os.pathsep.join((str(probe), str(jar))), 'LauncherProbe'],
                                        capture_output=True, text=True, timeout=30)
                row['launcher_contracts'].append({'input': label, 'exit': result.returncode,
                                                   'output': result.stdout.strip()})
                if result.returncode != 0:
                    raise RuntimeError(label + ' launcher probe failed: ' + result.stderr)
        # Compare the stable public/protected API surface of the repaired launcher
        # and pure enum helpers without executing the vendor classes.
        targets = ['org.springframework.boot.loader.MainMethodRunner'] if name in ('cas', 'usermanagement', 'smartcontrol') else []
        if name == 'smartcontrol':
            targets += ['be.tpvision.smartcontrol.util.ValueUtilities',
                        'be.tpvision.smartcontrol.rest.view_models.device_limits.MixedEnumLimitsViewModel']
        row['selected_api_comparisons'] = []
        for target in targets:
            surfaces = []
            for jar in (reference, rebuilt):
                result = subprocess.run([str(bin_dir / ('javap' + suffix)), '-protected', '-s',
                                         '-classpath', str(jar), target], capture_output=True, text=True,
                                        timeout=30, check=True)
                surfaces.append(sorted(line.strip() for line in result.stdout.splitlines()
                                       if line.strip() and not line.startswith('Compiled from')))
            row['selected_api_comparisons'].append({'class': target, 'match': surfaces[0] == surfaces[1]})
        results.append(row)
        write_json(probe / 'report.json', results)
        print(json.dumps(row), flush=True)
    return 0 if all(c['match'] for r in results for c in r['selected_api_comparisons']) else 1


if __name__ == '__main__':
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument('--run', required=True)
    parser.add_argument('--java-home', required=True)
    args = parser.parse_args()
    sys.exit(inspect_and_probe(args.run, args.java_home))
