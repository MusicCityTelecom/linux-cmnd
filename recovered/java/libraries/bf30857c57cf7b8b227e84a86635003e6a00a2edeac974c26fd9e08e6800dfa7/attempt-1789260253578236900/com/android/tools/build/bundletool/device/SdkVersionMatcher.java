/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.device;

import com.android.bundle.Devices;
import com.android.bundle.Targeting;
import com.android.tools.build.bundletool.device.IncompatibleDeviceException;
import com.android.tools.build.bundletool.device.TargetingDimensionMatcher;
import com.google.common.base.Preconditions;
import java.util.stream.Stream;

public final class SdkVersionMatcher
extends TargetingDimensionMatcher<Targeting.SdkVersionTargeting> {
    private final int deviceSdkVersion;

    public SdkVersionMatcher(Devices.DeviceSpec deviceSpec) {
        super(deviceSpec);
        this.deviceSdkVersion = deviceSpec.getSdkVersion();
    }

    @Override
    public boolean matchesTargeting(Targeting.SdkVersionTargeting targeting) {
        Targeting.SdkVersion sdkValue;
        Preconditions.checkArgument(targeting.getValueCount() <= 1, "Found more than one SDK version value in the variant targeting.");
        Targeting.SdkVersion sdkVersion = sdkValue = targeting.getValueCount() == 0 ? Targeting.SdkVersion.getDefaultInstance() : targeting.getValue(0);
        if (!this.matchesDeviceSdk(sdkValue, this.deviceSdkVersion)) {
            return false;
        }
        for (Targeting.SdkVersion alternativeSdkValue : targeting.getAlternativesList()) {
            if (!this.isBetterSdkMatch(alternativeSdkValue, sdkValue, this.deviceSdkVersion)) continue;
            return false;
        }
        return true;
    }

    private boolean isBetterSdkMatch(Targeting.SdkVersion candidate, Targeting.SdkVersion contestedValue, int deviceSdkVersion) {
        if (!this.matchesDeviceSdk(candidate, deviceSdkVersion)) {
            return false;
        }
        return candidate.hasMin() && candidate.getMin().getValue() > contestedValue.getMin().getValue();
    }

    private boolean matchesDeviceSdk(Targeting.SdkVersion value, int deviceSdkVersion) {
        return !value.hasMin() || value.getMin().getValue() <= deviceSdkVersion;
    }

    @Override
    protected Targeting.SdkVersionTargeting getTargetingValue(Targeting.ApkTargeting apkTargeting) {
        return apkTargeting.getSdkVersionTargeting();
    }

    @Override
    protected Targeting.SdkVersionTargeting getTargetingValue(Targeting.VariantTargeting variantTargeting) {
        return variantTargeting.getSdkVersionTargeting();
    }

    @Override
    protected Targeting.SdkVersionTargeting getTargetingValue(Targeting.ModuleTargeting moduleTargeting) {
        return moduleTargeting.getSdkVersionTargeting();
    }

    @Override
    protected void checkDeviceCompatibleInternal(Targeting.SdkVersionTargeting targeting) {
        Targeting.SdkVersion sdkValue = targeting.getValueCount() == 0 ? Targeting.SdkVersion.getDefaultInstance() : targeting.getValue(0);
        boolean anyMatch = Stream.concat(Stream.of(sdkValue), targeting.getAlternativesList().stream()).anyMatch(sdkVal -> this.matchesDeviceSdk((Targeting.SdkVersion)sdkVal, this.deviceSdkVersion));
        if (!anyMatch) {
            throw IncompatibleDeviceException.builder().withMessage("The app doesn't support SDK version of the device: (%d).", this.getDeviceSpec().getSdkVersion()).build();
        }
    }

    @Override
    protected boolean isDeviceDimensionPresent() {
        return this.deviceSdkVersion != 0;
    }
}

