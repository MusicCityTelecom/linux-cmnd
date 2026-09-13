/*
 * Decompiled with CFR 0.152.
 */
package com.github.benmanes.caffeine.cache;

import com.github.benmanes.caffeine.cache.StrongInterner;
import com.github.benmanes.caffeine.cache.WeakInterner;

@FunctionalInterface
public interface Interner<E> {
    public E intern(E var1);

    public static <E> Interner<E> newStrongInterner() {
        return new StrongInterner();
    }

    public static <E> Interner<E> newWeakInterner() {
        return new WeakInterner();
    }
}

