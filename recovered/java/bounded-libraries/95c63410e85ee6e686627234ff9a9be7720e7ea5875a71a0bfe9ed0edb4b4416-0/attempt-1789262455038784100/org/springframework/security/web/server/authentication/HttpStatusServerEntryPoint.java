/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.http.HttpStatus
 *  org.springframework.security.core.AuthenticationException
 *  org.springframework.util.Assert
 *  org.springframework.web.server.ServerWebExchange
 *  reactor.core.publisher.Mono
 */
package org.springframework.security.web.server.authentication;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.util.Assert;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public class HttpStatusServerEntryPoint
implements ServerAuthenticationEntryPoint {
    private final HttpStatus httpStatus;

    public HttpStatusServerEntryPoint(HttpStatus httpStatus) {
        Assert.notNull((Object)httpStatus, (String)"httpStatus cannot be null");
        this.httpStatus = httpStatus;
    }

    @Override
    public Mono<Void> commence(ServerWebExchange exchange, AuthenticationException authException) {
        return Mono.fromRunnable(() -> exchange.getResponse().setStatusCode(this.httpStatus));
    }
}

