/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.codehaus.groovy.runtime.memoize.CommonCache
 *  org.codehaus.groovy.runtime.memoize.EvictableCache
 *  org.codehaus.groovy.runtime.memoize.EvictableCache$EvictionStrategy
 */
package org.apache.groovy.json.internal;

import org.apache.groovy.json.internal.Cache;
import org.apache.groovy.json.internal.CacheType;
import org.codehaus.groovy.runtime.memoize.CommonCache;
import org.codehaus.groovy.runtime.memoize.EvictableCache;

public class SimpleCache<K, V>
implements Cache<K, V> {
    private EvictableCache<K, V> cache;

    public SimpleCache(int limit, CacheType type) {
        this.cache = type.equals((Object)CacheType.LRU) ? new CommonCache(limit) : new CommonCache(16, limit, EvictableCache.EvictionStrategy.FIFO);
    }

    public SimpleCache(int limit) {
        this(limit, CacheType.LRU);
    }

    @Override
    public void put(K key, V value) {
        this.cache.put(key, value);
    }

    @Override
    public V get(K key) {
        return (V)this.cache.get(key);
    }

    @Override
    public V getSilent(K key) {
        Object value = this.cache.get(key);
        if (value != null) {
            this.cache.remove(key);
            this.cache.put(key, value);
        }
        return (V)value;
    }

    @Override
    public void remove(K key) {
        this.cache.remove(key);
    }

    @Override
    public int size() {
        return this.cache.size();
    }

    public String toString() {
        return this.cache.toString();
    }
}

