/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.lang.Nullable
 */
package org.springframework.messaging.core;

import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.core.MessageReceivingOperations;

public interface DestinationResolvingMessageReceivingOperations<D>
extends MessageReceivingOperations<D> {
    @Override
    @Nullable
    public Message<?> receive(String var1) throws MessagingException;

    @Override
    @Nullable
    public <T> T receiveAndConvert(String var1, Class<T> var2) throws MessagingException;
}

