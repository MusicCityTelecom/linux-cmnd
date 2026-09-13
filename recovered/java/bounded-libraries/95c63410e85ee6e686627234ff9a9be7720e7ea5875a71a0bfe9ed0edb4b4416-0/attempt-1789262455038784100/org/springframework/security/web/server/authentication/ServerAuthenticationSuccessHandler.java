/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.security.core.Authentication
 *  reactor.core.publisher.Mono
 */
package org.springframework.security.web.server.authentication;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.WebFilterExchange;
import reactor.core.publisher.Mono;

public interface ServerAuthenticationSuccessHandler {
    public Mono<Void> onAuthenticationSuccess(WebFilterExchange var1, Authentication var2);
}

