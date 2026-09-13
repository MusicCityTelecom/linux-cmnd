/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.internal.guava;

import org.glassfish.jersey.internal.guava.AbstractFuture;

public final class SettableFuture<V>
extends AbstractFuture<V> {
    private SettableFuture() {
    }

    public static <V> SettableFuture<V> create() {
        return new SettableFuture<V>();
    }

    @Override
    public boolean set(V value) {
        return super.set(value);
    }

    @Override
    public boolean setException(Throwable throwable) {
        return super.setException(throwable);
    }
}

