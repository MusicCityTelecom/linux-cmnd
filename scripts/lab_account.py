"""Provision a unique temporary test account in the disposable fresh CAS DB."""
import hashlib
import json
import os
from pathlib import Path
import pwd
import secrets
import socket
import subprocess

if os.geteuid() or socket.gethostname() != 'cmnd-qualification':
    raise SystemExit('Requires the dedicated lab VM')
destination = Path('/home/cmndlab/browser-credentials.json')
if destination.exists():
    raise SystemExit('Test account file already exists; do not create duplicates')
username = 'linux-smoke-' + secrets.token_hex(4)
password = secrets.token_hex(24)
digest = hashlib.md5(password.encode()).hexdigest()  # Vendor CAS encoder, not a security recommendation.
query = f"INSERT INTO cas.users (username,password,role) VALUES ('{username}','{digest}','ADMIN');"
client = ['docker', 'exec', '-i', 'cmnd-lab-mysql', 'mysql', '--defaults-extra-file=/run/secrets/client.cnf', '--binary-mode=1']
result = subprocess.run(client, input=query.encode(), capture_output=True)
if result.returncode:
    raise SystemExit('Test account insertion failed; private DB diagnostic required')
with os.fdopen(os.open(destination, os.O_WRONLY | os.O_CREAT | os.O_EXCL, 0o600), 'w') as output:
    json.dump({'username': username, 'password': password}, output)
owner = pwd.getpwnam('cmndlab')
os.chown(destination, owner.pw_uid, owner.pw_gid)
print('Unique temporary CAS test account created; credential remains in guest mode-0600 file.')
