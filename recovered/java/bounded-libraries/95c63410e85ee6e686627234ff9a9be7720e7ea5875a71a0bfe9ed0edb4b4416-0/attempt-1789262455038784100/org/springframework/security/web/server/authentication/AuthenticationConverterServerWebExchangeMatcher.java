/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.util.Assert
 *  org.springframework.web.server.ServerWebExchange
 *  reactor.core.publisher.Mono
 */
package org.springframework.security.web.server.authentication;

import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher;
import org.springframework.util.Assert;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public final class AuthenticationConverterServerWebExchangeMatcher
implements ServerWebExchangeMatcher {
    private final ServerAuthenticationConverter serverAuthenticationConverter;

    public AuthenticationConverterServerWebExchangeMatcher(ServerAuthenticationConverter serverAuthenticationConverter) {
        Assert.notNull((Object)serverAuthenticationConverter, (String)"serverAuthenticationConverter cannot be null");
        this.serverAuthenticationConverter = serverAuthenticationConverter;
    }

    @Override
    public Mono<ServerWebExchangeMatcher.MatchResult> matches(ServerWebExchange exchange) {
        return this.serverAuthenticationConverter.convert(exchange).flatMap(a -> ServerWebExchangeMatcher.MatchResult.match()).onErrorResume(ex -> ServerWebExchangeMatcher.MatchResult.notMatch()).switchIfEmpty(ServerWebExchangeMatcher.MatchResult.notMatch());
    }
}

