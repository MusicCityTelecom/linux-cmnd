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
import com.android.tools.build.bundletool.model.targeting.TargetingComparators;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Streams;
import java.util.Collection;

public class MultiAbiMatcher
extends TargetingDimensionMatcher<Targeting.MultiAbiTargeting> {
    public MultiAbiMatcher(Devices.DeviceSpec deviceSpec) {
        super(deviceSpec);
    }

    @Override
    public boolean matchesTargeting(Targeting.MultiAbiTargeting targeting) {
        if (targeting.equals(Targeting.MultiAbiTargeting.getDefaultInstance())) {
            return true;
        }
        ImmutableSet valuesSet = targeting.getValueList().stream().map(MultiAbiMatcher::abiAliases).collect(ImmutableSet.toImmutableSet());
        ImmutableSet<Targeting.Abi.AbiAlias> deviceAbis = this.deviceAbiAliases();
        if (valuesSet.stream().noneMatch(deviceAbis::containsAll)) {
            return false;
        }
        ImmutableSet alternativesSet = targeting.getAlternativesList().stream().map(MultiAbiMatcher::abiAliases).collect(ImmutableSet.toImmutableSet());
        return alternativesSet.stream().noneMatch(alternative -> deviceAbis.containsAll((Collection<?>)alternative) && valuesSet.stream().allMatch(value -> TargetingComparators.MULTI_ABI_ALIAS_COMPARATOR.compare((ImmutableSet<Targeting.Abi.AbiAlias>)alternative, (ImmutableSet<Targeting.Abi.AbiAlias>)value) > 0));
    }

    @Override
    protected void checkDeviceCompatibleInternal(Targeting.MultiAbiTargeting targeting) {
        if (targeting.equals(Targeting.MultiAbiTargeting.getDefaultInstance())) {
            return;
        }
        ImmutableSet valuesAndAlternativesSet = Streams.concat(targeting.getValueList().stream().map(MultiAbiMatcher::abiAliases), targeting.getAlternativesList().stream().map(MultiAbiMatcher::abiAliases)).collect(ImmutableSet.toImmutableSet());
        ImmutableSet<Targeting.Abi.AbiAlias> deviceAbis = this.deviceAbiAliases();
        if (valuesAndAlternativesSet.stream().noneMatch(deviceAbis::containsAll)) {
            throw IncompatibleDeviceException.builder().withMessage("No set of ABI architectures that the app supports is contained in the ABI architecture set of the device. Device ABIs: %s, app ABIs: %s.", deviceAbis, valuesAndAlternativesSet).build();
        }
    }

    @Override
    protected Targeting.MultiAbiTargeting getTargetingValue(Targeting.ApkTargeting apkTargeting) {
        return apkTargeting.getMultiAbiTargeting();
    }

    @Override
    protected Targeting.MultiAbiTargeting getTargetingValue(Targeting.VariantTargeting variantTargeting) {
        return variantTargeting.getMultiAbiTargeting();
    }

    @Override
    protected boolean isDeviceDimensionPresent() {
        return !this.getDeviceSpec().getSupportedAbisList().isEmpty();
    }

    private static ImmutableSet<Targeting.Abi.AbiAlias> abiAliases(Targeting.MultiAbi multiAbi) {
        return multiAbi.getAbiList().stream().map(Targeting.Abi::getAlias).collect(ImmutableSet.toImmutableSet());
    }

    private ImmutableSet<Targeting.Abi.AbiAlias> deviceAbiAliases() {
        return this.getDeviceSpec().getSupportedAbisList().stream().map(abi -> AbiName.fromPlatformName(abi).orElseThrow(() -> ValidationException.builder().withMessage("Unrecognized ABI '%s' in device spec.", abi).build()).toProto()).collect(ImmutableSet.toImmutableSet());
    }
}

