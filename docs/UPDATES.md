# Command-line GitHub updates

Development 0.6.0 adds this entry point to the installed Linux executable:

```sh
sudo cmndctl --updates
```

The command checks `MusicCityTelecom/linux-cmnd` on GitHub, reports the installed
and available versions, and prompts you to type `INSTALL VERSION` before
installation. An empty answer, a different version, or a non-interactive input
does not install anything. Without root it reports availability and asks you to
rerun with sudo. Network failures are reported as failures, not “up to date.”

The repository is public; no token is needed. The existing configuration at
`/etc/linux-cmnd-management/updates.json` controls enabled/disabled state and
`preview` or `stable` channel. Evaluation prereleases require `preview`.

Before confirming, review and pause vendor TV jobs. The updater sends no TV
commands itself, but restarting vendor services can resume scheduled or queued
TV operations. Confirmation explicitly warns about that behavior.

The root worker rechecks the release ID/version, validates the compatibility
manifest, verifies GitHub asset SHA-256/size and Debian package identity, and
retains the current `.deb`, configuration and five-database backup. It restarts
the services and requires readiness before reporting success. Failures attempt
package rollback and report whether runtime recovery actually succeeded. Inspect
`/var/lib/cmnd-updates/status.json` and the private attempt logs on failure.

The terminal entry point runs synchronously under the same worker lock as the
existing queued updater. It does not overwrite or consume another pending GUI
request. It adds no arbitrary repository, URL, shell-command or installation
script input. No Philips GUI pages are changed by this feature.

## Scope and versions

This is a **tooling-only** update process. It preserves site data and does not
silently replace vendor WARs, migrate databases, rebuild the PHP image or
regenerate a running site's native configuration. New fresh-installer runtime
fixes require a separately qualified migration or fresh installation; changing
the displayed tooling version is not proof those runtime fixes were applied.

The switch is not present in v0.5.0. That older version supports the read-only
`cmndctl update-check` command and its existing management updater. Publication
of the next release is gated on the recorded Linux qualification; source code
presence alone is not a release or successful upgrade.

For automation/read-only JSON output, use:

```sh
sudo cmndctl update-check
```
