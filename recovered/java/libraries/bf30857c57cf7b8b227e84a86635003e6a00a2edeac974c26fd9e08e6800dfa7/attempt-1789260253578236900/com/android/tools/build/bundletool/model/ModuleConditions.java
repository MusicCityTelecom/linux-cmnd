/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.model;

import com.android.bundle.Targeting;
import com.android.tools.build.bundletool.model.AutoValue_ModuleConditions;
import com.android.tools.build.bundletool.model.DeviceFeatureCondition;
import com.android.tools.build.bundletool.model.UserCountriesCondition;
import com.android.tools.build.bundletool.model.exceptions.ValidationException;
import com.android.tools.build.bundletool.model.utils.TargetingProtoUtils;
import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;
import com.google.errorprone.annotations.Immutable;
import java.util.HashSet;
import java.util.Optional;

@Immutable
@AutoValue
@AutoValue.CopyAnnotations
public abstract class ModuleConditions {
    public abstract ImmutableList<DeviceFeatureCondition> getDeviceFeatureConditions();

    public abstract Optional<Integer> getMinSdkVersion();

    public abstract Optional<Integer> getMaxSdkVersion();

    public abstract Optional<UserCountriesCondition> getUserCountriesCondition();

    public boolean isEmpty() {
        return this.toTargeting().equals(Targeting.ModuleTargeting.getDefaultInstance());
    }

    public static Builder builder() {
        return new AutoValue_ModuleConditions.Builder();
    }

    public Targeting.ModuleTargeting toTargeting() {
        Targeting.ModuleTargeting.Builder moduleTargeting = Targeting.ModuleTargeting.newBuilder();
        for (DeviceFeatureCondition deviceFeatureCondition : this.getDeviceFeatureConditions()) {
            Targeting.DeviceFeature.Builder feature = Targeting.DeviceFeature.newBuilder().setFeatureName(deviceFeatureCondition.getFeatureName());
            deviceFeatureCondition.getFeatureVersion().ifPresent(feature::setFeatureVersion);
            moduleTargeting.addDeviceFeatureTargeting(Targeting.DeviceFeatureTargeting.newBuilder().setRequiredFeature(feature));
        }
        Optional<Integer> effectiveMinSdk = this.getMinSdkVersion().isPresent() ? this.getMinSdkVersion() : this.getMaxSdkVersion().map(unused -> 1);
        effectiveMinSdk.map(TargetingProtoUtils::sdkVersionFrom).map(TargetingProtoUtils::sdkVersionTargeting).ifPresent(moduleTargeting::setSdkVersionTargeting);
        this.getMaxSdkVersion().map(sdk -> sdk + 1).map(TargetingProtoUtils::sdkVersionFrom).ifPresent(maxSdkVersion -> moduleTargeting.getSdkVersionTargetingBuilder().addAlternatives((Targeting.SdkVersion)maxSdkVersion));
        if (this.getUserCountriesCondition().isPresent()) {
            UserCountriesCondition userCountriesCondition = this.getUserCountriesCondition().get();
            moduleTargeting.setUserCountriesTargeting(Targeting.UserCountriesTargeting.newBuilder().addAllCountryCodes(userCountriesCondition.getCountries()).setExclude(userCountriesCondition.getExclude()).build());
        }
        return moduleTargeting.build();
    }

    @AutoValue.Builder
    public static abstract class Builder {
        abstract ImmutableList.Builder<DeviceFeatureCondition> deviceFeatureConditionsBuilder();

        public Builder addDeviceFeatureCondition(DeviceFeatureCondition deviceFeatureCondition) {
            this.deviceFeatureConditionsBuilder().add((Object)deviceFeatureCondition);
            return this;
        }

        public abstract Builder setMinSdkVersion(int var1);

        public abstract Builder setMaxSdkVersion(int var1);

        public abstract Builder setUserCountriesCondition(UserCountriesCondition var1);

        protected abstract ModuleConditions autoBuild();

        public ModuleConditions build() {
            ModuleConditions moduleConditions = this.autoBuild();
            HashSet<String> featureNames = new HashSet<String>();
            for (DeviceFeatureCondition condition : moduleConditions.getDeviceFeatureConditions()) {
                if (featureNames.add(condition.getFeatureName())) continue;
                throw ValidationException.builder().withMessage("The device feature condition on '%s' is present more than once.", condition.getFeatureName()).build();
            }
            return moduleConditions;
        }
    }
}

