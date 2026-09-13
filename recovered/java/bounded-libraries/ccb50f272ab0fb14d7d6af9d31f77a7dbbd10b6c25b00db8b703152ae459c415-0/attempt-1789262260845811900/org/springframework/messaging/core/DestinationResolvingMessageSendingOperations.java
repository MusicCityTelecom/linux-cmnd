/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.lang.Nullable
 */
package org.springframework.messaging.core;

import java.util.Map;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.core.MessagePostProcessor;
import org.springframework.messaging.core.MessageSendingOperations;

public interface DestinationResolvingMessageSendingOperations<D>
extends MessageSendingOperations<D> {
    @Override
    public void send(String var1, Message<?> var2) throws MessagingException;

    @Override
    public <T> void convertAndSend(String var1, T var2) throws MessagingException;

    @Override
    public <T> void convertAndSend(String var1, T var2, @Nullable Map<String, Object> var3) throws MessagingException;

    @Override
    public <T> void convertAndSend(String var1, T var2, @Nullable MessagePostProcessor var3) throws MessagingException;

    @Override
    public <T> void convertAndSend(String var1, T var2, @Nullable Map<String, Object> var3, @Nullable MessagePostProcessor var4) throws MessagingException;
}

