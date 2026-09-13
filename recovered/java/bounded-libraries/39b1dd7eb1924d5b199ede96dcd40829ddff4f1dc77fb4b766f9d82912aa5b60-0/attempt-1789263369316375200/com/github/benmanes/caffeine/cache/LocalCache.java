/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.checkerframework.checker.nullness.qual.Nullable
 */
package com.github.benmanes.caffeine.cache;

import com.github.benmanes.caffeine.cache.Expiry;
import com.github.benmanes.caffeine.cache.RemovalCause;
import com.github.benmanes.caffeine.cache.Ticker;
import com.github.benmanes.caffeine.cache.stats.StatsCounter;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Executor;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.checkerframework.checker.nullness.qual.Nullable;

interface LocalCache<K, V>
extends ConcurrentMap<K, V> {
    public boolean isAsync();

    public boolean isRecordingStats();

    public StatsCounter statsCounter();

    public void notifyRemoval(@Nullable K var1, @Nullable V var2, RemovalCause var3);

    public Executor executor();

    public ConcurrentMap<Object, CompletableFuture<?>> refreshes();

    public boolean hasWriteTime();

    public @Nullable Expiry<K, V> expiry();

    public Ticker statsTicker();

    public long estimatedSize();

    public Object referenceKey(K var1);

    public @Nullable V getIfPresent(K var1, boolean var2);

    public @Nullable V getIfPresentQuietly(Object var1);

    public @Nullable V getIfPresentQuietly(K var1, long[] var2);

    public Map<K, V> getAllPresent(Iterable<? extends K> var1);

    @Override
    default public @Nullable V compute(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        return this.compute(key, remappingFunction, this.expiry(), true, true);
    }

    public @Nullable V compute(K var1, BiFunction<? super K, ? super V, ? extends V> var2, @Nullable Expiry<? super K, ? super V> var3, boolean var4, boolean var5);

    @Override
    default public @Nullable V computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction) {
        return this.computeIfAbsent(key, mappingFunction, true, true);
    }

    public @Nullable V computeIfAbsent(K var1, Function<? super K, ? extends V> var2, boolean var3, boolean var4);

    default public void invalidateAll(Iterable<?> keys) {
        for (Object key : keys) {
            this.remove(key);
        }
    }

    public void cleanUp();

    default public void notifyOnReplace(K key, V oldValue, V newValue) {
        if (oldValue == null || oldValue == newValue) {
            return;
        }
        if (this.isAsync()) {
            CompletableFuture oldFuture = (CompletableFuture)oldValue;
            CompletableFuture newFuture = (CompletableFuture)newValue;
            newFuture.whenCompleteAsync((nv, e) -> {
                if (e == null) {
                    oldFuture.thenAcceptAsync(ov -> {
                        if (nv != ov) {
                            this.notifyRemoval(key, oldValue, RemovalCause.REPLACED);
                        }
                    }, this.executor());
                } else {
                    this.notifyRemoval(key, oldValue, RemovalCause.REPLACED);
                }
            }, this.executor());
        } else {
            this.notifyRemoval(key, oldValue, RemovalCause.REPLACED);
        }
    }

    default public <T, R> Function<? super T, ? extends R> statsAware(Function<? super T, ? extends R> mappingFunction, boolean recordLoad) {
        if (!this.isRecordingStats()) {
            return mappingFunction;
        }
        return key -> {
            Object value;
            this.statsCounter().recordMisses(1);
            long startTime = this.statsTicker().read();
            try {
                value = mappingFunction.apply(key);
            }
            catch (Error | RuntimeException e) {
                this.statsCounter().recordLoadFailure(this.statsTicker().read() - startTime);
                throw e;
            }
            long loadTime = this.statsTicker().read() - startTime;
            if (recordLoad) {
                if (value == null) {
                    this.statsCounter().recordLoadFailure(loadTime);
                } else {
                    this.statsCounter().recordLoadSuccess(loadTime);
                }
            }
            return value;
        };
    }

    default public <T, U, R> BiFunction<? super T, ? super U, ? extends R> statsAware(BiFunction<? super T, ? super U, ? extends R> remappingFunction) {
        return this.statsAware(remappingFunction, true, true);
    }

    default public <T, U, R> BiFunction<? super T, ? super U, ? extends R> statsAware(BiFunction<? super T, ? super U, ? extends R> remappingFunction, boolean recordLoad, boolean recordLoadFailure) {
        if (!this.isRecordingStats()) {
            return remappingFunction;
        }
        return (t, u) -> {
            Object result;
            long startTime = this.statsTicker().read();
            try {
                result = remappingFunction.apply(t, u);
            }
            catch (Error | RuntimeException e) {
                if (recordLoadFailure) {
                    this.statsCounter().recordLoadFailure(this.statsTicker().read() - startTime);
                }
                throw e;
            }
            long loadTime = this.statsTicker().read() - startTime;
            if (recordLoad) {
                if (result == null) {
                    this.statsCounter().recordLoadFailure(loadTime);
                } else {
                    this.statsCounter().recordLoadSuccess(loadTime);
                }
            }
            return result;
        };
    }
}

