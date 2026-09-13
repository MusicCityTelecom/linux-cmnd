# Historical research drivers

These are the preserved scripts used for the CMND 7.5.9 recovery experiments.
They are provided so developers can inspect and adapt the recovery/build process.
They are **not portable production commands or installer entry points**: the
original working-directory layout, timestamped input folders and Windows tool
paths are intentionally recorded. Review and adjust them before running.

Use a disposable offline development workspace. Obtain the original inputs from
the research release assets and verify their hashes. The scripts stage class
inputs, inventory nested archives, invoke offline decompilers, compile selected
sources with annotation processing disabled, and compare static API signatures.
Do not substitute a live customer installation or database for the archive inputs.

Original tool versions were CFR 0.152, Vineflower 1.12.0, ILSpy 9.1.0.7988 and
Ghidra 12.1.3. Java analysis/compilation used the original JDK 17 tools; native
Ghidra analysis used a separate portable JDK 21. Tool downloads/runtimes are not
part of this source directory. Their owners' licenses remain applicable.

Most Python drivers expect to reside in `.lab/`, alongside their named inputs.
The Java exporters belong in `.lab/ghidra-scripts/`. `recovery_inventory.py` is
maintained under `src/cmnd_linux/` and is used by the normalization driver.
The provenance manifest records the exact copied script bytes. These drivers
do not establish source completeness or runtime equivalence merely by exiting
successfully. See `recovered/build-status.json` and `recovered/recovery-status.json`.
