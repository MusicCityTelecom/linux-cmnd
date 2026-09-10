"""Activate only the reviewed native-thumbnail PHP candidate in the lab."""
import json
import os
import socket
import subprocess
import sys

PREVIOUS = 'sha256:ab1537bf1305c30c0701e4b938b33aa475d022d6dbbc73aaab022cff23a1c27f'
IMAGE = 'sha256:69c32774056e5b31462d7f3bf994b28fe36cc9e0e5adec8b0c19a7ae0cc17436'
CMS = '/opt/cmnd-lab-cms/SmartCMS'


def run(*args):
    return subprocess.run(args, check=True, capture_output=True)


if os.geteuid() or socket.gethostname() != 'cmnd-qualification' or '--execute' not in sys.argv:
    raise SystemExit('Requires dedicated lab root and --execute')
run('python3', '/usr/local/lib/cmnd-lab/egress.py', '--policy', '/etc/cmnd/lab-egress.json', '--execute')
current = json.loads(run('docker', 'inspect', 'cmnd-lab-php').stdout)[0]
if (current['Image'] != PREVIOUS or current['Config']['User'] != '33:33'
        or not current['HostConfig']['ReadonlyRootfs'] or current['HostConfig']['NetworkMode'] != 'host'):
    raise SystemExit('Unexpected current PHP identity or confinement')
saved = 'cmnd-lab-php-before-native-helpers'
if subprocess.run(['docker', 'inspect', saved], capture_output=True).returncode == 0:
    raise SystemExit('Previous helper checkpoint exists; preserve it')
run('docker', 'image', 'inspect', IMAGE)
run('docker', 'stop', 'cmnd-lab-php')
run('docker', 'rename', 'cmnd-lab-php', saved)
run('docker', 'run', '-d', '--name', 'cmnd-lab-php', '--network', 'host', '--user', '33:33',
    '--read-only', '--cap-drop', 'ALL', '--pids-limit', '128', '--memory', '1g',
    '--security-opt', 'no-new-privileges', '--tmpfs', '/tmp:rw,nosuid,noexec,size=128m',
    '--mount', f'type=bind,src={CMS},dst={CMS},readonly',
    '--mount', f'type=bind,src={CMS}/sites/default/files,dst={CMS}/sites/default/files',
    '--mount', 'type=bind,src=/var/lib/cmnd-lab/fpm.conf,dst=/usr/local/etc/php-fpm.conf,readonly',
    '--mount', 'type=bind,src=/etc/ssl/certs,dst=/etc/ssl/certs,readonly', IMAGE)
run('docker', 'exec', 'cmnd-lab-php', 'install', '-d', '-m', '0700', '/tmp/cmnd-lab-pgt')
print(json.dumps({'image': IMAGE, 'previous_container_preserved': saved, 'thumbnail_qualified': False}))
