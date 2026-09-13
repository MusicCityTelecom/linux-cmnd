/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.messaging.Message
 */
package org.springframework.integration.handler;

import org.springframework.integration.IntegrationPatternType;
import org.springframework.integration.handler.AbstractReplyProducingMessageHandler;
import org.springframework.messaging.Message;

public class BridgeHandler
extends AbstractReplyProducingMessageHandler {
    @Override
    public String getComponentType() {
        return "bridge";
    }

    @Override
    public IntegrationPatternType getIntegrationPatternType() {
        return IntegrationPatternType.bridge;
    }

    @Override
    protected Object handleRequestMessage(Message<?> requestMessage) {
        return requestMessage;
    }

    @Override
    protected boolean shouldCopyRequestHeaders() {
        return false;
    }
}

