/*
 * Decompiled with CFR 0.152.
 */
package org.apache.groovy.util.concurrent;

import java.util.EnumSet;
import org.apache.groovy.util.concurrent.ConcurrentReferenceHashMap;

public class ManagedIdentityConcurrentMap<K, V>
extends ConcurrentReferenceHashMap<K, V> {
    private static final long serialVersionUID = 1046734443288902802L;

    public ManagedIdentityConcurrentMap() {
        this(ConcurrentReferenceHashMap.ReferenceType.WEAK);
    }

    public ManagedIdentityConcurrentMap(int initialCapacity) {
        this(ConcurrentReferenceHashMap.ReferenceType.WEAK, initialCapacity);
    }

    public ManagedIdentityConcurrentMap(ConcurrentReferenceHashMap.ReferenceType keyType) {
        super(keyType, ConcurrentReferenceHashMap.ReferenceType.STRONG, EnumSet.of(ConcurrentReferenceHashMap.Option.IDENTITY_COMPARISONS));
    }

    public ManagedIdentityConcurrentMap(ConcurrentReferenceHashMap.ReferenceType keyType, int initialCapacity) {
        super(initialCapacity, keyType, ConcurrentReferenceHashMap.ReferenceType.STRONG, EnumSet.of(ConcurrentReferenceHashMap.Option.IDENTITY_COMPARISONS));
    }

    public V getOrPut(K key, V value) {
        return (V)this.applyIfAbsent(key, k -> value);
    }
}

