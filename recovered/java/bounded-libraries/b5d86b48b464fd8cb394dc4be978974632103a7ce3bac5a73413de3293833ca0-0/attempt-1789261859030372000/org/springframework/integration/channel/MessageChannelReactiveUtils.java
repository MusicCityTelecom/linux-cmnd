/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Publisher
 *  org.springframework.messaging.Message
 *  org.springframework.messaging.MessageChannel
 */
package org.springframework.integration.channel;

import org.reactivestreams.Publisher;
import org.springframework.integration.util.IntegrationReactiveUtils;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;

@Deprecated
public final class MessageChannelReactiveUtils {
    private MessageChannelReactiveUtils() {
    }

    public static <T> Publisher<Message<T>> toPublisher(MessageChannel messageChannel) {
        return IntegrationReactiveUtils.messageChannelToFlux(messageChannel);
    }
}

