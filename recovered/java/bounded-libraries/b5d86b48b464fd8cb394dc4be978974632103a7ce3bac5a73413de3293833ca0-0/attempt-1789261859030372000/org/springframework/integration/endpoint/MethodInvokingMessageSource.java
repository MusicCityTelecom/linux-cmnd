/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.context.Lifecycle
 *  org.springframework.messaging.MessagingException
 *  org.springframework.util.Assert
 *  org.springframework.util.ReflectionUtils
 */
package org.springframework.integration.endpoint;

import java.lang.reflect.Method;
import org.springframework.context.Lifecycle;
import org.springframework.integration.endpoint.AbstractMessageSource;
import org.springframework.integration.support.management.ManageableLifecycle;
import org.springframework.messaging.MessagingException;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;

public class MethodInvokingMessageSource
extends AbstractMessageSource<Object>
implements ManageableLifecycle {
    private volatile Object object;
    private volatile Method method;
    private volatile String methodName;
    private volatile boolean initialized;
    private final Object initializationMonitor = new Object();

    public void setObject(Object object) {
        Assert.notNull((Object)object, (String)"'object' must not be null");
        this.object = object;
    }

    public void setMethod(Method method) {
        Assert.notNull((Object)method, (String)"'method' must not be null");
        this.method = method;
    }

    public void setMethodName(String methodName) {
        Assert.notNull((Object)methodName, (String)"'methodName' must not be null");
        this.methodName = methodName;
    }

    @Override
    public String getComponentType() {
        return "inbound-channel-adapter";
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    protected void onInit() {
        Object object = this.initializationMonitor;
        synchronized (object) {
            if (this.initialized) {
                return;
            }
            Assert.notNull((Object)this.object, (String)"object is required");
            Assert.isTrue((this.method != null || this.methodName != null ? 1 : 0) != 0, (String)"method or methodName is required");
            if (this.method == null) {
                this.method = ReflectionUtils.findMethod(this.object.getClass(), (String)this.methodName);
                Assert.notNull((Object)this.method, (String)("no such method '" + this.methodName + "' is available on " + this.object.getClass()));
            }
            Assert.isTrue((!Void.TYPE.equals(this.method.getReturnType()) ? 1 : 0) != 0, (String)("invalid MessageSource method '" + this.method.getName() + "', a non-void return is required"));
            ReflectionUtils.makeAccessible((Method)this.method);
            this.initialized = true;
        }
    }

    @Override
    public void start() {
        if (this.object instanceof Lifecycle) {
            ((Lifecycle)this.object).start();
        }
    }

    @Override
    public void stop() {
        if (this.object instanceof Lifecycle) {
            ((Lifecycle)this.object).stop();
        }
    }

    @Override
    public boolean isRunning() {
        return !(this.object instanceof Lifecycle) || ((Lifecycle)this.object).isRunning();
    }

    @Override
    protected Object doReceive() {
        try {
            if (!this.initialized) {
                this.afterPropertiesSet();
            }
            return ReflectionUtils.invokeMethod((Method)this.method, (Object)this.object);
        }
        catch (Exception e) {
            throw new MessagingException("Failed to invoke method", (Throwable)e);
        }
    }
}

