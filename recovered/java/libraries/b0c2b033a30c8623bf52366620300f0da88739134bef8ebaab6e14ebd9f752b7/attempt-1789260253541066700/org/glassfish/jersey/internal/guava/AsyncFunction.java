/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.internal.guava;

import org.glassfish.jersey.internal.guava.ListenableFuture;

interface AsyncFunction<I, O> {
    public ListenableFuture<O> apply(I var1);
}

