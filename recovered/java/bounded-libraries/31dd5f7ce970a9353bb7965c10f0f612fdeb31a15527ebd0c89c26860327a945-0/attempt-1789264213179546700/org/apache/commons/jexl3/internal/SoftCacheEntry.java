/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.jexl3.internal;

import java.util.Map;

class SoftCacheEntry<K, V>
implements Map.Entry<K, V> {
    private final K key;
    private final V value;

    SoftCacheEntry(Map.Entry<K, V> e) {
        this.key = e.getKey();
        this.value = e.getValue();
    }

    @Override
    public K getKey() {
        return this.key;
    }

    @Override
    public V getValue() {
        return this.value;
    }

    @Override
    public V setValue(V v) {
        throw new UnsupportedOperationException("Not supported.");
    }
}

