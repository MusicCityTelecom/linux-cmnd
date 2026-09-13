/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFormat$Shape
 *  com.fasterxml.jackson.core.JsonGenerator
 *  com.fasterxml.jackson.core.JsonToken
 *  com.fasterxml.jackson.databind.SerializationFeature
 *  com.fasterxml.jackson.databind.SerializerProvider
 */
package com.fasterxml.jackson.datatype.jsr310.ser;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.datatype.jsr310.ser.InstantSerializerBase;
import com.fasterxml.jackson.datatype.jsr310.ser.JSR310FormattedSerializerBase;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.chrono.ChronoZonedDateTime;
import java.time.format.DateTimeFormatter;

public class ZonedDateTimeSerializer
extends InstantSerializerBase<ZonedDateTime> {
    private static final long serialVersionUID = 1L;
    public static final ZonedDateTimeSerializer INSTANCE = new ZonedDateTimeSerializer();
    protected final Boolean _writeZoneId;

    protected ZonedDateTimeSerializer() {
        this(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }

    public ZonedDateTimeSerializer(DateTimeFormatter formatter) {
        super(ZonedDateTime.class, (T dt) -> dt.toInstant().toEpochMilli(), ChronoZonedDateTime::toEpochSecond, ZonedDateTime::getNano, formatter);
        this._writeZoneId = null;
    }

    protected ZonedDateTimeSerializer(ZonedDateTimeSerializer base, Boolean useTimestamp, DateTimeFormatter formatter, Boolean writeZoneId) {
        this(base, useTimestamp, null, formatter, writeZoneId);
    }

    protected ZonedDateTimeSerializer(ZonedDateTimeSerializer base, Boolean useTimestamp, Boolean useNanoseconds, DateTimeFormatter formatter, Boolean writeZoneId) {
        super(base, useTimestamp, useNanoseconds, formatter);
        this._writeZoneId = writeZoneId;
    }

    @Override
    protected JSR310FormattedSerializerBase<?> withFormat(Boolean useTimestamp, DateTimeFormatter formatter, JsonFormat.Shape shape) {
        return new ZonedDateTimeSerializer(this, useTimestamp, formatter, this._writeZoneId);
    }

    @Override
    @Deprecated
    protected JSR310FormattedSerializerBase<?> withFeatures(Boolean writeZoneId) {
        return new ZonedDateTimeSerializer(this, this._useTimestamp, this._formatter, writeZoneId);
    }

    @Override
    protected JSR310FormattedSerializerBase<?> withFeatures(Boolean writeZoneId, Boolean writeNanoseconds) {
        return new ZonedDateTimeSerializer(this, this._useTimestamp, writeNanoseconds, this._formatter, writeZoneId);
    }

    @Override
    public void serialize(ZonedDateTime value, JsonGenerator g, SerializerProvider provider) throws IOException {
        if (!this.useTimestamp(provider) && this.shouldWriteWithZoneId(provider)) {
            g.writeString(DateTimeFormatter.ISO_ZONED_DATE_TIME.format(value));
            return;
        }
        super.serialize(value, g, provider);
    }

    public boolean shouldWriteWithZoneId(SerializerProvider ctxt) {
        return this._writeZoneId != null ? this._writeZoneId.booleanValue() : ctxt.isEnabled(SerializationFeature.WRITE_DATES_WITH_ZONE_ID);
    }

    @Override
    protected JsonToken serializationShape(SerializerProvider provider) {
        if (!this.useTimestamp(provider) && this.shouldWriteWithZoneId(provider)) {
            return JsonToken.VALUE_STRING;
        }
        return super.serializationShape(provider);
    }
}

