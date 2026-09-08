# Networking

Historical CMND `192.168.254.20` and proposed server4 `10.1.10.14` are on different networks. Required directions from capture evidence are CMND→TV TCP/9079, TV→CMND TCP/8080 callbacks and package downloads, and browser/operator→CMND application ports. HTTPS 8443/8444 require separate runtime qualification.

Configure management bind, permitted target ranges, stable device allowlist, and TV-reachable callback URL independently. Do not advertise localhost or a container bridge to TVs. Discovery uses bounded unicast targets; do not scan a hotel VLAN or alter routes/firewalls automatically. Validate both directions with one approved identity before expansion.
