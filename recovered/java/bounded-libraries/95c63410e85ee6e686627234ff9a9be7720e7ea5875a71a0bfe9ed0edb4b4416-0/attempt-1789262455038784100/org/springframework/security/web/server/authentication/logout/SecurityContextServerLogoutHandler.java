/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.security.core.Authentication
 *  org.springframework.util.Assert
 *  reactor.core.publisher.Mono
 */
package org.springframework.security.web.server.authentication.logout;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.logout.ServerLogoutHandler;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.security.web.server.context.WebSessionServerSecurityContextRepository;
import org.springframework.util.Assert;
import reactor.core.publisher.Mono;

public class SecurityContextServerLogoutHandler
implements ServerLogoutHandler {
    private ServerSecurityContextRepository securityContextRepository = new WebSessionServerSecurityContextRepository();

    @Override
    public Mono<Void> logout(WebFilterExchange exchange, Authentication authentication) {
        return this.securityContextRepository.save(exchange.getExchange(), null);
    }

    public void setSecurityContextRepository(ServerSecurityContextRepository securityContextRepository) {
        Assert.notNull((Object)securityContextRepository, (String)"securityContextRepository cannot be null");
        this.securityContextRepository = securityContextRepository;
    }
}

