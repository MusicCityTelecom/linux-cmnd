/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.runtime.memoize;

import java.io.Serializable;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import org.codehaus.groovy.runtime.memoize.EvictableCache;
import org.codehaus.groovy.runtime.memoize.MemoizeCache;
import org.codehaus.groovy.runtime.memoize.ValueConvertable;

public class CommonCache<K, V>
implements EvictableCache<K, V>,
ValueConvertable<V, Object>,
Serializable {
    private static final long serialVersionUID = 934699400232698324L;
    public static final float DEFAULT_LOAD_FACTOR = 0.75f;
    public static final int DEFAULT_INITIAL_CAPACITY = 16;
    private final Map<K, V> map;

    public CommonCache() {
        this(new LinkedHashMap());
    }

    public CommonCache(int initialCapacity, final int maxSize, EvictableCache.EvictionStrategy evictionStrategy) {
        this(new LinkedHashMap<K, V>(initialCapacity, 0.75f, EvictableCache.EvictionStrategy.LRU == evictionStrategy){
            private static final long serialVersionUID = -8012450791479726621L;

            @Override
            protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
                return this.size() > maxSize;
            }
        });
    }

    public CommonCache(int initialCapacity, int maxSize) {
        this(initialCapacity, maxSize, EvictableCache.EvictionStrategy.LRU);
    }

    public CommonCache(int maxSize) {
        this(16, maxSize);
    }

    public CommonCache(Map<K, V> map) {
        this.map = map;
    }

    @Override
    public V get(Object key) {
        return this.map.get(key);
    }

    @Override
    public V put(K key, V value) {
        return this.map.put(key, value);
    }

    @Override
    public V getAndPut(K key, MemoizeCache.ValueProvider<? super K, ? extends V> valueProvider) {
        return this.getAndPut(key, valueProvider, true);
    }

    public V getAndPut(K key, MemoizeCache.ValueProvider<? super K, ? extends V> valueProvider, boolean shouldCache) {
        V value = this.get((Object)key);
        if (null != this.convertValue(value)) {
            return value;
        }
        value = null == valueProvider ? null : valueProvider.provide((K)key);
        Object v0 = value;
        if (shouldCache && null != this.convertValue(value)) {
            this.put(key, value);
        }
        return value;
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
    public boolean containsValue(Object value) {
        return this.map.containsValue(value);
    }

    @Override
    public boolean containsKey(Object key) {
        return this.map.containsKey(key);
    }

    @Override
    public int size() {
        return this.map.size();
    }

    @Override
    public boolean isEmpty() {
        return this.size() == 0;
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
        LinkedHashMap<K, V> result = new LinkedHashMap<K, V>(this.map);
        this.map.clear();
        return result;
    }

    @Override
    public void cleanUpNullReferences() {
        LinkedList<K> keys = new LinkedList<K>();
        for (Map.Entry<K, V> entry : this.map.entrySet()) {
            K key = entry.getKey();
            V value = entry.getValue();
            if (null != value && (!(value instanceof SoftReference) || null != ((SoftReference)value).get()) && (!(value instanceof WeakReference) || null != ((WeakReference)value).get())) continue;
            keys.add(key);
        }
        for (Map.Entry<K, V> key : keys) {
            this.map.remove(key);
        }
    }

    public String toString() {
        return this.map.toString();
    }

    @Override
    public Object convertValue(V value) {
        return value;
    }
}

