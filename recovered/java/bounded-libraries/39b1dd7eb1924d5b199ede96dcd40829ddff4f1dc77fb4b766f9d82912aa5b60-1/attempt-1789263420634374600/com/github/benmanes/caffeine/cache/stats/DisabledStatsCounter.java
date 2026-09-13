/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.checkerframework.checker.index.qual.NonNegative
 */
package com.github.benmanes.caffeine.cache.stats;

import com.github.benmanes.caffeine.cache.RemovalCause;
import com.github.benmanes.caffeine.cache.stats.CacheStats;
import com.github.benmanes.caffeine.cache.stats.StatsCounter;
import java.util.Objects;
import org.checkerframework.checker.index.qual.NonNegative;

enum DisabledStatsCounter implements StatsCounter
{
    INSTANCE;


    @Override
    public void recordHits(int count) {
    }

    @Override
    public void recordMisses(int count) {
    }

    @Override
    public void recordLoadSuccess(long loadTime) {
    }

    @Override
    public void recordLoadFailure(long loadTime) {
    }

    @Override
    public void recordEviction(@NonNegative int weight, RemovalCause cause) {
        Objects.requireNonNull(cause);
    }

    @Override
    public CacheStats snapshot() {
        return CacheStats.empty();
    }

    public String toString() {
        return this.snapshot().toString();
    }
}

