/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.model;

import com.android.tools.build.bundletool.model.ModuleSplit;
import com.android.tools.build.bundletool.model.ShardedSystemSplits;
import com.google.common.collect.ImmutableList;

final class AutoValue_ShardedSystemSplits
extends ShardedSystemSplits {
    private final ModuleSplit systemImageSplit;
    private final ImmutableList<ModuleSplit> additionalSplits;

    private AutoValue_ShardedSystemSplits(ModuleSplit systemImageSplit, ImmutableList<ModuleSplit> additionalSplits) {
        this.systemImageSplit = systemImageSplit;
        this.additionalSplits = additionalSplits;
    }

    @Override
    public ModuleSplit getSystemImageSplit() {
        return this.systemImageSplit;
    }

    @Override
    public ImmutableList<ModuleSplit> getAdditionalSplits() {
        return this.additionalSplits;
    }

    public String toString() {
        return "ShardedSystemSplits{systemImageSplit=" + this.systemImageSplit + ", additionalSplits=" + this.additionalSplits + "}";
    }

    public boolean equals(Object o3) {
        if (o3 == this) {
            return true;
        }
        if (o3 instanceof ShardedSystemSplits) {
            ShardedSystemSplits that = (ShardedSystemSplits)o3;
            return this.systemImageSplit.equals(that.getSystemImageSplit()) && this.additionalSplits.equals(that.getAdditionalSplits());
        }
        return false;
    }

    public int hashCode() {
        int h$ = 1;
        h$ *= 1000003;
        h$ ^= this.systemImageSplit.hashCode();
        h$ *= 1000003;
        return h$ ^= this.additionalSplits.hashCode();
    }

    static final class Builder
    extends ShardedSystemSplits.Builder {
        private ModuleSplit systemImageSplit;
        private ImmutableList<ModuleSplit> additionalSplits;

        Builder() {
        }

        @Override
        public ShardedSystemSplits.Builder setSystemImageSplit(ModuleSplit systemImageSplit) {
            if (systemImageSplit == null) {
                throw new NullPointerException("Null systemImageSplit");
            }
            this.systemImageSplit = systemImageSplit;
            return this;
        }

        @Override
        public ShardedSystemSplits.Builder setAdditionalSplits(ImmutableList<ModuleSplit> additionalSplits) {
            if (additionalSplits == null) {
                throw new NullPointerException("Null additionalSplits");
            }
            this.additionalSplits = additionalSplits;
            return this;
        }

        @Override
        public ShardedSystemSplits build() {
            String missing = "";
            if (this.systemImageSplit == null) {
                missing = missing + " systemImageSplit";
            }
            if (this.additionalSplits == null) {
                missing = missing + " additionalSplits";
            }
            if (!missing.isEmpty()) {
                throw new IllegalStateException("Missing required properties:" + missing);
            }
            return new AutoValue_ShardedSystemSplits(this.systemImageSplit, this.additionalSplits);
        }
    }
}

