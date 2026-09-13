/*
 * Decompiled with CFR 0.152.
 */
package com.github.benmanes.caffeine.cache;

import com.github.benmanes.caffeine.cache.BoundedLocalCache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Interner;

final class WeakInterner<E>
implements Interner<E> {
    final BoundedLocalCache<E, Boolean> cache = Caffeine.newWeakInterner();

    WeakInterner() {
    }

    @Override
    public E intern(E sample) {
        Boolean value;
        do {
            E canonical;
            if ((canonical = this.cache.getKey(sample)) == null) continue;
            return canonical;
        } while ((value = this.cache.putIfAbsent(sample, Boolean.TRUE)) != null);
        return sample;
    }
}

