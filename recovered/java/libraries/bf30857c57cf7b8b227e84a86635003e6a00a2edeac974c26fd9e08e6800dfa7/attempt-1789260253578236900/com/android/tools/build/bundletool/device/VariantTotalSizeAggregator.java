/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.device;

import com.android.bundle.Commands;
import com.android.bundle.Devices;
import com.android.bundle.Targeting;
import com.android.tools.build.bundletool.device.ApkMatcher;
import com.android.tools.build.bundletool.device.DeviceSpecUtils;
import com.android.tools.build.bundletool.model.ConfigurationSizes;
import com.android.tools.build.bundletool.model.GetSizeRequest;
import com.android.tools.build.bundletool.model.SizeConfiguration;
import com.android.tools.build.bundletool.model.ZipPath;
import com.android.tools.build.bundletool.model.utils.ResultUtils;
import com.android.tools.build.bundletool.model.version.Version;
import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import java.util.HashMap;

public class VariantTotalSizeAggregator {
    private static final Joiner COMMA_JOINER = Joiner.on(',');
    private final ImmutableMap<String, Long> sizeByApkPaths;
    private final Version bundleVersion;
    private final Commands.Variant variant;
    private final GetSizeRequest getSizeRequest;

    public VariantTotalSizeAggregator(ImmutableMap<String, Long> sizeByApkPaths, Version bundleVersion, Commands.Variant variant, GetSizeRequest getSizeRequest) {
        this.sizeByApkPaths = sizeByApkPaths;
        this.bundleVersion = bundleVersion;
        this.variant = variant;
        this.getSizeRequest = getSizeRequest;
    }

    public ConfigurationSizes getSize() {
        if (ResultUtils.isStandaloneApkVariant(this.variant)) {
            return this.getSizeStandaloneVariant();
        }
        return this.getSizeNonStandaloneVariant();
    }

    private ConfigurationSizes getSizeNonStandaloneVariant() {
        ImmutableList<Commands.ApkDescription> apkDescriptions = this.variant.getApkSetList().stream().flatMap(apkSet -> apkSet.getApkDescriptionList().stream()).collect(ImmutableList.toImmutableList());
        ImmutableSet<Targeting.AbiTargeting> abiTargetingOptions = this.getAllAbiTargetings(apkDescriptions);
        ImmutableSet<Targeting.LanguageTargeting> languageTargetingOptions = this.getAllLanguageTargetings(apkDescriptions);
        ImmutableSet<Targeting.ScreenDensityTargeting> screenDensityTargetingOptions = this.getAllScreenDensityTargetings(apkDescriptions);
        return this.getSizesPerConfiguration(abiTargetingOptions, languageTargetingOptions, screenDensityTargetingOptions);
    }

    private ConfigurationSizes getSizeStandaloneVariant() {
        Preconditions.checkState(!this.getSizeRequest.getInstant(), "Standalone Variants cant be selected when instant flag is set");
        if (this.getSizeRequest.getModules().isPresent()) {
            return ConfigurationSizes.create(ImmutableMap.of(), ImmutableMap.of());
        }
        Targeting.VariantTargeting variantTargeting = this.variant.getTargeting();
        SizeConfiguration sizeConfiguration = this.mergeWithDeviceSpec(this.getSizeConfiguration(variantTargeting.getSdkVersionTargeting(), variantTargeting.getAbiTargeting(), variantTargeting.getScreenDensityTargeting(), Targeting.LanguageTargeting.getDefaultInstance()), this.getSizeRequest.getDeviceSpec());
        long compressedSize = this.sizeByApkPaths.get(Iterables.getOnlyElement(Iterables.getOnlyElement(this.variant.getApkSetList()).getApkDescriptionList()).getPath());
        ImmutableMap<SizeConfiguration, Long> sizeConfigurationMap = ImmutableMap.of(sizeConfiguration, compressedSize);
        return ConfigurationSizes.create(sizeConfigurationMap, sizeConfigurationMap);
    }

    private ImmutableSet<Targeting.AbiTargeting> getAllAbiTargetings(ImmutableList<Commands.ApkDescription> apkDescriptions) {
        ImmutableSet.Builder abiTargetingOptions = ImmutableSet.builder();
        if (DeviceSpecUtils.isAbiMissing(this.getSizeRequest.getDeviceSpec())) {
            abiTargetingOptions.addAll((Iterable)apkDescriptions.stream().map(Commands.ApkDescription::getTargeting).filter(Targeting.ApkTargeting::hasAbiTargeting).map(Targeting.ApkTargeting::getAbiTargeting).collect(ImmutableSet.toImmutableSet()));
        }
        return abiTargetingOptions.build().isEmpty() ? ImmutableSet.of(Targeting.AbiTargeting.getDefaultInstance()) : abiTargetingOptions.build();
    }

    private ImmutableSet<Targeting.ScreenDensityTargeting> getAllScreenDensityTargetings(ImmutableList<Commands.ApkDescription> apkDescriptions) {
        ImmutableSet.Builder screenDensityTargetingOptions = ImmutableSet.builder();
        if (DeviceSpecUtils.isScreenDensityMissing(this.getSizeRequest.getDeviceSpec())) {
            screenDensityTargetingOptions.addAll((Iterable)apkDescriptions.stream().map(Commands.ApkDescription::getTargeting).filter(Targeting.ApkTargeting::hasScreenDensityTargeting).map(Targeting.ApkTargeting::getScreenDensityTargeting).collect(ImmutableSet.toImmutableSet()));
        }
        return screenDensityTargetingOptions.build().isEmpty() ? ImmutableSet.of(Targeting.ScreenDensityTargeting.getDefaultInstance()) : screenDensityTargetingOptions.build();
    }

    private ImmutableSet<Targeting.LanguageTargeting> getAllLanguageTargetings(ImmutableList<Commands.ApkDescription> apkDescriptions) {
        ImmutableSet.Builder languageTargetingOptions = ImmutableSet.builder();
        if (DeviceSpecUtils.isLocalesMissing(this.getSizeRequest.getDeviceSpec())) {
            languageTargetingOptions.addAll((Iterable)apkDescriptions.stream().map(Commands.ApkDescription::getTargeting).filter(Targeting.ApkTargeting::hasLanguageTargeting).map(Targeting.ApkTargeting::getLanguageTargeting).collect(ImmutableSet.toImmutableSet()));
        }
        return languageTargetingOptions.build().isEmpty() ? ImmutableSet.of(Targeting.LanguageTargeting.getDefaultInstance()) : languageTargetingOptions.build();
    }

    private ConfigurationSizes getSizesPerConfiguration(ImmutableSet<Targeting.AbiTargeting> abiTargetingOptions, ImmutableSet<Targeting.LanguageTargeting> languageTargetingOptions, ImmutableSet<Targeting.ScreenDensityTargeting> screenDensityTargetingOptions) {
        HashMap<SizeConfiguration, Long> minSizeByConfiguration = new HashMap<SizeConfiguration, Long>();
        HashMap<SizeConfiguration, Long> maxSizeByConfiguration = new HashMap<SizeConfiguration, Long>();
        Targeting.SdkVersionTargeting sdkVersionTargeting = this.variant.getTargeting().getSdkVersionTargeting();
        for (Targeting.AbiTargeting abiTargeting : abiTargetingOptions) {
            for (Targeting.ScreenDensityTargeting screenDensityTargeting : screenDensityTargetingOptions) {
                for (Targeting.LanguageTargeting languageTargeting : languageTargetingOptions) {
                    SizeConfiguration configuration = this.mergeWithDeviceSpec(this.getSizeConfiguration(sdkVersionTargeting, abiTargeting, screenDensityTargeting, languageTargeting), this.getSizeRequest.getDeviceSpec());
                    long compressedSize = this.getCompressedSize(new ApkMatcher(this.getDeviceSpec(this.getSizeRequest.getDeviceSpec(), sdkVersionTargeting, abiTargeting, screenDensityTargeting, languageTargeting), this.getSizeRequest.getModules(), this.getSizeRequest.getInstant()).getMatchingApksFromVariant(this.variant, this.bundleVersion));
                    minSizeByConfiguration.merge(configuration, compressedSize, Math::min);
                    maxSizeByConfiguration.merge(configuration, compressedSize, Math::max);
                }
            }
        }
        return ConfigurationSizes.create(ImmutableMap.copyOf(minSizeByConfiguration), ImmutableMap.copyOf(maxSizeByConfiguration));
    }

    private SizeConfiguration getSizeConfiguration(Targeting.SdkVersionTargeting sdkVersionTargeting, Targeting.AbiTargeting abiTargeting, Targeting.ScreenDensityTargeting screenDensityTargeting, Targeting.LanguageTargeting languageTargeting) {
        ImmutableSet<GetSizeRequest.Dimension> dimensions = this.getSizeRequest.getDimensions();
        SizeConfiguration.Builder sizeConfiguration = SizeConfiguration.builder();
        if (dimensions.contains((Object)GetSizeRequest.Dimension.SDK)) {
            sizeConfiguration.setSdkVersion(SizeConfiguration.getSdkName(sdkVersionTargeting).orElse(""));
        }
        if (dimensions.contains((Object)GetSizeRequest.Dimension.ABI)) {
            sizeConfiguration.setAbi(SizeConfiguration.getAbiName(abiTargeting).orElse(""));
        }
        if (dimensions.contains((Object)GetSizeRequest.Dimension.SCREEN_DENSITY)) {
            sizeConfiguration.setScreenDensity(SizeConfiguration.getScreenDensityName(screenDensityTargeting).orElse(""));
        }
        if (dimensions.contains((Object)GetSizeRequest.Dimension.LANGUAGE)) {
            sizeConfiguration.setLocale(SizeConfiguration.getLocaleName(languageTargeting).orElse(""));
        }
        return sizeConfiguration.build();
    }

    private Devices.DeviceSpec getDeviceSpec(Devices.DeviceSpec deviceSpec, Targeting.SdkVersionTargeting sdkVersionTargeting, Targeting.AbiTargeting abiTargeting, Targeting.ScreenDensityTargeting screenDensityTargeting, Targeting.LanguageTargeting languageTargeting) {
        return new DeviceSpecUtils.DeviceSpecFromTargetingBuilder(deviceSpec).setSdkVersion(sdkVersionTargeting).setSupportedAbis(abiTargeting).setScreenDensity(screenDensityTargeting).setSupportedLocales(languageTargeting).build();
    }

    private SizeConfiguration mergeWithDeviceSpec(SizeConfiguration getSizeConfiguration, Devices.DeviceSpec deviceSpec) {
        ImmutableSet<GetSizeRequest.Dimension> dimensions = this.getSizeRequest.getDimensions();
        SizeConfiguration.Builder mergedSizeConfiguration = getSizeConfiguration.toBuilder();
        if (dimensions.contains((Object)GetSizeRequest.Dimension.ABI) && !DeviceSpecUtils.isAbiMissing(deviceSpec)) {
            mergedSizeConfiguration.setAbi(COMMA_JOINER.join(deviceSpec.getSupportedAbisList()));
        }
        if (dimensions.contains((Object)GetSizeRequest.Dimension.SCREEN_DENSITY) && !DeviceSpecUtils.isScreenDensityMissing(deviceSpec)) {
            mergedSizeConfiguration.setScreenDensity(Integer.toString(deviceSpec.getScreenDensity()));
        }
        if (dimensions.contains((Object)GetSizeRequest.Dimension.LANGUAGE) && !DeviceSpecUtils.isLocalesMissing(deviceSpec)) {
            mergedSizeConfiguration.setLocale(COMMA_JOINER.join(deviceSpec.getSupportedLocalesList()));
        }
        if (dimensions.contains((Object)GetSizeRequest.Dimension.SDK) && !DeviceSpecUtils.isSdkVersionMissing(deviceSpec)) {
            mergedSizeConfiguration.setSdkVersion(String.format("%d", deviceSpec.getSdkVersion()));
        }
        return mergedSizeConfiguration.build();
    }

    private long getCompressedSize(ImmutableList<ZipPath> apkPaths) {
        return apkPaths.stream().mapToLong(apkPath -> this.sizeByApkPaths.get(apkPath.toString())).sum();
    }
}

