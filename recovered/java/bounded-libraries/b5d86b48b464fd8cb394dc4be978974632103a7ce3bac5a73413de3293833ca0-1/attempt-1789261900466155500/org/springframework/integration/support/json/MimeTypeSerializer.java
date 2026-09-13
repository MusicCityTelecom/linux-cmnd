/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonGenerator
 *  com.fasterxml.jackson.databind.SerializerProvider
 *  com.fasterxml.jackson.databind.jsontype.TypeSerializer
 *  com.fasterxml.jackson.databind.ser.std.StdSerializer
 *  org.springframework.util.MimeType
 */
package org.springframework.integration.support.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;
import org.springframework.util.MimeType;

public class MimeTypeSerializer
extends StdSerializer<MimeType> {
    private static final long serialVersionUID = 1L;

    public MimeTypeSerializer() {
        super(MimeType.class);
    }

    public void serializeWithType(MimeType value, JsonGenerator generator, SerializerProvider serializers, TypeSerializer typeSer) throws IOException {
        this.serialize(value, generator, serializers);
    }

    public void serialize(MimeType value, JsonGenerator generator, SerializerProvider provider) throws IOException {
        generator.writeString(value.toString());
    }
}

