/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.jms.support;

public interface JmsHeaders {
    public static final String PREFIX = "jms_";
    public static final String CORRELATION_ID = "jms_correlationId";
    public static final String DESTINATION = "jms_destination";
    public static final String DELIVERY_MODE = "jms_deliveryMode";
    public static final String EXPIRATION = "jms_expiration";
    public static final String MESSAGE_ID = "jms_messageId";
    public static final String PRIORITY = "jms_priority";
    public static final String REPLY_TO = "jms_replyTo";
    public static final String REDELIVERED = "jms_redelivered";
    public static final String TYPE = "jms_type";
    public static final String TIMESTAMP = "jms_timestamp";
}

