/*
 * Decompiled with CFR 0.152.
 */
package com.github.benmanes.caffeine.cache;

import com.github.benmanes.caffeine.cache.Interner;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

final class StrongInterner<E>
implements Interner<E> {
    final ConcurrentMap<E, E> map = new ConcurrentHashMap<E, E>();

    StrongInterner() {
    }

    @Override
    public E intern(E sample) {
        Object canonical = this.map.get(sample);
        if (canonical != null) {
            return (E)canonical;
        }
        E value = this.map.putIfAbsent(sample, sample);
        if (value == null) {
            return sample;
        }
        return value;
    }
}

