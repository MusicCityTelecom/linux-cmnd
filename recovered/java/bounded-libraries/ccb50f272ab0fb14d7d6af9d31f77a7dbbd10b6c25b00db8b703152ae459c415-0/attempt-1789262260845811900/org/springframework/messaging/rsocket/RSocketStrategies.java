/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.ReactiveAdapterRegistry
 *  org.springframework.core.ResolvableType
 *  org.springframework.core.codec.Decoder
 *  org.springframework.core.codec.Encoder
 *  org.springframework.core.io.buffer.DataBufferFactory
 *  org.springframework.lang.Nullable
 *  org.springframework.util.MimeType
 *  org.springframework.util.RouteMatcher
 */
package org.springframework.messaging.rsocket;

import java.util.List;
import java.util.function.Consumer;
import org.springframework.core.ReactiveAdapterRegistry;
import org.springframework.core.ResolvableType;
import org.springframework.core.codec.Decoder;
import org.springframework.core.codec.Encoder;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.lang.Nullable;
import org.springframework.messaging.rsocket.DefaultRSocketStrategies;
import org.springframework.messaging.rsocket.MetadataExtractor;
import org.springframework.messaging.rsocket.MetadataExtractorRegistry;
import org.springframework.util.MimeType;
import org.springframework.util.RouteMatcher;

public interface RSocketStrategies {
    public List<Encoder<?>> encoders();

    default public <T> Encoder<T> encoder(ResolvableType elementType, @Nullable MimeType mimeType) {
        for (Encoder<?> encoder : this.encoders()) {
            if (!encoder.canEncode(elementType, mimeType)) continue;
            return encoder;
        }
        throw new IllegalArgumentException("No encoder for " + elementType);
    }

    public List<Decoder<?>> decoders();

    default public <T> Decoder<T> decoder(ResolvableType elementType, @Nullable MimeType mimeType) {
        for (Decoder<?> decoder : this.decoders()) {
            if (!decoder.canDecode(elementType, mimeType)) continue;
            return decoder;
        }
        throw new IllegalArgumentException("No decoder for " + elementType);
    }

    public RouteMatcher routeMatcher();

    public ReactiveAdapterRegistry reactiveAdapterRegistry();

    public DataBufferFactory dataBufferFactory();

    public MetadataExtractor metadataExtractor();

    default public Builder mutate() {
        return new DefaultRSocketStrategies.DefaultRSocketStrategiesBuilder(this);
    }

    public static RSocketStrategies create() {
        return new DefaultRSocketStrategies.DefaultRSocketStrategiesBuilder().build();
    }

    public static Builder builder() {
        return new DefaultRSocketStrategies.DefaultRSocketStrategiesBuilder();
    }

    public static interface Builder {
        public Builder encoder(Encoder<?> ... var1);

        public Builder encoders(Consumer<List<Encoder<?>>> var1);

        public Builder decoder(Decoder<?> ... var1);

        public Builder decoders(Consumer<List<Decoder<?>>> var1);

        public Builder routeMatcher(@Nullable RouteMatcher var1);

        public Builder reactiveAdapterStrategy(@Nullable ReactiveAdapterRegistry var1);

        public Builder dataBufferFactory(@Nullable DataBufferFactory var1);

        public Builder metadataExtractor(@Nullable MetadataExtractor var1);

        public Builder metadataExtractorRegistry(Consumer<MetadataExtractorRegistry> var1);

        public RSocketStrategies build();
    }
}

