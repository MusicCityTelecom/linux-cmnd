/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.hk2.utilities.cache.internal;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.glassfish.hk2.utilities.cache.CacheEntry;
import org.glassfish.hk2.utilities.cache.CacheKeyFilter;
import org.glassfish.hk2.utilities.cache.LRUCache;

public class LRUCacheCheapRead<K, V>
extends LRUCache<K, V> {
    final Object prunningLock = new Object();
    final int maxCacheSize;
    Map<K, CacheEntryImpl<K, V>> cache = new ConcurrentHashMap<K, CacheEntryImpl<K, V>>();
    private static final CacheEntryImplComparator COMPARATOR = new CacheEntryImplComparator();

    public LRUCacheCheapRead(int maxCacheSize) {
        this.maxCacheSize = maxCacheSize;
    }

    @Override
    public V get(K key) {
        CacheEntryImpl<K, V> entry = this.cache.get(key);
        return entry != null ? (V)entry.hit().value : null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public CacheEntry put(K key, V value) {
        CacheEntryImpl<K, V> entry = new CacheEntryImpl<K, V>(key, value, this);
        Object object = this.prunningLock;
        synchronized (object) {
            if (this.cache.size() + 1 > this.maxCacheSize) {
                this.removeLRUItem();
            }
            this.cache.put(key, entry);
            return entry;
        }
    }

    @Override
    public void releaseCache() {
        this.cache.clear();
    }

    @Override
    public int getMaxCacheSize() {
        return this.maxCacheSize;
    }

    @Override
    public void releaseMatching(CacheKeyFilter<K> filter) {
        if (filter == null) {
            return;
        }
        for (Map.Entry<K, CacheEntryImpl<K, V>> entry : new HashMap<K, CacheEntryImpl<K, V>>(this.cache).entrySet()) {
            if (!filter.matches(entry.getKey())) continue;
            entry.getValue().removeFromCache();
        }
    }

    private void removeLRUItem() {
        Collection<CacheEntryImpl<K, V>> values = this.cache.values();
        Collections.min(values, COMPARATOR).removeFromCache();
    }

    private static class CacheEntryImpl<K, V>
    implements CacheEntry {
        final K key;
        final V value;
        final LRUCacheCheapRead<K, V> parent;
        long lastHit;

        public CacheEntryImpl(K k, V v, LRUCacheCheapRead<K, V> cache) {
            this.parent = cache;
            this.key = k;
            this.value = v;
            this.lastHit = System.nanoTime();
        }

        @Override
        public void removeFromCache() {
            this.parent.cache.remove(this.key);
        }

        public CacheEntryImpl<K, V> hit() {
            this.lastHit = System.nanoTime();
            return this;
        }
    }

    private static class CacheEntryImplComparator
    implements Comparator<CacheEntryImpl<?, ?>> {
        private CacheEntryImplComparator() {
        }

        @Override
        public int compare(CacheEntryImpl<?, ?> first, CacheEntryImpl<?, ?> second) {
            long diff = first.lastHit - second.lastHit;
            return diff > 0L ? 1 : (diff == 0L ? 0 : -1);
        }
    }
}

