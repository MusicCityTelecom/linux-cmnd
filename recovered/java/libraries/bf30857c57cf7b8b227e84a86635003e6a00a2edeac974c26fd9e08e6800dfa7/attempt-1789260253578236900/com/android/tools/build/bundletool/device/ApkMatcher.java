/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.device;

import com.android.bundle.Commands;
import com.android.bundle.Devices;
import com.android.bundle.Targeting;
import com.android.tools.build.bundletool.device.AbiMatcher;
import com.android.tools.build.bundletool.device.DeviceFeatureMatcher;
import com.android.tools.build.bundletool.device.LanguageMatcher;
import com.android.tools.build.bundletool.device.ModuleMatcher;
import com.android.tools.build.bundletool.device.MultiAbiMatcher;
import com.android.tools.build.bundletool.device.ScreenDensityMatcher;
import com.android.tools.build.bundletool.device.SdkVersionMatcher;
import com.android.tools.build.bundletool.device.TargetingDimensionMatcher;
import com.android.tools.build.bundletool.device.TextureCompressionFormatMatcher;
import com.android.tools.build.bundletool.device.VariantMatcher;
import com.android.tools.build.bundletool.model.ModuleSplit;
import com.android.tools.build.bundletool.model.ZipPath;
import com.android.tools.build.bundletool.model.exceptions.CommandExecutionException;
import com.android.tools.build.bundletool.model.exceptions.ValidationException;
import com.android.tools.build.bundletool.model.utils.ModuleDependenciesUtils;
import com.android.tools.build.bundletool.model.version.Version;
import com.android.tools.build.bundletool.model.version.VersionGuardedFeature;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.function.Consumer;

public class ApkMatcher {
    private final ImmutableList<? extends TargetingDimensionMatcher<?>> apkMatchers;
    private final Optional<ImmutableSet<String>> requestedModuleNames;
    private final boolean matchInstant;
    private final ModuleMatcher moduleMatcher;
    private final VariantMatcher variantMatcher;

    public ApkMatcher(Devices.DeviceSpec deviceSpec) {
        this(deviceSpec, Optional.empty(), false);
    }

    public ApkMatcher(Devices.DeviceSpec deviceSpec, Optional<ImmutableSet<String>> requestedModuleNames, boolean matchInstant) {
        Preconditions.checkArgument(!requestedModuleNames.isPresent() || !requestedModuleNames.get().isEmpty(), "Set of requested split modules cannot be empty.");
        SdkVersionMatcher sdkVersionMatcher = new SdkVersionMatcher(deviceSpec);
        AbiMatcher abiMatcher = new AbiMatcher(deviceSpec);
        MultiAbiMatcher multiAbiMatcher = new MultiAbiMatcher(deviceSpec);
        ScreenDensityMatcher screenDensityMatcher = new ScreenDensityMatcher(deviceSpec);
        LanguageMatcher languageMatcher = new LanguageMatcher(deviceSpec);
        DeviceFeatureMatcher deviceFeatureMatcher = new DeviceFeatureMatcher(deviceSpec);
        TextureCompressionFormatMatcher textureCompressionFormatMatcher = new TextureCompressionFormatMatcher(deviceSpec);
        this.apkMatchers = ImmutableList.of(sdkVersionMatcher, abiMatcher, multiAbiMatcher, screenDensityMatcher, languageMatcher, textureCompressionFormatMatcher);
        this.requestedModuleNames = requestedModuleNames;
        this.matchInstant = matchInstant;
        this.moduleMatcher = new ModuleMatcher(sdkVersionMatcher, deviceFeatureMatcher);
        this.variantMatcher = new VariantMatcher(sdkVersionMatcher, abiMatcher, multiAbiMatcher, screenDensityMatcher, textureCompressionFormatMatcher, matchInstant);
    }

    public ImmutableList<ZipPath> getMatchingApks(Commands.BuildApksResult buildApksResult) {
        Optional<Commands.Variant> matchingVariant = this.variantMatcher.getMatchingVariant(buildApksResult);
        if (matchingVariant.isPresent()) {
            this.validateVariant(matchingVariant.get(), buildApksResult);
        }
        ImmutableList variantApks = matchingVariant.isPresent() ? this.getMatchingApksFromVariant(matchingVariant.get(), Version.of(buildApksResult.getBundletool().getVersion())) : ImmutableList.of();
        ImmutableList<ZipPath> assetModuleApks = this.getMatchingApksFromAssetModules(buildApksResult);
        return ((ImmutableList.Builder)((ImmutableList.Builder)ImmutableList.builder().addAll((Iterable)variantApks)).addAll(assetModuleApks)).build();
    }

    public ImmutableList<ZipPath> getMatchingApksFromVariant(Commands.Variant variant, Version bundleVersion) {
        ImmutableList.Builder matchedApksBuilder = ImmutableList.builder();
        Predicate<String> moduleNameMatcher = this.getModuleNameMatcher(variant, bundleVersion);
        for (Commands.ApkSet apkSet : variant.getApkSetList()) {
            String moduleName = apkSet.getModuleMetadata().getName();
            for (Commands.ApkDescription apkDescription : apkSet.getApkDescriptionList()) {
                Targeting.ApkTargeting apkTargeting = apkDescription.getTargeting();
                boolean isSplit = !apkDescription.hasStandaloneApkMetadata() && !apkDescription.hasApexApkMetadata();
                this.checkCompatibleWithApkTargeting(apkTargeting);
                if (!this.matchesApk(apkTargeting, isSplit, moduleName, moduleNameMatcher)) continue;
                matchedApksBuilder.add(ZipPath.create(apkDescription.getPath()));
            }
        }
        return matchedApksBuilder.build();
    }

    private Predicate<String> getModuleNameMatcher(Commands.Variant variant, Version bundleVersion) {
        if (this.requestedModuleNames.isPresent()) {
            ImmutableMultimap<String, String> moduleDependenciesMap = ModuleDependenciesUtils.buildAdjacencyMap(variant);
            HashSet<String> dependencyModules = new HashSet<String>((Collection)this.requestedModuleNames.get());
            for (String requestedModuleName : this.requestedModuleNames.get()) {
                ModuleDependenciesUtils.addModuleDependencies(requestedModuleName, moduleDependenciesMap, dependencyModules);
            }
            if (this.matchInstant) {
                return dependencyModules::contains;
            }
            return Predicates.or(this.buildModulesDeliveredInstallTime(variant, bundleVersion)::contains, dependencyModules::contains);
        }
        if (this.matchInstant) {
            return Predicates.alwaysTrue();
        }
        return this.buildModulesDeliveredInstallTime(variant, bundleVersion)::contains;
    }

    private void validateVariant(Commands.Variant variant, Commands.BuildApksResult buildApksResult) {
        if (this.requestedModuleNames.isPresent()) {
            Sets.SetView availableModules = Sets.union(variant.getApkSetList().stream().map(Commands.ApkSet::getModuleMetadata).map(Commands.ModuleMetadata::getName).collect(ImmutableSet.toImmutableSet()), buildApksResult.getAssetSliceSetList().stream().map(Commands.AssetSliceSet::getAssetModuleMetadata).map(Commands.AssetModuleMetadata::getName).collect(ImmutableSet.toImmutableSet()));
            Sets.SetView unknownModules = Sets.difference(this.requestedModuleNames.get(), availableModules);
            if (!unknownModules.isEmpty()) {
                throw ValidationException.builder().withMessage("The APK Set archive does not contain the following modules: %s", unknownModules).build();
            }
        }
    }

    private ImmutableSet<String> buildModulesDeliveredInstallTime(Commands.Variant variant, Version bundleVersion) {
        return variant.getApkSetList().stream().map(Commands.ApkSet::getModuleMetadata).filter(moduleMetadata -> this.willBeDeliveredInstallTime((Commands.ModuleMetadata)moduleMetadata, bundleVersion)).map(Commands.ModuleMetadata::getName).collect(ImmutableSet.toImmutableSet());
    }

    private boolean willBeDeliveredInstallTime(Commands.ModuleMetadata moduleMetadata, Version bundleVersion) {
        boolean installTime = VersionGuardedFeature.NEW_DELIVERY_TYPE_MANIFEST_TAG.enabledForVersion(bundleVersion) ? moduleMetadata.getDeliveryType().equals(Commands.DeliveryType.INSTALL_TIME) : !moduleMetadata.getOnDemandDeprecated();
        return installTime && this.moduleMatcher.matchesModuleTargeting(moduleMetadata.getTargeting());
    }

    private boolean matchesApk(Targeting.ApkTargeting apkTargeting, boolean isSplit, String moduleName, Predicate<String> moduleNameMatcher) {
        boolean matchesTargeting = this.matchesApkTargeting(apkTargeting);
        if (isSplit) {
            return matchesTargeting && moduleNameMatcher.test(moduleName);
        }
        if (matchesTargeting && this.requestedModuleNames.isPresent()) {
            throw CommandExecutionException.builder().withMessage("Cannot restrict modules when the device matches a non-split APK.").build();
        }
        return matchesTargeting;
    }

    private boolean matchesApkTargeting(Targeting.ApkTargeting apkTargeting) {
        return this.apkMatchers.stream().allMatch(matcher -> matcher.getApkTargetingPredicate().test(apkTargeting));
    }

    public boolean matchesModuleSplitByTargeting(ModuleSplit moduleSplit) {
        this.variantMatcher.checkCompatibleWithVariantTargeting(moduleSplit.getVariantTargeting());
        this.checkCompatibleWithApkTargeting(moduleSplit.getApkTargeting());
        return this.variantMatcher.matchesVariantTargeting(moduleSplit.getVariantTargeting()) && this.matchesApkTargeting(moduleSplit.getApkTargeting());
    }

    public void checkCompatibleWithApkTargeting(ModuleSplit moduleSplit) {
        this.checkCompatibleWithApkTargeting(moduleSplit.getApkTargeting());
    }

    private void checkCompatibleWithApkTargeting(Targeting.ApkTargeting apkTargeting) {
        this.apkMatchers.forEach((Consumer<TargetingDimensionMatcher<?>>)((Consumer<TargetingDimensionMatcher>)matcher -> this.checkCompatibleWithApkTargetingHelper((TargetingDimensionMatcher)matcher, apkTargeting)));
    }

    private <T> void checkCompatibleWithApkTargetingHelper(TargetingDimensionMatcher<T> matcher, Targeting.ApkTargeting apkTargeting) {
        matcher.checkDeviceCompatible(matcher.getTargetingValue(apkTargeting));
    }

    private ImmutableList<ZipPath> getMatchingApksFromAssetModules(Commands.BuildApksResult buildApksResult) {
        ImmutableList.Builder matchedApksBuilder = ImmutableList.builder();
        Predicate<String> assetModuleNameMatcher = this.getAssetModuleNameMatcher(buildApksResult);
        for (Commands.AssetSliceSet sliceSet : buildApksResult.getAssetSliceSetList()) {
            String moduleName = sliceSet.getAssetModuleMetadata().getName();
            for (Commands.ApkDescription apkDescription : sliceSet.getApkDescriptionList()) {
                Targeting.ApkTargeting apkTargeting = apkDescription.getTargeting();
                if (!this.matchesApk(apkTargeting, true, moduleName, assetModuleNameMatcher)) continue;
                matchedApksBuilder.add(ZipPath.create(apkDescription.getPath()));
            }
        }
        return matchedApksBuilder.build();
    }

    private Predicate<String> getAssetModuleNameMatcher(Commands.BuildApksResult buildApksResult) {
        if (this.requestedModuleNames.isPresent()) {
            return this.requestedModuleNames.get()::contains;
        }
        ImmutableSet upfrontAssetModuleNames = buildApksResult.getAssetSliceSetList().stream().filter(sliceSet -> sliceSet.getAssetModuleMetadata().getDeliveryType().equals(Commands.DeliveryType.INSTALL_TIME)).map(sliceSet -> sliceSet.getAssetModuleMetadata().getName()).collect(ImmutableSet.toImmutableSet());
        return upfrontAssetModuleNames::contains;
    }
}

