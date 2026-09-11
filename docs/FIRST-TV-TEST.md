# First hardware test: receive a clone from one TV

The current operator instruction is export first, before any TV configuration,
power, firmware, or content changes. Do not scan a VPN subnet or use historical
customer addresses. Obtain one current IP and verify its stable serial/MAC identity.

Do not use native **Add/Detect with auto-import** before the first receive-only
test. In isolated simulator qualification, that workflow automatically sent
enabler, settings-clone and PMS commands. It is not a read-only discovery action.
Use the separate bounded discovery/clone-export CLI for the first physical TV.

Version 0.6.2 includes the physical-TV correlation-cookie and channel archive
recognition corrections. Successful clone readback does not qualify native
Add/Detect or grant permission for its automatic configuration writes. For
read-only discovery, use a private config restricted to the approved target:

```sh
cmndctl --config /private/first-tv.toml scan TV_IP --max-targets 1 --concurrency 1 --rate 1
```

This returns discovery information only; it does not add a TV to Philips CMND.
It still requires an authorized route and scoped firewall access. Do not disable
the native service egress guard or the lab subnet hold to make scanning work.
Broader subnet scans and native import require separately reviewed scope.

The original Philips Java implementation uses an IPCloneService Request to inspect
CloneToServerParameters, requires CloneToServerStatus=Ready and session fields,
then sends a Change containing only CloneToServerParameters/CloneToServerDetails.
The TV uploads multipart files to an HTTP CloneToServer endpoint. This requires
bidirectional VPN routing; client-to-TV reachability alone does not prove the TV
can reach the receiver. The legacy request starts export work but does not apply
settings. Do not describe it as literally zero activity on the TV.

The independent receive-only command stores private uploads and never imports
them into CMND, publishes them, or sends clone-to-TV/power/room commands. Use a
separate private config with only `clone-export` in the target's operations.
Its bind and callback HTTP origin must use the same explicit TV-reachable local
IPv4 address (not0.0.0.0). Do not change host firewall/VPN routing without scoped
approval. The disposable VM's existing loopback NAT forwards are not reachable
from remote TVs.

On an already running CMND host, native HTTP port 8080 is occupied. Choose a
separate unused receiver port (for example 18080) in the private export config's
`callback_base_url`, and verify that exact return route. Do not stop native CMND
or reuse its listener accidentally. No listener starts without `--execute`.

After the one-target identity probe and private allowlist preparation:

Create a private configuration outside the tracked source tree. Use `lab` mode,
one current TV `/32` in `permitted_ranges`, and one allowlist entry granting only
`operations = ["clone-export"]`. Never reuse the general simulator example, which
includes push permissions. Obtain the TV model and independent MAC/serial from the
operator, then use `discover TV_IP` for that address only and compare the returned
identity. The export command checks identity again immediately before requesting
the upload; it uses the returned Philips TVUniqueID even when the allowlist uses
a verified serial or MAC.

```sh
cmndctl --config /private/first-tv.toml clone-info TV_IP --identity VERIFIED_ID
cmndctl --config /private/first-tv.toml export-clone TV_IP --identity VERIFIED_ID \
  --item TVSettings --item TVChannelList --item RoomSpecificSettings \
  --output /private/new-tv-export
# Review the scope and receiver reachability, then repeat with --execute.
```

On this Windows development host, the same CLI can run from the source checkout
with `PYTHONPATH=src` and `python3.13 -m cmnd_linux.cli` in place of `cmndctl`.
The output's parent directory must already exist; the new session directory must
not. The tool removes inherited Windows permissions from that directory and grants
access only to the executing account. Allow roughly 6.1 GiB free space for the
default receive cap: it preserves both the original HTTP bodies and received ZIPs.
Do not run the HTTP listener until the specific target and callback address have
been reviewed. If the VPN uses NAT, an unexpected upload peer is rejected rather
than silently broadening the allowed sender.

That example requests only three setting-related items, not a full clone. Choose
the exact advertised items after reviewing clone-info; never claim a complete
clone if some items were not requested or received. Firmware is not a supported
export item. Vendor platform mapping is1.0 for TPN141,5.0 for TPM215,3.0 otherwise;
use --service-version only according to the verified model/platform.

Uploads require the exact peer IP and a random per-session URL. The receiver
accepts bounded Content-Length multipart bodies, stores them privately, ignores
supplied filenames for filesystem placement, and validates ZIP names, expansion,
CRC and identified items without extracting content. Chunked uploads are currently
rejected explicitly; any needed extension requires observed hardware evidence.
Encrypted archives can be retained but cannot count as CRC/content-validated
without the authorized password. A timeout or incomplete item set is failure,
not successful qualification. Preserve private evidence and report partial results.

Protocol reconstruction/unit and synthetic HTTP receiver tests are not hardware
evidence. The native CMND servlet normally processes imported files; this separate
receiver deliberately does not do that during the first readback test.
