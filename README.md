# Linux CMND

Original Music City Telecom tooling for qualifying and deploying operator-supplied Philips CMND applications on Ubuntu 24.04 x86_64. Vendor software is imported locally and is **not** distributed by this repository. This project is not affiliated with or endorsed by Philips.

Current release: `0.2.0` (development). Target vendor bundle: CMND installer labeled `7.5.9`; its extracted `buildnr.txt` says `7.5.10.3168`, so application/database coherence is not yet qualified.

Install on Ubuntu 24.04 or Debian 12/13 amd64 using `scripts/install.sh`, or build/install `dist/linux-cmnd_0.2.0_amd64.deb`. See [installation](docs/INSTALL.md) and [configuration](docs/CONFIGURATION.md). Original ports are the defaults and are configurable in `/etc/cmnd/cmnd.toml`.

## What works now

- Safe ZIP extraction with Windows separator normalization, traversal/symlink/collision/expansion guards, and rollback of partial file writes.
- SHA-256 artifact inventory.
- Capture-derived WIXP discovery, power, and clone command encoding with response correlation.
- Identity/IP/operation allowlists and mandatory `--execute` for TV writes.
- Room-ID clone package creation from an operator-supplied model template; room IDs remain strings.
- Restricted ZIP package serving with HTTP byte-range support.
- Synthetic TV endpoint for discovery, power, clone download, failures, and asynchronous state inspection.
- Rerunnable release staging, dry-run, status, filesystem backup/clean restore, rollback selection, and preserve-data uninstall marker.

This is not production-ready. Vendor applications have not been started on Linux; login, databases, PHP/Apache, browser workflows, callbacks, migration, and physical-TV application/readback remain unqualified. See [test results](docs/TEST-RESULTS.md) and [limitations](docs/KNOWN-LIMITATIONS.md).

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
