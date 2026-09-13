/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.concurrent.ThreadSafe
 */
package org.codehaus.groovy.runtime.memoize;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import javax.annotation.concurrent.ThreadSafe;
import org.codehaus.groovy.runtime.memoize.CommonCache;
import org.codehaus.groovy.runtime.memoize.EvictableCache;
import org.codehaus.groovy.runtime.memoize.MemoizeCache;
import org.codehaus.groovy.runtime.memoize.ValueConvertable;

@ThreadSafe
public class ConcurrentCommonCache<K, V>
implements EvictableCache<K, V>,
ValueConvertable<V, Object>,
Serializable {
    private static final long serialVersionUID = -7352338549333024936L;
    private final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
    private final ReentrantReadWriteLock.ReadLock readLock = this.rwl.readLock();
    private final ReentrantReadWriteLock.WriteLock writeLock = this.rwl.writeLock();
    private final CommonCache<K, V> commonCache;

    public ConcurrentCommonCache() {
        this.commonCache = new CommonCache();
    }

    public ConcurrentCommonCache(int initialCapacity, int maxSize, EvictableCache.EvictionStrategy evictionStrategy) {
        this.commonCache = new CommonCache(initialCapacity, maxSize, evictionStrategy);
    }

    public ConcurrentCommonCache(int initialCapacity, int maxSize) {
        this.commonCache = new CommonCache(initialCapacity, maxSize);
    }

    public ConcurrentCommonCache(int maxSize) {
        this.commonCache = new CommonCache(maxSize);
    }

    public ConcurrentCommonCache(Map<K, V> map) {
        this.commonCache = new CommonCache<K, V>(map);
    }

    @Override
    public V get(Object key) {
        return (V)this.doWithReadLock(c -> c.get(key));
    }

    @Override
    public V put(K key, V value) {
        return (V)this.doWithWriteLock(c -> c.put(key, value));
    }

    @Override
    public V getAndPut(K key, MemoizeCache.ValueProvider<? super K, ? extends V> valueProvider) {
        return this.getAndPut(key, valueProvider, true);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public V getAndPut(K key, MemoizeCache.ValueProvider<? super K, ? extends V> valueProvider, boolean shouldCache) {
        V value;
        this.readLock.lock();
        try {
            value = this.commonCache.get((Object)key);
            if (null != this.convertValue(value)) {
                V v = value;
                return v;
            }
        }
        finally {
            this.readLock.unlock();
        }
        this.writeLock.lock();
        try {
            value = this.commonCache.get((Object)key);
            if (null != this.convertValue(value)) {
                V v = value;
                return v;
            }
            value = null == valueProvider ? null : valueProvider.provide((K)key);
            Object v0 = value;
            if (shouldCache && null != this.convertValue(value)) {
                this.commonCache.put(key, value);
            }
        }
        finally {
            this.writeLock.unlock();
        }
        return value;
    }

    @Override
    public Collection<V> values() {
        return this.doWithReadLock(EvictableCache::values);
    }

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        return this.doWithReadLock(Map::entrySet);
    }

    @Override
    public Set<K> keys() {
        return this.doWithReadLock(EvictableCache::keys);
    }

    @Override
    public boolean containsKey(Object key) {
        return this.doWithReadLock(c -> c.containsKey(key));
    }

    @Override
    public boolean containsValue(Object value) {
        return this.doWithReadLock(c -> c.containsValue(value));
    }

    @Override
    public int size() {
        return this.doWithReadLock(EvictableCache::size);
    }

    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    @Override
    public V remove(Object key) {
        return (V)this.doWithWriteLock(c -> c.remove(key));
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        this.doWithWriteLock(c -> {
            c.putAll(m);
            return null;
        });
    }

    @Override
    public Set<K> keySet() {
        return this.keys();
    }

    @Override
    public Map<K, V> clearAll() {
        return this.doWithWriteLock(EvictableCache::clearAll);
    }

    @Override
    public void cleanUpNullReferences() {
        this.doWithWriteLock(c -> {
            c.cleanUpNullReferences();
            return null;
        });
    }

    @Override
    public Object convertValue(V value) {
        return value;
    }

    private <R> R doWithWriteLock(EvictableCache.Action<K, V, R> action) {
        this.writeLock.lock();
        try {
            R r = action.doWith(this.commonCache);
            return r;
        }
        finally {
            this.writeLock.unlock();
        }
    }

    private <R> R doWithReadLock(EvictableCache.Action<K, V, R> action) {
        this.readLock.lock();
        try {
            R r = action.doWith(this.commonCache);
            return r;
        }
        finally {
            this.readLock.unlock();
        }
    }
}

