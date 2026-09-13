/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.jms.config;

import org.springframework.jms.config.JmsListenerEndpoint;
import org.springframework.jms.listener.MessageListenerContainer;

public interface JmsListenerContainerFactory<C extends MessageListenerContainer> {
    public C createListenerContainer(JmsListenerEndpoint var1);
}

