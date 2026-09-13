/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.lang.Nullable
 *  org.springframework.messaging.Message
 *  org.springframework.messaging.support.MessageHeaderAccessor
 *  org.springframework.util.Assert
 *  org.springframework.util.ObjectUtils
 */
package org.springframework.integration;

import java.io.Closeable;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;
import org.springframework.integration.acks.AcknowledgmentCallback;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

public class IntegrationMessageHeaderAccessor
extends MessageHeaderAccessor {
    public static final String CORRELATION_ID = "correlationId";
    public static final String EXPIRATION_DATE = "expirationDate";
    public static final String PRIORITY = "priority";
    public static final String SEQUENCE_NUMBER = "sequenceNumber";
    public static final String SEQUENCE_SIZE = "sequenceSize";
    public static final String SEQUENCE_DETAILS = "sequenceDetails";
    public static final String ROUTING_SLIP = "routingSlip";
    public static final String DUPLICATE_MESSAGE = "duplicateMessage";
    public static final String CLOSEABLE_RESOURCE = "closeableResource";
    public static final String DELIVERY_ATTEMPT = "deliveryAttempt";
    public static final String ACKNOWLEDGMENT_CALLBACK = "acknowledgmentCallback";
    public static final String SOURCE_DATA = "sourceData";
    private static final BiFunction<String, String, String> TYPE_VERIFY_MESSAGE_FUNCTION = (name, trailer) -> "The '" + name + trailer;
    private Set<String> readOnlyHeaders = new HashSet<String>();

    public IntegrationMessageHeaderAccessor(@Nullable Message<?> message) {
        super(message);
    }

    public void setReadOnlyHeaders(String ... readOnlyHeaders) {
        Assert.noNullElements((Object[])readOnlyHeaders, (String)"'readOnlyHeaders' must not be contain null items.");
        if (!ObjectUtils.isEmpty((Object[])readOnlyHeaders)) {
            this.readOnlyHeaders = new HashSet<String>(Arrays.asList(readOnlyHeaders));
        }
    }

    @Nullable
    public Long getExpirationDate() {
        return this.getHeader(EXPIRATION_DATE, Long.class);
    }

    @Nullable
    public Object getCorrelationId() {
        return this.getHeader(CORRELATION_ID);
    }

    public int getSequenceNumber() {
        Number sequenceNumber = this.getHeader(SEQUENCE_NUMBER, Number.class);
        return sequenceNumber != null ? sequenceNumber.intValue() : 0;
    }

    public int getSequenceSize() {
        Number sequenceSize = this.getHeader(SEQUENCE_SIZE, Number.class);
        return sequenceSize != null ? sequenceSize.intValue() : 0;
    }

    @Nullable
    public Integer getPriority() {
        Number priority = this.getHeader(PRIORITY, Number.class);
        return priority != null ? Integer.valueOf(priority.intValue()) : null;
    }

    @Nullable
    public Closeable getCloseableResource() {
        return this.getHeader(CLOSEABLE_RESOURCE, Closeable.class);
    }

    @Nullable
    public AcknowledgmentCallback getAcknowledgmentCallback() {
        return this.getHeader(ACKNOWLEDGMENT_CALLBACK, AcknowledgmentCallback.class);
    }

    @Nullable
    public AtomicInteger getDeliveryAttempt() {
        return this.getHeader(DELIVERY_ATTEMPT, AtomicInteger.class);
    }

    @Nullable
    public <T> T getSourceData() {
        return (T)this.getHeader(SOURCE_DATA);
    }

    @Nullable
    public <T> T getHeader(String key, Class<T> type) {
        Object value = this.getHeader(key);
        if (value == null) {
            return null;
        }
        if (!type.isAssignableFrom(value.getClass())) {
            throw new IllegalArgumentException("Incorrect type specified for header '" + key + "'. Expected [" + type + "] but actual type is [" + value.getClass() + "]");
        }
        return (T)value;
    }

    protected void verifyType(String headerName, Object headerValue) {
        if (headerName != null && headerValue != null) {
            super.verifyType(headerName, headerValue);
            if (EXPIRATION_DATE.equals(headerName)) {
                Assert.isTrue((headerValue instanceof Date || headerValue instanceof Long ? 1 : 0) != 0, (String)TYPE_VERIFY_MESSAGE_FUNCTION.apply(headerName, "' header value must be a Date or Long."));
            } else if (SEQUENCE_NUMBER.equals(headerName) || SEQUENCE_SIZE.equals(headerName) || PRIORITY.equals(headerName)) {
                Assert.isTrue((boolean)Number.class.isAssignableFrom(headerValue.getClass()), (String)TYPE_VERIFY_MESSAGE_FUNCTION.apply(headerName, "' header value must be a Number."));
            } else if (ROUTING_SLIP.equals(headerName)) {
                Assert.isTrue((boolean)Map.class.isAssignableFrom(headerValue.getClass()), (String)TYPE_VERIFY_MESSAGE_FUNCTION.apply(headerName, "' header value must be a Map."));
            } else if (DUPLICATE_MESSAGE.equals(headerName)) {
                Assert.isTrue((boolean)Boolean.class.isAssignableFrom(headerValue.getClass()), (String)TYPE_VERIFY_MESSAGE_FUNCTION.apply(headerName, "' header value must be an Boolean."));
            }
        }
    }

    public boolean isReadOnly(String headerName) {
        return super.isReadOnly(headerName) || this.readOnlyHeaders.contains(headerName);
    }

    public Map<String, Object> toMap() {
        if (ObjectUtils.isEmpty(this.readOnlyHeaders)) {
            return super.toMap();
        }
        Map headers = super.toMap();
        for (String header : this.readOnlyHeaders) {
            headers.remove(header);
        }
        return headers;
    }
}

