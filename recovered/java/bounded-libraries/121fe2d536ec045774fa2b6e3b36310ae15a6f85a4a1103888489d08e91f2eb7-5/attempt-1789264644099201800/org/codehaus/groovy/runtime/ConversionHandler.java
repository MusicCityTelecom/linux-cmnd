/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.runtime;

import groovy.lang.GroovyObject;
import groovy.lang.GroovyRuntimeException;
import groovy.lang.GroovySystem;
import groovy.lang.MetaClass;
import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.codehaus.groovy.runtime.ScriptBytecodeAdapter;
import org.codehaus.groovy.runtime.metaclass.MetaClassRegistryImpl;
import org.codehaus.groovy.vmplugin.VMPlugin;
import org.codehaus.groovy.vmplugin.VMPluginFactory;

public abstract class ConversionHandler
implements InvocationHandler,
Serializable {
    private final Object delegate;
    private static final long serialVersionUID = 1162833717190835227L;
    private final ConcurrentHashMap<Method, Object> handleCache = new ConcurrentHashMap(16, 0.9f, 2);
    private MetaClass metaClass;

    public ConversionHandler(Object delegate) {
        if (delegate == null) {
            throw new IllegalArgumentException("delegate must not be null");
        }
        this.delegate = delegate;
    }

    public Object getDelegate() {
        return this.delegate;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (this.isDefaultMethod(method) && !this.defaultOverridden(method)) {
            VMPlugin plugin = VMPluginFactory.getPlugin();
            Object handle = this.handleCache.computeIfAbsent(method, m -> plugin.getInvokeSpecialHandle((Method)m, proxy));
            return plugin.invokeHandle(handle, args);
        }
        if (!this.checkMethod(method)) {
            try {
                if (method.getDeclaringClass() == GroovyObject.class) {
                    switch (method.getName()) {
                        case "getMetaClass": {
                            return this.getMetaClass(proxy);
                        }
                        case "setMetaClass": {
                            return this.setMetaClass((MetaClass)args[0]);
                        }
                    }
                }
                return this.invokeCustom(proxy, method, args);
            }
            catch (GroovyRuntimeException gre) {
                throw ScriptBytecodeAdapter.unwrap(gre);
            }
        }
        try {
            return method.invoke(this, args);
        }
        catch (InvocationTargetException ite) {
            throw ite.getTargetException();
        }
    }

    private boolean defaultOverridden(Method method) {
        return this.delegate instanceof Map && ((Map)this.delegate).containsKey(method.getName());
    }

    protected boolean isDefaultMethod(Method method) {
        return method.isDefault();
    }

    protected boolean checkMethod(Method method) {
        return ConversionHandler.isCoreObjectMethod(method);
    }

    public abstract Object invokeCustom(Object var1, Method var2, Object[] var3) throws Throwable;

    public boolean equals(Object obj) {
        if (obj instanceof Proxy) {
            obj = Proxy.getInvocationHandler(obj);
        }
        if (obj instanceof ConversionHandler) {
            return ((ConversionHandler)obj).getDelegate().equals(this.delegate);
        }
        return false;
    }

    public int hashCode() {
        return this.delegate.hashCode();
    }

    public String toString() {
        return this.delegate.toString();
    }

    public static boolean isCoreObjectMethod(Method method) {
        return Object.class.equals(method.getDeclaringClass());
    }

    private MetaClass setMetaClass(MetaClass mc) {
        this.metaClass = mc;
        return mc;
    }

    private MetaClass getMetaClass(Object proxy) {
        MetaClass mc = this.metaClass;
        if (mc == null) {
            this.metaClass = mc = ((MetaClassRegistryImpl)GroovySystem.getMetaClassRegistry()).getMetaClass(proxy);
        }
        return mc;
    }
}

