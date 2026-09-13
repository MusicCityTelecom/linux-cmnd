/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.DeserializationContext
 */
package com.fasterxml.jackson.datatype.jsr310.deser.key;

import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.datatype.jsr310.deser.key.Jsr310KeyDeserializer;
import java.io.IOException;
import java.time.DateTimeException;
import java.time.OffsetTime;
import java.time.format.DateTimeFormatter;

public class OffsetTimeKeyDeserializer
extends Jsr310KeyDeserializer {
    public static final OffsetTimeKeyDeserializer INSTANCE = new OffsetTimeKeyDeserializer();

    private OffsetTimeKeyDeserializer() {
    }

    @Override
    protected OffsetTime deserialize(String key, DeserializationContext ctxt) throws IOException {
        try {
            return OffsetTime.parse(key, DateTimeFormatter.ISO_OFFSET_TIME);
        }
        catch (DateTimeException e) {
            return (OffsetTime)this._handleDateTimeException(ctxt, OffsetTime.class, e, key);
        }
    }
}

