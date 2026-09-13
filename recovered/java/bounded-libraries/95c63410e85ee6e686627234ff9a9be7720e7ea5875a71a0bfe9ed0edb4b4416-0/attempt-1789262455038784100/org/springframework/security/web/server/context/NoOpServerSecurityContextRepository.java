/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.security.core.context.SecurityContext
 *  org.springframework.web.server.ServerWebExchange
 *  reactor.core.publisher.Mono
 */
package org.springframework.security.web.server.context;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public final class NoOpServerSecurityContextRepository
implements ServerSecurityContextRepository {
    private static final NoOpServerSecurityContextRepository INSTANCE = new NoOpServerSecurityContextRepository();

    private NoOpServerSecurityContextRepository() {
    }

    @Override
    public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
        return Mono.empty();
    }

    @Override
    public Mono<SecurityContext> load(ServerWebExchange exchange) {
        return Mono.empty();
    }

    public static NoOpServerSecurityContextRepository getInstance() {
        return INSTANCE;
    }
}

