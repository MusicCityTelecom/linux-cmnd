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

public class XFrameOptionsServerHttpHeadersWriter
implements ServerHttpHeadersWriter {
    public static final String X_FRAME_OPTIONS = "X-Frame-Options";
    private ServerHttpHeadersWriter delegate = XFrameOptionsServerHttpHeadersWriter.createDelegate(Mode.DENY);

    @Override
    public Mono<Void> writeHttpHeaders(ServerWebExchange exchange) {
        return this.delegate.writeHttpHeaders(exchange);
    }

    public void setMode(Mode mode) {
        this.delegate = XFrameOptionsServerHttpHeadersWriter.createDelegate(mode);
    }

    private static ServerHttpHeadersWriter createDelegate(Mode mode) {
        StaticServerHttpHeadersWriter.Builder builder = StaticServerHttpHeadersWriter.builder();
        builder.header(X_FRAME_OPTIONS, mode.name());
        return builder.build();
    }

    public static enum Mode {
        DENY,
        SAMEORIGIN;

    }
}

