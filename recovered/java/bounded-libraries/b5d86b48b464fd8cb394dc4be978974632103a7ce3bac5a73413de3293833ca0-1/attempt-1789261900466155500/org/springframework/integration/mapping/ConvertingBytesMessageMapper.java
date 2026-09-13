/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.lang.NonNull
 *  org.springframework.lang.Nullable
 *  org.springframework.messaging.Message
 *  org.springframework.messaging.MessageHeaders
 *  org.springframework.messaging.converter.MessageConverter
 *  org.springframework.util.Assert
 */
package org.springframework.integration.mapping;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Map;
import org.springframework.integration.mapping.BytesMessageMapper;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.util.Assert;

public class ConvertingBytesMessageMapper
implements BytesMessageMapper {
    private final MessageConverter messageConverter;

    public ConvertingBytesMessageMapper(MessageConverter messageConverter) {
        Assert.notNull((Object)messageConverter, (String)"'messageConverter' must not be null");
        this.messageConverter = messageConverter;
    }

    @Override
    @NonNull
    public Message<?> toMessage(byte[] bytes, @Nullable Map<String, Object> headers) {
        Message message;
        MessageHeaders messageHeaders = null;
        if (headers != null) {
            messageHeaders = new MessageHeaders(headers);
        }
        Assert.state(((message = this.messageConverter.toMessage((Object)bytes, messageHeaders)) != null ? 1 : 0) != 0, () -> "the '" + this.messageConverter + "' produced null for bytes:" + Arrays.toString(bytes));
        return message;
    }

    @Override
    @NonNull
    public byte[] fromMessage(Message<?> message) {
        Object result = this.messageConverter.fromMessage(message, byte[].class);
        Assert.state((result != null ? 1 : 0) != 0, () -> "the '" + this.messageConverter + "' produced null for message: " + message);
        return result instanceof String ? ((String)result).getBytes(StandardCharsets.UTF_8) : (byte[])result;
    }
}

