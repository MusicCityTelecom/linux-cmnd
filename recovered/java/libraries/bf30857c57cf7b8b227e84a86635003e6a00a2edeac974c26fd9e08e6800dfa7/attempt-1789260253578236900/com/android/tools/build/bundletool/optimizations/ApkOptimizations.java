/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.optimizations;

import com.android.tools.build.bundletool.model.OptimizationDimension;
import com.android.tools.build.bundletool.model.version.Version;
import com.android.tools.build.bundletool.optimizations.AutoValue_ApkOptimizations;
import com.google.auto.value.AutoValue;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSortedMap;
import com.google.errorprone.annotations.Immutable;

@Immutable
@AutoValue
@AutoValue.CopyAnnotations
public abstract class ApkOptimizations {
    private static final ImmutableSortedMap<Version, ApkOptimizations> DEFAULT_OPTIMIZATIONS_BY_BUNDLETOOL_VERSION = ((ImmutableSortedMap.Builder)((ImmutableSortedMap.Builder)((ImmutableSortedMap.Builder)ImmutableSortedMap.naturalOrder().put(Version.of("0.0.0-dev"), ApkOptimizations.builder().setSplitDimensions(ImmutableSet.of(OptimizationDimension.ABI, OptimizationDimension.SCREEN_DENSITY, OptimizationDimension.LANGUAGE)).setStandaloneDimensions(ImmutableSet.of(OptimizationDimension.ABI, OptimizationDimension.SCREEN_DENSITY)).build())).put(Version.of("0.6.0"), ApkOptimizations.builder().setSplitDimensions(ImmutableSet.of(OptimizationDimension.ABI, OptimizationDimension.SCREEN_DENSITY, OptimizationDimension.LANGUAGE)).setUncompressNativeLibraries(true).setStandaloneDimensions(ImmutableSet.of(OptimizationDimension.ABI, OptimizationDimension.SCREEN_DENSITY)).build())).put(Version.of("0.10.2"), ApkOptimizations.builder().setSplitDimensions(ImmutableSet.of(OptimizationDimension.ABI, OptimizationDimension.SCREEN_DENSITY, OptimizationDimension.TEXTURE_COMPRESSION_FORMAT, OptimizationDimension.LANGUAGE)).setUncompressNativeLibraries(true).setStandaloneDimensions(ImmutableSet.of(OptimizationDimension.ABI, OptimizationDimension.SCREEN_DENSITY)).build())).build();

    public abstract ImmutableSet<OptimizationDimension> getSplitDimensions();

    public abstract boolean getUncompressNativeLibraries();

    public abstract boolean getUncompressDexFiles();

    public abstract ImmutableSet<OptimizationDimension> getStandaloneDimensions();

    static Builder builder() {
        return new AutoValue_ApkOptimizations.Builder().setUncompressNativeLibraries(false).setUncompressDexFiles(false);
    }

    public static ApkOptimizations getDefaultOptimizationsForVersion(Version bundleToolVersion) {
        return Preconditions.checkNotNull(DEFAULT_OPTIMIZATIONS_BY_BUNDLETOOL_VERSION.floorEntry(bundleToolVersion), "No default optimizations found for BundleTool version %s.", (Object)bundleToolVersion).getValue();
    }

    public static ApkOptimizations getOptimizationsForUniversalApk() {
        return ApkOptimizations.builder().setSplitDimensions(ImmutableSet.of()).setStandaloneDimensions(ImmutableSet.of()).build();
    }

    public static ApkOptimizations getOptimizationsForAssetSlices() {
        return ApkOptimizations.builder().setSplitDimensions(ImmutableSet.of(OptimizationDimension.TEXTURE_COMPRESSION_FORMAT, OptimizationDimension.LANGUAGE)).setStandaloneDimensions(ImmutableSet.of()).build();
    }

    @AutoValue.Builder
    static abstract class Builder {
        Builder() {
        }

        abstract Builder setSplitDimensions(ImmutableSet<OptimizationDimension> var1);

        abstract Builder setUncompressNativeLibraries(boolean var1);

        abstract Builder setUncompressDexFiles(boolean var1);

        abstract Builder setStandaloneDimensions(ImmutableSet<OptimizationDimension> var1);

        abstract ApkOptimizations build();
    }
}

