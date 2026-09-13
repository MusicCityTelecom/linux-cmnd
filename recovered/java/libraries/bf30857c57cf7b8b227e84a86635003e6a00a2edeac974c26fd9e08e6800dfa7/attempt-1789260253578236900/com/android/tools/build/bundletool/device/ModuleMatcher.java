/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.device;

import com.android.bundle.Devices;
import com.android.bundle.Targeting;
import com.android.tools.build.bundletool.device.DeviceFeatureMatcher;
import com.android.tools.build.bundletool.device.SdkVersionMatcher;
import com.android.tools.build.bundletool.device.TargetingDimensionMatcher;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;

public class ModuleMatcher {
    private final ImmutableList<? extends TargetingDimensionMatcher<?>> moduleMatchers;

    public ModuleMatcher(SdkVersionMatcher sdkVersionMatcher, DeviceFeatureMatcher deviceFeatureMatcher) {
        this.moduleMatchers = ImmutableList.of(sdkVersionMatcher, deviceFeatureMatcher);
    }

    @VisibleForTesting
    public ModuleMatcher(Devices.DeviceSpec deviceSpec) {
        this(new SdkVersionMatcher(deviceSpec), new DeviceFeatureMatcher(deviceSpec));
    }

    public boolean matchesModuleTargeting(Targeting.ModuleTargeting targeting) {
        return this.moduleMatchers.stream().allMatch(matcher -> matcher.getModuleTargetingPredicate().test(targeting));
    }
}

