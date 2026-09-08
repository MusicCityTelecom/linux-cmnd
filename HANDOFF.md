# Handoff checkpoint

Branch: `main`. Tooling version: `0.1.0`. Remote: private `MusicCityTelecom/linux-cmnd`.

Implemented: deny-by-default source policy; config safety; artifact hashing/safe ZIP extraction; WIXP discovery/power/clone client; synthetic TV; room ZIP builder; range package server; filesystem lifecycle CLI; systemd/Tomcat templates; unit tests and documentation.

Local private inputs confirmed: installer 7.5.9 and five WARs under `{app}`. Do not commit them. Metadata conflict `7.5.9` versus build `7.5.10.3168` remains under investigation.

Tests: 17 unit/integration tests pass on Python 3.9.7. Static `innounp` 0.50 integrity and full extraction both returned exit 0; the isolated extracted copy contains all five WARs. Private reports/staging remain ignored.

Next actions: build a disposable Ubuntu isolation harness and callback/polling endpoint before any vendor bootstrap. Remaining runtime, DB, browser, security, and hardware gates are listed in `docs/TEST-RESULTS.md`.
