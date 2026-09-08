# Installation

Use only an isolated lab first. The installer accepts Ubuntu 24.04 and Debian 12/13 on amd64. Keep the operator-owned installer and extracted payload outside the checkout.

Source installer:

```bash
sudo ./scripts/install.sh --dry-run --install-dependencies --download-tomcat --payload /private/cmnd/'{app}'
sudo ./scripts/install.sh --install-dependencies --download-tomcat --payload /private/cmnd/'{app}'
```

Offline Tomcat installation requires an operator-supplied archive and exact SHA-512:

```bash
sudo ./scripts/install.sh --tomcat-archive /private/apache-tomcat-9.0.121.tar.gz --tomcat-sha512 <exact-sha512> --payload /private/cmnd/'{app}'
```

Debian package:

```bash
./scripts/build-deb.sh
sudo apt install ./dist/linux-cmnd_0.2.0_amd64.deb
sudo cmndctl --config /etc/cmnd/cmnd.toml doctor --source /private/cmnd/'{app}'
sudo cmndctl --config /etc/cmnd/cmnd.toml install --source /private/cmnd/'{app}' --root /opt/cmnd --release cmnd-7.5.9 --execute
```

The `.deb` pre-install script independently rejects unsupported distributions and non-amd64 systems before changing the host.

Edit `/etc/cmnd/cmnd.toml` before use. Original listener defaults are Tomcat 8080/8443, Apache 8082/8444, and database 3306. On the historical server4 layout, set database to 3307 if MariaDB still occupies 3306, then rerun `doctor`. Callback URLs must use the TV-reachable Tomcat address, not localhost.

The installer creates the `cmnd` account/directories, installs the tooling, can install checksum-verified Tomcat 9.0.121, and stages all five WARs. Vendor services remain disabled because SmartInstall's network listener override must be verified after expansion. Database import, Apache/PHP adaptation, and systemd activation remain qualification gates.
