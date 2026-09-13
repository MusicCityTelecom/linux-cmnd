/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.hk2.utilities.cache;

import org.glassfish.hk2.utilities.cache.CacheEntry;

public interface HybridCacheEntry<V>
extends CacheEntry {
    public V getValue();

    public boolean dropMe();
}

