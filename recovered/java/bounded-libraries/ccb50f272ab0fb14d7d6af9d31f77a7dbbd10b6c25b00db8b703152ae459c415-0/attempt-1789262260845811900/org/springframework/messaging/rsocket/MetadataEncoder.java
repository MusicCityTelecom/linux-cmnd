/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.ByteBuf
 *  io.netty.buffer.ByteBufAllocator
 *  io.netty.buffer.CompositeByteBuf
 *  io.rsocket.metadata.CompositeMetadataCodec
 *  io.rsocket.metadata.TaggingMetadataCodec
 *  io.rsocket.metadata.WellKnownMimeType
 *  org.reactivestreams.Publisher
 *  org.springframework.core.ReactiveAdapter
 *  org.springframework.core.ResolvableType
 *  org.springframework.core.codec.Encoder
 *  org.springframework.core.io.buffer.DataBuffer
 *  org.springframework.core.io.buffer.DataBufferFactory
 *  org.springframework.core.io.buffer.NettyDataBufferFactory
 *  org.springframework.lang.Nullable
 *  org.springframework.util.Assert
 *  org.springframework.util.CollectionUtils
 *  org.springframework.util.MimeType
 *  org.springframework.util.ObjectUtils
 *  reactor.core.publisher.Mono
 */
package org.springframework.messaging.rsocket;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.CompositeByteBuf;
import io.rsocket.metadata.CompositeMetadataCodec;
import io.rsocket.metadata.TaggingMetadataCodec;
import io.rsocket.metadata.WellKnownMimeType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.reactivestreams.Publisher;
import org.springframework.core.ReactiveAdapter;
import org.springframework.core.ResolvableType;
import org.springframework.core.codec.Encoder;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.NettyDataBufferFactory;
import org.springframework.lang.Nullable;
import org.springframework.messaging.rsocket.PayloadUtils;
import org.springframework.messaging.rsocket.RSocketStrategies;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MimeType;
import org.springframework.util.ObjectUtils;
import reactor.core.publisher.Mono;

final class MetadataEncoder {
    private static final Pattern VARS_PATTERN = Pattern.compile("\\{(.+?)}");
    private static final Object NO_VALUE = new Object();
    private final MimeType metadataMimeType;
    private final RSocketStrategies strategies;
    private final boolean isComposite;
    private final ByteBufAllocator allocator;
    @Nullable
    private String route;
    private final List<MetadataEntry> metadataEntries = new ArrayList<MetadataEntry>(4);
    private boolean hasAsyncValues;

    MetadataEncoder(MimeType metadataMimeType, RSocketStrategies strategies) {
        Assert.notNull((Object)metadataMimeType, (String)"'metadataMimeType' is required");
        Assert.notNull((Object)strategies, (String)"RSocketStrategies is required");
        this.metadataMimeType = metadataMimeType;
        this.strategies = strategies;
        this.isComposite = this.metadataMimeType.toString().equals(WellKnownMimeType.MESSAGE_RSOCKET_COMPOSITE_METADATA.getString());
        this.allocator = this.bufferFactory() instanceof NettyDataBufferFactory ? ((NettyDataBufferFactory)this.bufferFactory()).getByteBufAllocator() : ByteBufAllocator.DEFAULT;
    }

    private DataBufferFactory bufferFactory() {
        return this.strategies.dataBufferFactory();
    }

    public MetadataEncoder route(String route, Object ... routeVars) {
        this.route = MetadataEncoder.expand(route, routeVars);
        this.assertMetadataEntryCount();
        return this;
    }

    private static String expand(String route, Object ... routeVars) {
        if (ObjectUtils.isEmpty((Object[])routeVars)) {
            return route;
        }
        StringBuffer sb = new StringBuffer();
        int index = 0;
        Matcher matcher = VARS_PATTERN.matcher(route);
        while (matcher.find()) {
            Assert.isTrue((index < routeVars.length ? 1 : 0) != 0, () -> "No value for variable '" + matcher.group(1) + "'");
            String value = routeVars[index].toString();
            value = value.contains(".") ? value.replaceAll("\\.", "%2E") : value;
            matcher.appendReplacement(sb, value);
            ++index;
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    private void assertMetadataEntryCount() {
        if (!this.isComposite) {
            int count = this.route != null ? this.metadataEntries.size() + 1 : this.metadataEntries.size();
            Assert.isTrue((count < 2 ? 1 : 0) != 0, (String)"Composite metadata required for multiple metadata entries.");
        }
    }

    public MetadataEncoder metadata(Object metadata, @Nullable MimeType mimeType) {
        if (this.isComposite) {
            Assert.notNull((Object)mimeType, (String)"MimeType is required for composite metadata entries.");
        } else if (mimeType == null) {
            mimeType = this.metadataMimeType;
        } else if (!this.metadataMimeType.equals((Object)mimeType)) {
            throw new IllegalArgumentException("Mime type is optional when not using composite metadata, but it was provided and does not match the connection metadata mime type '" + this.metadataMimeType + "'.");
        }
        ReactiveAdapter adapter = this.strategies.reactiveAdapterRegistry().getAdapter(metadata.getClass());
        if (adapter != null) {
            Assert.isTrue((!adapter.isMultiValue() ? 1 : 0) != 0, (String)("Expected single value: " + metadata));
            metadata = Mono.from((Publisher)adapter.toPublisher(metadata)).defaultIfEmpty(NO_VALUE);
            this.hasAsyncValues = true;
        }
        this.metadataEntries.add(new MetadataEntry(metadata, mimeType));
        this.assertMetadataEntryCount();
        return this;
    }

    public MetadataEncoder metadataAndOrRoute(@Nullable Map<Object, MimeType> metadata, @Nullable String route, @Nullable Object[] vars) {
        if (route != null) {
            this.route = MetadataEncoder.expand(route, vars != null ? vars : new Object[]{});
        }
        if (!CollectionUtils.isEmpty(metadata)) {
            for (Map.Entry<Object, MimeType> entry : metadata.entrySet()) {
                this.metadata(entry.getKey(), entry.getValue());
            }
        }
        this.assertMetadataEntryCount();
        return this;
    }

    public Mono<DataBuffer> encode() {
        return this.hasAsyncValues ? this.resolveAsyncMetadata().map(this::encodeEntries) : Mono.fromCallable(() -> this.encodeEntries(this.metadataEntries));
    }

    private DataBuffer encodeEntries(List<MetadataEntry> entries) {
        if (this.isComposite) {
            CompositeByteBuf composite = this.allocator.compositeBuffer();
            try {
                if (this.route != null) {
                    CompositeMetadataCodec.encodeAndAddMetadata((CompositeByteBuf)composite, (ByteBufAllocator)this.allocator, (WellKnownMimeType)WellKnownMimeType.MESSAGE_RSOCKET_ROUTING, (ByteBuf)this.encodeRoute());
                }
                entries.forEach(entry -> {
                    Object value = entry.value();
                    CompositeMetadataCodec.encodeAndAddMetadata((CompositeByteBuf)composite, (ByteBufAllocator)this.allocator, (String)entry.mimeType().toString(), (ByteBuf)(value instanceof ByteBuf ? (ByteBuf)value : PayloadUtils.asByteBuf(this.encodeEntry((MetadataEntry)entry))));
                });
                return this.asDataBuffer((ByteBuf)composite);
            }
            catch (Throwable ex) {
                composite.release();
                throw ex;
            }
        }
        if (this.route != null) {
            Assert.isTrue((boolean)entries.isEmpty(), (String)"Composite metadata required for route and other entries");
            String routingMimeType = WellKnownMimeType.MESSAGE_RSOCKET_ROUTING.getString();
            return this.metadataMimeType.toString().equals(routingMimeType) ? this.asDataBuffer(this.encodeRoute()) : this.encodeEntry(this.route, this.metadataMimeType);
        }
        Assert.isTrue((entries.size() == 1 ? 1 : 0) != 0, (String)"Composite metadata required for multiple entries");
        MetadataEntry entry2 = entries.get(0);
        if (!this.metadataMimeType.equals((Object)entry2.mimeType())) {
            throw new IllegalArgumentException("Connection configured for metadata mime type '" + this.metadataMimeType + "', but actual is `" + entries + "`");
        }
        return this.encodeEntry(entry2);
    }

    private ByteBuf encodeRoute() {
        return TaggingMetadataCodec.createRoutingMetadata((ByteBufAllocator)this.allocator, Collections.singletonList(this.route)).getContent();
    }

    private <T> DataBuffer encodeEntry(MetadataEntry entry) {
        return this.encodeEntry(entry.value(), entry.mimeType());
    }

    private <T> DataBuffer encodeEntry(Object value, MimeType mimeType) {
        if (value instanceof ByteBuf) {
            return this.asDataBuffer((ByteBuf)value);
        }
        ResolvableType type = ResolvableType.forInstance((Object)value);
        Encoder encoder = this.strategies.encoder(type, mimeType);
        Assert.notNull(encoder, () -> "No encoder for metadata " + value + ", mimeType '" + mimeType + "'");
        return encoder.encodeValue(value, this.bufferFactory(), type, mimeType, Collections.emptyMap());
    }

    private DataBuffer asDataBuffer(ByteBuf byteBuf) {
        if (this.bufferFactory() instanceof NettyDataBufferFactory) {
            return ((NettyDataBufferFactory)this.bufferFactory()).wrap(byteBuf);
        }
        DataBuffer buffer = this.bufferFactory().wrap(byteBuf.nioBuffer());
        byteBuf.release();
        return buffer;
    }

    private Mono<List<MetadataEntry>> resolveAsyncMetadata() {
        Assert.state((boolean)this.hasAsyncValues, (String)"No asynchronous values to resolve");
        ArrayList valueMonos = new ArrayList();
        this.metadataEntries.forEach(entry -> {
            Object v = entry.value();
            valueMonos.add(v instanceof Mono ? (Mono)v : Mono.just((Object)v));
        });
        return Mono.zip(valueMonos, values -> {
            ArrayList<MetadataEntry> result = new ArrayList<MetadataEntry>(((Object[])values).length);
            for (int i2 = 0; i2 < ((Object[])values).length; ++i2) {
                if (values[i2] == NO_VALUE) continue;
                result.add(new MetadataEntry(values[i2], this.metadataEntries.get(i2).mimeType()));
            }
            return result;
        });
    }

    private static class MetadataEntry {
        private final Object value;
        private final MimeType mimeType;

        MetadataEntry(Object value, MimeType mimeType) {
            this.value = value;
            this.mimeType = mimeType;
        }

        public Object value() {
            return this.value;
        }

        public MimeType mimeType() {
            return this.mimeType;
        }
    }
}

