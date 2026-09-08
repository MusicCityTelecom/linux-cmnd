#!/usr/bin/env python3
"""Build a reproducible Debian binary package using only Python stdlib."""
from __future__ import annotations

from hashlib import sha256
from io import BytesIO
from pathlib import Path
import gzip
import os
import stat
import tarfile


ROOT = Path(__file__).resolve().parents[1]
EPOCH = int(os.environ.get("SOURCE_DATE_EPOCH", "1788835200"))


def tar_blob(entries: list[tuple[Path | None, str, int, bytes | None]]) -> bytes:
    raw = BytesIO()
    with gzip.GzipFile(fileobj=raw, mode="wb", mtime=EPOCH, filename="") as zipped:
        with tarfile.open(fileobj=zipped, mode="w") as archive:
            for source, destination, mode, literal in sorted(entries, key=lambda item: item[1]):
                data = literal if literal is not None else source.read_bytes()
                info = tarfile.TarInfo(destination)
                info.size = len(data); info.mode = mode; info.mtime = EPOCH; info.uid = 0; info.gid = 0
                info.uname = "root"; info.gname = "root"
                archive.addfile(info, BytesIO(data))
    return raw.getvalue()


def tree_entries(source: Path, destination: str) -> list[tuple[Path, str, int, None]]:
    result = []
    for path in source.rglob("*"):
        if not path.is_file() or "__pycache__" in path.parts or path.suffix == ".pyc":
            continue
        relative = path.relative_to(source).as_posix()
        mode = 0o755 if os.access(path, os.X_OK) else 0o644
        result.append((path, f"{destination}/{relative}", mode, None))
    return result


def ar_member(name: str, payload: bytes) -> bytes:
    encoded_name = (name + "/").encode().ljust(16, b" ")
    header = encoded_name + str(EPOCH).encode().ljust(12, b" ") + b"0     0     100644  " + str(len(payload)).encode().ljust(10, b" ") + b"`\n"
    return header + payload + (b"\n" if len(payload) % 2 else b"")


def build() -> Path:
    version = (ROOT / "VERSION").read_text().strip()
    control = (ROOT / "packaging/debian/control.in").read_text().replace("@VERSION@", version).encode()
    control_entries = [
        (None, "control", 0o644, control),
        (ROOT / "packaging/debian/conffiles", "conffiles", 0o644, None),
        (ROOT / "packaging/debian/preinst", "preinst", 0o755, None),
        (ROOT / "packaging/debian/postinst", "postinst", 0o755, None),
        (ROOT / "packaging/debian/prerm", "prerm", 0o755, None),
        (ROOT / "packaging/debian/postrm", "postrm", 0o755, None),
    ]
    release = f"opt/linux-cmnd/releases/{version}"
    data_entries = []
    for name in ("src", "config", "deploy"):
        data_entries += tree_entries(ROOT / name, f"{release}/{name}")
    for name in ("LICENSE", "VERSION"):
        data_entries.append((ROOT / name, f"{release}/{name}", 0o644, None))
    data_entries += tree_entries(ROOT / "deploy", "usr/share/linux-cmnd")
    data_entries += [
        (ROOT / "packaging/debian/cmndctl", "usr/bin/cmndctl", 0o755, None),
        (ROOT / "config/cmnd.example.toml", "etc/cmnd/cmnd.toml", 0o640, None),
        (ROOT / "README.md", "usr/share/doc/linux-cmnd/README.md", 0o644, None),
    ]
    deb = b"!<arch>\n" + ar_member("debian-binary", b"2.0\n")
    deb += ar_member("control.tar.gz", tar_blob(control_entries))
    deb += ar_member("data.tar.gz", tar_blob(data_entries))
    output = ROOT / "dist" / f"linux-cmnd_{version}_amd64.deb"
    output.parent.mkdir(exist_ok=True)
    output.write_bytes(deb)
    (Path(str(output) + ".sha256")).write_text(f"{sha256(deb).hexdigest()}  {output.name}\n")
    return output


if __name__ == "__main__":
    print(build())
