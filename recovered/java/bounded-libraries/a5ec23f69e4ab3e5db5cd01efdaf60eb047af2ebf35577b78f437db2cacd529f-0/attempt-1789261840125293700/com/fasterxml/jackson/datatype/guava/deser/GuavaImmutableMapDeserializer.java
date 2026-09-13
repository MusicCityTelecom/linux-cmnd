/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonParser
 *  com.fasterxml.jackson.core.JsonToken
 *  com.fasterxml.jackson.databind.DeserializationContext
 *  com.fasterxml.jackson.databind.JavaType
 *  com.fasterxml.jackson.databind.JsonDeserializer
 *  com.fasterxml.jackson.databind.KeyDeserializer
 *  com.fasterxml.jackson.databind.deser.NullValueProvider
 *  com.fasterxml.jackson.databind.jsontype.TypeDeserializer
 *  com.fasterxml.jackson.databind.util.AccessPattern
 *  com.google.common.collect.ImmutableMap
 *  com.google.common.collect.ImmutableMap$Builder
 */
package com.fasterxml.jackson.datatype.guava.deser;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.KeyDeserializer;
import com.fasterxml.jackson.databind.deser.NullValueProvider;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.util.AccessPattern;
import com.fasterxml.jackson.datatype.guava.deser.GuavaMapDeserializer;
import com.google.common.collect.ImmutableMap;
import java.io.IOException;

abstract class GuavaImmutableMapDeserializer<T extends ImmutableMap<Object, Object>>
extends GuavaMapDeserializer<T> {
    private static final long serialVersionUID = 2L;

    GuavaImmutableMapDeserializer(JavaType type, KeyDeserializer keyDeser, JsonDeserializer<?> valueDeser, TypeDeserializer valueTypeDeser, NullValueProvider nuller) {
        super(type, keyDeser, valueDeser, valueTypeDeser, nuller);
    }

    @Override
    public AccessPattern getEmptyAccessPattern() {
        return AccessPattern.CONSTANT;
    }

    protected abstract ImmutableMap.Builder<Object, Object> createBuilder();

    @Override
    protected T _deserializeEntries(JsonParser p, DeserializationContext ctxt) throws IOException {
        KeyDeserializer keyDes = this._keyDeserializer;
        JsonDeserializer valueDes = this._valueDeserializer;
        TypeDeserializer typeDeser = this._valueTypeDeserializer;
        ImmutableMap.Builder<Object, Object> builder = this.createBuilder();
        while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
            Object value;
            String fieldName = p.getCurrentName();
            String key = keyDes == null ? fieldName : keyDes.deserializeKey(fieldName, ctxt);
            JsonToken t = p.nextToken();
            if (t == JsonToken.VALUE_NULL) {
                if (!this._skipNullValues && (value = this._nullProvider.getNullValue(ctxt)) != null) {
                    builder.put((Object)key, value);
                }
            } else {
                value = typeDeser == null ? valueDes.deserialize(p, ctxt) : valueDes.deserializeWithType(p, ctxt, typeDeser);
                builder.put((Object)key, value);
            }
            p.nextToken();
        }
        ImmutableMap map = builder.build();
        return (T)map;
    }
}

