/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.model;

import com.android.tools.build.bundletool.model.DeviceFeatureCondition;
import com.android.tools.build.bundletool.model.ModuleConditions;
import com.android.tools.build.bundletool.model.UserCountriesCondition;
import com.google.common.collect.ImmutableList;
import java.util.Optional;

final class AutoValue_ModuleConditions
extends ModuleConditions {
    private final ImmutableList<DeviceFeatureCondition> deviceFeatureConditions;
    private final Optional<Integer> minSdkVersion;
    private final Optional<Integer> maxSdkVersion;
    private final Optional<UserCountriesCondition> userCountriesCondition;

    private AutoValue_ModuleConditions(ImmutableList<DeviceFeatureCondition> deviceFeatureConditions, Optional<Integer> minSdkVersion, Optional<Integer> maxSdkVersion, Optional<UserCountriesCondition> userCountriesCondition) {
        this.deviceFeatureConditions = deviceFeatureConditions;
        this.minSdkVersion = minSdkVersion;
        this.maxSdkVersion = maxSdkVersion;
        this.userCountriesCondition = userCountriesCondition;
    }

    @Override
    public ImmutableList<DeviceFeatureCondition> getDeviceFeatureConditions() {
        return this.deviceFeatureConditions;
    }

    @Override
    public Optional<Integer> getMinSdkVersion() {
        return this.minSdkVersion;
    }

    @Override
    public Optional<Integer> getMaxSdkVersion() {
        return this.maxSdkVersion;
    }

    @Override
    public Optional<UserCountriesCondition> getUserCountriesCondition() {
        return this.userCountriesCondition;
    }

    public String toString() {
        return "ModuleConditions{deviceFeatureConditions=" + this.deviceFeatureConditions + ", minSdkVersion=" + this.minSdkVersion + ", maxSdkVersion=" + this.maxSdkVersion + ", userCountriesCondition=" + this.userCountriesCondition + "}";
    }

    public boolean equals(Object o3) {
        if (o3 == this) {
            return true;
        }
        if (o3 instanceof ModuleConditions) {
            ModuleConditions that = (ModuleConditions)o3;
            return this.deviceFeatureConditions.equals(that.getDeviceFeatureConditions()) && this.minSdkVersion.equals(that.getMinSdkVersion()) && this.maxSdkVersion.equals(that.getMaxSdkVersion()) && this.userCountriesCondition.equals(that.getUserCountriesCondition());
        }
        return false;
    }

    public int hashCode() {
        int h$ = 1;
        h$ *= 1000003;
        h$ ^= this.deviceFeatureConditions.hashCode();
        h$ *= 1000003;
        h$ ^= this.minSdkVersion.hashCode();
        h$ *= 1000003;
        h$ ^= this.maxSdkVersion.hashCode();
        h$ *= 1000003;
        return h$ ^= this.userCountriesCondition.hashCode();
    }

    static final class Builder
    extends ModuleConditions.Builder {
        private ImmutableList.Builder<DeviceFeatureCondition> deviceFeatureConditionsBuilder$;
        private ImmutableList<DeviceFeatureCondition> deviceFeatureConditions;
        private Optional<Integer> minSdkVersion = Optional.empty();
        private Optional<Integer> maxSdkVersion = Optional.empty();
        private Optional<UserCountriesCondition> userCountriesCondition = Optional.empty();

        Builder() {
        }

        @Override
        ImmutableList.Builder<DeviceFeatureCondition> deviceFeatureConditionsBuilder() {
            if (this.deviceFeatureConditionsBuilder$ == null) {
                this.deviceFeatureConditionsBuilder$ = ImmutableList.builder();
            }
            return this.deviceFeatureConditionsBuilder$;
        }

        @Override
        public ModuleConditions.Builder setMinSdkVersion(int minSdkVersion) {
            this.minSdkVersion = Optional.of(minSdkVersion);
            return this;
        }

        @Override
        public ModuleConditions.Builder setMaxSdkVersion(int maxSdkVersion) {
            this.maxSdkVersion = Optional.of(maxSdkVersion);
            return this;
        }

        @Override
        public ModuleConditions.Builder setUserCountriesCondition(UserCountriesCondition userCountriesCondition) {
            this.userCountriesCondition = Optional.of(userCountriesCondition);
            return this;
        }

        @Override
        protected ModuleConditions autoBuild() {
            if (this.deviceFeatureConditionsBuilder$ != null) {
                this.deviceFeatureConditions = this.deviceFeatureConditionsBuilder$.build();
            } else if (this.deviceFeatureConditions == null) {
                this.deviceFeatureConditions = ImmutableList.of();
            }
            return new AutoValue_ModuleConditions(this.deviceFeatureConditions, this.minSdkVersion, this.maxSdkVersion, this.userCountriesCondition);
        }
    }
}

