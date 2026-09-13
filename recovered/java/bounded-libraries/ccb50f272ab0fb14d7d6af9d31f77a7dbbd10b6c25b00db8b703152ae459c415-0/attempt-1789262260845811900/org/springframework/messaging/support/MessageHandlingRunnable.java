/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.messaging.support;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;

public interface MessageHandlingRunnable
extends Runnable {
    public Message<?> getMessage();

    public MessageHandler getMessageHandler();
}

