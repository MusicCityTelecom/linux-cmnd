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
import java.util.stream.DoubleStream;

public class DoubleStreamSerializer
extends StdSerializer<DoubleStream> {
    private static final long serialVersionUID = 1L;
    public static final DoubleStreamSerializer INSTANCE = new DoubleStreamSerializer();

    private DoubleStreamSerializer() {
        super(DoubleStream.class);
    }

    public void serialize(DoubleStream stream, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        try (DoubleStream ds = stream;){
            jgen.writeStartArray();
            ds.forEachOrdered(value -> {
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

