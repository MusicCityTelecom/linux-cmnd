/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlinx.serialization.KSerializer
 *  kotlinx.serialization.SerializersKt
 *  kotlinx.serialization.json.Json
 *  org.springframework.util.ConcurrentReferenceHashMap
 *  org.springframework.util.FileCopyUtils
 */
package org.springframework.messaging.converter;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.Map;
import kotlinx.serialization.KSerializer;
import kotlinx.serialization.SerializersKt;
import kotlinx.serialization.json.Json;
import org.springframework.messaging.converter.AbstractJsonMessageConverter;
import org.springframework.messaging.converter.MessageConversionException;
import org.springframework.util.ConcurrentReferenceHashMap;
import org.springframework.util.FileCopyUtils;

public class KotlinSerializationJsonMessageConverter
extends AbstractJsonMessageConverter {
    private static final Map<Type, KSerializer<Object>> serializerCache = new ConcurrentReferenceHashMap();
    private final Json json;

    public KotlinSerializationJsonMessageConverter() {
        this((Json)Json.Default);
    }

    public KotlinSerializationJsonMessageConverter(Json json) {
        this.json = json;
    }

    @Override
    protected Object fromJson(Reader reader, Type resolvedType) {
        try {
            return this.fromJson(FileCopyUtils.copyToString((Reader)reader), resolvedType);
        }
        catch (IOException ex) {
            throw new MessageConversionException("Could not read JSON: " + ex.getMessage(), (Throwable)ex);
        }
    }

    @Override
    protected Object fromJson(String payload, Type resolvedType) {
        return this.json.decodeFromString(this.serializer(resolvedType), payload);
    }

    @Override
    protected void toJson(Object payload, Type resolvedType, Writer writer) {
        try {
            writer.write(this.toJson(payload, resolvedType).toCharArray());
        }
        catch (IOException ex) {
            throw new MessageConversionException("Could not write JSON: " + ex.getMessage(), (Throwable)ex);
        }
    }

    @Override
    protected String toJson(Object payload, Type resolvedType) {
        return this.json.encodeToString(this.serializer(resolvedType), payload);
    }

    private KSerializer<Object> serializer(Type type) {
        KSerializer serializer = serializerCache.get(type);
        if (serializer == null) {
            serializer = SerializersKt.serializer((Type)type);
            serializerCache.put(type, (KSerializer<Object>)serializer);
        }
        return serializer;
    }
}

