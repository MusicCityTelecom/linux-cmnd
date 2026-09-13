/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.jms.Destination
 *  org.springframework.lang.Nullable
 *  org.springframework.messaging.Message
 *  org.springframework.messaging.MessagingException
 *  org.springframework.messaging.core.MessagePostProcessor
 *  org.springframework.messaging.core.MessageReceivingOperations
 *  org.springframework.messaging.core.MessageRequestReplyOperations
 *  org.springframework.messaging.core.MessageSendingOperations
 */
package org.springframework.jms.core;

import java.util.Map;
import javax.jms.Destination;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.core.MessagePostProcessor;
import org.springframework.messaging.core.MessageReceivingOperations;
import org.springframework.messaging.core.MessageRequestReplyOperations;
import org.springframework.messaging.core.MessageSendingOperations;

public interface JmsMessageOperations
extends MessageSendingOperations<Destination>,
MessageReceivingOperations<Destination>,
MessageRequestReplyOperations<Destination> {
    public void send(String var1, Message<?> var2) throws MessagingException;

    public void convertAndSend(String var1, Object var2) throws MessagingException;

    public void convertAndSend(String var1, Object var2, Map<String, Object> var3) throws MessagingException;

    public void convertAndSend(String var1, Object var2, MessagePostProcessor var3) throws MessagingException;

    public void convertAndSend(String var1, Object var2, @Nullable Map<String, Object> var3, @Nullable MessagePostProcessor var4) throws MessagingException;

    @Nullable
    public Message<?> receive(String var1) throws MessagingException;

    @Nullable
    public <T> T receiveAndConvert(String var1, Class<T> var2) throws MessagingException;

    @Nullable
    public Message<?> sendAndReceive(String var1, Message<?> var2) throws MessagingException;

    @Nullable
    public <T> T convertSendAndReceive(String var1, Object var2, Class<T> var3) throws MessagingException;

    @Nullable
    public <T> T convertSendAndReceive(String var1, Object var2, @Nullable Map<String, Object> var3, Class<T> var4) throws MessagingException;

    @Nullable
    public <T> T convertSendAndReceive(String var1, Object var2, Class<T> var3, MessagePostProcessor var4) throws MessagingException;

    @Nullable
    public <T> T convertSendAndReceive(String var1, Object var2, Map<String, Object> var3, Class<T> var4, MessagePostProcessor var5) throws MessagingException;
}

