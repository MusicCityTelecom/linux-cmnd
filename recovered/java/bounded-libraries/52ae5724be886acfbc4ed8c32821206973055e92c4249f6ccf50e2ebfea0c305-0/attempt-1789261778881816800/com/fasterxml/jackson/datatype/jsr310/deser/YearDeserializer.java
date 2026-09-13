/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFormat$Shape
 *  com.fasterxml.jackson.core.JsonParser
 *  com.fasterxml.jackson.core.JsonToken
 *  com.fasterxml.jackson.core.StreamReadCapability
 *  com.fasterxml.jackson.core.io.NumberInput
 *  com.fasterxml.jackson.databind.DeserializationContext
 *  com.fasterxml.jackson.databind.JsonDeserializer
 */
package com.fasterxml.jackson.datatype.jsr310.deser;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.StreamReadCapability;
import com.fasterxml.jackson.core.io.NumberInput;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.JSR310DateTimeDeserializerBase;
import java.io.IOException;
import java.time.DateTimeException;
import java.time.Year;
import java.time.format.DateTimeFormatter;

public class YearDeserializer
extends JSR310DateTimeDeserializerBase<Year> {
    private static final long serialVersionUID = 1L;
    public static final YearDeserializer INSTANCE = new YearDeserializer();

    public YearDeserializer() {
        this((DateTimeFormatter)null);
    }

    public YearDeserializer(DateTimeFormatter formatter) {
        super(Year.class, formatter);
    }

    protected YearDeserializer(YearDeserializer base, Boolean leniency) {
        super(base, leniency);
    }

    protected YearDeserializer withDateFormat(DateTimeFormatter dtf) {
        return new YearDeserializer(dtf);
    }

    @Override
    protected YearDeserializer withLeniency(Boolean leniency) {
        return new YearDeserializer(this, leniency);
    }

    protected YearDeserializer withShape(JsonFormat.Shape shape) {
        return this;
    }

    public Year deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        JsonToken t = parser.currentToken();
        if (t == JsonToken.VALUE_STRING) {
            return this._fromString(parser, context, parser.getText());
        }
        if (t == JsonToken.START_OBJECT) {
            return this._fromString(parser, context, context.extractScalarFromObject(parser, (JsonDeserializer)this, this.handledType()));
        }
        if (t == JsonToken.VALUE_NUMBER_INT) {
            return this._fromNumber(context, parser.getIntValue());
        }
        if (t == JsonToken.VALUE_EMBEDDED_OBJECT) {
            return (Year)parser.getEmbeddedObject();
        }
        if (parser.hasToken(JsonToken.START_ARRAY)) {
            return (Year)this._deserializeFromArray(parser, context);
        }
        return (Year)this._handleUnexpectedToken(context, parser, JsonToken.VALUE_STRING, JsonToken.VALUE_NUMBER_INT);
    }

    protected Year _fromString(JsonParser p, DeserializationContext ctxt, String string0) throws IOException {
        String string = string0.trim();
        if (string.length() == 0) {
            return (Year)this._fromEmptyString(p, ctxt, string);
        }
        if (ctxt.isEnabled(StreamReadCapability.UNTYPED_SCALARS) && this._isValidTimestampString(string)) {
            return this._fromNumber(ctxt, NumberInput.parseInt((String)string));
        }
        try {
            if (this._formatter == null) {
                return Year.parse(string);
            }
            return Year.parse(string, this._formatter);
        }
        catch (DateTimeException e) {
            return (Year)this._handleDateTimeException(ctxt, e, string);
        }
    }

    protected Year _fromNumber(DeserializationContext ctxt, int value) {
        return Year.of(value);
    }
}

