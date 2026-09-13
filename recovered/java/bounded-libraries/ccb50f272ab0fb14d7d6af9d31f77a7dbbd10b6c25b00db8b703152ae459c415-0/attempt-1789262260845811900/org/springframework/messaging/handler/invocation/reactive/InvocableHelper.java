/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.core.MethodParameter
 *  org.springframework.core.ReactiveAdapterRegistry
 *  org.springframework.lang.Nullable
 *  org.springframework.util.Assert
 *  reactor.core.publisher.Mono
 */
package org.springframework.messaging.handler.invocation.reactive;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.MethodParameter;
import org.springframework.core.ReactiveAdapterRegistry;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.HandlerMethod;
import org.springframework.messaging.handler.MessagingAdviceBean;
import org.springframework.messaging.handler.invocation.AbstractExceptionHandlerMethodResolver;
import org.springframework.messaging.handler.invocation.reactive.HandlerMethodArgumentResolver;
import org.springframework.messaging.handler.invocation.reactive.HandlerMethodArgumentResolverComposite;
import org.springframework.messaging.handler.invocation.reactive.HandlerMethodReturnValueHandler;
import org.springframework.messaging.handler.invocation.reactive.HandlerMethodReturnValueHandlerComposite;
import org.springframework.messaging.handler.invocation.reactive.InvocableHandlerMethod;
import org.springframework.util.Assert;
import reactor.core.publisher.Mono;

class InvocableHelper {
    private static final Log logger = LogFactory.getLog(InvocableHelper.class);
    private final HandlerMethodArgumentResolverComposite argumentResolvers = new HandlerMethodArgumentResolverComposite();
    private final HandlerMethodReturnValueHandlerComposite returnValueHandlers = new HandlerMethodReturnValueHandlerComposite();
    private ReactiveAdapterRegistry reactiveAdapterRegistry = ReactiveAdapterRegistry.getSharedInstance();
    private final Function<Class<?>, AbstractExceptionHandlerMethodResolver> exceptionMethodResolverFactory;
    private final Map<Class<?>, AbstractExceptionHandlerMethodResolver> exceptionHandlerCache = new ConcurrentHashMap(64);
    private final Map<MessagingAdviceBean, AbstractExceptionHandlerMethodResolver> exceptionHandlerAdviceCache = new LinkedHashMap<MessagingAdviceBean, AbstractExceptionHandlerMethodResolver>(64);

    public InvocableHelper(Function<Class<?>, AbstractExceptionHandlerMethodResolver> exceptionMethodResolverFactory) {
        this.exceptionMethodResolverFactory = exceptionMethodResolverFactory;
    }

    public void addArgumentResolvers(List<? extends HandlerMethodArgumentResolver> resolvers) {
        this.argumentResolvers.addResolvers(resolvers);
    }

    public HandlerMethodArgumentResolverComposite getArgumentResolvers() {
        return this.argumentResolvers;
    }

    public void addReturnValueHandlers(List<? extends HandlerMethodReturnValueHandler> handlers) {
        this.returnValueHandlers.addHandlers(handlers);
    }

    public void setReactiveAdapterRegistry(ReactiveAdapterRegistry registry) {
        Assert.notNull((Object)registry, (String)"ReactiveAdapterRegistry is required");
        this.reactiveAdapterRegistry = registry;
    }

    public ReactiveAdapterRegistry getReactiveAdapterRegistry() {
        return this.reactiveAdapterRegistry;
    }

    public void registerExceptionHandlerAdvice(MessagingAdviceBean bean, AbstractExceptionHandlerMethodResolver resolver) {
        this.exceptionHandlerAdviceCache.put(bean, resolver);
    }

    public InvocableHandlerMethod initMessageMappingMethod(HandlerMethod handlerMethod) {
        InvocableHandlerMethod invocable = new InvocableHandlerMethod(handlerMethod);
        invocable.setArgumentResolvers(this.argumentResolvers.getResolvers());
        return invocable;
    }

    @Nullable
    public InvocableHandlerMethod initExceptionHandlerMethod(HandlerMethod handlerMethod, Throwable ex) {
        Class<?> beanType;
        AbstractExceptionHandlerMethodResolver resolver;
        if (logger.isDebugEnabled()) {
            logger.debug((Object)("Searching for methods to handle " + ex.getClass().getSimpleName()));
        }
        if ((resolver = this.exceptionHandlerCache.get(beanType = handlerMethod.getBeanType())) == null) {
            resolver = this.exceptionMethodResolverFactory.apply(beanType);
            this.exceptionHandlerCache.put(beanType, resolver);
        }
        InvocableHandlerMethod exceptionHandlerMethod = null;
        Method method = resolver.resolveMethod(ex);
        if (method != null) {
            exceptionHandlerMethod = new InvocableHandlerMethod(handlerMethod.getBean(), method);
        } else {
            for (Map.Entry<MessagingAdviceBean, AbstractExceptionHandlerMethodResolver> entry : this.exceptionHandlerAdviceCache.entrySet()) {
                MessagingAdviceBean advice = entry.getKey();
                if (!advice.isApplicableToBeanType(beanType) || (method = (resolver = entry.getValue()).resolveMethod(ex)) == null) continue;
                exceptionHandlerMethod = new InvocableHandlerMethod(advice.resolveBean(), method);
                break;
            }
        }
        if (exceptionHandlerMethod != null) {
            logger.debug((Object)("Found exception handler " + exceptionHandlerMethod.getShortLogMessage()));
            exceptionHandlerMethod.setArgumentResolvers(this.argumentResolvers.getResolvers());
        } else {
            logger.error((Object)"No exception handling method", ex);
        }
        return exceptionHandlerMethod;
    }

    public Mono<Void> handleMessage(HandlerMethod handlerMethod, Message<?> message) {
        InvocableHandlerMethod invocable = this.initMessageMappingMethod(handlerMethod);
        if (logger.isDebugEnabled()) {
            logger.debug((Object)("Invoking " + invocable.getShortLogMessage()));
        }
        return invocable.invoke(message, new Object[0]).switchIfEmpty(Mono.defer(() -> this.handleReturnValue(null, invocable, message))).flatMap(returnValue -> this.handleReturnValue(returnValue, invocable, message)).onErrorResume(ex -> {
            InvocableHandlerMethod exHandler = this.initExceptionHandlerMethod(handlerMethod, (Throwable)ex);
            if (exHandler == null) {
                return Mono.error((Throwable)ex);
            }
            if (logger.isDebugEnabled()) {
                logger.debug((Object)("Invoking " + exHandler.getShortLogMessage()));
            }
            return exHandler.invoke(message, ex).switchIfEmpty(Mono.defer(() -> this.handleReturnValue(null, exHandler, message))).flatMap(returnValue -> this.handleReturnValue(returnValue, exHandler, message));
        });
    }

    private Mono<Void> handleReturnValue(@Nullable Object returnValue, HandlerMethod handlerMethod, Message<?> message) {
        MethodParameter returnType = handlerMethod.getReturnType();
        return this.returnValueHandlers.handleReturnValue(returnValue, returnType, message);
    }
}

