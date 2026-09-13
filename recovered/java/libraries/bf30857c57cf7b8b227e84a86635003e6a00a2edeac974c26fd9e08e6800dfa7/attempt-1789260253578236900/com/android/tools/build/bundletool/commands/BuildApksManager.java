/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.commands;

import com.android.bundle.Commands;
import com.android.bundle.Config;
import com.android.bundle.Devices;
import com.android.tools.build.bundletool.commands.BuildApksCommand;
import com.android.tools.build.bundletool.device.AdbServer;
import com.android.tools.build.bundletool.device.ApkMatcher;
import com.android.tools.build.bundletool.device.DeviceAnalyzer;
import com.android.tools.build.bundletool.device.IncompatibleDeviceException;
import com.android.tools.build.bundletool.io.ApkPathManager;
import com.android.tools.build.bundletool.io.ApkSerializerManager;
import com.android.tools.build.bundletool.io.ApkSetBuilderFactory;
import com.android.tools.build.bundletool.io.SplitApkSerializer;
import com.android.tools.build.bundletool.io.StandaloneApkSerializer;
import com.android.tools.build.bundletool.model.Aapt2Command;
import com.android.tools.build.bundletool.model.ApkListener;
import com.android.tools.build.bundletool.model.ApkModifier;
import com.android.tools.build.bundletool.model.AppBundle;
import com.android.tools.build.bundletool.model.BundleModule;
import com.android.tools.build.bundletool.model.BundleModuleName;
import com.android.tools.build.bundletool.model.GeneratedApks;
import com.android.tools.build.bundletool.model.GeneratedAssetSlices;
import com.android.tools.build.bundletool.model.ModuleSplit;
import com.android.tools.build.bundletool.model.OptimizationDimension;
import com.android.tools.build.bundletool.model.SigningConfiguration;
import com.android.tools.build.bundletool.model.SourceStamp;
import com.android.tools.build.bundletool.model.exceptions.CommandExecutionException;
import com.android.tools.build.bundletool.model.targeting.AlternativeVariantTargetingPopulator;
import com.android.tools.build.bundletool.model.utils.ModuleDependenciesUtils;
import com.android.tools.build.bundletool.model.utils.SplitsXmlInjector;
import com.android.tools.build.bundletool.model.utils.files.FilePreconditions;
import com.android.tools.build.bundletool.model.utils.files.FileUtils;
import com.android.tools.build.bundletool.model.version.BundleToolVersion;
import com.android.tools.build.bundletool.model.version.Version;
import com.android.tools.build.bundletool.model.version.VersionGuardedFeature;
import com.android.tools.build.bundletool.optimizations.ApkOptimizations;
import com.android.tools.build.bundletool.optimizations.OptimizationsMerger;
import com.android.tools.build.bundletool.preprocessors.AppBundle64BitNativeLibrariesPreprocessor;
import com.android.tools.build.bundletool.preprocessors.EntryCompressionPreprocessor;
import com.android.tools.build.bundletool.preprocessors.LocalTestingPreprocessor;
import com.android.tools.build.bundletool.splitters.ApkGenerationConfiguration;
import com.android.tools.build.bundletool.splitters.AssetSlicesGenerator;
import com.android.tools.build.bundletool.splitters.ResourceAnalyzer;
import com.android.tools.build.bundletool.splitters.ShardedApksGenerator;
import com.android.tools.build.bundletool.splitters.SplitApksGenerator;
import com.android.tools.build.bundletool.validation.AppBundleValidator;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Stream;
import java.util.zip.ZipFile;
import javax.annotation.CheckReturnValue;

final class BuildApksManager {
    private static final Logger logger = Logger.getLogger(BuildApksManager.class.getName());
    private final BuildApksCommand command;
    private final Aapt2Command aapt2Command;
    private final Path tempDir;

    BuildApksManager(BuildApksCommand command, Aapt2Command aapt2Command, Path tempDir) {
        this.command = command;
        this.aapt2Command = aapt2Command;
        this.tempDir = tempDir;
    }

    public Path execute() {
        Path outputDirectory;
        this.validateInput();
        Path path = outputDirectory = this.command.getCreateApkSetArchive() ? this.command.getOutputFile().getParent() : this.command.getOutputFile();
        if (outputDirectory != null && Files.notExists(outputDirectory, new LinkOption[0])) {
            logger.info("Output directory '" + outputDirectory + "' does not exist, creating it.");
            FileUtils.createDirectories(outputDirectory);
        }
        Optional<Devices.DeviceSpec> deviceSpec = this.command.getDeviceSpec();
        if (this.command.getGenerateOnlyForConnectedDevice()) {
            deviceSpec = Optional.of(this.getDeviceSpecFromConnectedDevice());
        }
        try (ZipFile bundleZip = new ZipFile(this.command.getBundlePath().toFile());){
            this.executeWithZip(bundleZip, deviceSpec, this.command.getSourceStamp());
        }
        catch (IOException e2) {
            throw new UncheckedIOException(String.format("An error occurred when processing the bundle '%s'.", this.command.getBundlePath()), e2);
        }
        finally {
            if (this.command.isExecutorServiceCreatedByBundleTool()) {
                this.command.getExecutorService().shutdown();
            }
        }
        return this.command.getOutputFile();
    }

    private void executeWithZip(ZipFile bundleZip, Optional<Devices.DeviceSpec> deviceSpec, Optional<SourceStamp> sourceStamp) throws IOException {
        Optional<String> stampSource = sourceStamp.map(SourceStamp::getSource);
        AppBundleValidator bundleValidator = AppBundleValidator.create(this.command.getExtraValidators());
        bundleValidator.validateFile(bundleZip);
        AppBundle appBundle = AppBundle.buildFromZip(bundleZip);
        bundleValidator.validate(appBundle);
        appBundle = this.applyPreprocessors(appBundle);
        ImmutableSet<BundleModule> requestedModules = this.command.getModules().isEmpty() ? ImmutableSet.of() : ModuleDependenciesUtils.getModulesIncludingDependencies(appBundle, BuildApksManager.getBundleModules(appBundle, this.command.getModules()));
        Config.BundleConfig bundleConfig = appBundle.getBundleConfig();
        Version bundleVersion = BundleToolVersion.getVersionFromBundleConfig(bundleConfig);
        GeneratedApks.Builder generatedApksBuilder = GeneratedApks.builder();
        GeneratedAssetSlices.Builder generatedAssetSlices = GeneratedAssetSlices.builder();
        boolean enableUniversalAsFallbackForSplits = false;
        ApksToGenerate apksToGenerate = new ApksToGenerate(appBundle, this.command.getApkBuildMode(), enableUniversalAsFallbackForSplits, deviceSpec);
        if (apksToGenerate.generateSplitApks()) {
            generatedApksBuilder.setSplitApks(this.generateSplitApks(appBundle, stampSource));
        }
        if (apksToGenerate.generateInstantApks()) {
            generatedApksBuilder.setInstantApks(this.generateInstantApks(appBundle, stampSource));
        }
        if (apksToGenerate.generateStandaloneApks()) {
            generatedApksBuilder.setStandaloneApks(this.generateStandaloneApks(this.tempDir, appBundle, stampSource));
        }
        if (apksToGenerate.generateUniversalApk()) {
            ImmutableList<BundleModule> modulesToFuse = requestedModules.isEmpty() ? BuildApksManager.modulesToFuse(((ImmutableCollection)appBundle.getFeatureModules().values()).asList()) : requestedModules.asList();
            generatedApksBuilder.setStandaloneApks(new ShardedApksGenerator(this.tempDir, bundleVersion, false, BuildApksManager.getSuffixStrippings(bundleConfig), stampSource).generateSplits(modulesToFuse, appBundle.getBundleMetadata(), ApkOptimizations.getOptimizationsForUniversalApk()));
        }
        if (apksToGenerate.generateSystemApks()) {
            generatedApksBuilder.setSystemApks(this.generateSystemApks(appBundle, deviceSpec, requestedModules));
        }
        if (apksToGenerate.generateAssetSlices()) {
            generatedAssetSlices.setAssetSlices(this.generateAssetSlices(appBundle));
        }
        GeneratedApks generatedApks = AlternativeVariantTargetingPopulator.populateAlternativeVariantTargeting(generatedApksBuilder.build(), appBundle.getBaseModule().getAndroidManifest().getMaxSdkVersion());
        SplitsXmlInjector splitsXmlInjector = new SplitsXmlInjector();
        generatedApks = splitsXmlInjector.process(generatedApks);
        if (deviceSpec.isPresent()) {
            BuildApksManager.checkDeviceCompatibilityWithBundle(generatedApks, deviceSpec.get());
        }
        Optional<SigningConfiguration> stampSigningConfiguration = sourceStamp.map(SourceStamp::getSigningConfiguration);
        ApkSetBuilderFactory.ApkSetBuilder apkSetBuilder = this.createApkSetBuilder(this.aapt2Command, this.command.getSigningConfiguration(), stampSigningConfiguration, bundleVersion, bundleConfig.getCompression(), this.tempDir);
        ApkSerializerManager apkSerializerManager = new ApkSerializerManager(appBundle, apkSetBuilder, this.command.getExecutorService(), this.command.getApkListener().orElse(ApkListener.NO_OP), this.command.getApkModifier().orElse(ApkModifier.NO_OP), this.command.getFirstVariantNumber().orElse(0));
        apkSerializerManager.populateApkSetBuilder(generatedApks, generatedAssetSlices.build(), this.command.getApkBuildMode(), deviceSpec, BuildApksManager.getLocalTestingInfo(appBundle));
        if (this.command.getOverwriteOutput()) {
            Files.deleteIfExists(this.command.getOutputFile());
        }
        apkSetBuilder.writeTo(this.command.getOutputFile());
    }

    private ImmutableList<ModuleSplit> generateStandaloneApks(Path tempDir, AppBundle appBundle, Optional<String> stampSource) {
        ImmutableList<BundleModule> allModules = BuildApksManager.getModulesForStandaloneApks(appBundle);
        Version bundleVersion = Version.of(appBundle.getBundleConfig().getBundletool().getVersion());
        ShardedApksGenerator shardedApksGenerator = new ShardedApksGenerator(tempDir, bundleVersion, BuildApksManager.shouldStrip64BitLibrariesFromShards(appBundle), BuildApksManager.getSuffixStrippings(appBundle.getBundleConfig()), stampSource);
        return appBundle.isApex() ? shardedApksGenerator.generateApexSplits(BuildApksManager.modulesToFuse(allModules)) : shardedApksGenerator.generateSplits(BuildApksManager.modulesToFuse(allModules), appBundle.getBundleMetadata(), this.getApkOptimizations(appBundle.getBundleConfig()));
    }

    private ImmutableList<ModuleSplit> generateAssetSlices(AppBundle appBundle) {
        ApkGenerationConfiguration assetSlicesGenerationConfiguration = BuildApksManager.getAssetSliceGenerationConfiguration(appBundle.getBundleConfig());
        AssetSlicesGenerator assetSlicesGenerator = new AssetSlicesGenerator(appBundle, assetSlicesGenerationConfiguration);
        return assetSlicesGenerator.generateAssetSlices();
    }

    private ImmutableList<ModuleSplit> generateInstantApks(AppBundle appBundle, Optional<String> stampSource) {
        Version bundleVersion = Version.of(appBundle.getBundleConfig().getBundletool().getVersion());
        ImmutableList allFeatureModules = ((ImmutableCollection)appBundle.getFeatureModules().values()).asList();
        ImmutableList<BundleModule> instantModules = allFeatureModules.stream().filter(BundleModule::isInstantModule).collect(ImmutableList.toImmutableList());
        ApkGenerationConfiguration instantApkGenerationConfiguration = this.getCommonSplitApkGenerationConfiguration(appBundle).setForInstantAppVariants(true).setEnableDexCompressionSplitter(false).build();
        return new SplitApksGenerator(instantModules, bundleVersion, instantApkGenerationConfiguration, stampSource).generateSplits();
    }

    private ImmutableList<ModuleSplit> generateSplitApks(AppBundle appBundle, Optional<String> stampSource) throws IOException {
        Version bundleVersion = Version.of(appBundle.getBundleConfig().getBundletool().getVersion());
        ApkGenerationConfiguration.Builder apkGenerationConfiguration = this.getCommonSplitApkGenerationConfiguration(appBundle);
        if (VersionGuardedFeature.RESOURCES_REFERENCED_IN_MANIFEST_TO_MASTER_SPLIT.enabledForVersion(bundleVersion)) {
            apkGenerationConfiguration.setBaseManifestReachableResources(new ResourceAnalyzer(appBundle).findAllAppResourcesReachableFromBaseManifest());
        }
        ImmutableList<BundleModule> featureModules = ((ImmutableCollection)appBundle.getFeatureModules().values()).asList();
        return new SplitApksGenerator(featureModules, bundleVersion, apkGenerationConfiguration.build(), stampSource).generateSplits();
    }

    private ImmutableList<ModuleSplit> generateSystemApks(AppBundle appBundle, Optional<Devices.DeviceSpec> deviceSpec, ImmutableSet<BundleModule> requestedModules) {
        Version bundleVersion = Version.of(appBundle.getBundleConfig().getBundletool().getVersion());
        ImmutableList<BundleModule> featureModules = ((ImmutableCollection)appBundle.getFeatureModules().values()).asList();
        ImmutableList<BundleModule> modulesToFuse = requestedModules.isEmpty() ? BuildApksManager.modulesToFuse(featureModules) : requestedModules.asList();
        return new ShardedApksGenerator(this.tempDir, bundleVersion, BuildApksManager.shouldStrip64BitLibrariesFromShards(appBundle), BuildApksManager.getSuffixStrippings(appBundle.getBundleConfig())).generateSystemSplits(featureModules, modulesToFuse.stream().map(BundleModule::getName).collect(ImmutableSet.toImmutableSet()), appBundle.getBundleMetadata(), this.getApkOptimizations(appBundle.getBundleConfig()), deviceSpec);
    }

    private static void checkDeviceCompatibilityWithBundle(GeneratedApks generatedApks, Devices.DeviceSpec deviceSpec) {
        ApkMatcher apkMatcher = new ApkMatcher(deviceSpec);
        generatedApks.getAllApksStream().forEach(apkMatcher::checkCompatibleWithApkTargeting);
    }

    private Devices.DeviceSpec getDeviceSpecFromConnectedDevice() {
        AdbServer adbServer = this.command.getAdbServer().get();
        adbServer.init(this.command.getAdbPath().get());
        return new DeviceAnalyzer(adbServer).getDeviceSpec(this.command.getDeviceId());
    }

    private ApkSetBuilderFactory.ApkSetBuilder createApkSetBuilder(Aapt2Command aapt2Command, Optional<SigningConfiguration> signingConfiguration, Optional<SigningConfiguration> stampSigningConfiguration, Version bundleVersion, Config.Compression compression, Path tempDir) {
        ApkPathManager apkPathmanager = new ApkPathManager();
        SplitApkSerializer splitApkSerializer = new SplitApkSerializer(apkPathmanager, aapt2Command, signingConfiguration, stampSigningConfiguration, bundleVersion, compression);
        StandaloneApkSerializer standaloneApkSerializer = new StandaloneApkSerializer(apkPathmanager, aapt2Command, signingConfiguration, stampSigningConfiguration, bundleVersion, compression);
        if (!this.command.getCreateApkSetArchive()) {
            return ApkSetBuilderFactory.createApkSetWithoutArchiveBuilder(splitApkSerializer, standaloneApkSerializer, this.command.getOutputFile());
        }
        return ApkSetBuilderFactory.createApkSetBuilder(splitApkSerializer, standaloneApkSerializer, tempDir);
    }

    private ApkGenerationConfiguration.Builder getCommonSplitApkGenerationConfiguration(AppBundle appBundle) {
        Config.BundleConfig bundleConfig = appBundle.getBundleConfig();
        Version bundleToolVersion = Version.of(bundleConfig.getBundletool().getVersion());
        ApkOptimizations apkOptimizations = this.getApkOptimizations(bundleConfig);
        ApkGenerationConfiguration.Builder apkGenerationConfiguration = ApkGenerationConfiguration.builder().setOptimizationDimensions(apkOptimizations.getSplitDimensions());
        boolean enableNativeLibraryCompressionSplitter = apkOptimizations.getUncompressNativeLibraries();
        apkGenerationConfiguration.setEnableNativeLibraryCompressionSplitter(enableNativeLibraryCompressionSplitter);
        apkGenerationConfiguration.setInstallableOnExternalStorage(appBundle.getBaseModule().getAndroidManifest().getInstallLocationValue().map(installLocation -> installLocation.equals("auto") || installLocation.equals("preferExternal")).orElse(false));
        apkGenerationConfiguration.setMasterPinnedResourceIds(appBundle.getMasterPinnedResourceIds());
        apkGenerationConfiguration.setMasterPinnedResourceNames(appBundle.getMasterPinnedResourceNames());
        apkGenerationConfiguration.setSuffixStrippings(BuildApksManager.getSuffixStrippings(bundleConfig));
        return apkGenerationConfiguration;
    }

    private static ApkGenerationConfiguration getAssetSliceGenerationConfiguration(Config.BundleConfig bundleConfig) {
        ApkOptimizations apkOptimizations = ApkOptimizations.getOptimizationsForAssetSlices();
        return ApkGenerationConfiguration.builder().setOptimizationDimensions(apkOptimizations.getSplitDimensions()).setSuffixStrippings(BuildApksManager.getSuffixStrippings(bundleConfig)).build();
    }

    private static ImmutableList<BundleModule> modulesToFuse(ImmutableList<BundleModule> modules) {
        return modules.stream().filter(BundleModule::isIncludedInFusing).collect(ImmutableList.toImmutableList());
    }

    private static ImmutableMap<OptimizationDimension, Config.SuffixStripping> getSuffixStrippings(Config.BundleConfig bundleConfig) {
        return OptimizationsMerger.getSuffixStrippings(bundleConfig.getOptimizations().getSplitsConfig().getSplitDimensionList());
    }

    private ApkOptimizations getApkOptimizations(Config.BundleConfig bundleConfig) {
        return new OptimizationsMerger().mergeWithDefaults(bundleConfig, this.command.getOptimizationDimensions());
    }

    private void validateInput() {
        FilePreconditions.checkFileExistsAndReadable(this.command.getBundlePath());
        if (this.command.getCreateApkSetArchive() && !this.command.getOverwriteOutput()) {
            FilePreconditions.checkFileDoesNotExist(this.command.getOutputFile());
        }
        if (this.command.getGenerateOnlyForConnectedDevice()) {
            Preconditions.checkArgument(this.command.getAdbServer().isPresent(), "Property 'adbServer' is required when 'generateOnlyForConnectedDevice' is true.");
            Preconditions.checkArgument(this.command.getAdbPath().isPresent(), "Property 'adbPath' is required when 'generateOnlyForConnectedDevice' is true.");
            FilePreconditions.checkFileExistsAndExecutable(this.command.getAdbPath().get());
        }
    }

    private static boolean targetsOnlyPreL(AppBundle bundle) {
        Optional<Integer> maxSdkVersion = bundle.getBaseModule().getAndroidManifest().getMaxSdkVersion();
        return maxSdkVersion.isPresent() && maxSdkVersion.get() < 21;
    }

    private static boolean targetsPreL(AppBundle bundle) {
        int baseMinSdkVersion = bundle.getBaseModule().getAndroidManifest().getEffectiveMinSdkVersion();
        return baseMinSdkVersion < 21;
    }

    private static ImmutableList<BundleModule> getBundleModules(AppBundle appBundle, ImmutableSet<String> moduleNames) {
        return moduleNames.stream().map(BundleModuleName::create).map(appBundle::getModule).collect(ImmutableList.toImmutableList());
    }

    private static ImmutableList<BundleModule> getModulesForStandaloneApks(AppBundle appBundle) {
        return Stream.concat(appBundle.getFeatureModules().values().stream(), appBundle.getAssetModules().values().stream().filter(module -> module.getDeliveryType().equals((Object)BundleModule.ModuleDeliveryType.ALWAYS_INITIAL_INSTALL))).collect(ImmutableList.toImmutableList());
    }

    private static boolean shouldStrip64BitLibrariesFromShards(AppBundle appBundle) {
        return appBundle.getBundleConfig().getOptimizations().getStandaloneConfig().getStrip64BitLibraries();
    }

    @CheckReturnValue
    private AppBundle applyPreprocessors(AppBundle bundle) {
        bundle = new AppBundle64BitNativeLibrariesPreprocessor(this.command.getOutputPrintStream()).preprocess(bundle);
        if (this.command.getLocalTestingMode()) {
            bundle = new LocalTestingPreprocessor().preprocess(bundle);
        }
        bundle = new EntryCompressionPreprocessor().preprocess(bundle);
        return bundle;
    }

    private static Commands.LocalTestingInfo getLocalTestingInfo(AppBundle bundle) {
        Commands.LocalTestingInfo.Builder localTestingInfo = Commands.LocalTestingInfo.newBuilder();
        bundle.getBaseModule().getAndroidManifest().getMetadataValue("local_testing_dir").ifPresent(localTestingPath -> localTestingInfo.setEnabled(true).setLocalTestingPath((String)localTestingPath));
        return localTestingInfo.build();
    }

    private static class ApksToGenerate {
        private final AppBundle appBundle;
        private final BuildApksCommand.ApkBuildMode apkBuildMode;
        private final boolean enableUniversalAsFallbackForSplits;
        private final Optional<Devices.DeviceSpec> deviceSpec;

        private ApksToGenerate(AppBundle appBundle, BuildApksCommand.ApkBuildMode apkBuildMode, boolean enableUniversalAsFallbackForSplits, Optional<Devices.DeviceSpec> deviceSpec) {
            this.appBundle = appBundle;
            this.apkBuildMode = apkBuildMode;
            this.enableUniversalAsFallbackForSplits = enableUniversalAsFallbackForSplits;
            this.deviceSpec = deviceSpec;
            this.validate();
        }

        private void validate() {
            if (this.appBundle.isApex() && this.apkBuildMode.equals((Object)BuildApksCommand.ApkBuildMode.UNIVERSAL)) {
                throw new CommandExecutionException("APEX bundles do not support universal apks.");
            }
            if (this.deviceSpec.isPresent()) {
                int deviceSdk = this.deviceSpec.get().getSdkVersion();
                Optional<Integer> appMaxSdk = this.appBundle.getBaseModule().getAndroidManifest().getMaxSdkVersion();
                if (this.apkBuildMode.equals((Object)BuildApksCommand.ApkBuildMode.DEFAULT) || this.apkBuildMode.equals((Object)BuildApksCommand.ApkBuildMode.PERSISTENT)) {
                    if (deviceSdk >= 21) {
                        if (!this.generateSplitApks()) {
                            throw new IncompatibleDeviceException("App Bundle targets pre-L devices, but the device has SDK version higher or equal to L.");
                        }
                    } else if (!this.generateStandaloneApks()) {
                        throw new IncompatibleDeviceException("App Bundle targets L+ devices, but the device has SDK version lower than L.");
                    }
                }
                if (appMaxSdk.isPresent() && deviceSdk > appMaxSdk.get()) {
                    throw new IncompatibleDeviceException("Max SDK version of the App Bundle is lower than SDK version of the device");
                }
            }
            Preconditions.checkState(this.generateStandaloneApks() || this.generateSplitApks() || this.generateInstantApks() || this.generateUniversalApk() || this.generateSystemApks(), "No APKs to generate.");
        }

        public boolean generateSplitApks() {
            if (this.appBundle.isApex()) {
                return false;
            }
            if (!this.apkBuildMode.equals((Object)BuildApksCommand.ApkBuildMode.DEFAULT) && !this.apkBuildMode.equals((Object)BuildApksCommand.ApkBuildMode.PERSISTENT)) {
                return false;
            }
            return !BuildApksManager.targetsOnlyPreL(this.appBundle) && this.deviceSpec.map(spec -> spec.getSdkVersion() >= 21).orElse(true) != false;
        }

        public boolean generateStandaloneApks() {
            if (!this.apkBuildMode.equals((Object)BuildApksCommand.ApkBuildMode.DEFAULT) && !this.apkBuildMode.equals((Object)BuildApksCommand.ApkBuildMode.PERSISTENT)) {
                return false;
            }
            if (this.appBundle.isApex()) {
                return true;
            }
            return BuildApksManager.targetsPreL(this.appBundle) && this.deviceSpec.map(spec -> spec.getSdkVersion() < 21).orElse(true) != false;
        }

        public boolean generateInstantApks() {
            if (this.appBundle.isApex()) {
                return false;
            }
            return this.apkBuildMode.equals((Object)BuildApksCommand.ApkBuildMode.DEFAULT) || this.apkBuildMode.equals((Object)BuildApksCommand.ApkBuildMode.INSTANT);
        }

        public boolean generateUniversalApk() {
            if (this.appBundle.isApex()) {
                return false;
            }
            boolean shouldGenerateAsFallback = this.enableUniversalAsFallbackForSplits && this.generateSplitApks() && !this.generateStandaloneApks();
            return this.apkBuildMode.equals((Object)BuildApksCommand.ApkBuildMode.UNIVERSAL) || shouldGenerateAsFallback;
        }

        public boolean generateSystemApks() {
            if (this.appBundle.isApex()) {
                return false;
            }
            return this.apkBuildMode.isAnySystemMode();
        }

        public boolean generateAssetSlices() {
            if (this.appBundle.isApex()) {
                return false;
            }
            return this.apkBuildMode.equals((Object)BuildApksCommand.ApkBuildMode.DEFAULT) || this.apkBuildMode.equals((Object)BuildApksCommand.ApkBuildMode.INSTANT) || this.apkBuildMode.equals((Object)BuildApksCommand.ApkBuildMode.PERSISTENT);
        }
    }
}

