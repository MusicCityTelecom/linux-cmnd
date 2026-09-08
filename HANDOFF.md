# Handoff checkpoint

Branch: `main`. Tooling version: `0.3.0` development. Remote: private `MusicCityTelecom/linux-cmnd`. This is still an in-progress port, not a completed installer.

Implemented: deny-by-default source policy; config safety; artifact hashing/safe ZIP extraction; WIXP discovery/power/clone client; capture-derived callback/polling receiver; synthetic TV; room ZIP builder; range package server; filesystem lifecycle CLI; systemd/Tomcat templates; unit tests and documentation.

Local private inputs confirmed: installer 7.5.9 and five WARs under `{app}`. Do not commit them. Metadata conflict `7.5.9` versus build `7.5.10.3168` remains under investigation.

Tests: 71 unit/integration tests passed on Windows Python 3.13 and Ubuntu Python 3.12. Real Ubuntu `.deb` installation exposed and then verified the fix for missing tar directory entries. Actual vendor WAR startup, fresh Flyway histories (SmartInstall 122 rows/9.7; smartcontrol 27 rows/1.5.1), TLS-verified browser CAS login, native synthetic-TV onboarding, and CMS SSO/editor have evidence in docs/QUALIFICATION-LAB.md. The 7.4.8 SQL preparation passed locally; customer SQL transfer/import into the VM requires explicit approval and remains unexecuted. No backup was packaged.

Disposable VM: `CMND-Ubuntu24-Qualification-20260908`; guest hostname `cmnd-qualification`; localhost SSH port 22240, user cmndlab, ignored key `.lab/keys/id_ed25519`. Owner account permission is required to use that key. Never print or commit it. Root-only private logs/secrets are in guest `/var/lib/cmnd-lab`; browser test credential is guest `/home/cmndlab/browser-credentials.json` mode 0600. VM vendor egress is firewall-restricted; this must remain enforced. No server4/real TV authority exists.

Active runtime: systemd `cmnd-lab-tomcat`, Apache, Docker `cmnd-lab-mysql`, `cmnd-lab-php`, and `cmnd-lab-tv-internal`. The simulator is 172.30.44.4 on the existing internal cmnd-lab-db bridge; loopback simulator units are stopped. Sources `/home/cmndlab/tooling`, fresh payload `/home/cmndlab/input/vendor`. Actual app `/usr/local/tomcat`, data `/opt/Philips`, CMS `/opt/cmnd-lab-cms`. These are lab-only paths and lifecycle, not the canonical deploy design.

Native Add/Detect/Auto Import passed against the synthetic TV using the actual Philips browser interface. CMS SSO now reaches Websites overview after fixing serialized Windows debug/PGT paths using Drupal variable_set and setting a guest CA bundle. Secret-file HTTP denial returns403. Next: modern room/clone delivery, CMS content creation/publish, certificate background behavior, Windows restore, lifecycle; consolidate accepted steps into the production installer. Do not treat the independent CLI SQLite inventory as Philips inventory. Do not restart the existing DB or alter lower_case_table_names: inspected lowercase tables work with Linux default 0. RF/DekTec are explicitly out of scope.
