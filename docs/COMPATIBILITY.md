# Compatibility

| Component | Classification | Evidence / status |
|---|---|---|
| Five Java WARs | configuration-adapted | Fresh Ubuntu installer startup, HTTPS/migrations, CAS browser login and reboot passed |
| Tomcat 9 + reload.jar | configuration-adapted | Native certificates active; startup/reboot passed; vendor automatic certificate regeneration unqualified |
| Uppercase SmartCMS PHP | restricted legacy runtime | PHP 5.6.40 / Drupal 7.69; native SSO, editor and local ZIP export passed |
| MySQL 5.7 | restricted compatibility baseline | Fresh five-schema bootstrap passed; separate Windows-backup import/migration experiment only, not production restore |
| MGate/PSG/helpers | scope-dependent gaps | Supplied MGate is Windows-only; additional IP playout unqualified; legacy signing/packaging helpers remain model-specific gaps |
| DekTec RF hardware | explicitly excluded | Core web runtime and IP simulator slice run without it; RF functionality is not claimed |
| WIXP TV adapter | simulator-qualified subset | Native discovery/import, room delivery/readback and Standby passed against a synthetic TV; physical acceptance pending |

No general model compatibility claim is made. Captures primarily show TPM191HN-class Android TVs.

Ubuntu 24.04 amd64 is the runtime-qualified installer target. Debian 12/13
detection is tested, but complete Debian runtime qualification is pending.
See TEST-RESULTS.md for evidence categories and KNOWN-LIMITATIONS.md for open gates.
