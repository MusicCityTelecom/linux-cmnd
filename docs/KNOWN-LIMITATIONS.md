# Known limitations

- Real Linux Java runtime, fresh migrations, browser CAS/CMS login, synthetic TV discovery/import, native Standby control, and room delivery/readback have evidence; see QUALIFICATION-LAB.md. Complete CMS publishing/assigned clone deployment, restore, recovery, reboot, and physical-TV acceptance remain incomplete.
- The lab callback/polling receiver is implemented in memory, but is not integrated with vendor SmartInstall persistence, authentication, or restart recovery.
- The tooling room-package builder still requires an operator-supplied model-specific `TVSettings.xml`. The separate native CMND room path now has simulator download/XML/callback/readback evidence; physical-TV readback remains unqualified.
- Package server supports byte ranges but lacks a production authorization layer and must remain isolated.
- Filesystem lifecycle commands do not provision users/services/databases and are not a full installer or recovery system.
- RF-only MGate/PSG/DekTec functionality is explicitly out of scope. Modern TPM191/TPM215 IP packaging is Java-based. Legacy DWPack and AAB signing have separate native-helper gaps.
- Vendor certificate regeneration retains Windows paths/import/restart operations. Synthetic TLS startup is tested, but CA preservation, interface/SAN stability, regeneration, and hot reload require further qualification.
- The current PHP 5.6/MySQL 5.7 fidelity profile is legacy and not a supported security target; a supported-runtime migration is not yet qualified.
- Windows backup archives can be validated and safely staged, but SQL import/migration into MySQL is not yet executed by the tool.
