/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.commands;

import com.android.bundle.Commands;
import com.android.bundle.Devices;
import com.android.tools.build.bundletool.commands.AutoValue_ExtractApksCommand;
import com.android.tools.build.bundletool.commands.CommandHelp;
import com.android.tools.build.bundletool.device.ApkMatcher;
import com.android.tools.build.bundletool.device.DeviceSpecParser;
import com.android.tools.build.bundletool.device.IncompatibleDeviceException;
import com.android.tools.build.bundletool.flags.Flag;
import com.android.tools.build.bundletool.flags.ParsedFlags;
import com.android.tools.build.bundletool.model.ZipPath;
import com.android.tools.build.bundletool.model.exceptions.ValidationException;
import com.android.tools.build.bundletool.model.utils.ResultUtils;
import com.android.tools.build.bundletool.model.utils.files.FilePreconditions;
import com.android.tools.build.bundletool.model.utils.files.FileUtils;
import com.google.auto.value.AutoValue;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Stream;

@AutoValue
public abstract class ExtractApksCommand {
    private static final Logger logger = Logger.getLogger(ExtractApksCommand.class.getName());
    public static final String COMMAND_NAME = "extract-apks";
    static final String ALL_MODULES_SHORTCUT = "_ALL_";
    private static final Flag<Path> APKS_ARCHIVE_FILE_FLAG = Flag.path("apks");
    private static final Flag<Path> DEVICE_SPEC_FLAG = Flag.path("device-spec");
    private static final Flag<Path> OUTPUT_DIRECTORY = Flag.path("output-dir");
    private static final Flag<ImmutableSet<String>> MODULES_FLAG = Flag.stringSet("modules");
    private static final Flag<Boolean> INSTANT_FLAG = Flag.booleanFlag("instant");

    public abstract Path getApksArchivePath();

    public abstract Devices.DeviceSpec getDeviceSpec();

    public abstract Optional<Path> getOutputDirectory();

    public abstract Optional<ImmutableSet<String>> getModules();

    public abstract boolean getInstant();

    public static Builder builder() {
        return new AutoValue_ExtractApksCommand.Builder().setInstant(false);
    }

    public static ExtractApksCommand fromFlags(ParsedFlags flags) {
        Path apksArchivePath = APKS_ARCHIVE_FILE_FLAG.getRequiredValue(flags);
        Path deviceSpecPath = DEVICE_SPEC_FLAG.getRequiredValue(flags);
        Optional<Path> outputDirectory = OUTPUT_DIRECTORY.getValue(flags);
        Optional<ImmutableSet<String>> modules = MODULES_FLAG.getValue(flags);
        Optional<Boolean> instant = INSTANT_FLAG.getValue(flags);
        flags.checkNoUnknownFlags();
        Builder command = ExtractApksCommand.builder();
        Preconditions.checkArgument(!Files.isDirectory(apksArchivePath, new LinkOption[0]), "File '%s' is a directory.", (Object)apksArchivePath);
        command.setApksArchivePath(apksArchivePath);
        FilePreconditions.checkFileExistsAndReadable(deviceSpecPath);
        command.setDeviceSpec(DeviceSpecParser.parseDeviceSpec(deviceSpecPath));
        outputDirectory.ifPresent(command::setOutputDirectory);
        modules.ifPresent(command::setModules);
        instant.ifPresent(command::setInstant);
        return command.build();
    }

    public ImmutableList<Path> execute() {
        return this.execute(System.out);
    }

    @VisibleForTesting
    ImmutableList<Path> execute(PrintStream output) {
        this.validateInput();
        Commands.BuildApksResult toc = ResultUtils.readTableOfContents(this.getApksArchivePath());
        Optional<ImmutableSet<String>> requestedModuleNames = this.getModules().map(modules -> ExtractApksCommand.resolveRequestedModules(modules, toc));
        ApkMatcher apkMatcher = new ApkMatcher(this.getDeviceSpec(), requestedModuleNames, this.getInstant());
        ImmutableList<ZipPath> matchedApks = apkMatcher.getMatchingApks(toc);
        if (matchedApks.isEmpty()) {
            throw new IncompatibleDeviceException("No compatible APKs found for the device.");
        }
        if (Files.isDirectory(this.getApksArchivePath(), new LinkOption[0])) {
            return matchedApks.stream().map(matchedApk -> this.getApksArchivePath().resolve(matchedApk.toString())).collect(ImmutableList.toImmutableList());
        }
        return this.extractMatchedApksFromApksArchive(matchedApks);
    }

    static ImmutableSet<String> resolveRequestedModules(ImmutableSet<String> requestedModules, Commands.BuildApksResult toc) {
        return requestedModules.contains(ALL_MODULES_SHORTCUT) ? Stream.concat(toc.getVariantList().stream().flatMap(variant -> variant.getApkSetList().stream()).map(apkSet -> apkSet.getModuleMetadata().getName()), toc.getAssetSliceSetList().stream().map(Commands.AssetSliceSet::getAssetModuleMetadata).map(Commands.AssetModuleMetadata::getName)).collect(ImmutableSet.toImmutableSet()) : requestedModules;
    }

    private void validateInput() {
        if (this.getModules().isPresent() && this.getModules().get().isEmpty()) {
            throw new ValidationException("The set of modules cannot be empty.");
        }
        if (Files.isDirectory(this.getApksArchivePath(), new LinkOption[0])) {
            Preconditions.checkArgument(!this.getOutputDirectory().isPresent(), "Output directory should not be set when APKs are inside directory.");
            FilePreconditions.checkDirectoryExists(this.getApksArchivePath());
            FilePreconditions.checkFileExistsAndReadable(this.getApksArchivePath().resolve("toc.pb"));
        } else {
            FilePreconditions.checkFileExistsAndReadable(this.getApksArchivePath());
        }
    }

    /*
     * Exception decompiling
     */
    private ImmutableList<Path> extractMatchedApksFromApksArchive(ImmutableList<ZipPath> matchedApkPaths) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Started 2 blocks at once
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:412)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:487)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:736)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:850)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at org.benf.cfr.reader.Main.main(Main.java:54)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    private static Path createTempDirectory() {
        try {
            return Files.createTempDirectory("bundletool-extracted-apks", new FileAttribute[0]);
        }
        catch (IOException e2) {
            throw new UncheckedIOException("Unable to create a temporary directory for extracted APKs.", e2);
        }
    }

    public static CommandHelp help() {
        return CommandHelp.builder().setCommandName(COMMAND_NAME).setCommandDescription(CommandHelp.CommandDescription.builder().setShortDescription("Extracts from an APK Set the APKs that should be installed on a given device.").build()).addFlag(CommandHelp.FlagDescription.builder().setFlagName(APKS_ARCHIVE_FILE_FLAG.getName()).setExampleValue("archive.apks").setDescription("Path to the archive file generated by the '%s' command.", "build-apks").build()).addFlag(CommandHelp.FlagDescription.builder().setFlagName(DEVICE_SPEC_FLAG.getName()).setExampleValue("device-spec.json").setDescription("Path to the device spec file generated by the '%s' command.", "get-device-spec").build()).addFlag(CommandHelp.FlagDescription.builder().setFlagName(OUTPUT_DIRECTORY.getName()).setOptional(true).setExampleValue("output-dir").setDescription("Path to where the matched APKs will be extracted from the archive file. If not set, the APK Set archive is created in a temporary directory.").build()).addFlag(CommandHelp.FlagDescription.builder().setFlagName(MODULES_FLAG.getName()).setExampleValue("base,module1,module2").setOptional(true).setDescription("List of modules to be extracted, or \"%s\" for all modules. Defaults to modules installed during the first install, i.e. not on-demand. Note that the dependent modules will also be extracted. The value of this flag is ignored if the device receives a standalone APK.", ALL_MODULES_SHORTCUT).build()).addFlag(CommandHelp.FlagDescription.builder().setFlagName(INSTANT_FLAG.getName()).setOptional(true).setDescription("When set, APKs of the instant modules will be extracted instead of the installable APKs.").build()).build();
    }

    ExtractApksCommand() {
    }

    private static /* synthetic */ void lambda$extractMatchedApksFromApksArchive$4(Path dir) {
        if (!Files.exists(dir, new LinkOption[0])) {
            logger.info("Output directory '" + dir + "' does not exist, creating it.");
            FileUtils.createDirectories(dir);
        }
    }

    @AutoValue.Builder
    public static abstract class Builder {
        public abstract Builder setApksArchivePath(Path var1);

        public abstract Builder setDeviceSpec(Devices.DeviceSpec var1);

        public Builder setDeviceSpec(Path deviceSpecPath) {
            return this.setDeviceSpec(DeviceSpecParser.parseDeviceSpec(deviceSpecPath));
        }

        public abstract Builder setOutputDirectory(Path var1);

        public abstract Builder setModules(ImmutableSet<String> var1);

        public abstract Builder setInstant(boolean var1);

        abstract ExtractApksCommand autoBuild();

        public ExtractApksCommand build() {
            ExtractApksCommand command = this.autoBuild();
            DeviceSpecParser.validateDeviceSpec(command.getDeviceSpec(), true);
            return command;
        }
    }
}

