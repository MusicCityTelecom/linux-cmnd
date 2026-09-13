# Initial editable-project qualification

## Native Linux compilation

All six primary Java modules compiled successfully on Debian 13 under WSL using
an isolated Eclipse Temurin **JDK 17.0.20.1+1 Linux x64**. This was native Linux
`javac`, not Wine or a Windows compiler. No system JDK was installed; an official
portable archive was hash-verified and extracted into a dedicated temporary
directory. No InnAware services were reconfigured.

| Module | Primary Java source files | Original classes | Rebuilt application classes | Compiler |
| --- | ---: | ---: | ---: | --- |
| reload | 1 | 2 | 2 | Passed |
| smartcms | 12 | 12 | 12 | Passed |
| cas | 41 | 74 | 71 | Passed |
| usermanagement | 57 | 91 | 88 | Passed |
| smartcontrol | 1,118 | 1,222 | 1,218 | Passed |
| SmartInstall | 1,123 | 2,412 | 2,409 | Passed |

CAS also compiles one build-only `lombok.Generated` declaration, excluded from
the output artifact. The 13 missing original class-name occurrences are listed
explicitly in the evidence. All 13 were confirmed to have `ACC_SYNTHETIC` in
the original bytecode. They are nested `$1` classes; source/bytecode and
linkage review is required before accepting any compiler-generated structural
difference as harmless. Original bytes remain preserved; none was deleted.

Warnings about deprecated/unchecked APIs remain. The two HTNG clients use the
alternate CFR recovery with recorded type fixes rather than a failed-method
stub. The current primary-source marker scan reports no decompiler failure
markers; this does not prove that every method was reconstructed correctly.

## Focused offline contracts

- Original and rebuilt `MainMethodRunner` passed the same dummy-main probes in
  CAS, User Management and SmartControl: null, empty, multiple arguments and
  defensive copying of caller arguments.
- Five selected original/rebuilt public/protected API comparisons matched:
  the launcher in those three modules and the two repaired SmartControl enum
  utility/view-model classes.
- These probes execute only isolated launcher code against a dummy target.
  They do not start CMND, initialize databases, or contact devices.

Run the contracts against your own successful build:

```sh
python3 scripts/native_contracts.py \
  --run native-project/build/run-YOUR-RUN-ID \
  --java-home /usr/lib/jvm/java-17-openjdk-amd64
```

## Catalog and preservation

Validation checked all **42,349 provenance records** (42,143 distinct reference
files), all **2,352 primary Java mappings**, and recorded **17 edited Java files**.
The compile-only annotation is new tooling support, not an omitted vendor file.
The catalog also includes all 175 original installer files and 498 non-Java
occurrence records, including duplicate original locations.
Supporting distribution extraction cataloged all 7,781 files across 12 ZIPs,
including the separate SmartCMS frontend, plus 1,138 non-class Java archive web
resources. These are editable local reference copies, not runtime-tested ports.

All 21 focused Python project/preservation/track-separation tests passed. They
include refusal to overwrite source edits, path/ZIP safety and catalog omission
detection. Both research tag formats are rejected by the compatibility updater.

The platform audit lists 55 candidates needing context review. It is not an
exhaustive proof that all Windows dependencies have been found or removed.

## Not tested or delivered yet

- Complete rebuilt WAR assembly/deployment or application startup.
- Browser workflows, native host adapters, backup/restore, licensing or upgrades.
- TV discovery, enrollment, clone exchange, settings/content delivery or RF.
- Lossless source recovery, full API equivalence or behavioral parity.

Research artifacts are **not deployable**. The original compatibility release
and its update channel were not modified. Detailed results and artifact hashes
are in [`catalog/linux-build-evidence.json`](catalog/linux-build-evidence.json).
