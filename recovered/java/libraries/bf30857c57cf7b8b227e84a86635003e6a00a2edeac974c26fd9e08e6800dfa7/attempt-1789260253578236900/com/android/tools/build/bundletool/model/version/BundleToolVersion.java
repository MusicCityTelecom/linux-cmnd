/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.model.version;

import com.android.bundle.Config;
import com.android.tools.build.bundletool.model.version.Version;
import com.google.common.base.Strings;

public final class BundleToolVersion {
    private static final String CURRENT_VERSION = "0.13.4";

    public static Version getCurrentVersion() {
        return Version.of(CURRENT_VERSION);
    }

    public static Version getVersionFromBundleConfig(Config.BundleConfig bundleConfig) {
        String rawVersion = bundleConfig.getBundletool().getVersion();
        if (Strings.isNullOrEmpty(rawVersion)) {
            return Version.of("0.0.0");
        }
        return Version.of(rawVersion);
    }

    private BundleToolVersion() {
    }
}

