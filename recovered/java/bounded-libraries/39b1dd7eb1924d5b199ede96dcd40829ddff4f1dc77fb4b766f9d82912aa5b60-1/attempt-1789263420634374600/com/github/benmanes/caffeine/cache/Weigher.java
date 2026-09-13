/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.checkerframework.checker.index.qual.NonNegative
 */
package com.github.benmanes.caffeine.cache;

import com.github.benmanes.caffeine.cache.BoundedWeigher;
import com.github.benmanes.caffeine.cache.SingletonWeigher;
import org.checkerframework.checker.index.qual.NonNegative;

@FunctionalInterface
public interface Weigher<K, V> {
    public @NonNegative int weigh(K var1, V var2);

    public static <K, V> Weigher<K, V> singletonWeigher() {
        SingletonWeigher self = SingletonWeigher.INSTANCE;
        return self;
    }

    public static <K, V> Weigher<K, V> boundedWeigher(Weigher<K, V> delegate) {
        return new BoundedWeigher<K, V>(delegate);
    }
}

