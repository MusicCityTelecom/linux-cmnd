/*
 * Decompiled with CFR 0.152.
 */
package com.github.benmanes.caffeine.cache;

import com.github.benmanes.caffeine.cache.AsyncCacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.SSSMWAW;

final class SSSMWAWR<K, V>
extends SSSMWAW<K, V> {
    volatile long refreshAfterWriteNanos;

    SSSMWAWR(Caffeine<K, V> caffeine, AsyncCacheLoader<? super K, V> asyncCacheLoader, boolean bl) {
        super(caffeine, asyncCacheLoader, bl);
        this.refreshAfterWriteNanos = caffeine.getRefreshAfterWriteNanos();
    }

    @Override
    protected boolean refreshAfterWrite() {
        return true;
    }

    @Override
    protected long refreshAfterWriteNanos() {
        return this.refreshAfterWriteNanos;
    }

    @Override
    protected void setRefreshAfterWriteNanos(long l) {
        this.refreshAfterWriteNanos = l;
    }
}

