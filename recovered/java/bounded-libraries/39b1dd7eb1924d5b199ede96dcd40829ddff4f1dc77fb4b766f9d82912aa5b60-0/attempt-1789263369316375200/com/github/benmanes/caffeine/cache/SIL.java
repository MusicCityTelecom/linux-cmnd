/*
 * Decompiled with CFR 0.152.
 */
package com.github.benmanes.caffeine.cache;

import com.github.benmanes.caffeine.cache.AsyncCacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.RemovalListener;
import com.github.benmanes.caffeine.cache.SI;

class SIL<K, V>
extends SI<K, V> {
    final RemovalListener<K, V> removalListener;

    SIL(Caffeine<K, V> caffeine, AsyncCacheLoader<? super K, V> asyncCacheLoader, boolean bl) {
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

