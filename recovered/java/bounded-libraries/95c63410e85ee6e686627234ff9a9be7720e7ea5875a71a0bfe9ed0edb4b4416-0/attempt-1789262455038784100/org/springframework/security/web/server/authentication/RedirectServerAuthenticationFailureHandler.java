/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.security.core.AuthenticationException
 *  org.springframework.util.Assert
 *  reactor.core.publisher.Mono
 */
package org.springframework.security.web.server.authentication;

import java.net.URI;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.DefaultServerRedirectStrategy;
import org.springframework.security.web.server.ServerRedirectStrategy;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationFailureHandler;
import org.springframework.util.Assert;
import reactor.core.publisher.Mono;

public class RedirectServerAuthenticationFailureHandler
implements ServerAuthenticationFailureHandler {
    private final URI location;
    private ServerRedirectStrategy redirectStrategy = new DefaultServerRedirectStrategy();

    public RedirectServerAuthenticationFailureHandler(String location) {
        Assert.notNull((Object)location, (String)"location cannot be null");
        this.location = URI.create(location);
    }

    public void setRedirectStrategy(ServerRedirectStrategy redirectStrategy) {
        Assert.notNull((Object)redirectStrategy, (String)"redirectStrategy cannot be null");
        this.redirectStrategy = redirectStrategy;
    }

    @Override
    public Mono<Void> onAuthenticationFailure(WebFilterExchange webFilterExchange, AuthenticationException exception) {
        return this.redirectStrategy.sendRedirect(webFilterExchange.getExchange(), this.location);
    }
}

