/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.lang.Nullable
 */
package org.springframework.messaging.core;

import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.converter.MessageConversionException;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.core.AbstractMessageSendingTemplate;
import org.springframework.messaging.core.MessageReceivingOperations;

public abstract class AbstractMessageReceivingTemplate<D>
extends AbstractMessageSendingTemplate<D>
implements MessageReceivingOperations<D> {
    @Override
    @Nullable
    public Message<?> receive() {
        return this.doReceive(this.getRequiredDefaultDestination());
    }

    @Override
    @Nullable
    public Message<?> receive(D destination) {
        return this.doReceive(destination);
    }

    @Nullable
    protected abstract Message<?> doReceive(D var1);

    @Override
    @Nullable
    public <T> T receiveAndConvert(Class<T> targetClass) {
        return this.receiveAndConvert(this.getRequiredDefaultDestination(), targetClass);
    }

    @Override
    @Nullable
    public <T> T receiveAndConvert(D destination, Class<T> targetClass) {
        Message<?> message = this.doReceive(destination);
        if (message != null) {
            return this.doConvert(message, targetClass);
        }
        return null;
    }

    @Nullable
    protected <T> T doConvert(Message<?> message, Class<T> targetClass) {
        MessageConverter messageConverter = this.getMessageConverter();
        Object value = messageConverter.fromMessage(message, targetClass);
        if (value == null) {
            throw new MessageConversionException(message, "Unable to convert payload [" + message.getPayload() + "] to type [" + targetClass + "] using converter [" + messageConverter + "]");
        }
        return (T)value;
    }
}

