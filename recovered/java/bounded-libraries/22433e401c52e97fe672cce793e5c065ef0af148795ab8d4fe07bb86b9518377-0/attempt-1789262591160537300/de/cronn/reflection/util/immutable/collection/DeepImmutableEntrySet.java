/*
 * Decompiled with CFR 0.152.
 */
package de.cronn.reflection.util.immutable.collection;

import de.cronn.reflection.util.immutable.collection.DeepImmutableCollection;
import de.cronn.reflection.util.immutable.collection.DeepImmutableMap;
import java.util.Map;
import java.util.Set;

class DeepImmutableEntrySet<K, V>
extends DeepImmutableCollection<Map.Entry<K, V>>
implements Set<Map.Entry<K, V>> {
    private static final long serialVersionUID = 1L;
    private final DeepImmutableMap<K, V> immutableMap;

    DeepImmutableEntrySet(Set<Map.Entry<K, V>> entrySet, DeepImmutableMap<K, V> immutableMap) {
        super(entrySet, "This map is immutable");
        this.immutableMap = immutableMap;
    }

    @Override
    Map.Entry<K, V> createImmutableElement(Map.Entry<K, V> entry) {
        return new ImmutableEntry<K, V>(entry, this.immutableMap);
    }

    private static class ImmutableEntry<K, V>
    implements Map.Entry<K, V> {
        private final Map.Entry<K, V> delegate;
        private final transient DeepImmutableMap<K, V> immutableMap;

        ImmutableEntry(Map.Entry<K, V> delegate, DeepImmutableMap<K, V> immutableMap) {
            this.delegate = delegate;
            this.immutableMap = immutableMap;
        }

        @Override
        public K getKey() {
            K key = this.delegate.getKey();
            return this.immutableMap.getImmutableKey(key);
        }

        @Override
        public V getValue() {
            V value = this.delegate.getValue();
            return this.immutableMap.getImmutableValue(value);
        }

        @Override
        public V setValue(V value) {
            throw new UnsupportedOperationException("This map is immutable");
        }
    }
}

