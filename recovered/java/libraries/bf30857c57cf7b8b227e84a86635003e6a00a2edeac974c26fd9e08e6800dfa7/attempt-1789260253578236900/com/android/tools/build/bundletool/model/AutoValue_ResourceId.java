/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.model;

import com.android.tools.build.bundletool.model.$AutoValue_ResourceId;

final class AutoValue_ResourceId
extends $AutoValue_ResourceId {
    private volatile int getFullResourceId;
    private volatile boolean getFullResourceId$Memoized;

    AutoValue_ResourceId(int packageId$, int typeId$, int entryId$) {
        super(packageId$, typeId$, entryId$);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public int getFullResourceId() {
        if (!this.getFullResourceId$Memoized) {
            AutoValue_ResourceId autoValue_ResourceId = this;
            synchronized (autoValue_ResourceId) {
                if (!this.getFullResourceId$Memoized) {
                    this.getFullResourceId = super.getFullResourceId();
                    this.getFullResourceId$Memoized = true;
                }
            }
        }
        return this.getFullResourceId;
    }
}

