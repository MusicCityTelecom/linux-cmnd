/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.model.targeting;

import com.android.bundle.Files;
import com.android.bundle.Targeting;
import com.android.tools.build.bundletool.model.BundleModule;
import com.android.tools.build.bundletool.model.ModuleEntry;
import com.android.tools.build.bundletool.model.ModuleSplit;
import com.android.tools.build.bundletool.model.ZipPath;
import com.android.tools.build.bundletool.model.targeting.TargetedDirectory;
import com.android.tools.build.bundletool.model.targeting.TargetedDirectorySegment;
import com.android.tools.build.bundletool.model.targeting.TargetingDimension;
import com.android.tools.build.bundletool.model.utils.TargetingProtoUtils;
import com.android.tools.build.bundletool.model.utils.TextureCompressionUtils;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.Range;
import com.google.common.collect.Sets;
import java.util.Optional;

public final class TargetingUtils {
    public static ImmutableList<TargetingDimension> getTargetingDimensions(Targeting.AssetsDirectoryTargeting targeting) {
        ImmutableList.Builder dimensions = new ImmutableList.Builder();
        if (targeting.hasAbi()) {
            dimensions.add((Object)TargetingDimension.ABI);
        }
        if (targeting.hasGraphicsApi()) {
            dimensions.add((Object)TargetingDimension.GRAPHICS_API);
        }
        if (targeting.hasTextureCompressionFormat()) {
            dimensions.add((Object)TargetingDimension.TEXTURE_COMPRESSION_FORMAT);
        }
        if (targeting.hasLanguage()) {
            dimensions.add((Object)TargetingDimension.LANGUAGE);
        }
        return dimensions.build();
    }

    public static ImmutableSet<Targeting.VariantTargeting> generateAllVariantTargetings(ImmutableSet<Targeting.VariantTargeting> variantTargetings) {
        if (variantTargetings.size() <= 1) {
            return variantTargetings;
        }
        ImmutableList<Targeting.SdkVersionTargeting> sdkVersionTargetings = TargetingUtils.disjointSdkTargetings(variantTargetings.stream().map(variantTargeting -> variantTargeting.getSdkVersionTargeting()).collect(ImmutableList.toImmutableList()));
        return sdkVersionTargetings.stream().map(sdkVersionTargeting -> Targeting.VariantTargeting.newBuilder().setSdkVersionTargeting((Targeting.SdkVersionTargeting)sdkVersionTargeting).build()).collect(ImmutableSet.toImmutableSet());
    }

    public static ImmutableSet<Targeting.VariantTargeting> cropVariantsWithAppSdkRange(ImmutableSet<Targeting.VariantTargeting> variantTargetings, Range<Integer> sdkRange) {
        ImmutableList<Range<Integer>> ranges = TargetingUtils.calculateVariantSdkRanges(variantTargetings, sdkRange);
        return ranges.stream().map(range -> TargetingUtils.sdkVariantTargeting((Integer)range.lowerEndpoint())).collect(ImmutableSet.toImmutableSet());
    }

    private static ImmutableList<Range<Integer>> calculateVariantSdkRanges(ImmutableSet<Targeting.VariantTargeting> variantTargetings, Range<Integer> appSdkRange) {
        return TargetingUtils.disjointSdkTargetings(variantTargetings.stream().map(variantTargeting -> variantTargeting.getSdkVersionTargeting()).collect(ImmutableList.toImmutableList())).stream().map(sdkTargeting -> Range.closedOpen(TargetingUtils.getMinSdk(sdkTargeting), TargetingUtils.getMaxSdk(sdkTargeting))).filter(appSdkRange::isConnected).map(appSdkRange::intersection).filter(Predicates.not(Range::isEmpty)).collect(ImmutableList.toImmutableList());
    }

    private static ImmutableList<Targeting.SdkVersionTargeting> disjointSdkTargetings(ImmutableList<Targeting.SdkVersionTargeting> sdkVersionTargetings) {
        sdkVersionTargetings.forEach(sdkVersionTargeting -> Preconditions.checkState(sdkVersionTargeting.getValueList().size() == 1));
        ImmutableList minSdkValues = sdkVersionTargetings.stream().map(sdkVersionTargeting -> sdkVersionTargeting.getValue(0).getMin().getValue()).distinct().sorted().collect(ImmutableList.toImmutableList());
        ImmutableSet sdkVersions = minSdkValues.stream().map(TargetingProtoUtils::sdkVersionFrom).collect(ImmutableSet.toImmutableSet());
        return sdkVersions.stream().map(sdkVersion -> TargetingProtoUtils.sdkVersionTargeting(sdkVersion, Sets.difference(sdkVersions, ImmutableSet.of(sdkVersion)).immutableCopy())).collect(ImmutableList.toImmutableList());
    }

    public static int getMinSdk(Targeting.SdkVersionTargeting sdkVersionTargeting) {
        if (sdkVersionTargeting.getValueList().isEmpty()) {
            return 1;
        }
        return Iterables.getOnlyElement(sdkVersionTargeting.getValueList()).getMin().getValue();
    }

    public static int getMaxSdk(Targeting.SdkVersionTargeting sdkVersionTargeting) {
        int minSdk = TargetingUtils.getMinSdk(sdkVersionTargeting);
        int alternativeMinSdk = sdkVersionTargeting.getAlternativesList().stream().mapToInt(alternativeSdk -> alternativeSdk.getMin().getValue()).filter(sdkValue -> minSdk < sdkValue).min().orElse(Integer.MAX_VALUE);
        return alternativeMinSdk;
    }

    private static Targeting.VariantTargeting sdkVariantTargeting(int minSdk) {
        return Targeting.VariantTargeting.newBuilder().setSdkVersionTargeting(Targeting.SdkVersionTargeting.newBuilder().addValue(TargetingProtoUtils.sdkVersionFrom(minSdk))).build();
    }

    public static ModuleSplit setTargetingByDefaultSuffix(ModuleSplit moduleSplit, TargetingDimension dimension, String value) {
        Preconditions.checkArgument(dimension.equals((Object)TargetingDimension.TEXTURE_COMPRESSION_FORMAT));
        if (value.isEmpty()) {
            return moduleSplit;
        }
        return moduleSplit.toBuilder().setApkTargeting(moduleSplit.getApkTargeting().toBuilder().setTextureCompressionFormatTargeting(TextureCompressionUtils.TEXTURE_TO_TARGETING.get(value)).build()).setVariantTargeting(moduleSplit.getVariantTargeting().toBuilder().setTextureCompressionFormatTargeting(TextureCompressionUtils.TEXTURE_TO_TARGETING.get(value)).build()).build();
    }

    public static ModuleSplit removeAssetsTargeting(ModuleSplit moduleSplit, TargetingDimension dimension) {
        if (!moduleSplit.getAssetsConfig().isPresent()) {
            return moduleSplit;
        }
        Files.Assets assetsConfig = moduleSplit.getAssetsConfig().get();
        Files.Assets.Builder updatedAssetsConfig = assetsConfig.toBuilder().clearDirectory();
        ImmutableList<ModuleEntry> updatedEntries = moduleSplit.getEntries();
        for (Files.TargetedAssetsDirectory targetedAssetsDirectory : assetsConfig.getDirectoryList()) {
            Files.TargetedAssetsDirectory updatedTargetedAssetsDirectory = TargetingUtils.removeAssetsTargetingFromDirectory(targetedAssetsDirectory, dimension);
            if (!updatedTargetedAssetsDirectory.equals(targetedAssetsDirectory)) {
                ZipPath directoryPath = ZipPath.create(targetedAssetsDirectory.getPath());
                updatedEntries = updatedEntries.stream().map(entry -> {
                    if (entry.getPath().startsWith(directoryPath)) {
                        return TargetingUtils.removeTargetingFromEntry(entry, dimension);
                    }
                    return entry;
                }).collect(ImmutableList.toImmutableList());
            }
            updatedAssetsConfig.addDirectory(updatedTargetedAssetsDirectory);
        }
        return moduleSplit.toBuilder().setEntries(updatedEntries).setAssetsConfig(updatedAssetsConfig.build()).build();
    }

    public static ImmutableSet<TargetedDirectory> extractAssetsTargetedDirectories(BundleModule module) {
        return module.findEntriesUnderPath(BundleModule.ASSETS_DIRECTORY).map(ModuleEntry::getPath).filter(path -> path.getNameCount() > 1).map(ZipPath::getParent).map(TargetedDirectory::parse).collect(ImmutableSet.toImmutableSet());
    }

    public static ImmutableSet<Targeting.TextureCompressionFormat.TextureCompressionFormatAlias> extractTextureCompressionFormats(ImmutableSet<TargetedDirectory> targetedDirectories) {
        return targetedDirectories.stream().map(directory -> directory.getTargeting(TargetingDimension.TEXTURE_COMPRESSION_FORMAT)).filter(Optional::isPresent).map(Optional::get).flatMap(targeting -> targeting.getTextureCompressionFormat().getValueList().stream()).map(Targeting.TextureCompressionFormat::getAlias).collect(ImmutableSet.toImmutableSet());
    }

    public static ModuleSplit excludeAssetsTargetingOtherValue(ModuleSplit moduleSplit, TargetingDimension dimension, String value) {
        if (!moduleSplit.getAssetsConfig().isPresent()) {
            return moduleSplit;
        }
        Files.Assets assetsConfig = moduleSplit.getAssetsConfig().get();
        Files.Assets.Builder updatedAssetsConfig = assetsConfig.toBuilder().clearDirectory();
        ImmutableList<ModuleEntry> updatedEntries = moduleSplit.getEntries();
        for (Files.TargetedAssetsDirectory targetedAssetsDirectory : assetsConfig.getDirectoryList()) {
            ZipPath directoryPath = ZipPath.create(targetedAssetsDirectory.getPath());
            if (TargetingUtils.isDirectoryTargetingOtherValue(targetedAssetsDirectory, dimension, value)) {
                updatedEntries = updatedEntries.stream().filter(entry -> !entry.getPath().startsWith(directoryPath)).collect(ImmutableList.toImmutableList());
                continue;
            }
            updatedAssetsConfig.addDirectory(targetedAssetsDirectory);
        }
        return moduleSplit.toBuilder().setEntries(updatedEntries).setAssetsConfig(updatedAssetsConfig.build()).build();
    }

    private static Files.TargetedAssetsDirectory removeAssetsTargetingFromDirectory(Files.TargetedAssetsDirectory directory, TargetingDimension dimension) {
        Preconditions.checkArgument(dimension.equals((Object)TargetingDimension.TEXTURE_COMPRESSION_FORMAT));
        if (!directory.getTargeting().hasTextureCompressionFormat()) {
            return directory;
        }
        TargetedDirectory targetedDirectory = TargetedDirectory.parse(ZipPath.create(directory.getPath()));
        TargetedDirectory newTargetedDirectory = targetedDirectory.removeTargeting(dimension);
        return directory.toBuilder().setPath(newTargetedDirectory.toZipPath().toString()).setTargeting(directory.getTargeting().toBuilder().clearTextureCompressionFormat().build()).build();
    }

    private static ModuleEntry removeTargetingFromEntry(ModuleEntry moduleEntry, TargetingDimension dimension) {
        if (!TargetedDirectorySegment.pathMayContain(moduleEntry.getPath().toString(), dimension)) {
            return moduleEntry;
        }
        TargetedDirectory targetedDirectory = TargetedDirectory.parse(moduleEntry.getPath());
        TargetedDirectory newTargetedDirectory = targetedDirectory.removeTargeting(dimension);
        if (!newTargetedDirectory.equals(targetedDirectory)) {
            return moduleEntry.toBuilder().setPath(newTargetedDirectory.toZipPath()).build();
        }
        return moduleEntry;
    }

    private static boolean isDirectoryTargetingOtherValue(Files.TargetedAssetsDirectory directory, TargetingDimension dimension, String searchedValue) {
        Preconditions.checkArgument(dimension.equals((Object)TargetingDimension.TEXTURE_COMPRESSION_FORMAT));
        Targeting.AssetsDirectoryTargeting targeting = directory.getTargeting();
        if (!targeting.hasTextureCompressionFormat()) {
            return false;
        }
        boolean isDirectoryValueFallback = targeting.getTextureCompressionFormat().getValueList().isEmpty();
        boolean isSearchedValueFallback = searchedValue.isEmpty();
        if (isSearchedValueFallback || isDirectoryValueFallback) {
            return isSearchedValueFallback != isDirectoryValueFallback;
        }
        String targetingValue = TextureCompressionUtils.TARGETING_TO_TEXTURE.getOrDefault(Iterables.getOnlyElement(targeting.getTextureCompressionFormat().getValueList()).getAlias(), null);
        return !searchedValue.equals(targetingValue);
    }
}

