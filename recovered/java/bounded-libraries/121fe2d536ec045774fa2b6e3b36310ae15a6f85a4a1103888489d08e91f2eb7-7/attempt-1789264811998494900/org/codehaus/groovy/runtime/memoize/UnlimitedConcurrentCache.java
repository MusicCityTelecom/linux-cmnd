/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.concurrent.ThreadSafe
 */
package org.codehaus.groovy.runtime.memoize;

import java.io.Serializable;
import java.lang.ref.SoftReference;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.concurrent.ThreadSafe;
import org.codehaus.groovy.runtime.memoize.EvictableCache;
import org.codehaus.groovy.runtime.memoize.MemoizeCache;

@ThreadSafe
public final class UnlimitedConcurrentCache<K, V>
implements EvictableCache<K, V>,
Serializable {
    private static final long serialVersionUID = -857220494475488328L;
    private final ConcurrentHashMap<K, V> map;

    public UnlimitedConcurrentCache() {
        this.map = new ConcurrentHashMap();
    }

    public UnlimitedConcurrentCache(int initialCapacity) {
        this.map = new ConcurrentHashMap(initialCapacity);
    }

    public UnlimitedConcurrentCache(Map<? extends K, ? extends V> m) {
        this();
        this.map.putAll(m);
    }

    @Override
    public V remove(Object key) {
        return this.map.remove(key);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        this.map.putAll(m);
    }

    @Override
    public Set<K> keySet() {
        return this.map.keySet();
    }

    @Override
    public Map<K, V> clearAll() {
        LinkedHashMap<K, V> result = new LinkedHashMap<K, V>(this.map.size());
        for (Map.Entry<K, V> entry : this.map.entrySet()) {
            V value;
            K key = entry.getKey();
            boolean removed = this.map.remove(key, value = entry.getValue());
            if (!removed) continue;
            result.put(key, value);
        }
        return result;
    }

    @Override
    public Collection<V> values() {
        return this.map.values();
    }

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        return this.map.entrySet();
    }

    @Override
    public Set<K> keys() {
        return this.map.keySet();
    }

    @Override
    public boolean containsKey(Object key) {
        return this.map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return this.map.containsValue(value);
    }

    @Override
    public int size() {
        return this.map.size();
    }

    @Override
    public boolean isEmpty() {
        return this.map.isEmpty();
    }

    @Override
    public V put(K key, V value) {
        return this.map.put(key, value);
    }

    @Override
    public V get(Object key) {
        return this.map.get(key);
    }

    @Override
    public V getAndPut(K key, MemoizeCache.ValueProvider<? super K, ? extends V> valueProvider) {
        return (V)this.map.computeIfAbsent(key, valueProvider::provide);
    }

    @Override
    public void cleanUpNullReferences() {
        for (Map.Entry<K, V> entry : this.map.entrySet()) {
            V entryVal = entry.getValue();
            if (!(entryVal instanceof SoftReference) || ((SoftReference)entryVal).get() != null) continue;
            this.map.remove(entry.getKey(), entryVal);
        }
    }
}

