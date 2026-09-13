/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.Ordered
 *  org.springframework.web.server.ServerWebExchange
 *  org.springframework.web.server.WebFilter
 *  org.springframework.web.server.WebFilterChain
 *  org.springframework.web.server.WebSession
 *  reactor.core.publisher.Mono
 */
package org.springframework.boot.actuate.web.trace.reactive;

import java.security.Principal;
import java.util.Set;
import org.springframework.boot.actuate.trace.http.HttpExchangeTracer;
import org.springframework.boot.actuate.trace.http.HttpTrace;
import org.springframework.boot.actuate.trace.http.HttpTraceRepository;
import org.springframework.boot.actuate.trace.http.Include;
import org.springframework.boot.actuate.web.trace.reactive.ServerWebExchangeTraceableRequest;
import org.springframework.boot.actuate.web.trace.reactive.TraceableServerHttpResponse;
import org.springframework.core.Ordered;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import org.springframework.web.server.WebSession;
import reactor.core.publisher.Mono;

public class HttpTraceWebFilter
implements WebFilter,
Ordered {
    private static final Object NONE = new Object();
    private int order = 0x7FFFFFF5;
    private final HttpTraceRepository repository;
    private final HttpExchangeTracer tracer;
    private final Set<Include> includes;

    public HttpTraceWebFilter(HttpTraceRepository repository, HttpExchangeTracer tracer, Set<Include> includes) {
        this.repository = repository;
        this.tracer = tracer;
        this.includes = includes;
    }

    public int getOrder() {
        return this.order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        Mono principal = this.includes.contains((Object)Include.PRINCIPAL) ? exchange.getPrincipal().cast(Object.class).defaultIfEmpty(NONE) : Mono.just((Object)NONE);
        Mono session = this.includes.contains((Object)Include.SESSION_ID) ? exchange.getSession() : Mono.just((Object)NONE);
        return Mono.zip((Mono)principal, (Mono)session).flatMap(tuple -> this.filter(exchange, chain, this.asType(tuple.getT1(), Principal.class), this.asType(tuple.getT2(), WebSession.class)));
    }

    private <T> T asType(Object object, Class<T> type) {
        if (type.isInstance(object)) {
            return type.cast(object);
        }
        return null;
    }

    private Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain, Principal principal, WebSession session) {
        ServerWebExchangeTraceableRequest request = new ServerWebExchangeTraceableRequest(exchange);
        HttpTrace trace = this.tracer.receivedRequest(request);
        exchange.getResponse().beforeCommit(() -> {
            TraceableServerHttpResponse response = new TraceableServerHttpResponse(exchange.getResponse());
            this.tracer.sendingResponse(trace, response, () -> principal, () -> this.getStartedSessionId(session));
            this.repository.add(trace);
            return Mono.empty();
        });
        return chain.filter(exchange);
    }

    private String getStartedSessionId(WebSession session) {
        return session != null && session.isStarted() ? session.getId() : null;
    }
}

