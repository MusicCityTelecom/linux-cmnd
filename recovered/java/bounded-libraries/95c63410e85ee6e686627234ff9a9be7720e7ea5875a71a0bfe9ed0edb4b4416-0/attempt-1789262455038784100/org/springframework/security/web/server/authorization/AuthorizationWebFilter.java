/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.core.log.LogMessage
 *  org.springframework.security.access.AccessDeniedException
 *  org.springframework.security.authorization.ReactiveAuthorizationManager
 *  org.springframework.security.core.context.ReactiveSecurityContextHolder
 *  org.springframework.security.core.context.SecurityContext
 *  org.springframework.web.server.ServerWebExchange
 *  org.springframework.web.server.WebFilter
 *  org.springframework.web.server.WebFilterChain
 *  reactor.core.publisher.Mono
 */
package org.springframework.security.web.server.authorization;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.log.LogMessage;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

public class AuthorizationWebFilter
implements WebFilter {
    private static final Log logger = LogFactory.getLog(AuthorizationWebFilter.class);
    private ReactiveAuthorizationManager<? super ServerWebExchange> authorizationManager;

    public AuthorizationWebFilter(ReactiveAuthorizationManager<? super ServerWebExchange> authorizationManager) {
        this.authorizationManager = authorizationManager;
    }

    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        return ((Mono)ReactiveSecurityContextHolder.getContext().filter(c -> c.getAuthentication() != null).map(SecurityContext::getAuthentication).as(authentication -> this.authorizationManager.verify(authentication, (Object)exchange))).doOnSuccess(it -> logger.debug((Object)"Authorization successful")).doOnError(AccessDeniedException.class, ex -> logger.debug((Object)LogMessage.format((String)"Authorization failed: %s", (Object)ex.getMessage()))).switchIfEmpty(chain.filter(exchange));
    }
}

