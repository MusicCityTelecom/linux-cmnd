/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.internal.guava;

import java.util.concurrent.ExecutionException;
import java.util.function.Function;
import org.glassfish.jersey.internal.guava.Cache;

public interface LoadingCache<K, V>
extends Cache<K, V>,
Function<K, V> {
    public V get(K var1) throws ExecutionException;

    @Override
    @Deprecated
    public V apply(K var1);
}

