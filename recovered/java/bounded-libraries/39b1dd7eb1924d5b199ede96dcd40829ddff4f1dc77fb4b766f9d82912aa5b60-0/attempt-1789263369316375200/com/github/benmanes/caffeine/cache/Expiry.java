/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.checkerframework.checker.index.qual.NonNegative
 */
package com.github.benmanes.caffeine.cache;

import org.checkerframework.checker.index.qual.NonNegative;

public interface Expiry<K, V> {
    public long expireAfterCreate(K var1, V var2, long var3);

    public long expireAfterUpdate(K var1, V var2, long var3, @NonNegative long var5);

    public long expireAfterRead(K var1, V var2, long var3, @NonNegative long var5);
}

