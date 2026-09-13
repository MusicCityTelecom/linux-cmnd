/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.device;

import com.android.bundle.Devices;
import com.android.bundle.Targeting;
import com.android.tools.build.bundletool.device.IncompatibleDeviceException;
import com.android.tools.build.bundletool.device.TargetingDimensionMatcher;
import com.android.tools.build.bundletool.model.targeting.TargetingComparators;
import com.android.tools.build.bundletool.model.utils.TextureCompressionUtils;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSortedSet;
import com.google.common.collect.Sets;
import com.google.common.collect.Streams;
import java.util.Optional;
import java.util.stream.Stream;

public class TextureCompressionFormatMatcher
extends TargetingDimensionMatcher<Targeting.TextureCompressionFormatTargeting> {
    private static final String GL_ES_VERSION_FEATURE_PREFIX = "reqGlEsVersion=";
    private final ImmutableList<String> deviceGlExtensions;
    private final ImmutableSet<Targeting.TextureCompressionFormat.TextureCompressionFormatAlias> deviceSupportedTextureCompressionFormats;

    public TextureCompressionFormatMatcher(Devices.DeviceSpec deviceSpec) {
        super(deviceSpec);
        this.deviceGlExtensions = ImmutableList.copyOf(deviceSpec.getGlExtensionsList());
        ImmutableSet glExtensionSupportedFormats = this.deviceGlExtensions.stream().map(glExtension -> TextureCompressionUtils.textureCompressionFormat(glExtension)).filter(Optional::isPresent).map(Optional::get).collect(ImmutableSet.toImmutableSet());
        ImmutableList glVersionSupportedFormats = deviceSpec.getDeviceFeaturesList().stream().flatMap(deviceFeature -> {
            if (!deviceFeature.startsWith(GL_ES_VERSION_FEATURE_PREFIX)) {
                return Stream.of(new Targeting.TextureCompressionFormat.TextureCompressionFormatAlias[0]);
            }
            try {
                int glVersion = Integer.decode(deviceFeature.substring(GL_ES_VERSION_FEATURE_PREFIX.length()));
                return TextureCompressionUtils.textureCompressionFormatsForGl(glVersion).stream();
            }
            catch (NumberFormatException e2) {
                System.out.println("WARNING: the OpenGL ES version in the device spec is not a valid number.  It will be considered as missing for texture compression format matching with the device.");
                return Stream.of(new Targeting.TextureCompressionFormat.TextureCompressionFormatAlias[0]);
            }
        }).collect(ImmutableList.toImmutableList());
        this.deviceSupportedTextureCompressionFormats = ((ImmutableSet.Builder)((ImmutableSet.Builder)ImmutableSet.builder().addAll((Iterable)glExtensionSupportedFormats)).addAll((Iterable)glVersionSupportedFormats)).build();
    }

    @Override
    protected Targeting.TextureCompressionFormatTargeting getTargetingValue(Targeting.ApkTargeting apkTargeting) {
        return apkTargeting.getTextureCompressionFormatTargeting();
    }

    @Override
    protected Targeting.TextureCompressionFormatTargeting getTargetingValue(Targeting.VariantTargeting variantTargeting) {
        return variantTargeting.getTextureCompressionFormatTargeting();
    }

    @Override
    public boolean matchesTargeting(Targeting.TextureCompressionFormatTargeting targeting) {
        if (targeting.equals(Targeting.TextureCompressionFormatTargeting.getDefaultInstance())) {
            return true;
        }
        ImmutableSet values2 = targeting.getValueList().stream().map(Targeting.TextureCompressionFormat::getAlias).collect(ImmutableSet.toImmutableSet());
        ImmutableSet alternatives = targeting.getAlternativesList().stream().map(Targeting.TextureCompressionFormat::getAlias).collect(ImmutableSet.toImmutableSet());
        Sets.SetView intersection = Sets.intersection(values2, alternatives);
        Preconditions.checkArgument(intersection.isEmpty(), "Expected targeting values and alternatives to be mutually exclusive, but both contain: %s", intersection);
        ImmutableSortedSet<Targeting.TextureCompressionFormat.TextureCompressionFormatAlias> orderedSupportedTextureCompressionFormats = TargetingComparators.sortTextureCompressionFormat(this.deviceSupportedTextureCompressionFormats);
        for (Targeting.TextureCompressionFormat.TextureCompressionFormatAlias textureCompressionFormatAlias : orderedSupportedTextureCompressionFormats) {
            if (values2.contains(textureCompressionFormatAlias)) {
                return true;
            }
            if (!alternatives.contains(textureCompressionFormatAlias)) continue;
            return false;
        }
        return false;
    }

    @Override
    protected boolean isDeviceDimensionPresent() {
        return !this.deviceGlExtensions.isEmpty();
    }

    @Override
    protected void checkDeviceCompatibleInternal(Targeting.TextureCompressionFormatTargeting targeting) {
        if (targeting.equals(Targeting.TextureCompressionFormatTargeting.getDefaultInstance())) {
            return;
        }
        ImmutableSet valuesAndAlternativesSet = Streams.concat(targeting.getValueList().stream().map(Targeting.TextureCompressionFormat::getAlias), targeting.getAlternativesList().stream().map(Targeting.TextureCompressionFormat::getAlias)).collect(ImmutableSet.toImmutableSet());
        Sets.SetView intersection = Sets.intersection(valuesAndAlternativesSet, this.deviceSupportedTextureCompressionFormats);
        if (intersection.isEmpty()) {
            throw IncompatibleDeviceException.builder().withMessage("The app doesn't support texture compression formats of the device. Device formats: %s, app formats: %s.", this.deviceSupportedTextureCompressionFormats, valuesAndAlternativesSet).build();
        }
    }
}

