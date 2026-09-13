/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.commands;

import com.android.tools.build.bundletool.commands.AutoValue_DumpCommand;
import com.android.tools.build.bundletool.commands.CommandHelp;
import com.android.tools.build.bundletool.commands.DumpManager;
import com.android.tools.build.bundletool.flags.Flag;
import com.android.tools.build.bundletool.flags.ParsedFlags;
import com.android.tools.build.bundletool.model.BundleModuleName;
import com.android.tools.build.bundletool.model.ResourceTableEntry;
import com.android.tools.build.bundletool.model.exceptions.ValidationException;
import com.android.tools.build.bundletool.model.utils.files.FilePreconditions;
import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import java.io.PrintStream;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@AutoValue
public abstract class DumpCommand {
    public static final String COMMAND_NAME = "dump";
    private static final Flag<Path> BUNDLE_LOCATION_FLAG = Flag.path("bundle");
    private static final Flag<String> MODULE_FLAG = Flag.string("module");
    private static final Flag<String> XPATH_FLAG = Flag.string("xpath");
    private static final Flag<String> RESOURCE_FLAG = Flag.string("resource");
    private static final Flag<Boolean> VALUES_FLAG = Flag.booleanFlag("values");
    private static final Pattern RESOURCE_NAME_PATTERN = Pattern.compile("(?<type>[^/]+)/(?<name>[^/]+)");

    public abstract Path getBundlePath();

    public abstract PrintStream getOutputStream();

    public abstract DumpTarget getDumpTarget();

    public abstract Optional<String> getModuleName();

    public abstract Optional<String> getXPathExpression();

    public abstract Optional<Integer> getResourceId();

    public abstract Optional<String> getResourceName();

    public abstract Optional<Boolean> getPrintValues();

    public static Builder builder() {
        return new AutoValue_DumpCommand.Builder().setOutputStream(System.out);
    }

    public static DumpCommand fromFlags(ParsedFlags flags) {
        DumpTarget dumpTarget = DumpCommand.parseDumpTarget(flags);
        Path bundlePath = BUNDLE_LOCATION_FLAG.getRequiredValue(flags);
        Optional<String> moduleName = MODULE_FLAG.getValue(flags);
        Optional<String> xPath = XPATH_FLAG.getValue(flags);
        Optional<String> resource = RESOURCE_FLAG.getValue(flags);
        Optional<Boolean> printValues = VALUES_FLAG.getValue(flags);
        Builder dumpCommand = DumpCommand.builder().setBundlePath(bundlePath).setDumpTarget(dumpTarget);
        moduleName.ifPresent(dumpCommand::setModuleName);
        xPath.ifPresent(dumpCommand::setXPathExpression);
        printValues.ifPresent(dumpCommand::setPrintValues);
        resource.ifPresent(r3 -> {
            try {
                dumpCommand.setResourceId(Long.decode(r3).intValue());
            }
            catch (NumberFormatException e2) {
                dumpCommand.setResourceName((String)r3);
            }
        });
        return dumpCommand.build();
    }

    public void execute() {
        this.validateInput();
        switch (this.getDumpTarget()) {
            case CONFIG: {
                new DumpManager(this.getOutputStream(), this.getBundlePath()).printBundleConfig();
                break;
            }
            case MANIFEST: {
                BundleModuleName moduleName = this.getModuleName().map(BundleModuleName::create).orElse(BundleModuleName.BASE_MODULE_NAME);
                new DumpManager(this.getOutputStream(), this.getBundlePath()).printManifest(moduleName, this.getXPathExpression());
                break;
            }
            case RESOURCES: {
                new DumpManager(this.getOutputStream(), this.getBundlePath()).printResources(this.parseResourcePredicate(), this.getPrintValues().orElse(false));
            }
        }
    }

    private void validateInput() {
        FilePreconditions.checkFileExistsAndReadable(this.getBundlePath());
        if (this.getResourceId().isPresent() && this.getResourceName().isPresent()) {
            throw new ValidationException("Cannot pass both resource ID and resource name. Pick one!");
        }
        if (this.getDumpTarget().equals((Object)DumpTarget.RESOURCES) && this.getXPathExpression().isPresent()) {
            throw new ValidationException("Cannot pass an XPath expression when dumping resources.");
        }
        if (this.getDumpTarget().equals((Object)DumpTarget.RESOURCES) && this.getModuleName().isPresent()) {
            throw new ValidationException("The --module flag is unnecessary as the 'dump resources' by default searches across all modules.");
        }
        if (!this.getDumpTarget().equals((Object)DumpTarget.RESOURCES) && (this.getResourceId().isPresent() || this.getResourceName().isPresent())) {
            throw new ValidationException("The --resource flag can only be passed when dumping resources.");
        }
        if (!this.getDumpTarget().equals((Object)DumpTarget.RESOURCES) && this.getPrintValues().isPresent()) {
            throw new ValidationException("The --values flag can only be passed when dumping resources.");
        }
    }

    private static DumpTarget parseDumpTarget(ParsedFlags flags) {
        String subCommand = flags.getSubCommand().orElseThrow(() -> new ValidationException("Target of the dump not found."));
        return DumpTarget.fromString(subCommand);
    }

    private Predicate<ResourceTableEntry> parseResourcePredicate() {
        if (this.getResourceId().isPresent()) {
            return entry -> entry.getResourceId().getFullResourceId() == this.getResourceId().get().intValue();
        }
        if (this.getResourceName().isPresent()) {
            String resourceName = this.getResourceName().get();
            Matcher matcher = RESOURCE_NAME_PATTERN.matcher(resourceName);
            if (!matcher.matches()) {
                throw new ValidationException("Resource name must match the format '<type>/<name>', e.g. 'drawable/icon'.");
            }
            return entry -> entry.getType().getName().equals(matcher.group("type")) && entry.getEntry().getName().equals(matcher.group("name"));
        }
        return entry -> true;
    }

    public static CommandHelp help() {
        return CommandHelp.builder().setCommandName(COMMAND_NAME).setSubCommandNames(((ImmutableSet)DumpTarget.SUBCOMMAND_TO_TARGET.keySet()).asList()).setCommandDescription(CommandHelp.CommandDescription.builder().setShortDescription("Prints files or extract values from the bundle in a human-readable form.").addAdditionalParagraph("Examples:").addAdditionalParagraph(String.format("1. Prints the AndroidManifest.xml of the base module:%n$ bundletool dump manifest --bundle=/tmp/app.aab", new Object[0])).addAdditionalParagraph(String.format("2. Prints the versionCode of the bundle of the base module:%n$ bundletool dump manifest --bundle=/tmp/app.aab --xpath=/manifest/@versionCode", new Object[0])).addAdditionalParagraph(String.format("3. Prints all the resources present in the bundle:%n$ bundletool dump resources --bundle=/tmp/app.aab", new Object[0])).addAdditionalParagraph(String.format("4. Prints a resource's configs from its resource ID:%n$ bundletool dump resources --bundle=/tmp/app.aab --resource=0x7f0e013a", new Object[0])).addAdditionalParagraph(String.format("5. Prints a resource's configs and values from its resource type & name:%n$ bundletool dump resources --bundle=/tmp/app.aab --resource=drawable/icon --values", new Object[0])).addAdditionalParagraph(String.format("6. Prints the content of the bundle configuration file:%n$ bundletool dump config --bundle=/tmp/app.aab", new Object[0])).build()).addFlag(CommandHelp.FlagDescription.builder().setFlagName("bundle").setDescription("Path to the Android App Bundle.").setExampleValue("app.aab").build()).addFlag(CommandHelp.FlagDescription.builder().setFlagName("module").setDescription("Name of the module to apply the dump for. Only applies when dumping the manifest. Defaults to 'base'.").setExampleValue("base").setOptional(true).build()).addFlag(CommandHelp.FlagDescription.builder().setFlagName("xpath").setDescription("XPath expression to extract the value of attributes from the XML file being dumped. Only applies when dumping the manifest.").setExampleValue("/manifest/@android:versionCode").setOptional(true).build()).addFlag(CommandHelp.FlagDescription.builder().setFlagName("resource").setDescription("Name or ID of the resource to lookup. Only applies when dumping resources. If a resource ID is provided, it can be specified either as a decimal or hexadecimal integer. If a resource name is provided, it must follow the format '<type>/<name>', e.g. 'drawable/icon'").setExampleValue("0x7f030001").setOptional(true).build()).addFlag(CommandHelp.FlagDescription.builder().setFlagName("values").setDescription("When set, also prints the values of the resources. Defaults to false. Only applies when dumping the resources.").setOptional(true).build()).build();
    }

    public static enum DumpTarget {
        MANIFEST("manifest"),
        RESOURCES("resources"),
        CONFIG("config");

        static final ImmutableMap<String, DumpTarget> SUBCOMMAND_TO_TARGET;
        private final String subCommand;

        private DumpTarget(String subCommand) {
            this.subCommand = subCommand;
        }

        public String toString() {
            return this.subCommand;
        }

        public static DumpTarget fromString(String subCommand) {
            DumpTarget dumpTarget = SUBCOMMAND_TO_TARGET.get(subCommand);
            if (dumpTarget == null) {
                throw ValidationException.builder().withMessage("Unrecognized dump target: '%s'. Accepted values are: %s", subCommand, SUBCOMMAND_TO_TARGET.keySet()).build();
            }
            return dumpTarget;
        }

        static {
            SUBCOMMAND_TO_TARGET = Arrays.stream(DumpTarget.values()).collect(ImmutableMap.toImmutableMap(DumpTarget::toString, Function.identity()));
        }
    }

    @AutoValue.Builder
    public static abstract class Builder {
        public abstract Builder setBundlePath(Path var1);

        public abstract Builder setOutputStream(PrintStream var1);

        public abstract Builder setDumpTarget(DumpTarget var1);

        public abstract Builder setModuleName(String var1);

        public abstract Builder setXPathExpression(String var1);

        public abstract Builder setResourceId(int var1);

        public abstract Builder setResourceName(String var1);

        public abstract Builder setPrintValues(boolean var1);

        public abstract DumpCommand build();
    }
}

