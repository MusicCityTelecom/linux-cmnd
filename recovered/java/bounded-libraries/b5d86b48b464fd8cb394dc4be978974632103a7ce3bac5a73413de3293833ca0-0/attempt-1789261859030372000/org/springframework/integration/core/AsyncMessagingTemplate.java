/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.task.AsyncTaskExecutor
 *  org.springframework.core.task.SimpleAsyncTaskExecutor
 *  org.springframework.core.task.support.TaskExecutorAdapter
 *  org.springframework.messaging.Message
 *  org.springframework.messaging.MessageChannel
 *  org.springframework.messaging.PollableChannel
 *  org.springframework.messaging.core.MessagePostProcessor
 *  org.springframework.util.Assert
 */
package org.springframework.integration.core;

import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.support.TaskExecutorAdapter;
import org.springframework.integration.core.AsyncMessagingOperations;
import org.springframework.integration.core.MessagingTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.PollableChannel;
import org.springframework.messaging.core.MessagePostProcessor;
import org.springframework.util.Assert;

public class AsyncMessagingTemplate
extends MessagingTemplate
implements AsyncMessagingOperations {
    private static final String UNCHECKED = "unchecked";
    private volatile AsyncTaskExecutor executor = new SimpleAsyncTaskExecutor();

    public void setExecutor(Executor executor) {
        Assert.notNull((Object)executor, (String)"executor must not be null");
        this.executor = executor instanceof AsyncTaskExecutor ? (AsyncTaskExecutor)executor : new TaskExecutorAdapter(executor);
    }

    @Override
    public Future<?> asyncSend(Message<?> message) {
        return this.executor.submit(() -> this.send(message));
    }

    @Override
    public Future<?> asyncSend(MessageChannel channel, Message<?> message) {
        return this.executor.submit(() -> this.send(channel, message));
    }

    @Override
    public Future<?> asyncSend(String channelName, Message<?> message) {
        return this.executor.submit(() -> this.send(channelName, message));
    }

    @Override
    public Future<?> asyncConvertAndSend(Object object) {
        return this.executor.submit(() -> this.convertAndSend(object));
    }

    @Override
    public Future<?> asyncConvertAndSend(MessageChannel channel, Object object) {
        return this.executor.submit(() -> this.convertAndSend(channel, object));
    }

    @Override
    public Future<?> asyncConvertAndSend(String channelName, Object object) {
        return this.executor.submit(() -> this.convertAndSend(channelName, object));
    }

    @Override
    public Future<Message<?>> asyncReceive() {
        return this.executor.submit(() -> this.receive());
    }

    @Override
    public Future<Message<?>> asyncReceive(PollableChannel channel) {
        return this.executor.submit(() -> this.receive(channel));
    }

    @Override
    public Future<Message<?>> asyncReceive(String channelName) {
        return this.executor.submit(() -> this.receive(channelName));
    }

    @Override
    public <R> Future<R> asyncReceiveAndConvert() {
        return this.executor.submit(() -> this.receiveAndConvert(Object.class));
    }

    @Override
    public <R> Future<R> asyncReceiveAndConvert(PollableChannel channel) {
        return this.executor.submit(() -> this.receiveAndConvert(channel, Object.class));
    }

    @Override
    public <R> Future<R> asyncReceiveAndConvert(String channelName) {
        return this.executor.submit(() -> this.receiveAndConvert(channelName, Object.class));
    }

    @Override
    public Future<Message<?>> asyncSendAndReceive(Message<?> requestMessage) {
        return this.executor.submit(() -> this.sendAndReceive(requestMessage));
    }

    @Override
    public Future<Message<?>> asyncSendAndReceive(MessageChannel channel, Message<?> requestMessage) {
        return this.executor.submit(() -> this.sendAndReceive(channel, requestMessage));
    }

    @Override
    public Future<Message<?>> asyncSendAndReceive(String channelName, Message<?> requestMessage) {
        return this.executor.submit(() -> this.sendAndReceive(channelName, requestMessage));
    }

    @Override
    public <R> Future<R> asyncConvertSendAndReceive(Object request) {
        return this.executor.submit(() -> this.convertSendAndReceive(request, Object.class));
    }

    @Override
    public <R> Future<R> asyncConvertSendAndReceive(MessageChannel channel, Object request) {
        return this.executor.submit(() -> this.convertSendAndReceive(channel, request, Object.class));
    }

    @Override
    public <R> Future<R> asyncConvertSendAndReceive(String channelName, Object request) {
        return this.executor.submit(() -> this.convertSendAndReceive(channelName, request, Object.class));
    }

    @Override
    public <R> Future<R> asyncConvertSendAndReceive(Object request, MessagePostProcessor requestPostProcessor) {
        return this.executor.submit(() -> this.convertSendAndReceive(request, Object.class, requestPostProcessor));
    }

    @Override
    public <R> Future<R> asyncConvertSendAndReceive(MessageChannel channel, Object request, MessagePostProcessor requestPostProcessor) {
        return this.executor.submit(() -> this.convertSendAndReceive(channel, request, Object.class, requestPostProcessor));
    }

    @Override
    public <R> Future<R> asyncConvertSendAndReceive(String channelName, Object request, MessagePostProcessor requestPostProcessor) {
        return this.executor.submit(() -> this.convertSendAndReceive(channelName, request, Object.class, requestPostProcessor));
    }
}

