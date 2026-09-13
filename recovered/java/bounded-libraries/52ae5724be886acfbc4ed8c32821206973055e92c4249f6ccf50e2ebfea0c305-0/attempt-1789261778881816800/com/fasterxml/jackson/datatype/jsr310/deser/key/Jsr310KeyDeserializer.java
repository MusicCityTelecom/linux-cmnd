/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.DeserializationContext
 *  com.fasterxml.jackson.databind.JsonMappingException
 *  com.fasterxml.jackson.databind.KeyDeserializer
 */
package com.fasterxml.jackson.datatype.jsr310.deser.key;

import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.KeyDeserializer;
import java.io.IOException;
import java.time.DateTimeException;

abstract class Jsr310KeyDeserializer
extends KeyDeserializer {
    Jsr310KeyDeserializer() {
    }

    public final Object deserializeKey(String key, DeserializationContext ctxt) throws IOException {
        if ("".equals(key)) {
            return null;
        }
        return this.deserialize(key, ctxt);
    }

    protected abstract Object deserialize(String var1, DeserializationContext var2) throws IOException;

    protected <T> T _handleDateTimeException(DeserializationContext ctxt, Class<?> type, DateTimeException e0, String value) throws IOException {
        try {
            return (T)ctxt.handleWeirdKey(type, value, "Failed to deserialize %s: (%s) %s", new Object[]{type.getName(), e0.getClass().getName(), e0.getMessage()});
        }
        catch (JsonMappingException e) {
            e.initCause((Throwable)e0);
            throw e;
        }
        catch (IOException e) {
            if (null == e.getCause()) {
                e.initCause(e0);
            }
            throw JsonMappingException.fromUnexpectedIOE((IOException)e);
        }
    }
}

