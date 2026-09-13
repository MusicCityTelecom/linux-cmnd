/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.aopalliance.intercept.MethodInterceptor
 *  org.aopalliance.intercept.MethodInvocation
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.reactivestreams.Publisher
 *  org.springframework.messaging.Message
 *  org.springframework.util.Assert
 *  reactor.core.publisher.Mono
 */
package org.springframework.integration.handler.advice;

import java.lang.reflect.Method;
import java.util.function.BiFunction;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.reactivestreams.Publisher;
import org.springframework.messaging.Message;
import org.springframework.util.Assert;
import reactor.core.publisher.Mono;

public class ReactiveRequestHandlerAdvice
implements MethodInterceptor {
    private static final Log LOGGER = LogFactory.getLog(ReactiveRequestHandlerAdvice.class);
    private final BiFunction<Message<?>, Mono<?>, Publisher<?>> replyCustomizer;

    public ReactiveRequestHandlerAdvice(BiFunction<Message<?>, Mono<?>, Publisher<?>> replyCustomizer) {
        Assert.notNull(replyCustomizer, (String)"'replyCustomizer' must not be null");
        this.replyCustomizer = replyCustomizer;
    }

    public final Object invoke(MethodInvocation invocation) throws Throwable {
        boolean isReactiveMethod;
        Object result = invocation.proceed();
        Method method = invocation.getMethod();
        Object invocationThis = invocation.getThis();
        Object[] arguments = invocation.getArguments();
        boolean bl = isReactiveMethod = method.getName().equals("handleRequestMessage") && arguments.length == 1 && arguments[0] instanceof Message && result instanceof Mono;
        if (!isReactiveMethod) {
            if (LOGGER.isWarnEnabled()) {
                String clazzName = invocationThis == null ? method.getDeclaringClass().getName() : invocationThis.getClass().getName();
                LOGGER.warn((Object)("This advice " + this.getClass().getName() + " can only be used for MessageHandlers with reactive reply; an attempt to advise method '" + method.getName() + "' in '" + clazzName + "' is ignored."));
            }
            return result;
        }
        Mono replyMono = (Mono)result;
        Message requestMessage = (Message)arguments[0];
        return replyMono.transform(mono -> this.replyCustomizer.apply((Message<?>)requestMessage, (Mono<?>)mono));
    }
}

