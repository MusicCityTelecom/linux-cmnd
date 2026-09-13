/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.web.server.ServerWebExchange
 *  org.springframework.web.server.WebFilter
 *  org.springframework.web.server.WebFilterChain
 *  org.springframework.web.server.handler.DefaultWebFilterChain
 *  org.springframework.web.server.handler.FilteringWebHandler
 *  reactor.core.publisher.Flux
 *  reactor.core.publisher.Mono
 */
package org.springframework.security.web.server;

import java.util.Arrays;
import java.util.List;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import org.springframework.web.server.handler.DefaultWebFilterChain;
import org.springframework.web.server.handler.FilteringWebHandler;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class WebFilterChainProxy
implements WebFilter {
    private final List<SecurityWebFilterChain> filters;

    public WebFilterChainProxy(List<SecurityWebFilterChain> filters) {
        this.filters = filters;
    }

    public WebFilterChainProxy(SecurityWebFilterChain ... filters) {
        this.filters = Arrays.asList(filters);
    }

    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        return Flux.fromIterable(this.filters).filterWhen(securityWebFilterChain -> securityWebFilterChain.matches(exchange)).next().switchIfEmpty(chain.filter(exchange).then(Mono.empty())).flatMap(securityWebFilterChain -> securityWebFilterChain.getWebFilters().collectList()).map(filters -> new FilteringWebHandler(arg_0 -> ((WebFilterChain)chain).filter(arg_0), filters)).map(x$0 -> new DefaultWebFilterChain(x$0, new WebFilter[0])).flatMap(securedChain -> securedChain.filter(exchange));
    }
}

