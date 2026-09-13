#!/bin/sh
# Full evaluation installer; bootstrap downloads the hash-pinned vendor bundle
# from GitHub. This lower-level entrypoint consumes its extracted local inputs.
set -eu
execute=0 accept=0 dependencies=0 prepare=0 payload= config=/etc/cmnd/cmnd.toml archive= image= package= java_home=
while [ "$#" -gt 0 ]; do
  case "$1" in
    --execute) execute=1 ;;
    --accept-legacy-runtime) accept=1 ;;
    --install-dependencies) dependencies=1 ;;
    --prepare-only) prepare=1 ;;
    --payload) shift; payload=${1:?missing payload directory} ;;
    --config) shift; config=${1:?missing configuration} ;;
    --tomcat-archive) shift; archive=${1:?missing archive} ;;
    --php-image) shift; image=${1:?missing immutable PHP image} ;;
    --package) shift; package=${1:?missing package} ;;
    --java-home) shift; java_home=${1:?missing Java home} ;;
    --dry-run) execute=0 ;;
    *) echo 'Usage: cmnd-install --payload EXTRACTED_APP --config CONFIG [--package DEB] [--tomcat-archive FILE] [--php-image DIGEST] [--java-home DIR] [--install-dependencies] --accept-legacy-runtime --execute' >&2; exit 2 ;;
  esac
  shift
done
[ -r /etc/os-release ] || { echo 'Linux /etc/os-release required' >&2; exit 2; }
. /etc/os-release
case "${ID:-}:${VERSION_ID:-}" in ubuntu:24.04|debian:12|debian:13) ;; *) echo 'Requires Ubuntu 24.04 or Debian 12/13 amd64' >&2; exit 2 ;; esac
[ "$(dpkg --print-architecture)" = amd64 ] || { echo 'amd64 required' >&2; exit 2; }
[ -n "$payload" ] && [ -d "$payload" ] || { echo 'Supply your licensed, extracted Philips application directory with --payload' >&2; exit 2; }
[ -r "$config" ] || { echo 'Supply the reviewed configuration with --config' >&2; exit 2; }
if [ -z "$java_home" ]; then java_home=/usr/lib/jvm/java-17-openjdk-amd64; fi
if [ "${ID}:${VERSION_ID}" = debian:13 ] && [ ! -x "$java_home/bin/java" ]; then
  echo 'Debian 13 requires a separately supplied Java 17 runtime via --java-home; no changes made.' >&2; exit 2
fi
if [ "$execute" -ne 1 ]; then
  echo "PLAN: detect ${ID} ${VERSION_ID}; verify licensed payload; install tooling/dependencies if requested; provision PHP/MySQL, native TLS and five databases; start and verify CMND. No changes made."
  exit 0
fi
[ "$(id -u)" -eq 0 ] && [ "$accept" -eq 1 ] || { echo 'Requires root, --execute and --accept-legacy-runtime (PHP 5.6/MySQL 5.7 are legacy evaluation dependencies).' >&2; exit 2; }
for existing in /var/lib/cmnd-deployment /opt/cmnd/tomcat /opt/cmnd/SmartCMS /opt/Philips; do
  [ ! -e "$existing" ] && [ ! -L "$existing" ] || { echo "Fresh installer refuses existing application state: $existing. No changes made. Use sudo cmndctl install-summary (v0.7.2+) or sudo cat /var/lib/cmnd-deployment/initial-admin.json (v0.7.1); upgrade using sudo cmndctl --updates." >&2; exit 2; }
done
umask 077
script_dir=$(CDPATH= cd -- "$(dirname -- "$0")" && pwd)
if [ -z "$package" ] && [ -f "$script_dir/../scripts/build_deb.py" ]; then
  package=$(python3 "$script_dir/build_deb.py")
fi
[ -n "$package" ] || { echo 'Supply --package DEB even when tooling is already installed; the matching package is required for update rollback.' >&2; exit 2; }
transcript=$(mktemp /var/log/linux-cmnd-install-XXXXXXXX.log)
stage='package validation and early capacity check'
notice() { printf '%s INFO %s\n' "$(date -u +%FT%TZ)" "$1" | tee -a "$transcript"; }
finish() {
  result=$?
  trap - EXIT
  if [ "$result" -ne 0 ]; then
    printf '\nFINAL STATUS: FAIL\nStage: %s\nPrivate transcript: %s\nPreserve all state; inspect this transcript and deployment.log/php-build.log if created. Do not force a reinstall.\n' "$stage" "$transcript" | tee -a "$transcript" >&2
  fi
  exit "$result"
}
trap finish EXIT
notice "OS: ${ID} ${VERSION_ID}; stage: $stage; private transcript: $transcript"
if [ -n "$package" ]; then
  [ "$(dpkg-deb -f "$package" Package)" = linux-cmnd ] && [ "$(dpkg-deb -f "$package" Architecture)" = amd64 ] || { echo 'Unexpected tooling package' >&2; exit 2; }
  package_version=$(dpkg-deb -f "$package" Version)
  case "$package_version" in *[!0-9.]*|'') echo 'Invalid package version' >&2; exit 2 ;; esac
  # Inspect the supplied package without running maintainer scripts. The public
  # bootstrap already verified its hash. Local packages must be trusted inputs.
  scratch=$(mktemp -d /var/tmp/linux-cmnd-preflight-XXXXXXXX)
  dpkg-deb -x "$package" "$scratch"
  candidate="$scratch/opt/linux-cmnd/releases/$package_version/src"
  PYTHONPATH="$candidate" python3 -m cmnd_linux --config "$config" validate-config
  python3 "$candidate/cmnd_linux/install_storage.py" --config "$config"
  # Keep this small private extraction as preflight evidence; no recursive cleanup.
  stage='tooling package installation'
  notice "$stage"
  dpkg --force-confold --install "$package"
fi
command -v cmndctl >/dev/null || { echo 'Install the supplied linux-cmnd .deb first, or use --package FILE' >&2; exit 2; }
# Safety and immutable vendor verification precede dependency networking and DB initialization.
cmndctl --config "$config" validate-config
if [ "$(readlink -f "$config")" != /etc/cmnd/cmnd.toml ]; then
  install -m 0640 -o root -g cmnd "$config" /etc/cmnd/cmnd.toml
  config=/etc/cmnd/cmnd.toml
fi
# Package setup initially used the example configuration. Synchronize auxiliary
# environments once with the reviewed fresh-install address, before activation.
cmndctl --config "$config" render-runtime-config --output /etc/cmnd --execute
PYTHONPATH=/opt/linux-cmnd/current/src python3 -c '
import sys
from pathlib import Path
from cmnd_linux.application_stage import VENDOR_INPUT_HASHES
from cmnd_linux.artifacts import file_hash
root=Path(sys.argv[1])
for name, expected in VENDOR_INPUT_HASHES.items():
    path=root/name
    if not path.is_file() or path.is_symlink() or file_hash(path)!=expected:
        raise SystemExit("Unrecognized or missing vendor input: "+name)
' "$payload"
if [ "$dependencies" -eq 1 ]; then
  stage='OS prerequisites and Java 17'
  notice "$stage (apt output follows)"
  apt-get update
  # Only the Apache binary/modules are needed for our standalone configuration.
  # Installing the apache2 metapackage would start an unrelated default port-80 site.
  DEBIAN_FRONTEND=noninteractive apt-get install -y --no-install-recommends python3 adduser docker.io apache2-bin media-types openssl iptables iproute2 ca-certificates curl 7zip
  if [ ! -x "$java_home/bin/java" ]; then apt-get install -y --no-install-recommends openjdk-17-jre-headless; fi
fi
[ -x "$java_home/bin/java" ] || { echo 'Java 17 missing; install dependencies or supply --java-home' >&2; exit 2; }
install -d -m 0700 /var/cache/linux-cmnd/bootstrap
cache=/var/cache/linux-cmnd/bootstrap
[ ! -L "$cache/initial-installer.deb" ] || { echo 'Refusing symlink rollback cache' >&2; exit 2; }
install -m 0600 "$package" "$cache/initial-installer.deb"
PYTHONPATH=/opt/linux-cmnd/current/src python3 -c 'from cmnd_linux.native_deploy import validate_baseline; validate_baseline()'
if [ -z "$archive" ]; then
  stage='verified Tomcat archive'
  notice "$stage"
  archive=$cache/apache-tomcat-9.0.121.tar.gz
  if [ ! -f "$archive" ]; then
    curl --fail --location --proto '=https' --proto-redir '=https' --tlsv1.2 --max-time 300 \
      -o "$archive.partial" https://archive.apache.org/dist/tomcat/tomcat-9/v9.0.121/bin/apache-tomcat-9.0.121.tar.gz
    mv "$archive.partial" "$archive"
  fi
fi
PYTHONPATH=/opt/linux-cmnd/current/src python3 -c 'from pathlib import Path; import sys; from cmnd_linux.application_stage import _tomcat_members; _tomcat_members(Path(sys.argv[1]))' "$archive"
docker info >/dev/null
stage='private MySQL image'
notice "$stage"
if ! docker image inspect mysql@sha256:4bc6bc963e6d8443453676cae56536f4b8156d78bae03c0145cbe47c2aad73bb >/dev/null 2>&1; then
  docker pull mysql@sha256:4bc6bc963e6d8443453676cae56536f4b8156d78bae03c0145cbe47c2aad73bb
fi
if [ -z "$image" ]; then
  stage='PHP image build'
  notice "$stage; can take several minutes. Live details: sudo tail -f $cache/php-build.log"
  docker build -f /opt/linux-cmnd/current/deploy/php/Dockerfile.lab \
    --iidfile "$cache/php-image.id" /opt/linux-cmnd/current/deploy/php > "$cache/php-build.log" 2>&1 || {
      echo 'PHP build failed. Inspect private /var/cache/linux-cmnd/bootstrap/php-build.log' >&2; exit 1;
    }
  image=$(cat "$cache/php-image.id")
fi
if [ "$prepare" -eq 1 ]; then
  notice 'FINAL STATUS: PREPARED ONLY; application not installed'
  echo "Dependencies prepared; no vendor database or application started. PHP image: $image"
  exit 0
fi
stage='application deployment and readiness'
notice "$stage; completion URLs and initial password follow only after all checks pass"
cmndctl --config "$config" deploy-native --vendor "$payload" --tomcat-archive "$archive" \
  --php-image "$image" --java-home "$java_home" --accept-legacy-runtime --execute
# Do not print after the centralized CLI summary. Save a non-secret pointer.
printf '%s FINAL STATUS: PASS; receipt: /var/lib/cmnd-deployment/install-summary.txt\n' "$(date -u +%FT%TZ)" >> "$transcript"
