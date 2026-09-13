/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.mergers;

import com.android.tools.build.bundletool.mergers.FusingAndroidManifestMerger;
import com.android.tools.build.bundletool.model.AndroidManifest;
import com.android.tools.build.bundletool.model.BundleModuleName;
import com.android.tools.build.bundletool.model.exceptions.CommandExecutionException;
import com.google.common.collect.Iterables;
import com.google.common.collect.SetMultimap;
import java.util.Collection;

public interface AndroidManifestMerger {
    public AndroidManifest merge(SetMultimap<BundleModuleName, AndroidManifest> var1);

    public static AndroidManifestMerger manifestOverride(AndroidManifest override) {
        return manifests -> override;
    }

    public static AndroidManifestMerger fusingMerger() {
        return new FusingAndroidManifestMerger();
    }

    public static AndroidManifestMerger useBaseModuleManifestMerger() {
        return manifests -> {
            Collection baseManifests = manifests.get(BundleModuleName.BASE_MODULE_NAME);
            if (baseManifests.size() != 1) {
                throw CommandExecutionException.builder().withMessage("Expected exactly one base module manifest, but found %d.", baseManifests.size()).build();
            }
            return (AndroidManifest)Iterables.getOnlyElement(baseManifests);
        };
    }
}

