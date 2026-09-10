# Networking

The CMND server and TVs may be on different routed networks. Keep actual customer
and server addresses in private deployment configuration, not this repository.
Required directions from protocol evidence are CMND→TV TCP/9079,
TV→CMND TCP/8080 callbacks and package downloads, and browser/operator→CMND
application ports. Test HTTPS 8443/8444 independently on the installed runtime.

Configure management bind, permitted target ranges, stable device allowlist, and TV-reachable callback URL independently. Do not advertise localhost or a container bridge to TVs. Discovery uses bounded unicast targets; do not scan a hotel VLAN or alter routes/firewalls automatically. Validate both directions with one approved identity before expansion.
