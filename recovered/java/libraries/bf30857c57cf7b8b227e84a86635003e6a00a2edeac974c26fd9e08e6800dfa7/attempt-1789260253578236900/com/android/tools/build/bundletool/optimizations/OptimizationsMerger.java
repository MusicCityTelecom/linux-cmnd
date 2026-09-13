/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.optimizations;

import com.android.bundle.Config;
import com.android.tools.build.bundletool.model.OptimizationDimension;
import com.android.tools.build.bundletool.model.utils.EnumMapper;
import com.android.tools.build.bundletool.model.version.BundleToolVersion;
import com.android.tools.build.bundletool.model.version.Version;
import com.android.tools.build.bundletool.optimizations.ApkOptimizations;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.List;

public final class OptimizationsMerger {
    private static final ImmutableMap<Config.SplitDimension.Value, OptimizationDimension> SPLIT_DIMENSION_ENUM_MAP = EnumMapper.mapByName(Config.SplitDimension.Value.class, OptimizationDimension.class, ImmutableSet.of(Config.SplitDimension.Value.UNRECOGNIZED, Config.SplitDimension.Value.UNSPECIFIED_VALUE));

    public ApkOptimizations mergeWithDefaults(Config.BundleConfig bundleConfig) {
        return this.mergeWithDefaults(bundleConfig, ImmutableSet.of());
    }

    @Deprecated
    public ApkOptimizations mergeWithDefaults(Config.BundleConfig bundleConfig, ImmutableSet<OptimizationDimension> optimizationsOverride) {
        String buildVersionString = bundleConfig.getBundletool().getVersion();
        Version bundleToolBuildVersion = buildVersionString.isEmpty() ? BundleToolVersion.getCurrentVersion() : Version.of(buildVersionString);
        ApkOptimizations defaultOptimizations = ApkOptimizations.getDefaultOptimizationsForVersion(bundleToolBuildVersion);
        Config.Optimizations requestedOptimizations = bundleConfig.getOptimizations();
        ImmutableSet<OptimizationDimension> splitDimensions = OptimizationsMerger.getEffectiveSplitDimensions(defaultOptimizations, requestedOptimizations, optimizationsOverride);
        ImmutableSet<OptimizationDimension> standaloneDimensions = OptimizationsMerger.getEffectiveStandaloneDimensions(defaultOptimizations, requestedOptimizations, optimizationsOverride);
        boolean uncompressNativeLibraries = requestedOptimizations.hasUncompressNativeLibraries() ? requestedOptimizations.getUncompressNativeLibraries().getEnabled() : defaultOptimizations.getUncompressNativeLibraries();
        boolean uncompressDexFiles = requestedOptimizations.getUncompressDexFiles().getEnabled();
        return ApkOptimizations.builder().setSplitDimensions(splitDimensions).setUncompressNativeLibraries(uncompressNativeLibraries).setUncompressDexFiles(uncompressDexFiles).setStandaloneDimensions(standaloneDimensions).build();
    }

    public static ImmutableMap<OptimizationDimension, Config.SuffixStripping> getSuffixStrippings(List<Config.SplitDimension> requestedSplitDimensions) {
        EnumMap<OptimizationDimension, Config.SuffixStripping> mergedDimensions = new EnumMap<OptimizationDimension, Config.SuffixStripping>(OptimizationDimension.class);
        for (Config.SplitDimension requestedSplitDimension : requestedSplitDimensions) {
            OptimizationDimension internalDimension = SPLIT_DIMENSION_ENUM_MAP.get(requestedSplitDimension.getValue());
            if (requestedSplitDimension.getNegate()) continue;
            mergedDimensions.put(internalDimension, requestedSplitDimension.getSuffixStripping());
        }
        return ImmutableMap.copyOf(mergedDimensions);
    }

    private static ImmutableSet<OptimizationDimension> mergeSplitDimensions(ImmutableSet<OptimizationDimension> defaultSplitDimensions, List<Config.SplitDimension> requestedSplitDimensions) {
        HashSet<OptimizationDimension> mergedDimensions = new HashSet<OptimizationDimension>(defaultSplitDimensions);
        for (Config.SplitDimension requestedSplitDimension : requestedSplitDimensions) {
            OptimizationDimension internalDimension = SPLIT_DIMENSION_ENUM_MAP.get(requestedSplitDimension.getValue());
            if (requestedSplitDimension.getNegate()) {
                mergedDimensions.remove((Object)internalDimension);
                continue;
            }
            mergedDimensions.add(internalDimension);
        }
        return ImmutableSet.copyOf(mergedDimensions);
    }

    private static ImmutableSet<OptimizationDimension> getEffectiveSplitDimensions(ApkOptimizations defaultOptimizations, Config.Optimizations requestedOptimizations, ImmutableSet<OptimizationDimension> optimizationsOverride) {
        if (!optimizationsOverride.isEmpty()) {
            return optimizationsOverride;
        }
        return OptimizationsMerger.mergeSplitDimensions(defaultOptimizations.getSplitDimensions(), requestedOptimizations.getSplitsConfig().getSplitDimensionList());
    }

    private static ImmutableSet<OptimizationDimension> getEffectiveStandaloneDimensions(ApkOptimizations defaultOptimizations, Config.Optimizations requestedOptimizations, ImmutableSet<OptimizationDimension> optimizationsOverride) {
        if (!optimizationsOverride.isEmpty()) {
            return optimizationsOverride;
        }
        List<Config.SplitDimension> userDefinedStandaloneConfig = requestedOptimizations.hasStandaloneConfig() ? requestedOptimizations.getStandaloneConfig().getSplitDimensionList() : requestedOptimizations.getSplitsConfig().getSplitDimensionList();
        return OptimizationsMerger.mergeSplitDimensions(defaultOptimizations.getStandaloneDimensions(), userDefinedStandaloneConfig);
    }
}

