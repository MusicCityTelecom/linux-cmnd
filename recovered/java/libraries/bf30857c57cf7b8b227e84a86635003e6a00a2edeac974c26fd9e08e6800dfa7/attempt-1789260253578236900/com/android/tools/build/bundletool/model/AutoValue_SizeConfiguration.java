/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.model;

import com.android.tools.build.bundletool.model.SizeConfiguration;
import java.util.Optional;

final class AutoValue_SizeConfiguration
extends SizeConfiguration {
    private final Optional<String> abi;
    private final Optional<String> locale;
    private final Optional<String> screenDensity;
    private final Optional<String> sdkVersion;

    private AutoValue_SizeConfiguration(Optional<String> abi, Optional<String> locale, Optional<String> screenDensity, Optional<String> sdkVersion) {
        this.abi = abi;
        this.locale = locale;
        this.screenDensity = screenDensity;
        this.sdkVersion = sdkVersion;
    }

    @Override
    public Optional<String> getAbi() {
        return this.abi;
    }

    @Override
    public Optional<String> getLocale() {
        return this.locale;
    }

    @Override
    public Optional<String> getScreenDensity() {
        return this.screenDensity;
    }

    @Override
    public Optional<String> getSdkVersion() {
        return this.sdkVersion;
    }

    public String toString() {
        return "SizeConfiguration{abi=" + this.abi + ", locale=" + this.locale + ", screenDensity=" + this.screenDensity + ", sdkVersion=" + this.sdkVersion + "}";
    }

    public boolean equals(Object o3) {
        if (o3 == this) {
            return true;
        }
        if (o3 instanceof SizeConfiguration) {
            SizeConfiguration that = (SizeConfiguration)o3;
            return this.abi.equals(that.getAbi()) && this.locale.equals(that.getLocale()) && this.screenDensity.equals(that.getScreenDensity()) && this.sdkVersion.equals(that.getSdkVersion());
        }
        return false;
    }

    public int hashCode() {
        int h$ = 1;
        h$ *= 1000003;
        h$ ^= this.abi.hashCode();
        h$ *= 1000003;
        h$ ^= this.locale.hashCode();
        h$ *= 1000003;
        h$ ^= this.screenDensity.hashCode();
        h$ *= 1000003;
        return h$ ^= this.sdkVersion.hashCode();
    }

    @Override
    public SizeConfiguration.Builder toBuilder() {
        return new Builder(this);
    }

    static final class Builder
    extends SizeConfiguration.Builder {
        private Optional<String> abi = Optional.empty();
        private Optional<String> locale = Optional.empty();
        private Optional<String> screenDensity = Optional.empty();
        private Optional<String> sdkVersion = Optional.empty();

        Builder() {
        }

        private Builder(SizeConfiguration source) {
            this.abi = source.getAbi();
            this.locale = source.getLocale();
            this.screenDensity = source.getScreenDensity();
            this.sdkVersion = source.getSdkVersion();
        }

        @Override
        public SizeConfiguration.Builder setAbi(String abi) {
            this.abi = Optional.of(abi);
            return this;
        }

        @Override
        public SizeConfiguration.Builder setLocale(String locale) {
            this.locale = Optional.of(locale);
            return this;
        }

        @Override
        public SizeConfiguration.Builder setScreenDensity(String screenDensity) {
            this.screenDensity = Optional.of(screenDensity);
            return this;
        }

        @Override
        public SizeConfiguration.Builder setSdkVersion(String sdkVersion) {
            this.sdkVersion = Optional.of(sdkVersion);
            return this;
        }

        @Override
        public SizeConfiguration build() {
            return new AutoValue_SizeConfiguration(this.abi, this.locale, this.screenDensity, this.sdkVersion);
        }
    }
}

