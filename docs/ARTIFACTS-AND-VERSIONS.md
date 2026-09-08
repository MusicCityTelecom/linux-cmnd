# Artifacts and versions

Private workspace observation on 2026-09-07:

| Artifact | Bytes | SHA-256 / version | Status |
|---|---:|---|---|
| `cmnd_release_master-7.5.9.exe` | 825,154,352 | `4cc2d3bf79a8d6d8445c308ba5b4d2b6d551492b9f8ffddf3071c53b51de2348` | Present, product 7.5.9; signature unverified |
| `cas.war` | 115,638,544 | `e5203c0e…01066` | ZIP integrity observed |
| `usermanagement.war` | 52,248,122 | `4aa46c9b…d8bb7` | ZIP integrity observed |
| `smartcontrol.war` | 78,151,800 | `80219f61…d362` | ZIP integrity observed |
| `SmartInstall.war` | 89,822,512 | `55bbdbef…659d` | ZIP integrity observed |
| `smartcms.war` | 27,233,485 | `f90e8472…0478` | ZIP integrity observed |

The extracted payload says `version.txt=7.5.9` and `buildnr.txt=7.5.10.3168`. Internal manifests include SmartInstall `7.0.0.5698`, smartcontrol `2.6.0-SNAPSHOT.377`, and usermanagement `1.2.13-RELEASE.62`. This is evidence of component versioning, not yet proof of an incoherent bundle.

A separate reference-only CMND 7.4.8 full backup and fourteen TPM191HN clone/content packages were confirmed outside the repository. See `REFERENCE-ARTIFACTS.md`; none is redistributed.

Expected 7.5.9 baseline: Java 17.0.16, Tomcat 9.0.109, MySQL 5.7.44. Separate backup 7.5.1 reports Java 17.0.10, Tomcat 9.0.85, MySQL 5.7.43. Migration between them is NOT_RUN. Full hashes belong in a private generated inventory, not this public-capable source tree.
