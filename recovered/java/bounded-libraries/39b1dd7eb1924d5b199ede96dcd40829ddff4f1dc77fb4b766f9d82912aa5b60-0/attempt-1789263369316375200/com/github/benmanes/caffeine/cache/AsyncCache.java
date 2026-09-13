/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.errorprone.annotations.CheckReturnValue
 *  org.checkerframework.checker.nullness.qual.Nullable
 */
package com.github.benmanes.caffeine.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.google.errorprone.annotations.CheckReturnValue;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Executor;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.checkerframework.checker.nullness.qual.Nullable;

public interface AsyncCache<K, V> {
    public @Nullable CompletableFuture<V> getIfPresent(K var1);

    public CompletableFuture<V> get(K var1, Function<? super K, ? extends V> var2);

    public CompletableFuture<V> get(K var1, BiFunction<? super K, ? super Executor, ? extends CompletableFuture<? extends V>> var2);

    public CompletableFuture<Map<K, V>> getAll(Iterable<? extends K> var1, Function<? super Set<? extends K>, ? extends Map<? extends K, ? extends V>> var2);

    public CompletableFuture<Map<K, V>> getAll(Iterable<? extends K> var1, BiFunction<? super Set<? extends K>, ? super Executor, ? extends CompletableFuture<? extends Map<? extends K, ? extends V>>> var2);

    public void put(K var1, CompletableFuture<? extends V> var2);

    @CheckReturnValue
    public ConcurrentMap<K, CompletableFuture<V>> asMap();

    @CheckReturnValue
    public Cache<K, V> synchronous();
}

