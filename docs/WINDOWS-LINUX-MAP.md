# Windows 7.5.9 to Linux conversion map

Inspected the operator's fresh Windows installation on 2026-09-10. This is an
implementation/audit map, not a full feature-parity certificate. Raw inventories,
credentials, binaries, vendor code and screenshots remain private.

| Observed Windows location/component | Linux implementation | Evidence / remaining work |
|---|---|---|
| Program Files (x86)/CMND | Operator-supplied original installer inputs, rendered into a private candidate | All 26 pinned inputs verified; do not treat the staging directory as the only active application root |
| Program Files/TPV/jre-17.0.16 | Native Java 17 runtime | Active Windows Tomcat JVM registry path verified; Linux patch version is recorded separately |
| Program Files/Apache Software Foundation/Tomcat 9.0 | `/opt/cmnd/tomcat`, `cmnd-tomcat.service` | Five original WARs plus ROOT callback routing; JDBC/CAS/public URLs, paths and TLS config rendered locally |
| C:/Philips | `/opt/Philips` plus private Linux certificate/config locations | Templates/assets staged locally; MGate remains a separate unqualified native binary gap |
| C:/Apache24/htdocs/SmartCMS | `/opt/cmnd/SmartCMS`, standalone Apache with PHP-FPM | Drupal rewrite root, database settings, CAS trust and writable export paths adapted; prior Linux browser/export evidence exists |
| C:/php | Pinned PHP 5.6 compatibility container | Windows uses Apache's PHP DLL; Linux uses loopback FastCGI, native modules, PCNTL, private filesystem mounts and explicit helper PATH |
| C:/plugins/ffmpeg and ffprobe | Pinned Linux FFmpeg package in the PHP image | Missing dependency found in this audit; added to development image and required preflight helper checks; rebuilt-image validation pending |
| C:/plugins/phantomjs, rasterize.js, CutyCapt | Existing vendor Linux branch calls `/usr/local/bin/cutycapt.sh` | Native adapter/Xvfb/CutyCapt supplied; meaningful nonblank thumbnail qualification still required |
| C:/plugins/convert | Linux ImageMagick | Already in PHP image; now explicitly checked by preflight |
| C:/plugins/7-Zip | Vendor Linux PHP ZIP implementation | Code has an explicit Linux branch; no blanket executable/path substitution is appropriate |
| Windows MySQL service | Private pinned MySQL 5.7 container and generated per-schema accounts | Five schemas initialized independently; the Windows machine's root password is never reused |

## Confirmed Windows runtime details

- Tomcat9, MySQL and Apache2.4 services were running with automatic startup.
  Filebeat was running with manual startup. Linux uses dedicated service identities,
  not a translation of Windows LocalSystem privileges.
- Active Tomcat applications: CAS, SmartInstall, smartcontrol, smartcms,
  usermanagement, and ROOT. HTTP 8080, TLS 8443, custom Philips ReloadProtocol,
  TLS maxThreads 150, and HTTP maxSwallowSize -1 were observed.
- Active Apache serves SmartCMS on 8082 and 8444, loading PHP from C:/php.
- Windows PHP upload/post and memory settings are 8096 MiB; script timeout is 0.
  See CONFIGURATION.md for deliberate Linux resource limits and new disk-backed
  temporary uploads. Equivalent supported workflows do not require copying an
  unsafe memory setting into a smaller container.
- A read-only elevated collector inspected 14 properties files and found platform
  reference patterns in 161 text files. Those counts include third-party browser
  compatibility code, comments, and inactive Windows branches; they are not 161
  confirmed Linux defects. No raw property values were included in that report.

## Java and web code handling

The five WARs are used as original private inputs. The native staging renderer
changes reviewed configuration entries and two hash-pinned helper classes.
`ZipCommonUtils` invokes the distribution's Linux 7-Zip through `cmnd-7zip`, and
`AndroidAppHelper` looks for Java's `bin/keytool` rather than `keytool.exe`.
Only the exact reviewed constant-pool literals change; method bytecode and
constant indices are preserved. Unknown class hashes, missing literals or
duplicate literals fail staging. No vendor class bytes are in the repository.
All other classes, libraries, templates and static resources are retained.
Existing Linux branches are used where supplied.
Compiled Java code that launches native executables, regenerates certificates,
or invokes Windows service commands needs targeted behavioral review; changing
every string mentioning Windows would break otherwise portable libraries.

The live PHP CMS already chooses Linux-specific paths for media inspection,
thumbnails, document roots, ZIP creation and export worker processes. This audit
confirmed actual calls to `ffmpeg` and `ffprobe`, exposing the missing image
dependency. External process argument quoting, legacy packaging/signing helpers,
vendor certificate regeneration, complete backup restoration and MGate-dependent
IP playout still need qualification. RF/DekTec/modulator operation is out of scope.

A method-level follow-up inspected nine selected classes after a constant-pool
scan of 697 TP Vision/Philips classes. It confirmed additional Windows-only
legacy packaging calls (`HTV_DWPack_1401.exe`, `buh13_pack_rel.exe`), Windows batch
handling in captured process output, and Windows certificate-regeneration paths.
These are not solved by the two helper adaptations. Initial installation uses
our independently provisioned Linux certificates, not vendor regeneration.
Complete legacy settings/signing, Android AAB packaging and certificate rotation
are still separate acceptance gates; do not infer parity from the UI loading.

## Windows GUI baseline supplied by the operator

Screenshots show CMND 7.5.9 / TVControl 7.0.0.5698, the TV inventory and its tabs,
file categories (firmware, clones, settings, channels, apps, banners, welcome, UI,
schedules), Admin sections, and SmartCMS 24.02.682 with successful CAS login.
The local browser probe also reached the CAS sign-in page. These are Windows
observations, not new Linux browser acceptance. No Add/Detect, Assign, Force,
Delete, power or TV-delivery action was invoked during this audit.

Update interaction is now requested through `sudo cmndctl --updates`; no further
Philips GUI modifications are part of the update work. Existing v0.5.0 management
features are preserved rather than silently removed.
