/*
 * Decompiled with CFR 0.152.
 */
package com.github.benmanes.caffeine.cache;

import com.github.benmanes.caffeine.cache.AsyncCacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.RemovalListener;
import com.github.benmanes.caffeine.cache.SS;

class SSL<K, V>
extends SS<K, V> {
    final RemovalListener<K, V> removalListener;

    SSL(Caffeine<K, V> caffeine, AsyncCacheLoader<? super K, V> asyncCacheLoader, boolean bl) {
        super(caffeine, asyncCacheLoader, bl);
        this.removalListener = caffeine.getRemovalListener(bl);
    }

    @Override
    public final RemovalListener<K, V> removalListener() {
        return this.removalListener;
    }

    @Override
    protected final boolean hasRemovalListener() {
        return true;
    }
}

