# v0.7.2 qualification record

Status: IN PROGRESS — not a published or deployment-qualified release yet.

Baseline: v0.7.1 (`38fd6b4f9c4269127792ae6fac9cae6e78de734a`) plus the existing
unsupported-Python guard (`d33748d7f17b913f62a2eae36ac7de33a7f6d8ba`). Work is isolated
on `codex/v0.7.2-installer-readiness`. No decompiled or native-product sources are
part of this repair.

Existing test VM inspected: Ubuntu 24.04.5 amd64, 6 GiB RAM, enlarged 38 GiB root
filesystem, v0.7.1 installed. All seven CMND units active; original qualification
records seven HTTP routes and both migration histories passing. This observation
does not qualify the new candidate or authorize TV operations.

Required gates still to record before release publication:

- Full regression suite and package build/lifecycle evidence.
- Clean Ubuntu 24.04 candidate install and deliberate early low-disk failure.
- Final console credential matches the generated file, CAS and management login.
- Actual reboot; unchanged credentials/data, services/containers/listeners,
  egress isolation, HTTP and migration health restored.
- v0.7.1 upgrade preserves configuration, account credentials, certificates and data.
- Published asset identity/hash verification and public GitHub download validation.

Physical-TV operations, RF/DekTec, issued license activation, Debian runtime and
complete Windows-feature parity are not claimed by this maintenance release.
