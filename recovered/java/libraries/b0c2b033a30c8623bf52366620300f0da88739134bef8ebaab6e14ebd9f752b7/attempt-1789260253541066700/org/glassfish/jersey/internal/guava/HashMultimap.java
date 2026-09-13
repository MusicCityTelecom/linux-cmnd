/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.internal.guava;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Set;
import org.glassfish.jersey.internal.guava.AbstractSetMultimap;
import org.glassfish.jersey.internal.guava.Maps;
import org.glassfish.jersey.internal.guava.Serialization;
import org.glassfish.jersey.internal.guava.Sets;

public final class HashMultimap<K, V>
extends AbstractSetMultimap<K, V> {
    private static final int DEFAULT_VALUES_PER_KEY = 2;
    private static final long serialVersionUID = 0L;
    private transient int expectedValuesPerKey = 2;

    private HashMultimap() {
        super(new HashMap());
    }

    public static <K, V> HashMultimap<K, V> create() {
        return new HashMultimap<K, V>();
    }

    @Override
    Set<V> createCollection() {
        return Sets.newHashSetWithExpectedSize(this.expectedValuesPerKey);
    }

    private void writeObject(ObjectOutputStream stream) throws IOException {
        stream.defaultWriteObject();
        stream.writeInt(this.expectedValuesPerKey);
        Serialization.writeMultimap(this, stream);
    }

    private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        stream.defaultReadObject();
        this.expectedValuesPerKey = stream.readInt();
        int distinctKeys = Serialization.readCount(stream);
        HashMap map = Maps.newHashMapWithExpectedSize(distinctKeys);
        this.setMap(map);
        Serialization.populateMultimap(this, stream, distinctKeys);
    }
}

