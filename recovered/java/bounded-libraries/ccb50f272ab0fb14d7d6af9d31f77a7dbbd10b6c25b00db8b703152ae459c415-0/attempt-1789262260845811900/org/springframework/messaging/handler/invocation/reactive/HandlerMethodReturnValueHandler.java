/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.MethodParameter
 *  org.springframework.lang.Nullable
 *  reactor.core.publisher.Mono
 */
package org.springframework.messaging.handler.invocation.reactive;

import org.springframework.core.MethodParameter;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import reactor.core.publisher.Mono;

public interface HandlerMethodReturnValueHandler {
    public static final String DATA_BUFFER_FACTORY_HEADER = "dataBufferFactory";

    public boolean supportsReturnType(MethodParameter var1);

    public Mono<Void> handleReturnValue(@Nullable Object var1, MethodParameter var2, Message<?> var3);
}

