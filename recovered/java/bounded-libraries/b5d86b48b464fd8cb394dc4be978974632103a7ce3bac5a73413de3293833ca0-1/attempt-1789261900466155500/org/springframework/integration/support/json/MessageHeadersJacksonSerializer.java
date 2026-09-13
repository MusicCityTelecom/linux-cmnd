/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonGenerator
 *  com.fasterxml.jackson.databind.SerializerProvider
 *  com.fasterxml.jackson.databind.jsontype.TypeSerializer
 *  com.fasterxml.jackson.databind.ser.std.StdSerializer
 *  org.springframework.messaging.MessageHeaders
 */
package org.springframework.integration.support.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;
import java.util.HashMap;
import org.springframework.messaging.MessageHeaders;

public class MessageHeadersJacksonSerializer
extends StdSerializer<MessageHeaders> {
    private static final long serialVersionUID = 1L;

    public MessageHeadersJacksonSerializer() {
        super(MessageHeaders.class);
    }

    public void serializeWithType(MessageHeaders value, JsonGenerator gen, SerializerProvider serializers, TypeSerializer typeSer) throws IOException {
        this.serialize(value, gen, serializers);
    }

    public void serialize(MessageHeaders value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeObject(new HashMap(value));
    }
}

