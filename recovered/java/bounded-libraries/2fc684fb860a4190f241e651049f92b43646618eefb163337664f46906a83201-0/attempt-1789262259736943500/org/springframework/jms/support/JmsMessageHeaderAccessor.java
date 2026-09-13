/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.jms.Destination
 *  org.springframework.lang.Nullable
 *  org.springframework.messaging.Message
 *  org.springframework.messaging.support.NativeMessageHeaderAccessor
 */
package org.springframework.jms.support;

import java.util.List;
import java.util.Map;
import javax.jms.Destination;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.NativeMessageHeaderAccessor;

public class JmsMessageHeaderAccessor
extends NativeMessageHeaderAccessor {
    protected JmsMessageHeaderAccessor(Map<String, List<String>> nativeHeaders) {
        super(nativeHeaders);
    }

    protected JmsMessageHeaderAccessor(Message<?> message) {
        super(message);
    }

    @Nullable
    public String getCorrelationId() {
        return (String)this.getHeader("jms_correlationId");
    }

    @Nullable
    public Destination getDestination() {
        return (Destination)this.getHeader("jms_destination");
    }

    @Nullable
    public Integer getDeliveryMode() {
        return (Integer)this.getHeader("jms_deliveryMode");
    }

    @Nullable
    public Long getExpiration() {
        return (Long)this.getHeader("jms_expiration");
    }

    @Nullable
    public String getMessageId() {
        return (String)this.getHeader("jms_messageId");
    }

    @Nullable
    public Integer getPriority() {
        return (Integer)this.getHeader("jms_priority");
    }

    @Nullable
    public Destination getReplyTo() {
        return (Destination)this.getHeader("jms_replyTo");
    }

    @Nullable
    public Boolean getRedelivered() {
        return (Boolean)this.getHeader("jms_redelivered");
    }

    @Nullable
    public String getType() {
        return (String)this.getHeader("jms_type");
    }

    @Nullable
    public Long getTimestamp() {
        return (Long)this.getHeader("jms_timestamp");
    }

    public static JmsMessageHeaderAccessor wrap(Message<?> message) {
        return new JmsMessageHeaderAccessor(message);
    }
}

