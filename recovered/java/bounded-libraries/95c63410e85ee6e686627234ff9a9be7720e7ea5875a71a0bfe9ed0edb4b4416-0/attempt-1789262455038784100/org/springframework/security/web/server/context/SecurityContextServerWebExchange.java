/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.security.core.context.SecurityContext
 *  org.springframework.web.server.ServerWebExchange
 *  org.springframework.web.server.ServerWebExchangeDecorator
 *  reactor.core.publisher.Mono
 */
package org.springframework.security.web.server.context;

import java.security.Principal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.ServerWebExchangeDecorator;
import reactor.core.publisher.Mono;

public class SecurityContextServerWebExchange
extends ServerWebExchangeDecorator {
    private final Mono<SecurityContext> context;

    public SecurityContextServerWebExchange(ServerWebExchange delegate, Mono<SecurityContext> context) {
        super(delegate);
        this.context = context;
    }

    public <T extends Principal> Mono<T> getPrincipal() {
        return this.context.map(context -> context.getAuthentication());
    }
}

