/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.JsonDeserializer
 *  com.fasterxml.jackson.databind.KeyDeserializer
 *  com.fasterxml.jackson.databind.deser.NullValueProvider
 *  com.fasterxml.jackson.databind.jsontype.TypeDeserializer
 *  com.fasterxml.jackson.databind.type.MapLikeType
 *  com.google.common.collect.LinkedListMultimap
 */
package com.fasterxml.jackson.datatype.guava.deser.multimap.list;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.KeyDeserializer;
import com.fasterxml.jackson.databind.deser.NullValueProvider;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.type.MapLikeType;
import com.fasterxml.jackson.datatype.guava.deser.multimap.GuavaMultimapDeserializer;
import com.google.common.collect.LinkedListMultimap;
import java.lang.reflect.Method;

public class LinkedListMultimapDeserializer
extends GuavaMultimapDeserializer<LinkedListMultimap<Object, Object>> {
    private static final long serialVersionUID = 1L;

    public LinkedListMultimapDeserializer(MapLikeType type, KeyDeserializer keyDeserializer, TypeDeserializer elementTypeDeserializer, JsonDeserializer<?> elementDeserializer) {
        super(type, keyDeserializer, elementTypeDeserializer, elementDeserializer);
    }

    public LinkedListMultimapDeserializer(MapLikeType type, KeyDeserializer keyDeserializer, TypeDeserializer elementTypeDeserializer, JsonDeserializer<?> elementDeserializer, Method creatorMethod, NullValueProvider nvp) {
        super(type, keyDeserializer, elementTypeDeserializer, elementDeserializer, creatorMethod, nvp);
    }

    @Override
    protected LinkedListMultimap<Object, Object> createMultimap() {
        return LinkedListMultimap.create();
    }

    @Override
    protected JsonDeserializer<?> _createContextual(MapLikeType type, KeyDeserializer keyDeserializer, TypeDeserializer typeDeserializer, JsonDeserializer<?> elementDeserializer, Method method, NullValueProvider nvp) {
        return new LinkedListMultimapDeserializer(type, keyDeserializer, typeDeserializer, elementDeserializer, method, nvp);
    }
}

