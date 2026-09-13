/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.internal.util;

import java.util.concurrent.Callable;

public interface Producer<T>
extends Callable<T> {
    @Override
    public T call();
}

