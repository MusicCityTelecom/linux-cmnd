/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.model.targeting;

import com.android.bundle.Targeting;
import com.android.tools.build.bundletool.model.utils.TargetingProtoUtils;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Comparators;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSortedSet;
import com.google.common.collect.MoreCollectors;
import com.google.common.collect.Ordering;
import java.util.Comparator;
import java.util.Optional;

public final class TargetingComparators {
    public static final Ordering<Targeting.Abi.AbiAlias> ARCHITECTURE_ORDERING = Ordering.explicit(Targeting.Abi.AbiAlias.ARMEABI, Targeting.Abi.AbiAlias.ARMEABI_V7A, Targeting.Abi.AbiAlias.ARM64_V8A, Targeting.Abi.AbiAlias.X86, Targeting.Abi.AbiAlias.X86_64, Targeting.Abi.AbiAlias.MIPS, Targeting.Abi.AbiAlias.MIPS64);
    public static final Ordering<Targeting.TextureCompressionFormat.TextureCompressionFormatAlias> TEXTURE_COMPRESSION_FORMAT_ORDERING = Ordering.explicit(Targeting.TextureCompressionFormat.TextureCompressionFormatAlias.UNSPECIFIED_TEXTURE_COMPRESSION_FORMAT, Targeting.TextureCompressionFormat.TextureCompressionFormatAlias.PALETTED, Targeting.TextureCompressionFormat.TextureCompressionFormatAlias.ETC1_RGB8, Targeting.TextureCompressionFormat.TextureCompressionFormatAlias.ETC2, Targeting.TextureCompressionFormat.TextureCompressionFormatAlias.THREE_DC, Targeting.TextureCompressionFormat.TextureCompressionFormatAlias.ATC, Targeting.TextureCompressionFormat.TextureCompressionFormatAlias.LATC, Targeting.TextureCompressionFormat.TextureCompressionFormatAlias.DXT1, Targeting.TextureCompressionFormat.TextureCompressionFormatAlias.S3TC, Targeting.TextureCompressionFormat.TextureCompressionFormatAlias.PVRTC, Targeting.TextureCompressionFormat.TextureCompressionFormatAlias.ASTC);
    private static final Comparator<Targeting.VariantTargeting> ABI_COMPARATOR = Comparator.comparing(TargetingComparators::getAbi, Comparators.emptiesFirst(ARCHITECTURE_ORDERING));
    private static final Comparator<Targeting.VariantTargeting> SDK_COMPARATOR = Comparator.comparing(TargetingComparators::getMinSdk, Comparators.emptiesFirst(Ordering.natural()));
    private static final Comparator<Targeting.VariantTargeting> SCREEN_DENSITY_COMPARATOR = Comparator.comparing(TargetingComparators::getScreenDensity, Comparators.emptiesFirst(Ordering.natural()));
    private static final Comparator<Targeting.VariantTargeting> TEXTURE_COMPRESSION_FORMAT_COMPARATOR = Comparator.comparing(TargetingComparators::getTextureCompressionFormat, Comparators.emptiesFirst(TEXTURE_COMPRESSION_FORMAT_ORDERING));
    public static final Comparator<ImmutableSet<Targeting.Abi.AbiAlias>> MULTI_ABI_ALIAS_COMPARATOR = Comparator.comparing(TargetingComparators::sortMultiAbi, Comparators.lexicographical(ARCHITECTURE_ORDERING));
    @VisibleForTesting
    static final Comparator<Targeting.VariantTargeting> MULTI_ABI_COMPARATOR = Comparator.comparing(TargetingComparators::getMultiAbi, MULTI_ABI_ALIAS_COMPARATOR);
    public static final Comparator<Targeting.VariantTargeting> VARIANT_TARGETING_COMPARATOR = SDK_COMPARATOR.thenComparing(ABI_COMPARATOR).thenComparing(MULTI_ABI_COMPARATOR).thenComparing(SCREEN_DENSITY_COMPARATOR).thenComparing(TEXTURE_COMPRESSION_FORMAT_COMPARATOR);

    public static ImmutableSortedSet<Targeting.TextureCompressionFormat.TextureCompressionFormatAlias> sortTextureCompressionFormat(ImmutableSet<Targeting.TextureCompressionFormat.TextureCompressionFormatAlias> textureCompressionFormats) {
        return ImmutableSortedSet.copyOf(TEXTURE_COMPRESSION_FORMAT_ORDERING.reverse(), textureCompressionFormats);
    }

    private static Optional<Integer> getMinSdk(Targeting.VariantTargeting variantTargeting) {
        if (variantTargeting.getSdkVersionTargeting().getValueList().isEmpty()) {
            return Optional.empty();
        }
        return variantTargeting.getSdkVersionTargeting().getValueList().stream().map(sdkVersion -> sdkVersion.getMin().getValue()).max(Comparator.naturalOrder());
    }

    private static Optional<Targeting.Abi.AbiAlias> getAbi(Targeting.VariantTargeting variantTargeting) {
        if (variantTargeting.getAbiTargeting().getValueList().isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(((Targeting.Abi)variantTargeting.getAbiTargeting().getValueList().stream().collect(MoreCollectors.onlyElement())).getAlias());
    }

    private static Optional<Targeting.TextureCompressionFormat.TextureCompressionFormatAlias> getTextureCompressionFormat(Targeting.VariantTargeting variantTargeting) {
        if (variantTargeting.getTextureCompressionFormatTargeting().getValueList().isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(((Targeting.TextureCompressionFormat)variantTargeting.getTextureCompressionFormatTargeting().getValueList().stream().collect(MoreCollectors.onlyElement())).getAlias());
    }

    private static ImmutableSet<Targeting.Abi.AbiAlias> getMultiAbi(Targeting.VariantTargeting variantTargeting) {
        if (variantTargeting.getMultiAbiTargeting().getValueList().isEmpty()) {
            return ImmutableSet.of();
        }
        return ((Targeting.MultiAbi)variantTargeting.getMultiAbiTargeting().getValueList().stream().collect(MoreCollectors.onlyElement())).getAbiList().stream().map(Targeting.Abi::getAlias).collect(ImmutableSet.toImmutableSet());
    }

    private static ImmutableSortedSet<Targeting.Abi.AbiAlias> sortMultiAbi(ImmutableSet<Targeting.Abi.AbiAlias> abis) {
        return ImmutableSortedSet.copyOf(ARCHITECTURE_ORDERING.reverse(), abis);
    }

    private static Optional<Integer> getScreenDensity(Targeting.VariantTargeting variantTargeting) {
        return TargetingProtoUtils.getScreenDensityDpi(variantTargeting.getScreenDensityTargeting());
    }

    private TargetingComparators() {
    }
}

