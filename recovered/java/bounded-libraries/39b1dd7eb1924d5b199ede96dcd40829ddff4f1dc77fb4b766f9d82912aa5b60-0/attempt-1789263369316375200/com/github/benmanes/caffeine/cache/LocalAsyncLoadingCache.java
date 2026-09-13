/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.checkerframework.checker.nullness.qual.Nullable
 */
package com.github.benmanes.caffeine.cache;

import com.github.benmanes.caffeine.cache.Async;
import com.github.benmanes.caffeine.cache.AsyncCacheLoader;
import com.github.benmanes.caffeine.cache.AsyncLoadingCache;
import com.github.benmanes.caffeine.cache.CacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import com.github.benmanes.caffeine.cache.LocalAsyncCache;
import com.github.benmanes.caffeine.cache.RemovalCause;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeoutException;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.checkerframework.checker.nullness.qual.Nullable;

abstract class LocalAsyncLoadingCache<K, V>
implements LocalAsyncCache<K, V>,
AsyncLoadingCache<K, V> {
    static final System.Logger logger = System.getLogger(LocalAsyncLoadingCache.class.getName());
    final @Nullable BiFunction<? super Set<? extends K>, ? super Executor, ? extends CompletableFuture<? extends Map<? extends K, ? extends V>>> bulkMappingFunction;
    final BiFunction<? super K, ? super Executor, ? extends CompletableFuture<? extends V>> mappingFunction;
    final AsyncCacheLoader<K, V> cacheLoader;
    @Nullable LoadingCacheView<K, V> cacheView;

    LocalAsyncLoadingCache(AsyncCacheLoader<? super K, V> cacheLoader) {
        this.bulkMappingFunction = this.newBulkMappingFunction(cacheLoader);
        this.cacheLoader = cacheLoader;
        this.mappingFunction = this.newMappingFunction(cacheLoader);
    }

    BiFunction<? super K, ? super Executor, ? extends CompletableFuture<? extends V>> newMappingFunction(AsyncCacheLoader<? super K, V> cacheLoader) {
        return (key, executor) -> {
            try {
                return cacheLoader.asyncLoad((Object)key, (Executor)executor);
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

    @Nullable BiFunction<Set<? extends K>, Executor, CompletableFuture<Map<K, V>>> newBulkMappingFunction(AsyncCacheLoader<? super K, V> cacheLoader) {
        if (!this.canBulkLoad(cacheLoader)) {
            return null;
        }
        return (keysToLoad, executor) -> {
            try {
                CompletableFuture loaded = cacheLoader.asyncLoadAll((Set)keysToLoad, (Executor)executor);
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

    boolean canBulkLoad(AsyncCacheLoader<?, ?> loader) {
        try {
            Class<AsyncCacheLoader> defaultLoaderClass = AsyncCacheLoader.class;
            if (loader instanceof CacheLoader) {
                Method defaultLoadAll;
                defaultLoaderClass = CacheLoader.class;
                Method classLoadAll = loader.getClass().getMethod("loadAll", Set.class);
                if (!classLoadAll.equals(defaultLoadAll = CacheLoader.class.getMethod("loadAll", Set.class))) {
                    return true;
                }
            }
            Method classAsyncLoadAll = loader.getClass().getMethod("asyncLoadAll", Set.class, Executor.class);
            Method defaultAsyncLoadAll = defaultLoaderClass.getMethod("asyncLoadAll", Set.class, Executor.class);
            return !classAsyncLoadAll.equals(defaultAsyncLoadAll);
        }
        catch (NoSuchMethodException | SecurityException e) {
            logger.log(System.Logger.Level.WARNING, "Cannot determine if CacheLoader can bulk load", (Throwable)e);
            return false;
        }
    }

    @Override
    public CompletableFuture<V> get(K key) {
        return this.get(key, this.mappingFunction);
    }

    @Override
    public CompletableFuture<Map<K, V>> getAll(Iterable<? extends K> keys) {
        if (this.bulkMappingFunction != null) {
            return this.getAll(keys, this.bulkMappingFunction);
        }
        Function<Object, CompletableFuture> mappingFunction = this::get;
        LinkedHashMap<Object, CompletableFuture> result = new LinkedHashMap<Object, CompletableFuture>(Caffeine.calculateHashMapCapacity(keys));
        for (K key : keys) {
            CompletableFuture future = result.computeIfAbsent(key, mappingFunction);
            Objects.requireNonNull(future);
        }
        return LocalAsyncCache.composeResult(result);
    }

    @Override
    public LoadingCache<K, V> synchronous() {
        return this.cacheView == null ? (this.cacheView = new LoadingCacheView(this)) : this.cacheView;
    }

    static final class LoadingCacheView<K, V>
    extends LocalAsyncCache.AbstractCacheView<K, V>
    implements LoadingCache<K, V> {
        private static final long serialVersionUID = 1L;
        final LocalAsyncLoadingCache<K, V> asyncCache;

        LoadingCacheView(LocalAsyncLoadingCache<K, V> asyncCache) {
            this.asyncCache = Objects.requireNonNull(asyncCache);
        }

        @Override
        LocalAsyncLoadingCache<K, V> asyncCache() {
            return this.asyncCache;
        }

        @Override
        public V get(K key) {
            return LoadingCacheView.resolve(this.asyncCache.get(key));
        }

        @Override
        public Map<K, V> getAll(Iterable<? extends K> keys) {
            return LoadingCacheView.resolve(this.asyncCache.getAll(keys));
        }

        @Override
        public CompletableFuture<V> refresh(K key) {
            CompletableFuture<V> future;
            Objects.requireNonNull(key);
            Object keyReference = this.asyncCache.cache().referenceKey(key);
            do {
                if ((future = this.tryOptimisticRefresh(key, keyReference)) != null) continue;
                future = this.tryComputeRefresh(key, keyReference);
            } while (future == null);
            return future;
        }

        @Override
        public CompletableFuture<Map<K, V>> refreshAll(Iterable<? extends K> keys) {
            LinkedHashMap<Object, CompletableFuture> result = new LinkedHashMap<Object, CompletableFuture>(Caffeine.calculateHashMapCapacity(keys));
            for (K key : keys) {
                result.computeIfAbsent(key, this::refresh);
            }
            return LocalAsyncCache.composeResult(result);
        }

        private @Nullable CompletableFuture<V> tryOptimisticRefresh(K key, Object keyReference) {
            CompletableFuture oldValueFuture;
            CompletableFuture lastRefresh = (CompletableFuture)this.asyncCache.cache().refreshes().get(keyReference);
            if (lastRefresh != null) {
                if (Async.isReady(lastRefresh)) {
                    this.asyncCache.cache().refreshes().remove(keyReference, lastRefresh);
                } else {
                    return lastRefresh;
                }
            }
            if ((oldValueFuture = this.asyncCache.cache().getIfPresentQuietly(key)) == null || oldValueFuture.isDone() && oldValueFuture.isCompletedExceptionally()) {
                if (oldValueFuture != null) {
                    this.asyncCache.cache().remove(key, oldValueFuture);
                }
                CompletableFuture future = this.asyncCache.get(key, this.asyncCache.mappingFunction, false);
                CompletableFuture prior = this.asyncCache.cache().refreshes().putIfAbsent(keyReference, future);
                return prior == null ? future : prior;
            }
            if (!oldValueFuture.isDone()) {
                return oldValueFuture;
            }
            return null;
        }

        private @Nullable CompletableFuture<V> tryComputeRefresh(K key, Object keyReference) {
            long[] startTime = new long[1];
            long[] writeTime = new long[1];
            boolean[] refreshed = new boolean[1];
            CompletableFuture[] oldValueFuture = new CompletableFuture[1];
            CompletableFuture future = this.asyncCache.cache().refreshes().computeIfAbsent(keyReference, k -> {
                oldValueFuture[0] = this.asyncCache.cache().getIfPresentQuietly(key, writeTime);
                Object oldValue = Async.getIfReady(oldValueFuture[0]);
                if (oldValue == null) {
                    return null;
                }
                refreshed[0] = true;
                startTime[0] = this.asyncCache.cache().statsTicker().read();
                try {
                    return this.asyncCache.cacheLoader.asyncReload(key, oldValue, this.asyncCache.cache().executor());
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
            if (future == null) {
                return null;
            }
            CompletableFuture castedFuture = future;
            if (refreshed[0]) {
                castedFuture.whenComplete((newValue, error) -> {
                    this.asyncCache.cache().refreshes().remove(keyReference, castedFuture);
                    long loadTime = this.asyncCache.cache().statsTicker().read() - startTime[0];
                    if (error != null) {
                        if (!(error instanceof CancellationException) && !(error instanceof TimeoutException)) {
                            logger.log(System.Logger.Level.WARNING, "Exception thrown during refresh", (Throwable)error);
                        }
                        this.asyncCache.cache().statsCounter().recordLoadFailure(loadTime);
                        return;
                    }
                    boolean[] discard = new boolean[1];
                    CompletableFuture value = this.asyncCache.cache().compute(key, (ignored, currentValue) -> {
                        if (currentValue == oldValueFuture[0]) {
                            if (currentValue == null) {
                                discard[0] = newValue != null;
                                return null;
                            }
                            if (currentValue == newValue) {
                                return currentValue;
                            }
                            if (newValue == Async.getIfReady(currentValue)) {
                                return currentValue;
                            }
                            long expectedWriteTime = writeTime[0];
                            if (this.asyncCache.cache().hasWriteTime()) {
                                this.asyncCache.cache().getIfPresentQuietly(key, writeTime);
                            }
                            if (writeTime[0] == expectedWriteTime) {
                                return newValue == null ? null : castedFuture;
                            }
                        }
                        discard[0] = true;
                        return currentValue;
                    }, this.asyncCache.cache().expiry(), false, true);
                    if (discard[0] && newValue != null) {
                        RemovalCause cause = value == null ? RemovalCause.EXPLICIT : RemovalCause.REPLACED;
                        this.asyncCache.cache().notifyRemoval(key, castedFuture, cause);
                    }
                    if (newValue == null) {
                        this.asyncCache.cache().statsCounter().recordLoadFailure(loadTime);
                    } else {
                        this.asyncCache.cache().statsCounter().recordLoadSuccess(loadTime);
                    }
                });
            }
            return castedFuture;
        }
    }
}

