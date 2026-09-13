/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.device;

import com.android.bundle.Devices;
import com.android.bundle.Targeting;
import com.android.tools.build.bundletool.model.AbiName;
import com.android.tools.build.bundletool.model.utils.TargetingProtoUtils;
import com.google.common.collect.Iterables;

public final class DeviceSpecUtils {
    public static boolean isAbiMissing(Devices.DeviceSpec deviceSpec) {
        return deviceSpec.getSupportedAbisList().isEmpty();
    }

    public static boolean isScreenDensityMissing(Devices.DeviceSpec deviceSpec) {
        return deviceSpec.getScreenDensity() == 0;
    }

    public static boolean isSdkVersionMissing(Devices.DeviceSpec deviceSpec) {
        return deviceSpec.getSdkVersion() == 0;
    }

    public static boolean isLocalesMissing(Devices.DeviceSpec deviceSpec) {
        return deviceSpec.getSupportedLocalesList().isEmpty();
    }

    public static class DeviceSpecFromTargetingBuilder {
        private final Devices.DeviceSpec.Builder deviceSpec;

        DeviceSpecFromTargetingBuilder(Devices.DeviceSpec deviceSpec) {
            this.deviceSpec = deviceSpec.toBuilder();
        }

        DeviceSpecFromTargetingBuilder setSdkVersion(Targeting.SdkVersionTargeting sdkVersionTargeting) {
            if (!sdkVersionTargeting.equals(Targeting.SdkVersionTargeting.getDefaultInstance())) {
                this.deviceSpec.setSdkVersion(Iterables.getOnlyElement(sdkVersionTargeting.getValueList()).getMin().getValue());
            }
            return this;
        }

        DeviceSpecFromTargetingBuilder setSupportedAbis(Targeting.AbiTargeting abiTargeting) {
            if (!abiTargeting.equals(Targeting.AbiTargeting.getDefaultInstance())) {
                this.deviceSpec.addSupportedAbis(AbiName.fromProto(Iterables.getOnlyElement(abiTargeting.getValueList()).getAlias()).getPlatformName());
            }
            return this;
        }

        DeviceSpecFromTargetingBuilder setScreenDensity(Targeting.ScreenDensityTargeting screenDensityTargeting) {
            TargetingProtoUtils.getScreenDensityDpi(screenDensityTargeting).ifPresent(this.deviceSpec::setScreenDensity);
            return this;
        }

        DeviceSpecFromTargetingBuilder setSupportedLocales(Targeting.LanguageTargeting languageTargeting) {
            if (!languageTargeting.equals(Targeting.LanguageTargeting.getDefaultInstance())) {
                this.deviceSpec.addSupportedLocales(Iterables.getOnlyElement(languageTargeting.getValueList()));
            }
            return this;
        }

        DeviceSpecFromTargetingBuilder setDeviceFeatures(Targeting.DeviceFeatureTargeting deviceFeatureTargeting) {
            throw new UnsupportedOperationException();
        }

        Devices.DeviceSpec build() {
            return this.deviceSpec.build();
        }
    }
}

