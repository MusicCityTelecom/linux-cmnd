# Changelog

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
