/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool;

import com.android.tools.build.bundletool.commands.BuildApksCommand;
import com.android.tools.build.bundletool.commands.BuildBundleCommand;
import com.android.tools.build.bundletool.commands.CommandHelp;
import com.android.tools.build.bundletool.commands.DumpCommand;
import com.android.tools.build.bundletool.commands.ExtractApksCommand;
import com.android.tools.build.bundletool.commands.GetDeviceSpecCommand;
import com.android.tools.build.bundletool.commands.GetSizeCommand;
import com.android.tools.build.bundletool.commands.InstallApksCommand;
import com.android.tools.build.bundletool.commands.ValidateBundleCommand;
import com.android.tools.build.bundletool.commands.VersionCommand;
import com.android.tools.build.bundletool.device.DdmlibAdbServer;
import com.android.tools.build.bundletool.flags.FlagParser;
import com.android.tools.build.bundletool.model.version.BundleToolVersion;
import com.google.common.collect.ImmutableList;

public class BundleToolMain {
    public static final String HELP_CMD = "help";

    public static void main(String[] args) {
        BundleToolMain.main(args, Runtime.getRuntime());
    }

    /*
     * Unable to fully structure code
     */
    static void main(String[] args, Runtime runtime) {
        try {
            flags = new FlagParser().parse(args);
        }
        catch (FlagParser.FlagParseException e) {
            System.err.println("Error while parsing the flags: " + e.getMessage());
            runtime.exit(1);
            return;
        }
        command = flags.getMainCommand();
        if (!command.isPresent()) {
            System.err.println("Error: You have to specify a command.");
            BundleToolMain.help();
            runtime.exit(1);
            return;
        }
        try {
            var4_5 = command.get();
            var5_7 = -1;
            switch (var4_5.hashCode()) {
                case 1272077505: {
                    if (!var4_5.equals("build-bundle")) break;
                    var5_7 = 0;
                    break;
                }
                case -517146154: {
                    if (!var4_5.equals("build-apks")) break;
                    var5_7 = 1;
                    break;
                }
                case 637515683: {
                    if (!var4_5.equals("extract-apks")) break;
                    var5_7 = 2;
                    break;
                }
                case 1386860763: {
                    if (!var4_5.equals("get-device-spec")) break;
                    var5_7 = 3;
                    break;
                }
                case 1988628169: {
                    if (!var4_5.equals("install-apks")) break;
                    var5_7 = 4;
                    break;
                }
                case -1421272810: {
                    if (!var4_5.equals("validate")) break;
                    var5_7 = 5;
                    break;
                }
                case 3095028: {
                    if (!var4_5.equals("dump")) break;
                    var5_7 = 6;
                    break;
                }
                case 1930467352: {
                    if (!var4_5.equals("get-size")) break;
                    var5_7 = 7;
                    break;
                }
                case 351608024: {
                    if (!var4_5.equals("version")) break;
                    var5_7 = 8;
                    break;
                }
                case 3198785: {
                    if (!var4_5.equals("help")) break;
                    var5_7 = 9;
                }
            }
            switch (var5_7) {
                case 0: {
                    BuildBundleCommand.fromFlags(flags).execute();
                    break;
                }
                case 1: {
                    adbServer = DdmlibAdbServer.getInstance();
                    var7_11 = null;
                    BuildApksCommand.fromFlags(flags, adbServer).execute();
                    if (adbServer == null) break;
                    if (var7_11 == null) ** GOTO lbl75
                    try {
                        adbServer.close();
                    }
                    catch (Throwable var8_14) {
                        var7_11.addSuppressed(var8_14);
                    }
                    break;
lbl75:
                    // 1 sources

                    adbServer.close();
                    break;
                    catch (Throwable var8_15) {
                        try {
                            var7_11 = var8_15;
                            throw var8_15;
                        }
                        catch (Throwable var9_20) {
                            if (adbServer != null) {
                                if (var7_11 != null) {
                                    try {
                                        adbServer.close();
                                    }
                                    catch (Throwable var10_21) {
                                        var7_11.addSuppressed(var10_21);
                                    }
                                } else {
                                    adbServer.close();
                                }
                            }
                            throw var9_20;
                        }
                    }
                }
                case 2: {
                    ExtractApksCommand.fromFlags(flags).execute();
                    break;
                }
                case 3: {
                    adbServer = DdmlibAdbServer.getInstance();
                    var7_12 = null;
                    GetDeviceSpecCommand.fromFlags(flags, adbServer).execute();
                    if (adbServer == null) break;
                    if (var7_12 == null) ** GOTO lbl110
                    try {
                        adbServer.close();
                    }
                    catch (Throwable var8_16) {
                        var7_12.addSuppressed(var8_16);
                    }
                    break;
lbl110:
                    // 1 sources

                    adbServer.close();
                    break;
                    catch (Throwable var8_17) {
                        try {
                            var7_12 = var8_17;
                            throw var8_17;
                        }
                        catch (Throwable var11_22) {
                            if (adbServer != null) {
                                if (var7_12 != null) {
                                    try {
                                        adbServer.close();
                                    }
                                    catch (Throwable var12_23) {
                                        var7_12.addSuppressed(var12_23);
                                    }
                                } else {
                                    adbServer.close();
                                }
                            }
                            throw var11_22;
                        }
                    }
                }
                case 4: {
                    adbServer = DdmlibAdbServer.getInstance();
                    var7_13 = null;
                    InstallApksCommand.fromFlags(flags, adbServer).execute();
                    if (adbServer == null) break;
                    if (var7_13 == null) ** GOTO lbl140
                    try {
                        adbServer.close();
                    }
                    catch (Throwable var8_18) {
                        var7_13.addSuppressed(var8_18);
                    }
                    break;
lbl140:
                    // 1 sources

                    adbServer.close();
                    break;
                    catch (Throwable var8_19) {
                        try {
                            var7_13 = var8_19;
                            throw var8_19;
                        }
                        catch (Throwable var13_24) {
                            if (adbServer != null) {
                                if (var7_13 != null) {
                                    try {
                                        adbServer.close();
                                    }
                                    catch (Throwable var14_25) {
                                        var7_13.addSuppressed(var14_25);
                                    }
                                } else {
                                    adbServer.close();
                                }
                            }
                            throw var13_24;
                        }
                    }
                }
                case 5: {
                    ValidateBundleCommand.fromFlags(flags).execute();
                    break;
                }
                case 6: {
                    DumpCommand.fromFlags(flags).execute();
                    break;
                }
                case 7: {
                    GetSizeCommand.fromFlags(flags).execute();
                    break;
                }
                case 8: {
                    VersionCommand.fromFlags(flags, System.out).execute();
                    break;
                }
                case 9: {
                    if (flags.getSubCommand().isPresent()) {
                        BundleToolMain.help(flags.getSubCommand().get(), runtime);
                        break;
                    }
                    BundleToolMain.help();
                    break;
                }
                default: {
                    System.err.printf("Error: Unrecognized command '%s'.%n%n%n", new Object[]{command.get()});
                    BundleToolMain.help();
                    runtime.exit(1);
                    return;
                }
            }
        }
        catch (Exception e) {
            System.err.println("[BT:" + BundleToolVersion.getCurrentVersion() + "] Error: " + e.getMessage());
            e.printStackTrace();
            runtime.exit(1);
            return;
        }
        runtime.exit(0);
    }

    public static void help() {
        ImmutableList<CommandHelp> commandHelps = ImmutableList.of(BuildBundleCommand.help(), BuildApksCommand.help(), ExtractApksCommand.help(), GetDeviceSpecCommand.help(), InstallApksCommand.help(), ValidateBundleCommand.help(), DumpCommand.help(), GetSizeCommand.help(), VersionCommand.help());
        System.out.println("Synopsis: bundletool <command> ...");
        System.out.println();
        System.out.println("Use 'bundletool help <command>' to learn more about the given command.");
        System.out.println();
        commandHelps.forEach(commandHelp -> commandHelp.printSummary(System.out));
    }

    public static void help(String commandName, Runtime runtime) {
        CommandHelp commandHelp;
        switch (commandName) {
            case "build-bundle": {
                commandHelp = BuildBundleCommand.help();
                break;
            }
            case "build-apks": {
                commandHelp = BuildApksCommand.help();
                break;
            }
            case "extract-apks": {
                commandHelp = ExtractApksCommand.help();
                break;
            }
            case "get-device-spec": {
                commandHelp = GetDeviceSpecCommand.help();
                break;
            }
            case "install-apks": {
                commandHelp = InstallApksCommand.help();
                break;
            }
            case "validate": {
                commandHelp = ValidateBundleCommand.help();
                break;
            }
            case "dump": {
                commandHelp = DumpCommand.help();
                break;
            }
            case "get-size": {
                commandHelp = GetSizeCommand.help();
                break;
            }
            default: {
                System.err.printf("Error: Unrecognized command '%s'.%n%n%n", commandName);
                BundleToolMain.help();
                runtime.exit(1);
                return;
            }
        }
        commandHelp.printDetails(System.out);
    }
}

