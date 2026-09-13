/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.device;

import com.android.bundle.Devices;
import com.android.bundle.Targeting;
import com.android.tools.build.bundletool.device.IncompatibleDeviceException;
import com.android.tools.build.bundletool.device.TargetingDimensionMatcher;
import com.android.tools.build.bundletool.model.AbiName;
import com.android.tools.build.bundletool.model.exceptions.ValidationException;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.google.common.collect.Streams;

public final class AbiMatcher
extends TargetingDimensionMatcher<Targeting.AbiTargeting> {
    public AbiMatcher(Devices.DeviceSpec deviceSpec) {
        super(deviceSpec);
    }

    @Override
    public boolean matchesTargeting(Targeting.AbiTargeting targeting) {
        if (targeting.equals(Targeting.AbiTargeting.getDefaultInstance())) {
            return true;
        }
        ImmutableSet valuesList = targeting.getValueList().stream().map(Targeting.Abi::getAlias).collect(ImmutableSet.toImmutableSet());
        ImmutableSet alternativesList = targeting.getAlternativesList().stream().map(Targeting.Abi::getAlias).collect(ImmutableSet.toImmutableSet());
        Sets.SetView intersection = Sets.intersection(valuesList, alternativesList);
        Preconditions.checkArgument(intersection.isEmpty(), "Expected targeting values and alternatives to be mutually exclusive, but both contain: %s", intersection);
        for (String abi : this.getDeviceSpec().getSupportedAbisList()) {
            Targeting.Abi.AbiAlias abiAlias = AbiName.fromPlatformName(abi).orElseThrow(() -> ValidationException.builder().withMessage("Unrecognized ABI '%s' in device spec.", abi).build()).toProto();
            if (valuesList.contains(abiAlias)) {
                return true;
            }
            if (!alternativesList.contains(abiAlias)) continue;
            return false;
        }
        return valuesList.isEmpty();
    }

    @Override
    protected void checkDeviceCompatibleInternal(Targeting.AbiTargeting targeting) {
        ImmutableSet deviceAbis;
        if (targeting.equals(Targeting.AbiTargeting.getDefaultInstance())) {
            return;
        }
        ImmutableSet valuesAndAlternativesSet = Streams.concat(targeting.getValueList().stream().map(Targeting.Abi::getAlias).map(AbiName::fromProto).map(AbiName::getPlatformName), targeting.getAlternativesList().stream().map(Targeting.Abi::getAlias).map(AbiName::fromProto).map(AbiName::getPlatformName)).collect(ImmutableSet.toImmutableSet());
        Sets.SetView intersection = Sets.intersection(valuesAndAlternativesSet, deviceAbis = this.getDeviceSpec().getSupportedAbisList().stream().collect(ImmutableSet.toImmutableSet()));
        if (intersection.isEmpty()) {
            throw IncompatibleDeviceException.builder().withMessage("The app doesn't support ABI architectures of the device. Device ABIs: %s, app ABIs: %s.", this.getDeviceSpec().getSupportedAbisList(), valuesAndAlternativesSet).build();
        }
    }

    @Override
    protected Targeting.AbiTargeting getTargetingValue(Targeting.ApkTargeting apkTargeting) {
        return apkTargeting.getAbiTargeting();
    }

    @Override
    protected Targeting.AbiTargeting getTargetingValue(Targeting.VariantTargeting variantTargeting) {
        return variantTargeting.getAbiTargeting();
    }

    @Override
    protected boolean isDeviceDimensionPresent() {
        return !this.getDeviceSpec().getSupportedAbisList().isEmpty();
    }
}

