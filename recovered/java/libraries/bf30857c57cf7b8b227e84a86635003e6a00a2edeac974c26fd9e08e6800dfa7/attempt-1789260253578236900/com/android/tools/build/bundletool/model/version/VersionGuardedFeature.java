/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.model.version;

import com.android.tools.build.bundletool.model.version.Version;

public enum VersionGuardedFeature {
    ABI_SANITIZER_DISABLED("0.3.1"),
    NAMESPACE_ON_INCLUDE_ATTRIBUTE_REQUIRED("0.3.4"),
    RESOURCES_WITH_NO_ALTERNATIVES_IN_MASTER_SPLIT("0.4.0"),
    MODULE_TITLE_VALIDATION_ENFORCED("0.4.3"),
    NO_DEFAULT_UNCOMPRESS_EXTENSIONS("0.7.3"),
    RESOURCES_REFERENCED_IN_MANIFEST_TO_MASTER_SPLIT("0.8.1"),
    PREFER_EXPLICIT_DPI_OVER_DEFAULT_CONFIG("0.9.1"),
    NEW_DELIVERY_TYPE_MANIFEST_TAG("0.10.2"),
    NO_V1_SIGNING_WHEN_POSSIBLE("0.11.0"),
    FUSE_ACTIVITIES_FROM_FEATURE_MANIFESTS("0.13.4");

    private final Version enabledSinceVersion;

    private VersionGuardedFeature(String enabledSinceVersion) {
        this.enabledSinceVersion = Version.of(enabledSinceVersion);
    }

    public boolean enabledForVersion(Version bundletoolVersion) {
        return !bundletoolVersion.isOlderThan(this.enabledSinceVersion);
    }
}

