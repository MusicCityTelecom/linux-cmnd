/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonParseException
 *  com.fasterxml.jackson.core.JsonParser
 *  com.fasterxml.jackson.databind.BeanProperty
 *  com.fasterxml.jackson.databind.DeserializationContext
 *  com.fasterxml.jackson.databind.JavaType
 *  com.fasterxml.jackson.databind.JsonDeserializer
 *  com.fasterxml.jackson.databind.deser.ContextualDeserializer
 *  com.fasterxml.jackson.databind.type.LogicalType
 *  com.google.common.collect.ImmutableRangeSet
 *  com.google.common.collect.ImmutableRangeSet$Builder
 *  com.google.common.collect.Range
 *  com.google.common.collect.RangeSet
 */
package com.fasterxml.jackson.datatype.guava.deser;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.fasterxml.jackson.databind.type.LogicalType;
import com.google.common.collect.ImmutableRangeSet;
import com.google.common.collect.Range;
import com.google.common.collect.RangeSet;
import java.io.IOException;
import java.util.List;

public class RangeSetDeserializer
extends JsonDeserializer<RangeSet<Comparable<?>>>
implements ContextualDeserializer {
    private JavaType genericRangeListType;

    public LogicalType logicalType() {
        return LogicalType.Collection;
    }

    public RangeSet<Comparable<?>> deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        if (this.genericRangeListType == null) {
            throw new JsonParseException(p, "RangeSetJsonSerializer was not contextualized (no deserialize target type). You need to specify the generic type down to the generic parameter of RangeSet.");
        }
        Iterable ranges = (Iterable)ctxt.findContextualValueDeserializer(this.genericRangeListType, null).deserialize(p, ctxt);
        ImmutableRangeSet.Builder builder = ImmutableRangeSet.builder();
        for (Range range : ranges) {
            builder.add(range);
        }
        return builder.build();
    }

    public JsonDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property) {
        JavaType genericType = ctxt.getContextualType().containedType(0);
        if (genericType == null) {
            return this;
        }
        RangeSetDeserializer deserializer = new RangeSetDeserializer();
        deserializer.genericRangeListType = ctxt.getTypeFactory().constructCollectionType(List.class, ctxt.getTypeFactory().constructParametricType(Range.class, new JavaType[]{genericType}));
        return deserializer;
    }
}

