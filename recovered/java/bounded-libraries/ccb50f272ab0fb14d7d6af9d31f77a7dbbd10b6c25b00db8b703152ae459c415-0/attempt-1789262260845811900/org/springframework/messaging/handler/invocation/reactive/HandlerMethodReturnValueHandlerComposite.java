/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.core.MethodParameter
 *  org.springframework.lang.Nullable
 *  reactor.core.publisher.Mono
 */
package org.springframework.messaging.handler.invocation.reactive;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.MethodParameter;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.invocation.reactive.HandlerMethodReturnValueHandler;
import reactor.core.publisher.Mono;

public class HandlerMethodReturnValueHandlerComposite
implements HandlerMethodReturnValueHandler {
    protected final Log logger = LogFactory.getLog(this.getClass());
    private final List<HandlerMethodReturnValueHandler> returnValueHandlers = new ArrayList<HandlerMethodReturnValueHandler>();

    public List<HandlerMethodReturnValueHandler> getReturnValueHandlers() {
        return Collections.unmodifiableList(this.returnValueHandlers);
    }

    public void clear() {
        this.returnValueHandlers.clear();
    }

    public HandlerMethodReturnValueHandlerComposite addHandler(HandlerMethodReturnValueHandler returnValueHandler) {
        this.returnValueHandlers.add(returnValueHandler);
        return this;
    }

    public HandlerMethodReturnValueHandlerComposite addHandlers(@Nullable List<? extends HandlerMethodReturnValueHandler> handlers) {
        if (handlers != null) {
            this.returnValueHandlers.addAll(handlers);
        }
        return this;
    }

    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        return this.getReturnValueHandler(returnType) != null;
    }

    @Override
    public Mono<Void> handleReturnValue(@Nullable Object returnValue, MethodParameter returnType, Message<?> message) {
        HandlerMethodReturnValueHandler handler = this.getReturnValueHandler(returnType);
        if (handler == null) {
            throw new IllegalStateException("No handler for return value type: " + returnType.getParameterType());
        }
        if (this.logger.isTraceEnabled()) {
            this.logger.trace((Object)("Processing return value with " + handler));
        }
        return handler.handleReturnValue(returnValue, returnType, message);
    }

    @Nullable
    private HandlerMethodReturnValueHandler getReturnValueHandler(MethodParameter returnType) {
        for (int i2 = 0; i2 < this.returnValueHandlers.size(); ++i2) {
            HandlerMethodReturnValueHandler handler = this.returnValueHandlers.get(i2);
            if (!handler.supportsReturnType(returnType)) continue;
            return handler;
        }
        return null;
    }
}

