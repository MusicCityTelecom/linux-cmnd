/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.internal.inject;

import java.util.function.Supplier;

public interface DisposableSupplier<T>
extends Supplier<T> {
    public void dispose(T var1);
}

