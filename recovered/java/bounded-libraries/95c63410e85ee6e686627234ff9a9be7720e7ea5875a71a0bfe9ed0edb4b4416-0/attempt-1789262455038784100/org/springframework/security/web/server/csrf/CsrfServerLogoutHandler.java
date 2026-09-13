/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.security.core.Authentication
 *  org.springframework.util.Assert
 *  reactor.core.publisher.Mono
 */
package org.springframework.security.web.server.csrf;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.logout.ServerLogoutHandler;
import org.springframework.security.web.server.csrf.ServerCsrfTokenRepository;
import org.springframework.util.Assert;
import reactor.core.publisher.Mono;

public class CsrfServerLogoutHandler
implements ServerLogoutHandler {
    private final ServerCsrfTokenRepository csrfTokenRepository;

    public CsrfServerLogoutHandler(ServerCsrfTokenRepository csrfTokenRepository) {
        Assert.notNull((Object)csrfTokenRepository, (String)"csrfTokenRepository cannot be null");
        this.csrfTokenRepository = csrfTokenRepository;
    }

    @Override
    public Mono<Void> logout(WebFilterExchange exchange, Authentication authentication) {
        return this.csrfTokenRepository.saveToken(exchange.getExchange(), null);
    }
}

