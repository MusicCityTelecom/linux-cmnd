/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.security.core.Authentication
 *  org.springframework.util.Assert
 *  reactor.core.publisher.Flux
 *  reactor.core.publisher.Mono
 */
package org.springframework.security.web.server.authentication.logout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.logout.ServerLogoutHandler;
import org.springframework.util.Assert;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class DelegatingServerLogoutHandler
implements ServerLogoutHandler {
    private final List<ServerLogoutHandler> delegates = new ArrayList<ServerLogoutHandler>();

    public DelegatingServerLogoutHandler(ServerLogoutHandler ... delegates) {
        Assert.notEmpty((Object[])delegates, (String)"delegates cannot be null or empty");
        this.delegates.addAll(Arrays.asList(delegates));
    }

    public DelegatingServerLogoutHandler(Collection<ServerLogoutHandler> delegates) {
        Assert.notEmpty(delegates, (String)"delegates cannot be null or empty");
        this.delegates.addAll(delegates);
    }

    @Override
    public Mono<Void> logout(WebFilterExchange exchange, Authentication authentication) {
        return Flux.fromIterable(this.delegates).concatMap(delegate -> delegate.logout(exchange, authentication)).then();
    }
}

