from __future__ import annotations

import argparse
import json
import os
from pathlib import Path
import shutil
import subprocess
import sys
import tempfile

from .artifacts import inventory, safe_extract_zip, write_inventory, file_hash
from .config import ConfigError, load_config
from .protocol import ProtocolError, WIXPClient, clone_request, clone_session_state, discovery_request, power_request
from .packages import build_room_package, serve_packages
from .runtime import (RuntimeErrorCMND, backup_runtime, install_release, preflight,
                      render_runtime_config, restore_runtime, rollback, status, uninstall)
from .simulator import serve
from .callbacks import serve_callbacks
from .backups import BackupError, inspect_windows_backup, prepare_windows_restore, read_password


def emit(value) -> None:
    print(json.dumps(value, indent=2, sort_keys=True))


def extract_installer(installer: Path, output: Path, tool: str | None) -> dict:
    if not installer.is_file():
        raise RuntimeErrorCMND("installer not found")
    executable = tool or shutil.which("innoextract") or shutil.which("innounp") or shutil.which("innounp.exe")
    if not executable:
        raise RuntimeErrorCMND("no supported Inno extractor found (innoextract or innounp)")
    if output.exists():
        raise RuntimeErrorCMND("extraction output already exists")
    output.parent.mkdir(parents=True, exist_ok=True)
    with tempfile.TemporaryDirectory(prefix="cmnd-extract-", dir=output.parent) as temp:
        staging = Path(temp) / "payload"
        staging.mkdir()
        name = Path(executable).name.lower()
        command = [executable, "-e", "-d", str(staging), str(installer)] if "innoextract" in name else [executable, "-x", "-b", "-q", f"-d{staging}", str(installer)]
        run = subprocess.run(command, capture_output=True, text=True, timeout=1800)
        if run.returncode:
            raise RuntimeErrorCMND(f"extractor failed ({run.returncode}): {run.stderr[-2000:]}")
        output.mkdir()
        for child in staging.iterdir():
            shutil.move(str(child), output / child.name)
    wars = sorted(p.name for p in output.rglob("*.war"))
    result = {"installer": str(installer.resolve()), "sha256": file_hash(installer),
              "tool": str(executable), "command": command[:-1] + ["<installer>"], "exit_code": 0,
              "output": str(output.resolve()), "wars": wars}
    (output / "extraction.json").write_text(json.dumps(result, indent=2) + "\n", encoding="utf-8")
    return result


def parser() -> argparse.ArgumentParser:
    p = argparse.ArgumentParser(prog="cmndctl")
    p.add_argument("--config", default=os.environ.get("CMND_CONFIG", "config/cmnd.example.toml"))
    sub = p.add_subparsers(dest="command", required=True)
    for name in ("preflight", "doctor"):
        q = sub.add_parser(name); q.add_argument("--source", type=Path)
    q = sub.add_parser("inventory"); q.add_argument("root", type=Path); q.add_argument("--output", type=Path)
    q = sub.add_parser("safe-extract"); q.add_argument("archive", type=Path); q.add_argument("output", type=Path)
    q = sub.add_parser("extract"); q.add_argument("--installer", required=True, type=Path); q.add_argument("--output", required=True, type=Path); q.add_argument("--tool")
    q = sub.add_parser("validate-config")
    q = sub.add_parser("render-runtime-config"); q.add_argument("--output", required=True, type=Path); q.add_argument("--execute", action="store_true")
    for name in ("install", "upgrade"):
        q = sub.add_parser(name); q.add_argument("--source", required=True, type=Path); q.add_argument("--root", type=Path, default=Path("/opt/cmnd")); q.add_argument("--release", required=True)
        mode = q.add_mutually_exclusive_group(); mode.add_argument("--execute", action="store_true"); mode.add_argument("--dry-run", action="store_true")
    q = sub.add_parser("status"); q.add_argument("--root", type=Path, default=Path("/opt/cmnd"))
    for name in ("start", "stop"):
        q = sub.add_parser(name); q.add_argument("--execute", action="store_true")
    q = sub.add_parser("backup"); q.add_argument("--root", required=True, type=Path); q.add_argument("--output", required=True, type=Path)
    q = sub.add_parser("restore"); q.add_argument("--backup", required=True, type=Path); q.add_argument("--root", required=True, type=Path); q.add_argument("--execute", action="store_true")
    q = sub.add_parser("inspect-windows-backup"); q.add_argument("--backup", required=True, type=Path); q.add_argument("--password-file", type=Path); q.add_argument("--prompt-password", action="store_true")
    q = sub.add_parser("prepare-windows-restore"); q.add_argument("--backup", required=True, type=Path); q.add_argument("--staging", required=True, type=Path); q.add_argument("--password-file", type=Path); q.add_argument("--prompt-password", action="store_true"); q.add_argument("--execute", action="store_true")
    q = sub.add_parser("rollback"); q.add_argument("--root", required=True, type=Path); q.add_argument("--release", required=True); q.add_argument("--execute", action="store_true")
    q = sub.add_parser("uninstall"); q.add_argument("--root", required=True, type=Path); q.add_argument("--keep-data", action="store_true"); q.add_argument("--execute", action="store_true")
    q = sub.add_parser("simulator"); q.add_argument("--bind", default="127.0.0.1"); q.add_argument("--port", type=int, default=9079); q.add_argument("--identity", default="SIMULATOR00000001")
    q = sub.add_parser("serve-packages"); q.add_argument("--root", required=True, type=Path); q.add_argument("--bind", default="127.0.0.1"); q.add_argument("--port", type=int, default=8080)
    q = sub.add_parser("callback-server"); q.add_argument("--port", type=int, default=8080)
    q = sub.add_parser("build-room-package"); q.add_argument("output", type=Path); q.add_argument("--serial", required=True); q.add_argument("--room-id", required=True); q.add_argument("--tv-settings-template", required=True, type=Path)
    q = sub.add_parser("discover"); q.add_argument("target"); q.add_argument("--port", type=int, default=9079)
    q = sub.add_parser("power"); q.add_argument("target"); q.add_argument("state", choices=("On", "Standby")); q.add_argument("--identity", required=True); q.add_argument("--port", type=int, default=9079); q.add_argument("--execute", action="store_true")
    q = sub.add_parser("clone"); q.add_argument("target"); q.add_argument("item"); q.add_argument("version"); q.add_argument("url"); q.add_argument("--identity", required=True); q.add_argument("--port", type=int, default=9079); q.add_argument("--execute", action="store_true")
    return p


def main(argv=None) -> int:
    args = parser().parse_args(argv)
    try:
        if args.command in {"preflight", "doctor"}:
            cfg = load_config(args.config)
            checked_ports = (cfg.database_port, cfg.tomcat_http, cfg.tomcat_https, cfg.apache_http, cfg.apache_https)
            result = preflight(args.source, checked_ports); result["configuration"] = {"valid": True, "mode": cfg.mode}; emit(result)
        elif args.command == "inventory":
            records = inventory(args.root)
            if args.output: write_inventory(records, args.output)
            emit({"files": len(records), "output": str(args.output) if args.output else None})
        elif args.command == "safe-extract": emit({"files": safe_extract_zip(args.archive, args.output)})
        elif args.command == "extract": emit(extract_installer(args.installer, args.output, args.tool))
        elif args.command == "validate-config":
            cfg = load_config(args.config); emit({"valid": True, "mode": cfg.mode, "allowlisted_tvs": len(cfg.allowed_tvs)})
        elif args.command == "render-runtime-config":
            cfg = load_config(args.config); emit(render_runtime_config(cfg, args.output, args.execute))
        elif args.command in {"install", "upgrade"}:
            load_config(args.config); emit(install_release(args.source, args.root, args.release, execute=args.execute))
        elif args.command == "status": emit(status(args.root))
        elif args.command in {"start", "stop"}:
            if not args.execute: emit({"executed": False, "action": args.command, "service": "cmnd-tomcat.service"})
            else:
                run = subprocess.run(["systemctl", args.command, "cmnd-tomcat.service"], check=False)
                return run.returncode
        elif args.command == "backup": emit(backup_runtime(args.root, args.output))
        elif args.command == "restore": emit(restore_runtime(args.backup, args.root, execute=args.execute))
        elif args.command == "inspect-windows-backup":
            emit(inspect_windows_backup(args.backup, read_password(args.password_file, args.prompt_password)))
        elif args.command == "prepare-windows-restore":
            password = read_password(args.password_file, args.prompt_password)
            emit(prepare_windows_restore(args.backup, args.staging, password, args.execute))
        elif args.command == "rollback": emit(rollback(args.root, args.release, execute=args.execute))
        elif args.command == "uninstall": emit(uninstall(args.root, keep_data=args.keep_data, execute=args.execute))
        elif args.command == "simulator": serve(args.bind, args.port, args.identity)
        elif args.command == "serve-packages": serve_packages(args.root, args.bind, args.port)
        elif args.command == "callback-server":
            cfg = load_config(args.config); serve_callbacks(cfg.bind, args.port)
        elif args.command == "build-room-package":
            build_room_package(args.output, args.serial, args.room_id, args.tv_settings_template)
            emit({"output": str(args.output.resolve()), "room_id": args.room_id, "final_state_verified": False})
        elif args.command == "discover":
            cfg = load_config(args.config)
            emit(WIXPClient(cfg.timeout_seconds).send(args.target, discovery_request(), args.port))
        elif args.command == "power":
            cfg = load_config(args.config); cfg.authorize(args.target, args.identity, "power", args.execute)
            emit(WIXPClient(cfg.timeout_seconds).send(args.target, power_request(args.state), args.port))
        elif args.command == "clone":
            cfg = load_config(args.config); cfg.authorize(args.target, args.identity, "clone", args.execute); cfg.validate_package_url(args.url)
            response = WIXPClient(cfg.timeout_seconds).send(args.target, clone_request(args.identity, args.item, args.version, args.url), args.port)
            emit({"response": response, "initial_clone_state": clone_session_state(response, args.item), "final_state_verified": False})
        return 0
    except (BackupError, ConfigError, ProtocolError, RuntimeErrorCMND, ValueError, OSError) as exc:
        print(f"cmndctl: {exc}", file=sys.stderr)
        return 2


if __name__ == "__main__":
    raise SystemExit(main())
