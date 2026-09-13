/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.DeserializationContext
 *  com.fasterxml.jackson.databind.JavaType
 *  com.fasterxml.jackson.databind.JsonDeserializer
 *  com.fasterxml.jackson.databind.deser.NullValueProvider
 *  com.fasterxml.jackson.databind.jsontype.TypeDeserializer
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.ImmutableList$Builder
 */
package com.fasterxml.jackson.datatype.guava.deser;

import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.NullValueProvider;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.datatype.guava.deser.GuavaImmutableCollectionDeserializer;
import com.google.common.collect.ImmutableList;

public class ImmutableListDeserializer
extends GuavaImmutableCollectionDeserializer<ImmutableList<Object>> {
    private static final long serialVersionUID = 1L;

    public ImmutableListDeserializer(JavaType selfType, JsonDeserializer<?> deser, TypeDeserializer typeDeser, NullValueProvider nuller, Boolean unwrapSingle) {
        super(selfType, deser, typeDeser, nuller, unwrapSingle);
    }

    public ImmutableListDeserializer withResolved(JsonDeserializer<?> valueDeser, TypeDeserializer typeDeser, NullValueProvider nuller, Boolean unwrapSingle) {
        return new ImmutableListDeserializer(this._containerType, valueDeser, typeDeser, nuller, unwrapSingle);
    }

    protected ImmutableList.Builder<Object> createBuilder() {
        return ImmutableList.builder();
    }

    @Override
    protected ImmutableList<Object> _createEmpty(DeserializationContext ctxt) {
        return ImmutableList.of();
    }

    @Override
    protected ImmutableList<Object> _createWithSingleElement(DeserializationContext ctxt, Object value) {
        return ImmutableList.of((Object)value);
    }
}

