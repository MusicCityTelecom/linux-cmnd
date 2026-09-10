# Configuration

The installed configuration is `/etc/cmnd/cmnd.toml`. Validate every change before restarting anything:

For `cmnd-install`, choose ports, bind address and callback origin **before fresh
installation**; the installer applies them together to the native applications.
Editing the TOML on an already running deployment is not sufficient. Automated
in-place port/hostname reconfiguration and certificate rotation are not yet
qualified; do not restart with a partially updated configuration.

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

## CMS resource settings (0.6 series)

The fresh Linux renderer now derives PHP upload limits from the inspected Windows
7.5.9 configuration. These changes are absent from v0.5.0; a tooling-only update
does not rebuild the PHP image
or overwrite an existing site's native configuration.

```toml
[cms]
upload_limit_mb = 8096
memory_limit_mb = 256
execution_timeout_seconds = 0
```

The upload/post limit matches the Windows setting (8096 MiB). Temporary uploads
are stored privately on disk at `/var/lib/cmnd/php-uploads`, not in the CMS web
root or the PHP container's 256 MiB `/tmp` memory filesystem. Preflight reserves
deployment space plus room for two configured uploads. This is a configured
ceiling, not proof that an 8 GiB end-to-end upload has passed qualification.

The Windows PHP memory setting is also 8096 MiB, but it cannot safely be copied
into a 1 GiB Linux PHP container on a 6 GiB evaluation host. Linux instead uses
two workers at up to 256 MiB each, leaving room for media helpers. Configured
memory may be 64..256 MiB; upload limits may be 1..8096 MiB; execution timeout may
be 0..86400 seconds (0 retains Windows' unlimited PHP script execution setting).
Larger per-request memory needs a separately qualified container/host resource
profile, not merely a larger PHP setting. Apache's CMS request-body cap is
delegated to PHP and its backend response timeout is 900 seconds; the separate
management proxy retains its shorter timeout.

Default listener ports remain unchanged. TOML changes are applied during fresh
deployment, not automatically to running services.

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
as a partial port change. Fresh installer activation has Ubuntu runtime evidence;
in-place reconfiguration remains an acceptance gate. The CLI identity allowlist does not automatically constrain the unmodified
Philips GUI or background schedulers; enforce separately reviewed runtime egress
isolation before starting vendor applications against restored data.

TV writes require all three controls: the target must fall within `safety.permitted_ranges`; the exact IP and stable serial/MAC must match a `[[tv.allowlist]]`; and the operation must be listed. The operator must still pass `--execute`. Never identify a TV by room number alone.

The example is loopback-only. Do not change it to a production VLAN until the callback/package routes, firewall plan, restored job state, and exact hardware allowlist are approved.
