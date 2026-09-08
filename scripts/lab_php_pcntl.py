"""Add the actual vendor Linux export-worker dependency in the disposable VM."""
import argparse
import json
import os
from pathlib import Path
import re
import socket
import subprocess

BASE_IMAGE = 'sha256:94e10e115eaf9f009105bd0f6248d61798c4336875c5f730cc4e9f89e946a299'
CMS = '/opt/cmnd-lab-cms/SmartCMS'


def run(*args, **kwargs):
    return subprocess.run(args, check=True, capture_output=True, **kwargs)


def main():
    parser = argparse.ArgumentParser()
    parser.add_argument('--execute', action='store_true')
    args = parser.parse_args()
    if not args.execute or os.geteuid() or socket.gethostname() != 'cmnd-qualification':
        raise SystemExit('Requires dedicated lab root and --execute')
    run('systemctl', 'is-active', '--quiet', 'cmnd-lab-egress')
    run('python3', '/usr/local/lib/cmnd-lab/egress.py', '--policy', '/etc/cmnd/lab-egress.json', '--execute')
    previous = json.loads(run('docker', 'inspect', 'cmnd-lab-php').stdout)[0]
    if (previous['Image'] != BASE_IMAGE or previous['Config']['User'] != '33:33'
            or not previous['HostConfig']['ReadonlyRootfs'] or previous['HostConfig']['NetworkMode'] != 'host'):
        raise SystemExit('Existing PHP differs from the reviewed extension-enabled lab image')
    saved = 'cmnd-lab-php-before-pcntl'
    if subprocess.run(['docker', 'inspect', saved], capture_output=True).returncode == 0:
        raise SystemExit('Previous PHP checkpoint exists; preserve it')
    context = Path(__file__).resolve().parents[1] / 'deploy/php'
    build = subprocess.run(['docker', 'build', '--network', 'none', '--pull=false',
        '-t', 'cmnd-lab-php:5.6-pcntl', '-f', str(context / 'Dockerfile.lab-pcntl'), str(context)], capture_output=True)
    private_log = Path('/var/lib/cmnd-lab/php-pcntl-build.log')
    if not private_log.exists():
        with os.fdopen(os.open(private_log, os.O_CREAT | os.O_EXCL | os.O_WRONLY, 0o600), 'wb') as stream:
            stream.write(build.stdout + build.stderr)
    if build.returncode:
        raise SystemExit('Offline PCNTL build failed; original PHP unchanged, private build log retained')
    image = json.loads(run('docker', 'image', 'inspect', 'cmnd-lab-php:5.6-pcntl').stdout)[0]['Id']
    if not re.fullmatch(r'sha256:[0-9a-f]{64}', image):
        raise SystemExit('Unexpected image identifier')
    modules = run('docker', 'run', '--rm', '--network', 'none', '--user', '33:33', '--read-only',
        '--cap-drop', 'ALL', image, 'php', '-m').stdout.decode().splitlines()
    if not all(name in modules for name in ('pcntl', 'posix', 'zip', 'gd', 'xmlrpc', 'pdo_mysql', 'curl')):
        raise SystemExit('Candidate PHP lacks required runtime modules; original PHP unchanged')
    run('docker', 'stop', 'cmnd-lab-php')
    run('docker', 'rename', 'cmnd-lab-php', saved)
    run('docker', 'run', '-d', '--name', 'cmnd-lab-php', '--network', 'host',
        '--user', '33:33', '--read-only', '--cap-drop', 'ALL', '--pids-limit', '64', '--memory', '1g',
        '--security-opt', 'no-new-privileges', '--tmpfs', '/tmp:rw,nosuid,noexec,size=128m',
        '--mount', f'type=bind,src={CMS},dst={CMS},readonly',
        '--mount', f'type=bind,src={CMS}/sites/default/files,dst={CMS}/sites/default/files',
        '--mount', 'type=bind,src=/var/lib/cmnd-lab/fpm.conf,dst=/usr/local/etc/php-fpm.conf,readonly',
        '--mount', 'type=bind,src=/etc/ssl/certs,dst=/etc/ssl/certs,readonly', image)
    run('docker', 'exec', 'cmnd-lab-php', 'install', '-d', '-m', '0700', '/tmp/cmnd-lab-pgt')
    print(json.dumps({'image': image, 'required_modules_present': True, 'previous_container': saved,
        'network_build_access': False, 'cms_export_qualified': False}))


if __name__ == '__main__':
    main()
