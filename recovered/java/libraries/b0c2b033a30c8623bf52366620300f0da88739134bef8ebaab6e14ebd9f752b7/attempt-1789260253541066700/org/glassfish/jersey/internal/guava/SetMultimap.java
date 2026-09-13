/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.internal.guava;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import org.glassfish.jersey.internal.guava.Multimap;

public interface SetMultimap<K, V>
extends Multimap<K, V> {
    @Override
    public Set<V> get(K var1);

    @Override
    public Set<V> removeAll(Object var1);

    @Override
    public Set<Map.Entry<K, V>> entries();

    @Override
    public Map<K, Collection<V>> asMap();

    @Override
    public boolean equals(Object var1);
}

