/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.github.benmanes.caffeine.cache.CacheLoader
 *  com.github.benmanes.caffeine.cache.Caffeine
 *  com.google.common.cache.Cache
 *  com.google.common.cache.CacheLoader
 *  com.google.common.cache.LoadingCache
 *  com.google.errorprone.annotations.CheckReturnValue
 */
package com.github.benmanes.caffeine.guava;

import com.github.benmanes.caffeine.cache.CacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.guava.CaffeinatedGuavaCache;
import com.github.benmanes.caffeine.guava.CaffeinatedGuavaLoadingCache;
import com.google.common.cache.Cache;
import com.google.common.cache.LoadingCache;
import com.google.errorprone.annotations.CheckReturnValue;
import java.lang.reflect.Method;

public final class CaffeinatedGuava {
    private CaffeinatedGuava() {
    }

    @CheckReturnValue
    public static <K, V, K1 extends K, V1 extends V> Cache<K1, V1> build(Caffeine<K, V> builder) {
        return new CaffeinatedGuavaCache(builder.build());
    }

    @CheckReturnValue
    public static <K, V, K1 extends K, V1 extends V> LoadingCache<K1, V1> build(Caffeine<K, V> builder, com.google.common.cache.CacheLoader<? super K1, V1> loader) {
        com.google.common.cache.CacheLoader<? super K1, V1> castedLoader = loader;
        return CaffeinatedGuava.build(builder, CaffeinatedGuava.hasLoadAll(castedLoader) ? new CaffeinatedGuavaLoadingCache.BulkLoader<K1, V1>(castedLoader) : new CaffeinatedGuavaLoadingCache.SingleLoader<K1, V1>(castedLoader));
    }

    @CheckReturnValue
    public static <K, V, K1 extends K, V1 extends V> LoadingCache<K1, V1> build(Caffeine<K, V> builder, CacheLoader<? super K1, V1> loader) {
        return new CaffeinatedGuavaLoadingCache(builder.build(loader));
    }

    static boolean hasLoadAll(com.google.common.cache.CacheLoader<?, ?> cacheLoader) {
        return CaffeinatedGuava.hasMethod(cacheLoader, "loadAll", Iterable.class);
    }

    static boolean hasMethod(com.google.common.cache.CacheLoader<?, ?> cacheLoader, String name, Class<?> ... paramTypes) {
        try {
            Method method = cacheLoader.getClass().getMethod(name, paramTypes);
            return method.getDeclaringClass() != com.google.common.cache.CacheLoader.class;
        }
        catch (NoSuchMethodException | SecurityException e) {
            return false;
        }
    }
}

