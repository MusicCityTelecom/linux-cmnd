/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.core.MethodParameter
 *  org.springframework.lang.Nullable
 *  org.springframework.util.concurrent.ListenableFuture
 */
package org.springframework.messaging.handler.invocation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.MethodParameter;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.invocation.AsyncHandlerMethodReturnValueHandler;
import org.springframework.messaging.handler.invocation.HandlerMethodReturnValueHandler;
import org.springframework.util.concurrent.ListenableFuture;

public class HandlerMethodReturnValueHandlerComposite
implements AsyncHandlerMethodReturnValueHandler {
    public static final Log defaultLogger = LogFactory.getLog(HandlerMethodReturnValueHandlerComposite.class);
    private Log logger = defaultLogger;
    private final List<HandlerMethodReturnValueHandler> returnValueHandlers = new ArrayList<HandlerMethodReturnValueHandler>();

    public void setLogger(Log logger) {
        this.logger = logger;
    }

    public Log getLogger() {
        return this.logger;
    }

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

    @Nullable
    private HandlerMethodReturnValueHandler getReturnValueHandler(MethodParameter returnType) {
        for (int i2 = 0; i2 < this.returnValueHandlers.size(); ++i2) {
            HandlerMethodReturnValueHandler handler = this.returnValueHandlers.get(i2);
            if (!handler.supportsReturnType(returnType)) continue;
            return handler;
        }
        return null;
    }

    @Override
    public void handleReturnValue(@Nullable Object returnValue, MethodParameter returnType, Message<?> message) throws Exception {
        HandlerMethodReturnValueHandler handler = this.getReturnValueHandler(returnType);
        if (handler == null) {
            throw new IllegalStateException("No handler for return value type: " + returnType.getParameterType());
        }
        if (this.logger.isTraceEnabled()) {
            this.logger.trace((Object)("Processing return value with " + handler));
        }
        handler.handleReturnValue(returnValue, returnType, message);
    }

    @Override
    public boolean isAsyncReturnValue(Object returnValue, MethodParameter returnType) {
        HandlerMethodReturnValueHandler handler = this.getReturnValueHandler(returnType);
        return handler instanceof AsyncHandlerMethodReturnValueHandler && ((AsyncHandlerMethodReturnValueHandler)handler).isAsyncReturnValue(returnValue, returnType);
    }

    @Override
    @Nullable
    public ListenableFuture<?> toListenableFuture(Object returnValue, MethodParameter returnType) {
        HandlerMethodReturnValueHandler handler = this.getReturnValueHandler(returnType);
        if (handler instanceof AsyncHandlerMethodReturnValueHandler) {
            return ((AsyncHandlerMethodReturnValueHandler)handler).toListenableFuture(returnValue, returnType);
        }
        return null;
    }
}

