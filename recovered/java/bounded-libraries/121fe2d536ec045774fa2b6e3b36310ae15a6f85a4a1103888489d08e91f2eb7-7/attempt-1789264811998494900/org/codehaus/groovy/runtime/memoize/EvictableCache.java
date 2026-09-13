/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.runtime.memoize;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import org.codehaus.groovy.runtime.memoize.MemoizeCache;

public interface EvictableCache<K, V>
extends MemoizeCache<K, V>,
Map<K, V> {
    @Override
    public V remove(Object var1);

    public Map<K, V> clearAll();

    @Override
    default public void clear() {
        this.clearAll();
    }

    @Override
    public Collection<V> values();

    public Set<K> keys();

    @Override
    public boolean containsKey(Object var1);

    @Override
    public int size();

    @FunctionalInterface
    public static interface Action<K, V, R> {
        public R doWith(EvictableCache<K, V> var1);
    }

    public static enum EvictionStrategy {
        LRU,
        FIFO;

    }
}

