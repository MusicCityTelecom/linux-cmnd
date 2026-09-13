/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.security.core.Authentication
 *  org.springframework.util.Assert
 *  org.springframework.web.server.ServerWebExchange
 *  reactor.core.publisher.Mono
 */
package org.springframework.security.web.server.authentication;

import java.net.URI;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.DefaultServerRedirectStrategy;
import org.springframework.security.web.server.ServerRedirectStrategy;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
import org.springframework.security.web.server.savedrequest.ServerRequestCache;
import org.springframework.security.web.server.savedrequest.WebSessionServerRequestCache;
import org.springframework.util.Assert;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public class RedirectServerAuthenticationSuccessHandler
implements ServerAuthenticationSuccessHandler {
    private URI location = URI.create("/");
    private ServerRedirectStrategy redirectStrategy = new DefaultServerRedirectStrategy();
    private ServerRequestCache requestCache = new WebSessionServerRequestCache();

    public RedirectServerAuthenticationSuccessHandler() {
    }

    public RedirectServerAuthenticationSuccessHandler(String location) {
        this.location = URI.create(location);
    }

    public void setRequestCache(ServerRequestCache requestCache) {
        Assert.notNull((Object)requestCache, (String)"requestCache cannot be null");
        this.requestCache = requestCache;
    }

    @Override
    public Mono<Void> onAuthenticationSuccess(WebFilterExchange webFilterExchange, Authentication authentication) {
        ServerWebExchange exchange = webFilterExchange.getExchange();
        return this.requestCache.getRedirectUri(exchange).defaultIfEmpty((Object)this.location).flatMap(location -> this.redirectStrategy.sendRedirect(exchange, (URI)location));
    }

    public void setLocation(URI location) {
        Assert.notNull((Object)location, (String)"location cannot be null");
        this.location = location;
    }

    public void setRedirectStrategy(ServerRedirectStrategy redirectStrategy) {
        Assert.notNull((Object)redirectStrategy, (String)"redirectStrategy cannot be null");
        this.redirectStrategy = redirectStrategy;
    }
}

