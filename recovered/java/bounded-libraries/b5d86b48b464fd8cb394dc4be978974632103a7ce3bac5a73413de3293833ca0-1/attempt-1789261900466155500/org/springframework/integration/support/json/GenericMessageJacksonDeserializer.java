/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.DeserializationContext
 *  com.fasterxml.jackson.databind.JsonNode
 *  org.springframework.messaging.MessageHeaders
 *  org.springframework.messaging.support.GenericMessage
 */
package org.springframework.integration.support.json;

import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import java.io.IOException;
import org.springframework.integration.support.MutableMessageHeaders;
import org.springframework.integration.support.json.MessageJacksonDeserializer;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.GenericMessage;

public class GenericMessageJacksonDeserializer
extends MessageJacksonDeserializer<GenericMessage<?>> {
    private static final long serialVersionUID = 1L;

    public GenericMessageJacksonDeserializer() {
        super(GenericMessage.class);
    }

    @Override
    protected GenericMessage<?> buildMessage(MutableMessageHeaders headers, Object payload, JsonNode root, DeserializationContext ctxt) throws IOException {
        return new GenericMessage(payload, (MessageHeaders)headers);
    }
}

