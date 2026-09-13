/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.model;

import com.android.aapt.Resources;
import com.android.tools.build.bundletool.model.$AutoValue_ResourceTableEntry;
import com.android.tools.build.bundletool.model.ResourceId;

final class AutoValue_ResourceTableEntry
extends $AutoValue_ResourceTableEntry {
    private volatile ResourceId getResourceId;

    AutoValue_ResourceTableEntry(Resources.Package package$, Resources.Type type$, Resources.Entry entry$) {
        super(package$, type$, entry$);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public ResourceId getResourceId() {
        if (this.getResourceId == null) {
            AutoValue_ResourceTableEntry autoValue_ResourceTableEntry = this;
            synchronized (autoValue_ResourceTableEntry) {
                if (this.getResourceId == null) {
                    this.getResourceId = super.getResourceId();
                    if (this.getResourceId == null) {
                        throw new NullPointerException("getResourceId() cannot return null");
                    }
                }
            }
        }
        return this.getResourceId;
    }
}

