/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.ByteBuf
 *  io.netty.buffer.ByteBufAllocator
 *  io.rsocket.Payload
 *  io.rsocket.metadata.CompositeMetadata
 *  io.rsocket.metadata.CompositeMetadata$Entry
 *  io.rsocket.metadata.RoutingMetadata
 *  io.rsocket.metadata.WellKnownMimeType
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.core.ParameterizedTypeReference
 *  org.springframework.core.ResolvableType
 *  org.springframework.core.codec.Decoder
 *  org.springframework.core.io.buffer.DataBuffer
 *  org.springframework.core.io.buffer.NettyDataBuffer
 *  org.springframework.core.io.buffer.NettyDataBufferFactory
 *  org.springframework.lang.Nullable
 *  org.springframework.util.MimeType
 */
package org.springframework.messaging.rsocket;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.rsocket.Payload;
import io.rsocket.metadata.CompositeMetadata;
import io.rsocket.metadata.RoutingMetadata;
import io.rsocket.metadata.WellKnownMimeType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.ResolvableType;
import org.springframework.core.codec.Decoder;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.NettyDataBuffer;
import org.springframework.core.io.buffer.NettyDataBufferFactory;
import org.springframework.lang.Nullable;
import org.springframework.messaging.rsocket.MetadataExtractor;
import org.springframework.messaging.rsocket.MetadataExtractorRegistry;
import org.springframework.util.MimeType;

public class DefaultMetadataExtractor
implements MetadataExtractor,
MetadataExtractorRegistry {
    private static final Log logger = LogFactory.getLog(DefaultMetadataExtractor.class);
    private final List<Decoder<?>> decoders;
    private final Map<String, EntryExtractor<?>> registrations = new HashMap();

    public DefaultMetadataExtractor(Decoder<?> ... decoders) {
        this(Arrays.asList(decoders));
    }

    public DefaultMetadataExtractor(List<Decoder<?>> decoders) {
        this.decoders = Collections.unmodifiableList(new ArrayList(decoders));
    }

    public List<? extends Decoder<?>> getDecoders() {
        return this.decoders;
    }

    @Override
    public <T> void metadataToExtract(MimeType mimeType, Class<T> targetType, BiConsumer<T, Map<String, Object>> mapper) {
        this.registerMetadata(mimeType, ResolvableType.forClass(targetType), mapper);
    }

    @Override
    public <T> void metadataToExtract(MimeType mimeType, ParameterizedTypeReference<T> type, BiConsumer<T, Map<String, Object>> mapper) {
        this.registerMetadata(mimeType, ResolvableType.forType(type), mapper);
    }

    private <T> void registerMetadata(MimeType mimeType, ResolvableType targetType, BiConsumer<T, Map<String, Object>> mapper) {
        for (Decoder<?> decoder : this.decoders) {
            if (!decoder.canDecode(targetType, mimeType)) continue;
            this.registrations.put(mimeType.toString(), new EntryExtractor(decoder, mimeType, targetType, mapper));
            return;
        }
        throw new IllegalArgumentException("No decoder for " + mimeType + " and " + targetType);
    }

    @Override
    public Map<String, Object> extract(Payload payload, MimeType metadataMimeType) {
        HashMap<String, Object> result = new HashMap<String, Object>();
        if (metadataMimeType.toString().equals(WellKnownMimeType.MESSAGE_RSOCKET_COMPOSITE_METADATA.toString())) {
            for (CompositeMetadata.Entry entry : new CompositeMetadata(payload.metadata(), false)) {
                this.extractEntry(entry.getContent(), entry.getMimeType(), result);
            }
        } else {
            this.extractEntry(payload.metadata().slice(), metadataMimeType.toString(), result);
        }
        if (logger.isDebugEnabled()) {
            logger.debug((Object)("Values extracted from metadata: " + result + " with registrations for " + this.registrations.keySet() + "."));
        }
        return result;
    }

    private void extractEntry(ByteBuf content, @Nullable String mimeType, Map<String, Object> result) {
        Iterator iterator;
        if (content.readableBytes() == 0) {
            return;
        }
        EntryExtractor<?> extractor = this.registrations.get(mimeType);
        if (extractor != null) {
            extractor.extract(content, result);
            return;
        }
        if (mimeType != null && mimeType.equals(WellKnownMimeType.MESSAGE_RSOCKET_ROUTING.getString()) && (iterator = new RoutingMetadata(content).iterator()).hasNext()) {
            result.put("route", iterator.next());
        }
    }

    private static class EntryExtractor<T> {
        private static final NettyDataBufferFactory bufferFactory = new NettyDataBufferFactory(ByteBufAllocator.DEFAULT);
        private final Decoder<T> decoder;
        private final MimeType mimeType;
        private final ResolvableType targetType;
        private final BiConsumer<T, Map<String, Object>> accumulator;

        EntryExtractor(Decoder<T> decoder, MimeType mimeType, ResolvableType targetType, BiConsumer<T, Map<String, Object>> accumulator) {
            this.decoder = decoder;
            this.mimeType = mimeType;
            this.targetType = targetType;
            this.accumulator = accumulator;
        }

        public void extract(ByteBuf content, Map<String, Object> result) {
            NettyDataBuffer dataBuffer = bufferFactory.wrap(content.retain());
            Object value = this.decoder.decode((DataBuffer)dataBuffer, this.targetType, this.mimeType, Collections.emptyMap());
            this.accumulator.accept(value, result);
        }

        public String toString() {
            return "\"" + this.mimeType + "\" => " + this.targetType;
        }
    }
}

