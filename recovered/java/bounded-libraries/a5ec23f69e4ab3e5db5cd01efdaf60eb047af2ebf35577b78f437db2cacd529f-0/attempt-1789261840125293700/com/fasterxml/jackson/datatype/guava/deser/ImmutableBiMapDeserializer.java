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
 *  com.google.common.collect.ImmutableBiMap
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
import com.fasterxml.jackson.datatype.guava.deser.GuavaMapDeserializer;
import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableMap;

public class ImmutableBiMapDeserializer
extends GuavaImmutableMapDeserializer<ImmutableBiMap<Object, Object>> {
    private static final long serialVersionUID = 2L;

    public ImmutableBiMapDeserializer(JavaType type, KeyDeserializer keyDeser, JsonDeserializer<?> deser, TypeDeserializer typeDeser, NullValueProvider nuller) {
        super(type, keyDeser, deser, typeDeser, nuller);
    }

    @Override
    public Object getEmptyValue(DeserializationContext ctxt) throws JsonMappingException {
        return ImmutableBiMap.of();
    }

    @Override
    protected ImmutableMap.Builder<Object, Object> createBuilder() {
        return ImmutableBiMap.builder();
    }

    @Override
    public GuavaMapDeserializer<ImmutableBiMap<Object, Object>> withResolved(KeyDeserializer keyDeser, JsonDeserializer<?> valueDeser, TypeDeserializer typeDeser, NullValueProvider nuller) {
        return new ImmutableBiMapDeserializer(this._containerType, keyDeser, valueDeser, typeDeser, nuller);
    }
}

