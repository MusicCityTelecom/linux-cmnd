/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.commands;

import com.android.tools.build.bundletool.commands.CommandHelp;
import com.android.tools.build.bundletool.flags.ParsedFlags;
import com.android.tools.build.bundletool.model.version.BundleToolVersion;
import java.io.PrintStream;

public final class VersionCommand {
    public static final String COMMAND_NAME = "version";
    private final PrintStream out;

    private VersionCommand(PrintStream out) {
        this.out = out;
    }

    public static VersionCommand fromFlags(ParsedFlags flags, PrintStream out) {
        flags.checkNoUnknownFlags();
        return new VersionCommand(out);
    }

    public void execute() {
        this.out.println(BundleToolVersion.getCurrentVersion());
    }

    public static CommandHelp help() {
        return CommandHelp.builder().setCommandName(COMMAND_NAME).setCommandDescription(CommandHelp.CommandDescription.builder().setShortDescription("Prints the version of BundleTool.").build()).build();
    }
}

