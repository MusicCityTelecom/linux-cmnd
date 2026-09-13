/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.hk2.api;

import java.lang.annotation.Annotation;
import org.glassfish.hk2.api.ActiveDescriptor;
import org.glassfish.hk2.api.ServiceHandle;
import org.jvnet.hk2.annotations.Contract;

@Contract
public interface Context<T> {
    public Class<? extends Annotation> getScope();

    public <U> U findOrCreate(ActiveDescriptor<U> var1, ServiceHandle<?> var2);

    public boolean containsKey(ActiveDescriptor<?> var1);

    public void destroyOne(ActiveDescriptor<?> var1);

    public boolean supportsNullCreation();

    public boolean isActive();

    public void shutdown();
}

