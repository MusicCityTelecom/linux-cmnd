/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.security.core.Authentication
 *  reactor.core.publisher.Mono
 */
package org.springframework.security.web.server.authentication.logout;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.WebFilterExchange;
import reactor.core.publisher.Mono;

public interface ServerLogoutHandler {
    public Mono<Void> logout(WebFilterExchange var1, Authentication var2);
}

