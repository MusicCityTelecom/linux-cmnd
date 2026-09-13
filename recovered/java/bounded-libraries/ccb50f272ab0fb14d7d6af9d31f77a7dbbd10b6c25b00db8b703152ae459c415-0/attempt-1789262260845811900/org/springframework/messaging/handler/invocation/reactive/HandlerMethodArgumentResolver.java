/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.MethodParameter
 *  reactor.core.publisher.Mono
 */
package org.springframework.messaging.handler.invocation.reactive;

import org.springframework.core.MethodParameter;
import org.springframework.messaging.Message;
import reactor.core.publisher.Mono;

public interface HandlerMethodArgumentResolver {
    public boolean supportsParameter(MethodParameter var1);

    public Mono<Object> resolveArgument(MethodParameter var1, Message<?> var2);
}

