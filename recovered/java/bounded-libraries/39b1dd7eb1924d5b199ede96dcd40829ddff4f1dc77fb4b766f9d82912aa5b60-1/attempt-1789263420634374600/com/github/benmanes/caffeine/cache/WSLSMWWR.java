/*
 * Decompiled with CFR 0.152.
 */
package com.github.benmanes.caffeine.cache;

import com.github.benmanes.caffeine.cache.AsyncCacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.WSLSMWW;

final class WSLSMWWR<K, V>
extends WSLSMWW<K, V> {
    volatile long refreshAfterWriteNanos;

    WSLSMWWR(Caffeine<K, V> caffeine, AsyncCacheLoader<? super K, V> asyncCacheLoader, boolean bl) {
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

