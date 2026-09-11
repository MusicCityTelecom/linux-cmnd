# Test results

## 0.7.1 database-case patch (2026-09-11)

- Ubuntu 24.04: 204 tests passed (29.417 seconds), including the opt-in
  network-isolated, pinned-MySQL fresh-initialization test. It creates mixed-case
  schema/table/view names, queries alternate case and verifies stored text is
  unchanged. The disposable test container was removed afterward.
- Existing runtime: guarded repair applied to the public-0.7.0 Ubuntu test VM;
  all 9 schema names and 620 non-information-schema table/view names checked.
  Cold database backup retained privately; package row counts preserved.
- Authenticated HTTP: native Settings and Channels list endpoints returned 200
  and their existing records. Operator independently confirmed both lists now
  display in the browser. All seven HTTPS contexts and both migration histories
  passed native readiness after repair.
- Static audit: 3,811 Java classes and 2,672 static Java/PHP/CSS web references
  examined; no static path-case mismatch or case-colliding file found. Additional
  mixed-case SQL/entity references were flagged for review, not declared bugs
  solely from string matching (Hibernate entity names can intentionally differ).
- Customer archives stayed private. Five ZIP integrity checks passed; standalone
  Settings and Channels members matched their full-clone counterparts byte for
  byte. No TV contact, enrollment, power, settings or content push in this work.

Scope: this verifies the repair and fresh MySQL initialization, not a complete
fresh 0.7.1 installer run or full Windows feature parity. Published 0.7.0 previously
passed a full fresh install using public GitHub assets on Ubuntu 24.04, but its
health checks did not exercise these authenticated package-list endpoints.
Existing installations must explicitly run the [repair](DATABASE-CASE.md) after
updating tooling; the updater does not silently change database configuration.

## 0.6.2 evaluation release

Version 0.6.2 packages the two corrections described below: the WIXP cookie
range and physical channel archive recognition. Assets were built from source
commit `5bf5606`; version 0.6.1 assets remain unchanged. The release is an
evaluation prerelease, not a new full clean-install or production certification.

- Ubuntu 24.04: all 187 tests passed (24.769 seconds), from the release source archive.
- Windows: 187 tests run, 175 passed and 12 POSIX/root-only skips (43.761 seconds).
- Public-safe source/package audit passed. All eight GitHub assets downloaded
  byte-for-byte and matched GitHub size/SHA-256 metadata; updater manifest passed.
- Actual public GitHub CLI upgrade 0.6.1 -> 0.6.2 passed without a token. Cancel
  was non-mutating. Confirmation preserved configuration and synthetic CMS site,
  retained all five database backups plus configuration/prior-package backups,
  and passed all seven CA-verified HTTPS contexts and both migration histories.
- Installed/retained package SHA-256:
  `8c3a6135db590b9deca5ea2f66903bdf94fd211fa2dc2ee9fc5258a8c8a24a3d`.
- Public bootstrap selected v0.6.2 and verified package/installer downloads.
- Expected listeners remained present: Java 8080/8443 and Apache 8082/8444 at
  the configured guest address, database 3306, FPM 9000 and management 9078 on
  loopback. Native egress and subnet blocks stayed in force. No physical TV
  was contacted during release or upgrade testing.

Native GUI Add/Detect, fleet scanning, and TV configuration writes are not
qualified by the receive-only test. See FIRST-TV-TEST.md for safe scan scope.

## Physical-TV clone readback (2026-09-10, before 0.6.2 packaging)

The first approved TV-to-server export delivered all eight advertised item ZIPs
over the isolated VM's OpenVPN connection: TVSettings, TVChannelList,
RoomSpecificSettings, AndroidApps, ProfessionalAppsData, HTVCfg.xml, MyChoice
and DataDump. Firmware was not requested. The archives totaled 1,552,548 bytes.
All eight were unencrypted and passed CRC and safe-path checks. Original HTTP
bodies, command/reply evidence, files and a separate postvalidation report are
retained privately; no device identifiers, captures or customer files are public.

The source checkout with the cookie fix performed identity checks before the
single clone-export Change command. That command contained only TV-to-server
export parameters. No TV power/settings/firmware/content push, native import,
or fleet scan was performed. Temporary rules admitted only the selected TV to
the private receiver and the dedicated probe UID to its TCP9079 endpoint; those
rules were removed afterward and the subnet block restored.

Qualification distinction: transport/receipt of all eight items passed, but the
original receiver's completion result was **false** because it did not recognize
`ChannelList/` as the requested `TVChannelList`. It waited out its bounded window
with no receive failures. The validator correction included in 0.6.2 recognizes the
observed nonempty database/identifier pair. Offline revalidation of the original
files then passed all eight; the failed original report was preserved, not edited.
Automatic live completion with this second correction has not been retested.

An earlier attempt stopped at a discovery timeout before sending any export:
the VPN had restarted and was waiting for reauthentication. The private test
helper now checks VPN-status freshness and retains credentials in OpenVPN memory
for reconnects, without a password file. A later connection delivered the files.

Windows regression suite after both source fixes: 187 tests run, 175 passed,
12 POSIX/root-only skipped (58.298 seconds). At that checkpoint these changes
were source-only, not in v0.6.1 assets or the VM's installed package. No
claim of full Windows parity, native GUI import, or TV push qualification follows
from this receive-only test.

## First physical-TV read-only check (2026-09-10, historical checkpoint)

An individually authorized 50HFL5214U/27 answered over the test VM's OpenVPN
tunnel. No subnet scan, native inventory import, power/settings change or clone
transfer was performed. The native application's TV egress remained blocked;
temporary access was limited to a dedicated probe UID and one TV's TCP9079.
The permission was removed after each bounded check. Device/customer details
and raw replies are retained privately, not in this repository.

Two discovery attempts using the tooling's former large correlation-cookie range
returned a vendor error response with Cookie=-1. Inspection of the original
vendor generator established its half-open range [0,99999). A subsequent check
using values in that range passed exact identity/model verification and the
clone-export capability request, with matching response cookies. The TV reported
Standby and export Ready, advertising eight recognized export items.

This is real-hardware evidence for those two read operations only. The source
cookie-generation fix was then unreleased; published v0.6.1 has not been replaced.
At that checkpoint actual clone receipt and return-path reachability were still
pending; subsequent readback evidence is above. TV-changing operations remain
unqualified. Successful simulator tests did not catch this wire mismatch.
After the fix, the Windows suite ran 185 tests: 173 passed and 12 POSIX/root
checks skipped (54.169 seconds). Added regressions cover the vendor random range
and continued rejection of a mismatched response cookie. These are source tests,
not qualification of a newly built installer or a new published release.

## 0.6.1 release qualification (2026-09-10)

Release assets are built from `c5b0ac27e456826a4b0f6dd149c77ac6329edab7`.
The fresh native runtime is the unpublished 0.6.0 checkpoint; 0.6.1 adds the
package-configuration preservation fix and documentation. Evidence is scoped
below; no physical TVs were contacted.

- Source suite: all 183 tests passed on Ubuntu 24.04 (25.469 seconds). Windows
  ran 183 tests: 171 passed and 12 POSIX/root-only skips (41.400 seconds).
- Actual fresh installation, seven CA-verified HTTPS contexts, five schemas,
  expected migration histories and native Java archive/keytool execution: PASS.
- Browser recheck: management login, native CAS/TV login, CMS SSO and the original
  editor rendered successfully. The first run timed out waiting for full page
  load after the editor document loaded; the rerun inspected the existing site
  with DOM-content-loaded readiness rather than creating another site.
- Native local CMS export: PASS, 2,863,862 bytes, 125 entries, valid CRCs, safe
  paths and generated HTML. No content was published to TVs.
- FFmpeg generated an MPEG4 test video; ffprobe verified 160x120 dimensions.
  ImageMagick generated and identified a 32x24 test image. These are synthetic
  media-helper execution tests, not complete media-upload workflow coverage.
- Actual guest reboot: PASS. Services and the isolation guard started
  automatically; HTTPS/migrations and the synthetic CMS site persisted.
  Java 8080/8443 and Apache 8082/8444 bound to the configured guest address;
  MySQL 3306, PHP-FPM 9000 and management 9078 remained loopback-only.
- Actual CLI worker package rollback: PASS after an injected readiness failure.
  The prior 0.6.0 runtime recovered, backups were retained, and configuration,
  the synthetic site and baseline package were unchanged. Release transport and
  terminal confirmation were fixtures; dpkg, database backups and recovery were
  real. The initial run caught auxiliary `tomcat.env` regeneration; `c5b0ac2`
  fixes native package-setup preservation and fresh environment synchronization.
- Published evaluation prerelease v0.6.1: all eight GitHub assets were downloaded
  and compared byte-for-byte with local artifacts; sizes/digests and the updater
  manifest validated. Release artifacts remain pinned to the commit above.
- Actual public GitHub CLI upgrade 0.6.0 -> 0.6.1: PASS, without a token. First,
  cancelling the real terminal prompt left the installed version, configuration
  and synthetic site unchanged. Confirming the next prompt installed the released
  package, retained configuration and all five database backups, preserved the
  synthetic site, and passed all seven HTTPS contexts and migration checks.
  The retained package SHA-256 matched the published asset. This test used real
  GitHub transport and terminal input, not the earlier release fixtures.
- Actual public bootstrap selection/download: PASS, selected v0.6.1 and verified
  the released Debian package and installer without authentication. This was a
  download check, not a second fresh installation over the existing VM.

## Development 0.6.0 Linux/CLI checkpoint (2026-09-10)

- Commit `048660b`: all 172 tests passed on Ubuntu 24.04.4 in the dedicated
  QEMU/KVM guest. Windows ran 172 tests: 163 passed and 9 POSIX-only skips.
- Commit `46d4c5c` Java-adapter regression suite: all 180 tests passed on Ubuntu
  24.04.4 (25.312 seconds). Windows ran 180 tests, 171 passed and 9 POSIX-only
  skips. Native helper execution is still pending.
- Two actual original helper classes passed their pinned SHA-256 and exact
  literal adaptation checks locally. This is static evidence, not a running
  archive-creation or Android packaging acceptance result.
- Server4's preexisting Apache, MariaDB, PHP and signer processes were unchanged
  by initial VM provisioning. The host rebooted during a work pause; subsequent
  guest restart checks used a new baseline and left those host services unchanged.
- Candidate `46d4c5c` clean installation: PASS in the dedicated Ubuntu guest,
  including actual APT preparation, rebuilt PHP/FFmpeg image, all five schemas,
  seven CA-verified HTTPS contexts and both expected migration histories.
- Actual listeners: Java 8080/8443 and Apache 8082/8444 on the configured guest
  address; MySQL 3306, PHP-FPM 9000 and management 9078 on loopback only. All
  seven CMND services/path units and the identity egress guard were active.
- Native Java helper execution: PASS. The deployed classes matched the adapted
  hashes; the vendor keytool resolver selected and executed Java 17 keytool.
  The vendor archive method produced a real 7-Zip archive whose integrity check
  passed. This does not qualify complete Android bundle signing or legacy packers.
- PHP private upload directory: mode 0700, owned by the CMS service account.
- Installed CLI actual public GitHub check: PASS, no token and no newer published
  release; no package installed. Real newer-release upgrade is still pending.
- Browser management login, native CAS/TV login and CMS SSO: PASS. The synthetic
  editor URL loaded its document but the first test timed out waiting for the
  full page load; editor/export recheck and reboot are pending. No physical TVs
  were contacted. The unpublished 0.6.0 checkpoint is the 0.6.1 upgrade baseline.
- 0.6.1 version/documentation checkpoint: Windows ran 180 tests, 171 passed and
  9 POSIX-only skips (84.224 seconds).

## Public bootstrap source update (2026-09-10)

- Windows source regression suite: 158 tests run, 152 passed, 6 expected
  POSIX-only skips. Includes 14 new bootstrap/private-payload tests.
- Actual unauthenticated public GitHub release selection: PASS, selected v0.5.0.
- Actual public download of v0.5.0 `.deb` and `install.sh`: PASS; exact sizes and
  SHA-256 values verified against GitHub metadata, with no token supplied.
- Private payload helper: PASS with all 26 pinned original inputs; generated ZIP
  read back and hashed successfully. No customer database or credentials included.
- This new bootstrap has not yet completed a fresh Linux activation. The
  published v0.5.0 native installer's earlier runtime evidence remains below;
  bootstrap source testing does not replace it. Development v0.6.0 is unreleased.
- No new physical-TV, Windows restore, or Debian runtime evidence is claimed.

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

The release-candidate installer was subsequently run from its checksum-verified
standalone shell/`.deb` assets after restoring the pre-database Ubuntu snapshot.
Its configured address was on a new internal-only VM bridge, not loopback.
All seven HTTPS contexts and migrations passed, and the retained `.deb` matched
the installed candidate. Native browser authentication, CMS editor creation and
local export passed on this address. Native Add/Detect imported the exact
synthetic identity into Philips inventory; room-clone ZIP download/CRC/callback,
independent room readback, fresh-page room persistence and Standby also passed.
These remain simulator results, not physical-TV evidence. Auto-import emitted
settings/enabler/PMS commands, so FIRST-TV-TEST.md explicitly excludes it from the
initial receive-only hardware test. Final prompt wording warns that a vendor
restart can resume existing jobs; tooling-only does not mean no background activity.

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
