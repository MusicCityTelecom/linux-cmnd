/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.messaging.Message
 *  org.springframework.messaging.MessageChannel
 */
package org.springframework.integration.dsl.support;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;

public class FixedSubscriberChannelPrototype
implements MessageChannel {
    private final String name;

    public FixedSubscriberChannelPrototype() {
        this(null);
    }

    public FixedSubscriberChannelPrototype(String name) {
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

    public String toString() {
        return "FixedSubscriberChannelPrototype{name='" + this.name + '\'' + '}';
    }
}

