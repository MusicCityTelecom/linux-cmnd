/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.core.log.LogMessage
 *  org.springframework.http.HttpStatus
 *  org.springframework.security.core.AuthenticationException
 *  org.springframework.util.Assert
 *  org.springframework.web.server.ServerWebExchange
 *  reactor.core.publisher.Flux
 *  reactor.core.publisher.Mono
 */
package org.springframework.security.web.server;

import java.util.Arrays;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.log.LogMessage;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher;
import org.springframework.util.Assert;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class DelegatingServerAuthenticationEntryPoint
implements ServerAuthenticationEntryPoint {
    private static final Log logger = LogFactory.getLog(DelegatingServerAuthenticationEntryPoint.class);
    private final List<DelegateEntry> entryPoints;
    private ServerAuthenticationEntryPoint defaultEntryPoint = (exchange, ex) -> {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
    };

    public DelegatingServerAuthenticationEntryPoint(DelegateEntry ... entryPoints) {
        this(Arrays.asList(entryPoints));
    }

    public DelegatingServerAuthenticationEntryPoint(List<DelegateEntry> entryPoints) {
        Assert.notEmpty(entryPoints, (String)"entryPoints cannot be null");
        this.entryPoints = entryPoints;
    }

    @Override
    public Mono<Void> commence(ServerWebExchange exchange, AuthenticationException ex) {
        return Flux.fromIterable(this.entryPoints).filterWhen(entry -> this.isMatch(exchange, (DelegateEntry)entry)).next().map(entry -> entry.getEntryPoint()).doOnNext(entryPoint -> logger.debug((Object)LogMessage.format((String)"Match found! Executing %s", (Object)entryPoint))).switchIfEmpty(Mono.just((Object)this.defaultEntryPoint).doOnNext(entryPoint -> logger.debug((Object)LogMessage.format((String)"No match found. Using default entry point %s", (Object)this.defaultEntryPoint)))).flatMap(entryPoint -> entryPoint.commence(exchange, ex));
    }

    private Mono<Boolean> isMatch(ServerWebExchange exchange, DelegateEntry entry) {
        ServerWebExchangeMatcher matcher = entry.getMatcher();
        logger.debug((Object)LogMessage.format((String)"Trying to match using %s", (Object)matcher));
        return matcher.matches(exchange).map(ServerWebExchangeMatcher.MatchResult::isMatch);
    }

    public void setDefaultEntryPoint(ServerAuthenticationEntryPoint defaultEntryPoint) {
        this.defaultEntryPoint = defaultEntryPoint;
    }

    public static class DelegateEntry {
        private final ServerWebExchangeMatcher matcher;
        private final ServerAuthenticationEntryPoint entryPoint;

        public DelegateEntry(ServerWebExchangeMatcher matcher, ServerAuthenticationEntryPoint entryPoint) {
            this.matcher = matcher;
            this.entryPoint = entryPoint;
        }

        public ServerWebExchangeMatcher getMatcher() {
            return this.matcher;
        }

        public ServerAuthenticationEntryPoint getEntryPoint() {
            return this.entryPoint;
        }
    }
}

