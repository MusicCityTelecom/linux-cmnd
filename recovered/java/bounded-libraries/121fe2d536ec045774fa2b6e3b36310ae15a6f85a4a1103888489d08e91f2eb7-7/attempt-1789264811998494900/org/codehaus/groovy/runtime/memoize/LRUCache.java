/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.concurrent.ThreadSafe
 */
package org.codehaus.groovy.runtime.memoize;

import java.lang.ref.SoftReference;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import javax.annotation.concurrent.ThreadSafe;
import org.apache.groovy.util.concurrent.concurrentlinkedhashmap.ConcurrentLinkedHashMap;
import org.codehaus.groovy.runtime.memoize.MemoizeCache;

@ThreadSafe
public final class LRUCache<K, V>
implements MemoizeCache<K, V> {
    private final ConcurrentMap<K, V> map;

    public LRUCache(int maxCacheSize) {
        this.map = new ConcurrentLinkedHashMap.Builder().maximumWeightedCapacity(maxCacheSize).build();
    }

    @Override
    public V put(K key, V value) {
        return this.map.put(key, value);
    }

    @Override
    public V get(K key) {
        return this.map.get(key);
    }

    @Override
    public V getAndPut(K key, MemoizeCache.ValueProvider<? super K, ? extends V> valueProvider) {
        return (V)this.map.computeIfAbsent(key, valueProvider::provide);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void cleanUpNullReferences() {
        ConcurrentMap<K, V> concurrentMap = this.map;
        synchronized (concurrentMap) {
            Iterator iterator = this.map.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry entry = iterator.next();
                Object value = entry.getValue();
                if (!(value instanceof SoftReference) || ((SoftReference)value).get() != null) continue;
                iterator.remove();
            }
        }
    }
}

