# Test results

Checkpoint: source `0.1.0`, Windows development host, 2026-09-07.

| Area | Status | Actual evidence |
|---|---|---|
| Five WAR presence/integrity | PASS | all five present; `7z t` passed in read-only audit |
| Capture reassembly | PASS | tshark 4.6.8; discovery, power, callback, clone, and range streams decoded |
| Python unit suite | PASS | 17 tests passed with Python 3.9.7 on Windows; archive, config safety, room/range package, WIXP simulator, message, and lifecycle tests |
| Installer static integrity | PASS | `innounp` 0.50 `-t` returned exit 0 for installer SHA-256 `4cc2…2348`; this does not run installer actions |
| Installer extraction | PASS | `cmndctl extract` + `innounp` 0.50 exit 0; isolated staging contains all five required WAR names |
| Ubuntu clean runtime | INFRASTRUCTURE_BLOCKED | no disposable Ubuntu runtime executed |
| Vendor login/browser | NOT_RUN | applications not started |
| Five-database restore/migration | NOT_RUN | private backup not imported |
| Simulator protocol slice | NOT_RUN | code implemented, execution pending |
| Physical TV | HARDWARE_BLOCKED | no current device allowlist/approval |
| Security/recovery qualification | NOT_RUN | partial controls implemented only |

This file must be updated from command output; skipped tests never count as PASS.
