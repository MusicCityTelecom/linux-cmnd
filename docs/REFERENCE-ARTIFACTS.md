# Reference-only CMND and clone artifacts

Operator-owned customer archives were inspected in place. They are not copied into source, the installer, or the Debian package. Archive-specific names, hashes, sizes and customer labels are retained privately, not in release documentation.

The reference CMND 7.4.8 server backup contains all five expected database dumps with valid headers: `cas`, `tpvision`, `smartcms`, `smartcontroldb`, and uppercase `SmartInstall`. This confirms its classification as a server backup, not a TV clone. Firmware payloads are separately uploaded and intentionally absent. Structural classification does not establish complete production restore compatibility.

The remaining archives are unencrypted TPM191HN clone/content subsets, not CMND server backups:

| Reference category | Classification |
|---|---|
| UI/welcome | UI images/XML/JSON and welcome content |
| Apps | application metadata subset |
| Channels | channel database/XML subset |
| Settings | settings XML/JSON subset |
| Combined clone | multi-component TPM191HN clone trees |

These packages remain useful as model-specific structural references and future acceptance inputs. Their presence does not authorize deployment to a TV.
