"""Print stack frames and sanitized startup diagnostics from the lab only."""
from pathlib import Path
import re
import subprocess

pid = subprocess.check_output(['systemctl', 'show', 'cmnd-lab-tomcat', '-p', 'MainPID', '--value'], text=True).strip()
dump = subprocess.run(['nsenter', '-t', pid, '-m', '--', 'runuser', '-u', 'cmnd', '--', 'jcmd', pid, 'Thread.print'], capture_output=True, text=True, timeout=30).stdout
for block in dump.split('\n\n'):
    if any(name in block.split('\n', 1)[0] for name in ('"main"', 'Catalina-utility', 'mysql', 'Hikari')):
        print('\n'.join(line for line in block.splitlines() if line.startswith(('"', '\tat ', '\t- ', '   java.lang.Thread.State'))))
for path in Path('/usr/local/tomcat/logs').glob('*.log'):
    lines = path.read_text(errors='replace').splitlines()
    safe = [line for line in lines if not re.search(r'password|secret|jdbc|account|license|token|key|query|prop|select |insert |update |delete ', line, re.I)]
    print(path.name, '\n'.join(safe[-15:]))
