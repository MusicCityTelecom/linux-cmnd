/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.model;

import com.android.aapt.Resources;
import com.android.tools.build.bundletool.model.AutoValue_ResourceTableEntry;
import com.android.tools.build.bundletool.model.ResourceId;
import com.google.auto.value.AutoValue;
import com.google.errorprone.annotations.Immutable;

@Immutable
@AutoValue
@AutoValue.CopyAnnotations
public abstract class ResourceTableEntry {
    public static ResourceTableEntry create(Resources.Package pkg, Resources.Type type, Resources.Entry entry) {
        return new AutoValue_ResourceTableEntry(pkg, type, entry);
    }

    public abstract Resources.Package getPackage();

    public abstract Resources.Type getType();

    public abstract Resources.Entry getEntry();

    public ResourceId getResourceId() {
        return ResourceId.create(this.getPackage(), this.getType(), this.getEntry());
    }
}

