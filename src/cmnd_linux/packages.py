from __future__ import annotations

from http.server import SimpleHTTPRequestHandler, ThreadingHTTPServer
from pathlib import Path
import re
from urllib.parse import unquote, urlparse
import zipfile


ROOM_RE = re.compile(r"^[0-9]{1,16}$")


def build_room_package(output: Path, serial: str, room_id: str, tv_settings_template: Path) -> None:
    if not ROOM_RE.fullmatch(room_id):
        raise ValueError("room ID must be a 1-16 digit string; leading zeroes are preserved")
    if not re.fullmatch(r"[A-Za-z0-9._-]{8,128}", serial):
        raise ValueError("serial contains unsupported characters")
    if output.exists():
        raise ValueError("output package already exists")
    if not tv_settings_template.is_file():
        raise ValueError("capture/model-derived TVSettings.xml template is required")
    xml = ("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
           "<RoomSpecificSettings><SerialNumber>" + serial + "</SerialNumber>"
           "<Item Name=\"Professional Settings.Advanced.Identification Settings.RoomID\" "
           "Value=\"" + room_id + "\"/></RoomSpecificSettings>\n")
    output.parent.mkdir(parents=True, exist_ok=True)
    with zipfile.ZipFile(output, "x", compression=zipfile.ZIP_DEFLATED) as archive:
        archive.writestr("RoomSpecificSettings.xml", xml)
        archive.write(tv_settings_template, "TVSettings.xml")


class RangeHandler(SimpleHTTPRequestHandler):
    server_version = "CMND-Package-Server/0.1"

    def __init__(self, *args, directory=None, **kwargs):
        super().__init__(*args, directory=directory, **kwargs)

    def send_head(self):
        path = Path(self.translate_path(unquote(urlparse(self.path).path)))
        if path.suffix.lower() != ".zip":
            self.send_error(403, "only ZIP package objects are served")
            return None
        handle = path.open("rb") if path.is_file() else None
        if handle is None:
            self.send_error(404)
            return None
        size = path.stat().st_size
        match = re.fullmatch(r"bytes=(\d+)-(\d*)", self.headers.get("Range", ""))
        if match:
            start = int(match.group(1)); end = int(match.group(2)) if match.group(2) else size - 1
            if start >= size or end < start or end >= size:
                handle.close(); self.send_error(416); return None
            self.send_response(206); self.send_header("Content-Range", f"bytes {start}-{end}/{size}")
            self.send_header("Content-Length", str(end - start + 1)); handle.seek(start)
            self._range_remaining = end - start + 1
        else:
            self.send_response(200); self.send_header("Content-Length", str(size)); self._range_remaining = None
        self.send_header("Content-Type", "application/zip"); self.send_header("Accept-Ranges", "bytes"); self.end_headers()
        return handle

    def copyfile(self, source, outputfile):
        remaining = getattr(self, "_range_remaining", None)
        if remaining is None:
            return super().copyfile(source, outputfile)
        while remaining:
            chunk = source.read(min(64 * 1024, remaining))
            if not chunk: break
            outputfile.write(chunk); remaining -= len(chunk)


def serve_packages(root: Path, bind: str, port: int) -> None:
    root = root.resolve()
    if not root.is_dir():
        raise ValueError("package root is not a directory")
    handler = lambda *a, **kw: RangeHandler(*a, directory=str(root), **kw)
    ThreadingHTTPServer((bind, port), handler).serve_forever()
