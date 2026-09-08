# Installation

This currently installs development tooling and stages payloads, not a complete production CMND service. Use only an isolated lab first. The installer accepts Ubuntu 24.04 and Debian 12/13 on amd64; only Ubuntu has actual package-install evidence. Keep the operator-owned installer and extracted payload private.

Source installer:

```bash
sudo sh scripts/install.sh --dry-run --install-dependencies --download-tomcat --payload /private/cmnd/'{app}'
sudo sh scripts/install.sh --install-dependencies --download-tomcat --payload /private/cmnd/'{app}'
```

Offline Tomcat installation requires an operator-supplied archive and exact SHA-512:

```bash
sudo sh scripts/install.sh --tomcat-archive /private/apache-tomcat-9.0.121.tar.gz --tomcat-sha512 <exact-sha512> --payload /private/cmnd/'{app}'
```

Debian package:

```bash
sh scripts/build-deb.sh
sudo apt install ./dist/linux-cmnd_0.4.0_amd64.deb
sudo cmndctl --config /etc/cmnd/cmnd.toml doctor --source /private/cmnd/'{app}'
sudo cmndctl --config /etc/cmnd/cmnd.toml install --source /private/cmnd/'{app}' --root /opt/cmnd --release cmnd-7.5.9 --execute
```

## Complete private application staging (development)

`stage-application` consolidates the real Philips applications and native runtime
configuration into a new candidate. It does not copy customer reference backups,
initialize a database, or activate services:

```bash
sudo cmndctl --config /etc/cmnd/cmnd.toml stage-application \
  --source /private/cmnd/'{app}' \
  --tomcat-archive /private/cmnd/apache-tomcat-9.0.121.tar.gz \
  --certificate-stage /private/cmnd/certificates \
  --secrets-file /private/cmnd/secrets.json \
  --output /private/cmnd/application-next --execute
```

The certificate stage must be produced by `cmnd_linux.certificates` using the
matching configured PKCS12 password, public hostname, stable COMPUTERNAME, and
all active IPv4 addresses. The secrets JSON additionally requires
`tpvision_db_password` for PHP CMS. Every required vendor application/template/SQL
input is pinned to the inspected 7.5.9 bundle by SHA-256; Tomcat is pinned by
SHA-512. Unknown bytes are rejected, rather than silently treated as compatible.

The candidate includes all five exploded Java applications, PHP SmartCMS,
Philips templates, native configuration, CA/server certificates, and the ordered
fresh-schema SQL inputs. Stock Tomcat manager/examples are omitted. SQL is
**staged only**; it is not executed. Existing output directories are refused;
failed candidates retain a `FAILED` marker and must never be activated.

Actual private assembly passed in the disposable Ubuntu VM. The manifest records
`staged-not-activated`, input hashes, and remaining activation requirements. In
particular, Philips data belongs at `/opt/Philips`, TLS at `/etc/cmnd/tls`, CA trust
must be established for both OS/PHP and Java, and service-account permissions and
private PGT storage must be applied before startup. This command is not yet the
complete installation script requested for production use.

The `.deb` pre-install script independently rejects unsupported distributions and non-amd64 systems before changing the host.

Edit `/etc/cmnd/cmnd.toml` before use. Original listener defaults are Tomcat 8080/8443, Apache 8082/8444, and database 3306. On the historical server4 layout, set database to 3307 if MariaDB still occupies 3306, then rerun `doctor`. Callback URLs must use the TV-reachable Tomcat address, not localhost.

The installer creates the `cmnd` account/directories, installs the tooling, can install checksum-verified Tomcat 9.0.121, and stages all five WARs. `--enable-service` fails before making changes. SmartInstall ignores the supplied `listener.load` property and starts schedulers, so external isolation is mandatory. Debian 13 automatic Java 17 dependency provisioning is not yet qualified. See QUALIFICATION-LAB.md for the separate, actual Java/PHP VM experiment; those lab scripts are not a production installer.
