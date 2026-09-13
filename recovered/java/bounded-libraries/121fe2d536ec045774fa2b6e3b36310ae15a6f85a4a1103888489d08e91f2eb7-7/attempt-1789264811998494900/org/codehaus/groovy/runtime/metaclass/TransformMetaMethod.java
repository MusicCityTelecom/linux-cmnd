/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.runtime.metaclass;

import groovy.lang.MetaMethod;
import org.codehaus.groovy.reflection.CachedClass;

public class TransformMetaMethod
extends MetaMethod {
    private final MetaMethod metaMethod;

    public TransformMetaMethod(MetaMethod metaMethod) {
        this.metaMethod = metaMethod;
        this.setParametersTypes(metaMethod.getParameterTypes());
        this.nativeParamTypes = metaMethod.getNativeParameterTypes();
    }

    @Override
    public int getModifiers() {
        return this.metaMethod.getModifiers();
    }

    @Override
    public String getName() {
        return this.metaMethod.getName();
    }

    @Override
    public Class getReturnType() {
        return this.metaMethod.getReturnType();
    }

    @Override
    public CachedClass getDeclaringClass() {
        return this.metaMethod.getDeclaringClass();
    }

    @Override
    public Object invoke(Object object, Object[] arguments) {
        return this.metaMethod.invoke(object, arguments);
    }

    @Override
    public Object doMethodInvoke(Object object, Object[] arguments) {
        try {
            return this.invoke(object, arguments);
        }
        catch (Exception ex) {
            throw this.processDoMethodInvokeException(ex, object, arguments);
        }
    }
}

