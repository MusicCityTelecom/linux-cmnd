/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.authentication.principal.Principal
 */
package org.apereo.cas.notifications.push;

import java.util.List;
import java.util.Map;
import lombok.Generated;
import org.apereo.cas.authentication.principal.Principal;
import org.apereo.cas.notifications.push.NotificationSender;

public class DefaultNotificationSender
implements NotificationSender {
    private final List<NotificationSender> notificationSenders;

    @Override
    public boolean canSend() {
        return this.notificationSenders.stream().anyMatch(NotificationSender::canSend);
    }

    @Override
    public boolean notify(Principal principal, Map<String, String> messageData) {
        return this.notificationSenders.stream().anyMatch(sender -> {
            if (sender.canSend()) {
                sender.notify(principal, messageData);
                return true;
            }
            return false;
        });
    }

    @Generated
    public DefaultNotificationSender(List<NotificationSender> notificationSenders) {
        this.notificationSenders = notificationSenders;
    }
}

