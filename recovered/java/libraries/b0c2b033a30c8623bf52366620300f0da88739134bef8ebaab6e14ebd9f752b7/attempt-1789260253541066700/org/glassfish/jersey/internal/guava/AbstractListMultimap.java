/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.internal.guava;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.glassfish.jersey.internal.guava.AbstractMapBasedMultimap;
import org.glassfish.jersey.internal.guava.ListMultimap;

abstract class AbstractListMultimap<K, V>
extends AbstractMapBasedMultimap<K, V>
implements ListMultimap<K, V> {
    private static final long serialVersionUID = 6588350623831699109L;

    AbstractListMultimap(Map<K, Collection<V>> map) {
        super(map);
    }

    @Override
    abstract List<V> createCollection();

    @Override
    public List<V> get(K key) {
        return (List)super.get(key);
    }

    @Override
    public List<V> removeAll(Object key) {
        return (List)super.removeAll(key);
    }

    @Override
    public boolean put(K key, V value) {
        return super.put(key, value);
    }

    @Override
    public Map<K, Collection<V>> asMap() {
        return super.asMap();
    }

    @Override
    public boolean equals(Object object) {
        return super.equals(object);
    }
}

