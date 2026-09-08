#!/bin/sh
set -eu
ROOT=$(CDPATH= cd -- "$(dirname -- "$0")/.." && pwd)
cd "$ROOT"
command -v dpkg-deb >/dev/null || { echo "dpkg-deb is required" >&2; exit 1; }
VERSION=$(tr -d '\r\n' < VERSION)
STAGE=$(mktemp -d)
trap 'rm -rf "$STAGE"' EXIT INT TERM
PKG="$STAGE/linux-cmnd_${VERSION}_amd64"
install -d "$PKG/DEBIAN" "$PKG/opt/linux-cmnd/releases/$VERSION" "$PKG/usr/bin" "$PKG/etc/cmnd" "$PKG/usr/share/linux-cmnd" "$PKG/usr/share/doc/linux-cmnd"
sed "s/@VERSION@/$VERSION/g" packaging/debian/control.in > "$PKG/DEBIAN/control"
install -m 0755 packaging/debian/preinst packaging/debian/postinst packaging/debian/prerm packaging/debian/postrm "$PKG/DEBIAN/"
install -m 0644 packaging/debian/conffiles "$PKG/DEBIAN/"
cp -a src config deploy LICENSE VERSION "$PKG/opt/linux-cmnd/releases/$VERSION/"
install -m 0755 packaging/debian/cmndctl "$PKG/usr/bin/cmndctl"
install -m 0640 config/cmnd.example.toml "$PKG/etc/cmnd/cmnd.toml"
cp -a deploy/* "$PKG/usr/share/linux-cmnd/"
install -m 0644 README.md "$PKG/usr/share/doc/linux-cmnd/README.md"
install -d dist
SOURCE_DATE_EPOCH=${SOURCE_DATE_EPOCH:-1788835200} dpkg-deb --root-owner-group --build "$PKG" "dist/linux-cmnd_${VERSION}_amd64.deb"
sha256sum "dist/linux-cmnd_${VERSION}_amd64.deb" > "dist/linux-cmnd_${VERSION}_amd64.deb.sha256"
