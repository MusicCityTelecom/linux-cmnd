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

public interface MessageSendingOperations<D> {
    public void send(Message<?> var1) throws MessagingException;

    public void send(D var1, Message<?> var2) throws MessagingException;

    public void convertAndSend(Object var1) throws MessagingException;

    public void convertAndSend(D var1, Object var2) throws MessagingException;

    public void convertAndSend(D var1, Object var2, Map<String, Object> var3) throws MessagingException;

    public void convertAndSend(Object var1, @Nullable MessagePostProcessor var2) throws MessagingException;

    public void convertAndSend(D var1, Object var2, MessagePostProcessor var3) throws MessagingException;

    public void convertAndSend(D var1, Object var2, @Nullable Map<String, Object> var3, @Nullable MessagePostProcessor var4) throws MessagingException;
}

