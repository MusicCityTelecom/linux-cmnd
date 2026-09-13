/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.splitters;

import com.android.tools.build.bundletool.model.AppBundle;
import com.android.tools.build.bundletool.model.BundleModule;
import com.android.tools.build.bundletool.model.ModuleSplit;
import com.android.tools.build.bundletool.model.exceptions.manifest.ManifestVersionException;
import com.android.tools.build.bundletool.splitters.ApkGenerationConfiguration;
import com.android.tools.build.bundletool.splitters.AssetModuleSplitter;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;

public class AssetSlicesGenerator {
    private final AppBundle appBundle;
    private final ApkGenerationConfiguration apkGenerationConfiguration;

    public AssetSlicesGenerator(AppBundle appBundle, ApkGenerationConfiguration apkGenerationConfiguration) {
        this.appBundle = Preconditions.checkNotNull(appBundle);
        this.apkGenerationConfiguration = Preconditions.checkNotNull(apkGenerationConfiguration);
    }

    public ImmutableList<ModuleSplit> generateAssetSlices() {
        ImmutableList.Builder splits = ImmutableList.builder();
        int versionCode = this.appBundle.getBaseModule().getAndroidManifest().getVersionCode().orElseThrow(ManifestVersionException.VersionCodeMissingException::new);
        for (BundleModule module : this.appBundle.getAssetModules().values()) {
            AssetModuleSplitter moduleSplitter = new AssetModuleSplitter(module, this.apkGenerationConfiguration);
            splits.addAll((Iterable)moduleSplitter.splitModule().stream().map(split -> AssetSlicesGenerator.addVersionCode(split, versionCode)).collect(ImmutableList.toImmutableList()));
        }
        return splits.build();
    }

    public static ModuleSplit addVersionCode(ModuleSplit moduleSplit, int versionCode) {
        return moduleSplit.toBuilder().setAndroidManifest(moduleSplit.getAndroidManifest().toEditor().setVersionCode(versionCode).save()).build();
    }
}

