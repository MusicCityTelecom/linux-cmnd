/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.util.Assert
 *  org.springframework.web.server.ServerWebExchange
 *  org.springframework.web.server.WebFilter
 *  reactor.core.publisher.Flux
 *  reactor.core.publisher.Mono
 */
package org.springframework.security.web.server;

import java.util.List;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher;
import org.springframework.util.Assert;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class MatcherSecurityWebFilterChain
implements SecurityWebFilterChain {
    private final ServerWebExchangeMatcher matcher;
    private final List<WebFilter> filters;

    public MatcherSecurityWebFilterChain(ServerWebExchangeMatcher matcher, List<WebFilter> filters) {
        Assert.notNull((Object)matcher, (String)"matcher cannot be null");
        Assert.notEmpty(filters, () -> "filters cannot be null or empty. Got " + filters);
        this.matcher = matcher;
        this.filters = filters;
    }

    @Override
    public Mono<Boolean> matches(ServerWebExchange exchange) {
        return this.matcher.matches(exchange).map(m -> m.isMatch());
    }

    @Override
    public Flux<WebFilter> getWebFilters() {
        return Flux.fromIterable(this.filters);
    }
}

