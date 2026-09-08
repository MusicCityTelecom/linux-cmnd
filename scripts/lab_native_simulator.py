"""Start one synthetic TV on the existing, internal disposable-VM network."""
import json
import os
import socket
import subprocess
import sys
from pathlib import Path

IMAGE = 'python@sha256:78387bc3881b8273120a12ebe6c1ab22b018ccc2c9adf565ae1ac9b536e184ea'


def main():
    if os.geteuid() or socket.gethostname() != 'cmnd-qualification' or '--execute' not in sys.argv:
        raise SystemExit('Requires the dedicated disposable VM as root')
    network = json.loads(subprocess.check_output(['docker', 'network', 'inspect', 'cmnd-lab-db']))[0]
    if not network['Internal'] or network['IPAM']['Config'][0]['Subnet'] != '172.30.44.0/24':
        raise SystemExit('Internal lab network differs from the approved topology')
    command = ['iptables', '-C', 'CMND_LAB', '-d', '172.30.44.4/32', '-p', 'tcp', '--dport', '9079', '-j', 'ACCEPT']
    if subprocess.run(command, capture_output=True).returncode:
        command[1] = '-I'
        subprocess.run(command, check=True)
    if '--upgrade' in sys.argv:
        previous = json.loads(subprocess.check_output(['docker', 'inspect', 'cmnd-lab-tv-internal']))[0]
        if previous['NetworkSettings']['Networks']['cmnd-lab-db']['IPAddress'] != '172.30.44.4':
            raise SystemExit('Existing simulator is not the expected private target')
        saved = 'cmnd-lab-tv-internal-before-callback'
        if subprocess.run(['docker', 'inspect', saved], capture_output=True).returncode == 0:
            raise SystemExit('Prior simulator checkpoint exists; preserve it')
        subprocess.run(['docker', 'stop', 'cmnd-lab-tv-internal'], check=True)
        subprocess.run(['docker', 'rename', 'cmnd-lab-tv-internal', saved], check=True)
    else:
        subprocess.run(['systemctl', 'stop', 'cmnd-lab-tv-tpm191'], check=True)
    subprocess.run(['docker', 'run', '-d', '--name', 'cmnd-lab-tv-internal', '--network', 'cmnd-lab-db',
        '--ip', '172.30.44.4', '--user', '65534:65534', '--read-only', '--cap-drop', 'ALL',
        '--security-opt', 'no-new-privileges', '--pids-limit', '64', '--memory', '128m',
        '--mount', 'type=bind,src=/home/cmndlab/tooling/src,dst=/app,readonly',
        '-e', 'PYTHONPATH=/app', '-e', 'PYTHONDONTWRITEBYTECODE=1', IMAGE,
        'python', '-m', 'cmnd_linux', 'simulator', '--bind', '0.0.0.0', '--port', '9079',
        '--identity', 'SIMULATOR00000001020000000001', '--model', '43HFL6114U/27',
        '--serial', 'SIMULATOR00000001', '--callback-base-url', 'http://172.30.44.1:8080'], check=True)
    print('One synthetic TV started on the internal lab network; no published ports.')


if __name__ == '__main__':
    main()
