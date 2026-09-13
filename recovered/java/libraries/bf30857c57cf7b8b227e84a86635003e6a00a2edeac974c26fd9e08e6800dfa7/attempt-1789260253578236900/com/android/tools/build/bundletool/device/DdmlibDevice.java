/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.device;

import com.android.ddmlib.AdbCommandRejectedException;
import com.android.ddmlib.IDevice;
import com.android.ddmlib.IShellOutputReceiver;
import com.android.ddmlib.InstallException;
import com.android.ddmlib.MultiLineReceiver;
import com.android.ddmlib.ShellCommandUnresponsiveException;
import com.android.ddmlib.SyncException;
import com.android.ddmlib.TimeoutException;
import com.android.sdklib.AndroidVersion;
import com.android.tools.build.bundletool.device.AdbShellCommandTask;
import com.android.tools.build.bundletool.device.Device;
import com.android.tools.build.bundletool.device.DeviceFeaturesParser;
import com.android.tools.build.bundletool.device.GlExtensionsParser;
import com.android.tools.build.bundletool.model.exceptions.CommandExecutionException;
import com.android.tools.build.bundletool.model.exceptions.InstallationException;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.errorprone.annotations.FormatMethod;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class DdmlibDevice
extends Device {
    private final IDevice device;
    private static final int ADB_TIMEOUT_MS = 60000;
    private static final String DEVICE_FEATURES_COMMAND = "pm list features";
    private static final String GL_EXTENSIONS_COMMAND = "dumpsys SurfaceFlinger";
    private final DeviceFeaturesParser deviceFeaturesParser = new DeviceFeaturesParser();
    private final GlExtensionsParser glExtensionsParser = new GlExtensionsParser();

    public DdmlibDevice(IDevice device) {
        this.device = device;
    }

    @Override
    public IDevice.DeviceState getState() {
        return this.device.getState();
    }

    @Override
    public AndroidVersion getVersion() {
        return this.device.getVersion();
    }

    @Override
    public ImmutableList<String> getAbis() {
        return ImmutableList.copyOf(this.device.getAbis());
    }

    @Override
    public int getDensity() {
        return this.device.getDensity();
    }

    @Override
    public String getSerialNumber() {
        return this.device.getSerialNumber();
    }

    @Override
    public Optional<String> getProperty(String propertyName) {
        return Optional.ofNullable(this.device.getProperty(propertyName));
    }

    @Override
    public ImmutableList<String> getDeviceFeatures() {
        return this.deviceFeaturesParser.parse(new AdbShellCommandTask(this, DEVICE_FEATURES_COMMAND).execute(60000L, TimeUnit.MILLISECONDS));
    }

    @Override
    public ImmutableList<String> getGlExtensions() {
        return this.glExtensionsParser.parse(new AdbShellCommandTask(this, GL_EXTENSIONS_COMMAND).execute(60000L, TimeUnit.MILLISECONDS));
    }

    @Override
    public void executeShellCommand(String command, IShellOutputReceiver receiver, long maxTimeToOutputResponse, TimeUnit maxTimeUnits) throws TimeoutException, AdbCommandRejectedException, ShellCommandUnresponsiveException, IOException {
        this.device.executeShellCommand(command, receiver, maxTimeToOutputResponse, maxTimeUnits);
    }

    @Override
    public void installApks(ImmutableList<Path> apks, Device.InstallOptions installOptions) {
        ImmutableList<File> apkFiles = apks.stream().map(Path::toFile).collect(ImmutableList.toImmutableList());
        ImmutableList.Builder extraArgs = ImmutableList.builder();
        if (installOptions.getAllowDowngrade()) {
            extraArgs.add("-d");
        }
        if (installOptions.getAllowTestOnly()) {
            extraArgs.add("-t");
        }
        try {
            if (this.getVersion().isGreaterOrEqualThan(AndroidVersion.ALLOW_SPLIT_APK_INSTALLATION.getApiLevel())) {
                this.device.installPackages(apkFiles, installOptions.getAllowReinstall(), (List<String>)((Object)extraArgs.build()), installOptions.getTimeout().toMillis(), TimeUnit.MILLISECONDS);
            } else {
                this.device.installPackage(((File)Iterables.getOnlyElement(apkFiles)).toString(), installOptions.getAllowReinstall(), extraArgs.build().toArray(new String[0]));
            }
        }
        catch (InstallException e2) {
            throw InstallationException.builder().withCause(e2).withMessage("Installation of the app failed.").build();
        }
    }

    @Override
    public void pushApks(ImmutableList<Path> apks, Device.PushOptions pushOptions) {
        String splitsPath = pushOptions.getDestinationPath();
        Preconditions.checkArgument(!splitsPath.isEmpty(), "Splits path cannot be empty.");
        RemoteCommandExecutor commandExecutor = new RemoteCommandExecutor(this, pushOptions.getTimeout().toMillis(), System.err);
        try {
            if (!splitsPath.startsWith("/")) {
                String packageName = pushOptions.getPackageName().orElseThrow(() -> new CommandExecutionException("PushOptions.packageName must be set for relative paths."));
                splitsPath = DdmlibDevice.joinUnixPaths("/sdcard/Android/data/", packageName, "files", splitsPath);
            }
            if (pushOptions.getClearDestinationPath()) {
                commandExecutor.executeAndPrint("rm -rf %s", new String[]{splitsPath});
            }
            commandExecutor.executeAndPrint("mkdir -p %s && rmdir %s && mkdir -p %s", new String[]{splitsPath, splitsPath, splitsPath});
            for (Path path : apks) {
                this.device.pushFile(path.toFile().getAbsolutePath(), DdmlibDevice.joinUnixPaths(splitsPath, path.getFileName().toString()));
                System.err.printf("Pushed \"%s\"%n", DdmlibDevice.joinUnixPaths(splitsPath, path.getFileName().toString()));
            }
        }
        catch (AdbCommandRejectedException | ShellCommandUnresponsiveException | SyncException | TimeoutException | IOException e2) {
            throw CommandExecutionException.builder().withCause(e2).withMessage("Pushing additional splits for local testing failed. Your app might still have been installed correctly, but you won't be able to test dynamic modules.").build();
        }
    }

    static String joinUnixPaths(String ... parts) {
        StringBuilder sb = new StringBuilder();
        for (String part : parts) {
            if (sb.length() > 0 && sb.charAt(sb.length() - 1) != '/') {
                sb.append('/');
            }
            sb.append(part);
        }
        return sb.toString();
    }

    static class RemoteCommandExecutor {
        private final Device device;
        private final MultiLineReceiver receiver;
        private final long timeout;
        private final PrintStream out;
        private String lastOutputLine;

        RemoteCommandExecutor(Device device, long timeout, final PrintStream out) {
            this.device = device;
            this.timeout = timeout;
            this.out = out;
            this.receiver = new MultiLineReceiver(){

                @Override
                public void processNewLines(String[] lines) {
                    for (String line : lines) {
                        if (line.isEmpty()) continue;
                        out.println("ADB >> " + line);
                        lastOutputLine = line;
                    }
                }

                @Override
                public boolean isCancelled() {
                    return false;
                }
            };
        }

        @FormatMethod
        private void executeAndPrint(String commandFormat, String ... args) throws TimeoutException, AdbCommandRejectedException, ShellCommandUnresponsiveException, IOException {
            String command = RemoteCommandExecutor.formatCommandWithArgs(commandFormat, args);
            this.lastOutputLine = null;
            this.out.println("ADB << " + command);
            this.device.executeShellCommand(command + " && echo OK", this.receiver, this.timeout, TimeUnit.MILLISECONDS);
            if (!"OK".equals(this.lastOutputLine)) {
                throw new IOException("ADB command failed.");
            }
        }

        static String escapeAndSingleQuote(String string) {
            return "'" + string.replace("'", "'\\''") + "'";
        }

        @FormatMethod
        static String formatCommandWithArgs(String command, String ... args) {
            return String.format(command, Arrays.stream(args).map(RemoteCommandExecutor::escapeAndSingleQuote).toArray());
        }
    }
}

