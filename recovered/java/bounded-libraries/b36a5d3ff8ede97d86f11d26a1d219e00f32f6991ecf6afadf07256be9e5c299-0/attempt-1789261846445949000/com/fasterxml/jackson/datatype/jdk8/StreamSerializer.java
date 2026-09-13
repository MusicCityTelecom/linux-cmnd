/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonGenerator
 *  com.fasterxml.jackson.databind.BeanProperty
 *  com.fasterxml.jackson.databind.JavaType
 *  com.fasterxml.jackson.databind.JsonMappingException
 *  com.fasterxml.jackson.databind.JsonSerializer
 *  com.fasterxml.jackson.databind.MapperFeature
 *  com.fasterxml.jackson.databind.SerializerProvider
 *  com.fasterxml.jackson.databind.ser.ContextualSerializer
 *  com.fasterxml.jackson.databind.ser.std.StdSerializer
 */
package com.fasterxml.jackson.datatype.jdk8;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.fasterxml.jackson.datatype.jdk8.WrappedIOException;
import java.io.IOException;
import java.util.stream.Stream;

public class StreamSerializer
extends StdSerializer<Stream<?>>
implements ContextualSerializer {
    private static final long serialVersionUID = 1L;
    private final JavaType elemType;
    private final transient JsonSerializer<Object> elemSerializer;

    public StreamSerializer(JavaType streamType, JavaType elemType) {
        this(streamType, elemType, null);
    }

    public StreamSerializer(JavaType streamType, JavaType elemType, JsonSerializer<Object> elemSerializer) {
        super(streamType);
        this.elemType = elemType;
        this.elemSerializer = elemSerializer;
    }

    public JsonSerializer<?> createContextual(SerializerProvider provider, BeanProperty property) throws JsonMappingException {
        if (!this.elemType.hasRawClass(Object.class) && (provider.isEnabled(MapperFeature.USE_STATIC_TYPING) || this.elemType.isFinal())) {
            return new StreamSerializer(provider.getTypeFactory().constructParametricType(Stream.class, new JavaType[]{this.elemType}), this.elemType, (JsonSerializer<Object>)provider.findPrimaryPropertySerializer(this.elemType, property));
        }
        return this;
    }

    public void serialize(Stream<?> stream, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        try (Stream<?> s = stream;){
            jgen.writeStartArray();
            s.forEachOrdered(elem -> {
                try {
                    if (this.elemSerializer == null) {
                        provider.defaultSerializeValue(elem, jgen);
                    } else {
                        this.elemSerializer.serialize(elem, jgen, provider);
                    }
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

