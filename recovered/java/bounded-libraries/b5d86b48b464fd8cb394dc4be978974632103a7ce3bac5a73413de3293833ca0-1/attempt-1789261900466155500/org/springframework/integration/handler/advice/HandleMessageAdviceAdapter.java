/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.aopalliance.intercept.MethodInterceptor
 *  org.aopalliance.intercept.MethodInvocation
 *  org.springframework.util.Assert
 */
package org.springframework.integration.handler.advice;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.integration.handler.advice.HandleMessageAdvice;
import org.springframework.util.Assert;

public class HandleMessageAdviceAdapter
implements HandleMessageAdvice {
    private final MethodInterceptor delegate;

    public HandleMessageAdviceAdapter(MethodInterceptor delegate) {
        Assert.notNull((Object)delegate, (String)"The 'delegate' must not be null");
        this.delegate = delegate;
    }

    public Object invoke(MethodInvocation invocation) throws Throwable {
        return this.delegate.invoke(invocation);
    }
}

