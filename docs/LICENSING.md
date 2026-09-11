# Original vendor licensing and installation identity

The complete-install release includes the original Philips application files,
not an activation copied from an existing server. Vendor ownership and bundled
notices remain applicable; the Music City Telecom tooling license does not
relicense Philips applications. Original installer-distributed defaults are
retained under the operator's explicit distribution authorization. Customer
backups, live databases, VPN profiles, site credentials and locally generated
certificates/private keys are not release assets.

## Serial number

Inspection of the original 7.5.9 Java code shows that CMND computes the license
serial from CPU and motherboard information using its bundled OSHI hardware
library. It is not a random number copied into our release or a serial taken
from the reference Windows installation. We preserve that vendor calculation.
The screenshot supplied by the operator is from 7.5.1; qualification uses 7.5.9.

Keep VM CPU and motherboard/SMBIOS identifiers stable across reboots/upgrades.
Give separately installed VMs distinct motherboard serial identifiers: a UUID
alone is not sufficient if the hypervisor does not expose it as the motherboard
serial read by OSHI. Identical virtual hardware can produce identical CMND
serials. Do not clone an activated CMND disk to create a new installation.
Hardware changes or moving a Windows backup to Linux may require vendor-assisted
license rebinding; do not manufacture a matching serial or bypass validation.

## Request License

Use the unchanged **Admin > License** page and enter a vendor-provided activation
code when required. The button verifies/submits that code to the configured
vendor service; it does not unconditionally issue a free license. The original
code skips license synchronization when no activation code is configured, and
retains its feature checks, cache and scheduled refresh behavior.

The original configured service is `https://license.cmnd.pro`. Linux's default
service isolation denies external traffic. To explicitly allow that service:

```sh
sudo cmndctl license-network
sudo cmndctl license-network --execute
```

The first command previews without DNS/network changes. The second validates
the native policy and local loopback DNS resolver, resolves only the fixed vendor
hostname, rejects private/loopback/multicast answers, and adds TCP 443 access to
those public addresses. It preserves other endpoints and the final reject rule,
backs up the previous policy, and makes no activation request or TV contact.
Existing host/firewall restrictions can still prevent access. Rerun after vendor
DNS changes. Non-loopback resolver configurations require separate review.

This is IP-scoped HTTPS access, not an HTTP-path proxy; vendor TLS hostname
verification remains the responsibility of the unchanged Java HTTP client.
No activation code, paid entitlement, or successful vendor-issued license is
claimed by simulator or unit tests. A real activation requires an appropriate
vendor-issued code and separate recorded evidence.
