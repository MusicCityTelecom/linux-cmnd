/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.security.core.Authentication
 *  org.springframework.util.Assert
 *  reactor.core.publisher.Flux
 *  reactor.core.publisher.Mono
 */
package org.springframework.security.web.server.authentication;

import java.util.Arrays;
import java.util.List;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
import org.springframework.util.Assert;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class DelegatingServerAuthenticationSuccessHandler
implements ServerAuthenticationSuccessHandler {
    private final List<ServerAuthenticationSuccessHandler> delegates;

    public DelegatingServerAuthenticationSuccessHandler(ServerAuthenticationSuccessHandler ... delegates) {
        Assert.notEmpty((Object[])delegates, (String)"delegates cannot be null or empty");
        this.delegates = Arrays.asList(delegates);
    }

    @Override
    public Mono<Void> onAuthenticationSuccess(WebFilterExchange exchange, Authentication authentication) {
        return Flux.fromIterable(this.delegates).concatMap(delegate -> delegate.onAuthenticationSuccess(exchange, authentication)).then();
    }
}

