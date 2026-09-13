/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.messaging;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessagingException;

@FunctionalInterface
public interface MessageHandler {
    public void handleMessage(Message<?> var1) throws MessagingException;
}

