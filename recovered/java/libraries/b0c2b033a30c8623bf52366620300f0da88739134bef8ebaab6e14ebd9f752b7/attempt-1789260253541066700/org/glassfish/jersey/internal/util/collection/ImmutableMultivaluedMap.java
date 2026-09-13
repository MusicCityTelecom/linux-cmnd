/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.internal.util.collection;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

public class ImmutableMultivaluedMap<K, V>
implements MultivaluedMap<K, V> {
    private final MultivaluedMap<K, V> delegate;

    public static <K, V> ImmutableMultivaluedMap<K, V> empty() {
        return new ImmutableMultivaluedMap(new MultivaluedHashMap());
    }

    public ImmutableMultivaluedMap(MultivaluedMap<K, V> delegate) {
        if (delegate == null) {
            throw new NullPointerException("ImmutableMultivaluedMap delegate must not be 'null'.");
        }
        this.delegate = delegate;
    }

    @Override
    public boolean equalsIgnoreValueOrder(MultivaluedMap<K, V> otherMap) {
        return this.delegate.equalsIgnoreValueOrder(otherMap);
    }

    @Override
    public void putSingle(K key, V value) {
        throw new UnsupportedOperationException("This MultivaluedMap implementation is immutable.");
    }

    @Override
    public void add(K key, V value) {
        throw new UnsupportedOperationException("This MultivaluedMap implementation is immutable.");
    }

    @Override
    public V getFirst(K key) {
        return this.delegate.getFirst(key);
    }

    @Override
    public void addAll(K key, V ... newValues) {
        throw new UnsupportedOperationException("This MultivaluedMap implementation is immutable.");
    }

    @Override
    public void addAll(K key, List<V> valueList) {
        throw new UnsupportedOperationException("This MultivaluedMap implementation is immutable.");
    }

    @Override
    public void addFirst(K key, V value) {
        throw new UnsupportedOperationException("This MultivaluedMap implementation is immutable.");
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
    public List<V> get(Object key) {
        return (List)this.delegate.get(key);
    }

    @Override
    public List<V> put(K key, List<V> value) {
        throw new UnsupportedOperationException("This MultivaluedMap implementation is immutable.");
    }

    @Override
    public List<V> remove(Object key) {
        throw new UnsupportedOperationException("This MultivaluedMap implementation is immutable.");
    }

    @Override
    public void putAll(Map<? extends K, ? extends List<V>> m) {
        throw new UnsupportedOperationException("This MultivaluedMap implementation is immutable.");
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException("This MultivaluedMap implementation is immutable.");
    }

    @Override
    public Set<K> keySet() {
        return Collections.unmodifiableSet(this.delegate.keySet());
    }

    @Override
    public Collection<List<V>> values() {
        return Collections.unmodifiableCollection(this.delegate.values());
    }

    @Override
    public Set<Map.Entry<K, List<V>>> entrySet() {
        return Collections.unmodifiableSet(this.delegate.entrySet());
    }

    public String toString() {
        return this.delegate.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ImmutableMultivaluedMap)) {
            return false;
        }
        ImmutableMultivaluedMap that = (ImmutableMultivaluedMap)o;
        return this.delegate.equals(that.delegate);
    }

    @Override
    public int hashCode() {
        return this.delegate.hashCode();
    }
}

