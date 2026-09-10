# Architecture

The native installer keeps vendor Java applications on Tomcat 9 and uppercase `/SmartCMS` on Apache/PHP, backed by an isolated MySQL compatibility database. Original `cmndctl` code performs guarded import, release lifecycle, diagnostics, and protocol qualification. Vendor payload lives outside Git; tooling releases are versioned separately from the installed Philips applications.

Observed TV flow is bidirectional: CMND sends JSON over HTTP `POST /WIXP` to TV TCP 9079; TVs post status/poll messages to `/SmartInstall/webservices.jsp` on CMND TCP 8080 and fetch clone ZIPs below `/SmartInstall/Profile/Clone/`. A command acceptance, package fetch, reported completion, and setting readback are distinct states.

The qualification callback endpoint records the latest per-identity status and returns the capture-observed polling command. It is intentionally in-memory and lab-only; it does not replace SmartInstall persistence or authorization.

Native layout: versioned original tooling under `/opt/linux-cmnd/releases/<version>`;
Philips Tomcat at `/opt/cmnd/tomcat`, CMS at `/opt/cmnd/SmartCMS`, supporting files
under `/opt/Philips`; protected configuration `/etc/cmnd`; private deployment
state `/var/lib/cmnd-deployment`; update backups `/var/lib/cmnd-updates`.
The older `/opt/cmnd/releases` filesystem staging commands are separate, not the
active native-service layout.

An unprivileged management service is proxied through Apache HTTPS at
`/linux-cmnd/`. It checks a fixed GitHub repository and publishes only an explicitly
confirmed version/release-ID job. A separate root systemd worker validates assets,
backs up configuration and five databases, upgrades original tooling, checks
readiness, and attempts package rollback on failure. It never upgrades the Philips
payload or applies vendor database migrations. RF/DekTec is explicitly outside scope.
