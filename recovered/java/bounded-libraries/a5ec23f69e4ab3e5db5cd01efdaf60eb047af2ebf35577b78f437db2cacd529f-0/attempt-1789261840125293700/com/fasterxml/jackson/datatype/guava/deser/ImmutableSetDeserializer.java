/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.DeserializationContext
 *  com.fasterxml.jackson.databind.JavaType
 *  com.fasterxml.jackson.databind.JsonDeserializer
 *  com.fasterxml.jackson.databind.deser.NullValueProvider
 *  com.fasterxml.jackson.databind.jsontype.TypeDeserializer
 *  com.google.common.collect.ImmutableCollection$Builder
 *  com.google.common.collect.ImmutableSet
 */
package com.fasterxml.jackson.datatype.guava.deser;

import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.NullValueProvider;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.datatype.guava.deser.GuavaImmutableCollectionDeserializer;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableSet;

public class ImmutableSetDeserializer
extends GuavaImmutableCollectionDeserializer<ImmutableSet<Object>> {
    private static final long serialVersionUID = 1L;

    public ImmutableSetDeserializer(JavaType selfType, JsonDeserializer<?> deser, TypeDeserializer typeDeser, NullValueProvider nuller, Boolean unwrapSingle) {
        super(selfType, deser, typeDeser, nuller, unwrapSingle);
    }

    public ImmutableSetDeserializer withResolved(JsonDeserializer<?> valueDeser, TypeDeserializer typeDeser, NullValueProvider nuller, Boolean unwrapSingle) {
        return new ImmutableSetDeserializer(this._containerType, valueDeser, typeDeser, nuller, unwrapSingle);
    }

    @Override
    protected ImmutableCollection.Builder<Object> createBuilder() {
        return ImmutableSet.builder();
    }

    @Override
    protected ImmutableSet<Object> _createEmpty(DeserializationContext ctxt) {
        return ImmutableSet.of();
    }

    @Override
    protected ImmutableSet<Object> _createWithSingleElement(DeserializationContext ctxt, Object value) {
        return ImmutableSet.of((Object)value);
    }
}

