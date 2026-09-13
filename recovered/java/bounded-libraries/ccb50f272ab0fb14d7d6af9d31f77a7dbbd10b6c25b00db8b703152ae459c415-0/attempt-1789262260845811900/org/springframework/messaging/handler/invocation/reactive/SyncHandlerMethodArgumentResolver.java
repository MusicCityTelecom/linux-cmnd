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
import org.springframework.messaging.handler.invocation.reactive.HandlerMethodArgumentResolver;
import reactor.core.publisher.Mono;

public interface SyncHandlerMethodArgumentResolver
extends HandlerMethodArgumentResolver {
    @Override
    default public Mono<Object> resolveArgument(MethodParameter parameter, Message<?> message) {
        return Mono.justOrEmpty((Object)this.resolveArgumentValue(parameter, message));
    }

    @Nullable
    public Object resolveArgumentValue(MethodParameter var1, Message<?> var2);
}

