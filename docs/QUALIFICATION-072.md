# v0.7.2 qualification record

Status: pre-publication acceptance checks PASS, 2026-09-13. Subsequent public
GitHub install/update checks also passed; see the [published-download record](QUALIFICATION-072-PUBLIC.md). This is
Ubuntu evaluation qualification, not full vendor-feature or production certification.

Baseline: v0.7.1 (`38fd6b4f9c4269127792ae6fac9cae6e78de734a`) plus the existing
unsupported-Python guard (`d33748d7f17b913f62a2eae36ac7de33a7f6d8ba`). Work is isolated
on `codex/v0.7.2-installer-readiness`. No decompiled or native-product sources are
part of this repair.

Existing test VM inspected: Ubuntu 24.04.5 amd64, 6 GiB RAM, enlarged 38 GiB root
filesystem, v0.7.1 installed. All seven CMND units active; original qualification
records seven HTTP routes and both migration histories passing. This observation
does not qualify the new candidate or authorize TV operations.

## Root causes and changes

- v0.7.1 printed only a credential-file path, not a complete operator handoff.
  The common completion formatter now prints configured URLs, the actual initial
  password and important paths after readiness/enablement/receipt writes succeed.
- Expected MySQL and missing-migration-table startup probes were logged as errors
  before successful retries. Probe errors now remain private in memory until a
  deadline failure, with timestamped progress while waiting.
- The initial disk check allowed costly preparation before applying the upload
  reserve. A shared, parity-tested bootstrap/package policy now budgets peak space
  before downloads/dependencies, grouped by destination filesystem.
- adduser checked a home that did not exist. Creating it first removes the warning
  without changing existing UID/GID or enabling interactive account login.

## Automated evidence

Runtime changes are in commit `bcf81ef2`; subsequent changes before publication
are documentation only. Candidate package SHA-256:
`d3a26b2f1a686bd52f96ee86839c5e6189e1ec2595be6cfccc0de27357935916`.
The final release package includes the finalized documentation and therefore has
its own checksum in the release's `SHA256SUMS`.

| Test environment | Result |
| --- | --- |
| Windows Python 3.13 regression | 218 tests, PASS; 14 platform/opt-in skips; 69.522 seconds |
| Ubuntu 24.04 Python 3.12 regression | 218 tests, PASS; 1 opt-in MySQL skip; 59.578 seconds |
| Ubuntu with `CMND_TEST_MYSQL_CASE=1` | 218 tests, PASS; **no skips**; 29.318 seconds |
| Reproducible Debian package tests | PASS; package `linux-cmnd`, version `0.7.2`, architecture `amd64` |
| Git whitespace/diff checks | PASS |

Linux suite command:

```sh
PYTHONPATH=src CMND_TEST_MYSQL_CASE=1 python3 -m unittest discover -s tests -q
```

The opt-in case test creates a uniquely named, network-isolated pinned MySQL
container, exercises mixed-case tables/views and stored text, and removes only
that disposable container. Simulator/protocol tests do not contact physical TVs.

## Actual isolated VM evidence

Dedicated Ubuntu 24.04.4 amd64 QEMU/KVM guest on server4: 4 vCPU, 6 GiB RAM,
40 GiB virtual disk, approximately 38 GiB root filesystem. No existing signer,
host Apache, host database, route or production VM was modified.

- Real low-disk failure: private mount namespace with a 1 GiB `/var/tmp`
  filesystem. Bootstrap rejected capacity before GitHub downloads, package
  installation or database/deployment state creation. No host partition changed.
- Actual `.deb` new install, reinstall, v0.7.1-to-v0.7.2 upgrade and purge passed.
  Service account remained non-login with unchanged UID/GID; the missing-home
  warning was absent. These lifecycle tests preceded fresh application deployment.
- Full candidate application install completed **17:34:06–17:39:16 UTC**.
  Vendor bundle came from the public v0.7.1 asset and matched the unchanged
  `9c7ce2a4c60a639b654203d099b93f39fa704fb1537a95fe2699ad3ad2ce0a6d` SHA-256,
  472,913,047 bytes, 26 original inputs. Candidate tooling was locally staged,
  so this pre-publication run is not described as public v0.7.2 transport.
- Final console password exactly matched `initial-admin.json`; JSON ended in
  a newline; the persisted receipt was root-only. Console ended with FINAL STATUS:
  PASS and the configured addresses. No actual passwords are published here.
- All seven units active and enabled: cmnd-egress, cmnd-mysql, cmnd-php,
  cmnd-tomcat, cmnd-apache, cmnd-admin, cmnd-update.path. Both containers running.
  All seven configured listeners checked: 8080, 8443, 8082, 8444, 3306, 9000, 9078.
- Seven HTTPS contexts passed: CAS 200, SmartInstall 302, smartcontrol 302,
  usermanagement 302, Java smartcms 200, PHP SmartCMS 302, management 200.
  Both expected database migration histories passed.
- Real headless Chromium logged into management and CAS/SmartInstall with the
  exact printed credential, then reached SmartCMS via SSO. TLS was independently
  checked against the generated CA; the isolated browser used a disposable trust
  profile. This is browser evidence, not only HTTP/simulator evidence.
- Actual guest reboot confirmed using a changed kernel boot ID. Units, containers,
  listeners, HTTPS, migrations and browser logins recovered automatically. File
  hashes verified unchanged credentials, configuration, certificates and a synthetic
  CMS file. CAS credentials and a synthetic database row were also unchanged.
- An unrelated test firewall chain survived application installation; a separate
  test unit restored it at boot before CMND. CMND preserved it. IPv4/IPv6 CMND
  rules were restored, attached only to the two service UIDs; OUTPUT remained
  ACCEPT. Installer source contains no host reboot/poweroff operation and does
  not flush unrelated INPUT/OUTPUT chains.
- Fresh-installer rerun was refused with the new retrieval/update guidance;
  credential/configuration/data hashes were unchanged.
- No unexpected ERROR entries were present in the new deployment log. The
  expected startup/migration retries appeared as INFO progress instead.

## Upgrade evidence and boundaries

The original, hash-verified v0.7.1 tooling package was restored on the qualified
guest without replacing its unchanged vendor runtime. The real updater worker
then installed candidate v0.7.2, retaining configuration, all five database
backups and the prior package. Only GitHub release-fetch transport was replaced
with bounded local fixture bytes; original size/hash/manifest/package validation,
backup, service, dpkg, readiness and preservation operations ran normally.

Post-update runtime checks and real Chromium management/SmartInstall/SmartCMS
logins passed. Configuration, credentials, certificates, synthetic file and database
row matched the pre-reboot baseline. Actual public GitHub upgrade/install checks
are separately recorded after release publication, rather than mislabeling this
candidate transport test as a public download.

Private logs, snapshots, generated identities/keys, screenshots and database dumps
remain outside Git and release assets. v0.7.1 tag/release assets, decompiled source
and the separate native project remain unchanged.

Physical-TV operations, RF/DekTec, issued license activation, Debian runtime and
complete Windows-feature parity are not claimed by this maintenance release.
