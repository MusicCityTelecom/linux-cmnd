/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.lang.Nullable
 *  org.springframework.util.Assert
 */
package org.springframework.messaging.core;

import java.util.Map;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.core.AbstractMessagingTemplate;
import org.springframework.messaging.core.DestinationResolver;
import org.springframework.messaging.core.DestinationResolvingMessageReceivingOperations;
import org.springframework.messaging.core.DestinationResolvingMessageRequestReplyOperations;
import org.springframework.messaging.core.DestinationResolvingMessageSendingOperations;
import org.springframework.messaging.core.MessagePostProcessor;
import org.springframework.util.Assert;

public abstract class AbstractDestinationResolvingMessagingTemplate<D>
extends AbstractMessagingTemplate<D>
implements DestinationResolvingMessageSendingOperations<D>,
DestinationResolvingMessageReceivingOperations<D>,
DestinationResolvingMessageRequestReplyOperations<D> {
    @Nullable
    private DestinationResolver<D> destinationResolver;

    public void setDestinationResolver(@Nullable DestinationResolver<D> destinationResolver) {
        this.destinationResolver = destinationResolver;
    }

    @Nullable
    public DestinationResolver<D> getDestinationResolver() {
        return this.destinationResolver;
    }

    @Override
    public void send(String destinationName, Message<?> message) {
        D destination = this.resolveDestination(destinationName);
        this.doSend(destination, message);
    }

    protected final D resolveDestination(String destinationName) {
        Assert.state((this.destinationResolver != null ? 1 : 0) != 0, (String)"DestinationResolver is required to resolve destination names");
        return this.destinationResolver.resolveDestination(destinationName);
    }

    @Override
    public <T> void convertAndSend(String destinationName, T payload) {
        this.convertAndSend(destinationName, payload, (Map<String, Object>)null, (MessagePostProcessor)null);
    }

    @Override
    public <T> void convertAndSend(String destinationName, T payload, @Nullable Map<String, Object> headers) {
        this.convertAndSend(destinationName, payload, headers, (MessagePostProcessor)null);
    }

    @Override
    public <T> void convertAndSend(String destinationName, T payload, @Nullable MessagePostProcessor postProcessor) {
        this.convertAndSend(destinationName, payload, (Map<String, Object>)null, postProcessor);
    }

    @Override
    public <T> void convertAndSend(String destinationName, T payload, @Nullable Map<String, Object> headers, @Nullable MessagePostProcessor postProcessor) {
        D destination = this.resolveDestination(destinationName);
        super.convertAndSend(destination, payload, headers, postProcessor);
    }

    @Override
    @Nullable
    public Message<?> receive(String destinationName) {
        D destination = this.resolveDestination(destinationName);
        return super.receive(destination);
    }

    @Override
    @Nullable
    public <T> T receiveAndConvert(String destinationName, Class<T> targetClass) {
        D destination = this.resolveDestination(destinationName);
        return super.receiveAndConvert(destination, targetClass);
    }

    @Override
    @Nullable
    public Message<?> sendAndReceive(String destinationName, Message<?> requestMessage) {
        D destination = this.resolveDestination(destinationName);
        return super.sendAndReceive(destination, requestMessage);
    }

    @Override
    @Nullable
    public <T> T convertSendAndReceive(String destinationName, Object request, Class<T> targetClass) {
        D destination = this.resolveDestination(destinationName);
        return super.convertSendAndReceive(destination, request, targetClass);
    }

    @Override
    @Nullable
    public <T> T convertSendAndReceive(String destinationName, Object request, @Nullable Map<String, Object> headers, Class<T> targetClass) {
        D destination = this.resolveDestination(destinationName);
        return super.convertSendAndReceive(destination, request, headers, targetClass);
    }

    @Override
    @Nullable
    public <T> T convertSendAndReceive(String destinationName, Object request, Class<T> targetClass, @Nullable MessagePostProcessor postProcessor) {
        D destination = this.resolveDestination(destinationName);
        return super.convertSendAndReceive(destination, request, targetClass, postProcessor);
    }

    @Override
    @Nullable
    public <T> T convertSendAndReceive(String destinationName, Object request, @Nullable Map<String, Object> headers, Class<T> targetClass, @Nullable MessagePostProcessor postProcessor) {
        D destination = this.resolveDestination(destinationName);
        return super.convertSendAndReceive(destination, request, headers, targetClass, postProcessor);
    }
}

