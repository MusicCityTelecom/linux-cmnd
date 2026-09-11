# Ubuntu Server 24.04: complete installation and operation

Use the exact release's qualification results in TEST-RESULTS.md. A working
service does not certify every TV model, licensing entitlement or Windows feature.

## 1. Prepare a dedicated machine

- Ubuntu Server 24.04 LTS, amd64/x86_64 (not ARM), with systemd.
- At least 6 GiB RAM and a 40 GiB disk; allow extra space for uploads/backups.
- Working Internet, DNS and clock synchronization, and sudo access.
- A stable IPv4 address already assigned to the machine. The installer does not
  assign addresses or modify host routes. Use an isolated evaluation network.
- No existing CMND installation or conflicting listeners. Application ports are
  8080/8443 and 8082/8444; private listeners use 3306, 9000 and 9078.

```sh
sudo apt update
sudo apt install -y python3 curl ca-certificates
ip -brief address
timedatectl status
free -h
df -h /
sudo ss -lntp
```

OpenSSH is optional for CMND, but useful if administering remotely:

```sh
sudo apt install -y openssh-server
sudo systemctl enable --now ssh
```

Do not open SSH or the CMND web interface directly to the public Internet.
Use your organization's management network/VPN and firewall policy.

For a VM, give it unique and stable SMBIOS CPU/baseboard identity. CMND computes
its license serial from actual hardware information. Do not clone a previously
activated disk or copy another installation's serial/activation state. See
[LICENSING.md](LICENSING.md) for known VM identity limitations.

## 2. Download and run the bundled installer

```sh
curl --fail --location --proto '=https' --proto-redir '=https' \
  -o bootstrap.py https://raw.githubusercontent.com/MusicCityTelecom/linux-cmnd/main/scripts/bootstrap.py
sudo python3 bootstrap.py --execute --release v0.7.0
```

Review the downloaded script first if required by policy. It asks for the
server IPv4 address and acceptance of the legacy compatibility runtime. The
Philips application bundle downloads automatically; a separate Windows EXE,
extraction step, GitHub login or vendor ZIP supplied by the user is not required.

The installer checks GitHub SHA-256/size metadata and all 26 pinned original
inputs before installing dependencies. It installs Java 17, Apache, pinned
Tomcat/MySQL and the PHP compatibility runtime, creates fresh private database
credentials and TLS material, deploys the original five Java applications and
SmartCMS, initializes schemas, starts services and checks readiness.

Allow time for downloads, image building and initial Java startup. Do not start
a second installer while the first is running. PHP 5.6 and MySQL 5.7 are legacy
dependencies: this is not an Internet-facing deployment.

For unattended installation:

```sh
sudo python3 bootstrap.py --execute --release v0.7.0 \
  --server-ip YOUR_ASSIGNED_IPV4 --accept-legacy-runtime
```

Replace the placeholder; do not use it literally. For custom ports supply a
reviewed `--config /private/cmnd.toml` instead of `--server-ip`. See
[CONFIGURATION.md](CONFIGURATION.md). In-place port migration is not automatic.
`--payload /private/vendor.zip` remains an optional local/offline override.
The installer needs online dependencies unless separately prepared; the bundle
does not make the entire installation offline-capable.

## 3. Sign in and verify

Open `https://SERVER_IP:8444/linux-cmnd/` for management and
`https://SERVER_IP:8443/SmartInstall/` for original Philips TV management.
The CMS is at `https://SERVER_IP:8444/SmartCMS/`.

Retrieve the initial administrator credentials locally over a trusted terminal:

```sh
sudo cat /var/lib/cmnd-deployment/initial-admin.json
sudo cmndctl native-health --seconds 900
sudo systemctl --no-pager status cmnd-egress cmnd-mysql cmnd-php cmnd-apache cmnd-tomcat cmnd-admin
sudo ss -lntp
```

Do not post that credential file in an issue, screenshot or support bundle.
Current tooling creates a fresh random initial administrator password; do not
assume `admin/tpvision`. Upgrades preserve existing credentials. Change passwords
through the application; the original first-login/default-password parity is
not claimed until separately qualified.

Trust only the public installation CA certificate through your organization's
trusted process. Never copy or distribute the CA private key/server private key.
Services start on boot. Restart verification should include both service status
and `native-health`, not just a listening port.

## 4. License and TV tests

The unchanged Admin > License page uses the current machine's vendor-derived
serial and a vendor-issued activation code when required. See LICENSING.md for
the fixed-host `cmndctl license-network --execute` network permission step.
The installer does not purchase or fabricate a license.

Installation grants no TV access. Start with one individually approved TV and
verify its serial/MAC, route and return path. See FIRST-TV-TEST.md. Native
Add/Detect with auto-import preserves the vendor's automatic enrollment writes;
it is not a read-only scan. Do not open an entire customer subnet to test one TV.

## 5. Update from GitHub

```sh
sudo cmndctl --updates
```

Use the `preview` channel in `/etc/linux-cmnd-management/updates.json` for
evaluation releases. Review/pause vendor jobs before confirming `INSTALL VERSION`.
Checking alone installs nothing. The updater verifies the package, backs up
configuration and all five databases, and checks readiness after restarting.
See UPDATES.md for recovery and tooling-only update boundaries: bundling the
original applications for fresh installation does not silently replace deployed
WARs or migrate an existing vendor database during a tooling update.

## 6. Troubleshoot without losing data

- Download/checksum failure: verify Internet, DNS, clock and available disk.
  Do not bypass checksum checks or substitute a different vendor version.
- Unsupported OS or existing-state refusal: use a fresh supported VM; do not
  delete the detected directories to force installation over existing data.
- Port conflict: identify the owning service with `ss`; do not stop unrelated
  production services without review.
- Startup failure: inspect `sudo journalctl -u cmnd-tomcat -u cmnd-apache -n 100`
  and private deployment diagnostics. Logs may contain secrets; redact before sharing.
- Failed update: inspect `/var/lib/cmnd-updates/status.json` and retained attempt
  backups. Do not assume rollback succeeded or repeatedly reinstall blindly.
- No TVs found: verify the scoped allowlist, isolation policy and VPN/return
  route. Do not disable the firewall to make discovery work.

For a support report, provide OS/tooling/vendor versions, the failing step and
redacted errors. Never attach customer backups, activation codes, credentials,
certificates/private keys, or complete unredacted logs to public GitHub issues.
