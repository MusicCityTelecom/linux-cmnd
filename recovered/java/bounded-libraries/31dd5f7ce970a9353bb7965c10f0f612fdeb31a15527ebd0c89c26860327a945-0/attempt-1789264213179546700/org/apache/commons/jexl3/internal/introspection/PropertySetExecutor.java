/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.jexl3.internal.introspection;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.apache.commons.jexl3.JexlException;
import org.apache.commons.jexl3.internal.introspection.AbstractExecutor;
import org.apache.commons.jexl3.internal.introspection.Introspector;

public class PropertySetExecutor
extends AbstractExecutor.Set {
    private static final int SET_START_INDEX = 3;
    protected final String property;
    protected final Class<?> valueClass;

    public static PropertySetExecutor discover(Introspector is, Class<?> clazz, String property, Object value) {
        if (property == null || property.isEmpty()) {
            return null;
        }
        Method method = PropertySetExecutor.discoverSet(is, clazz, property, value);
        return method != null ? new PropertySetExecutor(clazz, method, property, value) : null;
    }

    protected PropertySetExecutor(Class<?> clazz, Method method, String key, Object value) {
        super(clazz, method);
        this.property = key;
        this.valueClass = PropertySetExecutor.classOf(value);
    }

    @Override
    public Object getTargetProperty() {
        return this.property;
    }

    @Override
    public Object invoke(Object o, Object arg) throws IllegalAccessException, InvocationTargetException {
        if (this.method != null) {
            Class<?> componentType;
            if (PropertySetExecutor.isEmptyArray(arg) && (componentType = this.method.getParameterTypes()[0].getComponentType()) != null && !componentType.equals(arg.getClass().getComponentType())) {
                arg = Array.newInstance(componentType, 0);
            }
            this.method.invoke(o, arg);
        }
        return arg;
    }

    @Override
    public Object tryInvoke(Object o, Object identifier, Object value) {
        if (o != null && this.method != null && this.property.equals(PropertySetExecutor.castString(identifier)) && this.objectClass.equals(o.getClass()) && this.valueClass.equals(PropertySetExecutor.classOf(value))) {
            try {
                return this.invoke(o, value);
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

    private static boolean isEmptyArray(Object arg) {
        return arg != null && arg.getClass().isArray() && Array.getLength(arg) == 0;
    }

    private static Method discoverSet(Introspector is, Class<?> clazz, String property, Object arg) {
        Object[] params = new Object[]{arg};
        StringBuilder sb = new StringBuilder("set");
        sb.append(property);
        char c = sb.charAt(3);
        sb.setCharAt(3, Character.toUpperCase(c));
        Method method = is.getMethod(clazz, sb.toString(), params);
        if (method == null) {
            sb.setCharAt(3, Character.toLowerCase(c));
            method = is.getMethod(clazz, sb.toString(), params);
            if (method == null && PropertySetExecutor.isEmptyArray(arg)) {
                sb.setCharAt(3, Character.toUpperCase(c));
                method = PropertySetExecutor.lookupSetEmptyArray(is, clazz, sb.toString());
                if (method == null) {
                    sb.setCharAt(3, Character.toLowerCase(c));
                    method = PropertySetExecutor.lookupSetEmptyArray(is, clazz, sb.toString());
                }
            }
        }
        return method;
    }

    private static Method lookupSetEmptyArray(Introspector is, Class<?> clazz, String mname) {
        Method candidate = null;
        Method[] methods = is.getMethods(clazz, mname);
        if (methods != null) {
            for (Method method : methods) {
                Class<?>[] paramTypes = method.getParameterTypes();
                if (paramTypes.length != 1 || !paramTypes[0].isArray()) continue;
                if (candidate != null) {
                    return null;
                }
                candidate = method;
            }
        }
        return candidate;
    }
}

