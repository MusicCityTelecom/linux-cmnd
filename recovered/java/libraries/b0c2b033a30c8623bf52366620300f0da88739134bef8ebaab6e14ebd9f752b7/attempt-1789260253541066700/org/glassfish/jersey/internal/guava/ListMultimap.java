/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.internal.guava;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.glassfish.jersey.internal.guava.Multimap;

public interface ListMultimap<K, V>
extends Multimap<K, V> {
    @Override
    public List<V> get(K var1);

    @Override
    public List<V> removeAll(Object var1);

    @Override
    public Map<K, Collection<V>> asMap();

    @Override
    public boolean equals(Object var1);
}

