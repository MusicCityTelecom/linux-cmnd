# Catalog guide

| File | Coverage |
| --- | --- |
| `summary.json` | Source-family counts and links to the untouched recovery status |
| `sources.json` | All 42,349 provenance records for 42,143 distinct recovered files; no namespace exclusions |
| `original-inputs.json` | All 175 original installer files plus the non-Java occurrence inventory, including unresolved binaries |
| `supporting-distributions.json` | Every extracted file in all 12 supporting ZIP distributions, including separate SmartCMS frontend, RF and runtime payloads |
| `web-resources.json` | Editable non-class resources from all six original Java component archives |
| `platform-candidates.json` | Windows/native/process review locations; not an exhaustive defect list |
| `edited-sources.json` | Editable Java files differing from their recorded original recovery, with both hashes |
| `linux-build-evidence.json` | Actual Linux compiler, class inventory, selected API checks and offline launcher contracts |

Original/source hashes establish provenance, not behavioral equivalence. Some
original locations share identical source bytes and therefore appear more than
once. The original installer, all binaries and every recovery variant remain
preserved. Decompilation and runtime qualification are still incomplete.

Search without loading the full JSON in a browser:

```sh
python3 scripts/native_project.py find 'SettingCreator'
python3 scripts/native_project.py find 'DTAPINET'
```

Catalog metadata is public. Prepared binary resources, test logs, runtime state,
customer data and generated identities are not promoted into source history.
