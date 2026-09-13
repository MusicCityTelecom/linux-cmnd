/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.jexl3.internal.introspection;

import java.beans.IntrospectionException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import org.apache.commons.jexl3.JexlException;
import org.apache.commons.jexl3.internal.introspection.Introspector;
import org.apache.commons.jexl3.internal.introspection.MethodKey;
import org.apache.commons.jexl3.internal.introspection.Uberspect;
import org.apache.commons.jexl3.introspection.JexlMethod;

public final class ConstructorMethod
implements JexlMethod {
    private final Constructor<?> ctor;

    public static ConstructorMethod discover(Introspector is, Object ctorHandle, Object ... args) {
        String className;
        Class clazz = null;
        if (ctorHandle instanceof Class) {
            clazz = (Class)ctorHandle;
            className = clazz.getName();
        } else if (ctorHandle != null) {
            className = ctorHandle.toString();
        } else {
            return null;
        }
        Constructor<?> ctor = is.getConstructor(clazz, new MethodKey(className, args));
        if (ctor != null) {
            return new ConstructorMethod(ctor);
        }
        return null;
    }

    ConstructorMethod(Constructor<?> theCtor) {
        this.ctor = theCtor;
    }

    @Override
    public Object invoke(Object obj, Object ... params) throws Exception {
        Class<?> ctorClass = this.ctor.getDeclaringClass();
        boolean invoke = true;
        if (obj != null) {
            invoke = obj instanceof Class ? ctorClass.equals(obj) : ctorClass.getName().equals(obj.toString());
        }
        if (invoke) {
            return this.ctor.newInstance(params);
        }
        throw new IntrospectionException("constructor resolution error");
    }

    @Override
    public Object tryInvoke(String name, Object obj, Object ... params) {
        try {
            Class<?> ctorClass = this.ctor.getDeclaringClass();
            boolean invoke = true;
            if (obj != null) {
                invoke = obj instanceof Class ? ctorClass.equals(obj) : ctorClass.getName().equals(obj.toString());
            }
            if (invoke &= name == null || ctorClass.getName().equals(name)) {
                return this.ctor.newInstance(params);
            }
        }
        catch (IllegalAccessException | IllegalArgumentException | InstantiationException xinstance) {
            return Uberspect.TRY_FAILED;
        }
        catch (InvocationTargetException xinvoke) {
            throw JexlException.tryFailed(xinvoke);
        }
        return Uberspect.TRY_FAILED;
    }

    @Override
    public boolean tryFailed(Object rval) {
        return rval == Uberspect.TRY_FAILED;
    }

    @Override
    public boolean isCacheable() {
        return true;
    }

    @Override
    public Class<?> getReturnType() {
        return this.ctor.getDeclaringClass();
    }
}

