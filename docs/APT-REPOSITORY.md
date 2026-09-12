# CMND Linux APT repository (0.8.0 development)

This document describes the planned signed Ubuntu repository for the 0.8.0 line.
It does **not** declare a repository published or production-ready until the
qualification evidence says so.

## User-facing goal

After one-time repository enrollment, the normal installation command is:

```sh
sudo apt update
sudo apt install cmnd-linux
```

`cmnd-linux` is the user-facing package name. During 0.8 development it is a
small meta package that requires the exact matching `linux-cmnd` payload package.
Keeping the payload name/layout avoids a risky rename of the already working
0.7.1 architecture while still giving operators the desired package name.

## Repository layout

The intended public layout is separated by release channel:

```text
https://repo.techfinity.tech/cmnd/development/
https://repo.techfinity.tech/cmnd/testing/
https://repo.techfinity.tech/cmnd/stable/
```

Each channel is an independent Debian repository root, for example:

```text
stable/
  dists/
    noble/
      InRelease
      Release
      Release.gpg
      main/
        binary-amd64/
          Packages
          Packages.gz
  pool/
    main/
      c/cmnd-linux/
      l/linux-cmnd/
```

Ubuntu 24.04 (`noble`) is the first qualification target. Ubuntu 22.04 and 26.04
must have separate distribution metadata and separate qualification results; a
package being installable is not proof that the runtime is supported.

## Trust model

Repository metadata must be signed with a dedicated CMND archive signing key.
Only the **public** key is distributed to clients. The private signing key must
remain outside Git, release bundles, web roots and installation packages.

Clients should use a dedicated keyring and a deb822 source definition, not
`apt-key` and not a globally trusted key. Planned client configuration:

```text
Types: deb
URIs: https://repo.techfinity.tech/cmnd/stable
Suites: noble
Components: main
Architectures: amd64
Signed-By: /usr/share/keyrings/cmnd-archive-keyring.gpg
```

A future `cmnd-archive-keyring` package may own the public key and source file.
Key rotation must be designed before that package is declared stable.

## Package layers

Initial 0.8 packaging keeps responsibilities separate:

- `cmnd-linux`: user-facing meta/entry package.
- `linux-cmnd`: Music City Telecom deployment tooling, configuration and runtime integration.
- Philips vendor application inputs remain hash-pinned and retain Philips ownership/attribution.

For a truly self-contained APT-only installation, additional release packages
may be introduced for the verified Philips 7.5.9 application bundle and any
runtime artifact that cannot be supplied safely by Ubuntu itself. This is
preferable to having a package maintainer script download untracked binaries
from arbitrary URLs while `dpkg` is configuring the system.

## Coexistence requirements

APT dependency resolution does not replace the 0.8 environment planner. Before
application activation the installer must still:

- inventory existing Apache/nginx, MySQL/MariaDB, Docker, Java and listeners;
- preserve unrelated sites, databases, containers and service definitions;
- reuse an existing database only after its read-only compatibility probe passes;
- never change `lower_case_table_names` on a populated unrelated database server;
- use CMND-owned alternate ports when unrelated listeners already own defaults;
- keep database administrator credentials transient and out of files/logs;
- stop fresh installation if an existing CMND deployment is detected unless an
  explicit, separately qualified upgrade/adoption workflow was requested.

## Release channels

`development` may contain unqualified 0.8 builds. `testing` requires clean
package construction and disposable-host installation evidence. `stable` must
not be populated until the complete supported-OS/runtime/browser/reboot/upgrade
qualification matrix passes.
