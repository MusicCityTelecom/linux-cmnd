/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.github.benmanes.caffeine.cache.CacheLoader
 *  com.github.benmanes.caffeine.cache.LoadingCache
 *  com.google.common.base.Throwables
 *  com.google.common.cache.CacheLoader
 *  com.google.common.cache.CacheLoader$InvalidCacheLoadException
 *  com.google.common.cache.LoadingCache
 *  com.google.common.collect.ImmutableMap
 *  com.google.common.util.concurrent.ExecutionError
 *  com.google.common.util.concurrent.Futures
 *  com.google.common.util.concurrent.UncheckedExecutionException
 */
package com.github.benmanes.caffeine.guava;

import com.github.benmanes.caffeine.cache.LoadingCache;
import com.github.benmanes.caffeine.guava.CaffeinatedGuavaCache;
import com.google.common.base.Throwables;
import com.google.common.cache.CacheLoader;
import com.google.common.collect.ImmutableMap;
import com.google.common.util.concurrent.ExecutionError;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.UncheckedExecutionException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

final class CaffeinatedGuavaLoadingCache<K, V>
extends CaffeinatedGuavaCache<K, V>
implements com.google.common.cache.LoadingCache<K, V> {
    private static final ThreadLocal<Boolean> nullBulkLoad = ThreadLocal.withInitial(() -> Boolean.FALSE);
    private static final long serialVersionUID = 1L;
    private final LoadingCache<K, V> cache;

    CaffeinatedGuavaLoadingCache(LoadingCache<K, V> cache) {
        super(cache);
        this.cache = cache;
    }

    public V get(K key) throws ExecutionException {
        Objects.requireNonNull(key);
        try {
            return (V)this.cache.get(key);
        }
        catch (CacheLoader.InvalidCacheLoadException e) {
            throw e;
        }
        catch (CaffeinatedGuavaCache.CacheLoaderException e) {
            throw new ExecutionException(e.getCause());
        }
        catch (RuntimeException e) {
            throw new UncheckedExecutionException((Throwable)e);
        }
        catch (Error e) {
            throw new ExecutionError(e);
        }
    }

    public V getUnchecked(K key) {
        try {
            return (V)this.cache.get(key);
        }
        catch (CacheLoader.InvalidCacheLoadException | NullPointerException e) {
            throw e;
        }
        catch (CaffeinatedGuavaCache.CacheLoaderException e) {
            throw new UncheckedExecutionException(e.getCause());
        }
        catch (Exception e) {
            throw new UncheckedExecutionException((Throwable)e);
        }
        catch (Error e) {
            throw new ExecutionError(e);
        }
    }

    public ImmutableMap<K, V> getAll(Iterable<? extends K> keys) throws ExecutionException {
        try {
            Map result = this.cache.getAll(keys);
            if (nullBulkLoad.get().booleanValue()) {
                nullBulkLoad.set(false);
                throw new CacheLoader.InvalidCacheLoadException("null key or value");
            }
            for (K key : keys) {
                if (result.containsKey(key)) continue;
                throw new CacheLoader.InvalidCacheLoadException("loadAll failed to return a value for " + key);
            }
            return ImmutableMap.copyOf((Map)result);
        }
        catch (CacheLoader.InvalidCacheLoadException | NullPointerException e) {
            throw e;
        }
        catch (CaffeinatedGuavaCache.CacheLoaderException e) {
            throw new ExecutionException(e.getCause());
        }
        catch (Exception e) {
            throw new UncheckedExecutionException((Throwable)e);
        }
        catch (Error e) {
            throw new ExecutionError(e);
        }
    }

    public V apply(K key) {
        return (V)this.cache.get(key);
    }

    public void refresh(K key) {
        this.cache.refresh(key);
    }

    static final class BulkLoader<K, V>
    extends SingleLoader<K, V> {
        private static final long serialVersionUID = 1L;

        BulkLoader(CacheLoader<K, V> cacheLoader) {
            super(cacheLoader);
        }

        public Map<K, V> loadAll(Set<? extends K> keys) {
            try {
                Map loaded = this.cacheLoader.loadAll(keys);
                if (loaded == null) {
                    throw new CacheLoader.InvalidCacheLoadException("null map");
                }
                HashMap result = new HashMap(loaded.size(), 1.0f);
                loaded.forEach((key, value) -> {
                    if (key == null || value == null) {
                        nullBulkLoad.set(true);
                    } else {
                        result.put(key, value);
                    }
                });
                return result;
            }
            catch (Error | RuntimeException e) {
                throw e;
            }
            catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new CaffeinatedGuavaCache.CacheLoaderException(e);
            }
            catch (Exception e) {
                throw new CaffeinatedGuavaCache.CacheLoaderException(e);
            }
        }
    }

    static class SingleLoader<K, V>
    implements com.github.benmanes.caffeine.cache.CacheLoader<K, V>,
    Serializable {
        private static final long serialVersionUID = 1L;
        final CacheLoader<K, V> cacheLoader;

        SingleLoader(CacheLoader<K, V> cacheLoader) {
            this.cacheLoader = Objects.requireNonNull(cacheLoader);
        }

        public V load(K key) {
            try {
                Object value = this.cacheLoader.load(key);
                if (value == null) {
                    throw new CacheLoader.InvalidCacheLoadException("null value");
                }
                return (V)value;
            }
            catch (Error | RuntimeException e) {
                throw e;
            }
            catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new CaffeinatedGuavaCache.CacheLoaderException(e);
            }
            catch (Exception e) {
                throw new CaffeinatedGuavaCache.CacheLoaderException(e);
            }
        }

        public V reload(K key, V oldValue) {
            try {
                Object value = Futures.getUnchecked((Future)this.cacheLoader.reload(key, oldValue));
                if (value == null) {
                    throw new CacheLoader.InvalidCacheLoadException("null value");
                }
                return (V)value;
            }
            catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new CaffeinatedGuavaCache.CacheLoaderException(e);
            }
            catch (Exception e) {
                Throwables.throwIfUnchecked((Throwable)e);
                throw new RuntimeException(e);
            }
        }
    }
}

