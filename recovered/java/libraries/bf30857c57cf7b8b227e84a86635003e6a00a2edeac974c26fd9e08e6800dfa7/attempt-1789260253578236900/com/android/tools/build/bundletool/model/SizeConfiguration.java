/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.model;

import com.android.bundle.Targeting;
import com.android.tools.build.bundletool.model.AbiName;
import com.android.tools.build.bundletool.model.AutoValue_SizeConfiguration;
import com.android.tools.build.bundletool.model.targeting.TargetingUtils;
import com.google.auto.value.AutoValue;
import com.google.common.collect.Iterables;
import com.google.errorprone.annotations.Immutable;
import java.util.Optional;

@Immutable
@AutoValue
@AutoValue.CopyAnnotations
public abstract class SizeConfiguration {
    public abstract Optional<String> getAbi();

    public abstract Optional<String> getLocale();

    public abstract Optional<String> getScreenDensity();

    public abstract Optional<String> getSdkVersion();

    public abstract Builder toBuilder();

    public static Builder builder() {
        return new AutoValue_SizeConfiguration.Builder();
    }

    public static SizeConfiguration getDefaultInstance() {
        return SizeConfiguration.builder().build();
    }

    public static Optional<String> getAbiName(Targeting.AbiTargeting abiTargeting) {
        if (abiTargeting.getValueList().isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(AbiName.fromProto(Iterables.getOnlyElement(abiTargeting.getValueList()).getAlias()).getPlatformName());
    }

    public static Optional<String> getSdkName(Targeting.SdkVersionTargeting sdkVersionTargeting) {
        int maxSdk = TargetingUtils.getMaxSdk(sdkVersionTargeting);
        return Optional.of(String.format("%d-%s", TargetingUtils.getMinSdk(sdkVersionTargeting), maxSdk != Integer.MAX_VALUE ? Integer.toString(maxSdk - 1) : ""));
    }

    public static Optional<String> getLocaleName(Targeting.LanguageTargeting languageTargeting) {
        if (languageTargeting.getValueList().isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(Iterables.getOnlyElement(languageTargeting.getValueList()));
    }

    public static Optional<String> getScreenDensityName(Targeting.ScreenDensityTargeting screenDensityTargeting) {
        if (screenDensityTargeting.getValueList().isEmpty()) {
            return Optional.empty();
        }
        Targeting.ScreenDensity screenDensity = Iterables.getOnlyElement(screenDensityTargeting.getValueList());
        return Optional.of(screenDensity.getDensityOneofCase().equals(Targeting.ScreenDensity.DensityOneofCase.DENSITY_ALIAS) ? screenDensity.getDensityAlias().name() : Integer.toString(screenDensity.getDensityDpi()));
    }

    @AutoValue.Builder
    public static abstract class Builder {
        public abstract Builder setAbi(String var1);

        public abstract Builder setLocale(String var1);

        public abstract Builder setScreenDensity(String var1);

        public abstract Builder setSdkVersion(String var1);

        public abstract SizeConfiguration build();
    }
}

