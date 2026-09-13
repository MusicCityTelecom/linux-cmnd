/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.checkerframework.checker.nullness.qual.Nullable
 */
package com.github.benmanes.caffeine.cache;

import com.github.benmanes.caffeine.cache.RemovalCause;
import org.checkerframework.checker.nullness.qual.Nullable;

@FunctionalInterface
public interface RemovalListener<K, V> {
    public void onRemoval(@Nullable K var1, @Nullable V var2, RemovalCause var3);
}

