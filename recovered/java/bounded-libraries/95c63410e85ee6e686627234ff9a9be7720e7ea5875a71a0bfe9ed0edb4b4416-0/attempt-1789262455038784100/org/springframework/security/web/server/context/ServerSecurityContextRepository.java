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
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public interface ServerSecurityContextRepository {
    public Mono<Void> save(ServerWebExchange var1, SecurityContext var2);

    public Mono<SecurityContext> load(ServerWebExchange var1);
}

