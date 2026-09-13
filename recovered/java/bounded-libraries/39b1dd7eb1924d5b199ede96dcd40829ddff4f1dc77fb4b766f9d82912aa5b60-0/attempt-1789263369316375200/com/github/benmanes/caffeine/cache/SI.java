/*
 * Decompiled with CFR 0.152.
 */
package com.github.benmanes.caffeine.cache;

import com.github.benmanes.caffeine.cache.AsyncCacheLoader;
import com.github.benmanes.caffeine.cache.BoundedLocalCache;
import com.github.benmanes.caffeine.cache.Caffeine;
import java.lang.ref.ReferenceQueue;

class SI<K, V>
extends BoundedLocalCache<K, V> {
    final ReferenceQueue<V> valueReferenceQueue = new ReferenceQueue();

    SI(Caffeine<K, V> caffeine, AsyncCacheLoader<? super K, V> asyncCacheLoader, boolean bl) {
        super(caffeine, asyncCacheLoader, bl);
    }

    @Override
    protected final ReferenceQueue<V> valueReferenceQueue() {
        return this.valueReferenceQueue;
    }

    @Override
    protected final boolean collectValues() {
        return true;
    }
}

