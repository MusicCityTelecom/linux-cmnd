/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.errorprone.annotations.CheckReturnValue
 *  org.checkerframework.checker.index.qual.NonNegative
 *  org.checkerframework.checker.nullness.qual.Nullable
 *  org.checkerframework.checker.nullness.qual.PolyNull
 */
package com.github.benmanes.caffeine.cache;

import com.github.benmanes.caffeine.cache.Policy;
import com.github.benmanes.caffeine.cache.stats.CacheStats;
import com.google.errorprone.annotations.CheckReturnValue;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.checkerframework.checker.nullness.qual.PolyNull;

public interface Cache<K, V> {
    public @Nullable V getIfPresent(K var1);

    public @PolyNull V get(K var1, Function<? super K, ? extends @PolyNull V> var2);

    public Map<K, V> getAllPresent(Iterable<? extends K> var1);

    public Map<K, V> getAll(Iterable<? extends K> var1, Function<? super Set<? extends K>, ? extends Map<? extends K, ? extends V>> var2);

    public void put(K var1, V var2);

    public void putAll(Map<? extends K, ? extends V> var1);

    public void invalidate(K var1);

    public void invalidateAll(Iterable<? extends K> var1);

    public void invalidateAll();

    @CheckReturnValue
    public @NonNegative long estimatedSize();

    @CheckReturnValue
    public CacheStats stats();

    @CheckReturnValue
    public ConcurrentMap<K, V> asMap();

    public void cleanUp();

    @CheckReturnValue
    public Policy<K, V> policy();
}

