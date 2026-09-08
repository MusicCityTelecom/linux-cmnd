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
import re
import secrets
import shutil
import socket
import subprocess
import time
import zipfile


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


def run(
    *args: str,
    data: bytes | None = None,
    capture: bool = False,
    error_name: str | None = None,
):
    # Keep vendor SQL errors and container output in-process; never echo private
    # statements, credentials, or rows into the task transcript.
    result = subprocess.run(args, input=data, capture_output=True)
    if result.returncode:
        if error_name and BASE.is_dir():
            error_path = BASE / error_name
            if not error_path.exists():
                descriptor = os.open(
                    error_path, os.O_WRONLY | os.O_CREAT | os.O_EXCL, 0o600
                )
                with os.fdopen(descriptor, "wb") as handle:
                    handle.write(result.stderr)
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
            error_name=f"import-{database}.error",
        )
        run(
            *root_client,
            data=f"ALTER USER '{importer}'@'localhost' ACCOUNT LOCK;".encode(),
        )
        (BASE / f"import-{database}.cnf").unlink()

    write_qualification(manifest)


def write_qualification(manifest: dict) -> None:
    expected_tables = {
        "cas": 2,
        "tpvision": 321,
        "smartcms": 3,
        "smartcontroldb": 7,
        # The Windows backup excludes upg_setting but its retained group_view
        # requires it. Restore-over-initialized-schema adds that one vendor table.
        "smartinstall": 65,
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


def resume_smartinstall(*, error_name: str = "import-smartinstall-resume.error") -> None:
    if os.geteuid() != 0 or socket.gethostname() != HOSTNAME:
        raise SystemExit("Refusing: dedicated disposable qualification VM root required")
    if not BASE.is_dir() or (BASE / "qualification.json").exists():
        raise SystemExit("Refusing: expected incomplete restore state not found")
    if subprocess.run(["docker", "container", "inspect", CONTAINER], capture_output=True).returncode:
        raise SystemExit("Refusing: restore container is absent")
    expected_config = BASE / "import-smartinstall.cnf"
    if not expected_config.is_file():
        raise SystemExit("Refusing: SmartInstall importer state is absent")
    if any((BASE / f"import-{name}.cnf").exists() for name in DATABASES if name != "smartinstall"):
        raise SystemExit("Refusing: earlier schema import remains incomplete")
    manifest = json.loads((BASE / "input" / "manifest.json").read_text(encoding="utf-8"))
    run(
        *mysql_client("import-smartinstall.cnf", "smartinstall"),
        data=(BASE / "input" / "smartinstall.sql").read_bytes(),
        error_name=error_name,
    )
    run(
        *mysql_client("root.cnf"),
        data=b"ALTER USER 'restore_smartinstall'@'localhost' ACCOUNT LOCK;",
    )
    expected_config.unlink()
    write_qualification(manifest)


def recover_smartinstall_prerequisite() -> None:
    """Copy only proven vendor DDL, never rows, from the fresh lab schema."""
    if os.geteuid() != 0 or socket.gethostname() != HOSTNAME or not BASE.is_dir():
        raise SystemExit("Refusing prerequisite recovery outside disposable restore lab")
    if (BASE / "smartinstall-prerequisite.json").exists():
        raise SystemExit("Refusing existing prerequisite recovery evidence")
    if scalar(
        "root.cnf",
        "SELECT COUNT(*) FROM information_schema.tables WHERE table_schema='smartinstall' "
        "AND table_name='upg_setting';",
    ) != "0":
        raise SystemExit("Refusing: candidate prerequisite already exists")
    query = b"SHOW CREATE TABLE smartinstall.upg_setting;"
    result = subprocess.run(
        ["docker", "exec", "-i", "cmnd-lab-mysql", "mysql",
         "--defaults-extra-file=/run/secrets/client.cnf", "--batch", "--skip-column-names"],
        input=query, capture_output=True, check=True,
    )
    fields = result.stdout.decode("utf-8", "strict").rstrip("\n").split("\t", 1)
    if len(fields) != 2 or fields[0] != "upg_setting":
        raise RuntimeError("fresh vendor prerequisite DDL was not returned as expected")
    ddl = fields[1].replace("\\n", "\n")
    if (
        not re.match(r"^CREATE TABLE `upg_setting` \(", ddl)
        or "DEFINER" in ddl.upper()
        or ";" in ddl
        or not all(f"`{column}`" in ddl for column in ("id", "name", "platform", "version"))
    ):
        raise RuntimeError("fresh vendor prerequisite DDL failed structural validation")
    ddl_hash = hashlib.sha256(ddl.encode()).hexdigest()
    run(*mysql_client("root.cnf", "smartinstall"), data=(ddl + ";").encode())
    evidence = {
        "object": "smartinstall.upg_setting",
        "source": "existing-fresh-vendor-schema-read-only",
        "ddl_sha256": ddl_hash,
        "rows_copied": False,
        "history_modified": False,
    }
    write_private(
        BASE / "smartinstall-prerequisite.json",
        json.dumps(evidence, indent=2, sort_keys=True) + "\n",
    )
    resume_smartinstall(error_name="import-smartinstall-recovered.error")


def print_error_summary() -> None:
    if os.geteuid() != 0 or socket.gethostname() != HOSTNAME or not BASE.is_dir():
        raise SystemExit("Refusing error summary outside disposable restore lab")
    patterns = re.compile(rb"ERROR ([0-9]+).*? at line ([0-9]+)")
    summary = {}
    for path in BASE.glob("import-*.error"):
        summary[path.name] = [
            {"mysql_error": int(code), "line": int(line)}
            for code, line in patterns.findall(path.read_bytes())
        ]
    flyway_error = BASE / "flyway-migrate.error"
    if flyway_error.is_file():
        content = flyway_error.read_text(encoding="utf-8", errors="replace")
        summary[flyway_error.name] = {
            "exception_classes": sorted(set(re.findall(
                r"(?:Caused by:\s*)?([A-Za-z_$][A-Za-z0-9_$.]*(?:Exception|Error))",
                content,
            ))),
            "sql_states": sorted(set(re.findall(r"SQL State\s*:\s*([A-Z0-9]+)", content))),
            "error_codes": sorted(set(int(value) for value in re.findall(
                r"Error Code\s*:\s*([0-9]+)", content
            ))),
            "checksum_mismatch_versions": sorted(set(re.findall(
                r"Migration checksum mismatch for migration version ([0-9.]+)",
                content,
            ))),
            "categories": [name for name, needle in {
                "validation": "Validate failed",
                "checksum": "checksum mismatch",
                "connectivity": "Unable to obtain connection",
                "unsupported_database": "Unsupported Database",
                "migration_failure": "Migration of schema",
            }.items() if needle.lower() in content.lower()],
        }
    print(json.dumps(summary, sort_keys=True))


def compare_fresh_schema() -> None:
    """Print vendor object-name differences only; never table rows."""
    if os.geteuid() != 0 or socket.gethostname() != HOSTNAME or not BASE.is_dir():
        raise SystemExit("Refusing schema comparison outside disposable restore lab")
    query = (
        "SELECT CONCAT(TABLE_NAME,':',TABLE_TYPE) FROM information_schema.tables "
        "WHERE table_schema='smartinstall' ORDER BY TABLE_NAME;"
    ).encode()
    current = subprocess.run(
        ["docker", "exec", "-i", "cmnd-lab-mysql", "mysql",
         "--defaults-extra-file=/run/secrets/client.cnf", "--batch", "--skip-column-names"],
        input=query, capture_output=True, check=True,
    ).stdout.decode().splitlines()
    restored = run(*mysql_client("root.cnf"), data=query, capture=True).decode().splitlines()
    print(json.dumps({
        "fresh_count": len(current),
        "restore_partial_count": len(restored),
        "fresh_only": sorted(set(current) - set(restored)),
        "restore_only": sorted(set(restored) - set(current)),
    }, sort_keys=True))


def migrate_smartinstall() -> None:
    """Run only shipped Flyway migrations against the isolated restored DB."""
    if os.geteuid() != 0 or socket.gethostname() != HOSTNAME:
        raise SystemExit("Refusing migration outside disposable qualification VM")
    if not (BASE / "qualification.json").is_file():
        raise SystemExit("Refusing migration before successful restore qualification")
    evidence_path = BASE / "migration-qualification.json"
    runner = BASE / "flyway-runner"
    if evidence_path.exists() or runner.exists() or runner.is_symlink():
        raise SystemExit("Refusing existing migration-runner state")
    if scalar("root.cnf", "SELECT @@event_scheduler;").upper() != "OFF":
        raise SystemExit("Refusing migration while event scheduler is enabled")
    before = scalar(
        "root.cnf",
        "SELECT CONCAT(COUNT(*),':',SUM(success=1),':',SUM(version='8.7')) "
        "FROM smartinstall.flyway_schema_history;",
    )
    if before != "112:112:1":
        raise SystemExit("Refusing unexpected pre-migration Flyway history")

    vendor_war = Path("/home/cmndlab/input/vendor/SmartInstall.war")
    if not vendor_war.is_file():
        raise SystemExit("Refusing: private vendor SmartInstall WAR missing")
    runner.mkdir(mode=0o700)
    libraries = runner / "lib"
    migrations = runner / "migration"
    libraries.mkdir(mode=0o700)
    migrations.mkdir(mode=0o700)
    flyway_jars = []
    migration_count = 0
    with zipfile.ZipFile(vendor_war) as archive:
        seen = set()
        for member in archive.infolist():
            if member.filename.startswith("WEB-INF/lib/") and member.filename.endswith(".jar"):
                destination = libraries / Path(member.filename).name
            elif (
                member.filename.startswith("WEB-INF/classes/db/migration/")
                and member.filename.endswith(".sql")
            ):
                destination = migrations / Path(member.filename).name
                migration_count += 1
            else:
                continue
            if destination.name in seen:
                raise RuntimeError("duplicate Flyway runner input name")
            seen.add(destination.name)
            with archive.open(member) as incoming, destination.open("xb") as outgoing:
                shutil.copyfileobj(incoming, outgoing, 1024 * 1024)
            destination.chmod(0o600)
            if destination.name.startswith("flyway-core-"):
                flyway_jars.append(destination.name)
    if migration_count != 122 or flyway_jars != ["flyway-core-7.15.0.jar"]:
        raise RuntimeError("unexpected shipped Flyway payload")

    credentials = json.loads((BASE / "credentials.json").read_text(encoding="utf-8"))
    write_private(runner / "db-password", credentials["applications"]["smartinstall"])
    java_source = """import java.nio.file.Files;
import java.nio.file.Path;
import org.flywaydb.core.Flyway;
public final class CmndFlywayRunner {
  public static void main(String[] ignored) throws Exception {
    String password = Files.readString(Path.of("/var/lib/cmnd-lab-restore-748/flyway-runner/db-password"));
    Flyway flyway = Flyway.configure()
      .dataSource("jdbc:mysql://172.30.44.5:3306/smartinstall?serverTimezone=UTC&useUnicode=true&characterEncoding=utf8", "siuser", password)
      .locations("filesystem:/var/lib/cmnd-lab-restore-748/flyway-runner/migration")
      .table("flyway_schema_history")
      .baselineOnMigrate(false)
      .validateOnMigrate(true)
      .load();
    System.out.println("MIGRATIONS=" + flyway.migrate().migrationsExecuted);
  }
}
"""
    write_private(runner / "CmndFlywayRunner.java", java_source)
    classpath = str(libraries / "*")
    run(
        "javac", "-cp", classpath, str(runner / "CmndFlywayRunner.java"),
        error_name="flyway-compile.error",
    )
    execution = subprocess.run(
        ["java", "-cp", f"{runner}:{classpath}", "CmndFlywayRunner"],
        capture_output=True,
    )
    if execution.returncode:
        write_private(BASE / "flyway-migrate.error", execution.stderr.decode("utf-8", "replace"))
        raise RuntimeError("standalone Flyway migration failed; private error retained")
    match = re.search(rb"^MIGRATIONS=([0-9]+)$", execution.stdout, re.MULTILINE)
    if not match or int(match.group(1)) != 10:
        raise RuntimeError("unexpected standalone Flyway migration count")

    after = scalar(
        "root.cnf",
        "SELECT CONCAT(COUNT(*),':',SUM(success=1),':',SUM(version='9.7')) "
        "FROM smartinstall.flyway_schema_history;",
    )
    tables = scalar(
        "root.cnf",
        "SELECT COUNT(*) FROM information_schema.tables WHERE table_schema='smartinstall';",
    )
    unchanged = {
        "cas": scalar("root.cnf", "SELECT COUNT(*) FROM cas.schema_version;"),
        "smartcms": scalar("root.cnf", "SELECT COUNT(*) FROM smartcms.schema_version;"),
        "smartcontroldb": scalar("root.cnf", "SELECT COUNT(*) FROM smartcontroldb.schema_version;"),
        "tpvision": scalar(
            "root.cnf",
            "SELECT COUNT(*) FROM information_schema.tables WHERE table_schema='tpvision';",
        ),
    }
    if after != "122:122:1" or tables != "67" or unchanged != {
        "cas": "1", "smartcms": "1", "smartcontroldb": "27", "tpvision": "321"
    }:
        raise RuntimeError("post-Flyway structural qualification mismatch")
    if scalar("root.cnf", "SELECT @@event_scheduler;").upper() != "OFF":
        raise RuntimeError("event scheduler changed during standalone migration")
    evidence = {
        "runner": "standalone-org.flywaydb.core.Flyway",
        "flyway": "7.15.0",
        "vendor_war_sha256": sha256_file(vendor_war),
        "bundled_migration_files": migration_count,
        "migrations_executed": 10,
        "before": {"history_rows": 112, "final_version": "8.7", "tables_and_views": 65},
        "after": {"history_rows": 122, "successful_rows": 122, "final_version": "9.7", "tables_and_views": 67},
        "other_schema_assertions": unchanged,
        "event_scheduler": "OFF",
        "application_startup_performed": False,
        "tv_contact_performed": False,
    }
    write_private(evidence_path, json.dumps(evidence, indent=2, sort_keys=True) + "\n")
    print("Standalone Flyway 7.4.8-to-current migration qualification passed.")


def history_snapshot() -> list[list[str]]:
    query = (
        "SELECT installed_rank,HEX(IFNULL(version,'')),HEX(description),HEX(type),"
        "HEX(script),IFNULL(checksum,'NULL'),HEX(installed_by),"
        "DATE_FORMAT(installed_on,'%Y-%m-%dT%H:%i:%s'),execution_time,success "
        "FROM smartinstall.flyway_schema_history ORDER BY installed_rank;"
    )
    output = run(*mysql_client("root.cnf"), data=query.encode(), capture=True).decode()
    return [line.split("\t") for line in output.splitlines()]


def repair_and_migrate_smartinstall() -> None:
    """Exercise an isolated checksum-acceptance hypothesis, then migrate.

    This is deliberately not part of restore initialization.  The differing
    historical migration sources are unavailable, so this lab action does not
    establish semantic equivalence and must not be promoted to an installer or
    production restore path.
    """
    if os.geteuid() != 0 or socket.gethostname() != HOSTNAME:
        raise SystemExit("Refusing migration repair outside disposable qualification VM")
    runner = BASE / "flyway-runner"
    error_path = BASE / "flyway-migrate.error"
    evidence_path = BASE / "migration-qualification.json"
    if not runner.is_dir() or not error_path.is_file() or evidence_path.exists():
        raise SystemExit("Refusing: expected failed validation state not found")
    content = error_path.read_text(encoding="utf-8", errors="replace")
    mismatch_versions = set(re.findall(
        r"Migration checksum mismatch for migration version ([0-9.]+)", content
    ))
    if mismatch_versions != {"2.4", "2.13", "4.5"}:
        raise SystemExit("Refusing unproven Flyway checksum mismatch set")
    if scalar("root.cnf", "SELECT @@event_scheduler;").upper() != "OFF":
        raise SystemExit("Refusing repair while event scheduler is enabled")
    before = history_snapshot()
    if len(before) != 112 or any(row[-1] != "1" for row in before):
        raise SystemExit("Refusing unexpected pre-repair history")
    snapshot_bytes = ("\n".join("\t".join(row) for row in before) + "\n").encode()
    descriptor = os.open(
        BASE / "flyway-history-before-repair.tsv",
        os.O_WRONLY | os.O_CREAT | os.O_EXCL,
        0o600,
    )
    with os.fdopen(descriptor, "wb") as handle:
        handle.write(snapshot_bytes)

    source = """import java.nio.file.Files;
import java.nio.file.Path;
import org.flywaydb.core.Flyway;
public final class CmndFlywayRepairRunner {
  public static void main(String[] ignored) throws Exception {
    String password = Files.readString(Path.of("/var/lib/cmnd-lab-restore-748/flyway-runner/db-password"));
    Flyway flyway = Flyway.configure()
      .dataSource("jdbc:mysql://172.30.44.5:3306/smartinstall?serverTimezone=UTC&useUnicode=true&characterEncoding=utf8", "siuser", password)
      .locations("filesystem:/var/lib/cmnd-lab-restore-748/flyway-runner/migration")
      .table("flyway_schema_history").baselineOnMigrate(false).validateOnMigrate(true).load();
    flyway.repair();
    System.out.println("MIGRATIONS=" + flyway.migrate().migrationsExecuted);
  }
}
"""
    write_private(runner / "CmndFlywayRepairRunner.java", source)
    classpath = str(runner / "lib" / "*")
    run(
        "javac", "-cp", classpath, str(runner / "CmndFlywayRepairRunner.java"),
        error_name="flyway-repair-compile.error",
    )
    execution = subprocess.run(
        ["java", "-cp", f"{runner}:{classpath}", "CmndFlywayRepairRunner"],
        capture_output=True,
    )
    if execution.returncode:
        write_private(BASE / "flyway-repair-migrate.error", execution.stderr.decode("utf-8", "replace"))
        raise RuntimeError("Flyway repair/migrate failed; private error retained")
    match = re.search(rb"^MIGRATIONS=([0-9]+)$", execution.stdout, re.MULTILINE)
    if not match or int(match.group(1)) != 10:
        raise RuntimeError("unexpected post-repair Flyway migration count")

    after = history_snapshot()
    if len(after) != 122 or any(row[-1] != "1" for row in after):
        raise RuntimeError("unexpected post-migration history")
    changed = set()
    for original, repaired in zip(before, after[:112]):
        if original[:5] + original[6:] != repaired[:5] + repaired[6:]:
            raise RuntimeError("Flyway repair changed retained non-checksum history")
        if original[5] != repaired[5]:
            changed.add(bytes.fromhex(original[1]).decode())
    if changed != mismatch_versions:
        raise RuntimeError("Flyway repair changed an unexpected checksum set")
    if scalar("root.cnf", "SELECT COUNT(*) FROM information_schema.tables WHERE table_schema='smartinstall';") != "67":
        raise RuntimeError("post-migration SmartInstall object count mismatch")
    if scalar("root.cnf", "SELECT @@event_scheduler;").upper() != "OFF":
        raise RuntimeError("event scheduler changed during migration")
    final = {
        "qualification_scope": "isolated-experimental-checksum-acceptance",
        "runner": "standalone-org.flywaydb.core.Flyway",
        "flyway": "7.15.0",
        "vendor_war_sha256": sha256_file(Path("/home/cmndlab/input/vendor/SmartInstall.war")),
        "repair_performed": True,
        "vendor_startup_invokes_repair": False,
        "historical_semantic_equivalence_proven": False,
        "repair_checksum_versions": sorted(changed),
        "retained_rows_before": 112,
        "retained_non_checksum_fields_unchanged": True,
        "history_before_sha256": hashlib.sha256(snapshot_bytes).hexdigest(),
        "migrations_executed": 10,
        "after": {"history_rows": 122, "successful_rows": 122, "final_version": "9.7", "tables_and_views": 67},
        "event_scheduler": "OFF",
        "application_startup_performed": False,
        "tv_contact_performed": False,
    }
    write_private(evidence_path, json.dumps(final, indent=2, sort_keys=True) + "\n")
    print("Explicit Flyway checksum repair and 7.4.8-to-current migration passed.")


if __name__ == "__main__":
    parser = argparse.ArgumentParser()
    parser.add_argument("--source", type=Path)
    parser.add_argument('--execute', action='store_true')
    parser.add_argument('--resume-smartinstall', action='store_true')
    parser.add_argument('--error-summary', action='store_true')
    parser.add_argument('--compare-fresh-schema', action='store_true')
    parser.add_argument('--recover-smartinstall-prerequisite', action='store_true')
    parser.add_argument('--migrate-smartinstall', action='store_true')
    parser.add_argument('--repair-and-migrate-smartinstall', action='store_true')
    args = parser.parse_args()
    if args.error_summary:
        print_error_summary()
        raise SystemExit(0)
    if args.compare_fresh_schema:
        compare_fresh_schema()
        raise SystemExit(0)
    if not args.execute:
        raise SystemExit('Restore initialization requires --execute and approved private input transfer')
    if args.repair_and_migrate_smartinstall:
        repair_and_migrate_smartinstall()
    elif args.migrate_smartinstall:
        migrate_smartinstall()
    elif args.recover_smartinstall_prerequisite:
        recover_smartinstall_prerequisite()
    elif args.resume_smartinstall:
        resume_smartinstall()
    else:
        if args.source is None:
            raise SystemExit("Initial restore requires --source")
        qualified_main(args.source.resolve())
