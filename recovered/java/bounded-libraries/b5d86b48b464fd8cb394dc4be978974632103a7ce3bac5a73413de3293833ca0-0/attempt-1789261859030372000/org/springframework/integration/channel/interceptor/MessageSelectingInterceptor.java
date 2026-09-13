/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.messaging.Message
 *  org.springframework.messaging.MessageChannel
 *  org.springframework.messaging.MessageDeliveryException
 *  org.springframework.messaging.support.ChannelInterceptor
 */
package org.springframework.integration.channel.interceptor;

import java.util.Arrays;
import java.util.List;
import org.springframework.integration.core.MessageSelector;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageDeliveryException;
import org.springframework.messaging.support.ChannelInterceptor;

public class MessageSelectingInterceptor
implements ChannelInterceptor {
    private final List<MessageSelector> selectors;

    public MessageSelectingInterceptor(MessageSelector ... selectors) {
        this.selectors = Arrays.asList(selectors);
    }

    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        for (MessageSelector selector : this.selectors) {
            if (selector.accept(message)) continue;
            throw new MessageDeliveryException(message, "selector '" + selector + "' did not accept message");
        }
        return message;
    }
}

