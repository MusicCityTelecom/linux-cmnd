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
package org.springframework.security.web.server;

import java.net.URI;
import org.springframework.security.web.server.DefaultServerRedirectStrategy;
import org.springframework.security.web.server.ServerRedirectStrategy;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher;
import org.springframework.util.Assert;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

public final class ExchangeMatcherRedirectWebFilter
implements WebFilter {
    private final ServerRedirectStrategy redirectStrategy = new DefaultServerRedirectStrategy();
    private final ServerWebExchangeMatcher exchangeMatcher;
    private final URI redirectUri;

    public ExchangeMatcherRedirectWebFilter(ServerWebExchangeMatcher exchangeMatcher, String redirectUrl) {
        Assert.notNull((Object)exchangeMatcher, (String)"exchangeMatcher cannot be null");
        Assert.hasText((String)redirectUrl, (String)"redirectUrl cannot be empty");
        this.exchangeMatcher = exchangeMatcher;
        this.redirectUri = URI.create(redirectUrl);
    }

    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        return this.exchangeMatcher.matches(exchange).filter(ServerWebExchangeMatcher.MatchResult::isMatch).switchIfEmpty(chain.filter(exchange).then(Mono.empty())).flatMap(result -> this.redirectStrategy.sendRedirect(exchange, this.redirectUri));
    }
}

