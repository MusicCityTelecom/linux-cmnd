"""Fresh, isolated vendor startup experiment. NOT a production installer.

Run as root only in the NoCloud VM created by lab_seed.py. Never accepts a
backup or existing database. Vendor output and generated secrets stay in VM.
"""
from pathlib import Path
import hashlib
import json
import os
import pwd
import secrets
import shutil
import socket
import subprocess
import tarfile
import time

from cmnd_linux.artifacts import safe_extract_zip
from cmnd_linux.vendor_config import VendorRenderConfig, REQUIRED_SECRETS, render_vendor_wars

BASE = Path('/var/lib/cmnd-lab')
SOURCE = Path('/home/cmndlab/input/vendor')
TOMCAT_VERSION = '9.0.121'
TOMCAT_HASH = '16494dd4745f808d3c506807b5275521fd71044d976f441d18eeeab0f5a38bc1b5344ca395292f6f26eb7612cd8c8e746d01ccdfb29893d394052d9f4b1f4c11'
MYSQL = 'mysql@sha256:4bc6bc963e6d8443453676cae56536f4b8156d78bae03c0145cbe47c2aad73bb'


def run(*args, data=None):
    with (BASE / 'bootstrap.log').open('ab') as log:
        result = subprocess.run(args, input=data, stdout=log, stderr=log)
    if result.returncode:
        raise RuntimeError(f'{args[0]} failed; inspect private {BASE}/bootstrap.log')


def write(path, text, mode=0o600):
    with os.fdopen(os.open(path, os.O_WRONLY | os.O_CREAT | os.O_EXCL, mode), 'w') as out:
        out.write(text)


def main():
    if os.geteuid() or socket.gethostname() != 'cmnd-qualification' or not Path('/var/lib/cmnd-lab-bootstrap-complete').is_file():
        raise SystemExit('Refusing: requires the dedicated disposable lab VM as root')
    for path in (BASE, Path('/opt/Philips'), Path('/usr/local/tomcat')):
        if path.exists() or path.is_symlink():
            raise SystemExit(f'Refusing existing state: {path}; preserve it for diagnosis')
    if subprocess.run(['docker', 'container', 'inspect', 'cmnd-lab-mysql'], capture_output=True).returncode == 0:
        raise SystemExit('Refusing existing lab database container')
    BASE.mkdir(mode=0o700)
    os.umask(0o077)
    values = {name: secrets.token_hex(24) for name in REQUIRED_SECRETS}
    values['tpvision_db_password'] = secrets.token_hex(24)
    root_password = secrets.token_hex(32)
    write(BASE / 'secrets.json', json.dumps(values))
    write(BASE / 'root-password', root_password)
    write(BASE / 'client.cnf', '[client]\nuser=root\npassword=' + root_password + '\n')
    run('useradd', '--system', '--home-dir', '/var/lib/cmnd', '--shell', '/usr/sbin/nologin', 'cmnd')
    uid = str(pwd.getpwnam('cmnd').pw_uid)
    # Only the dedicated vendor UID is restricted; no Windows host changes.
    for binary, loopback in (('iptables', '127.0.0.0/8'), ('ip6tables', '::1/128')):
        run(binary, '-N', 'CMND_LAB')
        run(binary, '-A', 'OUTPUT', '-m', 'owner', '--uid-owner', uid, '-j', 'CMND_LAB')
        run(binary, '-A', 'CMND_LAB', '-m', 'conntrack', '--ctstate', 'ESTABLISHED,RELATED', '-j', 'ACCEPT')
        run(binary, '-A', 'CMND_LAB', '-d', loopback, '-j', 'ACCEPT')
        if binary == 'iptables':
            run(binary, '-A', 'CMND_LAB', '-d', '172.30.44.2/32', '-p', 'tcp', '--dport', '3306', '-j', 'ACCEPT')
        run(binary, '-A', 'CMND_LAB', '-j', 'REJECT')
    run('docker', 'network', 'create', '--internal', '--subnet', '172.30.44.0/24', 'cmnd-lab-db')
    run('docker', 'run', '-d', '--name', 'cmnd-lab-mysql', '--network', 'cmnd-lab-db', '--ip', '172.30.44.2',
        '--mount', f'type=bind,src={BASE}/root-password,dst=/run/secrets/root-password,readonly',
        '--mount', f'type=bind,src={BASE}/client.cnf,dst=/run/secrets/client.cnf,readonly',
        '--mount', 'type=volume,src=cmnd-lab-db-data,dst=/var/lib/mysql',
        '-e', 'MYSQL_ROOT_PASSWORD_FILE=/run/secrets/root-password', MYSQL,
        '--event-scheduler=ON', '--local-infile=0', '--sql-mode=NO_ENGINE_SUBSTITUTION')
    client = ['docker', 'exec', '-i', 'cmnd-lab-mysql', 'mysql', '--defaults-extra-file=/run/secrets/client.cnf', '--binary-mode=1']
    for _ in range(60):
        if subprocess.run(client, input=b'SELECT 1;', capture_output=True).returncode == 0:
            break
        time.sleep(1)
    else:
        raise RuntimeError('MySQL did not become ready')
    databases = [('smartinstall', 'siuser', 'smartinstall', (2, 3)),
                 ('cas', 'cas', 'cas', (2,)), ('tpvision', 'tpvision', 'tpvision', tuple(range(2, 14))),
                 ('smartcontroldb', 'smartcontrol', 'smartcontrol', (2,)), ('smartcms', 'smartcms', 'smartcms', (2, 3))]
    for db, user, folder, numbers in databases:
        password_key = 'smartcontrol_db_password' if db == 'smartcontroldb' else db + '_db_password'
        sql = (f'CREATE DATABASE `{db}` CHARACTER SET utf8 COLLATE utf8_general_ci;'
               f"CREATE USER '{user}'@'%' IDENTIFIED BY '{values[password_key]}';"
               f"GRANT ALL ON `{db}`.* TO '{user}'@'%';")
        run(*client, data=sql.encode())
        for number in numbers:
            run(*client, db, data=(SOURCE / 'SQLScripts' / folder / f'sql_{number}.sql').read_bytes())
        print('Initialized fresh schema:', db, flush=True)
    tomcat_archive = BASE / 'tomcat.tar.gz'
    run('curl', '--fail', '--location', '--proto', '=https', '-o', str(tomcat_archive),
        f'https://archive.apache.org/dist/tomcat/tomcat-9/v{TOMCAT_VERSION}/bin/apache-tomcat-{TOMCAT_VERSION}.tar.gz')
    if hashlib.sha512(tomcat_archive.read_bytes()).hexdigest() != TOMCAT_HASH:
        raise RuntimeError('Tomcat checksum mismatch')
    with tarfile.open(tomcat_archive) as archive:
        archive.extractall('/usr/local', filter='data')
    tomcat = Path('/usr/local') / f'apache-tomcat-{TOMCAT_VERSION}'
    Path('/usr/local/tomcat').symlink_to(tomcat)
    stage = BASE / 'rendered'
    ports = dict(tomcat_http=8080, tomcat_https=8443, apache_http=8082, apache_https=8444, database=3306)
    render_vendor_wars(SOURCE, stage, VendorRenderConfig('127.0.0.1', ports, values, '172.30.44.2'))
    for war in stage.iterdir():
        shutil.copy2(war, tomcat / 'webapps' / war.name)
    shutil.copy2(SOURCE / 'Tomcat 9.0/lib/reload.jar', tomcat / 'lib/reload.jar')
    finish_startup()


def finish_startup():
    if os.geteuid() or socket.gethostname() != 'cmnd-qualification' or not BASE.is_dir():
        raise SystemExit('Requires the initialized dedicated lab VM')
    tomcat = Path('/usr/local') / f'apache-tomcat-{TOMCAT_VERSION}'
    # Vendor channel templates contain highly compressible zero-filled files.
    # Keep the expanded-byte/count caps; only this known private payload gets
    # the higher ratio budget. This does not change backup extraction policy.
    safe_extract_zip(SOURCE / 'Philips.zip', '/opt/Philips', max_ratio=2000)
    for path in ('/var/lib/cmnd', '/var/lib/cmnd/smartcontrol', '/var/log/cmnd'):
        Path(path).mkdir(parents=True, exist_ok=True)
        run('chown', 'cmnd:cmnd', path)
    run('chown', '-R', 'cmnd:cmnd', str(tomcat), '/opt/Philips')
    # This first startup test deliberately has no HTTPS or Apache claim.
    write('/etc/systemd/system/cmnd-lab-tomcat.service', '''[Unit]
Description=Isolated disposable CMND vendor startup test
After=docker.service
[Service]
User=cmnd
Group=cmnd
Environment=JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
Environment="CATALINA_OPTS=-Xms512m -Xmx4096m -Djava.awt.headless=true"
WorkingDirectory=/usr/local/tomcat
ExecStart=/usr/local/tomcat/bin/catalina.sh run
UMask=0077
NoNewPrivileges=true
PrivateTmp=true
''', 0o644)
    run('systemctl', 'daemon-reload')
    run('systemctl', 'start', 'cmnd-lab-tomcat')
    print('Vendor startup dispatched. Health/login/migration validation still required.', flush=True)


if __name__ == '__main__':
    main()
