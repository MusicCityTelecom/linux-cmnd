/*
 * Decompiled with CFR 0.152.
 */
package com.github.benmanes.caffeine.cache;

import com.github.benmanes.caffeine.cache.AsyncCacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Node;
import com.github.benmanes.caffeine.cache.Pacer;
import com.github.benmanes.caffeine.cache.SISMS;
import com.github.benmanes.caffeine.cache.Scheduler;
import com.github.benmanes.caffeine.cache.Ticker;
import com.github.benmanes.caffeine.cache.WriteOrderDeque;

class SISMSW<K, V>
extends SISMS<K, V> {
    final Ticker ticker;
    final WriteOrderDeque<Node<K, V>> writeOrderDeque;
    volatile long expiresAfterWriteNanos;
    final Pacer pacer;

    SISMSW(Caffeine<K, V> caffeine, AsyncCacheLoader<? super K, V> asyncCacheLoader, boolean bl) {
        super(caffeine, asyncCacheLoader, bl);
        this.ticker = caffeine.getTicker();
        this.writeOrderDeque = new WriteOrderDeque();
        this.expiresAfterWriteNanos = caffeine.getExpiresAfterWriteNanos();
        this.pacer = caffeine.getScheduler() == Scheduler.disabledScheduler() ? null : new Pacer(caffeine.getScheduler());
    }

    @Override
    public final Ticker expirationTicker() {
        return this.ticker;
    }

    @Override
    protected final WriteOrderDeque<Node<K, V>> writeOrderDeque() {
        return this.writeOrderDeque;
    }

    @Override
    protected final boolean expiresAfterWrite() {
        return true;
    }

    @Override
    protected final long expiresAfterWriteNanos() {
        return this.expiresAfterWriteNanos;
    }

    @Override
    protected final void setExpiresAfterWriteNanos(long l) {
        this.expiresAfterWriteNanos = l;
    }

    @Override
    public final Pacer pacer() {
        return this.pacer;
    }
}

