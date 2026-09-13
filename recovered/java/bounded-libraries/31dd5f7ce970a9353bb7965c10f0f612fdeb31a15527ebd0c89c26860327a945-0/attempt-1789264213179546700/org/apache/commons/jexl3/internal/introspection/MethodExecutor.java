/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.jexl3.internal.introspection;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.apache.commons.jexl3.JexlEngine;
import org.apache.commons.jexl3.JexlException;
import org.apache.commons.jexl3.internal.introspection.AbstractExecutor;
import org.apache.commons.jexl3.internal.introspection.ArrayListWrapper;
import org.apache.commons.jexl3.internal.introspection.Introspector;
import org.apache.commons.jexl3.internal.introspection.MethodKey;

public final class MethodExecutor
extends AbstractExecutor.Method {
    private final int vaStart;
    private final Class<?> vaClass;

    public static MethodExecutor discover(Introspector is, Object obj, String method, Object[] args) {
        MethodKey key;
        Class<?> clazz = obj.getClass();
        Method m = is.getMethod(clazz, key = new MethodKey(method, args));
        if (m == null && clazz.isArray()) {
            m = is.getMethod(ArrayListWrapper.class, key);
        }
        if (m == null && obj instanceof Class) {
            m = is.getMethod((Class)obj, key);
        }
        return m == null ? null : new MethodExecutor(clazz, m, key);
    }

    private MethodExecutor(Class<?> c, Method m, MethodKey k) {
        super(c, m, k);
        int vastart = -1;
        Class<?> vaclass = null;
        if (MethodKey.isVarArgs(this.method)) {
            Class<?>[] formal = this.method.getParameterTypes();
            vastart = formal.length - 1;
            vaclass = formal[vastart].getComponentType();
        }
        this.vaStart = vastart;
        this.vaClass = vaclass;
    }

    @Override
    public Object invoke(Object o, Object ... args) throws IllegalAccessException, InvocationTargetException {
        if (this.vaClass != null && args != null) {
            args = this.handleVarArg(args);
        }
        if (this.method.getDeclaringClass() == ArrayListWrapper.class && o.getClass().isArray()) {
            return this.method.invoke(new ArrayListWrapper(o), args);
        }
        return this.method.invoke(o, args);
    }

    @Override
    public Object tryInvoke(String name, Object obj, Object ... args) {
        MethodKey tkey = new MethodKey(name, args);
        if (this.objectClass.equals(obj.getClass()) && tkey.equals(this.key)) {
            try {
                return this.invoke(obj, args);
            }
            catch (IllegalAccessException | IllegalArgumentException xill) {
                return TRY_FAILED;
            }
            catch (InvocationTargetException xinvoke) {
                throw JexlException.tryFailed(xinvoke);
            }
        }
        return JexlEngine.TRY_FAILED;
    }

    private Object[] handleVarArg(Object[] actual) {
        Class<?> vaclass = this.vaClass;
        int vastart = this.vaStart;
        int varargc = actual.length - vastart;
        if (varargc == 1) {
            Class<?> aclazz;
            if (!(actual[vastart] == null || (aclazz = actual[vastart].getClass()).isArray() && vaclass.isAssignableFrom(aclazz.getComponentType()))) {
                Object lastActual = Array.newInstance(vaclass, 1);
                Array.set(lastActual, 0, actual[vastart]);
                actual[vastart] = lastActual;
            }
        } else {
            Object varargs = Array.newInstance(vaclass, varargc);
            System.arraycopy(actual, vastart, varargs, 0, varargc);
            Object[] newActual = new Object[vastart + 1];
            System.arraycopy(actual, 0, newActual, 0, vastart);
            newActual[vastart] = varargs;
            actual = newActual;
        }
        return actual;
    }
}

