/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.lang.Nullable
 *  org.springframework.util.Assert
 */
package org.springframework.messaging.support;

import java.util.Map;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.ErrorMessage;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.util.Assert;

public final class MessageBuilder<T> {
    private final T payload;
    @Nullable
    private final Message<T> providedMessage;
    private MessageHeaderAccessor headerAccessor;

    private MessageBuilder(Message<T> providedMessage) {
        Assert.notNull(providedMessage, (String)"Message must not be null");
        this.payload = providedMessage.getPayload();
        this.providedMessage = providedMessage;
        this.headerAccessor = new MessageHeaderAccessor(providedMessage);
    }

    private MessageBuilder(T payload, MessageHeaderAccessor accessor) {
        Assert.notNull(payload, (String)"Payload must not be null");
        Assert.notNull((Object)accessor, (String)"MessageHeaderAccessor must not be null");
        this.payload = payload;
        this.providedMessage = null;
        this.headerAccessor = accessor;
    }

    public MessageBuilder<T> setHeaders(MessageHeaderAccessor accessor) {
        Assert.notNull((Object)accessor, (String)"MessageHeaderAccessor must not be null");
        this.headerAccessor = accessor;
        return this;
    }

    public MessageBuilder<T> setHeader(String headerName, @Nullable Object headerValue) {
        this.headerAccessor.setHeader(headerName, headerValue);
        return this;
    }

    public MessageBuilder<T> setHeaderIfAbsent(String headerName, Object headerValue) {
        this.headerAccessor.setHeaderIfAbsent(headerName, headerValue);
        return this;
    }

    public MessageBuilder<T> removeHeaders(String ... headerPatterns) {
        this.headerAccessor.removeHeaders(headerPatterns);
        return this;
    }

    public MessageBuilder<T> removeHeader(String headerName) {
        this.headerAccessor.removeHeader(headerName);
        return this;
    }

    public MessageBuilder<T> copyHeaders(@Nullable Map<String, ?> headersToCopy) {
        this.headerAccessor.copyHeaders(headersToCopy);
        return this;
    }

    public MessageBuilder<T> copyHeadersIfAbsent(@Nullable Map<String, ?> headersToCopy) {
        this.headerAccessor.copyHeadersIfAbsent(headersToCopy);
        return this;
    }

    public MessageBuilder<T> setReplyChannel(MessageChannel replyChannel) {
        this.headerAccessor.setReplyChannel(replyChannel);
        return this;
    }

    public MessageBuilder<T> setReplyChannelName(String replyChannelName) {
        this.headerAccessor.setReplyChannelName(replyChannelName);
        return this;
    }

    public MessageBuilder<T> setErrorChannel(MessageChannel errorChannel) {
        this.headerAccessor.setErrorChannel(errorChannel);
        return this;
    }

    public MessageBuilder<T> setErrorChannelName(String errorChannelName) {
        this.headerAccessor.setErrorChannelName(errorChannelName);
        return this;
    }

    public Message<T> build() {
        if (this.providedMessage != null && !this.headerAccessor.isModified()) {
            return this.providedMessage;
        }
        MessageHeaders headersToUse = this.headerAccessor.toMessageHeaders();
        if (this.payload instanceof Throwable) {
            Message<?> message;
            if (this.providedMessage != null && this.providedMessage instanceof ErrorMessage && (message = ((ErrorMessage)this.providedMessage).getOriginalMessage()) != null) {
                return new ErrorMessage((Throwable)this.payload, headersToUse, message);
            }
            return new ErrorMessage((Throwable)this.payload, headersToUse);
        }
        return new GenericMessage<T>(this.payload, headersToUse);
    }

    public static <T> MessageBuilder<T> fromMessage(Message<T> message) {
        return new MessageBuilder<T>(message);
    }

    public static <T> MessageBuilder<T> withPayload(T payload) {
        return new MessageBuilder<T>(payload, new MessageHeaderAccessor());
    }

    public static <T> Message<T> createMessage(@Nullable T payload, MessageHeaders messageHeaders) {
        Assert.notNull(payload, (String)"Payload must not be null");
        Assert.notNull((Object)messageHeaders, (String)"MessageHeaders must not be null");
        if (payload instanceof Throwable) {
            return new ErrorMessage((Throwable)payload, messageHeaders);
        }
        return new GenericMessage<T>(payload, messageHeaders);
    }
}

