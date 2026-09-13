/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.lang.Nullable
 *  org.springframework.messaging.MessageChannel
 *  org.springframework.messaging.MessageHandler
 */
package org.springframework.integration.handler;

import org.springframework.lang.Nullable;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

public interface DiscardingMessageHandler
extends MessageHandler {
    @Nullable
    public MessageChannel getDiscardChannel();
}

