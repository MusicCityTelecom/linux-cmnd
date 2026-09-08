# Actual Linux qualification evidence

Development checkpoint: 2026-09-08 UTC, tooling 0.4.0. This is not a production acceptance report.

## Environment

A disposable VirtualBox VM, `CMND-Ubuntu24-Qualification-20260908`, runs Ubuntu 24.04 amd64 with four virtual CPUs and a 40 GiB virtual disk. RAM was reduced from 8 to 6 GiB before the recovery check to leave room for the Windows host. No server4 or production-TV connection was made. Windows host networking was not changed. VM NAT forwards bind to Windows loopback only.

The canonical Ubuntu cloud image `noble/20260826/noble-server-cloudimg-amd64.vmdk` matched its published SHA-256: `fb3ba097a9013d759fa13ab22d2b4118bd55452c617ca3758a55303eea96de6e`.

- Java: OpenJDK 17.0.20, Ubuntu build.
- Tomcat: 9.0.121, upstream archive SHA-512 checked before extraction.
- MySQL: 5.7.44, image digest `sha256:4bc6bc963e6d8443453676cae56536f4b8156d78bae03c0145cbe47c2aad73bb`, internal Docker network, no LAN database publication.
- PHP fidelity experiment: PHP 5.6.40 with PDO MySQL/mysqli/opcache/GD/ZIP/XML-RPC compiled from pinned sources. GD has FreeType/JPEG/PNG. Running extension image: `sha256:94e10e115eaf9f009105bd0f6248d61798c4336875c5f730cc4e9f89e946a299`. End-of-life; not a supported production security profile. Full CMS publishing acceptance remains pending.
- The Linux CMS export worker additionally requires PCNTL. An offline build added it to the preceding image; current runtime is `sha256:ab1537bf1305c30c0701e4b938b33aa475d022d6dbbc73aaab022cff23a1c27f`. Previous PHP containers remain stopped/preserved.
- Browser: Playwright 1.62.0, Chromium 151.0.7922.34, inside the guest. Synthetic certificate trusted in the guest only; `ignore_https_errors=False`.

Vendor Java and PHP run as non-root identities. IPv4/IPv6 OUTPUT rules allow their new connections only to guest loopback, the one internal database endpoint, and the synthetic TV at 172.30.44.4:9079. The TV container has no published ports and shares the internal-only Docker bridge. Customer SQL was imported only into a separate internal candidate, never the browser application's database; the customer candidate remains disconnected from all application services. `listener.load=off` is NOT an isolation control: the application ignores it and starts schedulers unconditionally.

## Direct observations

| Test | Result | Evidence |
|---|---|---|
| Five private WARs rendered | PASS | Exact configuration resources replaced; other ZIP entry content preserved; original payload unchanged |
| Fresh five-schema initialization | PASS | Lowercase cas, tpvision, smartcms, smartcontroldb, smartinstall initialized without vendor credential-changing sql_1 or cleanup scripts |
| SmartInstall migration history | PASS | 122 rows, all successful, final 9.7; 67 tables |
| smartcontrol migration history | PASS | 27 rows, all successful, final 1.5.1 |
| All five Java contexts respond | PASS | Four authentication redirects, lowercase smartcms HTTP 200; not proof of every workflow |
| TLS/browser login page | PASS | Actual Philips CAS page HTTP 200; certificate verification enabled |
| Incorrect password | PASS | CAS denied authentication |
| Generated test-account login | PASS | Browser reached `/SmartInstall/dev`, actual TVs/Groups/Settings/Triggers UI, empty fresh inventory |
| Apache/PHP SmartCMS authentication | PASS after fixes | Initial HTTP 200 masked CAS failure; Drupal-aware Windows debug/PGT path correction and guest CA validation now reach `Websites overview / CMND CMS` through actual browser SSO; creation/publishing not yet accepted |
| CMS sensitive-file HTTP denial | PASS | settings.php and config.properties both returned 403 |
| CMS new content editor | PASS | Actual Create new content action opened the My templates editor at `/SmartCMS/website/edit/6208`; synthetic content only, not published |
| CMS native content clone/local export | PASS after dependency fix | Native DOM Clone/Export events created synthetic node6221; final2862950-byte download has125members, required HTML/configuration/metadata and valid CRC; SHA256 55ef7adf3a9116721c6d24724b00226847904f2adb57e838374c5ff88f247514. No TV publication |
| Tooling .deb on Ubuntu | PASS after fix | First attempt exposed missing directory entries; fixed package unpack/configuration and CLI validation succeeded |
| Unit/integration source suite | PASS | 102 tests on Ubuntu Python 3.12; Windows Python 3.13 ran 102 with one expected POSIX-permission skip; 0.4.0 Debian package built and installed/CLI-validated inside Ubuntu |
| Vendor TV discovery/onboarding | SIMULATOR_PASS | Actual browser Add/Detect/Auto Import found one synthetic 43HFL6114U/27 at 172.30.44.4 and inserted it into Philips inventory; loopback failed route selection |
| Native room delivery/readback | SIMULATOR_PASS | Real UI generated a 1553-byte nested ZIP; simulator parsed actual lowercase item XML, matched serial, applied room `0042`, sent callback to SmartInstall, and independent discovery read back `0042` |
| Native power control/readback | SIMULATOR_PASS | Real remote-control dialog sent Standby; independent simulator discovery/state readback confirmed Standby |
| Native room persistence after navigation | SIMULATOR_PASS | Subsequent edit to `0043` survived a fresh vendor TVs page request; UI value and independent simulator readback both matched |
| Complete assigned clone/content deployment | NOT_RUN | Room-only success is not full clone/content-job qualification |
| Windows five-database restore preparation | PASS | Original 7.4.8 archive: five SQL dumps staged privately; exactly 11 proven definer rewrites, all literals/Flyway text preserved |
| Windows five-database import | PASS with prerequisite | User approved private transfer; all five schemas imported in a separate isolated container after supplying omitted firmware-catalogue DDL; no customer application started |
| Windows upgrade comparison | STRUCTURALLY_COMPATIBLE | Existing key column/FK definitions match; final device view has identical 74-column projection; ten pending migrations are additive |
| Windows migration experiment | CONDITIONAL_PASS | Explicit reconciliation of three historical checksums followed by ten migrations reached122successful rows/9.7; not automatic production restore acceptance |
| Native certificates | PASS | Eight Linux tests, validated CA preservation, aliases/SANs/permissions; inactive native certificate stage generated, active synthetic TLS not replaced |
| Full private application assembly | STAGING_PASS | Actual fiveWARs, SmartCMS, templates, certificates and native config assembled without reference backups; no activation claim |
| Native service configuration | SYNTAX_PASS | Actual standalone Apache and PHP-FPM accepted generated configuration; default and changed-port mappings unit-tested |
| Isolation after reboot | PASS after fix | Original ephemeral chains disappeared on reboot while applications remained stopped; new managed IPv4/IPv6 chains and dependency guard survived a second reboot before Docker/CMND startup |
| Guarded manual application resume | PASS | Original synthetic database/TV/PHP containers only; actual CAS/CMS browser login, room delivery/readback, and Standby repeated after reboot. Customer restore container remains stopped; automatic application startup is not claimed |
| Debian 12/13 runtime | NOT_RUN | Distribution detection is not runtime qualification |
| Physical TVs/server4 | HARDWARE_BLOCKED | No current scoped live target/deployment approval |

Initial startup took approximately 440 seconds; the 6 GiB post-reboot run logged 394.7 seconds. Readiness checks must inspect application responses and actual migration history; SmartInstall catches migration exceptions and can appear started anyway. The simulator is intentionally memory-only and resets its room to `0001` on container restart; successful post-reboot UI control reapplied `0042`. This is not evidence of physical-TV nonvolatile storage.

## Important compatibility findings

Evidence comes from the supplied WAR resources and targeted class/method analysis, not assumptions about the Windows installer.

- `NetworkUtils` considers every active non-loopback IPv4 interface. Interface stability and correct advertised/download addresses matter.
- The internal simulator uses `python@sha256:78387bc3881b8273120a12ebe6c1ab22b018ccc2c9adf565ae1ac9b536e184ea`. Its synthetic UID is the uppercase serial concatenated with colon-free MAC, as required by the native importer. This is simulator evidence, not hardware certification.
- CMS CAS configuration contains Windows debug and proxy-ticket paths in serialized Drupal variables. Use `variable_set`, not SQL string replacement. The lab sets `cas_cert` to its guest CA bundle, disables CAS debug logging, and uses a private writable Linux proxy-ticket directory.
- Native CMS export uses the HTTP DocumentRoot as its filesystem root. Set DocumentRoot to SmartCMS, retain the `/SmartCMS` alias, and explicitly set `RewriteBase /SmartCMS` in the staged `.htaccess`; otherwise Drupal clean URLs return404. Only `sites/default/files` is writable to PHP, including its `export` and `tpvision` subdirectories. The private PGT tmpfs directory must be recreated as PHP's own UID after container restart.
- The vendor Linux export worker exits early when `pcntl_fork` is missing, but its parent can still report success and cache the base-only ZIP. The first downloaded117-member archive had no HTML and was rejected by the test. After offline PCNTL provisioning, a new native clone produced generated content. Preserve failed evidence; never accept CMS success JSON or ZIP size alone. Thumbnail generation still requires the absent CutyCapt helper.
- The vendor metadata generator emits an XML declaration followed by two top-level elements, `SchemaVersion` and `SmartInfoBrowser`. The test validates that exact fragment inside a temporary parser root without modifying export bytes; a single-root XML parser alone would incorrectly reject this native format.
- The supplied CAS login page has a `$ is not defined` error at line 37. Authentication succeeds, but the vendor script ordering issue remains recorded.
- Actual room packages contain `RoomSpecificSettings/RoomSpecificSettings.xml` and `TVSettings/TVSettings.xml` plus identifiers. Room XML uses lowercase `item` with child `Name`/`Value`. Strict validation initially failed until the simulator matched this observed layout. Successful room package SHA-256: `c561a8fef99bf286b9f09ee36ea5f94ecdd58d50f79cdb69a17338dd795d6efb`; expanded size 2232 bytes. Synthetic material only.
- `ScheduleListener` unconditionally starts background jobs and certificate tasks. Keep restored data isolated until those jobs are reviewed.
- Preserve `reload.jar` and the ROOT rewrite mappings for `/webservices.jsp`. Synthetic HTTPS startup with the vendor ReloadProtocol has been exercised; full certificate regeneration/reload qualification is pending.
- `CertUtils` has Linux OpenSSL selection but residual Windows paths/import/restart operations. Pre-provision CA/server material; preserve existing enrollment CAs during restore. Set a stable `COMPUTERNAME` and SANs covering the JVM's actual interface addresses.
- `IPCloneServiceManager` and `SettingCreator` use Java ZIP and HTTP/JAPIT for modern TPM191/TPM215 IP workflows. RF/PSG/DekTec helpers are outside the assignment. Legacy TPN141 proprietary DWPack and AAB signing have separate native-dependency gaps.
- Keep `lower_case_table_names=0`; inspected table names are lowercase. Explicitly map the Windows `SmartInstall` dump header/path to lowercase `smartinstall` based on the actual JDBC configuration. Do not rename schemas or discard Flyway history blindly.
- CAS uses vendor legacy MD5 password encoding. The test credential is generated, private, and temporary; this observation is not a security recommendation.

## Reproduction boundaries

`scripts/lab_seed.py`, `lab_vendor_runtime.py`, `lab_tls.py`, `lab_cms.py`, and `lab_browser.py` are explicitly VM-only experiments. They fail closed on existing initialization state, are not a general host installer, and are not packaged as production deployment commands. Private logs, source payloads, database volumes, screenshots, certificates, and credentials stay in the ignored `.lab` tree or inside the disposable VM.

The production installer must be consolidated from accepted behavior only after the remaining workflow, restore, and recovery tests pass.
