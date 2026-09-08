"""Content-minimized diagnostics for the disposable lab CMS, with secret redaction."""
import json
import os
import socket
from pathlib import Path
from urllib.request import urlopen
from urllib.error import HTTPError

if os.geteuid() or socket.gethostname() != 'cmnd-qualification':
    raise SystemExit('Requires the dedicated lab VM')
values = json.loads(Path('/var/lib/cmnd-lab/secrets.json').read_text()).values()
for suffix in ('', 'sites/default/settings.php', 'sites/default/config.properties'):
    try:
        with urlopen('https://127.0.0.1:8444/SmartCMS/' + suffix, timeout=15) as response:
            status = response.status
            content = response.read(512).decode(errors='replace') if not suffix else ''
    except HTTPError as error:
        status, content = error.code, ''
    for value in values:
        if value:
            content = content.replace(value, '[REDACTED]')
    print(json.dumps({'path': suffix or '/', 'status': status, 'diagnostic': content}))
