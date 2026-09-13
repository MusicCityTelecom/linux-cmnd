/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.device;

import com.android.bundle.Devices;
import com.android.bundle.Targeting;
import com.android.tools.build.bundletool.device.TargetingDimensionMatcher;
import com.android.tools.build.bundletool.model.utils.ResourcesUtils;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

public class LanguageMatcher
extends TargetingDimensionMatcher<Targeting.LanguageTargeting> {
    private final ImmutableSet<String> deviceLanguages;

    public LanguageMatcher(Devices.DeviceSpec deviceSpec) {
        super(deviceSpec);
        this.deviceLanguages = deviceSpec.getSupportedLocalesList().stream().map(ResourcesUtils::convertLocaleToLanguage).collect(ImmutableSet.toImmutableSet());
    }

    @Override
    protected Targeting.LanguageTargeting getTargetingValue(Targeting.ApkTargeting apkTargeting) {
        return apkTargeting.getLanguageTargeting();
    }

    @Override
    protected Targeting.LanguageTargeting getTargetingValue(Targeting.VariantTargeting variantTargeting) {
        return Targeting.LanguageTargeting.getDefaultInstance();
    }

    @Override
    public boolean matchesTargeting(Targeting.LanguageTargeting targetingValue) {
        if (targetingValue.equals(Targeting.LanguageTargeting.getDefaultInstance())) {
            return true;
        }
        if (targetingValue.getValueCount() > 0) {
            ImmutableSet<String> targetingLanguages = ImmutableSet.copyOf(targetingValue.getValueList());
            return !Sets.intersection(targetingLanguages, this.deviceLanguages).isEmpty();
        }
        ImmutableSet<String> alternativeLanguages = ImmutableSet.copyOf(targetingValue.getAlternativesList());
        return !alternativeLanguages.containsAll(this.deviceLanguages);
    }

    @Override
    protected boolean isDeviceDimensionPresent() {
        return !this.deviceLanguages.isEmpty();
    }

    @Override
    protected void checkDeviceCompatibleInternal(Targeting.LanguageTargeting targetingValue) {
    }
}

