"""Install persistent egress/startup dependencies in the disposable VM only.

Does not start CMND, Apache, PHP, databases, or the TV simulator. Existing
containers and their data remain unchanged. No Windows host networking changes.
"""
import argparse
import json
import os
from pathlib import Path
import pwd
import socket
import subprocess


def main():
    parser = argparse.ArgumentParser()
    parser.add_argument('--egress-source', type=Path, required=True)
    parser.add_argument('--execute', action='store_true')
    args = parser.parse_args()
    if not args.execute or os.geteuid() or socket.gethostname() != 'cmnd-qualification':
        raise SystemExit('Requires the dedicated qualification VM as root and --execute')
    if not Path('/var/lib/cmnd-lab-bootstrap-complete').is_file():
        raise SystemExit('Dedicated lab marker missing')
    if not args.egress_source.is_file() or args.egress_source.is_symlink():
        raise SystemExit('Expected source-only egress module is missing')
    # Require stopped applications until their isolation dependency is established.
    for name in ('cmnd-lab-tomcat', 'apache2'):
        if subprocess.run(['systemctl', 'is-active', '--quiet', name]).returncode == 0:
            raise SystemExit('Stop the dedicated lab applications before installing recovery dependencies')
    cmnd_uid = pwd.getpwnam('cmnd').pw_uid
    php_uid = pwd.getpwnam('www-data').pw_uid
    policy = {'chain': 'CMND_LAB', 'uids': [cmnd_uid, php_uid],
              'tcp_endpoints': [['172.30.44.2', 3306], ['172.30.44.4', 9079]]}
    worker = Path('/usr/local/lib/cmnd-lab/egress.py')
    policy_path = Path('/etc/cmnd/lab-egress.json')
    files = {worker: args.egress_source.read_bytes(), policy_path: (json.dumps(policy, indent=2) + '\n').encode(),
        Path('/etc/systemd/system/cmnd-lab-egress.service'): b'''[Unit]
Description=Disposable CMND lab IPv4/IPv6 service isolation
After=local-fs.target
Before=docker.service apache2.service cmnd-lab-tomcat.service
[Service]
Type=oneshot
RemainAfterExit=yes
Environment=PATH=/usr/sbin:/usr/bin:/sbin:/bin
ExecStart=/usr/bin/python3 /usr/local/lib/cmnd-lab/egress.py --policy /etc/cmnd/lab-egress.json --execute
NoNewPrivileges=true
PrivateTmp=true
ProtectHome=true
ProtectSystem=strict
ReadWritePaths=/run
CapabilityBoundingSet=CAP_NET_ADMIN CAP_NET_RAW
[Install]
WantedBy=multi-user.target
'''}
    for service in ('docker', 'apache2', 'cmnd-lab-tomcat'):
        files[Path(f'/etc/systemd/system/{service}.service.d/40-cmnd-lab-isolation.conf')] = (
            b'[Unit]\nRequires=cmnd-lab-egress.service\nAfter=cmnd-lab-egress.service\n')
    for target, data in files.items():
        if any(parent.is_symlink() for parent in (target, *target.parents)):
            raise SystemExit('Recovery target must not traverse a symbolic link')
        if target.exists() and target.read_bytes() != data:
            raise SystemExit('Refusing a differing pre-existing recovery configuration: ' + str(target))
    for target, data in files.items():
        target.parent.mkdir(parents=True, exist_ok=True)
        if not target.exists():
            with os.fdopen(os.open(target, os.O_WRONLY | os.O_CREAT | os.O_EXCL,
                                   0o600 if target == policy_path else 0o644), 'wb') as out:
                out.write(data)
    subprocess.run(['systemd-analyze', 'verify', '/etc/systemd/system/cmnd-lab-egress.service'], check=True)
    subprocess.run(['systemctl', 'daemon-reload'], check=True)
    subprocess.run(['systemctl', 'enable', '--now', 'cmnd-lab-egress.service'], check=True)
    print(json.dumps({'persistent_egress_enabled': True, 'application_services_started': False,
                      'dependency_units': ['docker', 'apache2', 'cmnd-lab-tomcat'], 'uids': policy['uids']}))


if __name__ == '__main__':
    main()
