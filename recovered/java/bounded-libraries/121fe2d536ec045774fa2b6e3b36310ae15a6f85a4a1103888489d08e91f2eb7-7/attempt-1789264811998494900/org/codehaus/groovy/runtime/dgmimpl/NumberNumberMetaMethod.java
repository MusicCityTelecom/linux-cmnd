/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.runtime.dgmimpl;

import groovy.lang.MetaClassImpl;
import groovy.lang.MetaMethod;
import org.codehaus.groovy.reflection.CachedClass;
import org.codehaus.groovy.reflection.ReflectionCache;
import org.codehaus.groovy.runtime.callsite.CallSite;
import org.codehaus.groovy.runtime.callsite.CallSiteAwareMetaMethod;
import org.codehaus.groovy.runtime.callsite.PojoMetaMethodSite;
import org.codehaus.groovy.runtime.typehandling.NumberMath;

public abstract class NumberNumberMetaMethod
extends CallSiteAwareMetaMethod {
    private static final CachedClass NUMBER_CLASS = ReflectionCache.getCachedClass(Number.class);
    private static final CachedClass[] NUMBER_CLASS_ARR = new CachedClass[]{NUMBER_CLASS};

    protected NumberNumberMetaMethod() {
        this.parameterTypes = NUMBER_CLASS_ARR;
    }

    @Override
    public int getModifiers() {
        return 1;
    }

    @Override
    public Class getReturnType() {
        return NUMBER_CLASS.getTheClass();
    }

    @Override
    public final CachedClass getDeclaringClass() {
        return NUMBER_CLASS;
    }

    @Override
    public CallSite createPojoCallSite(CallSite site, MetaClassImpl metaClass, MetaMethod metaMethod, Class[] params, Object receiver, Object[] args) {
        Object firstArg = args[0];
        if (receiver instanceof Integer) {
            if (firstArg instanceof Integer) {
                return this.createIntegerInteger(site, metaClass, metaMethod, params, receiver, args);
            }
            if (firstArg instanceof Long) {
                return this.createIntegerLong(site, metaClass, metaMethod, params, receiver, args);
            }
            if (firstArg instanceof Float) {
                return this.createIntegerFloat(site, metaClass, metaMethod, params, receiver, args);
            }
            if (firstArg instanceof Double) {
                return this.createIntegerDouble(site, metaClass, metaMethod, params, receiver, args);
            }
        }
        if (receiver instanceof Long) {
            if (firstArg instanceof Integer) {
                return this.createLongInteger(site, metaClass, metaMethod, params, receiver, args);
            }
            if (firstArg instanceof Long) {
                return this.createLongLong(site, metaClass, metaMethod, params, receiver, args);
            }
            if (firstArg instanceof Float) {
                return this.createLongFloat(site, metaClass, metaMethod, params, receiver, args);
            }
            if (firstArg instanceof Double) {
                return this.createLongDouble(site, metaClass, metaMethod, params, receiver, args);
            }
        }
        if (receiver instanceof Float) {
            if (firstArg instanceof Integer) {
                return this.createFloatInteger(site, metaClass, metaMethod, params, receiver, args);
            }
            if (firstArg instanceof Long) {
                return this.createFloatLong(site, metaClass, metaMethod, params, receiver, args);
            }
            if (firstArg instanceof Float) {
                return this.createFloatFloat(site, metaClass, metaMethod, params, receiver, args);
            }
            if (firstArg instanceof Double) {
                return this.createFloatDouble(site, metaClass, metaMethod, params, receiver, args);
            }
        }
        if (receiver instanceof Double) {
            if (firstArg instanceof Integer) {
                return this.createDoubleInteger(site, metaClass, metaMethod, params, receiver, args);
            }
            if (firstArg instanceof Long) {
                return this.createDoubleLong(site, metaClass, metaMethod, params, receiver, args);
            }
            if (firstArg instanceof Float) {
                return this.createDoubleFloat(site, metaClass, metaMethod, params, receiver, args);
            }
            if (firstArg instanceof Double) {
                return this.createDoubleDouble(site, metaClass, metaMethod, params, receiver, args);
            }
        }
        return this.createNumberNumber(site, metaClass, metaMethod, params, receiver, args);
    }

    public abstract CallSite createIntegerInteger(CallSite var1, MetaClassImpl var2, MetaMethod var3, Class[] var4, Object var5, Object[] var6);

    public abstract CallSite createIntegerLong(CallSite var1, MetaClassImpl var2, MetaMethod var3, Class[] var4, Object var5, Object[] var6);

    public abstract CallSite createIntegerFloat(CallSite var1, MetaClassImpl var2, MetaMethod var3, Class[] var4, Object var5, Object[] var6);

    public abstract CallSite createIntegerDouble(CallSite var1, MetaClassImpl var2, MetaMethod var3, Class[] var4, Object var5, Object[] var6);

    public abstract CallSite createLongInteger(CallSite var1, MetaClassImpl var2, MetaMethod var3, Class[] var4, Object var5, Object[] var6);

    public abstract CallSite createLongLong(CallSite var1, MetaClassImpl var2, MetaMethod var3, Class[] var4, Object var5, Object[] var6);

    public abstract CallSite createLongFloat(CallSite var1, MetaClassImpl var2, MetaMethod var3, Class[] var4, Object var5, Object[] var6);

    public abstract CallSite createLongDouble(CallSite var1, MetaClassImpl var2, MetaMethod var3, Class[] var4, Object var5, Object[] var6);

    public abstract CallSite createFloatInteger(CallSite var1, MetaClassImpl var2, MetaMethod var3, Class[] var4, Object var5, Object[] var6);

    public abstract CallSite createFloatLong(CallSite var1, MetaClassImpl var2, MetaMethod var3, Class[] var4, Object var5, Object[] var6);

    public abstract CallSite createFloatFloat(CallSite var1, MetaClassImpl var2, MetaMethod var3, Class[] var4, Object var5, Object[] var6);

    public abstract CallSite createFloatDouble(CallSite var1, MetaClassImpl var2, MetaMethod var3, Class[] var4, Object var5, Object[] var6);

    public abstract CallSite createDoubleInteger(CallSite var1, MetaClassImpl var2, MetaMethod var3, Class[] var4, Object var5, Object[] var6);

    public abstract CallSite createDoubleLong(CallSite var1, MetaClassImpl var2, MetaMethod var3, Class[] var4, Object var5, Object[] var6);

    public abstract CallSite createDoubleFloat(CallSite var1, MetaClassImpl var2, MetaMethod var3, Class[] var4, Object var5, Object[] var6);

    public abstract CallSite createDoubleDouble(CallSite var1, MetaClassImpl var2, MetaMethod var3, Class[] var4, Object var5, Object[] var6);

    public abstract CallSite createNumberNumber(CallSite var1, MetaClassImpl var2, MetaMethod var3, Class[] var4, Object var5, Object[] var6);

    public static abstract class NumberNumberCallSite
    extends PojoMetaMethodSite {
        final NumberMath math;

        public NumberNumberCallSite(CallSite site, MetaClassImpl metaClass, MetaMethod metaMethod, Class[] params, Number receiver, Number arg) {
            super(site, metaClass, metaMethod, params);
            this.math = NumberMath.getMath(receiver, arg);
        }
    }
}

