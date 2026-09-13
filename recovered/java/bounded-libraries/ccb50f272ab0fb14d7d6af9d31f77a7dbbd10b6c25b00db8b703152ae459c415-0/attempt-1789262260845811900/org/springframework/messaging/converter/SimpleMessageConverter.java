/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.lang.Nullable
 *  org.springframework.util.ClassUtils
 */
package org.springframework.messaging.converter;

import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.util.ClassUtils;

public class SimpleMessageConverter
implements MessageConverter {
    @Override
    @Nullable
    public Object fromMessage(Message<?> message, Class<?> targetClass) {
        Object payload = message.getPayload();
        return ClassUtils.isAssignableValue(targetClass, payload) ? payload : null;
    }

    @Override
    public Message<?> toMessage(Object payload, @Nullable MessageHeaders headers) {
        MessageHeaderAccessor accessor;
        if (headers != null && (accessor = MessageHeaderAccessor.getAccessor(headers, MessageHeaderAccessor.class)) != null && accessor.isMutable()) {
            return MessageBuilder.createMessage(payload, accessor.getMessageHeaders());
        }
        return MessageBuilder.withPayload(payload).copyHeaders(headers).build();
    }
}

