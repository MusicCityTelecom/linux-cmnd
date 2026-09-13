/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.messaging.Message
 */
package org.springframework.integration.support;

import org.springframework.messaging.Message;

@FunctionalInterface
public interface MessageDecorator {
    public Message<?> decorateMessage(Message<?> var1);
}

