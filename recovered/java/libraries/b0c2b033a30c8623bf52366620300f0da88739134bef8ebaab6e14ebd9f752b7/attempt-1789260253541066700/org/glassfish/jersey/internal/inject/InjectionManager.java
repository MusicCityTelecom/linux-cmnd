/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.internal.inject;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.List;
import org.glassfish.jersey.internal.inject.Binder;
import org.glassfish.jersey.internal.inject.Binding;
import org.glassfish.jersey.internal.inject.ForeignDescriptor;
import org.glassfish.jersey.internal.inject.ServiceHolder;

public interface InjectionManager {
    public void completeRegistration();

    public void shutdown();

    public void register(Binding var1);

    public void register(Iterable<Binding> var1);

    public void register(Binder var1);

    public void register(Object var1) throws IllegalArgumentException;

    public boolean isRegistrable(Class<?> var1);

    public <T> T createAndInitialize(Class<T> var1);

    public <T> List<ServiceHolder<T>> getAllServiceHolders(Class<T> var1, Annotation ... var2);

    public <T> T getInstance(Class<T> var1, Annotation ... var2);

    public <T> T getInstance(Class<T> var1, String var2);

    public <T> T getInstance(Class<T> var1);

    public <T> T getInstance(Type var1);

    public Object getInstance(ForeignDescriptor var1);

    public ForeignDescriptor createForeignDescriptor(Binding var1);

    public <T> List<T> getAllInstances(Type var1);

    public void inject(Object var1);

    public void inject(Object var1, String var2);

    public void preDestroy(Object var1);
}

