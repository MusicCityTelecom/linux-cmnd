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
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public interface ServerRequestCache {
    public Mono<Void> saveRequest(ServerWebExchange var1);

    public Mono<URI> getRedirectUri(ServerWebExchange var1);

    public Mono<ServerHttpRequest> removeMatchingRequest(ServerWebExchange var1);
}

