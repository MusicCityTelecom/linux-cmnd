/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.MethodParameter
 *  org.springframework.util.concurrent.ListenableFuture
 */
package org.springframework.messaging.handler.invocation;

import org.springframework.core.MethodParameter;
import org.springframework.messaging.handler.invocation.AbstractAsyncReturnValueHandler;
import org.springframework.util.concurrent.ListenableFuture;

public class ListenableFutureReturnValueHandler
extends AbstractAsyncReturnValueHandler {
    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        return ListenableFuture.class.isAssignableFrom(returnType.getParameterType());
    }

    @Override
    public ListenableFuture<?> toListenableFuture(Object returnValue, MethodParameter returnType) {
        return (ListenableFuture)returnValue;
    }
}

