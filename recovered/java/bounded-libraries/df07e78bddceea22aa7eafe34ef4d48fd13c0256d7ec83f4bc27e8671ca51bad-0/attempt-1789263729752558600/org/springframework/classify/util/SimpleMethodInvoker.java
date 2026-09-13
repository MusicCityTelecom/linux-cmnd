/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.aop.framework.Advised
 *  org.springframework.util.Assert
 *  org.springframework.util.ClassUtils
 */
package org.springframework.classify.util;

import java.lang.reflect.Method;
import java.util.Arrays;
import org.springframework.aop.framework.Advised;
import org.springframework.classify.util.MethodInvoker;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

public class SimpleMethodInvoker
implements MethodInvoker {
    private final Object object;
    private final Method method;
    private final Class<?>[] parameterTypes;
    private volatile Object target;

    public SimpleMethodInvoker(Object object, Method method) {
        Assert.notNull((Object)object, (String)"Object to invoke must not be null");
        Assert.notNull((Object)method, (String)"Method to invoke must not be null");
        this.method = method;
        method.setAccessible(true);
        this.object = object;
        this.parameterTypes = method.getParameterTypes();
    }

    public SimpleMethodInvoker(Object object, String methodName, Class<?> ... paramTypes) {
        Assert.notNull((Object)object, (String)"Object to invoke must not be null");
        Method method = ClassUtils.getMethodIfAvailable(object.getClass(), (String)methodName, (Class[])paramTypes);
        if (method == null) {
            method = ClassUtils.getMethodIfAvailable(object.getClass(), (String)methodName, (Class[])new Class[0]);
        }
        Assert.notNull((Object)method, (String)("No methods found for name: [" + methodName + "] in class: [" + object.getClass() + "] with arguments of type: [" + Arrays.toString(paramTypes) + "]"));
        this.object = object;
        this.method = method;
        method.setAccessible(true);
        this.parameterTypes = method.getParameterTypes();
    }

    @Override
    public Object invokeMethod(Object ... args) {
        Assert.state((this.parameterTypes.length == args.length ? 1 : 0) != 0, (String)("Wrong number of arguments, expected no more than: [" + this.parameterTypes.length + "]"));
        try {
            Object target = this.extractTarget(this.object, this.method);
            return this.method.invoke(target, args);
        }
        catch (Exception e) {
            throw new IllegalArgumentException("Unable to invoke method: [" + this.method + "] on object: [" + this.object + "] with arguments: [" + Arrays.toString(args) + "]", e);
        }
    }

    private Object extractTarget(Object target, Method method) {
        if (this.target == null) {
            if (target instanceof Advised) {
                Object source;
                try {
                    source = ((Advised)target).getTargetSource().getTarget();
                }
                catch (Exception e) {
                    throw new IllegalStateException("Could not extract target from proxy", e);
                }
                if (source instanceof Advised) {
                    source = this.extractTarget(source, method);
                }
                if (method.getDeclaringClass().isAssignableFrom(source.getClass())) {
                    target = source;
                }
            }
            this.target = target;
        }
        return this.target;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof SimpleMethodInvoker)) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        SimpleMethodInvoker rhs = (SimpleMethodInvoker)obj;
        return rhs.method.equals(this.method) && rhs.object.equals(this.object);
    }

    public int hashCode() {
        int result = 25;
        result = 31 * result + this.object.hashCode();
        result = 31 * result + this.method.hashCode();
        return result;
    }
}

