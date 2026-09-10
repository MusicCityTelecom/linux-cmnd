#!/bin/sh
# Original Linux adapter for the vendor's existing thumbnail invocation.
set -eu
url= out=
for arg in "$@"; do
  case "$arg" in
    --url=*) [ -z "$url" ] || exit 2; url=${arg#--url=} ;;
    --out=*) [ -z "$out" ] || exit 2; out=${arg#--out=} ;;
    *) echo 'Unsupported thumbnail argument' >&2; exit 2 ;;
  esac
done
case "$url" in http://*|https://*) ;; *) echo 'Thumbnail requires HTTP(S)' >&2; exit 2 ;; esac
case "$url" in *'/SmartCMS/pages/thumbnail/'[0-9]*) ;; *) echo 'Not a CMS thumbnail route' >&2; exit 2 ;; esac
case "$out" in
  /opt/cmnd/SmartCMS/sites/default/files/*.png|/opt/cmnd-lab-cms/SmartCMS/sites/default/files/*.png) ;;
  *) echo 'Thumbnail output is outside CMS files' >&2; exit 2 ;;
esac
case "$out" in *'/../'*|*'/./'*|*'//'*) echo 'Ambiguous thumbnail output' >&2; exit 2 ;; esac
task_parent=$out
while [ "$task_parent" != / ]; do
  [ ! -L "$task_parent" ] || { echo 'Symlink thumbnail output refused' >&2; exit 2; }
  task_parent=$(dirname -- "$task_parent")
done
[ -d "$(dirname -- "$out")" ] || { echo 'Thumbnail parent missing' >&2; exit 2; }
exec timeout --signal=TERM --kill-after=5s 45s xvfb-run -a \
  --server-args='-screen 0 1920x1080x24 -nolisten tcp' \
  cutycapt --url="$url" --out="$out" --out-format=png \
  --min-width=1920 --min-height=1080 --delay=1000 --max-wait=30000
