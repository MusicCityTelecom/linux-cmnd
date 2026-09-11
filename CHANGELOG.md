# Changelog

## Unreleased

- Recognize the physical TV's `ChannelList/` archive as `TVChannelList` when
  both its nonempty channel database and identifier are present. All eight
  advertised clone items were received from one approved TV and passed offline
  validation; the original receive run timed out solely on this naming alias.
  No second export was needed, and no received content was imported or pushed.

- Match the original vendor's correlation-cookie generation range, 0 through
  99998, in the independent WIXP client. A physical-TV read-only check rejected
  the previous large values; a vendor-range value returned matching identity
  and ready clone-export capabilities. Strict response correlation remains
  enforced. This fix is not included in the existing v0.6.1 release assets.

## 0.6.1 - 2026-09-10 (evaluation candidate)

The unpublished 0.6.0 checkpoint is retained as the real installation/upgrade
baseline; 0.6.1 packages the same runtime adaptations with corrected deployment
documentation. See TEST-RESULTS.md for separately recorded release gates.

- Preserve an existing native deployment's environment configuration during
  package setup. Fresh installations synchronize auxiliary environments after
  applying the selected bind address. This fixes a preservation-test failure
  found during actual package rollback qualification.

- Added fail-closed, exact-hash Java literal adaptations for Linux archive
  creation and Java keytool discovery. The distribution `7zip` package and
  keytool are checked before deployment; no vendor classes are redistributed.
  Legacy proprietary packers and vendor certificate regeneration remain
  unqualified and are documented in the Windows/Linux conversion map.
- Added `cmndctl --updates`: fixed-repository release checks, exact-version
  interactive confirmation and synchronous verified worker execution, without
  additional Philips GUI changes. No installation on empty/noninteractive input.
- Audited the installed Windows service/layout/configuration footprint. Added
  missing FFmpeg/ffprobe dependencies and helper PATH checks to the development
  PHP profile, Windows-derived upload limits, private disk upload storage,
  resource-bounded PHP workers and the observed Tomcat HTTP swallow setting.
  These fresh-runtime changes are not automatically applied by tooling-only
  updates. The rebuilt image and adapted Java helpers passed isolated Ubuntu
  installation and execution checks; full feature parity remains unqualified.

- Added a guided, standalone public-GitHub bootstrap: release selection including
  prereleases, bounded HTTPS downloads, GitHub SHA-256/size checks, Debian package
  identity checks, automatic configuration and dependency installation.
- Added private local payload ZIP preparation with exact original input hashes;
  the bootstrap accepts that ZIP without manual extraction. Vendor binaries and
  customer data remain excluded from all public assets.
- Added bootstrap safety/regression tests and a two-command installation guide.
- Added explicit iproute2 installation for minimal supported Linux images.
- No new physical-TV, complete Windows restore, Debian runtime, or MGate parity
  qualification is implied by this installer convenience release.

## 0.5.0 - 2026-09-09 (evaluation candidate)

- Added a full fresh-host installer, shared shell/`.deb` entry point, pinned
  PHP/MySQL dependency preparation, native certificates, five-schema bootstrap,
  managed isolation, and boot-enabled service/readiness checks.
- Qualified a fresh Ubuntu deployment and actual reboot, native browser login,
  CMS single sign-on/editor creation, and post-reboot local export validation.
- Added authenticated management GUI startup release checks, explicit installation
  confirmation, verified tooling-only packages, private backups, and recovery.
  End-to-end private-GitHub installation is not yet qualified.
- Added release-source/asset safety checks, management request limits, explicit
  file-mode regression coverage, and failure-path recovery tests.
- RF/DekTec/modulator workflows remain explicitly out of scope. Additional
  MGate-dependent IP playout is unqualified; the supplied MGate archive contains
  only a Windows executable. This is not full
  Windows-feature parity, Debian runtime certification, or physical-TV evidence.

## 0.4.0 - 2026-09-08 (development)

- Added identity-gated TV-to-server clone export, a separate export-only permission,
  bounded private multipart receipt, CRC checks, and a first-hardware-test guide.
  Full/partial loopback uploads, identity drift, empty/corrupt ZIPs, wrong senders,
  and unsafe archive paths are tested; no physical-TV evidence is claimed.
- Added Linux-native CA/server certificate preprovisioning with PKCS12 aliases,
  full IPv4 SAN coverage, preserved-CA validation, secret-file handling, and
  non-overwriting/symlink-safe staging. Eight certificate tests passed on Ubuntu.
- Added actual Tomcat XML, callback rewrites, standalone Apache, PHP-FPM, and
  Drupal CAS path configuration staging. Apache and PHP-FPM syntax checks passed
  against the real services without changing active configuration.
- Assembled all five actual Philips applications, SmartCMS, templates, certificates,
  and native configuration in one private inactive candidate; no customer backup
  is included. Added the `stage-application` CLI and retained failed-stage markers.
- Added persistent managed IPv4/IPv6 lab isolation and guarded manual resume;
  verified the guard through reboot and repeated native browser/simulator checks.
- Corrected CMS export/content directory provisioning, DocumentRoot, and explicit
  `/SmartCMS` rewrite base; CMS overview works after reboot. Browser checks now
  reject CMS error pages instead of treating HTTP navigation alone as success.
- Added the missing PCNTL dependency in the isolated PHP fidelity profile. Native
  CMS cloning/local ZIP export passed byte-level validation after rejecting an
  earlier vendor-reported success whose ZIP contained no generated HTML.
- Source suite: 117 tests passed on Ubuntu; Windows ran 117 with one expected
  POSIX-permission skip. Installed and validated the 0.4.0 tooling package on Ubuntu.
- Complete installer activation/automatic recovery, Debian runtime, and physical-TV
  acceptance remain unqualified. This is a development checkpoint, not 1.0.

## 0.3.0 - 2026-09-08 (development)

- Started all five real Philips WARs on isolated Ubuntu 24.04, Java 17, Tomcat 9, and MySQL 5.7.44; tested real CAS/browser authentication over verified TLS.
- Added bounded discovery and independently owned, identity-revalidated inventory.
- Verified actual Philips browser discovery/import, native Standby control, and room clone delivery/callback/readback against an isolated synthetic TV; validated CMS SSO and content-editor navigation.
- Added exact WAR and SmartCMS settings renderers with synthetic tests and credential-safe handling.
- Added static five-database SQL scope/privilege auditing; this is not a SQL execution sandbox.
- Added reproducible VM-only qualification scripts, synthetic TLS, and legacy PHP fidelity experiment.
- Fixed Debian archive directory entries, Linux line endings, and platform-independent package permissions; installed the package on Ubuntu.
- Hardened release names, clean restore targets, backup output paths, package-removal behavior, and installer preflight.
- Complete production deployment and physical TV acceptance remain unqualified.

## 0.2.0 - 2026-09-07

- Added Ubuntu 24.04 and Debian 12/13 detection and installation script.
- Added reproducible amd64 Debian package builder and lifecycle scripts.
- Preserved original CMND port defaults with atomic runtime configuration rendering.
- Added Windows CMND five-database backup validation and safe staging.
- Classified supplied customer backups and clone/content packages as reference-only.

## 0.1.0 - 2026-09-07

- Initial private development baseline.
- Added safe artifact inventory/extraction, lifecycle CLI, WIXP discovery/power/clone client, room-package builder, byte-range package server, TV simulator, safety allowlists, and unit tests.
- Added Ubuntu systemd/Tomcat templates and qualification documentation.
- Added capture-derived TV callback/polling endpoint and integration test.
