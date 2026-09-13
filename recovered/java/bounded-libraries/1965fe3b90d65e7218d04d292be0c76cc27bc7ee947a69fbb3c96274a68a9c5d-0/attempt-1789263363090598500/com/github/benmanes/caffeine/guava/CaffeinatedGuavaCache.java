/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.github.benmanes.caffeine.cache.Cache
 *  com.github.benmanes.caffeine.cache.stats.CacheStats
 *  com.google.common.cache.Cache
 *  com.google.common.cache.CacheLoader$InvalidCacheLoadException
 *  com.google.common.cache.CacheStats
 *  com.google.common.collect.ForwardingCollection
 *  com.google.common.collect.ForwardingConcurrentMap
 *  com.google.common.collect.ForwardingSet
 *  com.google.common.collect.ImmutableMap
 *  com.google.common.util.concurrent.ExecutionError
 *  com.google.common.util.concurrent.UncheckedExecutionException
 *  org.checkerframework.checker.nullness.qual.Nullable
 */
package com.github.benmanes.caffeine.guava;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.CacheStats;
import com.google.common.collect.ForwardingCollection;
import com.google.common.collect.ForwardingConcurrentMap;
import com.google.common.collect.ForwardingSet;
import com.google.common.collect.ImmutableMap;
import com.google.common.util.concurrent.ExecutionError;
import com.google.common.util.concurrent.UncheckedExecutionException;
import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import org.checkerframework.checker.nullness.qual.Nullable;

class CaffeinatedGuavaCache<K, V>
implements Cache<K, V>,
Serializable {
    private static final long serialVersionUID = 1L;
    private final com.github.benmanes.caffeine.cache.Cache<K, V> cache;
    private transient @Nullable ConcurrentMap<K, V> mapView;

    CaffeinatedGuavaCache(com.github.benmanes.caffeine.cache.Cache<K, V> cache) {
        this.cache = Objects.requireNonNull(cache);
    }

    public @Nullable V getIfPresent(Object key) {
        Object castedKey = key;
        return (V)this.cache.getIfPresent(castedKey);
    }

    public V get(K key, Callable<? extends V> valueLoader) throws ExecutionException {
        Objects.requireNonNull(valueLoader);
        try {
            return (V)this.cache.get(key, k -> {
                try {
                    Object value = valueLoader.call();
                    if (value == null) {
                        throw new CacheLoader.InvalidCacheLoadException("null value");
                    }
                    return value;
                }
                catch (CacheLoader.InvalidCacheLoadException e) {
                    throw e;
                }
                catch (RuntimeException e) {
                    throw new UncheckedExecutionException((Throwable)e);
                }
                catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    throw new CacheLoaderException(e);
                }
                catch (Exception e) {
                    throw new CacheLoaderException(e);
                }
                catch (Error e) {
                    throw new ExecutionError(e);
                }
            });
        }
        catch (CacheLoaderException e) {
            throw new ExecutionException(e.getCause());
        }
    }

    public ImmutableMap<K, V> getAllPresent(Iterable<?> keys) {
        Iterable<?> castedKeys = keys;
        return ImmutableMap.copyOf((Map)this.cache.getAllPresent(castedKeys));
    }

    public void put(K key, V value) {
        this.cache.put(key, value);
    }

    public void putAll(Map<? extends K, ? extends V> m) {
        this.cache.putAll(m);
    }

    public void invalidate(Object key) {
        Object castedKey = key;
        this.cache.invalidate(castedKey);
    }

    public void invalidateAll(Iterable<?> keys) {
        Iterable<?> castedKeys = keys;
        this.cache.invalidateAll(castedKeys);
    }

    public void invalidateAll() {
        this.cache.invalidateAll();
    }

    public long size() {
        return this.cache.estimatedSize();
    }

    public CacheStats stats() {
        com.github.benmanes.caffeine.cache.stats.CacheStats stats = this.cache.stats();
        return new CacheStats(stats.hitCount(), stats.missCount(), stats.loadSuccessCount(), stats.loadFailureCount(), stats.totalLoadTime(), stats.evictionCount());
    }

    public ConcurrentMap<K, V> asMap() {
        return this.mapView == null ? (this.mapView = new AsMapView()) : this.mapView;
    }

    public void cleanUp() {
        this.cache.cleanUp();
    }

    static final class CacheLoaderException
    extends RuntimeException {
        private static final long serialVersionUID = 1L;

        CacheLoaderException(Exception e) {
            super(e);
        }

        @Override
        public Throwable fillInStackTrace() {
            return this;
        }
    }

    final class EntrySetView
    extends ForwardingSet<Map.Entry<K, V>> {
        EntrySetView() {
        }

        public boolean add(Map.Entry<K, V> entry) {
            throw new UnsupportedOperationException();
        }

        public boolean addAll(Collection<? extends Map.Entry<K, V>> entry) {
            throw new UnsupportedOperationException();
        }

        public boolean removeIf(Predicate<? super Map.Entry<K, V>> filter) {
            return this.delegate().removeIf(filter);
        }

        protected Set<Map.Entry<K, V>> delegate() {
            return CaffeinatedGuavaCache.this.cache.asMap().entrySet();
        }
    }

    final class ValuesView
    extends ForwardingCollection<V> {
        ValuesView() {
        }

        public boolean removeIf(Predicate<? super V> filter) {
            return this.delegate().removeIf(filter);
        }

        public boolean remove(Object o) {
            return o != null && this.delegate().remove(o);
        }

        protected Collection<V> delegate() {
            return CaffeinatedGuavaCache.this.cache.asMap().values();
        }
    }

    final class KeySetView
    extends ForwardingSet<K> {
        KeySetView() {
        }

        public boolean removeIf(Predicate<? super K> filter) {
            return this.delegate().removeIf(filter);
        }

        public boolean remove(Object o) {
            return o != null && this.delegate().remove(o);
        }

        protected Set<K> delegate() {
            return CaffeinatedGuavaCache.this.cache.asMap().keySet();
        }
    }

    final class AsMapView
    extends ForwardingConcurrentMap<K, V> {
        @Nullable EntrySetView entrySet;
        @Nullable KeySetView keySet;
        @Nullable ValuesView values;

        AsMapView() {
        }

        public boolean containsKey(Object key) {
            return key != null && this.delegate().containsKey(key);
        }

        public boolean containsValue(Object value) {
            return value != null && this.delegate().containsValue(value);
        }

        public void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
            this.delegate().replaceAll(function);
        }

        public V computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction) {
            return this.delegate().computeIfAbsent(key, mappingFunction);
        }

        public V computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
            return this.delegate().computeIfPresent(key, remappingFunction);
        }

        public V compute(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
            return this.delegate().compute(key, remappingFunction);
        }

        public V merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
            return this.delegate().merge(key, value, remappingFunction);
        }

        public Set<K> keySet() {
            return this.keySet == null ? (this.keySet = new KeySetView()) : this.keySet;
        }

        public Collection<V> values() {
            return this.values == null ? (this.values = new ValuesView()) : this.values;
        }

        public Set<Map.Entry<K, V>> entrySet() {
            return this.entrySet == null ? (this.entrySet = new EntrySetView()) : this.entrySet;
        }

        protected ConcurrentMap<K, V> delegate() {
            return CaffeinatedGuavaCache.this.cache.asMap();
        }
    }
}

