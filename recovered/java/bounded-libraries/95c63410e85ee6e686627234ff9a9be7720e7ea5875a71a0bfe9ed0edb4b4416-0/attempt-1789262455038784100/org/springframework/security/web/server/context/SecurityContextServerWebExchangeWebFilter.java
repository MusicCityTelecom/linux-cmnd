/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.security.core.context.ReactiveSecurityContextHolder
 *  org.springframework.security.core.context.SecurityContext
 *  org.springframework.web.server.ServerWebExchange
 *  org.springframework.web.server.WebFilter
 *  org.springframework.web.server.WebFilterChain
 *  reactor.core.publisher.Mono
 */
package org.springframework.security.web.server.context;

import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.server.context.SecurityContextServerWebExchange;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

public class SecurityContextServerWebExchangeWebFilter
implements WebFilter {
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        return chain.filter((ServerWebExchange)new SecurityContextServerWebExchange(exchange, (Mono<SecurityContext>)ReactiveSecurityContextHolder.getContext()));
    }
}

