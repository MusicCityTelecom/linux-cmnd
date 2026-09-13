/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.commands;

import com.android.bundle.Commands;
import com.android.bundle.Devices;
import com.android.tools.build.bundletool.commands.AutoValue_InstallApksCommand;
import com.android.tools.build.bundletool.commands.CommandHelp;
import com.android.tools.build.bundletool.commands.ExtractApksCommand;
import com.android.tools.build.bundletool.device.AdbRunner;
import com.android.tools.build.bundletool.device.AdbServer;
import com.android.tools.build.bundletool.device.Device;
import com.android.tools.build.bundletool.device.DeviceAnalyzer;
import com.android.tools.build.bundletool.flags.Flag;
import com.android.tools.build.bundletool.flags.ParsedFlags;
import com.android.tools.build.bundletool.io.TempDirectory;
import com.android.tools.build.bundletool.model.exceptions.CommandExecutionException;
import com.android.tools.build.bundletool.model.utils.DefaultSystemEnvironmentProvider;
import com.android.tools.build.bundletool.model.utils.ResultUtils;
import com.android.tools.build.bundletool.model.utils.SdkToolsLocator;
import com.android.tools.build.bundletool.model.utils.SystemEnvironmentProvider;
import com.android.tools.build.bundletool.model.utils.files.FilePreconditions;
import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.Optional;

@AutoValue
public abstract class InstallApksCommand {
    public static final String COMMAND_NAME = "install-apks";
    private static final Flag<Path> ADB_PATH_FLAG = Flag.path("adb");
    private static final Flag<Path> APKS_ARCHIVE_FILE_FLAG = Flag.path("apks");
    private static final Flag<String> DEVICE_ID_FLAG = Flag.string("device-id");
    private static final Flag<ImmutableSet<String>> MODULES_FLAG = Flag.stringSet("modules");
    private static final Flag<Boolean> ALLOW_DOWNGRADE_FLAG = Flag.booleanFlag("allow-downgrade");
    private static final Flag<Boolean> ALLOW_TEST_ONLY_FLAG = Flag.booleanFlag("allow-test-only");
    private static final String ANDROID_SERIAL_VARIABLE = "ANDROID_SERIAL";
    private static final SystemEnvironmentProvider DEFAULT_PROVIDER = new DefaultSystemEnvironmentProvider();

    public abstract Path getAdbPath();

    public abstract Path getApksArchivePath();

    public abstract Optional<String> getDeviceId();

    public abstract Optional<ImmutableSet<String>> getModules();

    public abstract boolean getAllowDowngrade();

    public abstract boolean getAllowTestOnly();

    abstract AdbServer getAdbServer();

    public static Builder builder() {
        return new AutoValue_InstallApksCommand.Builder().setAllowDowngrade(false).setAllowTestOnly(false);
    }

    public static InstallApksCommand fromFlags(ParsedFlags flags, AdbServer adbServer) {
        return InstallApksCommand.fromFlags(flags, DEFAULT_PROVIDER, adbServer);
    }

    public static InstallApksCommand fromFlags(ParsedFlags flags, SystemEnvironmentProvider systemEnvironmentProvider, AdbServer adbServer) {
        Path apksArchivePath = APKS_ARCHIVE_FILE_FLAG.getRequiredValue(flags);
        Path adbPath = ADB_PATH_FLAG.getValue(flags).orElseGet(() -> new SdkToolsLocator().locateAdb(systemEnvironmentProvider).orElseThrow(() -> new CommandExecutionException("Unable to determine the location of ADB. Please set the --adb flag or define ANDROID_HOME or PATH environment variable.")));
        Optional<String> deviceSerialName = DEVICE_ID_FLAG.getValue(flags);
        if (!deviceSerialName.isPresent()) {
            deviceSerialName = systemEnvironmentProvider.getVariable(ANDROID_SERIAL_VARIABLE);
        }
        Optional<ImmutableSet<String>> modules = MODULES_FLAG.getValue(flags);
        Optional<Boolean> allowDowngrade = ALLOW_DOWNGRADE_FLAG.getValue(flags);
        Optional<Boolean> allowTestOnly = ALLOW_TEST_ONLY_FLAG.getValue(flags);
        flags.checkNoUnknownFlags();
        Builder command = InstallApksCommand.builder().setAdbPath(adbPath).setAdbServer(adbServer).setApksArchivePath(apksArchivePath);
        deviceSerialName.ifPresent(command::setDeviceId);
        modules.ifPresent(command::setModules);
        allowDowngrade.ifPresent(command::setAllowDowngrade);
        allowTestOnly.ifPresent(command::setAllowTestOnly);
        return command.build();
    }

    public void execute() {
        this.validateInput();
        AdbServer adbServer = this.getAdbServer();
        adbServer.init(this.getAdbPath());
        try (TempDirectory tempDirectory = new TempDirectory();){
            Devices.DeviceSpec deviceSpec = new DeviceAnalyzer(adbServer).getDeviceSpec(this.getDeviceId());
            Commands.BuildApksResult toc = ResultUtils.readTableOfContents(this.getApksArchivePath());
            ImmutableList<Path> apksToInstall = this.getApksToInstall(toc, deviceSpec, tempDirectory.getPath());
            ImmutableList<Path> apksToPush = this.getApksToPushToStorage(toc, deviceSpec, tempDirectory.getPath());
            AdbRunner adbRunner = new AdbRunner(adbServer);
            Device.InstallOptions installOptions = Device.InstallOptions.builder().setAllowDowngrade(this.getAllowDowngrade()).setAllowTestOnly(this.getAllowTestOnly()).build();
            if (this.getDeviceId().isPresent()) {
                adbRunner.run(device -> device.installApks(apksToInstall, installOptions), this.getDeviceId().get());
            } else {
                adbRunner.run(device -> device.installApks(apksToInstall, installOptions));
            }
            if (!apksToPush.isEmpty()) {
                this.pushSplits(apksToPush, toc, adbRunner);
            }
        }
    }

    private ImmutableList<Path> getApksToInstall(Commands.BuildApksResult toc, Devices.DeviceSpec deviceSpec, Path output) {
        ExtractApksCommand.Builder extractApksCommand = ExtractApksCommand.builder().setApksArchivePath(this.getApksArchivePath()).setDeviceSpec(deviceSpec);
        if (!Files.isDirectory(this.getApksArchivePath(), new LinkOption[0])) {
            extractApksCommand.setOutputDirectory(output);
        }
        ImmutableSet dynamicAssetModules = toc.getAssetSliceSetList().stream().map(Commands.AssetSliceSet::getAssetModuleMetadata).filter(metadata -> metadata.getDeliveryType() != Commands.DeliveryType.INSTALL_TIME).map(Commands.AssetModuleMetadata::getName).collect(ImmutableSet.toImmutableSet());
        this.getModules().map(modules -> ExtractApksCommand.resolveRequestedModules(modules, toc)).map(modules -> Sets.difference(modules, dynamicAssetModules).immutableCopy()).ifPresent(extractApksCommand::setModules);
        return extractApksCommand.build().execute();
    }

    private ImmutableList<Path> getApksToPushToStorage(Commands.BuildApksResult toc, Devices.DeviceSpec deviceSpec, Path output) {
        if (!toc.getLocalTestingInfo().getEnabled()) {
            return ImmutableList.of();
        }
        ExtractApksCommand.Builder extractApksCommand = ExtractApksCommand.builder().setApksArchivePath(this.getApksArchivePath()).setDeviceSpec(InstallApksCommand.addAllSupportedLanguages(deviceSpec, toc));
        if (!Files.isDirectory(this.getApksArchivePath(), new LinkOption[0])) {
            extractApksCommand.setOutputDirectory(output);
        }
        ImmutableSet installTimeAssetModules = toc.getAssetSliceSetList().stream().map(Commands.AssetSliceSet::getAssetModuleMetadata).filter(metadata -> metadata.getDeliveryType() == Commands.DeliveryType.INSTALL_TIME).map(Commands.AssetModuleMetadata::getName).collect(ImmutableSet.toImmutableSet());
        ImmutableSet<String> allModules = ExtractApksCommand.resolveRequestedModules(ImmutableSet.of("_ALL_"), toc);
        extractApksCommand.setModules(Sets.difference(allModules, installTimeAssetModules).immutableCopy());
        return extractApksCommand.build().execute().stream().filter(apk -> !ResultUtils.getAllBaseMasterSplitPaths(toc).contains(apk.getFileName().toString())).collect(ImmutableList.toImmutableList());
    }

    private void pushSplits(ImmutableList<Path> splits, Commands.BuildApksResult toc, AdbRunner adbRunner) {
        String packageName = toc.getPackageName();
        if (packageName.isEmpty()) {
            throw new CommandExecutionException("Unable to determine the package name of the base APK. If your APK set was produced using an older version of bundletool, please regenerate it.");
        }
        Device.PushOptions.Builder pushOptions = Device.PushOptions.builder().setDestinationPath(toc.getLocalTestingInfo().getLocalTestingPath()).setClearDestinationPath(true).setPackageName(packageName);
        if (this.getDeviceId().isPresent()) {
            adbRunner.run(device -> device.pushApks(splits, pushOptions.build()), this.getDeviceId().get());
        } else {
            adbRunner.run(device -> device.pushApks(splits, pushOptions.build()));
        }
    }

    private static Devices.DeviceSpec addAllSupportedLanguages(Devices.DeviceSpec deviceSpec, Commands.BuildApksResult toc) {
        ImmutableSet<String> targetedLanguages = ResultUtils.getAllTargetedLanguages(toc);
        return deviceSpec.toBuilder().addAllSupportedLocales(targetedLanguages).build();
    }

    private void validateInput() {
        if (Files.isDirectory(this.getApksArchivePath(), new LinkOption[0])) {
            FilePreconditions.checkDirectoryExists(this.getApksArchivePath());
        } else {
            FilePreconditions.checkFileExistsAndReadable(this.getApksArchivePath());
        }
        FilePreconditions.checkFileExistsAndExecutable(this.getAdbPath());
    }

    public static CommandHelp help() {
        return CommandHelp.builder().setCommandName(COMMAND_NAME).setCommandDescription(CommandHelp.CommandDescription.builder().setShortDescription("Installs APKs extracted from an APK Set to a connected device. Replaces already installed package.").addAdditionalParagraph("This will extract from the APK Set archive and install only the APKs that would be served to that device. If the app is not compatible with the device or if the APK Set archive was generated for a different type of device, this command will fail.").build()).addFlag(CommandHelp.FlagDescription.builder().setFlagName(ADB_PATH_FLAG.getName()).setExampleValue("path/to/adb").setOptional(true).setDescription("Path to the adb utility. If absent, an attempt will be made to locate it if the %s or %s environment variable is set.", "ANDROID_HOME", "PATH").build()).addFlag(CommandHelp.FlagDescription.builder().setFlagName(APKS_ARCHIVE_FILE_FLAG.getName()).setExampleValue("archive.apks").setDescription("Path to the archive file generated by the '%s' command.", "build-apks").build()).addFlag(CommandHelp.FlagDescription.builder().setFlagName(DEVICE_ID_FLAG.getName()).setExampleValue("device-serial-name").setOptional(true).setDescription("Device serial name. If absent, this uses the %s environment variable. Either this flag or the environment variable is required when more than one device or emulator is connected.", ANDROID_SERIAL_VARIABLE).build()).addFlag(CommandHelp.FlagDescription.builder().setFlagName(ALLOW_DOWNGRADE_FLAG.getName()).setOptional(true).setDescription("If set, allows APKs to be installed on the device even if the app is already installed with a lower version code.").build()).addFlag(CommandHelp.FlagDescription.builder().setFlagName(MODULES_FLAG.getName()).setExampleValue("base,module1,module2").setOptional(true).setDescription("List of modules to be installed, or \"%s\" for all modules. Defaults to modules installed during the first install, i.e. not on-demand. Note that the dependent modules will also be extracted. The value of this flag is ignored if the device receives a standalone APK.", "_ALL_").build()).addFlag(CommandHelp.FlagDescription.builder().setFlagName(ALLOW_TEST_ONLY_FLAG.getName()).setOptional(true).setDescription("If set, apps with 'android:testOnly=true' set in their manifest can also be deployed").build()).build();
    }

    @AutoValue.Builder
    public static abstract class Builder {
        public abstract Builder setAdbPath(Path var1);

        public abstract Builder setApksArchivePath(Path var1);

        public abstract Builder setDeviceId(String var1);

        public abstract Builder setModules(ImmutableSet<String> var1);

        public abstract Builder setAllowDowngrade(boolean var1);

        public abstract Builder setAdbServer(AdbServer var1);

        public abstract Builder setAllowTestOnly(boolean var1);

        public abstract InstallApksCommand build();
    }
}

