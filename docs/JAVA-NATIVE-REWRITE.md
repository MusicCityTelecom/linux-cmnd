# Java-native development: separate from the compatibility port

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

Recovered vendor source, binaries, detailed disassembly and decompiler logs stay
in ignored private lab storage. Never commit or publish them. Keep the verified
original archives unchanged. No third-party online decompiler uploads.

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
