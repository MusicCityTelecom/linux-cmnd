/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.splitters;

import com.android.bundle.Config;
import com.android.bundle.Devices;
import com.android.bundle.Targeting;
import com.android.tools.build.bundletool.model.BundleMetadata;
import com.android.tools.build.bundletool.model.BundleModule;
import com.android.tools.build.bundletool.model.BundleModuleName;
import com.android.tools.build.bundletool.model.ModuleSplit;
import com.android.tools.build.bundletool.model.OptimizationDimension;
import com.android.tools.build.bundletool.model.ShardedSystemSplits;
import com.android.tools.build.bundletool.model.SourceStamp;
import com.android.tools.build.bundletool.model.utils.TargetingProtoUtils;
import com.android.tools.build.bundletool.model.version.Version;
import com.android.tools.build.bundletool.optimizations.ApkOptimizations;
import com.android.tools.build.bundletool.splitters.BundleSharder;
import com.android.tools.build.bundletool.splitters.BundleSharderConfiguration;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import java.nio.file.Path;
import java.util.Optional;

public final class ShardedApksGenerator {
    private final Path tempDir;
    private final Version bundleVersion;
    private final boolean strip64BitLibrariesFromShards;
    private final ImmutableMap<OptimizationDimension, Config.SuffixStripping> suffixStrippings;
    private final Optional<String> stampSource;

    public ShardedApksGenerator(Path tempDir, Version bundleVersion, boolean strip64BitLibrariesFromShards) {
        this(tempDir, bundleVersion, strip64BitLibrariesFromShards, ImmutableMap.of());
    }

    public ShardedApksGenerator(Path tempDir, Version bundleVersion, boolean strip64BitLibrariesFromShards, ImmutableMap<OptimizationDimension, Config.SuffixStripping> suffixStrippings) {
        this(tempDir, bundleVersion, strip64BitLibrariesFromShards, suffixStrippings, Optional.empty());
    }

    public ShardedApksGenerator(Path tempDir, Version bundleVersion, boolean strip64BitLibrariesFromShards, ImmutableMap<OptimizationDimension, Config.SuffixStripping> suffixStrippings, Optional<String> stampSource) {
        this.tempDir = tempDir;
        this.bundleVersion = bundleVersion;
        this.strip64BitLibrariesFromShards = strip64BitLibrariesFromShards;
        this.suffixStrippings = suffixStrippings;
        this.stampSource = stampSource;
    }

    public ImmutableList<ModuleSplit> generateSplits(ImmutableList<BundleModule> modules, BundleMetadata bundleMetadata, ApkOptimizations apkOptimizations) {
        BundleSharderConfiguration configuration = BundleSharderConfiguration.builder().setStrip64BitLibrariesFromShards(this.strip64BitLibrariesFromShards).setSuffixStrippings(this.suffixStrippings).build();
        BundleSharder bundleSharder = new BundleSharder(this.tempDir, this.bundleVersion, configuration);
        ImmutableList<ModuleSplit> moduleSplits = ImmutableList.copyOf(ShardedApksGenerator.setVariantTargetingAndSplitType(bundleSharder.shardBundle(modules, apkOptimizations.getStandaloneDimensions(), bundleMetadata), ModuleSplit.SplitType.STANDALONE));
        if (this.stampSource.isPresent()) {
            return moduleSplits.stream().map(moduleSplit -> moduleSplit.writeSourceStampInManifest(this.stampSource.get(), SourceStamp.StampType.STAMP_TYPE_STANDALONE_APK)).collect(ImmutableList.toImmutableList());
        }
        return moduleSplits;
    }

    public ImmutableList<ModuleSplit> generateSystemSplits(ImmutableList<BundleModule> modules, ImmutableSet<BundleModuleName> modulesToFuse, BundleMetadata bundleMetadata, ApkOptimizations apkOptimizations, Optional<Devices.DeviceSpec> deviceSpec) {
        BundleSharderConfiguration configuration = BundleSharderConfiguration.builder().setStrip64BitLibrariesFromShards(this.strip64BitLibrariesFromShards).setSuffixStrippings(this.suffixStrippings).setDeviceSpec(deviceSpec).build();
        BundleSharder bundleSharder = new BundleSharder(this.tempDir, this.bundleVersion, configuration);
        ShardedSystemSplits shardedApks = bundleSharder.shardForSystemApps(modules, modulesToFuse, apkOptimizations.getSplitDimensions(), bundleMetadata);
        ModuleSplit fusedApk = ShardedApksGenerator.setVariantTargetingAndSplitType(shardedApks.getSystemImageSplit(), ModuleSplit.SplitType.SYSTEM).toBuilder().setMasterSplit(true).build();
        ImmutableList additionalSplitApks = shardedApks.getAdditionalSplits().stream().map(split -> split.toBuilder().setVariantTargeting(fusedApk.getVariantTargeting()).setSplitType(ModuleSplit.SplitType.SYSTEM).build()).collect(ImmutableList.toImmutableList());
        return ((ImmutableList.Builder)((ImmutableList.Builder)ImmutableList.builder().add(fusedApk)).addAll((Iterable)additionalSplitApks)).build();
    }

    public ImmutableList<ModuleSplit> generateApexSplits(ImmutableList<BundleModule> modules) {
        BundleSharderConfiguration configuration = BundleSharderConfiguration.builder().setStrip64BitLibrariesFromShards(this.strip64BitLibrariesFromShards).setSuffixStrippings(this.suffixStrippings).build();
        BundleSharder bundleSharder = new BundleSharder(this.tempDir, this.bundleVersion, configuration);
        ImmutableList<ModuleSplit> shardedApexApks = bundleSharder.shardApexBundle(Iterables.getOnlyElement(modules));
        return ShardedApksGenerator.setVariantTargetingAndSplitType(shardedApexApks, ModuleSplit.SplitType.STANDALONE);
    }

    private static ImmutableList<ModuleSplit> setVariantTargetingAndSplitType(ImmutableList<ModuleSplit> standaloneApks, ModuleSplit.SplitType splitType) {
        return standaloneApks.stream().map(moduleSplit -> ShardedApksGenerator.setVariantTargetingAndSplitType(moduleSplit, splitType)).collect(ImmutableList.toImmutableList());
    }

    private static ModuleSplit setVariantTargetingAndSplitType(ModuleSplit moduleSplit, ModuleSplit.SplitType splitType) {
        return moduleSplit.toBuilder().setVariantTargeting(ShardedApksGenerator.standaloneApkVariantTargeting(moduleSplit)).setSplitType(splitType).build();
    }

    private static Targeting.VariantTargeting standaloneApkVariantTargeting(ModuleSplit standaloneApk) {
        Targeting.ApkTargeting apkTargeting = standaloneApk.getApkTargeting();
        Targeting.VariantTargeting.Builder variantTargeting = Targeting.VariantTargeting.newBuilder();
        if (apkTargeting.hasAbiTargeting()) {
            variantTargeting.setAbiTargeting(apkTargeting.getAbiTargeting());
        }
        if (apkTargeting.hasScreenDensityTargeting()) {
            variantTargeting.setScreenDensityTargeting(apkTargeting.getScreenDensityTargeting());
        }
        if (apkTargeting.hasMultiAbiTargeting()) {
            variantTargeting.setMultiAbiTargeting(apkTargeting.getMultiAbiTargeting());
        }
        if (apkTargeting.hasTextureCompressionFormatTargeting()) {
            variantTargeting.setTextureCompressionFormatTargeting(apkTargeting.getTextureCompressionFormatTargeting());
        }
        variantTargeting.setSdkVersionTargeting(ShardedApksGenerator.sdkVersionTargeting(standaloneApk));
        return variantTargeting.build();
    }

    private static Targeting.SdkVersionTargeting sdkVersionTargeting(ModuleSplit moduleSplit) {
        return Targeting.SdkVersionTargeting.newBuilder().addValue(TargetingProtoUtils.sdkVersionFrom(moduleSplit.getAndroidManifest().getEffectiveMinSdkVersion())).build();
    }
}

