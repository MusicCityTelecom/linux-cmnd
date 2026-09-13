/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.model;

import com.android.tools.build.bundletool.model.DeviceFeatureCondition;
import java.util.Optional;

final class AutoValue_DeviceFeatureCondition
extends DeviceFeatureCondition {
    private final String featureName;
    private final Optional<Integer> featureVersion;

    AutoValue_DeviceFeatureCondition(String featureName, Optional<Integer> featureVersion) {
        if (featureName == null) {
            throw new NullPointerException("Null featureName");
        }
        this.featureName = featureName;
        if (featureVersion == null) {
            throw new NullPointerException("Null featureVersion");
        }
        this.featureVersion = featureVersion;
    }

    @Override
    public String getFeatureName() {
        return this.featureName;
    }

    @Override
    public Optional<Integer> getFeatureVersion() {
        return this.featureVersion;
    }

    public String toString() {
        return "DeviceFeatureCondition{featureName=" + this.featureName + ", featureVersion=" + this.featureVersion + "}";
    }

    public boolean equals(Object o3) {
        if (o3 == this) {
            return true;
        }
        if (o3 instanceof DeviceFeatureCondition) {
            DeviceFeatureCondition that = (DeviceFeatureCondition)o3;
            return this.featureName.equals(that.getFeatureName()) && this.featureVersion.equals(that.getFeatureVersion());
        }
        return false;
    }

    public int hashCode() {
        int h$ = 1;
        h$ *= 1000003;
        h$ ^= this.featureName.hashCode();
        h$ *= 1000003;
        return h$ ^= this.featureVersion.hashCode();
    }
}

