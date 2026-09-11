# Repository instructions

- Treat all Philips binaries, WAR/JAR files, packet captures, backups, SQL dumps, certificates, keys, logs, and customer/device data as private inputs. Never commit them.
- Run safety validation before database or network initialization.
- Network writes require both an identity allowlist entry and an explicit `--execute` flag. Room ID alone is never an identity.
- Do not contact live TVs, change host networking, or deploy to a production host without explicit approval.
- Preserve Philips attribution. This repository contains original Music City Telecom tooling only and does not imply Philips endorsement.
- Keep tooling releases in SemVer. Record supported/tested vendor CMND releases separately.
- Tests must distinguish simulator, runtime, browser, and real-hardware evidence.
- Preserve the familiar Windows CMND installer/operator experience wherever Linux permits. Keep original UI, terminology, default ports and vendor first-login behavior when verified; document necessary differences. Fresh-install defaults must never reset existing account credentials during upgrades.
