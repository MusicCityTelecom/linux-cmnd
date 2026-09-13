/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.JavaType
 *  com.fasterxml.jackson.databind.JsonDeserializer
 *  com.fasterxml.jackson.databind.deser.NullValueProvider
 *  com.fasterxml.jackson.databind.jsontype.TypeDeserializer
 *  com.google.common.collect.HashMultiset
 */
package com.fasterxml.jackson.datatype.guava.deser;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.NullValueProvider;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.datatype.guava.deser.GuavaMultisetDeserializer;
import com.google.common.collect.HashMultiset;

public class HashMultisetDeserializer
extends GuavaMultisetDeserializer<HashMultiset<Object>> {
    private static final long serialVersionUID = 1L;

    public HashMultisetDeserializer(JavaType selfType, JsonDeserializer<?> deser, TypeDeserializer typeDeser, NullValueProvider nuller, Boolean unwrapSingle) {
        super(selfType, deser, typeDeser, nuller, unwrapSingle);
    }

    public HashMultisetDeserializer withResolved(JsonDeserializer<?> valueDeser, TypeDeserializer typeDeser, NullValueProvider nuller, Boolean unwrapSingle) {
        return new HashMultisetDeserializer(this._containerType, valueDeser, typeDeser, nuller, unwrapSingle);
    }

    @Override
    protected HashMultiset<Object> createMultiset() {
        return HashMultiset.create();
    }
}

