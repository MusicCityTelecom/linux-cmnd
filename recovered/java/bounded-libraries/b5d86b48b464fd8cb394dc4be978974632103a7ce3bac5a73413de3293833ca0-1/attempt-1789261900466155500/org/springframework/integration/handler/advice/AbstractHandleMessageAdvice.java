/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.aopalliance.intercept.MethodInvocation
 *  org.springframework.messaging.Message
 *  org.springframework.messaging.MessageHandler
 */
package org.springframework.integration.handler.advice;

import java.lang.reflect.Method;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.integration.context.IntegrationObjectSupport;
import org.springframework.integration.handler.advice.HandleMessageAdvice;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;

public abstract class AbstractHandleMessageAdvice
extends IntegrationObjectSupport
implements HandleMessageAdvice {
    public final Object invoke(MethodInvocation invocation) throws Throwable {
        boolean isMessageMethod;
        Method method = invocation.getMethod();
        Object invocationThis = invocation.getThis();
        Object[] arguments = invocation.getArguments();
        boolean isMessageHandler = invocationThis instanceof MessageHandler;
        boolean bl = isMessageMethod = method.getName().equals("handleMessage") && arguments.length == 1 && arguments[0] instanceof Message;
        if (!isMessageHandler || !isMessageMethod) {
            if (this.logger.isWarnEnabled()) {
                String clazzName = invocationThis == null ? method.getDeclaringClass().getName() : invocationThis.getClass().getName();
                this.logger.warn((CharSequence)("This advice " + this.getClass().getName() + " can only be used for MessageHandlers; an attempt to advise method '" + method.getName() + "' in '" + clazzName + "' is ignored."));
            }
            return invocation.proceed();
        }
        Message message = (Message)arguments[0];
        return this.doInvoke(invocation, message);
    }

    protected abstract Object doInvoke(MethodInvocation var1, Message<?> var2) throws Throwable;
}

