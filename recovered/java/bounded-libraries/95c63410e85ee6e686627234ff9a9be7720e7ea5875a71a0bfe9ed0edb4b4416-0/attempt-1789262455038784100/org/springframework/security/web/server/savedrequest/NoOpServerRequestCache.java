/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.http.server.reactive.ServerHttpRequest
 *  org.springframework.web.server.ServerWebExchange
 *  reactor.core.publisher.Mono
 */
package org.springframework.security.web.server.savedrequest;

import java.net.URI;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.web.server.savedrequest.ServerRequestCache;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public final class NoOpServerRequestCache
implements ServerRequestCache {
    private NoOpServerRequestCache() {
    }

    @Override
    public Mono<Void> saveRequest(ServerWebExchange exchange) {
        return Mono.empty();
    }

    @Override
    public Mono<URI> getRedirectUri(ServerWebExchange exchange) {
        return Mono.empty();
    }

    @Override
    public Mono<ServerHttpRequest> removeMatchingRequest(ServerWebExchange exchange) {
        return Mono.empty();
    }

    public static NoOpServerRequestCache getInstance() {
        return new NoOpServerRequestCache();
    }
}

