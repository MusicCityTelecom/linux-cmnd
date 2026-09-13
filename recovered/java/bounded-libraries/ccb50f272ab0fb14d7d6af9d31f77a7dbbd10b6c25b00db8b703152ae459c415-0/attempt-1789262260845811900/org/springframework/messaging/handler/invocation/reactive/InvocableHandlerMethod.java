/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Publisher
 *  org.springframework.core.CoroutinesUtils
 *  org.springframework.core.DefaultParameterNameDiscoverer
 *  org.springframework.core.KotlinDetector
 *  org.springframework.core.MethodParameter
 *  org.springframework.core.ParameterNameDiscoverer
 *  org.springframework.core.ReactiveAdapter
 *  org.springframework.core.ReactiveAdapterRegistry
 *  org.springframework.lang.Nullable
 *  org.springframework.util.ObjectUtils
 *  reactor.core.publisher.Mono
 */
package org.springframework.messaging.handler.invocation.reactive;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import org.reactivestreams.Publisher;
import org.springframework.core.CoroutinesUtils;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.KotlinDetector;
import org.springframework.core.MethodParameter;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.core.ReactiveAdapter;
import org.springframework.core.ReactiveAdapterRegistry;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.HandlerMethod;
import org.springframework.messaging.handler.invocation.MethodArgumentResolutionException;
import org.springframework.messaging.handler.invocation.reactive.HandlerMethodArgumentResolver;
import org.springframework.messaging.handler.invocation.reactive.HandlerMethodArgumentResolverComposite;
import org.springframework.util.ObjectUtils;
import reactor.core.publisher.Mono;

public class InvocableHandlerMethod
extends HandlerMethod {
    private static final Mono<Object[]> EMPTY_ARGS = Mono.just((Object)new Object[0]);
    private static final Object NO_ARG_VALUE = new Object();
    private HandlerMethodArgumentResolverComposite resolvers = new HandlerMethodArgumentResolverComposite();
    private ParameterNameDiscoverer parameterNameDiscoverer = new DefaultParameterNameDiscoverer();
    private ReactiveAdapterRegistry reactiveAdapterRegistry = ReactiveAdapterRegistry.getSharedInstance();

    public InvocableHandlerMethod(HandlerMethod handlerMethod) {
        super(handlerMethod);
    }

    public InvocableHandlerMethod(Object bean, Method method) {
        super(bean, method);
    }

    public void setArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        this.resolvers.addResolvers(resolvers);
    }

    public List<HandlerMethodArgumentResolver> getResolvers() {
        return this.resolvers.getResolvers();
    }

    public void setParameterNameDiscoverer(ParameterNameDiscoverer nameDiscoverer) {
        this.parameterNameDiscoverer = nameDiscoverer;
    }

    public ParameterNameDiscoverer getParameterNameDiscoverer() {
        return this.parameterNameDiscoverer;
    }

    public void setReactiveAdapterRegistry(ReactiveAdapterRegistry registry) {
        this.reactiveAdapterRegistry = registry;
    }

    public Mono<Object> invoke(Message<?> message, Object ... providedArgs) {
        return this.getMethodArgumentValues(message, providedArgs).flatMap(args -> {
            Object value;
            boolean isSuspendingFunction = false;
            try {
                Method method = this.getBridgedMethod();
                if (KotlinDetector.isSuspendingFunction((Method)method)) {
                    isSuspendingFunction = true;
                    value = CoroutinesUtils.invokeSuspendingFunction((Method)method, (Object)this.getBean(), (Object[])args);
                } else {
                    value = method.invoke(this.getBean(), args);
                }
            }
            catch (IllegalArgumentException ex) {
                this.assertTargetBean(this.getBridgedMethod(), this.getBean(), (Object[])args);
                String text = ex.getMessage() != null ? ex.getMessage() : "Illegal argument";
                return Mono.error((Throwable)new IllegalStateException(this.formatInvokeError(text, (Object[])args), ex));
            }
            catch (InvocationTargetException ex) {
                return Mono.error((Throwable)ex.getTargetException());
            }
            catch (Throwable ex) {
                return Mono.error((Throwable)new IllegalStateException(this.formatInvokeError("Invocation failure", (Object[])args), ex));
            }
            MethodParameter returnType = this.getReturnType();
            Class reactiveType = isSuspendingFunction ? value.getClass() : returnType.getParameterType();
            ReactiveAdapter adapter = this.reactiveAdapterRegistry.getAdapter(reactiveType);
            return this.isAsyncVoidReturnType(returnType, adapter) ? Mono.from((Publisher)adapter.toPublisher(value)) : Mono.justOrEmpty((Object)value);
        });
    }

    private Mono<Object[]> getMethodArgumentValues(Message<?> message, Object ... providedArgs) {
        MethodParameter[] parameters = this.getMethodParameters();
        if (ObjectUtils.isEmpty((Object[])this.getMethodParameters())) {
            return EMPTY_ARGS;
        }
        ArrayList<Mono> argMonos = new ArrayList<Mono>(parameters.length);
        for (MethodParameter parameter : parameters) {
            parameter.initParameterNameDiscovery(this.parameterNameDiscoverer);
            Object providedArg = InvocableHandlerMethod.findProvidedArgument(parameter, providedArgs);
            if (providedArg != null) {
                argMonos.add(Mono.just((Object)providedArg));
                continue;
            }
            if (!this.resolvers.supportsParameter(parameter)) {
                return Mono.error((Throwable)((Object)new MethodArgumentResolutionException(message, parameter, InvocableHandlerMethod.formatArgumentError(parameter, "No suitable resolver"))));
            }
            try {
                argMonos.add(this.resolvers.resolveArgument(parameter, message).defaultIfEmpty(NO_ARG_VALUE).doOnError(ex -> this.logArgumentErrorIfNecessary(parameter, (Throwable)ex)));
            }
            catch (Exception ex2) {
                this.logArgumentErrorIfNecessary(parameter, ex2);
                argMonos.add(Mono.error((Throwable)ex2));
            }
        }
        return Mono.zip(argMonos, values -> Stream.of(values).map(value -> value != NO_ARG_VALUE ? value : null).toArray());
    }

    private void logArgumentErrorIfNecessary(MethodParameter parameter, Throwable ex) {
        String exMsg = ex.getMessage();
        if (exMsg != null && !exMsg.contains(parameter.getExecutable().toGenericString()) && this.logger.isDebugEnabled()) {
            this.logger.debug((Object)InvocableHandlerMethod.formatArgumentError(parameter, exMsg));
        }
    }

    private boolean isAsyncVoidReturnType(MethodParameter returnType, @Nullable ReactiveAdapter reactiveAdapter) {
        ParameterizedType type;
        if (reactiveAdapter != null && reactiveAdapter.supportsEmpty() && reactiveAdapter.isNoValue()) {
            return true;
        }
        Type parameterType = returnType.getGenericParameterType();
        if (parameterType instanceof ParameterizedType && (type = (ParameterizedType)parameterType).getActualTypeArguments().length == 1) {
            return Void.class.equals((Object)type.getActualTypeArguments()[0]);
        }
        Method method = returnType.getMethod();
        return method != null && KotlinDetector.isSuspendingFunction((Method)method) && Void.TYPE.equals(returnType.getParameterType());
    }
}

