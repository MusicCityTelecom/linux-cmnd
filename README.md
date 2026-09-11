# Linux CMND

Philips CMND applications running on Ubuntu 24.04 x86_64, with Music City Telecom Linux deployment/update tooling. Releases include the original vendor applications; no separately obtained payload is required. Vendor ownership and notices are preserved; no Philips endorsement is implied.

Tooling version: `0.7.0` (evaluation). Check [GitHub releases](https://github.com/MusicCityTelecom/linux-cmnd/releases) for publication status; the bootstrap installs only published releases, never a development checkout or draft. The unpublished `0.6.0` build is the clean-install and upgrade-test baseline. Target vendor bundle: CMND installer labeled `7.5.9`; its extracted `buildnr.txt` says `7.5.10.3168`. Vendor components have their own internal versions; these are not the tooling version.

## Quick installation

Start with the [complete Ubuntu 24.04 walkthrough](docs/UBUNTU-24.04.md), including
prerequisites, optional SSH, first login, licensing, updates and troubleshooting.

On a fresh Ubuntu 24.04 amd64 server/VM (6 GiB RAM, 40 GiB disk recommended),
download one script and run its guided installer. Python 3.11+ and trusted HTTPS
certificates are required; Ubuntu Server normally already supplies them.

```sh
curl --fail --location --proto '=https' --proto-redir '=https' \
  -o bootstrap.py https://raw.githubusercontent.com/MusicCityTelecom/linux-cmnd/main/scripts/bootstrap.py
sudo python3 bootstrap.py --execute
```

The script automatically downloads the Philips applications and asks for this server's
stable IPv4 address, then asks you to accept the legacy evaluation runtime. It
downloads the newest compatible GitHub evaluation release, checks the package
and installer against GitHub's SHA-256 digests, installs dependencies, generates
configuration with the original default ports, and starts CMND. No git checkout,
manual `.deb` download, GitHub login, or hand-written config is needed.

Philips applications are included as a release asset, outside Git source history.
`--payload` remains an optional local override. Customer backups, site credentials
and existing activation state are never installer inputs. See
[licensing and installation identity](docs/LICENSING.md).
No physical TVs are authorized by installation.

See [quick-install options](docs/QUICK-INSTALL.md) for unattended installs,
release pinning, Debian requirements, and custom ports. Without `--execute`, the
bootstrap prints a plan and makes no downloads or changes.

## Qualification status

The 0.6 series adds a terminal update switch, without changing Philips GUI
pages: `sudo cmndctl --updates`. It checks the fixed GitHub repository and asks
for an exact version confirmation before handing off to the verified update
worker. Published v0.5.0 has `cmndctl update-check`; the new switch is not in that
older release. See [update instructions](docs/UPDATES.md) and the
[Windows-to-Linux conversion map](docs/WINDOWS-LINUX-MAP.md).

The full installer and `.deb` accept Ubuntu 24.04 or Debian 12/13 amd64; Debian 13 needs an operator-supplied Java 17 runtime. The new installer has completed a fresh Ubuntu VM deployment of the five Java applications and SmartCMS, with HTTPS/migration readiness, automatic startup after reboot, real browser login, and native CMS local export. Debian runtime qualification remains pending. Start with the [evaluation installation guide](docs/EVALUATION-GUIDE.md), [configuration](docs/CONFIGURATION.md), and [test results](docs/TEST-RESULTS.md).

This is **not full Windows-feature parity or production certification**. RF/DekTec/modulator workflows are explicitly out of scope. The supplied MGate archive is Windows-only; additional MGate-dependent IP transport-stream playout is not qualified. Physical-TV qualification and production Windows-backup restore remain incomplete. GUI updates have passed end-to-end installation and rollback with synthetic release transport; real GitHub transport qualification is recorded separately. Vendor applications are included; customer reference archives are not.

## What works now

- Safe ZIP extraction with Windows separator normalization, traversal/symlink/collision/expansion guards, and rollback of partial file writes.
- SHA-256 artifact inventory.
- Capture-derived WIXP discovery, power, and clone command encoding with response correlation.
- Bounded unicast scan and identity-revalidated addition to a separate tooling inventory. This does not silently modify Philips database tables or grant control permissions.
- Exact, secret-aware rendering of all five operator-supplied WARs and the separate PHP SmartCMS application.
- Full native-service installation from locally supplied, hash-verified Philips payloads; original default ports are configured together from TOML.
- Native certificate preprovisioning, private service accounts, guarded database initialization, startup readiness checks, and boot-enabled services.
- Actual Ubuntu lab qualification of Philips browser login, TV scan/import, native room delivery/readback/persistence and Standby against a synthetic TV, plus CMS SSO/content-editor navigation. These are not physical-TV or complete deployment certifications.
- Persistent service-identity isolation and automatic startup passed a real reboot in the fresh installer VM. No physical-TV egress is granted by installation.
- Native CMS editing and local export work in the fresh installer VM; the retained synthetic site exported a CRC-valid ZIP with generated HTML and metadata after reboot. Meaningful thumbnails and TV publication remain unqualified.
- A separate authenticated management page checks GitHub at startup and requires explicit confirmation before queuing a release update. The worker verifies package identity/integrity and retains configuration, database, and prior-package backups. The CLI completed an actual public GitHub upgrade from 0.6.0 to 0.6.1, preserving configuration/site data and passing readiness checks; the GUI transport tests remain separately scoped.
- Identity/IP/operation allowlists and mandatory `--execute` for TV writes.
- Receive-only TV clone export with a separate permission, identity rechecks,
  private bounded uploads, and ZIP validation. The [first TV test](docs/FIRST-TV-TEST.md)
  must download from one selected TV before any push; hardware evidence is pending.
- Room-ID clone package creation from an operator-supplied model template; room IDs remain strings.
- Restricted ZIP package serving with HTTP byte-range support.
- Synthetic TV endpoint for discovery, power, clone download, failures, and asynchronous state inspection.
- Rerunnable release staging, dry-run, status, filesystem backup/clean restore, rollback selection, and preserve-data uninstall marker.

Cold Java startup took about 11 minutes on the 6 GiB test VM. The management page becomes available earlier; use `cmndctl native-health --seconds 900` before testing vendor applications. Simulator results from the earlier lab do not constitute physical-TV evidence. See [test results](docs/TEST-RESULTS.md) and [limitations](docs/KNOWN-LIMITATIONS.md).

## Developer quick start

Ubuntu 24.04 includes a suitable Python version:

```bash
python3 -m venv .venv
. .venv/bin/activate
python -m pip install -e .
python -m unittest discover -s tests -v
cmndctl --config config/cmnd.example.toml doctor --source /private/staging/'{app}'
cmndctl inventory /private/staging --output /private/reports/inventory.json
cmndctl install --source /private/staging/'{app}' --root /tmp/cmnd-lab --release 7.5.9-lab --dry-run
```

The CLI uses dry-run behavior unless `--execute` is supplied. `--dry-run` is accepted for explicit scripts:

```bash
cmndctl install --source /private/staging/'{app}' --root /tmp/cmnd-lab --release 7.5.9-lab
cmndctl install --source /private/staging/'{app}' --root /tmp/cmnd-lab --release 7.5.9-lab --execute
```

Simulator example:

```bash
cmndctl simulator --bind 127.0.0.1 --port 9079
cmndctl discover 127.0.0.1
cmndctl power 127.0.0.1 Standby --identity SIMULATOR00000001 --execute
```

Never reuse the example allowlist for real hardware. Follow [INSTALL.md](docs/INSTALL.md), [NETWORKING.md](docs/NETWORKING.md), and [SECURITY.md](docs/SECURITY.md).

## Versioning

Our source and release wrapper use Semantic Versioning. Before `1.0.0`, interfaces may change. Philips CMND compatibility is recorded independently in `docs/ARTIFACTS-AND-VERSIONS.md`; a wrapper version never asserts that a vendor release is qualified.

## Ownership, license and disclaimer

Philips, TP Vision/TPVision, CMND, their logos, and the original CMND software
are the property of their respective owners and licensors. They are **not owned
by Tommy Heggie, TechFinity Communications, or Music City Telecom**. The PHILIPS
name and shield are trademarks of Koninklijke Philips N.V.; see the
[official Philips trademark information](https://www.philips.com/a-w/about/innovation/ips/philips-general-trademark-use-guidelines.html).
All original vendor copyright, trademark and license notices remain applicable.

Tommy Heggie, TechFinity Communications and Music City Telecom provide this
Linux adaptation and its deployment tooling **free of charge**. No fee is
charged by this project for downloading or using this release. This does not
waive vendor licensing or activation requirements, or third-party service costs.
The [Apache 2.0 tooling license](LICENSE) applies only to original project code,
not to bundled Philips/TPVision software or other third-party components. Those
components retain their own licenses; inclusion does not transfer ownership,
relicense them, or grant rights beyond their applicable terms.

Names and logos identify compatibility and original products. This project does
not claim vendor certification, endorsement, sponsorship, or official support.
Only unmodified, hash-verified inputs from the original vendor archive are
included in the vendor bundle. Hardware-generated identities, activation state,
customer backups, site credentials and locally generated private keys are not
distributed.

The Linux adaptation and tooling are provided **AS IS**, without warranties of
merchantability, fitness for a particular purpose, non-infringement, uninterrupted
operation, or compatibility with every device. To the maximum extent permitted
by applicable law, Tommy Heggie, TechFinity Communications, Music City Telecom
and project contributors accept no liability for losses resulting from its use,
including data loss, service interruption or device misconfiguration. Nothing
here excludes rights or liability that applicable law does not permit excluding,
or changes the original vendors' separate terms.

Use the release-specific [test results](docs/TEST-RESULTS.md), keep backups, and
qualify an isolated test installation before production use. Free availability
is not a guarantee of complete Windows parity or production readiness.
