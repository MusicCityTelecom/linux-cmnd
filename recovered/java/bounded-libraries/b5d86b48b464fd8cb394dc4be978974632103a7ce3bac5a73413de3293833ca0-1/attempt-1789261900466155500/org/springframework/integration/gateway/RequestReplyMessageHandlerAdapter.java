/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.messaging.Message
 *  org.springframework.util.Assert
 */
package org.springframework.integration.gateway;

import org.springframework.integration.gateway.RequestReplyExchanger;
import org.springframework.integration.handler.AbstractReplyProducingMessageHandler;
import org.springframework.messaging.Message;
import org.springframework.util.Assert;

class RequestReplyMessageHandlerAdapter
extends AbstractReplyProducingMessageHandler {
    private final RequestReplyExchanger exchanger;

    RequestReplyMessageHandlerAdapter(RequestReplyExchanger exchanger) {
        Assert.notNull((Object)exchanger, (String)"exchanger must not be null");
        this.exchanger = exchanger;
    }

    @Override
    protected Object handleRequestMessage(Message<?> requestMessage) {
        return this.exchanger.exchange(requestMessage);
    }
}

