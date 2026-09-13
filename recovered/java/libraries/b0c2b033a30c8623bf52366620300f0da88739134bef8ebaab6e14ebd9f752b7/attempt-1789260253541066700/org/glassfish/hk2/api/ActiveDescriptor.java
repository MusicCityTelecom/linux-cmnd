/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.hk2.api;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Set;
import org.glassfish.hk2.api.Descriptor;
import org.glassfish.hk2.api.Injectee;
import org.glassfish.hk2.api.ServiceHandle;
import org.glassfish.hk2.api.SingleCache;

public interface ActiveDescriptor<T>
extends Descriptor,
SingleCache<T> {
    public boolean isReified();

    public Class<?> getImplementationClass();

    public Type getImplementationType();

    public Set<Type> getContractTypes();

    public Annotation getScopeAsAnnotation();

    public Class<? extends Annotation> getScopeAnnotation();

    public Set<Annotation> getQualifierAnnotations();

    public List<Injectee> getInjectees();

    public Long getFactoryServiceId();

    public Long getFactoryLocatorId();

    public T create(ServiceHandle<?> var1);

    public void dispose(T var1);
}

