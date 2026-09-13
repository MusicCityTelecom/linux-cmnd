/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.commands;

import com.android.bundle.Devices;
import com.android.tools.build.bundletool.commands.AutoValue_BuildApksCommand;
import com.android.tools.build.bundletool.commands.BuildApksManager;
import com.android.tools.build.bundletool.commands.CommandHelp;
import com.android.tools.build.bundletool.commands.DebugKeystoreUtils;
import com.android.tools.build.bundletool.device.AdbServer;
import com.android.tools.build.bundletool.device.DeviceSpecParser;
import com.android.tools.build.bundletool.flags.Flag;
import com.android.tools.build.bundletool.flags.ParsedFlags;
import com.android.tools.build.bundletool.io.TempDirectory;
import com.android.tools.build.bundletool.model.Aapt2Command;
import com.android.tools.build.bundletool.model.ApkListener;
import com.android.tools.build.bundletool.model.ApkModifier;
import com.android.tools.build.bundletool.model.OptimizationDimension;
import com.android.tools.build.bundletool.model.Password;
import com.android.tools.build.bundletool.model.SigningConfiguration;
import com.android.tools.build.bundletool.model.SourceStamp;
import com.android.tools.build.bundletool.model.exceptions.CommandExecutionException;
import com.android.tools.build.bundletool.model.exceptions.ValidationException;
import com.android.tools.build.bundletool.model.utils.DefaultSystemEnvironmentProvider;
import com.android.tools.build.bundletool.model.utils.SdkToolsLocator;
import com.android.tools.build.bundletool.model.utils.SystemEnvironmentProvider;
import com.android.tools.build.bundletool.validation.SubValidator;
import com.google.auto.value.AutoValue;
import com.google.common.base.Ascii;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.io.MoreFiles;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import java.io.PrintStream;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@AutoValue
public abstract class BuildApksCommand {
    private static final int DEFAULT_THREAD_POOL_SIZE = 4;
    public static final String COMMAND_NAME = "build-apks";
    private static final Flag<Path> BUNDLE_LOCATION_FLAG = Flag.path("bundle");
    private static final Flag<Path> OUTPUT_FILE_FLAG = Flag.path("output");
    private static final Flag<Boolean> OVERWRITE_OUTPUT_FLAG = Flag.booleanFlag("overwrite");
    private static final Flag<ImmutableSet<OptimizationDimension>> OPTIMIZE_FOR_FLAG = Flag.enumSet("optimize-for", OptimizationDimension.class);
    private static final Flag<Path> AAPT2_PATH_FLAG = Flag.path("aapt2");
    private static final Flag<Integer> MAX_THREADS_FLAG = Flag.positiveInteger("max-threads");
    private static final Flag<ApkBuildMode> BUILD_MODE_FLAG = Flag.enumFlag("mode", ApkBuildMode.class);
    private static final Flag<Boolean> LOCAL_TESTING_MODE_FLAG = Flag.booleanFlag("local-testing");
    private static final Flag<Path> ADB_PATH_FLAG = Flag.path("adb");
    private static final Flag<Boolean> CONNECTED_DEVICE_FLAG = Flag.booleanFlag("connected-device");
    private static final Flag<String> DEVICE_ID_FLAG = Flag.string("device-id");
    private static final String ANDROID_SERIAL_VARIABLE = "ANDROID_SERIAL";
    private static final Flag<ImmutableSet<String>> MODULES_FLAG = Flag.stringSet("modules");
    private static final Flag<Path> DEVICE_SPEC_FLAG = Flag.path("device-spec");
    private static final Flag<Path> KEYSTORE_FLAG = Flag.path("ks");
    private static final Flag<String> KEY_ALIAS_FLAG = Flag.string("ks-key-alias");
    private static final Flag<Password> KEYSTORE_PASSWORD_FLAG = Flag.password("ks-pass");
    private static final Flag<Password> KEY_PASSWORD_FLAG = Flag.password("key-pass");
    private static final Flag<Boolean> CREATE_STAMP_FLAG = Flag.booleanFlag("create-stamp");
    private static final Flag<Path> STAMP_KEYSTORE_FLAG = Flag.path("stamp-ks");
    private static final Flag<Password> STAMP_KEYSTORE_PASSWORD_FLAG = Flag.password("stamp-ks-pass");
    private static final Flag<String> STAMP_KEY_ALIAS_FLAG = Flag.string("stamp-key-alias");
    private static final Flag<Password> STAMP_KEY_PASSWORD_FLAG = Flag.password("stamp-key-pass");
    private static final Flag<String> STAMP_SOURCE_FLAG = Flag.string("stamp-source");
    private static final String APK_SET_ARCHIVE_EXTENSION = "apks";
    private static final SystemEnvironmentProvider DEFAULT_PROVIDER = new DefaultSystemEnvironmentProvider();

    public abstract Path getBundlePath();

    public abstract Path getOutputFile();

    public abstract boolean getOverwriteOutput();

    public abstract ImmutableSet<OptimizationDimension> getOptimizationDimensions();

    public abstract ImmutableSet<String> getModules();

    public abstract Optional<Devices.DeviceSpec> getDeviceSpec();

    public abstract boolean getGenerateOnlyForConnectedDevice();

    public abstract Optional<String> getDeviceId();

    abstract Optional<AdbServer> getAdbServer();

    public abstract Optional<Path> getAdbPath();

    public abstract ApkBuildMode getApkBuildMode();

    public abstract boolean getLocalTestingMode();

    public abstract Optional<Aapt2Command> getAapt2Command();

    public abstract Optional<SigningConfiguration> getSigningConfiguration();

    ListeningExecutorService getExecutorService() {
        return this.getExecutorServiceInternal();
    }

    abstract ListeningExecutorService getExecutorServiceInternal();

    abstract boolean isExecutorServiceCreatedByBundleTool();

    public abstract boolean getCreateApkSetArchive();

    public abstract Optional<ApkListener> getApkListener();

    public abstract Optional<ApkModifier> getApkModifier();

    public abstract ImmutableList<SubValidator> getExtraValidators();

    public abstract Optional<Integer> getFirstVariantNumber();

    public abstract Optional<PrintStream> getOutputPrintStream();

    public abstract Optional<SourceStamp> getSourceStamp();

    public static Builder builder() {
        return new AutoValue_BuildApksCommand.Builder().setOverwriteOutput(false).setApkBuildMode(ApkBuildMode.DEFAULT).setLocalTestingMode(false).setGenerateOnlyForConnectedDevice(false).setCreateApkSetArchive(true).setOptimizationDimensions(ImmutableSet.of()).setModules(ImmutableSet.of()).setExtraValidators(ImmutableList.of());
    }

    public static BuildApksCommand fromFlags(ParsedFlags flags, AdbServer adbServer) {
        return BuildApksCommand.fromFlags(flags, System.out, DEFAULT_PROVIDER, adbServer);
    }

    static BuildApksCommand fromFlags(ParsedFlags flags, PrintStream out, SystemEnvironmentProvider systemEnvironmentProvider, AdbServer adbServer) {
        ApkBuildMode apkBuildMode;
        boolean supportsPartialDeviceSpecs;
        Builder buildApksCommand = BuildApksCommand.builder().setBundlePath(BUNDLE_LOCATION_FLAG.getRequiredValue(flags)).setOutputFile(OUTPUT_FILE_FLAG.getRequiredValue(flags)).setOutputPrintStream(out);
        OVERWRITE_OUTPUT_FLAG.getValue(flags).ifPresent(buildApksCommand::setOverwriteOutput);
        AAPT2_PATH_FLAG.getValue(flags).ifPresent(aapt2Path -> buildApksCommand.setAapt2Command(Aapt2Command.createFromExecutablePath(aapt2Path)));
        BUILD_MODE_FLAG.getValue(flags).ifPresent(buildApksCommand::setApkBuildMode);
        LOCAL_TESTING_MODE_FLAG.getValue(flags).ifPresent(buildApksCommand::setLocalTestingMode);
        MAX_THREADS_FLAG.getValue(flags).ifPresent(maxThreads -> buildApksCommand.setExecutorService(BuildApksCommand.createInternalExecutorService(maxThreads)).setExecutorServiceCreatedByBundleTool(true));
        OPTIMIZE_FOR_FLAG.getValue(flags).ifPresent(buildApksCommand::setOptimizationDimensions);
        BuildApksCommand.populateSigningConfigurationFromFlags(buildApksCommand, flags, out, systemEnvironmentProvider);
        BuildApksCommand.populateSourceStampFromFlags(buildApksCommand, flags, out, systemEnvironmentProvider);
        boolean connectedDeviceMode = CONNECTED_DEVICE_FLAG.getValue(flags).orElse(false);
        CONNECTED_DEVICE_FLAG.getValue(flags).ifPresent(buildApksCommand::setGenerateOnlyForConnectedDevice);
        Optional<String> deviceSerialName = DEVICE_ID_FLAG.getValue(flags);
        if (connectedDeviceMode && !deviceSerialName.isPresent()) {
            deviceSerialName = systemEnvironmentProvider.getVariable(ANDROID_SERIAL_VARIABLE);
        }
        deviceSerialName.ifPresent(buildApksCommand::setDeviceId);
        Optional<Path> adbPathFromFlag = ADB_PATH_FLAG.getValue(flags);
        if (connectedDeviceMode) {
            Path adbPath = adbPathFromFlag.orElseGet(() -> new SdkToolsLocator().locateAdb(systemEnvironmentProvider).orElseThrow(() -> new CommandExecutionException("Unable to determine the location of ADB. Please set the --adb flag or define ANDROID_HOME or PATH environment variable.")));
            buildApksCommand.setAdbPath(adbPath).setAdbServer(adbServer);
        }
        Function deviceSpecParser = (supportsPartialDeviceSpecs = (apkBuildMode = BUILD_MODE_FLAG.getValue(flags).orElse(ApkBuildMode.DEFAULT)).isAnySystemMode()) ? DeviceSpecParser::parsePartialDeviceSpec : DeviceSpecParser::parseDeviceSpec;
        DEVICE_SPEC_FLAG.getValue(flags).map(deviceSpecParser).ifPresent(buildApksCommand::setDeviceSpec);
        MODULES_FLAG.getValue(flags).ifPresent(buildApksCommand::setModules);
        flags.checkNoUnknownFlags();
        return buildApksCommand.build();
    }

    public Path execute() {
        try (TempDirectory tempDir = new TempDirectory();){
            Aapt2Command aapt2 = this.getAapt2Command().orElseGet(() -> BuildApksCommand.extractAapt2FromJar(tempDir.getPath()));
            Path path = new BuildApksManager(this, aapt2, tempDir.getPath()).execute();
            return path;
        }
    }

    private static Aapt2Command extractAapt2FromJar(Path tempDir) {
        return new SdkToolsLocator().extractAapt2(tempDir).map(Aapt2Command::createFromExecutablePath).orElseThrow(() -> new CommandExecutionException("Could not extract aapt2: consider updating bundletool to a more recent version or providing the path to aapt2 using the flag --aapt2."));
    }

    private static ListeningExecutorService createInternalExecutorService(int maxThreads) {
        Preconditions.checkArgument(maxThreads >= 0, "The maxThreads must be positive, got %s.", maxThreads);
        return MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(maxThreads));
    }

    public static CommandHelp help() {
        return CommandHelp.builder().setCommandName(COMMAND_NAME).setCommandDescription(CommandHelp.CommandDescription.builder().setShortDescription("Generates an APK Set archive containing either all possible split APKs and standalone APKs or APKs optimized for the connected device (see %s flag).", CONNECTED_DEVICE_FLAG).build()).addFlag(CommandHelp.FlagDescription.builder().setFlagName(BUNDLE_LOCATION_FLAG.getName()).setExampleValue("bundle.aab").setDescription("Path to the Android App Bundle to generate APKs from.").build()).addFlag(CommandHelp.FlagDescription.builder().setFlagName(OUTPUT_FILE_FLAG.getName()).setExampleValue("output.apks").setDescription("Path to where the APK Set archive should be created.").build()).addFlag(CommandHelp.FlagDescription.builder().setFlagName(OVERWRITE_OUTPUT_FLAG.getName()).setOptional(true).setDescription("If set, any previous existing output will be overwritten.").build()).addFlag(CommandHelp.FlagDescription.builder().setFlagName(AAPT2_PATH_FLAG.getName()).setExampleValue("path/to/aapt2").setOptional(true).setDescription("Path to the aapt2 binary to use.").build()).addFlag(CommandHelp.FlagDescription.builder().setFlagName(BUILD_MODE_FLAG.getName()).setExampleValue(BuildApksCommand.joinFlagOptions(ApkBuildMode.values())).setOptional(true).setDescription("Specifies which mode to run '%s' command against. Acceptable values are '%s'. If not set or set to '%s' we generate split, standalone and instant APKs. If set to '%s' we generate universal APK. If set to '%s' we generate APKs for system image. If set to '%s' we generate compressed APK and an additional uncompressed stub APK (containing only Android manifest) for the system image.", COMMAND_NAME, BuildApksCommand.joinFlagOptions(ApkBuildMode.values()), ApkBuildMode.DEFAULT.getLowerCaseName(), ApkBuildMode.UNIVERSAL.getLowerCaseName(), ApkBuildMode.SYSTEM.getLowerCaseName(), ApkBuildMode.SYSTEM_COMPRESSED.getLowerCaseName()).build()).addFlag(CommandHelp.FlagDescription.builder().setFlagName(MAX_THREADS_FLAG.getName()).setExampleValue("num-threads").setOptional(true).setDescription("Sets the maximum number of threads to use (default: %d).", 4).build()).addFlag(CommandHelp.FlagDescription.builder().setFlagName(OPTIMIZE_FOR_FLAG.getName()).setExampleValue(BuildApksCommand.joinFlagOptions(OptimizationDimension.values())).setOptional(true).setDescription("If set, will generate APKs with optimizations for the given dimensions. Acceptable values are '%s'. This flag should be only be set with --%s=%s flag.", BuildApksCommand.joinFlagOptions(OptimizationDimension.values()), BUILD_MODE_FLAG.getName(), ApkBuildMode.DEFAULT.getLowerCaseName()).build()).addFlag(CommandHelp.FlagDescription.builder().setFlagName(KEYSTORE_FLAG.getName()).setExampleValue("path/to/keystore").setOptional(true).setDescription("Path to the keystore that should be used to sign the generated APKs. If not set, the default debug keystore will be used if it exists. If not found the APKs will not be signed. If set, the flag '%s' must also be set.", KEY_ALIAS_FLAG).build()).addFlag(CommandHelp.FlagDescription.builder().setFlagName(KEY_ALIAS_FLAG.getName()).setExampleValue("key-alias").setOptional(true).setDescription("Alias of the key to use in the keystore to sign the generated APKs.").build()).addFlag(CommandHelp.FlagDescription.builder().setFlagName(KEYSTORE_PASSWORD_FLAG.getName()).setExampleValue("[pass|file]:value").setOptional(true).setDescription("Password of the keystore to use to sign the generated APKs. If provided, must be prefixed with either 'pass:' (if the password is passed in clear text, e.g. 'pass:qwerty') or 'file:' (if the password is the first line of a file, e.g. 'file:/tmp/myPassword.txt'). If this flag is not set, the password will be requested on the prompt.").build()).addFlag(CommandHelp.FlagDescription.builder().setFlagName(KEY_PASSWORD_FLAG.getName()).setExampleValue("key-password").setOptional(true).setDescription("Password of the key in the keystore to use to sign the generated APKs. If provided, must be prefixed with either 'pass:' (if the password is passed in clear text, e.g. 'pass:qwerty') or 'file:' (if the password is the first line of a file, e.g. 'file:/tmp/myPassword.txt'). If this flag is not set, the keystore password will be tried. If that fails, the password will be requested on the prompt.").build()).addFlag(CommandHelp.FlagDescription.builder().setFlagName(CONNECTED_DEVICE_FLAG.getName()).setOptional(true).setDescription("If set, will generate APK Set optimized for the connected device. The generated APK Set will only be installable on that specific class of devices. This flag should be only be set with --%s=%s flag.", BUILD_MODE_FLAG.getName(), ApkBuildMode.DEFAULT.getLowerCaseName()).build()).addFlag(CommandHelp.FlagDescription.builder().setFlagName(ADB_PATH_FLAG.getName()).setExampleValue("path/to/adb").setOptional(true).setDescription("Path to the adb utility. If absent, an attempt will be made to locate it if the %s or %s environment variable is set. Used only if %s flag is set.", "ANDROID_HOME", "PATH", CONNECTED_DEVICE_FLAG).build()).addFlag(CommandHelp.FlagDescription.builder().setFlagName(DEVICE_ID_FLAG.getName()).setExampleValue("device-serial-name").setOptional(true).setDescription("Device serial name. If absent, this uses the %s environment variable. Either this flag or the environment variable is required when more than one device or emulator is connected. Used only if %s flag is set.", ANDROID_SERIAL_VARIABLE, CONNECTED_DEVICE_FLAG).build()).addFlag(CommandHelp.FlagDescription.builder().setFlagName(DEVICE_SPEC_FLAG.getName()).setExampleValue("device-spec.json").setOptional(true).setDescription("Path to the device spec file generated by the '%s' command. If present, it will generate an APK Set optimized for the specified device spec. This flag should be only be set with --%s=%s flag.", "get-device-spec", BUILD_MODE_FLAG.getName(), ApkBuildMode.DEFAULT.getLowerCaseName()).build()).addFlag(CommandHelp.FlagDescription.builder().setFlagName(MODULES_FLAG.getName()).setExampleValue("base,module1,module2").setOptional(true).setDescription("List of module names to include in the generated APK Set in modes %s, %s and %s.", ApkBuildMode.UNIVERSAL.getLowerCaseName(), ApkBuildMode.SYSTEM.getLowerCaseName(), ApkBuildMode.SYSTEM_COMPRESSED.getLowerCaseName()).build()).addFlag(CommandHelp.FlagDescription.builder().setFlagName(LOCAL_TESTING_MODE_FLAG.getName()).setOptional(true).setDescription("If enabled, the APK set will be built in local testing mode, which includes additional metadata in the output. When `bundletool %s` is later used to install APKs from this set on a device, it will additionally push all dynamic module splits and asset packs to a location that can be accessed by the Play Core API.", "install-apks").build()).addFlag(CommandHelp.FlagDescription.builder().setFlagName(CREATE_STAMP_FLAG.getName()).setOptional(true).setDescription("If set, a stamp will be generated and embedded in the generated APKs.").build()).addFlag(CommandHelp.FlagDescription.builder().setFlagName(STAMP_KEYSTORE_FLAG.getName()).setExampleValue("path/to/keystore").setOptional(true).setDescription("Path to the stamp keystore that should be used to sign the APK contents hash. If not set, the '%s' keystore will be tried if present. Otherwise, the default debug keystore will be used if it exists. If a default debug keystore is not found, the stamp will fail to get generated. If set, the flag '%s' must also be set.", KEYSTORE_FLAG, STAMP_KEY_ALIAS_FLAG).build()).addFlag(CommandHelp.FlagDescription.builder().setFlagName(STAMP_KEYSTORE_PASSWORD_FLAG.getName()).setExampleValue("[pass|file]:value").setOptional(true).setDescription("Password of the stamp keystore to use to sign the APK contents hash. If provided, must be prefixed with either 'pass:' (if the password is passed in clear text, e.g. 'pass:qwerty') or 'file:' (if the password is the first line of a file, e.g. 'file:/tmp/myPassword.txt'). If this flag is not set, the password will be requested on the prompt.").build()).addFlag(CommandHelp.FlagDescription.builder().setFlagName(STAMP_KEY_ALIAS_FLAG.getName()).setExampleValue("stamp-key-alias").setOptional(true).setDescription("Alias of the stamp key to use in the keystore to sign the APK contents hash. If not set, the '%s' key alias will be tried if present.", KEY_ALIAS_FLAG).build()).addFlag(CommandHelp.FlagDescription.builder().setFlagName(STAMP_KEY_PASSWORD_FLAG.getName()).setExampleValue("stamp-key-password").setOptional(true).setDescription("Password of the stamp key in the keystore to use to sign the APK contents hash. if provided, must be prefixed with either 'pass:' (if the password is passed in clear text, e.g. 'pass:qwerty') or 'file:' (if the password is the first line of a file, e.g. 'file:/tmp/myPassword.txt'). If this flag is not set, the keystore password will be tried. If that fails, the password will be requested on the prompt.").build()).addFlag(CommandHelp.FlagDescription.builder().setFlagName(STAMP_SOURCE_FLAG.getName()).setExampleValue("stamp-source").setOptional(true).setDescription("Name of source generating the stamp. For stores, it is their package names. For locally generated stamp, it is 'local'.").build()).build();
    }

    private static String joinFlagOptions(Enum<?> ... flagOptions) {
        return Arrays.stream(flagOptions).map(Enum::name).map(String::toLowerCase).collect(Collectors.joining("|"));
    }

    private static void populateSigningConfigurationFromFlags(Builder buildApksCommand, ParsedFlags flags, PrintStream out, SystemEnvironmentProvider systemEnvironmentProvider) {
        Optional<Path> keystorePath = KEYSTORE_FLAG.getValue(flags);
        Optional<String> keyAlias = KEY_ALIAS_FLAG.getValue(flags);
        Optional<Password> keystorePassword = KEYSTORE_PASSWORD_FLAG.getValue(flags);
        Optional<Password> keyPassword = KEY_PASSWORD_FLAG.getValue(flags);
        if (keystorePath.isPresent() && keyAlias.isPresent()) {
            buildApksCommand.setSigningConfiguration(SigningConfiguration.extractFromKeystore(keystorePath.get(), keyAlias.get(), keystorePassword, keyPassword));
        } else {
            if (keystorePath.isPresent() && !keyAlias.isPresent()) {
                throw new CommandExecutionException("Flag --ks-key-alias is required when --ks is set.");
            }
            if (!keystorePath.isPresent() && keyAlias.isPresent()) {
                throw new CommandExecutionException("Flag --ks is required when --ks-key-alias is set.");
            }
            Optional<SigningConfiguration> debugConfig = DebugKeystoreUtils.getDebugSigningConfiguration(systemEnvironmentProvider);
            if (debugConfig.isPresent()) {
                out.printf("INFO: The APKs will be signed with the debug keystore found at '%s'.%n", DebugKeystoreUtils.DEBUG_KEYSTORE_CACHE.getUnchecked(systemEnvironmentProvider).get());
                buildApksCommand.setSigningConfiguration(debugConfig.get());
            } else {
                out.println("WARNING: The APKs won't be signed and thus not installable unless you also pass a keystore via the flag --ks. See the command help for more information.");
            }
        }
    }

    private static void populateSourceStampFromFlags(Builder buildApksCommand, ParsedFlags flags, PrintStream out, SystemEnvironmentProvider systemEnvironmentProvider) {
        boolean createStamp = CREATE_STAMP_FLAG.getValue(flags).orElse(false);
        Optional<String> stampSource = STAMP_SOURCE_FLAG.getValue(flags);
        if (!createStamp) {
            return;
        }
        SourceStamp.Builder sourceStamp = SourceStamp.builder();
        sourceStamp.setSigningConfiguration(BuildApksCommand.getStampSigningConfiguration(flags, out, systemEnvironmentProvider));
        stampSource.ifPresent(sourceStamp::setSource);
        buildApksCommand.setSourceStamp(sourceStamp.build());
    }

    private static SigningConfiguration getStampSigningConfiguration(ParsedFlags flags, PrintStream out, SystemEnvironmentProvider systemEnvironmentProvider) {
        Optional<Path> signingKeystorePath = KEYSTORE_FLAG.getValue(flags);
        Optional<Password> signingKeystorePassword = KEYSTORE_PASSWORD_FLAG.getValue(flags);
        Optional<String> signingKeyAlias = KEY_ALIAS_FLAG.getValue(flags);
        Optional<Password> signingKeyPassword = KEY_PASSWORD_FLAG.getValue(flags);
        Optional<Path> stampKeystorePath = STAMP_KEYSTORE_FLAG.getValue(flags);
        Optional<Password> stampKeystorePassword = STAMP_KEYSTORE_PASSWORD_FLAG.getValue(flags);
        Optional<String> stampKeyAlias = STAMP_KEY_ALIAS_FLAG.getValue(flags);
        Optional<Password> stampKeyPassword = STAMP_KEY_PASSWORD_FLAG.getValue(flags);
        Path keystorePath = null;
        Optional<Password> keystorePassword = Optional.empty();
        if (stampKeystorePath.isPresent()) {
            keystorePath = stampKeystorePath.get();
            keystorePassword = stampKeystorePassword;
        } else if (signingKeystorePath.isPresent()) {
            keystorePath = signingKeystorePath.get();
            keystorePassword = signingKeystorePassword;
        }
        if (keystorePath == null) {
            Optional<SigningConfiguration> debugConfig = DebugKeystoreUtils.getDebugSigningConfiguration(systemEnvironmentProvider);
            if (debugConfig.isPresent()) {
                out.printf("INFO: The stamp will be signed with the debug keystore found at '%s'.%n", DebugKeystoreUtils.DEBUG_KEYSTORE_CACHE.getUnchecked(systemEnvironmentProvider).get());
                return debugConfig.get();
            }
            throw new CommandExecutionException("No key was found to sign the stamp.");
        }
        String keyAlias = null;
        Optional<Password> keyPassword = Optional.empty();
        if (stampKeyAlias.isPresent()) {
            keyAlias = stampKeyAlias.get();
            keyPassword = stampKeyPassword;
        } else if (signingKeyAlias.isPresent()) {
            keyAlias = signingKeyAlias.get();
            keyPassword = signingKeyPassword;
        }
        if (keyAlias == null) {
            throw new CommandExecutionException("Flag --stamp-key-alias or --ks-key-alias are required when --stamp-ks or --ks are set.");
        }
        return SigningConfiguration.extractFromKeystore(keystorePath, keyAlias, keystorePassword, keyPassword);
    }

    @AutoValue.Builder
    public static abstract class Builder {
        public abstract Builder setBundlePath(Path var1);

        public abstract Builder setOutputFile(Path var1);

        public abstract Builder setOverwriteOutput(boolean var1);

        @Deprecated
        public abstract Builder setOptimizationDimensions(ImmutableSet<OptimizationDimension> var1);

        public abstract Builder setApkBuildMode(ApkBuildMode var1);

        public abstract Builder setLocalTestingMode(boolean var1);

        public abstract Builder setGenerateOnlyForConnectedDevice(boolean var1);

        public abstract Builder setModules(ImmutableSet<String> var1);

        public abstract Builder setDeviceSpec(Devices.DeviceSpec var1);

        public Builder setDeviceSpec(Path deviceSpecFile) {
            return this.setDeviceSpec(DeviceSpecParser.parsePartialDeviceSpec(deviceSpecFile));
        }

        public abstract Builder setDeviceId(String var1);

        public abstract Builder setAdbPath(Path var1);

        public abstract Builder setAdbServer(AdbServer var1);

        public abstract Builder setAapt2Command(Aapt2Command var1);

        public abstract Builder setSigningConfiguration(SigningConfiguration var1);

        public Builder setExecutorService(ListeningExecutorService executorService) {
            this.setExecutorServiceInternal(executorService);
            this.setExecutorServiceCreatedByBundleTool(false);
            return this;
        }

        abstract Builder setExecutorServiceInternal(ListeningExecutorService var1);

        abstract Optional<ListeningExecutorService> getExecutorServiceInternal();

        abstract Builder setExecutorServiceCreatedByBundleTool(boolean var1);

        public abstract Builder setCreateApkSetArchive(boolean var1);

        public abstract Builder setApkListener(ApkListener var1);

        public abstract Builder setApkModifier(ApkModifier var1);

        public abstract Builder setExtraValidators(ImmutableList<SubValidator> var1);

        public abstract Builder setFirstVariantNumber(int var1);

        public abstract Builder setOutputPrintStream(PrintStream var1);

        public abstract Builder setSourceStamp(SourceStamp var1);

        abstract BuildApksCommand autoBuild();

        public BuildApksCommand build() {
            BuildApksCommand command;
            if (!this.getExecutorServiceInternal().isPresent()) {
                this.setExecutorServiceInternal(BuildApksCommand.createInternalExecutorService(4));
                this.setExecutorServiceCreatedByBundleTool(true);
            }
            if (!(command = this.autoBuild()).getOptimizationDimensions().isEmpty() && !command.getApkBuildMode().equals((Object)ApkBuildMode.DEFAULT)) {
                throw new ValidationException(String.format("Optimization dimension can be only set when running with '%s' mode flag.", ApkBuildMode.DEFAULT.getLowerCaseName()));
            }
            if (command.getGenerateOnlyForConnectedDevice() && !command.getApkBuildMode().equals((Object)ApkBuildMode.DEFAULT)) {
                throw new ValidationException(String.format("Optimizing for connected device only possible when running with '%s' mode flag.", ApkBuildMode.DEFAULT.getLowerCaseName()));
            }
            if (command.getDeviceSpec().isPresent()) {
                boolean supportsPartialDeviceSpecs = command.getApkBuildMode().isAnySystemMode();
                DeviceSpecParser.validateDeviceSpec(command.getDeviceSpec().get(), supportsPartialDeviceSpecs);
                switch (command.getApkBuildMode()) {
                    case UNIVERSAL: {
                        throw new ValidationException(String.format("Optimizing for device spec not possible when running with '%s' mode flag.", ApkBuildMode.UNIVERSAL.getLowerCaseName()));
                    }
                    case SYSTEM: 
                    case SYSTEM_COMPRESSED: {
                        Devices.DeviceSpec deviceSpec = command.getDeviceSpec().get();
                        if (deviceSpec.getScreenDensity() != 0 && !deviceSpec.getSupportedAbisList().isEmpty()) break;
                        throw new ValidationException(String.format("Device spec must have screen density and ABIs set when running with '%s' or '%s' mode flag. ", ApkBuildMode.SYSTEM.getLowerCaseName(), ApkBuildMode.SYSTEM_COMPRESSED.getLowerCaseName()));
                    }
                }
            } else if (command.getApkBuildMode().isAnySystemMode()) {
                throw ValidationException.builder().withMessage("Device spec must always be set when running with '%s' or '%s' mode flag.", ApkBuildMode.SYSTEM.getLowerCaseName(), ApkBuildMode.SYSTEM_COMPRESSED.getLowerCaseName()).build();
            }
            if (command.getGenerateOnlyForConnectedDevice() && command.getDeviceSpec().isPresent()) {
                throw new ValidationException("Cannot optimize for the device spec and connected device at the same time.");
            }
            if (command.getDeviceId().isPresent() && !command.getGenerateOnlyForConnectedDevice()) {
                throw new ValidationException("Setting --device-id requires using the --connected-device flag.");
            }
            if (command.getCreateApkSetArchive() && !BuildApksCommand.APK_SET_ARCHIVE_EXTENSION.equals(MoreFiles.getFileExtension(command.getOutputFile()))) {
                throw ValidationException.builder().withMessage("Flag --output should be the path where to generate the APK Set. Its extension must be '.apks'.").build();
            }
            if (!(command.getModules().isEmpty() || command.getApkBuildMode().isAnySystemMode() || command.getApkBuildMode().equals((Object)ApkBuildMode.UNIVERSAL))) {
                throw ValidationException.builder().withMessage("Modules can be only set when running with '%s', '%s' or '%s' mode flag.", ApkBuildMode.UNIVERSAL.getLowerCaseName(), ApkBuildMode.SYSTEM.getLowerCaseName(), ApkBuildMode.SYSTEM_COMPRESSED.getLowerCaseName()).build();
            }
            return command;
        }
    }

    public static enum ApkBuildMode {
        DEFAULT,
        UNIVERSAL,
        SYSTEM,
        SYSTEM_COMPRESSED,
        PERSISTENT,
        INSTANT;


        public final String getLowerCaseName() {
            return Ascii.toLowerCase(this.name());
        }

        public final boolean isAnySystemMode() {
            return this.equals((Object)SYSTEM) || this.equals((Object)SYSTEM_COMPRESSED);
        }
    }
}

