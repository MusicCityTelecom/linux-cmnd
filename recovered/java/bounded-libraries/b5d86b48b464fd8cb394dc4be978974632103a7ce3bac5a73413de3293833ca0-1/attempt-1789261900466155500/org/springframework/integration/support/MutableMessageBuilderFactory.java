/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.messaging.Message
 */
package org.springframework.integration.support;

import org.springframework.integration.support.MessageBuilderFactory;
import org.springframework.integration.support.MutableMessageBuilder;
import org.springframework.messaging.Message;

public class MutableMessageBuilderFactory
implements MessageBuilderFactory {
    public <T> MutableMessageBuilder<T> fromMessage(Message<T> message) {
        return MutableMessageBuilder.fromMessage(message);
    }

    public <T> MutableMessageBuilder<T> withPayload(T payload) {
        return MutableMessageBuilder.withPayload(payload);
    }
}

