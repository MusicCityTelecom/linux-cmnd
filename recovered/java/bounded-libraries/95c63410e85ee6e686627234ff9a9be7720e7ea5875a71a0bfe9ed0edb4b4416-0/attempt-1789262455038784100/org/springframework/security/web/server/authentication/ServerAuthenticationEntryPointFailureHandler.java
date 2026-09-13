/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.security.core.AuthenticationException
 *  org.springframework.util.Assert
 *  reactor.core.publisher.Mono
 */
package org.springframework.security.web.server.authentication;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationFailureHandler;
import org.springframework.util.Assert;
import reactor.core.publisher.Mono;

public class ServerAuthenticationEntryPointFailureHandler
implements ServerAuthenticationFailureHandler {
    private final ServerAuthenticationEntryPoint authenticationEntryPoint;

    public ServerAuthenticationEntryPointFailureHandler(ServerAuthenticationEntryPoint authenticationEntryPoint) {
        Assert.notNull((Object)authenticationEntryPoint, (String)"authenticationEntryPoint cannot be null");
        this.authenticationEntryPoint = authenticationEntryPoint;
    }

    @Override
    public Mono<Void> onAuthenticationFailure(WebFilterExchange webFilterExchange, AuthenticationException exception) {
        return this.authenticationEntryPoint.commence(webFilterExchange.getExchange(), exception);
    }
}

