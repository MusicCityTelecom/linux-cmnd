/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFormat$Shape
 *  com.fasterxml.jackson.core.JsonGenerator
 *  com.fasterxml.jackson.core.JsonToken
 *  com.fasterxml.jackson.core.type.WritableTypeId
 *  com.fasterxml.jackson.databind.JavaType
 *  com.fasterxml.jackson.databind.JsonMappingException
 *  com.fasterxml.jackson.databind.SerializerProvider
 *  com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper
 *  com.fasterxml.jackson.databind.jsonFormatVisitors.JsonStringFormatVisitor
 *  com.fasterxml.jackson.databind.jsonFormatVisitors.JsonValueFormat
 *  com.fasterxml.jackson.databind.jsontype.TypeSerializer
 */
package com.fasterxml.jackson.datatype.jsr310.ser;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.type.WritableTypeId;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonStringFormatVisitor;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonValueFormat;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.JSR310FormattedSerializerBase;
import java.io.IOException;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

public class YearMonthSerializer
extends JSR310FormattedSerializerBase<YearMonth> {
    private static final long serialVersionUID = 1L;
    public static final YearMonthSerializer INSTANCE = new YearMonthSerializer();

    protected YearMonthSerializer() {
        this((DateTimeFormatter)null);
    }

    public YearMonthSerializer(DateTimeFormatter formatter) {
        super(YearMonth.class, formatter);
    }

    private YearMonthSerializer(YearMonthSerializer base, Boolean useTimestamp, DateTimeFormatter formatter) {
        super(base, useTimestamp, formatter, null);
    }

    protected YearMonthSerializer withFormat(Boolean useTimestamp, DateTimeFormatter formatter, JsonFormat.Shape shape) {
        return new YearMonthSerializer(this, useTimestamp, formatter);
    }

    public void serialize(YearMonth value, JsonGenerator g, SerializerProvider provider) throws IOException {
        if (this.useTimestamp(provider)) {
            g.writeStartArray();
            this._serializeAsArrayContents(value, g, provider);
            g.writeEndArray();
            return;
        }
        g.writeString(this._formatter == null ? value.toString() : value.format(this._formatter));
    }

    @Override
    public void serializeWithType(YearMonth value, JsonGenerator g, SerializerProvider provider, TypeSerializer typeSer) throws IOException {
        WritableTypeId typeIdDef = typeSer.writeTypePrefix(g, typeSer.typeId((Object)value, this.serializationShape(provider)));
        if (typeIdDef.valueShape == JsonToken.START_ARRAY) {
            this._serializeAsArrayContents(value, g, provider);
        } else {
            g.writeString(this._formatter == null ? value.toString() : value.format(this._formatter));
        }
        typeSer.writeTypeSuffix(g, typeIdDef);
    }

    protected void _serializeAsArrayContents(YearMonth value, JsonGenerator g, SerializerProvider provider) throws IOException {
        g.writeNumber(value.getYear());
        g.writeNumber(value.getMonthValue());
    }

    @Override
    protected void _acceptTimestampVisitor(JsonFormatVisitorWrapper visitor, JavaType typeHint) throws JsonMappingException {
        boolean useTimestamp;
        SerializerProvider provider = visitor.getProvider();
        boolean bl = useTimestamp = provider != null && this.useTimestamp(provider);
        if (useTimestamp) {
            super._acceptTimestampVisitor(visitor, typeHint);
        } else {
            JsonStringFormatVisitor v2 = visitor.expectStringFormat(typeHint);
            if (v2 != null) {
                v2.format(JsonValueFormat.DATE_TIME);
            }
        }
    }

    @Override
    protected JsonToken serializationShape(SerializerProvider provider) {
        return this.useTimestamp(provider) ? JsonToken.START_ARRAY : JsonToken.VALUE_STRING;
    }
}

