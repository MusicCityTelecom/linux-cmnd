/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.model;

import com.android.tools.build.bundletool.model.AutoValue_GeneratedAssetSlices;
import com.android.tools.build.bundletool.model.ModuleSplit;
import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;
import com.google.errorprone.annotations.Immutable;

@Immutable
@AutoValue
@AutoValue.CopyAnnotations
public abstract class GeneratedAssetSlices {
    public abstract ImmutableList<ModuleSplit> getAssetSlices();

    public int size() {
        return this.getAssetSlices().size();
    }

    public static Builder builder() {
        return new AutoValue_GeneratedAssetSlices.Builder().setAssetSlices(ImmutableList.of());
    }

    public static GeneratedAssetSlices fromModuleSplits(ImmutableList<ModuleSplit> moduleSplits) {
        ImmutableList<ModuleSplit> assetSlices = moduleSplits.stream().filter(split -> split.getSplitType().equals((Object)ModuleSplit.SplitType.ASSET_SLICE)).collect(ImmutableList.toImmutableList());
        return GeneratedAssetSlices.builder().setAssetSlices(assetSlices).build();
    }

    @AutoValue.Builder
    public static abstract class Builder {
        public abstract Builder setAssetSlices(ImmutableList<ModuleSplit> var1);

        public abstract GeneratedAssetSlices build();
    }
}

