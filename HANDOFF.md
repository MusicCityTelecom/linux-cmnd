# Handoff checkpoint

Branch: `main`. Tooling version: `0.4.0` development. Private remote: `MusicCityTelecom/linux-cmnd`. Full production installer remains unfinished; do not label this production-ready or 1.0.

## Accepted evidence

All five actual vendor WARs run on Ubuntu24.04/Java17/Tomcat9/MySQL5.7. Fresh SmartInstall history:122successful rows/final9.7; smartcontrol27/final1.5.1. TLS-verified CAS login/incorrect-password rejection and native TV discovery/import, room delivery/callback/readback and Standby passed against the internal simulator, not physical TVs.

CMS SSO/overview/editor work. DocumentRoot must be CMS itself, with Alias /SmartCMS and explicit RewriteBase /SmartCMS; otherwise clean URLs return404. PHP can write only sites/default/files, including export/tpvision. Recreate private PGT storage as UID33 after tmpfs restart.

Persistent IPv4/IPv6 UID isolation survived actual reboot. Guarded manual resume restored Java/CMS login and synthetic room/power control. Automatic application startup is not qualified. Java startup took394.7seconds on the6GiB VM.

Native certificates/preserved-CA validation, native configuration syntax and complete private application assembly passed. Native candidate remains inactive; active synthetic TLS was not replaced. Source suite:102tests pass on Ubuntu Python3.12; Windows Python3.13 ran102 with one expected POSIX-permission skip. Tooling0.4.0 deb installed and sudo CLI validation passed on Ubuntu.

## Windows backup

User approved private SQL import into a separate disposable database. All five7.4.8schemas imported after supplying only omitted vendor upg_setting DDL. Firmware binaries (~2GB) are intentionally uploaded separately, not included in the backup or package. Preserve unresolved firmware references; never map by reused numeric ID.

Direct backup/new-WAR comparison found matching relevant column/FK definitions and74device-view columns. Native Flyway rejects checksums2.4,2.13,4.5; explicit lab-only reconciliation followed by ten additive migrations reached122successful rows/9.7. Do not silently repair production history. Full production restore/content replay is unqualified. Original backup unchanged; customer restore container stopped and never attached to applications.

## Private lab state

VM CMND-Ubuntu24-Qualification-20260908, hostname cmnd-qualification,6GiB/4CPU/40GiB. Windows loopback SSH22240, usercmndlab, Owner-only ignored key .lab/keys/id_ed25519. Owner/escalated tools required. No current scoped live-TV target or server4 deployment approval.

Guest source: /home/cmndlab/qualification-checkpoint-004; /home/cmndlab/tooling retained. Vendor input /home/cmndlab/input/vendor. Active Tomcat /usr/local/tomcat, Philips /opt/Philips, CMS /opt/cmnd-lab-cms/SmartCMS. Root-private /var/lib/cmnd-lab; browser credentials /home/cmndlab/browser-credentials.json mode0600; private browser outputs /home/cmndlab/browser-results. Inactive full stage /var/lib/cmnd-lab/application-stage-001 requires reviewed canonical activation, not a symlink switch.

Services cmnd-lab-egress, cmnd-lab-tomcat, Apache. Guard /etc/cmnd/lab-egress.json; worker /usr/local/lib/cmnd-lab/egress.py. systemd dependencies require guard before Docker/Apache/Tomcat; revalidate kernel policy before startup.

Containers cmnd-lab-mysql172.30.44.2 and cmnd-lab-tv-internal172.30.44.4 on internal-only cmnd-lab-db, no ports published. cmnd-lab-php uses host networking as33:33 with UID guard/read-only root and code/capability drop/resource limits. Customer cmnd-lab-restore-748 remains stopped; never resume it with synthetic applications.

PHP5.6.40 is a legacy fidelity experiment, not a supported security profile. Export originally falsely returned success for a117-member base-only ZIP without HTML: Linux worker requires pcntl_fork. Offline PCNTL build installed image sha256:ab1537bf1305c30c0701e4b938b33aa475d022d6dbbc73aaab022cff23a1c27f. Prior cmnd-lab-php-before-pcntl container is preserved/stopped. After the fix, native clone6208 to6221 exported2862950bytes/125members with generated HTML and valid CRC. Old cached incomplete export6208 is preserved as failed synthetic evidence. Use scripts/lab_cms_export.py --node 6221 --execute to recheck; --clone-first creates another synthetic node through native DOM actions. Never clicks Publish or deploys to TV. Read latest private cms-export-report.json for detailed results.

Only permitted TV identity: SIMULATOR00000001020000000001. Simulator memory resets room0001 on container restart; post-reboot command reapplied0042. Browser runner: PYTHONPATH=src /home/cmndlab/browser-env/bin/python scripts/lab_browser.py --room-simulator --remote-dialog --power-simulator --cms --execute --summary. Fresh native inventory route /SmartInstall/dev?type=index; bare/devGET can be blank. Known vendor CAS '$ is not defined' script ordering error does not prevent authentication.

## Next work

Finish CMS thumbnail helper (/usr/local/bin/cutycapt.sh missing), local publishing and full clone/content jobs; consolidate canonical installer activation, database/PHP provisioning, role ownership, OS/Java CA trust and boot lifecycle; clean-install/recovery on Ubuntu and Debian; full Windows restore; then obtain current physical-TV IP/identity/model and scoped deployment authority. Preserve default configurable ports8080/8443/8082/8444/3306. Tooling SQLite inventory is not Philips inventory. RF/DekTec is out of scope. Never commit vendor/customer inputs, SQL dumps, certificates, captures, logs or generated exports.
