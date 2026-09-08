"""Run operator-supplied SmartCMS in the isolated disposable lab, never production."""
from pathlib import Path
import json
import os
import shutil
import socket
from lab_vendor_runtime import BASE, SOURCE, run, write
from cmnd_linux.vendor_cms import stage_smartcms
from cmnd_linux.vendor_config import _props


def main():
    if os.geteuid() or socket.gethostname() != 'cmnd-qualification':
        raise SystemExit('Requires the dedicated lab VM')
    values = json.loads((BASE / 'secrets.json').read_text())
    stage = stage_smartcms(SOURCE / 'SmartCMS.zip', Path('/opt/cmnd-lab-cms'),
        db_host='172.30.44.2', db_port=3306, db_user='tpvision', db_password=values['tpvision_db_password'])
    properties = stage.root / 'sites/default/config.properties'
    original = properties.read_bytes()
    existing = {line.split('=', 1)[0].strip() for line in original.decode().splitlines() if '=' in line and not line.lstrip().startswith('#')}
    candidates = {'server.name': '127.0.0.1', 'cms.name': '127.0.0.1', 'cas.name': '127.0.0.1', 'published.name': '127.0.0.1',
        'server.port': '8080', 'cms.port': '8082', 'cas.port': '8080', 'published.port': '8080',
        'mysql.ip': '172.30.44.2', 'mysql.port': '3306', 'security.pwd': values['application_security_password'],
        'cert.ca.password': values['cert_ca_password'], 'survey.email.smtp.account': '', 'survey.email.smtp.pwd': '',
        'cloud.server.auth.clientid': '', 'cloud.server.auth.clientsecret': ''}
    properties.write_bytes(_props(original, {key: value for key, value in candidates.items() if key in existing}))
    for path in stage.root.rglob('*'):
        path.chmod(0o755 if path.is_dir() else 0o644)
    stage.root.parent.chmod(0o755)
    stage.root.chmod(0o755)
    for protected in (stage.settings, properties):
        protected.chmod(0o640)
        run('chown', 'root:www-data', str(protected))
    run('chown', '-R', 'www-data:www-data', str(stage.writable_files))
    # PHP runs as UID 33 even with guest host networking. Its only new outbound
    # connections can be guest loopback or the one isolated database endpoint.
    for firewall in ('iptables', 'ip6tables'):
        run(firewall, '-A', 'OUTPUT', '-m', 'owner', '--uid-owner', '33', '-j', 'CMND_LAB')
    write(BASE / 'fpm.conf', '[global]\ndaemonize = no\nerror_log = /proc/self/fd/2\n[www]\nlisten = 127.0.0.1:9000\nuser = www-data\ngroup = www-data\npm = dynamic\npm.max_children = 5\npm.start_servers = 1\npm.min_spare_servers = 1\npm.max_spare_servers = 2\ncatch_workers_output = yes\n', 0o644)
    run('docker', 'run', '-d', '--name', 'cmnd-lab-php', '--network', 'host', '--user', '33:33', '--read-only',
        '--cap-drop', 'ALL', '--security-opt', 'no-new-privileges', '--tmpfs', '/tmp:rw,nosuid,noexec,size=128m',
        '--mount', f'type=bind,src={stage.root},dst={stage.root},readonly',
        '--mount', f'type=bind,src={stage.writable_files},dst={stage.writable_files}',
        '--mount', f'type=bind,src={BASE}/fpm.conf,dst=/usr/local/etc/php-fpm.conf,readonly',
        '--mount', 'type=bind,src=/etc/ssl/certs,dst=/etc/ssl/certs,readonly',
        'cmnd-lab-php:5.6')
    shutil.copy2('/etc/apache2/ports.conf', BASE / 'apache-ports.original')
    Path('/etc/apache2/ports.conf').write_text('Listen 8082\nListen 8444\n')
    # Public HTTP and HTTPS remain VM NAT localhost forwards only.
    configuration = ''
    for port in (8082, 8444):
        configuration += f'''<VirtualHost *:{port}>
ServerName 127.0.0.1
DocumentRoot {stage.root}
Alias /SmartCMS {stage.root}
<Directory {stage.root}>
    Require all granted
    AllowOverride All
    Options FollowSymLinks
    DirectoryIndex index.php
    <FilesMatch "(?i)(^settings\\.php$|\\.(properties|p12|pem|key|sql)$)">
        Require all denied
    </FilesMatch>
    <FilesMatch "\\.php$">
        SetHandler "proxy:fcgi://127.0.0.1:9000"
    </FilesMatch>
</Directory>
'''
        if port == 8444:
            configuration += f'SSLEngine on\nSSLCertificateFile {BASE}/lab.crt\nSSLCertificateKeyFile {BASE}/lab.key\n'
        configuration += '</VirtualHost>\n'
    write('/etc/apache2/sites-available/cmnd-lab.conf', configuration, 0o644)
    run('a2dissite', '000-default')
    run('a2enmod', 'proxy_fcgi', 'rewrite', 'headers', 'expires', 'ssl')
    run('a2ensite', 'cmnd-lab')
    run('apache2ctl', 'configtest')
    run('systemctl', 'start', 'apache2')
    print('SmartCMS Apache/PHP started; browser and extension qualification remains.')


if __name__ == '__main__':
    main()
