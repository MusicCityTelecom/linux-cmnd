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
 *  com.google.common.collect.ImmutableSortedSet
 *  com.google.common.collect.ImmutableSortedSet$Builder
 */
package com.fasterxml.jackson.datatype.guava.deser;

import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.NullValueProvider;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.datatype.guava.deser.GuavaImmutableCollectionDeserializer;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableSortedSet;

public class ImmutableSortedSetDeserializer
extends GuavaImmutableCollectionDeserializer<ImmutableSortedSet<Object>> {
    private static final long serialVersionUID = 1L;

    public ImmutableSortedSetDeserializer(JavaType selfType, JsonDeserializer<?> deser, TypeDeserializer typeDeser, NullValueProvider nuller, Boolean unwrapSingle) {
        super(selfType, deser, typeDeser, nuller, unwrapSingle);
    }

    public ImmutableSortedSetDeserializer withResolved(JsonDeserializer<?> valueDeser, TypeDeserializer typeDeser, NullValueProvider nuller, Boolean unwrapSingle) {
        return new ImmutableSortedSetDeserializer(this._containerType, valueDeser, typeDeser, nuller, unwrapSingle);
    }

    @Override
    protected ImmutableCollection.Builder<Object> createBuilder() {
        ImmutableSortedSet.Builder builderComp;
        ImmutableSortedSet.Builder builder = builderComp = ImmutableSortedSet.naturalOrder();
        return builder;
    }

    @Override
    protected ImmutableSortedSet<Object> _createEmpty(DeserializationContext ctxt) {
        return ImmutableSortedSet.of();
    }

    @Override
    protected ImmutableSortedSet<Object> _createWithSingleElement(DeserializationContext ctxt, Object value) {
        return (ImmutableSortedSet)this.createBuilder().add(value).build();
    }
}

