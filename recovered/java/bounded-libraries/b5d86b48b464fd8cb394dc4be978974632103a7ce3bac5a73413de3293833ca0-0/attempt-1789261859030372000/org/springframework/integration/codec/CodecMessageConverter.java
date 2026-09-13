/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.messaging.Message
 *  org.springframework.messaging.MessageHeaders
 *  org.springframework.messaging.MessagingException
 *  org.springframework.messaging.converter.MessageConverter
 *  org.springframework.messaging.support.GenericMessage
 *  org.springframework.util.Assert
 */
package org.springframework.integration.codec;

import java.io.IOException;
import java.util.Map;
import org.springframework.integration.codec.Codec;
import org.springframework.integration.context.IntegrationObjectSupport;
import org.springframework.integration.support.AbstractIntegrationMessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.util.Assert;

public class CodecMessageConverter
extends IntegrationObjectSupport
implements MessageConverter {
    private final Codec codec;
    private final Class<?> messageClass;

    public CodecMessageConverter(Codec codec) {
        this.codec = codec;
        this.messageClass = GenericMessage.class;
    }

    public Object fromMessage(Message<?> message, Class<?> targetClass) {
        try {
            return this.codec.encode(message);
        }
        catch (IOException e) {
            throw new MessagingException(message, "Failed to encode Message", (Throwable)e);
        }
    }

    public Message<?> toMessage(Object payload, MessageHeaders headers) {
        Assert.isInstanceOf(byte[].class, (Object)payload);
        try {
            Message decoded = (Message)this.codec.decode((byte[])payload, this.messageClass);
            if (headers != null) {
                AbstractIntegrationMessageBuilder builder = this.getMessageBuilderFactory().fromMessage(decoded);
                builder.copyHeaders((Map<String, ?>)headers);
                return builder.build();
            }
            return decoded;
        }
        catch (IOException e) {
            throw new MessagingException("Failed to decode", (Throwable)e);
        }
    }
}

