/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.splitters;

import com.android.aapt.ConfigurationOuterClass;
import com.android.bundle.Targeting;
import com.android.tools.build.bundletool.mergers.SameTargetingMerger;
import com.android.tools.build.bundletool.model.AndroidManifest;
import com.android.tools.build.bundletool.model.BundleModule;
import com.android.tools.build.bundletool.model.ManifestEditor;
import com.android.tools.build.bundletool.model.ModuleSplit;
import com.android.tools.build.bundletool.model.OptimizationDimension;
import com.android.tools.build.bundletool.model.ResourceId;
import com.android.tools.build.bundletool.model.ResourceTableEntry;
import com.android.tools.build.bundletool.model.SourceStamp;
import com.android.tools.build.bundletool.model.SuffixManager;
import com.android.tools.build.bundletool.model.exceptions.CommandExecutionException;
import com.android.tools.build.bundletool.model.utils.TargetingProtoUtils;
import com.android.tools.build.bundletool.model.version.Version;
import com.android.tools.build.bundletool.splitters.AbiNativeLibrariesSplitter;
import com.android.tools.build.bundletool.splitters.AbiPlaceholderInjector;
import com.android.tools.build.bundletool.splitters.ApkGenerationConfiguration;
import com.android.tools.build.bundletool.splitters.DexCompressionSplitter;
import com.android.tools.build.bundletool.splitters.LanguageAssetsSplitter;
import com.android.tools.build.bundletool.splitters.LanguageResourcesSplitter;
import com.android.tools.build.bundletool.splitters.ModuleSplitSplitter;
import com.android.tools.build.bundletool.splitters.NativeLibrariesCompressionSplitter;
import com.android.tools.build.bundletool.splitters.SanitizerNativeLibrariesSplitter;
import com.android.tools.build.bundletool.splitters.ScreenDensityResourcesSplitter;
import com.android.tools.build.bundletool.splitters.SplittingPipeline;
import com.android.tools.build.bundletool.splitters.TextureCompressionFormatAssetsSplitter;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.protobuf.Int32Value;
import java.util.Optional;

public class ModuleSplitter {
    private final BundleModule module;
    private final ImmutableSet<String> allModuleNames;
    private final SuffixManager suffixManager = new SuffixManager();
    private final Version bundleVersion;
    private final ApkGenerationConfiguration apkGenerationConfiguration;
    private final Targeting.VariantTargeting variantTargeting;
    private final Optional<String> stampSource;
    private final SourceStamp.StampType stampType;
    private final AbiPlaceholderInjector abiPlaceholderInjector;

    @VisibleForTesting
    public static ModuleSplitter createForTest(BundleModule module, Version bundleVersion) {
        return new ModuleSplitter(module, bundleVersion, ApkGenerationConfiguration.getDefaultInstance(), TargetingProtoUtils.lPlusVariantTargeting(), ImmutableSet.of(), Optional.empty(), null);
    }

    public static ModuleSplitter createNoStamp(BundleModule module, Version bundleVersion, ApkGenerationConfiguration apkGenerationConfiguration, Targeting.VariantTargeting variantTargeting, ImmutableSet<String> allModuleNames) {
        return new ModuleSplitter(module, bundleVersion, apkGenerationConfiguration, variantTargeting, allModuleNames, Optional.empty(), null);
    }

    public static ModuleSplitter create(BundleModule module, Version bundleVersion, ApkGenerationConfiguration apkGenerationConfiguration, Targeting.VariantTargeting variantTargeting, ImmutableSet<String> allModuleNames, Optional<String> stampSource, SourceStamp.StampType stampType) {
        return new ModuleSplitter(module, bundleVersion, apkGenerationConfiguration, variantTargeting, allModuleNames, stampSource, stampType);
    }

    private ModuleSplitter(BundleModule module, Version bundleVersion, ApkGenerationConfiguration apkGenerationConfiguration, Targeting.VariantTargeting variantTargeting, ImmutableSet<String> allModuleNames, Optional<String> stampSource, SourceStamp.StampType stampType) {
        this.module = Preconditions.checkNotNull(module);
        this.bundleVersion = Preconditions.checkNotNull(bundleVersion);
        this.apkGenerationConfiguration = Preconditions.checkNotNull(apkGenerationConfiguration);
        this.variantTargeting = Preconditions.checkNotNull(variantTargeting);
        this.abiPlaceholderInjector = new AbiPlaceholderInjector(apkGenerationConfiguration.getAbisForPlaceholderLibs());
        this.allModuleNames = allModuleNames;
        this.stampSource = stampSource;
        this.stampType = stampType;
    }

    public ImmutableList<ModuleSplit> splitModule() {
        if (this.apkGenerationConfiguration.isForInstantAppVariants()) {
            return this.splitModuleInternal().stream().map(this::makeInstantManifestChanges).map(moduleSplit -> moduleSplit.toBuilder().setSplitType(ModuleSplit.SplitType.INSTANT).build()).collect(ImmutableList.toImmutableList());
        }
        return this.splitModuleInternal().stream().map(this::removeSplitName).map(this::addPlaceHolderNativeLibsToBaseModule).collect(ImmutableList.toImmutableList());
    }

    private ModuleSplit addPlaceHolderNativeLibsToBaseModule(ModuleSplit moduleSplit) {
        if (!this.apkGenerationConfiguration.getAbisForPlaceholderLibs().isEmpty() && moduleSplit.isBaseModuleSplit() && moduleSplit.isMasterSplit()) {
            return this.abiPlaceholderInjector.addPlaceholderNativeEntries(moduleSplit);
        }
        return moduleSplit;
    }

    private ImmutableList<ModuleSplit> splitModuleInternal() {
        ImmutableList<ModuleSplit> moduleSplits = this.runSplitters().stream().map(this::addLPlusApkTargeting).map(this::writeSplitIdInManifest).map(ModuleSplit::addApplicationElementIfMissingInManifest).collect(ImmutableList.toImmutableList());
        if (this.stampSource.isPresent()) {
            return moduleSplits.stream().map(moduleSplit -> moduleSplit.writeSourceStampInManifest(this.stampSource.get(), this.stampType)).collect(ImmutableList.toImmutableList());
        }
        return moduleSplits;
    }

    private ImmutableList<ModuleSplit> runSplitters() {
        if (ModuleSplitter.targetsOnlyPreL(this.module)) {
            throw CommandExecutionException.builder().withMessage("Cannot split module '%s' because it does not target devices on Android L or above.", this.module.getName()).build();
        }
        ImmutableList.Builder splits = ImmutableList.builder();
        SplittingPipeline resourcesPipeline = this.createResourcesSplittingPipeline();
        splits.addAll(resourcesPipeline.split(ModuleSplit.forResources(this.module, this.variantTargeting)));
        SplittingPipeline nativePipeline = this.createNativeLibrariesSplittingPipeline();
        splits.addAll(nativePipeline.split(ModuleSplit.forNativeLibraries(this.module, this.variantTargeting)));
        SplittingPipeline assetsPipeline = this.createAssetsSplittingPipeline();
        splits.addAll(assetsPipeline.split(ModuleSplit.forAssets(this.module, this.variantTargeting)));
        SplittingPipeline dexPipeline = this.createDexSplittingPipeline();
        splits.addAll(dexPipeline.split(ModuleSplit.forDex(this.module, this.variantTargeting)));
        splits.add(ModuleSplit.forRoot(this.module, this.variantTargeting));
        ImmutableCollection mergedSplits = new SameTargetingMerger().merge(ModuleSplitter.applyMasterManifestMutators(splits.build()));
        ImmutableList defaultTargetingSplits = mergedSplits.stream().filter(split -> split.getApkTargeting().equals(Targeting.ApkTargeting.getDefaultInstance())).collect(ImmutableList.toImmutableList());
        Preconditions.checkState(defaultTargetingSplits.size() == 1, "Expected one split with default targeting.");
        return mergedSplits;
    }

    public ModuleSplit writeSplitIdInManifest(ModuleSplit moduleSplit) {
        String resolvedSuffix = this.suffixManager.createSuffix(moduleSplit);
        return moduleSplit.writeSplitIdInManifest(resolvedSuffix);
    }

    public ModuleSplit removeSplitName(ModuleSplit moduleSplit) {
        return moduleSplit.removeSplitName();
    }

    public ModuleSplit makeInstantManifestChanges(ModuleSplit moduleSplit) {
        AndroidManifest manifest = moduleSplit.getAndroidManifest();
        ManifestEditor editor = manifest.toEditor();
        editor.setTargetSandboxVersion(2);
        if (manifest.getEffectiveMinSdkVersion() < 21) {
            editor.setMinSdkVersion(21);
        }
        editor.removeUnknownSplitComponents(this.allModuleNames);
        return moduleSplit.toBuilder().setAndroidManifest(editor.save()).build();
    }

    private SplittingPipeline createResourcesSplittingPipeline() {
        ImmutableList.Builder resourceSplitters = ImmutableList.builder();
        ImmutableSet<ResourceId> masterPinnedResourceIds = this.apkGenerationConfiguration.getMasterPinnedResourceIds();
        ImmutableSet<String> masterPinnedResourceNames = this.apkGenerationConfiguration.getMasterPinnedResourceNames();
        ImmutableSet<ResourceId> baseManifestReachableResources = this.apkGenerationConfiguration.getBaseManifestReachableResources();
        if (this.apkGenerationConfiguration.getOptimizationDimensions().contains((Object)OptimizationDimension.SCREEN_DENSITY)) {
            resourceSplitters.add(new ScreenDensityResourcesSplitter(this.bundleVersion, masterPinnedResourceIds::contains, baseManifestReachableResources::contains));
        }
        if (this.apkGenerationConfiguration.getOptimizationDimensions().contains((Object)OptimizationDimension.LANGUAGE)) {
            Predicate<ResourceTableEntry> pinLangResourceToMaster = Predicates.or(entry -> masterPinnedResourceIds.contains(entry.getResourceId()), entry -> masterPinnedResourceNames.contains(entry.getEntry().getName()), entry -> baseManifestReachableResources.contains(entry.getResourceId()) && !ModuleSplitter.hasDefaultConfig(entry));
            resourceSplitters.add(new LanguageResourcesSplitter(pinLangResourceToMaster));
        }
        return new SplittingPipeline((ImmutableList<ModuleSplitSplitter>)resourceSplitters.build());
    }

    public static ImmutableList<ModuleSplit> applyMasterManifestMutators(ImmutableCollection<ModuleSplit> moduleSplits) {
        Preconditions.checkState(moduleSplits.stream().map(ModuleSplit::getVariantTargeting).distinct().count() == 1L, "Expected same variant targeting across all splits.");
        ImmutableList manifestMutators = moduleSplits.stream().flatMap(moduleSplit -> moduleSplit.getMasterManifestMutators().stream()).collect(ImmutableList.toImmutableList());
        return moduleSplits.stream().map(moduleSplit -> {
            if (moduleSplit.isMasterSplit()) {
                moduleSplit = moduleSplit.toBuilder().setAndroidManifest(moduleSplit.getAndroidManifest().applyMutators(manifestMutators)).build();
            }
            return moduleSplit;
        }).collect(ImmutableList.toImmutableList());
    }

    private SplittingPipeline createNativeLibrariesSplittingPipeline() {
        ImmutableList.Builder nativeSplitters = ImmutableList.builder();
        if (this.apkGenerationConfiguration.getEnableNativeLibraryCompressionSplitter()) {
            nativeSplitters.add(new NativeLibrariesCompressionSplitter(this.apkGenerationConfiguration));
        }
        if (this.apkGenerationConfiguration.getOptimizationDimensions().contains((Object)OptimizationDimension.ABI)) {
            nativeSplitters.add(new AbiNativeLibrariesSplitter());
        }
        nativeSplitters.add(new SanitizerNativeLibrariesSplitter());
        return new SplittingPipeline((ImmutableList<ModuleSplitSplitter>)nativeSplitters.build());
    }

    private SplittingPipeline createAssetsSplittingPipeline() {
        ImmutableList.Builder assetsSplitters = ImmutableList.builder();
        if (this.apkGenerationConfiguration.getOptimizationDimensions().contains((Object)OptimizationDimension.LANGUAGE)) {
            assetsSplitters.add(LanguageAssetsSplitter.create());
        }
        if (this.apkGenerationConfiguration.getOptimizationDimensions().contains((Object)OptimizationDimension.TEXTURE_COMPRESSION_FORMAT)) {
            assetsSplitters.add(TextureCompressionFormatAssetsSplitter.create(this.apkGenerationConfiguration.shouldStripTargetingSuffix(OptimizationDimension.TEXTURE_COMPRESSION_FORMAT)));
        }
        return new SplittingPipeline((ImmutableList<ModuleSplitSplitter>)assetsSplitters.build());
    }

    private SplittingPipeline createDexSplittingPipeline() {
        ImmutableList.Builder dexSplitters = ImmutableList.builder();
        if (this.apkGenerationConfiguration.getEnableDexCompressionSplitter()) {
            dexSplitters.add(new DexCompressionSplitter());
        }
        return new SplittingPipeline((ImmutableList<ModuleSplitSplitter>)dexSplitters.build());
    }

    private static boolean targetsOnlyPreL(BundleModule module) {
        Optional<Integer> maxSdkVersion = module.getAndroidManifest().getMaxSdkVersion();
        return maxSdkVersion.isPresent() && maxSdkVersion.get() < 21;
    }

    private ModuleSplit addLPlusApkTargeting(ModuleSplit split) {
        if (split.getApkTargeting().hasSdkVersionTargeting()) {
            Preconditions.checkState(split.getApkTargeting().getSdkVersionTargeting().getValue(0).getMin().getValue() >= 21, "Module Split should target SDK versions above L.");
            return split;
        }
        return split.toBuilder().setApkTargeting(split.getApkTargeting().toBuilder().setSdkVersionTargeting(Targeting.SdkVersionTargeting.newBuilder().addValue(Targeting.SdkVersion.newBuilder().setMin(Int32Value.newBuilder().setValue(21)))).build()).build();
    }

    private static boolean hasDefaultConfig(ResourceTableEntry entry) {
        return entry.getEntry().getConfigValueList().stream().anyMatch(configValue -> configValue.getConfig().equals(ConfigurationOuterClass.Configuration.getDefaultInstance()));
    }
}

