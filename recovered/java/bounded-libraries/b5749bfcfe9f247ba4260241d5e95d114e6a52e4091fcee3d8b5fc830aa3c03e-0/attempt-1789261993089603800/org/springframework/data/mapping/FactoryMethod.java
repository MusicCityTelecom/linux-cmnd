/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.util.ReflectionUtils
 */
package org.springframework.data.mapping;

import java.lang.reflect.Method;
import org.springframework.data.mapping.InstanceCreatorMetadataSupport;
import org.springframework.data.mapping.Parameter;
import org.springframework.data.mapping.PersistentProperty;
import org.springframework.util.ReflectionUtils;

public final class FactoryMethod<T, P extends PersistentProperty<P>>
extends InstanceCreatorMetadataSupport<T, P> {
    @SafeVarargs
    public FactoryMethod(Method factoryMethod, Parameter<Object, P> ... parameters) {
        super(factoryMethod, parameters);
        ReflectionUtils.makeAccessible((Method)factoryMethod);
    }

    public Method getFactoryMethod() {
        return (Method)this.getExecutable();
    }
}

