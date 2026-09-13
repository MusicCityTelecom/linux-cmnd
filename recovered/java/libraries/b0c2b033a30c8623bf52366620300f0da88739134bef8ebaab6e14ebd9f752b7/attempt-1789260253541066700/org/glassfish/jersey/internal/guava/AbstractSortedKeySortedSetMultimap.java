/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.internal.guava;

import java.util.Collection;
import java.util.SortedMap;
import java.util.SortedSet;
import org.glassfish.jersey.internal.guava.AbstractSortedSetMultimap;

abstract class AbstractSortedKeySortedSetMultimap<K, V>
extends AbstractSortedSetMultimap<K, V> {
    AbstractSortedKeySortedSetMultimap(SortedMap<K, Collection<V>> map) {
        super(map);
    }

    @Override
    public SortedMap<K, Collection<V>> asMap() {
        return (SortedMap)super.asMap();
    }

    @Override
    SortedMap<K, Collection<V>> backingMap() {
        return (SortedMap)super.backingMap();
    }

    @Override
    public SortedSet<K> keySet() {
        return (SortedSet)super.keySet();
    }
}

