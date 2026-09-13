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
import java.time.Period;

public class PeriodKeyDeserializer
extends Jsr310KeyDeserializer {
    public static final PeriodKeyDeserializer INSTANCE = new PeriodKeyDeserializer();

    private PeriodKeyDeserializer() {
    }

    @Override
    protected Period deserialize(String key, DeserializationContext ctxt) throws IOException {
        try {
            return Period.parse(key);
        }
        catch (DateTimeException e) {
            return (Period)this._handleDateTimeException(ctxt, Period.class, e, key);
        }
    }
}

