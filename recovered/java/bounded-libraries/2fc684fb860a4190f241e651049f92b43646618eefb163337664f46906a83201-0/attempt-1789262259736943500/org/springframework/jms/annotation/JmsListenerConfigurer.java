/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.jms.annotation;

import org.springframework.jms.config.JmsListenerEndpointRegistrar;

@FunctionalInterface
public interface JmsListenerConfigurer {
    public void configureJmsListeners(JmsListenerEndpointRegistrar var1);
}

