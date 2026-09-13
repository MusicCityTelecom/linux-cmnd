# v0.7.2 published-download qualification

Status: public GitHub fresh installation and in-place update **PASS**,
2026-09-13. This supplements the [pre-publication checks](QUALIFICATION-072.md)
without changing the published tag or release assets.

## Published artifacts

[v0.7.2](https://github.com/MusicCityTelecom/linux-cmnd/releases/tag/v0.7.2)
was published at 18:20:29 UTC as a non-draft evaluation prerelease. All ten
asset sizes and SHA-256 digests match the frozen local release build. The original
v0.7.1 release asset IDs/digests and tag target were independently checked and
remain unchanged.

- Release source commit: `0bfb15e2a8cd9d2425ecdfc9e8e85ad6936276fb`.
- Main integration: [PR #5](https://github.com/MusicCityTelecom/linux-cmnd/pull/5),
  merge commit `76555545e4614f304e2207337a11646dc74633bc`.
- Published `.deb` SHA-256:
  `099963db8c9e8b5f6d56bf5637f97d34795cbff6aecbc463dce382a77c5b43b8`.
- Published bootstrap SHA-256:
  `f2a4d97c6205a0d24c0fda0c61337f43d68020aba730cce40487e9782f408ad5`.
- Included vendor bundle: CMND 7.5.9, 472,913,047 bytes, 26 verified original
  inputs; SHA-256
  `9c7ce2a4c60a639b654203d099b93f39fa704fb1537a95fe2699ad3ad2ce0a6d`.

The complete asset inventory and remaining hashes are in the release's
`SHA256SUMS`. No application binaries were added to Git source history.

## Fresh installation using only public GitHub artifacts

A second, blank Ubuntu 24.04.4 amd64 guest was created specifically for this
test: 4 vCPU, 6 GiB RAM, 40 GiB virtual disk, approximately 38 GiB root filesystem.
Only a private test driver was staged over SSH. No installer, `.deb`, source,
vendor bundle or application payload was supplied locally to this guest.

The driver ran the README prerequisite commands, downloaded the published
v0.7.2 bootstrap over HTTPS without a GitHub token, verified its hash, then ran:

```sh
sudo python3 bootstrap.py --execute --release v0.7.2 \
  --server-ip 10.0.2.15 --accept-legacy-runtime
```

`10.0.2.15` is this isolated guest's NAT address, not an address installers
should copy. Interactive users omit the last two options and answer the prompts.
The bootstrap fetched the remaining release artifacts from public GitHub and
installed the required operating-system/container dependencies normally.

The actual installation completed at **18:29:58 UTC**, exit status 0:

- Final console ended in `FINAL STATUS: PASS` and printed the exact initial
  password from the generated root-only credential file, plus configured URLs.
- Qualification verified all 26 vendor inputs, initial port availability, all
  seven HTTPS contexts and both database migration histories.
- All seven CMND units were active and enabled; both containers were running.
- Connections succeeded to 8080/8443 and 8082/8444 at the configured address,
  and private 3306/9000/9078 on loopback.
- Installed updater rollback baseline matched the published `.deb` hash above.
- The new primary deployment log contained no unexpected `ERROR` entries.
- A repeated fresh-installer invocation was refused with recovery/update
  guidance; configuration, credentials, certificates and synthetic data were
  unchanged.
- Real Chromium logins passed for Linux management and CAS/SmartInstall;
  SmartCMS was reached through SSO. The generated CA was independently verified.
- A real reboot of this public-only fresh guest also passed: changed kernel boot
  ID, all seven units active/enabled, both containers running, all seven listeners,
  HTTPS/migrations and all three browser-login workflows restored. Configuration,
  credentials, certificates and synthetic database/content hashes were unchanged;
  IPv4/IPv6 UID-scoped egress rules recovered automatically.

The final published source archive was then downloaded independently to this
guest and verified against SHA-256
`68c81ca7dad2cc4c70057125816335411b51c1c8f2300519315853d3778cbd0e`.
Running the following from that extracted archive produced **218 tests, PASS,
no skips, 28.549 seconds**:

```sh
PYTHONPATH=src CMND_TEST_MYSQL_CASE=1 python3 -m unittest discover -s tests -q
```

## Public GitHub updater, v0.7.1 to v0.7.2

On the first isolated guest, the original hash-verified v0.7.1 tooling package
was restored without replacing its vendor runtime. The actual interactive
`sudo cmndctl --updates` command detected the published evaluation release.
After the exact `INSTALL 0.7.2` confirmation, it downloaded from public GitHub,
validated the release/package, backed up configuration/databases/prior package,
installed v0.7.2 and reported application readiness verified. No transport
fixtures or mocked fetch functions were used for this public test.

The updater's final status was `complete`, version `0.7.2`, with configuration
preserved and backup retained. The retained current package matches the published
SHA-256 above. Subsequent runtime and real Chromium login checks passed.

An actual guest reboot was then performed. After startup, all seven units,
containers, listeners, HTTPS contexts, migrations and browser logins passed
again. Hash comparisons verified unchanged configuration, credentials,
certificates, synthetic CMS content and synthetic database data. IPv4/IPv6
UID-scoped egress protection recovered; unrelated firewall sentinel rules
remained present.

## Boundaries

The [218-test Ubuntu suite](QUALIFICATION-072.md#automated-evidence), package
lifecycle tests, low-disk rejection and earlier candidate checks are separately
recorded. Public-download evidence here uses the exact final published assets.

These were disposable, no-TV guests on server4; existing signer, Apache,
database and host networking were not altered. The user's VM being reinstalled
was not used for these publication checks. Test logs, credentials, generated
keys/certificates, screenshots and database backups remain private.

This is Ubuntu installation/update qualification, not certification of every
vendor feature. No physical TV was contacted. RF/DekTec/MGate, issued license
activation, Debian runtime and complete Windows-feature parity remain unverified.
The original v0.7.1, decompiled references and separate native product were not
modified by this repair.

## Source and build handoff

Repair branch: `codex/v0.7.2-installer-readiness`. Release HEAD is
`0bfb15e2a8cd9d2425ecdfc9e8e85ad6936276fb`; this post-publication record is a
later documentation-only commit, not a replacement release build.

Commits forming the release:

- `bcf81ef2`: installer completion, early storage checks, accurate bounded
  readiness logging, package-account creation and regression tests.
- `a88841df`: fresh-install and v0.7.1 recovery/upgrade instructions.
- `0bfb15e2`: actual Ubuntu installation, lifecycle, browser, reboot and
  update-worker acceptance record.

Exact files changed from the existing main baseline `d33748d7` to release HEAD:

```text
CHANGELOG.md
README.md
VERSION
docs/EVALUATION-GUIDE.md
docs/INSTALLER-072.md
docs/QUALIFICATION-072.md
docs/QUICK-INSTALL.md
docs/TEST-RESULTS.md
docs/UBUNTU-24.04.md
docs/UPDATES.md
packaging/debian/postinst
pyproject.toml
scripts/bootstrap.py
scripts/install-native.sh
scripts/install.sh
src/cmnd_linux/__init__.py
src/cmnd_linux/cli.py
src/cmnd_linux/install_log.py
src/cmnd_linux/install_storage.py
src/cmnd_linux/install_summary.py
src/cmnd_linux/native_deploy.py
tests/test_install_experience.py
```

The post-publication documentation update adds this record and adjusts README,
QUALIFICATION-072 and TEST-RESULTS links/status only. Runtime, packaging and
published assets remain frozen. `scripts/build_release.py` completed with its
clean-committed-source and pinned-vendor checks; reproducible package checks
passed for `linux-cmnd`, `0.7.2`, `amd64`. `git diff --check` passed.

### Complete published SHA-256 inventory

```text
099963db8c9e8b5f6d56bf5637f97d34795cbff6aecbc463dce382a77c5b43b8  linux-cmnd_0.7.2_amd64.deb
31a764cb7acb973b581982962af61d4cedf6bee34480858b0cda585d28e2dd25  install.sh
6690524d2ca70fa808f871e56ea5dd2eba555c93a0ecc5220f42814fcc55fa03  linux-cmnd-update.json
68c81ca7dad2cc4c70057125816335411b51c1c8f2300519315853d3778cbd0e  linux-cmnd-0.7.2-source.tar.gz
9bbf2d100c0e710f6edac92f88ba87ba1f493a10960f9fb2726d17e6a22bb3f8  EVALUATION-GUIDE.md
03585f70f58dfae604e97430087bf02b838bb462d27e2d74e039deb4ebac8833  cmnd.example.toml
f2a4d97c6205a0d24c0fda0c61337f43d68020aba730cce40487e9782f408ad5  bootstrap.py
9c7ce2a4c60a639b654203d099b93f39fa704fb1537a95fe2699ad3ad2ce0a6d  cmnd-vendor-7.5.9.zip
58de18a94438805441823a77f6144712f5c5464f73eb1df603dd3dd602626a9e  vendor-bundle.json
cd0e89239d6ab5aa7da9482e3e0d2941716573d90bc82860800133d5a6eda5aa  SHA256SUMS
```
