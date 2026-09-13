/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.errorprone.annotations.CheckReturnValue
 */
package com.github.benmanes.caffeine.cache;

import com.github.benmanes.caffeine.cache.CacheLoader;
import com.google.errorprone.annotations.CheckReturnValue;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.BiFunction;
import java.util.function.Function;

@FunctionalInterface
public interface AsyncCacheLoader<K, V> {
    public CompletableFuture<? extends V> asyncLoad(K var1, Executor var2) throws Exception;

    default public CompletableFuture<? extends Map<? extends K, ? extends V>> asyncLoadAll(Set<? extends K> keys, Executor executor) throws Exception {
        throw new UnsupportedOperationException();
    }

    default public CompletableFuture<? extends V> asyncReload(K key, V oldValue, Executor executor) throws Exception {
        return this.asyncLoad(key, executor);
    }

    @CheckReturnValue
    public static <K, V> AsyncCacheLoader<K, V> bulk(Function<? super Set<? extends K>, ? extends Map<? extends K, ? extends V>> mappingFunction) {
        return CacheLoader.bulk(mappingFunction);
    }

    @CheckReturnValue
    public static <K, V> AsyncCacheLoader<K, V> bulk(final BiFunction<? super Set<? extends K>, ? super Executor, ? extends CompletableFuture<? extends Map<? extends K, ? extends V>>> mappingFunction) {
        Objects.requireNonNull(mappingFunction);
        return new AsyncCacheLoader<K, V>(){

            @Override
            public CompletableFuture<V> asyncLoad(K key, Executor executor) {
                return this.asyncLoadAll(Set.of(key), executor).thenApply(results -> results.get(key));
            }

            @Override
            public CompletableFuture<Map<K, V>> asyncLoadAll(Set<? extends K> keys, Executor executor) {
                Objects.requireNonNull(keys);
                Objects.requireNonNull(executor);
                CompletableFuture future = (CompletableFuture)mappingFunction.apply(keys, executor);
                return future;
            }
        };
    }
}

