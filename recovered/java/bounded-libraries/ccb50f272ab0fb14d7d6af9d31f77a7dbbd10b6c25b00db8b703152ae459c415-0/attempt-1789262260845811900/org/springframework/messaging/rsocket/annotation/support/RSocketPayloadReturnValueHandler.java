/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.rsocket.Payload
 *  org.springframework.core.MethodParameter
 *  org.springframework.core.ReactiveAdapterRegistry
 *  org.springframework.core.codec.Encoder
 *  org.springframework.core.io.buffer.DataBuffer
 *  org.springframework.lang.Nullable
 *  org.springframework.util.Assert
 *  reactor.core.publisher.Flux
 *  reactor.core.publisher.Mono
 */
package org.springframework.messaging.rsocket.annotation.support;

import io.rsocket.Payload;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import org.springframework.core.MethodParameter;
import org.springframework.core.ReactiveAdapterRegistry;
import org.springframework.core.codec.Encoder;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.invocation.reactive.AbstractEncoderMethodReturnValueHandler;
import org.springframework.messaging.rsocket.PayloadUtils;
import org.springframework.util.Assert;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class RSocketPayloadReturnValueHandler
extends AbstractEncoderMethodReturnValueHandler {
    public static final String RESPONSE_HEADER = "rsocketResponse";

    public RSocketPayloadReturnValueHandler(List<Encoder<?>> encoders, ReactiveAdapterRegistry registry) {
        super(encoders, registry);
    }

    @Override
    protected Mono<Void> handleEncodedContent(Flux<DataBuffer> encodedContent, MethodParameter returnType, Message<?> message) {
        AtomicReference<Flux<Payload>> responseRef = this.getResponseReference(message);
        Assert.notNull(responseRef, (String)"Missing 'rsocketResponse'");
        responseRef.set((Flux<Payload>)encodedContent.map(PayloadUtils::createPayload));
        return Mono.empty();
    }

    @Override
    protected Mono<Void> handleNoContent(MethodParameter returnType, Message<?> message) {
        AtomicReference<Flux<Payload>> responseRef = this.getResponseReference(message);
        if (responseRef != null) {
            responseRef.set((Flux<Payload>)Flux.empty());
        }
        return Mono.empty();
    }

    @Nullable
    private AtomicReference<Flux<Payload>> getResponseReference(Message<?> message) {
        Object headerValue = message.getHeaders().get(RESPONSE_HEADER);
        Assert.state((headerValue == null || headerValue instanceof AtomicReference ? 1 : 0) != 0, (String)"Expected AtomicReference");
        return (AtomicReference)headerValue;
    }
}

