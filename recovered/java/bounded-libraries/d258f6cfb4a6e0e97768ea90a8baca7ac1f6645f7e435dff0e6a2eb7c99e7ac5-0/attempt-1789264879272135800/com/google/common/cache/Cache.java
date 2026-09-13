/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.errorprone.annotations.CheckReturnValue
 *  com.google.errorprone.annotations.CompatibleWith
 *  com.google.errorprone.annotations.DoNotMock
 *  org.checkerframework.checker.nullness.qual.Nullable
 */
package com.google.common.cache;

import com.google.common.annotations.GwtCompatible;
import com.google.common.cache.CacheStats;
import com.google.common.collect.ImmutableMap;
import com.google.errorprone.annotations.CheckReturnValue;
import com.google.errorprone.annotations.CompatibleWith;
import com.google.errorprone.annotations.DoNotMock;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import org.checkerframework.checker.nullness.qual.Nullable;

@DoNotMock(value="Use CacheBuilder.newBuilder().build()")
@GwtCompatible
public interface Cache<K, V> {
    public @Nullable V getIfPresent(@CompatibleWith(value="K") Object var1);

    public V get(K var1, Callable<? extends V> var2) throws ExecutionException;

    public ImmutableMap<K, V> getAllPresent(Iterable<?> var1);

    public void put(K var1, V var2);

    public void putAll(Map<? extends K, ? extends V> var1);

    public void invalidate(@CompatibleWith(value="K") Object var1);

    public void invalidateAll(Iterable<?> var1);

    public void invalidateAll();

    @CheckReturnValue
    public long size();

    @CheckReturnValue
    public CacheStats stats();

    @CheckReturnValue
    public ConcurrentMap<K, V> asMap();

    public void cleanUp();
}

