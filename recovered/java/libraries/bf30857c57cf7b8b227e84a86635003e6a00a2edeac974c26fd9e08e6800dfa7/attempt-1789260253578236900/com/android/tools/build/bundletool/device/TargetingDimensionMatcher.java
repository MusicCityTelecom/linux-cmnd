/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.device;

import com.android.bundle.Devices;
import com.android.bundle.Targeting;
import com.google.common.base.Predicates;
import java.util.function.Predicate;

public abstract class TargetingDimensionMatcher<T> {
    private final Devices.DeviceSpec deviceSpec;

    public TargetingDimensionMatcher(Devices.DeviceSpec deviceSpec) {
        this.deviceSpec = deviceSpec;
    }

    protected Devices.DeviceSpec getDeviceSpec() {
        return this.deviceSpec;
    }

    public Predicate<Targeting.ApkTargeting> getApkTargetingPredicate() {
        return Predicates.compose(this::matchesTargeting, this::getTargetingValue);
    }

    public Predicate<Targeting.VariantTargeting> getVariantTargetingPredicate() {
        return variantTargeting -> !this.isDeviceDimensionPresent() || this.matchesTargeting(this.getTargetingValue((Targeting.VariantTargeting)variantTargeting));
    }

    public Predicate<Targeting.ModuleTargeting> getModuleTargetingPredicate() {
        return Predicates.compose(this::matchesTargeting, this::getTargetingValue);
    }

    protected T getTargetingValue(Targeting.ApkTargeting apkTargeting) {
        throw new UnsupportedOperationException();
    }

    protected T getTargetingValue(Targeting.VariantTargeting variantTargeting) {
        throw new UnsupportedOperationException();
    }

    protected T getTargetingValue(Targeting.ModuleTargeting moduleTargeting) {
        throw new UnsupportedOperationException();
    }

    protected abstract boolean isDeviceDimensionPresent();

    public abstract boolean matchesTargeting(T var1);

    public void checkDeviceCompatible(T targetingValue) {
        if (this.isDeviceDimensionPresent()) {
            this.checkDeviceCompatibleInternal(targetingValue);
        }
    }

    protected abstract void checkDeviceCompatibleInternal(T var1);
}

