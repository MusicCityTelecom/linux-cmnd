/*
 * Decompiled with CFR 0.152.
 */
package org.apereo.cas.monitor;

public interface CacheStatistics {
    default public long getSize() {
        return 0L;
    }

    default public long getCapacity() {
        return 0L;
    }

    default public long getEvictions() {
        return 0L;
    }

    default public long getPercentFree() {
        return 0L;
    }

    public String getName();

    public String toString(StringBuilder var1);
}

