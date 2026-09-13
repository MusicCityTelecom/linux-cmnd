/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.model.utils;

import com.android.bundle.Targeting;
import com.android.tools.build.bundletool.model.utils.ResourcesUtils;
import com.google.common.collect.Comparators;
import com.google.common.collect.ImmutableList;
import java.util.Comparator;

public final class TargetingNormalizer {
    private static final Comparator<Targeting.Abi> ABI_COMPARATOR = Comparator.comparing(Targeting.Abi::getAlias);
    private static final Comparator<Targeting.GraphicsApi> GRAPHICS_API_COMPARATOR = Comparator.comparing(Targeting.GraphicsApi::getApiOneofCase).thenComparing(graphicsApi -> graphicsApi.getMinOpenGlVersion().getMajor()).thenComparing(graphicsApi -> graphicsApi.getMinOpenGlVersion().getMinor()).thenComparing(graphicsApi -> graphicsApi.getMinVulkanVersion().getMajor()).thenComparing(graphicsApi -> graphicsApi.getMinVulkanVersion().getMinor());
    private static final Comparator<Targeting.MultiAbi> MULTI_ABI_COMPARATOR = Comparator.comparing(Targeting.MultiAbi::getAbiList, Comparators.lexicographical(Comparator.comparing(Targeting.Abi::getAlias)));
    private static final Comparator<Targeting.Sanitizer> SANITIZER_COMPARATOR = Comparator.comparing(Targeting.Sanitizer::getAlias);
    private static final Comparator<Targeting.ScreenDensity> SCREEN_DENSITY_COMPARATOR = Comparator.comparingInt(ResourcesUtils::convertToDpi);
    private static final Comparator<Targeting.SdkVersion> SDK_VERSION_COMPARATOR = Comparator.comparing(sdkVersion -> sdkVersion.getMin().getValue());
    private static final Comparator<Targeting.TextureCompressionFormat> TEXTURE_COMPRESSION_FORMAT_COMPARATOR = Comparator.comparing(Targeting.TextureCompressionFormat::getAlias);

    public static Targeting.ApkTargeting normalizeApkTargeting(Targeting.ApkTargeting targeting) {
        Targeting.ApkTargeting.Builder normalized = targeting.toBuilder();
        if (targeting.hasAbiTargeting()) {
            normalized.setAbiTargeting(TargetingNormalizer.normalizeAbiTargeting(targeting.getAbiTargeting()));
        }
        if (targeting.hasLanguageTargeting()) {
            normalized.setLanguageTargeting(TargetingNormalizer.normalizeLanguageTargeting(targeting.getLanguageTargeting()));
        }
        if (targeting.hasGraphicsApiTargeting()) {
            normalized.setGraphicsApiTargeting(TargetingNormalizer.normalizeGraphicsApiTargeting(targeting.getGraphicsApiTargeting()));
        }
        if (targeting.hasMultiAbiTargeting()) {
            normalized.setMultiAbiTargeting(TargetingNormalizer.normalizeMultiAbiTargeting(targeting.getMultiAbiTargeting()));
        }
        if (targeting.hasSanitizerTargeting()) {
            normalized.setSanitizerTargeting(TargetingNormalizer.normalizeSanitizerTargeting(targeting.getSanitizerTargeting()));
        }
        if (targeting.hasScreenDensityTargeting()) {
            normalized.setScreenDensityTargeting(TargetingNormalizer.normalizeScreenDensityTargeting(targeting.getScreenDensityTargeting()));
        }
        if (targeting.hasSdkVersionTargeting()) {
            normalized.setSdkVersionTargeting(TargetingNormalizer.normalizeSdkVersionTargeting(targeting.getSdkVersionTargeting()));
        }
        if (targeting.hasTextureCompressionFormatTargeting()) {
            normalized.setTextureCompressionFormatTargeting(TargetingNormalizer.normalizeTextureCompressionFormatTargeting(targeting.getTextureCompressionFormatTargeting()));
        }
        return normalized.build();
    }

    public static Targeting.VariantTargeting normalizeVariantTargeting(Targeting.VariantTargeting targeting) {
        Targeting.VariantTargeting.Builder normalized = targeting.toBuilder();
        if (targeting.hasAbiTargeting()) {
            normalized.setAbiTargeting(TargetingNormalizer.normalizeAbiTargeting(targeting.getAbiTargeting()));
        }
        if (targeting.hasMultiAbiTargeting()) {
            normalized.setMultiAbiTargeting(TargetingNormalizer.normalizeMultiAbiTargeting(targeting.getMultiAbiTargeting()));
        }
        if (targeting.hasScreenDensityTargeting()) {
            normalized.setScreenDensityTargeting(TargetingNormalizer.normalizeScreenDensityTargeting(targeting.getScreenDensityTargeting()));
        }
        if (targeting.hasSdkVersionTargeting()) {
            normalized.setSdkVersionTargeting(TargetingNormalizer.normalizeSdkVersionTargeting(targeting.getSdkVersionTargeting()));
        }
        if (targeting.hasTextureCompressionFormatTargeting()) {
            normalized.setTextureCompressionFormatTargeting(TargetingNormalizer.normalizeTextureCompressionFormatTargeting(targeting.getTextureCompressionFormatTargeting()));
        }
        return normalized.build();
    }

    private static Targeting.AbiTargeting normalizeAbiTargeting(Targeting.AbiTargeting targeting) {
        return Targeting.AbiTargeting.newBuilder().addAllValue(ImmutableList.sortedCopyOf(ABI_COMPARATOR, targeting.getValueList())).addAllAlternatives(ImmutableList.sortedCopyOf(ABI_COMPARATOR, targeting.getAlternativesList())).build();
    }

    private static Targeting.GraphicsApiTargeting normalizeGraphicsApiTargeting(Targeting.GraphicsApiTargeting targeting) {
        return Targeting.GraphicsApiTargeting.newBuilder().addAllValue(ImmutableList.sortedCopyOf(GRAPHICS_API_COMPARATOR, targeting.getValueList())).addAllAlternatives(ImmutableList.sortedCopyOf(GRAPHICS_API_COMPARATOR, targeting.getAlternativesList())).build();
    }

    private static Targeting.LanguageTargeting normalizeLanguageTargeting(Targeting.LanguageTargeting targeting) {
        return Targeting.LanguageTargeting.newBuilder().addAllValue(ImmutableList.sortedCopyOf(targeting.getValueList())).addAllAlternatives(ImmutableList.sortedCopyOf(targeting.getAlternativesList())).build();
    }

    private static Targeting.MultiAbiTargeting normalizeMultiAbiTargeting(Targeting.MultiAbiTargeting targeting) {
        return Targeting.MultiAbiTargeting.newBuilder().addAllValue(targeting.getValueList().stream().map(TargetingNormalizer::normalizeMultiAbi).sorted(MULTI_ABI_COMPARATOR).collect(ImmutableList.toImmutableList())).addAllAlternatives(targeting.getAlternativesList().stream().map(TargetingNormalizer::normalizeMultiAbi).sorted(MULTI_ABI_COMPARATOR).collect(ImmutableList.toImmutableList())).build();
    }

    private static Targeting.MultiAbi normalizeMultiAbi(Targeting.MultiAbi targeting) {
        return Targeting.MultiAbi.newBuilder().addAllAbi(ImmutableList.sortedCopyOf(ABI_COMPARATOR, targeting.getAbiList())).build();
    }

    private static Targeting.SanitizerTargeting normalizeSanitizerTargeting(Targeting.SanitizerTargeting targeting) {
        return Targeting.SanitizerTargeting.newBuilder().addAllValue(ImmutableList.sortedCopyOf(SANITIZER_COMPARATOR, targeting.getValueList())).build();
    }

    private static Targeting.ScreenDensityTargeting normalizeScreenDensityTargeting(Targeting.ScreenDensityTargeting targeting) {
        return Targeting.ScreenDensityTargeting.newBuilder().addAllValue(ImmutableList.sortedCopyOf(SCREEN_DENSITY_COMPARATOR, targeting.getValueList())).addAllAlternatives(ImmutableList.sortedCopyOf(SCREEN_DENSITY_COMPARATOR, targeting.getAlternativesList())).build();
    }

    private static Targeting.SdkVersionTargeting normalizeSdkVersionTargeting(Targeting.SdkVersionTargeting targeting) {
        return Targeting.SdkVersionTargeting.newBuilder().addAllValue(ImmutableList.sortedCopyOf(SDK_VERSION_COMPARATOR, targeting.getValueList())).addAllAlternatives(ImmutableList.sortedCopyOf(SDK_VERSION_COMPARATOR, targeting.getAlternativesList())).build();
    }

    private static Targeting.TextureCompressionFormatTargeting normalizeTextureCompressionFormatTargeting(Targeting.TextureCompressionFormatTargeting targeting) {
        return Targeting.TextureCompressionFormatTargeting.newBuilder().addAllValue(ImmutableList.sortedCopyOf(TEXTURE_COMPRESSION_FORMAT_COMPARATOR, targeting.getValueList())).addAllAlternatives(ImmutableList.sortedCopyOf(TEXTURE_COMPRESSION_FORMAT_COMPARATOR, targeting.getAlternativesList())).build();
    }

    private TargetingNormalizer() {
    }
}

