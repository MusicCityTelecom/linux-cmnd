/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.ByteBufAllocator
 *  io.netty.buffer.PooledByteBufAllocator
 *  org.springframework.core.ReactiveAdapterRegistry
 *  org.springframework.core.codec.ByteArrayDecoder
 *  org.springframework.core.codec.ByteArrayEncoder
 *  org.springframework.core.codec.ByteBufferDecoder
 *  org.springframework.core.codec.ByteBufferEncoder
 *  org.springframework.core.codec.CharSequenceEncoder
 *  org.springframework.core.codec.DataBufferDecoder
 *  org.springframework.core.codec.DataBufferEncoder
 *  org.springframework.core.codec.Decoder
 *  org.springframework.core.codec.Encoder
 *  org.springframework.core.codec.StringDecoder
 *  org.springframework.core.io.buffer.DataBufferFactory
 *  org.springframework.core.io.buffer.NettyDataBufferFactory
 *  org.springframework.lang.Nullable
 *  org.springframework.util.AntPathMatcher
 *  org.springframework.util.PathMatcher
 *  org.springframework.util.RouteMatcher
 *  org.springframework.util.SimpleRouteMatcher
 */
package org.springframework.messaging.rsocket;

import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.PooledByteBufAllocator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import org.springframework.core.ReactiveAdapterRegistry;
import org.springframework.core.codec.ByteArrayDecoder;
import org.springframework.core.codec.ByteArrayEncoder;
import org.springframework.core.codec.ByteBufferDecoder;
import org.springframework.core.codec.ByteBufferEncoder;
import org.springframework.core.codec.CharSequenceEncoder;
import org.springframework.core.codec.DataBufferDecoder;
import org.springframework.core.codec.DataBufferEncoder;
import org.springframework.core.codec.Decoder;
import org.springframework.core.codec.Encoder;
import org.springframework.core.codec.StringDecoder;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.NettyDataBufferFactory;
import org.springframework.lang.Nullable;
import org.springframework.messaging.rsocket.DefaultMetadataExtractor;
import org.springframework.messaging.rsocket.MetadataExtractor;
import org.springframework.messaging.rsocket.MetadataExtractorRegistry;
import org.springframework.messaging.rsocket.RSocketStrategies;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.util.RouteMatcher;
import org.springframework.util.SimpleRouteMatcher;

final class DefaultRSocketStrategies
implements RSocketStrategies {
    private final List<Encoder<?>> encoders;
    private final List<Decoder<?>> decoders;
    private final RouteMatcher routeMatcher;
    private final ReactiveAdapterRegistry adapterRegistry;
    private final DataBufferFactory bufferFactory;
    private final MetadataExtractor metadataExtractor;

    private DefaultRSocketStrategies(List<Encoder<?>> encoders, List<Decoder<?>> decoders, RouteMatcher routeMatcher, ReactiveAdapterRegistry adapterRegistry, DataBufferFactory bufferFactory, MetadataExtractor metadataExtractor) {
        this.encoders = Collections.unmodifiableList(encoders);
        this.decoders = Collections.unmodifiableList(decoders);
        this.routeMatcher = routeMatcher;
        this.adapterRegistry = adapterRegistry;
        this.bufferFactory = bufferFactory;
        this.metadataExtractor = metadataExtractor;
    }

    @Override
    public List<Encoder<?>> encoders() {
        return this.encoders;
    }

    @Override
    public List<Decoder<?>> decoders() {
        return this.decoders;
    }

    @Override
    public RouteMatcher routeMatcher() {
        return this.routeMatcher;
    }

    @Override
    public ReactiveAdapterRegistry reactiveAdapterRegistry() {
        return this.adapterRegistry;
    }

    @Override
    public DataBufferFactory dataBufferFactory() {
        return this.bufferFactory;
    }

    @Override
    public MetadataExtractor metadataExtractor() {
        return this.metadataExtractor;
    }

    static class DefaultRSocketStrategiesBuilder
    implements RSocketStrategies.Builder {
        private final List<Encoder<?>> encoders = new ArrayList();
        private final List<Decoder<?>> decoders = new ArrayList();
        @Nullable
        private RouteMatcher routeMatcher;
        @Nullable
        private ReactiveAdapterRegistry adapterRegistry = ReactiveAdapterRegistry.getSharedInstance();
        @Nullable
        private DataBufferFactory bufferFactory;
        @Nullable
        private MetadataExtractor metadataExtractor;
        private final List<Consumer<MetadataExtractorRegistry>> metadataExtractors = new ArrayList<Consumer<MetadataExtractorRegistry>>();

        DefaultRSocketStrategiesBuilder() {
            this.encoders.add((Encoder<?>)CharSequenceEncoder.allMimeTypes());
            this.encoders.add((Encoder<?>)new ByteBufferEncoder());
            this.encoders.add((Encoder<?>)new ByteArrayEncoder());
            this.encoders.add((Encoder<?>)new DataBufferEncoder());
            this.decoders.add((Decoder<?>)StringDecoder.allMimeTypes());
            this.decoders.add((Decoder<?>)new ByteBufferDecoder());
            this.decoders.add((Decoder<?>)new ByteArrayDecoder());
            this.decoders.add((Decoder<?>)new DataBufferDecoder());
        }

        DefaultRSocketStrategiesBuilder(RSocketStrategies other) {
            this.encoders.addAll(other.encoders());
            this.decoders.addAll(other.decoders());
            this.routeMatcher = other.routeMatcher();
            this.adapterRegistry = other.reactiveAdapterRegistry();
            this.bufferFactory = other.dataBufferFactory();
            this.metadataExtractor = other.metadataExtractor();
        }

        @Override
        public RSocketStrategies.Builder encoder(Encoder<?> ... encoders) {
            this.encoders.addAll(Arrays.asList(encoders));
            return this;
        }

        @Override
        public RSocketStrategies.Builder decoder(Decoder<?> ... decoder) {
            this.decoders.addAll(Arrays.asList(decoder));
            return this;
        }

        @Override
        public RSocketStrategies.Builder encoders(Consumer<List<Encoder<?>>> consumer) {
            consumer.accept(this.encoders);
            return this;
        }

        @Override
        public RSocketStrategies.Builder decoders(Consumer<List<Decoder<?>>> consumer) {
            consumer.accept(this.decoders);
            return this;
        }

        @Override
        public RSocketStrategies.Builder routeMatcher(@Nullable RouteMatcher routeMatcher) {
            this.routeMatcher = routeMatcher;
            return this;
        }

        @Override
        public RSocketStrategies.Builder reactiveAdapterStrategy(@Nullable ReactiveAdapterRegistry registry) {
            this.adapterRegistry = registry;
            return this;
        }

        @Override
        public RSocketStrategies.Builder dataBufferFactory(@Nullable DataBufferFactory bufferFactory) {
            this.bufferFactory = bufferFactory;
            return this;
        }

        @Override
        public RSocketStrategies.Builder metadataExtractor(@Nullable MetadataExtractor metadataExtractor) {
            this.metadataExtractor = metadataExtractor;
            return this;
        }

        @Override
        public RSocketStrategies.Builder metadataExtractorRegistry(Consumer<MetadataExtractorRegistry> consumer) {
            this.metadataExtractors.add(consumer);
            return this;
        }

        @Override
        public RSocketStrategies build() {
            MetadataExtractor extractor;
            RouteMatcher matcher = this.routeMatcher != null ? this.routeMatcher : this.initRouteMatcher();
            ReactiveAdapterRegistry registry = this.adapterRegistry != null ? this.adapterRegistry : ReactiveAdapterRegistry.getSharedInstance();
            DataBufferFactory factory = this.bufferFactory != null ? this.bufferFactory : new NettyDataBufferFactory((ByteBufAllocator)PooledByteBufAllocator.DEFAULT);
            MetadataExtractor metadataExtractor = extractor = this.metadataExtractor != null ? this.metadataExtractor : new DefaultMetadataExtractor(this.decoders);
            if (extractor instanceof MetadataExtractorRegistry) {
                this.metadataExtractors.forEach(consumer -> consumer.accept((MetadataExtractorRegistry)((Object)extractor)));
            }
            return new DefaultRSocketStrategies(this.encoders, this.decoders, matcher, registry, factory, extractor);
        }

        private RouteMatcher initRouteMatcher() {
            AntPathMatcher pathMatcher = new AntPathMatcher();
            pathMatcher.setPathSeparator(".");
            return new SimpleRouteMatcher((PathMatcher)pathMatcher);
        }
    }
}

