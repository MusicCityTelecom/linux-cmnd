/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.commands;

import com.android.tools.build.bundletool.commands.AutoValue_ValidateBundleCommand;
import com.android.tools.build.bundletool.commands.CommandHelp;
import com.android.tools.build.bundletool.flags.Flag;
import com.android.tools.build.bundletool.flags.ParsedFlags;
import com.android.tools.build.bundletool.model.AppBundle;
import com.android.tools.build.bundletool.model.BundleModule;
import com.android.tools.build.bundletool.model.ModuleEntry;
import com.android.tools.build.bundletool.model.exceptions.CommandExecutionException;
import com.android.tools.build.bundletool.model.utils.files.FilePreconditions;
import com.android.tools.build.bundletool.validation.AppBundleValidator;
import com.google.auto.value.AutoValue;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Path;
import java.util.Map;
import java.util.zip.ZipFile;

@AutoValue
public abstract class ValidateBundleCommand {
    public static final String COMMAND_NAME = "validate";
    private static final Flag<Path> BUNDLE_FLAG = Flag.path("bundle");

    public abstract Path getBundlePath();

    public abstract Boolean getPrintOutput();

    public static Builder builder() {
        return new AutoValue_ValidateBundleCommand.Builder().setPrintOutput(false);
    }

    public static ValidateBundleCommand fromFlags(ParsedFlags flags) {
        Builder builder = ValidateBundleCommand.builder().setBundlePath(BUNDLE_FLAG.getRequiredValue(flags)).setPrintOutput(true);
        flags.checkNoUnknownFlags();
        return builder.build();
    }

    public void execute() throws CommandExecutionException {
        this.validateInput();
        try (ZipFile bundleZip = new ZipFile(this.getBundlePath().toFile());){
            AppBundleValidator bundleValidator = AppBundleValidator.create();
            bundleValidator.validateFile(bundleZip);
            AppBundle appBundle = AppBundle.buildFromZip(bundleZip);
            bundleValidator.validate(appBundle);
            if (this.getPrintOutput().booleanValue()) {
                this.printBundleSummary(appBundle);
            }
        }
        catch (IOException e2) {
            throw new UncheckedIOException(String.format("Error reading zip file '%s'", this.getBundlePath()), e2);
        }
    }

    private void validateInput() {
        FilePreconditions.checkFileExistsAndReadable(this.getBundlePath());
    }

    private void printBundleSummary(AppBundle appBundle) {
        System.out.printf("App Bundle information\n", new Object[0]);
        System.out.printf("------------\n", new Object[0]);
        System.out.printf("Feature modules:\n", new Object[0]);
        for (Map.Entry moduleEntry : appBundle.getFeatureModules().entrySet()) {
            System.out.printf("\tFeature module: %s\n", moduleEntry.getKey());
            this.printModuleSummary((BundleModule)moduleEntry.getValue());
        }
        if (!appBundle.getAssetModules().isEmpty()) {
            System.out.printf("Asset packs:\n", new Object[0]);
            for (Map.Entry moduleEntry : appBundle.getAssetModules().entrySet()) {
                System.out.printf("\tAsset pack: %s\n", moduleEntry.getKey());
                this.printModuleSummary((BundleModule)moduleEntry.getValue());
            }
        }
    }

    private void printModuleSummary(BundleModule bundleModule) {
        for (ModuleEntry entry : bundleModule.getEntries()) {
            System.out.printf("\t\tFile: %s\n", entry.getPath().toString());
        }
    }

    public static CommandHelp help() {
        return CommandHelp.builder().setCommandName(COMMAND_NAME).setCommandDescription(CommandHelp.CommandDescription.builder().setShortDescription("Verifies the given Android App Bundle is valid and prints out information about it.").build()).addFlag(CommandHelp.FlagDescription.builder().setFlagName(BUNDLE_FLAG.getName()).setExampleValue("bundle.aab").setDescription("Path to the Android App Bundle to validate.").build()).build();
    }

    @AutoValue.Builder
    public static abstract class Builder {
        public abstract Builder setBundlePath(Path var1);

        public abstract Builder setPrintOutput(Boolean var1);

        public abstract ValidateBundleCommand build();
    }
}

