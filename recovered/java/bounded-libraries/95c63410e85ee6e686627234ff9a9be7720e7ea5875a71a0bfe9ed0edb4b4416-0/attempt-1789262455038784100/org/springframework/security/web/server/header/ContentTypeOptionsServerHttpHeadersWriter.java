/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.web.server.ServerWebExchange
 *  reactor.core.publisher.Mono
 */
package org.springframework.security.web.server.header;

import org.springframework.security.web.server.header.ServerHttpHeadersWriter;
import org.springframework.security.web.server.header.StaticServerHttpHeadersWriter;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public class ContentTypeOptionsServerHttpHeadersWriter
implements ServerHttpHeadersWriter {
    public static final String X_CONTENT_OPTIONS = "X-Content-Type-Options";
    public static final String NOSNIFF = "nosniff";
    private static final ServerHttpHeadersWriter CONTENT_TYPE_HEADERS = StaticServerHttpHeadersWriter.builder().header("X-Content-Type-Options", "nosniff").build();

    @Override
    public Mono<Void> writeHttpHeaders(ServerWebExchange exchange) {
        return CONTENT_TYPE_HEADERS.writeHttpHeaders(exchange);
    }
}

