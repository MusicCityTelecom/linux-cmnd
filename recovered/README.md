# CMND 7.5.9 recovery collection

This is an **incomplete research snapshot**, not the installable Linux CMND
release and not a replacement application. Keep development on
`codex/java-native-rewrite`, separate from the compatibility port on `main`.

Recovered vendor source remains unchanged, including vendor constants reviewed
and approved for publication. Philips/TPVision and other respective owners retain
their rights. The repository tooling license does **not** relicense this code.
Original license/notice files are retained under `notices/` with their archive
locations in `provenance.json`. See the repository ownership/disclaimer documents.

## Contents

- `java/cfr/` and `java/vineflower/`: independent direct-application decompilations.
- `java/comparison/`: selected problem classes compiled and public/protected API
  checked against original bytecode. This is not behavioral equivalence testing.
- `java/libraries/`: preserved first-pass output, including partial attempts.
- `java/bounded-libraries/`: completed bounded attempts across all library
  namespaces, including nonstandard class containers. Tool exit zero does not
  imply complete source coverage; consult the per-unit reports.
- `dotnet/` and `il/`: recovered Gateway and DTAPINET C# projects and IL.
- `native/`: native helper disassembly and reconstructed pseudocode. These are
  not original or currently buildable C/C++ sources.
- `original/`: source/header/map files distributed in the original archives.
- `provenance.json`: content hashes and original locations for recovered files.
- `build-status.json` and `recovery-status.json`: compilation and coverage evidence.
- `binary-artifacts.json`: original compiled classes named Notice/LICENSE that
  were routed to `original-notice-binaries.zip`, not silently dropped as bad notices.

Research release assets retain the entire original installer and extracted tree,
resource/project files, inventories, and compiler outputs. Binaries are kept out
of Git history. No RF/DekTec component is excluded from the original collection;
most native binaries still await analysis. Customer backups, captures, live
database exports, site credentials and locally generated identities are excluded.

## Development rules

Keep this recovered baseline intact. Make corrections in a separate working
source tree and record their origin, reason, compilation results and behavior
tests. Do not silently replace one decompiler's output with another or delete
failed methods to make a build pass. Original archives remain the reference.

Only the reload helper and SmartCMS direct classes have passed full compilation
in this snapshot. Other compiler outputs are explicitly partial and **must not
be deployed**. No reconstructed application has been runtime- or TV-qualified.
The original framework installer and updater must ignore research snapshots.
