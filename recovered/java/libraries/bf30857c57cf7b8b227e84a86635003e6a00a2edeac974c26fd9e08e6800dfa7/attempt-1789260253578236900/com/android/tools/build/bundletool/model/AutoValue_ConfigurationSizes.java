/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.model;

import com.android.tools.build.bundletool.model.ConfigurationSizes;
import com.android.tools.build.bundletool.model.SizeConfiguration;
import com.google.common.collect.ImmutableMap;

final class AutoValue_ConfigurationSizes
extends ConfigurationSizes {
    private final ImmutableMap<SizeConfiguration, Long> minSizeConfigurationMap;
    private final ImmutableMap<SizeConfiguration, Long> maxSizeConfigurationMap;

    AutoValue_ConfigurationSizes(ImmutableMap<SizeConfiguration, Long> minSizeConfigurationMap, ImmutableMap<SizeConfiguration, Long> maxSizeConfigurationMap) {
        if (minSizeConfigurationMap == null) {
            throw new NullPointerException("Null minSizeConfigurationMap");
        }
        this.minSizeConfigurationMap = minSizeConfigurationMap;
        if (maxSizeConfigurationMap == null) {
            throw new NullPointerException("Null maxSizeConfigurationMap");
        }
        this.maxSizeConfigurationMap = maxSizeConfigurationMap;
    }

    @Override
    public ImmutableMap<SizeConfiguration, Long> getMinSizeConfigurationMap() {
        return this.minSizeConfigurationMap;
    }

    @Override
    public ImmutableMap<SizeConfiguration, Long> getMaxSizeConfigurationMap() {
        return this.maxSizeConfigurationMap;
    }

    public String toString() {
        return "ConfigurationSizes{minSizeConfigurationMap=" + this.minSizeConfigurationMap + ", maxSizeConfigurationMap=" + this.maxSizeConfigurationMap + "}";
    }

    public boolean equals(Object o3) {
        if (o3 == this) {
            return true;
        }
        if (o3 instanceof ConfigurationSizes) {
            ConfigurationSizes that = (ConfigurationSizes)o3;
            return this.minSizeConfigurationMap.equals(that.getMinSizeConfigurationMap()) && this.maxSizeConfigurationMap.equals(that.getMaxSizeConfigurationMap());
        }
        return false;
    }

    public int hashCode() {
        int h$ = 1;
        h$ *= 1000003;
        h$ ^= this.minSizeConfigurationMap.hashCode();
        h$ *= 1000003;
        return h$ ^= this.maxSizeConfigurationMap.hashCode();
    }
}

