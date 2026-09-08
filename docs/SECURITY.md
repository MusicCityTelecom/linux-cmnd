# Security

Safety validation precedes all writes. TV mutations require a literal allowlisted IP, stable MAC/serial identity, allowed operation, and `--execute`. Restored environments must be egress-isolated before application bootstrap because vendor SmartInstall defaults include `listener.load=on`.

Private/vendor inputs are denied by `.gitignore`; tracked files must also be scanned before each push. Secrets belong in mode-0600 files, never CLI arguments or logs. The PHP 5.6.40/Drupal 7.69 payload and MySQL 5.7 baseline are legacy compatibility risks. Tomcat's historical shutdown port, embedded keystore password, and stock applications must not carry into a candidate. Do not weaken CAS, certificate validation, CSRF, VSecure, DRM, or signatures.
