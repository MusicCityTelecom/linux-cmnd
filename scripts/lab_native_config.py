"""Syntax-check native configuration in the dedicated VM without activation."""
from dataclasses import replace
from io import BytesIO
import json
import os
from pathlib import Path
import socket
import subprocess
import tarfile

from cmnd_linux.config import Config
from cmnd_linux.native_config import NativeLayout, stage_native_config


def main():
    if os.geteuid() or socket.gethostname() != 'cmnd-qualification':
        raise SystemExit('Requires root in the dedicated qualification VM')
    base = Path('/var/lib/cmnd-lab')
    secrets = json.loads((base / 'secrets.json').read_text())
    output = base / 'native-config-check-003'
    config = Config('lab', '0.0.0.0', 'http://127.0.0.1:8080', ('127.0.0.0/8',), ())
    layout = replace(NativeLayout(), tomcat='/usr/local/tomcat', cms='/opt/cmnd-lab-cms/SmartCMS',
                     run='/var/run/apache2', certificate=str(base / 'lab.crt'), key=str(base / 'lab.key'),
                     php_user='www-data', pgt='/tmp/cmnd-lab-pgt')
    result = stage_native_config(config, secrets, output, execute=True, layout=layout)
    apache = subprocess.run(['apache2', '-t', '-f', str(output / 'apache.conf')], capture_output=True)
    # PHP 5.6 FPM does not reliably parse /dev/stdin as its INI source. Copy the
    # non-secret candidate into the existing private tmpfs, never its active config.
    payload = BytesIO()
    data = (output / 'php-fpm.conf').read_bytes()
    with tarfile.open(fileobj=payload, mode='w') as archive:
        entry = tarfile.TarInfo('native-config-check-003.conf')
        entry.mode = 0o600
        entry.size = len(data)
        archive.addfile(entry, BytesIO(data))
    subprocess.run(['docker', 'exec', '-i', 'cmnd-lab-php', 'tar', '-x', '-C', '/tmp', '--no-same-owner'],
                   input=payload.getvalue(), check=True, capture_output=True)
    php = subprocess.run(['docker', 'exec', 'cmnd-lab-php', 'php-fpm', '-tt', '-y',
                          '/tmp/native-config-check-003.conf'], capture_output=True)
    for name, run in (('apache', apache), ('php-fpm', php)):
        with os.fdopen(os.open(output / (name + '-syntax.log'), os.O_WRONLY | os.O_CREAT | os.O_EXCL, 0o600), 'wb') as log:
            log.write(run.stdout + run.stderr)
        result[name + '_syntax_pass'] = run.returncode == 0
    with os.fdopen(os.open(output / 'qualification.json', os.O_WRONLY | os.O_CREAT | os.O_EXCL, 0o600), 'w') as out:
        json.dump(result, out, indent=2)
    print(json.dumps(result, indent=2))
    if apache.returncode or php.returncode:
        raise SystemExit(1)


if __name__ == '__main__':
    main()
