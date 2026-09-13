/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.lang.Nullable
 */
package org.springframework.messaging;

import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;

public interface PollableChannel
extends MessageChannel {
    @Nullable
    public Message<?> receive();

    @Nullable
    public Message<?> receive(long var1);
}

