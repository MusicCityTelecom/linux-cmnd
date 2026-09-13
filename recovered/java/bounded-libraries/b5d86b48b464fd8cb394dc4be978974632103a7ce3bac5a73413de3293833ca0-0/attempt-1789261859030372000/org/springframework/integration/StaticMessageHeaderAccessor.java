/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.lang.Nullable
 *  org.springframework.messaging.Message
 *  org.springframework.util.MimeType
 */
package org.springframework.integration;

import java.io.Closeable;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.integration.acks.AcknowledgmentCallback;
import org.springframework.integration.acks.SimpleAcknowledgment;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.util.MimeType;

public final class StaticMessageHeaderAccessor {
    private StaticMessageHeaderAccessor() {
    }

    @Nullable
    public static UUID getId(Message<?> message) {
        Object value = message.getHeaders().get((Object)"id");
        if (value == null) {
            return null;
        }
        return value instanceof UUID ? (UUID)value : UUID.fromString(value.toString());
    }

    @Nullable
    public static Long getTimestamp(Message<?> message) {
        Object value = message.getHeaders().get((Object)"timestamp");
        if (value == null) {
            return null;
        }
        return value instanceof Long ? (Long)value : Long.parseLong(value.toString());
    }

    @Nullable
    public static MimeType getContentType(Message<?> message) {
        Object value = message.getHeaders().get((Object)"contentType");
        if (value == null) {
            return null;
        }
        return value instanceof MimeType ? (MimeType)value : MimeType.valueOf((String)value.toString());
    }

    @Nullable
    public static Long getExpirationDate(Message<?> message) {
        return (Long)message.getHeaders().get((Object)"expirationDate", Long.class);
    }

    public static int getSequenceNumber(Message<?> message) {
        Number sequenceNumber = (Number)message.getHeaders().get((Object)"sequenceNumber", Number.class);
        return sequenceNumber != null ? sequenceNumber.intValue() : 0;
    }

    public static int getSequenceSize(Message<?> message) {
        Number sequenceSize = (Number)message.getHeaders().get((Object)"sequenceSize", Number.class);
        return sequenceSize != null ? sequenceSize.intValue() : 0;
    }

    @Nullable
    public static Integer getPriority(Message<?> message) {
        Number priority = (Number)message.getHeaders().get((Object)"priority", Number.class);
        return priority != null ? Integer.valueOf(priority.intValue()) : null;
    }

    @Nullable
    public static Closeable getCloseableResource(Message<?> message) {
        return (Closeable)message.getHeaders().get((Object)"closeableResource", Closeable.class);
    }

    @Nullable
    public static AtomicInteger getDeliveryAttempt(Message<?> message) {
        return (AtomicInteger)message.getHeaders().get((Object)"deliveryAttempt", AtomicInteger.class);
    }

    @Nullable
    public static AcknowledgmentCallback getAcknowledgmentCallback(Message<?> message) {
        return (AcknowledgmentCallback)message.getHeaders().get((Object)"acknowledgmentCallback", AcknowledgmentCallback.class);
    }

    @Nullable
    public static SimpleAcknowledgment getAcknowledgment(Message<?> message) {
        return (SimpleAcknowledgment)message.getHeaders().get((Object)"acknowledgmentCallback", SimpleAcknowledgment.class);
    }

    @Nullable
    public static <T> T getSourceData(Message<?> message) {
        return (T)message.getHeaders().get((Object)"sourceData");
    }
}

