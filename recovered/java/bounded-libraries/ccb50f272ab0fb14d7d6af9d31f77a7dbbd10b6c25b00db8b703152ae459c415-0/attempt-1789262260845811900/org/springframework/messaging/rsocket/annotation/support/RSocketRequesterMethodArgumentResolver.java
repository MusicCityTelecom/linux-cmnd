/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.rsocket.RSocket
 *  org.springframework.core.MethodParameter
 *  org.springframework.util.Assert
 *  reactor.core.publisher.Mono
 */
package org.springframework.messaging.rsocket.annotation.support;

import io.rsocket.RSocket;
import org.springframework.core.MethodParameter;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.invocation.reactive.HandlerMethodArgumentResolver;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.util.Assert;
import reactor.core.publisher.Mono;

public class RSocketRequesterMethodArgumentResolver
implements HandlerMethodArgumentResolver {
    public static final String RSOCKET_REQUESTER_HEADER = "rsocketRequester";

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        Class type = parameter.getParameterType();
        return RSocketRequester.class.equals((Object)type) || RSocket.class.isAssignableFrom(type);
    }

    @Override
    public Mono<Object> resolveArgument(MethodParameter parameter, Message<?> message) {
        Object headerValue = message.getHeaders().get(RSOCKET_REQUESTER_HEADER);
        Assert.notNull((Object)headerValue, (String)"Missing 'rsocketRequester'");
        Assert.isInstanceOf(RSocketRequester.class, (Object)headerValue, (String)"Expected header value of type RSocketRequester");
        RSocketRequester requester = (RSocketRequester)headerValue;
        Class type = parameter.getParameterType();
        if (RSocketRequester.class.equals((Object)type)) {
            return Mono.just((Object)requester);
        }
        if (RSocket.class.isAssignableFrom(type)) {
            return Mono.justOrEmpty((Object)requester.rsocket());
        }
        return Mono.error((Throwable)new IllegalArgumentException("Unexpected parameter type: " + parameter));
    }
}

