/*
 * Decompiled with CFR 0.152.
 */
package com.github.benmanes.caffeine.cache;

import com.github.benmanes.caffeine.cache.Cache;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public interface LoadingCache<K, V>
extends Cache<K, V> {
    public V get(K var1);

    public Map<K, V> getAll(Iterable<? extends K> var1);

    public CompletableFuture<V> refresh(K var1);

    public CompletableFuture<Map<K, V>> refreshAll(Iterable<? extends K> var1);
}

