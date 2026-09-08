# Known limitations

- Real Linux Java runtime, fresh migrations, browser CAS/CMS login, synthetic TV discovery/import, native Standby control, and room delivery/readback have evidence; see QUALIFICATION-LAB.md. Reboot-persistent isolation and guarded manual runtime recovery also passed. Complete CMS publishing/assigned clone deployment, production restore, automatic recovery, and physical-TV acceptance remain incomplete.
- The lab callback/polling receiver is implemented in memory, but is not integrated with vendor SmartInstall persistence, authentication, or restart recovery.
- The tooling room-package builder still requires an operator-supplied model-specific `TVSettings.xml`. The separate native CMND room path now has simulator download/XML/callback/readback evidence; physical-TV readback remains unqualified.
- Package server supports byte ranges but lacks a production authorization layer and must remain isolated.
- Filesystem lifecycle commands do not provision users/services/databases and are not a full installer or recovery system.
- RF-only MGate/PSG/DekTec functionality is explicitly out of scope. Modern TPM191/TPM215 IP packaging is Java-based. Legacy DWPack and AAB signing have separate native-helper gaps.
- Vendor certificate regeneration retains Windows paths/import/restart operations. Native certificate generation, preserved-CA validation, and SAN/alias staging tests passed; the inactive native candidate has not replaced active synthetic TLS. Automatic regeneration and hot reload remain unqualified.
- The current PHP 5.6/MySQL 5.7 fidelity profile is legacy and not a supported security target; a supported-runtime migration is not yet qualified.
- Windows backup SQL import and conditional migration were executed only in a separate isolated candidate. Omitted firmware-catalogue DDL and three historical Flyway checksum mismatches require reviewed handling; automatic production restore is not implemented. Firmware binaries are intentionally outside the supplied backup and must be uploaded separately.
