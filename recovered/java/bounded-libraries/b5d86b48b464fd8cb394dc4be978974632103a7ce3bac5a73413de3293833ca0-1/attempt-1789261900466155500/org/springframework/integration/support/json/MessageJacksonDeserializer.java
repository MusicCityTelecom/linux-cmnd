/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.DeserializationContext
 *  com.fasterxml.jackson.databind.JavaType
 *  com.fasterxml.jackson.databind.JsonNode
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.fasterxml.jackson.databind.deser.std.StdNodeBasedDeserializer
 *  com.fasterxml.jackson.databind.type.TypeFactory
 *  org.springframework.messaging.Message
 *  org.springframework.util.Assert
 */
package org.springframework.integration.support.json;

import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdNodeBasedDeserializer;
import com.fasterxml.jackson.databind.type.TypeFactory;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.integration.support.MutableMessageHeaders;
import org.springframework.messaging.Message;
import org.springframework.util.Assert;

public abstract class MessageJacksonDeserializer<T extends Message<?>>
extends StdNodeBasedDeserializer<T> {
    private static final long serialVersionUID = 1L;
    private JavaType payloadType = TypeFactory.defaultInstance().constructType(Object.class);
    private ObjectMapper mapper = new ObjectMapper();

    protected MessageJacksonDeserializer(Class<T> targetType) {
        super(targetType);
    }

    public void setMapper(ObjectMapper mapper) {
        Assert.notNull((Object)mapper, (String)"'mapper' must not be null");
        this.mapper = mapper;
    }

    protected final void setPayloadType(JavaType payloadType) {
        Assert.notNull((Object)payloadType, (String)"'payloadType' must not be null");
        this.payloadType = payloadType;
    }

    protected ObjectMapper getMapper() {
        return this.mapper;
    }

    public T convert(JsonNode root, DeserializationContext ctxt) throws IOException {
        Map headers = (Map)this.mapper.readValue(root.get("headers").traverse(), (JavaType)TypeFactory.defaultInstance().constructMapType(HashMap.class, String.class, Object.class));
        Object payload = this.mapper.readValue(root.get("payload").traverse(), this.payloadType);
        return this.buildMessage(new MutableMessageHeaders(headers), payload, root, ctxt);
    }

    protected abstract T buildMessage(MutableMessageHeaders var1, Object var2, JsonNode var3, DeserializationContext var4) throws IOException;
}

