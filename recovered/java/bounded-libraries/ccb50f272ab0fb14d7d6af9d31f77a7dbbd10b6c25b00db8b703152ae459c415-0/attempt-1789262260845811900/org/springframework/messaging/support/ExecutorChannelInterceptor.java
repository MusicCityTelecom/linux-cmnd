/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.lang.Nullable
 */
package org.springframework.messaging.support;

import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.support.ChannelInterceptor;

public interface ExecutorChannelInterceptor
extends ChannelInterceptor {
    @Nullable
    default public Message<?> beforeHandle(Message<?> message, MessageChannel channel, MessageHandler handler) {
        return message;
    }

    default public void afterMessageHandled(Message<?> message, MessageChannel channel, MessageHandler handler, @Nullable Exception ex) {
    }
}

