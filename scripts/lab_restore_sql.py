"""Import a prepared CMND 7.4.8 SQL stage into a new isolated lab MySQL.

VM-only qualification helper. It refuses production-like hosts, existing state,
non-internal networks, and unverified prepared input. It never starts CMND apps.
"""

from __future__ import annotations

import argparse
import hashlib
import json
import os
from pathlib import Path
import secrets
import shutil
import socket
import subprocess
import time


HOSTNAME = "cmnd-qualification"
BASE = Path("/var/lib/cmnd-lab-restore-748")
CONTAINER = "cmnd-lab-restore-748"
VOLUME = "cmnd-lab-restore-748-data"
NETWORK = "cmnd-lab-db"
ADDRESS = "172.30.44.5"
MYSQL_IMAGE = "mysql@sha256:4bc6bc963e6d8443453676cae56536f4b8156d78bae03c0145cbe47c2aad73bb"
EXPECTED_ARCHIVE_SHA256 = "9285176231c01fbb1a866ba3dcf9d67a59d190abbd93f9d9cf2745926f8dbe30"
DATABASES = {
    "cas": ("cas", "schema_version", 1, "1"),
    "tpvision": ("tpvision", None, None, None),
    "smartcms": ("smartcms", "schema_version", 1, "1"),
    "smartcontroldb": ("smartcontrol", "schema_version", 27, "1.5.1"),
    "smartinstall": ("siuser", "flyway_schema_history", 112, "8.7"),
}


def run(*args: str, data: bytes | None = None, capture: bool = False):
    # Keep vendor SQL errors and container output in-process; never echo private
    # statements, credentials, or rows into the task transcript.
    result = subprocess.run(args, input=data, capture_output=True)
    if result.returncode:
        raise RuntimeError(f"{args[0]} failed; private lab state retained for diagnosis")
    return result.stdout if capture else b""


def write_private(path: Path, content: str) -> None:
    descriptor = os.open(path, os.O_WRONLY | os.O_CREAT | os.O_EXCL, 0o600)
    with os.fdopen(descriptor, "w", encoding="utf-8") as handle:
        handle.write(content)


def sha256_file(path: Path) -> str:
    digest = hashlib.sha256()
    with path.open("rb") as handle:
        for block in iter(lambda: handle.read(1024 * 1024), b""):
            digest.update(block)
    return digest.hexdigest()


def mysql_config(user: str, password: str) -> str:
    return f"[client]\nuser={user}\npassword={password}\nlocal-infile=0\n"


def mysql_client(config_name: str, database: str | None = None) -> list[str]:
    command = [
        "docker", "exec", "-i", CONTAINER, "mysql",
        f"--defaults-extra-file=/run/cmnd-restore/{config_name}",
        "--binary-mode=1", "--local-infile=0", "--batch", "--skip-column-names",
    ]
    if database:
        command.append(database)
    return command


def scalar(config_name: str, sql: str) -> str:
    return run(*mysql_client(config_name), data=sql.encode(), capture=True).decode().strip()


def qualified_main(source: Path) -> None:
    """Implementation split out so existence probes can inspect return codes."""
    if os.geteuid() != 0 or socket.gethostname() != HOSTNAME:
        raise SystemExit("Refusing: dedicated disposable qualification VM root required")
    if not Path("/var/lib/cmnd-lab-bootstrap-complete").is_file():
        raise SystemExit("Refusing: lab bootstrap marker missing")
    if BASE.exists() or BASE.is_symlink():
        raise SystemExit(f"Refusing existing state: {BASE}")
    if subprocess.run(["docker", "container", "inspect", CONTAINER], capture_output=True).returncode == 0:
        raise SystemExit("Refusing existing restore container")
    if subprocess.run(["docker", "volume", "inspect", VOLUME], capture_output=True).returncode == 0:
        raise SystemExit("Refusing existing restore volume")
    network = json.loads(run("docker", "network", "inspect", NETWORK, capture=True))[0]
    if not network.get("Internal"):
        raise SystemExit("Refusing non-internal Docker network")
    configs = network.get("IPAM", {}).get("Config", [])
    if not any(config.get("Subnet") == "172.30.44.0/24" for config in configs):
        raise SystemExit("Refusing unexpected Docker subnet")
    for endpoint in network.get("Containers", {}).values():
        if endpoint.get("IPv4Address", "").split("/")[0] == ADDRESS:
            raise SystemExit("Refusing occupied restore address")

    manifest_path = source / "manifest.json"
    manifest = json.loads(manifest_path.read_text(encoding="utf-8"))
    if (
        manifest.get("format") != "cmnd-windows-7.4.8-sql-restore-preparation-v1"
        or manifest.get("archive_sha256") != EXPECTED_ARCHIVE_SHA256
        or manifest.get("database_execution_performed") is not False
        or manifest.get("customer_content_extracted") is not False
        or set(manifest.get("databases", {})) != set(DATABASES)
    ):
        raise SystemExit("Refusing unverified prepared restore manifest")
    for database, details in manifest["databases"].items():
        sql_path = source / details["staged_file"]
        if (
            not sql_path.is_file()
            or sql_path.parent.resolve() != source.resolve()
            or sha256_file(sql_path) != details["staged_sha256"]
        ):
            raise SystemExit("Refusing prepared SQL checksum/path mismatch")

    BASE.mkdir(mode=0o700)
    os.umask(0o077)
    input_directory = BASE / "input"
    input_directory.mkdir(mode=0o700)
    for name in ("manifest.json", *(f"{database}.sql" for database in DATABASES)):
        destination = input_directory / name
        with (source / name).open("rb") as incoming, destination.open("xb") as outgoing:
            shutil.copyfileobj(incoming, outgoing, 1024 * 1024)
        destination.chmod(0o600)

    root_password = secrets.token_hex(32)
    application_passwords = {database: secrets.token_hex(32) for database in DATABASES}
    importer_passwords = {database: secrets.token_hex(32) for database in DATABASES}
    credentials = {
        "root": root_password,
        "applications": application_passwords,
    }
    write_private(BASE / "credentials.json", json.dumps(credentials, sort_keys=True))
    write_private(BASE / "root-password", root_password)
    write_private(BASE / "root.cnf", mysql_config("root", root_password))
    for database in DATABASES:
        write_private(
            BASE / f"import-{database}.cnf",
            mysql_config(f"restore_{database}", importer_passwords[database]),
        )

    run("docker", "volume", "create", VOLUME)
    run(
        "docker", "run", "-d", "--name", CONTAINER,
        "--network", NETWORK, "--ip", ADDRESS,
        "--mount", f"type=volume,src={VOLUME},dst=/var/lib/mysql",
        "--mount", f"type=bind,src={BASE},dst=/run/cmnd-restore,readonly",
        "-e", "MYSQL_ROOT_PASSWORD_FILE=/run/cmnd-restore/root-password",
        MYSQL_IMAGE, "--event-scheduler=OFF", "--local-infile=0",
        "--sql-mode=NO_ENGINE_SUBSTITUTION",
    )
    root_client = mysql_client("root.cnf")
    for _ in range(90):
        result = subprocess.run(root_client, input=b"SELECT 1;", capture_output=True)
        if result.returncode == 0:
            break
        time.sleep(1)
    else:
        raise RuntimeError("restore MySQL did not become ready")

    for database, (application_user, _, _, _) in DATABASES.items():
        importer = f"restore_{database}"
        # Generated secrets are hexadecimal, so SQL quoting cannot be escaped by
        # their values. Account and schema identifiers are fixed constants.
        initialization = (
            f"CREATE DATABASE `{database}` CHARACTER SET utf8 COLLATE utf8_general_ci;"
            f"CREATE USER '{application_user}'@'%' IDENTIFIED BY '{application_passwords[database]}';"
            f"GRANT ALL ON `{database}`.* TO '{application_user}'@'%';"
            f"CREATE USER '{importer}'@'localhost' IDENTIFIED BY '{importer_passwords[database]}';"
            f"GRANT ALL ON `{database}`.* TO '{importer}'@'localhost';"
        )
        run(*root_client, data=initialization.encode())
        privilege_check = scalar(
            "root.cnf",
            "SELECT CONCAT(File_priv,Super_priv,Grant_priv) FROM mysql.user "
            f"WHERE User='{importer}' AND Host='localhost';",
        )
        if privilege_check != "NNN":
            raise RuntimeError("importer unexpectedly has global privileges")
        run(
            *mysql_client(f"import-{database}.cnf", database),
            data=(input_directory / f"{database}.sql").read_bytes(),
        )
        run(
            *root_client,
            data=f"ALTER USER '{importer}'@'localhost' ACCOUNT LOCK;".encode(),
        )
        (BASE / f"import-{database}.cnf").unlink()

    expected_tables = {
        "cas": 2,
        "tpvision": 321,
        "smartcms": 3,
        "smartcontroldb": 7,
        "smartinstall": 64,
    }
    results: dict[str, dict] = {}
    for database, (_, history_table, history_rows, final_version) in DATABASES.items():
        table_count = int(
            scalar(
                "root.cnf",
                "SELECT COUNT(*) FROM information_schema.tables "
                f"WHERE table_schema='{database}';",
            )
        )
        if table_count != expected_tables[database]:
            raise RuntimeError("restored schema table-count mismatch")
        history_result = None
        if history_table:
            history_result = scalar(
                "root.cnf",
                f"SELECT CONCAT(COUNT(*),':',SUM(success=1),':',"
                f"SUM(version='{final_version}')) FROM `{database}`.`{history_table}`;",
            )
            if history_result != f"{history_rows}:{history_rows}:1":
                raise RuntimeError("restored migration-history mismatch")
        results[database] = {
            "tables_and_views": table_count,
            "history_table": history_table,
            "history_rows": history_rows,
            "final_history_version": final_version,
            "history_check": "PASS" if history_table else "NOT_APPLICABLE",
            "staged_sha256": manifest["databases"][database]["staged_sha256"],
        }

    if scalar(
        "root.cnf",
        "SELECT COUNT(*) FROM information_schema.schemata WHERE BINARY schema_name='SmartInstall';",
    ) != "0":
        raise RuntimeError("unexpected uppercase SmartInstall schema")
    qualification = {
        "container": CONTAINER,
        "volume": VOLUME,
        "network": NETWORK,
        "address": ADDRESS,
        "image": MYSQL_IMAGE,
        "archive_sha256": EXPECTED_ARCHIVE_SHA256,
        "database_execution_performed": True,
        "application_startup_performed": False,
        "local_infile": False,
        "event_scheduler": "OFF",
        "importers_locked": True,
        "databases": results,
    }
    write_private(BASE / "qualification.json", json.dumps(qualification, indent=2, sort_keys=True) + "\n")
    print("Isolated CMND 7.4.8 SQL restore qualification passed; apps were not started.")


if __name__ == "__main__":
    parser = argparse.ArgumentParser()
    parser.add_argument("--source", type=Path, required=True)
    parser.add_argument('--execute', action='store_true')
    args = parser.parse_args()
    if not args.execute:
        raise SystemExit('Restore initialization requires --execute and approved private input transfer')
    qualified_main(args.source.resolve())
