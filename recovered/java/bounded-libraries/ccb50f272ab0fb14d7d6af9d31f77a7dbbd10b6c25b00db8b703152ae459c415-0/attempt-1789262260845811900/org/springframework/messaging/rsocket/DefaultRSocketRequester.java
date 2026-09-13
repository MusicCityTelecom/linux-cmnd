/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.netty.util.ReferenceCounted
 *  io.rsocket.Payload
 *  io.rsocket.RSocket
 *  io.rsocket.core.RSocketClient
 *  org.reactivestreams.Publisher
 *  org.springframework.core.ParameterizedTypeReference
 *  org.springframework.core.ReactiveAdapter
 *  org.springframework.core.ResolvableType
 *  org.springframework.core.codec.Decoder
 *  org.springframework.core.codec.Encoder
 *  org.springframework.core.io.buffer.DataBuffer
 *  org.springframework.core.io.buffer.DataBufferFactory
 *  org.springframework.core.io.buffer.DataBufferUtils
 *  org.springframework.lang.Nullable
 *  org.springframework.util.Assert
 *  org.springframework.util.MimeType
 *  reactor.core.publisher.Flux
 *  reactor.core.publisher.Mono
 */
package org.springframework.messaging.rsocket;

import io.netty.util.ReferenceCounted;
import io.rsocket.Payload;
import io.rsocket.RSocket;
import io.rsocket.core.RSocketClient;
import java.util.Collections;
import java.util.Map;
import java.util.function.Consumer;
import org.reactivestreams.Publisher;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.ReactiveAdapter;
import org.springframework.core.ResolvableType;
import org.springframework.core.codec.Decoder;
import org.springframework.core.codec.Encoder;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.lang.Nullable;
import org.springframework.messaging.rsocket.MetadataEncoder;
import org.springframework.messaging.rsocket.PayloadUtils;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.messaging.rsocket.RSocketStrategies;
import org.springframework.util.Assert;
import org.springframework.util.MimeType;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

final class DefaultRSocketRequester
implements RSocketRequester {
    private static final Map<String, Object> EMPTY_HINTS = Collections.emptyMap();
    private final RSocketClient rsocketClient;
    @Nullable
    private final RSocket rsocket;
    private final MimeType dataMimeType;
    private final MimeType metadataMimeType;
    private final RSocketStrategies strategies;
    private final Mono<DataBuffer> emptyBufferMono;

    DefaultRSocketRequester(@Nullable RSocketClient rsocketClient, @Nullable RSocket rsocket, MimeType dataMimeType, MimeType metadataMimeType, RSocketStrategies strategies) {
        Assert.isTrue((rsocketClient != null || rsocket != null ? 1 : 0) != 0, (String)"RSocketClient or RSocket is required");
        Assert.notNull((Object)dataMimeType, (String)"'dataMimeType' is required");
        Assert.notNull((Object)metadataMimeType, (String)"'metadataMimeType' is required");
        Assert.notNull((Object)strategies, (String)"RSocketStrategies is required");
        this.rsocketClient = rsocketClient != null ? rsocketClient : RSocketClient.from((RSocket)rsocket);
        this.rsocket = rsocket;
        this.dataMimeType = dataMimeType;
        this.metadataMimeType = metadataMimeType;
        this.strategies = strategies;
        this.emptyBufferMono = Mono.just((Object)this.strategies.dataBufferFactory().wrap(new byte[0]));
    }

    @Override
    public RSocketClient rsocketClient() {
        return this.rsocketClient;
    }

    @Override
    @Nullable
    public RSocket rsocket() {
        return this.rsocket;
    }

    @Override
    public MimeType dataMimeType() {
        return this.dataMimeType;
    }

    @Override
    public MimeType metadataMimeType() {
        return this.metadataMimeType;
    }

    @Override
    public RSocketRequester.RequestSpec route(String route, Object ... vars) {
        return new DefaultRequestSpec(route, vars);
    }

    @Override
    public RSocketRequester.RequestSpec metadata(Object metadata, @Nullable MimeType mimeType) {
        return new DefaultRequestSpec(metadata, mimeType);
    }

    private static boolean isVoid(ResolvableType elementType) {
        return Void.class.equals((Object)elementType.resolve()) || Void.TYPE.equals(elementType.resolve());
    }

    private DataBufferFactory bufferFactory() {
        return this.strategies.dataBufferFactory();
    }

    private class DefaultRequestSpec
    implements RSocketRequester.RequestSpec {
        private final MetadataEncoder metadataEncoder;
        @Nullable
        private Mono<Payload> payloadMono;
        @Nullable
        private Flux<Payload> payloadFlux;

        public DefaultRequestSpec(String route, Object ... vars) {
            this.metadataEncoder = new MetadataEncoder(DefaultRSocketRequester.this.metadataMimeType(), DefaultRSocketRequester.this.strategies);
            this.metadataEncoder.route(route, vars);
        }

        public DefaultRequestSpec(@Nullable Object metadata, MimeType mimeType) {
            this.metadataEncoder = new MetadataEncoder(DefaultRSocketRequester.this.metadataMimeType(), DefaultRSocketRequester.this.strategies);
            this.metadataEncoder.metadata(metadata, mimeType);
        }

        @Override
        public RSocketRequester.RequestSpec metadata(Object metadata, MimeType mimeType) {
            this.metadataEncoder.metadata(metadata, mimeType);
            return this;
        }

        @Override
        public RSocketRequester.RequestSpec metadata(Consumer<RSocketRequester.MetadataSpec<?>> configurer) {
            configurer.accept(this);
            return this;
        }

        @Override
        public RSocketRequester.RequestSpec data(Object data) {
            Assert.notNull((Object)data, (String)"'data' must not be null");
            this.createPayload(data, ResolvableType.NONE);
            return this;
        }

        @Override
        public RSocketRequester.RequestSpec data(Object producer, Class<?> elementClass) {
            Assert.notNull((Object)producer, (String)"'producer' must not be null");
            Assert.notNull(elementClass, (String)"'elementClass' must not be null");
            ReactiveAdapter adapter = this.getAdapter(producer.getClass());
            Assert.notNull((Object)adapter, (String)"'producer' type is unknown to ReactiveAdapterRegistry");
            this.createPayload(adapter.toPublisher(producer), ResolvableType.forClass(elementClass));
            return this;
        }

        @Nullable
        private ReactiveAdapter getAdapter(Class<?> aClass) {
            return DefaultRSocketRequester.this.strategies.reactiveAdapterRegistry().getAdapter(aClass);
        }

        @Override
        public RSocketRequester.RequestSpec data(Object producer, ParameterizedTypeReference<?> elementTypeRef) {
            Assert.notNull((Object)producer, (String)"'producer' must not be null");
            Assert.notNull(elementTypeRef, (String)"'elementTypeRef' must not be null");
            ReactiveAdapter adapter = this.getAdapter(producer.getClass());
            Assert.notNull((Object)adapter, (String)"'producer' type is unknown to ReactiveAdapterRegistry");
            this.createPayload(adapter.toPublisher(producer), ResolvableType.forType(elementTypeRef));
            return this;
        }

        private void createPayload(Object input, ResolvableType elementType) {
            Encoder encoder;
            Publisher publisher;
            ReactiveAdapter adapter = this.getAdapter(input.getClass());
            if (input instanceof Publisher) {
                publisher = (Publisher)input;
            } else if (adapter != null) {
                publisher = adapter.toPublisher(input);
            } else {
                ResolvableType type = ResolvableType.forInstance((Object)input);
                this.payloadMono = this.firstPayload((Mono<DataBuffer>)Mono.fromCallable(() -> this.encodeData(input, type, null)));
                this.payloadFlux = null;
                return;
            }
            if (DefaultRSocketRequester.isVoid(elementType) || adapter != null && adapter.isNoValue()) {
                this.payloadMono = Mono.when((Publisher[])new Publisher[]{publisher}).then(this.firstPayload((Mono<DataBuffer>)DefaultRSocketRequester.this.emptyBufferMono));
                this.payloadFlux = null;
                return;
            }
            Encoder encoder2 = encoder = elementType != ResolvableType.NONE && !Object.class.equals((Object)elementType.resolve()) ? DefaultRSocketRequester.this.strategies.encoder(elementType, DefaultRSocketRequester.this.dataMimeType) : null;
            if (adapter != null && !adapter.isMultiValue()) {
                Mono data = Mono.from((Publisher)publisher).map(value -> this.encodeData(value, elementType, encoder)).switchIfEmpty(DefaultRSocketRequester.this.emptyBufferMono);
                this.payloadMono = this.firstPayload((Mono<DataBuffer>)data);
                this.payloadFlux = null;
                return;
            }
            this.payloadMono = null;
            this.payloadFlux = Flux.from((Publisher)publisher).map(value -> this.encodeData(value, elementType, encoder)).switchIfEmpty((Publisher)DefaultRSocketRequester.this.emptyBufferMono).switchOnFirst((signal, inner) -> {
                DataBuffer data = (DataBuffer)signal.get();
                if (data != null) {
                    return this.firstPayload((Mono<DataBuffer>)Mono.fromCallable(() -> data)).concatWith((Publisher)inner.skip(1L).map(PayloadUtils::createPayload));
                }
                return inner.map(PayloadUtils::createPayload);
            }).doOnDiscard(Payload.class, ReferenceCounted::release);
        }

        private <T> DataBuffer encodeData(T value, ResolvableType elementType, @Nullable Encoder<?> encoder) {
            if (encoder == null) {
                elementType = ResolvableType.forInstance(value);
                encoder = DefaultRSocketRequester.this.strategies.encoder(elementType, DefaultRSocketRequester.this.dataMimeType);
            }
            return encoder.encodeValue(value, DefaultRSocketRequester.this.bufferFactory(), elementType, DefaultRSocketRequester.this.dataMimeType, EMPTY_HINTS);
        }

        private Mono<Payload> firstPayload(Mono<DataBuffer> encodedData) {
            return Mono.zip(encodedData, this.metadataEncoder.encode()).map(tuple -> PayloadUtils.createPayload((DataBuffer)tuple.getT1(), (DataBuffer)tuple.getT2())).doOnDiscard(DataBuffer.class, DataBufferUtils::release).doOnDiscard(Payload.class, ReferenceCounted::release);
        }

        @Override
        public Mono<Void> sendMetadata() {
            return DefaultRSocketRequester.this.rsocketClient.metadataPush(this.getPayloadMono());
        }

        @Override
        public Mono<Void> send() {
            return DefaultRSocketRequester.this.rsocketClient.fireAndForget(this.getPayloadMono());
        }

        @Override
        public <T> Mono<T> retrieveMono(Class<T> dataType) {
            return this.retrieveMono(ResolvableType.forClass(dataType));
        }

        @Override
        public <T> Mono<T> retrieveMono(ParameterizedTypeReference<T> dataTypeRef) {
            return this.retrieveMono(ResolvableType.forType(dataTypeRef));
        }

        private <T> Mono<T> retrieveMono(ResolvableType elementType) {
            Mono payloadMono = DefaultRSocketRequester.this.rsocketClient.requestResponse(this.getPayloadMono());
            if (DefaultRSocketRequester.isVoid(elementType)) {
                return payloadMono.then();
            }
            Decoder decoder = DefaultRSocketRequester.this.strategies.decoder(elementType, DefaultRSocketRequester.this.dataMimeType);
            return payloadMono.map(this::retainDataAndReleasePayload).map(dataBuffer -> decoder.decode(dataBuffer, elementType, DefaultRSocketRequester.this.dataMimeType, EMPTY_HINTS));
        }

        @Override
        public <T> Flux<T> retrieveFlux(Class<T> dataType) {
            return this.retrieveFlux(ResolvableType.forClass(dataType));
        }

        @Override
        public <T> Flux<T> retrieveFlux(ParameterizedTypeReference<T> dataTypeRef) {
            return this.retrieveFlux(ResolvableType.forType(dataTypeRef));
        }

        private <T> Flux<T> retrieveFlux(ResolvableType elementType) {
            Flux payloadFlux;
            Flux flux = payloadFlux = this.payloadFlux != null ? DefaultRSocketRequester.this.rsocketClient.requestChannel(this.payloadFlux) : DefaultRSocketRequester.this.rsocketClient.requestStream(this.getPayloadMono());
            if (DefaultRSocketRequester.isVoid(elementType)) {
                return payloadFlux.thenMany((Publisher)Flux.empty());
            }
            Decoder decoder = DefaultRSocketRequester.this.strategies.decoder(elementType, DefaultRSocketRequester.this.dataMimeType);
            return payloadFlux.map(this::retainDataAndReleasePayload).map(dataBuffer -> decoder.decode(dataBuffer, elementType, DefaultRSocketRequester.this.dataMimeType, EMPTY_HINTS));
        }

        private Mono<Payload> getPayloadMono() {
            Assert.state((this.payloadFlux == null ? 1 : 0) != 0, (String)"No RSocket interaction with Flux request and Mono response.");
            return this.payloadMono != null ? this.payloadMono : this.firstPayload((Mono<DataBuffer>)DefaultRSocketRequester.this.emptyBufferMono);
        }

        private DataBuffer retainDataAndReleasePayload(Payload payload) {
            return PayloadUtils.retainDataAndReleasePayload(payload, DefaultRSocketRequester.this.bufferFactory());
        }
    }
}

