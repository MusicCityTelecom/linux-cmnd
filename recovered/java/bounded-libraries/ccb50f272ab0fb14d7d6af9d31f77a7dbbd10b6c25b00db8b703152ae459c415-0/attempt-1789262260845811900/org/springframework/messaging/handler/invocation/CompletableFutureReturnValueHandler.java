/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.MethodParameter
 *  org.springframework.util.concurrent.CompletableToListenableFutureAdapter
 *  org.springframework.util.concurrent.ListenableFuture
 */
package org.springframework.messaging.handler.invocation;

import java.util.concurrent.CompletionStage;
import org.springframework.core.MethodParameter;
import org.springframework.messaging.handler.invocation.AbstractAsyncReturnValueHandler;
import org.springframework.util.concurrent.CompletableToListenableFutureAdapter;
import org.springframework.util.concurrent.ListenableFuture;

public class CompletableFutureReturnValueHandler
extends AbstractAsyncReturnValueHandler {
    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        return CompletionStage.class.isAssignableFrom(returnType.getParameterType());
    }

    @Override
    public ListenableFuture<?> toListenableFuture(Object returnValue, MethodParameter returnType) {
        return new CompletableToListenableFutureAdapter((CompletionStage)returnValue);
    }
}

