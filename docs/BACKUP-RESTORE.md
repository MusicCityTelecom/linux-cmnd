# Backup and restore

`cmndctl backup --root <runtime> --output <new.tgz>` creates a non-overwriting filesystem release backup. `cmndctl restore --backup <tgz> --root <clean-root>` validates paths/symlinks and previews; add `--execute` only for an empty destination. It does not back up or restore CMND databases and is not a complete CMND recovery mechanism.

Database-aware backup/restore, five-schema verification, content consistency, encryption, and second-instance recovery are NOT_RUN. Never describe the filesystem command as full CMND backup.
