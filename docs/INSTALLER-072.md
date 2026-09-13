# v0.7.2 installer and upgrade notes

This maintenance release is based on v0.7.1 plus its early unsupported-Python
guard. It does not contain or modify the separate native product or recovered
vendor source. Release publication depends on the qualification evidence in
[the v0.7.2 test record](QUALIFICATION-072.md).

## Requirements and capacity

Ubuntu 24.04 amd64, at least 6 GiB installed RAM and a 40 GiB target disk are the
recommended evaluation environment. Debian 12/13 are accepted by the installer;
their runtime qualification is separate. Debian 13 needs an operator-supplied
Java 17 runtime. Ubuntu's installer installs Java 17 and other application
dependencies automatically. The initial download requires Python 3.11+, curl
and ca-certificates; OpenSSH is optional, only for remote administration.

The default single-filesystem budget before downloading is 27.8125 GiB free:

| Allowance | GiB | Reason |
| --- | ---: | --- |
| Downloads/staging | 2 | Vendor ZIP, extracted inputs, tooling |
| Cache | 0.5 | Tomcat archive and rollback installer |
| Docker | 4 | MySQL/PHP images and transient build layers |
| Runtime | 2 | Installed applications |
| Database/candidate | 1.5 | Initialization and candidate staging |
| OS dependencies | 1.5 | Java, Apache and prerequisites |
| Temporary space | 0.5 | Temporary files |
| Upload reserve | 15.8125 | Two configured 8,096 MiB copies |

This is a conservative peak budget, not final disk consumption. The prepared
deployment phase rechecks 25.8125 GiB after download/dependency preparation. This
intentionally retains a safety margin rather than deducting every cached image.
Checks group the actual destination paths by filesystem; mounting `/opt`, `/var`
or `/tmp` separately does not let one filesystem's free space cover another.
Docker uses its default `/var/lib/docker` data root for this budget; a custom
Docker data-root needs separately checked equivalent capacity before installation.
The reviewed `cms.upload_limit_mb` changes both upload allowances (range 1–8096).
Do not reduce it just to hide a full filesystem if large uploads are needed.

Check `df -h / /var/lib /opt /var/tmp`. Growing a virtual disk does not itself
grow a guest filesystem or LVM logical volume. No installer step repartitions,
extends a volume, reboots or powers off the machine. A pending
`/var/run/reboot-required` is reported prominently; arrange any reboot yourself.

## Completion and diagnostics

The last fresh-install output is a centralized summary containing the actual
initial administrator password and configured URLs. It is not the Windows
`admin/tpvision` default: this compatibility release preserves v0.7.1's randomly
generated initial password. Store it privately. Initial CAS and Linux management
credentials match; they can later be changed independently.

The receipt is written only after service startup, HTTPS/migration readiness,
systemd enablement, deployment.json and qualification.json succeed. A failed
deployment never prints a success credential banner. The receipt records those
installation checks; `sudo cmndctl native-health --seconds 120` performs a new
live readiness check. Hardware parity/activation/RF are not inferred from HTTP.

| Path/command | Purpose |
| --- | --- |
| `sudo cmndctl install-summary` or `show-login` | Local root-only display, no changes |
| `/var/lib/cmnd-deployment/initial-admin.json` | Root-only original credential, newline-terminated on new installs |
| `/var/lib/cmnd-deployment/install-summary.txt` | Root-only fresh-install receipt, contains initial password |
| `/var/lib/cmnd-deployment/qualification.json` | Detailed installation evidence |
| `/etc/cmnd/deployment.json` | Managed deployment marker |
| `/var/lib/cmnd-deployment/deployment.log` | Private stage log; final readiness failure reason |
| `/var/log/linux-cmnd-install-*.log` | Private preparation stage transcript, unique per attempt |
| `/var/cache/linux-cmnd/bootstrap/php-build.log` | Private PHP build details |
| `/etc/cmnd/cmnd.toml` | Reviewed ports and network configuration |
| `/etc/cmnd/tls/ca.crt` | Public CA to trust on operator workstations |

During startup a container can be not running, a TCP socket not listening, or
migration-history tables absent. These retries are expected. Progress remains
visible; only a deadline failure writes the final failed probe reason as ERROR.
Do not erase v0.7.1's earlier log entries: they are historical evidence.

For systemd details use `sudo journalctl -u cmnd-tomcat -u cmnd-mysql -u cmnd-php
--since today --no-pager`. Never post raw logs/receipts without redacting passwords,
device/customer data and private keys. The final console password is intentional;
do not screen-record it for a public bug report.

See the [README v0.7.1 instructions](../README.md#already-installed-v071) for safe
credential retrieval and upgrade. A package-only `dpkg -i` is not the supported
application-upgrade workflow: use the verified updater with its backups and
readiness/rollback handling. A completed install is never adopted by a fresh
installer; a partial install is never silently destroyed to allow another attempt.
