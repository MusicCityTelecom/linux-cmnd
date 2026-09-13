/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.splitters;

import com.android.bundle.Config;
import com.android.bundle.Targeting;
import com.android.tools.build.bundletool.model.OptimizationDimension;
import com.android.tools.build.bundletool.model.ResourceId;
import com.android.tools.build.bundletool.splitters.AutoValue_ApkGenerationConfiguration;
import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.errorprone.annotations.Immutable;

@Immutable
@AutoValue
@AutoValue.CopyAnnotations
public abstract class ApkGenerationConfiguration {
    public abstract ImmutableSet<OptimizationDimension> getOptimizationDimensions();

    public abstract boolean isForInstantAppVariants();

    public abstract boolean getEnableNativeLibraryCompressionSplitter();

    public abstract boolean getEnableDexCompressionSplitter();

    public abstract boolean isInstallableOnExternalStorage();

    public abstract ImmutableSet<Targeting.Abi> getAbisForPlaceholderLibs();

    public abstract ImmutableSet<ResourceId> getMasterPinnedResourceIds();

    public abstract ImmutableSet<String> getMasterPinnedResourceNames();

    public abstract ImmutableSet<ResourceId> getBaseManifestReachableResources();

    public abstract ImmutableMap<OptimizationDimension, Config.SuffixStripping> getSuffixStrippings();

    public boolean shouldStripTargetingSuffix(OptimizationDimension dimension) {
        return this.getSuffixStrippings().containsKey((Object)dimension) && this.getSuffixStrippings().get((Object)dimension).getEnabled();
    }

    public abstract Builder toBuilder();

    public static Builder builder() {
        return new AutoValue_ApkGenerationConfiguration.Builder().setForInstantAppVariants(false).setEnableNativeLibraryCompressionSplitter(false).setEnableDexCompressionSplitter(false).setInstallableOnExternalStorage(false).setAbisForPlaceholderLibs(ImmutableSet.of()).setOptimizationDimensions(ImmutableSet.of()).setMasterPinnedResourceIds(ImmutableSet.of()).setMasterPinnedResourceNames(ImmutableSet.of()).setBaseManifestReachableResources(ImmutableSet.of()).setSuffixStrippings(ImmutableMap.of());
    }

    public static ApkGenerationConfiguration getDefaultInstance() {
        return ApkGenerationConfiguration.builder().build();
    }

    ApkGenerationConfiguration() {
    }

    @AutoValue.Builder
    public static abstract class Builder {
        public abstract Builder setOptimizationDimensions(ImmutableSet<OptimizationDimension> var1);

        public abstract Builder setForInstantAppVariants(boolean var1);

        public abstract Builder setInstallableOnExternalStorage(boolean var1);

        public abstract Builder setEnableNativeLibraryCompressionSplitter(boolean var1);

        public abstract Builder setEnableDexCompressionSplitter(boolean var1);

        public abstract Builder setAbisForPlaceholderLibs(ImmutableSet<Targeting.Abi> var1);

        public abstract Builder setMasterPinnedResourceIds(ImmutableSet<ResourceId> var1);

        public abstract Builder setMasterPinnedResourceNames(ImmutableSet<String> var1);

        public abstract Builder setBaseManifestReachableResources(ImmutableSet<ResourceId> var1);

        public abstract Builder setSuffixStrippings(ImmutableMap<OptimizationDimension, Config.SuffixStripping> var1);

        public abstract ApkGenerationConfiguration build();
    }
}

