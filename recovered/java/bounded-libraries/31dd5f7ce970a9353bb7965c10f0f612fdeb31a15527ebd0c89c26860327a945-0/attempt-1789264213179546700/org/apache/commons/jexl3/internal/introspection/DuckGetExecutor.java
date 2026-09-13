/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.jexl3.internal.introspection;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.apache.commons.jexl3.JexlException;
import org.apache.commons.jexl3.internal.introspection.AbstractExecutor;
import org.apache.commons.jexl3.internal.introspection.Introspector;

public final class DuckGetExecutor
extends AbstractExecutor.Get {
    private final Object property;

    public static DuckGetExecutor discover(Introspector is, Class<?> clazz, Object identifier) {
        Method method = is.getMethod(clazz, "get", DuckGetExecutor.makeArgs(identifier));
        return method == null ? null : new DuckGetExecutor(clazz, method, identifier);
    }

    private DuckGetExecutor(Class<?> clazz, Method method, Object identifier) {
        super(clazz, method);
        this.property = identifier;
    }

    @Override
    public Object getTargetProperty() {
        return this.property;
    }

    @Override
    public Object invoke(Object obj) throws IllegalAccessException, InvocationTargetException {
        Object[] args = new Object[]{this.property};
        return this.method == null ? null : this.method.invoke(obj, args);
    }

    @Override
    public Object tryInvoke(Object obj, Object key) {
        if (obj != null && this.objectClass.equals(obj.getClass()) && this.method != null && (this.property == null && key == null || this.property != null && this.property.equals(key))) {
            try {
                Object[] args = new Object[]{this.property};
                return this.method.invoke(obj, args);
            }
            catch (IllegalAccessException | IllegalArgumentException xill) {
                return TRY_FAILED;
            }
            catch (InvocationTargetException xinvoke) {
                throw JexlException.tryFailed(xinvoke);
            }
        }
        return TRY_FAILED;
    }
}

