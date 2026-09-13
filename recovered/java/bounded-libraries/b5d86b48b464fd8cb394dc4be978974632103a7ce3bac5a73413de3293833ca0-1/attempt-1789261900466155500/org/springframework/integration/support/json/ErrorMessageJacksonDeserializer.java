/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.DeserializationContext
 *  com.fasterxml.jackson.databind.JsonNode
 *  com.fasterxml.jackson.databind.type.TypeFactory
 *  org.springframework.messaging.Message
 *  org.springframework.messaging.MessageHeaders
 *  org.springframework.messaging.support.ErrorMessage
 */
package org.springframework.integration.support.json;

import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.type.TypeFactory;
import java.io.IOException;
import org.springframework.integration.support.MutableMessageHeaders;
import org.springframework.integration.support.json.MessageJacksonDeserializer;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.ErrorMessage;

public class ErrorMessageJacksonDeserializer
extends MessageJacksonDeserializer<ErrorMessage> {
    private static final long serialVersionUID = 1L;

    public ErrorMessageJacksonDeserializer() {
        super(ErrorMessage.class);
        this.setPayloadType(TypeFactory.defaultInstance().constructType(Throwable.class));
    }

    @Override
    protected ErrorMessage buildMessage(MutableMessageHeaders headers, Object payload, JsonNode root, DeserializationContext ctxt) throws IOException {
        Message originalMessage = (Message)this.getMapper().readValue(root.get("originalMessage").traverse(), Message.class);
        return new ErrorMessage((Throwable)payload, (MessageHeaders)headers, originalMessage);
    }
}

