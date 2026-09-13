/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.jexl3.internal.introspection;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.apache.commons.jexl3.JexlException;
import org.apache.commons.jexl3.internal.introspection.AbstractExecutor;
import org.apache.commons.jexl3.internal.introspection.Introspector;
import org.apache.commons.jexl3.internal.introspection.PropertyGetExecutor;

public final class BooleanGetExecutor
extends AbstractExecutor.Get {
    private final String property;

    public static BooleanGetExecutor discover(Introspector is, Class<?> clazz, String property) {
        Method m;
        if (!(property == null || property.isEmpty() || (m = PropertyGetExecutor.discoverGet(is, "is", clazz, property)) == null || m.getReturnType() != Boolean.TYPE && m.getReturnType() != Boolean.class)) {
            return new BooleanGetExecutor(clazz, m, property);
        }
        return null;
    }

    private BooleanGetExecutor(Class<?> clazz, Method method, String key) {
        super(clazz, method);
        this.property = key;
    }

    @Override
    public Object getTargetProperty() {
        return this.property;
    }

    @Override
    public Object invoke(Object obj) throws IllegalAccessException, InvocationTargetException {
        return this.method == null ? null : this.method.invoke(obj, null);
    }

    @Override
    public Object tryInvoke(Object obj, Object key) {
        if (obj != null && this.method != null && this.property.equals(key) && this.objectClass.equals(obj.getClass())) {
            try {
                return this.method.invoke(obj, null);
            }
            catch (IllegalAccessException xill) {
                return TRY_FAILED;
            }
            catch (InvocationTargetException xinvoke) {
                throw JexlException.tryFailed(xinvoke);
            }
        }
        return TRY_FAILED;
    }
}

