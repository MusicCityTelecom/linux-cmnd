/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.jexl3.internal.introspection;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.apache.commons.jexl3.JexlException;
import org.apache.commons.jexl3.internal.introspection.AbstractExecutor;
import org.apache.commons.jexl3.internal.introspection.Introspector;

public final class PropertyGetExecutor
extends AbstractExecutor.Get {
    private static final Object[] EMPTY_PARAMS = new Object[0];
    private final String property;

    public static PropertyGetExecutor discover(Introspector is, Class<?> clazz, String property) {
        Method method = PropertyGetExecutor.discoverGet(is, "get", clazz, property);
        return method == null ? null : new PropertyGetExecutor(clazz, method, property);
    }

    private PropertyGetExecutor(Class<?> clazz, Method method, String identifier) {
        super(clazz, method);
        this.property = identifier;
    }

    @Override
    public Object getTargetProperty() {
        return this.property;
    }

    @Override
    public Object invoke(Object o) throws IllegalAccessException, InvocationTargetException {
        return this.method == null ? null : this.method.invoke(o, null);
    }

    @Override
    public Object tryInvoke(Object o, Object identifier) {
        if (o != null && this.method != null && this.property.equals(PropertyGetExecutor.castString(identifier)) && this.objectClass.equals(o.getClass())) {
            try {
                return this.method.invoke(o, null);
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

    static Method discoverGet(Introspector is, String which, Class<?> clazz, String property) {
        if (property == null || property.isEmpty()) {
            return null;
        }
        int start = which.length();
        StringBuilder sb = new StringBuilder(which);
        sb.append(property);
        char c = sb.charAt(start);
        sb.setCharAt(start, Character.toUpperCase(c));
        Method method = is.getMethod(clazz, sb.toString(), EMPTY_PARAMS);
        if (method == null) {
            sb.setCharAt(start, Character.toLowerCase(c));
            method = is.getMethod(clazz, sb.toString(), EMPTY_PARAMS);
        }
        return method;
    }
}

