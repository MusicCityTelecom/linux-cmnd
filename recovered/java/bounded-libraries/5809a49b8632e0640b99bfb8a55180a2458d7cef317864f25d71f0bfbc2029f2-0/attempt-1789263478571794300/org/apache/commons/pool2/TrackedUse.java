/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.pool2;

import java.time.Instant;

public interface TrackedUse {
    @Deprecated
    public long getLastUsed();

    default public Instant getLastUsedInstant() {
        return Instant.ofEpochMilli(this.getLastUsed());
    }
}

