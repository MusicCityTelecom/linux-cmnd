/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.model;

import com.android.tools.build.bundletool.model.AutoValue_ResourceId;
import com.android.tools.build.bundletool.model.ResourceId;

abstract class $AutoValue_ResourceId
extends ResourceId {
    private final int packageId;
    private final int typeId;
    private final int entryId;

    $AutoValue_ResourceId(int packageId, int typeId, int entryId) {
        this.packageId = packageId;
        this.typeId = typeId;
        this.entryId = entryId;
    }

    @Override
    public int getPackageId() {
        return this.packageId;
    }

    @Override
    public int getTypeId() {
        return this.typeId;
    }

    @Override
    public int getEntryId() {
        return this.entryId;
    }

    public boolean equals(Object o3) {
        if (o3 == this) {
            return true;
        }
        if (o3 instanceof ResourceId) {
            ResourceId that = (ResourceId)o3;
            return this.packageId == that.getPackageId() && this.typeId == that.getTypeId() && this.entryId == that.getEntryId();
        }
        return false;
    }

    public int hashCode() {
        int h$ = 1;
        h$ *= 1000003;
        h$ ^= this.packageId;
        h$ *= 1000003;
        h$ ^= this.typeId;
        h$ *= 1000003;
        return h$ ^= this.entryId;
    }

    static final class Builder
    extends ResourceId.Builder {
        private Integer packageId;
        private Integer typeId;
        private Integer entryId;

        Builder() {
        }

        @Override
        public ResourceId.Builder setPackageId(int packageId) {
            this.packageId = packageId;
            return this;
        }

        @Override
        public ResourceId.Builder setTypeId(int typeId) {
            this.typeId = typeId;
            return this;
        }

        @Override
        public ResourceId.Builder setEntryId(int entryId) {
            this.entryId = entryId;
            return this;
        }

        @Override
        ResourceId autoBuild() {
            String missing = "";
            if (this.packageId == null) {
                missing = missing + " packageId";
            }
            if (this.typeId == null) {
                missing = missing + " typeId";
            }
            if (this.entryId == null) {
                missing = missing + " entryId";
            }
            if (!missing.isEmpty()) {
                throw new IllegalStateException("Missing required properties:" + missing);
            }
            return new AutoValue_ResourceId(this.packageId, this.typeId, this.entryId);
        }
    }
}

