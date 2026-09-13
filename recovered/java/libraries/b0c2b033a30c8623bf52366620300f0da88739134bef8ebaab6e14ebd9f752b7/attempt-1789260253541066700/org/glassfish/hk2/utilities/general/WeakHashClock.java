/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.hk2.utilities.general;

import java.util.Map;
import org.glassfish.hk2.utilities.cache.CacheKeyFilter;

public interface WeakHashClock<K, V> {
    public void put(K var1, V var2);

    public V get(K var1);

    public V remove(K var1);

    public void releaseMatching(CacheKeyFilter<K> var1);

    public int size();

    public Map.Entry<K, V> next();

    public void clear();

    public void clearStaleReferences();

    public boolean hasWeakKeys();
}

