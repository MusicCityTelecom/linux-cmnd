/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.jms.MessageListener
 *  org.springframework.lang.Nullable
 *  org.springframework.util.Assert
 */
package org.springframework.jms.config;

import javax.jms.MessageListener;
import org.springframework.jms.config.AbstractJmsListenerEndpoint;
import org.springframework.jms.listener.MessageListenerContainer;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

public class SimpleJmsListenerEndpoint
extends AbstractJmsListenerEndpoint {
    @Nullable
    private MessageListener messageListener;

    public void setMessageListener(@Nullable MessageListener messageListener) {
        this.messageListener = messageListener;
    }

    @Nullable
    public MessageListener getMessageListener() {
        return this.messageListener;
    }

    @Override
    protected MessageListener createMessageListener(MessageListenerContainer container) {
        MessageListener listener = this.getMessageListener();
        Assert.state((listener != null ? 1 : 0) != 0, (String)"No MessageListener set");
        return listener;
    }

    @Override
    protected StringBuilder getEndpointDescription() {
        return super.getEndpointDescription().append(" | messageListener='").append(this.messageListener).append('\'');
    }
}

