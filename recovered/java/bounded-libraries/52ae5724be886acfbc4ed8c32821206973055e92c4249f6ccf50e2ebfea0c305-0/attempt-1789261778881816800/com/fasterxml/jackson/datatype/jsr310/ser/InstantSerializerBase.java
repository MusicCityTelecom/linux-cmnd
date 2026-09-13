/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFormat$Shape
 *  com.fasterxml.jackson.core.JsonGenerator
 *  com.fasterxml.jackson.core.JsonParser$NumberType
 *  com.fasterxml.jackson.core.JsonToken
 *  com.fasterxml.jackson.databind.JavaType
 *  com.fasterxml.jackson.databind.JsonMappingException
 *  com.fasterxml.jackson.databind.SerializationFeature
 *  com.fasterxml.jackson.databind.SerializerProvider
 *  com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper
 *  com.fasterxml.jackson.databind.jsonFormatVisitors.JsonIntegerFormatVisitor
 *  com.fasterxml.jackson.databind.jsonFormatVisitors.JsonNumberFormatVisitor
 */
package com.fasterxml.jackson.datatype.jsr310.ser;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonIntegerFormatVisitor;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonNumberFormatVisitor;
import com.fasterxml.jackson.datatype.jsr310.DecimalUtils;
import com.fasterxml.jackson.datatype.jsr310.ser.JSR310FormattedSerializerBase;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAccessor;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

public abstract class InstantSerializerBase<T extends Temporal>
extends JSR310FormattedSerializerBase<T> {
    private final DateTimeFormatter defaultFormat;
    private final ToLongFunction<T> getEpochMillis;
    private final ToLongFunction<T> getEpochSeconds;
    private final ToIntFunction<T> getNanoseconds;

    protected InstantSerializerBase(Class<T> supportedType, ToLongFunction<T> getEpochMillis, ToLongFunction<T> getEpochSeconds, ToIntFunction<T> getNanoseconds, DateTimeFormatter formatter) {
        super(supportedType, null);
        this.defaultFormat = formatter;
        this.getEpochMillis = getEpochMillis;
        this.getEpochSeconds = getEpochSeconds;
        this.getNanoseconds = getNanoseconds;
    }

    protected InstantSerializerBase(InstantSerializerBase<T> base, Boolean useTimestamp, DateTimeFormatter dtf) {
        this(base, useTimestamp, null, dtf);
    }

    protected InstantSerializerBase(InstantSerializerBase<T> base, Boolean useTimestamp, Boolean useNanoseconds, DateTimeFormatter dtf) {
        super(base, useTimestamp, useNanoseconds, dtf, null);
        this.defaultFormat = base.defaultFormat;
        this.getEpochMillis = base.getEpochMillis;
        this.getEpochSeconds = base.getEpochSeconds;
        this.getNanoseconds = base.getNanoseconds;
    }

    @Override
    protected abstract JSR310FormattedSerializerBase<?> withFormat(Boolean var1, DateTimeFormatter var2, JsonFormat.Shape var3);

    public void serialize(T value, JsonGenerator generator, SerializerProvider provider) throws IOException {
        if (this.useTimestamp(provider)) {
            if (this.useNanoseconds(provider)) {
                generator.writeNumber(DecimalUtils.toBigDecimal(this.getEpochSeconds.applyAsLong(value), this.getNanoseconds.applyAsInt(value)));
                return;
            }
            generator.writeNumber(this.getEpochMillis.applyAsLong(value));
            return;
        }
        generator.writeString(this.formatValue(value, provider));
    }

    @Override
    protected void _acceptTimestampVisitor(JsonFormatVisitorWrapper visitor, JavaType typeHint) throws JsonMappingException {
        if (this.useNanoseconds(visitor.getProvider())) {
            JsonNumberFormatVisitor v2 = visitor.expectNumberFormat(typeHint);
            if (v2 != null) {
                v2.numberType(JsonParser.NumberType.BIG_DECIMAL);
            }
        } else {
            JsonIntegerFormatVisitor v2 = visitor.expectIntegerFormat(typeHint);
            if (v2 != null) {
                v2.numberType(JsonParser.NumberType.LONG);
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

    protected String formatValue(T value, SerializerProvider provider) {
        DateTimeFormatter formatter;
        DateTimeFormatter dateTimeFormatter = formatter = this._formatter != null ? this._formatter : this.defaultFormat;
        if (formatter != null) {
            if (formatter.getZone() == null && provider.getConfig().hasExplicitTimeZone() && provider.isEnabled(SerializationFeature.WRITE_DATES_WITH_CONTEXT_TIME_ZONE)) {
                formatter = formatter.withZone(provider.getTimeZone().toZoneId());
            }
            return formatter.format((TemporalAccessor)value);
        }
        return value.toString();
    }
}

