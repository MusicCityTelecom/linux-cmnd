/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.integration.mapping.OutboundMessageMapper
 *  org.springframework.lang.Nullable
 *  org.springframework.messaging.Message
 *  org.springframework.util.Assert
 */
package org.springframework.integration.jmx;

import java.util.concurrent.atomic.AtomicLong;
import javax.management.Notification;
import javax.management.ObjectName;
import org.springframework.integration.mapping.OutboundMessageMapper;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.util.Assert;

class DefaultNotificationMapper
implements OutboundMessageMapper<Notification> {
    private final ObjectName sourceObjectName;
    @Nullable
    private final String defaultNotificationType;
    private final AtomicLong sequence = new AtomicLong();

    DefaultNotificationMapper(ObjectName sourceObjectName, @Nullable String defaultNotificationType) {
        this.sourceObjectName = sourceObjectName;
        this.defaultNotificationType = defaultNotificationType;
    }

    public Notification fromMessage(Message<?> message) {
        String type = this.resolveNotificationType(message);
        Assert.hasText((String)type, (String)"No notification type header is available, and no default has been provided.");
        Object payload = message.getPayload();
        String notificationMessage = payload instanceof String ? (String)payload : null;
        Notification notification = new Notification(type, this.sourceObjectName, this.sequence.incrementAndGet(), System.currentTimeMillis(), notificationMessage);
        if (!(payload instanceof String)) {
            notification.setUserData(payload);
        }
        return notification;
    }

    private String resolveNotificationType(Message<?> message) {
        String type = (String)message.getHeaders().get((Object)"jmx_notificationType", String.class);
        return type != null ? type : this.defaultNotificationType;
    }
}

