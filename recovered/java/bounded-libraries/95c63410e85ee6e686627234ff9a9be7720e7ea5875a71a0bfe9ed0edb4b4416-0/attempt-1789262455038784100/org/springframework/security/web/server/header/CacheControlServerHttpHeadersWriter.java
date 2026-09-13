/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.http.HttpStatus
 *  org.springframework.web.server.ServerWebExchange
 *  reactor.core.publisher.Mono
 */
package org.springframework.security.web.server.header;

import org.springframework.http.HttpStatus;
import org.springframework.security.web.server.header.ServerHttpHeadersWriter;
import org.springframework.security.web.server.header.StaticServerHttpHeadersWriter;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public class CacheControlServerHttpHeadersWriter
implements ServerHttpHeadersWriter {
    public static final String EXPIRES_VALUE = "0";
    public static final String PRAGMA_VALUE = "no-cache";
    public static final String CACHE_CONTRTOL_VALUE = "no-cache, no-store, max-age=0, must-revalidate";
    private static final ServerHttpHeadersWriter CACHE_HEADERS = StaticServerHttpHeadersWriter.builder().header("Cache-Control", "no-cache, no-store, max-age=0, must-revalidate").header("Pragma", "no-cache").header("Expires", "0").build();

    @Override
    public Mono<Void> writeHttpHeaders(ServerWebExchange exchange) {
        if (exchange.getResponse().getStatusCode() == HttpStatus.NOT_MODIFIED) {
            return Mono.empty();
        }
        return CACHE_HEADERS.writeHttpHeaders(exchange);
    }
}

