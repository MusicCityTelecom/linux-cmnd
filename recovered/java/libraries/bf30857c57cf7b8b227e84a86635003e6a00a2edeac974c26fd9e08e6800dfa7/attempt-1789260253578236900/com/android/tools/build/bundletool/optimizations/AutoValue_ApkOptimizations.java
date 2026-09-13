/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.optimizations;

import com.android.tools.build.bundletool.model.OptimizationDimension;
import com.android.tools.build.bundletool.optimizations.ApkOptimizations;
import com.google.common.collect.ImmutableSet;

final class AutoValue_ApkOptimizations
extends ApkOptimizations {
    private final ImmutableSet<OptimizationDimension> splitDimensions;
    private final boolean uncompressNativeLibraries;
    private final boolean uncompressDexFiles;
    private final ImmutableSet<OptimizationDimension> standaloneDimensions;

    private AutoValue_ApkOptimizations(ImmutableSet<OptimizationDimension> splitDimensions, boolean uncompressNativeLibraries, boolean uncompressDexFiles, ImmutableSet<OptimizationDimension> standaloneDimensions) {
        this.splitDimensions = splitDimensions;
        this.uncompressNativeLibraries = uncompressNativeLibraries;
        this.uncompressDexFiles = uncompressDexFiles;
        this.standaloneDimensions = standaloneDimensions;
    }

    @Override
    public ImmutableSet<OptimizationDimension> getSplitDimensions() {
        return this.splitDimensions;
    }

    @Override
    public boolean getUncompressNativeLibraries() {
        return this.uncompressNativeLibraries;
    }

    @Override
    public boolean getUncompressDexFiles() {
        return this.uncompressDexFiles;
    }

    @Override
    public ImmutableSet<OptimizationDimension> getStandaloneDimensions() {
        return this.standaloneDimensions;
    }

    public String toString() {
        return "ApkOptimizations{splitDimensions=" + this.splitDimensions + ", uncompressNativeLibraries=" + this.uncompressNativeLibraries + ", uncompressDexFiles=" + this.uncompressDexFiles + ", standaloneDimensions=" + this.standaloneDimensions + "}";
    }

    public boolean equals(Object o3) {
        if (o3 == this) {
            return true;
        }
        if (o3 instanceof ApkOptimizations) {
            ApkOptimizations that = (ApkOptimizations)o3;
            return this.splitDimensions.equals(that.getSplitDimensions()) && this.uncompressNativeLibraries == that.getUncompressNativeLibraries() && this.uncompressDexFiles == that.getUncompressDexFiles() && this.standaloneDimensions.equals(that.getStandaloneDimensions());
        }
        return false;
    }

    public int hashCode() {
        int h$ = 1;
        h$ *= 1000003;
        h$ ^= this.splitDimensions.hashCode();
        h$ *= 1000003;
        h$ ^= this.uncompressNativeLibraries ? 1231 : 1237;
        h$ *= 1000003;
        h$ ^= this.uncompressDexFiles ? 1231 : 1237;
        h$ *= 1000003;
        return h$ ^= this.standaloneDimensions.hashCode();
    }

    static final class Builder
    extends ApkOptimizations.Builder {
        private ImmutableSet<OptimizationDimension> splitDimensions;
        private Boolean uncompressNativeLibraries;
        private Boolean uncompressDexFiles;
        private ImmutableSet<OptimizationDimension> standaloneDimensions;

        Builder() {
        }

        @Override
        ApkOptimizations.Builder setSplitDimensions(ImmutableSet<OptimizationDimension> splitDimensions) {
            if (splitDimensions == null) {
                throw new NullPointerException("Null splitDimensions");
            }
            this.splitDimensions = splitDimensions;
            return this;
        }

        @Override
        ApkOptimizations.Builder setUncompressNativeLibraries(boolean uncompressNativeLibraries) {
            this.uncompressNativeLibraries = uncompressNativeLibraries;
            return this;
        }

        @Override
        ApkOptimizations.Builder setUncompressDexFiles(boolean uncompressDexFiles) {
            this.uncompressDexFiles = uncompressDexFiles;
            return this;
        }

        @Override
        ApkOptimizations.Builder setStandaloneDimensions(ImmutableSet<OptimizationDimension> standaloneDimensions) {
            if (standaloneDimensions == null) {
                throw new NullPointerException("Null standaloneDimensions");
            }
            this.standaloneDimensions = standaloneDimensions;
            return this;
        }

        @Override
        ApkOptimizations build() {
            String missing = "";
            if (this.splitDimensions == null) {
                missing = missing + " splitDimensions";
            }
            if (this.uncompressNativeLibraries == null) {
                missing = missing + " uncompressNativeLibraries";
            }
            if (this.uncompressDexFiles == null) {
                missing = missing + " uncompressDexFiles";
            }
            if (this.standaloneDimensions == null) {
                missing = missing + " standaloneDimensions";
            }
            if (!missing.isEmpty()) {
                throw new IllegalStateException("Missing required properties:" + missing);
            }
            return new AutoValue_ApkOptimizations(this.splitDimensions, this.uncompressNativeLibraries, this.uncompressDexFiles, this.standaloneDimensions);
        }
    }
}

