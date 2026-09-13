/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.messaging.core;

import org.springframework.messaging.Message;

@FunctionalInterface
public interface MessagePostProcessor {
    public Message<?> postProcessMessage(Message<?> var1);
}

