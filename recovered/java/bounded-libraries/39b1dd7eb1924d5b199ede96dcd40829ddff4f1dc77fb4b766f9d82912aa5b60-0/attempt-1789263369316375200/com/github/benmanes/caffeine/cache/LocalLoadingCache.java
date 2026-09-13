/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.checkerframework.checker.nullness.qual.Nullable
 */
package com.github.benmanes.caffeine.cache;

import com.github.benmanes.caffeine.cache.Async;
import com.github.benmanes.caffeine.cache.AsyncCacheLoader;
import com.github.benmanes.caffeine.cache.CacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import com.github.benmanes.caffeine.cache.LocalAsyncCache;
import com.github.benmanes.caffeine.cache.LocalManualCache;
import com.github.benmanes.caffeine.cache.RemovalCause;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.TimeoutException;
import java.util.function.Function;
import org.checkerframework.checker.nullness.qual.Nullable;

interface LocalLoadingCache<K, V>
extends LocalManualCache<K, V>,
LoadingCache<K, V> {
    public static final System.Logger logger = System.getLogger(LocalLoadingCache.class.getName());

    public AsyncCacheLoader<? super K, V> cacheLoader();

    public Function<K, V> mappingFunction();

    public @Nullable Function<Set<? extends K>, Map<K, V>> bulkMappingFunction();

    @Override
    default public V get(K key) {
        return this.cache().computeIfAbsent(key, this.mappingFunction());
    }

    @Override
    default public Map<K, V> getAll(Iterable<? extends K> keys) {
        Function<Set<K>, Map<K, V>> mappingFunction = this.bulkMappingFunction();
        return mappingFunction == null ? this.loadSequentially(keys) : this.getAll(keys, mappingFunction);
    }

    default public Map<K, V> loadSequentially(Iterable<? extends K> keys) {
        LinkedHashMap result = new LinkedHashMap(Caffeine.calculateHashMapCapacity(keys));
        for (K key : keys) {
            result.put(key, null);
        }
        int count = 0;
        try {
            Iterator iter = result.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry entry = iter.next();
                ++count;
                V value = this.get(entry.getKey());
                if (value == null) {
                    iter.remove();
                    continue;
                }
                entry.setValue(value);
            }
        }
        catch (Throwable t) {
            this.cache().statsCounter().recordMisses(result.size() - count);
            throw t;
        }
        return Collections.unmodifiableMap(result);
    }

    @Override
    default public CompletableFuture<V> refresh(K key) {
        Objects.requireNonNull(key);
        long[] writeTime = new long[1];
        long[] startTime = new long[1];
        Object[] oldValue = new Object[1];
        CompletableFuture[] reloading = new CompletableFuture[1];
        Object keyReference = this.cache().referenceKey(key);
        CompletableFuture future = this.cache().refreshes().compute(keyReference, (k, existing) -> {
            if (existing != null && !Async.isReady(existing)) {
                return existing;
            }
            try {
                CompletableFuture<V> refreshFuture;
                startTime[0] = this.cache().statsTicker().read();
                oldValue[0] = this.cache().getIfPresentQuietly(key, writeTime);
                reloading[0] = refreshFuture = oldValue[0] == null ? this.cacheLoader().asyncLoad(key, this.cache().executor()) : this.cacheLoader().asyncReload(key, oldValue[0], this.cache().executor());
                return refreshFuture;
            }
            catch (RuntimeException e) {
                throw e;
            }
            catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new CompletionException(e);
            }
            catch (Exception e) {
                throw new CompletionException(e);
            }
        });
        if (reloading[0] != null) {
            reloading[0].whenComplete((newValue, error) -> {
                boolean removed = this.cache().refreshes().remove(keyReference, reloading[0]);
                long loadTime = this.cache().statsTicker().read() - startTime[0];
                if (error != null) {
                    if (!(error instanceof CancellationException) && !(error instanceof TimeoutException)) {
                        logger.log(System.Logger.Level.WARNING, "Exception thrown during refresh", (Throwable)error);
                    }
                    this.cache().statsCounter().recordLoadFailure(loadTime);
                    return;
                }
                boolean[] discard = new boolean[1];
                Object value = this.cache().compute(key, (k, currentValue) -> {
                    if (currentValue == oldValue[0]) {
                        if (currentValue == null) {
                            if (newValue == null) {
                                return null;
                            }
                            if (removed) {
                                return newValue;
                            }
                        } else {
                            long expectedWriteTime = writeTime[0];
                            if (this.cache().hasWriteTime()) {
                                this.cache().getIfPresentQuietly(key, writeTime);
                            }
                            if (writeTime[0] == expectedWriteTime) {
                                return newValue;
                            }
                        }
                    }
                    discard[0] = currentValue != newValue;
                    return currentValue;
                }, this.cache().expiry(), false, true);
                if (discard[0] && newValue != null) {
                    RemovalCause cause = value == null ? RemovalCause.EXPLICIT : RemovalCause.REPLACED;
                    this.cache().notifyRemoval(key, newValue, cause);
                }
                if (newValue == null) {
                    this.cache().statsCounter().recordLoadFailure(loadTime);
                } else {
                    this.cache().statsCounter().recordLoadSuccess(loadTime);
                }
            });
        }
        CompletableFuture castedFuture = future;
        return castedFuture;
    }

    @Override
    default public CompletableFuture<Map<K, V>> refreshAll(Iterable<? extends K> keys) {
        LinkedHashMap<Object, CompletableFuture> result = new LinkedHashMap<Object, CompletableFuture>(Caffeine.calculateHashMapCapacity(keys));
        for (K key : keys) {
            result.computeIfAbsent(key, this::refresh);
        }
        return LocalAsyncCache.composeResult(result);
    }

    public static <K, V> Function<K, V> newMappingFunction(CacheLoader<? super K, V> cacheLoader) {
        return key -> {
            try {
                return cacheLoader.load(key);
            }
            catch (RuntimeException e) {
                throw e;
            }
            catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new CompletionException(e);
            }
            catch (Exception e) {
                throw new CompletionException(e);
            }
        };
    }

    public static <K, V> @Nullable Function<Set<? extends K>, Map<K, V>> newBulkMappingFunction(CacheLoader<? super K, V> cacheLoader) {
        if (!LocalLoadingCache.hasLoadAll(cacheLoader)) {
            return null;
        }
        return keysToLoad -> {
            try {
                Map loaded = cacheLoader.loadAll((Set)keysToLoad);
                return loaded;
            }
            catch (RuntimeException e) {
                throw e;
            }
            catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new CompletionException(e);
            }
            catch (Exception e) {
                throw new CompletionException(e);
            }
        };
    }

    public static boolean hasLoadAll(CacheLoader<?, ?> loader) {
        try {
            Method classLoadAll = loader.getClass().getMethod("loadAll", Set.class);
            Method defaultLoadAll = CacheLoader.class.getMethod("loadAll", Set.class);
            return !classLoadAll.equals(defaultLoadAll);
        }
        catch (NoSuchMethodException | SecurityException e) {
            logger.log(System.Logger.Level.WARNING, "Cannot determine if CacheLoader can bulk load", (Throwable)e);
            return false;
        }
    }
}

