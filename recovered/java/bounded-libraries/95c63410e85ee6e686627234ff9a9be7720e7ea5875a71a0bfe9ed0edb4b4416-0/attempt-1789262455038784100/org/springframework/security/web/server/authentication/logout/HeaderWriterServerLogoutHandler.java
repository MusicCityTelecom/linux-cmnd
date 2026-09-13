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
import org.springframework.security.web.server.header.ServerHttpHeadersWriter;
import org.springframework.util.Assert;
import reactor.core.publisher.Mono;

public final class HeaderWriterServerLogoutHandler
implements ServerLogoutHandler {
    private final ServerHttpHeadersWriter headersWriter;

    public HeaderWriterServerLogoutHandler(ServerHttpHeadersWriter headersWriter) {
        Assert.notNull((Object)headersWriter, (String)"headersWriter cannot be null");
        this.headersWriter = headersWriter;
    }

    @Override
    public Mono<Void> logout(WebFilterExchange exchange, Authentication authentication) {
        return this.headersWriter.writeHttpHeaders(exchange.getExchange());
    }
}

