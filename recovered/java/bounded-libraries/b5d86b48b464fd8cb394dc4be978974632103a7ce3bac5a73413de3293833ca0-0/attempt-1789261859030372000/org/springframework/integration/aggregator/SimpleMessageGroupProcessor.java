/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.integration.aggregator;

import org.springframework.integration.aggregator.MessageGroupProcessor;
import org.springframework.integration.store.MessageGroup;

public class SimpleMessageGroupProcessor
implements MessageGroupProcessor {
    @Override
    public Object processMessageGroup(MessageGroup group) {
        return group.getMessages();
    }
}

