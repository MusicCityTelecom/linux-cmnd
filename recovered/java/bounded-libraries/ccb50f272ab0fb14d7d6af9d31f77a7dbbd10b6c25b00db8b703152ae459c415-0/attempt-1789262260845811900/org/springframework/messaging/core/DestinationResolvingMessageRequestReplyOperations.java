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
import org.springframework.messaging.core.MessageRequestReplyOperations;

public interface DestinationResolvingMessageRequestReplyOperations<D>
extends MessageRequestReplyOperations<D> {
    @Override
    @Nullable
    public Message<?> sendAndReceive(String var1, Message<?> var2) throws MessagingException;

    @Override
    @Nullable
    public <T> T convertSendAndReceive(String var1, Object var2, Class<T> var3) throws MessagingException;

    @Override
    @Nullable
    public <T> T convertSendAndReceive(String var1, Object var2, @Nullable Map<String, Object> var3, Class<T> var4) throws MessagingException;

    @Override
    @Nullable
    public <T> T convertSendAndReceive(String var1, Object var2, Class<T> var3, @Nullable MessagePostProcessor var4) throws MessagingException;

    @Override
    @Nullable
    public <T> T convertSendAndReceive(String var1, Object var2, @Nullable Map<String, Object> var3, Class<T> var4, @Nullable MessagePostProcessor var5) throws MessagingException;
}

