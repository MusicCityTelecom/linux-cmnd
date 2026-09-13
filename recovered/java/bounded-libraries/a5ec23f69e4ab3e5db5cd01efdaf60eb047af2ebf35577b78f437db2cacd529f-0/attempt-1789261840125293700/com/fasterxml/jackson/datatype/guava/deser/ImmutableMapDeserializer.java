/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.DeserializationContext
 *  com.fasterxml.jackson.databind.JavaType
 *  com.fasterxml.jackson.databind.JsonDeserializer
 *  com.fasterxml.jackson.databind.JsonMappingException
 *  com.fasterxml.jackson.databind.KeyDeserializer
 *  com.fasterxml.jackson.databind.deser.NullValueProvider
 *  com.fasterxml.jackson.databind.jsontype.TypeDeserializer
 *  com.google.common.collect.ImmutableMap
 *  com.google.common.collect.ImmutableMap$Builder
 */
package com.fasterxml.jackson.datatype.guava.deser;

import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.KeyDeserializer;
import com.fasterxml.jackson.databind.deser.NullValueProvider;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.datatype.guava.deser.GuavaImmutableMapDeserializer;
import com.google.common.collect.ImmutableMap;

public class ImmutableMapDeserializer
extends GuavaImmutableMapDeserializer<ImmutableMap<Object, Object>> {
    private static final long serialVersionUID = 2L;

    public ImmutableMapDeserializer(JavaType type, KeyDeserializer keyDeser, JsonDeserializer<?> valueDeser, TypeDeserializer valueTypeDeser, NullValueProvider nuller) {
        super(type, keyDeser, valueDeser, valueTypeDeser, nuller);
    }

    @Override
    public Object getEmptyValue(DeserializationContext ctxt) throws JsonMappingException {
        return ImmutableMap.of();
    }

    public ImmutableMapDeserializer withResolved(KeyDeserializer keyDeser, JsonDeserializer<?> valueDeser, TypeDeserializer valueTypeDeser, NullValueProvider nuller) {
        return new ImmutableMapDeserializer(this._containerType, keyDeser, valueDeser, valueTypeDeser, nuller);
    }

    @Override
    protected ImmutableMap.Builder<Object, Object> createBuilder() {
        return ImmutableMap.builder();
    }
}

