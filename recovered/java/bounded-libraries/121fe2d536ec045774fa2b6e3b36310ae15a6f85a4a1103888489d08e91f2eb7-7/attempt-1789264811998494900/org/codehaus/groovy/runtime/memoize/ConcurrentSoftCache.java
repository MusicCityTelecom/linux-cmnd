/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.runtime.memoize;

import java.lang.ref.SoftReference;
import java.util.Map;
import org.codehaus.groovy.runtime.memoize.ConcurrentCommonCache;
import org.codehaus.groovy.runtime.memoize.EvictableCache;

public class ConcurrentSoftCache<K, V>
extends ConcurrentCommonCache<K, SoftReference<V>> {
    private static final long serialVersionUID = 5646536868666351819L;

    public ConcurrentSoftCache() {
    }

    public ConcurrentSoftCache(int initialCapacity, int maxSize, EvictableCache.EvictionStrategy evictionStrategy) {
        super(initialCapacity, maxSize, evictionStrategy);
    }

    public ConcurrentSoftCache(int initialCapacity, int maxSize) {
        super(initialCapacity, maxSize);
    }

    public ConcurrentSoftCache(int maxSize) {
        super(maxSize);
    }

    public ConcurrentSoftCache(Map<K, SoftReference<V>> map) {
        super(map);
    }

    @Override
    public Object convertValue(SoftReference<V> value) {
        if (null == value) {
            return null;
        }
        return value.get();
    }
}

