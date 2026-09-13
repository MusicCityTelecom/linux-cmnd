/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.internal.util.collection;

public interface UnsafeValue<T, E extends Throwable> {
    public T get() throws E;
}

