/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.model;

import com.android.aapt.Resources;
import com.android.tools.build.bundletool.model.$AutoValue_ResourceId;
import com.google.auto.value.AutoValue;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.Immutable;

@Immutable
@AutoValue
@AutoValue.CopyAnnotations
public abstract class ResourceId {
    public static final int MAX_ENTRY_ID = 65535;
    public static final int MAX_TYPE_ID = 255;
    public static final int MAX_PACKAGE_ID = 255;
    private static final int ENTRY_ID_MASK = 65535;
    private static final int TYPE_ID_MASK = 0xFF0000;
    private static final int PACKAGE_ID_MASK = -16777216;
    private static final int PACKAGE_SHIFT = 24;
    private static final int TYPE_SHIFT = 16;

    public abstract int getPackageId();

    public abstract int getTypeId();

    public abstract int getEntryId();

    public static ResourceId create(Resources.PackageOrBuilder pkg, Resources.TypeOrBuilder type, Resources.EntryOrBuilder entry) {
        return ResourceId.builder().setPackageId(pkg.getPackageId().getId()).setTypeId(type.getTypeId().getId()).setEntryId(entry.getEntryId().getId()).build();
    }

    public static ResourceId create(int fullResourceId) {
        return ResourceId.builder().setPackageId((fullResourceId & 0xFF000000) >>> 24).setTypeId((fullResourceId & 0xFF0000) >>> 16).setEntryId(fullResourceId & 0xFFFF).build();
    }

    public static Builder builder() {
        return new $AutoValue_ResourceId.Builder();
    }

    public int getFullResourceId() {
        return (this.getPackageId() << 24) + (this.getTypeId() << 16) + this.getEntryId();
    }

    public final String toString() {
        return String.format("0x%08x", this.getFullResourceId());
    }

    @AutoValue.Builder
    public static abstract class Builder {
        public abstract Builder setPackageId(int var1);

        public abstract Builder setTypeId(int var1);

        public abstract Builder setEntryId(int var1);

        abstract ResourceId autoBuild();

        public ResourceId build() {
            ResourceId resourceId = this.autoBuild();
            Preconditions.checkState(0 <= resourceId.getEntryId() && resourceId.getEntryId() <= 65535, "Entry id not in [0, 0xffff]: %s", resourceId.getEntryId());
            Preconditions.checkState(0 < resourceId.getTypeId() && resourceId.getTypeId() <= 255, "Type id not in [1, 0xff]: %s", resourceId.getTypeId());
            Preconditions.checkState(0 <= resourceId.getPackageId() && resourceId.getPackageId() <= 255, "Package id not in [0, 0xff]: %s", resourceId.getPackageId());
            return resourceId;
        }
    }
}

