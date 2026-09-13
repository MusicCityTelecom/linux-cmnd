/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.messaging;

import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

public interface SubscribableChannel
extends MessageChannel {
    public boolean subscribe(MessageHandler var1);

    public boolean unsubscribe(MessageHandler var1);
}

