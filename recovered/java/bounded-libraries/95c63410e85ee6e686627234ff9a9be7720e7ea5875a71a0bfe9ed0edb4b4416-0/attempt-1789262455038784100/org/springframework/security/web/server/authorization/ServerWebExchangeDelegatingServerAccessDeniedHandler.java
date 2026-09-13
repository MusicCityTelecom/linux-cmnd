/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.http.HttpStatus
 *  org.springframework.security.access.AccessDeniedException
 *  org.springframework.util.Assert
 *  org.springframework.web.server.ServerWebExchange
 *  reactor.core.publisher.Flux
 *  reactor.core.publisher.Mono
 */
package org.springframework.security.web.server.authorization;

import java.util.Arrays;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher;
import org.springframework.util.Assert;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class ServerWebExchangeDelegatingServerAccessDeniedHandler
implements ServerAccessDeniedHandler {
    private final List<DelegateEntry> handlers;
    private ServerAccessDeniedHandler defaultHandler = (exchange, ex) -> {
        exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
        return exchange.getResponse().setComplete();
    };

    public ServerWebExchangeDelegatingServerAccessDeniedHandler(DelegateEntry ... handlers) {
        this(Arrays.asList(handlers));
    }

    public ServerWebExchangeDelegatingServerAccessDeniedHandler(List<DelegateEntry> handlers) {
        Assert.notEmpty(handlers, (String)"handlers cannot be null");
        this.handlers = handlers;
    }

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, AccessDeniedException denied) {
        return Flux.fromIterable(this.handlers).filterWhen(entry -> this.isMatch(exchange, (DelegateEntry)entry)).next().map(DelegateEntry::getAccessDeniedHandler).defaultIfEmpty((Object)this.defaultHandler).flatMap(handler -> handler.handle(exchange, denied));
    }

    public void setDefaultAccessDeniedHandler(ServerAccessDeniedHandler accessDeniedHandler) {
        Assert.notNull((Object)accessDeniedHandler, (String)"accessDeniedHandler cannot be null");
        this.defaultHandler = accessDeniedHandler;
    }

    private Mono<Boolean> isMatch(ServerWebExchange exchange, DelegateEntry entry) {
        ServerWebExchangeMatcher matcher = entry.getMatcher();
        return matcher.matches(exchange).map(ServerWebExchangeMatcher.MatchResult::isMatch);
    }

    public static class DelegateEntry {
        private final ServerWebExchangeMatcher matcher;
        private final ServerAccessDeniedHandler accessDeniedHandler;

        public DelegateEntry(ServerWebExchangeMatcher matcher, ServerAccessDeniedHandler accessDeniedHandler) {
            this.matcher = matcher;
            this.accessDeniedHandler = accessDeniedHandler;
        }

        public ServerWebExchangeMatcher getMatcher() {
            return this.matcher;
        }

        public ServerAccessDeniedHandler getAccessDeniedHandler() {
            return this.accessDeniedHandler;
        }
    }
}

