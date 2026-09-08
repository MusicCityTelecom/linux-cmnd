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
sudo apt install ./dist/linux-cmnd_0.3.0_amd64.deb
sudo cmndctl --config /etc/cmnd/cmnd.toml doctor --source /private/cmnd/'{app}'
sudo cmndctl --config /etc/cmnd/cmnd.toml install --source /private/cmnd/'{app}' --root /opt/cmnd --release cmnd-7.5.9 --execute
```

The `.deb` pre-install script independently rejects unsupported distributions and non-amd64 systems before changing the host.

Edit `/etc/cmnd/cmnd.toml` before use. Original listener defaults are Tomcat 8080/8443, Apache 8082/8444, and database 3306. On the historical server4 layout, set database to 3307 if MariaDB still occupies 3306, then rerun `doctor`. Callback URLs must use the TV-reachable Tomcat address, not localhost.

The installer creates the `cmnd` account/directories, installs the tooling, can install checksum-verified Tomcat 9.0.121, and stages all five WARs. `--enable-service` fails before making changes. SmartInstall ignores the supplied `listener.load` property and starts schedulers, so external isolation is mandatory. Debian 13 automatic Java 17 dependency provisioning is not yet qualified. See QUALIFICATION-LAB.md for the separate, actual Java/PHP VM experiment; those lab scripts are not a production installer.
