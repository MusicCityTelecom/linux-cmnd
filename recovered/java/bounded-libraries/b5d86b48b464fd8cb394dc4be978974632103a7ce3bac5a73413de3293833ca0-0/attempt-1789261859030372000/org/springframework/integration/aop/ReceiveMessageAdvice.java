/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.aopalliance.intercept.MethodInterceptor
 *  org.aopalliance.intercept.MethodInvocation
 *  org.springframework.lang.Nullable
 *  org.springframework.messaging.Message
 *  org.springframework.messaging.PollableChannel
 */
package org.springframework.integration.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.integration.core.MessageSource;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.PollableChannel;

@FunctionalInterface
public interface ReceiveMessageAdvice
extends MethodInterceptor {
    default public boolean beforeReceive(Object source) {
        return true;
    }

    @Nullable
    default public Object invoke(MethodInvocation invocation) throws Throwable {
        Object target = invocation.getThis();
        if (!(target instanceof MessageSource) && !(target instanceof PollableChannel)) {
            return invocation.proceed();
        }
        Message result = null;
        if (this.beforeReceive(target)) {
            result = (Message)invocation.proceed();
        }
        return this.afterReceive(result, target);
    }

    @Nullable
    public Message<?> afterReceive(@Nullable Message<?> var1, Object var2);
}

