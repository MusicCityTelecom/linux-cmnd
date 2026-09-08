# Configuration

The installed configuration is `/etc/cmnd/cmnd.toml`. Validate every change before restarting anything:

```bash
sudo cmndctl --config /etc/cmnd/cmnd.toml validate-config
sudo cmndctl --config /etc/cmnd/cmnd.toml doctor --source /private/cmnd/'{app}'
```

Original CMND defaults are:

```toml
[ports]
tomcat_http = 8080
tomcat_https = 8443
apache_http = 8082
apache_https = 8444
database = 3306
```

Change a value only if `doctor` reports a conflict or the approved network design requires it. Update the corresponding Tomcat, Apache, JDBC, CAS service URL, and TV-reachable callback settings as one change. On the historical server4 layout, the compatibility MySQL container used host port 3307 because MariaDB occupied 3306; set `database = 3307` for that verified layout.

Render service environment files atomically after validation:

```bash
sudo cmndctl render-runtime-config --output /etc/cmnd
sudo cmndctl render-runtime-config --output /etc/cmnd --execute
```

`network.bind` controls the management/application listener. `network.callback_base_url` must be an address the TV can route back to; `127.0.0.1`, a Docker bridge, or server4's management-only address will not work for TVs on another network.

## Native service configuration staging

The environment-file renderer above is not sufficient to reconfigure the vendor
applications. The native renderer stages actual Tomcat XML, ROOT callback
rewrites, standalone Apache configuration, PHP-FPM configuration, and a CLI-only
Drupal CAS path bootstrap from the same TOML ports:

```bash
sudo cmndctl --config /etc/cmnd/cmnd.toml stage-native-config \
  --secrets-file /private/cmnd/secrets.json --output /private/cmnd/native-config-next
sudo cmndctl --config /etc/cmnd/cmnd.toml stage-native-config \
  --secrets-file /private/cmnd/secrets.json --output /private/cmnd/native-config-next --execute
```

The secrets JSON uses the keys in `vendor_config.REQUIRED_SECRETS`; values must
match the separately provisioned database accounts and certificate passwords.
Do not put secrets in command arguments. On Linux the input file must deny group
and other access. The output directory must not already exist; it is mode 0700
and generated files are mode 0600. The manifest contains hashes, not passwords.

The public callback URL must be an origin (no credentials, query, or path) whose
port equals `tomcat_http` or `tomcat_https` for its scheme. Isolated mode requires
a loopback bind. Native IPv6 deployment remains unqualified and is rejected.
The renderer retains Philips `ReloadProtocol` and the `tomcat` PKCS12 alias.
Apache is standalone: it does not import another site's configuration or replace
the distribution-wide `ports.conf`. Sensitive configuration files and executable
PHP uploads under `sites/default/files` are denied.

Actual Apache and PHP 5.6 FPM syntax checks passed in the Ubuntu qualification VM.
Staging does **not** install certificates, initialize databases, update WARs,
start services, or apply a firewall. Do not copy this stage over a running system
as a partial port change. Coordinated activation remains an installer acceptance
gate. The CLI identity allowlist does not automatically constrain the unmodified
Philips GUI or background schedulers; enforce separately reviewed runtime egress
isolation before starting vendor applications against restored data.

TV writes require all three controls: the target must fall within `safety.permitted_ranges`; the exact IP and stable serial/MAC must match a `[[tv.allowlist]]`; and the operation must be listed. The operator must still pass `--execute`. Never identify a TV by room number alone.

The example is loopback-only. Do not change it to a production VLAN until the callback/package routes, firewall plan, restored job state, and exact hardware allowlist are approved.
