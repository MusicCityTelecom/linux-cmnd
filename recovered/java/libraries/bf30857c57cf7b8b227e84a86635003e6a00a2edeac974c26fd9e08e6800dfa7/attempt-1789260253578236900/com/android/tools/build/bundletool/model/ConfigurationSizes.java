/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.model;

import com.android.tools.build.bundletool.model.AutoValue_ConfigurationSizes;
import com.android.tools.build.bundletool.model.SizeConfiguration;
import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableMap;
import com.google.errorprone.annotations.Immutable;

@Immutable
@AutoValue
@AutoValue.CopyAnnotations
public abstract class ConfigurationSizes {
    public abstract ImmutableMap<SizeConfiguration, Long> getMinSizeConfigurationMap();

    public abstract ImmutableMap<SizeConfiguration, Long> getMaxSizeConfigurationMap();

    public static ConfigurationSizes create(ImmutableMap<SizeConfiguration, Long> minSizeConfigurationMap, ImmutableMap<SizeConfiguration, Long> maxSizeConfigurationMap) {
        return new AutoValue_ConfigurationSizes(minSizeConfigurationMap, maxSizeConfigurationMap);
    }
}

