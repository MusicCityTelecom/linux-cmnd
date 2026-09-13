/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.model;

import com.android.tools.build.bundletool.model.AutoValue_DeviceFeatureCondition;
import com.google.auto.value.AutoValue;
import com.google.errorprone.annotations.Immutable;
import java.util.Optional;

@Immutable
@AutoValue
@AutoValue.CopyAnnotations
public abstract class DeviceFeatureCondition {
    public abstract String getFeatureName();

    public abstract Optional<Integer> getFeatureVersion();

    public static DeviceFeatureCondition create(String featureName) {
        return new AutoValue_DeviceFeatureCondition(featureName, Optional.empty());
    }

    public static DeviceFeatureCondition create(String featureName, Optional<Integer> version) {
        return new AutoValue_DeviceFeatureCondition(featureName, version);
    }
}

