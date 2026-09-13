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
import java.time.Year;

public class YearKeyDeserializer
extends Jsr310KeyDeserializer {
    public static final YearKeyDeserializer INSTANCE = new YearKeyDeserializer();

    protected YearKeyDeserializer() {
    }

    @Override
    protected Year deserialize(String key, DeserializationContext ctxt) throws IOException {
        try {
            return Year.of(Integer.parseInt(key));
        }
        catch (NumberFormatException nfe) {
            return (Year)this._handleDateTimeException(ctxt, Year.class, new DateTimeException("Number format exception", nfe), key);
        }
        catch (DateTimeException dte) {
            return (Year)this._handleDateTimeException(ctxt, Year.class, dte, key);
        }
    }
}

