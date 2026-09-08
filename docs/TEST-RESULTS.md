# Test results

Current evidence: [Ubuntu 0.4.0 qualification](QUALIFICATION-LAB.md). All five actual WARs run in a disposable Ubuntu VM; real CAS/browser login, fresh migrations, reboot-persistent isolation, and synthetic controls after guarded resume passed. Full runtime acceptance is still incomplete.

On 2026-09-08, the source suite ran 117 tests on Windows Python 3.13 (116 passed, one expected POSIX-permission skip) and passed all 117 on Ubuntu Python 3.12. This includes 15 receive-only clone-export tests with actual loopback HTTP uploads and mocked TV control responses: complete/partial receipt, timeout, CRC corruption, empty/traversal ZIPs, sender/token checks, readiness, identity drift, and export-only permissions. None constitutes physical-TV qualification.

The earlier 0.4.0 development `.deb` unpacked/configured successfully on Ubuntu and `sudo cmndctl --config /etc/cmnd/cmnd.toml validate-config` passed. That package predates the receive-only export checkpoint. These are tooling/package results, not a complete application-installer claim.

The table below is the preserved historical `0.2.0` checkpoint from Windows on 2026-09-07, not the current infrastructure state.

| Area | Status | Actual evidence |
|---|---|---|
| Five WAR presence/integrity | PASS | all five present; `7z t` passed in read-only audit |
| Capture reassembly | PASS | tshark 4.6.8; discovery, power, callback, clone, and range streams decoded |
| Python unit suite | PASS | 22 tests passed with Python 3.9.7 on Windows; archive, config/runtime rendering, callback/polling, room/range package, WIXP simulator, Windows-backup, Debian-package, message, and lifecycle tests |
| Debian package contents | PASS | stdlib package builder; validates control/data members and absence of vendor payload extensions |
| Installer OS detection/dry-run | PASS | shell syntax checks and Ubuntu 24.04 root-prefix dry-run completed; non-dry-run Linux filesystem semantics not available on NTFS |
| Debian package install on Ubuntu/Debian | NOT_RUN | no local Debian-family VM/container was available |
| CMND 7.4.8 backup structure | PASS | reference archive is unencrypted; all five exact SQL members and expected database headers validated read-only |
| Installer static integrity | PASS | `innounp` 0.50 `-t` returned exit 0 for installer SHA-256 `4cc2…2348`; this does not run installer actions |
| Installer extraction | PASS | `cmndctl extract` + `innounp` 0.50 exit 0; isolated staging contains all five required WAR names |
| Ubuntu clean runtime | INFRASTRUCTURE_BLOCKED | no disposable Ubuntu runtime executed |
| Vendor login/browser | NOT_RUN | applications not started |
| Five-database restore/migration | NOT_RUN | 7.4.8 backup validated/stageable but SQL not imported; 7.5.1 artifact unavailable in current workspace |
| Simulator protocol slice | NOT_RUN | code implemented, execution pending |
| Physical TV | HARDWARE_BLOCKED | no current device allowlist/approval |
| Security/recovery qualification | NOT_RUN | partial controls implemented only |

This file must be updated from command output; skipped tests never count as PASS.
