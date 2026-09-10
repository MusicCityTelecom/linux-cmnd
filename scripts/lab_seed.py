"""Generate an ignored NoCloud seed ISO for the disposable Ubuntu VM.

Requires pycdlib. Only the caller's generated public key enters the ISO.
"""
from pathlib import Path
from io import BytesIO
import argparse
import re
import pycdlib


def build(directory: Path, public_key: Path, hostname: str = 'cmnd-qualification'):
    if not re.fullmatch(r'cmnd-[a-z0-9-]{1,48}', hostname):
        raise ValueError('seed hostname must be a safe CMND lab name')
    key = public_key.read_text().strip()
    if not key.startswith("ssh-ed25519 "):
        raise ValueError("expected the lab's generated ed25519 public key")
    user_data = f"""#cloud-config
hostname: {hostname}
manage_etc_hosts: true
ssh_pwauth: false
users:
  - name: cmndlab
    sudo: ALL=(ALL) NOPASSWD:ALL
    groups: [sudo]
    shell: /bin/bash
    lock_passwd: true
    ssh_authorized_keys:
      - {key}
package_update: true
packages:
  - openjdk-17-jdk-headless
  - docker.io
  - apache2
  - php
  - php-mysql
  - php-xml
  - php-gd
  - php-curl
  - php-mbstring
  - php-zip
  - php-intl
  - unzip
  - jq
  - python3-venv
runcmd:
  - [systemctl, disable, --now, apache2]
  - [systemctl, enable, --now, docker]
  - [touch, /var/lib/cmnd-lab-bootstrap-complete]
"""
    metadata = f'instance-id: {hostname}\nlocal-hostname: {hostname}\n'
    directory.mkdir(parents=True, exist_ok=True)
    iso = pycdlib.PyCdlib()
    iso.new(interchange_level=3, joliet=3, rock_ridge="1.09", vol_ident="cidata")
    for name, content in (("user-data", user_data), ("meta-data", metadata)):
        data = content.encode()
        iso.add_fp(BytesIO(data), len(data), iso_path='/' + name.upper().replace('-', '_') + ';1',
                   rr_name=name, joliet_path='/' + name)
    iso.write(str(directory / 'seed.iso'))
    iso.close()


if __name__ == '__main__':
    parser = argparse.ArgumentParser()
    parser.add_argument('--directory', type=Path, required=True)
    parser.add_argument('--public-key', type=Path, required=True)
    parser.add_argument('--hostname', default='cmnd-qualification')
    args = parser.parse_args()
    build(args.directory, args.public_key, args.hostname)
