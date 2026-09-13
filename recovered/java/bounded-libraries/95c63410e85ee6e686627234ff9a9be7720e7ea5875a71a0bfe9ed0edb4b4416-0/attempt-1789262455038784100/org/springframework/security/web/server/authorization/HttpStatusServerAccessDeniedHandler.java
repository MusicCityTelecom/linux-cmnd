/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Publisher
 *  org.springframework.core.io.buffer.DataBuffer
 *  org.springframework.core.io.buffer.DataBufferFactory
 *  org.springframework.core.io.buffer.DataBufferUtils
 *  org.springframework.http.HttpStatus
 *  org.springframework.http.MediaType
 *  org.springframework.security.access.AccessDeniedException
 *  org.springframework.util.Assert
 *  org.springframework.web.server.ServerWebExchange
 *  reactor.core.publisher.Mono
 */
package org.springframework.security.web.server.authorization;

import java.nio.charset.Charset;
import org.reactivestreams.Publisher;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.util.Assert;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public class HttpStatusServerAccessDeniedHandler
implements ServerAccessDeniedHandler {
    private final HttpStatus httpStatus;

    public HttpStatusServerAccessDeniedHandler(HttpStatus httpStatus) {
        Assert.notNull((Object)httpStatus, (String)"httpStatus cannot be null");
        this.httpStatus = httpStatus;
    }

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, AccessDeniedException ex) {
        return Mono.defer(() -> Mono.just((Object)exchange.getResponse())).flatMap(response -> {
            response.setStatusCode(this.httpStatus);
            response.getHeaders().setContentType(MediaType.TEXT_PLAIN);
            DataBufferFactory dataBufferFactory = response.bufferFactory();
            DataBuffer buffer = dataBufferFactory.wrap(ex.getMessage().getBytes(Charset.defaultCharset()));
            return response.writeWith((Publisher)Mono.just((Object)buffer)).doOnError(error -> DataBufferUtils.release((DataBuffer)buffer));
        });
    }
}

