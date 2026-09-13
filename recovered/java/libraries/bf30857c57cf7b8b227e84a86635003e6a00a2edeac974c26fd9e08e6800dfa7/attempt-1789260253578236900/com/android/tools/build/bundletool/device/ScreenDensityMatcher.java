/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.device;

import com.android.bundle.Devices;
import com.android.bundle.Targeting;
import com.android.tools.build.bundletool.device.TargetingDimensionMatcher;
import com.android.tools.build.bundletool.model.targeting.ScreenDensitySelector;
import com.android.tools.build.bundletool.model.utils.ResourcesUtils;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import java.util.function.Predicate;

public final class ScreenDensityMatcher
extends TargetingDimensionMatcher<Targeting.ScreenDensityTargeting> {
    public ScreenDensityMatcher(Devices.DeviceSpec deviceSpec) {
        super(deviceSpec);
    }

    @Override
    public boolean matchesTargeting(Targeting.ScreenDensityTargeting targeting) {
        ImmutableCollection allDensities = ((ImmutableList.Builder)((ImmutableList.Builder)ImmutableList.builder().addAll(targeting.getValueList())).addAll(targeting.getAlternativesList())).build();
        if (allDensities.isEmpty()) {
            return true;
        }
        int bestMatchingDensity = new ScreenDensitySelector().selectBestDensity(Iterables.transform(allDensities, ResourcesUtils::convertToDpi), this.getDeviceSpec().getScreenDensity());
        return targeting.getValueList().stream().map(ResourcesUtils::convertToDpi).anyMatch(Predicate.isEqual(bestMatchingDensity));
    }

    @Override
    protected Targeting.ScreenDensityTargeting getTargetingValue(Targeting.ApkTargeting apkTargeting) {
        return apkTargeting.getScreenDensityTargeting();
    }

    @Override
    protected Targeting.ScreenDensityTargeting getTargetingValue(Targeting.VariantTargeting variantTargeting) {
        return variantTargeting.getScreenDensityTargeting();
    }

    @Override
    protected boolean isDeviceDimensionPresent() {
        return this.getDeviceSpec().getScreenDensity() != 0;
    }

    @Override
    protected void checkDeviceCompatibleInternal(Targeting.ScreenDensityTargeting targetingValue) {
    }
}

