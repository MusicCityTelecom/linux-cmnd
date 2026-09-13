/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.internal.util.collection;

import org.glassfish.jersey.internal.util.collection.UnsafeValue;

public interface LazyUnsafeValue<T, E extends Throwable>
extends UnsafeValue<T, E> {
    public boolean isInitialized();
}

