/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.device;

import com.android.bundle.Commands;
import com.android.bundle.Devices;
import com.android.bundle.Targeting;
import com.android.tools.build.bundletool.device.AbiMatcher;
import com.android.tools.build.bundletool.device.MultiAbiMatcher;
import com.android.tools.build.bundletool.device.ScreenDensityMatcher;
import com.android.tools.build.bundletool.device.SdkVersionMatcher;
import com.android.tools.build.bundletool.device.TargetingDimensionMatcher;
import com.android.tools.build.bundletool.device.TextureCompressionFormatMatcher;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.MoreCollectors;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class VariantMatcher {
    private final ImmutableList<? extends TargetingDimensionMatcher<?>> variantMatchers;
    private final boolean matchInstant;

    public VariantMatcher(Devices.DeviceSpec deviceSpec) {
        this(deviceSpec, false);
    }

    public VariantMatcher(Devices.DeviceSpec deviceSpec, boolean matchInstant) {
        this(new SdkVersionMatcher(deviceSpec), new AbiMatcher(deviceSpec), new MultiAbiMatcher(deviceSpec), new ScreenDensityMatcher(deviceSpec), new TextureCompressionFormatMatcher(deviceSpec), matchInstant);
    }

    VariantMatcher(SdkVersionMatcher sdkVersionMatcher, AbiMatcher abiMatcher, MultiAbiMatcher multiAbiMatcher, ScreenDensityMatcher screenDensityMatcher, TextureCompressionFormatMatcher textureCompressionFormatMatcher, boolean matchInstant) {
        this.variantMatchers = ImmutableList.of(sdkVersionMatcher, abiMatcher, multiAbiMatcher, screenDensityMatcher, textureCompressionFormatMatcher);
        this.matchInstant = matchInstant;
    }

    public ImmutableList<Commands.Variant> getAllMatchingVariants(Commands.BuildApksResult buildApksResult) {
        Supplier<Stream> variantsToMatch = () -> buildApksResult.getVariantList().stream().filter(variant -> VariantMatcher.isVariantInstant(variant) == this.matchInstant);
        variantsToMatch.get().forEach(this::checkCompatibleWithVariant);
        return variantsToMatch.get().filter(variant -> this.matchesVariantTargeting(variant.getTargeting())).collect(ImmutableList.toImmutableList());
    }

    public Optional<Commands.Variant> getMatchingVariant(Commands.BuildApksResult buildApksResult) {
        return this.getAllMatchingVariants(buildApksResult).stream().collect(MoreCollectors.toOptional());
    }

    public void checkCompatibleWithVariant(Commands.Variant variant) {
        this.checkCompatibleWithVariantTargeting(variant.getTargeting());
    }

    public void checkCompatibleWithVariantTargeting(Targeting.VariantTargeting targeting) {
        this.variantMatchers.stream().forEach(matcher -> this.checkCompatibleWithVariantTargetingHelper((TargetingDimensionMatcher)matcher, targeting));
    }

    public boolean matchesVariantTargeting(Targeting.VariantTargeting variantTargeting) {
        return this.variantMatchers.stream().allMatch(matcher -> matcher.getVariantTargetingPredicate().test(variantTargeting));
    }

    private static boolean isVariantInstant(Commands.Variant variant) {
        return variant.getApkSetList().stream().flatMap(apkSet -> apkSet.getApkDescriptionList().stream()).allMatch(Commands.ApkDescription::hasInstantApkMetadata);
    }

    private <T> void checkCompatibleWithVariantTargetingHelper(TargetingDimensionMatcher<T> matcher, Targeting.VariantTargeting variantTargeting) {
        matcher.checkDeviceCompatible(matcher.getTargetingValue(variantTargeting));
    }
}

