/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.hk2.utilities.general;

import org.glassfish.hk2.utilities.cache.CacheKeyFilter;

public interface WeakHashLRU<K> {
    public void add(K var1);

    public boolean contains(K var1);

    public boolean remove(K var1);

    public void releaseMatching(CacheKeyFilter<K> var1);

    public int size();

    public K remove();

    public void clear();

    public void clearStaleReferences();
}

