/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.device;

import com.android.tools.build.bundletool.device.Device;
import java.time.Duration;
import java.util.Optional;

final class AutoValue_Device_PushOptions
extends Device.PushOptions {
    private final String destinationPath;
    private final Duration timeout;
    private final Optional<String> packageName;
    private final boolean clearDestinationPath;

    private AutoValue_Device_PushOptions(String destinationPath, Duration timeout, Optional<String> packageName, boolean clearDestinationPath) {
        this.destinationPath = destinationPath;
        this.timeout = timeout;
        this.packageName = packageName;
        this.clearDestinationPath = clearDestinationPath;
    }

    @Override
    public String getDestinationPath() {
        return this.destinationPath;
    }

    @Override
    public Duration getTimeout() {
        return this.timeout;
    }

    @Override
    public Optional<String> getPackageName() {
        return this.packageName;
    }

    @Override
    public boolean getClearDestinationPath() {
        return this.clearDestinationPath;
    }

    public String toString() {
        return "PushOptions{destinationPath=" + this.destinationPath + ", timeout=" + this.timeout + ", packageName=" + this.packageName + ", clearDestinationPath=" + this.clearDestinationPath + "}";
    }

    public boolean equals(Object o3) {
        if (o3 == this) {
            return true;
        }
        if (o3 instanceof Device.PushOptions) {
            Device.PushOptions that = (Device.PushOptions)o3;
            return this.destinationPath.equals(that.getDestinationPath()) && this.timeout.equals(that.getTimeout()) && this.packageName.equals(that.getPackageName()) && this.clearDestinationPath == that.getClearDestinationPath();
        }
        return false;
    }

    public int hashCode() {
        int h$ = 1;
        h$ *= 1000003;
        h$ ^= this.destinationPath.hashCode();
        h$ *= 1000003;
        h$ ^= this.timeout.hashCode();
        h$ *= 1000003;
        h$ ^= this.packageName.hashCode();
        h$ *= 1000003;
        return h$ ^= this.clearDestinationPath ? 1231 : 1237;
    }

    static final class Builder
    extends Device.PushOptions.Builder {
        private String destinationPath;
        private Duration timeout;
        private Optional<String> packageName = Optional.empty();
        private Boolean clearDestinationPath;

        Builder() {
        }

        @Override
        public Device.PushOptions.Builder setDestinationPath(String destinationPath) {
            if (destinationPath == null) {
                throw new NullPointerException("Null destinationPath");
            }
            this.destinationPath = destinationPath;
            return this;
        }

        @Override
        public Device.PushOptions.Builder setTimeout(Duration timeout) {
            if (timeout == null) {
                throw new NullPointerException("Null timeout");
            }
            this.timeout = timeout;
            return this;
        }

        @Override
        public Device.PushOptions.Builder setPackageName(String packageName) {
            this.packageName = Optional.of(packageName);
            return this;
        }

        @Override
        public Device.PushOptions.Builder setClearDestinationPath(boolean clearDestinationPath) {
            this.clearDestinationPath = clearDestinationPath;
            return this;
        }

        @Override
        public Device.PushOptions build() {
            String missing = "";
            if (this.destinationPath == null) {
                missing = missing + " destinationPath";
            }
            if (this.timeout == null) {
                missing = missing + " timeout";
            }
            if (this.clearDestinationPath == null) {
                missing = missing + " clearDestinationPath";
            }
            if (!missing.isEmpty()) {
                throw new IllegalStateException("Missing required properties:" + missing);
            }
            return new AutoValue_Device_PushOptions(this.destinationPath, this.timeout, this.packageName, this.clearDestinationPath);
        }
    }
}

