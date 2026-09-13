/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.device;

import com.android.ddmlib.AdbCommandRejectedException;
import com.android.ddmlib.IDevice;
import com.android.ddmlib.IShellOutputReceiver;
import com.android.ddmlib.ShellCommandUnresponsiveException;
import com.android.ddmlib.TimeoutException;
import com.android.sdklib.AndroidVersion;
import com.android.tools.build.bundletool.device.AutoValue_Device_InstallOptions;
import com.android.tools.build.bundletool.device.AutoValue_Device_PushOptions;
import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;
import com.google.errorprone.annotations.Immutable;
import java.io.IOException;
import java.nio.file.Path;
import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public abstract class Device {
    private static final Duration DEFAULT_ADB_TIMEOUT = Duration.ofMinutes(10L);

    public abstract IDevice.DeviceState getState();

    public abstract AndroidVersion getVersion();

    public abstract ImmutableList<String> getAbis();

    public abstract int getDensity();

    public abstract String getSerialNumber();

    public abstract Optional<String> getProperty(String var1);

    public abstract ImmutableList<String> getDeviceFeatures();

    public abstract ImmutableList<String> getGlExtensions();

    public abstract void executeShellCommand(String var1, IShellOutputReceiver var2, long var3, TimeUnit var5) throws TimeoutException, AdbCommandRejectedException, ShellCommandUnresponsiveException, IOException;

    public abstract void installApks(ImmutableList<Path> var1, InstallOptions var2);

    public abstract void pushApks(ImmutableList<Path> var1, PushOptions var2);

    @Immutable
    @AutoValue
    @AutoValue.CopyAnnotations
    public static abstract class PushOptions {
        public abstract String getDestinationPath();

        public abstract Duration getTimeout();

        public abstract Optional<String> getPackageName();

        public abstract boolean getClearDestinationPath();

        public static Builder builder() {
            return new AutoValue_Device_PushOptions.Builder().setTimeout(DEFAULT_ADB_TIMEOUT).setClearDestinationPath(true);
        }

        @AutoValue.Builder
        public static abstract class Builder {
            public abstract Builder setDestinationPath(String var1);

            public abstract Builder setTimeout(Duration var1);

            public abstract Builder setPackageName(String var1);

            public abstract Builder setClearDestinationPath(boolean var1);

            public abstract PushOptions build();
        }
    }

    @Immutable
    @AutoValue
    @AutoValue.CopyAnnotations
    public static abstract class InstallOptions {
        public abstract boolean getAllowDowngrade();

        public abstract boolean getAllowReinstall();

        public abstract boolean getAllowTestOnly();

        public abstract Duration getTimeout();

        public static Builder builder() {
            return new AutoValue_Device_InstallOptions.Builder().setTimeout(DEFAULT_ADB_TIMEOUT).setAllowReinstall(true).setAllowDowngrade(false).setAllowTestOnly(false);
        }

        @AutoValue.Builder
        public static abstract class Builder {
            public abstract Builder setAllowDowngrade(boolean var1);

            public abstract Builder setAllowReinstall(boolean var1);

            public abstract Builder setTimeout(Duration var1);

            public abstract Builder setAllowTestOnly(boolean var1);

            public abstract InstallOptions build();
        }
    }
}

