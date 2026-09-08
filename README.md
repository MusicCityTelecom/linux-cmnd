# Linux CMND

Original Music City Telecom tooling for qualifying and deploying operator-supplied Philips CMND applications on Ubuntu 24.04 x86_64. Vendor software is imported locally and is **not** distributed by this repository. This project is not affiliated with or endorsed by Philips.

Current tooling version: `0.4.0` (development). Target vendor bundle: CMND installer labeled `7.5.9`; its extracted `buildnr.txt` says `7.5.10.3168`. Vendor components have their own internal versions; these are not the tooling version.

The tooling installer accepts Ubuntu 24.04 or Debian 12/13 amd64. Build with `sh scripts/build-deb.sh`. The `0.4.0` tooling package has been installed on Ubuntu 24.04; Debian runtime qualification remains pending. This is not yet a complete production installer. See [installation](docs/INSTALL.md), [configuration](docs/CONFIGURATION.md), and [actual lab evidence](docs/QUALIFICATION-LAB.md).

## What works now

- Safe ZIP extraction with Windows separator normalization, traversal/symlink/collision/expansion guards, and rollback of partial file writes.
- SHA-256 artifact inventory.
- Capture-derived WIXP discovery, power, and clone command encoding with response correlation.
- Bounded unicast scan and identity-revalidated addition to a separate tooling inventory. This does not silently modify Philips database tables or grant control permissions.
- Exact, secret-aware rendering of all five operator-supplied WARs and the separate PHP SmartCMS application.
- Private staging of native Tomcat/Apache/PHP configuration with callback-port consistency checks; actual Apache and PHP-FPM syntax validated in the Ubuntu VM. This is not automatic service activation.
- Native certificate preprovisioning with validated CA preservation, and complete private application assembly from the supplied vendor payload. The actual assembled candidate is inactive; clean installation and recovery remain acceptance gates.
- Actual Ubuntu lab qualification of Philips browser login, TV scan/import, native room delivery/readback/persistence and Standby against a synthetic TV, plus CMS SSO/content-editor navigation. These are not physical-TV or complete deployment certifications.
- Persistent lab egress restrictions survived an actual VM reboot; guarded manual resume restored CAS/CMS login and synthetic room/power control. Full automatic installer recovery remains unfinished.
- Native CMS content cloning/local export produced a validated ZIP after provisioning the vendor Linux worker's PCNTL dependency. Thumbnail generation and publication to TVs remain unqualified.
- Identity/IP/operation allowlists and mandatory `--execute` for TV writes.
- Room-ID clone package creation from an operator-supplied model template; room IDs remain strings.
- Restricted ZIP package serving with HTTP byte-range support.
- Synthetic TV endpoint for discovery, power, clone download, failures, and asynchronous state inspection.
- Rerunnable release staging, dry-run, status, filesystem backup/clean restore, rollback selection, and preserve-data uninstall marker.

This is not production-ready. The genuine five-WAR stack runs in a disposable Ubuntu VM; CAS rejected an incorrect password and accepted a generated account into the actual TV-management UI with TLS validation enabled. Fresh SmartInstall and smartcontrol migrations reached their expected versions. The native Add/Detect workflow imported a synthetic TV into Philips inventory, and Apache/PHP SmartCMS SSO reaches Websites overview after Linux path corrections. Complete delivery/readback, CMS content workflows, Windows restore, recovery, and physical TVs still require qualification. See [test results](docs/TEST-RESULTS.md) and [limitations](docs/KNOWN-LIMITATIONS.md).

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
