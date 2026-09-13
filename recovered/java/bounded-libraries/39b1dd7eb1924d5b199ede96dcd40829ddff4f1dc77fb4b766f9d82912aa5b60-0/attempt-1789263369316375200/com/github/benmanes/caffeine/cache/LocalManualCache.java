/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.checkerframework.checker.nullness.qual.Nullable
 */
package com.github.benmanes.caffeine.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LocalCache;
import com.github.benmanes.caffeine.cache.stats.CacheStats;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;
import org.checkerframework.checker.nullness.qual.Nullable;

interface LocalManualCache<K, V>
extends Cache<K, V> {
    public LocalCache<K, V> cache();

    @Override
    default public long estimatedSize() {
        return this.cache().estimatedSize();
    }

    @Override
    default public void cleanUp() {
        this.cache().cleanUp();
    }

    @Override
    default public @Nullable V getIfPresent(K key) {
        return this.cache().getIfPresent(key, true);
    }

    @Override
    default public @Nullable V get(K key, Function<? super K, ? extends V> mappingFunction) {
        return this.cache().computeIfAbsent((K)key, mappingFunction);
    }

    @Override
    default public Map<K, V> getAllPresent(Iterable<? extends K> keys) {
        return this.cache().getAllPresent(keys);
    }

    @Override
    default public Map<K, V> getAll(Iterable<? extends K> keys, Function<? super Set<? extends K>, ? extends Map<? extends K, ? extends V>> mappingFunction) {
        Objects.requireNonNull(mappingFunction);
        Map<K, V> found = this.cache().getAllPresent(keys);
        int initialCapacity = Caffeine.calculateHashMapCapacity(keys);
        LinkedHashMap<K, V> result = new LinkedHashMap<K, V>(initialCapacity);
        LinkedHashSet<K> keysToLoad = new LinkedHashSet<K>(initialCapacity);
        for (K key : keys) {
            V value = found.get(key);
            if (value == null) {
                keysToLoad.add(key);
            }
            result.put(key, value);
        }
        if (keysToLoad.isEmpty()) {
            return found;
        }
        this.bulkLoad(keysToLoad, result, mappingFunction);
        return Collections.unmodifiableMap(result);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    default public void bulkLoad(Set<K> keysToLoad, Map<K, V> result, Function<? super Set<? extends K>, ? extends Map<? extends K, ? extends V>> mappingFunction) {
        boolean success = false;
        long startTime = this.cache().statsTicker().read();
        try {
            Map<Object, Object> loaded = mappingFunction.apply(Collections.unmodifiableSet(keysToLoad));
            loaded.forEach(this.cache()::put);
            for (K key : keysToLoad) {
                V value = loaded.get(key);
                if (value == null) {
                    result.remove(key);
                    continue;
                }
                result.put(key, value);
            }
            success = !loaded.isEmpty();
        }
        finally {
            long loadTime = this.cache().statsTicker().read() - startTime;
            if (success) {
                this.cache().statsCounter().recordLoadSuccess(loadTime);
            } else {
                this.cache().statsCounter().recordLoadFailure(loadTime);
            }
        }
    }

    @Override
    default public void put(K key, V value) {
        this.cache().put(key, value);
    }

    @Override
    default public void putAll(Map<? extends K, ? extends V> map) {
        this.cache().putAll(map);
    }

    @Override
    default public void invalidate(K key) {
        this.cache().remove(key);
    }

    @Override
    default public void invalidateAll(Iterable<? extends K> keys) {
        this.cache().invalidateAll(keys);
    }

    @Override
    default public void invalidateAll() {
        this.cache().clear();
    }

    @Override
    default public CacheStats stats() {
        return this.cache().statsCounter().snapshot();
    }

    @Override
    default public ConcurrentMap<K, V> asMap() {
        return this.cache();
    }
}

