/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.security.core.context.ReactiveSecurityContextHolder
 *  org.springframework.security.core.context.SecurityContext
 *  org.springframework.util.Assert
 *  org.springframework.web.server.ServerWebExchange
 *  org.springframework.web.server.WebFilter
 *  org.springframework.web.server.WebFilterChain
 *  reactor.core.publisher.Mono
 *  reactor.util.context.Context
 */
package org.springframework.security.web.server.context;

import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.util.Assert;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;

public class ReactorContextWebFilter
implements WebFilter {
    private final ServerSecurityContextRepository repository;

    public ReactorContextWebFilter(ServerSecurityContextRepository repository) {
        Assert.notNull((Object)repository, (String)"repository cannot be null");
        this.repository = repository;
    }

    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        return chain.filter(exchange).subscriberContext(context -> context.hasKey(SecurityContext.class) ? context : this.withSecurityContext((Context)context, exchange));
    }

    private Context withSecurityContext(Context mainContext, ServerWebExchange exchange) {
        return mainContext.putAll((Context)this.repository.load(exchange).as(ReactiveSecurityContextHolder::withSecurityContext));
    }
}

