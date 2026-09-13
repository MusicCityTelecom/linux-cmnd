/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.model;

import com.android.tools.build.bundletool.model.GeneratedAssetSlices;
import com.android.tools.build.bundletool.model.ModuleSplit;
import com.google.common.collect.ImmutableList;

final class AutoValue_GeneratedAssetSlices
extends GeneratedAssetSlices {
    private final ImmutableList<ModuleSplit> assetSlices;

    private AutoValue_GeneratedAssetSlices(ImmutableList<ModuleSplit> assetSlices) {
        this.assetSlices = assetSlices;
    }

    @Override
    public ImmutableList<ModuleSplit> getAssetSlices() {
        return this.assetSlices;
    }

    public String toString() {
        return "GeneratedAssetSlices{assetSlices=" + this.assetSlices + "}";
    }

    public boolean equals(Object o3) {
        if (o3 == this) {
            return true;
        }
        if (o3 instanceof GeneratedAssetSlices) {
            GeneratedAssetSlices that = (GeneratedAssetSlices)o3;
            return this.assetSlices.equals(that.getAssetSlices());
        }
        return false;
    }

    public int hashCode() {
        int h$ = 1;
        h$ *= 1000003;
        return h$ ^= this.assetSlices.hashCode();
    }

    static final class Builder
    extends GeneratedAssetSlices.Builder {
        private ImmutableList<ModuleSplit> assetSlices;

        Builder() {
        }

        @Override
        public GeneratedAssetSlices.Builder setAssetSlices(ImmutableList<ModuleSplit> assetSlices) {
            if (assetSlices == null) {
                throw new NullPointerException("Null assetSlices");
            }
            this.assetSlices = assetSlices;
            return this;
        }

        @Override
        public GeneratedAssetSlices build() {
            String missing = "";
            if (this.assetSlices == null) {
                missing = missing + " assetSlices";
            }
            if (!missing.isEmpty()) {
                throw new IllegalStateException("Missing required properties:" + missing);
            }
            return new AutoValue_GeneratedAssetSlices(this.assetSlices);
        }
    }
}

