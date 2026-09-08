# Architecture

The target keeps vendor Java applications on Tomcat 9 and uppercase `/SmartCMS` on Apache/PHP, backed by an isolated MySQL compatibility database. Original `cmndctl` code performs guarded import, release lifecycle, diagnostics, and protocol qualification. Vendor payload lives outside Git and is copied into versioned runtime releases.

Observed TV flow is bidirectional: CMND sends JSON over HTTP `POST /WIXP` to TV TCP 9079; TVs post status/poll messages to `/SmartInstall/webservices.jsp` on CMND TCP 8080 and fetch clone ZIPs below `/SmartInstall/Profile/Clone/`. A command acceptance, package fetch, reported completion, and setting readback are distinct states.

Runtime separation: source checkout; immutable `/opt/cmnd/releases/<release>`; selected release marker; protected `/etc/cmnd`; writable `/var/lib/cmnd`; logs `/var/log/cmnd`; backups outside releases. Missing DekTec hardware must be tested as optional before claiming vendor runtime success.
