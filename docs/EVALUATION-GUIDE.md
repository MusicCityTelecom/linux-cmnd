# Linux CMND evaluation installer

This is the evaluation-release runbook. Release publication requires the recorded
clean-install, browser, restart, and updater checks; code presence alone is not a
PASS. See TEST-RESULTS.md for the actual evidence, not the steps below.

## Scope and prerequisites

Use a fresh, dedicated amd64 VM with at least 6 GiB RAM and 40 GiB disk. Ubuntu
24.04 and Debian 12 use Java 17 packages. Debian 13 is detected but requires an
operator-supplied Java 17 runtime through `--java-home`; do not substitute Java 21.
Debian runtime qualification is recorded separately from Ubuntu.

This is not full Windows-feature parity. The supplied `MGate.zip` contains only
the Windows `MGate.exe`; the Java application also has an explicit Linux `MGate`
path, but that native binary/source has not been supplied. RF/DekTec/modulator
workflows are explicitly outside this project's scope; additional MGate-dependent
IP transport-stream playout is not qualified. The five Java web applications and
SmartCMS are separate from this missing component. Do not interpret successful
web-service readiness as proof of playout or physical-TV compatibility.

The installer starts the original five Philips Java applications, Apache/PHP
SmartCMS, and a private MySQL database. PHP 5.6 and MySQL 5.7 are legacy dependencies
used for vendor compatibility: this evaluation must not be exposed to the public
Internet. The installer does not change distribution-wide Apache sites, host
routes, or unrelated firewall rules. It creates named CMND service-UID egress rules.

Philips applications are **not redistributed in our GitHub assets**. Supply the
licensed extracted application directory from the inspected 7.5.9 installer
(`buildnr.txt` reports 7.5.10.3168). All required inputs are hash-checked. Customer
backups, clone files, firmware, and credentials are not installer payloads.

## Install

For the guided GitHub download-and-install flow, start with
[QUICK-INSTALL.md](QUICK-INSTALL.md). It replaces the manual release-asset download
and configuration steps below. The repository is public; no token is required.

Download the evaluation `.deb`, `install.sh`, `cmnd.example.toml`, and `SHA256SUMS` from the same GitHub
release, and verify their checksums. Keep the `.deb` for verified update rollback.
Before executing the installer, prepare a private configuration. Leave the
allowlist empty for initial installation. For access from another machine, edit
the bind/callback settings as described below before running the installer.

```sh
sudo install -d -m 0700 /srv/private
sudo install -m 0600 ./cmnd.example.toml /srv/private/cmnd.toml
sudo editor /srv/private/cmnd.toml
```

```sh
sudo sh ./install.sh --package ./linux-cmnd_0.6.2_amd64.deb \
  --payload /srv/private/cmnd-vendor --config /srv/private/cmnd.toml \
  --install-dependencies --accept-legacy-runtime --execute
```

The same full installer is installed as `cmnd-install` by the `.deb`; still supply
`--package` to retain the matching rollback installer. From a source
checkout, use `sudo sh scripts/install.sh --full` with the same arguments. Omitting
`--execute` prints the plan only. `--prepare-only` downloads/builds dependencies
without initializing the vendor databases or starting the application; it is useful
for an offline staging checkpoint. Supply `--tomcat-archive` and `--php-image` to
reuse locally validated dependencies. The PHP image argument must be an immutable
image ID or repository digest, never a mutable tag.

For another machine to reach the GUI, choose a stable IPv4 address on this server:
set `safety.mode = "lab"`, set `network.bind` to that address, and set
`network.callback_base_url = "http://SERVER_IP:8080"`. Do this before installation.
The loopback example is intentionally accessible only from the server itself.
The default ports remain 8080/8443 (Java), 8082/8444 (CMS), and 3306 (private DB).
FPM 9000 and administration 9078 are loopback-only internal listeners.

Successful installation checks HTTPS responses and expected database migrations,
then enables its services at boot. Open `https://SERVER_IP:8444/linux-cmnd/` for the
management page, or the Java root on 8443, which redirects there. The installed CA
must be trusted by the evaluator's browser; export only `/etc/cmnd/tls/ca.crt`, never
the private key. The management page links to the original TV/channel/settings
interface and SmartCMS site/content editor.

Allow time for the Java applications to initialize: cold startup took about
11 minutes in the 6 GiB qualification VM. The management portal starts earlier
than the vendor applications; a visible management page alone is not proof that
the Java interfaces are ready. Use `cmndctl native-health --seconds 900` to wait
for verified application readiness before testing.

Initial credentials are stored root-only in
`/var/lib/cmnd-deployment/initial-admin.json`. The Linux management login initially
uses the same generated administrator password as CAS, but is a separate account;
changing the Philips account does not automatically change Linux administration.
Do not paste credentials into tickets, logs, or GitHub.

## Updates

Use `sudo cmndctl --updates` to check public GitHub releases and review an
explicit version-scoped installation prompt. Press Enter to cancel. This is a
tooling-only update: it does not retrofit new WAR adaptations, regenerate native
configuration or rebuild the PHP image on an older installation. See
[UPDATES.md](UPDATES.md) before upgrading an existing site.

### Existing management update checks

The management service checks GitHub at startup and offers an explicit **Install
update** confirmation for a newer allowed release. Manual checks are rate-limited.
Unavailable networking or private-repository access is an error state, not a false
"up to date" result. No package is installed merely by opening the GUI.

`/etc/linux-cmnd-management/updates.json` controls `enabled`, `channel` (`stable`
or `preview`), and an optional `token_file`. Evaluation installations use `preview`
so GitHub prereleases are visible. Public repositories need no token. While the
repository is private, use a fine-grained GitHub token with read-only access to
this repository's contents; store it in a root-owned, `cmnd-admin`-group-readable
0640 file and put only its absolute path in `token_file`. The browser never receives
the token. Do not include a development-machine credential in a distributed image.

The root worker rechecks the selected GitHub release ID/version, verifies the asset
size and GitHub SHA-256 digest, validates Debian package identity, and requires a
tooling-only compatibility manifest. Configuration, databases, and the previous
installer are retained privately before installation. Readiness failure attempts
to reinstall the retained previous package and restart its runtime, and always
reports whether recovery actually succeeded. Do not assume rollback passed from
the existence of a backup. Root-private attempt directories are under
`/var/lib/cmnd-updates`; no backup is uploaded to GitHub.

This updater does not change the Philips payload, perform database migrations,
upgrade firmware, or push TV settings. Vendor-version and major-version changes
are deliberately routed to an explicit migration process.

The updater itself sends no TV commands, but restarting vendor services can
resume previously queued or scheduled TV jobs. Review and pause those jobs before
updating an operational installation; do not interpret a tooling-only update as
a guarantee of no background vendor activity. The initial installation's empty
TV egress policy remains the safe evaluation default.

The real GUI startup offer, explicit confirmation, systemd worker, package upgrade,
readiness and success display have been exercised with synthetic release transport.
Real package rollback after an injected failure also passed. The repository is
now public and needs no GitHub credential. Public transport evidence is recorded
separately in TEST-RESULTS.md; the earlier fixture results do not establish it.

## Qualification and recovery

```sh
sudo cmndctl --config /etc/cmnd/cmnd.toml native-health --seconds 900
systemctl status cmnd-mysql cmnd-php cmnd-apache cmnd-tomcat cmnd-admin cmnd-egress
```

A failed fresh install is preserved at `/var/lib/cmnd-deployment/FAILED` and its
private logs. Vendor web services are stopped on failure. The installer refuses
to overwrite existing application state; use a fresh disposable VM/snapshot when
qualifying a corrected candidate. Never delete a customer installation to retry.

No physical-TV egress is granted by installation, even if a tooling allowlist was
provided. For the first hardware test, follow FIRST-TV-TEST.md: download a clone
from one independently identified TV before any push, power, room, or firmware
changes. Browser/editor presence and simulator results are not hardware evidence.
