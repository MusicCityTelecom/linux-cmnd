/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonGenerationException
 *  com.fasterxml.jackson.core.JsonGenerator
 *  com.fasterxml.jackson.core.JsonToken
 *  com.fasterxml.jackson.core.type.WritableTypeId
 *  com.fasterxml.jackson.databind.BeanProperty
 *  com.fasterxml.jackson.databind.JavaType
 *  com.fasterxml.jackson.databind.JsonMappingException
 *  com.fasterxml.jackson.databind.JsonSerializer
 *  com.fasterxml.jackson.databind.PropertyNamingStrategy
 *  com.fasterxml.jackson.databind.SerializerProvider
 *  com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitable
 *  com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper
 *  com.fasterxml.jackson.databind.jsonFormatVisitors.JsonObjectFormatVisitor
 *  com.fasterxml.jackson.databind.jsontype.TypeSerializer
 *  com.fasterxml.jackson.databind.ser.ContextualSerializer
 *  com.fasterxml.jackson.databind.ser.std.StdSerializer
 *  com.google.common.collect.BoundType
 *  com.google.common.collect.Range
 */
package com.fasterxml.jackson.datatype.guava.ser;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.type.WritableTypeId;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitable;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonObjectFormatVisitor;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.fasterxml.jackson.datatype.guava.deser.util.RangeHelper;
import com.google.common.collect.BoundType;
import com.google.common.collect.Range;
import java.io.IOException;

public class RangeSerializer
extends StdSerializer<Range<?>>
implements ContextualSerializer {
    protected final JavaType _rangeType;
    protected final JsonSerializer<Object> _endpointSerializer;
    protected final RangeHelper.RangeProperties _fieldNames;

    public RangeSerializer(JavaType type) {
        this(type, null);
    }

    @Deprecated
    public RangeSerializer(JavaType type, JsonSerializer<?> endpointSer) {
        this(type, endpointSer, RangeHelper.standardNames());
    }

    public RangeSerializer(JavaType type, JsonSerializer<?> endpointSer, RangeHelper.RangeProperties fieldNames) {
        super(type);
        this._rangeType = type;
        this._endpointSerializer = endpointSer;
        this._fieldNames = fieldNames;
    }

    public boolean isEmpty(SerializerProvider prov, Range<?> value) {
        return value.isEmpty();
    }

    public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty property) throws JsonMappingException {
        PropertyNamingStrategy propertyNamingStrategy = prov.getConfig().getPropertyNamingStrategy();
        RangeHelper.RangeProperties nameMapping = RangeHelper.getPropertyNames(prov.getConfig(), propertyNamingStrategy);
        JsonSerializer endpointSer = this._endpointSerializer;
        if (endpointSer == null) {
            JavaType endpointType = this._rangeType.containedTypeOrUnknown(0);
            if (endpointType != null && !endpointType.hasRawClass(Object.class)) {
                JsonSerializer ser = prov.findValueSerializer(endpointType, property);
                return new RangeSerializer(this._rangeType, ser, nameMapping);
            }
        } else if (endpointSer instanceof ContextualSerializer) {
            endpointSer = ((ContextualSerializer)endpointSer).createContextual(prov, property);
        }
        if (endpointSer != this._endpointSerializer || nameMapping != null) {
            return new RangeSerializer(this._rangeType, endpointSer, nameMapping);
        }
        return this;
    }

    public void serialize(Range<?> value, JsonGenerator gen, SerializerProvider provider) throws IOException, JsonGenerationException {
        gen.writeStartObject(value);
        this._writeContents(value, gen, provider);
        gen.writeEndObject();
    }

    public void serializeWithType(Range<?> value, JsonGenerator gen, SerializerProvider provider, TypeSerializer typeSer) throws IOException {
        gen.setCurrentValue(value);
        WritableTypeId typeIdDef = typeSer.writeTypePrefix(gen, typeSer.typeId(value, JsonToken.START_OBJECT));
        this._writeContents(value, gen, provider);
        typeSer.writeTypeSuffix(gen, typeIdDef);
    }

    private void _writeContents(Range<?> value, JsonGenerator g, SerializerProvider provider) throws IOException {
        String fieldName;
        if (value.hasLowerBound()) {
            fieldName = this._fieldNames.lowerEndpoint;
            if (this._endpointSerializer != null) {
                g.writeFieldName(fieldName);
                this._endpointSerializer.serialize((Object)value.lowerEndpoint(), g, provider);
            } else {
                provider.defaultSerializeField(fieldName, (Object)value.lowerEndpoint(), g);
            }
            g.writeStringField(this._fieldNames.lowerBoundType, value.lowerBoundType().name());
        }
        if (value.hasUpperBound()) {
            fieldName = this._fieldNames.upperEndpoint;
            if (this._endpointSerializer != null) {
                g.writeFieldName(fieldName);
                this._endpointSerializer.serialize((Object)value.upperEndpoint(), g, provider);
            } else {
                provider.defaultSerializeField(fieldName, (Object)value.upperEndpoint(), g);
            }
            g.writeStringField(this._fieldNames.upperBoundType, value.upperBoundType().name());
        }
    }

    public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper visitor, JavaType typeHint) throws JsonMappingException {
        JsonObjectFormatVisitor objectVisitor;
        if (visitor != null && (objectVisitor = visitor.expectObjectFormat(typeHint)) != null && this._endpointSerializer != null) {
            JavaType endpointType = this._rangeType.containedType(0);
            JavaType btType = visitor.getProvider().constructType(BoundType.class);
            JsonSerializer btSer = visitor.getProvider().findValueSerializer(btType, null);
            objectVisitor.property(this._fieldNames.lowerEndpoint, this._endpointSerializer, endpointType);
            objectVisitor.property(this._fieldNames.lowerBoundType, (JsonFormatVisitable)btSer, btType);
            objectVisitor.property(this._fieldNames.upperEndpoint, this._endpointSerializer, endpointType);
            objectVisitor.property(this._fieldNames.upperBoundType, (JsonFormatVisitable)btSer, btType);
        }
    }
}

