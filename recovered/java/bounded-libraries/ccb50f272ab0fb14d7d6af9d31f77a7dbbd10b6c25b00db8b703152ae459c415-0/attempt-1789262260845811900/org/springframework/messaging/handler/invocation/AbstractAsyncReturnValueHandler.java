/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.MethodParameter
 *  org.springframework.lang.Nullable
 */
package org.springframework.messaging.handler.invocation;

import org.springframework.core.MethodParameter;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.invocation.AsyncHandlerMethodReturnValueHandler;

public abstract class AbstractAsyncReturnValueHandler
implements AsyncHandlerMethodReturnValueHandler {
    @Override
    public boolean isAsyncReturnValue(Object returnValue, MethodParameter returnType) {
        return true;
    }

    @Override
    public void handleReturnValue(@Nullable Object returnValue, MethodParameter returnType, Message<?> message) {
        throw new IllegalStateException("Unexpected invocation");
    }
}

