/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.model;

import com.android.aapt.Resources;
import com.android.bundle.Files;
import com.android.bundle.Targeting;
import com.android.tools.build.bundletool.model.$AutoValue_ModuleSplit;
import com.android.tools.build.bundletool.model.AbiName;
import com.android.tools.build.bundletool.model.AndroidManifest;
import com.android.tools.build.bundletool.model.BundleModule;
import com.android.tools.build.bundletool.model.BundleModuleName;
import com.android.tools.build.bundletool.model.ManifestMutator;
import com.android.tools.build.bundletool.model.ModuleEntry;
import com.android.tools.build.bundletool.model.SourceStamp;
import com.android.tools.build.bundletool.model.ZipPath;
import com.android.tools.build.bundletool.model.utils.ResourcesUtils;
import com.android.tools.build.bundletool.model.utils.TargetingNormalizer;
import com.android.tools.build.bundletool.model.utils.TargetingProtoUtils;
import com.google.auto.value.AutoValue;
import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.MoreCollectors;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import com.google.errorprone.annotations.Immutable;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.stream.Stream;
import javax.annotation.CheckReturnValue;

@Immutable
@AutoValue
@AutoValue.CopyAnnotations
public abstract class ModuleSplit {
    private static final Joiner MULTI_ABI_SUFFIX_JOINER = Joiner.on('.');

    public abstract Targeting.ApkTargeting getApkTargeting();

    public abstract Targeting.VariantTargeting getVariantTargeting();

    public abstract SplitType getSplitType();

    public abstract ImmutableList<ModuleEntry> getEntries();

    public abstract Optional<Resources.ResourceTable> getResourceTable();

    public abstract AndroidManifest getAndroidManifest();

    public abstract ImmutableList<ManifestMutator> getMasterManifestMutators();

    public abstract BundleModuleName getModuleName();

    public abstract boolean isMasterSplit();

    public abstract Optional<Files.NativeLibraries> getNativeConfig();

    public abstract Optional<Files.Assets> getAssetsConfig();

    public abstract Optional<Files.ApexImages> getApexConfig();

    public abstract Builder toBuilder();

    public boolean isBaseModuleSplit() {
        return this.getModuleName().equals(BundleModuleName.BASE_MODULE_NAME);
    }

    public String getSuffix() {
        if (this.isMasterSplit()) {
            return "";
        }
        StringJoiner suffixJoiner = new StringJoiner("_");
        Targeting.AbiTargeting abiTargeting = this.getApkTargeting().getAbiTargeting();
        if (!abiTargeting.getValueList().isEmpty()) {
            abiTargeting.getValueList().forEach(value -> suffixJoiner.add(ModuleSplit.formatAbi(value)));
        } else if (!abiTargeting.getAlternativesList().isEmpty()) {
            suffixJoiner.add("other_abis");
        }
        Targeting.MultiAbiTargeting multiAbiTargeting = this.getApkTargeting().getMultiAbiTargeting();
        for (Targeting.MultiAbi multiAbi : multiAbiTargeting.getValueList()) {
            suffixJoiner.add(MULTI_ABI_SUFFIX_JOINER.join(multiAbi.getAbiList().stream().map(ModuleSplit::formatAbi).collect(ImmutableList.toImmutableList())));
        }
        Targeting.SanitizerTargeting sanitizerTargeting = this.getApkTargeting().getSanitizerTargeting();
        for (Targeting.Sanitizer sanitizer : sanitizerTargeting.getValueList()) {
            if (sanitizer.getAlias().equals(Targeting.Sanitizer.SanitizerAlias.HWADDRESS)) {
                suffixJoiner.add("hwasan");
                continue;
            }
            throw new IllegalArgumentException("Unknown sanitizer");
        }
        Targeting.LanguageTargeting languageTargeting = this.getApkTargeting().getLanguageTargeting();
        if (!languageTargeting.getValueList().isEmpty()) {
            languageTargeting.getValueList().forEach(suffixJoiner::add);
        } else if (!languageTargeting.getAlternativesList().isEmpty()) {
            suffixJoiner.add("other_lang");
        }
        this.getApkTargeting().getScreenDensityTargeting().getValueList().forEach(value -> suffixJoiner.add(((String)((ImmutableMap)((Object)ResourcesUtils.SCREEN_DENSITY_TO_PROTO_VALUE_MAP.inverse())).get(value.getDensityAlias())).replace('-', '_')));
        Targeting.GraphicsApiTargeting graphicsApiTargeting = this.getApkTargeting().getGraphicsApiTargeting();
        if (!graphicsApiTargeting.getValueList().isEmpty()) {
            graphicsApiTargeting.getValueList().forEach(value -> suffixJoiner.add(ModuleSplit.formatGraphicsApi(value)));
        } else if (!graphicsApiTargeting.getAlternativesList().isEmpty()) {
            suffixJoiner.add("other_gfx");
        }
        Targeting.TextureCompressionFormatTargeting textureFormatTargeting = this.getApkTargeting().getTextureCompressionFormatTargeting();
        if (!textureFormatTargeting.getValueList().isEmpty()) {
            textureFormatTargeting.getValueList().forEach(value -> suffixJoiner.add(value.getAlias().name().toLowerCase()));
        } else if (!textureFormatTargeting.getAlternativesList().isEmpty()) {
            suffixJoiner.add("other_tcf");
        }
        return suffixJoiner.toString();
    }

    private static String formatAbi(Targeting.Abi abi) {
        return AbiName.fromProto(abi.getAlias()).getPlatformName().replace('-', '_');
    }

    private static String formatGraphicsApi(Targeting.GraphicsApi graphicsTargeting) {
        StringJoiner result = new StringJoiner("_");
        if (graphicsTargeting.hasMinOpenGlVersion()) {
            result.add("gl" + ModuleSplit.formatGlVersion(graphicsTargeting.getMinOpenGlVersion()));
        } else if (graphicsTargeting.hasMinVulkanVersion()) {
            result.add("vk" + ModuleSplit.formatVulkanVersion(graphicsTargeting.getMinVulkanVersion()));
        }
        return result.toString();
    }

    private static String formatVulkanVersion(Targeting.VulkanVersion vulkanVersion) {
        return vulkanVersion.getMajor() + "_" + vulkanVersion.getMinor();
    }

    private static String formatGlVersion(Targeting.OpenGlVersion glVersion) {
        return glVersion.getMajor() + "_" + glVersion.getMinor();
    }

    public static ImmutableList<ModuleEntry> filterResourceEntries(ImmutableList<ModuleEntry> entries, Resources.ResourceTable resourceTable) {
        ImmutableSet<ZipPath> referencedPaths = ResourcesUtils.getAllFileReferences(resourceTable);
        return entries.stream().filter(entry -> referencedPaths.contains(entry.getPath())).collect(ImmutableList.toImmutableList());
    }

    @CheckReturnValue
    public ModuleSplit removeSplitName() {
        AndroidManifest apkManifest = this.getAndroidManifest().toEditor().removeSplitName().save();
        return this.toBuilder().setAndroidManifest(apkManifest).build();
    }

    public ModuleSplit removeUnknownSplitComponents(ImmutableSet<String> knownSplits) {
        AndroidManifest apkManifest = this.getAndroidManifest().toEditor().removeUnknownSplitComponents(knownSplits).save();
        return this.toBuilder().setAndroidManifest(apkManifest).build();
    }

    public ModuleSplit writeSourceStampInManifest(String stampSource, SourceStamp.StampType stampType) {
        if (!this.isEligibleForSourceStamp()) {
            return this;
        }
        ModuleSplit.checkStampSource(stampSource);
        AndroidManifest apkManifest = this.getAndroidManifest().toEditor().addMetaDataString("com.android.stamp.source", stampSource).addMetaDataString("com.android.stamp.type", stampType.toString()).save();
        return this.toBuilder().setAndroidManifest(apkManifest).build();
    }

    private boolean isEligibleForSourceStamp() {
        switch (this.getSplitType()) {
            case SPLIT: 
            case INSTANT: {
                return this.isBaseModuleSplit() && this.isMasterSplit();
            }
            case STANDALONE: {
                return true;
            }
        }
        return false;
    }

    private static void checkStampSource(String stampSource) {
        try {
            new URL(stampSource).toURI();
        }
        catch (MalformedURLException | URISyntaxException e2) {
            throw new IllegalArgumentException("Invalid stamp source. Stamp sources should be URLs.", e2);
        }
    }

    @CheckReturnValue
    public ModuleSplit writeSplitIdInManifest(String resolvedSplitIdSuffix) {
        AndroidManifest moduleManifest = this.getAndroidManifest();
        String splitId = this.generateSplitId(resolvedSplitIdSuffix);
        AndroidManifest apkManifest = this.isMasterSplit() ? moduleManifest.toEditor().setSplitIdForFeatureSplit(splitId).save() : AndroidManifest.createForConfigSplit(moduleManifest.getPackageName(), moduleManifest.getVersionCode(), splitId, this.getSplitIdForMasterSplit(), moduleManifest.getExtractNativeLibsValue());
        return this.toBuilder().setAndroidManifest(apkManifest).build();
    }

    @CheckReturnValue
    public ModuleSplit addApplicationElementIfMissingInManifest() {
        AndroidManifest modifiedManifest = this.getAndroidManifest().toEditor().addApplicationElementIfMissing().save();
        return this.toBuilder().setAndroidManifest(modifiedManifest).build();
    }

    @CheckReturnValue
    public ModuleSplit setHasCodeInManifest(boolean hasCode) {
        AndroidManifest modifiedManifest = this.getAndroidManifest().toEditor().setHasCode(hasCode).save();
        return this.toBuilder().setAndroidManifest(modifiedManifest).build();
    }

    private String generateSplitId(String resolvedSuffix) {
        String masterSplitId = this.getSplitIdForMasterSplit();
        if (this.isMasterSplit()) {
            return masterSplitId;
        }
        StringBuilder splitIdBuilder = new StringBuilder(masterSplitId);
        if (splitIdBuilder.length() > 0) {
            splitIdBuilder.append(".");
        }
        return splitIdBuilder.append("config.").append(resolvedSuffix).toString();
    }

    private String getSplitIdForMasterSplit() {
        return this.getModuleName().getNameForSplitId();
    }

    public static Builder builder() {
        return new $AutoValue_ModuleSplit.Builder().setEntries(ImmutableList.of()).setSplitType(SplitType.SPLIT);
    }

    public static ModuleSplit forModule(BundleModule bundleModule) {
        return ModuleSplit.forModule(bundleModule, TargetingProtoUtils.lPlusVariantTargeting());
    }

    public static ModuleSplit forModule(BundleModule bundleModule, Targeting.VariantTargeting variantTargeting) {
        return ModuleSplit.fromBundleModule(bundleModule, Predicates.alwaysTrue(), true, variantTargeting);
    }

    public static ModuleSplit forResources(BundleModule bundleModule) {
        return ModuleSplit.forResources(bundleModule, TargetingProtoUtils.lPlusVariantTargeting());
    }

    public static ModuleSplit forResources(BundleModule bundleModule, Targeting.VariantTargeting variantTargeting) {
        return ModuleSplit.fromBundleModule(bundleModule, entry -> entry.getPath().startsWith(BundleModule.RESOURCES_DIRECTORY), true, variantTargeting);
    }

    public static ModuleSplit forAssets(BundleModule bundleModule) {
        return ModuleSplit.forAssets(bundleModule, TargetingProtoUtils.lPlusVariantTargeting());
    }

    public static ModuleSplit forAssets(BundleModule bundleModule, Targeting.VariantTargeting variantTargeting) {
        return ModuleSplit.fromBundleModule(bundleModule, entry -> entry.getPath().startsWith(BundleModule.ASSETS_DIRECTORY), false, variantTargeting);
    }

    public static ModuleSplit forNativeLibraries(BundleModule bundleModule) {
        return ModuleSplit.forNativeLibraries(bundleModule, TargetingProtoUtils.lPlusVariantTargeting());
    }

    public static ModuleSplit forNativeLibraries(BundleModule bundleModule, Targeting.VariantTargeting variantTargeting) {
        return ModuleSplit.fromBundleModule(bundleModule, entry -> entry.getPath().startsWith(BundleModule.LIB_DIRECTORY), false, variantTargeting);
    }

    public static ModuleSplit forDex(BundleModule bundleModule) {
        return ModuleSplit.forDex(bundleModule, TargetingProtoUtils.lPlusVariantTargeting());
    }

    public static ModuleSplit forDex(BundleModule bundleModule, Targeting.VariantTargeting variantTargeting) {
        return ModuleSplit.fromBundleModule(bundleModule, entry -> entry.getPath().startsWith(BundleModule.DEX_DIRECTORY), false, variantTargeting);
    }

    public static ModuleSplit forRoot(BundleModule bundleModule) {
        return ModuleSplit.forRoot(bundleModule, TargetingProtoUtils.lPlusVariantTargeting());
    }

    public static ModuleSplit forRoot(BundleModule bundleModule, Targeting.VariantTargeting variantTargeting) {
        return ModuleSplit.fromBundleModule(bundleModule, entry -> entry.getPath().startsWith(BundleModule.ROOT_DIRECTORY), false, variantTargeting);
    }

    public static ModuleSplit forApex(BundleModule bundleModule) {
        return ModuleSplit.fromBundleModule(bundleModule, entry -> entry.getPath().startsWith(BundleModule.APEX_DIRECTORY), false, TargetingProtoUtils.lPlusVariantTargeting());
    }

    private static ModuleSplit fromBundleModule(BundleModule bundleModule, Predicate<ModuleEntry> entriesPredicate, boolean setResourceTable, Targeting.VariantTargeting variantTargeting) {
        Builder splitBuilder = ModuleSplit.builder().setModuleName(bundleModule.getName()).setEntries(bundleModule.getEntries().stream().filter(entriesPredicate).collect(ImmutableList.toImmutableList())).setAndroidManifest(bundleModule.getAndroidManifest()).setMasterSplit(true).setSplitType(ModuleSplit.getSplitTypeFromModuleType(bundleModule.getModuleType())).setApkTargeting(Targeting.ApkTargeting.getDefaultInstance()).setVariantTargeting(variantTargeting);
        bundleModule.getNativeConfig().ifPresent(splitBuilder::setNativeConfig);
        bundleModule.getAssetsConfig().ifPresent(splitBuilder::setAssetsConfig);
        bundleModule.getApexConfig().ifPresent(splitBuilder::setApexConfig);
        if (setResourceTable) {
            bundleModule.getResourceTable().ifPresent(splitBuilder::setResourceTable);
        }
        return splitBuilder.build();
    }

    private static SplitType getSplitTypeFromModuleType(BundleModule.ModuleType moduleType) {
        switch (moduleType) {
            case FEATURE_MODULE: {
                return SplitType.SPLIT;
            }
            case ASSET_MODULE: {
                return SplitType.ASSET_SLICE;
            }
        }
        throw new IllegalStateException();
    }

    Multimap<ZipPath, ModuleEntry> getEntriesByDirectory() {
        return Multimaps.index(this.getEntries(), entry -> entry.getPath().getParent());
    }

    public Stream<ModuleEntry> getEntriesInDirectory(ZipPath directory) {
        Preconditions.checkArgument(directory.getNameCount() > 0, "ZipPath '%s' is empty", (Object)directory);
        return this.getEntriesByDirectory().get(directory).stream();
    }

    public Stream<ModuleEntry> findEntriesUnderPath(String path) {
        ZipPath zipPath = ZipPath.create(path);
        return this.findEntriesUnderPath(zipPath);
    }

    public Stream<ModuleEntry> findEntriesUnderPath(ZipPath zipPath) {
        return this.getEntriesByDirectory().asMap().entrySet().stream().filter(dirAndEntries -> ((ZipPath)dirAndEntries.getKey()).startsWith(zipPath)).flatMap(dirAndEntries -> ((Collection)dirAndEntries.getValue()).stream());
    }

    public Optional<ModuleEntry> findEntry(ZipPath path) {
        return this.getEntriesInDirectory(path.getParent()).filter(entry -> entry.getPath().equals(path)).collect(MoreCollectors.toOptional());
    }

    public Optional<ModuleEntry> findEntry(String path) {
        return this.findEntry(ZipPath.create(path));
    }

    public boolean isApex() {
        return this.getApexConfig().isPresent();
    }

    @AutoValue.Builder
    public static abstract class Builder {
        public abstract Builder setModuleName(BundleModuleName var1);

        public abstract Builder setMasterSplit(boolean var1);

        public abstract Builder setNativeConfig(Files.NativeLibraries var1);

        public abstract Builder setAssetsConfig(Files.Assets var1);

        public abstract Builder setApexConfig(Files.ApexImages var1);

        protected abstract Targeting.ApkTargeting getApkTargeting();

        public abstract Builder setApkTargeting(Targeting.ApkTargeting var1);

        protected abstract Targeting.VariantTargeting getVariantTargeting();

        public abstract Builder setVariantTargeting(Targeting.VariantTargeting var1);

        public abstract Builder setSplitType(SplitType var1);

        public abstract Builder setEntries(List<ModuleEntry> var1);

        public abstract Builder setResourceTable(Resources.ResourceTable var1);

        public abstract Builder setAndroidManifest(AndroidManifest var1);

        abstract ImmutableList.Builder<ManifestMutator> masterManifestMutatorsBuilder();

        public Builder addMasterManifestMutator(ManifestMutator manifestMutator) {
            this.masterManifestMutatorsBuilder().add((Object)manifestMutator);
            return this;
        }

        protected abstract ModuleSplit autoBuild();

        public ModuleSplit build() {
            ModuleSplit moduleSplit = this.setApkTargeting(TargetingNormalizer.normalizeApkTargeting(this.getApkTargeting())).setVariantTargeting(TargetingNormalizer.normalizeVariantTargeting(this.getVariantTargeting())).autoBuild();
            if (moduleSplit.isMasterSplit() && !moduleSplit.getSplitType().equals((Object)SplitType.SYSTEM)) {
                Preconditions.checkState(moduleSplit.getApkTargeting().toBuilder().clearSdkVersionTargeting().clearTextureCompressionFormatTargeting().build().equals(Targeting.ApkTargeting.getDefaultInstance()), "Master split cannot have any targeting other than SDK version or TextureCompression Format.");
            }
            return moduleSplit;
        }
    }

    public static enum SplitType {
        STANDALONE,
        SYSTEM,
        SPLIT,
        INSTANT,
        ASSET_SLICE;

    }
}

