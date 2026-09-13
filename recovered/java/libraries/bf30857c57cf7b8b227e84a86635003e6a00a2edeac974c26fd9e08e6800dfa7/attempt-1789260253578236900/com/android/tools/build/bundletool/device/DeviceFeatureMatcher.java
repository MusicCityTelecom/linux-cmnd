/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.device;

import com.android.bundle.Devices;
import com.android.bundle.Targeting;
import com.android.tools.build.bundletool.device.TargetingDimensionMatcher;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

public class DeviceFeatureMatcher
extends TargetingDimensionMatcher<ImmutableList<Targeting.DeviceFeatureTargeting>> {
    private final ImmutableSet<String> deviceFeatures;

    public DeviceFeatureMatcher(Devices.DeviceSpec deviceSpec) {
        super(deviceSpec);
        this.deviceFeatures = ImmutableSet.copyOf(deviceSpec.getDeviceFeaturesList());
    }

    @Override
    public boolean matchesTargeting(ImmutableList<Targeting.DeviceFeatureTargeting> targetingValue) {
        ImmutableSet requiredFeatureSet = targetingValue.stream().map(Targeting.DeviceFeatureTargeting::getRequiredFeature).map(Targeting.DeviceFeature::getFeatureName).collect(ImmutableSet.toImmutableSet());
        return Sets.difference(requiredFeatureSet, this.deviceFeatures).isEmpty();
    }

    @Override
    protected ImmutableList<Targeting.DeviceFeatureTargeting> getTargetingValue(Targeting.ModuleTargeting moduleTargeting) {
        return ImmutableList.copyOf(moduleTargeting.getDeviceFeatureTargetingList());
    }

    @Override
    protected boolean isDeviceDimensionPresent() {
        return !this.getDeviceSpec().getDeviceFeaturesList().isEmpty();
    }

    @Override
    protected void checkDeviceCompatibleInternal(ImmutableList<Targeting.DeviceFeatureTargeting> targetingValue) {
    }
}

