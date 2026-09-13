/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.mergers;

import com.android.bundle.Files;
import com.android.bundle.Targeting;
import com.android.tools.build.bundletool.model.exceptions.CommandExecutionException;
import com.android.tools.build.bundletool.model.utils.TargetingProtoUtils;
import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.annotation.Nullable;

final class MergingUtils {
    public static <T> Optional<T> getSameValueOrNonNull(@Nullable T nullableValue, T otherValue) {
        Preconditions.checkNotNull(otherValue);
        if (nullableValue == null || nullableValue.equals(otherValue)) {
            return Optional.of(otherValue);
        }
        return Optional.empty();
    }

    public static Targeting.ApkTargeting mergeShardTargetings(Targeting.ApkTargeting targeting1, Targeting.ApkTargeting targeting2) {
        MergingUtils.checkHasOnlyAbiDensityLanguageAndTcfTargeting(targeting1);
        MergingUtils.checkHasOnlyAbiDensityLanguageAndTcfTargeting(targeting2);
        Targeting.ApkTargeting.Builder merged = Targeting.ApkTargeting.newBuilder();
        if (targeting1.hasAbiTargeting() || targeting2.hasAbiTargeting()) {
            merged.setAbiTargeting(MergingUtils.mergeAbiTargetingsOf(targeting1, targeting2));
        }
        if (targeting1.hasScreenDensityTargeting() || targeting2.hasScreenDensityTargeting()) {
            merged.setScreenDensityTargeting(MergingUtils.mergeDensityTargetingsOf(targeting1, targeting2));
        }
        if (targeting1.hasLanguageTargeting() || targeting2.hasLanguageTargeting()) {
            merged.setLanguageTargeting(MergingUtils.mergeLanguageTargetingsOf(targeting1, targeting2));
        }
        if (targeting1.hasTextureCompressionFormatTargeting() || targeting2.hasTextureCompressionFormatTargeting()) {
            merged.setTextureCompressionFormatTargeting(MergingUtils.mergeTextureCompressionFormatTargetingsOf(targeting1, targeting2));
        }
        return merged.build();
    }

    public static void mergeTargetedAssetsDirectories(Map<String, Files.TargetedAssetsDirectory> assetsDirectories, List<Files.TargetedAssetsDirectory> newAssetsDirectories) {
        for (Files.TargetedAssetsDirectory directory : newAssetsDirectories) {
            String path = directory.getPath();
            if (assetsDirectories.containsKey(path)) {
                Files.TargetedAssetsDirectory existingDirectory = assetsDirectories.get(path);
                if (existingDirectory.equals(directory)) continue;
                throw new IllegalStateException("Encountered conflicting targeting values while merging assets config.");
            }
            assetsDirectories.put(path, directory);
        }
    }

    private static void checkHasOnlyAbiDensityLanguageAndTcfTargeting(Targeting.ApkTargeting targeting) {
        Targeting.ApkTargeting targetingWithoutAbiDensityLanguageAndTcf = targeting.toBuilder().clearAbiTargeting().clearScreenDensityTargeting().clearLanguageTargeting().clearTextureCompressionFormatTargeting().build();
        if (!targetingWithoutAbiDensityLanguageAndTcf.equals(Targeting.ApkTargeting.getDefaultInstance())) {
            throw CommandExecutionException.builder().withMessage("Expecting only ABI, screen density, language and texture compression format targeting, got '%s'.", targeting).build();
        }
    }

    private static Targeting.AbiTargeting mergeAbiTargetingsOf(Targeting.ApkTargeting targeting1, Targeting.ApkTargeting targeting2) {
        Sets.SetView<Targeting.Abi> universe = Sets.union(TargetingProtoUtils.abiUniverse(targeting1), TargetingProtoUtils.abiUniverse(targeting2));
        Sets.SetView<Targeting.Abi> values2 = Sets.union(TargetingProtoUtils.abiValues(targeting1), TargetingProtoUtils.abiValues(targeting2));
        return Targeting.AbiTargeting.newBuilder().addAllValue(values2).addAllAlternatives(Sets.difference(universe, values2)).build();
    }

    private static Targeting.ScreenDensityTargeting mergeDensityTargetingsOf(Targeting.ApkTargeting targeting1, Targeting.ApkTargeting targeting2) {
        Sets.SetView<Targeting.ScreenDensity> universe = Sets.union(TargetingProtoUtils.densityUniverse(targeting1), TargetingProtoUtils.densityUniverse(targeting2));
        Sets.SetView<Targeting.ScreenDensity> values2 = Sets.union(TargetingProtoUtils.densityValues(targeting1), TargetingProtoUtils.densityValues(targeting2));
        return Targeting.ScreenDensityTargeting.newBuilder().addAllValue(values2).addAllAlternatives(Sets.difference(universe, values2)).build();
    }

    private static Targeting.LanguageTargeting mergeLanguageTargetingsOf(Targeting.ApkTargeting targeting1, Targeting.ApkTargeting targeting2) {
        Sets.SetView<String> universe = Sets.union(TargetingProtoUtils.languageUniverse(targeting1), TargetingProtoUtils.languageUniverse(targeting2));
        Sets.SetView<String> values2 = Sets.union(TargetingProtoUtils.languageValues(targeting1), TargetingProtoUtils.languageValues(targeting2));
        return Targeting.LanguageTargeting.newBuilder().addAllValue(values2).addAllAlternatives(Sets.difference(universe, values2)).build();
    }

    private static Targeting.TextureCompressionFormatTargeting mergeTextureCompressionFormatTargetingsOf(Targeting.ApkTargeting targeting1, Targeting.ApkTargeting targeting2) {
        Sets.SetView<Targeting.TextureCompressionFormat> universe = Sets.union(TargetingProtoUtils.textureCompressionFormatUniverse(targeting1), TargetingProtoUtils.textureCompressionFormatUniverse(targeting2));
        Sets.SetView<Targeting.TextureCompressionFormat> values2 = Sets.union(TargetingProtoUtils.textureCompressionFormatValues(targeting1), TargetingProtoUtils.textureCompressionFormatValues(targeting2));
        return Targeting.TextureCompressionFormatTargeting.newBuilder().addAllValue(values2).addAllAlternatives(Sets.difference(universe, values2)).build();
    }

    private MergingUtils() {
    }
}

