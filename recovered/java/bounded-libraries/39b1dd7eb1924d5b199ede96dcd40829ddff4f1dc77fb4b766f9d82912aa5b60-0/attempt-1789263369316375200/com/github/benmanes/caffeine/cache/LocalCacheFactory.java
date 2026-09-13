/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.checkerframework.checker.nullness.qual.Nullable
 */
package com.github.benmanes.caffeine.cache;

import com.github.benmanes.caffeine.cache.AsyncCacheLoader;
import com.github.benmanes.caffeine.cache.BoundedLocalCache;
import com.github.benmanes.caffeine.cache.Caffeine;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import org.checkerframework.checker.nullness.qual.Nullable;

final class LocalCacheFactory {
    private static final MethodHandles.Lookup LOOKUP = MethodHandles.lookup();
    private static final MethodType FACTORY = MethodType.methodType(Void.TYPE, Caffeine.class, AsyncCacheLoader.class, Boolean.TYPE);

    private LocalCacheFactory() {
    }

    static <K, V> BoundedLocalCache<K, V> newBoundedLocalCache(Caffeine<K, V> builder, @Nullable AsyncCacheLoader<? super K, V> cacheLoader, boolean async) {
        String className = LocalCacheFactory.getClassName(builder);
        return LocalCacheFactory.loadFactory(builder, cacheLoader, async, className);
    }

    static String getClassName(Caffeine<?, ?> builder) {
        StringBuilder className = new StringBuilder(LocalCacheFactory.class.getPackageName()).append('.');
        if (builder.isStrongKeys()) {
            className.append('S');
        } else {
            className.append('W');
        }
        if (builder.isStrongValues()) {
            className.append('S');
        } else {
            className.append('I');
        }
        if (builder.removalListener != null) {
            className.append('L');
        }
        if (builder.isRecordingStats()) {
            className.append('S');
        }
        if (builder.evicts()) {
            className.append('M');
            if (builder.isWeighted()) {
                className.append('W');
            } else {
                className.append('S');
            }
        }
        if (builder.expiresAfterAccess() || builder.expiresVariable()) {
            className.append('A');
        }
        if (builder.expiresAfterWrite()) {
            className.append('W');
        }
        if (builder.refreshAfterWrite()) {
            className.append('R');
        }
        return className.toString();
    }

    static <K, V> BoundedLocalCache<K, V> loadFactory(Caffeine<K, V> builder, @Nullable AsyncCacheLoader<? super K, V> cacheLoader, boolean async, String className) {
        try {
            Class<?> clazz = Class.forName(className);
            MethodHandle handle = LOOKUP.findConstructor(clazz, FACTORY);
            return handle.invoke(builder, cacheLoader, async);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable t) {
            throw new IllegalStateException(className, t);
        }
    }
}

