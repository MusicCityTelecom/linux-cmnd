/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.splitters;

import com.android.bundle.Config;
import com.android.bundle.Devices;
import com.android.bundle.Targeting;
import com.android.tools.build.bundletool.device.ApkMatcher;
import com.android.tools.build.bundletool.mergers.D8DexMerger;
import com.android.tools.build.bundletool.mergers.ModuleSplitsToShardMerger;
import com.android.tools.build.bundletool.mergers.SameTargetingMerger;
import com.android.tools.build.bundletool.model.BundleMetadata;
import com.android.tools.build.bundletool.model.BundleModule;
import com.android.tools.build.bundletool.model.BundleModuleName;
import com.android.tools.build.bundletool.model.ModuleSplit;
import com.android.tools.build.bundletool.model.OptimizationDimension;
import com.android.tools.build.bundletool.model.ShardedSystemSplits;
import com.android.tools.build.bundletool.model.exceptions.CommandExecutionException;
import com.android.tools.build.bundletool.model.targeting.TargetingDimension;
import com.android.tools.build.bundletool.model.targeting.TargetingUtils;
import com.android.tools.build.bundletool.model.utils.TargetingProtoUtils;
import com.android.tools.build.bundletool.model.version.Version;
import com.android.tools.build.bundletool.preprocessors.AppBundle64BitNativeLibrariesPreprocessor;
import com.android.tools.build.bundletool.splitters.AbiApexImagesSplitter;
import com.android.tools.build.bundletool.splitters.AbiNativeLibrariesSplitter;
import com.android.tools.build.bundletool.splitters.BundleSharderConfiguration;
import com.android.tools.build.bundletool.splitters.LanguageAssetsSplitter;
import com.android.tools.build.bundletool.splitters.LanguageResourcesSplitter;
import com.android.tools.build.bundletool.splitters.ModuleSplitSplitter;
import com.android.tools.build.bundletool.splitters.SanitizerNativeLibrariesSplitter;
import com.android.tools.build.bundletool.splitters.ScreenDensityResourcesSplitter;
import com.android.tools.build.bundletool.splitters.SplittingPipeline;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.Multimaps;
import com.google.common.collect.Sets;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

public class BundleSharder {
    private final Version bundleVersion;
    private final ModuleSplitsToShardMerger merger;
    private final BundleSharderConfiguration bundleSharderConfiguration;

    public BundleSharder(Path globalTempDir, Version bundleVersion, BundleSharderConfiguration bundleSharderConfiguration) {
        this.bundleVersion = bundleVersion;
        this.merger = new ModuleSplitsToShardMerger(new D8DexMerger(), bundleVersion, globalTempDir);
        this.bundleSharderConfiguration = bundleSharderConfiguration;
    }

    public ImmutableList<ModuleSplit> shardBundle(ImmutableList<BundleModule> modules, ImmutableSet<OptimizationDimension> shardingDimensions, BundleMetadata bundleMetadata) {
        Preconditions.checkState(!this.bundleSharderConfiguration.getDeviceSpec().isPresent(), "Device spec should be set only when sharding for system apps.");
        return this.merger.merge(this.generateUnfusedShards(modules, shardingDimensions), bundleMetadata);
    }

    public ShardedSystemSplits shardForSystemApps(ImmutableList<BundleModule> modules, ImmutableSet<BundleModuleName> modulesToFuse, ImmutableSet<OptimizationDimension> shardingDimensions, BundleMetadata bundleMetadata) {
        Preconditions.checkState(this.bundleSharderConfiguration.getDeviceSpec().isPresent(), "Device spec should be set when sharding for system apps.");
        return this.merger.mergeSystemShard((ImmutableCollection<ModuleSplit>)Iterables.getOnlyElement(this.generateUnfusedShards(modules, shardingDimensions)), modulesToFuse, bundleMetadata, this.bundleSharderConfiguration.getDeviceSpec().get());
    }

    private ImmutableList<ImmutableList<ModuleSplit>> generateUnfusedShards(ImmutableList<BundleModule> modules, ImmutableSet<OptimizationDimension> shardingDimensions) {
        Preconditions.checkArgument(!modules.isEmpty(), "At least one module is required.");
        if (this.bundleSharderConfiguration.getStrip64BitLibrariesFromShards()) {
            modules = ImmutableList.copyOf(AppBundle64BitNativeLibrariesPreprocessor.processModules(modules));
        }
        ImmutableList<ModuleSplit> moduleSplits = modules.stream().flatMap(module -> this.generateSplits((BundleModule)module, shardingDimensions).stream()).collect(ImmutableList.toImmutableList());
        return this.groupSplitsToShards(moduleSplits);
    }

    public ImmutableList<ModuleSplit> shardApexBundle(BundleModule apexModule) {
        ImmutableList<ModuleSplit> splits = this.generateSplits(apexModule, ImmutableSet.of());
        ImmutableList<ImmutableList<ModuleSplit>> unfusedShards = this.groupSplitsToShardsForApex(splits);
        return this.merger.mergeApex(unfusedShards);
    }

    private ImmutableList<ModuleSplit> generateSplits(BundleModule module, ImmutableSet<OptimizationDimension> shardingDimensions) {
        ImmutableList.Builder rawSplits = ImmutableList.builder();
        SplittingPipeline nativePipeline = this.createNativeLibrariesSplittingPipeline(shardingDimensions);
        rawSplits.addAll(nativePipeline.split(ModuleSplit.forNativeLibraries(module)));
        SplittingPipeline resourcesPipeline = this.createResourcesSplittingPipeline(shardingDimensions);
        rawSplits.addAll(resourcesPipeline.split(ModuleSplit.forResources(module)));
        SplittingPipeline apexPipeline = this.createApexImagesSplittingPipeline();
        rawSplits.addAll(apexPipeline.split(ModuleSplit.forApex(module)));
        SplittingPipeline assetsPipeline = this.createAssetsSplittingPipeline(shardingDimensions);
        rawSplits.addAll(assetsPipeline.split(ModuleSplit.forAssets(module)));
        rawSplits.add(ModuleSplit.forDex(module));
        rawSplits.add(ModuleSplit.forRoot(module));
        ImmutableList<ModuleSplit> unmergedSplits = rawSplits.build();
        unmergedSplits = this.applySuffixStripping(unmergedSplits);
        ImmutableList<ModuleSplit> mergedSplits = new SameTargetingMerger().merge(unmergedSplits);
        mergedSplits = mergedSplits.stream().map(ModuleSplit::removeSplitName).collect(ImmutableList.toImmutableList());
        long masterSplitCount = mergedSplits.stream().filter(ModuleSplit::isMasterSplit).count();
        Preconditions.checkState(masterSplitCount == 1L, "Expected one master split, got %s.", masterSplitCount);
        return mergedSplits;
    }

    private ImmutableList<ModuleSplit> applySuffixStripping(ImmutableList<ModuleSplit> splits) {
        Config.SuffixStripping tcfSuffixStripping = this.bundleSharderConfiguration.getSuffixStrippings().get((Object)OptimizationDimension.TEXTURE_COMPRESSION_FORMAT);
        if (tcfSuffixStripping == null || !tcfSuffixStripping.getEnabled()) {
            return splits;
        }
        return splits.stream().map(split -> BundleSharder.applySuffixStripping(split, TargetingDimension.TEXTURE_COMPRESSION_FORMAT, tcfSuffixStripping)).collect(ImmutableList.toImmutableList());
    }

    private static ModuleSplit applySuffixStripping(ModuleSplit split, TargetingDimension dimension, Config.SuffixStripping suffixStripping) {
        Preconditions.checkArgument(dimension.equals((Object)TargetingDimension.TEXTURE_COMPRESSION_FORMAT));
        split = TargetingUtils.excludeAssetsTargetingOtherValue(split, dimension, suffixStripping.getDefaultSuffix());
        split = TargetingUtils.removeAssetsTargeting(split, dimension);
        split = TargetingUtils.setTargetingByDefaultSuffix(split, dimension, suffixStripping.getDefaultSuffix());
        return split;
    }

    private SplittingPipeline createNativeLibrariesSplittingPipeline(ImmutableSet<OptimizationDimension> shardingDimensions) {
        ImmutableList.Builder nativeSplitters = ImmutableList.builder();
        if (shardingDimensions.contains((Object)OptimizationDimension.ABI)) {
            nativeSplitters.add(new AbiNativeLibrariesSplitter());
        }
        nativeSplitters.add(new SanitizerNativeLibrariesSplitter());
        return new SplittingPipeline((ImmutableList<ModuleSplitSplitter>)nativeSplitters.build());
    }

    private SplittingPipeline createResourcesSplittingPipeline(ImmutableSet<OptimizationDimension> shardingDimensions) {
        ImmutableList.Builder resourceSplitters = ImmutableList.builder();
        if (shardingDimensions.contains((Object)OptimizationDimension.SCREEN_DENSITY)) {
            resourceSplitters.add(new ScreenDensityResourcesSplitter(this.bundleVersion, Predicates.alwaysFalse(), Predicates.alwaysFalse()));
        }
        if (shardingDimensions.contains((Object)OptimizationDimension.LANGUAGE) && this.bundleSharderConfiguration.splitByLanguage()) {
            resourceSplitters.add(new LanguageResourcesSplitter());
        }
        return new SplittingPipeline((ImmutableList<ModuleSplitSplitter>)resourceSplitters.build());
    }

    private SplittingPipeline createAssetsSplittingPipeline(ImmutableSet<OptimizationDimension> shardingDimensions) {
        ImmutableList.Builder assetsSplitters = ImmutableList.builder();
        if (shardingDimensions.contains((Object)OptimizationDimension.LANGUAGE) && this.bundleSharderConfiguration.splitByLanguage()) {
            assetsSplitters.add(LanguageAssetsSplitter.create());
        }
        return new SplittingPipeline((ImmutableList<ModuleSplitSplitter>)assetsSplitters.build());
    }

    private SplittingPipeline createApexImagesSplittingPipeline() {
        return new SplittingPipeline(ImmutableList.of(new AbiApexImagesSplitter()));
    }

    private ImmutableList<ImmutableList<ModuleSplit>> groupSplitsToShards(ImmutableList<ModuleSplit> splits) {
        ImmutableSet<ModuleSplit> abiSplits = this.subsetWithTargeting(splits, Targeting.ApkTargeting::hasAbiTargeting);
        ImmutableSet<ModuleSplit> densitySplits = this.subsetWithTargeting(splits, Targeting.ApkTargeting::hasScreenDensityTargeting);
        ImmutableSet<ModuleSplit> languageSplits = BundleSharder.getLanguageSplits(splits);
        ImmutableSet<ModuleSplit> masterSplits = this.getMasterSplits(splits);
        Preconditions.checkState(Sets.intersection(Sets.newHashSet(abiSplits), Sets.newHashSet(densitySplits)).isEmpty(), "No split is expected to have both ABI and screen density targeting.");
        Preconditions.checkState(BundleSharder.sameTargetedUniverse(densitySplits, split -> TargetingProtoUtils.densityUniverse(split.getApkTargeting())), "Density splits are expected to cover the same densities.");
        if (!BundleSharder.sameTargetedUniverse(abiSplits, split -> TargetingProtoUtils.abiUniverse(split.getApkTargeting()))) {
            throw CommandExecutionException.builder().withMessage("Modules for standalone APKs must cover the same ABIs when optimizing for ABI.").build();
        }
        Collection abiSplitsSubsets = BundleSharder.nonEmpty(BundleSharder.partitionByTargeting(abiSplits));
        Collection densitySplitsSubsets = BundleSharder.nonEmpty(BundleSharder.partitionByTargeting(densitySplits));
        ImmutableList.Builder shards = ImmutableList.builder();
        for (Collection abiSplitsSubset : abiSplitsSubsets) {
            for (Collection densitySplitsSubset : densitySplitsSubsets) {
                shards.add(((ImmutableList.Builder)((ImmutableList.Builder)((ImmutableList.Builder)((ImmutableList.Builder)ImmutableList.builder().addAll(masterSplits)).addAll(languageSplits)).addAll((Iterable)abiSplitsSubset)).addAll((Iterable)densitySplitsSubset)).build());
            }
        }
        return shards.build();
    }

    private ImmutableList<ImmutableList<ModuleSplit>> groupSplitsToShardsForApex(ImmutableList<ModuleSplit> splits) {
        ImmutableSet<ModuleSplit> multiAbiSplits = this.subsetWithTargeting(splits, Targeting.ApkTargeting::hasMultiAbiTargeting);
        Sets.SetView<ModuleSplit> masterSplits = Sets.difference(ImmutableSet.copyOf(splits), multiAbiSplits);
        ModuleSplit masterSplit = Iterables.getOnlyElement(masterSplits);
        Preconditions.checkState(masterSplit.getApkTargeting().equals(Targeting.ApkTargeting.getDefaultInstance()), "Master splits are expected to have default targeting.");
        return multiAbiSplits.stream().map(abiSplit -> ImmutableList.of(masterSplit, abiSplit)).collect(ImmutableList.toImmutableList());
    }

    private ImmutableSet<ModuleSplit> subsetWithTargeting(ImmutableList<ModuleSplit> splits, Predicate<Targeting.ApkTargeting> predicate) {
        return splits.stream().filter(split -> predicate.test(split.getApkTargeting())).filter(split -> this.bundleSharderConfiguration.getDeviceSpec().map(spec -> BundleSharder.splitMatchesDeviceSpec(split, spec)).orElse(true)).collect(ImmutableSet.toImmutableSet());
    }

    private ImmutableSet<ModuleSplit> getMasterSplits(ImmutableList<ModuleSplit> splits) {
        ImmutableSet<ModuleSplit> masterSplits = splits.stream().filter(ModuleSplit::isMasterSplit).collect(ImmutableSet.toImmutableSet());
        Preconditions.checkState(masterSplits.size() >= 1, "Expecting at least one master split, got %s.", masterSplits.size());
        Preconditions.checkState(masterSplits.stream().allMatch(split -> split.getApkTargeting().toBuilder().clearTextureCompressionFormatTargeting().build().equals(Targeting.ApkTargeting.getDefaultInstance())), "Master splits are expected to have default or Texture Compression Format only targeting.");
        return masterSplits;
    }

    private static ImmutableSet<ModuleSplit> getLanguageSplits(ImmutableList<ModuleSplit> splits) {
        return splits.stream().filter(split -> split.getApkTargeting().hasLanguageTargeting()).collect(ImmutableSet.toImmutableSet());
    }

    private static Collection<Collection<ModuleSplit>> partitionByTargeting(Collection<ModuleSplit> splits) {
        return ((ImmutableMap)Multimaps.index(splits, ModuleSplit::getApkTargeting).asMap()).values();
    }

    private static <T> Collection<Collection<T>> nonEmpty(Collection<Collection<T>> x3) {
        return x3.isEmpty() ? ImmutableList.of(ImmutableList.of()) : x3;
    }

    private static boolean sameTargetedUniverse(Set<ModuleSplit> splits, Function<ModuleSplit, Collection<?>> getUniverseFn) {
        long distinctNonEmptyUniverseCount = splits.stream().map(getUniverseFn::apply).filter(Predicates.not(Collection::isEmpty)).distinct().count();
        return distinctNonEmptyUniverseCount <= 1L;
    }

    static boolean splitMatchesDeviceSpec(ModuleSplit moduleSplit, Devices.DeviceSpec deviceSpec) {
        return new ApkMatcher(deviceSpec).matchesModuleSplitByTargeting(moduleSplit);
    }
}

