/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.lang.Nullable
 *  org.springframework.messaging.Message
 *  org.springframework.messaging.MessageChannel
 *  org.springframework.messaging.MessageHeaders
 *  org.springframework.messaging.support.ErrorMessage
 *  org.springframework.messaging.support.GenericMessage
 *  org.springframework.util.Assert
 *  org.springframework.util.ObjectUtils
 */
package org.springframework.integration.support;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.integration.IntegrationMessageHeaderAccessor;
import org.springframework.integration.support.AbstractIntegrationMessageBuilder;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.ErrorMessage;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

public final class MessageBuilder<T>
extends AbstractIntegrationMessageBuilder<T> {
    private static final Log LOGGER = LogFactory.getLog(MessageBuilder.class);
    private final T payload;
    private final IntegrationMessageHeaderAccessor headerAccessor;
    @Nullable
    private final Message<T> originalMessage;
    private volatile boolean modified;
    private String[] readOnlyHeaders;

    private MessageBuilder(T payload, @Nullable Message<T> originalMessage) {
        Assert.notNull(payload, (String)"payload must not be null");
        this.payload = payload;
        this.originalMessage = originalMessage;
        this.headerAccessor = new IntegrationMessageHeaderAccessor(originalMessage);
        if (originalMessage != null) {
            this.modified = !this.payload.equals(originalMessage.getPayload());
        }
    }

    @Override
    public T getPayload() {
        return this.payload;
    }

    @Override
    public Map<String, Object> getHeaders() {
        return this.headerAccessor.toMap();
    }

    @Override
    @Nullable
    public <V> V getHeader(String key, Class<V> type) {
        return this.headerAccessor.getHeader(key, type);
    }

    public static <T> MessageBuilder<T> fromMessage(Message<T> message) {
        Assert.notNull(message, (String)"message must not be null");
        return new MessageBuilder<Object>(message.getPayload(), message);
    }

    public static <T> MessageBuilder<T> withPayload(T payload) {
        return new MessageBuilder<T>(payload, null);
    }

    @Override
    public MessageBuilder<T> setHeader(String headerName, @Nullable Object headerValue) {
        this.headerAccessor.setHeader(headerName, headerValue);
        return this;
    }

    @Override
    public MessageBuilder<T> setHeaderIfAbsent(String headerName, Object headerValue) {
        this.headerAccessor.setHeaderIfAbsent(headerName, headerValue);
        return this;
    }

    @Override
    public MessageBuilder<T> removeHeaders(String ... headerPatterns) {
        this.headerAccessor.removeHeaders(headerPatterns);
        return this;
    }

    @Override
    public MessageBuilder<T> removeHeader(String headerName) {
        if (!this.headerAccessor.isReadOnly(headerName)) {
            this.headerAccessor.removeHeader(headerName);
        } else if (LOGGER.isInfoEnabled()) {
            LOGGER.info((Object)("The header [" + headerName + "] is ignored for removal because it is is readOnly."));
        }
        return this;
    }

    @Override
    public MessageBuilder<T> copyHeaders(@Nullable Map<String, ?> headersToCopy) {
        this.headerAccessor.copyHeaders(headersToCopy);
        return this;
    }

    @Override
    public MessageBuilder<T> copyHeadersIfAbsent(@Nullable Map<String, ?> headersToCopy) {
        if (headersToCopy != null) {
            for (Map.Entry<String, ?> entry : headersToCopy.entrySet()) {
                String headerName = entry.getKey();
                if (this.headerAccessor.isReadOnly(headerName)) continue;
                this.headerAccessor.setHeaderIfAbsent(headerName, entry.getValue());
            }
        }
        return this;
    }

    @Override
    @Nullable
    protected List<List<Object>> getSequenceDetails() {
        return (List)this.headerAccessor.getHeader("sequenceDetails");
    }

    @Override
    @Nullable
    protected Object getCorrelationId() {
        return this.headerAccessor.getCorrelationId();
    }

    @Override
    protected Object getSequenceNumber() {
        return this.headerAccessor.getSequenceNumber();
    }

    @Override
    protected Object getSequenceSize() {
        return this.headerAccessor.getSequenceSize();
    }

    @Override
    public MessageBuilder<T> pushSequenceDetails(Object correlationId, int sequenceNumber, int sequenceSize) {
        super.pushSequenceDetails(correlationId, sequenceNumber, sequenceSize);
        return this;
    }

    @Override
    public MessageBuilder<T> popSequenceDetails() {
        super.popSequenceDetails();
        return this;
    }

    @Override
    public MessageBuilder<T> setExpirationDate(Long expirationDate) {
        super.setExpirationDate(expirationDate);
        return this;
    }

    @Override
    public MessageBuilder<T> setExpirationDate(Date expirationDate) {
        super.setExpirationDate(expirationDate);
        return this;
    }

    @Override
    public MessageBuilder<T> setCorrelationId(Object correlationId) {
        super.setCorrelationId(correlationId);
        return this;
    }

    @Override
    public MessageBuilder<T> setReplyChannel(MessageChannel replyChannel) {
        super.setReplyChannel(replyChannel);
        return this;
    }

    @Override
    public MessageBuilder<T> setReplyChannelName(String replyChannelName) {
        super.setReplyChannelName(replyChannelName);
        return this;
    }

    @Override
    public MessageBuilder<T> setErrorChannel(MessageChannel errorChannel) {
        super.setErrorChannel(errorChannel);
        return this;
    }

    @Override
    public MessageBuilder<T> setErrorChannelName(String errorChannelName) {
        super.setErrorChannelName(errorChannelName);
        return this;
    }

    @Override
    public MessageBuilder<T> setSequenceNumber(Integer sequenceNumber) {
        super.setSequenceNumber(sequenceNumber);
        return this;
    }

    @Override
    public MessageBuilder<T> setSequenceSize(Integer sequenceSize) {
        super.setSequenceSize(sequenceSize);
        return this;
    }

    @Override
    public MessageBuilder<T> setPriority(Integer priority) {
        super.setPriority(priority);
        return this;
    }

    public MessageBuilder<T> readOnlyHeaders(String ... readOnlyHeaders) {
        this.readOnlyHeaders = readOnlyHeaders != null ? Arrays.copyOf(readOnlyHeaders, readOnlyHeaders.length) : null;
        this.headerAccessor.setReadOnlyHeaders(readOnlyHeaders);
        return this;
    }

    @Override
    public Message<T> build() {
        if (!(this.modified || this.headerAccessor.isModified() || this.originalMessage == null || this.containsReadOnly(this.originalMessage.getHeaders()))) {
            return this.originalMessage;
        }
        if (this.payload instanceof Throwable) {
            return new ErrorMessage((Throwable)this.payload, this.headerAccessor.toMap());
        }
        return new GenericMessage(this.payload, this.headerAccessor.toMap());
    }

    private boolean containsReadOnly(MessageHeaders headers) {
        if (!ObjectUtils.isEmpty((Object[])this.readOnlyHeaders)) {
            for (String readOnly : this.readOnlyHeaders) {
                if (!headers.containsKey((Object)readOnly)) continue;
                return true;
            }
        }
        return false;
    }
}

