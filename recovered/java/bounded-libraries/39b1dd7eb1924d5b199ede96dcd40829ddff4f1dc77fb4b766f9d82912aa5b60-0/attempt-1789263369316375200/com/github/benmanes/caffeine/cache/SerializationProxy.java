/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.checkerframework.checker.nullness.qual.Nullable
 */
package com.github.benmanes.caffeine.cache;

import com.github.benmanes.caffeine.cache.AsyncCacheLoader;
import com.github.benmanes.caffeine.cache.CacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Expiry;
import com.github.benmanes.caffeine.cache.RemovalListener;
import com.github.benmanes.caffeine.cache.Ticker;
import com.github.benmanes.caffeine.cache.Weigher;
import java.io.Serializable;
import java.util.concurrent.TimeUnit;
import org.checkerframework.checker.nullness.qual.Nullable;

final class SerializationProxy<K, V>
implements Serializable {
    private static final long serialVersionUID = 1L;
    boolean async;
    boolean weakKeys;
    boolean weakValues;
    boolean softValues;
    boolean isRecordingStats;
    long refreshAfterWriteNanos;
    long expiresAfterWriteNanos;
    long expiresAfterAccessNanos;
    long maximumSize = -1L;
    long maximumWeight = -1L;
    @Nullable Ticker ticker;
    @Nullable Expiry<?, ?> expiry;
    @Nullable Weigher<?, ?> weigher;
    @Nullable AsyncCacheLoader<?, ?> cacheLoader;
    @Nullable RemovalListener<?, ?> removalListener;
    @Nullable RemovalListener<?, ?> evictionListener;

    SerializationProxy() {
    }

    Caffeine<Object, Object> recreateCaffeine() {
        Caffeine<Object, Object> builder = Caffeine.newBuilder();
        if (this.ticker != null) {
            builder.ticker(this.ticker);
        }
        if (this.isRecordingStats) {
            builder.recordStats();
        }
        if (this.maximumSize != -1L) {
            builder.maximumSize(this.maximumSize);
        }
        if (this.weigher != null) {
            Weigher<?, ?> castedWeigher = this.weigher;
            builder.maximumWeight(this.maximumWeight);
            builder.weigher(castedWeigher);
        }
        if (this.expiry != null) {
            builder.expireAfter(this.expiry);
        }
        if (this.expiresAfterWriteNanos > 0L) {
            builder.expireAfterWrite(this.expiresAfterWriteNanos, TimeUnit.NANOSECONDS);
        }
        if (this.expiresAfterAccessNanos > 0L) {
            builder.expireAfterAccess(this.expiresAfterAccessNanos, TimeUnit.NANOSECONDS);
        }
        if (this.refreshAfterWriteNanos > 0L) {
            builder.refreshAfterWrite(this.refreshAfterWriteNanos, TimeUnit.NANOSECONDS);
        }
        if (this.weakKeys) {
            builder.weakKeys();
        }
        if (this.weakValues) {
            builder.weakValues();
        }
        if (this.softValues) {
            builder.softValues();
        }
        if (this.removalListener != null) {
            builder.removalListener(this.removalListener);
        }
        if (this.evictionListener != null) {
            builder.evictionListener(this.evictionListener);
        }
        return builder;
    }

    Object readResolve() {
        Caffeine<Object, Object> builder = this.recreateCaffeine();
        if (this.async) {
            if (this.cacheLoader == null) {
                return builder.buildAsync();
            }
            AsyncCacheLoader<?, ?> loader = this.cacheLoader;
            return builder.buildAsync(loader);
        }
        if (this.cacheLoader == null) {
            return builder.build();
        }
        CacheLoader loader = (CacheLoader)this.cacheLoader;
        return builder.build(loader);
    }
}

