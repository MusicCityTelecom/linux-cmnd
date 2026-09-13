/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.commands;

import com.android.bundle.Commands;
import com.android.bundle.Devices;
import com.android.tools.build.bundletool.commands.AutoValue_GetSizeCommand;
import com.android.tools.build.bundletool.commands.CommandHelp;
import com.android.tools.build.bundletool.device.DeviceSpecParser;
import com.android.tools.build.bundletool.device.VariantMatcher;
import com.android.tools.build.bundletool.device.VariantTotalSizeAggregator;
import com.android.tools.build.bundletool.flags.Flag;
import com.android.tools.build.bundletool.flags.ParsedFlags;
import com.android.tools.build.bundletool.model.ConfigurationSizes;
import com.android.tools.build.bundletool.model.GetSizeRequest;
import com.android.tools.build.bundletool.model.SizeConfiguration;
import com.android.tools.build.bundletool.model.exceptions.ValidationException;
import com.android.tools.build.bundletool.model.utils.ApkSizeUtils;
import com.android.tools.build.bundletool.model.utils.CollectorUtils;
import com.android.tools.build.bundletool.model.utils.GetSizeCsvUtils;
import com.android.tools.build.bundletool.model.utils.ResultUtils;
import com.android.tools.build.bundletool.model.utils.files.FilePreconditions;
import com.android.tools.build.bundletool.model.version.Version;
import com.google.auto.value.AutoValue;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import java.io.PrintStream;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Function;

@AutoValue
public abstract class GetSizeCommand
implements GetSizeRequest {
    public static final String COMMAND_NAME = "get-size";
    private static final Flag<Path> APKS_ARCHIVE_FILE_FLAG = Flag.path("apks");
    private static final Flag<Path> DEVICE_SPEC_FLAG = Flag.path("device-spec");
    private static final Flag<ImmutableSet<String>> MODULES_FLAG = Flag.stringSet("modules");
    private static final Flag<Boolean> INSTANT_FLAG = Flag.booleanFlag("instant");
    private static final Flag<ImmutableSet<GetSizeRequest.Dimension>> DIMENSIONS_FLAG = Flag.enumSet("dimensions", GetSizeRequest.Dimension.class);
    private static final Joiner COMMA_JOINER = Joiner.on(',');
    @VisibleForTesting
    static final ImmutableSet<GetSizeRequest.Dimension> SUPPORTED_DIMENSIONS = ImmutableSet.of(GetSizeRequest.Dimension.SDK, GetSizeRequest.Dimension.ABI, GetSizeRequest.Dimension.LANGUAGE, GetSizeRequest.Dimension.SCREEN_DENSITY);

    public abstract Path getApksArchivePath();

    @Override
    public abstract Devices.DeviceSpec getDeviceSpec();

    @Override
    public abstract Optional<ImmutableSet<String>> getModules();

    @Override
    public abstract ImmutableSet<GetSizeRequest.Dimension> getDimensions();

    public abstract GetSizeSubcommand getGetSizeSubCommand();

    @Override
    public abstract boolean getInstant();

    public static Builder builder() {
        return new AutoValue_GetSizeCommand.Builder().setDeviceSpec(Devices.DeviceSpec.getDefaultInstance()).setInstant(false).setDimensions(ImmutableSet.of());
    }

    public static GetSizeCommand fromFlags(ParsedFlags flags) {
        Path apksArchivePath = APKS_ARCHIVE_FILE_FLAG.getRequiredValue(flags);
        Optional<Path> deviceSpecPath = DEVICE_SPEC_FLAG.getValue(flags);
        Optional<ImmutableSet<String>> modules = MODULES_FLAG.getValue(flags);
        Optional<Boolean> instant = INSTANT_FLAG.getValue(flags);
        ImmutableSet<GetSizeRequest.Dimension> dimensions = DIMENSIONS_FLAG.getValue(flags).orElse(ImmutableSet.of());
        flags.checkNoUnknownFlags();
        FilePreconditions.checkFileExistsAndReadable(apksArchivePath);
        deviceSpecPath.ifPresent(FilePreconditions::checkFileExistsAndReadable);
        Devices.DeviceSpec deviceSpec = deviceSpecPath.map(DeviceSpecParser::parsePartialDeviceSpec).orElse(Devices.DeviceSpec.getDefaultInstance());
        Builder command = GetSizeCommand.builder().setApksArchivePath(apksArchivePath).setDeviceSpec(deviceSpec).setGetSizeSubCommand(GetSizeCommand.parseGetSizeSubCommand(flags));
        modules.ifPresent(command::setModules);
        instant.ifPresent(command::setInstant);
        if (dimensions.contains((Object)GetSizeRequest.Dimension.ALL)) {
            dimensions = SUPPORTED_DIMENSIONS;
        }
        command.setDimensions(dimensions);
        return command.build();
    }

    private static GetSizeSubcommand parseGetSizeSubCommand(ParsedFlags flags) {
        String subCommand = flags.getSubCommand().orElseThrow(() -> new ValidationException("Target of the get-size command not found."));
        return GetSizeSubcommand.fromString(subCommand);
    }

    public void execute() {
        switch (this.getGetSizeSubCommand()) {
            case TOTAL: {
                this.getSizeTotal(System.out);
            }
        }
    }

    public void getSizeTotal(PrintStream output) {
        output.print(GetSizeCsvUtils.getSizeTotalOutputInCsv(this.getSizeTotalInternal(), this.getDimensions()));
    }

    @VisibleForTesting
    ConfigurationSizes getSizeTotalInternal() {
        Commands.BuildApksResult buildApksResult = ResultUtils.readTableOfContents(this.getApksArchivePath());
        ImmutableList<Commands.Variant> variants = new VariantMatcher(this.getDeviceSpec(), this.getInstant()).getAllMatchingVariants(buildApksResult);
        ImmutableMap<String, Long> compressedSizeByApkPaths = ApkSizeUtils.getCompressedSizeByApkPaths(variants, this.getApksArchivePath());
        ImmutableMap<SizeConfiguration, Long> minSizeConfigurationMap = ImmutableMap.of();
        ImmutableMap<SizeConfiguration, Long> maxSizeConfigurationMap = ImmutableMap.of();
        for (Commands.Variant variant : variants) {
            ConfigurationSizes configurationSizes = new VariantTotalSizeAggregator(compressedSizeByApkPaths, Version.of(buildApksResult.getBundletool().getVersion()), variant, this).getSize();
            minSizeConfigurationMap = CollectorUtils.combineMaps(minSizeConfigurationMap, configurationSizes.getMinSizeConfigurationMap(), Math::min);
            maxSizeConfigurationMap = CollectorUtils.combineMaps(maxSizeConfigurationMap, configurationSizes.getMaxSizeConfigurationMap(), Math::max);
        }
        return ConfigurationSizes.create(minSizeConfigurationMap, maxSizeConfigurationMap);
    }

    public static CommandHelp help() {
        return CommandHelp.builder().setCommandName(COMMAND_NAME).setSubCommandNames(((ImmutableSet)GetSizeSubcommand.STRING_TO_SUBCOMMAND.keySet()).asList()).setCommandDescription(CommandHelp.CommandDescription.builder().setShortDescription("Computes the min and max download sizes of APKs served to different devices configurations from an APK Set.").addAdditionalParagraph("The output is in CSV format.").build()).addFlag(CommandHelp.FlagDescription.builder().setFlagName(APKS_ARCHIVE_FILE_FLAG.getName()).setExampleValue("archive.apks").setDescription("Path to the archive file generated by the '%s' command.", "build-apks").build()).addFlag(CommandHelp.FlagDescription.builder().setFlagName(DEVICE_SPEC_FLAG.getName()).setExampleValue("device-spec.json").setOptional(true).setDescription("Path to the device spec file to be used for matching (defaults to empty device spec). Note that partial specifications are allowed in the file as opposed to the spec generated by '%s'.", "get-device-spec").build()).addFlag(CommandHelp.FlagDescription.builder().setFlagName(DIMENSIONS_FLAG.getName()).setExampleValue(COMMA_JOINER.join((Object[])GetSizeRequest.Dimension.values())).setOptional(true).setDescription("Specifies which dimensions to expand the sizes in the output against. Note that ALL is a shortcut to all other dimensions and including ALL here would cause the output to be expanded over all possible dimensions.").build()).addFlag(CommandHelp.FlagDescription.builder().setFlagName(MODULES_FLAG.getName()).setExampleValue("base,module1,module2").setOptional(true).setDescription("List of modules to run this report on (defaults to all the modules installed during the first download). Note that the dependent modules will also be considered. We ignore standalone APKs for size calculation when this flag is set.").build()).addFlag(CommandHelp.FlagDescription.builder().setFlagName(INSTANT_FLAG.getName()).setOptional(true).setDescription("When set, APKs of the instant modules will be considered instead of the installable APKs. Defaults to false.").build()).build();
    }

    GetSizeCommand() {
    }

    @AutoValue.Builder
    public static abstract class Builder {
        public abstract Builder setApksArchivePath(Path var1);

        public abstract Builder setDeviceSpec(Devices.DeviceSpec var1);

        public Builder setDeviceSpec(Path deviceSpecPath) {
            return this.setDeviceSpec(DeviceSpecParser.parsePartialDeviceSpec(deviceSpecPath));
        }

        public abstract Builder setModules(ImmutableSet<String> var1);

        public abstract Builder setDimensions(ImmutableSet<GetSizeRequest.Dimension> var1);

        public abstract Builder setInstant(boolean var1);

        public abstract Builder setGetSizeSubCommand(GetSizeSubcommand var1);

        public abstract GetSizeCommand build();
    }

    public static enum GetSizeSubcommand {
        TOTAL("total");

        static final ImmutableMap<String, GetSizeSubcommand> STRING_TO_SUBCOMMAND;
        private final String subCommand;

        private GetSizeSubcommand(String subCommand) {
            this.subCommand = subCommand;
        }

        public String toString() {
            return this.subCommand;
        }

        public static GetSizeSubcommand fromString(String subCommand) {
            GetSizeSubcommand result = STRING_TO_SUBCOMMAND.get(subCommand);
            if (result == null) {
                throw ValidationException.builder().withMessage("Unrecognized get-size command target: '%s'. Accepted values are: %s", subCommand, STRING_TO_SUBCOMMAND.keySet()).build();
            }
            return result;
        }

        static {
            STRING_TO_SUBCOMMAND = Arrays.stream(GetSizeSubcommand.values()).collect(ImmutableMap.toImmutableMap(GetSizeSubcommand::toString, Function.identity()));
        }
    }
}

