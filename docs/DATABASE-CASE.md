# Settings/Channels loading error: database case compatibility

Version 0.7.0 imported packages but native list requests could fail because the
vendor uses `settingPackage`/`channelPackage` while stored tables are named
`settingpackage`/`channelpackage`. Linux MySQL defaults to case-sensitive table
lookup. Version 0.7.1 initializes fresh databases with
`lower_case_table_names=1`, matching the Windows lookup behavior.

This does not lowercase your data, change text collations, rename uploaded files,
replace vendor Java files, reset passwords or send commands to TVs.

## Existing installations

Choose a maintenance window, pause scheduled jobs and stop other administrators
from uploading/editing until this finishes. The repair briefly stops the CMND
web services. Keep an independent VM backup/snapshot as usual.

```sh
sudo cmndctl --updates
# Select/install 0.7.1 (preview channel for evaluation releases).
sudo cmndctl native-database-case
# Review the audit, then explicitly apply:
sudo cmndctl native-database-case --execute
sudo cmndctl native-health --seconds 120
```

Refresh the Settings and Channels tabs; log in again if needed. Existing packages
should display without deleting or uploading them again. The GUI updater and CLI
updater install tooling only; the separate repair command is intentional.

The audit requires the pinned MySQL 5.7 container, its expected private data mount,
and all stored schema/table names already lowercase. It refuses mixed-case names,
collisions, conflicting options or an existing repair configuration. Those cases
need a separately reviewed dump/reload migration; do not force the setting.

The repair retains a root-private cold backup and recovery metadata under
`/var/lib/cmnd-deployment/case-backups/<timestamp>/`. The backup includes the full
MySQL directory and accounts, so treat it as confidential. Do not publish it or
delete it until verification and your normal backup retention policy permit.
The configuration persists in the existing CMND MySQL container across restarts;
new containers created by the 0.7.1 installer receive the equivalent startup flag.

If verification fails, the repair disables its added configuration and attempts
to restart MySQL, but leaves application writers stopped for investigation. Keep
the backup and private deployment log. Do not restore the archive over a running
database or rerun installation over the existing deployment.

## Evidence and limits

- Reproduced original SQL errors on an Ubuntu 24.04 public-0.7.0 test install.
- Applied the guarded repair without deleting/reimporting customer packages;
  existing Settings/Channels counts were preserved.
- Authenticated native HTTP list requests returned 200 and the existing records;
  the operator independently confirmed both lists now display in the browser.
- Read-only static audit covered 3,811 Java classes and 2,672 static web references
  across the Java applications and PHP SmartCMS; no static path-case mismatch or
  case-colliding file was found in that scan.
  Additional mixed-case SQL/entity references are candidates, not all proven bugs:
  Java/Hibernate entity names may correctly be case-sensitive.
- This is not proof of every dynamic filesystem path, CMS workflow, TV operation,
  or a complete fresh 0.7.1 end-to-end installation. See the test results for scope.

The repair follows the distinction in the
[MySQL 5.7 identifier-case documentation](https://dev.mysql.com/doc/refman/5.7/en/identifier-case-sensitivity.html)
between already-lowercase stored names and databases requiring conversion first.
