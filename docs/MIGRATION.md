# Migration

The 7.5.1 backup maps five dumps: `cas/cas.sql→cas`, `CMS/tpvision.sql→tpvision`, `smartcms/smartcms.sql→smartcms`, `smartcontrol/smartcontroldb.sql→smartcontroldb`, and `SmartInstall/SmartInstall.sql→SmartInstall`.

Do not restore them with the current CLI. A qualified restore must prompt for archive secrets without command-line exposure; inspect executable SQL; initialize an empty disposable MySQL instance with the chosen case policy; import with explicit mappings and without `--force`; preserve Flyway history; compare objects/rows/content; then test a second clean recovery. Installer SQL includes drops, grants, a definer, and numerically ordered scripts. The 7.5.1→7.5.9 migration is NOT_RUN.
