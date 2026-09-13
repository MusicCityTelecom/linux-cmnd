/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.reflection;

import groovy.lang.MetaClassImpl;
import groovy.lang.MetaMethod;
import groovy.lang.MissingMethodException;
import java.lang.annotation.Annotation;
import java.lang.ref.SoftReference;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import org.codehaus.groovy.classgen.asm.BytecodeHelper;
import org.codehaus.groovy.reflection.AccessPermissionChecker;
import org.codehaus.groovy.reflection.CacheAccessControlException;
import org.codehaus.groovy.reflection.CachedClass;
import org.codehaus.groovy.reflection.ParameterTypes;
import org.codehaus.groovy.reflection.ReflectionCache;
import org.codehaus.groovy.reflection.ReflectionUtils;
import org.codehaus.groovy.runtime.InvokerInvocationException;
import org.codehaus.groovy.runtime.callsite.CallSite;
import org.codehaus.groovy.runtime.callsite.CallSiteGenerator;
import org.codehaus.groovy.runtime.callsite.PogoMetaMethodSite;
import org.codehaus.groovy.runtime.callsite.PojoMetaMethodSite;
import org.codehaus.groovy.runtime.callsite.StaticMetaMethodSite;
import org.codehaus.groovy.runtime.metaclass.MethodHelper;

public class CachedMethod
extends MetaMethod
implements Comparable {
    public static final CachedMethod[] EMPTY_ARRAY = new CachedMethod[0];
    public final CachedClass cachedClass;
    private final Method cachedMethod;
    private int hashCode;
    private boolean skipCompiled;
    private boolean accessAllowed;
    private boolean makeAccessibleDone;
    private CachedMethod transformedMethod;
    private SoftReference<Constructor<CallSite>> pogoCallSiteConstructor;
    private SoftReference<Constructor<CallSite>> pojoCallSiteConstructor;
    private SoftReference<Constructor<CallSite>> staticCallSiteConstructor;

    public static CachedMethod find(Method method) {
        CachedMethod[] methods = ReflectionCache.getCachedClass(method.getDeclaringClass()).getMethods();
        int i = Arrays.binarySearch(methods, method, (o1, o2) -> {
            if (o1 instanceof CachedMethod) {
                return ((CachedMethod)o1).compareTo(o2);
            }
            if (o2 instanceof CachedMethod) {
                return -((CachedMethod)o2).compareTo(o1);
            }
            throw new ClassCastException("One of the two comparables must be a CachedMethod");
        });
        return i < 0 ? null : methods[i];
    }

    public CachedMethod(CachedClass clazz, Method method) {
        this.cachedMethod = method;
        this.cachedClass = clazz;
    }

    public CachedMethod(Method method) {
        this(ReflectionCache.getCachedClass(method.getDeclaringClass()), method);
    }

    public int compareTo(Object other) {
        if (other == this) {
            return 0;
        }
        if (other == null) {
            return -1;
        }
        return other instanceof CachedMethod ? this.compareToCachedMethod((CachedMethod)other) : this.compareToMethod((Method)other);
    }

    private int compareToCachedMethod(CachedMethod other) {
        CachedClass[] otherParams;
        int strComp = this.getName().compareTo(other.getName());
        if (strComp != 0) {
            return strComp;
        }
        int retComp = this.getReturnType().getName().compareTo(other.getReturnType().getName());
        if (retComp != 0) {
            return retComp;
        }
        CachedClass[] params = this.getParameterTypes();
        int pd = params.length - (otherParams = other.getParameterTypes()).length;
        if (pd != 0) {
            return pd;
        }
        int n = params.length;
        for (int i = 0; i < n; ++i) {
            int nameComp = params[i].getName().compareTo(otherParams[i].getName());
            if (nameComp == 0) continue;
            return nameComp;
        }
        int classComp = this.cachedClass.toString().compareTo(other.getDeclaringClass().toString());
        if (classComp != 0) {
            return classComp;
        }
        throw new RuntimeException("Should never happen");
    }

    private int compareToMethod(Method other) {
        Class<?>[] mparams;
        int strComp = this.getName().compareTo(other.getName());
        if (strComp != 0) {
            return strComp;
        }
        int retComp = this.getReturnType().getName().compareTo(other.getReturnType().getName());
        if (retComp != 0) {
            return retComp;
        }
        CachedClass[] params = this.getParameterTypes();
        int pd = params.length - (mparams = other.getParameterTypes()).length;
        if (pd != 0) {
            return pd;
        }
        int n = params.length;
        for (int i = 0; i < n; ++i) {
            int nameComp = params[i].getName().compareTo(mparams[i].getName());
            if (nameComp == 0) continue;
            return nameComp;
        }
        return 0;
    }

    public boolean equals(Object other) {
        return other instanceof CachedMethod && this.cachedMethod.equals(((CachedMethod)other).cachedMethod) || other instanceof Method && this.cachedMethod.equals(other);
    }

    public int hashCode() {
        if (this.hashCode == 0) {
            this.hashCode = this.cachedMethod.hashCode();
            if (this.hashCode == 0) {
                this.hashCode = -889274690;
            }
        }
        return this.hashCode;
    }

    @Override
    public String toString() {
        return this.cachedMethod.toString();
    }

    public boolean canAccessLegally(Class<?> callerClass) {
        return ReflectionUtils.checkAccessible(callerClass, this.cachedMethod.getDeclaringClass(), this.cachedMethod.getModifiers(), false);
    }

    public <T extends Annotation> T getAnnotation(Class<T> annotationClass) {
        return this.cachedMethod.getAnnotation(annotationClass);
    }

    public Method getCachedMethod() {
        this.makeAccessibleIfNecessary();
        if (!this.accessAllowed) {
            AccessPermissionChecker.checkAccessPermission(this.cachedMethod);
            this.accessAllowed = true;
        }
        return this.cachedMethod;
    }

    @Override
    public CachedClass getDeclaringClass() {
        return this.cachedClass;
    }

    @Override
    public String getDescriptor() {
        return BytecodeHelper.getMethodDescriptor(this.getReturnType(), this.getNativeParameterTypes());
    }

    @Override
    public int getModifiers() {
        return this.cachedMethod.getModifiers();
    }

    @Override
    public String getName() {
        return this.cachedMethod.getName();
    }

    public int getParamsCount() {
        return this.getParameterTypes().length;
    }

    public ParameterTypes getParamTypes() {
        return null;
    }

    @Override
    public Class[] getPT() {
        return this.cachedMethod.getParameterTypes();
    }

    @Override
    public Class getReturnType() {
        return this.cachedMethod.getReturnType();
    }

    @Override
    public String getSignature() {
        return this.getName() + this.getDescriptor();
    }

    public CachedMethod getTransformedMethod() {
        return this.transformedMethod;
    }

    public void setTransformedMethod(CachedMethod transformedMethod) {
        this.transformedMethod = transformedMethod;
    }

    @Override
    public boolean isStatic() {
        return MethodHelper.isStatic(this.cachedMethod);
    }

    public boolean isSynthetic() {
        return this.cachedMethod.isSynthetic();
    }

    public CallSite createPogoMetaMethodSite(CallSite site, MetaClassImpl metaClass, Class[] params) {
        if (!this.skipCompiled) {
            Constructor ctor = CachedMethod.deref(this.pogoCallSiteConstructor);
            if (ctor == null) {
                if (CallSiteGenerator.isCompilable(this)) {
                    ctor = CallSiteGenerator.compilePogoMethod(this);
                }
                if (ctor != null) {
                    this.pogoCallSiteConstructor = new SoftReference<Constructor>(ctor);
                } else {
                    this.skipCompiled = true;
                }
            }
            if (ctor != null) {
                try {
                    return (CallSite)ctor.newInstance(site, metaClass, this, params, ctor);
                }
                catch (Error e) {
                    this.skipCompiled = true;
                    throw e;
                }
                catch (Throwable e) {
                    this.skipCompiled = true;
                }
            }
        }
        return new PogoMetaMethodSite.PogoCachedMethodSiteNoUnwrapNoCoerce(site, metaClass, this, params);
    }

    public CallSite createPojoMetaMethodSite(CallSite site, MetaClassImpl metaClass, Class[] params) {
        if (!this.skipCompiled) {
            Constructor ctor = CachedMethod.deref(this.pojoCallSiteConstructor);
            if (ctor == null) {
                if (CallSiteGenerator.isCompilable(this)) {
                    ctor = CallSiteGenerator.compilePojoMethod(this);
                }
                if (ctor != null) {
                    this.pojoCallSiteConstructor = new SoftReference<Constructor>(ctor);
                } else {
                    this.skipCompiled = true;
                }
            }
            if (ctor != null) {
                try {
                    return (CallSite)ctor.newInstance(site, metaClass, this, params, ctor);
                }
                catch (Error e) {
                    this.skipCompiled = true;
                    throw e;
                }
                catch (Throwable e) {
                    this.skipCompiled = true;
                }
            }
        }
        return new PojoMetaMethodSite.PojoCachedMethodSiteNoUnwrapNoCoerce(site, metaClass, (MetaMethod)this, params);
    }

    public CallSite createStaticMetaMethodSite(CallSite site, MetaClassImpl metaClass, Class[] params) {
        if (!this.skipCompiled) {
            Constructor ctor = CachedMethod.deref(this.staticCallSiteConstructor);
            if (ctor == null) {
                if (CallSiteGenerator.isCompilable(this)) {
                    ctor = CallSiteGenerator.compileStaticMethod(this);
                }
                if (ctor != null) {
                    this.staticCallSiteConstructor = new SoftReference<Constructor>(ctor);
                } else {
                    this.skipCompiled = true;
                }
            }
            if (ctor != null) {
                try {
                    return (CallSite)ctor.newInstance(site, metaClass, this, params, ctor);
                }
                catch (Error e) {
                    this.skipCompiled = true;
                    throw e;
                }
                catch (Throwable e) {
                    this.skipCompiled = true;
                }
            }
        }
        return new StaticMetaMethodSite.StaticMetaMethodSiteNoUnwrapNoCoerce(site, metaClass, (MetaMethod)this, params);
    }

    private static <T> Constructor<T> deref(SoftReference<Constructor<T>> ref) {
        return ref != null ? ref.get() : null;
    }

    @Override
    public final Object invoke(Object object, Object[] arguments) {
        this.makeAccessibleIfNecessary();
        if (!this.accessAllowed) {
            try {
                AccessPermissionChecker.checkAccessPermission(this.cachedMethod);
                this.accessAllowed = true;
            }
            catch (CacheAccessControlException ex) {
                throw new InvokerInvocationException(ex);
            }
        }
        try {
            return this.cachedMethod.invoke(object, arguments);
        }
        catch (IllegalAccessException | IllegalArgumentException e) {
            throw new InvokerInvocationException(e);
        }
        catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            throw cause instanceof RuntimeException && !(cause instanceof MissingMethodException) ? (RuntimeException)cause : new InvokerInvocationException(e);
        }
    }

    private void makeAccessibleIfNecessary() {
        if (!this.makeAccessibleDone) {
            ReflectionUtils.makeAccessibleInPrivilegedAction(this.cachedMethod);
            this.makeAccessibleDone = true;
        }
    }

    public final Method setAccessible() {
        return this.getCachedMethod();
    }
}

