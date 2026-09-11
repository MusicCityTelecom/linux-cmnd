# Guided installation from public GitHub

Use a fresh, dedicated Ubuntu 24.04 amd64 server or VM with at least 6 GiB RAM
and a 40 GiB disk. The 0.6.0 preflight requires approximately 26 GiB free after
dependency preparation at the default CMS upload limit (10 GiB plus twice
8096 MiB); allow additional space for your content and backups. Do not run on a
production host or an existing CMND installation. Installed 0.6.0 systems use
`sudo cmndctl --updates` for confirmed tooling updates; the fresh installer
deliberately refuses to overwrite them. See [UPDATES.md](UPDATES.md) for the
older 0.5.0 update entry point and runtime-migration limitations.

## Two commands

```sh
curl --fail --location --proto '=https' --proto-redir '=https' \
  -o bootstrap.py https://raw.githubusercontent.com/MusicCityTelecom/linux-cmnd/main/scripts/bootstrap.py
sudo python3 bootstrap.py --execute
```

You are executing code from Music City Telecom's repository as root: inspect
the downloaded file first if required by your organization's policy. Do not
pipe it directly into a shell. The bootstrap needs Python 3.11+, curl for the
initial command, and the system CA trust store. On a minimal supported image
missing these prerequisites, install them first:

```sh
sudo apt-get update
sudo apt-get install -y python3 curl ca-certificates
```

Ubuntu **21.04 is not supported**. Its default Python 3.9 cannot run the
bootstrap, which requires Python 3.11 or newer. The current repository bootstrap
checks this before importing dependencies and exits without installation changes.
Older release copies may instead report `ModuleNotFoundError: No module named
'tomllib'`. Use a fresh Ubuntu 24.04 system; installing a Python backport does not
make an unsupported Ubuntu release supported. Do not replace the host's system
Python or install a similarly named package from pip to bypass this check.

The script prompts for:

The Philips application bundle downloads automatically; --payload is optional.

1. A stable IPv4 address already assigned to this Linux host. No address or
   route is added. Choose `127.0.0.1` only for access from the server itself.
2. Explicit acceptance of PHP 5.6/MySQL 5.7 legacy evaluation dependencies.

It then selects the highest compatible published `0.x` release (including
evaluation prereleases), verifies SHA-256 and size for the `.deb` and full
installer against GitHub's TLS-protected metadata, validates package identity,
and verifies all 26 original vendor inputs before dependency installation.
It installs the original tooling plus Apache, Java 17, Docker, pinned Tomcat and
MySQL, builds the PHP image, generates private credentials/certificates,
initializes the five CMND schemas, and starts the native services. Download
failures and integrity mismatches stop installation; no arbitrary mirror is used.

GitHub serves the tooling and original Philips application bundle, not customer
data, site credentials, or existing activation state.
No GitHub account/token is needed now that the repository is public. Release
checksums establish integrity relative to GitHub metadata, not an independent
offline code-signing identity. Downloads require working DNS/HTTPS access to
GitHub, the distribution repositories, Apache archives, and container registries.

Configuration is saved to `/etc/cmnd/cmnd.toml`. Default ports remain 8080/8443
(Java), 8082/8444 (CMS), and 3306 (private database); 9000 and 9078 are private
internal listeners. Services start at boot. Initial installation grants no TV
egress and has an empty device allowlist. It never scans or contacts a TV.

## Optional local/offline payload override

From the source checkout beside your original **extracted** installer payload:

```sh
python3 scripts/prepare-vendor.py --source /path/to/original-extracted-app \
  --output /private/cmnd-vendor-7.5.9.zip --execute
```

On Windows, use `py -3` instead of `python3` if appropriate, and quote paths with
spaces. Use an ACL-protected output directory. The script verifies the pinned
original files and copies only those 26 inputs, then verifies the ZIP contents.
It refuses to overwrite an existing output and never collects databases,
locally generated certificates, site credentials, reference backups, room content,
or firmware. Original vendor-distributed defaults are retained unchanged. The
output parent directory must already exist. Omit `--execute` for validation only.

Transfer that ZIP directly to the evaluator's Linux host, for example using SCP.
Never commit binaries to Git history. Only the approved original-input bundle
may be published as a vendor release asset; never upload a live installation. An
installed Windows application's WARs may have been rewritten by setup; if hashes
differ, use the original installer extraction, not modified runtime files. The
helper fails closed on any missing/changed input rather than accepting a version
label as proof. The bootstrap accepts this ZIP directly; manual extraction on
Linux is unnecessary. Extra files in a prepared ZIP are rejected.

## Unattended installation and version pinning

```sh
sudo python3 bootstrap.py --execute --accept-legacy-runtime \
  --server-ip 192.0.2.10 --release v0.7.0
```

Replace the example IP with an address actually assigned to your server. Omit
`--release` to select the newest compatible evaluation release. Use
`--channel stable` only when a stable release exists; currently these releases
are evaluation prereleases. A numeric SemVer tag is required. No published
release or Git history is rewritten when a new version is added.

The bootstrap is newly published on `main`; v0.5.0 predates it and does not
contain `bootstrap.py`. To pin the bootstrap itself, replace the raw URL's `main`
with its reviewed full Git commit ID. Future releases will include the bootstrap
and its SHA-256 in the release assets. A raw `main` download intentionally follows
the latest reviewed source and should not be treated as immutable. The next
tooling version is 0.7.0; check GitHub releases for its publication status. A
draft is never installable through the public bootstrap. See TEST-RESULTS.md for
the exact qualification checkpoint rather than assuming a version proves parity.

For custom ports, use `--config /private/reviewed.toml` instead of `--server-ip`.
The config must use isolated/lab mode, an empty allowlist, and a callback address
matching the selected bind IP and Tomcat port. It is validated before download
and again using the selected release before dependency installation. Existing
site config is not edited in place; the explicitly supplied config is installed
to `/etc/cmnd/cmnd.toml`. See [configuration](CONFIGURATION.md).

`--prepare-only` downloads/builds dependencies without initializing CMND; rerun
without it to finish. `--dry-run` overrides `--execute` and performs no downloads
or changes. For offline dependency overrides, use the full `install.sh` from the
same release as described in [the evaluation guide](EVALUATION-GUIDE.md).

Ubuntu 24.04 is the runtime-qualified target. Debian 12/13 are detected; Debian
13 needs an operator-supplied Java 17 runtime via `--java-home /absolute/path`.
Debian full-runtime qualification remains pending. Do not substitute Java 21.

## After installation

Cold Java startup can take about 11 minutes on the 6 GiB VM. The bootstrap waits
for application readiness before reporting success. Open the printed management
URL (`https://SERVER_IP:8444/linux-cmnd/` with default ports). Credentials are
root-only at `/var/lib/cmnd-deployment/initial-admin.json`; do not share them in
tickets or GitHub. Trust only the public CA `/etc/cmnd/tls/ca.crt` in your test
browser; never distribute private keys. The portal links to the original Philips
TV/channel/settings UI and SmartCMS and checks GitHub for tooling updates.

Use [the first-TV test](FIRST-TV-TEST.md) for receive-only clone download before
any push. Simulator/runtime readiness is not physical-TV certification. Full
Windows backup recovery and feature parity remain unqualified. Review
[test results](TEST-RESULTS.md) and [limitations](KNOWN-LIMITATIONS.md).

If native installation fails, private state/logs are preserved and fresh install
refuses to overwrite them. Diagnose in place or restore a disposable pre-install
VM snapshot; never delete a customer installation to retry. A dependency-only
failure may leave installed packages/images for a later retry.
