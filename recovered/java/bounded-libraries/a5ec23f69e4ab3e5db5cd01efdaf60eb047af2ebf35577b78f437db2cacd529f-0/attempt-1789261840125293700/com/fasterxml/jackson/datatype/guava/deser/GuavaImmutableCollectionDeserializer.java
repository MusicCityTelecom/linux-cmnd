/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonParser
 *  com.fasterxml.jackson.core.JsonToken
 *  com.fasterxml.jackson.databind.DeserializationConfig
 *  com.fasterxml.jackson.databind.DeserializationContext
 *  com.fasterxml.jackson.databind.JavaType
 *  com.fasterxml.jackson.databind.JsonDeserializer
 *  com.fasterxml.jackson.databind.deser.NullValueProvider
 *  com.fasterxml.jackson.databind.jsontype.TypeDeserializer
 *  com.fasterxml.jackson.databind.util.AccessPattern
 *  com.google.common.collect.ImmutableCollection
 *  com.google.common.collect.ImmutableCollection$Builder
 */
package com.fasterxml.jackson.datatype.guava.deser;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.NullValueProvider;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.util.AccessPattern;
import com.fasterxml.jackson.datatype.guava.deser.GuavaCollectionDeserializer;
import com.google.common.collect.ImmutableCollection;
import java.io.IOException;

abstract class GuavaImmutableCollectionDeserializer<T extends ImmutableCollection<Object>>
extends GuavaCollectionDeserializer<T> {
    private static final long serialVersionUID = 1L;

    GuavaImmutableCollectionDeserializer(JavaType selfType, JsonDeserializer<?> deser, TypeDeserializer typeDeser, NullValueProvider nuller, Boolean unwrapSingle) {
        super(selfType, deser, typeDeser, nuller, unwrapSingle);
    }

    protected abstract ImmutableCollection.Builder<Object> createBuilder();

    public Boolean supportsUpdate(DeserializationConfig config) {
        return Boolean.FALSE;
    }

    @Override
    public AccessPattern getEmptyAccessPattern() {
        return AccessPattern.CONSTANT;
    }

    @Override
    public T getEmptyValue(DeserializationContext ctxt) {
        return (T)((ImmutableCollection)this._createEmpty(ctxt));
    }

    @Override
    protected T _deserializeContents(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonToken t;
        JsonDeserializer valueDes = this._valueDeserializer;
        TypeDeserializer typeDeser = this._valueTypeDeserializer;
        ImmutableCollection.Builder<Object> builder = this.createBuilder();
        while ((t = p.nextToken()) != JsonToken.END_ARRAY) {
            Object value;
            if (t == JsonToken.VALUE_NULL) {
                if (this._skipNullValues) continue;
                value = this._resolveNullToValue(ctxt);
            } else {
                value = typeDeser == null ? valueDes.deserialize(p, ctxt) : valueDes.deserializeWithType(p, ctxt, typeDeser);
            }
            builder.add(value);
        }
        ImmutableCollection collection = builder.build();
        return (T)collection;
    }

    protected Object _resolveNullToValue(DeserializationContext ctxt) throws IOException {
        Object value = this._nullProvider.getNullValue(ctxt);
        return value;
    }
}

