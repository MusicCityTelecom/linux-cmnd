/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.lang.Nullable
 *  org.springframework.messaging.Message
 *  org.springframework.messaging.MessageHeaders
 *  org.springframework.util.Assert
 *  org.springframework.util.PatternMatchUtils
 *  org.springframework.util.StringUtils
 */
package org.springframework.integration.support;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.integration.support.AbstractIntegrationMessageBuilder;
import org.springframework.integration.support.MutableMessage;
import org.springframework.integration.support.MutableMessageHeaders;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.util.Assert;
import org.springframework.util.PatternMatchUtils;
import org.springframework.util.StringUtils;

public final class MutableMessageBuilder<T>
extends AbstractIntegrationMessageBuilder<T> {
    private final MutableMessage<T> mutableMessage;
    private final Map<String, Object> headers;

    private MutableMessageBuilder(Message<T> message) {
        Assert.notNull(message, (String)"message must not be null");
        this.mutableMessage = message instanceof MutableMessage ? (MutableMessage<Object>)message : new MutableMessage<Object>(message.getPayload(), (Map<String, Object>)message.getHeaders());
        this.headers = this.mutableMessage.getRawHeaders();
    }

    @Override
    public T getPayload() {
        return this.mutableMessage.getPayload();
    }

    @Override
    public Map<String, Object> getHeaders() {
        return this.headers;
    }

    @Override
    @Nullable
    public <V> V getHeader(String key, Class<V> type) {
        Object value = this.headers.get(key);
        if (value == null) {
            return null;
        }
        if (!type.isAssignableFrom(value.getClass())) {
            throw new IllegalArgumentException("Incorrect type specified for header '" + key + "'. Expected [" + type + "] but actual type is [" + value.getClass() + "]");
        }
        return (V)value;
    }

    public static <T> MutableMessageBuilder<T> withPayload(T payload) {
        return MutableMessageBuilder.withPayload(payload, true);
    }

    public static <T> MutableMessageBuilder<T> withPayload(T payload, boolean generateHeaders) {
        MutableMessage<T> message = generateHeaders ? new MutableMessage<T>(payload) : new MutableMessage<T>(payload, new MutableMessageHeaders(null, MessageHeaders.ID_VALUE_NONE, -1L));
        return MutableMessageBuilder.fromMessage(message);
    }

    public static <T> MutableMessageBuilder<T> fromMessage(Message<T> message) {
        Assert.notNull(message, (String)"'message' must not be null");
        return new MutableMessageBuilder<T>(message);
    }

    @Override
    public AbstractIntegrationMessageBuilder<T> setHeader(String headerName, @Nullable Object headerValue) {
        Assert.notNull((Object)headerName, (String)"'headerName' must not be null");
        if (headerValue == null) {
            this.removeHeader(headerName);
        } else {
            this.headers.put(headerName, headerValue);
        }
        return this;
    }

    @Override
    public AbstractIntegrationMessageBuilder<T> setHeaderIfAbsent(String headerName, Object headerValue) {
        if (!this.headers.containsKey(headerName)) {
            this.headers.put(headerName, headerValue);
        }
        return this;
    }

    @Override
    public AbstractIntegrationMessageBuilder<T> removeHeaders(String ... headerPatterns) {
        ArrayList<String> headersToRemove = new ArrayList<String>();
        for (String pattern : headerPatterns) {
            if (!StringUtils.hasLength((String)pattern)) continue;
            if (pattern.contains("*")) {
                headersToRemove.addAll(this.getMatchingHeaderNames(pattern, this.headers));
                continue;
            }
            headersToRemove.add(pattern);
        }
        for (String headerToRemove : headersToRemove) {
            this.removeHeader(headerToRemove);
        }
        return this;
    }

    private List<String> getMatchingHeaderNames(String pattern, Map<String, Object> headers) {
        ArrayList<String> matchingHeaderNames = new ArrayList<String>();
        if (headers != null) {
            for (Map.Entry<String, Object> header : headers.entrySet()) {
                if (!PatternMatchUtils.simpleMatch((String)pattern, (String)header.getKey())) continue;
                matchingHeaderNames.add(header.getKey());
            }
        }
        return matchingHeaderNames;
    }

    @Override
    public AbstractIntegrationMessageBuilder<T> removeHeader(String headerName) {
        if (StringUtils.hasLength((String)headerName)) {
            this.headers.remove(headerName);
        }
        return this;
    }

    @Override
    public AbstractIntegrationMessageBuilder<T> copyHeaders(@Nullable Map<String, ?> headersToCopy) {
        if (headersToCopy != null) {
            this.headers.putAll(headersToCopy);
        }
        return this;
    }

    @Override
    public AbstractIntegrationMessageBuilder<T> copyHeadersIfAbsent(@Nullable Map<String, ?> headersToCopy) {
        if (headersToCopy != null) {
            for (Map.Entry<String, ?> entry : headersToCopy.entrySet()) {
                this.setHeaderIfAbsent(entry.getKey(), entry.getValue());
            }
        }
        return this;
    }

    @Override
    protected List<List<Object>> getSequenceDetails() {
        return (List)this.headers.get("sequenceDetails");
    }

    @Override
    protected Object getCorrelationId() {
        return this.headers.get("correlationId");
    }

    @Override
    protected Object getSequenceNumber() {
        return this.headers.get("sequenceNumber");
    }

    @Override
    protected Object getSequenceSize() {
        return this.headers.get("sequenceSize");
    }

    @Override
    public Message<T> build() {
        return this.mutableMessage;
    }
}

