/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.model;

import com.android.tools.build.bundletool.model.GeneratedApks;
import com.android.tools.build.bundletool.model.ModuleSplit;
import com.google.common.collect.ImmutableList;

final class AutoValue_GeneratedApks
extends GeneratedApks {
    private final ImmutableList<ModuleSplit> instantApks;
    private final ImmutableList<ModuleSplit> splitApks;
    private final ImmutableList<ModuleSplit> standaloneApks;
    private final ImmutableList<ModuleSplit> systemApks;

    private AutoValue_GeneratedApks(ImmutableList<ModuleSplit> instantApks, ImmutableList<ModuleSplit> splitApks, ImmutableList<ModuleSplit> standaloneApks, ImmutableList<ModuleSplit> systemApks) {
        this.instantApks = instantApks;
        this.splitApks = splitApks;
        this.standaloneApks = standaloneApks;
        this.systemApks = systemApks;
    }

    @Override
    public ImmutableList<ModuleSplit> getInstantApks() {
        return this.instantApks;
    }

    @Override
    public ImmutableList<ModuleSplit> getSplitApks() {
        return this.splitApks;
    }

    @Override
    public ImmutableList<ModuleSplit> getStandaloneApks() {
        return this.standaloneApks;
    }

    @Override
    public ImmutableList<ModuleSplit> getSystemApks() {
        return this.systemApks;
    }

    public String toString() {
        return "GeneratedApks{instantApks=" + this.instantApks + ", splitApks=" + this.splitApks + ", standaloneApks=" + this.standaloneApks + ", systemApks=" + this.systemApks + "}";
    }

    public boolean equals(Object o3) {
        if (o3 == this) {
            return true;
        }
        if (o3 instanceof GeneratedApks) {
            GeneratedApks that = (GeneratedApks)o3;
            return this.instantApks.equals(that.getInstantApks()) && this.splitApks.equals(that.getSplitApks()) && this.standaloneApks.equals(that.getStandaloneApks()) && this.systemApks.equals(that.getSystemApks());
        }
        return false;
    }

    public int hashCode() {
        int h$ = 1;
        h$ *= 1000003;
        h$ ^= this.instantApks.hashCode();
        h$ *= 1000003;
        h$ ^= this.splitApks.hashCode();
        h$ *= 1000003;
        h$ ^= this.standaloneApks.hashCode();
        h$ *= 1000003;
        return h$ ^= this.systemApks.hashCode();
    }

    static final class Builder
    extends GeneratedApks.Builder {
        private ImmutableList<ModuleSplit> instantApks;
        private ImmutableList<ModuleSplit> splitApks;
        private ImmutableList<ModuleSplit> standaloneApks;
        private ImmutableList<ModuleSplit> systemApks;

        Builder() {
        }

        @Override
        public GeneratedApks.Builder setInstantApks(ImmutableList<ModuleSplit> instantApks) {
            if (instantApks == null) {
                throw new NullPointerException("Null instantApks");
            }
            this.instantApks = instantApks;
            return this;
        }

        @Override
        public GeneratedApks.Builder setSplitApks(ImmutableList<ModuleSplit> splitApks) {
            if (splitApks == null) {
                throw new NullPointerException("Null splitApks");
            }
            this.splitApks = splitApks;
            return this;
        }

        @Override
        public GeneratedApks.Builder setStandaloneApks(ImmutableList<ModuleSplit> standaloneApks) {
            if (standaloneApks == null) {
                throw new NullPointerException("Null standaloneApks");
            }
            this.standaloneApks = standaloneApks;
            return this;
        }

        @Override
        public GeneratedApks.Builder setSystemApks(ImmutableList<ModuleSplit> systemApks) {
            if (systemApks == null) {
                throw new NullPointerException("Null systemApks");
            }
            this.systemApks = systemApks;
            return this;
        }

        @Override
        public GeneratedApks build() {
            String missing = "";
            if (this.instantApks == null) {
                missing = missing + " instantApks";
            }
            if (this.splitApks == null) {
                missing = missing + " splitApks";
            }
            if (this.standaloneApks == null) {
                missing = missing + " standaloneApks";
            }
            if (this.systemApks == null) {
                missing = missing + " systemApks";
            }
            if (!missing.isEmpty()) {
                throw new IllegalStateException("Missing required properties:" + missing);
            }
            return new AutoValue_GeneratedApks(this.instantApks, this.splitApks, this.standaloneApks, this.systemApks);
        }
    }
}

