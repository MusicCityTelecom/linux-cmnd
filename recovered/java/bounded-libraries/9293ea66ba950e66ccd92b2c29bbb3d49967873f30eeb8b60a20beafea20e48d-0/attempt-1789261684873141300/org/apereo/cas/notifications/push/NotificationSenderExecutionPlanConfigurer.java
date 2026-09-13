/*
 * Decompiled with CFR 0.152.
 */
package org.apereo.cas.notifications.push;

import org.apereo.cas.notifications.push.NotificationSender;

@FunctionalInterface
public interface NotificationSenderExecutionPlanConfigurer {
    default public String getName() {
        return this.getClass().getSimpleName();
    }

    public NotificationSender configureNotificationSender();
}

