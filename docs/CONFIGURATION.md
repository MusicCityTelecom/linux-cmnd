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

TV writes require all three controls: the target must fall within `safety.permitted_ranges`; the exact IP and stable serial/MAC must match a `[[tv.allowlist]]`; and the operation must be listed. The operator must still pass `--execute`. Never identify a TV by room number alone.

The example is loopback-only. Do not change it to a production VLAN until the callback/package routes, firewall plan, restored job state, and exact hardware allowlist are approved.
