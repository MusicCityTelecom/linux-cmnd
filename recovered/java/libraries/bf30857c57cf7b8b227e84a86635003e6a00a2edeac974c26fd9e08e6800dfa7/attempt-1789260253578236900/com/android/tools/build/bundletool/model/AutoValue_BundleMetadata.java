/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.model;

import com.android.tools.build.bundletool.model.BundleMetadata;
import com.android.tools.build.bundletool.model.InputStreamSupplier;
import com.android.tools.build.bundletool.model.ZipPath;
import com.google.common.collect.ImmutableMap;

final class AutoValue_BundleMetadata
extends BundleMetadata {
    private final ImmutableMap<ZipPath, InputStreamSupplier> fileDataMap;

    private AutoValue_BundleMetadata(ImmutableMap<ZipPath, InputStreamSupplier> fileDataMap) {
        this.fileDataMap = fileDataMap;
    }

    @Override
    public ImmutableMap<ZipPath, InputStreamSupplier> getFileDataMap() {
        return this.fileDataMap;
    }

    public String toString() {
        return "BundleMetadata{fileDataMap=" + this.fileDataMap + "}";
    }

    public boolean equals(Object o3) {
        if (o3 == this) {
            return true;
        }
        if (o3 instanceof BundleMetadata) {
            BundleMetadata that = (BundleMetadata)o3;
            return this.fileDataMap.equals(that.getFileDataMap());
        }
        return false;
    }

    public int hashCode() {
        int h$ = 1;
        h$ *= 1000003;
        return h$ ^= this.fileDataMap.hashCode();
    }

    static final class Builder
    extends BundleMetadata.Builder {
        private ImmutableMap.Builder<ZipPath, InputStreamSupplier> fileDataMapBuilder$;
        private ImmutableMap<ZipPath, InputStreamSupplier> fileDataMap;

        Builder() {
        }

        @Override
        ImmutableMap.Builder<ZipPath, InputStreamSupplier> fileDataMapBuilder() {
            if (this.fileDataMapBuilder$ == null) {
                this.fileDataMapBuilder$ = ImmutableMap.builder();
            }
            return this.fileDataMapBuilder$;
        }

        @Override
        public BundleMetadata build() {
            if (this.fileDataMapBuilder$ != null) {
                this.fileDataMap = this.fileDataMapBuilder$.build();
            } else if (this.fileDataMap == null) {
                this.fileDataMap = ImmutableMap.of();
            }
            return new AutoValue_BundleMetadata(this.fileDataMap);
        }
    }
}

