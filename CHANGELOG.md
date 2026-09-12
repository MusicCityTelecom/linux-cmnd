# Changelog

## 0.8.0 - development

- Development is isolated on `develop/0.8.0`, created directly from the immutable
  `v0.7.1` tag. Published 0.7.1 code/assets remain the frozen working reference.
- Added read-only host inventory and installation planning for coexistence with
  existing Apache/nginx, MySQL/MariaDB, Docker, Java/PHP and occupied listeners.
  Existing unrelated services keep their ports; only CMND-owned listeners move.
- Added read-only shared-database compatibility assessment. Existing database
  servers are never forced to change `lower_case_table_names`; incompatible or
  unqualified servers are preserved and the isolated qualified runtime remains
  the fallback. Administrator credentials are transient execution input only.
- Added the user-facing `cmnd-linux` APT meta-package design while retaining the
  existing `linux-cmnd` payload package internally, plus a separately generated
  `cmnd-vendor-759` package built only from the exact hash-verified Philips 7.5.9
  release bundle. Vendor binaries remain outside Git history.
- Added bounded Debian repository generation for development/testing/stable
  channels. Testing/stable metadata must be signed; unsigned output is permitted
  only for an explicitly requested development repository. Private signing keys
  are never repository inputs.
- Added branch-only CI for Ubuntu and Windows source regressions, package-build
  smoke tests and verification that 0.8 remains descended from exact `v0.7.1`.
- Ubuntu 24.04 remains the qualified runtime baseline. Ubuntu 22.04 and 26.04 are
  development targets and must be separately qualified before being called
  supported. Docker/Compose production deployment remains a later 0.8 milestone.

## 0.7.1 - 2026-09-11 (evaluation)

- Fresh MySQL initialization uses `lower_case_table_names=1`, matching Windows
  table-name lookup. Fixes the native Settings/Channels list errors after imports.
- Add `cmndctl native-database-case` audit and explicit `--execute` repair for
  existing pinned MySQL 5.7 deployments whose stored identifiers are all lowercase.
  Stops application writers, retains a cold database backup, verifies package
  counts and mixed-case queries, and restarts previously active services.
- Mixed-case stored identifiers and unexpected containers/configurations are
  rejected rather than silently renamed or merged. Text collations, uploaded
  archives, vendor Java code, credentials and TV settings are unchanged.
- Existing installs must run the documented repair after the tooling update;
  updating the .deb alone does not change the running database configuration.


## 0.7.0 - evaluation candidate

- Complete installation download now includes the original Philips 7.5.9
  application bundle. Bootstrap downloads and validates it automatically;
  --payload is an optional local/offline override. No existing site databases,
  customer backups or activation state are packaged. Original vendor-distributed
  defaults are retained, separately from the open-source tooling license.
- Release building requires an exact, hash-verified original-input bundle and
  emits a bundle manifest and checksums. Vendor binaries remain outside Git history.
- Document the preserved hardware-derived serial/activation behavior and VM
  identity requirements. Add an explicit, fixed-vendor license-network command
  without disabling TV isolation, granting entitlements or submitting a license.

## 0.6.2 - 2026-09-10 (evaluation)

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

## 0.6.0 - development baseline

- Full public GitHub bootstrap path installs the original Philips application
  bundle after verifying exact file hashes and release metadata.
- Qualified fresh Ubuntu 24.04 deployment with isolated MySQL 5.7/PHP 5.6,
  Java 17/Tomcat 9, Apache, generated credentials/certificates and CMND services.
- Added native management/update tooling, backup/restore preparation, protocol
  simulator and bounded receive-only TV clone-export qualification paths.
