/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.jms.config;

import org.springframework.jms.listener.MessageListenerContainer;

public interface JmsListenerEndpoint {
    public String getId();

    public void setupListenerContainer(MessageListenerContainer var1);
}

