# Backup and restore

`cmndctl backup --root <runtime> --output <new.tgz>` creates a non-overwriting filesystem release backup. `cmndctl restore --backup <tgz> --root <clean-root>` validates paths/symlinks and previews; add `--execute` only for an empty destination. It does not back up or restore CMND databases and is not a complete CMND recovery mechanism.

Database-aware backup/restore, five-schema verification, content consistency, encryption, and second-instance recovery are NOT_RUN. Never describe the filesystem command as full CMND backup.

Windows CMND backup preparation is supported:

```bash
sudo install -m 0600 /dev/null /etc/cmnd/backup.pass
sudo editor /etc/cmnd/backup.pass
cmndctl inspect-windows-backup --backup backupCMND.zip --password-file /etc/cmnd/backup.pass
cmndctl prepare-windows-restore --backup backupCMND.zip --staging /var/lib/cmnd/restore/backup-id --password-file /etc/cmnd/backup.pass
cmndctl prepare-windows-restore --backup backupCMND.zip --staging /var/lib/cmnd/restore/backup-id --password-file /etc/cmnd/backup.pass --execute
```

The command validates exact dump paths and headers for all five databases, including uppercase `SmartInstall`, then performs bounded path-safe extraction. It does not yet execute SQL; `database_import_executed=false` is deliberate until an empty disposable MySQL target and migration path are qualified.
