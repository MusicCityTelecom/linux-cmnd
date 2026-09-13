/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.reflection;

import groovy.lang.GroovyRuntimeException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import org.codehaus.groovy.reflection.AccessPermissionChecker;
import org.codehaus.groovy.reflection.CachedClass;
import org.codehaus.groovy.reflection.ParameterTypes;
import org.codehaus.groovy.reflection.ReflectionCache;
import org.codehaus.groovy.reflection.ReflectionUtils;
import org.codehaus.groovy.runtime.FormatHelper;
import org.codehaus.groovy.runtime.InvokerInvocationException;

public class CachedConstructor
extends ParameterTypes {
    private final CachedClass clazz;
    private final Constructor cachedConstructor;
    private boolean makeAccessibleDone = false;
    private boolean accessAllowed = false;

    public CachedConstructor(CachedClass clazz, Constructor c) {
        this.cachedConstructor = c;
        this.clazz = clazz;
    }

    public CachedConstructor(Constructor c) {
        this(ReflectionCache.getCachedClass(c.getDeclaringClass()), c);
    }

    @Override
    protected Class[] getPT() {
        return this.cachedConstructor.getParameterTypes();
    }

    public static CachedConstructor find(Constructor constructor) {
        CachedConstructor[] constructors;
        for (CachedConstructor cachedConstructor : constructors = ReflectionCache.getCachedClass(constructor.getDeclaringClass()).getConstructors()) {
            if (!cachedConstructor.cachedConstructor.equals(constructor)) continue;
            return cachedConstructor;
        }
        throw new RuntimeException("Couldn't find method: " + constructor);
    }

    public Object doConstructorInvoke(Object[] argumentArray) {
        argumentArray = this.coerceArgumentsToClasses(argumentArray);
        return this.invoke(argumentArray);
    }

    public Object invoke(Object[] argumentArray) {
        Constructor constr = this.cachedConstructor;
        if (this.isConstructorOfAbstractClass()) {
            throw CachedConstructor.createException("failed to invoke constructor: ", constr, argumentArray, new InstantiationException(), true);
        }
        this.makeAccessibleIfNecessary();
        try {
            return constr.newInstance(argumentArray);
        }
        catch (InvocationTargetException e) {
            throw e.getCause() instanceof RuntimeException ? (RuntimeException)e.getCause() : new InvokerInvocationException(e);
        }
        catch (IllegalArgumentException e) {
            throw CachedConstructor.createException("failed to invoke constructor: ", constr, argumentArray, e, false);
        }
        catch (IllegalAccessException e) {
            throw CachedConstructor.createException("could not access constructor: ", constr, argumentArray, e, false);
        }
        catch (Exception e) {
            if (e instanceof RuntimeException) {
                throw (RuntimeException)e;
            }
            throw CachedConstructor.createException("failed to invoke constructor: ", constr, argumentArray, e, true);
        }
    }

    private static GroovyRuntimeException createException(String init, Constructor constructor, Object[] argumentArray, Throwable e, boolean setReason) {
        return new GroovyRuntimeException(init + constructor + " with arguments: " + FormatHelper.toString(argumentArray) + " reason: " + e, setReason ? e : null);
    }

    public String toString() {
        return this.cachedConstructor.toString();
    }

    public int getModifiers() {
        return this.cachedConstructor.getModifiers();
    }

    public CachedClass getCachedClass() {
        return this.clazz;
    }

    public Class getDeclaringClass() {
        return this.cachedConstructor.getDeclaringClass();
    }

    public Constructor getCachedConstructor() {
        this.makeAccessibleIfNecessary();
        if (!this.accessAllowed) {
            AccessPermissionChecker.checkAccessPermission(this.cachedConstructor);
            this.accessAllowed = true;
        }
        return this.cachedConstructor;
    }

    private void makeAccessibleIfNecessary() {
        if (!this.makeAccessibleDone) {
            ReflectionUtils.makeAccessibleInPrivilegedAction(this.cachedConstructor);
            this.makeAccessibleDone = true;
        }
    }

    private boolean isConstructorOfAbstractClass() {
        return Modifier.isAbstract(this.cachedConstructor.getDeclaringClass().getModifiers());
    }
}

