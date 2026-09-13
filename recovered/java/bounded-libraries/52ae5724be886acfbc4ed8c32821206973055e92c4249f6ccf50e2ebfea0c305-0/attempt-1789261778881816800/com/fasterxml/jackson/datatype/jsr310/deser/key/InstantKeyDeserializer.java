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
import java.time.Instant;
import java.time.format.DateTimeFormatter;

public class InstantKeyDeserializer
extends Jsr310KeyDeserializer {
    public static final InstantKeyDeserializer INSTANCE = new InstantKeyDeserializer();

    private InstantKeyDeserializer() {
    }

    @Override
    protected Instant deserialize(String key, DeserializationContext ctxt) throws IOException {
        try {
            return DateTimeFormatter.ISO_INSTANT.parse((CharSequence)key, Instant::from);
        }
        catch (DateTimeException e) {
            return (Instant)this._handleDateTimeException(ctxt, Instant.class, e, key);
        }
    }
}

