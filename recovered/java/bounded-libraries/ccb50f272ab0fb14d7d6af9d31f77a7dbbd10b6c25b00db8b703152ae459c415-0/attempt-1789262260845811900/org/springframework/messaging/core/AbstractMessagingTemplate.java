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
import org.springframework.messaging.core.AbstractMessageReceivingTemplate;
import org.springframework.messaging.core.MessagePostProcessor;
import org.springframework.messaging.core.MessageRequestReplyOperations;

public abstract class AbstractMessagingTemplate<D>
extends AbstractMessageReceivingTemplate<D>
implements MessageRequestReplyOperations<D> {
    @Override
    @Nullable
    public Message<?> sendAndReceive(Message<?> requestMessage) {
        return this.sendAndReceive(this.getRequiredDefaultDestination(), requestMessage);
    }

    @Override
    @Nullable
    public Message<?> sendAndReceive(D destination, Message<?> requestMessage) {
        return this.doSendAndReceive(destination, requestMessage);
    }

    @Nullable
    protected abstract Message<?> doSendAndReceive(D var1, Message<?> var2);

    @Override
    @Nullable
    public <T> T convertSendAndReceive(Object request, Class<T> targetClass) {
        return this.convertSendAndReceive(this.getRequiredDefaultDestination(), request, targetClass);
    }

    @Override
    @Nullable
    public <T> T convertSendAndReceive(D destination, Object request, Class<T> targetClass) {
        return this.convertSendAndReceive(destination, request, null, targetClass);
    }

    @Override
    @Nullable
    public <T> T convertSendAndReceive(D destination, Object request, @Nullable Map<String, Object> headers, Class<T> targetClass) {
        return this.convertSendAndReceive(destination, request, headers, targetClass, null);
    }

    @Override
    @Nullable
    public <T> T convertSendAndReceive(Object request, Class<T> targetClass, @Nullable MessagePostProcessor postProcessor) {
        return this.convertSendAndReceive(this.getRequiredDefaultDestination(), request, targetClass, postProcessor);
    }

    @Override
    @Nullable
    public <T> T convertSendAndReceive(D destination, Object request, Class<T> targetClass, @Nullable MessagePostProcessor postProcessor) {
        return this.convertSendAndReceive(destination, request, null, targetClass, postProcessor);
    }

    @Override
    @Nullable
    public <T> T convertSendAndReceive(D destination, Object request, @Nullable Map<String, Object> headers, Class<T> targetClass, @Nullable MessagePostProcessor postProcessor) {
        Message<?> requestMessage = this.doConvert(request, headers, postProcessor);
        Message<?> replyMessage = this.sendAndReceive(destination, requestMessage);
        return (T)(replyMessage != null ? this.getMessageConverter().fromMessage(replyMessage, targetClass) : null);
    }
}

