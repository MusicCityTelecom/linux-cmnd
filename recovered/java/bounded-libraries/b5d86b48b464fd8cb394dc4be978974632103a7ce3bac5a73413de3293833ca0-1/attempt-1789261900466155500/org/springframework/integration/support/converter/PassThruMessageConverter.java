/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.messaging.Message
 *  org.springframework.messaging.MessageHeaders
 *  org.springframework.messaging.converter.MessageConverter
 *  org.springframework.messaging.support.MessageBuilder
 *  org.springframework.util.Assert
 */
package org.springframework.integration.support.converter;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.util.Assert;

public class PassThruMessageConverter
implements MessageConverter {
    public Object fromMessage(Message<?> message, Class<?> targetClass) {
        return message;
    }

    public Message<?> toMessage(Object payload, MessageHeaders headers) {
        Assert.isInstanceOf(byte[].class, (Object)payload, (String)"'payload' must be of 'byte[]' type.");
        return MessageBuilder.createMessage((Object)payload, (MessageHeaders)headers);
    }
}

