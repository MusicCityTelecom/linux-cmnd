/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Publisher
 *  org.springframework.core.ResolvableType
 *  org.springframework.core.convert.converter.Converter
 *  org.springframework.core.serializer.Deserializer
 *  org.springframework.core.serializer.Serializer
 *  org.springframework.expression.Expression
 *  org.springframework.expression.spel.standard.SpelExpressionParser
 *  org.springframework.lang.Nullable
 *  org.springframework.messaging.Message
 *  reactor.core.publisher.Flux
 *  reactor.core.publisher.Mono
 */
package org.springframework.integration.dsl;

import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import org.reactivestreams.Publisher;
import org.springframework.core.ResolvableType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.serializer.Deserializer;
import org.springframework.core.serializer.Serializer;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.integration.codec.Codec;
import org.springframework.integration.expression.FunctionExpression;
import org.springframework.integration.json.JsonToObjectTransformer;
import org.springframework.integration.json.ObjectToJsonTransformer;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.integration.support.json.JsonObjectMapper;
import org.springframework.integration.transformer.DecodingTransformer;
import org.springframework.integration.transformer.EncodingPayloadTransformer;
import org.springframework.integration.transformer.MapToObjectTransformer;
import org.springframework.integration.transformer.ObjectToMapTransformer;
import org.springframework.integration.transformer.ObjectToStringTransformer;
import org.springframework.integration.transformer.PayloadDeserializingTransformer;
import org.springframework.integration.transformer.PayloadSerializingTransformer;
import org.springframework.integration.transformer.PayloadTypeConvertingTransformer;
import org.springframework.integration.transformer.StreamTransformer;
import org.springframework.integration.transformer.SyslogToMapTransformer;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public abstract class Transformers {
    private static final SpelExpressionParser PARSER = new SpelExpressionParser();

    public static ObjectToStringTransformer objectToString() {
        return Transformers.objectToString(null);
    }

    public static ObjectToStringTransformer objectToString(@Nullable String charset) {
        return charset != null ? new ObjectToStringTransformer(charset) : new ObjectToStringTransformer();
    }

    public static ObjectToMapTransformer toMap() {
        return new ObjectToMapTransformer();
    }

    public static ObjectToMapTransformer toMap(boolean shouldFlattenKeys) {
        ObjectToMapTransformer transformer = new ObjectToMapTransformer();
        transformer.setShouldFlattenKeys(shouldFlattenKeys);
        return transformer;
    }

    public static ObjectToMapTransformer toMap(JsonObjectMapper<?, ?> jsonObjectMapper) {
        return new ObjectToMapTransformer(jsonObjectMapper);
    }

    public static ObjectToMapTransformer toMap(JsonObjectMapper<?, ?> jsonObjectMapper, boolean shouldFlattenKeys) {
        ObjectToMapTransformer transformer = new ObjectToMapTransformer(jsonObjectMapper);
        transformer.setShouldFlattenKeys(shouldFlattenKeys);
        return transformer;
    }

    public static MapToObjectTransformer fromMap(Class<?> targetClass) {
        return new MapToObjectTransformer(targetClass);
    }

    public static MapToObjectTransformer fromMap(String beanName) {
        return new MapToObjectTransformer(beanName);
    }

    public static ObjectToJsonTransformer toJson() {
        return Transformers.toJson(null, null, null);
    }

    public static ObjectToJsonTransformer toJson(@Nullable JsonObjectMapper<?, ?> jsonObjectMapper) {
        return Transformers.toJson(jsonObjectMapper, null, null);
    }

    public static ObjectToJsonTransformer toJson(@Nullable JsonObjectMapper<?, ?> jsonObjectMapper, @Nullable ObjectToJsonTransformer.ResultType resultType) {
        return Transformers.toJson(jsonObjectMapper, resultType, null);
    }

    public static ObjectToJsonTransformer toJson(@Nullable String contentType) {
        return Transformers.toJson(null, null, contentType);
    }

    public static ObjectToJsonTransformer toJson(@Nullable JsonObjectMapper<?, ?> jsonObjectMapper, @Nullable String contentType) {
        return Transformers.toJson(jsonObjectMapper, null, contentType);
    }

    public static ObjectToJsonTransformer toJson(@Nullable ObjectToJsonTransformer.ResultType resultType) {
        return Transformers.toJson(null, resultType, null);
    }

    public static ObjectToJsonTransformer toJson(@Nullable ObjectToJsonTransformer.ResultType resultType, @Nullable String contentType) {
        return Transformers.toJson(null, resultType, contentType);
    }

    public static ObjectToJsonTransformer toJson(@Nullable JsonObjectMapper<?, ?> jsonObjectMapper, @Nullable ObjectToJsonTransformer.ResultType resultType, @Nullable String contentType) {
        ObjectToJsonTransformer transformer = jsonObjectMapper != null ? (resultType != null ? new ObjectToJsonTransformer(jsonObjectMapper, resultType) : new ObjectToJsonTransformer(jsonObjectMapper)) : (resultType != null ? new ObjectToJsonTransformer(resultType) : new ObjectToJsonTransformer());
        if (contentType != null) {
            transformer.setContentType(contentType);
        }
        return transformer;
    }

    public static JsonToObjectTransformer fromJson() {
        return Transformers.fromJson((Class)null, null);
    }

    public static JsonToObjectTransformer fromJson(@Nullable Class<?> targetClass) {
        return Transformers.fromJson(targetClass, null);
    }

    public static JsonToObjectTransformer fromJson(ResolvableType targetType) {
        return Transformers.fromJson(targetType, null);
    }

    public static JsonToObjectTransformer fromJson(@Nullable JsonObjectMapper<?, ?> jsonObjectMapper) {
        return Transformers.fromJson((Class)null, jsonObjectMapper);
    }

    public static JsonToObjectTransformer fromJson(@Nullable Class<?> targetClass, @Nullable JsonObjectMapper<?, ?> jsonObjectMapper) {
        return new JsonToObjectTransformer(targetClass, jsonObjectMapper);
    }

    public static JsonToObjectTransformer fromJson(ResolvableType targetType, @Nullable JsonObjectMapper<?, ?> jsonObjectMapper) {
        return new JsonToObjectTransformer(targetType, jsonObjectMapper);
    }

    public static PayloadSerializingTransformer serializer() {
        return Transformers.serializer(null);
    }

    public static PayloadSerializingTransformer serializer(@Nullable Serializer<Object> serializer) {
        PayloadSerializingTransformer transformer = new PayloadSerializingTransformer();
        if (serializer != null) {
            transformer.setSerializer(serializer);
        }
        return transformer;
    }

    public static PayloadDeserializingTransformer deserializer(String ... allowedPatterns) {
        return Transformers.deserializer(null, allowedPatterns);
    }

    public static PayloadDeserializingTransformer deserializer(@Nullable Deserializer<Object> deserializer, String ... allowedPatterns) {
        PayloadDeserializingTransformer transformer = new PayloadDeserializingTransformer();
        transformer.setAllowedPatterns(allowedPatterns);
        if (deserializer != null) {
            transformer.setDeserializer(deserializer);
        }
        return transformer;
    }

    public static <T, U> PayloadTypeConvertingTransformer<T, U> converter(Converter<T, U> converter) {
        PayloadTypeConvertingTransformer<T, U> transformer = new PayloadTypeConvertingTransformer<T, U>();
        transformer.setConverter(converter);
        return transformer;
    }

    public static SyslogToMapTransformer syslogToMap() {
        return new SyslogToMapTransformer();
    }

    public static <T> EncodingPayloadTransformer<T> encoding(Codec codec) {
        return new EncodingPayloadTransformer(codec);
    }

    public static <T> DecodingTransformer<T> decoding(Codec codec, Class<T> type) {
        return new DecodingTransformer<T>(codec, type);
    }

    public static <T> DecodingTransformer<T> decoding(Codec codec, String typeExpression) {
        return Transformers.decoding(codec, PARSER.parseExpression(typeExpression));
    }

    public static <T> DecodingTransformer<T> decoding(Codec codec, Function<Message<?>, Class<T>> typeFunction) {
        return Transformers.decoding(codec, new FunctionExpression(typeFunction));
    }

    public static <T> DecodingTransformer<T> decoding(Codec codec, Expression typeExpression) {
        return new DecodingTransformer(codec, typeExpression);
    }

    public static StreamTransformer fromStream() {
        return Transformers.fromStream(null);
    }

    public static StreamTransformer fromStream(@Nullable String charset) {
        return new StreamTransformer(charset);
    }

    static <I, O> Flux<Message<O>> transformWithFunction(Publisher<Message<I>> publisher, Function<? super Flux<Message<I>>, ? extends Publisher<O>> fluxFunction) {
        return Flux.from(publisher).flatMap(message -> Mono.deferContextual(ctx -> {
            ((RequestMessageHolder)ctx.get(RequestMessageHolder.class)).set(message);
            return Mono.just((Object)message);
        })).transform(fluxFunction).flatMap(data -> data instanceof Message ? Mono.just((Object)((Message)data)) : Mono.deferContextual(ctx -> Mono.just((Object)((Message)((RequestMessageHolder)ctx.get(RequestMessageHolder.class)).get()))).map(requestMessage -> ((MessageBuilder)MessageBuilder.withPayload(data).copyHeaders((Map)requestMessage.getHeaders())).build())).contextWrite(ctx -> ctx.put(RequestMessageHolder.class, (Object)new RequestMessageHolder()));
    }

    private static class RequestMessageHolder
    extends AtomicReference<Message<?>> {
        private RequestMessageHolder() {
        }
    }
}

