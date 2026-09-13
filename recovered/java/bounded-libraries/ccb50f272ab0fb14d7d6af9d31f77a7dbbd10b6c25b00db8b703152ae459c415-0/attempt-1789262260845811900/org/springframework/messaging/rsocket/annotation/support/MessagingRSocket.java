/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.rsocket.ConnectionSetupPayload
 *  io.rsocket.Payload
 *  io.rsocket.RSocket
 *  io.rsocket.frame.FrameType
 *  org.reactivestreams.Publisher
 *  org.springframework.core.io.buffer.DataBuffer
 *  org.springframework.core.io.buffer.DataBufferUtils
 *  org.springframework.core.io.buffer.NettyDataBuffer
 *  org.springframework.lang.Nullable
 *  org.springframework.util.Assert
 *  org.springframework.util.MimeType
 *  org.springframework.util.RouteMatcher
 *  org.springframework.util.RouteMatcher$Route
 *  reactor.core.publisher.Flux
 *  reactor.core.publisher.Mono
 */
package org.springframework.messaging.rsocket.annotation.support;

import io.rsocket.ConnectionSetupPayload;
import io.rsocket.Payload;
import io.rsocket.RSocket;
import io.rsocket.frame.FrameType;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.NettyDataBuffer;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.ReactiveMessageHandler;
import org.springframework.messaging.rsocket.MetadataExtractor;
import org.springframework.messaging.rsocket.PayloadUtils;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.messaging.rsocket.RSocketStrategies;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.util.Assert;
import org.springframework.util.MimeType;
import org.springframework.util.RouteMatcher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

class MessagingRSocket
implements RSocket {
    private final MimeType dataMimeType;
    private final MimeType metadataMimeType;
    private final MetadataExtractor metadataExtractor;
    private final ReactiveMessageHandler messageHandler;
    private final RouteMatcher routeMatcher;
    private final RSocketRequester requester;
    private final RSocketStrategies strategies;

    MessagingRSocket(MimeType dataMimeType, MimeType metadataMimeType, MetadataExtractor metadataExtractor, RSocketRequester requester, ReactiveMessageHandler messageHandler, RouteMatcher routeMatcher, RSocketStrategies strategies) {
        Assert.notNull((Object)dataMimeType, (String)"'dataMimeType' is required");
        Assert.notNull((Object)metadataMimeType, (String)"'metadataMimeType' is required");
        Assert.notNull((Object)metadataExtractor, (String)"MetadataExtractor is required");
        Assert.notNull((Object)requester, (String)"RSocketRequester is required");
        Assert.notNull((Object)messageHandler, (String)"ReactiveMessageHandler is required");
        Assert.notNull((Object)routeMatcher, (String)"RouteMatcher is required");
        Assert.notNull((Object)strategies, (String)"RSocketStrategies is required");
        this.dataMimeType = dataMimeType;
        this.metadataMimeType = metadataMimeType;
        this.metadataExtractor = metadataExtractor;
        this.requester = requester;
        this.messageHandler = messageHandler;
        this.routeMatcher = routeMatcher;
        this.strategies = strategies;
    }

    public Mono<Void> handleConnectionSetupPayload(ConnectionSetupPayload payload) {
        payload.retain();
        return this.handle((Payload)payload, FrameType.SETUP);
    }

    public Mono<Void> fireAndForget(Payload payload) {
        return this.handle(payload, FrameType.REQUEST_FNF);
    }

    public Mono<Payload> requestResponse(Payload payload) {
        return this.handleAndReply(payload, FrameType.REQUEST_RESPONSE, (Flux<Payload>)Flux.just((Object)payload)).next();
    }

    public Flux<Payload> requestStream(Payload payload) {
        return this.handleAndReply(payload, FrameType.REQUEST_STREAM, (Flux<Payload>)Flux.just((Object)payload));
    }

    public Flux<Payload> requestChannel(Publisher<Payload> payloads) {
        return Flux.from(payloads).switchOnFirst((signal, innerFlux) -> {
            Payload firstPayload = (Payload)signal.get();
            return firstPayload == null ? innerFlux : this.handleAndReply(firstPayload, FrameType.REQUEST_CHANNEL, (Flux<Payload>)innerFlux);
        });
    }

    public Mono<Void> metadataPush(Payload payload) {
        return this.handle(payload, FrameType.METADATA_PUSH);
    }

    private Mono<Void> handle(Payload payload, FrameType frameType) {
        MessageHeaders headers = this.createHeaders(payload, frameType, null);
        DataBuffer dataBuffer = this.retainDataAndReleasePayload(payload);
        int refCount = this.refCount(dataBuffer);
        Message<DataBuffer> message = MessageBuilder.createMessage(dataBuffer, headers);
        return Mono.defer(() -> this.messageHandler.handleMessage(message)).doFinally(s -> {
            if (this.refCount(dataBuffer) == refCount) {
                DataBufferUtils.release((DataBuffer)dataBuffer);
            }
        });
    }

    private int refCount(DataBuffer dataBuffer) {
        return dataBuffer instanceof NettyDataBuffer ? ((NettyDataBuffer)dataBuffer).getNativeBuffer().refCnt() : 1;
    }

    private Flux<Payload> handleAndReply(Payload firstPayload, FrameType frameType, Flux<Payload> payloads) {
        AtomicReference<Flux<Payload>> responseRef = new AtomicReference<Flux<Payload>>();
        MessageHeaders headers = this.createHeaders(firstPayload, frameType, responseRef);
        AtomicBoolean read = new AtomicBoolean();
        Flux buffers = payloads.map(this::retainDataAndReleasePayload).doOnSubscribe(s -> read.set(true));
        Message<Flux> message = MessageBuilder.createMessage(buffers, headers);
        return Mono.defer(() -> this.messageHandler.handleMessage(message)).doFinally(s -> {
            if (!read.get()) {
                firstPayload.release();
            }
        }).thenMany((Publisher)Flux.defer(() -> responseRef.get() != null ? (Publisher)responseRef.get() : Mono.error((Throwable)new IllegalStateException("Expected response"))));
    }

    private DataBuffer retainDataAndReleasePayload(Payload payload) {
        return PayloadUtils.retainDataAndReleasePayload(payload, this.strategies.dataBufferFactory());
    }

    private MessageHeaders createHeaders(Payload payload, FrameType frameType, @Nullable AtomicReference<Flux<Payload>> responseRef) {
        MessageHeaderAccessor headers = new MessageHeaderAccessor();
        headers.setLeaveMutable(true);
        Map<String, Object> metadataValues = this.metadataExtractor.extract(payload, this.metadataMimeType);
        metadataValues.putIfAbsent("route", "");
        for (Map.Entry<String, Object> entry : metadataValues.entrySet()) {
            if (entry.getKey().equals("route")) {
                RouteMatcher.Route route = this.routeMatcher.parseRoute((String)entry.getValue());
                headers.setHeader("lookupDestination", route);
                continue;
            }
            headers.setHeader(entry.getKey(), entry.getValue());
        }
        headers.setContentType(this.dataMimeType);
        headers.setHeader("rsocketFrameType", frameType);
        headers.setHeader("rsocketRequester", this.requester);
        if (responseRef != null) {
            headers.setHeader("rsocketResponse", responseRef);
        }
        headers.setHeader("dataBufferFactory", this.strategies.dataBufferFactory());
        return headers.getMessageHeaders();
    }
}

