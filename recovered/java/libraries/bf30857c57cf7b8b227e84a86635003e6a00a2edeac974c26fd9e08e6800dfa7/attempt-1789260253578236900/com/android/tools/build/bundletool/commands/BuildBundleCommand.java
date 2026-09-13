/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.commands;

import com.android.bundle.Config;
import com.android.bundle.Files;
import com.android.tools.build.bundletool.commands.AutoValue_BuildBundleCommand;
import com.android.tools.build.bundletool.commands.CommandHelp;
import com.android.tools.build.bundletool.flags.Flag;
import com.android.tools.build.bundletool.flags.ParsedFlags;
import com.android.tools.build.bundletool.io.AppBundleSerializer;
import com.android.tools.build.bundletool.model.AppBundle;
import com.android.tools.build.bundletool.model.BundleMetadata;
import com.android.tools.build.bundletool.model.BundleModule;
import com.android.tools.build.bundletool.model.ModuleEntry;
import com.android.tools.build.bundletool.model.ZipPath;
import com.android.tools.build.bundletool.model.exceptions.CommandExecutionException;
import com.android.tools.build.bundletool.model.exceptions.ValidationException;
import com.android.tools.build.bundletool.model.targeting.TargetingGenerator;
import com.android.tools.build.bundletool.model.utils.files.BufferedIo;
import com.android.tools.build.bundletool.model.utils.files.FilePreconditions;
import com.android.tools.build.bundletool.model.version.BundleToolVersion;
import com.android.tools.build.bundletool.validation.BundleModulesValidator;
import com.google.auto.value.AutoValue;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.io.Closer;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import com.google.protobuf.util.JsonFormat;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.Optional;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

@AutoValue
public abstract class BuildBundleCommand {
    public static final String COMMAND_NAME = "build-bundle";
    private static final Flag<Path> OUTPUT_FLAG = Flag.path("output");
    private static final Flag<Path> BUNDLE_CONFIG_FLAG = Flag.path("config");
    private static final Flag<ImmutableList<Path>> MODULES_FLAG = Flag.pathList("modules");
    private static final Flag<ImmutableMap<ZipPath, Path>> METADATA_FILES_FLAG = Flag.mapCollector("metadata-file", ZipPath.class, Path.class);
    private static final Flag<Boolean> UNCOMPRESSED_FLAG = Flag.booleanFlag("uncompressed");

    public abstract Path getOutputPath();

    public abstract ImmutableList<Path> getModulesPaths();

    public abstract Optional<Config.BundleConfig> getBundleConfig();

    public abstract BundleMetadata getBundleMetadata();

    abstract boolean getUncompressedBundle();

    public static Builder builder() {
        return new AutoValue_BuildBundleCommand.Builder().setUncompressedBundle(false);
    }

    public static BuildBundleCommand fromFlags(ParsedFlags flags) {
        Builder builder = BuildBundleCommand.builder().setOutputPath(OUTPUT_FLAG.getRequiredValue(flags)).setModulesPaths(MODULES_FLAG.getRequiredValue(flags));
        BUNDLE_CONFIG_FLAG.getValue(flags).ifPresent(path -> builder.setBundleConfig(BuildBundleCommand.parseBundleConfigJson(path)));
        METADATA_FILES_FLAG.getValue(flags).ifPresent(metadataFiles -> metadataFiles.forEach(builder::addMetadataFileInternal));
        UNCOMPRESSED_FLAG.getValue(flags).ifPresent(builder::setUncompressedBundle);
        flags.checkNoUnknownFlags();
        return builder.build();
    }

    public void execute() {
        this.validateInput();
        try (Closer closer = Closer.create();){
            ImmutableList.Builder moduleZipFilesBuilder = ImmutableList.builder();
            for (Path modulePath : this.getModulesPaths()) {
                try {
                    moduleZipFilesBuilder.add(closer.register(new ZipFile(modulePath.toFile())));
                }
                catch (ZipException e2) {
                    throw CommandExecutionException.builder().withCause(e2).withMessage("File '%s' does not seem to be a valid ZIP file.", modulePath).build();
                }
                catch (IOException e3) {
                    throw CommandExecutionException.builder().withCause(e3).withMessage("Unable to read file '%s'.", modulePath).build();
                }
            }
            ImmutableCollection moduleZipFiles = moduleZipFilesBuilder.build();
            ImmutableList<BundleModule> modules = new BundleModulesValidator().validate((ImmutableList<ZipFile>)moduleZipFiles);
            Preconditions.checkState(moduleZipFiles.size() == modules.size(), "Incorrect number of modules parsed (%s != %s).", moduleZipFiles.size(), modules.size());
            ImmutableList.Builder modulesWithTargeting = ImmutableList.builder();
            for (BundleModule module : modules) {
                BundleModule.Builder moduleWithTargeting = module.toBuilder();
                Optional<Files.Assets> assetsTargeting = BuildBundleCommand.generateAssetsTargeting(module);
                assetsTargeting.ifPresent(moduleWithTargeting::setAssetsConfig);
                Optional<Files.NativeLibraries> nativeLibrariesTargeting = BuildBundleCommand.generateNativeLibrariesTargeting(module);
                nativeLibrariesTargeting.ifPresent(moduleWithTargeting::setNativeConfig);
                Optional<Files.ApexImages> apexImagesTargeting = BuildBundleCommand.generateApexImagesTargeting(module);
                apexImagesTargeting.ifPresent(moduleWithTargeting::setApexConfig);
                modulesWithTargeting.add(moduleWithTargeting.build());
            }
            Config.BundleConfig bundleConfig = this.getBundleConfig().orElse(Config.BundleConfig.getDefaultInstance()).toBuilder().setBundletool(Config.Bundletool.newBuilder().setVersion(BundleToolVersion.getCurrentVersion().toString())).build();
            AppBundle appBundle = AppBundle.buildFromModules((ImmutableList<BundleModule>)modulesWithTargeting.build(), bundleConfig, this.getBundleMetadata());
            new AppBundleSerializer(this.getUncompressedBundle()).writeToDisk(appBundle, this.getOutputPath());
        }
        catch (IOException e4) {
            throw new UncheckedIOException(e4);
        }
    }

    private void validateInput() {
        this.getModulesPaths().forEach(path -> {
            FilePreconditions.checkFileHasExtension("File", path, ".zip");
            FilePreconditions.checkFileExistsAndReadable(path);
        });
        FilePreconditions.checkFileDoesNotExist(this.getOutputPath());
    }

    private static Optional<Files.Assets> generateAssetsTargeting(BundleModule module) {
        ImmutableList<ZipPath> assetDirectories = module.findEntriesUnderPath(BundleModule.ASSETS_DIRECTORY).map(ModuleEntry::getPath).filter(path -> path.getNameCount() > 1).map(ZipPath::getParent).distinct().collect(ImmutableList.toImmutableList());
        if (assetDirectories.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(new TargetingGenerator().generateTargetingForAssets(assetDirectories));
    }

    private static Optional<Files.NativeLibraries> generateNativeLibrariesTargeting(BundleModule module) {
        ImmutableList<String> libAbiDirs = module.findEntriesUnderPath(BundleModule.LIB_DIRECTORY).map(ModuleEntry::getPath).filter(path -> path.getNameCount() > 2).map(path -> path.subpath(0, 2)).map(ZipPath::toString).distinct().collect(ImmutableList.toImmutableList());
        if (libAbiDirs.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(new TargetingGenerator().generateTargetingForNativeLibraries(libAbiDirs));
    }

    private static Optional<Files.ApexImages> generateApexImagesTargeting(BundleModule module) {
        ImmutableList<ZipPath> apexImageFiles = module.findEntriesUnderPath(BundleModule.APEX_DIRECTORY).map(ModuleEntry::getPath).filter(p3 -> p3.toString().endsWith("img")).collect(ImmutableList.toImmutableList());
        boolean hasBuildInfo = module.findEntriesUnderPath(BundleModule.APEX_DIRECTORY).map(ModuleEntry::getPath).anyMatch(p3 -> p3.toString().endsWith("build_info.pb"));
        if (apexImageFiles.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(new TargetingGenerator().generateTargetingForApexImages(module.getBundleConfig().getApexConfig(), apexImageFiles, hasBuildInfo));
    }

    private static Config.BundleConfig parseBundleConfigJson(Path bundleConfigJsonPath) {
        Config.BundleConfig.Builder bundleConfig = Config.BundleConfig.newBuilder();
        try (BufferedReader bundleConfigReader = BufferedIo.reader(bundleConfigJsonPath);){
            JsonFormat.parser().merge(bundleConfigReader, (Message.Builder)bundleConfig);
        }
        catch (InvalidProtocolBufferException e2) {
            throw CommandExecutionException.builder().withCause(e2).withMessage("The file '%s' is not a valid BundleConfig JSON file.", bundleConfigJsonPath).build();
        }
        catch (IOException e3) {
            throw CommandExecutionException.builder().withCause(e3).withMessage("An error occurred while trying to read the file '%s'.", bundleConfigJsonPath).build();
        }
        return bundleConfig.build();
    }

    public static CommandHelp help() {
        return CommandHelp.builder().setCommandName(COMMAND_NAME).setCommandDescription(CommandHelp.CommandDescription.builder().setShortDescription("Builds an Android App Bundle from a set of Bundle modules provided as zip files.").addAdditionalParagraph("Note that the resource table, the AndroidManifest.xml and the resources must already have been compiled with aapt2 in the proto format.").build()).addFlag(CommandHelp.FlagDescription.builder().setFlagName(OUTPUT_FLAG.getName()).setExampleValue("bundle.aab").setDescription("Path to the where the Android App Bundle should be built.").build()).addFlag(CommandHelp.FlagDescription.builder().setFlagName(MODULES_FLAG.getName()).setExampleValue("path/to/module1.zip,path/to/module2.zip,...").setDescription("The list of module files to build the final Android App Bundle from.").build()).addFlag(CommandHelp.FlagDescription.builder().setFlagName(BUNDLE_CONFIG_FLAG.getName()).setExampleValue("BundleConfig.pb.json").setDescription("Path to a JSON file that describes the configuration of the App Bundle. This configuration will be merged with BundleTool defaults.").setOptional(true).build()).addFlag(CommandHelp.FlagDescription.builder().setFlagName(METADATA_FILES_FLAG.getName()).setExampleValue("com.some.namespace/file-name:path/to/file").setDescription("Specifies a file that will be included as metadata in the Android App Bundle. The format of the flag value is '<bundle-path>:<physical-file>' where 'bundle-path' denotes the file location inside the App Bundle's metadata directory, and 'physical-file' is an existing file containing the raw data to be stored. The flag can be repeated.").setOptional(true).build()).build();
    }

    @AutoValue.Builder
    public static abstract class Builder {
        public abstract Builder setOutputPath(Path var1);

        public abstract Builder setModulesPaths(ImmutableList<Path> var1);

        public abstract Builder setBundleConfig(Config.BundleConfig var1);

        public Builder setBundleConfig(Path bundleConfigFile) {
            return this.setBundleConfig(BuildBundleCommand.parseBundleConfigJson(bundleConfigFile));
        }

        abstract BundleMetadata.Builder bundleMetadataBuilder();

        public Builder addMetadataFile(String metadataDirectory, String fileName, Path file) {
            this.addMetadataFileInternal(ZipPath.create(metadataDirectory).resolve(fileName), file);
            return this;
        }

        void addMetadataFileInternal(ZipPath metadataPath, Path file) {
            if (!Files.exists(file, new LinkOption[0])) {
                throw ValidationException.builder().withMessage("Metadata file '%s' does not exist.", file).build();
            }
            this.bundleMetadataBuilder().addFile(metadataPath, BufferedIo.inputStreamSupplier(file));
        }

        public Builder setMainDexListFile(Path file) {
            return this.addMetadataFile("com.android.tools.build.bundletool", "mainDexList.txt", file);
        }

        public abstract Builder setUncompressedBundle(boolean var1);

        public abstract BuildBundleCommand build();
    }
}

