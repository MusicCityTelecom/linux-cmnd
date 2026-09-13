/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.messaging.Message
 *  org.springframework.util.Assert
 */
package org.springframework.integration.aggregator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import org.springframework.integration.aggregator.AbstractAggregatingMessageGroupProcessor;
import org.springframework.integration.store.MessageGroup;
import org.springframework.messaging.Message;
import org.springframework.util.Assert;

public class DefaultAggregatingMessageGroupProcessor
extends AbstractAggregatingMessageGroupProcessor {
    @Override
    protected final Object aggregatePayloads(MessageGroup group, Map<String, Object> headers) {
        Collection<Message<?>> messages = group.getMessages();
        Assert.notEmpty(messages, (String)(this.getClass().getSimpleName() + " cannot process empty message groups"));
        ArrayList<Object> payloads = new ArrayList<Object>(messages.size());
        for (Message<?> message : messages) {
            payloads.add(message.getPayload());
        }
        return payloads;
    }
}

