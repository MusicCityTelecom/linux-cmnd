/*
 * Decompiled with CFR 0.152.
 */
package groovy.lang;

import groovy.lang.AdaptingMetaClass;
import groovy.lang.Closure;
import groovy.lang.GroovyObject;
import groovy.lang.GroovySystem;
import groovy.lang.Interceptor;
import groovy.lang.MetaClass;
import groovy.lang.MetaClassImpl;
import groovy.lang.MetaClassRegistry;
import groovy.lang.PropertyAccessInterceptor;
import java.util.Objects;
import java.util.function.Supplier;

public class ProxyMetaClass
extends MetaClassImpl
implements AdaptingMetaClass {
    protected MetaClass adaptee;
    protected Interceptor interceptor;

    public static ProxyMetaClass getInstance(Class theClass) {
        MetaClassRegistry metaRegistry = GroovySystem.getMetaClassRegistry();
        MetaClass meta = metaRegistry.getMetaClass(theClass);
        return new ProxyMetaClass(metaRegistry, theClass, meta);
    }

    public ProxyMetaClass(MetaClassRegistry registry, Class theClass, MetaClass adaptee) {
        super(registry, theClass);
        this.adaptee = Objects.requireNonNull(adaptee, "adaptee must not be null");
        super.initialize();
    }

    @Override
    public synchronized void initialize() {
        this.adaptee.initialize();
    }

    @Override
    public MetaClass getAdaptee() {
        return this.adaptee;
    }

    @Override
    public void setAdaptee(MetaClass metaClass) {
        this.adaptee = metaClass;
    }

    public Interceptor getInterceptor() {
        return this.interceptor;
    }

    public void setInterceptor(Interceptor interceptor) {
        this.interceptor = interceptor;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Object use(Closure closure) {
        MetaClass origMetaClass = this.registry.getMetaClass(this.theClass);
        this.registry.setMetaClass(this.theClass, this);
        try {
            Object v = closure.call();
            return v;
        }
        finally {
            this.registry.setMetaClass(this.theClass, origMetaClass);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Object use(GroovyObject object, Closure closure) {
        MetaClass origMetaClass = object.getMetaClass();
        object.setMetaClass(this);
        try {
            Object v = closure.call();
            return v;
        }
        finally {
            object.setMetaClass(origMetaClass);
        }
    }

    @Override
    public Object invokeMethod(Object object, String methodName, Object[] arguments) {
        return this.doCall(object, methodName, arguments, this.interceptor, () -> this.adaptee.invokeMethod(object, methodName, arguments));
    }

    @Override
    public Object invokeMethod(Class sender, Object object, String methodName, Object[] arguments, boolean isCallToSuper, boolean fromInsideClass) {
        return this.doCall(object, methodName, arguments, this.interceptor, () -> this.adaptee.invokeMethod(sender, object, methodName, arguments, isCallToSuper, fromInsideClass));
    }

    @Override
    public Object invokeStaticMethod(Object object, String methodName, Object[] arguments) {
        return this.doCall(object, methodName, arguments, this.interceptor, () -> this.adaptee.invokeStaticMethod(object, methodName, arguments));
    }

    @Override
    public Object invokeConstructor(Object[] arguments) {
        return this.doCall(this.theClass, "ctor", arguments, this.interceptor, () -> this.adaptee.invokeConstructor(arguments));
    }

    @Override
    public Object getProperty(Class aClass, Object object, String property, boolean useSuper, boolean fromInsideClass) {
        if (null == this.interceptor) {
            return super.getProperty(aClass, object, property, useSuper, fromInsideClass);
        }
        if (this.interceptor instanceof PropertyAccessInterceptor) {
            PropertyAccessInterceptor pae = (PropertyAccessInterceptor)this.interceptor;
            Object result = pae.beforeGet(object, property);
            if (this.interceptor.doInvoke()) {
                result = super.getProperty(aClass, object, property, useSuper, fromInsideClass);
            }
            return result;
        }
        return super.getProperty(aClass, object, property, useSuper, fromInsideClass);
    }

    @Override
    public void setProperty(Class aClass, Object object, String property, Object newValue, boolean useSuper, boolean fromInsideClass) {
        if (null == this.interceptor) {
            super.setProperty(aClass, object, property, newValue, useSuper, fromInsideClass);
        }
        if (this.interceptor instanceof PropertyAccessInterceptor) {
            PropertyAccessInterceptor pae = (PropertyAccessInterceptor)this.interceptor;
            pae.beforeSet(object, property, newValue);
            if (this.interceptor.doInvoke()) {
                super.setProperty(aClass, object, property, newValue, useSuper, fromInsideClass);
            }
        } else {
            super.setProperty(aClass, object, property, newValue, useSuper, fromInsideClass);
        }
    }

    private Object doCall(Object object, String methodName, Object[] arguments, Interceptor interceptor, Supplier<Object> howToInvoke) {
        if (interceptor == null) {
            return howToInvoke.get();
        }
        Object result = interceptor.beforeInvoke(object, methodName, arguments);
        if (interceptor.doInvoke()) {
            result = howToInvoke.get();
        }
        result = interceptor.afterInvoke(object, methodName, arguments, result);
        return result;
    }
}

