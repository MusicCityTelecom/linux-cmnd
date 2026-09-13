/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.http.HttpStatus
 *  org.springframework.security.core.Authentication
 *  org.springframework.util.Assert
 *  reactor.core.publisher.Mono
 */
package org.springframework.security.web.server.authentication.logout;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.logout.ServerLogoutSuccessHandler;
import org.springframework.util.Assert;
import reactor.core.publisher.Mono;

public class HttpStatusReturningServerLogoutSuccessHandler
implements ServerLogoutSuccessHandler {
    private final HttpStatus httpStatusToReturn;

    public HttpStatusReturningServerLogoutSuccessHandler(HttpStatus httpStatusToReturn) {
        Assert.notNull((Object)httpStatusToReturn, (String)"The provided HttpStatus must not be null.");
        this.httpStatusToReturn = httpStatusToReturn;
    }

    public HttpStatusReturningServerLogoutSuccessHandler() {
        this.httpStatusToReturn = HttpStatus.OK;
    }

    @Override
    public Mono<Void> onLogoutSuccess(WebFilterExchange exchange, Authentication authentication) {
        return Mono.fromRunnable(() -> exchange.getExchange().getResponse().setStatusCode(this.httpStatusToReturn));
    }
}

