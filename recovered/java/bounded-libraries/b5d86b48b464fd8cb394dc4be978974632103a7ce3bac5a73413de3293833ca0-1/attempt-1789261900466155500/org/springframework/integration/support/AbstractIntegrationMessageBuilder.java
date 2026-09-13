/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.lang.Nullable
 *  org.springframework.messaging.Message
 *  org.springframework.messaging.MessageChannel
 *  org.springframework.util.Assert
 *  org.springframework.util.ObjectUtils
 *  org.springframework.util.PatternMatchUtils
 */
package org.springframework.integration.support;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.util.PatternMatchUtils;

public abstract class AbstractIntegrationMessageBuilder<T> {
    public AbstractIntegrationMessageBuilder<T> setExpirationDate(Long expirationDate) {
        return this.setHeader("expirationDate", expirationDate);
    }

    public AbstractIntegrationMessageBuilder<T> setExpirationDate(@Nullable Date expirationDate) {
        if (expirationDate != null) {
            return this.setHeader("expirationDate", expirationDate.getTime());
        }
        return this.setHeader("expirationDate", null);
    }

    public AbstractIntegrationMessageBuilder<T> setCorrelationId(Object correlationId) {
        return this.setHeader("correlationId", correlationId);
    }

    public AbstractIntegrationMessageBuilder<T> pushSequenceDetails(Object correlationId, int sequenceNumber, int sequenceSize) {
        Object incomingCorrelationId = this.getCorrelationId();
        List<List<Object>> incomingSequenceDetails = this.getSequenceDetails();
        if (incomingCorrelationId != null) {
            incomingSequenceDetails = incomingSequenceDetails == null ? new ArrayList<List<Object>>() : new ArrayList<List<Object>>(incomingSequenceDetails);
            incomingSequenceDetails.add(Arrays.asList(incomingCorrelationId, this.getSequenceNumber(), this.getSequenceSize()));
            incomingSequenceDetails = Collections.unmodifiableList(incomingSequenceDetails);
        }
        if (incomingSequenceDetails != null) {
            this.setHeader("sequenceDetails", incomingSequenceDetails);
        }
        return this.setCorrelationId(correlationId).setSequenceNumber(sequenceNumber).setSequenceSize(sequenceSize);
    }

    public AbstractIntegrationMessageBuilder<T> popSequenceDetails() {
        List<List<Object>> incomingSequenceDetails = this.getSequenceDetails();
        if (incomingSequenceDetails == null) {
            return this;
        }
        List<Object> sequenceDetails = (incomingSequenceDetails = new ArrayList<List<Object>>(incomingSequenceDetails)).remove(incomingSequenceDetails.size() - 1);
        Assert.state((sequenceDetails.size() == 3 ? 1 : 0) != 0, () -> "Wrong sequence details (not created by MessageBuilder?): " + sequenceDetails);
        this.setCorrelationId(sequenceDetails.get(0));
        Integer sequenceNumber = (Integer)sequenceDetails.get(1);
        Integer sequenceSize = (Integer)sequenceDetails.get(2);
        if (sequenceNumber != null) {
            this.setSequenceNumber(sequenceNumber);
        }
        if (sequenceSize != null) {
            this.setSequenceSize(sequenceSize);
        }
        if (!incomingSequenceDetails.isEmpty()) {
            this.setHeader("sequenceDetails", incomingSequenceDetails);
        } else {
            this.removeHeader("sequenceDetails");
        }
        return this;
    }

    public AbstractIntegrationMessageBuilder<T> setReplyChannel(MessageChannel replyChannel) {
        return this.setHeader("replyChannel", replyChannel);
    }

    public AbstractIntegrationMessageBuilder<T> setReplyChannelName(String replyChannelName) {
        return this.setHeader("replyChannel", replyChannelName);
    }

    public AbstractIntegrationMessageBuilder<T> setErrorChannel(MessageChannel errorChannel) {
        return this.setHeader("errorChannel", errorChannel);
    }

    public AbstractIntegrationMessageBuilder<T> setErrorChannelName(String errorChannelName) {
        return this.setHeader("errorChannel", errorChannelName);
    }

    public AbstractIntegrationMessageBuilder<T> setSequenceNumber(Integer sequenceNumber) {
        return this.setHeader("sequenceNumber", sequenceNumber);
    }

    public AbstractIntegrationMessageBuilder<T> setSequenceSize(Integer sequenceSize) {
        return this.setHeader("sequenceSize", sequenceSize);
    }

    public AbstractIntegrationMessageBuilder<T> setPriority(Integer priority) {
        return this.setHeader("priority", priority);
    }

    public AbstractIntegrationMessageBuilder<T> filterAndCopyHeadersIfAbsent(Map<String, ?> headersToCopy, String ... headerPatternsToFilter) {
        Map<String, ?> headers = headersToCopy;
        if (!ObjectUtils.isEmpty((Object[])headerPatternsToFilter)) {
            headers = new HashMap(headersToCopy);
            headers.entrySet().removeIf(entry -> PatternMatchUtils.simpleMatch((String[])headerPatternsToFilter, (String)((String)entry.getKey())));
        }
        return this.copyHeadersIfAbsent(headers);
    }

    @Nullable
    protected abstract List<List<Object>> getSequenceDetails();

    @Nullable
    protected abstract Object getCorrelationId();

    @Nullable
    protected abstract Object getSequenceNumber();

    @Nullable
    protected abstract Object getSequenceSize();

    public abstract T getPayload();

    public abstract Map<String, Object> getHeaders();

    @Nullable
    public abstract <V> V getHeader(String var1, Class<V> var2);

    public abstract AbstractIntegrationMessageBuilder<T> setHeader(String var1, @Nullable Object var2);

    public abstract AbstractIntegrationMessageBuilder<T> setHeaderIfAbsent(String var1, Object var2);

    public abstract AbstractIntegrationMessageBuilder<T> removeHeaders(String ... var1);

    public abstract AbstractIntegrationMessageBuilder<T> removeHeader(String var1);

    public abstract AbstractIntegrationMessageBuilder<T> copyHeaders(@Nullable Map<String, ?> var1);

    public abstract AbstractIntegrationMessageBuilder<T> copyHeadersIfAbsent(@Nullable Map<String, ?> var1);

    public abstract Message<T> build();
}

