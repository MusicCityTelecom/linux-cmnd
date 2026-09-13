/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.lang.Nullable
 *  org.springframework.messaging.MessageChannel
 */
package org.springframework.integration.core;

import org.springframework.lang.Nullable;
import org.springframework.messaging.MessageChannel;

public interface MessageProducer {
    public void setOutputChannel(MessageChannel var1);

    default public void setOutputChannelName(String outputChannel) {
        throw new UnsupportedOperationException("This MessageProducer does not support setting the channel by name.");
    }

    @Nullable
    public MessageChannel getOutputChannel();
}

