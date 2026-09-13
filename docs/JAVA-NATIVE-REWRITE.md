# Java-native development: separate from the compatibility port

The editable build workspace is now under [`native-project/`](../native-project/README.md).
Use its catalog, module sources, build driver and evidence; do not modify the
recovered reference baseline. Linux compiler success and focused contract checks
are recorded separately from application runtime/browser/TV qualification.

## Two independent tracks

- `main`: maintain the released Linux installer/runtime integration around the
  original Philips applications. Update the vendor bundle through the existing,
  separately qualified process. Existing `v0.x` tags and `linux-cmnd` update
  manifests remain exclusive to this track.
- `codex/java-native-rewrite`: recover and audit Java, reconstruct builds, then
  consider behavior-preserving Linux-native replacements. It starts at `d33748d`
  as a reference baseline, not as a new installable application.

Use separate working directories. Do not merge recovered Java or experimental
runtime changes into `main` by default. Shared tooling fixes require an explicit,
reviewed cherry-pick and independent compatibility tests.

Before any Java-native release, establish its own product/package identifier,
SemVer sequence, artifact names and release/update selector. Never publish it as
`v0.x` or with the compatibility updater manifest. Prefer a separate repository
for its eventual release channel if that makes the boundary clearer. The
inherited packaging commands currently refuse this track deliberately.

## Initial scope

Prioritize network/IP discovery, TV communication, clone retrieval and delivery,
settings, channel maps, EPG and the familiar web workflows. Discovery must be
separable from enrollment and writes. Preserve device identity and licensing
behavior and the original TV protocol fields and filenames.

DekTec/RF hardware control, drivers and MGate playout are deprioritized following
the operator's discussion with TPVision. Channel-map/EPG RF metadata remains in
scope; do not delete it just because physical RF playout is low priority.

## Recovery rules

**No vendor component is excluded from the recovery collection.** Include the
entire supplied installer tree, proprietary components, every nested dependency,
RF/DekTec/MGate, scripts, resources and native binaries. Priority changes the work
order only. Attempt every Java class, not just selected package namespaces.
Inventory opaque/non-Java containers and failed decompilations explicitly; retain
the original bytes rather than dropping or silently replacing them. Native
EXE/DLL/MSI code is not Java source and needs separate analysis where necessary.

Keep an unchanged local master of every recovered file. The operator has now
requested publication of recovered source on this research branch, including
vendor components, to support future development. Do not redact, strip constants,
or remove functionality from that master or silently alter the published source.
Flag serious credential/signing-key findings for operator review before public
publication of the affected material. Routine verified vendor defaults remain.
Customer data, site credentials, locally generated keys, logs and captures remain
private. Recompiled binaries belong in clearly labeled research release assets,
not Git source history. Preserve third-party licenses and notices; recovered
vendor code is not relicensed under the Music City Telecom tooling license.
Keep the verified original archives unchanged. No online decompiler uploads.

Track these separately: class/source coverage; decompiler warnings; successful
compilation; API/resource compatibility; simulated behavior; isolated runtime;
browser workflows; and approved physical-TV behavior. None alone proves lossless
recovery or equivalence to the unavailable original source.

Class files cannot restore comments or other information removed by compilation.
Compiler-generated structures and stripped names can need manual reconstruction.
Use multiple decompilers for problem methods and compare bytecode/API behavior;
successful startup does not exercise restore, licensing, firmware or error paths.

Windows-looking literals require classification before changes: active host
commands/paths, existing OS-selected branches, configuration defaults, historical
text, RF-only behavior, or TV/protocol data that must remain unchanged. Never use
a global Windows-to-Linux string replacement.

## First private audit (2026-09-12)

The first CFR pass covered direct application classes in five original WARs and
the reload helper, plus vendor namespaces found in nested dependencies. It is
not a claim that every bundled third-party library or native DLL was recovered.
Source was emitted for all expected outer classes in this scope. Three native
TV-management source files contain decompiler warning markers and need review.
Compilation and behavioral equivalence must still be measured independently.

The initial collection retains all 175 files from the extracted 7.5.9 installer
tree, with a per-file SHA-256 manifest. Its ZIP-family inventory found 578 unique
archives and 520 class-bearing artifacts. The focused application CFR pass
covered 3,813 classes and emitted 2,352 Java files; this is **not** the completion
of the broader recovery queue. Nonstandard class containers, native installers,
JRE modules and other opaque formats must remain explicitly pending until
examined. Original bytes remain available even when extraction is incomplete.

Static native inventory identified 466 distinct retained binaries: 413 PE images
(two managed .NET and 411 native), 25 ELF files, two OLE/MSI containers, and 26
other formats. This is an extension-driven first pass, not proof that every
executable embedded in every opaque container has been found.

Both managed PE assemblies (`Gateway.exe` and `DTAPINET.DLL`) produced C# and IL
with ILSpy: 998 C# files total. Tool success is not a build or equivalence claim;
mixed-mode/native portions and decompiler warnings need independent review.
Native EXE/DLL work uses offline Ghidra analysis, disassembly, function manifests
and reconstructed pseudocode. Neither the target executables nor drivers are run.
RF-related files are included, with hardware qualification lower in the queue.

The native pilot exported disassembly and pseudocode for all functions identified
in three helpers: 549 in `HTV_DWPack_1401.exe`, 372 in `TSGen.dll`, and 51 in
`TsGenUtil.exe`. No per-function export failures were reported in that pilot.
These are **identified-function** counts, not proof that analysis discovered
every code path or recovered buildable C/C++. The rest of the native inventory,
including MGate and driver internals, remains pending. `TSGen.dll` exposes
transport-stream generation, multiple-service generation, and merge entry points;
their signatures, callers and output formats require behavior-level comparison.

The preserved originals also include 58 Java source files, 151 headers, three
PDB files, 34 map files, and 5,745 `.class_terracotta` members verified by Java
class magic bytes. The latter are retained and explicitly pending decompilation,
not excluded by a normal `*.class` filter. The master installer itself has also
been retained separately from its extracted tree and hash-verified.

Compilation experiments found malformed control flow, generic-type reconstruction
errors and missing compile-time dependencies. These failures are recorded rather
than patched out merely to obtain a successful build. A readable decompilation
is not yet a replacement for the original application.

Portable decompilers and their required Java runtime are private research tools,
not dependencies added to the released installer. Their provenance and hashes,
all detailed reports, original source/header/debug files and recovered output
are retained locally. Nothing in this audit changes the compatibility release.

The current test VM remains off; this work does not authorize TV contact, host
network changes or deployment of reconstructed code.

## Independent recovery and compilation follow-up

Vineflower 1.12.0 reconstructed seven outer classes that had failed compilation
or carried CFR warnings. All seven compiled against the original dependencies;
their public/protected `javap` surfaces matched the originals. This comparison
does not cover private/package-level members, resources or runtime behavior.

The full direct-application pass has the following static build results:

| Component | Input classes | Java source files | Compilation result |
| --- | ---: | ---: | --- |
| SSL reload helper | 2 | 1 | Passed; 2 classes emitted |
| SmartCMS | 12 | 12 | Passed; 12 classes emitted |
| CAS | 74 | 41 | Missing annotation dependency and reconstruction errors |
| User management | 91 | 57 | Reconstruction errors; partial compiler output only |
| SmartControl | 1,222 | 1,118 | Reconstruction errors; partial compiler output only |
| SmartInstall | 2,412 | 1,123 | Compiler errors/time limit; partial output only |

SmartCMS's compile-only validation API was available in the original CAS archive;
no application code was changed to obtain that passing result. Successful builds
are not deployed and do not establish behavioral equivalence.

The broader input queue contains 551 normalized class sets and 120,087 distinct
class byte sequences. Four conflicting internal names in nonstandard bytecode
were retained in separate jobs, not overwritten. Very large jobs hit bounded
timeouts; their partial output is preserved. Recovery is now split into smaller
units with parent-archive dependency context and resumable per-unit results.

Research snapshots must use a non-installer tag namespace and never include an
installer update manifest. Tests verify that the current bootstrap and updater
ignore a `research-recovery-*` snapshot even on their preview channel. Compiled
research assets must label partial outputs separately from successful compilation.

## Preserved development snapshot

The `recovered/` tree contains the unchanged recovery baseline and a per-file
provenance manifest. `scripts/research_snapshot/` preserves 15 historical recovery,
compilation and native-export drivers for adaptation by future developers.
`python3 scripts/verify_recovered_source.py` checks source hashes and rejects
unexpected files and executable artifacts from the source tree.

The bounded snapshot covers 220 of 729 units: 219 tools exited successfully and
one timed out; all partial output is retained. Those attempts emitted 24,539 Java
files. Exit success does not imply complete coverage; per-unit missing-source
and warning fields remain in `recovered/recovery-status.json`. An earlier
successful large job is retained separately. The remaining 509 units and most
native binaries still need recovery/analysis.

Research assets preserve the complete original installer, all 175 extracted
installer-tree files, managed project resources, original inventories and all
current compiler output. Original vendor signing constants reviewed with the
operator are retained unchanged. Customer/site data is not part of this snapshot.
