/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonGenerator
 *  com.fasterxml.jackson.core.JsonToken
 *  com.fasterxml.jackson.core.type.WritableTypeId
 *  com.fasterxml.jackson.databind.BeanProperty
 *  com.fasterxml.jackson.databind.JavaType
 *  com.fasterxml.jackson.databind.JsonMappingException
 *  com.fasterxml.jackson.databind.JsonSerializer
 *  com.fasterxml.jackson.databind.SerializerProvider
 *  com.fasterxml.jackson.databind.jsontype.TypeSerializer
 *  com.fasterxml.jackson.databind.ser.ContainerSerializer
 *  com.fasterxml.jackson.databind.ser.ContextualSerializer
 *  com.fasterxml.jackson.databind.ser.std.MapSerializer
 *  com.fasterxml.jackson.databind.type.MapType
 *  com.fasterxml.jackson.databind.type.TypeFactory
 *  com.google.common.collect.Table
 */
package com.fasterxml.jackson.datatype.guava.ser;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.type.WritableTypeId;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.ContainerSerializer;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.fasterxml.jackson.databind.ser.std.MapSerializer;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.google.common.collect.Table;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

public class TableSerializer
extends ContainerSerializer<Table<?, ?, ?>>
implements ContextualSerializer {
    private static final long serialVersionUID = 1L;
    private final JavaType _type;
    private final BeanProperty _property;
    private final JsonSerializer<Object> _rowSerializer;
    private final JsonSerializer<Object> _columnSerializer;
    private final TypeSerializer _valueTypeSerializer;
    private final JsonSerializer<Object> _valueSerializer;
    private final MapSerializer _rowMapSerializer;

    public TableSerializer(JavaType type) {
        super(type);
        this._type = type;
        this._property = null;
        this._rowSerializer = null;
        this._columnSerializer = null;
        this._valueTypeSerializer = null;
        this._valueSerializer = null;
        this._rowMapSerializer = null;
    }

    protected TableSerializer(TableSerializer src, BeanProperty property, TypeFactory typeFactory, JsonSerializer<?> rowKeySerializer, JsonSerializer<?> columnKeySerializer, TypeSerializer valueTypeSerializer, JsonSerializer<?> valueSerializer) {
        super((ContainerSerializer)src);
        this._type = src._type;
        this._property = property;
        this._rowSerializer = rowKeySerializer;
        this._columnSerializer = columnKeySerializer;
        this._valueTypeSerializer = valueTypeSerializer;
        this._valueSerializer = valueSerializer;
        MapType columnAndValueType = typeFactory.constructMapType(Map.class, this._type.containedTypeOrUnknown(1), this._type.containedTypeOrUnknown(2));
        MapSerializer columnAndValueSerializer = MapSerializer.construct((Set)null, (JavaType)columnAndValueType, (boolean)false, (TypeSerializer)this._valueTypeSerializer, this._columnSerializer, this._valueSerializer, null);
        MapType rowMapType = typeFactory.constructMapType(Map.class, this._type.containedTypeOrUnknown(0), (JavaType)columnAndValueType);
        this._rowMapSerializer = MapSerializer.construct((Set)null, (JavaType)rowMapType, (boolean)false, null, this._rowSerializer, (JsonSerializer)columnAndValueSerializer, null);
    }

    protected TableSerializer(TableSerializer src, TypeSerializer typeSer) {
        super((ContainerSerializer)src);
        this._type = src._type;
        this._property = src._property;
        this._rowSerializer = src._rowSerializer;
        this._columnSerializer = src._columnSerializer;
        this._valueTypeSerializer = typeSer;
        this._valueSerializer = src._valueSerializer;
        this._rowMapSerializer = src._rowMapSerializer;
    }

    protected TableSerializer withResolved(BeanProperty property, TypeFactory typeFactory, JsonSerializer<?> rowKeySer, JsonSerializer<?> columnKeySer, TypeSerializer vts, JsonSerializer<?> valueSer) {
        return new TableSerializer(this, property, typeFactory, rowKeySer, columnKeySer, vts, valueSer);
    }

    protected ContainerSerializer<?> _withValueTypeSerializer(TypeSerializer typeSer) {
        return new TableSerializer(this, typeSer);
    }

    public JsonSerializer<?> createContextual(SerializerProvider provider, BeanProperty property) throws JsonMappingException {
        JsonSerializer valueSer = this._valueSerializer;
        if (valueSer == null) {
            JavaType valueType = this._type.containedTypeOrUnknown(2);
            if (valueType.isFinal()) {
                valueSer = provider.findValueSerializer(valueType, property);
            }
        } else if (valueSer instanceof ContextualSerializer) {
            valueSer = ((ContextualSerializer)valueSer).createContextual(provider, property);
        }
        JsonSerializer rowKeySer = this._rowSerializer;
        if (rowKeySer == null) {
            rowKeySer = provider.findKeySerializer(this._type.containedTypeOrUnknown(0), property);
        } else if (rowKeySer instanceof ContextualSerializer) {
            rowKeySer = ((ContextualSerializer)rowKeySer).createContextual(provider, property);
        }
        JsonSerializer columnKeySer = this._columnSerializer;
        if (columnKeySer == null) {
            columnKeySer = provider.findKeySerializer(this._type.containedTypeOrUnknown(1), property);
        } else if (columnKeySer instanceof ContextualSerializer) {
            columnKeySer = ((ContextualSerializer)columnKeySer).createContextual(provider, property);
        }
        TypeSerializer typeSer = this._valueTypeSerializer;
        if (typeSer != null) {
            typeSer = typeSer.forProperty(property);
        }
        return this.withResolved(property, provider.getTypeFactory(), rowKeySer, columnKeySer, typeSer, valueSer);
    }

    public JavaType getContentType() {
        return this._type.getContentType();
    }

    public JsonSerializer<?> getContentSerializer() {
        return this._valueSerializer;
    }

    public boolean isEmpty(SerializerProvider provider, Table<?, ?, ?> table) {
        return table.isEmpty();
    }

    public boolean hasSingleElement(Table<?, ?, ?> table) {
        return table.size() == 1;
    }

    public void serialize(Table<?, ?, ?> value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject(value);
        if (!value.isEmpty()) {
            this.serializeFields(value, gen, provider);
        }
        gen.writeEndObject();
    }

    public void serializeWithType(Table<?, ?, ?> value, JsonGenerator gen, SerializerProvider provider, TypeSerializer typeSer) throws IOException {
        gen.setCurrentValue(value);
        WritableTypeId typeIdDef = typeSer.writeTypePrefix(gen, typeSer.typeId(value, JsonToken.START_OBJECT));
        this.serializeFields(value, gen, provider);
        typeSer.writeTypeSuffix(gen, typeIdDef);
    }

    private final void serializeFields(Table<?, ?, ?> table, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        this._rowMapSerializer.serializeFields(table.rowMap(), jgen, provider);
    }
}

