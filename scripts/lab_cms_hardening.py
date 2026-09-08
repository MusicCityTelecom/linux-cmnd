"""Restrict sensitive CMS file types on the disposable lab Apache instance."""
import os
import socket
from lab_vendor_runtime import run, write

if os.geteuid() or socket.gethostname() != 'cmnd-qualification':
    raise SystemExit('Requires the dedicated lab VM')
write('/etc/apache2/conf-available/cmnd-lab-deny-secrets.conf',
      '<FilesMatch "(?i)(^settings\\.php$|\\.(properties|p12|pem|key|sql)$)">\nRequire all denied\n</FilesMatch>\n', 0o644)
run('a2enconf', 'cmnd-lab-deny-secrets')
run('apache2ctl', 'configtest')
run('systemctl', 'reload', 'apache2')
print('Sensitive CMS file types denied over HTTP; local PHP config reads preserved.')
