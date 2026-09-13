/*
 * Decompiled with CFR 0.152.
 */
package groovy.lang;

import groovy.lang.Closure;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

public final class MapWithDefault<K, V>
implements Map<K, V> {
    private final Map<K, V> delegate;
    private final Closure<V> initClosure;
    private final boolean autoGrow;
    private final boolean autoShrink;

    private MapWithDefault(Map<K, V> m, Closure<V> initClosure, boolean autoGrow, boolean autoShrink) {
        this.delegate = m;
        this.initClosure = initClosure;
        this.autoGrow = autoGrow;
        this.autoShrink = autoShrink;
    }

    public static <K, V> Map<K, V> newInstance(Map<K, V> m, Closure<V> initClosure) {
        return new MapWithDefault<K, V>(m, initClosure, true, false);
    }

    public static <K, V> Map<K, V> newInstance(Map<K, V> m, boolean autoGrow, boolean autoShrink, Closure<V> initClosure) {
        return new MapWithDefault<K, V>(m, initClosure, autoGrow, autoShrink);
    }

    @Override
    public int size() {
        return this.delegate.size();
    }

    @Override
    public boolean isEmpty() {
        return this.delegate.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return this.delegate.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return this.delegate.containsValue(value);
    }

    @Override
    public V get(Object key) {
        if (this.delegate.containsKey(key)) {
            return this.delegate.get(key);
        }
        V value = this.getDefaultValue(key);
        if (this.autoGrow) {
            this.delegate.put(key, value);
        }
        return value;
    }

    private V getDefaultValue(Object key) {
        return this.initClosure.call(new Object[]{key});
    }

    @Override
    public V put(K key, V value) {
        if (this.autoShrink && value.equals(this.getDefaultValue(key))) {
            return this.remove(key);
        }
        return this.delegate.put(key, value);
    }

    @Override
    public V remove(Object key) {
        return this.delegate.remove(key);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        m.entrySet().forEach((? super T e) -> this.put(e.getKey(), e.getValue()));
    }

    @Override
    public void clear() {
        this.delegate.clear();
    }

    @Override
    public Set<K> keySet() {
        return this.delegate.keySet();
    }

    @Override
    public Collection<V> values() {
        return this.delegate.values();
    }

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        return this.delegate.entrySet();
    }

    @Override
    public boolean equals(Object obj) {
        return this.delegate.equals(obj);
    }

    @Override
    public int hashCode() {
        return this.delegate.hashCode();
    }
}

