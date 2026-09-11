# Repository instructions

- Deliver the actual Philips CMND applications with the Linux installation download, not a tooling-only installer requiring a separately obtained payload. The user authorizes bundling original vendor applications as GitHub release assets. Keep binaries out of Git source history; preserve vendor attribution and applicable notices.
- Packet captures, customer backups, live database dumps, site credentials, generated certificates/private keys, logs, and customer/device data remain private. Never commit or publish them. The user explicitly authorized retaining the original vendor-distributed defaults, including the identified legacy packer keys, inside the hash-pinned vendor release bundle. This exception does not cover locally generated or customer secrets.
- Run safety validation before database or network initialization.
- Network writes require both an identity allowlist entry and an explicit `--execute` flag. Room ID alone is never an identity.
- Do not contact live TVs, change host networking, or deploy to a production host without explicit approval.
- Preserve Philips attribution. The source repository contains Music City Telecom tooling; bundled vendor applications retain their own ownership/license and are not relicensed under the tooling license. Distribution does not imply Philips endorsement.
- Keep tooling releases in SemVer. Record supported/tested vendor CMND releases separately.
- Tests must distinguish simulator, runtime, browser, and real-hardware evidence.
- Preserve the familiar Windows CMND installer/operator experience wherever Linux permits. Keep original UI, terminology, default ports and vendor first-login behavior when verified; document necessary differences. Fresh-install defaults must never reset existing account credentials during upgrades.
