/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.internal.inject;

import java.lang.reflect.Type;
import java.util.Set;

public interface ServiceHolder<T> {
    public T getInstance();

    public Class<T> getImplementationClass();

    public Set<Type> getContractTypes();

    public int getRank();
}

