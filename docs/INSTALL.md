# Installation

Use only an isolated Ubuntu 24.04 lab. Keep the operator-owned installer and extracted payload outside the checkout.

1. Install Python 3.11+, Java 17, Tomcat 9, Apache/PHP dependencies, and an isolated database only after reviewing exact pins.
2. Install this wrapper with `python3 -m pip install -e .`.
3. Copy and edit `config/cmnd.example.toml`; validate with `cmndctl --config <file> validate-config`.
4. Run `cmndctl doctor --source <extracted-{app}>`.
5. Preview with `cmndctl install --source <source> --root /opt/cmnd --release <id>`; apply only with `--execute`.
6. Install the reviewed systemd template and protected environment file manually during the approved host change.

The current installer stages the five WARs and lifecycle metadata. It does not yet render all vendor overlays, provision databases, Apache/PHP, accounts, or systemd. Therefore the canonical Ubuntu deployment remains INFRASTRUCTURE_BLOCKED and this is a development adoption procedure, not production installation.
