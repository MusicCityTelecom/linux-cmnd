/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.web.server.ServerWebExchange
 *  org.springframework.web.server.WebFilter
 *  org.springframework.web.server.WebFilterChain
 *  reactor.core.publisher.Mono
 */
package org.springframework.security.web.server.header;

import org.springframework.security.web.server.header.ServerHttpHeadersWriter;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

public class HttpHeaderWriterWebFilter
implements WebFilter {
    private final ServerHttpHeadersWriter writer;

    public HttpHeaderWriterWebFilter(ServerHttpHeadersWriter writer) {
        this.writer = writer;
    }

    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        exchange.getResponse().beforeCommit(() -> this.writer.writeHttpHeaders(exchange));
        return chain.filter(exchange);
    }
}

