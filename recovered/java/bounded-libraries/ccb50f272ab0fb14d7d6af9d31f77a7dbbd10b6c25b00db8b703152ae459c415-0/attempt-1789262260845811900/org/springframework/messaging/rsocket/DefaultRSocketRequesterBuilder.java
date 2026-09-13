/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.netty.util.ReferenceCounted
 *  io.rsocket.Payload
 *  io.rsocket.RSocket
 *  io.rsocket.core.RSocketClient
 *  io.rsocket.core.RSocketConnector
 *  io.rsocket.frame.decoder.PayloadDecoder
 *  io.rsocket.loadbalance.LoadbalanceRSocketClient
 *  io.rsocket.loadbalance.LoadbalanceStrategy
 *  io.rsocket.loadbalance.LoadbalanceTarget
 *  io.rsocket.metadata.WellKnownMimeType
 *  io.rsocket.transport.ClientTransport
 *  io.rsocket.transport.netty.client.TcpClientTransport
 *  io.rsocket.transport.netty.client.WebsocketClientTransport
 *  io.rsocket.util.DefaultPayload
 *  org.reactivestreams.Publisher
 *  org.springframework.core.ReactiveAdapter
 *  org.springframework.core.ResolvableType
 *  org.springframework.core.codec.Decoder
 *  org.springframework.core.codec.Encoder
 *  org.springframework.core.codec.StringDecoder
 *  org.springframework.core.io.buffer.DataBuffer
 *  org.springframework.core.io.buffer.DataBufferUtils
 *  org.springframework.core.io.buffer.NettyDataBufferFactory
 *  org.springframework.lang.Nullable
 *  org.springframework.util.Assert
 *  org.springframework.util.CollectionUtils
 *  org.springframework.util.MimeType
 *  org.springframework.util.MimeTypeUtils
 *  reactor.core.publisher.Mono
 */
package org.springframework.messaging.rsocket;

import io.netty.util.ReferenceCounted;
import io.rsocket.Payload;
import io.rsocket.RSocket;
import io.rsocket.core.RSocketClient;
import io.rsocket.core.RSocketConnector;
import io.rsocket.frame.decoder.PayloadDecoder;
import io.rsocket.loadbalance.LoadbalanceRSocketClient;
import io.rsocket.loadbalance.LoadbalanceStrategy;
import io.rsocket.loadbalance.LoadbalanceTarget;
import io.rsocket.metadata.WellKnownMimeType;
import io.rsocket.transport.ClientTransport;
import io.rsocket.transport.netty.client.TcpClientTransport;
import io.rsocket.transport.netty.client.WebsocketClientTransport;
import io.rsocket.util.DefaultPayload;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import org.reactivestreams.Publisher;
import org.springframework.core.ReactiveAdapter;
import org.springframework.core.ResolvableType;
import org.springframework.core.codec.Decoder;
import org.springframework.core.codec.Encoder;
import org.springframework.core.codec.StringDecoder;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.NettyDataBufferFactory;
import org.springframework.lang.Nullable;
import org.springframework.messaging.rsocket.DefaultRSocketRequester;
import org.springframework.messaging.rsocket.MetadataEncoder;
import org.springframework.messaging.rsocket.PayloadUtils;
import org.springframework.messaging.rsocket.RSocketConnectorConfigurer;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.messaging.rsocket.RSocketStrategies;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;
import reactor.core.publisher.Mono;

final class DefaultRSocketRequesterBuilder
implements RSocketRequester.Builder {
    private static final Map<String, Object> HINTS = Collections.emptyMap();
    private static final byte[] EMPTY_BYTE_ARRAY = new byte[0];
    private static final Payload EMPTY_SETUP_PAYLOAD = DefaultPayload.create((byte[])EMPTY_BYTE_ARRAY);
    @Nullable
    private MimeType dataMimeType;
    @Nullable
    private MimeType metadataMimeType;
    @Nullable
    private Object setupData;
    @Nullable
    private String setupRoute;
    @Nullable
    private Object[] setupRouteVars;
    @Nullable
    private Map<Object, MimeType> setupMetadata;
    @Nullable
    private RSocketStrategies strategies;
    private List<Consumer<RSocketStrategies.Builder>> strategiesConfigurers = new ArrayList<Consumer<RSocketStrategies.Builder>>();
    private List<RSocketConnectorConfigurer> rsocketConnectorConfigurers = new ArrayList<RSocketConnectorConfigurer>();

    DefaultRSocketRequesterBuilder() {
    }

    @Override
    public RSocketRequester.Builder dataMimeType(@Nullable MimeType mimeType) {
        this.dataMimeType = mimeType;
        return this;
    }

    @Override
    public RSocketRequester.Builder metadataMimeType(MimeType mimeType) {
        Assert.notNull((Object)mimeType, (String)"`metadataMimeType` is required");
        this.metadataMimeType = mimeType;
        return this;
    }

    @Override
    public RSocketRequester.Builder setupData(Object data) {
        this.setupData = data;
        return this;
    }

    @Override
    public RSocketRequester.Builder setupRoute(String route, Object ... routeVars) {
        this.setupRoute = route;
        this.setupRouteVars = routeVars;
        return this;
    }

    @Override
    public RSocketRequester.Builder setupMetadata(Object metadata, @Nullable MimeType mimeType) {
        this.setupMetadata = this.setupMetadata == null ? new LinkedHashMap(4) : this.setupMetadata;
        this.setupMetadata.put(metadata, mimeType);
        return this;
    }

    @Override
    public RSocketRequester.Builder rsocketStrategies(@Nullable RSocketStrategies strategies) {
        this.strategies = strategies;
        return this;
    }

    @Override
    public RSocketRequester.Builder rsocketStrategies(Consumer<RSocketStrategies.Builder> configurer) {
        this.strategiesConfigurers.add(configurer);
        return this;
    }

    @Override
    public RSocketRequester.Builder rsocketConnector(RSocketConnectorConfigurer configurer) {
        this.rsocketConnectorConfigurers.add(configurer);
        return this;
    }

    @Override
    public RSocketRequester.Builder apply(Consumer<RSocketRequester.Builder> configurer) {
        configurer.accept(this);
        return this;
    }

    @Override
    public RSocketRequester tcp(String host, int port) {
        return this.transport((ClientTransport)TcpClientTransport.create((String)host, (int)port));
    }

    @Override
    public RSocketRequester websocket(URI uri) {
        return this.transport((ClientTransport)WebsocketClientTransport.create((URI)uri));
    }

    @Override
    public RSocketRequester transport(ClientTransport transport) {
        RSocketStrategies strategies = this.getRSocketStrategies();
        MimeType metaMimeType = this.getMetadataMimeType();
        MimeType dataMimeType = this.getDataMimeType(strategies);
        RSocketConnector connector = this.initConnector(this.rsocketConnectorConfigurers, metaMimeType, dataMimeType, strategies);
        RSocketClient client = RSocketClient.from((Mono)connector.connect(transport));
        return new DefaultRSocketRequester(client, null, dataMimeType, metaMimeType, strategies);
    }

    @Override
    public RSocketRequester transports(Publisher<List<LoadbalanceTarget>> targetPublisher, LoadbalanceStrategy loadbalanceStrategy) {
        RSocketStrategies strategies = this.getRSocketStrategies();
        MimeType metaMimeType = this.getMetadataMimeType();
        MimeType dataMimeType = this.getDataMimeType(strategies);
        RSocketConnector connector = this.initConnector(this.rsocketConnectorConfigurers, metaMimeType, dataMimeType, strategies);
        LoadbalanceRSocketClient client = LoadbalanceRSocketClient.builder(targetPublisher).connector(connector).loadbalanceStrategy(loadbalanceStrategy).build();
        return new DefaultRSocketRequester((RSocketClient)client, null, dataMimeType, metaMimeType, strategies);
    }

    @Override
    public Mono<RSocketRequester> connectTcp(String host, int port) {
        return this.connect((ClientTransport)TcpClientTransport.create((String)host, (int)port));
    }

    @Override
    public Mono<RSocketRequester> connectWebSocket(URI uri) {
        return this.connect((ClientTransport)WebsocketClientTransport.create((URI)uri));
    }

    @Override
    public Mono<RSocketRequester> connect(ClientTransport transport) {
        RSocketStrategies rsocketStrategies = this.getRSocketStrategies();
        MimeType metaMimeType = this.getMetadataMimeType();
        MimeType dataMimeType = this.getDataMimeType(rsocketStrategies);
        RSocketConnector connector = this.initConnector(this.rsocketConnectorConfigurers, metaMimeType, dataMimeType, rsocketStrategies);
        return connector.connect(transport).map(rsocket -> new DefaultRSocketRequester(null, (RSocket)rsocket, dataMimeType, metaMimeType, rsocketStrategies));
    }

    public MimeType getMetadataMimeType() {
        return this.metadataMimeType != null ? this.metadataMimeType : MimeTypeUtils.parseMimeType((String)WellKnownMimeType.MESSAGE_RSOCKET_COMPOSITE_METADATA.getString());
    }

    private RSocketStrategies getRSocketStrategies() {
        RSocketStrategies result;
        if (!this.strategiesConfigurers.isEmpty()) {
            RSocketStrategies.Builder builder = this.strategies != null ? this.strategies.mutate() : RSocketStrategies.builder();
            this.strategiesConfigurers.forEach(c -> c.accept(builder));
            result = builder.build();
        } else {
            result = this.strategies != null ? this.strategies : RSocketStrategies.builder().build();
        }
        Assert.isTrue((!result.encoders().isEmpty() ? 1 : 0) != 0, (String)"No encoders");
        Assert.isTrue((!result.decoders().isEmpty() ? 1 : 0) != 0, (String)"No decoders");
        return result;
    }

    private MimeType getDataMimeType(RSocketStrategies strategies) {
        if (this.dataMimeType != null) {
            return this.dataMimeType;
        }
        for (Decoder<?> candidate : strategies.decoders()) {
            if (DefaultRSocketRequesterBuilder.isCoreCodec(candidate) || candidate.getDecodableMimeTypes().isEmpty()) continue;
            return DefaultRSocketRequesterBuilder.getMimeType(candidate);
        }
        for (Decoder<?> decoder : strategies.decoders()) {
            if (decoder.getDecodableMimeTypes().isEmpty()) continue;
            return DefaultRSocketRequesterBuilder.getMimeType(decoder);
        }
        throw new IllegalArgumentException("Failed to select data MimeType to use.");
    }

    private static boolean isCoreCodec(Object codec) {
        return codec.getClass().getPackage().equals(StringDecoder.class.getPackage());
    }

    private static MimeType getMimeType(Decoder<?> decoder) {
        MimeType mimeType = (MimeType)decoder.getDecodableMimeTypes().get(0);
        return mimeType.getParameters().isEmpty() ? mimeType : new MimeType(mimeType, Collections.emptyMap());
    }

    private Mono<Payload> getSetupPayload(MimeType dataMimeType, MimeType metaMimeType, RSocketStrategies strategies) {
        boolean hasMetadata;
        Object data = this.setupData;
        boolean bl = hasMetadata = this.setupRoute != null || !CollectionUtils.isEmpty(this.setupMetadata);
        if (!hasMetadata && data == null) {
            return Mono.just((Object)EMPTY_SETUP_PAYLOAD);
        }
        Mono dataMono = Mono.empty();
        if (data != null) {
            ReactiveAdapter adapter = strategies.reactiveAdapterRegistry().getAdapter(data.getClass());
            Assert.isTrue((adapter == null || !adapter.isMultiValue() ? 1 : 0) != 0, (String)("Expected single value: " + data));
            Mono mono = adapter != null ? Mono.from((Publisher)adapter.toPublisher(data)) : Mono.just((Object)data);
            dataMono = mono.map(value -> {
                ResolvableType type = ResolvableType.forClass(value.getClass());
                Encoder encoder = strategies.encoder(type, dataMimeType);
                Assert.notNull(encoder, () -> "No encoder for " + dataMimeType + ", " + type);
                return encoder.encodeValue(value, strategies.dataBufferFactory(), type, dataMimeType, HINTS);
            });
        }
        Mono<DataBuffer> metaMono = Mono.empty();
        if (hasMetadata) {
            metaMono = new MetadataEncoder(metaMimeType, strategies).metadataAndOrRoute(this.setupMetadata, this.setupRoute, this.setupRouteVars).encode();
        }
        Mono emptyBuffer = Mono.fromCallable(() -> strategies.dataBufferFactory().wrap(EMPTY_BYTE_ARRAY));
        dataMono = dataMono.switchIfEmpty(emptyBuffer);
        metaMono = metaMono.switchIfEmpty(emptyBuffer);
        return Mono.zip((Mono)dataMono, metaMono).map(tuple -> PayloadUtils.createPayload((DataBuffer)tuple.getT1(), (DataBuffer)tuple.getT2())).doOnDiscard(DataBuffer.class, DataBufferUtils::release).doOnDiscard(Payload.class, ReferenceCounted::release);
    }

    private RSocketConnector initConnector(List<RSocketConnectorConfigurer> connectorConfigurers, MimeType metaMimeType, MimeType dataMimeType, RSocketStrategies rsocketStrategies) {
        RSocketConnector connector = RSocketConnector.create();
        connectorConfigurers.forEach(c -> c.configure(connector));
        if (rsocketStrategies.dataBufferFactory() instanceof NettyDataBufferFactory) {
            connector.payloadDecoder(PayloadDecoder.ZERO_COPY);
        }
        connector.metadataMimeType(metaMimeType.toString());
        connector.dataMimeType(dataMimeType.toString());
        Mono<Payload> setupPayloadMono = this.getSetupPayload(dataMimeType, metaMimeType, rsocketStrategies);
        if (setupPayloadMono != EMPTY_SETUP_PAYLOAD) {
            connector.setupPayload(setupPayloadMono);
        }
        return connector;
    }
}

