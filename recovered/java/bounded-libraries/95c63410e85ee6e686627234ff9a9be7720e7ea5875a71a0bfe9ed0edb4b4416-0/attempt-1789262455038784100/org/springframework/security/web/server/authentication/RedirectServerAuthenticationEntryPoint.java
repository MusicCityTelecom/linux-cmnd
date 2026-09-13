/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.security.core.AuthenticationException
 *  org.springframework.util.Assert
 *  org.springframework.web.server.ServerWebExchange
 *  reactor.core.publisher.Mono
 */
package org.springframework.security.web.server.authentication;

import java.net.URI;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.DefaultServerRedirectStrategy;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.security.web.server.ServerRedirectStrategy;
import org.springframework.security.web.server.savedrequest.ServerRequestCache;
import org.springframework.security.web.server.savedrequest.WebSessionServerRequestCache;
import org.springframework.util.Assert;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public class RedirectServerAuthenticationEntryPoint
implements ServerAuthenticationEntryPoint {
    private final URI location;
    private ServerRedirectStrategy redirectStrategy = new DefaultServerRedirectStrategy();
    private ServerRequestCache requestCache = new WebSessionServerRequestCache();

    public RedirectServerAuthenticationEntryPoint(String location) {
        Assert.notNull((Object)location, (String)"location cannot be null");
        this.location = URI.create(location);
    }

    public void setRequestCache(ServerRequestCache requestCache) {
        Assert.notNull((Object)requestCache, (String)"requestCache cannot be null");
        this.requestCache = requestCache;
    }

    @Override
    public Mono<Void> commence(ServerWebExchange exchange, AuthenticationException ex) {
        return this.requestCache.saveRequest(exchange).then(this.redirectStrategy.sendRedirect(exchange, this.location));
    }

    public void setRedirectStrategy(ServerRedirectStrategy redirectStrategy) {
        Assert.notNull((Object)redirectStrategy, (String)"redirectStrategy cannot be null");
        this.redirectStrategy = redirectStrategy;
    }
}

