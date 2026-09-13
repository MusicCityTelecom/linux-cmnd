/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.commands;

import com.android.bundle.Devices;
import com.android.tools.build.bundletool.commands.AutoValue_GetDeviceSpecCommand;
import com.android.tools.build.bundletool.commands.CommandHelp;
import com.android.tools.build.bundletool.device.AdbServer;
import com.android.tools.build.bundletool.device.DeviceAnalyzer;
import com.android.tools.build.bundletool.flags.Flag;
import com.android.tools.build.bundletool.flags.ParsedFlags;
import com.android.tools.build.bundletool.model.exceptions.CommandExecutionException;
import com.android.tools.build.bundletool.model.exceptions.ValidationException;
import com.android.tools.build.bundletool.model.utils.DefaultSystemEnvironmentProvider;
import com.android.tools.build.bundletool.model.utils.SdkToolsLocator;
import com.android.tools.build.bundletool.model.utils.SystemEnvironmentProvider;
import com.android.tools.build.bundletool.model.utils.files.FilePreconditions;
import com.google.auto.value.AutoValue;
import com.google.common.io.MoreFiles;
import com.google.protobuf.util.JsonFormat;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.util.Optional;
import java.util.logging.Logger;

@AutoValue
public abstract class GetDeviceSpecCommand {
    private static final Logger logger = Logger.getLogger(GetDeviceSpecCommand.class.getName());
    public static final String COMMAND_NAME = "get-device-spec";
    private static final Flag<Path> ADB_PATH_FLAG = Flag.path("adb");
    private static final Flag<String> DEVICE_ID_FLAG = Flag.string("device-id");
    private static final Flag<Path> OUTPUT_FLAG = Flag.path("output");
    private static final Flag<Boolean> OVERWRITE_OUTPUT_FLAG = Flag.booleanFlag("overwrite");
    private static final String ANDROID_SERIAL_VARIABLE = "ANDROID_SERIAL";
    private static final SystemEnvironmentProvider DEFAULT_PROVIDER = new DefaultSystemEnvironmentProvider();
    private static final String JSON_EXTENSION = "json";

    public abstract Path getAdbPath();

    public abstract Optional<String> getDeviceId();

    public abstract Path getOutputPath();

    public abstract boolean getOverwriteOutput();

    abstract AdbServer getAdbServer();

    public static Builder builder() {
        return new AutoValue_GetDeviceSpecCommand.Builder().setOverwriteOutput(false);
    }

    public static GetDeviceSpecCommand fromFlags(ParsedFlags flags, AdbServer adbServer) {
        return GetDeviceSpecCommand.fromFlags(flags, DEFAULT_PROVIDER, adbServer);
    }

    public static GetDeviceSpecCommand fromFlags(ParsedFlags flags, SystemEnvironmentProvider systemEnvironmentProvider, AdbServer adbServer) {
        Builder builder = GetDeviceSpecCommand.builder().setAdbServer(adbServer).setOutputPath(OUTPUT_FLAG.getRequiredValue(flags));
        Optional<String> deviceSerialName = DEVICE_ID_FLAG.getValue(flags);
        if (!deviceSerialName.isPresent()) {
            deviceSerialName = systemEnvironmentProvider.getVariable(ANDROID_SERIAL_VARIABLE);
        }
        deviceSerialName.ifPresent(builder::setDeviceId);
        Path adbPath = ADB_PATH_FLAG.getValue(flags).orElseGet(() -> new SdkToolsLocator().locateAdb(systemEnvironmentProvider).orElseThrow(() -> new CommandExecutionException("Unable to determine the location of ADB. Please set the --adb flag or define ANDROID_HOME or PATH environment variable.")));
        builder.setAdbPath(adbPath);
        OVERWRITE_OUTPUT_FLAG.getValue(flags).ifPresent(builder::setOverwriteOutput);
        flags.checkNoUnknownFlags();
        return builder.build();
    }

    public Devices.DeviceSpec execute() {
        if (!this.getOverwriteOutput()) {
            FilePreconditions.checkFileDoesNotExist(this.getOutputPath());
        }
        Path pathToAdb = this.getAdbPath();
        FilePreconditions.checkFileExistsAndExecutable(pathToAdb);
        AdbServer adb = this.getAdbServer();
        adb.init(this.getAdbPath());
        Devices.DeviceSpec deviceSpec = new DeviceAnalyzer(adb).getDeviceSpec(this.getDeviceId());
        this.writeDeviceSpecToFile(deviceSpec, this.getOutputPath());
        return deviceSpec;
    }

    private void writeDeviceSpecToFile(Devices.DeviceSpec deviceSpec, Path outputFile) {
        try {
            Path outputDirectory;
            if (this.getOverwriteOutput()) {
                Files.deleteIfExists(this.getOutputPath());
            }
            if ((outputDirectory = this.getOutputPath().getParent()) != null && !Files.exists(outputDirectory, new LinkOption[0])) {
                logger.info("Output directory '" + outputDirectory + "' does not exist, creating it.");
                Files.createDirectories(outputDirectory, new FileAttribute[0]);
            }
            Files.write(outputFile, JsonFormat.printer().print(deviceSpec).getBytes(StandardCharsets.UTF_8), new OpenOption[0]);
        }
        catch (IOException e2) {
            throw new UncheckedIOException(String.format("Error while writing the output file '%s'.", outputFile), e2);
        }
    }

    public static CommandHelp help() {
        return CommandHelp.builder().setCommandName(COMMAND_NAME).setCommandDescription(CommandHelp.CommandDescription.builder().setShortDescription("Writes out a JSON file containing the device specifications (i.e. features and properties) of the connected Android device.").build()).addFlag(CommandHelp.FlagDescription.builder().setFlagName(OUTPUT_FLAG.getName()).setExampleValue("device-spec.json").setDescription("Path to the output device spec file. Must have the .json extension.").build()).addFlag(CommandHelp.FlagDescription.builder().setFlagName(ADB_PATH_FLAG.getName()).setExampleValue("path/to/adb").setOptional(true).setDescription("Path to the adb utility. If absent, an attempt will be made to locate it if the %s or %s environment variable is set.", "ANDROID_HOME", "PATH").build()).addFlag(CommandHelp.FlagDescription.builder().setFlagName(DEVICE_ID_FLAG.getName()).setExampleValue("device-serial-name").setOptional(true).setDescription("Device serial name. If absent, this uses the %s environment variable. Either this flag or the environment variable is required when more than one device or emulator is connected.", ANDROID_SERIAL_VARIABLE).build()).addFlag(CommandHelp.FlagDescription.builder().setFlagName(OVERWRITE_OUTPUT_FLAG.getName()).setOptional(true).setDescription("If set, any previous existing output will be overwritten.").build()).build();
    }

    @AutoValue.Builder
    public static abstract class Builder {
        public abstract Builder setOutputPath(Path var1);

        public abstract Builder setOverwriteOutput(boolean var1);

        public abstract Builder setAdbPath(Path var1);

        public abstract Builder setDeviceId(String var1);

        public abstract Builder setAdbServer(AdbServer var1);

        abstract GetDeviceSpecCommand autoBuild();

        public GetDeviceSpecCommand build() {
            GetDeviceSpecCommand command = this.autoBuild();
            if (!GetDeviceSpecCommand.JSON_EXTENSION.equals(MoreFiles.getFileExtension(command.getOutputPath()))) {
                throw ValidationException.builder().withMessage("Flag --output should be the path where to generate the device spec file. Its extension must be '.json'.").build();
            }
            return command;
        }
    }
}

