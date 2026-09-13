/*
 * Decompiled with CFR 0.152.
 */
package com.github.benmanes.caffeine.cache;

import com.github.benmanes.caffeine.cache.AsyncCacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Ticker;
import com.github.benmanes.caffeine.cache.WS;
import com.github.benmanes.caffeine.cache.stats.StatsCounter;

class WSS<K, V>
extends WS<K, V> {
    final StatsCounter statsCounter;

    WSS(Caffeine<K, V> caffeine, AsyncCacheLoader<? super K, V> asyncCacheLoader, boolean bl) {
        super(caffeine, asyncCacheLoader, bl);
        this.statsCounter = caffeine.getStatsCounterSupplier().get();
    }

    @Override
    public final boolean isRecordingStats() {
        return true;
    }

    @Override
    public final Ticker statsTicker() {
        return Ticker.systemTicker();
    }

    @Override
    public final StatsCounter statsCounter() {
        return this.statsCounter;
    }
}

