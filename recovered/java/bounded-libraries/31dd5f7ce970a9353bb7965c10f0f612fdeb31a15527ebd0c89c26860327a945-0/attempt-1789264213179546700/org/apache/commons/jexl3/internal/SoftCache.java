/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.jexl3.internal;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.apache.commons.jexl3.internal.SoftCacheEntry;

public class SoftCache<K, V> {
    private static final float LOAD_FACTOR = 0.75f;
    private final int size;
    private SoftReference<Map<K, V>> ref = null;
    private final ReadWriteLock lock;

    SoftCache(int theSize) {
        this.size = theSize;
        this.lock = new ReentrantReadWriteLock();
    }

    public int size() {
        return this.size;
    }

    public void clear() {
        this.lock.writeLock().lock();
        try {
            this.ref = null;
        }
        finally {
            this.lock.writeLock().unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public V get(K key) {
        this.lock.readLock().lock();
        try {
            Map<K, V> map = this.ref != null ? this.ref.get() : null;
            V v = map != null ? (V)map.get(key) : null;
            return v;
        }
        finally {
            this.lock.readLock().unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void put(K key, V script) {
        this.lock.writeLock().lock();
        try {
            Map<K, V> map;
            Map<K, V> map2 = map = this.ref != null ? this.ref.get() : null;
            if (map == null) {
                map = this.createCache(this.size);
                this.ref = new SoftReference<Map<K, V>>(map);
            }
            map.put(key, script);
        }
        finally {
            this.lock.writeLock().unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public List<Map.Entry<K, V>> entries() {
        this.lock.readLock().lock();
        try {
            Map<K, V> map;
            Map<K, V> map2 = map = this.ref != null ? this.ref.get() : null;
            if (map == null) {
                List<Map.Entry<K, V>> list = Collections.emptyList();
                return list;
            }
            Set<Map.Entry<K, V>> set = map.entrySet();
            ArrayList<SoftCacheEntry<K, V>> entries = new ArrayList<SoftCacheEntry<K, V>>(set.size());
            for (Map.Entry<K, V> e : set) {
                entries.add(new SoftCacheEntry<K, V>(e));
            }
            ArrayList<SoftCacheEntry<K, V>> arrayList = entries;
            return arrayList;
        }
        finally {
            this.lock.readLock().unlock();
        }
    }

    public <K, V> Map<K, V> createCache(final int cacheSize) {
        return new LinkedHashMap<K, V>(cacheSize, 0.75f, true){
            private static final long serialVersionUID = 1L;

            @Override
            protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
                return super.size() > cacheSize;
            }
        };
    }
}

