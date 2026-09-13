#!/usr/bin/env python3
"""Offline, non-deploying catalog/workspace/build tools for the research track.

Requires Python 3.11+, JDK 17 and original hash-pinned vendor inputs. Never starts
CMND, contacts devices, downloads code, initializes a DB or modifies services.
"""
from __future__ import annotations

import argparse
from collections import Counter
import hashlib
import json
import os
import re
from pathlib import Path, PurePosixPath
import stat
import subprocess
import time
import zipfile

ROOT = Path(__file__).resolve().parents[1]
PROJECT = ROOT / 'native-project'


def digest(path):
    with Path(path).open('rb') as stream:
        return hashlib.file_digest(stream, 'sha256').hexdigest()


def write_json(path, data):
    path = Path(path)
    path.parent.mkdir(parents=True, exist_ok=True)
    path.write_text(json.dumps(data, indent=2) + '\n', encoding='utf-8')


def safe_relative(name):
    p = PurePosixPath(name)
    if not name or p.is_absolute() or '..' in p.parts or '\\' in name or ':' in name or any(ord(c) < 32 for c in name):
        raise ValueError('Unsafe relative path: ' + repr(name))
    return p


def destination(root, name):
    root = Path(root).resolve()
    path = root.joinpath(*safe_relative(name).parts)
    if not path.resolve().is_relative_to(root):
        raise ValueError('Destination escapes workspace: ' + name)
    return path


def preserve_write(path, raw):
    """Populate missing files, but never overwrite an existing developer edit."""
    path = Path(path)
    if path.exists():
        if path.read_bytes() != raw:
            raise ValueError('Refusing to overwrite different existing file: ' + str(path))
        return
    path.parent.mkdir(parents=True, exist_ok=True)
    with path.open('xb') as stream:
        stream.write(raw)


def checked_members(archive):
    seen = set()
    for info in archive.infolist():
        safe_relative(info.filename)
        key = info.filename.casefold() if os.name == 'nt' else info.filename
        if key in seen:
            raise ValueError('Duplicate/colliding ZIP path: ' + info.filename)
        seen.add(key)
        if stat.S_ISLNK(info.external_attr >> 16):
            raise ValueError('ZIP symlink rejected: ' + info.filename)
        if not info.is_dir():
            yield info


def materialize(project=PROJECT, reference=ROOT / 'recovered'):
    config = json.loads((project / 'project.json').read_text())
    provenance = json.loads((reference / 'provenance.json').read_text())
    hashes = {r['path']: r['sha256'] for r in provenance}
    map_path = project / 'source-map.json'
    previous = {r['editable']: r for r in json.loads(map_path.read_text())} if map_path.exists() else {}
    mapping = []
    for name in config['modules']:
        base = reference / 'java' / 'vineflower' / name
        sources = sorted(base.rglob('*.java'))
        if not sources:
            raise ValueError('No recovered source for module: ' + name)
        for source in sources:
            package_path = source.relative_to(base).as_posix()
            variant = config.get('source_variants', {}).get(name, {}).get(package_path)
            if variant:
                if variant not in ('cfr', 'vineflower'):
                    raise ValueError('Unknown decompiler variant')
                source = destination(reference / 'java' / variant / name, package_path)
            relative = source.relative_to(reference).as_posix()
            if digest(source) != hashes[relative]:
                raise ValueError('Reference source hash mismatch: ' + relative)
            editable = 'modules/' + name + '/src/main/java/' + package_path
            target = destination(project, editable)
            # Existing working copies intentionally retain edits; provenance is
            # always anchored to the independently verified original reference.
            if not target.exists():
                preserve_write(target, source.read_bytes())
            elif editable in previous and previous[editable]['reference'] != 'recovered/' + relative:
                if digest(target) != previous[editable]['reference_sha256']:
                    raise ValueError('Cannot switch decompiler variant over developer edits: ' + editable)
                # Explicit project.json variant change; old reference remains
                # untouched. Only a byte-identical pristine working copy changes.
                target.write_bytes(source.read_bytes())
            mapping.append({'editable': editable, 'reference': 'recovered/' + relative,
                            'reference_sha256': hashes[relative]})
    write_json(project / 'source-map.json', mapping)
    catalog = []
    primary = {r['reference']: r['editable'] for r in mapping}
    for row in provenance:
        original = 'recovered/' + row['path']
        family = '/'.join(row['path'].split('/')[:2]) if row['path'].split('/')[0] not in ('original', 'notices') else row['path'].split('/')[0]
        catalog.append({**row, 'reference': original, 'family': family,
                        'editable': primary.get(original),
                        'checkout_supported': True})
    write_json(project / 'catalog' / 'sources.json', catalog)
    write_json(project / 'catalog' / 'summary.json', {
        'reference_files': len({r['path'] for r in catalog}), 'provenance_records': len(catalog), 'primary_java_files': len(mapping),
        'families': dict(sorted(Counter(r['family'] for r in catalog).items())),
        'originals': '../../recovered/README.md',
        'recovery_status': '../../recovered/recovery-status.json',
        'runtime_qualified': False,
    })
    print(json.dumps({'catalog_records': len(catalog), 'unique_files': len({r['path'] for r in catalog}), 'editable_java': len(mapping)}), flush=True)


def checkout(relative, project=PROJECT):
    """Editable copy of ANY cataloged alternate/library/native/source file."""
    catalog = json.loads((project / 'catalog/sources.json').read_text())
    selected = list({r['sha256']: r for r in catalog if r['path'] == relative}.values())
    if len(selected) != 1:
        raise ValueError('Select one exact path from catalog/sources.json')
    row = selected[0]
    source = destination(ROOT / 'recovered', relative)
    if digest(source) != row['sha256']:
        raise ValueError('Reference hash mismatch')
    target = destination(project / 'working', relative)
    preserve_write(target, source.read_bytes())
    print(target)


def checkout_tree(prefix, project=PROJECT, reference=ROOT / 'recovered'):
    prefix = safe_relative(prefix.rstrip('/')).as_posix() + '/'
    catalog = json.loads((project / 'catalog/sources.json').read_text())
    selected = {}
    for row in catalog:
        if row['path'].startswith(prefix):
            if row['path'] in selected and row['sha256'] != selected[row['path']]['sha256']:
                raise ValueError('Conflicting source hashes')
            selected[row['path']] = row
    if not selected:
        raise ValueError('No cataloged sources under prefix')
    for relative, row in selected.items():
        source = destination(reference, relative)
        if digest(source) != row['sha256']:
            raise ValueError('Reference hash mismatch')
        preserve_write(destination(project / 'working', relative), source.read_bytes())
    print(json.dumps({'editable_copies': len(selected), 'prefix': prefix}))


def search_catalog(query, project=PROJECT):
    rows = json.loads((project / 'catalog/sources.json').read_text())
    matches = {r['path']: {'path': r['path'], 'editable': r['editable'], 'status': r.get('status')}
               for r in rows if query.casefold() in (r['path'] + ' ' + str(r.get('origin', ''))).casefold()}
    print(json.dumps({'matches': len(matches), 'first_50': list(matches.values())[:50]}, indent=2))


def prepare(vendor_tree, tomcat_home, project=PROJECT):
    config = json.loads((project / 'project.json').read_text())
    vendor_tree = Path(vendor_tree).resolve()
    if digest(vendor_tree) != config['vendor_tree_sha256']:
        raise ValueError('Original vendor tree SHA-256 mismatch')
    vendor = project / '.vendor'
    locks = []
    resource_rows = []
    with zipfile.ZipFile(vendor_tree) as tree:
        # Validate all paths before writing any original archive.
        entries = {i.filename: i for i in checked_members(tree)}
        for name, module in config['modules'].items():
            member = module['archive']
            raw = tree.read(entries[member])
            target = vendor / 'originals' / (name + Path(member).suffix)
            preserve_write(target, raw)
            locks.append({'path': target.relative_to(project).as_posix(), 'sha256': digest(target), 'origin': member})
            with zipfile.ZipFile(target) as war:
                infos = list(checked_members(war))
                for info in infos:
                    member_path = info.filename
                    if member_path.startswith(('WEB-INF/lib/', 'WEB-INF/lib-provided/')) and member_path.endswith('.jar'):
                        dep = destination(vendor / 'dependencies' / name, member_path)
                        preserve_write(dep, war.read(info))
                        locks.append({'path': dep.relative_to(project).as_posix(), 'sha256': digest(dep), 'origin': member + '!' + member_path})
                    # Every non-class/non-library web/config resource is editable
                    # locally, including binary media and original manifests.
                    elif not member_path.endswith('.class'):
                        resource = destination(project / 'resources' / name, member_path)
                        raw_resource = war.read(info)
                        preserve_write(resource, raw_resource)
                        resource_rows.append({'module': name, 'original': member + '!' + member_path,
                                              'editable_local': resource.relative_to(project).as_posix(),
                                              'bytes': len(raw_resource), 'sha256': hashlib.sha256(raw_resource).hexdigest()})
    # Bootstrap jar set is locked in the repository from the original Tomcat
    # installer. A different local Tomcat version is rejected, not substituted.
    expected = json.loads((project / 'tomcat-lock.json').read_text())
    for row in expected:
        source = destination(Path(tomcat_home), row['path'])
        if digest(source) != row['sha256']:
            raise ValueError('Tomcat dependency mismatch: ' + row['path'])
        target = destination(vendor / 'tomcat', row['path'])
        preserve_write(target, source.read_bytes())
        locks.append({'path': target.relative_to(project).as_posix(), 'sha256': digest(target), 'origin': 'original Tomcat 9.0.109/' + row['path']})
    write_json(vendor / 'dependencies.json', locks)
    write_json(project / 'catalog/web-resources.json', resource_rows)
    print(json.dumps({'prepared_dependencies_and_archives': len(locks), 'vendor_executed': False}), flush=True)


def verify_dependencies(project):
    rows = json.loads((project / '.vendor/dependencies.json').read_text())
    for row in rows:
        if digest(destination(project, row['path'])) != row['sha256']:
            raise ValueError('Changed dependency: ' + row['path'])
    return rows


def prepare_distributions(vendor_tree, project=PROJECT):
    """Preserve ALL top-level supporting ZIP distributions as editable local data.

    Includes the separate SmartCMS frontend, plugins, PHP/Apache, Philips
    resources, MGate, PSG, runtime distributions and their original binaries.
    Nothing is executed or promoted into Git automatically.
    """
    config = json.loads((project / 'project.json').read_text())
    if digest(vendor_tree) != config['vendor_tree_sha256']:
        raise ValueError('Original vendor tree SHA-256 mismatch')
    rows = []
    with zipfile.ZipFile(vendor_tree) as tree:
        entries = list(checked_members(tree))
        for entry in entries:
            if not entry.filename.endswith('.zip'):
                continue
            name = PurePosixPath(entry.filename).stem
            original = project / '.vendor/distributions' / (name + '.zip')
            preserve_write(original, tree.read(entry))
            with zipfile.ZipFile(original) as archive:
                infos = list(checked_members(archive))
                for info in infos:
                    target = destination(project / 'resources/distributions' / name, info.filename)
                    raw = archive.read(info)
                    preserve_write(target, raw)
                    rows.append({'distribution': name, 'original': entry.filename + '!' + info.filename,
                                 'editable_local': target.relative_to(project).as_posix(),
                                 'bytes': len(raw), 'sha256': hashlib.sha256(raw).hexdigest(),
                                 'execution_tested': False})
            write_json(project / 'catalog/supporting-distributions.json', rows)
            print(json.dumps({'distribution': name, 'files': len(infos), 'vendor_executed': False}), flush=True)


def build(names, java_home, timeout=300, project=PROJECT):
    config = json.loads((project / 'project.json').read_text())
    for name in names:
        if name not in config['modules']:
            raise ValueError('Unknown module: ' + name)
    verify_dependencies(project)
    javac = Path(java_home) / 'bin' / ('javac.exe' if os.name == 'nt' else 'javac')
    version = subprocess.run([str(javac), '-version'], capture_output=True, text=True, check=True).stdout.strip()
    if not version.startswith('javac 17.'):
        raise ValueError('Use JDK 17; found ' + version)
    run = project / 'build' / ('run-' + str(time.time_ns()))
    run.mkdir(parents=True)
    results = []
    for name in names:
        folder = run / name
        classes = folder / 'classes'
        classes.mkdir(parents=True)
        # Match Tomcat's container API precedence; the WAR also contains old
        # servlet API copies, which must not shadow the container's API.
        deps = sorted((project / '.vendor/tomcat').rglob('*.jar'))
        deps += sorted((project / '.vendor/dependencies' / name).rglob('*.jar'))
        if name == 'smartcms':
            deps += list((project / '.vendor/dependencies/cas').rglob('validation-api-*.jar'))
        source_root = project / 'modules' / name / 'src/main/java'
        sources = sorted(source_root.rglob('*.java'), key=lambda p: p.relative_to(source_root).as_posix())
        supports = project / 'modules' / name / 'src/compileOnly/java'
        sources += sorted(supports.rglob('*.java'))
        recovery_warnings = [p.relative_to(source_root).as_posix() for p in source_root.rglob('*.java')
                             if re.search(r'(?i)could not be decompiled|couldn.t be decompiled|\$VF:|decompilation failed', p.read_text(encoding='utf-8', errors='replace'))]
        if not sources:
            raise ValueError('No sources; run init first: ' + name)
        args = ['-proc:none', '-implicit:none', '--release', str(config['java_release']),
                '-encoding', 'UTF-8', '-Xmaxerrs', '100', '-d', str(classes),
                '-classpath', os.pathsep.join(str(p) for p in deps)] + [str(p) for p in sources]
        argfile = folder / 'javac.args'
        # Java argument files interpret backslashes as escapes.
        argfile.write_text('\n'.join(json.dumps(a.replace('\\', '/')) for a in args), encoding='utf-8')
        with (folder / 'compiler.log').open('wb') as log:
            try:
                code = subprocess.run([str(javac), '-J-XX:ActiveProcessorCount=2', '-J-Xmx768m', '@' + str(argfile)],
                                      stdout=log, stderr=subprocess.STDOUT, timeout=timeout).returncode
            except subprocess.TimeoutExpired:
                code = 'timeout'
        emitted = sorted(classes.rglob('*.class'), key=lambda p: p.relative_to(classes).as_posix())
        original = next((project / '.vendor/originals').glob(name + '.*'))
        with zipfile.ZipFile(original) as archive:
            original_classes = {i.filename.removeprefix('WEB-INF/classes/') for i in archive.infolist()
                                if i.filename.endswith('.class') and not i.filename.startswith('META-INF/versions/')}
        emitted_names = {p.relative_to(classes).as_posix() for p in emitted if p.relative_to(classes).as_posix() != 'lombok/Generated.class'}
        artifact = None
        if code == 0:
            artifact = folder / (name + '-research-classes.jar')
            with zipfile.ZipFile(artifact, 'w', compression=zipfile.ZIP_DEFLATED) as archive:
                for path in emitted:
                    # Build-only annotation declarations are not application code.
                    if path.relative_to(classes).as_posix() == 'lombok/Generated.class':
                        continue
                    info = zipfile.ZipInfo(path.relative_to(classes).as_posix(), (1980, 1, 1, 0, 0, 0))
                    info.compress_type = zipfile.ZIP_DEFLATED
                    archive.writestr(info, path.read_bytes())
        row = {'module': name, 'compiler': version, 'compiler_exit': code,
               'source_files': len(sources), 'emitted_classes': len(emitted),
               'artifact': artifact.name if artifact else None,
               'artifact_sha256': digest(artifact) if artifact else None,
               'inputs_sha256': hashlib.sha256('\n'.join(p.relative_to(project).as_posix() + ':' + digest(p) for p in sources).encode()).hexdigest(),
               'original_class_count': len(original_classes),
               'missing_original_class_names': sorted(original_classes - emitted_names),
               'additional_class_names': sorted(emitted_names - original_classes),
               'vendor_executed': False, 'runtime_qualified': False,
               'recovery_warnings': recovery_warnings,
               'deployable': False}
        results.append(row)
        write_json(run / 'report.json', results)
        print(json.dumps(row), flush=True)
    print('Build report: ' + str(run / 'report.json'), flush=True)
    return 0 if all(r['compiler_exit'] == 0 for r in results) else 1


def audit_platform(project=PROJECT):
    patterns = {
        'windows-process-or-native-library': re.compile(r'(?i)\.(exe|dll)\b|cmd\s*/c|powershell|System\.loadLibrary'),
        'windows-path-or-platform-api': re.compile(r'[A-Za-z]:\\\\|(?i:wmic|netsh|taskkill|tasklist|os\.name|ProcessBuilder|Runtime\.getRuntime\(\)\.exec)'),
    }
    rows = []
    for source in sorted((project / 'modules').rglob('*.java')):
        for number, line in enumerate(source.read_text(encoding='utf-8', errors='replace').splitlines(), 1):
            for category, pattern in patterns.items():
                if pattern.search(line):
                    rows.append({'file': source.relative_to(project).as_posix(), 'line': number,
                                 'category': category, 'status': 'needs-context-review-not-automatically-a-defect'})
    write_json(project / 'catalog/platform-candidates.json', rows)
    print(json.dumps({'platform_review_candidates': len(rows)}))


def validate_project(project=PROJECT, reference=ROOT / 'recovered'):
    provenance = json.loads((reference / 'provenance.json').read_text())
    catalog = json.loads((project / 'catalog/sources.json').read_text())
    keys = ('path', 'sha256', 'origin', 'status')
    identity = lambda r: tuple(r.get(k) for k in keys)
    if Counter(map(identity, provenance)) != Counter(map(identity, catalog)):
        raise ValueError('Catalog omits or changes provenance records')
    mappings = json.loads((project / 'source-map.json').read_text())
    seen = set()
    repairs = []
    for row in mappings:
        if row['editable'] in seen:
            raise ValueError('Duplicate editable source mapping')
        seen.add(row['editable'])
        if not row['reference'].startswith('recovered/'):
            raise ValueError('Invalid reference root')
        source = destination(reference, row['reference'].removeprefix('recovered/'))
        if digest(source) != row['reference_sha256']:
            raise ValueError('Reference source changed: ' + row['reference'])
        target = destination(project, row['editable'])
        actual = digest(target)
        if actual != row['reference_sha256']:
            repairs.append({'file': row['editable'], 'reference': row['reference'],
                            'reference_sha256': row['reference_sha256'], 'editable_sha256': actual})
    working = {p.relative_to(project).as_posix() for p in (project / 'modules').glob('*/src/main/java/**/*.java')}
    if working != seen:
        raise ValueError('Primary source map does not match editable Java tree')
    write_json(project / 'catalog/edited-sources.json', repairs)
    print(json.dumps({'reference_records_preserved': len(catalog), 'mapped_java_files': len(seen),
                      'edited_java_files': len(repairs)}), flush=True)


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    sub = parser.add_subparsers(dest='command', required=True)
    sub.add_parser('init', help='Catalog every recovered file and seed six editable Java modules; retain existing edits')
    sub.add_parser('audit-platform', help='Inventory host commands/native dependencies without changing behavior')
    sub.add_parser('validate', help='Check catalog completeness, primary source mappings and unchanged references')
    copy = sub.add_parser('checkout', help='Copy any cataloged source/alternate/native file into working/')
    copy.add_argument('path')
    copy_tree = sub.add_parser('checkout-tree', help='Create editable copies of an entire cataloged source subtree')
    copy_tree.add_argument('prefix')
    search = sub.add_parser('find', help='Search all cataloged paths/origins without opening the large JSON index')
    search.add_argument('query')
    prep = sub.add_parser('prepare', help='Prepare offline vendor dependencies and editable web resources')
    prep.add_argument('--vendor-tree', required=True)
    prep.add_argument('--tomcat-home', required=True)
    distributions = sub.add_parser('prepare-distributions', help='Extract every supporting ZIP, including the separate SmartCMS frontend and RF/native components, as local editable data')
    distributions.add_argument('--vendor-tree', required=True)
    compile_cmd = sub.add_parser('build', help='Compile only; never starts vendor applications')
    compile_cmd.add_argument('--java-home', required=True)
    compile_cmd.add_argument('--module', action='append')
    compile_cmd.add_argument('--timeout', type=int, default=300)
    args = parser.parse_args()
    if args.command == 'init':
        materialize()
    elif args.command == 'audit-platform':
        audit_platform()
    elif args.command == 'validate':
        validate_project()
    elif args.command == 'checkout':
        checkout(args.path)
    elif args.command == 'checkout-tree':
        checkout_tree(args.prefix)
    elif args.command == 'find':
        search_catalog(args.query)
    elif args.command == 'prepare':
        prepare(args.vendor_tree, args.tomcat_home)
    elif args.command == 'prepare-distributions':
        prepare_distributions(args.vendor_tree)
    else:
        modules = args.module or list(json.loads((PROJECT / 'project.json').read_text())['modules'])
        return build(modules, args.java_home, args.timeout)
    return 0


if __name__ == '__main__':
    raise SystemExit(main())
