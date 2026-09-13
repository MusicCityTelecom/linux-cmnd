/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.model.utils;

import com.android.bundle.Targeting;
import com.android.tools.build.bundletool.model.utils.ResourcesUtils;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.MoreCollectors;
import com.google.protobuf.Int32Value;
import java.util.Optional;

public final class TargetingProtoUtils {
    public static Targeting.AssetsDirectoryTargeting toAlternativeTargeting(Targeting.AssetsDirectoryTargeting targeting) {
        Targeting.AssetsDirectoryTargeting.Builder alternativeTargeting = Targeting.AssetsDirectoryTargeting.newBuilder();
        if (targeting.hasTextureCompressionFormat()) {
            alternativeTargeting.getTextureCompressionFormatBuilder().addAllAlternatives(targeting.getTextureCompressionFormat().getValueList());
        }
        if (targeting.hasGraphicsApi()) {
            alternativeTargeting.getGraphicsApiBuilder().addAllAlternatives(targeting.getGraphicsApi().getValueList());
        }
        if (targeting.hasAbi()) {
            alternativeTargeting.getAbiBuilder().addAllAlternatives(targeting.getAbi().getValueList());
        }
        if (targeting.hasLanguage()) {
            alternativeTargeting.getLanguageBuilder().addAllAlternatives(targeting.getLanguage().getValueList());
        }
        return alternativeTargeting.build();
    }

    public static ImmutableSet<Targeting.Abi> abiValues(Targeting.ApkTargeting targeting) {
        return ImmutableSet.copyOf(targeting.getAbiTargeting().getValueList());
    }

    public static ImmutableSet<Targeting.Abi> abiAlternatives(Targeting.ApkTargeting targeting) {
        return ImmutableSet.copyOf(targeting.getAbiTargeting().getAlternativesList());
    }

    public static ImmutableSet<Targeting.Abi> abiUniverse(Targeting.ApkTargeting targeting) {
        return ((ImmutableSet.Builder)((ImmutableSet.Builder)ImmutableSet.builder().addAll(TargetingProtoUtils.abiValues(targeting))).addAll(TargetingProtoUtils.abiAlternatives(targeting))).build();
    }

    public static ImmutableSet<Targeting.ScreenDensity> densityValues(Targeting.ApkTargeting targeting) {
        return ImmutableSet.copyOf(targeting.getScreenDensityTargeting().getValueList());
    }

    public static ImmutableSet<Targeting.ScreenDensity> densityAlternatives(Targeting.ApkTargeting targeting) {
        return ImmutableSet.copyOf(targeting.getScreenDensityTargeting().getAlternativesList());
    }

    public static ImmutableSet<Targeting.ScreenDensity> densityUniverse(Targeting.ApkTargeting targeting) {
        return ((ImmutableSet.Builder)((ImmutableSet.Builder)ImmutableSet.builder().addAll(TargetingProtoUtils.densityValues(targeting))).addAll(TargetingProtoUtils.densityAlternatives(targeting))).build();
    }

    public static ImmutableSet<String> languageValues(Targeting.ApkTargeting targeting) {
        return ImmutableSet.copyOf(targeting.getLanguageTargeting().getValueList());
    }

    public static ImmutableSet<String> languageAlternatives(Targeting.ApkTargeting targeting) {
        return ImmutableSet.copyOf(targeting.getLanguageTargeting().getAlternativesList());
    }

    public static ImmutableSet<String> languageUniverse(Targeting.ApkTargeting targeting) {
        return ((ImmutableSet.Builder)((ImmutableSet.Builder)ImmutableSet.builder().addAll(TargetingProtoUtils.languageValues(targeting))).addAll(TargetingProtoUtils.languageAlternatives(targeting))).build();
    }

    public static ImmutableSet<Targeting.TextureCompressionFormat> textureCompressionFormatValues(Targeting.ApkTargeting targeting) {
        return ImmutableSet.copyOf(targeting.getTextureCompressionFormatTargeting().getValueList());
    }

    public static ImmutableSet<Targeting.TextureCompressionFormat> textureCompressionFormatAlternatives(Targeting.ApkTargeting targeting) {
        return ImmutableSet.copyOf(targeting.getTextureCompressionFormatTargeting().getAlternativesList());
    }

    public static ImmutableSet<Targeting.TextureCompressionFormat> textureCompressionFormatUniverse(Targeting.ApkTargeting targeting) {
        return ((ImmutableSet.Builder)((ImmutableSet.Builder)ImmutableSet.builder().addAll(TargetingProtoUtils.textureCompressionFormatValues(targeting))).addAll(TargetingProtoUtils.textureCompressionFormatAlternatives(targeting))).build();
    }

    public static Targeting.SdkVersion sdkVersionFrom(int from) {
        return Targeting.SdkVersion.newBuilder().setMin(Int32Value.newBuilder().setValue(from)).build();
    }

    public static Targeting.SdkVersionTargeting sdkVersionTargeting(Targeting.SdkVersion sdkVersion, ImmutableSet<Targeting.SdkVersion> alternatives) {
        return Targeting.SdkVersionTargeting.newBuilder().addValue(sdkVersion).addAllAlternatives(alternatives).build();
    }

    public static Targeting.SdkVersionTargeting sdkVersionTargeting(Targeting.SdkVersion sdkVersion) {
        return Targeting.SdkVersionTargeting.newBuilder().addValue(sdkVersion).build();
    }

    public static Targeting.VariantTargeting variantTargeting(Targeting.SdkVersionTargeting sdkVersionTargeting) {
        return Targeting.VariantTargeting.newBuilder().setSdkVersionTargeting(sdkVersionTargeting).build();
    }

    public static Targeting.VariantTargeting lPlusVariantTargeting() {
        return TargetingProtoUtils.variantTargeting(TargetingProtoUtils.sdkVersionTargeting(TargetingProtoUtils.sdkVersionFrom(21)));
    }

    public static Optional<Integer> getScreenDensityDpi(Targeting.ScreenDensityTargeting screenDensityTargeting) {
        if (screenDensityTargeting.getValueList().isEmpty()) {
            return Optional.empty();
        }
        Targeting.ScreenDensity densityTargeting = (Targeting.ScreenDensity)screenDensityTargeting.getValueList().stream().collect(MoreCollectors.onlyElement());
        return Optional.of(ResourcesUtils.convertToDpi(densityTargeting));
    }
}

