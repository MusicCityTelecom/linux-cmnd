/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonGenerator
 *  com.fasterxml.jackson.databind.JsonMappingException
 *  com.fasterxml.jackson.databind.JsonSerializer
 *  com.fasterxml.jackson.databind.SerializerProvider
 */
package org.springframework.boot.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.Closeable;
import java.io.IOException;

public abstract class JsonObjectSerializer<T>
extends JsonSerializer<T> {
    public final void serialize(T value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        try {
            jgen.writeStartObject();
            this.serializeObject(value, jgen, provider);
            jgen.writeEndObject();
        }
        catch (Exception ex) {
            if (ex instanceof IOException) {
                throw (IOException)ex;
            }
            throw new JsonMappingException((Closeable)jgen, "Object serialize error", (Throwable)ex);
        }
    }

    protected abstract void serializeObject(T var1, JsonGenerator var2, SerializerProvider var3) throws IOException;
}

