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
 *  com.google.common.collect.ImmutableSortedMultiset
 *  com.google.common.collect.ImmutableSortedMultiset$Builder
 */
package com.fasterxml.jackson.datatype.guava.deser;

import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.NullValueProvider;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.datatype.guava.deser.GuavaCollectionDeserializer;
import com.fasterxml.jackson.datatype.guava.deser.GuavaImmutableCollectionDeserializer;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableSortedMultiset;

public class ImmutableSortedMultisetDeserializer
extends GuavaImmutableCollectionDeserializer<ImmutableSortedMultiset<Object>> {
    private static final long serialVersionUID = 1L;

    public ImmutableSortedMultisetDeserializer(JavaType selfType, JsonDeserializer<?> deser, TypeDeserializer typeDeser, NullValueProvider nuller, Boolean unwrapSingle) {
        super(selfType, deser, typeDeser, nuller, unwrapSingle);
    }

    @Override
    public GuavaCollectionDeserializer<ImmutableSortedMultiset<Object>> withResolved(JsonDeserializer<?> valueDeser, TypeDeserializer typeDeser, NullValueProvider nuller, Boolean unwrapSingle) {
        return new ImmutableSortedMultisetDeserializer(this._containerType, valueDeser, typeDeser, nuller, unwrapSingle);
    }

    @Override
    protected ImmutableCollection.Builder<Object> createBuilder() {
        ImmutableSortedMultiset.Builder builder = ImmutableSortedMultiset.naturalOrder();
        return builder;
    }

    @Override
    protected ImmutableSortedMultiset<Object> _createEmpty(DeserializationContext ctxt) {
        return ImmutableSortedMultiset.of();
    }

    @Override
    protected ImmutableSortedMultiset<Object> _createWithSingleElement(DeserializationContext ctxt, Object value) {
        return (ImmutableSortedMultiset)this.createBuilder().add(value).build();
    }
}

