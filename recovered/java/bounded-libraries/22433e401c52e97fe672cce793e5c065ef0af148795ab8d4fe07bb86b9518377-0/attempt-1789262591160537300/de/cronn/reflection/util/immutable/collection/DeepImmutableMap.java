/*
 * Decompiled with CFR 0.152.
 */
package de.cronn.reflection.util.immutable.collection;

import de.cronn.reflection.util.immutable.Immutable;
import de.cronn.reflection.util.immutable.ImmutableProxy;
import de.cronn.reflection.util.immutable.ImmutableProxyOption;
import de.cronn.reflection.util.immutable.collection.DeepImmutableEntrySet;
import java.io.Serializable;
import java.util.AbstractMap;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Set;

public class DeepImmutableMap<K, V>
extends AbstractMap<K, V>
implements Immutable,
Serializable {
    private static final long serialVersionUID = 1L;
    static final String IMMUTABLE_MESSAGE = "This map is immutable";
    private final Map<K, V> delegate;
    private final Map<K, K> immutableKeyCache = new IdentityHashMap<K, K>();
    private final Map<V, V> immutableValueCache = new IdentityHashMap<V, V>();

    public DeepImmutableMap(Map<K, V> delegate) {
        this.delegate = delegate;
    }

    K getImmutableKey(K key) {
        return (K)this.immutableKeyCache.computeIfAbsent(key, x$0 -> ImmutableProxy.create(x$0, new ImmutableProxyOption[0]));
    }

    V getImmutableValue(V value) {
        return (V)this.immutableValueCache.computeIfAbsent(value, x$0 -> ImmutableProxy.create(x$0, new ImmutableProxyOption[0]));
    }

    @Override
    public V get(Object key) {
        V value = this.delegate.get(key);
        return this.getImmutableValue(value);
    }

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        return new DeepImmutableEntrySet<K, V>(this.delegate.entrySet(), this);
    }

    @Override
    public V put(K key, V value) {
        throw new UnsupportedOperationException(IMMUTABLE_MESSAGE);
    }

    @Override
    public V remove(Object key) {
        throw new UnsupportedOperationException(IMMUTABLE_MESSAGE);
    }
}

