/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.splitters;

import com.android.bundle.Config;
import com.android.bundle.Devices;
import com.android.tools.build.bundletool.model.OptimizationDimension;
import com.android.tools.build.bundletool.splitters.AutoValue_BundleSharderConfiguration;
import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableMap;
import com.google.errorprone.annotations.Immutable;
import java.util.Optional;

@Immutable
@AutoValue
@AutoValue.CopyAnnotations
public abstract class BundleSharderConfiguration {
    public abstract boolean getStrip64BitLibrariesFromShards();

    public abstract Optional<Devices.DeviceSpec> getDeviceSpec();

    public abstract ImmutableMap<OptimizationDimension, Config.SuffixStripping> getSuffixStrippings();

    public abstract Builder toBuilder();

    public static Builder builder() {
        return new AutoValue_BundleSharderConfiguration.Builder().setStrip64BitLibrariesFromShards(false).setSuffixStrippings(ImmutableMap.of());
    }

    public static BundleSharderConfiguration getDefaultInstance() {
        return BundleSharderConfiguration.builder().build();
    }

    public boolean splitByLanguage() {
        return this.getDeviceSpec().map(spec -> !spec.getSupportedLocalesList().isEmpty()).orElse(false);
    }

    BundleSharderConfiguration() {
    }

    @AutoValue.Builder
    public static abstract class Builder {
        public abstract Builder setStrip64BitLibrariesFromShards(boolean var1);

        public abstract Builder setDeviceSpec(Optional<Devices.DeviceSpec> var1);

        public abstract Builder setSuffixStrippings(ImmutableMap<OptimizationDimension, Config.SuffixStripping> var1);

        abstract BundleSharderConfiguration build();
    }
}

