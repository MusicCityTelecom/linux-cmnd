/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.lang.Nullable
 *  org.springframework.util.Assert
 */
package org.springframework.messaging.core;

import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.converter.MessageConversionException;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.converter.SimpleMessageConverter;
import org.springframework.messaging.converter.SmartMessageConverter;
import org.springframework.messaging.core.MessagePostProcessor;
import org.springframework.messaging.core.MessageSendingOperations;
import org.springframework.util.Assert;

public abstract class AbstractMessageSendingTemplate<D>
implements MessageSendingOperations<D> {
    public static final String CONVERSION_HINT_HEADER = "conversionHint";
    protected final Log logger = LogFactory.getLog(this.getClass());
    @Nullable
    private D defaultDestination;
    private MessageConverter converter = new SimpleMessageConverter();

    public void setDefaultDestination(@Nullable D defaultDestination) {
        this.defaultDestination = defaultDestination;
    }

    @Nullable
    public D getDefaultDestination() {
        return this.defaultDestination;
    }

    public void setMessageConverter(MessageConverter messageConverter) {
        Assert.notNull((Object)messageConverter, (String)"MessageConverter must not be null");
        this.converter = messageConverter;
    }

    public MessageConverter getMessageConverter() {
        return this.converter;
    }

    @Override
    public void send(Message<?> message) {
        this.send(this.getRequiredDefaultDestination(), message);
    }

    protected final D getRequiredDefaultDestination() {
        Assert.state((this.defaultDestination != null ? 1 : 0) != 0, (String)"No 'defaultDestination' configured");
        return this.defaultDestination;
    }

    @Override
    public void send(D destination, Message<?> message) {
        this.doSend(destination, message);
    }

    protected abstract void doSend(D var1, Message<?> var2);

    @Override
    public void convertAndSend(Object payload) throws MessagingException {
        this.convertAndSend(payload, null);
    }

    @Override
    public void convertAndSend(D destination, Object payload) throws MessagingException {
        this.convertAndSend(destination, payload, (Map<String, Object>)null);
    }

    @Override
    public void convertAndSend(D destination, Object payload, @Nullable Map<String, Object> headers) throws MessagingException {
        this.convertAndSend(destination, payload, headers, null);
    }

    @Override
    public void convertAndSend(Object payload, @Nullable MessagePostProcessor postProcessor) throws MessagingException {
        this.convertAndSend(this.getRequiredDefaultDestination(), payload, postProcessor);
    }

    @Override
    public void convertAndSend(D destination, Object payload, @Nullable MessagePostProcessor postProcessor) throws MessagingException {
        this.convertAndSend(destination, payload, null, postProcessor);
    }

    @Override
    public void convertAndSend(D destination, Object payload, @Nullable Map<String, Object> headers, @Nullable MessagePostProcessor postProcessor) throws MessagingException {
        Message<?> message = this.doConvert(payload, headers, postProcessor);
        this.send(destination, message);
    }

    protected Message<?> doConvert(Object payload, @Nullable Map<String, Object> headers, @Nullable MessagePostProcessor postProcessor) {
        MessageConverter converter;
        Message<?> message;
        MessageHeaders messageHeaders = null;
        Object conversionHint = headers != null ? headers.get(CONVERSION_HINT_HEADER) : null;
        Map<String, Object> headersToUse = this.processHeadersToSend(headers);
        if (headersToUse != null) {
            messageHeaders = headersToUse instanceof MessageHeaders ? (MessageHeaders)headersToUse : new MessageHeaders(headersToUse);
        }
        Message<?> message2 = message = (converter = this.getMessageConverter()) instanceof SmartMessageConverter ? ((SmartMessageConverter)converter).toMessage(payload, messageHeaders, conversionHint) : converter.toMessage(payload, messageHeaders);
        if (message == null) {
            String payloadType = payload.getClass().getName();
            Object contentType = messageHeaders != null ? messageHeaders.get("contentType") : null;
            throw new MessageConversionException("Unable to convert payload with type='" + payloadType + "', contentType='" + contentType + "', converter=[" + this.getMessageConverter() + "]");
        }
        if (postProcessor != null) {
            message = postProcessor.postProcessMessage(message);
        }
        return message;
    }

    @Nullable
    protected Map<String, Object> processHeadersToSend(@Nullable Map<String, Object> headers) {
        return headers;
    }
}

