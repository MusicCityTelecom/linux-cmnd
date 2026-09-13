/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.MethodParameter
 *  org.springframework.lang.Nullable
 *  org.springframework.util.concurrent.ListenableFuture
 */
package org.springframework.messaging.handler.invocation;

import org.springframework.core.MethodParameter;
import org.springframework.lang.Nullable;
import org.springframework.messaging.handler.invocation.HandlerMethodReturnValueHandler;
import org.springframework.util.concurrent.ListenableFuture;

public interface AsyncHandlerMethodReturnValueHandler
extends HandlerMethodReturnValueHandler {
    public boolean isAsyncReturnValue(Object var1, MethodParameter var2);

    @Nullable
    public ListenableFuture<?> toListenableFuture(Object var1, MethodParameter var2);
}

