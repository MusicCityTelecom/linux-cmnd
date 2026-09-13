/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.DeserializationContext
 *  com.fasterxml.jackson.databind.JsonNode
 */
package org.springframework.integration.support.json;

import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import java.io.IOException;
import java.util.Map;
import org.springframework.integration.support.MutableMessage;
import org.springframework.integration.support.MutableMessageHeaders;
import org.springframework.integration.support.json.MessageJacksonDeserializer;

public class MutableMessageJacksonDeserializer
extends MessageJacksonDeserializer<MutableMessage<?>> {
    private static final long serialVersionUID = 1L;

    public MutableMessageJacksonDeserializer() {
        super(MutableMessage.class);
    }

    @Override
    protected MutableMessage<?> buildMessage(MutableMessageHeaders headers, Object payload, JsonNode root, DeserializationContext ctxt) throws IOException {
        return new MutableMessage<Object>(payload, (Map<String, Object>)((Object)headers));
    }
}

