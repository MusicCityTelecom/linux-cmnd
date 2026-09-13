/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.messaging.Message
 *  org.springframework.messaging.MessageChannel
 *  org.springframework.util.Assert
 */
package org.springframework.integration.dsl.support;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.util.Assert;

public class MessageChannelReference
implements MessageChannel {
    private final String name;

    public MessageChannelReference(String name) {
        Assert.notNull((Object)name, (String)"'name' must not be null");
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public boolean send(Message<?> message) {
        throw new UnsupportedOperationException();
    }

    public boolean send(Message<?> message, long timeout) {
        throw new UnsupportedOperationException();
    }
}

