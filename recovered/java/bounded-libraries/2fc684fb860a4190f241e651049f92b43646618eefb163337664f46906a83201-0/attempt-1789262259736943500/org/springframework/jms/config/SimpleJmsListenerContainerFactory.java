/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.jms.config;

import org.springframework.jms.config.AbstractJmsListenerContainerFactory;
import org.springframework.jms.listener.SimpleMessageListenerContainer;

public class SimpleJmsListenerContainerFactory
extends AbstractJmsListenerContainerFactory<SimpleMessageListenerContainer> {
    @Override
    protected SimpleMessageListenerContainer createContainerInstance() {
        return new SimpleMessageListenerContainer();
    }
}

