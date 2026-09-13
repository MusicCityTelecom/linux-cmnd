/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.messaging.Message
 *  org.springframework.messaging.converter.StringMessageConverter
 */
package org.springframework.integration.support.converter;

import org.springframework.messaging.Message;
import org.springframework.messaging.converter.StringMessageConverter;

public class ObjectStringMessageConverter
extends StringMessageConverter {
    protected Object convertFromInternal(Message<?> message, Class<?> targetClass, Object conversionHint) {
        Object payload = message.getPayload();
        if (payload instanceof String || payload instanceof byte[]) {
            return super.convertFromInternal(message, targetClass, conversionHint);
        }
        return payload.toString();
    }
}

