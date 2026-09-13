/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.internal.guava;

import java.util.Map;
import java.util.Objects;
import org.glassfish.jersey.internal.guava.ForwardingObject;

public abstract class ForwardingMapEntry<K, V>
extends ForwardingObject
implements Map.Entry<K, V> {
    ForwardingMapEntry() {
    }

    @Override
    protected abstract Map.Entry<K, V> delegate();

    @Override
    public K getKey() {
        return this.delegate().getKey();
    }

    @Override
    public V getValue() {
        return this.delegate().getValue();
    }

    @Override
    public V setValue(V value) {
        return this.delegate().setValue(value);
    }

    @Override
    public boolean equals(Object object) {
        return this.delegate().equals(object);
    }

    @Override
    public int hashCode() {
        return this.delegate().hashCode();
    }

    boolean standardEquals(Object object) {
        if (object instanceof Map.Entry) {
            Map.Entry that = (Map.Entry)object;
            return Objects.equals(this.getKey(), that.getKey()) && Objects.equals(this.getValue(), that.getValue());
        }
        return false;
    }
}

