/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonGenerator
 *  com.fasterxml.jackson.databind.SerializerProvider
 *  com.fasterxml.jackson.databind.ser.std.StdSerializer
 */
package com.fasterxml.jackson.datatype.jdk8;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.fasterxml.jackson.datatype.jdk8.WrappedIOException;
import java.io.IOException;
import java.util.stream.LongStream;

public class LongStreamSerializer
extends StdSerializer<LongStream> {
    private static final long serialVersionUID = 1L;
    public static final LongStreamSerializer INSTANCE = new LongStreamSerializer();

    private LongStreamSerializer() {
        super(LongStream.class);
    }

    public void serialize(LongStream stream, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        try (LongStream ls = stream;){
            jgen.writeStartArray();
            ls.forEachOrdered(value -> {
                try {
                    jgen.writeNumber(value);
                }
                catch (IOException e) {
                    throw new WrappedIOException(e);
                }
            });
            jgen.writeEndArray();
        }
        catch (WrappedIOException e) {
            throw e.getCause();
        }
    }
}

