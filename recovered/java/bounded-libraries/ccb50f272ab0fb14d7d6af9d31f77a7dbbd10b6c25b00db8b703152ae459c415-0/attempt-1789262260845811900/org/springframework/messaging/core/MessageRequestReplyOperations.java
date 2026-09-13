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

public interface MessageRequestReplyOperations<D> {
    @Nullable
    public Message<?> sendAndReceive(Message<?> var1) throws MessagingException;

    @Nullable
    public Message<?> sendAndReceive(D var1, Message<?> var2) throws MessagingException;

    @Nullable
    public <T> T convertSendAndReceive(Object var1, Class<T> var2) throws MessagingException;

    @Nullable
    public <T> T convertSendAndReceive(D var1, Object var2, Class<T> var3) throws MessagingException;

    @Nullable
    public <T> T convertSendAndReceive(D var1, Object var2, @Nullable Map<String, Object> var3, Class<T> var4) throws MessagingException;

    @Nullable
    public <T> T convertSendAndReceive(Object var1, Class<T> var2, @Nullable MessagePostProcessor var3) throws MessagingException;

    @Nullable
    public <T> T convertSendAndReceive(D var1, Object var2, Class<T> var3, MessagePostProcessor var4) throws MessagingException;

    @Nullable
    public <T> T convertSendAndReceive(D var1, Object var2, @Nullable Map<String, Object> var3, Class<T> var4, @Nullable MessagePostProcessor var5) throws MessagingException;
}

