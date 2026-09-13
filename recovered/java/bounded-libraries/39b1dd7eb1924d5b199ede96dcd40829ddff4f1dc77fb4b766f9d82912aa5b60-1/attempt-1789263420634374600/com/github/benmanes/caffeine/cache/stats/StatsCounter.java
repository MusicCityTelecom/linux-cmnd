/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.checkerframework.checker.index.qual.NonNegative
 */
package com.github.benmanes.caffeine.cache.stats;

import com.github.benmanes.caffeine.cache.RemovalCause;
import com.github.benmanes.caffeine.cache.stats.CacheStats;
import com.github.benmanes.caffeine.cache.stats.DisabledStatsCounter;
import com.github.benmanes.caffeine.cache.stats.GuardedStatsCounter;
import org.checkerframework.checker.index.qual.NonNegative;

public interface StatsCounter {
    public void recordHits(@NonNegative int var1);

    public void recordMisses(@NonNegative int var1);

    public void recordLoadSuccess(@NonNegative long var1);

    public void recordLoadFailure(@NonNegative long var1);

    public void recordEviction(@NonNegative int var1, RemovalCause var2);

    public CacheStats snapshot();

    public static StatsCounter disabledStatsCounter() {
        return DisabledStatsCounter.INSTANCE;
    }

    public static StatsCounter guardedStatsCounter(StatsCounter statsCounter) {
        return statsCounter instanceof GuardedStatsCounter ? statsCounter : new GuardedStatsCounter(statsCounter);
    }
}

