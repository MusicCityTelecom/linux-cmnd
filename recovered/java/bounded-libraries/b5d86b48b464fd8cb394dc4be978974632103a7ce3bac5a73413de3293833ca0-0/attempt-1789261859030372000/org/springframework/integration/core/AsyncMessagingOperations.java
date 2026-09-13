/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.messaging.Message
 *  org.springframework.messaging.MessageChannel
 *  org.springframework.messaging.PollableChannel
 *  org.springframework.messaging.core.MessagePostProcessor
 */
package org.springframework.integration.core;

import java.util.concurrent.Future;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.PollableChannel;
import org.springframework.messaging.core.MessagePostProcessor;

public interface AsyncMessagingOperations {
    public Future<?> asyncSend(Message<?> var1);

    public Future<?> asyncSend(MessageChannel var1, Message<?> var2);

    public Future<?> asyncSend(String var1, Message<?> var2);

    public Future<?> asyncConvertAndSend(Object var1);

    public Future<?> asyncConvertAndSend(MessageChannel var1, Object var2);

    public Future<?> asyncConvertAndSend(String var1, Object var2);

    public Future<Message<?>> asyncReceive();

    public Future<Message<?>> asyncReceive(PollableChannel var1);

    public Future<Message<?>> asyncReceive(String var1);

    public <R> Future<R> asyncReceiveAndConvert();

    public <R> Future<R> asyncReceiveAndConvert(PollableChannel var1);

    public <R> Future<R> asyncReceiveAndConvert(String var1);

    public Future<Message<?>> asyncSendAndReceive(Message<?> var1);

    public Future<Message<?>> asyncSendAndReceive(MessageChannel var1, Message<?> var2);

    public Future<Message<?>> asyncSendAndReceive(String var1, Message<?> var2);

    public <R> Future<R> asyncConvertSendAndReceive(Object var1);

    public <R> Future<R> asyncConvertSendAndReceive(MessageChannel var1, Object var2);

    public <R> Future<R> asyncConvertSendAndReceive(String var1, Object var2);

    public <R> Future<R> asyncConvertSendAndReceive(Object var1, MessagePostProcessor var2);

    public <R> Future<R> asyncConvertSendAndReceive(MessageChannel var1, Object var2, MessagePostProcessor var3);

    public <R> Future<R> asyncConvertSendAndReceive(String var1, Object var2, MessagePostProcessor var3);
}

