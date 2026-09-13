/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.errorprone.annotations.CheckReturnValue
 *  org.checkerframework.checker.nullness.qual.Nullable
 */
package com.github.benmanes.caffeine.cache;

import com.github.benmanes.caffeine.cache.AsyncCacheLoader;
import com.google.errorprone.annotations.CheckReturnValue;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.Executor;
import java.util.function.Function;
import org.checkerframework.checker.nullness.qual.Nullable;

@FunctionalInterface
public interface CacheLoader<K, V>
extends AsyncCacheLoader<K, V> {
    public @Nullable V load(K var1) throws Exception;

    default public Map<? extends K, ? extends V> loadAll(Set<? extends K> keys) throws Exception {
        throw new UnsupportedOperationException();
    }

    @Override
    default public CompletableFuture<? extends V> asyncLoad(K key, Executor executor) throws Exception {
        Objects.requireNonNull(key);
        Objects.requireNonNull(executor);
        return CompletableFuture.supplyAsync(() -> {
            try {
                return this.load(key);
            }
            catch (RuntimeException e) {
                throw e;
            }
            catch (Exception e) {
                throw new CompletionException(e);
            }
        }, executor);
    }

    @Override
    default public CompletableFuture<? extends Map<? extends K, ? extends V>> asyncLoadAll(Set<? extends K> keys, Executor executor) throws Exception {
        Objects.requireNonNull(keys);
        Objects.requireNonNull(executor);
        return CompletableFuture.supplyAsync(() -> {
            try {
                return this.loadAll(keys);
            }
            catch (RuntimeException e) {
                throw e;
            }
            catch (Exception e) {
                throw new CompletionException(e);
            }
        }, executor);
    }

    default public @Nullable V reload(K key, V oldValue) throws Exception {
        return this.load(key);
    }

    @Override
    default public CompletableFuture<? extends V> asyncReload(K key, V oldValue, Executor executor) throws Exception {
        Objects.requireNonNull(key);
        Objects.requireNonNull(executor);
        return CompletableFuture.supplyAsync(() -> {
            try {
                return this.reload(key, oldValue);
            }
            catch (RuntimeException e) {
                throw e;
            }
            catch (Exception e) {
                throw new CompletionException(e);
            }
        }, executor);
    }

    @CheckReturnValue
    public static <K, V> CacheLoader<K, V> bulk(final Function<? super Set<? extends K>, ? extends Map<? extends K, ? extends V>> mappingFunction) {
        Objects.requireNonNull(mappingFunction);
        return new CacheLoader<K, V>(){

            @Override
            public @Nullable V load(K key) {
                return this.loadAll(Set.of(key)).get(key);
            }

            @Override
            public Map<? extends K, ? extends V> loadAll(Set<? extends K> keys) {
                return (Map)mappingFunction.apply(keys);
            }
        };
    }
}

