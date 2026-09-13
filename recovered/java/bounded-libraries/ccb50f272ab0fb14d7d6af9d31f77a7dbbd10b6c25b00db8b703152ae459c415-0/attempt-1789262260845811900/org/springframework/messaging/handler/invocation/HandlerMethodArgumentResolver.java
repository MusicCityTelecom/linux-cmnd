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

public interface HandlerMethodArgumentResolver {
    public boolean supportsParameter(MethodParameter var1);

    @Nullable
    public Object resolveArgument(MethodParameter var1, Message<?> var2) throws Exception;
}

