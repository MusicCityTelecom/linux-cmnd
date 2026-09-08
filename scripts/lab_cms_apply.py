"""Apply only the reviewed lab CMS settings; no shell evaluation or web endpoint."""
import os
import socket
import subprocess
import sys
from pathlib import Path

if os.geteuid() or socket.gethostname() != 'cmnd-qualification' or '--execute' not in sys.argv:
    raise SystemExit('Requires dedicated lab VM root and --execute')
subprocess.run(['docker', 'exec', '-i', '-e', 'CMND_LAB_EXECUTE=1', 'cmnd-lab-php', 'php'],
    input=Path(__file__).with_name('lab_cms_config.php').read_bytes(), check=True)
