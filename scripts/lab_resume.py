"""Resume the original synthetic lab after verified persistent isolation."""
import argparse
import json
import os
from pathlib import Path
import shutil
import socket
import subprocess
import time

from cmnd_linux.vendor_cms import configure_cms_rewrite_base, prepare_cms_writable_directories


def run(*args, **kwargs):
    return subprocess.run(args, check=True, capture_output=True, **kwargs)


def main():
    parser = argparse.ArgumentParser()
    parser.add_argument('--execute', action='store_true')
    args = parser.parse_args()
    if not args.execute or os.geteuid() or socket.gethostname() != 'cmnd-qualification':
        raise SystemExit('Requires dedicated lab root and --execute')
    run('systemctl', 'is-active', '--quiet', 'cmnd-lab-egress')
    # Revalidate actual kernel policy, not merely an earlier oneshot status.
    run('python3', '/usr/local/lib/cmnd-lab/egress.py', '--policy', '/etc/cmnd/lab-egress.json', '--execute')
    network = json.loads(run('docker', 'network', 'inspect', 'cmnd-lab-db').stdout)[0]
    if not network['Internal']:
        raise SystemExit('The synthetic database/TV network is not internal')
    expected = {
        'cmnd-lab-mysql': ('mysql@sha256:4bc6bc963e6d8443453676cae56536f4b8156d78bae03c0145cbe47c2aad73bb', 'cmnd-lab-db', '172.30.44.2'),
        'cmnd-lab-tv-internal': ('python@sha256:78387bc3881b8273120a12ebe6c1ab22b018ccc2c9adf565ae1ac9b536e184ea', 'cmnd-lab-db', '172.30.44.4'),
        'cmnd-lab-php': ('sha256:ab1537bf1305c30c0701e4b938b33aa475d022d6dbbc73aaab022cff23a1c27f', 'host', None),
    }
    for name, (image, mode, address) in expected.items():
        item = json.loads(run('docker', 'inspect', name).stdout)[0]
        if item['Config']['Image'] != image or item['HostConfig']['NetworkMode'] != mode:
            raise SystemExit('Unexpected lab container image or network: ' + name)
        if address:
            assigned = item['NetworkSettings']['Networks']['cmnd-lab-db']['IPAMConfig']['IPv4Address']
            if assigned != address:
                raise SystemExit('Unexpected synthetic endpoint address')
        if name == 'cmnd-lab-php' and (item['Config']['User'] != '33:33' or not item['HostConfig']['ReadonlyRootfs']):
            raise SystemExit('PHP isolation differs from the reviewed profile')
    cms = Path('/opt/cmnd-lab-cms/SmartCMS')
    configure_cms_rewrite_base(cms)
    for directory in prepare_cms_writable_directories(cms):
        os.chown(directory, 33, 33)
    apache_config = Path('/etc/apache2/sites-available/cmnd-lab.conf')
    content = apache_config.read_text()
    correct = f'DocumentRoot {cms}\n'
    if correct not in content:
        anchor = f'ServerName 127.0.0.1\nAlias /SmartCMS {cms}\n'
        if content.count(anchor) != 2 or 'DocumentRoot' in content:
            raise SystemExit('Unexpected Apache configuration; preserve it for review')
        backup = Path('/var/lib/cmnd-lab/apache-before-documentroot.conf')
        if backup.exists():
            raise SystemExit('Previous Apache configuration checkpoint exists')
        shutil.copyfile(apache_config, backup)
        backup.chmod(0o600)
        apache_config.write_text(content.replace(anchor, f'ServerName 127.0.0.1\n{correct}Alias /SmartCMS {cms}\n'))
        try:
            run('apache2ctl', 'configtest')
        except Exception:
            shutil.copyfile(backup, apache_config)
            raise
    else:
        run('apache2ctl', 'configtest')
    run('docker', 'start', 'cmnd-lab-mysql')
    client = ['docker', 'exec', '-i', 'cmnd-lab-mysql', 'mysql', '--defaults-extra-file=/run/secrets/client.cnf']
    for _ in range(60):
        if subprocess.run(client, input=b'SELECT 1;', capture_output=True).returncode == 0:
            break
        time.sleep(0.5)
    else:
        raise SystemExit('Synthetic database did not become ready; CMND remains stopped')
    run('docker', 'start', 'cmnd-lab-tv-internal', 'cmnd-lab-php')
    # The container drops all capabilities, including CHOWN. Create this as the
    # actual PHP UID instead of trying to create/chown it as container root.
    run('docker', 'exec', 'cmnd-lab-php', 'install', '-d', '-m', '0700', '/tmp/cmnd-lab-pgt')
    run('systemctl', 'start', 'cmnd-lab-tomcat', 'apache2')
    print(json.dumps({'synthetic_runtime_started': True, 'customer_restore_container_started': False,
                      'cms_document_root_corrected': True, 'readiness_verified': False}))


if __name__ == '__main__':
    main()
