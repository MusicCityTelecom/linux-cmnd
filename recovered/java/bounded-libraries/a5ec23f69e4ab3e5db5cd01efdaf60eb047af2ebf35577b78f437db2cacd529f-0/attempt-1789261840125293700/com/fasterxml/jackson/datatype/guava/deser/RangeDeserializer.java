/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonParser
 *  com.fasterxml.jackson.core.JsonToken
 *  com.fasterxml.jackson.databind.BeanProperty
 *  com.fasterxml.jackson.databind.DeserializationContext
 *  com.fasterxml.jackson.databind.JavaType
 *  com.fasterxml.jackson.databind.JsonDeserializer
 *  com.fasterxml.jackson.databind.JsonMappingException
 *  com.fasterxml.jackson.databind.deser.ContextualDeserializer
 *  com.fasterxml.jackson.databind.deser.std.StdDeserializer
 *  com.fasterxml.jackson.databind.jsontype.TypeDeserializer
 *  com.fasterxml.jackson.databind.type.LogicalType
 *  com.fasterxml.jackson.databind.type.TypeFactory
 *  com.google.common.base.Preconditions
 *  com.google.common.collect.BoundType
 *  com.google.common.collect.Range
 */
package com.fasterxml.jackson.datatype.guava.deser;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.type.LogicalType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.datatype.guava.deser.util.RangeFactory;
import com.fasterxml.jackson.datatype.guava.deser.util.RangeHelper;
import com.google.common.base.Preconditions;
import com.google.common.collect.BoundType;
import com.google.common.collect.Range;
import java.io.IOException;
import java.util.Arrays;

public class RangeDeserializer
extends StdDeserializer<Range<?>>
implements ContextualDeserializer {
    private static final long serialVersionUID = 1L;
    protected final JavaType _rangeType;
    protected final JsonDeserializer<Object> _endpointDeserializer;
    protected final BoundType _defaultBoundType;
    protected final RangeHelper.RangeProperties _fieldNames;

    public RangeDeserializer(BoundType defaultBoundType, JavaType rangeType) {
        this(rangeType, null, defaultBoundType);
    }

    @Deprecated
    public RangeDeserializer(JavaType rangeType, JsonDeserializer<?> endpointDeser) {
        this(rangeType, endpointDeser, null);
    }

    @Deprecated
    public RangeDeserializer(JavaType rangeType, JsonDeserializer<?> endpointDeser, BoundType defaultBoundType) {
        this(rangeType, endpointDeser, defaultBoundType, RangeHelper.standardNames());
    }

    protected RangeDeserializer(JavaType rangeType, JsonDeserializer<?> endpointDeser, BoundType defaultBoundType, RangeHelper.RangeProperties fieldNames) {
        super(rangeType);
        this._rangeType = rangeType;
        this._endpointDeserializer = endpointDeser;
        this._defaultBoundType = defaultBoundType;
        this._fieldNames = fieldNames;
    }

    public JavaType getValueType() {
        return this._rangeType;
    }

    public LogicalType logicalType() {
        return LogicalType.POJO;
    }

    public JsonDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property) throws JsonMappingException {
        RangeHelper.RangeProperties fieldNames = RangeHelper.getPropertyNames(ctxt.getConfig(), ctxt.getConfig().getPropertyNamingStrategy());
        JsonDeserializer deser = this._endpointDeserializer;
        if (deser == null) {
            JavaType endpointType = this._rangeType.containedType(0);
            if (endpointType == null) {
                endpointType = TypeFactory.unknownType();
            }
            deser = ctxt.findContextualValueDeserializer(endpointType, property);
        } else if (deser instanceof ContextualDeserializer) {
            deser = ((ContextualDeserializer)deser).createContextual(ctxt, property);
        }
        if (deser != this._endpointDeserializer || fieldNames != this._fieldNames) {
            return new RangeDeserializer(this._rangeType, deser, this._defaultBoundType, fieldNames);
        }
        return this;
    }

    public Object deserializeWithType(JsonParser p, DeserializationContext ctxt, TypeDeserializer typeDeserializer) throws IOException {
        return typeDeserializer.deserializeTypedFromObject(p, ctxt);
    }

    public Range<?> deserialize(JsonParser p, DeserializationContext context) throws IOException {
        JsonToken t = p.getCurrentToken();
        if (t == JsonToken.START_OBJECT) {
            t = p.nextToken();
        }
        Comparable<?> lowerEndpoint = null;
        Comparable<?> upperEndpoint = null;
        BoundType lowerBoundType = this._defaultBoundType;
        BoundType upperBoundType = this._defaultBoundType;
        while (t != JsonToken.END_OBJECT) {
            this.expect(context, JsonToken.FIELD_NAME, t);
            String fieldName = p.getCurrentName();
            try {
                if (fieldName.equals(this._fieldNames.lowerEndpoint)) {
                    p.nextToken();
                    lowerEndpoint = this.deserializeEndpoint(context, p);
                } else if (fieldName.equals(this._fieldNames.upperEndpoint)) {
                    p.nextToken();
                    upperEndpoint = this.deserializeEndpoint(context, p);
                } else if (fieldName.equals(this._fieldNames.lowerBoundType)) {
                    p.nextToken();
                    lowerBoundType = this.deserializeBoundType(context, p);
                } else if (fieldName.equals(this._fieldNames.upperBoundType)) {
                    p.nextToken();
                    upperBoundType = this.deserializeBoundType(context, p);
                } else {
                    context.handleUnknownProperty(p, (JsonDeserializer)this, Range.class, fieldName);
                }
            }
            catch (IllegalStateException e) {
                context.reportBadDefinition(this.handledType(), e.getMessage());
                return null;
            }
            t = p.nextToken();
        }
        try {
            if (lowerEndpoint != null && upperEndpoint != null) {
                Preconditions.checkState((lowerEndpoint.getClass() == upperEndpoint.getClass() ? 1 : 0) != 0, (String)"Endpoint types are not the same - 'lowerEndpoint' deserialized to [%s], and 'upperEndpoint' deserialized to [%s].", (Object)lowerEndpoint.getClass().getName(), (Object)upperEndpoint.getClass().getName());
                Preconditions.checkState((lowerBoundType != null ? 1 : 0) != 0, (Object)"'lowerEndpoint' field found, but not 'lowerBoundType'");
                Preconditions.checkState((upperBoundType != null ? 1 : 0) != 0, (Object)"'upperEndpoint' field found, but not 'upperBoundType'");
                return RangeFactory.range(lowerEndpoint, lowerBoundType, upperEndpoint, upperBoundType);
            }
            if (lowerEndpoint != null) {
                Preconditions.checkState((lowerBoundType != null ? 1 : 0) != 0, (Object)"'lowerEndpoint' field found, but not 'lowerBoundType'");
                return RangeFactory.downTo(lowerEndpoint, lowerBoundType);
            }
            if (upperEndpoint != null) {
                Preconditions.checkState((upperBoundType != null ? 1 : 0) != 0, (Object)"'upperEndpoint' field found, but not 'upperBoundType'");
                return RangeFactory.upTo(upperEndpoint, upperBoundType);
            }
            return RangeFactory.all();
        }
        catch (IllegalStateException e) {
            context.reportBadDefinition(this.handledType(), e.getMessage());
            return null;
        }
    }

    private BoundType deserializeBoundType(DeserializationContext context, JsonParser p) throws IOException {
        this.expect(context, JsonToken.VALUE_STRING, p.getCurrentToken());
        String name = p.getText();
        try {
            return BoundType.valueOf((String)name);
        }
        catch (IllegalArgumentException e) {
            return (BoundType)context.handleWeirdStringValue(BoundType.class, name, "not a valid BoundType name (should be one oF: %s)", new Object[]{Arrays.asList(BoundType.values())});
        }
    }

    private Comparable<?> deserializeEndpoint(DeserializationContext context, JsonParser p) throws IOException {
        Object obj = this._endpointDeserializer.deserialize(p, context);
        if (!(obj instanceof Comparable)) {
            context.reportBadDefinition(this._rangeType, String.format("Field [%s] deserialized to [%s], which does not implement Comparable.", p.getCurrentName(), obj.getClass().getName()));
        }
        return (Comparable)obj;
    }

    private void expect(DeserializationContext context, JsonToken expected, JsonToken actual) throws JsonMappingException {
        if (actual != expected) {
            context.reportInputMismatch((JsonDeserializer)this, String.format("Problem deserializing %s: expecting %s, found %s", this.handledType().getName(), expected, actual), new Object[0]);
        }
    }
}

