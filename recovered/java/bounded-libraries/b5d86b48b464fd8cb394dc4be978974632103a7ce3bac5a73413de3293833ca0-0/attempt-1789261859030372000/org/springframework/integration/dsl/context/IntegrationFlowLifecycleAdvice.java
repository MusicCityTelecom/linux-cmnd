/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.aopalliance.intercept.MethodInterceptor
 *  org.aopalliance.intercept.MethodInvocation
 *  org.springframework.context.SmartLifecycle
 *  org.springframework.util.ObjectUtils
 */
package org.springframework.integration.dsl.context;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.context.SmartLifecycle;
import org.springframework.integration.dsl.StandardIntegrationFlow;
import org.springframework.util.ObjectUtils;

class IntegrationFlowLifecycleAdvice
implements MethodInterceptor {
    private final StandardIntegrationFlow delegate;

    IntegrationFlowLifecycleAdvice(StandardIntegrationFlow delegate) {
        this.delegate = delegate;
    }

    public Object invoke(MethodInvocation invocation) throws Throwable {
        Object target = invocation.getThis();
        String method = invocation.getMethod().getName();
        Object result = null;
        if ("getInputChannel".equals(method)) {
            result = invocation.proceed();
            if (result == null) {
                result = this.delegate.getInputChannel();
            }
        } else if ("getIntegrationComponents".equals(method)) {
            result = invocation.proceed();
            if (result == null) {
                result = this.delegate.getIntegrationComponents();
            }
        } else {
            if (target instanceof SmartLifecycle) {
                result = invocation.proceed();
            }
            result = this.applyToDelegate(invocation, method, result);
        }
        return result;
    }

    private Object applyToDelegate(MethodInvocation invocation, String method, Object resultArg) {
        Object result = resultArg;
        switch (method) {
            case "start": {
                this.delegate.start();
                break;
            }
            case "stop": {
                Object[] arguments = invocation.getArguments();
                if (!ObjectUtils.isEmpty((Object[])arguments)) {
                    this.delegate.stop((Runnable)arguments[0]);
                    break;
                }
                this.delegate.stop();
                break;
            }
            case "isRunning": {
                if (result != null) break;
                result = this.delegate.isRunning();
                break;
            }
            case "isAutoStartup": {
                if (result != null) break;
                result = this.delegate.isAutoStartup();
                break;
            }
            case "getPhase": {
                if (result != null) break;
                result = this.delegate.getPhase();
                break;
            }
        }
        return result;
    }
}

