/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFormat$Shape
 *  com.fasterxml.jackson.annotation.JsonFormat$Value
 *  com.fasterxml.jackson.core.JsonGenerator
 *  com.fasterxml.jackson.core.JsonParser$NumberType
 *  com.fasterxml.jackson.core.JsonToken
 *  com.fasterxml.jackson.databind.BeanProperty
 *  com.fasterxml.jackson.databind.JavaType
 *  com.fasterxml.jackson.databind.JsonMappingException
 *  com.fasterxml.jackson.databind.JsonSerializer
 *  com.fasterxml.jackson.databind.SerializationFeature
 *  com.fasterxml.jackson.databind.SerializerProvider
 *  com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper
 *  com.fasterxml.jackson.databind.jsonFormatVisitors.JsonIntegerFormatVisitor
 *  com.fasterxml.jackson.databind.jsonFormatVisitors.JsonValueFormat
 */
package com.fasterxml.jackson.datatype.jsr310.ser;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonIntegerFormatVisitor;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonValueFormat;
import com.fasterxml.jackson.datatype.jsr310.DecimalUtils;
import com.fasterxml.jackson.datatype.jsr310.ser.JSR310FormattedSerializerBase;
import com.fasterxml.jackson.datatype.jsr310.util.DurationUnitConverter;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.format.DateTimeFormatter;

public class DurationSerializer
extends JSR310FormattedSerializerBase<Duration> {
    private static final long serialVersionUID = 1L;
    public static final DurationSerializer INSTANCE = new DurationSerializer();
    private DurationUnitConverter _durationUnitConverter;

    protected DurationSerializer() {
        super(Duration.class);
    }

    protected DurationSerializer(DurationSerializer base, Boolean useTimestamp, DateTimeFormatter dtf) {
        super(base, useTimestamp, dtf, null);
    }

    protected DurationSerializer(DurationSerializer base, Boolean useTimestamp, Boolean useNanoseconds, DateTimeFormatter dtf) {
        super(base, useTimestamp, useNanoseconds, dtf, null);
    }

    protected DurationSerializer(DurationSerializer base, DurationUnitConverter converter) {
        super(base, base._useTimestamp, base._useNanoseconds, base._formatter, base._shape);
        this._durationUnitConverter = converter;
    }

    protected DurationSerializer withFormat(Boolean useTimestamp, DateTimeFormatter dtf, JsonFormat.Shape shape) {
        return new DurationSerializer(this, useTimestamp, dtf);
    }

    protected DurationSerializer withConverter(DurationUnitConverter converter) {
        return new DurationSerializer(this, converter);
    }

    @Override
    protected SerializationFeature getTimestampsFeature() {
        return SerializationFeature.WRITE_DURATIONS_AS_TIMESTAMPS;
    }

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty property) throws JsonMappingException {
        DurationSerializer ser = (DurationSerializer)super.createContextual(prov, property);
        JsonFormat.Value format = this.findFormatOverrides(prov, property, this.handledType());
        if (format != null && format.hasPattern()) {
            String pattern = format.getPattern();
            DurationUnitConverter p = DurationUnitConverter.from(pattern);
            if (p == null) {
                prov.reportBadDefinition(this.handledType(), String.format("Bad 'pattern' definition (\"%s\") for `Duration`: expected one of [%s]", pattern, DurationUnitConverter.descForAllowed()));
            }
            ser = ser.withConverter(p);
        }
        return ser;
    }

    public void serialize(Duration duration, JsonGenerator generator, SerializerProvider provider) throws IOException {
        if (this.useTimestamp(provider)) {
            if (this.useNanoseconds(provider)) {
                generator.writeNumber(this._toNanos(duration));
            } else if (this._durationUnitConverter != null) {
                generator.writeNumber(this._durationUnitConverter.convert(duration));
            } else {
                generator.writeNumber(duration.toMillis());
            }
        } else {
            generator.writeString(duration.toString());
        }
    }

    private BigDecimal _toNanos(Duration duration) {
        BigDecimal bd;
        if (duration.isNegative()) {
            duration = duration.abs();
            bd = DecimalUtils.toBigDecimal(duration.getSeconds(), duration.getNano()).negate();
        } else {
            bd = DecimalUtils.toBigDecimal(duration.getSeconds(), duration.getNano());
        }
        return bd;
    }

    @Override
    protected void _acceptTimestampVisitor(JsonFormatVisitorWrapper visitor, JavaType typeHint) throws JsonMappingException {
        JsonIntegerFormatVisitor v2 = visitor.expectIntegerFormat(typeHint);
        if (v2 != null) {
            v2.numberType(JsonParser.NumberType.LONG);
            SerializerProvider provider = visitor.getProvider();
            if (provider == null || !this.useNanoseconds(provider)) {
                v2.format(JsonValueFormat.UTC_MILLISEC);
            }
        }
    }

    @Override
    protected JsonToken serializationShape(SerializerProvider provider) {
        if (this.useTimestamp(provider)) {
            if (this.useNanoseconds(provider)) {
                return JsonToken.VALUE_NUMBER_FLOAT;
            }
            return JsonToken.VALUE_NUMBER_INT;
        }
        return JsonToken.VALUE_STRING;
    }

    @Override
    protected JSR310FormattedSerializerBase<?> withFeatures(Boolean writeZoneId, Boolean writeNanoseconds) {
        return new DurationSerializer(this, this._useTimestamp, writeNanoseconds, this._formatter);
    }

    @Override
    protected DateTimeFormatter _useDateTimeFormatter(SerializerProvider prov, JsonFormat.Value format) {
        return null;
    }
}

