# Test results

## New full-installer evaluation (2026-09-09)

The new installer was exercised as a private `0.4.0` development baseline for
the `0.5.0` candidate, on a fresh Ubuntu 24.04 amd64 VM with 6 GiB RAM/40 GiB disk.
This is separate from the earlier manually assembled simulator lab below.

| Check | Result | Evidence scope |
|---|---|---|
| Complete dependency preparation | PASS | Real APT packages, pinned Tomcat/MySQL, full PHP Dockerfile build |
| Pre-initialization checks | PASS | 26 vendor hashes, Java/image/module/helper checks, free default ports |
| Full native installation | PASS | Actual fresh DB initialization and boot-enabled services |
| Application readiness | PASS | TLS-verified responses from all five Java contexts, CMS and management; SmartInstall 122/9.7 and smartcontrol 27/1.5.1 histories |
| Automatic startup after reboot | PASS | Guard, MySQL, PHP, Apache, Tomcat, management, and update path started without manual service activation; readiness repeated |
| Real browser authentication | PASS | Management login, native TV-management CAS login, CMS SSO; isolated Edge headless, separate CA-verified TLS probes |
| CMS editor/content persistence | PASS | Synthetic site created through the native editor and retained across reboot |
| Native local CMS export | PASS | Native Export event; 2,863,861-byte ZIP, 125 entries, safe paths/CRC/generated HTML/vendor XML fragment validated |
| Source tests at checkpoint | PASS | Linux 144 passed; Windows 144 run/138 passed/6 Linux-only skips |
| Real package rollback after injected failure | PASS | Actual 0.4→0.5 dpkg install, forced readiness failure, 0.4 reinstall, restored application readiness, five-DB backup, unchanged configuration/site/baseline; local release transport fixture, not GitHub |
| Real GUI-triggered package update | PASS | Startup offer, explicit browser confirmation, atomic queue, systemd worker, actual dpkg 0.4→0.5, readiness/success display and preserved configuration/site/baseline; synthetic release transport; temporary service overrides removed afterward |
| Actual GitHub GUI upgrade/recovery | NOT_RUN | Private-repository credential transfer requires explicit approval; no token transferred |
| RF/DekTec/modulator workflows | OUT_OF_SCOPE | Explicit assignment exclusion; missing Windows-only MGate is not used to block core IP management |
| Additional MGate-dependent IP playout | NOT_RUN | No Linux MGate supplied; not claimed functional |
| Debian native runtime | NOT_RUN | Detection is tested; no Debian full-runtime result claimed |
| Physical TVs | NOT_RUN | No current individual target supplied; first test remains clone download from TV |

The first broad Linux run had a five-second login-test timeout; an unchanged
focused rerun and the refreshed full 135-test suite passed. Cold native Java
startup took 652 seconds. A missing Tomcat context directory was corrected before
the reboot test. The SSL reload helper logs its keystore path at error severity
unconditionally; inspection and successful TLS loading distinguished this from
an actual certificate failure. No vendor bytecode, credentials, or test exports
are release assets. A Windows rejected-upload test raced socket closure when it
sent a body after authorization rejection; it now tests the existing
`Expect: 100-continue` pre-body rejection path. The corrected full Windows and
Linux suites passed without changing receiver authorization behavior.

## Earlier qualification checkpoints

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
