/*
 * Decompiled with CFR 0.152.
 */
package de.cronn.reflection.util;

@FunctionalInterface
public interface TypedPropertyGetter<T, V> {
    public V get(T var1);
}

