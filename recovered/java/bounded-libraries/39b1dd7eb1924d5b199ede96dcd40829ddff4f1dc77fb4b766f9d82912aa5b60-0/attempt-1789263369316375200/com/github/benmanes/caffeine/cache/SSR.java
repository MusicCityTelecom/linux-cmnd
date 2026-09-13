/*
 * Decompiled with CFR 0.152.
 */
package com.github.benmanes.caffeine.cache;

import com.github.benmanes.caffeine.cache.AsyncCacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.SS;
import com.github.benmanes.caffeine.cache.Ticker;

final class SSR<K, V>
extends SS<K, V> {
    final Ticker ticker;
    volatile long refreshAfterWriteNanos;

    SSR(Caffeine<K, V> caffeine, AsyncCacheLoader<? super K, V> asyncCacheLoader, boolean bl) {
        super(caffeine, asyncCacheLoader, bl);
        this.ticker = caffeine.getTicker();
        this.refreshAfterWriteNanos = caffeine.getRefreshAfterWriteNanos();
    }

    @Override
    public Ticker expirationTicker() {
        return this.ticker;
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

