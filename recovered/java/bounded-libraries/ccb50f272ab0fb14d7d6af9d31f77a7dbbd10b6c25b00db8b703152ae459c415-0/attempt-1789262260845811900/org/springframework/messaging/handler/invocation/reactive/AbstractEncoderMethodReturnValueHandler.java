/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.reactivestreams.Publisher
 *  org.springframework.core.KotlinDetector
 *  org.springframework.core.MethodParameter
 *  org.springframework.core.ReactiveAdapter
 *  org.springframework.core.ReactiveAdapterRegistry
 *  org.springframework.core.ResolvableType
 *  org.springframework.core.codec.Encoder
 *  org.springframework.core.io.buffer.DataBuffer
 *  org.springframework.core.io.buffer.DataBufferFactory
 *  org.springframework.core.io.buffer.DefaultDataBufferFactory
 *  org.springframework.lang.Nullable
 *  org.springframework.util.Assert
 *  org.springframework.util.MimeType
 *  reactor.core.publisher.Flux
 *  reactor.core.publisher.Mono
 */
package org.springframework.messaging.handler.invocation.reactive;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.reactivestreams.Publisher;
import org.springframework.core.KotlinDetector;
import org.springframework.core.MethodParameter;
import org.springframework.core.ReactiveAdapter;
import org.springframework.core.ReactiveAdapterRegistry;
import org.springframework.core.ResolvableType;
import org.springframework.core.codec.Encoder;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.handler.invocation.reactive.ChannelSendOperator;
import org.springframework.messaging.handler.invocation.reactive.HandlerMethodReturnValueHandler;
import org.springframework.util.Assert;
import org.springframework.util.MimeType;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public abstract class AbstractEncoderMethodReturnValueHandler
implements HandlerMethodReturnValueHandler {
    private static final ResolvableType VOID_RESOLVABLE_TYPE = ResolvableType.forClass(Void.class);
    private static final ResolvableType OBJECT_RESOLVABLE_TYPE = ResolvableType.forClass(Object.class);
    private static final String COROUTINES_FLOW_CLASS_NAME = "kotlinx.coroutines.flow.Flow";
    protected final Log logger = LogFactory.getLog(this.getClass());
    private final List<Encoder<?>> encoders;
    private final ReactiveAdapterRegistry adapterRegistry;

    protected AbstractEncoderMethodReturnValueHandler(List<Encoder<?>> encoders, ReactiveAdapterRegistry registry) {
        Assert.notEmpty(encoders, (String)"At least one Encoder is required");
        Assert.notNull((Object)registry, (String)"ReactiveAdapterRegistry is required");
        this.encoders = Collections.unmodifiableList(encoders);
        this.adapterRegistry = registry;
    }

    public List<Encoder<?>> getEncoders() {
        return this.encoders;
    }

    public ReactiveAdapterRegistry getAdapterRegistry() {
        return this.adapterRegistry;
    }

    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        return true;
    }

    @Override
    public Mono<Void> handleReturnValue(@Nullable Object returnValue, MethodParameter returnType, Message<?> message) {
        if (returnValue == null) {
            return this.handleNoContent(returnType, message);
        }
        DataBufferFactory bufferFactory = (DataBufferFactory)message.getHeaders().getOrDefault("dataBufferFactory", DefaultDataBufferFactory.sharedInstance);
        MimeType mimeType = (MimeType)message.getHeaders().get("contentType");
        Flux<DataBuffer> encodedContent = this.encodeContent(returnValue, returnType, bufferFactory, mimeType, Collections.emptyMap());
        return new ChannelSendOperator<DataBuffer>((Publisher<DataBuffer>)encodedContent, publisher -> this.handleEncodedContent((Flux<DataBuffer>)Flux.from((Publisher)publisher), returnType, message));
    }

    private Flux<DataBuffer> encodeContent(@Nullable Object content, MethodParameter returnType, DataBufferFactory bufferFactory, @Nullable MimeType mimeType, Map<String, Object> hints) {
        ResolvableType elementType;
        Mono publisher;
        ResolvableType returnValueType = ResolvableType.forMethodParameter((MethodParameter)returnType);
        ReactiveAdapter adapter = this.getAdapterRegistry().getAdapter(returnValueType.resolve(), content);
        if (adapter != null) {
            publisher = adapter.toPublisher(content);
            Method method = returnType.getMethod();
            boolean isUnwrapped = method != null && KotlinDetector.isSuspendingFunction((Method)method) && !COROUTINES_FLOW_CLASS_NAME.equals(returnValueType.toClass().getName());
            ResolvableType genericType = isUnwrapped ? returnValueType : returnValueType.getGeneric(new int[0]);
            elementType = this.getElementType(adapter, genericType);
        } else {
            publisher = Mono.justOrEmpty((Object)content);
            ResolvableType resolvableType = elementType = returnValueType.toClass() == Object.class && content != null ? ResolvableType.forInstance((Object)content) : returnValueType;
        }
        if (elementType.resolve() == Void.TYPE || elementType.resolve() == Void.class) {
            return Flux.from((Publisher)publisher).cast(DataBuffer.class);
        }
        Encoder encoder = this.getEncoder(elementType, mimeType);
        return Flux.from((Publisher)publisher).map(value -> this.encodeValue(value, elementType, encoder, bufferFactory, mimeType, hints));
    }

    private ResolvableType getElementType(ReactiveAdapter adapter, ResolvableType type) {
        if (adapter.isNoValue()) {
            return VOID_RESOLVABLE_TYPE;
        }
        if (type != ResolvableType.NONE) {
            return type;
        }
        return OBJECT_RESOLVABLE_TYPE;
    }

    @Nullable
    private <T> Encoder<T> getEncoder(ResolvableType elementType, @Nullable MimeType mimeType) {
        for (Encoder<?> encoder : this.getEncoders()) {
            if (!encoder.canEncode(elementType, mimeType)) continue;
            return encoder;
        }
        return null;
    }

    private <T> DataBuffer encodeValue(Object element, ResolvableType elementType, @Nullable Encoder<T> encoder, DataBufferFactory bufferFactory, @Nullable MimeType mimeType, @Nullable Map<String, Object> hints) {
        if (encoder == null && (encoder = this.getEncoder(ResolvableType.forInstance((Object)element), mimeType)) == null) {
            throw new MessagingException("No encoder for " + elementType + ", current value type is " + element.getClass());
        }
        return encoder.encodeValue(element, bufferFactory, elementType, mimeType, hints);
    }

    protected abstract Mono<Void> handleEncodedContent(Flux<DataBuffer> var1, MethodParameter var2, Message<?> var3);

    protected abstract Mono<Void> handleNoContent(MethodParameter var1, Message<?> var2);
}

