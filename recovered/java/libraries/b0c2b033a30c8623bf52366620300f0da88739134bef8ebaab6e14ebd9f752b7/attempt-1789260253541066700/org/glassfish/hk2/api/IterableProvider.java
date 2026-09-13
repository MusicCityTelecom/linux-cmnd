/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.hk2.api;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import javax.inject.Provider;
import org.glassfish.hk2.api.ServiceHandle;

public interface IterableProvider<T>
extends Provider<T>,
Iterable<T> {
    public ServiceHandle<T> getHandle();

    public int getSize();

    public IterableProvider<T> named(String var1);

    public <U> IterableProvider<U> ofType(Type var1);

    public IterableProvider<T> qualifiedWith(Annotation ... var1);

    public Iterable<ServiceHandle<T>> handleIterator();
}

