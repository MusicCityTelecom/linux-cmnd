/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.splitters;

import com.android.bundle.Config;
import com.android.bundle.Devices;
import com.android.tools.build.bundletool.model.OptimizationDimension;
import com.android.tools.build.bundletool.splitters.BundleSharderConfiguration;
import com.google.common.collect.ImmutableMap;
import java.util.Optional;

final class AutoValue_BundleSharderConfiguration
extends BundleSharderConfiguration {
    private final boolean strip64BitLibrariesFromShards;
    private final Optional<Devices.DeviceSpec> deviceSpec;
    private final ImmutableMap<OptimizationDimension, Config.SuffixStripping> suffixStrippings;

    private AutoValue_BundleSharderConfiguration(boolean strip64BitLibrariesFromShards, Optional<Devices.DeviceSpec> deviceSpec, ImmutableMap<OptimizationDimension, Config.SuffixStripping> suffixStrippings) {
        this.strip64BitLibrariesFromShards = strip64BitLibrariesFromShards;
        this.deviceSpec = deviceSpec;
        this.suffixStrippings = suffixStrippings;
    }

    @Override
    public boolean getStrip64BitLibrariesFromShards() {
        return this.strip64BitLibrariesFromShards;
    }

    @Override
    public Optional<Devices.DeviceSpec> getDeviceSpec() {
        return this.deviceSpec;
    }

    @Override
    public ImmutableMap<OptimizationDimension, Config.SuffixStripping> getSuffixStrippings() {
        return this.suffixStrippings;
    }

    public String toString() {
        return "BundleSharderConfiguration{strip64BitLibrariesFromShards=" + this.strip64BitLibrariesFromShards + ", deviceSpec=" + this.deviceSpec + ", suffixStrippings=" + this.suffixStrippings + "}";
    }

    public boolean equals(Object o3) {
        if (o3 == this) {
            return true;
        }
        if (o3 instanceof BundleSharderConfiguration) {
            BundleSharderConfiguration that = (BundleSharderConfiguration)o3;
            return this.strip64BitLibrariesFromShards == that.getStrip64BitLibrariesFromShards() && this.deviceSpec.equals(that.getDeviceSpec()) && this.suffixStrippings.equals(that.getSuffixStrippings());
        }
        return false;
    }

    public int hashCode() {
        int h$ = 1;
        h$ *= 1000003;
        h$ ^= this.strip64BitLibrariesFromShards ? 1231 : 1237;
        h$ *= 1000003;
        h$ ^= this.deviceSpec.hashCode();
        h$ *= 1000003;
        return h$ ^= this.suffixStrippings.hashCode();
    }

    @Override
    public BundleSharderConfiguration.Builder toBuilder() {
        return new Builder(this);
    }

    static final class Builder
    extends BundleSharderConfiguration.Builder {
        private Boolean strip64BitLibrariesFromShards;
        private Optional<Devices.DeviceSpec> deviceSpec = Optional.empty();
        private ImmutableMap<OptimizationDimension, Config.SuffixStripping> suffixStrippings;

        Builder() {
        }

        private Builder(BundleSharderConfiguration source) {
            this.strip64BitLibrariesFromShards = source.getStrip64BitLibrariesFromShards();
            this.deviceSpec = source.getDeviceSpec();
            this.suffixStrippings = source.getSuffixStrippings();
        }

        @Override
        public BundleSharderConfiguration.Builder setStrip64BitLibrariesFromShards(boolean strip64BitLibrariesFromShards) {
            this.strip64BitLibrariesFromShards = strip64BitLibrariesFromShards;
            return this;
        }

        @Override
        public BundleSharderConfiguration.Builder setDeviceSpec(Optional<Devices.DeviceSpec> deviceSpec) {
            if (deviceSpec == null) {
                throw new NullPointerException("Null deviceSpec");
            }
            this.deviceSpec = deviceSpec;
            return this;
        }

        @Override
        public BundleSharderConfiguration.Builder setSuffixStrippings(ImmutableMap<OptimizationDimension, Config.SuffixStripping> suffixStrippings) {
            if (suffixStrippings == null) {
                throw new NullPointerException("Null suffixStrippings");
            }
            this.suffixStrippings = suffixStrippings;
            return this;
        }

        @Override
        BundleSharderConfiguration build() {
            String missing = "";
            if (this.strip64BitLibrariesFromShards == null) {
                missing = missing + " strip64BitLibrariesFromShards";
            }
            if (this.suffixStrippings == null) {
                missing = missing + " suffixStrippings";
            }
            if (!missing.isEmpty()) {
                throw new IllegalStateException("Missing required properties:" + missing);
            }
            return new AutoValue_BundleSharderConfiguration(this.strip64BitLibrariesFromShards, this.deviceSpec, this.suffixStrippings);
        }
    }
}

