/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.internal.guava;

public interface Cache<K, V> {
    public V getIfPresent(Object var1);

    public void put(K var1, V var2);
}

