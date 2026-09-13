/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.web.server.ServerWebExchange
 *  reactor.core.publisher.Mono
 */
package org.springframework.security.web.server.csrf;

import org.springframework.security.web.server.csrf.CsrfToken;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public interface ServerCsrfTokenRepository {
    public Mono<CsrfToken> generateToken(ServerWebExchange var1);

    public Mono<Void> saveToken(ServerWebExchange var1, CsrfToken var2);

    public Mono<CsrfToken> loadToken(ServerWebExchange var1);
}

