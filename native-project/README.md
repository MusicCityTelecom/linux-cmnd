# Editable CMND native research project

This is the **development project**, not an installable replacement for CMND.
It is separate from the compatibility installer on `main`. No command below
starts CMND, initializes a database, scans/enrolls TVs, or modifies services.

## Where to work

| Location | Purpose |
| --- | --- |
| `modules/<component>/src/main/java/` | Editable Java sources for reload, smartcms, cas, usermanagement, smartcontrol and SmartInstall |
| `source-map.json` | Exact untouched recovery file/hash from which each editable source originated |
| `catalog/sources.json` | Every recovered source/notice occurrence, including alternate decompilers, third-party libraries, managed code, native pseudocode and original headers |
| `catalog/original-inputs.json` | All original installer files and retained non-Java inventory entries, including unrecovered native components |
| `catalog/platform-candidates.json` | Host command/path/native-library review queue; matches are candidates, not confirmed defects |
| `resources/<component>/` | Full editable non-class web/config/media resources, prepared locally from the original WARs; ignored by Git |
| `resources/distributions/` | Separate SmartCMS frontend, Philips resources, plugins, native/RF and other supporting distributions; prepared locally, ignored by Git |
| `working/` | On-demand editable copies of any cataloged library/native/alternate source; ignored by Git until reviewed and deliberately promoted |
| `.vendor/` | Hash-verified original WAR/JAR files and dependencies; ignored by Git |
| `build/run-*/` | Fresh compiler logs, evidence and research class JARs; ignored by Git |
| `../recovered/` | Untouched recovered reference baseline; do not edit it |

The 42,143 distinct reference files have 42,349 provenance records because some
identical files have multiple original locations. All 2,352 primary Java source
files are represented in the editable modules. A class count is not a source
file count: inner classes and compiler-generated classes complicate comparisons.

RF/DekTec, Gateway, packers and third-party source remain in the catalog. Their
presence does not mean that their native binaries can already be rebuilt.
All unchanged vendor inputs are available in the
[research release](https://github.com/MusicCityTelecom/linux-cmnd/releases/tag/research-recovery-2026-09-12).

## Linux development setup

Use an isolated Ubuntu 24.04 development machine or VM, not a hotel CMND server.
Python 3.11+ and **JDK 17 including javac** are required. A JRE alone is insufficient.

```sh
sudo apt update
sudo apt install -y git python3 openjdk-17-jdk-headless p7zip-full curl ca-certificates
git clone --branch codex/java-native-rewrite https://github.com/MusicCityTelecom/linux-cmnd.git
cd linux-cmnd
mkdir -p native-project/.vendor/downloads native-project/.vendor/extracted-tomcat
curl --fail --location --proto '=https' --proto-redir '=https' \
  -o native-project/.vendor/downloads/original-installer-tree.zip \
  https://github.com/MusicCityTelecom/linux-cmnd/releases/download/research-recovery-2026-09-12/original-installer-tree.zip
echo 'dde03139345da3d073299c1330ead0d0f2f459a8969f85d26fde0b1abe548298  native-project/.vendor/downloads/original-installer-tree.zip' | sha256sum -c -
```

Stop if the hash check fails. Extract the original Tomcat distribution as data;
**do not run its Windows installer**:

```sh
7z x native-project/.vendor/downloads/original-installer-tree.zip \
  '-onative-project/.vendor/extracted-tomcat' '{app}/apache-tomcat-9.0.109.exe'
7z x 'native-project/.vendor/extracted-tomcat/{app}/apache-tomcat-9.0.109.exe' \
  '-onative-project/.vendor/tomcat-reference'
python3 scripts/native_project.py prepare \
  --vendor-tree native-project/.vendor/downloads/original-installer-tree.zip \
  --tomcat-home native-project/.vendor/tomcat-reference
python3 scripts/native_project.py prepare-distributions \
  --vendor-tree native-project/.vendor/downloads/original-installer-tree.zip
python3 scripts/native_project.py build --java-home /usr/lib/jvm/java-17-openjdk-amd64
```

These setup commands describe Ubuntu's package layout. The build driver itself
also runs on Windows and Debian; use your actual JDK home. Original Tomcat JARs
are checked against `tomcat-lock.json`; a different version is rejected.
Compilation runs offline with annotation processing disabled. WAR-bundled old
Servlet API copies are retained, but the original container API has classpath
precedence. No library is silently upgraded or removed to obtain compilation.
`prepare-distributions` also extracts the separate `SmartCMS.zip` frontend and
every other supporting ZIP, not just the small SmartCMS Java WAR. Its complete
file/hash index is in `catalog/supporting-distributions.json`. Extracted Windows
EXEs/DLLs are reference data, not runnable Linux replacements. Nested archives
remain intact for further inspection; extraction is not native decompilation.

## Normal edit/build cycle

Edit files directly in `modules/<component>/src/main/java/`, then:

```sh
python3 scripts/native_project.py build --java-home /usr/lib/jvm/java-17-openjdk-amd64 --module SmartInstall
python3 scripts/native_project.py audit-platform
python3 scripts/native_project.py validate
python3 -m unittest discover -s tests -p test_native_project.py -v
```

`init` refreshes catalogs and creates missing primary copies without resetting
developer changes. Explicitly changing a decompiler selection in `project.json`
only replaces a **pristine, hash-matching** working copy; it refuses to overwrite
an edited one. Alternate/native/library files can be copied using an exact
`path` from `catalog/sources.json`, for example:

```sh
python3 scripts/native_project.py checkout 'java/cfr/cas/org/apereo/cas/adaptors/jdbc/UsersManager.java'
python3 scripts/native_project.py find 'Gateway'
python3 scripts/native_project.py checkout-tree 'dotnet/Gateway'
python3 scripts/native_project.py checkout-tree 'native'
```

For an IDE, open this repository and mark each module's `src/main/java` as a
source root. Configure JDK 17; use `tomcat-lock.json` and the prepared per-module
dependencies for indexing. The Python build driver is the authoritative build
command; the timestamped `javac.args` records its exact source/classpath inputs.

Do not edit the reference baseline. Review changes under `resources/` or
`working/` before moving selected text into a tracked module; keep binaries in
release assets and customer/generated secrets private. Preparing again refuses
to overwrite changed resources. Preserve all originals and applicable notices.

## What a successful build means

Output JARs contain rebuilt **classes only**, not complete deployable WARs. Failed
modules do not produce a success artifact. Partial classes/logs are retained for
diagnosis. Build reports separately record compiler results, decompiler warnings,
original/output class-name differences and artifact hashes. Changes in synthetic
class names need analysis, not automatic deletion or a false completeness claim.

There is no deploy or TV-write command in this project. A compiler pass is not a
runtime, browser, licensing, backup/restore, packaging or real-TV test. See
`ROADMAP.md`, `REPAIRS.md` and `BUILD-EVIDENCE.md` for current boundaries.

Vendor and third-party ownership/licenses remain applicable. The repository's
tooling license does not relicense recovered vendor applications. The project
is free of charge and distribution does not imply vendor endorsement. See the
repository disclaimer; no disclaimer is injected into the application UI.
