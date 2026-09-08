# Backup and restore

`cmndctl backup --root <runtime> --output <new.tgz>` creates a non-overwriting filesystem release backup. `cmndctl restore --backup <tgz> --root <clean-root>` validates paths/symlinks and previews; add `--execute` only for an empty destination. It does not back up or restore CMND databases and is not a complete CMND recovery mechanism.

An isolated five-schema import and migration experiment has now run, as described
below. Complete content consistency, encrypted backup, service activation, and
second-instance recovery remain unqualified. Never describe the filesystem
command as full CMND backup.

Windows CMND backup preparation is supported:

```bash
sudo install -m 0600 /dev/null /etc/cmnd/backup.pass
sudo editor /etc/cmnd/backup.pass
cmndctl inspect-windows-backup --backup backupCMND.zip --password-file /etc/cmnd/backup.pass
cmndctl prepare-windows-restore --backup backupCMND.zip --staging /var/lib/cmnd/restore/backup-id --password-file /etc/cmnd/backup.pass
cmndctl prepare-windows-restore --backup backupCMND.zip --staging /var/lib/cmnd/restore/backup-id --password-file /etc/cmnd/backup.pass --execute
```

The command validates exact dump paths and headers for all five databases, including uppercase `SmartInstall`, then performs bounded path-safe extraction. It does not yet execute SQL; `database_import_executed=false` is deliberate until an empty disposable MySQL target and migration path are qualified.

The stricter, version-specific SQL preparer is available for the supplied 7.4.8 layout:

```bash
cmndctl prepare-windows-sql --backup /private/backupCMND_7.4.8.zip --staging /private/new-sql-stage --execute
```

It writes only five SQL dumps and a content-free hash manifest into a new private directory. It preserves quoted SQL values and migration-history text, applies only the verified schema mapping and 11 definer syntax changes, and rejects unsafe scope/privilege constructs. This is static preparation, not an SQL execution sandbox or full content restore. The user approved transfer to the disposable VM, and all five databases subsequently imported into a separate isolated candidate after providing the missing vendor firmware-catalogue table. No backup content enters Git or the installation package.

The VM-only `lab_restore_sql.py` experiment refuses existing targets, uses a separate internal database, restricts each importer to one schema, and keeps restored event scheduling OFF. Never enable restored schedules or connect applications until migrations, inventory, and outbound behavior are reviewed.

## Firmware is uploaded separately

The user confirmed that large firmware packages are deliberately not included in
the CMND backup and are uploaded separately. The supplied backup also omits the
`upg_setting` firmware-catalogue table, although its `group_view` references that
table. This omission is expected for this backup workflow, not evidence that the
room/channel/UI backup is defective.

An empty-schema import must first provide the standard vendor table definition.
Creating that table does not reconstruct excluded firmware records or binaries.
Preserve backed-up firmware IDs; report assignments whose packages are unavailable
and require reviewed re-upload/reassociation before permitting deployment. Never
silently clear assignments or associate a new upload solely by a reused numeric ID.
No firmware flash was performed during qualification.

## Direct 7.4.8 to supplied 7.5.9 comparison

The backup's existing room-ID types, firmware/clone-ID defaults, removed legacy
foreign keys, and final device view are structurally consistent with the newer
application. The device view exposes the same 74 columns in the same order. Ten
pending vendor migrations, 8.8 through 9.7, add two casting tables and additional
fields; they contain no row updates/deletions or table/column drops.

Flyway nevertheless rejects historical checksums for 2.4, 2.13, and 4.5. These
differences are not demonstrated data incompatibilities, but cannot simply be
ignored in an automatic installer. The isolated experiment preserved all 112
original non-checksum history fields, explicitly reconciled the three checksums,
then successfully applied ten migrations to reach 122 successful rows/version9.7
and 67 objects. This is conditional migration evidence, not a complete restored
application acceptance test. Philips' normal startup calls `migrate()`, not
`repair()`, and can log migration failure while appearing to start. Automatic
checksum reconciliation must remain disabled until its exact structural and
data-preservation gates are implemented and independently verified.
