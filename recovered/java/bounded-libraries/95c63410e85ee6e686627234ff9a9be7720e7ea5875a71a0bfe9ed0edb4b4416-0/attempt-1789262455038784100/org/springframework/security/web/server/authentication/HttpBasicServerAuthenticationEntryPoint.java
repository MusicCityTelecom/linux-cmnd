/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.http.HttpStatus
 *  org.springframework.http.server.reactive.ServerHttpResponse
 *  org.springframework.security.core.AuthenticationException
 *  org.springframework.util.Assert
 *  org.springframework.web.server.ServerWebExchange
 *  reactor.core.publisher.Mono
 */
package org.springframework.security.web.server.authentication;

import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.util.Assert;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public class HttpBasicServerAuthenticationEntryPoint
implements ServerAuthenticationEntryPoint {
    private static final String WWW_AUTHENTICATE = "WWW-Authenticate";
    private static final String DEFAULT_REALM = "Realm";
    private static String WWW_AUTHENTICATE_FORMAT = "Basic realm=\"%s\"";
    private String headerValue = HttpBasicServerAuthenticationEntryPoint.createHeaderValue("Realm");

    @Override
    public Mono<Void> commence(ServerWebExchange exchange, AuthenticationException ex) {
        return Mono.fromRunnable(() -> {
            ServerHttpResponse response = exchange.getResponse();
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            response.getHeaders().set(WWW_AUTHENTICATE, this.headerValue);
        });
    }

    public void setRealm(String realm) {
        this.headerValue = HttpBasicServerAuthenticationEntryPoint.createHeaderValue(realm);
    }

    private static String createHeaderValue(String realm) {
        Assert.notNull((Object)realm, (String)"realm cannot be null");
        return String.format(WWW_AUTHENTICATE_FORMAT, realm);
    }
}

