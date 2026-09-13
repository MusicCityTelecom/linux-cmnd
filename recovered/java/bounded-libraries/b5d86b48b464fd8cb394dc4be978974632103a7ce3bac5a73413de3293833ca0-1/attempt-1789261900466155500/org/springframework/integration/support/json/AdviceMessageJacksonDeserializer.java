/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.DeserializationContext
 *  com.fasterxml.jackson.databind.JsonNode
 *  org.springframework.messaging.Message
 */
package org.springframework.integration.support.json;

import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import java.io.IOException;
import org.springframework.integration.message.AdviceMessage;
import org.springframework.integration.support.MutableMessageHeaders;
import org.springframework.integration.support.json.MessageJacksonDeserializer;
import org.springframework.messaging.Message;

public class AdviceMessageJacksonDeserializer
extends MessageJacksonDeserializer<AdviceMessage<?>> {
    private static final long serialVersionUID = 1L;

    public AdviceMessageJacksonDeserializer() {
        super(AdviceMessage.class);
    }

    @Override
    protected AdviceMessage<?> buildMessage(MutableMessageHeaders headers, Object payload, JsonNode root, DeserializationContext ctxt) throws IOException {
        Message inputMessage = (Message)this.getMapper().readValue(root.get("inputMessage").traverse(), Message.class);
        return new AdviceMessage<Object>(payload, headers, inputMessage);
    }
}

