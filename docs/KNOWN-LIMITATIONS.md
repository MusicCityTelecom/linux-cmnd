# Known limitations

- No Linux vendor runtime, CAS login, Apache/PHP UI, database, browser, reboot, or physical-TV workflow has been executed.
- The lab callback/polling receiver is implemented in memory, but is not integrated with vendor SmartInstall persistence, authentication, or restart recovery.
- Room package XML is capture-derived but still requires an operator-supplied model-specific `TVSettings.xml`; final room readback is not implemented.
- Package server supports byte ranges but lacks a production authorization layer and must remain isolated.
- Filesystem lifecycle commands do not provision users/services/databases and are not a full installer or recovery system.
- MGate, PSG, SIServer, native process helpers, optional hardware absence, TLS reload, schema migrations, and supported modern PHP/database profiles remain unresolved.
