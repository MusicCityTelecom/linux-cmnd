/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.util.Assert
 *  org.springframework.web.server.ServerWebExchange
 *  org.springframework.web.server.WebFilter
 *  org.springframework.web.server.WebFilterChain
 *  reactor.core.publisher.Mono
 */
package org.springframework.security.web.server.savedrequest;

import org.springframework.security.web.server.savedrequest.ServerRequestCache;
import org.springframework.security.web.server.savedrequest.WebSessionServerRequestCache;
import org.springframework.util.Assert;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

public class ServerRequestCacheWebFilter
implements WebFilter {
    private ServerRequestCache requestCache = new WebSessionServerRequestCache();

    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        return this.requestCache.removeMatchingRequest(exchange).map(r -> exchange.mutate().request(r).build()).defaultIfEmpty((Object)exchange).flatMap(e -> chain.filter(e));
    }

    public void setRequestCache(ServerRequestCache requestCache) {
        Assert.notNull((Object)requestCache, (String)"requestCache cannot be null");
        this.requestCache = requestCache;
    }
}

