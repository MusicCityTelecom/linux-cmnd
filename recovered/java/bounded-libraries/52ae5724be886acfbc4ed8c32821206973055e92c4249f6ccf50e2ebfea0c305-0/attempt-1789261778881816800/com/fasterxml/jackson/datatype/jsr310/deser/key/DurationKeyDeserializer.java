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
import java.time.Duration;

public class DurationKeyDeserializer
extends Jsr310KeyDeserializer {
    public static final DurationKeyDeserializer INSTANCE = new DurationKeyDeserializer();

    private DurationKeyDeserializer() {
    }

    @Override
    protected Duration deserialize(String key, DeserializationContext ctxt) throws IOException {
        try {
            return Duration.parse(key);
        }
        catch (DateTimeException e) {
            return (Duration)this._handleDateTimeException(ctxt, Duration.class, e, key);
        }
    }
}

