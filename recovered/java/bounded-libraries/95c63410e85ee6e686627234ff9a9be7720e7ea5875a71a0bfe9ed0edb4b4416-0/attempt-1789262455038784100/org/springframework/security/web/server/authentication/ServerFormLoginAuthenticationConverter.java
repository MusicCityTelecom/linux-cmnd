/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.security.core.Authentication
 *  org.springframework.web.server.ServerWebExchange
 *  reactor.core.publisher.Mono
 */
package org.springframework.security.web.server.authentication;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public class ServerFormLoginAuthenticationConverter
extends org.springframework.security.web.server.ServerFormLoginAuthenticationConverter
implements ServerAuthenticationConverter {
    @Override
    public Mono<Authentication> convert(ServerWebExchange exchange) {
        return this.apply(exchange);
    }
}

