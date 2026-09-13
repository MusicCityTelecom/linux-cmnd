/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.errorprone.annotations.CheckReturnValue
 */
package com.github.benmanes.caffeine.cache;

import com.github.benmanes.caffeine.cache.AsyncCache;
import com.github.benmanes.caffeine.cache.LoadingCache;
import com.google.errorprone.annotations.CheckReturnValue;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public interface AsyncLoadingCache<K, V>
extends AsyncCache<K, V> {
    public CompletableFuture<V> get(K var1);

    public CompletableFuture<Map<K, V>> getAll(Iterable<? extends K> var1);

    @Override
    @CheckReturnValue
    public LoadingCache<K, V> synchronous();
}

