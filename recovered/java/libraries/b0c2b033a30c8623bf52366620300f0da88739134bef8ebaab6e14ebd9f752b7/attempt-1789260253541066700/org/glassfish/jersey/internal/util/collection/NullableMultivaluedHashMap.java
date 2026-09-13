/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.internal.util.collection;

import java.util.List;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

public class NullableMultivaluedHashMap<K, V>
extends MultivaluedHashMap<K, V> {
    public NullableMultivaluedHashMap() {
    }

    public NullableMultivaluedHashMap(int initialCapacity) {
        super(initialCapacity);
    }

    public NullableMultivaluedHashMap(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }

    public NullableMultivaluedHashMap(MultivaluedMap<? extends K, ? extends V> map) {
        super(map);
    }

    @Override
    protected void addFirstNull(List<V> values) {
        values.add(null);
    }

    @Override
    protected void addNull(List<V> values) {
        values.add(null);
    }
}

