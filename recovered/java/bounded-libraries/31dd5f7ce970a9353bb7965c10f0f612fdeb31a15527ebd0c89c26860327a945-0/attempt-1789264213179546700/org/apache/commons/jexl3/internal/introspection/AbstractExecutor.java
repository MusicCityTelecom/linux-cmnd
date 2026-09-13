/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.jexl3.internal.introspection;

import org.apache.commons.jexl3.JexlEngine;
import org.apache.commons.jexl3.internal.introspection.MethodKey;
import org.apache.commons.jexl3.introspection.JexlMethod;
import org.apache.commons.jexl3.introspection.JexlPropertyGet;
import org.apache.commons.jexl3.introspection.JexlPropertySet;

abstract class AbstractExecutor {
    public static final Object TRY_FAILED = JexlEngine.TRY_FAILED;
    protected final Class<?> objectClass;
    protected final java.lang.reflect.Method method;

    static java.lang.reflect.Method initMarker(Class<?> clazz, String name, Class<?> ... parms) {
        try {
            return clazz.getMethod(name, parms);
        }
        catch (Exception xnever) {
            throw new Error(xnever);
        }
    }

    static Integer castInteger(Object arg) {
        return arg instanceof Number ? Integer.valueOf(((Number)arg).intValue()) : null;
    }

    static String castString(Object arg) {
        return arg instanceof CharSequence || arg instanceof Integer ? arg.toString() : null;
    }

    static Object[] makeArgs(Object ... args) {
        return args;
    }

    static Class<?> classOf(Object instance) {
        return instance == null ? Object.class : instance.getClass();
    }

    protected AbstractExecutor(Class<?> theClass, java.lang.reflect.Method theMethod) {
        this.objectClass = theClass;
        this.method = theMethod;
    }

    public boolean equals(Object obj) {
        return this == obj || obj instanceof AbstractExecutor && this.equals((AbstractExecutor)obj);
    }

    public int hashCode() {
        return this.method.hashCode();
    }

    public boolean equals(AbstractExecutor arg) {
        if (!this.getClass().equals(arg.getClass())) {
            return false;
        }
        if (!this.getMethod().equals(arg.getMethod())) {
            return false;
        }
        if (!this.getTargetClass().equals(arg.getTargetClass())) {
            return false;
        }
        Object lhsp = this.getTargetProperty();
        Object rhsp = arg.getTargetProperty();
        if (lhsp == null && rhsp == null) {
            return true;
        }
        if (lhsp != null && rhsp != null) {
            return lhsp.equals(rhsp);
        }
        return false;
    }

    public final boolean isAlive() {
        return this.method != null;
    }

    public boolean isCacheable() {
        return this.method != null;
    }

    public final java.lang.reflect.Method getMethod() {
        return this.method;
    }

    public final Class<?> getTargetClass() {
        return this.objectClass;
    }

    public Object getTargetProperty() {
        return null;
    }

    public final String getMethodName() {
        return this.method.getName();
    }

    public final boolean tryFailed(Object exec) {
        return exec == JexlEngine.TRY_FAILED;
    }

    public static abstract class Method
    extends AbstractExecutor
    implements JexlMethod {
        protected final MethodKey key;

        protected Method(Class<?> c, java.lang.reflect.Method m, MethodKey k) {
            super(c, m);
            this.key = k;
        }

        @Override
        public Object getTargetProperty() {
            return this.key;
        }

        @Override
        public final Class<?> getReturnType() {
            return this.method.getReturnType();
        }
    }

    public static abstract class Set
    extends AbstractExecutor
    implements JexlPropertySet {
        protected Set(Class<?> theClass, java.lang.reflect.Method theMethod) {
            super(theClass, theMethod);
        }
    }

    public static abstract class Get
    extends AbstractExecutor
    implements JexlPropertyGet {
        protected Get(Class<?> theClass, java.lang.reflect.Method theMethod) {
            super(theClass, theMethod);
        }
    }
}

