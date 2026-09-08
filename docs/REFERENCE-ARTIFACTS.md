# Reference-only CMND and clone artifacts

These operator-owned customer archives were inspected in place on 2026-09-07. They are not copied into source, the installer, or the Debian package.

`backupCMND_7.4.8_20240101_1625.zip` is a complete unencrypted CMND server backup: 22,999,129 bytes, 230 entries, SHA-256 `9285176231c01fbb1a866ba3dcf9d67a59d190abbd93f9d9cf2745926f8dbe30`. It contains all five exact database dumps and valid headers: `cas`, `tpvision`, `smartcms`, `smartcontroldb`, and uppercase `SmartInstall`. Metadata reports CMND 7.4.8, BackupTool 1.1.5, Java 17.0.1, Tomcat 9.0.73, MySQL 5.7.40, and Apache 2.4.55.

The remaining archives are unencrypted TPM191HN clone/content subsets, not CMND server backups:

| Reference category | Archives | Classification |
|---|---|---|
| Room UI/welcome | `UI-WiFi Rooms`, `Welcome-WiFi Rooms`, `Default Rooms ui`, `Default Rooms welcome` | UI images/XML/JSON and welcome content |
| Common-area UI/welcome | `Default Common Areas ui`, `Deafult Common Areas welcome` | UI/welcome content; source spelling preserved |
| Apps | `Default Rooms Apps`, `Default Common Areas Apps` | application metadata subset |
| Channels | `Default Rooms Channels`, `Default Common Areas Channels` | channel database/XML subset |
| Settings | `Default Rooms Settings`, `Default Common Areas Settings` | settings XML/JSON subset |
| Combined clone | `Default Rooms Clone`, `Default Common Areas Clone` | multi-component TPM191HN clone trees |

These packages remain useful as model-specific structural references and future acceptance inputs. Their presence does not authorize deployment to a TV.
