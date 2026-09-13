/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.integration.endpoint;

import org.springframework.integration.endpoint.AbstractMessageSource;
import org.springframework.integration.handler.MessageProcessor;

public class MessageProcessorMessageSource
extends AbstractMessageSource<Object> {
    private final MessageProcessor<?> messageProcessor;

    public MessageProcessorMessageSource(MessageProcessor<?> messageProcessor) {
        this.messageProcessor = messageProcessor;
    }

    @Override
    public String getComponentType() {
        return "inbound-channel-adapter";
    }

    @Override
    protected Object doReceive() {
        return this.messageProcessor.processMessage(null);
    }
}

