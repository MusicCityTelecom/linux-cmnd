/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.rsocket.RSocket
 *  io.rsocket.core.RSocketClient
 *  io.rsocket.loadbalance.LoadbalanceStrategy
 *  io.rsocket.loadbalance.LoadbalanceTarget
 *  io.rsocket.transport.ClientTransport
 *  org.reactivestreams.Publisher
 *  org.springframework.core.ParameterizedTypeReference
 *  org.springframework.lang.Nullable
 *  org.springframework.util.MimeType
 *  reactor.core.Disposable
 *  reactor.core.publisher.Flux
 *  reactor.core.publisher.Mono
 */
package org.springframework.messaging.rsocket;

import io.rsocket.RSocket;
import io.rsocket.core.RSocketClient;
import io.rsocket.loadbalance.LoadbalanceStrategy;
import io.rsocket.loadbalance.LoadbalanceTarget;
import io.rsocket.transport.ClientTransport;
import java.net.URI;
import java.util.List;
import java.util.function.Consumer;
import org.reactivestreams.Publisher;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.lang.Nullable;
import org.springframework.messaging.rsocket.DefaultRSocketRequester;
import org.springframework.messaging.rsocket.DefaultRSocketRequesterBuilder;
import org.springframework.messaging.rsocket.RSocketConnectorConfigurer;
import org.springframework.messaging.rsocket.RSocketStrategies;
import org.springframework.util.MimeType;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RSocketRequester
extends Disposable {
    public RSocketClient rsocketClient();

    @Nullable
    public RSocket rsocket();

    public MimeType dataMimeType();

    public MimeType metadataMimeType();

    public RequestSpec route(String var1, Object ... var2);

    public RequestSpec metadata(Object var1, @Nullable MimeType var2);

    default public void dispose() {
        this.rsocketClient().dispose();
    }

    default public boolean isDisposed() {
        return this.rsocketClient().isDisposed();
    }

    public static Builder builder() {
        return new DefaultRSocketRequesterBuilder();
    }

    public static RSocketRequester wrap(RSocket rsocket, MimeType dataMimeType, MimeType metadataMimeType, RSocketStrategies strategies) {
        return new DefaultRSocketRequester(null, rsocket, dataMimeType, metadataMimeType, strategies);
    }

    public static interface RetrieveSpec {
        public Mono<Void> send();

        public <T> Mono<T> retrieveMono(Class<T> var1);

        public <T> Mono<T> retrieveMono(ParameterizedTypeReference<T> var1);

        public <T> Flux<T> retrieveFlux(Class<T> var1);

        public <T> Flux<T> retrieveFlux(ParameterizedTypeReference<T> var1);
    }

    public static interface MetadataSpec<S extends MetadataSpec<S>> {
        public S metadata(Object var1, MimeType var2);
    }

    public static interface RequestSpec
    extends MetadataSpec<RequestSpec>,
    RetrieveSpec {
        public RequestSpec metadata(Consumer<MetadataSpec<?>> var1);

        public Mono<Void> sendMetadata();

        public RetrieveSpec data(Object var1);

        public RetrieveSpec data(Object var1, Class<?> var2);

        public RetrieveSpec data(Object var1, ParameterizedTypeReference<?> var2);
    }

    public static interface Builder {
        public Builder dataMimeType(@Nullable MimeType var1);

        public Builder metadataMimeType(MimeType var1);

        public Builder setupData(Object var1);

        public Builder setupRoute(String var1, Object ... var2);

        public Builder setupMetadata(Object var1, @Nullable MimeType var2);

        public Builder rsocketStrategies(@Nullable RSocketStrategies var1);

        public Builder rsocketStrategies(Consumer<RSocketStrategies.Builder> var1);

        public Builder rsocketConnector(RSocketConnectorConfigurer var1);

        public Builder apply(Consumer<Builder> var1);

        public RSocketRequester tcp(String var1, int var2);

        public RSocketRequester websocket(URI var1);

        public RSocketRequester transport(ClientTransport var1);

        public RSocketRequester transports(Publisher<List<LoadbalanceTarget>> var1, LoadbalanceStrategy var2);

        @Deprecated
        public Mono<RSocketRequester> connectTcp(String var1, int var2);

        @Deprecated
        public Mono<RSocketRequester> connectWebSocket(URI var1);

        @Deprecated
        public Mono<RSocketRequester> connect(ClientTransport var1);
    }
}

