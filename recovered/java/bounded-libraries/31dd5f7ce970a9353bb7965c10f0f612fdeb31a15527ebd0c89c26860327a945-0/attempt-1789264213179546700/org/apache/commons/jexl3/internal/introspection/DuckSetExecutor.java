/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.jexl3.internal.introspection;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.apache.commons.jexl3.JexlException;
import org.apache.commons.jexl3.internal.introspection.AbstractExecutor;
import org.apache.commons.jexl3.internal.introspection.Introspector;

public final class DuckSetExecutor
extends AbstractExecutor.Set {
    private final Object property;
    private final Class<?> valueClass;

    public static DuckSetExecutor discover(Introspector is, Class<?> clazz, Object key, Object value) {
        Method method = is.getMethod(clazz, "set", DuckSetExecutor.makeArgs(key, value));
        if (method == null) {
            method = is.getMethod(clazz, "put", DuckSetExecutor.makeArgs(key, value));
        }
        return method == null ? null : new DuckSetExecutor(clazz, method, key, value);
    }

    private DuckSetExecutor(Class<?> clazz, Method method, Object key, Object value) {
        super(clazz, method);
        this.property = key;
        this.valueClass = DuckSetExecutor.classOf(value);
    }

    @Override
    public Object getTargetProperty() {
        return this.property;
    }

    @Override
    public Object invoke(Object obj, Object value) throws IllegalAccessException, InvocationTargetException {
        Object[] pargs = new Object[]{this.property, value};
        if (this.method != null) {
            this.method.invoke(obj, pargs);
        }
        return value;
    }

    @Override
    public Object tryInvoke(Object obj, Object key, Object value) {
        if (obj != null && this.objectClass.equals(obj.getClass()) && this.method != null && (this.property != null && this.property.equals(key) || this.property == null && key == null) && this.valueClass.equals(DuckSetExecutor.classOf(value))) {
            try {
                Object[] args = new Object[]{this.property, value};
                this.method.invoke(obj, args);
                return value;
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

