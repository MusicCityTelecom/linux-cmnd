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
import java.time.ZoneOffset;

public class ZoneOffsetKeyDeserializer
extends Jsr310KeyDeserializer {
    public static final ZoneOffsetKeyDeserializer INSTANCE = new ZoneOffsetKeyDeserializer();

    private ZoneOffsetKeyDeserializer() {
    }

    @Override
    protected ZoneOffset deserialize(String key, DeserializationContext ctxt) throws IOException {
        try {
            return ZoneOffset.of(key);
        }
        catch (DateTimeException e) {
            return (ZoneOffset)this._handleDateTimeException(ctxt, ZoneOffset.class, e, key);
        }
    }
}

