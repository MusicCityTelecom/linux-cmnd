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

public interface HandlerMethodReturnValueHandler {
    public boolean supportsReturnType(MethodParameter var1);

    public void handleReturnValue(@Nullable Object var1, MethodParameter var2, Message<?> var3) throws Exception;
}

