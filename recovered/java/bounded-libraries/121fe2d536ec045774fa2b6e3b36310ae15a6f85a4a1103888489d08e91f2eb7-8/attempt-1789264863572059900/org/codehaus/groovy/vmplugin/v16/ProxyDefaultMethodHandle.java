/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.vmplugin.v16;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

class ProxyDefaultMethodHandle {
    private final Proxy proxy;
    private final Method method;

    ProxyDefaultMethodHandle(Proxy proxy, Method method) {
        this.proxy = proxy;
        this.method = method;
    }

    Object invokeWithArguments(Object ... arguments) throws Throwable {
        return InvocationHandler.invokeDefault(this.proxy, this.method, arguments);
    }
}

