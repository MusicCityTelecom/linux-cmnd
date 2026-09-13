/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.model;

import com.android.tools.build.bundletool.model.AutoValue_ShardedSystemSplits;
import com.android.tools.build.bundletool.model.ModuleSplit;
import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;
import com.google.errorprone.annotations.Immutable;

@Immutable
@AutoValue
@AutoValue.CopyAnnotations
public abstract class ShardedSystemSplits {
    public abstract ModuleSplit getSystemImageSplit();

    public abstract ImmutableList<ModuleSplit> getAdditionalSplits();

    public static Builder builder() {
        return new AutoValue_ShardedSystemSplits.Builder();
    }

    @AutoValue.Builder
    public static abstract class Builder {
        public abstract Builder setSystemImageSplit(ModuleSplit var1);

        public abstract Builder setAdditionalSplits(ImmutableList<ModuleSplit> var1);

        public abstract ShardedSystemSplits build();
    }
}

