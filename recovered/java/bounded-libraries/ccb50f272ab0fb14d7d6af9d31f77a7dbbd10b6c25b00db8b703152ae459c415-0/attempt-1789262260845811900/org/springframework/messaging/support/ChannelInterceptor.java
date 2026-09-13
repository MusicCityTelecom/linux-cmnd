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

public interface ChannelInterceptor {
    @Nullable
    default public Message<?> preSend(Message<?> message, MessageChannel channel) {
        return message;
    }

    default public void postSend(Message<?> message, MessageChannel channel, boolean sent) {
    }

    default public void afterSendCompletion(Message<?> message, MessageChannel channel, boolean sent, @Nullable Exception ex) {
    }

    default public boolean preReceive(MessageChannel channel) {
        return true;
    }

    @Nullable
    default public Message<?> postReceive(Message<?> message, MessageChannel channel) {
        return message;
    }

    default public void afterReceiveCompletion(@Nullable Message<?> message, MessageChannel channel, @Nullable Exception ex) {
    }
}

