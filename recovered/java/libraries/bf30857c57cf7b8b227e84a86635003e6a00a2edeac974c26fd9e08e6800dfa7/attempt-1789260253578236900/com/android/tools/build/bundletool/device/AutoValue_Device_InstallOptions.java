/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.device;

import com.android.tools.build.bundletool.device.Device;
import java.time.Duration;

final class AutoValue_Device_InstallOptions
extends Device.InstallOptions {
    private final boolean allowDowngrade;
    private final boolean allowReinstall;
    private final boolean allowTestOnly;
    private final Duration timeout;

    private AutoValue_Device_InstallOptions(boolean allowDowngrade, boolean allowReinstall, boolean allowTestOnly, Duration timeout) {
        this.allowDowngrade = allowDowngrade;
        this.allowReinstall = allowReinstall;
        this.allowTestOnly = allowTestOnly;
        this.timeout = timeout;
    }

    @Override
    public boolean getAllowDowngrade() {
        return this.allowDowngrade;
    }

    @Override
    public boolean getAllowReinstall() {
        return this.allowReinstall;
    }

    @Override
    public boolean getAllowTestOnly() {
        return this.allowTestOnly;
    }

    @Override
    public Duration getTimeout() {
        return this.timeout;
    }

    public String toString() {
        return "InstallOptions{allowDowngrade=" + this.allowDowngrade + ", allowReinstall=" + this.allowReinstall + ", allowTestOnly=" + this.allowTestOnly + ", timeout=" + this.timeout + "}";
    }

    public boolean equals(Object o3) {
        if (o3 == this) {
            return true;
        }
        if (o3 instanceof Device.InstallOptions) {
            Device.InstallOptions that = (Device.InstallOptions)o3;
            return this.allowDowngrade == that.getAllowDowngrade() && this.allowReinstall == that.getAllowReinstall() && this.allowTestOnly == that.getAllowTestOnly() && this.timeout.equals(that.getTimeout());
        }
        return false;
    }

    public int hashCode() {
        int h$ = 1;
        h$ *= 1000003;
        h$ ^= this.allowDowngrade ? 1231 : 1237;
        h$ *= 1000003;
        h$ ^= this.allowReinstall ? 1231 : 1237;
        h$ *= 1000003;
        h$ ^= this.allowTestOnly ? 1231 : 1237;
        h$ *= 1000003;
        return h$ ^= this.timeout.hashCode();
    }

    static final class Builder
    extends Device.InstallOptions.Builder {
        private Boolean allowDowngrade;
        private Boolean allowReinstall;
        private Boolean allowTestOnly;
        private Duration timeout;

        Builder() {
        }

        @Override
        public Device.InstallOptions.Builder setAllowDowngrade(boolean allowDowngrade) {
            this.allowDowngrade = allowDowngrade;
            return this;
        }

        @Override
        public Device.InstallOptions.Builder setAllowReinstall(boolean allowReinstall) {
            this.allowReinstall = allowReinstall;
            return this;
        }

        @Override
        public Device.InstallOptions.Builder setAllowTestOnly(boolean allowTestOnly) {
            this.allowTestOnly = allowTestOnly;
            return this;
        }

        @Override
        public Device.InstallOptions.Builder setTimeout(Duration timeout) {
            if (timeout == null) {
                throw new NullPointerException("Null timeout");
            }
            this.timeout = timeout;
            return this;
        }

        @Override
        public Device.InstallOptions build() {
            String missing = "";
            if (this.allowDowngrade == null) {
                missing = missing + " allowDowngrade";
            }
            if (this.allowReinstall == null) {
                missing = missing + " allowReinstall";
            }
            if (this.allowTestOnly == null) {
                missing = missing + " allowTestOnly";
            }
            if (this.timeout == null) {
                missing = missing + " timeout";
            }
            if (!missing.isEmpty()) {
                throw new IllegalStateException("Missing required properties:" + missing);
            }
            return new AutoValue_Device_InstallOptions(this.allowDowngrade, this.allowReinstall, this.allowTestOnly, this.timeout);
        }
    }
}

