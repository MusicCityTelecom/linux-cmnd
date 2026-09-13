/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.splitters;

import com.android.bundle.Targeting;
import com.android.tools.build.bundletool.model.BundleModule;
import com.android.tools.build.bundletool.model.ModuleSplit;
import com.android.tools.build.bundletool.model.OptimizationDimension;
import com.android.tools.build.bundletool.model.SuffixManager;
import com.android.tools.build.bundletool.splitters.ApkGenerationConfiguration;
import com.android.tools.build.bundletool.splitters.ModuleSplitSplitter;
import com.android.tools.build.bundletool.splitters.SplittingPipeline;
import com.android.tools.build.bundletool.splitters.TextureCompressionFormatAssetsSplitter;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.protobuf.Int32Value;

public class AssetModuleSplitter {
    private final BundleModule module;
    private final ApkGenerationConfiguration apkGenerationConfiguration;
    private final SuffixManager suffixManager = new SuffixManager();

    public AssetModuleSplitter(BundleModule module, ApkGenerationConfiguration apkGenerationConfiguration) {
        this.module = Preconditions.checkNotNull(module);
        this.apkGenerationConfiguration = Preconditions.checkNotNull(apkGenerationConfiguration);
    }

    public ImmutableList<ModuleSplit> splitModule() {
        ImmutableList.Builder splitsBuilder = ImmutableList.builder();
        SplittingPipeline assetsPipeline = this.createAssetsSplittingPipeline();
        splitsBuilder.addAll(assetsPipeline.split(ModuleSplit.forModule(this.module)));
        ImmutableList splits = splitsBuilder.build();
        if (this.module.getDeliveryType().equals((Object)BundleModule.ModuleDeliveryType.ALWAYS_INITIAL_INSTALL)) {
            splits = splits.stream().map(AssetModuleSplitter::addLPlusApkTargeting).collect(ImmutableList.toImmutableList());
        }
        return splits.stream().map(this::setAssetSliceManifest).collect(ImmutableList.toImmutableList());
    }

    private SplittingPipeline createAssetsSplittingPipeline() {
        ImmutableList.Builder assetsSplitters = ImmutableList.builder();
        if (this.apkGenerationConfiguration.getOptimizationDimensions().contains((Object)OptimizationDimension.TEXTURE_COMPRESSION_FORMAT)) {
            assetsSplitters.add(TextureCompressionFormatAssetsSplitter.create(this.apkGenerationConfiguration.shouldStripTargetingSuffix(OptimizationDimension.TEXTURE_COMPRESSION_FORMAT)));
        }
        return new SplittingPipeline((ImmutableList<ModuleSplitSplitter>)assetsSplitters.build());
    }

    private static ModuleSplit addLPlusApkTargeting(ModuleSplit split) {
        if (split.getApkTargeting().hasSdkVersionTargeting()) {
            Preconditions.checkState(split.getApkTargeting().getSdkVersionTargeting().getValue(0).getMin().getValue() >= 21, "Module Split should target SDK versions above L.");
            return split;
        }
        return split.toBuilder().setApkTargeting(split.getApkTargeting().toBuilder().setSdkVersionTargeting(Targeting.SdkVersionTargeting.newBuilder().addValue(Targeting.SdkVersion.newBuilder().setMin(Int32Value.newBuilder().setValue(21)))).build()).build();
    }

    private ModuleSplit setAssetSliceManifest(ModuleSplit assetSlice) {
        String resolvedSuffix = this.suffixManager.createSuffix(assetSlice);
        return assetSlice.writeSplitIdInManifest(resolvedSuffix).setHasCodeInManifest(false);
    }
}

