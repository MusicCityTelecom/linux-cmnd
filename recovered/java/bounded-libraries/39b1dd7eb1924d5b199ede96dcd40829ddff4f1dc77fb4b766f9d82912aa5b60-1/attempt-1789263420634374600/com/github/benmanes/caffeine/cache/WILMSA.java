/*
 * Decompiled with CFR 0.152.
 */
package com.github.benmanes.caffeine.cache;

import com.github.benmanes.caffeine.cache.AsyncCacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Expiry;
import com.github.benmanes.caffeine.cache.Pacer;
import com.github.benmanes.caffeine.cache.Scheduler;
import com.github.benmanes.caffeine.cache.Ticker;
import com.github.benmanes.caffeine.cache.TimerWheel;
import com.github.benmanes.caffeine.cache.WILMS;

class WILMSA<K, V>
extends WILMS<K, V> {
    final Ticker ticker;
    final Expiry<K, V> expiry;
    final TimerWheel<K, V> timerWheel;
    volatile long expiresAfterAccessNanos;
    final Pacer pacer;

    WILMSA(Caffeine<K, V> caffeine, AsyncCacheLoader<? super K, V> asyncCacheLoader, boolean bl) {
        super(caffeine, asyncCacheLoader, bl);
        this.ticker = caffeine.getTicker();
        this.expiry = caffeine.getExpiry(this.isAsync);
        this.timerWheel = caffeine.expiresVariable() ? new TimerWheel(this) : null;
        this.expiresAfterAccessNanos = caffeine.getExpiresAfterAccessNanos();
        this.pacer = caffeine.getScheduler() == Scheduler.disabledScheduler() ? null : new Pacer(caffeine.getScheduler());
    }

    @Override
    public final Ticker expirationTicker() {
        return this.ticker;
    }

    @Override
    protected final boolean expiresVariable() {
        return this.timerWheel != null;
    }

    @Override
    public final Expiry<K, V> expiry() {
        return this.expiry;
    }

    @Override
    protected final TimerWheel<K, V> timerWheel() {
        return this.timerWheel;
    }

    @Override
    protected final boolean expiresAfterAccess() {
        return this.timerWheel == null;
    }

    @Override
    protected final long expiresAfterAccessNanos() {
        return this.expiresAfterAccessNanos;
    }

    @Override
    protected final void setExpiresAfterAccessNanos(long l) {
        this.expiresAfterAccessNanos = l;
    }

    @Override
    public final Pacer pacer() {
        return this.pacer;
    }
}

