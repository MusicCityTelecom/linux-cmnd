/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.internal.guava;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public interface Multimap<K, V> {
    public int size();

    public boolean containsKey(Object var1);

    public boolean containsValue(Object var1);

    public boolean containsEntry(Object var1, Object var2);

    public boolean put(K var1, V var2);

    public boolean remove(Object var1, Object var2);

    public boolean putAll(K var1, Iterable<? extends V> var2);

    public Collection<V> removeAll(Object var1);

    public void clear();

    public Collection<V> get(K var1);

    public Set<K> keySet();

    public Collection<V> values();

    public Collection<Map.Entry<K, V>> entries();

    public Map<K, Collection<V>> asMap();

    public boolean equals(Object var1);

    public int hashCode();
}

