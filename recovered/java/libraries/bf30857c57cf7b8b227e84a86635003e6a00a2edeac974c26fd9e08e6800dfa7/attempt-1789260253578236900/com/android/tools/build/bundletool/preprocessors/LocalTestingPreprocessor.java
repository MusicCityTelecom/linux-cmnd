/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.preprocessors;

import com.android.tools.build.bundletool.model.AndroidManifest;
import com.android.tools.build.bundletool.model.AppBundle;
import com.android.tools.build.bundletool.model.BundleModule;
import com.android.tools.build.bundletool.preprocessors.AppBundlePreprocessor;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;

public class LocalTestingPreprocessor
implements AppBundlePreprocessor {
    public static final String METADATA_NAME = "local_testing_dir";
    @VisibleForTesting
    static final String METADATA_VALUE = "local_testing";

    @Override
    public AppBundle preprocess(AppBundle bundle) {
        return bundle.toBuilder().setRawModules(bundle.getModules().values().stream().map(module -> module.isBaseModule() ? LocalTestingPreprocessor.addLocalTestingMetadata(module) : module).collect(ImmutableList.toImmutableList())).build();
    }

    private static BundleModule addLocalTestingMetadata(BundleModule module) {
        return module.toBuilder().setAndroidManifest(LocalTestingPreprocessor.addLocalTestingMetadata(module.getAndroidManifest())).build();
    }

    private static AndroidManifest addLocalTestingMetadata(AndroidManifest manifest) {
        if (manifest.getMetadataValue(METADATA_NAME).isPresent()) {
            return manifest;
        }
        return manifest.toEditor().addMetaDataString(METADATA_NAME, METADATA_VALUE).save();
    }
}

