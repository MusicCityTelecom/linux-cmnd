#!/bin/sh
set -eu

TOOL_VERSION="0.4.0"
TOMCAT_VERSION="9.0.121"
TOMCAT_URL="https://downloads.apache.org/tomcat/tomcat-9/v${TOMCAT_VERSION}/bin/apache-tomcat-${TOMCAT_VERSION}.tar.gz"
TOMCAT_SHA512="16494dd4745f808d3c506807b5275521fd71044d976f441d18eeeab0f5a38bc1b5344ca395292f6f26eb7612cd8c8e746d01ccdfb29893d394052d9f4b1f4c11"

DRY_RUN=0
ROOT_PREFIX=""
PAYLOAD=""
INSTALL_DEPS=0
DOWNLOAD_TOMCAT=0
TOMCAT_ARCHIVE=""
TOMCAT_ARCHIVE_SHA512=""
ENABLE_SERVICE=0

usage() {
  echo "Usage: sudo ./scripts/install.sh [--dry-run] [--root-prefix DIR] [--install-dependencies]" >&2
  echo "       [--payload EXTRACTED_APP_DIR] [--download-tomcat | --tomcat-archive FILE --tomcat-sha512 HASH]" >&2
  echo "       [--enable-service]" >&2
}

while [ "$#" -gt 0 ]; do
  case "$1" in
    --dry-run) DRY_RUN=1 ;;
    --root-prefix) shift; ROOT_PREFIX=${1:?missing root prefix} ;;
    --payload) shift; PAYLOAD=${1:?missing payload path} ;;
    --install-dependencies) INSTALL_DEPS=1 ;;
    --download-tomcat) DOWNLOAD_TOMCAT=1 ;;
    --tomcat-archive) shift; TOMCAT_ARCHIVE=${1:?missing archive path} ;;
    --tomcat-sha512) shift; TOMCAT_ARCHIVE_SHA512=${1:?missing SHA-512} ;;
    --enable-service) ENABLE_SERVICE=1 ;;
    -h|--help) usage; exit 0 ;;
    *) echo "Unknown option: $1" >&2; usage; exit 2 ;;
  esac
  shift
done

case "$ROOT_PREFIX" in
  "") ROOT_PREFIX="" ;;
  /*) ROOT_PREFIX=${ROOT_PREFIX%/} ;;
  *) echo "--root-prefix must be absolute" >&2; exit 2 ;;
esac

SCRIPT_DIR=$(CDPATH= cd -- "$(dirname -- "$0")" && pwd)
SOURCE_DIR=$(CDPATH= cd -- "$SCRIPT_DIR/.." && pwd)
OS_RELEASE="${ROOT_PREFIX}/etc/os-release"
if [ ! -f "$OS_RELEASE" ]; then
  echo "Cannot detect Debian-family OS: $OS_RELEASE is missing" >&2; exit 3
fi
# shellcheck disable=SC1090
. "$OS_RELEASE"
case "${ID:-}:${VERSION_ID:-}" in
  ubuntu:24.04|debian:12|debian:13) ;;
  *) echo "Unsupported OS ${ID:-unknown} ${VERSION_ID:-unknown}; supported: Ubuntu 24.04, Debian 12/13" >&2; exit 3 ;;
esac

if [ -z "$ROOT_PREFIX" ]; then
  ARCH=$(dpkg --print-architecture 2>/dev/null || uname -m)
else
  ARCH="amd64"
fi
case "$ARCH" in amd64|x86_64) ;; *) echo "Unsupported architecture: $ARCH (amd64 required)" >&2; exit 3 ;; esac
if [ -z "$ROOT_PREFIX" ] && [ "$(id -u)" -ne 0 ]; then
  echo "Run as root or use --root-prefix for an isolated filesystem test" >&2; exit 4
fi
if [ "$DOWNLOAD_TOMCAT" -eq 1 ] && [ -n "$TOMCAT_ARCHIVE" ]; then
  echo "Choose --download-tomcat or --tomcat-archive, not both" >&2; exit 2
fi
if [ -n "$TOMCAT_ARCHIVE" ] && [ -z "$TOMCAT_ARCHIVE_SHA512" ]; then
  echo "--tomcat-archive requires --tomcat-sha512" >&2; exit 2
fi
if [ "$ENABLE_SERVICE" -eq 1 ]; then
  echo "Full vendor deployment is not qualified; --enable-service is unavailable. No changes made." >&2
  exit 9
fi
if [ -n "$PAYLOAD" ]; then
  for war in cas.war usermanagement.war smartcontrol.war SmartInstall.war smartcms.war; do
    [ -f "$PAYLOAD/$war" ] || { echo "Missing required payload: $PAYLOAD/$war" >&2; exit 7; }
  done
fi
if [ "$INSTALL_DEPS" -eq 1 ] && [ "${ID:-}:${VERSION_ID:-}" = "debian:13" ]; then
  echo "Debian 13 Java 17 provisioning is not qualified; install an approved JDK separately." >&2
  exit 3
fi

run() {
  if [ "$DRY_RUN" -eq 1 ]; then printf '+ '; printf '%s ' "$@"; printf '\n'; else "$@"; fi
}

if [ "$INSTALL_DEPS" -eq 1 ]; then
  if [ -n "$ROOT_PREFIX" ]; then echo "Dependency installation is disabled with --root-prefix" >&2; exit 2; fi
  run apt-get update
  run apt-get install -y --no-install-recommends python3 openjdk-17-jre-headless ca-certificates curl tar
fi

if [ "$DRY_RUN" -eq 0 ]; then
  command -v python3 >/dev/null || { echo "python3 is required" >&2; exit 5; }
  python3 -c 'import sys; raise SystemExit(0 if sys.version_info >= (3, 11) else 1)' || {
    echo "Python 3.11 or newer is required on the target" >&2; exit 5; }
fi

APP_BASE="${ROOT_PREFIX}/opt/linux-cmnd"
RELEASE="$APP_BASE/releases/$TOOL_VERSION"
CMND_ROOT="${ROOT_PREFIX}/opt/cmnd"
ETC_DIR="${ROOT_PREFIX}/etc/cmnd"
VAR_DIR="${ROOT_PREFIX}/var/lib/cmnd"
LOG_DIR="${ROOT_PREFIX}/var/log/cmnd"

if [ -d "$RELEASE" ] && { [ ! -f "$RELEASE/VERSION" ] || [ "$(tr -d '\r\n' < "$RELEASE/VERSION")" != "$TOOL_VERSION" ]; }; then
  echo "Refusing unknown or mismatched existing tooling release: $RELEASE" >&2; exit 10
fi

run install -d -m 0755 "$APP_BASE/releases" "$RELEASE" "${ROOT_PREFIX}/usr/bin"
run install -d -m 0750 "$ETC_DIR" "$VAR_DIR" "$LOG_DIR" "$CMND_ROOT/releases"
if [ "$DRY_RUN" -eq 0 ]; then
  cp -a "$SOURCE_DIR/src" "$RELEASE/"
  cp -a "$SOURCE_DIR/config" "$RELEASE/"
  cp -a "$SOURCE_DIR/deploy" "$RELEASE/"
  install -m 0644 "$SOURCE_DIR/VERSION" "$SOURCE_DIR/LICENSE" "$RELEASE/"
  ln -sfn "releases/$TOOL_VERSION" "$APP_BASE/current"
  cat > "${ROOT_PREFIX}/usr/bin/cmndctl" <<'EOF'
#!/bin/sh
CMND_CONFIG=${CMND_CONFIG:-/etc/cmnd/cmnd.toml}
export CMND_CONFIG
PYTHONPATH=/opt/linux-cmnd/current/src exec python3 -m cmnd_linux "$@"
EOF
  chmod 0755 "${ROOT_PREFIX}/usr/bin/cmndctl"
  if [ ! -f "$ETC_DIR/cmnd.toml" ]; then
    install -m 0640 "$SOURCE_DIR/config/cmnd.example.toml" "$ETC_DIR/cmnd.toml"
  fi
fi

if [ "$DRY_RUN" -eq 1 ]; then
  echo "+ cmndctl render-runtime-config --output $ETC_DIR --execute"
elif [ -n "$ROOT_PREFIX" ]; then
  CMND_CONFIG="$ETC_DIR/cmnd.toml" PYTHONPATH="$RELEASE/src" python3 -m cmnd_linux render-runtime-config --output "$ETC_DIR" --execute
else
  cmndctl render-runtime-config --output /etc/cmnd --execute
fi

if [ -z "$ROOT_PREFIX" ] && [ "$DRY_RUN" -eq 0 ]; then
  getent group cmnd >/dev/null || groupadd --system cmnd
  id cmnd >/dev/null 2>&1 || useradd --system --gid cmnd --home-dir /var/lib/cmnd --shell /usr/sbin/nologin cmnd
  chown cmnd:cmnd "$VAR_DIR" "$LOG_DIR" "$CMND_ROOT/releases"
  for rendered in cmnd.toml tomcat.env apache.env compose.env; do
    chown root:cmnd "$ETC_DIR/$rendered"
    chmod 0640 "$ETC_DIR/$rendered"
  done
fi

install_tomcat_archive() {
  archive=$1 expected=$2
  actual=$(sha512sum "$archive" | awk '{print $1}')
  [ "$actual" = "$expected" ] || { echo "Tomcat SHA-512 mismatch" >&2; exit 6; }
  target="$CMND_ROOT/tomcat-$TOMCAT_VERSION"
  if [ ! -d "$target" ]; then
    run install -d -m 0755 "$target"
    if [ "$DRY_RUN" -eq 0 ]; then tar -xzf "$archive" --strip-components=1 -C "$target"; fi
  fi
  run ln -sfn "tomcat-$TOMCAT_VERSION" "$CMND_ROOT/tomcat"
}

if [ "$DOWNLOAD_TOMCAT" -eq 1 ]; then
  if [ "$DRY_RUN" -eq 1 ]; then
    tmp="PRIVATE_TEMP/apache-tomcat-${TOMCAT_VERSION}.tar.gz"
  else
    task_tmp=$(mktemp -d)
    trap 'rm -f "$task_tmp/tomcat.tar.gz"; rmdir "$task_tmp"' EXIT INT TERM
    tmp="$task_tmp/tomcat.tar.gz"
  fi
  run curl --fail --location --proto '=https' --tlsv1.2 -o "$tmp" "$TOMCAT_URL"
  [ "$DRY_RUN" -eq 1 ] || install_tomcat_archive "$tmp" "$TOMCAT_SHA512"
elif [ -n "$TOMCAT_ARCHIVE" ]; then
  install_tomcat_archive "$TOMCAT_ARCHIVE" "$TOMCAT_ARCHIVE_SHA512"
fi

if [ -n "$PAYLOAD" ]; then
  for war in cas.war usermanagement.war smartcontrol.war SmartInstall.war smartcms.war; do
    [ -f "$PAYLOAD/$war" ] || { echo "Missing required payload: $PAYLOAD/$war" >&2; exit 7; }
  done
  if [ "$DRY_RUN" -eq 1 ]; then
    echo "+ cmndctl install --source $PAYLOAD --root /opt/cmnd --release cmnd-7.5.9 --execute"
  elif [ -n "$ROOT_PREFIX" ]; then
    PYTHONPATH="$RELEASE/src" python3 -m cmnd_linux --config "$ETC_DIR/cmnd.toml" install --source "$PAYLOAD" --root "$CMND_ROOT" --release cmnd-7.5.9 --execute
  else
    cmndctl --config /etc/cmnd/cmnd.toml install --source "$PAYLOAD" --root /opt/cmnd --release cmnd-7.5.9 --execute
  fi
fi

if [ "$ENABLE_SERVICE" -eq 1 ]; then
  [ -z "$ROOT_PREFIX" ] || { echo "Cannot enable systemd service with --root-prefix" >&2; exit 2; }
  [ -x /opt/cmnd/tomcat/bin/catalina.sh ] || { echo "Tomcat is not installed/adoptable at /opt/cmnd/tomcat" >&2; exit 8; }
  [ -L /opt/cmnd/current ] || { echo "CMND payload is not installed at /opt/cmnd/current" >&2; exit 8; }
  echo "Refusing automatic vendor service enable: SmartInstall listener isolation must be verified after WAR expansion." >&2
  echo "Install the reviewed unit manually after completing docs/INSTALL.md acceptance gates." >&2
  exit 9
fi

echo "linux-cmnd $TOOL_VERSION installed for ${ID} ${VERSION_ID} (${ARCH})."
echo "Configuration: ${ETC_DIR}/cmnd.toml"
echo "Vendor services remain disabled until isolation and application overrides are verified."
