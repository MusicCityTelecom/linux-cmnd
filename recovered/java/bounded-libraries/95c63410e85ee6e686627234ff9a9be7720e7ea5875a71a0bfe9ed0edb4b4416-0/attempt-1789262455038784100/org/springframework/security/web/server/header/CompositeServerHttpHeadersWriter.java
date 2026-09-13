/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.web.server.ServerWebExchange
 *  reactor.core.publisher.Flux
 *  reactor.core.publisher.Mono
 */
package org.springframework.security.web.server.header;

import java.util.Arrays;
import java.util.List;
import org.springframework.security.web.server.header.ServerHttpHeadersWriter;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class CompositeServerHttpHeadersWriter
implements ServerHttpHeadersWriter {
    private final List<ServerHttpHeadersWriter> writers;

    public CompositeServerHttpHeadersWriter(ServerHttpHeadersWriter ... writers) {
        this(Arrays.asList(writers));
    }

    public CompositeServerHttpHeadersWriter(List<ServerHttpHeadersWriter> writers) {
        this.writers = writers;
    }

    @Override
    public Mono<Void> writeHttpHeaders(ServerWebExchange exchange) {
        return Flux.fromIterable(this.writers).concatMap(w -> w.writeHttpHeaders(exchange)).then();
    }
}

