/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.security.core.Authentication
 *  org.springframework.util.Assert
 *  reactor.core.publisher.Mono
 */
package org.springframework.security.web.server.authentication.logout;

import java.net.URI;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.DefaultServerRedirectStrategy;
import org.springframework.security.web.server.ServerRedirectStrategy;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.logout.ServerLogoutSuccessHandler;
import org.springframework.util.Assert;
import reactor.core.publisher.Mono;

public class RedirectServerLogoutSuccessHandler
implements ServerLogoutSuccessHandler {
    public static final String DEFAULT_LOGOUT_SUCCESS_URL = "/login?logout";
    private URI logoutSuccessUrl = URI.create("/login?logout");
    private ServerRedirectStrategy redirectStrategy = new DefaultServerRedirectStrategy();

    @Override
    public Mono<Void> onLogoutSuccess(WebFilterExchange exchange, Authentication authentication) {
        return this.redirectStrategy.sendRedirect(exchange.getExchange(), this.logoutSuccessUrl);
    }

    public void setLogoutSuccessUrl(URI logoutSuccessUrl) {
        Assert.notNull((Object)logoutSuccessUrl, (String)"logoutSuccessUrl cannot be null");
        this.logoutSuccessUrl = logoutSuccessUrl;
    }
}

