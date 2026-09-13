/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.messaging.Message
 *  org.springframework.messaging.MessageHandler
 *  org.springframework.messaging.MessagingException
 *  org.springframework.messaging.ReactiveMessageHandler
 *  org.springframework.util.Assert
 */
package org.springframework.integration.handler;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.ReactiveMessageHandler;
import org.springframework.util.Assert;

public class ReactiveMessageHandlerAdapter
implements MessageHandler {
    private final ReactiveMessageHandler delegate;

    public ReactiveMessageHandlerAdapter(ReactiveMessageHandler reactiveMessageHandler) {
        Assert.notNull((Object)reactiveMessageHandler, (String)"'reactiveMessageHandler' must not be null");
        this.delegate = reactiveMessageHandler;
    }

    public ReactiveMessageHandler getDelegate() {
        return this.delegate;
    }

    public void handleMessage(Message<?> message) throws MessagingException {
        this.delegate.handleMessage(message).subscribe();
    }
}

