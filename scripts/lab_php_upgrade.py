"""Swap only the disposable lab PHP process; retain its old container/data."""
import json
import os
import socket
import subprocess
import sys

IMAGE = 'sha256:94e10e115eaf9f009105bd0f6248d61798c4336875c5f730cc4e9f89e946a299'
BASE = '/var/lib/cmnd-lab'
CMS = '/opt/cmnd-lab-cms/SmartCMS'

if os.geteuid() or socket.gethostname() != 'cmnd-qualification' or '--execute' not in sys.argv:
    raise SystemExit('Requires dedicated lab root and --execute')
previous = json.loads(subprocess.check_output(['docker', 'inspect', 'cmnd-lab-php']))[0]
if (previous['Config']['User'] != '33:33' or not previous['HostConfig']['ReadonlyRootfs']
        or previous['HostConfig']['NetworkMode'] != 'host'):
    raise SystemExit('Existing PHP container differs from the expected private lab target')
saved = 'cmnd-lab-php-before-extensions'
if subprocess.run(['docker', 'inspect', saved], capture_output=True).returncode == 0:
    raise SystemExit('Previous PHP checkpoint exists; preserve it')
subprocess.run(['docker', 'image', 'inspect', IMAGE], stdout=subprocess.DEVNULL, check=True)
subprocess.run(['docker', 'stop', 'cmnd-lab-php'], check=True)
subprocess.run(['docker', 'rename', 'cmnd-lab-php', saved], check=True)
try:
    subprocess.run(['docker', 'run', '-d', '--name', 'cmnd-lab-php', '--network', 'host',
        '--user', '33:33', '--read-only', '--cap-drop', 'ALL', '--pids-limit', '64', '--memory', '1g',
        '--security-opt', 'no-new-privileges', '--tmpfs', '/tmp:rw,nosuid,noexec,size=128m',
        '--mount', f'type=bind,src={CMS},dst={CMS},readonly',
        '--mount', f'type=bind,src={CMS}/sites/default/files,dst={CMS}/sites/default/files',
        '--mount', f'type=bind,src={BASE}/fpm.conf,dst=/usr/local/etc/php-fpm.conf,readonly',
        '--mount', 'type=bind,src=/etc/ssl/certs,dst=/etc/ssl/certs,readonly', IMAGE], check=True)
except Exception:
    print('New PHP startup failed; preserved prior container is ' + saved, file=sys.stderr)
    raise
print('New extension-enabled PHP started; recreate private PGT directory and verify SSO.')
